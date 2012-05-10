package sg.edu.nus.iss.yunakti.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.core.search.TypeReferenceMatch;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.handlers.HandlerUtil;

public class YunaktiCommand extends AbstractHandler{
	
	

	public YunaktiCommand() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		System.out.println("Execution event fired :::::"+event);
		IStructuredSelection selection=(IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		invokeSearch(selection);
		
		return null;
	}

	protected void invokeSearch(IStructuredSelection selection) {
		
		List<IJavaElement> javaElements = gatherSelectionElements(selection);
			
		
			
		System.out.println("About to search.....");
		SearchPattern pattern=SearchPattern.createPattern("TC", IJavaSearchConstants.ANNOTATION_TYPE, IJavaSearchConstants.ALL_OCCURRENCES, SearchPattern.R_CASE_SENSITIVE);
		System.out.println("Pattern... "+pattern);
		try {
			IJavaElement[] elements;
			new SearchEngine().search(pattern, new SearchParticipant[]{SearchEngine.getDefaultSearchParticipant()},
					SearchEngine.createJavaSearchScope(javaElements.toArray(new IJavaElement[]{})), new DummySearchRequester(), null);
			System.out.println("Search done....");
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
	}

	private List<IJavaElement> gatherSelectionElements(
			IStructuredSelection selection) {
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
	
	private class DummySearchRequester extends SearchRequestor{

		MessageConsoleStream outputStream;
		@Override
		public void beginReporting() {
			outputStream=createConsoleStream();
			outputStream.println("Begin reporting");
			super.beginReporting();
			
		}
		
		@Override
		public void acceptSearchMatch(SearchMatch eachSearchMatch) throws CoreException {
			//eachSearchMatch.
		/*	outputStream.println("Element  :"+ eachSearchMatch.getElement().toString());
			outputStream.println("Class  :"+ eachSearchMatch.getClass().toString());
			outputStream.println("Resource :"+eachSearchMatch.getResource().toString());*/
			//System.out.println(eachSearchMatch.getElement());
			
			//ResolvedSourceType sourceType=(ResolvedSourceType) eachSearchMatch.getElement();
			//System.out.println("Source Type : "+sourceType);
			//System.out.println("Source Type class"+sourceType.getKey().getClass());
			
			//System.out.println("Source Type instance of IJava Element" +(sourceType instanceof IJavaElement));
			
			////IJavaElement javaElement =(IJavaElement) sourceType;
			//System.out.println("Element name "+javaElement.getElementName());
			//System.out.println("Underlying resource" +javaElement.getUnderlyingResource().getClass());
			
			
			
			if (eachSearchMatch.getClass().equals(TypeReferenceMatch.class)){
				outputStream.println("Printing Classes alone  :"+eachSearchMatch.getResource().toString());
				System.out.println("@@@@@@@@@@@ element class "+eachSearchMatch.getElement().getClass());
				
				System.out.println("Search Match class ::: "+eachSearchMatch.getClass());
				
				IJavaElement javaElement=(IJavaElement)eachSearchMatch.getElement();
				System.out.println("Java element type : "+javaElement.getElementType());
				if (javaElement.getElementType()==IJavaElement.TYPE){
					System.out.println("Yayyyyyyyy. Class file found.... ");
					IType iType=(IType)javaElement;
					outputStream.println("Type : "+iType.getElementName());
					
					IField[] fields = iType.getFields();
					for (IField iField : fields) {
						
					
						String[][] type = iField.getDeclaringType().resolveType(Signature.toString(iField.getTypeSignature()));
						String signature = Signature.toString(iField.getTypeSignature());
						
						System.out.println("Type array : "+type);
						if ((type!=null) && (type[0][0].startsWith("java"))){
							outputStream.println("\t\t Skipping Field type  because it is inbuilt : " +signature);
						}
						else{
							outputStream.println("\t\t Field type taken into account : " +signature);
						}
						
						System.out.println("IField fully qualified name : "+type);
						//String signature = Signature.toString(iField.getTypeSignature());
						System.out.println("Printing type signature :"+Signature.getElementType(iField.getTypeSignature()));
						System.out.println("Signature qualifier "+Signature.getSignatureQualifier(iField.getTypeSignature()));
						System.out.println("Signature simple name "+Signature.getSimpleName(iField.getTypeSignature()));
						
						/*if (signature.startsWith("java")){
							outputStream.println("\t\t Skipping Field type  because it is inbuilt : " +signature);
						}
						else{
							outputStream.println("\t\t Field type taken into account : " +signature);	
						}*/
						
						 //+iField.getKey());
						
						 //System.out.println("resolve type" +JavaModelUtil.getResolvedTypeName(iField.getTypeSignature(), iType));
						//System.out.println("Signature "+Signature.toString(iField.getTypeSignature()));
						
						outputStream.println("\t\t Field name  : " +iField.getElementName());
						outputStream.println("\t\t**********************************************");
					}
				}
				outputStream.println("**********************************************************************");
				/*System.out.println(eachSearchMatch.getElement());
				TypeReferenceMatch typeMatch=(TypeReferenceMatch) eachSearchMatch;
				System.out.println("Local element"+typeMatch.getLocalElement());*/
				
				/*ResolvedSourceType sourceType=(ResolvedSourceType) typeMatch.getElement();
				String elementName = sourceType.getElementName();
				Class<?> clazz = null;
				Field[] helperAndVariables = null;
				
				System.out.println("Element Name : "+elementName);
				
				try {
					clazz = Class.forName(elementName);
					helperAndVariables=clazz.getDeclaredFields();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}*/
				
				
				//Field[] helperAndVariables = typeMatch.getClass().getDeclaredFields();
				
				/*for (Field field : helperAndVariables) {
					field.setAccessible(true);
					outputStream.println("\t\t Helpers in Class : "+field.getName());
				}*/
				
				/*System.out.println("Element class : "+typeMatch.getElement().getClass());
				System.out.println(typeMatch.getResource().getClass());
				System.out.println(typeMatch.getLocalElement().getClass());*/
				//System.out.println(typeMatch.getLocalElement().getElementName());
				//System.out.println(typeMatch.getResource().getResourceAttributes());
				//System.out.println("Type Match class;;;;;"+typeMatch.getClass());
				
				
				

				/*Class<?> clazz = null;
				try {
					clazz = Class.forName(typeMatch.getElementName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}*/
				
				
				
				
			}
		}
		
		@Override
		public void endReporting() {
			outputStream.println("End reporting");
			super.endReporting();
		}
		
		private MessageConsoleStream createConsoleStream() {

			MessageConsole console=new MessageConsole("Annotations Search Resulst", null);
			console.activate();
			ConsolePlugin consolePlugin=ConsolePlugin.getDefault();
			consolePlugin.getConsoleManager().addConsoles(new IConsole[]{console});
			MessageConsoleStream messageStream = console.newMessageStream();
			return messageStream;
		}
		
		private void logToConsole(){
			
		}
	}
	

}
