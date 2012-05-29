package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

public class CompilationUnitVisitor extends ASTVisitor{
	
	private List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
	@Override
	public boolean visit(TypeDeclaration node) {
		types.add(node);
		return super.visit(node);
	}

	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
	
	private List<SimpleName> simpleNames = new ArrayList<SimpleName>();
	
	private List<SingleMemberAnnotation> annoList = new ArrayList<SingleMemberAnnotation>();
	
	@Override
	public boolean visit(SingleMemberAnnotation node) {
		annoList.add(node);
		return super.visit(node);
	}
	
	private List<AnnotationTypeMemberDeclaration> annoTypeList = new ArrayList<AnnotationTypeMemberDeclaration>();
	
	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		annoTypeList.add(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		methods.add(node);
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		fields.add(node);
		return super.visit(node); 
	}
	
	public boolean visit(SimpleName node){
		simpleNames.add(node);
		return super.visit(node); 
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public List<FieldDeclaration> getFields() {
		return fields;
	}
	
	public List<SimpleName> getNames() {
		return simpleNames;
	}
	
	public List<SingleMemberAnnotation> getAnnoList() {
		return annoList;
	}
	
	
	public Set<String> getInvokedObjects(){
		
		Set<String> objectList = new TreeSet<String>();
		List<SimpleName> nameList = getNames();   
        
        if(nameList != null && nameList.size() > 0){
        	for(SimpleName tmpName : nameList){
        		
        		//System.out.println("Printing simple name =====> " + tmpName.getFullyQualifiedName());
        		if(tmpName.resolveTypeBinding() != null && tmpName.resolveTypeBinding().isClass()){
        			//System.out.println("Printing simple name =====> " + tmpName.getFullyQualifiedName() + " / " + tmpName.resolveTypeBinding().getQualifiedName());
        			objectList.add(tmpName.resolveTypeBinding().getQualifiedName());
        		}
        	}
        }
		
		
		return objectList;
		
		/*
		List<FieldDeclaration> fieldList = getFields();
		if(fieldList != null && fieldList.size() > 0){
			
			for(FieldDeclaration tmpField : fieldList){
				
				Type tmpType = tmpField.getType();
				if(!tmpType.isPrimitiveType()){
					objectList.add(tmpType.getClass().getName());
					//System.out.println("Field class ====> " + tmpType.toString());
				}
			}
		}
		
		List<MethodDeclaration> methodList = getMethods();
		if(methodList != null && methodList.size() > 0){
			
			for(MethodDeclaration tmpMethod : methodList){
				System.out.println(tmpMethod.getName());
				
				IMethodBinding methodBind = tmpMethod.resolveBinding(); 
				//System.out.println("Qulified name for return type ======> " + methodBind.getReturnType().getQualifiedName());
				ITypeBinding[] tmpTypeBinding = methodBind.getParameterTypes();
				for(int i = 0; i < tmpTypeBinding.length; i ++){
					
					if(tmpTypeBinding[i].isClass()){
						//System.out.println("Param Type ======> " + tmpTypeBinding[i].getQualifiedName());
						objectList.add(tmpTypeBinding[i].getQualifiedName());
					}
				}
				
				ITypeBinding tmpReturnType = methodBind.getReturnType();
				if(tmpReturnType.isClass()){
					//System.out.println("Return Type ======> " + tmpReturnType.getQualifiedName());
				}
				
				
				List statements = tmpMethod.getBody().statements();
				Iterator iter=statements.iterator();
	            while(iter.hasNext()){
	            	Statement stmt=(Statement)iter.next();
	            }
	        }
		}
		
		

		if(annoList != null && annoList.size() > 0){
			
			for(SingleMemberAnnotation tmpAnno : annoList){
				System.out.println("Annotation Name ===> " + tmpAnno.resolveAnnotationBinding().getName());
				System.out.println("Annotation Value ===> " + tmpAnno.resolveAnnotationBinding().getDeclaredMemberValuePairs()[0].getValue());
				if("TC".equals(tmpAnno.resolveAnnotationBinding().getName())){
					tmpAnno.delete();
					System.out.println("TC deleted");
				}
			}
		}
		else{
			
			System.out.println("Annotation is empty!!!");
			if(types != null && types.size() > 0){
				
				for(TypeDeclaration tmpType : types){
					System.out.println("Types =====> " + tmpType.getName());
					AST ast = tmpType.getAST();
					//ASTRewrite rewriter = ASTRewrite.create(ast);
					SingleMemberAnnotation testClassAnnotation = ast.newSingleMemberAnnotation();
					testClassAnnotation.setTypeName(ast.newSimpleName("TC"));
					testClassAnnotation.setValue(ast.newSimpleName("Class"));
					System.out.println("Modifiers ====> " + tmpType.modifiers().size() + " / " + tmpType.modifiers().get(0));
					tmpType.modifiers().add(0,testClassAnnotation);
					System.out.println("Modifiers after ====> " + tmpType.modifiers().size() + " / " + tmpType.modifiers().get(0));
					
					System.out.println("method annotated");
				}
			}
			
		}
		*/
        
        
	}
	
	
	public String markTestClassAnnotation(CompilationUnit comUnit, String CUTQualifiedName){
		
		AST ast = comUnit.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);
		SingleMemberAnnotation testClassAnnotation = ast.newSingleMemberAnnotation();
		testClassAnnotation.setTypeName(ast.newName("TC"));
		testClassAnnotation.setValue(ast.newName(CUTQualifiedName));
		
		TypeDeclaration td = (TypeDeclaration)comUnit.types().get(0);
		rewriter.getListRewrite(td, td.getModifiersProperty()).insertAt(testClassAnnotation, 0, null);
		String updatedUnit = "";
		TextEdit edits = null;
		try {
			updatedUnit = ((ICompilationUnit)comUnit.getJavaElement()).getSource();
			System.out.println("Updated unit String Before =====> " + updatedUnit);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = new Document(updatedUnit);
		edits = rewriter.rewriteAST(doc, null);
		try {
			edits.apply(doc);
			System.out.println("Updated unit String After =====> " + doc.get());
		} catch (MalformedTreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return doc.get();
	}
	
	public String removeTestClassAnnotation(CompilationUnit comUnit){
		
		AST ast = comUnit.getAST();
		ASTRewrite rewriter = ASTRewrite.create(ast);
		TypeDeclaration td = (TypeDeclaration)comUnit.types().get(0);
		
		List<SingleMemberAnnotation> annoList = getAnnoList();
		if(annoList != null && annoList.size() > 0){
			
			for(SingleMemberAnnotation tmpAnnotation : annoList){
				if("TC".equals(tmpAnnotation.resolveAnnotationBinding().getName())){
					
					rewriter.getListRewrite(td, td.getModifiersProperty()).remove(tmpAnnotation, null);
					break;
				}
			}
		}
		
		String updatedUnit = "";
		TextEdit edits = null;
		try {
			updatedUnit = ((ICompilationUnit)comUnit.getJavaElement()).getSource();
			System.out.println("Updated unit String Before =====> " + updatedUnit);
		} catch (JavaModelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc = new Document(updatedUnit);
		edits = rewriter.rewriteAST(doc, null);
		try {
			edits.apply(doc);
			System.out.println("Updated unit String After =====> " + doc.get());
		} catch (MalformedTreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return doc.get();
	}

}