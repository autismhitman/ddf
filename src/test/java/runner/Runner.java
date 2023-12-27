package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {
	
	
	CustomTestNGRunner testRun = new CustomTestNGRunner(1);
	
	
	
	
	
	 


}


/*
  testRun.createSuite("Suite Stocks", false);
 testRun.addListener("listener.CustomListener");
testRun.addTest("Test1");
testRun.addTestParam("actions", "Neo1" );
testRun.addTestParam("actions1", "Neo2" );
List<String> includedMethodNames = new ArrayList<>();
  includedMethodNames.add("testA");
  includedMethodNames.add("testB");
testRun.addTestClass("pack1.TestClass1",includedMethodNames );
testRun.run();

*/