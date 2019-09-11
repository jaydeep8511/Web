package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Base.TestBase;
import pages.AddNumber;
import pages.LoginPage;
import pages.NSMusicAndMessages;
import pages.NSOpeningHours;
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class NSOpeningHoursTest {
	
	NSOpeningHours nsOpeningHours;
	NSMusicAndMessages nsMusicAndMessage;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public NSOpeningHoursTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());
		
		nsOpeningHours = PageFactory.initElements(driver, NSOpeningHours.class);
		nsMusicAndMessage = PageFactory.initElements(driver, NSMusicAndMessages.class);
		numberSettingPage = PageFactory.initElements(driver, NumberSettingPage.class);
		addNumber = PageFactory.initElements(driver, AddNumber.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
	}

	@AfterMethod
	public void teardown() {
		// driver.quit();
	}

	public void signupAndSignin() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get(url.signUp());
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get(url.signIn());
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
	}

	public void addNumberAndOpenTheirNumberSettingPage() {
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();

//		addNumber.selectSearchByTollfree();
//		addNumber.clickOnSearchButton();

		String numberBeforePurchased = addNumber.getSeletedNumber();
		addNumber.selectNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$8.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

		String NumberAfterPurchased = addNumber.getNumeberAfterPurchased();
		assertEquals(numberBeforePurchased, NumberAfterPurchased);

		// numberSettingPage.invisiblityOfValidationMessage();

		boolean isSettingIconDisplayed = numberSettingPage.validateSettingIcon();
		boolean isDeleteIconDisplayed = numberSettingPage.validateDeleteIcon();

		assertEquals(isSettingIconDisplayed, true);
		assertEquals(isDeleteIconDisplayed, true);

		addNumber.clickOnNumberSetting();

		String actualTitle = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle = "Number Settings | Callhippo.com";

		assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_always_Closed_And_Always_Open_Button_For_Opening_Hours() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsOpeningHours.clickOnAlwaysClosedButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsOpeningHours.clickOnAlwaysClosedButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_with_change_timezone_For_Opening_Hours() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsOpeningHours.clickOnEditTimezonePen();
		Thread.sleep(1000);
		nsOpeningHours.SelectTimezone("(UTC-07:00) Arizona");
		Thread.sleep(1000);
		nsOpeningHours.clickOnSaveImageButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);
		
		String acutalTimezone = nsOpeningHours.validateTimeZone();
		String expectedTimezone = "(UTC-07:00) Arizona";
		assertEquals(acutalTimezone, expectedTimezone);

	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_custom_option_For_Opening_Hours_in_basic_Monthly_plan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsOpeningHours.clickOnCustomButton();
		
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_custom_option_For_Opening_Hours_in_basic_Annually_plan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsOpeningHours.clickOnCustomButton();
		
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		
		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		// addNumber.selectUserPlatinumPlan();
		addNumber.selectUserAnuallyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitForSubscribePage();
		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);
		// addNumber.clickOnNumberLink();
		numberSettingPage.clickOnNumberLink();
		addNumber.clickOnNumberSetting();
		String actualTitle1 = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle1 = "Number Settings | Callhippo.com";
		assertEquals(actualTitle1, expectedTitle1);
		
		nsOpeningHours.clickOnCustomButton();
		boolean selectPlanPopup1 = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup1, true);
		
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_custom_option_For_Opening_Hours_in_Bronze_Monthly_plan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsOpeningHours.clickOnCustomButton();
		
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		
		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserBronzePlan();
		addNumber.selectUserMontlyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitForSubscribePage();
		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);
		// addNumber.clickOnNumberLink();
		numberSettingPage.clickOnNumberLink();
		addNumber.clickOnNumberSetting();
		String actualTitle1 = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle1 = "Number Settings | Callhippo.com";
		assertEquals(actualTitle1, expectedTitle1);
		
		nsOpeningHours.clickOnCustomButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);
		
		nsOpeningHours.clickOnSetTheCustomTimeLink();
		
		String actualTitle = nsOpeningHours.validateCurrectPageTitle();
		String expectedTitle = "Numbers - Custom Availability | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
		
		nsOpeningHours.selectTime();
	}
	
	
}


