package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Test;

import sg.edu.nus.iss.yunakti.model.YMethod;
import sg.edu.nus.iss.yunakti.model.YModel;

public class MethodDialogTest extends TestCase{
	MethodDialog dialog;
	YModel model = new YModel();
	ArrayList<YMethod> testMethods;

	public void setUp() throws Exception {
		super.setUp();
	    this.testMethods = new ArrayList<YMethod>();
	    
		YMethod method1 = new YMethod();
		method1.setMethodName("method1");
		this.testMethods.add(method1);
		
		YMethod method2 = new YMethod();
		method2.setMethodName("method2");
		this.testMethods.add(method2);
		
		YMethod method3 = new YMethod();
		method3.setMethodName("method3");
		this.testMethods.add(method3);
		
		YMethod method4 = new YMethod();
		method4.setMethodName("method4");
		this.testMethods.add(method4);
		
		openDialog();
	}
	

	private void openDialog(){
		try {
			this.testMethods = new ArrayList<YMethod>();
		    
			YMethod method1 = new YMethod();
			method1.setMethodName("method1");
			this.testMethods.add(method1);
			
			YMethod method2 = new YMethod();
			method2.setMethodName("method2");
			this.testMethods.add(method2);
			
			YMethod method3 = new YMethod();
			method3.setMethodName("method3");
			this.testMethods.add(method3);
			
			YMethod method4 = new YMethod();
			method4.setMethodName("method4");
			this.testMethods.add(method4);
			
					
			System.out.println(this.testMethods);
		dialog = new MethodDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(),  this.testMethods);
		dialog.create();
	    dialog.open();
		}catch(Exception ex){
			System.out.println("Error occured while opening the MehtodDialog");
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testTestCaseDialogTitle() throws Exception {
		openDialog();
		assertEquals("Select Test Methods", dialog.getShell().getText());
	}
	
	@Test
	public void testTestCaseDialogNUll() throws Exception {
		this.testMethods = null;
		openDialog();
	}

}
