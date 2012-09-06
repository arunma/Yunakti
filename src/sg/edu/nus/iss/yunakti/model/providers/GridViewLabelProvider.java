package sg.edu.nus.iss.yunakti.model.providers;

import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import sg.edu.nus.iss.yunakti.Activator;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YMethod;
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
		
if(element instanceof YClass){
			

			YClass yClass = (YClass) element;
			switch (columnIndex) {
			case 0:
				return JavaUI.getSharedImages().getImage(org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PUBLIC);
			case 1:
				
				return Activator.getImageDescriptor("icons/search.gif").createImage();
			case 2:
				
				return null;
			}
			
			
			
		}
else
		
		if(element instanceof YModel)
		{
		YModel yModel = (YModel) element;
		switch (columnIndex) {
		case 0:
			
			return JavaUI.getSharedImages().getImage(org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS);
					
		case 1:
		   return	Activator.getImageDescriptor("icons/search.gif").createImage();
			
			//return 
					//PlatformUI.getWorkbench().getSharedImages()
					//.getImage(ISharedImages.IMG_OBJ_FOLDER);
		case 2:
			
			if(getHelperClasses(yModel)==null){
				return null;
			}
			
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_FILE);
		}
		
		}
		else{
			if(element instanceof YParentModel){
				
								
				switch (columnIndex) {
				case 0:
					return JavaUI.getSharedImages().getImage(org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_PACKAGE);
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
		if(element instanceof YClass){
			

			YClass yClass = (YClass) element;
			switch (columnIndex) {
			case 0:
				return getCUTMethod(yClass);
			case 1:
				
				return getTestClassMethods(yClass);
			case 2:
				
				return "";
			}
			
			
			
		}
		else
		if(element instanceof YModel)
		{
		YModel yModel = (YModel) element;
		switch (columnIndex) {
		case 0:
			return yModel.getClassUnderTest().getName();
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
			builder.append(testCase.getName()).append(", ");
			
		}
		
		strTestClasses = formatToStr(builder);
		
		return strTestClasses;
		
	}
	

private String getHelperClasses(YModel yModel){
		
String strHelperClasses = null;


StringBuilder builder=new StringBuilder();
for (YClass testCase: yModel.getTestCases()){

for (YClass testCaseMember:testCase.getMembers()){
	builder.append(testCaseMember.getName()).append(", ");
}
}
	strHelperClasses = formatToStr(builder);
		
		return strHelperClasses;
		
	}	

	private String formatToStr(StringBuilder builder){
		String str = null;
		int len = builder.length();
		
		if(len > 2){
			
			
			str = builder.substring(0, len-2);
		}
		
		return str;
		
	}
	
	private String getTestClassMethods(YClass yClass){
		
		String strMethod = null;
		
		

		StringBuilder builder=new StringBuilder();
		
		/*for (YMethod yMethod: yClass.getMethods()){
		
		for (YClass testCaseMember:testCase.getMembers()){
			builder.append(testCaseMember.getName()).append(", ");
		}
		}
		strMethod = formatToStr(builder);
				*/
		
		
		
			
			for(YMethod yMethod : yClass.getMethods()){
				
				builder.append(yMethod.getMethodName()).append(", ");
				
				
			
		}
		
		strMethod = formatToStr(builder);
		
		return strMethod;
		
	}
	
	private String getCUTMethod(YClass yClass){
		String cutMethod = null;
		
		Collection<YMethod> yMethods = (List<YMethod>) yClass.getMethods();
		
		if(yMethods != null && yMethods.size() != 0){
			
			YMethod yMethod = (YMethod)yMethods.toArray()[0];
			
			YMethod yMethod2 = yMethod.getCallees().get(0);
			
			cutMethod = yMethod2.getMethodName();
			
		}
		
		return cutMethod;
		
		
	}

}
