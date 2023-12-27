package keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import reports.ExtentManager;

public class GenericKeywords {

	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert sa;

	public WebDriver openBrowser(String browserName) {
		log("Starting the borwser :" + browserName);
		
		if(prop.get("grid_run").equals("Y")) {
			
			DesiredCapabilities cap=new DesiredCapabilities();
			if(browserName.equals("Mozilla")){
				
				cap.setBrowserName("firefox");
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(browserName.equals("chrome")){
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				// hit the hub
				driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
				  
			} catch (Exception e) {
			  e.printStackTrace();
			}
			
		}
		
		
		
		else {
			
			if (browserName.equals("chrome")) {
		 

			ChromeOptions co = new ChromeOptions();
			co.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			List<String> options = new ArrayList<>();
			options.add("--disable-extensions");
			options.add("start-maximized");
			options.add("--disable-notifications");
			options.add("--disable-geolocation");
			options.add("--disable-media-stream");
			options.add("--remote-allow-origins=*");
			options.add("ignore-certificate-errors");
			// options.add("--headless=new");
			// options.add("unsafely-treat-insecure-origin-as-secure");
			options.add("--incognito");
			options.add("--disable-dev-shm-usage");
			options.add("--disable-application-cache");
			options.add("--disable-gpu");
			options.add("--no-sandbox");
			options.add("--disable-gpu");

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(co.addArguments(options));
		}

		else if (browserName.equals("ff")) {

			//System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, ".\\logs\\flog.log");

			FirefoxOptions ops = new FirefoxOptions();

			// ops.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			ProfilesIni allprof = new ProfilesIni();
			FirefoxProfile fp = allprof.getProfile("Nov2023");
			fp.setPreference("dom.webnotifications.enabled", false);

			// ssl certificates
			fp.setAcceptUntrustedCertificates(true);
			fp.setAssumeUntrustedCertificateIssuer(false);
			/*
			 * //proxy settings fp.setPreference("network.proxy.type", 1);
			 * fp.setPreference("network.proxy.socks", "83.778.87.11");
			 * fp.setPreference("network.proxy.socks_port", 1827);
			 */
			ops.setProfile(fp);
			ops.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(ops);
			}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		return driver;
	}

	public void navigate(String urlKey) {

		log("Navigating to " + envProp.getProperty(urlKey));
		driver.get(envProp.getProperty(urlKey));
	}

	public void click(String locatorkey) {

		log("Clicking on  " + locatorkey);
		getElement(locatorkey).click();
	}
	
	 public void clickEnterButton(String locatorkey) {
		 
		   log("Clicking on  " + locatorkey);
		   getElement(locatorkey).sendKeys(Keys.ENTER);
		}

	public void type(String locatorkey, String data) {

		log("Typing on  " + locatorkey + "data: " + data);
		getElement(locatorkey).sendKeys(data);
	}

	public void clear(String locatorkey) {
		log("Clearing text on " + locatorkey);
		getElement(locatorkey).clear();
		;
	}

	public void select(String locatorkey, String data) {

		log("Selecting " + locatorkey + "data: " + data);
		getElement(locatorkey).sendKeys(data);
	}

 
	
	public String getText(String locatorKey) {
		log("Getting the text of the element " + locatorKey);
		return getElement(locatorKey).getText();
	}

	public WebElement getElement(String locatorkey) {

		if (!isElementPresent(locatorkey)) {
			System.out.println("Element not present :" + locatorkey);
		}

		if (!isElementVisible(locatorkey)) {
			System.out.println("Element not visible :" + locatorkey);
		}

		return driver.findElement(getLocator(locatorkey));

	}

	public boolean isElementPresent(String locatorkey) {

		log("Presence of element :" + locatorkey);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {

			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorkey)));

		} catch (Exception e) {
			screenshot();
			log("Element not present :" + locatorkey);
			return false;
		}

		return true;

	}

	public boolean isElementVisible(String locatorkey) {

		log("Visibility  of element :" + locatorkey);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorkey)));
		} catch (Exception e) {
			screenshot();
			log("Element not visible:" + locatorkey);
			return false;
		}

		return true;

	}

	public By getLocator(String locatorkey) {

		By by = null;

		if (locatorkey.endsWith("_id")) {
			by = By.id(prop.getProperty(locatorkey));
		} else if (locatorkey.endsWith("_xpath")) {
			by = By.xpath(prop.getProperty(locatorkey));
		} else if (locatorkey.endsWith("_css")) {
			by = By.cssSelector(prop.getProperty(locatorkey));
		} else if (locatorkey.endsWith("_name")) {
			by = By.name(prop.getProperty(locatorkey));
		}
		return by;

	}
    
	
	public void selectByVisibleText(String locatorKey, String portfolioName) {
		 
		Select s = new Select(getElement(locatorKey));
		s.selectByVisibleText(portfolioName);
	}
	
	public void acceptAlert() {
	 
		test.log(Status.INFO, "Switching to alert");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted successfully");
		}catch(Exception e){
				reportFailure("Alert not found when mandatory",true);
		}
	}
	
	public void quit() {

		driver.quit();
	}

	public void log(String msg) {

		test.log(Status.INFO, msg);
	}

	public void reportFailure(String failMsg, boolean isCritical) {

		test.log(Status.FAIL, failMsg);
		screenshot();
		sa.fail(failMsg);

		if (isCritical) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
			assertAll();
		}
	}

	public void assertAll() {

		sa.assertAll();
	}

	public void screenshot() {

		Date d = new Date();
		String screenshotFile = d.toString().replaceAll(" ", "_").replaceAll(":", "_") + ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {

			FileUtils.copyFile(src, new File(ExtentManager.screenshotPath + "//" + screenshotFile));
			test.log(Status.INFO, "screenshot-> "
					+ test.addScreenCaptureFromPath(ExtentManager.screenshotPath + "//" + screenshotFile));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void waitForPageLoad() {

		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		// ajax status
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}
	
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public int getRowNumWithCellData(String tableLocator, String data) {
		 
		 WebElement table = driver.findElement(getLocator(tableLocator));
		 
		 List<WebElement> rows = table.findElements(By.tagName("tr"));
		 for( int rNum= 0; rNum<rows.size();rNum++) {
			 
			 WebElement row = rows.get(rNum);
			 List<WebElement> cells = row.findElements(By.tagName("td"));
			 for( int cNum = 0; cNum<cells.size(); cNum++) {
				 
				 WebElement cell = cells.get(cNum);
				 System.out.println("Text " + cell.getText());
				 
				 if(!cell.getText().trim().equals(""))
				    if(data.startsWith(cell.getText()))
				    	return (rNum+1);
				 
			 }
			 
			 
		 }
		 
		 
		 return -1;
	 }
	
	

//
}
