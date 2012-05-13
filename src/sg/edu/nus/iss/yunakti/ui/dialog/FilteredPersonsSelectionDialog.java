package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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

public class FilteredPersonsSelectionDialog extends
		FilteredItemsSelectionDialog {

	private List<Person> persons;
	private static final String DIALOG_SETTINGS = "FilteredResourcesSelectionDialogExampleSettings";

	public FilteredPersonsSelectionDialog(Shell shell, List<Person> persons) {
		super(shell);
		this.persons = persons;
		setTitle("Filtered Resources Selection Dialog Example");
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		// TODO Auto-generated method stub
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
	protected Comparator<Person> getItemsComparator() {
		return new Comparator<Person>() {
			public int compare(Person arg0, Person arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		progressMonitor.beginTask("Searching", persons.size()); //$NON-NLS-1$
		for (Iterator<Person> iter = persons.iterator(); iter.hasNext();) {
			contentProvider.add(iter.next(), itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();

	}

	@Override
	public String getElementName(Object item) {
		return ((Person)item).getName();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Shell shell = new Shell();
		   List<Person> persons = new ArrayList<Person>();
		   Person p1 = new Person("Subu");
		   Person p2 = new Person("Arun");
		   Person p3 = new Person("Kovalan");
		   Person p4 = new Person("Alphy");
		   Person p5 = new Person("Suriya");
		   persons.add(p1);
		   persons.add(p2);
		   persons.add(p3);
		   persons.add(p4);
		   persons.add(p5);
		   
		   FilteredPersonsSelectionDialog dialog = new FilteredPersonsSelectionDialog(shell, persons);
		   dialog.setInitialPattern("a");
		   dialog.open();

	}

}