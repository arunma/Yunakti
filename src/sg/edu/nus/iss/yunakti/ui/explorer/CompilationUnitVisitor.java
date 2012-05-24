package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

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
	private List<SimpleType> simpleTypes = new ArrayList<SimpleType>();
	
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
	
	public boolean visit(SimpleType node){
		simpleTypes.add(node);
		return super.visit(node); 
	}

	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public List<FieldDeclaration> getFields() {
		return fields;
	}
	
	public List<SimpleType> getTypes() {
		return simpleTypes;
	}
	
	public List<SimpleName> getNames() {
		return simpleNames;
	}
	
	
	public Set<String> getInvokedObjects(){
		
		Set<String> objectList = new TreeSet<String>();
		
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
				/*
				if("testString".equals(tmpMethod.getName().toString())){
					
					System.out.println("Found method");
					AST ast = tmpMethod.getAST();
					ASTRewrite rewriter = ASTRewrite.create(ast);
					SingleMemberAnnotation testClassAnnotation = ast.newSingleMemberAnnotation();
					testClassAnnotation.setTypeName(ast.newSimpleName("TC"));
					testClassAnnotation.setValue(ast.newSimpleName("Class"));
					System.out.println("Modifiers ====> " + tmpMethod.modifiers().size() + " / " + tmpMethod.modifiers().get(0));
					tmpMethod.modifiers().add(0,testClassAnnotation);
					System.out.println("Modifiers after ====> " + tmpMethod.modifiers().size() + " / " + tmpMethod.modifiers().get(0));
					
					try {
						rewriter.rewriteAST();
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("method annotated");
				}
				*/
				//tmpMethod.
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
	            	//System.out.println("looping method body ======> " + stmt.toString() + " Class type ======> " + stmt.getClass());
	            	//objectList = resolveStatement(stmt, objectList);
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
		
		if(annoTypeList != null && annoTypeList.size() > 0){
			
			for(AnnotationTypeMemberDeclaration tmpAnno : annoTypeList){
				System.out.println("Annotation Name ===> " + tmpAnno.getName());
				//System.out.println("Annotation Value ===> " + tmpAnno.resolveAnnotationBinding().getDeclaredMemberValuePairs()[0].getValue());
			}
		}
		else{
			
			System.out.println("Annotation is empty!!!");
		}
        
        
        
        List<SimpleName> nameList = getNames();    
        List<SimpleType> typeList = getTypes(); 
        
        if(nameList != null && nameList.size() > 0){
        	for(SimpleName tmpName : nameList){
        		
        		//System.out.println("Printing simple name =====> " + tmpName.getFullyQualifiedName());
        		if(tmpName.resolveTypeBinding() != null && tmpName.resolveTypeBinding().isClass()){
        			//System.out.println("Printing simple name =====> " + tmpName.getFullyQualifiedName() + " / " + tmpName.resolveTypeBinding().getQualifiedName());
        			objectList.add(tmpName.resolveTypeBinding().getQualifiedName());
        		}
        	}
        }
        
        if(typeList != null && typeList.size() > 0){
        	
        	for(SimpleType tmpType : typeList){
        		
        		//System.out.println("Printing simple type =====> " + tmpType.resolveBinding().getQualifiedName());
        	}
        }
		
		
		return objectList;
	}
	
	private Set<String> resolveStatement(Statement stmt, Set<String> objectList){
		
		if(stmt instanceof VariableDeclarationStatement)  
        {  
            VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;
            //System.out.println(var.toString() + "===> Type of variable:"+var.getType().resolveBinding().getQualifiedName());
            objectList.add(var.getType().resolveBinding().getQualifiedName());
            
        }
    	if(stmt instanceof ExpressionStatement)
    	{
    		ExpressionStatement expStat = (ExpressionStatement) stmt;
    		Expression express = expStat.getExpression();  
    		
    		if(express instanceof Assignment)  
            {  
                Assignment assign=(Assignment)express;  
                System.out.println("LHS:"+assign.getLeftHandSide()+"; ");  
                System.out.println("Op:"+assign.getOperator()+"; ");  
                System.out.println("RHS:"+assign.getRightHandSide());  
                  
            }  
    		else if(express instanceof MethodInvocation)  
            {  
                MethodInvocation mi=(MethodInvocation) express;  
                System.out.println("invocation name:"+mi.getName());  
                System.out.println("invocation exp:"+mi.getExpression());  
                System.out.println("invocation arg:"+mi.arguments());  
                  
            }
    	}
		
		return objectList;
	}
}
