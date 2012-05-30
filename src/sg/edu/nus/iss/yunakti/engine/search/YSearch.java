package sg.edu.nus.iss.yunakti.engine.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.TypeReferenceMatch;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.util.ConsoleStreamUtil;
import sg.edu.nus.iss.yunakti.engine.util.ParserUtils;
import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.ui.explorer.YModelVisitor;

public class YSearch {
	
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
	
	public void search(List<IJavaElement> javaElements){
		
		ResultSetMapperBase resultMapper=new ResultSetMapperImpl();
		System.out.println("About to search.....");
		
		SearchPattern pattern = SearchPattern.createPattern(YConstants.TEST_CASE_ANNOTATION, IJavaSearchConstants.ANNOTATION_TYPE,IJavaSearchConstants.ANNOTATION_TYPE_REFERENCE, SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE);
		//SearchPattern pattern=SearchPattern.createPattern(YConstants.TEST_CASE_ANNOTATION, IJavaSearchConstants.ANNOTATION_TYPE, IJavaSearchConstants.ALL_OCCURRENCES, SearchPattern.R_FULL_MATCH);
		System.out.println("Pattern... "+pattern);
		try {
			new SearchEngine().search(pattern, new SearchParticipant[]{SearchEngine.getDefaultSearchParticipant()},
					SearchEngine.createJavaSearchScope(javaElements.toArray(new IJavaElement[]{})), resultMapper, null);
			System.out.println("Search done....");
			
			
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	

	public List<YModel> getResults() {
		
		models=groupModels(models);
		ConsoleStreamUtil.println("Returning results :"+models);
		return models;
	}


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
		@Override
		public void acceptSearchMatch(SearchMatch eachSearchMatch) throws CoreException {

			model=new YModel();
			
			
			
			if (eachSearchMatch.getClass().equals(TypeReferenceMatch.class)){
	
				IJavaElement testCaseElement=(IJavaElement)eachSearchMatch.getElement();
			
				if (testCaseElement.getAncestor(IJavaElement.COMPILATION_UNIT) instanceof ICompilationUnit){
					
					ICompilationUnit testCaseElementCompilationUnit=(ICompilationUnit) testCaseElement.getAncestor(IJavaElement.COMPILATION_UNIT);
					
					streamUtil.println("Yaaaay.. icompilationunit"+testCaseElementCompilationUnit);
					
					YModelVisitor visitor = new YModelVisitor(model, testCaseElementCompilationUnit);
					
					CompilationUnit comUnit = ParserUtils.parse(testCaseElementCompilationUnit);
					streamUtil.println("Compilation unit is : "+comUnit);
					comUnit.accept(visitor);
					
					streamUtil.println("Fields ready?"+visitor.getFields());
					
					
				}
				
			}
			streamUtil.println("Adding model...... to models");
			//streamUtil.print("Model record : "+model.toString());
			models.add(model);
		}
	
	}
}
