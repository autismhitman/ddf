package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {
	
	
	public static void main(String[] args) {
		
		
		TestNGRunner testRun = new TestNGRunner(1);
		testRun.createSuite("Suite Stocks", false);
		testRun.addListener("listener.CustomListener");
		testRun.addTest("Add New Stock Test");
		//testRun.addTestParam("action", "addstock" );
		List<String> includedMethodNames = new ArrayList<>();
		      includedMethodNames.add("selectPortfolio");
		testRun.addTestClass("ff.tests.PortfolioManagement",includedMethodNames );
		
		List<String> includedMethodNames1 = new ArrayList<>();
	      includedMethodNames1.add("addNewStock");
	      includedMethodNames1.add("verifyStockPresent");
	      includedMethodNames1.add("verifyStockQuantity");
	      includedMethodNames1.add("verifyTrasactionHistory");
		testRun.addTestClass("ff.tests.StockManagement",includedMethodNames1 );
		
		testRun.run();
		
		
		
		
	}
	

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