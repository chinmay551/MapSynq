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

public class TestCases {
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
		  //Browser selection
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", Constant.chromeDriverPath);
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")){
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
	
	@BeforeMethod
	  public void getMethodName(Method method) throws IOException{
		logger.info("*************************"+method.getName()+"*****************************");
			extentLogger = extent.createTest(method.getName());
			String screenShotPath = getScreenShot(method.getName()+"_before");
			extentLogger.addScreenCaptureFromPath(screenShotPath);
		}
	
	@Test(priority=0)
  public void openUrl() throws IOException {
		
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
	public void hideToggleAd(){
		pageFactory.FirstPage firstPage= new pageFactory.FirstPage(driver)  ;
		Assert.assertTrue(firstPage.toggleAdVisible());
		
		
	}
	
	@Test(priority=2)
	
	public void newRegistrationPageHeader() throws IOException{
		
		pageFactory.Register register = new pageFactory.Register(driver)  ;
		//Reading Actual Register page heading
		String actRegisterHeading = register.registerTitleString();
		//Reading Expected Register page heading from Excel.
		String expRegisterHeading = excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,0).toString();
		// Register page heading matching or not
		Assert.assertEquals(actRegisterHeading,expRegisterHeading);
		
	}
	
	@Test(priority=3)
		public void newRegistration() throws IOException{
			pageFactory.Register register = new pageFactory.Register(driver)  ;
				
		// fetch details from excel
		String firstname= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,1);
			String lastname= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,2);
			 	String country= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,3);
			 		String address= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,4);
			 			String contact= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,5);
			 				String gender= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,6);
			 					String dob= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,7);
			 						String email= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,8);
			 							String password= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,9);
			 								String expMessage= excel.getCellData(Constant.filePath, Constant.fileName, "Registration",1,10);
		//Enter details in registration page
		String message =register.registration(firstname, lastname, address, contact, email, password, dob, country, gender);
		Assert.assertEquals(message,expMessage);
		
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
