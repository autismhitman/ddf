package ff.tests;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testBase.BaseTest;

public class PortfolioManagement extends BaseTest {
	
	
	@Test
	public void createPortfolio( ) {
		
		 System.out.println("create Portfolio");
		 test.log(Status.INFO, "create Portfolio");
		//System.out.println(con.getAttribute("i"));
	 
		System.out.println("PM--->" + app.hashCode());
		app.validateElementPresent("login_submit_css");
		app.click("login_submit_css");
	}
	
	
	@Test
	public void deletePortfolio() {
		
		 test.log(Status.INFO, "delete Portfolio" );
		 System.out.println("PM--->" + app.hashCode());
		System.out.println("delete Portfolio");
		app.navigate("url");
		app.reportFailure("Something failed", false);
		app.assertAll();
		
		
	}

}
