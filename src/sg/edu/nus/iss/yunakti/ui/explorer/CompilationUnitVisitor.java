package sg.edu.nus.iss.yunakti.ui.explorer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class CompilationUnitVisitor extends ASTVisitor{

	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();

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

	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public List<FieldDeclaration> getFields() {
		return fields;
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
				
				
				/*
				List paramList = tmpMethod.parameters();
				Iterator iter = paramList.iterator();
				while(iter.hasNext()){
					Object paramIter = (Object)iter.next();
					System.out.println("Param Type ====> " + paramIter);
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
	            	if(stmt instanceof VariableDeclarationStatement)  
	                {  
	                    VariableDeclarationStatement var=(VariableDeclarationStatement) stmt;
	                    //System.out.println("Type of variable:"+var.getType().resolveBinding().getQualifiedName());
	                    objectList.add(var.getType().resolveBinding().getQualifiedName());
	                }  
	            }
			}
		}
		
		
		return objectList;
	}
}