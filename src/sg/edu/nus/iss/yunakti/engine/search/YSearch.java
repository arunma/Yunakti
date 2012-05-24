package sg.edu.nus.iss.yunakti.engine.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		ConsoleStreamUtil.println("Returning results +"+models);
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
					
					YModelVisitor visitor = new YModelVisitor(model);
					CompilationUnit comUnit = ParserUtils.parse(testCaseElementCompilationUnit);
					streamUtil.println("Compilation unit is : "+comUnit);
					comUnit.accept(visitor);
					
					streamUtil.println("Fields ready?"+visitor.getFields());
					
					
				}
				
				/*if (testCaseElement.getElementType()==IJavaElement.TYPE){
					
					//ICompilationUnit icompilationUnit=(ICompilationUnit)testCaseElement;
					//streamUtil.print("Yaaaay.. icompilationunit"+icompilationUnit);
					
					IType testCaseType=(IType)testCaseElement;
					YClass testCase=new YClass( testCaseType.getFullyQualifiedName());
					
					String fqnClassUnderTest=getClassUnderTest(testCaseElement.getJavaProject(), testCaseType);
					model.setClassUnderTest(new YClass(fqnClassUnderTest));
					
					gatherHelpers(testCaseType, testCase);
					
					model.getTestCases().add(testCase);
				}
				streamUtil.print("Model :"+model.toString());*/
				
			}
			streamUtil.println("Adding model...... to models");
			//streamUtil.print("Model record : "+model.toString());
			models.add(model);
		}
	
		/*
		private String getClassUnderTest(IJavaProject javaProject, IType testCaseType) throws JavaModelException {
			
			IAnnotation annotation = testCaseType.getAnnotation(YConstants.TEST_CASE_ANNOTATION);
			IMemberValuePair[] annotationMemberValuePairs = annotation.getMemberValuePairs();
			IType classUnderTestType = javaProject.findType(annotationMemberValuePairs[0].getValue().toString());
			return classUnderTestType.getFullyQualifiedName();
			
		}
	
		private void gatherHelpers(IType testCaseType, YClass testCase)	throws JavaModelException {
			IField[] testCaseFields = testCaseType.getFields();
			
			for (IField eachTestCaseField : testCaseFields) {
				
			
				String[][] type = eachTestCaseField.getDeclaringType().resolveType(Signature.toString(eachTestCaseField.getTypeSignature()));
				
				if ((type==null) || (type!=null && StringUtils.startsWith(type[0][0],YConstants.JAVA))) { 
					//streamUtil.print("\t\t Skipping Field type  because it is inbuilt : " +eachFieldSignature); 
				}
				//TODO Negate the "if" block and insert the following section
				else{
					
					String typeSignature = eachTestCaseField.getTypeSignature();
					
					String[][] ss = eachTestCaseField.getDeclaringType().resolveType(Signature.toString(typeSignature));
					String eachTestCaseFieldFQN = ss[0][0]+"."+ss[0][1]; //full path
					
					testCase.getMembers().add(new YClass(eachTestCaseFieldFQN));
				}
				
			}
		}
		*/
	}
}
