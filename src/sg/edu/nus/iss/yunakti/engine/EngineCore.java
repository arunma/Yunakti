package sg.edu.nus.iss.yunakti.engine;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.util.YSearch;
import sg.edu.nus.iss.yunakti.model.YModel;

public class EngineCore {

	
	public EngineCore() {
		
	}
	
	public List<YModel> populateModel(IStructuredSelection selection) {
		
		
		YSearch search=new YSearch();
		List<IJavaElement> allSearchElements = search.gatherAllSearchElementsFromSelection(selection);
		search.search(allSearchElements);
		
		return search.getModels();
		
	}
	

}
