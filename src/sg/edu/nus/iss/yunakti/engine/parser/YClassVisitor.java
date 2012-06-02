package sg.edu.nus.iss.yunakti.engine.parser;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class YClassVisitor extends ASTVisitor{

	private YClass yClass;
	
	public YClassVisitor(YClass yClass) {
		this.yClass=yClass;
	}
	
	@Override
	public boolean visit(TypeDeclaration node) {
		
		System.out.println("Type declaration node : "+node.resolveBinding().getQualifiedName());
		yClass=new YClass(node.resolveBinding().getQualifiedName());
		yClass.setyClassType(YTYPE.TEST_HELPER);
		return super.visit(node);
		
	}

	public YClass getyClass() {
		return yClass;
	}
	
	@Override
	public boolean visit(SimpleName simpleName){
		
		System.out.println("Simple name : "+simpleName);
		return super.visit(simpleName); 
	}
}
