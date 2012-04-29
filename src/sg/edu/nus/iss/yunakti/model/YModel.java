package sg.edu.nus.iss.yunakti.model;

import java.util.Collection;

public class YModel {
	
	private YClass classUnderTest;
	private Collection<YClass> testCases;

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
	
}
