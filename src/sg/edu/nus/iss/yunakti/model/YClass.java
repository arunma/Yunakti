package sg.edu.nus.iss.yunakti.model;

import java.util.Collection;

public class YClass extends YJavaElement{
	
	private YTYPE yClassType; //test/helper/cut
	@Override
	public String toString() {
		return super.getFullyQualifiedName();
	}

	private Collection<YMODIFIER> accessModifiers;
	private Collection<YMethod> methods;
	private Collection<YClass> members; //common name for helpers/instance primitives in testcases and instance variables in normal classes
	private Collection<YAnnotation> annotations;
	
	public YTYPE getyClassType() {
		return yClassType;
	}

	public void setyClassType(YTYPE yClassType) {
		this.yClassType = yClassType;
	}

	public Collection<YMODIFIER> getAccessModifiers() {
		return accessModifiers;
	}

	public void setAccessModifiers(Collection<YMODIFIER> accessModifiers) {
		this.accessModifiers = accessModifiers;
	}

	public Collection<YMethod> getMethods() {
		return methods;
	}

	public void setMethods(Collection<YMethod> methods) {
		this.methods = methods;
	}

	public Collection<YClass> getMembers() {
		return members;
	}

	public void setMembers(Collection<YClass> members) {
		this.members = members;
	}

	public Collection<YAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Collection<YAnnotation> annotations) {
		this.annotations = annotations;
	}

	
}
