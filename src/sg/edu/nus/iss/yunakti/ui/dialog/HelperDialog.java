package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

import sg.edu.nus.iss.yunakti.model.YClass;

/**
 * Apr 18, 2012
 * HelperDialog.java
 * @author Alphy Thomas Kanatt
 * 
 **/
public class HelperDialog extends ListDialog {
List<String> listItems;
List<YClass> lstYClass;
	public List<String> getListItems() {
	return listItems;
}

public void setListItems(List<String> listItems) {
	this.listItems = listItems;
	this.setInput(listItems.toArray());

	this.setInitialSelections(listItems.toArray());

	this.setTitle("Helper Classes");

	this.open();
}



	public List<YClass> getLstYClass() {
	return lstYClass;
}

public void setLstYClass(List<YClass> lstYClass) {
	if(lstYClass !=null && !lstYClass.isEmpty())
	{
	this.lstYClass = lstYClass;
	this.setInput(lstYClass.toArray());

	this.setInitialSelections(lstYClass.toArray());
	this.setTitle("Helper Classes");

	this.open();
	}
	else
	{
		
		YunaktiMessageDialog yunaktimsgDlg=new YunaktiMessageDialog(this.getParentShell(),"Info",null,"No class found",MessageDialog.INFORMATION, new String[] { "OK",
			 },0);
	}
}

	public HelperDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
		
		this.setAddCancelButton(false);

		this.setContentProvider(new ArrayContentProvider());

		this.setLabelProvider(new LabelProvider());

		
	}

}
