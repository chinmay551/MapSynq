package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Register {
	//Local Variables declared.
	WebDriver driver;
	private Logger logger = Logger.getLogger("mapsync");
	
	//Object properties.
	@FindBy(xpath="//*[@id='div_header']/div[2]/div[4]/a[2]")
	WebElement registrer;
	//Register Page Heading
	@FindBy(xpath="/html/body/div[2]/table/tbody/tr/td/div/h5")
	WebElement registerHeading;
	
	@FindBy(xpath="//input[@name='profile[first_name]']")
	WebElement firstName;
	@FindBy(xpath="//input[@id='profile_last_name']")
	WebElement lastName;
	@FindBy(xpath="//select[@id='profile_country']")
	WebElement countryDropdown;
	@FindBy(xpath="//input[@id='profile_address']")
	WebElement address;
	@FindBy(xpath="//input[@id='profile_contact_no']")
	WebElement contact;
	@FindBy(xpath="//label[@for='profile_gender_F']")
	WebElement genderFemaleRadioBtn;
	@FindBy(xpath="//label[@for='profile_gender_M']")
	WebElement genderMaleRadioBtn;
	//DOB
	
	@FindBy(xpath="//input[@id='profile_dob']")
	WebElement dob;
	
/*	@FindBy(xpath="//select[@class='ui-datepicker-month']")
	WebElement month;
	@FindBy(xpath="//select[@class='ui-datepicker-year']")
	WebElement year;*/
		
	@FindBy(xpath="//input[@id='profile_email']")
	WebElement eMail;
	@FindBy(xpath="//input[@name='identity[password]']")
	WebElement password;
	@FindBy(xpath="//input[@id='identity[password_confirmation]']")
	WebElement confirmPassword;
	//check boxes
	@FindBy(xpath="//input[@id='profile_subscribe_to_newsletter']")
	WebElement subscribeCheckBox;
	@FindBy(xpath="//input[@id='chk_agree']")
	WebElement agreeCheckBox;
	//Submit button
	@FindBy(xpath="//input[@value='Create Profile']")
	WebElement createProfileButton;
	@FindBy(xpath="//div[@class='error_signup']")
	WebElement errorMsg;
	
	
	// Constructor
	public Register(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	public String registerTitleString(){
		//Click on Register link
		logger.info("Clicking on Register");
		registrer.click();
		logger.info("Clicked on Registerlink");
		logger.info("Will return actual regstration page Header.");
		String actualRegPageHeader = registerHeading.getText();
		logger.info("Actual Registration Page Header is:'"+actualRegPageHeader+"'");
		return actualRegPageHeader;
	}
	
	/*public void sendkeysRegister(WebElement webElement,String value){
		logger.info("Entering "+value+"in "+webElement+"field");
		webElement.sendKeys(value);
		logger.info("Entered "+value+"in "+webElement+"field");
	}*/
	
	private void sendkeysRegister(String firstname, String lastname, String addressValue, String contactNumber,String emailid, String passwordValue,String dobDate){
		
		//Enter Field Values
		//firstName
		logger.info("Entering "+firstname+"in first name field");
		firstName.sendKeys(firstname);
		logger.info("Entered "+firstname+"in first name field");
		//lastname
		logger.info("Entering "+lastname+"in last name field");
		lastName.sendKeys(lastname);
		logger.info("Entered "+lastname+"in last name field");
		//	address	
		logger.info("Entering "+addressValue+"in address field");
		address.sendKeys(addressValue);
		logger.info("Entered "+addressValue+"in address field");
		//contactNumber
		logger.info("Entering "+contactNumber+"in contact Number field");
		contact.sendKeys(contactNumber);
		logger.info("Entered "+contactNumber+"in contact Number field");
		//Password
		logger.info("Entering "+passwordValue+"in password field");
		password.sendKeys(passwordValue);
		logger.info("Entered "+passwordValue+"in password field");
		// confirmPassword	
		logger.info("Entering "+passwordValue+"in confirm Password field");
		confirmPassword.sendKeys(passwordValue);
		logger.info("Entered "+passwordValue+"in confirm Password field");
		//DOB
		logger.info("Entering "+dobDate+"in DOB field");
		dob.sendKeys(dobDate);
		logger.info("Entered "+dobDate+"in DOB field");
		//Enter email
		logger.info("Entering "+emailid+"in email field");
		eMail.sendKeys(emailid);
		logger.info("Entered "+emailid+"in email field");
		// select subscribe check box
		if (!subscribeCheckBox.isSelected()){
			logger.info("Selecting subscribe CheckBox");
			subscribeCheckBox.click();
			logger.info("Selected subscribe CheckBox.");
		}
		else if( subscribeCheckBox.isSelected()){
			logger.info("Subscribe CheckBox is selected already.");
		}
		//select I Agree check box
		logger.info("Selecting I agree CheckBox");
		agreeCheckBox.click();
		logger.info("Selected I agree CheckBox.");
		
		
	}
	
	private void selectCountry(String country){
		
		logger.info("Entering "+country+"in country field");
		Select select = new Select(countryDropdown);
		select.selectByVisibleText(country);
		logger.info("Entered "+country+"in country field");
	}
	private void selectGender(String gender){
		if ("Male"==gender){
			logger.info("Selecting Male radio button field");
			genderMaleRadioBtn.click();
			logger.info("Selected Male radio button field");
		}
		else if("Female"==gender){
			logger.info("Selecting Female radio button field");
			genderFemaleRadioBtn.click();
			logger.info("Selected Female radio button field");
		}
		else{
			logger.info("In-correct Radio button- Failed to select gender.");
		}
	}
	public String registration(String firstname, String lastname, String addressValue, String contactNumber,String emailid, String passwordValue,String dobDate, String country, String gender){
		logger.info("entetingregistraton field values");
		sendkeysRegister(firstname, lastname, addressValue,  contactNumber, emailid, passwordValue, dobDate);
		selectCountry(country);
		selectGender(gender);
		logger.info("Clcking on Create Profile Button");
		createProfileButton.click();
		logger.info("Clcked on Create Profile Button");
		if (errorMsg.isDisplayed()){
			logger.info("Error message displayed.");
			return errorMsg.getText();
		}
		else {
			return null;
		}
	}
	
	
}
