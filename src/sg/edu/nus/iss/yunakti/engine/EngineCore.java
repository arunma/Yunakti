package sg.edu.nus.iss.yunakti.engine;

import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.search.YSearch;
import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.model.YModel;

public class EngineCore {

	
	public EngineCore() {
		
	}
	
	public List<YModel> populateModel(IStructuredSelection selection) {
		
		
		List<YModel> searchResults = null;
		try {
			YSearch search=new YSearch();
			List<IJavaElement> allSearchElements = search.gatherAllSearchElementsFromSelection(selection);
			search.search(allSearchElements);
			searchResults = search.getResults();
			ConsoleStreamUtil.println("Next line coming up ::::");
			//System.out.println("Search results:"+searchResults);
		} catch (Exception e) {
			System.out.println("sdkljsklfdjlkdshjklsjsdklghsdg");
			ConsoleStreamUtil.print(e);
			e.printStackTrace();
		}
		
		//ConsoleStreamUtil.print("Printing....xxxxx"+(searchResults.get(0).toString()));
		return searchResults;
		
		
	}
	

}