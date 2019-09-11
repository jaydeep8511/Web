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
import pages.NumberSettingPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class CreateTeamTest {
	
	CreateTeam createTeam;
	NumberSettingPage numberSettingPage;
	AddNumber addNumber;
	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	RegistrationPage registrationPage;
	PropertiesFile url;

	public CreateTeamTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		// driver.get(url.signIn());
		
		createTeam = PageFactory.initElements(driver, CreateTeam.class);
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
	
	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_create_Team_successfull() throws Exception {
		
		signupAndSignin();
		addNumberWithBronzePlan();
		
		createTeam.clickOnTeamLink();
		createTeam.clickOnCreateTeamLink();
		createTeam.enterTeamName("Team 1");
		createTeam.clickOnSimultaneouslyRedioButton();
		createTeam.selectUser1();
		createTeam.clickOnYesButton();
//		createTeam.selectUser2();
//		createTeam.clickOnYesButton();
		createTeam.clickOnCreateButton();
		createTeam.clickOnYesButton();
		
		createTeam.waitForvalidationMessage();
		String acutualSuccessfulValidationMsg = numberSettingPage.validationMessage();
		String expectedSuccessfulValidationMsg = "Team created successfully";
		assertEquals(acutualSuccessfulValidationMsg, expectedSuccessfulValidationMsg);
		
		
		
		
	}

}
