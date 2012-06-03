package sg.edu.nus.iss.yunakti.ui.dialog;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import sg.edu.nus.iss.yunakti.ui.view.YunaktiGridView;

public class FilteredTCSelectionDialog extends FilteredItemsSelectionDialog {

	private List<YClass> allTestClasses;
	private static final String DIALOG_SETTINGS = "FilteredResourcesSelectionDialogExampleSettings";
	Shell parentShell;
	private YModel model;
	private YunaktiGridView gridView;

	public FilteredTCSelectionDialog(Shell shell, List<YClass> allTestClasses, YModel model, YunaktiGridView view) {
		super(shell);
		this.parentShell = shell;
		this.allTestClasses = allTestClasses;
		this.model = model;
		this.gridView = view;
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
				return class1.compareTo(
						class2);
			}
		};
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider,
			ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
			throws CoreException {
		progressMonitor
				.beginTask("Searching", allTestClasses.size()); //$NON-NLS-1$
		for (Iterator<YClass> iter = allTestClasses.iterator(); iter
				.hasNext();) {
			contentProvider.add(iter.next().getFullyQualifiedName(), itemsFilter);
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
			
//			if(this.model.getTestCases().size() == 1){
//				 MessageDialog.openError(getShell(), "Error", "Can not add more than one Test Class for a ClassUnderTest");
//				 return;
//
//			}
			
			 
			YClass class1 = new YClass(this.getSelectedItems()
					.getFirstElement().toString());
			class1.setyClassType(YTYPE.TEST_CASE);
			
			boolean found = false;
			
			for (YClass testClass : model.getTestCases()) {
				if(testClass.getFullyQualifiedName().equals(class1.getFullyQualifiedName())){
					found = true;
				}
			}
			// Dont add duplicate test class again.
			if(found == false){
				model.addTestCase(class1);
				gridView.updateGridView(model);
				parentShell.forceFocus();
				try{
				EngineCore engineCore = new EngineCore();
				engineCore.writeAnnotation(model);
				}catch(Exception ex){
					System.out.println("Error in writing back to model");
					ex.printStackTrace();
				}
				super.okPressed();
			}else{
				 MessageDialog.openError(getShell(), "Error", "Duplicate Class: Selected Test Class has been already added");
			}
		
		}
	}

}