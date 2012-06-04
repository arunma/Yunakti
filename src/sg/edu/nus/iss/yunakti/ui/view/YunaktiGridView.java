package sg.edu.nus.iss.yunakti.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.engine.util.YModelListParser;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YParentModel;
import sg.edu.nus.iss.yunakti.model.providers.GridViewContentProvider1;
import sg.edu.nus.iss.yunakti.model.providers.GridViewLabelProvider;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;
import sg.edu.nus.iss.yunakti.ui.dialog.TestCaseDialog;

public class YunaktiGridView extends PageBookView implements  ISelectionListener{
	
	public static final String ID = "sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView";
	private TreeViewer viewer;
	
	EngineCore engineCore = new EngineCore();
	
	private static YunaktiGridView gridView = null;

	
	private static List<YParentModel> packageList = new ArrayList<YParentModel>();
	
	private static  List<YModel> yModels = null;



	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		setUpView(parent);
		
		gridView = this;
		
		getViewSite().getPage().addSelectionListener(this);
		
		try {
			//YunaktiTextView vw=(YunaktiTextView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(YunaktiTextView.ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
		
	}
	
	private void setUpView(final Composite parent){
		
		viewer = new TreeViewer(parent, SWT.BORDER
				| SWT.FULL_SELECTION);
		
		final Tree tree = viewer.getTree();
		
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		
		String[] title = { "Class", "Test Case", "Helper Class" }; //$NON-NLS-1$ //$NON-NLS-2$
		int[] bounds = { 200, 200,200 };

		for (int col = 0; col < 3; col++) {
			TreeViewerColumn column = new TreeViewerColumn(
					this.viewer, SWT.NONE);
			column.getColumn().setText(title[col]);
			column.getColumn().setWidth(bounds[col]);			
		}
		
						
		tree.addListener(SWT.MouseDoubleClick, new Listener() {  
			 
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
				 Shell shell = new Shell();
	              shell.setText("Test Class");

	              // Set layout for shell
	              GridLayout layout = new GridLayout();
	              shell.setLayout(layout);
	      		
	             	              // Open the shell and run until a close event is detected
	              String className = null;
	              
	              TreeItem[] selection = tree.getSelection();
	              for (int i = 0; i < selection.length; i++)
	            	  className = selection[i].getText();
	              System.out.println("DefaultSelection={" + className + "}");
	              
	              
	                       
	              
	              Point p = new Point(event.x, event.y);
	              ViewerCell cell = viewer.getCell(p);
	              
	              if(cell != null)
	              {
	              
	              int columnIndex = cell.getColumnIndex();
	              
	              System.out.println(columnIndex);
	              
	              
	              if(columnIndex == 1){	            	 
	            	  System.out.println(yModels);	            	  
	            	  
	            	 TestCaseDialog caseDialog = new TestCaseDialog(parent.getShell(),getTestCasses(className, columnIndex), engineCore.getAllClassesInWorkspace(), gridView);
						
	  				caseDialog.create();
	  				
	  				caseDialog.open();
	            	  
	            	  
	              }
	              else
	            	  if(columnIndex == 2){
	            		  
	            		  
	            		  HelperDialog dialog = new HelperDialog(parent.getShell());
	    	              
	    	              dialog.setListItems(getHelperClasses(className, columnIndex));
	    				
	            		  
	            	  }
	              
	              }	       
	            
			}
			 }); 
		
	
		viewer.setContentProvider(new GridViewContentProvider1());		
		viewer.setLabelProvider(new GridViewLabelProvider());			
		
		viewer.setInput(packageList.toArray());		
		
		
		getSite().setSelectionProvider(viewer);
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
		
		viewer.expandAll();
		
		
		
				
	}

	@Override
	protected IPage createDefaultPage(PageBook book) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private YModel getTestCasses(String className, int colNum){
		
		YModel returnModel = null;
		
		List<YParentModel> yParentModels =  packageList;
		
		for(YParentModel yParentModel: yParentModels){
			
			for(YModel yModel: yParentModel.getClassList()){
				
				if(className.equals(yModel.getClassUnderTest().getName())){
					
					returnModel =yModel;
					
				}
			}
		}
		
		return returnModel;
	}
	
	
private List<String> getHelperClasses(String className, int colNum){
	
	List<String> helperClasses = new ArrayList<String>();
	
	
	List<YClass> testCases = getTestCasses(className, colNum).getTestCases();
	
	
	for(YClass yClass: testCases){
		
		for(YClass helperClass: yClass.getMembers()){
			
			//helperClasses.add(helperClass.getName());
			helperClasses.add(helperClass.getFullyQualifiedName());
			
		}
		
	}
		
		
		return helperClasses;
	}

@Override
public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	
	if (part != null &&
            selection instanceof IStructuredSelection) {
            
		System.out.println("selector invoked");
		
		
		 if(YModelListParser.findwhetherToPopulateModel(selection))
         {

		
		
		List<YModel> tempYModel = engineCore.populateModel((IStructuredSelection)selection);	
		
		 		
		
		if(tempYModel != null && !tempYModel.isEmpty())
		{
		
			yModels= tempYModel;
			buildPackageList(engineCore.getModelsByPackageName(yModels));
			viewer.setInput(packageList.toArray());
			viewer.expandAll();
		}
		
		else
        {
                viewer.setInput(null);
        }
		
		
         }
		 
		 
				
            }
	
}



public void buildPackageList(Map<String,List<YModel>> packageMap){
	
	
	packageList = new ArrayList<YParentModel>();
	
	YParentModel parentModel = null;
	
	for (Map.Entry<String,List<YModel>> entry : packageMap.entrySet()) {
		
		parentModel = new YParentModel();
		parentModel.setParentName(entry.getKey());
		parentModel.setClassList(entry.getValue());
		
		packageList.add(parentModel);
	}
	
}

public void updateGridView(YModel model){
	
	for(YParentModel parentModel : packageList){
		
		for(YModel yModel: parentModel.getClassList())
		{
			
			if(yModel.getClassUnderTest().getFullyQualifiedName().equals(model.getClassUnderTest().getFullyQualifiedName())){
				
				parentModel.getClassList().remove(yModel);
				parentModel.getClassList().add(model);				
				
				viewer.setInput(packageList.toArray());
				
				viewer.expandAll();
				
				return;
				
			}
			
		}
		
		
	}
	
	
}



}
