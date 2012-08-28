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

	public List<YModel> getResults() {
		
		models=groupModels(models);
		ConsoleStreamUtil.println("Returning results :"+models);
		return models;
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
					
					YModelVisitor visitor = new YModelVisitor(model, testCaseElementCompilationUnit, allClassNames);
					
					CompilationUnit comUnit = ParserUtils.parse(testCaseElementCompilationUnit);
					comUnit.accept(visitor);
					
					
					
				}
				
			}
			streamUtil.println("Adding model...... to models");
			//streamUtil.print("Model record : "+model.toString());
			models.add(model);
		}
	
	}
}
