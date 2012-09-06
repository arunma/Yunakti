package sg.edu.nus.iss.yunakti.engine.writer;

import static sg.edu.nus.iss.yunakti.engine.util.YConstants.METHOD_ANNOTATION;
import static sg.edu.nus.iss.yunakti.engine.util.YConstants.TEST_CASE_ANNOTATION;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.engine.util.ParserUtils;
import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YMethod;
import sg.edu.nus.iss.yunakti.model.YModel;

public class YPersister {
	
	private static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public void writeAnnotation(YModel yModel) throws MalformedTreeException, BadLocationException, CoreException{
		
		if (yModel==null){
			return;
		}
		
		if (yModel.getTestCases()!=null){
			
			for (YClass eachTestCase : yModel.getTestCases()) {
					
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				logger.fine(eachTestCase.getPath());
				IPath location = Path.fromOSString(eachTestCase.getPath()); 
				IFile file = workspace.getRoot().getFileForLocation(location); 
				
				ICompilationUnit iCompilationUnit = (ICompilationUnit) JavaCore.create(file); 
				
				CompilationUnit compilationUnit = ParserUtils.parse(iCompilationUnit);
				
				compilationUnit.recordModifications();
				AST compilationUnitAst = compilationUnit.getAST();
				
				Map<String,String> annotationProperties=getHelperPropertyAsMap(eachTestCase);
				annotationProperties.putAll(getClassUnderTestPropertyAsMap(yModel));
				
				NormalAnnotation testCaseAnnotation = createAnnotation(compilationUnitAst, YConstants.TEST_CASE_ANNOTATION, annotationProperties);
				TypeDeclaration typeDeclaration = (TypeDeclaration)compilationUnit.types().get(0);
				
				ASTRewrite rewriter=ASTRewrite.create(compilationUnitAst);
				ListRewrite listRewriter = rewriter.getListRewrite(typeDeclaration, typeDeclaration.getModifiersProperty());
				removeTestCaseAnnotation(compilationUnit, listRewriter);

				
				if (!eachTestCase.isDeleteFlag()){
					listRewriter.insertAt(testCaseAnnotation, 0, null);
				}
				
				Map<String, List<YMethod>> methodsToBeAnnotatedToMap = convertMethodsToBeAnnotatedToMap(eachTestCase.getMethodsToBeAnnotated());
				addMethodLevelAnnotations(methodsToBeAnnotatedToMap, compilationUnit, listRewriter);
				
				String updatedUnit = "";
				TextEdit edits = null;
				try {
					updatedUnit = ((ICompilationUnit)compilationUnit.getJavaElement()).getSource();
					logger.fine("Updated unit String Before =====> \n" + updatedUnit);
				} catch (JavaModelException e1) {
					e1.printStackTrace();
					throw e1;
				}
				
				Document doc = new Document(updatedUnit);
				
				String newSource=doc.get();
				iCompilationUnit.getBuffer().setContents(newSource);
				
				edits = rewriter.rewriteAST(doc, null);
				logger.fine(edits.toString());
				edits.apply(doc);
				writeToFile(doc,file);

			    
				logger.fine("Updated unit String After  \n" + doc.get());
			}
		}
				
			
	}
	

	private Map<String, List<YMethod>> convertMethodsToBeAnnotatedToMap(List<YMethod> methodsToBeAnnotated) {
		Map<String,List<YMethod>> methodAnnotationMap=null;
		
		if (methodsToBeAnnotated!=null && methodsToBeAnnotated.size()>0){
			
			methodAnnotationMap=new HashMap<String,List<YMethod>>();
			
			for (YMethod yMethod : methodsToBeAnnotated) {
				
				if (methodAnnotationMap.get(yMethod.getMethodName())!=null){
					methodAnnotationMap.get(yMethod.getMethodName()).add(yMethod);
				}
				else{
					List<YMethod> yMethodList=new ArrayList<YMethod>();
					yMethodList.add(yMethod);
					methodAnnotationMap.put(yMethod.getMethodName(), yMethodList);
				}
				
			}
		}
		return methodAnnotationMap;
	}


	private Map<String, String> getHelperPropertyAsMap(YClass eachTestCase) {
	
		Map<String,String> helperProperty=null;
		
		StringBuilder builder=null;
		if (eachTestCase.getMembers()!=null){
			helperProperty=new HashMap<String,String>();
			builder=new StringBuilder();
			
			for (YClass eachHelper : eachTestCase.getMembers()) {
				builder.append(eachHelper.getFullyQualifiedName()).append(YConstants.COMMA);
				
			}
			
			helperProperty.put(YConstants.ANNOTATION_PROPERTY_HELPER_CLASSES, StringUtils.substringBeforeLast(builder.toString(), YConstants.COMMA));
		}
		
		
		return helperProperty;
	
	}
	
	
	private Map<String, String> getClassUnderTestPropertyAsMap(YModel yModel) {
		
		Map<String,String> cutProperty=new HashMap<String,String>();
		cutProperty.put(YConstants.ANNOTATION_PROPERTY_CLASS_UNDER_TEST, StringUtils.remove(yModel.getClassUnderTest().getFullyQualifiedName(),YConstants.DOUBLE_QUOTE));
		return cutProperty;
	}
	

	private static NormalAnnotation createAnnotation(AST compilationUnitAst, String annotationName, Map<String, String> valuePairs){
			NormalAnnotation annotation = compilationUnitAst.newNormalAnnotation();
			annotation.setTypeName(annotation.getAST().newName(annotationName));
			
			if (valuePairs!=null){
				for (Map.Entry<String, String> entry : valuePairs.entrySet()) {
					MemberValuePair valuePair = createValuePair(annotation.getAST(),entry.getKey(), entry.getValue());
					annotation.values().add(valuePair);
				}	
			}
			
			return annotation;
	}
	
	
	
