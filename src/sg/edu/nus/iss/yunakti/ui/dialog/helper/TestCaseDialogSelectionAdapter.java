package sg.edu.nus.iss.yunakti.ui.dialog.helper;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import sg.edu.nus.iss.yunakti.model.YClass;

public class TestCaseDialogSelectionAdapter extends SelectionAdapter{
	
	private List<YClass> testClasses;

	public TestCaseDialogSelectionAdapter(){
		super();
	}
	
	public TestCaseDialogSelectionAdapter(List<YClass> testClasses){
		this();
		this.testClasses = testClasses;
	}
	
	public void widgetSelected(SelectionEvent e) {
	    
		
	}
}
