package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import sg.edu.nus.iss.yunakti.Activator;
import sg.edu.nus.iss.yunakti.model.YMethod;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView;

public class FilteredMethodSelectionDialog extends FilteredItemsSelectionDialog {

	private List<YMethod> allTestMethods;
	private static final String DIALOG_SETTINGS = "FilteredResourcesSelectionDialogExampleSettings";
	Shell parentShell;
	private YModel model;
	private YunaktiGridView gridView;
	private TestCaseDialog testCaseDialog;
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public FilteredMethodSelectionDialog(Shell shell,
			List<YMethod> allTestMethods) {
		super(shell);
		this.parentShell = shell;
		this.allTestMethods = allTestMethods;
		System.out.println(allTestMethods);
		// this.model = model;
		// this.gridView = view;
		// this.testCaseDialog = testCaseDialog;
		setTitle("Add new Test Method");
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings()
				.getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings()
					.addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ItemsFilter() {
			public boolean matchItem(Object item) {
				return matches(item.toString());
			}

			public boolean isConsistentItem(Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator<String> getItemsComparator() {
		return new Comparator<String>() {
			public int compare(String class1, String class2) {
				return class1.compareTo(class2);
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		progressMonitor.beginTask("Searching", allTestMethods.size()); //$NON-NLS-1$
		for (Iterator<YMethod> iter = allTestMethods.iterator(); iter.hasNext();) {
			contentProvider.add(iter.next().getMethodName(), itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();

	}

	@Override
	public String getElementName(Object item) {
		return item.toString();
	}

	/**
	 * When ok is pressed in the Filtered Selection Dialog, add the testcase to
	 * the model.
	 */
	protected void okPressed() {
		if (this.getSelectedItems() != null
				&& this.getSelectedItems().size() > 0) {

			// if(this.model.getTestCases().size() == 1){
			// MessageDialog.openError(getShell(), "Error",
			// "Can not add more than one Test Class for a ClassUnderTest");
			// return;
			//
			// }

			String methodName = this.getSelectedItems().getFirstElement()
					.toString();
			YMethod method1 = new YMethod();
			method1.setMethodName(methodName);

			System.out.println(method1);

		}
	}

}