package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.TestCaseModel;

public class AddPersonDialog extends TitleAreaDialog {

	private Text text1;
	private TestCaseModel testCase;
    private ArrayList<YClass> testCases;

	public TestCaseModel getTestCase() {
		return testCase;
	}

	
	public AddPersonDialog(Shell parentShell){
		super(parentShell);
	}
	
	public AddPersonDialog(Shell parentShell, ArrayList<YClass> testCases) {	
		this(parentShell);
		this.testCases = testCases;
		
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Add a new TestCase");
		setMessage("Add a new TestCase",
				IMessageProvider.INFORMATION);
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		Label label1 = new Label(parent, SWT.NONE);
		label1.setText("Test Case");
		text1 = new Text(parent, SWT.BORDER);
		return parent;

	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Add");
		button.setFont(JFaceResources.getDialogFont());
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (text1.getText().length() != 0) {
					YClass class1 = new YClass();
					class1.setFullyQualifiedName(text1.getText());
					class1.setyClassType(YTYPE.TEST_CASE);
					testCases.add(class1);
					System.out.println(testCases);
					
					close();

				} else {
					setErrorMessage("Please enter the class name");
				}
			}
		});
	}
}
