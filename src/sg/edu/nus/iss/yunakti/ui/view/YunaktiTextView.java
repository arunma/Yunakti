package sg.edu.nus.iss.yunakti.ui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import sg.edu.nus.iss.yunakti.engine.EngineCore;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.ui.util.YModelListParser;

public class YunaktiTextView extends ViewPart implements ISelectionListener {
	
	private static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	// private Label label;
	public static final String ID = "Yunakti.view1";
	public static final String NAME = "YunaktiTextview";
	EngineCore engineCore = new EngineCore();
	YModelListParser lstPareser = new YModelListParser();
	List<YModel> lstModelClass;
	private List<YClass> lstYClass;
	private org.eclipse.swt.widgets.List lstModel;
	StringBuilder printString;
	public YunaktiTextView() {
		super();
		lstYClass = new ArrayList<YClass>();
	}

	public void setFocus() {
		// label.setFocus();

	}

	/**
	 * @see IViewPart.init(IViewSite)
	 */
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
	}

	/*private void setUpModelData() {
		lstModelClass = new ArrayList<YModel>();
		List<YClass> lstTestclasses1 = new ArrayList<YClass>();
		List<YClass> lstTestclasses2 = new ArrayList<YClass>();
		YClass tstcls1 = new YClass();
		tstcls1.setFullyQualifiedName("Test1.class");
		YClass tstcls2 = new YClass();
		tstcls2.setFullyQualifiedName("Test2.class");
		YClass tstcls3 = new YClass();
		tstcls3.setFullyQualifiedName("Test3.class");
		YClass tstcls4 = new YClass();
		tstcls4.setFullyQualifiedName("Test4.class");
		YClass tstcls5 = new YClass();
		tstcls5.setFullyQualifiedName("Test5.class");
		List<YClass> lstHelperClass = new ArrayList<YClass>();
		YClass cls1 = new YClass();
		cls1.setFullyQualifiedName("Helper1.class");
		cls1.setyClassType(YTYPE.TEST_HELPER);
		YClass cls2 = new YClass();
		cls2.setFullyQualifiedName("Helper2.class");
		cls2.setyClassType(YTYPE.TEST_HELPER);
		YClass cls3 = new YClass();
		cls3.setFullyQualifiedName("Helper3.class");
		cls3.setyClassType(YTYPE.TEST_HELPER);
		YClass cls4 = new YClass();
		cls4.setFullyQualifiedName("Helper4.class");
		cls4.setyClassType(YTYPE.TEST_HELPER);
		YClass cls5 = new YClass();
		cls5.setFullyQualifiedName("Helper5.class");
		cls5.setyClassType(YTYPE.TEST_HELPER);
		lstHelperClass.add(cls1);

		lstHelperClass.add(cls2);
		tstcls1.addMember(cls1);
		tstcls1.addMember(cls2);
		List<YClass> lstHelperClass1 = new ArrayList<YClass>();
		lstHelperClass1.add(cls3);
		lstHelperClass1.add(cls4);
		lstHelperClass1.add(cls5);
		tstcls2.addMember(cls3);
		tstcls2.addMember(cls4);
		tstcls3.addMember(cls4);
		tstcls3.addMember(cls5);
		tstcls4.addMember(cls5);
		tstcls5.addMember(cls5);
		lstTestclasses1.add(tstcls1);
		lstTestclasses1.add(tstcls2);
		lstTestclasses2.add(tstcls3);
		lstTestclasses2.add(tstcls4);
		lstTestclasses2.add(tstcls5);
		YClass modelcls1 = new YClass();
		modelcls1.setFullyQualifiedName("Class1.class");
		YClass modelcls2 = new YClass();
		modelcls2.setFullyQualifiedName("Class2.class");
		YModel model1 = new YModel();
		model1.setClassUnderTest(modelcls1);
		model1.addTestCase(tstcls1);
		model1.addTestCase(tstcls2);
		YModel model2 = new YModel();
		model2.setClassUnderTest(modelcls2);
		model2.addTestCase(tstcls4);
		lstModelClass.add(model1);
		lstModelClass.add(model2);

	}
*/
	
	

	public void createPartControl(Composite parent) {
		

	

		Composite composite = new Composite(parent, SWT.NONE);

		// Add a checkbox to toggle filter
		lstModel = new org.eclipse.swt.widgets.List(composite, SWT.BORDER
				| SWT.MULTI);

		getSite().getPage().addSelectionListener(this);
		// setTextList(lstYClass);

		final Shell shell = parent.getShell();
		Button print = new Button(composite, SWT.PUSH);
		print.setText("Print");
		final int insetX = 4, insetY = 4;
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = insetX;
		formLayout.marginHeight = insetY;
		composite.setLayout(formLayout);
		FormData button1Data = new FormData();
		button1Data.right = new FormAttachment(100, -insetX);

		button1Data.bottom = new FormAttachment(100, 0);
		print.setLayoutData(button1Data);
		FormData listData = new FormData();
		listData.left = new FormAttachment(0, 0);
		listData.right = new FormAttachment(100, 0);
		listData.top = new FormAttachment(0, 0);
		listData.bottom = new FormAttachment(print, -insetY);
		lstModel.setLayoutData(listData);

		print.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PrintDialog printDialog = new PrintDialog(shell, SWT.NONE);
				printDialog.setText("Print");
				PrinterData printerData = printDialog.open();
				if (!(printerData == null)) {
					Printer p = new Printer(printerData);
					p.startJob("PrintJob");
					p.startPage();
					Rectangle trim = p.computeTrim(0, 0, 0, 0);
					Point dpi = p.getDPI();
					int leftMargin = dpi.x + trim.x;
					int topMargin = dpi.y / 2 + trim.y;
					GC gc = new GC(p);
					Font font = gc.getFont();
					// String printText = categories.getItems().toString();
					//String printText = lstModel.getItems().toString();
				//System.out.println("leftMargin"+leftMargin);
				
					Point extent = gc.stringExtent(printString.toString());
					gc.drawString(printString.toString(), leftMargin,
							topMargin + font.getFontData()[0].getHeight());
					p.endPage();
					gc.dispose();
					p.endJob();
					p.dispose();
				}

			}
		});
		shell.open();
		
		// selectionChanged(null,null);
		
		//selectionChanged(getSite().getPage().getSelection());
		parent.pack();
	}

	public List<YClass> getLstYClass() {
		return lstYClass;
	}

	public void setLstYClass(List<YClass> lstYClass) {
		this.lstYClass = lstYClass;
	}
	public void setListModel(List<YModel> yModels)
	{
		logger.fine("ymodels"+yModels);
		logger.fine("lstModel"+lstModel);
		String[] objMapper=null;
		printString=new StringBuilder();
		if (yModels != null && !yModels.isEmpty()) {
			ObjectMapper mapper=new ObjectMapper();
			try {
				//System.out.println("objMapper first"+mapper.writeValueAsString(yModels));
				objMapper=new String[1];
			objMapper[0]=mapper.writeValueAsString(yModels);
				System.out.println("objMapper"+objMapper[0]);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		/*	List<StringBuilder> sb= YModelListParser.parseListYModelToString(yModels);
			String[] str = new String[sb.size()];
			int i = 0;
			for (StringBuilder s : sb) {
				str[i] = s.toString();
				i++;
				printString.append(s);
			}*/
		
			if(objMapper!=null && objMapper.length>0)
			{
			printString.append(objMapper[0]);
			lstModel.setItems(objMapper);
			}
			
		}
		else
		{
			lstModel.removeAll();
		}
			
		
	}

	
	

	/**
	 * @see ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
	 **/
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		
		logger.fine("selector invoked" + selection);
		if(YModelListParser.findwhetherToPopulateModel(selection))
		{
		List<YModel> yModels = engineCore
				.populateModel((IStructuredSelection) selection);
		logger.fine("yModels" + yModels);
		setListModel(yModels);
		}


	}

}

class ItemLabelProvider implements ILabelProvider {
	public Image getImage(Object arg0) {
		return null;
	}

	public String getText(Object arg0) {
		// logger.fine("gettext"+((YClass)
		// arg0).getFullyQualifiedName());
		// return ((YClass) arg0).getFullyQualifiedName();
		return arg0.toString();
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
