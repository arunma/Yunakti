package sg.edu.nus.iss.yunakti.model;

import java.util.Collection;

public class YAnnotationProperty {

	private String name;
	private boolean hasMultipleValues;
	private Collection<Object> values;
	private Class valueType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isHasMultipleValues() {
		return hasMultipleValues;
	}
	public void setHasMultipleValues(boolean hasMultipleValues) {
		this.hasMultipleValues = hasMultipleValues;
	}
	public Collection<Object> getValues() {
		return values;
	}
	public void setValues(Collection<Object> values) {
		this.values = values;
	}
	public Class getValueType() {
		return valueType;
	}
	public void setValueType(Class valueType) {
		this.valueType = valueType;
	}
}
