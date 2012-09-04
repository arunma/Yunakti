package sg.edu.nus.iss.yunakti.model;

import java.util.ArrayList;
import java.util.List;

public class YMethod {

	private YClass parentClass =null;
	private List<YMODIFIER> modifiers=new ArrayList<YMODIFIER>();
	private YClass returnType;
	private List<YClass> parameters =new ArrayList<YClass>();
	private List<YClass> members=new ArrayList<YClass>();
	private List<YClass> exceptions=new ArrayList<YClass>();
	private List<YAnnotation> annotations=new ArrayList<YAnnotation>();
	private List<YMethod> callees=new ArrayList<YMethod>();
	private String methodName;
	
	private boolean deleteFlag;
	
	public YMethod(YClass parentClass, String methodName) {
		this.parentClass=parentClass;
		this.methodName=methodName;
	}
	
	public YMethod() {
	}

	public YClass getParentClass() {
		return parentClass;
	}
	public void setParentClass(YClass parentClass) {
		this.parentClass = parentClass;
	}
	public List<YMODIFIER> getModifiers() {
		return modifiers;
	}
	public void setModifiers(List<YMODIFIER> modifiers) {
		this.modifiers = modifiers;
	}
	public YClass getReturnType() {
		return returnType;
	}
	public void setReturnType(YClass returnType) {
		this.returnType = returnType;
	}
	public List<YClass> getParameters() {
		return parameters;
	}
	public void setParameters(List<YClass> parameters) {
		this.parameters = parameters;
	}
	public List<YClass> getMembers() {
		return members;
	}
	public void setMembers(List<YClass> members) {
		this.members = members;
	}
	public List<YClass> getExceptions() {
		return exceptions;
	}
	public void setExceptions(List<YClass> exceptions) {
		this.exceptions = exceptions;
	}
	public List<YAnnotation> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<YAnnotation> annotations) {
		this.annotations = annotations;
	}
	public List<YMethod> getCallees() {
		return callees;
	}
	public void setCallees(List<YMethod> callees) {
		this.callees = callees;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Override
	public String toString() {
		return "Method name : " +getMethodName() + " : Method class : "+ getParentClass().getFullyQualifiedName();
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
