package sg.edu.nus.iss.yunakti.ui.dialog;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;

public class HelperDialogTest extends TestCase {
	MyHelperDialog helperDialog;
	List<YClass> lstHelperClass;

	protected void setUp() throws Exception {
		super.setUp();
		lstHelperClass = new ArrayList<YClass>();
		YClass cls1 = new YClass();
		cls1.setFullyQualifiedName("Helper1.class");
		YClass cls2 = new YClass();
		cls2.setFullyQualifiedName("Helper2.class");
		YClass cls3 = new YClass();
		cls3.setFullyQualifiedName("Helper3.class");
		YClass cls4 = new YClass();
		cls4.setFullyQualifiedName("Helper4.class");
		YClass cls5 = new YClass();
		cls5.setFullyQualifiedName("Helper5.class");
		lstHelperClass.add(cls1);
		lstHelperClass.add(cls2);
		lstHelperClass.add(cls3);
		lstHelperClass.add(cls4);
		lstHelperClass.add(cls5);
		openDialog();
	}

	private void openDialog() {

		try {
			if (helperDialog != null) {
				helperDialog.close();
			}
			helperDialog = new MyHelperDialog(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell());
			
			helperDialog.setLstYClass(lstHelperClass);
			//helperDialog.setBlockOnOpen(false);
			//helperDialog.open();
			
		} catch (Exception ex) {
			System.out.println("helper ex" + ex.getMessage());

		}
	}

	@Test
	public void testHelperDialogTitle() throws Exception {
		assertEquals("Helper Classes", helperDialog.getShell().getText());
	}

	@Test
	public void testHelperDialogEmptyList() {
		lstHelperClass = null;
		openDialog();
	}

	class MyHelperDialog extends HelperDialog {
		public MyHelperDialog(Shell parent) {
			super(parent);
		}

	}
}
