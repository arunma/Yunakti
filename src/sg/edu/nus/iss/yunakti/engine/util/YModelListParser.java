package sg.edu.nus.iss.yunakti.engine.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ISelection;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class YModelListParser {
	
	public static List<StringBuilder> parseListYModelToString(List<YModel> lstModel)
	{
		
		List<StringBuilder> lstYModelString=new ArrayList<StringBuilder>();
		
		for(YModel ymodel:lstModel)
		{
			StringBuilder out = new StringBuilder(); 
			StringBuilder helperout=new StringBuilder();
			out.append(ymodel.getClassUnderTest().getFullyQualifiedName());
			out.append(YConstants.COLON);
			List<YClass> lstTestclasses=(List<YClass>)ymodel.getTestCases();
			int i=0;
			int j=0;
		for(YClass yclass:lstTestclasses)
		{
			if(i>0)
			{
				out.append(YConstants.COMMA);
			}
			out.append(yclass.getFullyQualifiedName());
			i++;
			HashSet<YClass> lstHelperclass=new HashSet<YClass>();
			lstHelperclass=(HashSet<YClass>)yclass.getMembers();
			
			
			for(YClass helperfortest:lstHelperclass)
			{
				
				if(j>0)
				{
					helperout.append(YConstants.COMMA);
				}
				helperout.append(helperfortest.getFullyQualifiedName());
				
				
				/*if(helperfortest.getyClassType().name().equals(YTYPE.TEST_HELPER.toString()))
				{
					helperout.append(helperfortest.getFullyQualifiedName());
					helperout.append(YConstants.COMMA);
					System.out.println("helperout inside"+helperout);
				}*/
				j++;
			}
		}
		
		out.append(YConstants.COLON);
		out.append(helperout);
		
		lstYModelString.add(out);
		}
		
		return lstYModelString;
	}
	
	public static boolean findwhetherToPopulateModel(ISelection selection) {
		boolean isValid = false;
		try {
		
			String selectiontype = ((IStructuredSelection) selection)
					.getFirstElement().getClass().getName();
			List<String> lstAllowedNames = new ArrayList<String>();
			lstAllowedNames
					.add(YConstants.JAVA_PACKAGE);
			lstAllowedNames
					.add(YConstants.JAVA_PROJECT);
			lstAllowedNames.add(YConstants.JAVA_PACKAGEROOT);
			lstAllowedNames
					.add(YConstants.JAVA_COMPILATIONUNIT);
			if (selectiontype != null
					&& lstAllowedNames.contains(selectiontype)) {
				isValid=true;
			}
		} catch (Exception e) {

		}
		return isValid;
	}

}
