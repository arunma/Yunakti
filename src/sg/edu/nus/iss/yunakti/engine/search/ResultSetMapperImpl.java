package sg.edu.nus.iss.yunakti.engine.search;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMemberValuePair;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.TypeReferenceMatch;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import sg.edu.nus.iss.yunakti.engine.util.YConstants;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class ResultSetMapperImpl extends ResultSetMapperBase{

	private YModel model=null;
	
	MessageConsoleStream outputStream=createConsoleStream();
	
	@Override
	public void acceptSearchMatch(SearchMatch eachSearchMatch) throws CoreException {

		outputStream.println(eachSearchMatch.getClass().toString());
		
		if (eachSearchMatch.getClass().equals(TypeReferenceMatch.class)){
			model=new YModel();
			outputStream.println("@@@@@@@@@@@ element class "+eachSearchMatch.getElement().getClass());
			

			outputStream.println("Search Match class ::: "+eachSearchMatch.getClass());
			
			IJavaElement testCaseElement=(IJavaElement)eachSearchMatch.getElement();
			outputStream.println("Java element type : "+testCaseElement.getElementType());
			if (testCaseElement.getElementType()==IJavaElement.TYPE){
				outputStream.println("Yayyyyyyyy. Class file found.... ");
				
				IType testCaseType=(IType)testCaseElement;
				
				outputStream.println("TestCase fully qualified name : "+ testCaseType.getFullyQualifiedName());
				
				YClass testCase=new YClass( testCaseType.getFullyQualifiedName());
				
				IAnnotation annotation = testCaseType.getAnnotation(YConstants.TEST_CASE_ANNOTATION);
				IMemberValuePair[] annotationMemberValuePairs = annotation.getMemberValuePairs();
				
				//TODO Arun - If there are more than one annotation, we are dead here.
				outputStream.print("Annotation Member key "+annotationMemberValuePairs[0].getMemberName());
				outputStream.print("Annotation Member key "+annotationMemberValuePairs[0].getValue());
				
				model.setClassUnderTest(new YClass(annotationMemberValuePairs[0].getValue().toString()));
				
				IField[] testCaseFields = testCaseType.getFields();
				
				for (IField eachTestCaseField : testCaseFields) {
					
				
					String[][] type = eachTestCaseField.getDeclaringType().resolveType(Signature.toString(eachTestCaseField.getTypeSignature()));
					String eachFieldSignature = Signature.toString(eachTestCaseField.getTypeSignature());
					
					outputStream.println("Type array : "+type);
					//if ((type!=null) && (type[0][0].startsWith("java"))){
					if ((type==null) || (type!=null && StringUtils.startsWith(type[0][0],YConstants.JAVA))) { 
						//outputStream.println("\t\t Skipping Field type  because it is inbuilt : " +eachFieldSignature); 
					}
					//TODO Negate the "if" block and insert the following section
					else{
						testCase.getMembers().add(new YClass(Signature.toString(eachTestCaseField.getTypeSignature())));
						outputStream.println("\t\t Field type taken into account : " +eachFieldSignature);
					}
					
					outputStream.println("IField fully qualified name : "+type);
					//String signature = Signature.toString(iField.getTypeSignature());
					outputStream.println("Printing type signature :"+Signature.getElementType(eachTestCaseField.getTypeSignature()));
					outputStream.println("Signature qualifier "+Signature.getSignatureQualifier(eachTestCaseField.getTypeSignature()));
					outputStream.println("Signature simple name "+Signature.getSimpleName(eachTestCaseField.getTypeSignature()));
					outputStream.println("Signature toString  "+Signature.toString(eachTestCaseField.getTypeSignature()));
					
					outputStream.println("\t\t Field name  : " +eachTestCaseField.getElementName());
					outputStream.println("\t\t**********************************************");
				}
				
				model.getTestCases().add(testCase);
			}
			outputStream.println(model.toString());
			
		}
	}
	
	private MessageConsoleStream createConsoleStream() {

		MessageConsole console=new MessageConsole("Annotations Search Resulst", null);
		console.activate();
		ConsolePlugin consolePlugin=ConsolePlugin.getDefault();
		consolePlugin.getConsoleManager().addConsoles(new IConsole[]{console});
		MessageConsoleStream messageStream = console.newMessageStream();
		return messageStream;
	}
}
