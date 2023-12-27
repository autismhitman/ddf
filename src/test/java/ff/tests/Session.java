package ff.tests;

import org.openqa.selenium.By;
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
     	app.navigate("url");
		app.type("username_id","sample.sharma");
		app.type("password_id","sample.sharma");
		app.clear("login_submit_id");
	 
	//	
	}

}
