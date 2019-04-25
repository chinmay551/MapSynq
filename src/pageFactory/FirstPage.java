package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class FirstPage {
	//initiation of Web driver & logger 
	WebDriver driver;
	private Logger logger = Logger.getLogger("mapsync");
	
	
	//WebEliment properties
	@FindBy(id="galactioLink")
	WebElement galactioToggleAd;
	@FindBy(id="ad_toggle")
	WebElement galactioToggleAdButton;
	
	
	// Constructor
	public FirstPage(WebDriver driver){
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Click to hide toggle ad if visible.
	public boolean toggleAdVisible(){
		logger.info("Method 'toggleAdVisible' called");
		WebDriverWait wait= new WebDriverWait(driver, 20);
		WebElement toggleAd;
		toggleAd = wait.until(ExpectedConditions.visibilityOf(galactioToggleAd));
		
		//return toggleAd.isDisplayed();
			
		if (toggleAd.isDisplayed()){
			logger.info("Ad is visible. Click on Ad Button to hide");
			//if ad is visible, click on hide button.
			galactioToggleAdButton.click();
			logger.info("Clicked on hide Button.");
			//Activate Below to click on Ad. It will navigate to new window/tab for Mobile Application.
			//toggleAd.click();
			Boolean adInvisibilityFlag = wait.until(ExpectedConditions.invisibilityOf(toggleAd));
			
			return adInvisibilityFlag;
			}
		else{
			System.out.println("Ad is NOT visible.");
			return false;
		}
		}
					
}
