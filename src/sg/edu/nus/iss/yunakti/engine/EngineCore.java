package sg.edu.nus.iss.yunakti.engine;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.search.ResultSetMapperBase;
import sg.edu.nus.iss.yunakti.engine.search.ResultSetMapperImpl;
import sg.edu.nus.iss.yunakti.engine.util.SearchUtil;
import sg.edu.nus.iss.yunakti.model.YModel;

public class EngineCore {

	
	public EngineCore() {
		
	}
	
	public YModel populateModel(IStructuredSelection selection) {
		
		
		
		List<IJavaElement> allSelectedElements = SearchUtil.gatherAllSearchElementsFromSelection(selection);
		
		ResultSetMapperBase resultSetMapper=new ResultSetMapperImpl();
		SearchUtil.search(allSelectedElements, resultSetMapper);
		return null;
	}
	

}
