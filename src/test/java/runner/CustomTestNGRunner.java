package runner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

public class CustomTestNGRunner {
	
    TestNG myTestNG ;
	
	XmlSuite suite;
	List<XmlSuite> mySuites;
	
	XmlTest test;
	List<XmlTest> suitetestCases;
	
	List<XmlClass> testclasses ;
	
	Map<String, String> testngParams;
	
	
	
	
	public CustomTestNGRunner(int threadPoolSize) {
		
		mySuites = new ArrayList<XmlSuite>();
		myTestNG = new TestNG();
		myTestNG.setSuiteThreadPoolSize(threadPoolSize);
		myTestNG.setXmlSuites(mySuites);
		
	}
    
	
	public void createSuite(String suiteName, boolean parallelTests) {
		
		suite = new XmlSuite();
		suite.setName(suiteName);
		
		if(parallelTests) {
			suite.setParallel(ParallelMode.TESTS);
		}
		
		mySuites.add(suite);
		suitetestCases = new ArrayList<XmlTest>();
		
	}
	
	public void addTest(String name) {
		
		test =  new XmlTest(suite);
		test.setName(name);
		testngParams = new HashMap<>();
		test.setParameters(testngParams);
		testclasses = new  ArrayList<XmlClass>();
		test.setXmlClasses(testclasses);
		suitetestCases.add(test);
		
	}
	
	
	public void addTestParam(String key, String value) {
		
		test.addParameter(key, value);
	}
	
	public void addTestClass(String fullClassName, List<String> includeMethodNames) {
		
		 XmlClass testclass = new XmlClass(fullClassName);
		 
		 List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
		 int order= 1;
		 for( String name : includeMethodNames) {
			 
			
			 includedMethods.add(new XmlInclude(name, order));
			 order++;
		 }
		 
		 testclass.setIncludedMethods(includedMethods );
		 testclasses.add(testclass);
		
	}
	
	public void addListener(String listenerFile) {
		suite.addListener("listener.CustomListener");
	}
	
	public void run() {
		
		myTestNG.run();
	}
	
}
