package sg.edu.nus.iss.yunakti.model;


public class YJavaElement {

	private String fullyQualifiedName; //will be int, char etc in case of primitives of FQ names in case of objects

	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	public void setFullyQualifiedName(String fullyQualifiedName) {
		this.fullyQualifiedName = fullyQualifiedName;
	}
}
