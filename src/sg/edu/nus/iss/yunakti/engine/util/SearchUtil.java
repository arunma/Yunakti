package sg.edu.nus.iss.yunakti.engine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jface.viewers.IStructuredSelection;

import sg.edu.nus.iss.yunakti.engine.search.ResultSetMapperBase;

public class SearchUtil {
	
	public static List<IJavaElement> gatherAllSearchElementsFromSelection(IStructuredSelection selection) {
		
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
	
	public static void search(List<IJavaElement> javaElements, ResultSetMapperBase resultMapper){
		
		
		System.out.println("About to search.....");
		SearchPattern pattern=SearchPattern.createPattern(YConstants.TEST_CASE_ANNOTATION, IJavaSearchConstants.ANNOTATION_TYPE, IJavaSearchConstants.ALL_OCCURRENCES, SearchPattern.R_CASE_SENSITIVE);
		System.out.println("Pattern... "+pattern);
		try {
			IJavaElement[] elements;
			new SearchEngine().search(pattern, new SearchParticipant[]{SearchEngine.getDefaultSearchParticipant()},
					SearchEngine.createJavaSearchScope(javaElements.toArray(new IJavaElement[]{})), resultMapper, null);
			System.out.println("Search done....");
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
