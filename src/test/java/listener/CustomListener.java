package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class CustomListener implements ITestListener{

	@Override
	public void onTestSuccess(ITestResult result) {
		
		ExtentTest test  = (ExtentTest) result.getTestContext().getAttribute("test");
		test.log(Status.PASS, result.getName()+"--Test Passed");
	 
	}

	
	
	@Override
	public void onTestFailure(ITestResult result) {
		
		ExtentTest test  = (ExtentTest) result.getTestContext().getAttribute("test");
		test.log(Status.FAIL,  result.getName() + "--" + result.getThrowable().getMessage());
		//Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
	}
	
	

	@Override
	public void onTestSkipped(ITestResult result) {
		
		ExtentTest test  = (ExtentTest) result.getTestContext().getAttribute("test");
		test.log(Status.SKIP, result.getName()+"--Test Skipped");
	}

	
	
	
}
