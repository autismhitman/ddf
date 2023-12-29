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
		
		
		Map<String, String> classMethods2 = new DataUtil().loadClassMethods();
		
		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\testconfig.json";
		JSONParser parser = new JSONParser();
		JSONObject jo = (JSONObject) parser.parse(new FileReader(new File(path)));
		String parallelsuites = (String) jo.get("parallelsuites");
		TestNGRunner testRun = new TestNGRunner(Integer.parseInt(parallelsuites));///1
	    JSONArray testsuites = (JSONArray) jo.get("testsuites");
		for( int sID= 0; sID< testsuites.size(); sID++) {
			
			JSONObject testsuite   =  (JSONObject) testsuites.get(sID);
			String runmode =(String) testsuite.get("runmode");
			if (runmode.equals("Y")) {
				
				String name=(String) testsuite.get("name");
				String testdatajsonfile =(String) testsuite.get("testdatajsonfile");
				String suitefilename =(String) testsuite.get("suitefilename");
				String paralleltests =(String) testsuite.get("paralleltests");
				boolean pTests= false;
				 if(paralleltests.equals("Y"))
					 pTests= true;
			 	testRun.createSuite(name, pTests);//2
				
			 //	testRun.addListener("listener.CustomListener");//3
				
				String pathSuite = System.getProperty("user.dir")+"\\src\\test\\resources\\jsons\\"+suitefilename ;
				
				JSONParser suiteparser = new JSONParser();
				JSONObject jSuite = (JSONObject) suiteparser.parse(new FileReader(new File(pathSuite)));
				JSONArray suiteTestcases =  (JSONArray) jSuite.get("testcases");
				for ( int sTId= 0; sTId<suiteTestcases.size(); sTId++) {
					
					 JSONObject suitetestcase   =  (JSONObject) suiteTestcases.get(sTId);
					 String testName = (String) suitetestcase.get("name");
					 JSONArray parameterName = (JSONArray) suitetestcase.get("parameternames");
					 JSONArray executions = (JSONArray)suitetestcase.get("executions") ;
					 
					 for ( int eId= 0; eId< executions.size(); eId++) {
						 JSONObject execution  =  (JSONObject) executions.get(eId);
						// String runmodeTC = (String) execution.get("runmode");
						 String executionname = (String) execution.get("executionname");
						 String dataflag = (String) execution.get("dataflag");
						 JSONArray parametervalues = ( JSONArray) execution.get("parametervalues");
						 JSONArray methods = (JSONArray) execution.get("methods");
						 
						 testRun.addTest(testName +"-" +executionname); //4
						 
						 for( int pid= 0; pid <parameterName.size(); pid++) {
							 testRun.addTestParameter((String)parameterName.get(pid), (String)parametervalues.get(pid) );//5
						 }
						 
							List<String> includedMethods = new ArrayList<String>();;
							 
							 for(int mId=0;mId<methods.size();mId++) {
								 String method = (String)methods.get(mId);
								 String methodClass=classMethods2.get(method);
								 //System.out.println(method +" -- "+ methodClass);
									 if(mId==methods.size()-1 || !((String)classMethods2.get((String)methods.get(mId+1))).equals(methodClass)) {
										 // next method is from different class
										 includedMethods.add(method);
										 testRun.addTestClass(methodClass, includedMethods);
										 includedMethods = new ArrayList<String>();;
									 }else {
										 // same class
										 includedMethods.add(method);
									 }	 
							 }
	
							System.out.println("-----------------------------"); 
							 
		            }
            	 }
				testRun.run();//7
			}
			
			
		}
		


		
	}

}
 
