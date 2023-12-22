package testBase;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.App;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;

public class BaseTest {
	
	 
	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext con) {
		System.out.println("---------B4 test--------");
		 
		app = new ApplicationKeywords();
		con.setAttribute("app", app);
		
		rep = ExtentManager.getReports();
		con.setAttribute("rep", rep);
		test = rep.createTest(con.getCurrentXmlTest().getName());
		con.setAttribute("test", test);
		
		test.log(Status.INFO, "Starting Test : "+ con.getCurrentXmlTest().getName() );
		app.setReport(test);
		
		
	}     
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(ITestContext con) {
		System.out.println("---------B4 method--------");
		test = (ExtentTest) con.getAttribute("test");
		
		String criticalError = (String) con.getAttribute("CriticalFailure");
		
			if(criticalError!=null && criticalError.equals("Y")) {
				
				test.log(Status.SKIP, "Critical Error occured in previous test method");
				throw new SkipException("Critical Error occured in previous test method");
				
			}
		
		app = (ApplicationKeywords) con.getAttribute("app");
		rep = (ExtentReports) con.getAttribute("rep");
		
	}
	
	
	
	@AfterTest
	public void afterTest() {
		
		
		if(rep!=null)
		   rep.flush();
		
	}
}
