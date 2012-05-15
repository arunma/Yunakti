package sg.edu.nus.iss.yunakti.model;

import java.util.Collection;

public class YAnnotation extends YJavaElement{
	
	private String name;
	private Collection<YAnnotationProperty> properties;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Collection<YAnnotationProperty> getProperties() {
		return properties;
	}
	public void setProperties(Collection<YAnnotationProperty> properties) {
		this.properties = properties;
	}

}
