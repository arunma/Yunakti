package sg.edu.nus.iss.yunakti.ui.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class YunaktiMessageDialog extends MessageDialog{

	public YunaktiMessageDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		// TODO Auto-generated constructor stub
		int result = this.open();
		System.out.println(result);

		// A few standard message dialog
		
		//this.openInformation(parentShell, dialogTitle, dialogMessage);

	}

}