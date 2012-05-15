package sg.edu.nus.iss.yunakti.ui.dialog.helper;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import sg.edu.nus.iss.yunakti.model.YJavaElement;

public class TestCaseModel {
	private String testCaseName;
	private String packageName;
	private String testCaseDescription;
	
	private ArrayList<YJavaElement> testCases;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public TestCaseModel() {
	}

	public TestCaseModel(String testCaseName, String packageName,
			String testCaseDescription) {
		super();
		this.testCaseName = testCaseName;
		this.testCaseDescription = testCaseDescription;
		this.packageName = packageName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		propertyChangeSupport.firePropertyChange("testCaseName",
				this.testCaseName, this.testCaseName = testCaseName);
	}

	public String getTestCaseDescription() {
		return testCaseDescription;
	}

	public void setTestCaseDescription(String testCaseDescription) {
		propertyChangeSupport.firePropertyChange("testCaseDescription",
				this.testCaseDescription,
				this.testCaseDescription = testCaseDescription);
	}

	@Override
	public String toString() {
		return testCaseName + " " + packageName;
	}

	public static TestCaseModel[] getArray() {
		return new TestCaseModel[] {
				new TestCaseModel("TestCase1", "com.iss.nus.edu.sg.TestCase1",
						"Description of TestCaseModel1"),
				new TestCaseModel("TestCase2",
						"com.iss.nus.edu.india.TestCase1",
						"Description of TestCaseModel2"),
				new TestCaseModel("TestCase3", "com.iss.nus.edu.us.TestCase1",
						"Description of TestCaseModel3"),
				new TestCaseModel("TestCase4", "com.iss.nus.edu.hk.TestCase1",
						"Description of TestCaseModel4"),
				new TestCaseModel("TestCase5",
						"com.iss.nus.edu.google.TestCase1",
						"Description of TestCaseModel5")

		};
	}
	
	public static ArrayList<TestCaseModel> getList(){
		TestCaseModel t1 = new TestCaseModel("TestCase1", "com.iss.nus.edu.sg.TestCase1",
				"Description of TestCaseModel1");
		TestCaseModel t2 = new TestCaseModel("TestCase2", "com.iss.nus.edu.india.TestCase1",
				"Description of TestCaseModel2");
		TestCaseModel t3 = new TestCaseModel("TestCase3", "com.iss.nus.edu.us.TestCase1",
				"Description of TestCaseModel3");
		
		
		ArrayList<TestCaseModel> testCasesList = new ArrayList<TestCaseModel>();
		testCasesList.add(t1);
		testCasesList.add(t2);
		testCasesList.add(t3);
		
		return testCasesList;	
		
	}

}