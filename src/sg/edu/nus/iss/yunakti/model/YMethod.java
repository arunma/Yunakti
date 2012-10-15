package sg.edu.nus.iss.yunakti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonInclude(value=JsonInclude.Include.NON_EMPTY) 
@JsonIgnoreProperties(value={ "parentClass" ,"modifiers","returnType","parameters","members","exceptions","annotations","deleteFlag"})
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY) 

public class YMethod implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private YClass parentClass =null;
	private List<YMODIFIER> modifiers=new ArrayList<YMODIFIER>();
	private YClass returnType;
	private List<YClass> parameters =new ArrayList<YClass>();
	private List<YClass> members=new ArrayList<YClass>();
	private List<YClass> exceptions=new ArrayList<YClass>();
	private List<YAnnotation> annotations=new ArrayList<YAnnotation>();
	@JsonProperty("testmethod")
	private List<YMethod> callees=new ArrayList<YMethod>();
	private String methodName;
	
	private boolean deleteFlag;
	
	public YMethod(String methodName) {
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

	public void addCallee(YMethod yMethod){
		this.callees.add(yMethod);
	}
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annotations == null) ? 0 : annotations.hashCode());
		result = prime * result + ((callees == null) ? 0 : callees.hashCode());
		result = prime * result + (deleteFlag ? 1231 : 1237);
		result = prime * result
				+ ((exceptions == null) ? 0 : exceptions.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result
				+ ((modifiers == null) ? 0 : modifiers.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result
				+ ((parentClass == null) ? 0 : parentClass.hashCode());
		result = prime * result
				+ ((returnType == null) ? 0 : returnType.hashCode());
		return result;
	}
	/*Added By Alphy on 11/10/2012 to compare YMethod List */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YMethod other = (YMethod) obj;
		if (annotations == null) {
			if (other.annotations != null)
				return false;
		} else if (!annotations.equals(other.annotations))
			return false;
		if (callees == null) {
			if (other.callees != null)
				return false;
		} else if (!callees.equals(other.callees))
			return false;
		if (deleteFlag != other.deleteFlag)
			return false;
		if (exceptions == null) {
			if (other.exceptions != null)
				return false;
		} else if (!exceptions.equals(other.exceptions))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (modifiers == null) {
			if (other.modifiers != null)
				return false;
		} else if (!modifiers.equals(other.modifiers))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (parentClass == null) {
			if (other.parentClass != null)
				return false;
		} else if (!parentClass.equals(other.parentClass))
			return false;
		if (returnType == null) {
			if (other.returnType != null)
				return false;
		} else if (!returnType.equals(other.returnType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Method name : " +getMethodName();
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
