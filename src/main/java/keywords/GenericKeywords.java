package keywords;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

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
			//options.add("unsafely-treat-insecure-origin-as-secure");
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

			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, ".\\logs\\flog.log");

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

	public void type(String locatorkey, String data) {

		log("Typing on  " + locatorkey + "data: " + data);
		getElement(locatorkey).sendKeys(data);
	}

	public void select(String locatorkey, String data) {

		log("Selecting " + locatorkey + "data: " + data);
		getElement(locatorkey).sendKeys(data);
	}

	public void getText(String locatorkey) {

		log("Getting the text of the element " + locatorkey);
		getElement(locatorkey).getText();
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
          by=  By.id(prop.getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_xpath")) {
          by=  By.xpath(prop.getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_css")) {
			 by=   By.cssSelector(prop.getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_name")) {
			 by=   By.name(prop.getProperty(locatorkey));
		}
		return by;

	}

	public void quit() {

		driver.quit();
	}
	
	public void log(String msg) {
		
		test.log(Status.INFO, msg);
	}
	
	public void reportFailure(String failMsg, boolean isCritical) {
		
		test.log(Status.FAIL,failMsg);
		screenshot();
		sa.fail(failMsg);
		
		if(isCritical) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("CriticalFailure", "Y");
			assertAll();
		}
	}
	
	public void assertAll() {
		
		
		sa.assertAll();
	}
	
	public void screenshot()   {
		 
		 Date d  = new Date();
		 String screenshotFile = d.toString().replaceAll(" ", "_").replaceAll(":", "_")+".png";
		 File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		 
		 try {
			 
			 FileUtils.copyFile(src, new File(ExtentManager.screenshotPath+"//"+screenshotFile));
			 test.log(Status.INFO, "screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotPath+"//"+screenshotFile));
		 }
		 catch(IOException e) {
			 
			 e.printStackTrace();
		 }
		 
	}

}
