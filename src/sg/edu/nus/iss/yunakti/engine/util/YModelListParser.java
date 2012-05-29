package sg.edu.nus.iss.yunakti.engine.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;

public class YModelListParser {
	
	public List<StringBuilder> parseListYModelToString(List<YModel> lstModel)
	{
		
		List<StringBuilder> lstYModelString=new ArrayList<StringBuilder>();
		char colseperator=';';
		char fieldseperator=',';
		for(YModel ymodel:lstModel)
		{
			StringBuilder out = new StringBuilder(); 
			StringBuilder helperout=new StringBuilder();
			out.append(ymodel.getClassUnderTest().getFullyQualifiedName());
			out.append(colseperator);
			List<YClass> lstTestclasses=(List<YClass>)ymodel.getTestCases();
			int i=0;
			int j=0;
		for(YClass yclass:lstTestclasses)
		{
			if(i>0)
			{
				out.append(fieldseperator);
			}
			out.append(yclass.getFullyQualifiedName());
			i++;
			HashSet<YClass> lstHelperclass=new HashSet<YClass>();
			lstHelperclass=(HashSet<YClass>)yclass.getMembers();
			System.out.println("lstHelper"+lstHelperclass.size());
			
			for(YClass helperfortest:lstHelperclass)
			{
				//System.out.println("helperfortest"+helperfortest.getyClassType().name().toString());
				if(j>0)
				{
					helperout.append(fieldseperator);
				}
				helperout.append(helperfortest.getFullyQualifiedName());
				
				System.out.println("helperout inside"+helperout);
				/*if(helperfortest.getyClassType().name().equals(YTYPE.TEST_HELPER.toString()))
				{
					helperout.append(helperfortest.getFullyQualifiedName());
					helperout.append(fieldseperator);
					System.out.println("helperout inside"+helperout);
				}*/
				j++;
			}
		}
		System.out.println("helperout"+helperout);
		out.append(colseperator);
		out.append(helperout);
		
		lstYModelString.add(out);
		}
		//System.out.println("lstYModelString"+lstYModelString);
		return lstYModelString;
	}

}
