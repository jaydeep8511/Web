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
import pages.InviteUser;
import pages.LoginPage;
import pages.NSAllocation;
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class InviteUserTest {
	
	InviteUser inviteUser;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public InviteUserTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());
		
		inviteUser = PageFactory.initElements(driver, InviteUser.class);
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
		String actualActivatedMessage =inviteUser.validateAccountActivatedMessage();
		String expectedActivatedMessage = "Your account has been activated.";
		assertEquals(actualActivatedMessage, expectedActivatedMessage);
	}
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_invite_User_successfully() throws Exception {
		
		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();
		
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
		String actualActivatedMessage =inviteUser.validateAccountActivatedMessage();
		String expectedActivatedMessage = "Your account has been activated.";
		assertEquals(actualActivatedMessage, expectedActivatedMessage);
		
//		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
//		boolean expectedVerifyMsg = true;
//		assertEquals(actualVerifyMsg, expectedVerifyMsg);
		
		
		
	}
	

}
