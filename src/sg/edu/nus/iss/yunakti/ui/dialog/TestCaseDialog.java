package sg.edu.nus.iss.yunakti.ui.dialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import sg.edu.nus.iss.yunakti.ui.dialog.filter.TestCaseFilter;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.TestCaseLabelProvider;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.TestCaseModel;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.YTestCaseCollection;

public class TestCaseDialog extends TitleAreaDialog {

	private Table testCasesTable;
	private TableViewer tableViewer;
	private Text searchText;
	private TestCaseFilter filter;
	YTestCaseCollection collection;

	public TestCaseDialog(Shell parentShell) {
		super(parentShell);
		collection = new YTestCaseCollection();
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Select Test Classes");
		// Set the message
		setMessage("Select Test Classes", IMessageProvider.INFORMATION);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search: ");
		searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
				| GridData.HORIZONTAL_ALIGN_FILL));

		// Instantiate TableViewer
		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		int[] columnAlignments = new int[] { SWT.LEFT, SWT.LEFT };
		String[] tableColumns = new String[] { "ClassName", "Type" };

		for (int i = 0; i < 2; i++) {
			TableColumn tableColumn = new TableColumn(table,
					columnAlignments[i]);
			new TableColumn(table, columnAlignments[i]);
			tableColumn.setText(tableColumns[i]);
			tableColumn.setWidth(100);

		}

		tableViewer.setLabelProvider(new TestCaseLabelProvider());

		tableViewer.setContentProvider(new ArrayContentProvider());

		tableViewer.setInput(collection.getTestCases());

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableViewer.getControl().setLayoutData(gridData);
		// Make the selection available to other views
		// ((Object) getShell()).setSelectionProvider(tableViewer);

		searchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				filter.setSearchText(searchText.getText());
				tableViewer.refresh();
			}
		});
		filter = new TestCaseFilter();
		tableViewer.addFilter(filter);

		return parent;

	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, "Add", true);
		// Add a SelectionListener

		// Create Reresh button
		Button refreshButton = createButton(parent, SWT.PUSH , "Refresh", false);
		// Add a SelectionListener
		refreshButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tableViewer.refresh();
				this.notify();
			}
		});
		

		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});
	}

	protected Button createOkButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				// ModelProvider testCases = ModelProvider.INSTANCE;
				
				AddPersonDialog dialog = new AddPersonDialog(getShell(), collection.getTestCases());
				dialog.open();
				tableViewer.refresh();
				if (dialog.getTestCase() != null) {

					System.out.println(dialog.getTestCase());
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;
	}
	
	public void refresh(){
		tableViewer.refresh();
	}

}
