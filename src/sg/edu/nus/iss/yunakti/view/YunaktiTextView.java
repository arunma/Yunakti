package sg.edu.nus.iss.yunakti.view;
import java.util.ArrayList;
import java.util.List;


import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.engine.util.YModelListParser;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class YunaktiTextView extends ViewPart implements  ISelectionListener
{
//private Label label;
	public static final String ID = "Yunakti.view1";
	public static final String NAME = "YunaktiTextview";
	EngineCore engineCore = new EngineCore();
	YModelListParser lstPareser=new YModelListParser();
private ListViewer viewer;
private List<YClass> lstYClass;
public YunaktiTextView() {
	super();
	lstYClass=new ArrayList<YClass>();
}
public void setFocus() {
	//label.setFocus();
	
}
/**
 * @see IViewPart.init(IViewSite)
 */
public void init(IViewSite site) throws PartInitException {
	super.init(site);
}
public void createPartControl(Composite parent) {
	/*label = new Label(parent, 0);
	label.setText("Hello World");*/
	
	YClass cls1 = new YClass();
	cls1.setFullyQualifiedName("Helper1.class");
	cls1.setyClassType(YTYPE.TEST_HELPER);
	YClass cls2 = new YClass();
	cls2.setFullyQualifiedName("Helper2.class");
	cls2.setyClassType(YTYPE.TEST_HELPER);
	lstYClass.add(cls1);
	lstYClass.add(cls2);
	Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new GridLayout(1, false));

    // Add a checkbox to toggle filter
    Button print = new Button(composite, SWT.PUSH);
    print.setText("Print");
    viewer=new ListViewer(composite,SWT.PRINT_SCREEN);
    getSite().getPage().addSelectionListener(this);
    setTextList(lstYClass);
    // prime the selection
    selectionChanged(null, getSite().getPage().getSelection());

	

parent.pack();

}
public List<YClass> getLstYClass() {
	return lstYClass;
}
public void setLstYClass(List<YClass> lstYClass) {
	this.lstYClass = lstYClass;
}
public void setTextList(List<YClass> lstYClass)
{
	 
	//viewer.setInput(lstYClass);
	System.out.println("lstYClass"+lstYClass);
	if(lstYClass!=null && !lstYClass.isEmpty())
	{
		
	viewer.setContentProvider(new ItemContentProvider());
	viewer.setLabelProvider(new ItemLabelProvider());
//	viewer.setInput(lstYClass);
	//viewer.setInput(getViewSite());
	}
}

/**
 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
 **/
public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	if (part != null &&
            selection instanceof IStructuredSelection) {
            
		System.out.println("selector invoked"+selection);
		
		
		
		List<YModel> yModels= 	engineCore.populateModel((IStructuredSelection)selection);
		System.out.println("yModels"+yModels.size());
		if(yModels != null && !yModels.isEmpty())
		{
		
		//viewer.setInput(getParentModel(yModeyModels.ls).toArray());
			viewer.setInput(lstPareser.parseListYModelToString(yModels));
		}
		
		
		
		
            }
	
	
}


}
class ItemLabelProvider implements ILabelProvider {
	  public Image getImage(Object arg0) {
	    return null;
	  }
	  public String getText(Object arg0) {
		  System.out.println("gettext"+((YClass) arg0).getFullyQualifiedName());
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








