package validationtests;

import org.testng.annotations.Test;

import keywords.ApplicationKeywords;

public class PortfolioValidationTest {
	
	
	@Test
	public void createPortfolioTest() throws InterruptedException {
		
		ApplicationKeywords app = new ApplicationKeywords();
		app.openBrowser("chrome");
		app.navigate("url");
		app.type("username_id", "standard_user");
		app.type("password_xpath", "secret_sauce");
		app.validateElementPresent("login_submit_css");
		app.click("login_submit_css");
		app.quit();
	}

}
