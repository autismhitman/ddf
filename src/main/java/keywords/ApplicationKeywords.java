package keywords;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
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

	
	public void defaultLogin() {
		navigate("url");
		type("username_id", envProp.getProperty("user_name"));
		type("password_id", envProp.getProperty("user_password"));
		click("login_submit_id");
		waitForPageLoad();
		 
		
	}

	public int findCurrentStockQuantity(String companyName) {
		log("Finding current stock quantity for "+ companyName);
		
		int row =getRowNumWithCellData("table_stock_css", companyName);
		if (row==-1) {
			
			log("Current stock quantity is 0 as stock is not present in the list");
			return 0;
		}
		
		String quantity = driver.findElement(By.cssSelector(prop.getProperty("table_stock_css")+" > tr:nth-child("+row+") > td:nth-child(4)")).getText();
		log("current stock quantiry " + quantity);
		
		return Integer.parseInt(quantity);
	}

	public void selectDateFromCalendar(String selectionDate) {
	  log("selecting Date" +  selectionDate);
		try {
    	 	Date currentDate = new Date();
    	    Date dateTosel = new SimpleDateFormat("d-MM-yyyy").parse(selectionDate);
    	 	String day= new SimpleDateFormat("d").format(dateTosel);
    		String month= new SimpleDateFormat("MMMM").format(dateTosel);
    		String year= new SimpleDateFormat("yyyy").format(dateTosel);
    		String monthYearToBeselected = month+" "+ year;
    		
    		String monthYearDisplayed=getElement("monthyear_css").getText();
		 
		    while(!monthYearToBeselected.equals(monthYearDisplayed)) {
		    	
		    	click("datefwdbutton_xpath");
		    	monthYearDisplayed=getElement("monthyear_css").getText();
		    }
		    
		    driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
	 
			} catch (ParseException e) {
		 
				e.printStackTrace();
				}
		
	}

	public void goToTransactionHistory(String companyName) {
		  log("Selecting the company row "+companyName );
			int row = getRowNumWithCellData("table_stock_css", companyName);
			if(row==-1) {
				log("Stock not present in list");
				// report failure
			}
			driver.findElement(By.cssSelector(prop.getProperty("table_stock_css")+" > tr:nth-child("+row+") >td:nth-child(1)")).click();
			driver.findElement(By.cssSelector(prop.getProperty("table_stock_css")+"  tr:nth-child("+row+") input.equityTransaction" )).click();
		
	}

	public void goToBuySell(String companyName) {
		log("Selecting the company row "+companyName );
		int row = getRowNumWithCellData("stocktable_css", companyName);
		if(row==-1) {
			log("Stock not present in list");
		}
		driver.findElement(By.cssSelector(prop.getProperty("table_stock_css")+" > tr:nth-child("+row+") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector(prop.getProperty("table_stock_css")+"  tr:nth-child("+row+") input.buySell" )).click();
		
	}
	

	

	

}
