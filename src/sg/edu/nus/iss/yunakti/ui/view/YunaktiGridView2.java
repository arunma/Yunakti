package sg.edu.nus.iss.yunakti.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.model.providers.GridViewContentProvider1;
import sg.edu.nus.iss.yunakti.ui.dialog.HelperDialog;
import sg.edu.nus.iss.yunakti.ui.dialog.TestCaseDialog;


public class YunaktiGridView2  extends PageBookView{
	public static final String ID = "sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView";

	private TableViewer viewer;
	// We use icons
	/*private static final Image CHECKED = Activator.getImageDescriptor(
			"icons/checked.gif").createImage();
	private static final Image UNCHECKED = Activator.getImageDescriptor(
			"icons/unchecked.gif").createImage();*/

	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);		
		createViewer(parent);
	}

	private void createViewer(final Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		//createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		
		table.addListener(SWT.Selection, new Listener() {  
			 
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				
				 Shell shell = new Shell();
	              shell.setText("Test Class");

	              // Set layout for shell
	              GridLayout layout = new GridLayout();
	              shell.setLayout(layout);
	      		
	             	              // Open the shell and run until a close event is detected
	              String str = null;
	              
	              TableItem[] selection = table.getSelection();
	              for (int i = 0; i < selection.length; i++)
	                str = selection[i].getText();
	              System.out.println("DefaultSelection={" + str + "}");
	              
				
				/*TestCaseDialog caseDialog = new TestCaseDialog(parent.getShell());
								
				caseDialog.create();
				
				caseDialog.setTableData((List<YClass>)getModel().get(0).getTestCases());
				
				caseDialog.open();*/
	              
	              HelperDialog dialog = new HelperDialog(parent.getShell());
	              
	              dialog.setLstYClass((List<YClass>)((List<YClass>)getModel().get(0).getTestCases()).get(0).getMembers());
				
				
			}
			 }); 
		
		
		
		viewer.setContentProvider(new ArrayContentProvider());
		
		
		
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Class Under Test");
		column.setLabelProvider(new ColumnLabelProvider() {

			public String getText(Object element) {
				

				
				if(element != null && element instanceof YModel)
					
				{					
					YModel ymodel = (YModel) element;
				
				return ymodel.getClassUnderTest().getFullyQualifiedName();
						
			}	
				
				return null;
			
				
			}		
			

		});		
		
		column = new TableViewerColumn(viewer, SWT.Selection);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Test Cases");	
	
		
		column.getColumn().addSelectionListener(new SelectionAdapter() {
	       	
	          public void widgetSelected(SelectionEvent e) {
	        	  
	        	  
	        	  
	        	 
	        	  String str = null;
	              
	              TableItem[] selection = table.getSelection();
	              for (int i = 0; i < selection.length; i++)
	                str = selection[i].getText();
	              System.out.println("DefaultSelection$$$$$$$$$$$={" + str + "}");

	        	  	
	        	  
	        	  
	          }
	          });

		
		column.setLabelProvider(new ColumnLabelProvider() {



			
			public String getText(Object element) {
				
				if(element != null && element instanceof YModel)
					
				{					
					YModel ymodel = (YModel) element;
					
					if(!ymodel.getTestCases().isEmpty())
					{
						return	getTestClasses(ymodel);
					}
				
				
						
			}
				
				return null;
			
		}
			
			
		
			
			@Override
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				
				
				return PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJ_FOLDER);
			}

	
		

		});	
		
		
		
		column = new TableViewerColumn(viewer, SWT.Selection);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Helper Classes");
		column.setLabelProvider(new ColumnLabelProvider() {
						
			public String getText(Object element) {
				
				if(element != null && element instanceof YModel)
					
				{					
					YModel ymodel = (YModel) element;
					
					if(!ymodel.getTestCases().isEmpty())
					{
																	
						return	getHelperClasses(ymodel);
					}
				
				
						
			}	
				
				return null;
			
		}
			
			
			@Override
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				
				
				return PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJ_FILE);
						
			}

	
			
			
		});
		
		
		viewer.setInput(getModel());

		
		
				
		
		
		
		
		
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

	public TableViewer getViewer() {
		return viewer;
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	protected IPage createDefaultPage(PageBook arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart arg0, PageRec arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
private String getTestClasses(YModel yModel){
		
		String strTestClasses = null;
		
		StringBuilder builder=new StringBuilder();
		
		for (YClass testCase: yModel.getTestCases()){
			builder.append(testCase.getFullyQualifiedName()).append(", ");
			
		}
		
		if(builder != null){
			strTestClasses = builder.toString();
			
			strTestClasses = strTestClasses.substring(0,strTestClasses.length()-2);
		}
		
		return strTestClasses;
		
	}

	private String getHelperClasses(YModel yModel){
		
String strHelperClasses = null;


StringBuilder builder=new StringBuilder();
for (YClass testCase: yModel.getTestCases()){

for (YClass testCaseMember:testCase.getMembers()){
	builder.append(testCaseMember.getFullyQualifiedName()).append(", ");
}
}
		if(builder != null){
			strHelperClasses = builder.toString();
			
			strHelperClasses = strHelperClasses.substring(0,strHelperClasses.length()-2);
		}
		
		return strHelperClasses;
		
	}		
	
	
	
	
	public void updateView(List<YModel> modelList){
		
		viewer.setInput(modelList);
		
	}
	
private List<YModel>	 getModel(){
	
	
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
					tc1.addMember(hc1);
					tc1.addMember(hc2);
					
					tc1.setyClassType(YTYPE.TEST_CASE);
					YClass tc2 = new YClass();
					tc2.setFullyQualifiedName("TestClass2");
					tc2.addMember(hc1);
					tc2.addMember(hc2);
					tc2.setyClassType(YTYPE.TEST_CASE);
					List<YClass> testClassList = new ArrayList<YClass>();
					testClassList.add(tc1);
					testClassList.add(tc2);
									
					
					//classes
					YClass c1 = new YClass();
					c1.setFullyQualifiedName("Class1");
					c1.addMember(tc1);
					c1.addMember(tc2);
					YClass c2 = new YClass();
					c2.setFullyQualifiedName("Class2");
					c2.addMember(tc1);
					c2.addMember(tc2);
					
				
					// model
					YModel yModel1 = new YModel();
					yModel1.setClassUnderTest(c1);
					//yModel1.setTestCases(testClassList);
					
					// model
					YModel yModel2 = new YModel();
					yModel2.setClassUnderTest(c2);
					//yModel2.setTestCases(testClassList);
					
					List<YModel> yModels = new ArrayList<YModel>();
					yModels.add(yModel1);
					yModels.add(yModel2);
					
					
					return yModels;
			
	
	
}
	

	
	
}
