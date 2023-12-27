package ff.tests;

import org.openqa.selenium.By;
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
		 System.out.println("create Portfolio--->" + app.hashCode());
		 
		    String portfolioName= "2";
			app.click("portfolio_create_css");
			app.clear("portfolioText_css");
			app.type("portfolioText_css", portfolioName);
			app.click("portfolio_createButton_css");
			app.waitForPageLoad();
		    app.validateSelectedValueInDropDown("portfolio_ddl_css", portfolioName);
		 
	}
	
	
	@Test
	public void deletePortfolio() {
		
		 test.log(Status.INFO, "delete Portfolio" );
		 System.out.println("delete Portfolio--->" + app.hashCode());
		 System.out.println("delete Portfolio");
	     String portfolioName= "sam1";
		 app.selectByVisibleText("portfolio_ddl_css",portfolioName);
		 app.waitForPageLoad();
		 app.click("delete_portfolio_css");
		 app.acceptAlert();
		 app.waitForPageLoad();
		 app.validateSelectedValueNotInDropDown("portfolio_ddl_css", portfolioName);
 	
	}
	
	
	@Test
	public void selectPortfolio() {
		
		 test.log(Status.INFO, "Select Portfolio" );
		 System.out.println("Select Portfolio--->" + app.hashCode());
		 System.out.println("select Portfolio");
		  
		 app.selectByVisibleText("portfolio_ddl_css", "CAT");
	     app.waitForPageLoad();
	}

}
