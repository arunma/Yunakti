package sg.edu.nus.iss.yunakti.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructo
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(
			window.getShell(),
			"Yunakti",
			"Hello, Eclipse world");
		HelperDialog helperDialog=new HelperDialog(window.getShell());
		
		/*List<String> listItems=new ArrayList<String>();
		listItems.add("Helper1");
		listItems.add("Helper2");
		helperDialog.setListItems(listItems);*/
		
		List<YClass> lstYClass=new ArrayList<YClass>();
		/*YClass cls1=new YClass();
		cls1.setFullyQualifiedName("Helper1.class");
		YClass cls2=new YClass();
		cls2.setFullyQualifiedName("Helper2.class");
		lstYClass.add(cls1);
		lstYClass.add(cls2);*/
		helperDialog.setLstYClass(lstYClass);
		
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	
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
