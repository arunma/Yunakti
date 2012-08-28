package sg.edu.nus.iss.yunakti.engine.parser;

import static sg.edu.nus.iss.yunakti.engine.util.YConstants.ANNOTATION_PROPERTY_CLASS_UNDER_TEST;
import static sg.edu.nus.iss.yunakti.engine.util.YConstants.TEST_CASE_ANNOTATION;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class YModelVisitor extends ASTVisitor implements YModelSource{

	private static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	ConsoleStreamUtil streamUtil=ConsoleStreamUtil.getInstance();
	
	private YModel model;
	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
	private List<NormalAnnotation> annotations = new ArrayList<NormalAnnotation>();
	private boolean testCaseConstructed=false;
	private String currentClassName;
	
	private ICompilationUnit testCaseCompilationUnit;

	private List<String> allClassNames;

	public YModelVisitor(YModel model, ICompilationUnit testCaseElementCompilationUnit, List<String> allClassNames) {
		this.model=model;
		this.testCaseCompilationUnit=testCaseElementCompilationUnit;
		this.allClassNames=allClassNames;
	}
	

	@Override
	public boolean visit(MethodDeclaration node) {
		methods.add(node);
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(FieldDeclaration node) {
		
		
		//node.getType().resolveBinding().getQualifiedName();
		
		if (node.getType().isSimpleType()){
			
			SimpleType simpleFieldType=(SimpleType) node.getType();
			
			addToModel(simpleFieldType.resolveBinding().getQualifiedName());
			
		}
		
		return super.visit(node); 
		
	}

	private void addToModel(String qualifiedName) {
		
		logger.fine("Current qualifed nme : "+qualifiedName);
		logger.fine("Current class name   : "+currentClassName);
		if (!testCaseConstructed) return;
		else if (StringUtils.equals(qualifiedName, currentClassName)) return;
		YClass member=null;
		
		if (allClassNames.contains(qualifiedName)){
			member=new YClass(qualifiedName);
			member.setyClassType(YTYPE.TEST_HELPER);
			model.getTestCases().get(0).addMember(member);
		}
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		
		annotations.add(node);
		resolveClassUnderTest(node);
		
		return super.visit(node);
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		
		streamUtil.println("Field Class name : "+node.getName().toString());
		YClass testCaseClass=new YClass(node.resolveBinding().getQualifiedName());
		testCaseClass.setyClassType(YTYPE.TEST_CASE);
		testCaseClass.setPath(testCaseCompilationUnit.getResource().getLocation().toOSString());
		model.addTestCase(testCaseClass);
		testCaseConstructed=true;
		currentClassName=testCaseClass.getFullyQualifiedName();
		//model.addTestCase(new YClass(ParserUtils.getClassName(node.getName().toString())));
		return super.visit(node);
		
	}
	
	
	@Override
	public boolean visit(SimpleName simpleName){
		
		streamUtil.print("Simple name : "+simpleName);
		if(simpleName.resolveTypeBinding() != null && simpleName.resolveTypeBinding().isClass()){
			addToModel(simpleName.resolveTypeBinding().getQualifiedName());	
		}
		  
		return super.visit(simpleName); 
	}

	@Override
	public boolean visit(MethodInvocation node) {
		
		streamUtil.print("Method invocation : "+node);
		
		return super.visit(node);
	}
	
	private void resolveClassUnderTest(NormalAnnotation node) {
		if (StringUtils.equals(node.getTypeName().getFullyQualifiedName(),TEST_CASE_ANNOTATION)){
			List<MemberValuePair> members = node.values();
			for (MemberValuePair memberValuePair : members) {
				if (StringUtils.equals(memberValuePair.getName().toString(),ANNOTATION_PROPERTY_CLASS_UNDER_TEST)){
					
					String classUnderTestString="";
					
					classUnderTestString=memberValuePair.getValue().toString();
					if (StringUtils.isNotBlank(classUnderTestString)){
						classUnderTestString=StringUtils.replace(classUnderTestString, "\"", "");
					}
					logger.fine("Class Under Test String : "+classUnderTestString);
					YClass classUnderTest=new YClass(classUnderTestString);
					classUnderTest.setyClassType(YTYPE.CLASS_UNDER_TEST);
					logger.fine("Annotation Root : "+node.getRoot());
					
					model.setClassUnderTest(classUnderTest);
					
				}
			}
			
		}
	}
	
	
	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public List<NormalAnnotation> getAnnotations() {
		return annotations;
	}

	public List<FieldDeclaration> getFields() {
		return fields;
		
	}
	
}