	private static MemberValuePair createValuePair(AST ast, String key, String value){
			MemberValuePair annotationProperty = ast.newMemberValuePair();
			annotationProperty.setName(annotationProperty.getAST().newSimpleName(key));
			StringLiteral literal = annotationProperty.getAST().newStringLiteral();
			literal.setLiteralValue(value);
			annotationProperty.setValue(literal);
			return annotationProperty;
	}
		
	
	

	
	public void removeTestCaseAnnotation(CompilationUnit comUnit, ListRewrite listRewriter){
		
		comUnit.accept(new TestCaseAnnotationVisitor(listRewriter));
		
	}

	
	
	private static class TestCaseAnnotationVisitor extends ASTVisitor {
		
		private ListRewrite rewriter;

		public TestCaseAnnotationVisitor(ListRewrite rewriter) {
			this.rewriter=rewriter;
		}
	    
		@Override
		public boolean visit(NormalAnnotation node) {
			
			logger.fine("Normal annotation.");
			removeTestCaseAnnotation(node);
			
			return super.visit(node);
		}
	 
		@Override
		public boolean visit(org.eclipse.jdt.core.dom.SingleMemberAnnotation node) {
			
			logger.fine("Single member annotation.");
			removeTestCaseAnnotation(node);
			
			return super.visit(node);
			
		}
		
		private void removeTestCaseAnnotation(Annotation node) {
			
			if (StringUtils.equals(node.getTypeName().getFullyQualifiedName(),TEST_CASE_ANNOTATION) || StringUtils.equals(node.getTypeName().getFullyQualifiedName(),METHOD_ANNOTATION) ){
				rewriter.remove(node, null);
			}
		}
	}
	
	
	

	private void addMethodLevelAnnotations(Map<String, List<YMethod>> methodsToBeAnnotatedToMap, CompilationUnit compilationUnit, ListRewrite listRewriter) {
		compilationUnit.accept(new MethodAnnotationVisitor(methodsToBeAnnotatedToMap, compilationUnit, listRewriter));
		
	}
	

	
	private static class MethodAnnotationVisitor extends ASTVisitor {
		
		private ListRewrite rewriter;
		private CompilationUnit compilationUnit;
		private Map<String, List<YMethod>> methodsToBeAnnotatedToMap;

		public MethodAnnotationVisitor(Map<String, List<YMethod>> methodsToBeAnnotatedToMap, CompilationUnit compilationUnit, ListRewrite rewriter) {
			this.methodsToBeAnnotatedToMap=methodsToBeAnnotatedToMap;
			this.compilationUnit=compilationUnit;
			this.rewriter=rewriter;
		}
	    
		@Override
		public boolean visit(MethodDeclaration node) {
			logger.fine("Method Declaration ");
			addAnnotation(node);
			return super.visit(node);
		}
		
		private void addAnnotation(MethodDeclaration node) {
			
			final List list= rewriter.getOriginalList();
			
			ConsoleStreamUtil.println("Adding annotation for "+node.resolveBinding().getName());
			if (methodsToBeAnnotatedToMap.containsKey(node.resolveBinding().getName())){
				ConsoleStreamUtil.println("Bingoooooo. Found method to annotate : "+node.resolveBinding().getName());
				NormalAnnotation methodAnnotation = createAnnotation(compilationUnit.getAST(), YConstants.METHOD_ANNOTATION, null);
				int indexOfMethodDeclaration = list.indexOf(node.getAST());
				//rewriter.insertAt(methodAnnotation, indexOfMethodDeclaration, null);
				rewriter.insertBefore(methodAnnotation, node, null);
			}
					
			/*Map<String,String> annotationProperties=getCalleesAsMap(yModel);
			annotationProperties.putAll(getClassUnderTestPropertyAsMap(yModel));*/
			
			
			
			/*if (StringUtils.equals(node.getTypeName().getFullyQualifiedName(),TEST_CASE_ANNOTATION) || StringUtils.equals(node.getTypeName().getFullyQualifiedName(),METHOD_ANNOTATION) ){
				rewriter.remove(node, null);
			}*/
			
			
			
		}
	}
	
	
/*
	private Map<String, String> getCalleesAsMap( eachTestCase) {
	
		Map<String,String> helperProperty=null;
		
		StringBuilder builder=null;
		if (eachTestCase.getMembers()!=null){
			helperProperty=new HashMap<String,String>();
			builder=new StringBuilder();
			
			for (YClass eachHelper : eachTestCase.getMembers()) {
				builder.append(eachHelper.getFullyQualifiedName()).append(YConstants.COMMA);
				
			}
			
			helperProperty.put(YConstants.ANNOTATION_PROPERTY_HELPER_CLASSES, StringUtils.substringBeforeLast(builder.toString(), YConstants.COMMA));
		}
		
		
		return helperProperty;
	
	}
	*/
	
	private void writeToFile(IDocument document, IFile file) throws CoreException{
		try {
			setContents(document.get(), file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void setContents(final String content, final IFile file) throws Exception {
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
				try {
					manager.connect(file.getFullPath(), LocationKind.IFILE, monitor);
					ITextFileBuffer buffer = manager.getTextFileBuffer(file.getFullPath(), LocationKind.IFILE);
					IDocument document = buffer.getDocument();
					document.set(content);
					buffer.commit(monitor, true);
				} finally {
					manager.disconnect(file.getFullPath(), LocationKind.IFILE, monitor);
				}
			}
		}, ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(file), IResource.NONE, new NullProgressMonitor());
	}
}
