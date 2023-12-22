package reports;

 

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	
	  static ExtentReports reports ;
	 public static String screenshotPath;
	  
	 
	 public static ExtentReports getReports() {
		 
		 if (reports==null) {
			 
			 reports = new ExtentReports();
			 Date d = new Date();
			 
			 String folderName= d.toString().replaceAll(":", "-")+ "//screenshots";
			 
			 String reportfolderpath = System.getProperty("user.dir")+"//reports//"+d.toString().replaceAll(":", "-");
			 
			 screenshotPath = System.getProperty("user.dir")+"//reports//"+folderName;
			 
			 File f= new File(screenshotPath);
			 f.mkdirs();
			 
			 ExtentSparkReporter esr = new ExtentSparkReporter(reportfolderpath);
			 esr.config().setTheme(Theme.STANDARD);
			 esr.config().setReportName("Web automation testing result 2023");
			 esr.config().setDocumentTitle("Automation Admin protocols");
			 esr.config().setEncoding("utf-8");
			 
			 reports.attachReporter(esr);
		 }
		 
		 return reports;
	 }

}
