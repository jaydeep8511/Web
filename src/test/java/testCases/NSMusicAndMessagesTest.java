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
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class NSMusicAndMessagesTest {

	NSMusicAndMessages nsMusicAndMessage;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public NSMusicAndMessagesTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());

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
	public void verify_Welcome_Message_Toggle() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 2, retryAnalyzer = Retry.class)
	public void verify_Welcome_Message_validation() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clearWelcomeMessageText();
		String actualRequireFieldMsg = nsMusicAndMessage.validateRequiredFieldValidationMessage();
		String expectedRequireFieldMsg = "Welcome message is required";
		assertEquals(actualRequireFieldMsg, expectedRequireFieldMsg);

		nsMusicAndMessage.enterWelcomeMessage("@!@#");
		nsMusicAndMessage.clickOnWelcomeSaveButton();
		nsMusicAndMessage.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);

		nsMusicAndMessage.clearWelcomeMessageText();
		nsMusicAndMessage.enterWelcomeMessage("test!@");
		nsMusicAndMessage.clickOnWelcomeSaveButton();
		nsMusicAndMessage.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);

	}

	@Test(priority = 3, retryAnalyzer = Retry.class)
	public void verify_with_Change_Welcome_Message_validation() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clearWelcomeMessageText();
		nsMusicAndMessage.enterWelcomeMessage("test with number and save 123456");
		nsMusicAndMessage.clickOnWelcomeSaveButton();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clearWelcomeMessageText();
		nsMusicAndMessage.enterWelcomeMessage("hello, have a great day.");
		nsMusicAndMessage.clickOnWelcomeSaveButton();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 4, retryAnalyzer = Retry.class)
	public void verify_with_Change_language_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.selectLanguageFromWelcomeLanguageDropdown("French");
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 5, retryAnalyzer = Retry.class)
	public void verify_with_Change_voice_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clickOnMaleVoiceForWelcomeMessage();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnFemaleVoiceForWelcomeMessage();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 6, retryAnalyzer = Retry.class)
	public void verify_with_Upload_MP3_less_than_5MB_Music_File_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clickOnMusicRadioButton();
		nsMusicAndMessage.clickOnUploadFileForMusic();
		
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 7, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_WAV_Music_File_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clickOnMusicRadioButton();
		nsMusicAndMessage.clickOnUploadFileForMusic();
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_4_98MB.exe");
		Thread.sleep(3000);
		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 8, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_MP3_Music_File_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clickOnMusicRadioButton();
		nsMusicAndMessage.clickOnUploadFileForMusic();

		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_10MB.exe");

		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 9, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_WAV_Music_File_for_welcome_message() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clickOnMusicRadioButton();
		nsMusicAndMessage.clickOnUploadFileForMusic();
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_10MB.exe");
		Thread.sleep(3000);
		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 10, retryAnalyzer = Retry.class)
	public void verify_Welcome_Voicemail_Toggle() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnVoicemailToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 11, retryAnalyzer = Retry.class)
	public void verify_voicemail_audioText_validation() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		// nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.clearVoicemailText();
		String actualRequireFieldMsg = nsMusicAndMessage.validateRequiredFieldValidationMessage();
		String expectedRequireFieldMsg = "Voice mail is required";
		assertEquals(actualRequireFieldMsg, expectedRequireFieldMsg);

		nsMusicAndMessage.enterVoicemailMessage("@!@#");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		// nsMusicAndMessage.clickOnSelectPlanButton();
		nsMusicAndMessage.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);

		nsMusicAndMessage.clearVoicemailText();
		nsMusicAndMessage.enterVoicemailMessage("test!@");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		nsMusicAndMessage.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);

	}

	@Test(priority = 12, retryAnalyzer = Retry.class)
	public void verify_with_Change_language_for_Voicemail() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnWelcomeMessageToggle();

		nsMusicAndMessage.selectLanguageForVoicemailLanguageDropdown("French");
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 13, retryAnalyzer = Retry.class)
	public void verify_with_Change_voice_for_Voicemail() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnMaleVoiceForVoicemail();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnFemaleVoiceForVoicemail();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 14, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Basic_monthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		String actualVoicemailText = nsMusicAndMessage.getVoicemailText();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		driver.navigate().refresh();

		String expectedVoicemailText = nsMusicAndMessage.getVoicemailText();
		assertEquals(actualVoicemailText, expectedVoicemailText);

	}

	@Test(priority = 15, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Basic_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);
