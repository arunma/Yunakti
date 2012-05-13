package sg.edu.nus.iss.yunakti.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import sg.edu.nus.iss.yunakti.ui.dialog.TestCaseDialog;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
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
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
	    TestCaseDialog dialog = new TestCaseDialog(this.window.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {

		}
		
//		 Shell shell = new Shell();
//		 List<Person> persons = new ArrayList<Person>();
//		   Person p1 = new Person("Subu");
//		   Person p2 = new Person("Arun");
//		   Person p3 = new Person("Kovalan");
//		   Person p4 = new Person("Alphy");
//		   Person p5 = new Person("Suriya");
//		   persons.add(p1);
//		   persons.add(p2);
//		   persons.add(p3);
//		   persons.add(p4);
//		   persons.add(p5);
//		   
//		   YTestCaseCollection collection = new YTestCaseCollection();   
//		   
//		   List<YClass> testCases = collection.getTestCases();
//		   FilteredTCSelectionDialog dialog = new FilteredTCSelectionDialog(this.window.getShell(), testCases);
//		   dialog.setInitialPattern("?");
//		   dialog.open();		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		System.out.println("kk");
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}


