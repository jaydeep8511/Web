package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Base.TestBase;
import pages.AddNumber;
import pages.CreateTeam;
import pages.InviteUser;
import pages.LoginPage;
import pages.NSAllocation;
import pages.NSivr;
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class NSivrTest {

	NSivr nsIVR;
	InviteUser inviteUser;
	CreateTeam createTeam;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public NSivrTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());

		nsIVR = PageFactory.initElements(driver, NSivr.class);
		createTeam = PageFactory.initElements(driver, CreateTeam.class);
		inviteUser = PageFactory.initElements(driver, InviteUser.class);
		numberSettingPage = PageFactory.initElements(driver, NumberSettingPage.class);
		addNumber = PageFactory.initElements(driver, AddNumber.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
	}

	@AfterMethod
	public void teardown() {
		//driver.quit();
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

	public void addNumberWithBronzePlan() {
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$18.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

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
	
	public void inviteUser() throws Exception {
		inviteUser.clickOnUsersLink();
		inviteUser.clickOnInviteUserButton();

		String actualTitle = inviteUser.validateTitle();
		String expectedTitle = "Invite User | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);

		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		inviteUser.enterEmail(email);
		inviteUser.clickOnNumberCheckbox();
		inviteUser.clickOnInviteButton();

		inviteUser.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Your invitation sent successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

		inviteUser.ValidateVerifyButtonOnGmail();
		inviteUser.enterFullName("Invited User1");
		inviteUser.enterPassword("12345678");
		inviteUser.enterconfirmPassword("12345678");
		inviteUser.clickOnSubmitButton();
		String actualActivatedMessage = inviteUser.validateAccountActivatedMessage();
		String expectedActivatedMessage = "Your account has been activated.";
		assertEquals(actualActivatedMessage, expectedActivatedMessage);
	}
	
	public void createTeam() throws IOException {
		driver.get(url.signIn());
		createTeam.clickOnTeamLink();
		createTeam.clickOnCreateTeamLink();
		createTeam.enterTeamName("Team 1");
		createTeam.clickOnSimultaneouslyRedioButton();
		createTeam.selectUser1();
		createTeam.clickOnYesButton();
		createTeam.selectUser2();
		createTeam.clickOnYesButton();
		createTeam.closeCreditPopup();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		createTeam.clickOnCreateButton();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
		createTeam.clickOnYesButton();
		
		createTeam.waitForvalidationMessage();
		String acutualSuccessfulValidationMsg = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg = "Team created successfully";
		assertEquals(acutualSuccessfulValidationMsg, expectedSuccessfulValidationMsg);
		
		driver.get(url.numbersPage());
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
	public void Verify_That_ivr_functionality_not_supported_in_Basic_Plan() throws Exception {
		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
		nsIVR.waitForIVRValidationMessage();
		String acutualSuccessfulValidationMsg = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg = "Ivr feature is not available in Basic Plan. Please Upgrade your plan.";
		assertEquals(acutualSuccessfulValidationMsg, expectedSuccessfulValidationMsg);
		
	}
	
	@Test(priority = 1)
	public void Verify_Required_Field_Validation_for_message_field() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
		
		nsIVR.clickOnEditMessagePen();
		nsIVR.clearIVRMessage();
		String actualRequireFieldMsg = nsIVR.requireFieldValidationForMessage();
		String expectedRequireFieldMsg = "IVR message is required";
		assertEquals(actualRequireFieldMsg, expectedRequireFieldMsg);
		
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_special_character_validation_for_Message_field() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.clickOnEditMessagePen();
		nsIVR.clearIVRMessage();
		nsIVR.enterIVRMessage("@#!");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
		
		
		nsIVR.clearIVRMessage();
		nsIVR.enterIVRMessage("Hello@#!");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);
		
		
		nsIVR.clearIVRMessage();
		nsIVR.enterIVRMessage("@#!hello");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitUntilSpecialCharacterValidationMessage();
		String acutualErrorValidationMsg2 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg2 = "Special characters are not allowed.";
		assertEquals(acutualErrorValidationMsg2, expectedErrorValidationMsg2);
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_message_field_updated_successfully() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.clickOnEditMessagePen();
		nsIVR.clearIVRMessage();
		nsIVR.enterIVRMessage("Hello, please press 1 for team.");
		nsIVR.clickOnSaveIcon();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Number updated successfully";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
		
		nsIVR.clickOnEditMessagePen();
		nsIVR.clearIVRMessage();
		nsIVR.enterIVRMessage("123456789Text");
		nsIVR.clickOnSaveIcon();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Number updated successfully";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);
		
		
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_change_language_successfully() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.selectLanguageForIVRMessage("French - Canadian");
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Number updated successfully";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
	
	}
	
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_change_Voice_successfully() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.clickOnMaleRadioButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Number updated successfully";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
		
		nsIVR.clickOnfemaleRadioButton();
		
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Number updated successfully";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);
	
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_press_field_validation() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
//		inviteUser();
//		createTeam();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.enterPressDigits("1");
		//nsIVR.selectValueFromActionDropdown("tstAutomation");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitForActionValidationMessage();
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Please Select Number or User.";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
		
		nsIVR.clearPressDigits();
		nsIVR.selectValueFromActionDropdown("tstAutomation");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitForPressValidationMessage();
		String acutualErrorValidationMsg1 = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg1 = "Please insert appropriate values for IVR.";
		assertEquals(acutualErrorValidationMsg1, expectedErrorValidationMsg1);
		
	
	}
	
	
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void Verify_with_add_ivr_options_successfully() throws Exception {
		signupAndSignin();
		addNumberWithBronzePlan();
		inviteUser();
		createTeam();
		nsIVR.clickOnIVRSection();
		nsIVR.clickonIVRToggle();
	
		nsIVR.enterPressDigits("1");
		nsIVR.selectValueFromActionDropdown("tstAutomation");
		nsIVR.clickOnSaveIcon();
		
		nsIVR.waitForDigitAddedSuccessfullyMessage("1");
		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "IVR for digit 1 is added successfully";
		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);
		
	
	}
	
}
