package sg.edu.nus.iss.yunakti.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class YunaktiTextView extends ViewPart implements ISelectionListener
{
private Label label;
public YunaktiTextView() {
	super();
}
public void setFocus() {
	label.setFocus();
}
public void createPartControl(Composite parent) {
	label = new Label(parent, 0);
	label.setText("Hello World");
	getViewSite().getPage().addSelectionListener(this);
}

/**
 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
 */
@Override
public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	if (selection instanceof IStructuredSelection) {
		Object first = ((IStructuredSelection)selection).getFirstElement();
		label.setText(first.toString());
		
	}
}


}