//		addNumber.selectUserBronzePlan();
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

		String actualVoicemailText = nsMusicAndMessage.getVoicemailText();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup1 = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup1, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		driver.navigate().refresh();

		String expectedVoicemailText = nsMusicAndMessage.getVoicemailText();
		assertEquals(actualVoicemailText, expectedVoicemailText);

	}

	@Test(priority = 16, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Bronze_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 17, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Bronze_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserBronzePlan();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 18, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Silver_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 19, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Silver_AnnuallyPlan() throws Exception {

		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 20, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Platinum_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 21, retryAnalyzer = Retry.class)
	public void verify_with_Change_voicemail_audioText_validation_in_Platinum_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clearVoicemailText();

		nsMusicAndMessage.enterVoicemailMessage("Hello Please leave a message.");
		nsMusicAndMessage.clickOnVoicemailSaveButton();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 22, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_MP3_Audio_File_for_Voicemail() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_10MB.exe");
		Thread.sleep(3000);
		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 23, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_WAV_Audio_File_for_Voicemail() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_10MB.exe");
		Thread.sleep(3000);
		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 24, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Basic_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateFileNotUploaded();
		String expectedResult = "No file uploaded";
		assertEquals(actualResult, expectedResult);

	}

	@Test(priority = 25, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_WAV_Audio_File_for_Voicemail_in_Basic_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_4_98MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateFileNotUploaded();
		String expectedResult = "No file uploaded";
		assertEquals(actualResult, expectedResult);

	}

	@Test(priority = 26, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Basic_Annually() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup1 = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup1, true);

		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateFileNotUploaded();
		String expectedResult = "No file uploaded";
		assertEquals(actualResult, expectedResult);

	}

	@Test(priority = 27, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Bronze_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 28, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Bronze_AnnuallyPlan()
			throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserBronzePlan();
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 29, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Silver_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 30, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Silver_AnnullyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(3000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 31, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Platinum_MonthlyPlan()
			throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 32, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_Voicemail_in_Platinum_AnnullyPlan()
			throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnVoicemailAdioRadioButton();
		nsMusicAndMessage.clickOnAudioUploadFile();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 33, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Basic_MonthlyPaln() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
	}

	@Test(priority = 34, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Basic_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		//addNumber.selectUserPlatinumPlan();
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

		boolean actualResult1 = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult1, true);
		
	}
	
	@Test(priority = 35, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Bronze_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
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

		boolean actualResult1 = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult1, true);
		
	}
	
	@Test(priority = 36, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Bronze_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserBronzePlan();
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

		boolean actualResult1 = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult1, true);
		
	}
	
	@Test(priority = 37, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Silver_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnTranscriptionToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnTranscriptionToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);
		
	}
	
	@Test(priority = 38, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Silver_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnTranscriptionToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnTranscriptionToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);
		
	}
	
	@Test(priority = 39, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Platinum_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnTranscriptionToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnTranscriptionToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);
		
	}
	
	@Test(priority = 40, retryAnalyzer = Retry.class)
	public void verify_Voicemail_Transcription_in_Platinum_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();
		
		boolean actualResult = nsMusicAndMessage.ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed();
		assertEquals(actualResult, true);
		
		nsMusicAndMessage.clickOnUpgradeButtonOfVoiceMailTranscription();
		
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnTranscriptionToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		nsMusicAndMessage.clickOnTranscriptionToggle();

		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);
		
	}
	
	@Test(priority = 41, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_MP3_Audio_File_for_Call_Hold() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_10MB.exe");
		Thread.sleep(3000);
		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 42, retryAnalyzer = Retry.class)
	public void verify_with_Upload_more_than_5MB_WAV_Audio_File_for_Call_Hold() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();
		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_10MB.exe");
		Thread.sleep(3000);
		// nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = nsMusicAndMessage.validateMusicFileErrorMessage();
		String expectedSuccessfulValidationMsg1 = "File is too large, max size is 5MB.";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 43, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Basic_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateFileNotUploaded();
		String expectedResult = "No file uploaded";
		assertEquals(actualResult, expectedResult);

	}
	
	@Test(priority = 44, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_WAV_Audio_File_for_CallHold_in_Basic_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\WAV_4_98MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);
		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateThereIsNoAudioText();
		String expectedResult = "There is no audio";
		assertEquals(actualResult, expectedResult);

	}
	
	@Test(priority = 45, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Basic_Annually() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup1 = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup1, true);

		nsMusicAndMessage.closeSelectPlanPopup();
		String actualResult = nsMusicAndMessage.validateThereIsNoAudioText();
		String expectedResult = "There is no audio";
		assertEquals(actualResult, expectedResult);

	}
	
	@Test(priority = 46, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Bronze_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 47, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Bronze_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserBronzePlan();
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 48, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Silver_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 49, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Silver_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserSilverPlan();
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 50, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Platinum_MonthlyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
	
	@Test(priority = 51, retryAnalyzer = Retry.class)
	public void verify_with_Upload_less_than_5MB_MP3_Audio_File_for_CallHold_in_Platinum_AnnuallyPlan() throws Exception {

		signupAndSignin();

		addNumberAndOpenTheirNumberSettingPage();

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);
		boolean selectPlanPopup = nsMusicAndMessage.validateSelectPlanPopupIsDisplayed();
		assertEquals(selectPlanPopup, true);

		nsMusicAndMessage.clickOnSelectPlanButton();
		addNumber.waitForSubscribePage();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@scroll-on-click='yourplan']")).click();
		Thread.sleep(3000);

		addNumber.selectUserPlatinumPlan();
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

		nsMusicAndMessage.clickOnCallHoldFileUploadButton();

		Thread.sleep(3000);
		Runtime.getRuntime().exec("..\\Web\\src\\main\\java\\audioFiles\\MP3_5MB.exe");
		Thread.sleep(1000);

		nsMusicAndMessage.waitUntilFileUploadedMessageDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Your file has been uploaded successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}
}
