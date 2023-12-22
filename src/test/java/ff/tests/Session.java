package ff.tests;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testBase.BaseTest;

public class Session extends BaseTest {
	
	
	@Test
	public void doLogin( ) {
		
		System.out.println("LOGIN");
		 test.log(Status.INFO, "Login"  );
		 
		//con.setAttribute("i", 100);
		
		System.out.println("login --->"+app.hashCode());
		app.openBrowser("chrome");
		app.navigate("url");
		int i= 100/0;
		app.type("username_id", "standard_user");
		app.type("password_xpath", "secret_sauce");
		app.reportFailure("Will ths stop", true);
	 
	//	
	}

}
