package runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONRunner {
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		
		Map<String, String> classMethods = new DataUtil().loadClassMethods();
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\testconfig.json";
		
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(new FileReader(new File(path)));
		String parallelsuites = (String) jo.get("parallelsuites");
		
	 	CustomTestNGRunner testRun = new CustomTestNGRunner(Integer.parseInt(parallelsuites));
	 	//testRun.addListener("listener.CustomListener");
		
		JSONArray testsuites = (JSONArray) jo.get("testsuites");
		for( int i= 0; i< testsuites.size(); i++) {
			
			JSONObject testsuite   =  (JSONObject) testsuites.get(i);
			String runmode =(String) testsuite.get("runmode");
			if (runmode.equals("Y")) {
				
				String name=(String) testsuite.get("name");
				String testdatajsonfile =(String) testsuite.get("testdatajsonfile");
				String suitefilename =(String) testsuite.get("suitefilename");
				String paralleltests =(String) testsuite.get("paralleltests");
				boolean pTests= false;
				 if(paralleltests.equals("Y"))
					 pTests= true;
			 	testRun.createSuite(name, pTests);
				
				
				
				String pathSuite = System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\"+suitefilename ;
				
				JSONParser suiteparser = new JSONParser();
				JSONObject jSuite = (JSONObject) suiteparser.parse(new FileReader(new File(pathSuite)));
				JSONArray testcases =  (JSONArray) jSuite.get("testcases");
				for ( int k= 0; k<testcases.size(); k++) {
					
					JSONObject testcase   =  (JSONObject) testcases.get(k);
					 String testName = (String) testcase.get("name");
					 JSONArray parameterName = (JSONArray) testcase.get("parameternames");
					 
					 JSONArray executions = (JSONArray)testcase.get("executions") ;
					 
					 for ( int m= 0; m< executions.size(); m++) {
						 JSONObject execution  =  (JSONObject) executions.get(m);
						 String runmodeTC = (String) execution.get("runmode");
						 String executionname = (String) execution.get("executionname");
						 String dataflag = (String) execution.get("dataflag");
						 JSONArray parametervalues = ( JSONArray) execution.get("parametervalues");
						 JSONArray methods = (JSONArray) execution.get("methods");
						 
						 testRun.addTest(testName +"-" +executionname);
						 
						 for( int pid= 0; pid <parameterName.size(); pid++) {
							 testRun.addTestParam((String)parameterName.get(pid), (String)parametervalues.get(pid) );
						 }
						 
						 List<String> includedMethodNames = new ArrayList<>();
						 
						 for( int mId = 0; mId<methods.size(); mId++) {
							 
							 String method = (String)methods.get(mId);
							 String methodClass = classMethods.get(method);
							 System.out.println("Method :"+ method +"--"+ "MethodClass : "+ methodClass);
							 
						 }
						 
					    //  includedMethodNames.add("selectPortfolio");
					   //   testRun.addTestClass("ff.tests.PortfolioManagement",includedMethodNames );
					
					
						  System.out.println(testName +"-" +executionname ) ;
						  System.out.println(parameterName +"-" +parametervalues );
						  System.out.println(methods);
						  System.out.println("---------------------");
					  
					 
				}
				
				
				
				  
			}
			
			
		}
		
		}
		
	}

}
    /*
 	testRun.addTest("Add New Stock Test");
		testRun.addTestParam("action", "addstock" );
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
	*/
