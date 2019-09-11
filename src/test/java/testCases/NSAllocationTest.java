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
import pages.NSMusicAndMessages;
import pages.NSOpeningHours;
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class NSAllocationTest {

	NSAllocation nsAllocation;
	InviteUser inviteUser;
	CreateTeam createTeam;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public NSAllocationTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());

		nsAllocation = PageFactory.initElements(driver, NSAllocation.class);
		createTeam = PageFactory.initElements(driver, CreateTeam.class);
		inviteUser = PageFactory.initElements(driver, InviteUser.class);
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
	
	public void createTeam() {
		createTeam.clickOnTeamLink();
		createTeam.clickOnCreateTeamLink();
		createTeam.enterTeamName("Team 1");
		createTeam.clickOnSimultaneouslyRedioButton();
		createTeam.selectUser1();
		createTeam.clickOnYesButton();
		createTeam.selectUser2();
		createTeam.clickOnYesButton();
		nsAllocation.closeCreditPopup();
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
	}

	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_number_deallocation_to_user_with_popup_Option_Yes_and_No() throws Exception {

		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();

		nsAllocation.clickOnUser1();
		String actualPopupMessage = nsAllocation.ValidatPopupText();
		String expectedPopupMessage = "Are you sure you want to deallocate this user ?";
		assertEquals(actualPopupMessage, expectedPopupMessage);

		nsAllocation.clickOnPopupNoButton();
		String actualPopupMessage1 = nsAllocation.ValidatPopupText();
		String expectedPopupMessage1 = "Are you sure you want to deallocate this user ?";
		assertEquals(actualPopupMessage1, expectedPopupMessage1);

		nsAllocation.clickOnPopupYesButton();

		nsAllocation.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "User deallocated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

	}

	@Test(priority = 2, retryAnalyzer = Retry.class)
	public void verify_number_allocation_to_user_with_popup_Option_Yes_and_No() throws Exception {

		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();

		nsAllocation.clickOnUser1();
		String actualPopupMessage = nsAllocation.ValidatPopupText();
		String expectedPopupMessage = "Are you sure you want to deallocate this user ?";
		assertEquals(actualPopupMessage, expectedPopupMessage);

		nsAllocation.clickOnPopupYesButton();

		nsAllocation.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "User deallocated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

		nsAllocation.clickOnUser1();
		String actualPopupMessageAllocate = nsAllocation.ValidatPopupText();
		String expectedPopupMessageAllocate = "Are you sure you want to allocate this user ?";
		assertEquals(actualPopupMessageAllocate, expectedPopupMessageAllocate);

		nsAllocation.clickOnPopupNoButton();

		nsAllocation.clickOnUser1();
		String actualPopupMessageAllocate1 = nsAllocation.ValidatPopupText();
		String expectedPopupMessageAllocate1 = "Are you sure you want to allocate this user ?";
		assertEquals(actualPopupMessageAllocate1, expectedPopupMessageAllocate1);

		nsAllocation.clickOnPopupYesButton();
		nsAllocation.waitUntilSuccessfulValidationMsgDisplayDeallocate();
		String acutualSuccessfulValidationMsgAllocation = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsgAllocation = "User allocated successfully";
		assertEquals(acutualSuccessfulValidationMsgAllocation, expectedSuccessfulValidationMsgAllocation);
	}

	@Test(priority = 3, retryAnalyzer = Retry.class)
	public void verify_number_allocation_to_multiple_users_with_popup_Option_Yes_and_No() throws Exception {

		signupAndSignin();
		addNumberAndOpenTheirNumberSettingPage();
		inviteUser();
		driver.get("https://jenkins-app.callhippo.com/#!/numbers");
		addNumber.clickOnNumberSetting();

		String actualTitle = numberSettingPage.verifyNumberSettingPage();
		String expectedTitle = "Number Settings | Callhippo.com";

		assertEquals(actualTitle, expectedTitle);

		nsAllocation.clickOnUser2();
		String actualPopupMessage = nsAllocation.ValidatPopupText();
		String expectedPopupMessage = "Are you sure you want to deallocate this user ?";
		assertEquals(actualPopupMessage, expectedPopupMessage);

		nsAllocation.clickOnPopupYesButton();

		nsAllocation.waitUntilSuccessfulValidationMsgDisplay();
		String acutualSuccessfulValidationMsg2 = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg2 = "User deallocated successfully";
		assertEquals(acutualSuccessfulValidationMsg2, expectedSuccessfulValidationMsg2);

		nsAllocation.clickOnUser2();
		String actualPopupMessageAllocate = nsAllocation.ValidatPopupText();
		String expectedPopupMessageAllocate = "Are you sure you want to allocate this user ?";
		assertEquals(actualPopupMessageAllocate, expectedPopupMessageAllocate);

		nsAllocation.clickOnPopupNoButton();

		nsAllocation.clickOnUser2();
		String actualPopupMessageAllocate1 = nsAllocation.ValidatPopupText();
		String expectedPopupMessageAllocate1 = "Are you sure you want to allocate this user ?";
		assertEquals(actualPopupMessageAllocate1, expectedPopupMessageAllocate1);

		nsAllocation.clickOnPopupYesButton();
		nsAllocation.waitUntilSuccessfulValidationMsgDisplayDeallocate();
		String acutualSuccessfulValidationMsgAllocation = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsgAllocation = "User allocated successfully";
		assertEquals(acutualSuccessfulValidationMsgAllocation, expectedSuccessfulValidationMsgAllocation);

	}
	
	@Test(priority = 4, retryAnalyzer = Retry.class)
	public void verify_number_allocation_to_Team_with_popup_Option_Yes_and_No() throws Exception {

		signupAndSignin();
		addNumberWithBronzePlan();
		inviteUser();
		driver.get("https://jenkins-app.callhippo.com/#!/numbers");
		addNumber.clickOnNumberSetting();
		createTeam();
		driver.get("https://jenkins-app.callhippo.com/#!/numbers");
		addNumber.clickOnNumberSetting();
		
		nsAllocation.clickOnAllocationToggle();
		nsAllocation.selectTeam();
		nsAllocation.clickOnPopupYesButton();
		nsAllocation.waitUntilTeamAllocationMessageDisplayed();
		String acutualSuccessfulValidationMsgTeamAllocation = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsgTeamAllocation = "Allocation is changed to team";
		assertEquals(acutualSuccessfulValidationMsgTeamAllocation, expectedSuccessfulValidationMsgTeamAllocation);
		
	}

}
