package rough;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Raw {

	
	 static WebDriver driver ;
	 
	public static void main(String[] args) throws InterruptedException {
		 
		 

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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://portfolio.rediff.com/portfolio-login");
		driver.findElement(By.id("useremail")).sendKeys("sample.sharma");
		driver.findElement(By.id("userpass")).sendKeys("Jamuna1@");
		driver.findElement(By.id("loginsubmit")).click();
		Select s = new Select(driver.findElement(By.id("portfolioid")));
		s.selectByVisibleText("111");
		Thread.sleep(5000);
		
		//addstock
		driver.findElement(By.id("addStock")).click();
		
	    type("Birla Cable Ltd");
		wait(1);
		clickEnterButton();
		
		
		String selectionDate ="26-01-2024";
		
		driver.findElement(By.cssSelector("a#stockPurchaseDate")).click();
		
	 
       
		
		
		
		
		//driver.findElement(By.cssSelector("input#addstockqty")).sendKeys("100");
		//driver.findElement(By.cssSelector("input#addstockprice")).sendKeys("200");
		
		//driver.findElement(By.cssSelector("input#addStockButton")).click();
		
		
		
		
		
		
		 
		
		
		

	}
	
	public static  void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void type( String data) {

		  driver.findElement(By.cssSelector("input#addstockname")).sendKeys(data);
	 
	}
	
	public static void clickEnterButton() {
		 
		    
		driver.findElement(By.cssSelector("input#addstockname")).sendKeys(Keys.ENTER);
		}

}
