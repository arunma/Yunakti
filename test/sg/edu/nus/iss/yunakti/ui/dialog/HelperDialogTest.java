package sg.edu.nus.iss.yunakti.ui.dialog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;

public class HelperDialogTest extends TestCase{
	MyHelperDialog helperDialog;
	List<String> lstHelperClass;
	protected void setUp() throws Exception {
        super.setUp();
        lstHelperClass=new ArrayList<String>();
        lstHelperClass.add("Helper1");
        lstHelperClass.add("Helper2");
        lstHelperClass.add("Helper3");
        lstHelperClass.add("Helper4");
        lstHelperClass.add("Helper5");
        openDialog(lstHelperClass);
    }
	private void openDialog(List<String> lstHelperClass) {
       
		try
		{if (helperDialog != null) {
        	helperDialog.close();
        }
        helperDialog = new MyHelperDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        helperDialog.setListItems(lstHelperClass);
        helperDialog.setBlockOnOpen(false);
        helperDialog.open();
        System.out.println("helper"+helperDialog);
		}
		catch(Exception ex)
		{
			System.out.println("helper ex"+ex.getMessage());
			
		}
    }    
	@Test
	public void testHelperDialogTitle() throws Exception {
        assertEquals("Helper Classes", helperDialog.getShell().getText());
    }

	 class MyHelperDialog extends HelperDialog{
	        public MyHelperDialog(Shell parent) {
	            super(parent);
	        }

	       
	    }
}
