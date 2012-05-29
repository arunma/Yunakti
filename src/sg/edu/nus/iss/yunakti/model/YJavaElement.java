package sg.edu.nus.iss.yunakti.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;


@SuppressWarnings("serial")
public class YJavaElement implements Serializable{

	public YJavaElement() {
	}
	public YJavaElement(String fullyQualifiedName) {
		this.fullyQualifiedName=fullyQualifiedName;
	}
	
	
	
	protected String fullyQualifiedName; //will be int, char etc in case of primitives of FQ names in case of objects
	private String path;

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public void setFullyQualifiedName(String fullyQualifiedName) {
		this.fullyQualifiedName = fullyQualifiedName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
