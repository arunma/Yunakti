package sg.edu.nus.iss.yunakti.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.ui.dialog.TestCaseDialog;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class R2Action implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructo
	 */
	public R2Action() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		;
		YModel model = new YModel();
		YClass classUnderTest = new YClass("com.subu.ClassUnderTest");
		
		
	    model.setClassUnderTest(classUnderTest);
	    List<YClass> testClasses = new ArrayList<YClass>();
	    
		YClass testClass1 = new YClass("com.subu.testClass1");
		testClass1.setyClassType(YTYPE.TEST_CASE);
		YClass testClass2 = new YClass("com.subu.testClass2");
		testClass2.setyClassType(YTYPE.TEST_CASE);
		YClass testClass3 = new YClass("com.subu.testClass3");
		testClass3.setyClassType(YTYPE.TEST_CASE);
		testClasses.add(testClass1);
		testClasses.add(testClass2);
		testClasses.add(testClass3);
		model.addAllTestCase(testClasses);
		
		YClass testClass5 = new YClass("com.subu.testClass1");
		testClass5.setyClassType(YTYPE.TEST_CASE);
		YClass testClass6 = new YClass("com.subu.testClass2");
		testClass6.setyClassType(YTYPE.TEST_CASE);
		YClass testClass7 = new YClass("com.subu.testClass3");
		testClass7.setyClassType(YTYPE.TEST_CASE);
		YClass testClass8 = new YClass("com.subu.testClass4");
		testClass8.setyClassType(YTYPE.TEST_CASE);
		YClass testClass9 = new YClass("com.subu.testClass5");
		testClass9.setyClassType(YTYPE.TEST_CASE);
		YClass testClass10 = new YClass("com.subu.testClass6");
		testClass10.setyClassType(YTYPE.TEST_CASE);
	
		List<YClass> allTestClasses = new ArrayList<YClass>();
		allTestClasses.add(testClass5);
		allTestClasses.add(testClass6);
		allTestClasses.add(testClass7);
		allTestClasses.add(testClass8);
		allTestClasses.add(testClass9);
		allTestClasses.add(testClass10);
		
		System.out.println(model);
		
		
		TestCaseDialog dialog = new TestCaseDialog(this.window.getShell(), model, allTestClasses);

		  dialog.create();
		if (dialog.open() == Window.OK) {

		}
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {

	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
