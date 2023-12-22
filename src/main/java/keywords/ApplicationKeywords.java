package keywords;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords{
	
	
	public ApplicationKeywords()  {
		
	 
		String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		
			prop = new Properties();
			envProp = new Properties();
			
			try {
				
				FileInputStream fis = new FileInputStream(path);
				prop.load(fis);
				String env = prop.getProperty("env")+".properties";
				
				path = System.getProperty("user.dir")+"//src//test//resources//"+env;
				fis = new FileInputStream(path);
				envProp.load(fis);
			
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			
		   sa = new SoftAssert();

	}

	public void setReport(ExtentTest test) {
		this.test = test;
		
	}

}
