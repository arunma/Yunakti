package sg.edu.nus.iss.yunakti.ui.dialog;

import static org.junit.Assert.*;

import java.util.List;

import javax.management.openmbean.OpenDataException;

import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialogTest.MyHelperDialog;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.YTestCaseCollection;

public class TestCaseDialogTest {
	TestCaseDialog dialog;
	List<YClass> testClasses;

	@Before
	public void setUp() throws Exception {
		YTestCaseCollection collection = new YTestCaseCollection();
		testClasses = collection.getTestCases();		
		openDialog();
	}
	
	
	private void openDialog(){
		try {
		dialog = new TestCaseDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell());
		dialog.create();
		dialog.setTableData(testClasses);
		dialog.open();
		}catch(Exception ex){
			System.out.println("Error occured while opening the TestCase Dialog");
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testTestCaseDialogTitle() throws Exception {
		assertEquals("Select Test Classes", dialog.getShell().getText());
	}

}
