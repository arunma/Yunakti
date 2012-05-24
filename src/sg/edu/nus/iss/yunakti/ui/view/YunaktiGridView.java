package sg.edu.nus.iss.yunakti.ui.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.ViewPart;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YParentModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.model.providers.GridViewContentProvider1;
import sg.edu.nus.iss.yunakti.model.providers.GridViewLabelProvider;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;
import sg.edu.nus.iss.yunakti.ui.dialog.TestCaseDialog;
import sg.edu.nus.iss.yunakti.view.YunaktiTextView;

public class YunaktiGridView extends PageBookView{
	
	public static final String ID = "sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView";
	private TreeViewer viewer;

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		
		setUpView(parent);
		
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
	            	  
	            	 TestCaseDialog caseDialog = new TestCaseDialog(parent.getShell());
						
	  				caseDialog.create();
	  				
	  				caseDialog.setTableData(getTestCasses(className, columnIndex));
	  				
	  				caseDialog.open();
	            	  
	            	  
	              }
	              else
	            	  if(columnIndex == 2){
	            		  
	            		  
	            		  HelperDialog dialog = new HelperDialog(parent.getShell());
	    	              
	    	              dialog.setLstYClass(getHelperClasses(className, columnIndex));
	    				
	            		  
	            	  }
	              
	              }

	              
	              
	              
	              
	              /*Point pt = new Point(event.x, event.y);
	              TableItem item = tree.get
	              if (item == null)
	               return;
	              for (int i = 0; i < columnCount; i++) {
	               Rectangle rect = item.getBounds(i);
	               if (rect.contains(pt)) {
	                int index = tree.indexOf(item);
	                System.out.println("Item " + index + "-" + i);
	               }
	              }*/
	              
	              
	              
	              
	              
	              


	              
	             			
				/*TestCaseDialog caseDialog = new TestCaseDialog(parent.getShell());
								
				caseDialog.create();
				
				caseDialog.setTableData((List<YClass>)getModel().get(0).getTestCases());
				
				caseDialog.open();*/
	            /*  
	              HelperDialog dialog = new HelperDialog(parent.getShell());
	              
	              dialog.setLstYClass((List<YClass>)((List<YClass>)getModel().get(0).getTestCases()).get(0).getMembers());
				*/
				
			}
			 }); 
		
	
		
		
		
		
		
		
		
		
		
		viewer.setContentProvider(new GridViewContentProvider1());		
		viewer.setLabelProvider(new GridViewLabelProvider());			
				
		
		
		viewer.setInput(getModel().toArray());		
		
		
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
	
	private List<YParentModel>	 getModel(){
		
		
		//helper classes
				YClass hc1 = new YClass();
				hc1.setFullyQualifiedName("HelperClass1");
				YClass hc2 = new YClass();
				hc2.setFullyQualifiedName("HelperClass2");
				List<YClass> helperClassList = new ArrayList<YClass>();
				helperClassList.add(hc1);
				helperClassList.add(hc2);
				
				
				//test classes
						YClass tc1 = new YClass();
						tc1.setFullyQualifiedName("TestClass1");
						tc1.setMembers(helperClassList);
						tc1.setyClassType(YTYPE.TEST_CASE);
						YClass tc2 = new YClass();
						tc2.setFullyQualifiedName("TestClass2");
						tc2.setMembers(helperClassList);
						tc2.setyClassType(YTYPE.TEST_CASE);
						List<YClass> testClassList = new ArrayList<YClass>();
						testClassList.add(tc1);
						testClassList.add(tc2);
										
						
						//classes
						YClass c1 = new YClass();
						c1.setFullyQualifiedName("Class1");
						c1.setMembers(testClassList);
						YClass c2 = new YClass();
						c2.setFullyQualifiedName("Class2");
						c2.setMembers(testClassList);
						
					
						// model
						YModel yModel1 = new YModel();
						yModel1.setClassUnderTest(c1);
						yModel1.addTestCase(tc1);
						yModel1.addTestCase(tc2);
						
						// model
						YModel yModel2 = new YModel();
						yModel2.setClassUnderTest(c2);
						yModel2.addTestCase(tc1);
						yModel2.addTestCase(tc2);
						
						List<YModel> yModels = new ArrayList<YModel>();
						yModels.add(yModel1);
						yModels.add(yModel2);
						
						
						// parent model
						List<YParentModel> parentModelList = new ArrayList<YParentModel>();
						
						
						YParentModel parentModel = new YParentModel();						
						parentModel.setClassList(yModels);						
						parentModel.setParentName("Package1");
						parentModelList.add(parentModel);
						
						
						parentModel = new YParentModel();						
						parentModel.setClassList(yModels);						
						parentModel.setParentName("Package2");
						parentModelList.add(parentModel);
						
											
						
						
						return parentModelList;
				
		
		
	}
	
	
	private List<YClass> getTestCasses(String className, int colNum){
		
		List<YClass> yClasses = null;
		
		List<YParentModel> yParentModels =  getModel();
		
		for(YParentModel yParentModel: yParentModels){
			
			for(YModel yModel: yParentModel.getClassList()){
				
				if(className.equals(yModel.getClassUnderTest().getFullyQualifiedName())){
					
					yClasses = (List<YClass>)yModel.getTestCases();
					
				}
			}
		}
		
		return yClasses;
	}
	
	
private List<YClass> getHelperClasses(String className, int colNum){
	
	List<YClass> helperClasses = new ArrayList<YClass>();
	
	
	List<YClass> testCases = getTestCasses(className, colNum);
	
	
	for(YClass yClass: testCases){
		
		for(YClass helperClass: yClass.getMembers()){
			
			helperClasses.add(helperClass);
			
		}
		
	}
		
		
		return helperClasses;
	}



}