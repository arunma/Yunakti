package sg.edu.nus.iss.yunakti.ui.dialog.helper;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import sg.edu.nus.iss.yunakti.model.YClass;

public class TestCaseLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		YClass testCase = (YClass) element;
		if (testCase != null) {
			switch (columnIndex) {
			case 0:
				if (testCase.getFullyQualifiedName() != null) {
					return testCase.getFullyQualifiedName();
				}
			case 1:
				if (testCase.getyClassType() != null) {
					return testCase.getyClassType().toString();
				}
			case 2:
				if (testCase.getyClassType() != null) {
					return testCase.getyClassType().toString();
				}
			default:
				return "unknown " + columnIndex;
			}
		} else {
			return "";
		}

	}

}
