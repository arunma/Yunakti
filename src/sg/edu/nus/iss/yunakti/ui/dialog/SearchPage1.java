package sg.edu.nus.iss.yunakti.ui.dialog;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.TextSearchQueryProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class SearchPage1 extends DialogPage implements ISearchPage {
	
	private Text idText;
	private String selected;

	
	public SearchPage1() {
		
	}

	public SearchPage1(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public SearchPage1(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 5;
		parent.setLayout(layout);
		new Label(parent, 0).setText("Yunakti - Containing text:");
		idText = new Text(parent, SWT.BORDER);
		idText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		if (selected != null) {
			idText.setText(selected);
			idText.setSelection(0, selected.length());
		}
		setControl(parent);

	}

	@Override
	public boolean performAction() {
		if (idText.getText().length() == 0)
			return false;
		try {
			NewSearchUI.runQueryInBackground(TextSearchQueryProvider.getPreferred().createQuery(idText.getText()));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
        return true;
	}

	@Override
	public void setContainer(ISearchPageContainer container) {
		if (container.getSelection() instanceof TextSelection) {
			selected = ((TextSelection) container.getSelection()).getText();
			}

	}

}
