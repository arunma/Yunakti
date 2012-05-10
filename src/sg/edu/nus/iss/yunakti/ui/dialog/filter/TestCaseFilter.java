package sg.edu.nus.iss.yunakti.ui.dialog.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.ui.dialog.helper.TestCaseModel;

public class TestCaseFilter extends ViewerFilter {
	
	private String searchString;

	public void setSearchText(String s) {
		// Search must be a substring of the existing value
		this.searchString = ".*" + s + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (searchString == null || searchString.length() == 0) {
			return true;
		}		
		YClass testClass = (YClass) element;
		if (testClass.getFullyQualifiedName().matches(searchString)) {
			return true;
		}
		if (testClass.getyClassType().toString().matches(searchString)) {
			return true;
		}
		return false;
	}

}
