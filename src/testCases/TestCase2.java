package testCases;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import excelnteraction.ExcelInteraction;
import utility.Constant;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

public class TestCase2 {
	
	//Declare Global Variables
		WebDriver driver;
		ExcelInteraction excel;
	//Report & Logger	
		ExtentReports extent;
		ExtentHtmlReporter htmlReporter;
		ExtentTest extentLogger;
		private Logger logger = Logger.getLogger("mapsync");
		
		@Parameters({"browser"})
		@BeforeTest
		  public void setUp(String browser) throws IOException {
			if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
				driver = new ChromeDriver();	
			}
			else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", Constant.geckoDriverPath);
				driver = new FirefoxDriver();
			}
			// Maximize the Browser
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		// Initialize the objects
			excel = new ExcelInteraction();
			
			extent = new ExtentReports();
			htmlReporter = new ExtentHtmlReporter(new File(Constant.reportPath+"\\TestExecutionReport.html") );
			
		// htmlReporter Configuration Settings
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("Extent Report : MapSync");
			htmlReporter.config().setReportName("End To End Testing");
			htmlReporter.config().setTheme(Theme.DARK);
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			 
			extent.attachReporter(htmlReporter);
			
		// Provide System Information
			extent.setSystemInfo("Project Name", "MapSync");
			extent.setSystemInfo("Browser", browser);
			extent.setSystemInfo("OS",System.getProperty("os.name"));
			extent.setSystemInfo("Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("UserName", System.getProperty("user.name"));
			InetAddress myHost = InetAddress.getLocalHost();
			extent.setSystemInfo("Host Name", myHost.getHostName());
}


  @Test(priority=0)
  public void launchBrowserValidatePageTitle() throws IOException {
	  
	  // ******************Launch URL***************************
	  logger.info("******************Launch URL***************************");
	  String url= excel.getCellData(Constant.filePath, Constant.fileName,"LaunchApplication", 1, 0);
		logger.info("Browsing URL:"+url);
		driver.get(url);
		String titleOfWebPage=driver.getTitle();
		System.out.println("Current Web Title="+titleOfWebPage);
		String titleExpected = excel.getCellData(Constant.filePath, Constant.fileName,"LaunchApplication", 1, 1);
		logger.info("Expected Web Title="+titleExpected);
		logger.info("Actual Web Title="+titleOfWebPage);
		Assert.assertEquals(titleOfWebPage, titleExpected);
  }
  @Test(priority=1)
  	public void hideVisibleAdScreen() throws IOException {
	  //******************** Toggle visible ad screen********************************
		
	  pageFactory.FirstPage firstPage= new pageFactory.FirstPage(driver)  ;
		Assert.assertTrue(firstPage.toggleAdVisible());
  	}
  @Test(priority=2)
	public void registration() throws IOException {	
	// Read number of test data rows.
		int rowCount=0;
		rowCount= excel.getRowCount(Constant.filePath, Constant.fileName, "Registration");
		logger.info(rowCount+" :Number of regstrations to be done.");
		
// FOR LOOP
		for(int i=1;i<=rowCount;i++){
	
			//********************* Click on Register & Compare ****************************
			
			pageFactory.Register register = new pageFactory.Register(driver)  ;
			//Reading Actual Register page heading
			String actRegisterHeading = register.registerTitleString();
			//Reading Expected Register page heading from Excel.
			String expRegisterHeading = excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,0).toString();
			// Register page heading matching or not
			Assert.assertEquals(actRegisterHeading,expRegisterHeading);
			
		//********************** New Registration ***************************************
			pageFactory.Register registerNewUser = new pageFactory.Register(driver)  ;
			
			// fetch details from excel
			String firstname= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,1);
				String lastname= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,2);
				 	String country= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,3);
				 		String address= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,4);
				 			String contact= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,5);
				 				String gender= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,6);
				 					String dob= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,7);
				 						String email= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,8);
				 							String password= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,9);
				 								String expMessage= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",i,10);
			//Enter details in registration page
			String message =registerNewUser.registration(firstname, lastname, address, contact, email, password, dob, country, gender);
			Assert.assertEquals(message,expMessage);
		// LAUNCH URL
			logger.info("******************Launch URL***************************");
			  String url= excel.getCellData(Constant.filePath, Constant.fileName,"LaunchApplication", 1, 0);
				logger.info("Browsing URL:"+url);
				driver.get(url);
				
		}
// FOR LOOP Ends
		
	 
  }
  @BeforeMethod
  public void getMethodName(Method method) throws IOException{
		logger.info("*************************"+method.getName()+"*****************************");
			extentLogger = extent.createTest(method.getName());
			String screenShotPath = getScreenShot(method.getName()+"_before");
			extentLogger.addScreenCaptureFromPath(screenShotPath);
		}
  

  @AfterMethod
  public void getTestExecutionStatus(ITestResult result) throws IOException{
		if(result.getStatus()==ITestResult.SUCCESS){
			extentLogger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" got passed", ExtentColor.GREEN));
			String screenShotPath = getScreenShot(result.getName());
			extentLogger.addScreenCaptureFromPath(screenShotPath);
		}else if(result.getStatus()==ITestResult.FAILURE){
			extentLogger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" got failed due to "+result.getThrowable(), ExtentColor.RED));
			String screenShotPath = getScreenShot(result.getName());
			extentLogger.addScreenCaptureFromPath(screenShotPath);
		}else if(result.getStatus()==ITestResult.SKIP){
			extentLogger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" got skipped", ExtentColor.YELLOW));
		}
	}

  

  @AfterTest
  public void afterTest() throws IOException {
	  extent.flush();
	  driver.quit();
	  
  }
  //Taking screenshot method
  private String getScreenShot(String screenShotName) throws IOException{
		TakesScreenshot srcShot = (TakesScreenshot)driver;
		String dateFormat = new SimpleDateFormat("dd-MM-yyyy_hh_mm_ss").format(new Date());
		String destination = System.getProperty("user.dir")+"\\screenshots\\"+screenShotName+"_"+dateFormat+".png";
		File srcFile =  srcShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(destination);
		FileUtils.copyFile(srcFile, destFile);
		return destination;
	}
 
}
