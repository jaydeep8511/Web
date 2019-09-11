package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.TestBase;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class LoginTest {

	LoginPage loginPage;
	WebDriver driver;
	static Utilitylib excel;
	PropertiesFile url;

	public LoginTest() throws Exception {
		 url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\login.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		//driver.get("https://staging-app.callhippo.com/#!/login");
		driver.get(url.signIn());

		loginPage = PageFactory.initElements(driver, LoginPage.class);
	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	@Test (priority = 1, retryAnalyzer=Retry.class)
	public void Verify_Forgot_Password_Link() {
		loginPage.clickOnForgotPasswordLink();
		String actualTitle = loginPage.validateforgotPasswordPageTitle();
		String expectedTitle = "Forgot Password | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 2, retryAnalyzer=Retry.class)
	public void VerifyTitle() {
		// driver.get("https://staging-app.callhippo.com/#!/forgotpassword");
		String actualResult = loginPage.getTitle();
		String expectedResult = "Login | Callhippo.com";
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 3, retryAnalyzer=Retry.class)
	public void Verify_Required_fields_validation() {

		loginPage.enterEmail("TestEmail");
		loginPage.clearEmail();

		loginPage.enterPassword("TestPassword");
		loginPage.clearPassword();

		String actualEmailValidationMsg = loginPage.requiredEmailMessage();
		String expectedEmailValidationMsg = "Email is required.";
		assertEquals(actualEmailValidationMsg, expectedEmailValidationMsg);
		System.out.println("email requirecompleted");

		String actualPasswordValidationMsg = loginPage.requiredPasswordMessage();
		String expectedPasswordValidationMsg = "Password is required.";
		assertEquals(actualPasswordValidationMsg, expectedPasswordValidationMsg);
		System.out.println("password requirecompleted");

	}

	@Test(priority = 4, retryAnalyzer=Retry.class)
	public void Verify_button_is_disabled_with_blank_Email_and_password_combination() {

		for (int i = 1; i <= 3; i++) {

			// blank email and password
			if (i == 1) {

				boolean actualResut = loginPage.IsdisabledLoginButton();
				boolean expectedResut = true;
				assertEquals(actualResut, expectedResut);
				System.out.println("loop " + i);
			}

			// valida email and blank password
			if (i == 2) {
				loginPage.clearEmail();
				loginPage.clearPassword();
				loginPage.enterEmail("jayadip+automationt1@callhippo.com");
				boolean actualResut = loginPage.IsdisabledLoginButton();
				boolean expectedResut = true;
				assertEquals(actualResut, expectedResut);
				System.out.println("loop " + i);
			}

			// blank email and valid password
			if (i == 3) {
				loginPage.clearEmail();
				loginPage.clearPassword();
				loginPage.enterPassword("12345678");
				boolean actualResut = loginPage.IsdisabledLoginButton();
				boolean expectedResut = true;
				assertEquals(actualResut, expectedResut);
				System.out.println("loop " + i);
			}

		}

	}

	@DataProvider(name = "EmailValidation")
	public static Object[][] invalidEmails() {

		return new Object[][] { { excel.getdata(0, 2, 2) }, { excel.getdata(0, 2, 3) }, { excel.getdata(0, 2, 4) },
				{ excel.getdata(0, 2, 5) }, { excel.getdata(0, 2, 6) } };

	}

	@Test(priority = 5, dataProvider = "EmailValidation", retryAnalyzer=Retry.class)
	public void verify_email_validation_with_Invalid_email(String email) {
		loginPage.enterEmail(email);
		String actualResult = loginPage.emailValidation();
		String expectedResult = "Enter a valid email.";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 6, retryAnalyzer=Retry.class)
	public void verify_with_invalid_email_and_invalid_password() {
		loginPage.enterEmail(excel.getdata(0, 5, 2));
		loginPage.enterPassword(excel.getdata(0, 6, 2));
		loginPage.clickOnLogin();
		String actualResult = loginPage.loginValidation();
		String expectedResult = "This email doesn't exist";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 7, retryAnalyzer=Retry.class)
	public void verify_with_valid_email_and_invalid_password() {
		loginPage.enterEmail(excel.getdata(0, 5, 3));
		loginPage.enterPassword(excel.getdata(0, 6, 3));
		loginPage.clickOnLogin();
		String actualResult = loginPage.loginValidation();
		String expectedResult = "Incorrect email or password";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 8, retryAnalyzer=Retry.class)
	public void verify_with_invalid_email_and_valid_password() {
		loginPage.enterEmail(excel.getdata(0, 5, 4));
		loginPage.enterPassword(excel.getdata(0, 6, 4));
		loginPage.clickOnLogin();
		String actualResult = loginPage.loginValidation();
		String expectedResult = "This email doesn't exist";
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 9, retryAnalyzer=Retry.class)
	public void verify_with_valid_email_and_valid_password() {
		loginPage.enterEmail(excel.getdata(0, 5, 5));
		loginPage.enterPassword(excel.getdata(0, 6, 5));
		loginPage.clickOnLogin();
		
		String actualResult = loginPage.loginSuccessfully();
		String expectedResult = "Dashboard | Callhippo.com";
		assertEquals(actualResult, expectedResult);
	}

	
}
