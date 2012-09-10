package sg.edu.nus.iss.yunakti.model.providers;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YParentModel;

public class GridViewContentProvider1 implements ITreeContentProvider{
	
	private Object[] yParentModels;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
		
		this.yParentModels = (Object[])newInput;
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		
		return this.yParentModels;		
		
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof YParentModel)
		return ((YParentModel) parentElement).getClassList().toArray();
		
		if(parentElement instanceof YModel)
		{
			if(((YModel) parentElement).getTestCases() != null)
			
			return  ((YModel) parentElement).getTestCases().toArray();
		}
				
		
		
		return null;
		}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		
		if(element !=null && element instanceof YParentModel)
		{
			if(((YParentModel) element).getClassList() !=null)
			
		return ((YParentModel) element).getClassList().size() > 0;
		}
		
		
		if(element !=null && element instanceof YModel)
		{
			if(((YModel) element).getTestCases() !=null)
			
		return ((YModel) element).getTestCases().size() > 0;
		}
		
		return false;
	}

}
