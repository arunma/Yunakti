package sg.edu.nus.iss.yunakti.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@SuppressWarnings("serial")
public class YModel implements Serializable{
	
	private YClass classUnderTest;
	private List<YClass> testCases=new ArrayList<YClass>();
	
	public List<YClass> getTestCases() {
		return testCases;
	}


	public YClass getClassUnderTest() {
		return classUnderTest;
	}

	public void setClassUnderTest(YClass classUnderTest) {
		this.classUnderTest = classUnderTest;
	}

	public void addTestCase(YClass testCase){
		this.testCases.add(testCase);
	}
	
	public void addAllTestCase(List<YClass> testCases){
		this.testCases.addAll(testCases);
	}
	
	public void removeTestCase(YClass testCase){
		this.testCases.remove(testCase);
		if(testCase.isDeleteFlag()) {
			testCase.setDeleteFlag(true);
			this.testCases.add(testCase);
		}
		
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
