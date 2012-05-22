package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.ui.dialog.filter.TestCaseFilter;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.TestCaseLabelProvider;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.YTestCaseCollection;

/**
 * Dialog used to display all the testcases for a CUT. Also used to add or
 * delete an existing testcases for a CUT
 * 
 * @author subu
 * 
 */
public class TestCaseDialog extends TitleAreaDialog {

	private TableViewer tableViewer;
	private Text searchText;
	private TestCaseFilter filter;
	private YTestCaseCollection collection;
	private TestCaseDialog dialog;
	private List<YClass> testClassForCUT;

	public TestCaseDialog(Shell parentShell) {
		super(parentShell);
		dialog = this;
		// TODO : Replace with the original data.
		collection = new YTestCaseCollection();
	}

	public TestCaseDialog(Shell parentShell, YTestCaseCollection collection) {
		super(parentShell);
		dialog = this;
		// this.collection = new YTestCaseCollection();
		this.collection = collection;
	}

	@Override
	public void create() {
		super.create();
		// Set the title
		setTitle("Select Test Classes");
		// Set the message
		setMessage("Select Test Classes", IMessageProvider.INFORMATION);

		// This is done to refresh the tableviewer everytime the dialog gets
		// focus.
		this.getShell().addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				dialog.refresh();
			}
		});

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

		if (collection != null) {
			testClassForCUT = collection.getTestCases();
			setTableData(testClassForCUT);
		}

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		tableViewer.getControl().setLayoutData(gridData);

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

	public void setTableData(List<YClass> testClasses) {
		tableViewer.setInput(testClasses);
		this.refresh();
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

		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setReturnCode(CANCEL);
				close();
			}
		});

		// Create Refresh button
		Button refreshButton = createButton(parent, SWT.PUSH, "Refresh", false);
		// Add a SelectionListener
		refreshButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tableViewer.refresh();
			}
		});

		// Create Delete button
		Button deleteButton = createButton(parent, SWT.PUSH, "Delete", false);
		// Add a SelectionListener
		deleteButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				List<YClass> testClasses = collection.getTestCases();
				System.out.println("remove selection listener");
				for (YClass yClass : testClasses) {

					if (tableViewer.getSelection().toString()
							.contains(yClass.getFullyQualifiedName())) {
						try {
							testClasses.remove(yClass);
							tableViewer.refresh();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		this.setTableData(collection.getTestCases());
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
				if (collection != null) {
					FilteredTCSelectionDialog dialog = new FilteredTCSelectionDialog(
							getShell(), collection);
					dialog.setInitialPattern("?");
					dialog.open();
				} else {
					MessageDialog.openError(getShell(), "Error",
							"There are no testclasses in the selected project");
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

	public void refresh() {
		tableViewer.refresh();
	}

}
