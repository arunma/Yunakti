package sg.edu.nus.iss.yunakti.engine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.search.YSearch;
import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.model.YClass;
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
			ConsoleStreamUtil.print(e);
			e.printStackTrace();
		}
		
		//ConsoleStreamUtil.print("Printing....xxxxx"+(searchResults.get(0).toString()));
		return searchResults;
		
		
	}

	public Set<YClass> getUniqueTestCases(List<YModel> models) {
		
		System.out.println("Calling getUniqueTestCases");
		Set<YClass> uniqueTestCases=new HashSet<YClass>();
		if (models!=null && models.size()>0){
			List<YClass> allTestCasesInModel = null;
			for (YModel eachModel : models) {
				allTestCasesInModel = eachModel.getTestCases();
				uniqueTestCases.addAll(allTestCasesInModel);
			}
			
		}
		System.out.println("Unique testcase size : "+uniqueTestCases.size());
		return uniqueTestCases;
	}
	

}