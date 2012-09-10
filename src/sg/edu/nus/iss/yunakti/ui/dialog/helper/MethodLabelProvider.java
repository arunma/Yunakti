package sg.edu.nus.iss.yunakti.ui.dialog.helper;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YMethod;

public class MethodLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		YMethod testMethod = (YMethod) element;

		if (testMethod != null) {
				return testMethod.getMethodName();
		}else{
			return "";
		}

	}

}
