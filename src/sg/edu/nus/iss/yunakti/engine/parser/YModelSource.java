package sg.edu.nus.iss.yunakti.engine.parser;

import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public interface YModelSource {
	
	public List<MethodDeclaration> getMethods();
	
	public List<FieldDeclaration> getFields();
	

}
