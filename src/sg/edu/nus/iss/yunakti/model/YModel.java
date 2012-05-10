package sg.edu.nus.iss.yunakti.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.ReflectionException;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class YModel {
	
	private YClass classUnderTest;
	private Collection<YClass> testCases=new ArrayList<YClass>();
	
	//Arun - this will be implemented in future for the observable implementation. 
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


	public Collection<YClass> getTestCases() {
		return testCases;
	}

	public void setTestCases(Collection<YClass> testCases) {
		this.testCases = testCases;
	}

	public YClass getClassUnderTest() {
		return classUnderTest;
	}

	public void setClassUnderTest(YClass classUnderTest) {
		this.classUnderTest = classUnderTest;
	}
	
//Arun - enabling firing of event when needed. 
	// pcs.firePropertyChange("whichProperty", oldValue, newValue);

	//Our observables will add on to this
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}


	@Override
	public String toString() {
	
		//return ReflectionToStringBuilder.toString(this);
		StringBuilder builder=new StringBuilder();
		builder.append("Class under test : ").append(classUnderTest.getFullyQualifiedName()).append("\n\t");
		for (YClass testCase: getTestCases()){
			builder.append("Test case : ").append(testCase.getFullyQualifiedName()).append("\n\t");
			for (YClass testCaseMember:testCase.getMembers()){
				builder.append("Members    : \t").append(testCaseMember.getFullyQualifiedName()).append("\n");
			}
		}
		
		return builder.toString();
	}

}
