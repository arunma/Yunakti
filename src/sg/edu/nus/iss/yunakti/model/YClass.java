package sg.edu.nus.iss.yunakti.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@SuppressWarnings("serial")
public class YClass extends YJavaElement{
	
	private YTYPE yClassType; //test/helper/cut
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	private List<YMODIFIER> accessModifiers;
	private List<YMethod> methods=new ArrayList<YMethod>();
	private List<YClass> members=new ArrayList<YClass>(); //common name for helpers/instance primitives in testcases and instance variables in normal classes
	private List<YAnnotation> annotations=new ArrayList<YAnnotation>();
	
	public YClass() {
	}
	
	public YClass(String fullyQualifiedName) {
		super(fullyQualifiedName);
	}

	
	public YTYPE getyClassType() {
		return yClassType;
	}

	public void setyClassType(YTYPE yClassType) {
		this.yClassType = yClassType;
	}

	public List<YMODIFIER> getAccessModifiers() {
		return accessModifiers;
	}

	public void setAccessModifiers(List<YMODIFIER> accessModifiers) {
		this.accessModifiers = accessModifiers;
	}

	public Collection<YMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<YMethod> methods) {
		this.methods = methods;
	}

	public Collection<YClass> getMembers() {
		return members;
	}

	public void setMembers(List<YClass> members) {
		this.members = members;
	}
	
	public void addMember(YClass member){
		this.members.add(member);
	}

	public Collection<YAnnotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<YAnnotation> annotations) {
		this.annotations = annotations;
	}
	
}
