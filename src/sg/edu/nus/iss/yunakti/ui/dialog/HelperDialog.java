package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * Apr 18, 2012
 * HelperDialog.java
 * @author Alphy Thomas Kanatt
 * 
 **/
public class HelperDialog extends ListDialog {
List<String> listItems;

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

	public HelperDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
		this.setAddCancelButton(false);

		this.setContentProvider(new ArrayContentProvider());

		this.setLabelProvider(new LabelProvider());

		
	}

}
