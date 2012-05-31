package sg.edu.nus.iss.yunakti.model.providers;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YParentModel;

public class GridViewLabelProvider implements ITableLabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof YModel)
		{
		YModel yModel = (YModel) element;
		switch (columnIndex) {
		case 0:
			
			
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FILE);
		case 1:
			
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FOLDER);
		case 2:
			
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FILE);
		}
		
		}
		else{
			if(element instanceof YParentModel){
				
				YParentModel yParentModel = (YParentModel) element;
				
				switch (columnIndex) {
				case 0:
					return PlatformUI.getWorkbench().getSharedImages()
							.getImage(ISharedImages.IMG_OBJ_FOLDER);
				case 1:
					
					return null;
				case 2:
					
					return null;
				}
				
			}
		}
		
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof YModel)
		{
		YModel yModel = (YModel) element;
		switch (columnIndex) {
		case 0:
			return yModel.getClassUnderTest().getFullyQualifiedName();
		case 1:
			
			return getTestClasses(yModel);
		case 2:
			
			return getHelperClasses(yModel);
		}
		
		}
		else{
			if(element instanceof YParentModel){
				
				YParentModel yParentModel = (YParentModel) element;
				
				switch (columnIndex) {
				case 0:
					return yParentModel.getParentName();
				case 1:
					
					return "";
				case 2:
					
					return "";
				}
				
			}
		}
		
		return null;
	}
	
	private String getTestClasses(YModel yModel){
		
		String strTestClasses = null;
		
		StringBuilder builder=new StringBuilder();
		
		for (YClass testCase: yModel.getTestCases()){
			builder.append(testCase.getFullyQualifiedName()).append(", ");
			
		}
		
		strTestClasses = builder.toString();
		
		return strTestClasses;
		
	}
	
	
	private String getHelperClasses(YModel yModel){
		
String strHelperClasses = null;


StringBuilder builder=new StringBuilder();
for (YClass testCase: yModel.getTestCases()){

for (YClass testCaseMember:testCase.getMembers()){
	builder.append(testCaseMember.getFullyQualifiedName()).append(", ");
}
}
	strHelperClasses = builder.toString();
		
		return strHelperClasses;
		
	}		

}
