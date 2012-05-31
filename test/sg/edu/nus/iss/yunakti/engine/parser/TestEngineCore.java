package sg.edu.nus.iss.yunakti.engine.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class TestEngineCore {

	//@Test
	public void testUniqueTestCases() {
	
		EngineCore engineCore = new EngineCore();
		List<YModel> models=new ArrayList<YModel>();
		models.add(constructYModel());
		models.add(constructYModel());//duplicate
		
		System.out.println("Models : "+ models);
		assertEquals(engineCore.getUniqueTestCases(models).size(),2);
	}

	

	//@Test
	public void testGetModelsByPackageName() {
	
		EngineCore engineCore = new EngineCore();
		List<YModel> models=new ArrayList<YModel>();
		models.add(constructYModel());
		models.add(constructYModel());//duplicate
		
		System.out.println("Models : "+ models);
		//assertEquals(engineCore.getModelsByPackageName(models).size(),2);
		
		System.out.println(engineCore.getModelsByPackageName(models));
	}
	
	@Test
	public void testWriteAnnotation() {
		
		EngineCore engineCore = new EngineCore();
		YModel yModel=constructYModel();
		
		//assertEquals(engineCore.getModelsByPackageName(models).size(),2);
		
		engineCore.writeAnnotation(yModel);
		
		System.out.println(yModel);
	}
	
	private YModel constructYModel() {
		
		YModel yModel = new YModel();
		
		YClass classUnderTest=new YClass();
		classUnderTest.setFullyQualifiedName("sg.edu.nus.iss.yunakti.ClassUnderTest");
		
		YClass testCase1=mockTestCase1();
		YClass testCase2=mockTestCase2();
		
		yModel.setClassUnderTest(classUnderTest);
		yModel.addTestCase(testCase1);
		yModel.addTestCase(testCase1);//adding duplicate
		yModel.addTestCase(testCase2);
		
		return yModel;
	}


	private YClass mockTestCase1() {
		YClass testCase1=new YClass();;
		testCase1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase1");
		testCase1.setPath("/Users/spark/Development/projects/Research/Yunakti/test/sg/edu/nus/iss/yunakti/TestCase1.java");
		YClass helper1=new YClass();;
		YClass helper2=new YClass();;
		helper1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper1");
		helper2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper2");
		testCase1.addMember(helper1);
		testCase1.addMember(helper2);
		
		return testCase1;
	}

	
	private YClass mockTestCase2() {
		
		YClass testCase2=new YClass();;
		testCase2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase2");
		
		YClass helper1=new YClass();;
		YClass helper2=new YClass();;
		helper1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper1");
		helper2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper2");
		testCase2.addMember(helper1);
		testCase2.addMember(helper1);//adding duplicate
		testCase2.addMember(helper2);
		
		return testCase2;
	}
	
}
