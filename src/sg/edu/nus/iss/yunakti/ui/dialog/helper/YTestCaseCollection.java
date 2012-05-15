package sg.edu.nus.iss.yunakti.ui.dialog.helper;

import java.util.ArrayList;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class YTestCaseCollection {
	private ArrayList<YClass> testCases;
	
	public YTestCaseCollection() {
		testCases = new ArrayList<YClass>();
		YClass class1 = new YClass();
		class1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.class1");
		class1.setyClassType(YTYPE.TEST_CASE);
		testCases.add(class1);
		
		YClass class2 = new YClass();
		class2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.class2");
		class2.setyClassType(YTYPE.TEST_CASE);
		testCases.add(class2);
		
		YClass class3 = new YClass();
		class3.setFullyQualifiedName("sg.edu.nus.iss.yunakti.class3");
		class3.setyClassType(YTYPE.TEST_CASE);
		testCases.add(class3);
		
	}
	

	public void addTestCase(YClass testCase){
		testCases.add(testCase);
	}
	
	public ArrayList<YClass> getTestCases(){
		return testCases;
	}
	

	@Override
	public String toString() {
		return "YTestCaseCollection [testCases=" + testCases + "]";
	}
}