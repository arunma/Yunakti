package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

import sg.edu.nus.iss.yunakti.Activator;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.YTestCaseCollection;

public class FilteredTCSelectionDialog extends FilteredItemsSelectionDialog {

	private YTestCaseCollection collection;
	private static final String DIALOG_SETTINGS = "FilteredResourcesSelectionDialogExampleSettings";
	Shell parentShell;

	public FilteredTCSelectionDialog(Shell shell, YTestCaseCollection collection) {
		super(shell);
		this.parentShell = shell;
		this.collection = collection;
		setTitle("Add new Test Class");
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
				return matches(((YClass) item).getFullyQualifiedName()
						.toString());
			}

			public boolean isConsistentItem(Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator<YClass> getItemsComparator() {
		return new Comparator<YClass>() {
			public int compare(YClass class1, YClass class2) {
				return class1.getFullyQualifiedName().compareTo(
						class2.getFullyQualifiedName());
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		progressMonitor
				.beginTask("Searching", collection.getAllTestCases().size()); //$NON-NLS-1$
		for (Iterator<YClass> iter = collection.getAllTestCases().iterator(); iter
				.hasNext();) {
			contentProvider.add(iter.next(), itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();

	}

	@Override
	public String getElementName(Object item) {
		return ((YClass) item).getFullyQualifiedName();
	}

	/**
	 * When ok is pressed in the Filtered Selection Dialog, add the testcase to 
	 * the model.
	 */
	protected void okPressed() {
		if (this.getSelectedItems() != null
				&& this.getSelectedItems().size() > 0) {
			YClass class1 = new YClass(this.getSelectedItems()
					.getFirstElement().toString());
			class1.setyClassType(YTYPE.TEST_CASE);
			
			boolean found = false;
			for (YClass testClass : collection.getTestCases()) {
				if(testClass.getFullyQualifiedName().equals(class1.getFullyQualifiedName())){
					found = true;
				}
			}
			
			if(found == false){
				collection.addTestCase(class1);
				parentShell.forceFocus();
				super.okPressed();
			}else{
				 MessageDialog.openError(getShell(), "Error", "Duplicate Class: Selected Test Class has been already added");
			}
		
		}
	}

}