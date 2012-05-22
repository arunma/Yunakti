package sg.edu.nus.iss.yunakti.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import sg.edu.nus.iss.yunakti.model.YClass;

public class YunaktiTextView extends ViewPart implements ISelectionListener
{
//private Label label;
private ListViewer viewer;
public YunaktiTextView() {
	super();
}
public void setFocus() {
	//label.setFocus();
}
public void createPartControl(Composite parent) {
	/*label = new Label(parent, 0);
	label.setText("Hello World");*/
	viewer=new ListViewer(parent);
	
	List<YClass> lstYClass=new ArrayList<YClass>();
	YClass cls1=new YClass();
	cls1.setFullyQualifiedName("Helper1.class");
	YClass cls2=new YClass();
	cls2.setFullyQualifiedName("Helper2.class");
	lstYClass.add(cls1);
	lstYClass.add(cls2);
	
	viewer.setContentProvider(new ItemContentProvider());
	viewer.setLabelProvider(new ItemLabelProvider());
	setTextList(lstYClass);
	getViewSite().getPage().addSelectionListener(this);
}
public void setTextList(List<YClass> lstYClass)
{
	 
	viewer.setInput(lstYClass);
}

/**
 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
 */
@Override
public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	if (selection instanceof IStructuredSelection) {
		Object first = ((IStructuredSelection)selection).getFirstElement();
		//label.setText(first.toString());
		
	}
}
}
class ItemLabelProvider implements ILabelProvider {
	  public Image getImage(Object arg0) {
	    return null;
	  }
	  public String getText(Object arg0) {
	    return ((YClass) arg0).getFullyQualifiedName();
	  }
	  public void addListener(ILabelProviderListener arg0) {
	  }
	  public void dispose() {
	  }
	  public boolean isLabelProperty(Object arg0, String arg1) {
	    return false;
	  }
	  public void removeListener(ILabelProviderListener arg0) {
	  }
	}

	class ItemContentProvider implements IStructuredContentProvider {
	  public Object[] getElements(Object arg0) {
	    List<YClass> list = (List<YClass>) arg0;
		return list.toArray();
	  }
	  public void dispose() {
	  }
	  public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
	  }
	}




