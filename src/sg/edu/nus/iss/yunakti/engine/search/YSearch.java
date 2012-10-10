package sg.edu.nus.iss.yunakti.engine.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.TypeReferenceMatch;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.parser.YModelVisitor;
import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.engine.util.ParserUtils;
import sg.edu.nus.iss.yunakti.engine.util.WorkspaceUtils;
import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class YSearch {
	
	private static Logger logger=Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private List<YModel> models=new ArrayList<YModel>();
	
	public List<IJavaElement> gatherAllSearchElementsFromSelection(IStructuredSelection selection) {
		
		Iterator elementIterator = selection.iterator();
		List<IJavaElement> javaElements=new ArrayList<IJavaElement>();
		for(Object elementObject;elementIterator.hasNext();){
			elementObject=elementIterator.next();
			if (elementObject instanceof IJavaElement){
				javaElements.add((IJavaElement)elementObject);
			}
		}
		
		return javaElements;
		
	}
	
	public void search(List<IJavaElement> javaElements) throws JavaModelException, CoreException{
		
		List<String> allClassNames = getAllClassNamesInWorkspace();
		
		ResultSetMapperBase resultMapper=new ResultSetMapperImpl(allClassNames);
		logger.fine("About to search.....");
		
		SearchPattern pattern = SearchPattern.createPattern(YConstants.TEST_CASE_ANNOTATION, IJavaSearchConstants.ANNOTATION_TYPE,IJavaSearchConstants.ANNOTATION_TYPE_REFERENCE, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE);
		//SearchPattern pattern=SearchPattern.createPattern(YConstants.TEST_CASE_ANNOTATION, IJavaSearchConstants.ANNOTATION_TYPE, IJavaSearchConstants.ALL_OCCURRENCES, SearchPattern.R_FULL_MATCH);
		logger.fine("Pattern... "+pattern);
		try {
			new SearchEngine().search(pattern, new SearchParticipant[]{SearchEngine.getDefaultSearchParticipant()},
					SearchEngine.createJavaSearchScope(javaElements.toArray(new IJavaElement[]{})), resultMapper, null);
			logger.fine("Search done....");
			
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	

	private List<String> getAllClassNamesInWorkspace() throws JavaModelException, CoreException {

		WorkspaceUtils utils=WorkspaceUtils.getInstance();
		utils.gatherAllClassesInWorkspace();
		List<YClass> allClasses = utils.getAllClasses();
		List<String> fqNames=new ArrayList<String>();
		for (YClass yClass : allClasses) {
			fqNames.add(yClass.getFullyQualifiedName());
		}
		return fqNames;
	}

	public List<YModel> getResults(List<IJavaElement> allSearchElements, boolean fullScan) {
		
		models=groupModels(models);
		models=filterModels(models, allSearchElements, fullScan);
		
		ConsoleStreamUtil.classToString("Returning results :",models);
		return models;
	}


	/**
	 * 
	 * In case of full scan (when the non-annotated class under tests are selected), 
	 * the unrelated classes should be filtered out.
	 * For normal scan (when the actual test cases are selected), we can ignore this method
	 * @param allSearchElements 
	 * @return 
	 */
	private List<YModel> filterModels(List<YModel> models, List<IJavaElement> allSearchElements, boolean fullScan) {
		if (!fullScan){
			return models;
		}
		
		HashMap<String, YModel> cutModelMap = getYModelsAsCUTYModelMap(models);
		HashMap<String, YModel> tcModelMap = getYModelsAsTCYModelMap(models);
		models=filterSearchElementsFromModels(allSearchElements, cutModelMap, tcModelMap);
		
		
		return models;
		//return filteredYModels;
	}


	/**
	 * This is an utility method for better lookup during filtering. 
	 * 
	 * Say, you do a full scan of the project and have a list of ymodels. But you select just a subsection (eg : a package)
	 * Instead of doing a linear search on YModel list, we could do a constant time search with HashMap
	 * 
	 * @param models
	 * @return
	 */
	private HashMap<String,YModel> getYModelsAsCUTYModelMap(List<YModel> models){
		HashMap<String,YModel> cutModelMap=new HashMap<String,YModel>();
		for (YModel eachModel : models) {
			cutModelMap.put(eachModel.getClassUnderTest().getFullyQualifiedName(), eachModel);	
		}
		
		return cutModelMap;
		
	}
	
	//Same as above. just that the key is a testcase
	private HashMap<String,YModel> getYModelsAsTCYModelMap(List<YModel> models){
		HashMap<String,YModel> tcModelMap=new HashMap<String,YModel>();
		//Hail 0(n^2)
		for (YModel eachModel : models) {
			for (YClass eachTestCase:eachModel.getTestCases()){
				tcModelMap.put(eachTestCase.getFullyQualifiedName(), eachModel);	
			}
				
		}
		
		return tcModelMap;
		
	}
	
	private List<YModel> filterSearchElementsFromModels(List<IJavaElement> allSearchElements, HashMap<String, YModel> cutModelMap, HashMap<String, YModel> tcModelMap) {
		
		List<YModel> filteredModels=new ArrayList<YModel>();
		
		ICompilationUnit eachSearchCompilationUnit=null;
		for (IJavaElement eachSearchElement : allSearchElements) {
			
			ConsoleStreamUtil.println(" Each element name "+eachSearchElement.getElementName());
			if (eachSearchElement instanceof ICompilationUnit){
				eachSearchCompilationUnit = (ICompilationUnit)eachSearchElement;
				IType searchMainType =null;
				try {
					searchMainType = eachSearchCompilationUnit.getAllTypes()[0];
				
					ConsoleStreamUtil.println(" AWESOME BEFORE "+searchMainType.getFullyQualifiedName());	
					if (cutModelMap.containsKey(searchMainType.getFullyQualifiedName())){
						ConsoleStreamUtil.println(" CUT !!!! "+searchMainType.getFullyQualifiedName());	
						filteredModels.add(cutModelMap.get(searchMainType.getFullyQualifiedName()));
					}
					else if (tcModelMap.containsKey(searchMainType.getFullyQualifiedName())){
						ConsoleStreamUtil.println(" TESTCASE !!!! "+searchMainType.getFullyQualifiedName());	
						filteredModels.add(tcModelMap.get(searchMainType.getFullyQualifiedName()));
					}
					else{
						//Construct a dummy YModel. Why did i do it here?  Optionally, I could have done it in the engine core.
						//However, there could be a time when a non-mapped class and a mapped class is selected under the same selection
						YModel dummyYModelForSelection = createDummyYModelForSelection(eachSearchCompilationUnit);
						filteredModels.add(dummyYModelForSelection);
					}
				} catch (JavaModelException e) {
					e.printStackTrace();
				}
			}
		}
		
		return filteredModels;
	}
	
	private YModel createDummyYModelForSelection(ICompilationUnit compilationUnit) throws JavaModelException {
		
		YModel yModel=new YModel();
		YClass classUnderTest=new YClass();
		IType mainType = compilationUnit.getAllTypes()[0];
		classUnderTest.setFullyQualifiedName(mainType.getFullyQualifiedName());
		classUnderTest.setName(mainType.getElementName());
		yModel.setClassUnderTest(classUnderTest);
		
		ConsoleStreamUtil.classToString("Filtered YModels : ",yModel);
		return yModel;
		
		
	}

	/**
	 * The input list of models have one model per testcase and the class under tests are duplicated. 
	 * 
	 * This method just takes the existing list, combines all the testcases under each class under test 
	 * and returns the models
	 * 
	 * @param models
	 * @return
	 */
	private List<YModel> groupModels(List<YModel> models) {

		Map<String,YModel> groupedModels=new HashMap<String,YModel>();
		if (models!=null && models.size()>0){
			
			YModel tempYModel=null;
			for (YModel yModel : models) {
				
				if (groupedModels.containsKey(yModel.getClassUnderTest().getFullyQualifiedName())){
					tempYModel=groupedModels.get(yModel.getClassUnderTest().getFullyQualifiedName());
					tempYModel.addAllTestCase(yModel.getTestCases());
					
				}
				else{
					groupedModels.put(yModel.getClassUnderTest().getFullyQualifiedName(), yModel);
				}
				
			}
			
			models.clear();
			models.addAll(groupedModels.values());
			
			
		}
		
		return models;
		
		
	}


	class ResultSetMapperImpl extends ResultSetMapperBase{

		private ConsoleStreamUtil streamUtil=ConsoleStreamUtil.getInstance();
		private YModel model=null;
		private List<String> allClassNames;
		public ResultSetMapperImpl(List<String> allClassNames) {
			this.allClassNames=allClassNames;
		}
		
		@Override
		public void acceptSearchMatch(SearchMatch eachSearchMatch) throws CoreException {

			model=new YModel();
			
			if (eachSearchMatch.getClass().equals(TypeReferenceMatch.class)){
	
				IJavaElement testCaseElement=(IJavaElement)eachSearchMatch.getElement();
			
				if (testCaseElement.getAncestor(IJavaElement.COMPILATION_UNIT) instanceof ICompilationUnit){
					
					ICompilationUnit testCaseElementCompilationUnit=(ICompilationUnit) testCaseElement.getAncestor(IJavaElement.COMPILATION_UNIT);
					
					mapSearchCompilationUnitToModel(testCaseElementCompilationUnit, model, allClassNames);
					
					
					
				}
				
			}
			streamUtil.println("Adding model...... to models");
			//streamUtil.print("Model record : "+model.toString());
			models.add(model);
		}

		public void mapSearchCompilationUnitToModel(ICompilationUnit testCaseElementCompilationUnit, YModel model, List<String> allClassNames) {
			
			YModelVisitor visitor = new YModelVisitor(model, testCaseElementCompilationUnit, allClassNames);
			
			CompilationUnit comUnit = ParserUtils.parse(testCaseElementCompilationUnit);
			comUnit.accept(visitor);
		}
	
	}

	
}
