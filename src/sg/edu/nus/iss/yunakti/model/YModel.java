package sg.edu.nus.iss.yunakti.model;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class YModel {
	
	private YClass classUnderTest;
	private Collection<YClass> testCases=new ArrayList<YClass>();
	
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

	
	
	
	@Override
	public String toString() {
	
		return ReflectionToStringBuilder.toString(this);
		/*StringBuilder builder=new StringBuilder();
		builder.append("Class under test : ").append(classUnderTest.getFullyQualifiedName()).append("\n\t");
		for (YClass testCase: getTestCases()){
			builder.append("Test case : ").append(testCase.getFullyQualifiedName()).append("\n\t");
			for (YClass testCaseMember:testCase.getMembers()){
				builder.append("Members    : \t").append(testCaseMember.getFullyQualifiedName()).append("\n");
			}
		}
		
		return builder.toString();*/
	}

}
