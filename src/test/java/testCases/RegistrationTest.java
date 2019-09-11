package testCases;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.TestBase;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class RegistrationTest {
	RegistrationPage registrationPage;
	WebDriver driver;
	static Utilitylib excel;
	PropertiesFile url;

	public RegistrationTest() throws Exception {
		 url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws Exception {
		
		driver = TestBase.init();
		driver.get(url.signUp());

		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);

	}

	@Test(priority = 1, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void VerifyTitle() {
		String actualResult = registrationPage.getTitle();
		String expectedResult = "Signup | Callhippo.com";

		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 2, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void verifyFunllNameFieldIsRequired() {
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 4, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		boolean btn = registrationPage.signupIsClickable();
		assertEquals(btn, true);

	}

	@Test(priority = 4, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void verify_Number_IsRequired() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterEmail(excel.getdata(0, 4, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));

		boolean btn = registrationPage.signupIsClickable();
		assertEquals(btn, true);

	}

	@Test(priority = 5, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void verify_Email_IsRequired() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));

		boolean btn = registrationPage.signupIsClickable();
		assertEquals(btn, true);

	}

	@Test(priority = 6, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void verify_password_IsRequired() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 4, 2));

		boolean btn = registrationPage.signupIsClickable();
		assertEquals(btn, true);

	}

	@Test(priority = 7, groups = "Signup-Required fields validation", retryAnalyzer=Retry.class)
	public void verify_with_filled_all_Filled_IsEnabled() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 4, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		boolean btn = registrationPage.signupIsClickable();
		assertEquals(btn, false);
	}

	@DataProvider(name = "FullNameValidation")
	public static Object[][] lessThan5CharectersOfFullName() {

		return new Object[][] { { excel.getdata(0, 7, 2) }, { excel.getdata(0, 7, 3) }, { excel.getdata(0, 7, 4) },
				{ excel.getdata(0, 7, 5) } };

	}

	@Test(priority = 8, dataProvider = "FullNameValidation", retryAnalyzer=Retry.class)
	public void Verify_fullName_validation(String fullname) {
		registrationPage.enterFullname(fullname);
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 4, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		registrationPage.clickOnSignupButton();
		boolean actualResult = registrationPage.fullNameValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);

	}

	@DataProvider(name = "NumberValidation")
	public static Object[][] invalidMobleNumbers() {

		return new Object[][] { { excel.getdata(0, 8, 2) }, { excel.getdata(0, 8, 3) }, { excel.getdata(0, 8, 4) } };

	}

	@Test(priority = 9, dataProvider = "NumberValidation", retryAnalyzer=Retry.class)

	public void verify_number_validation_with_Invalid_number(String number) {
		registrationPage.enterMobile(number);
		boolean actualResult = registrationPage.numberValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 10, retryAnalyzer=Retry.class)
	public void verify_number_validation_with_Valid_number() {
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		boolean actualResult = registrationPage.numberValidation();
		boolean expectedResult = false;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 11, retryAnalyzer=Retry.class)
	public void verifyFlag() {
		registrationPage.selectFlag();
		String actualresult = registrationPage.getFlagTitle();
		String expectedResult = "United Kingdom: +44";
		assertEquals(actualresult, expectedResult);
	}

	@DataProvider(name = "EmailValidation")
	public static Object[][] invalidEmails() {

		return new Object[][] { { excel.getdata(0, 9, 2) }, { excel.getdata(0, 9, 3) }, { excel.getdata(0, 9, 4) },
				{ excel.getdata(0, 9, 5) }, { excel.getdata(0, 9, 6) } };

	}

	@Test(priority = 12, dataProvider = "EmailValidation", retryAnalyzer=Retry.class)
	public void verify_email_validation_with_Invalid_email(String email) {
		registrationPage.enterEmail(email);
		boolean actualResult = registrationPage.emailValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 13, retryAnalyzer=Retry.class)
	public void verify_email_validation_with_valid_email() {
		registrationPage.enterEmail(excel.getdata(0, 4, 2));
		boolean actualResult = registrationPage.emailValidation();
		boolean expectedResult = false;
		// System.out.println(registrationPage.emailValidation());
		assertEquals(actualResult, expectedResult);
	}

	@DataProvider(name = "PasswordValidation")
	public static Object[][] invalidpasswords() {

		return new Object[][] { { excel.getdata(0, 10, 2) }, { excel.getdata(0, 10, 3) }, { excel.getdata(0, 10, 4) },
				{ excel.getdata(0, 10, 5) } };

	}

	@Test(priority = 14, dataProvider = "PasswordValidation", retryAnalyzer=Retry.class)
	public void verify_password_validation_with_Invalid_password(String pass) {
		registrationPage.enterPassword(pass);
		boolean actualResult = registrationPage.passwordValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 15, retryAnalyzer=Retry.class)
	public void verify_password_validation_with_Valid_password() {
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		boolean actualResult = registrationPage.passwordValidation();
		boolean expectedResult = false;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 16, retryAnalyzer=Retry.class)
	public void Verify_with_blocked_domain() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 11, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		registrationPage.clickOnSignupButton();
		boolean actualResult = registrationPage.blockedEmailValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 17, retryAnalyzer=Retry.class)
	public void Verify_with_registered_email() {
		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 12, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		registrationPage.clickOnSignupButton();
		boolean actualResult = registrationPage.registeredEmailValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 18, retryAnalyzer=Retry.class)
	public void Verify_createAccount() {

		registrationPage.enterFullname(excel.getdata(0, 1, 2));
		registrationPage.enterCompanyName(excel.getdata(0, 2, 2));
		registrationPage.enterMobile(excel.getdata(0, 3, 2));
		registrationPage.enterEmail(excel.getdata(0, 14, 2));
		registrationPage.enterPassword(excel.getdata(0, 5, 2));
		registrationPage.clickOnSignupButton();
		boolean actualResult = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResult = true;
//		 assertEquals(actualResult, expectedResult);
//		 driver.get("https://dev-app.callhippo.com/#!/signup");
		assertEquals(actualResult, expectedResult);
		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

	}
	
//	@Test(priority = 19)
//	public void verify_Link() {
//		registrationPage.ValidateMagicLinOnGmail();
//		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
//		boolean expectedVerifyMsg = true;
//		assertEquals(actualVerifyMsg, expectedVerifyMsg);
//
//	}

	@Test(priority = 20, retryAnalyzer=Retry.class)
	public void verifyExpiredLink() {
		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountLinkExpiredMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

	}
	
	
	@Test (enabled = false)
	public void Verify_createAccount1() {
		for(int i=4;i<=23;i++) {
			driver = TestBase.init();
			registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
		driver.get("https://staging-app.callhippo.com/#!/signup");
		registrationPage.enterFullname(excel.getdata(0, 1, i));
		registrationPage.enterCompanyName(excel.getdata(0, 2, i));
		registrationPage.enterMobile(excel.getdata(0, 3, i));
		registrationPage.enterEmail(excel.getdata(0, 4, i));
		registrationPage.enterPassword(excel.getdata(0, 5, i));
		registrationPage.clickOnSignupButton();
		boolean actualResult = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResult = true;
//		 assertEquals(actualResult, expectedResult);
//		 driver.get("https://dev-app.callhippo.com/#!/signup");
		assertEquals(actualResult, expectedResult);
//		registrationPage.ValidateVerifyButtonOnGmail();
//		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
//		boolean expectedVerifyMsg = true;
//		assertEquals(actualVerifyMsg, expectedVerifyMsg);
		System.out.println("loop "+i);
		driver.quit();
		}
	}

	
	
	

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

}
