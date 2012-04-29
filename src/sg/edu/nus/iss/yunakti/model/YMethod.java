package sg.edu.nus.iss.yunakti.model;

import java.util.Collection;

public class YMethod {

	private Collection<YMODIFIER> modifiers;
	private YClass returnType;
	private Collection<YClass> parameters;
	private Collection<YClass> members;
	private Collection<YClass> exceptions;
	private Collection<YAnnotation> annotations;
	
	public Collection<YMODIFIER> getModifiers() {
		return modifiers;
	}
	public void setModifiers(Collection<YMODIFIER> modifiers) {
		this.modifiers = modifiers;
	}
	public YClass getReturnType() {
		return returnType;
	}
	public void setReturnType(YClass returnType) {
		this.returnType = returnType;
	}
	public Collection<YClass> getParameters() {
		return parameters;
	}
	public void setParameters(Collection<YClass> parameters) {
		this.parameters = parameters;
	}
	public Collection<YClass> getMembers() {
		return members;
	}
	public void setMembers(Collection<YClass> members) {
		this.members = members;
	}
	public Collection<YClass> getExceptions() {
		return exceptions;
	}
	public void setExceptions(Collection<YClass> exceptions) {
		this.exceptions = exceptions;
	}
	public Collection<YAnnotation> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(Collection<YAnnotation> annotations) {
		this.annotations = annotations;
	}
}
