package sg.edu.nus.iss.yunakti.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.text.edits.MalformedTreeException;

import sg.edu.nus.iss.yunakti.engine.search.YSearch;
import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.engine.writer.YPersister;
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
			//System.out.println("Search results:"+searchResults);
		} catch (Exception e) {
			ConsoleStreamUtil.print(e);
			e.printStackTrace();
		}
		
		//ConsoleStreamUtil.print("Printing....xxxxx"+(searchResults.get(0).toString()));
		
		
		/*for (YModel yModel : searchResults) {
			writeAnnotation(yModel);
		}*/
		
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
	
	public Set<YClass> getUniqueHelpers(List<YModel> models) {
		
		System.out.println("Calling getUniqueHelpers");
		Set<YClass> uniqueHelpers=new HashSet<YClass>();
		if (models!=null && models.size()>0){
			List<YClass> allTestCasesInModel = null;
			
			for (YModel eachModel : models) {
				allTestCasesInModel = eachModel.getTestCases();
				
				if (allTestCasesInModel!=null && allTestCasesInModel.size()>0){
					for (YClass eachTestCase : allTestCasesInModel) {
						uniqueHelpers.addAll(eachTestCase.getMembers());		
					}
				}
				
				
			}
			
		}
		System.out.println("Unique helpers size : "+uniqueHelpers.size());
		return uniqueHelpers;
	}
	
	
	public Map<String,List<YModel>> getModelsByPackageName(List<YModel> models) {
		
		System.out.println("Calling getModelsByPackageName");
		
		Map<String,List<YModel>> modelsByPackageName=new HashMap <String,List<YModel>>();
		
		if (models!=null && models.size()>0){
			
			YClass classUnderTest = null;
			String packageName=null;
			for (YModel eachModel : models) {
				classUnderTest = eachModel.getClassUnderTest();
				packageName=StringUtils.substringBeforeLast(classUnderTest.getFullyQualifiedName(), YConstants.DOT);
				
				addPackageAndModel(modelsByPackageName,packageName,eachModel);
			}
			
		}
		System.out.println("Unique testcase size : "+modelsByPackageName.size());
		return modelsByPackageName;
	}

	private void addPackageAndModel(Map<String, List<YModel>> modelsByPackageName, String packageName, YModel eachModel) {
		
		if (modelsByPackageName.containsKey(packageName)){
			List<YModel> existingModelsInPackage = modelsByPackageName.get(packageName);
			existingModelsInPackage.add(eachModel);
		}
		else{
			List<YModel> newModelList=new ArrayList<YModel>();
			newModelList.add(eachModel);
			modelsByPackageName.put(packageName, newModelList);
		}
		
	}
	
	
	public void writeAnnotation(YModel yModel){
		//TODO persist only if needed. Need to investigate Memento
		
		YPersister persister=new YPersister();
		
		try {
			persister.writeAnnotation(yModel);
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}
	
	

}