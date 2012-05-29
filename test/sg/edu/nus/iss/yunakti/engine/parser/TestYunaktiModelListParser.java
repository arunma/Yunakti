package sg.edu.nus.iss.yunakti.engine.parser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sg.edu.nus.iss.yunakti.engine.util.YModelListParser;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;
import sg.edu.nus.iss.yunakti.model.YTYPE;
import junit.framework.TestCase;

public class TestYunaktiModelListParser extends TestCase{
	/*
	List<YModel> lstModelClass;
	protected void setUp() throws Exception {
		super.setUp();
		lstModelClass = new ArrayList<YModel>();
		List<YClass> lstTestclasses1=new ArrayList<YClass>();
		List<YClass> lstTestclasses2=new ArrayList<YClass>();
		YClass tstcls1 = new YClass();
		tstcls1.setFullyQualifiedName("Test1.class");
		YClass tstcls2 = new YClass();
		tstcls2.setFullyQualifiedName("Test2.class");
		YClass tstcls3 = new YClass();
		tstcls3.setFullyQualifiedName("Test3.class");
		YClass tstcls4 = new YClass();
		tstcls4.setFullyQualifiedName("Test4.class");
		YClass tstcls5 = new YClass();
		tstcls5.setFullyQualifiedName("Test5.class");
		List<YClass> lstHelperClass=new ArrayList<YClass>();
		YClass cls1 = new YClass();
		cls1.setFullyQualifiedName("Helper1.class");
		cls1.setyClassType(YTYPE.TEST_HELPER);
		YClass cls2 = new YClass();
		cls2.setFullyQualifiedName("Helper2.class");
		cls2.setyClassType(YTYPE.TEST_HELPER);
		YClass cls3 = new YClass();
		cls3.setFullyQualifiedName("Helper3.class");
		cls3.setyClassType(YTYPE.TEST_HELPER);
		YClass cls4 = new YClass();
		cls4.setFullyQualifiedName("Helper4.class");
		cls4.setyClassType(YTYPE.TEST_HELPER);
		YClass cls5 = new YClass();
		cls5.setFullyQualifiedName("Helper5.class");
		cls5.setyClassType(YTYPE.TEST_HELPER);
		lstHelperClass.add(cls1);
		
		lstHelperClass.add(cls2);	
		tstcls1.addMember(cls1);
		tstcls1.addMember(cls2);
		List<YClass> lstHelperClass1=new ArrayList<YClass>();
		lstHelperClass1.add(cls3);
		lstHelperClass1.add(cls4);
		lstHelperClass1.add(cls5);
		tstcls2.addMember(cls3);
		tstcls2.addMember(cls4);
		tstcls2.addMember(cls5);
		tstcls3.addMember(cls1);
		tstcls3.addMember(cls2);
		tstcls4.addMember(cls3);
		tstcls4.addMember(cls4);
		tstcls4.addMember(cls5);
		tstcls5.addMember(cls3);
		tstcls5.addMember(cls4);
		tstcls5.addMember(cls5);
		lstTestclasses1.add(tstcls1);
		lstTestclasses1.add(tstcls2);
		lstTestclasses2.add(tstcls3);
		lstTestclasses2.add(tstcls4);
		lstTestclasses2.add(tstcls5);
		YClass modelcls1 = new YClass();
		modelcls1.setFullyQualifiedName("Class1.class");
		YClass modelcls2 = new YClass();
		modelcls2.setFullyQualifiedName("Class2.class");
		YModel model1=new YModel();
		model1.setClassUnderTest(modelcls1);
		model1.addTestCase(tstcls1);
		model1.addTestCase(tstcls2);
		YModel model2=new YModel();
		model2.setClassUnderTest(modelcls2);
		model2.addTestCase(tstcls3);
		model2.addTestCase(tstcls4);
		model2.addTestCase(tstcls5);
		lstModelClass.add(model1);
		lstModelClass.add(model2);
	}
	@Test
	public void testparseListYModelToString()
	{
		YModelListParser yModellistparser=new YModelListParser();
		List<StringBuilder>lstParsedString=yModellistparser.parseListYModelToString(lstModelClass);
		StringBuilder sbline1=lstParsedString.get(0);
		System.out.println("line1:"+sbline1.toString());
		assertEquals("Class1.class;Test1.class,Test2.class;Helper2.class,Helper1.class,Helper4.class,Helper3.class,Helper5.class", sbline1.toString());
		StringBuilder sbline2=lstParsedString.get(1);
		System.out.println("line2:"+sbline2.toString());
		assertEquals("Class2.class;Test3.class,Test4.class,Test5.class;Helper2.class,Helper1.class,Helper4.class,Helper3.class,Helper5.class,Helper4.class,Helper3.class,Helper5.class", sbline2.toString());
		
		
	}
	*/
}
