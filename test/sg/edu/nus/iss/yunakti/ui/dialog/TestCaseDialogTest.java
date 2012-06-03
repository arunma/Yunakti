package sg.edu.nus.iss.yunakti.ui.dialog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class TestCaseDialogTest {
	TestCaseDialog dialog;
	YModel model = new YModel();
	Set<YClass> allTestClasses;

	@Before
	public void setUp() throws Exception {
//		YTestCaseCollection collection = new YTestCaseCollection();
		YClass classUnderTest = new YClass("com.subu.ClassUnderTest");
		
	    this.model.setClassUnderTest(classUnderTest);
	    List<YClass> testClasses = new ArrayList<YClass>();
	    
		YClass testClass1 = new YClass("com.subu.testClass1");
		YClass testClass2 = new YClass("com.subu.testClass2");
		YClass testClass3 = new YClass("com.subu.testClass3");
		testClasses.add(testClass1);
		testClasses.add(testClass2);
		testClasses.add(testClass3);
		this.model.addAllTestCase(testClasses);	
		
		allTestClasses = new HashSet<YClass>();
		YClass testClass4 = new YClass("com.subu.testClass1");
		YClass testClass5 = new YClass("com.subu.testClass2");
		YClass testClass6 = new YClass("com.subu.testClass3");
		allTestClasses.add(testClass1);
		allTestClasses.add(testClass2);
		allTestClasses.add(testClass3);
		allTestClasses.add(testClass4);
		allTestClasses.add(testClass5);
		allTestClasses.add(testClass6);
		
		openDialog();
		
		
//		model.
		
	}
	

	private void openDialog(){
//		try {
//		dialog = new TestCaseDialog(PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getShell(),  this.model, allTestClasses);
//		dialog.create();
//	    dialog.open();
//		}catch(Exception ex){
//			System.out.println("Error occured while opening the TestCase Dialog");
//		}
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testTestCaseDialogTitle() throws Exception {
		assertEquals("Select Test Classes", dialog.getShell().getText());
	}
	
	@Test
	public void testTestCaseDialogNUll() throws Exception {
		this.model = null;
		openDialog();
	}

}
