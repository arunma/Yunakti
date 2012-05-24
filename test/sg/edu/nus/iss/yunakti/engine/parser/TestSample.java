package sg.edu.nus.iss.yunakti.engine.parser;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import sg.edu.nus.iss.yunakti.engine.EngineCore;
import sg.edu.nus.iss.yunakti.model.YClass;
import sg.edu.nus.iss.yunakti.model.YModel;

public class TestSample {

	@Test
	public void test() {
		
		YModel yModel = mock(YModel.class);
		EngineCore engineCore = mock(EngineCore.class);
		YClass classUnderTest=mock(YClass.class);
		YClass testCase1=mock(YClass.class);
		YClass testCase2=mock(YClass.class);
		
		YClass helper1=mock(YClass.class);
		YClass helper2=mock(YClass.class);
		
		classUnderTest.setFullyQualifiedName("sg.edu.nus.iss.yunakti.ClassUnderTest");
		testCase1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase1");
		testCase2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.TestCase2");
		helper1.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper1");
		helper2.setFullyQualifiedName("sg.edu.nus.iss.yunakti.Helper2");
		
		testCase1.addMember(helper1);
		testCase1.addMember(helper2);
		
		yModel.setClassUnderTest(classUnderTest)
	}

}
