package sg.edu.nus.iss.yunakti.engine.parser;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class TestEngineCore {

	@Test
	public void test() {
	
		YModel yModel=constructYModel();
		EngineCore engineCore = new EngineCore();
		List<YModel> models=mock(List.class);
		models.add(constructYModel());
		models.add(constructYModel());//duplicate
		
		//verify (engineCore).getUniqueTestCases(models);
		
		assertEquals(engineCore.getUniqueTestCases(models).size(),2);
	}

	
	private YModel constructYModel() {
		
		YModel yModel = mock(YModel.class);
		
		YClass classUnderTest=mock(YClass.class);
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
		YClass testCase1=mock(YClass.class);
		testCase1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase1");
		
		YClass helper1=mock(YClass.class);
		YClass helper2=mock(YClass.class);
		helper1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper1");
		helper2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper2");
		testCase1.addMember(helper1);
		testCase1.addMember(helper2);
		
		return testCase1;
	}

	
	private YClass mockTestCase2() {
		
		YClass testCase2=mock(YClass.class);
		testCase2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase2");
		
		YClass helper1=mock(YClass.class);
		YClass helper2=mock(YClass.class);
		helper1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper1");
		helper2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper2");
		testCase2.addMember(helper1);
		testCase2.addMember(helper1);//adding duplicate
		testCase2.addMember(helper2);
		
		return testCase2;
	}
	
}
