package sg.edu.nus.iss.yunakti.engine.util;

import java.util.ArrayList;
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
		for(YClass yclass:lstTestclasses)
		{
			out.append(yclass.getFullyQualifiedName());
			out.append(fieldseperator);
			List<YClass> lstHelperclass=new ArrayList<YClass>();
			lstHelperclass=(List<YClass>)yclass.getMembers();
			System.out.println("lstHelper"+lstHelperclass.size());
			for(YClass helperfortest:lstHelperclass)
			{
				System.out.println("helperfortest"+helperfortest.getyClassType().name().toString());
				if(helperfortest.getyClassType().name().equals(YTYPE.TEST_HELPER.toString()))
				{
					helperout.append(helperfortest.getFullyQualifiedName());
					helperout.append(fieldseperator);
					System.out.println("helperout inside"+helperout);
				}
				
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
