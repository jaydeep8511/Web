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
import pages.LoginPage;
import pages.NumberSettingPage;
import pages.RegistrationPage;
import pages.User1PhoneApp;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class NumberSettingTest {

	NumberSettingPage numberSettingPage;
	User1PhoneApp user1PhoneApp;
	User1PhoneApp user2PhoneApp;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	WebDriver driver1;
	WebDriver driver2;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public NumberSettingTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());

		numberSettingPage = PageFactory.initElements(driver, NumberSettingPage.class);
		addNumber = PageFactory.initElements(driver, AddNumber.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
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

	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_purchased_number_And_Numbers_setting_page() throws Exception {

		signupAndSignin();

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

		boolean isSettingIconDisplayed = numberSettingPage.validateSettingIcon();
		boolean isDeleteIconDisplayed = numberSettingPage.validateDeleteIcon();

		assertEquals(isSettingIconDisplayed, true);
		assertEquals(isDeleteIconDisplayed, true);

		addNumber.clickOnNumberSetting();

		String actualTitle = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle = "Number Settings | Callhippo.com";

		assertEquals(actualTitle, expectedTitle);

		String actualnumber = numberSettingPage.getNumber();
		assertEquals(numberBeforePurchased, actualnumber);

	}

	@Test(priority = 2, retryAnalyzer = Retry.class)
	public void verify_Department_name_of_number_Validation() throws Exception {

		signupAndSignin();

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

		boolean isSettingIconDisplayed = numberSettingPage.validateSettingIcon();
		boolean isDeleteIconDisplayed = numberSettingPage.validateDeleteIcon();

		assertEquals(isSettingIconDisplayed, true);
		assertEquals(isDeleteIconDisplayed, true);

		addNumber.clickOnNumberSetting();

		String actualTitle = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle = "Number Settings | Callhippo.com";

		assertEquals(actualTitle, expectedTitle);

		String actualnumber = numberSettingPage.getNumber();
		assertEquals(numberBeforePurchased, actualnumber);

		String actualDepartmentName = numberSettingPage.getDepartmentName();
		String expectedDepartmentName = "Number 1";
		assertEquals(actualDepartmentName, expectedDepartmentName);

		numberSettingPage.clickOnEditNameIcon();
		numberSettingPage.clearDepartmentNameTextBox();

		String actualValidationMsg = numberSettingPage.requiredFieldValidationMessage();
		String expectedValidationMsg = "Name is required";

		assertEquals(actualValidationMsg, expectedValidationMsg);
	}

	@Test(priority = 3, retryAnalyzer = Retry.class)
	public void verify_Department_name_of_number_Validation_with_change_department_name() throws Exception {

		signupAndSignin();

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

		numberSettingPage.clickOnEditNameIcon();
		numberSettingPage.clearDepartmentNameTextBox();
		numberSettingPage.enterDepartmentName("Name 2");
		numberSettingPage.clickOnSaveButton();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();

		String acutualSuccessfulValidationMsg = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg = "Number updated successfully";

		assertEquals(acutualSuccessfulValidationMsg, expectedSuccessfulValidationMsg);

		numberSettingPage.clickOnEditNameIcon();
		numberSettingPage.clearDepartmentNameTextBox();
		numberSettingPage.enterDepartmentName("Name 3#@");
		numberSettingPage.clickOnSaveButton();
		numberSettingPage.waitUntilErrorValidationMsgDisplay();

		String acutualErrorValidationMsg = numberSettingPage.validationMessage();
		String expectedErrorValidationMsg = "Special characters are not allowed.";

		assertEquals(acutualErrorValidationMsg, expectedErrorValidationMsg);

	}

	@Test(priority = 4, retryAnalyzer = Retry.class)
	public void verify_Show_CallHippo_Number_in_Incoming_Forwarded_Calls_Toggle_validation() throws Exception {

		signupAndSignin();

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

		numberSettingPage.clickOnShowCallhippoToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg, expectedSuccessfulValidationMsg);

		numberSettingPage.clickOnShowCallhippoToggle();
		numberSettingPage.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

	}

	@Test(priority = 5, retryAnalyzer = Retry.class)
	public void verify_number_verification() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateNumberVerification();
		String expectedVerificationText = "Verfied";
		assertEquals(actualVerificationText, expectedVerificationText);

	}

	@Test(priority = 6, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Basic_montly_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

	}

	@Test(priority = 7, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Basic_Annually_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
		// addNumber.selectUserSilverPlan();
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

		String actualVerificationText1 = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText1 = "UPGRADE";
		assertEquals(actualVerificationText1, expectedVerificationText1);

	}

	@Test(priority = 8, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Bronze_montly_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 9, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Bronze_Annually_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 10, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Silver_montly_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 11, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Silver_Annually_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 12, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Platinum_montly_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 13, retryAnalyzer = Retry.class)
	public void verify_call_Recording_toggle_in_Platinum_Annually_plan() throws Exception {

		signupAndSignin();

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

		String actualVerificationText = numberSettingPage.validateCallrecordingUpgradeButton();
		String expectedVerificationText = "UPGRADE";
		assertEquals(actualVerificationText, expectedVerificationText);

		numberSettingPage.clickOnCallRecordingUpgradeButton();

		addNumber.waitForSubscribePage();
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

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg1 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg1 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg1, expectedSuccessfulValidationMsg1);

		numberSettingPage.clickOnCallRecordingToggle();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "Number updated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

//	@Test
//	public void LoginDialerUser1() throws InterruptedException {
//		driver1 = TestBase.init();
//		user1PhoneApp = PageFactory.initElements(driver1, User1PhoneApp.class);
//		driver1.get("https://staging-phone.callhippo.com/#!/login");
//		
//		driver2 = TestBase.init();
//		user2PhoneApp = PageFactory.initElements(driver2, User1PhoneApp.class);
//		driver2.get("https://staging-phone.callhippo.com/#!/login");
//		
//		user1PhoneApp.enterEmail("jayadip+automationt1@callhippo.com");
//		user2PhoneApp.enterEmail("jayadip+jb@callhippo.com");
//		user1PhoneApp.enterPassword("12345678");
//		user2PhoneApp.enterPassword("12345678");
//		user1PhoneApp.clickOnLoginButton();
//		user2PhoneApp.clickOnLoginButton();
//		user1PhoneApp.enterNumber("+17074743890");
//		user1PhoneApp.makeAcall();
//		
//		
//		
//		
//		
//	}
//	
}
