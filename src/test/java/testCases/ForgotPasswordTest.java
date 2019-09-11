package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.TestBase;
import junit.framework.Assert;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class ForgotPasswordTest {
	
	ForgotPasswordPage forgotPasswordPage;
	WebDriver driver;
	static Utilitylib excel;
	PropertiesFile url;
	
	public ForgotPasswordTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Forgot Password.xlsx");
	}

	
	@BeforeMethod
	public void initialization() {
		
		driver = TestBase.init();
		//driver.get("https://staging-app.callhippo.com/#!/forgotpassword");
		forgotPasswordPage = PageFactory.initElements(driver, ForgotPasswordPage.class);
		

	}
	
	@AfterMethod
	public void teardown() {
		// driver.quit();
	}
	
	
	@Test (priority = 2, retryAnalyzer=Retry.class) 
	public void VerifyTitle() throws IOException {
		driver.get(url.forgotPassword());
		String actualResult = forgotPasswordPage.getTitle();
		String expectedResult = "Forgot Password | Callhippo.com.";
		assertEquals(actualResult, expectedResult);
	}
	
	@DataProvider(name = "EmailValidation")
	public static Object[][] invalidEmails() {

		return new Object[][] { { excel.getdata(0, 1, 2) }, { excel.getdata(0, 1, 3) }, { excel.getdata(0, 1, 4) },
				{ excel.getdata(0, 1, 5) }, { excel.getdata(0, 1, 6) } };

	}

	@Test( priority = 3, dataProvider = "EmailValidation", retryAnalyzer=Retry.class)
	public void verify_email_validation_with_Invalid_email(String email) throws IOException {
		driver.get(url.forgotPassword());
		forgotPasswordPage.enterEmail(email);
		boolean actualResult = forgotPasswordPage.emailValidation();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}
	
	@Test (priority = 4, retryAnalyzer=Retry.class)
	public void Verify_Email_is_required_field() throws IOException {
		driver.get(url.forgotPassword());
		forgotPasswordPage.clickOnResetPassword();
		String actualResult = forgotPasswordPage.ValidateEmailRequiredField();
		String expectedResult = "Email is required";
		assertEquals(actualResult, expectedResult);
		
	}
	
	@Test (priority = 5, retryAnalyzer=Retry.class)
	public void Verify_with_non_registered_email() throws IOException {
		driver.get(url.forgotPassword());
		forgotPasswordPage.enterEmail(excel.getdata(0, 2, 2));
		forgotPasswordPage.clickOnResetPassword();
		String actualResult = forgotPasswordPage.ValidateEmailDoesnotExist();
		String expectedResult = "Email does not exist";
		assertEquals(actualResult, expectedResult);
		
	}
	
	@Test (priority = 6, retryAnalyzer=Retry.class)
	public void Verify_with_registered_email() throws IOException {
		driver.get(url.forgotPassword());
		//forgotPasswordPage.enterFullName("Test Auto");
		forgotPasswordPage.enterEmail(excel.getdata(0, 3, 2));
		forgotPasswordPage.clickOnResetPassword();
		String actualResult = forgotPasswordPage.validateWithRegisteredEmail();
		String expectedResult = "An email has been sent to reset your password";
		assertEquals(actualResult, expectedResult);
		
	}
	
	@Test (priority = 1, dependsOnMethods = {"Verify_with_registered_email"}, retryAnalyzer=Retry.class)
	public void verify_reset_password_link_From_gmail() {
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
	}
	
	@Test (priority = 7, retryAnalyzer=Retry.class)
	public void verify_Back_Signin_link() throws IOException {
		driver.get(url.forgotPassword());
		String actualTitle =forgotPasswordPage.validateSigninLink();
		String expectedTitle = "Login | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
	}
	
	
	@Test ( priority = 8, retryAnalyzer=Retry.class)//, dependsOnMethods = { "verify_reset_password_link_From_gmail" })
	public void reset_password_without_entering_any_value_in_reset_password_page() throws IOException {
		verify_reset_password_link_From_gmail();
		//driver.get(url.forgotPassword());
		boolean actualResutl = forgotPasswordPage.submitIsClickable();
		boolean expectedResult = true;
		assertEquals(actualResutl, expectedResult);
	}


	
	
	@DataProvider(name = "mismatchPasswordValidation")
	public static Object[][] mismatchpasswords() {

		return new Object[][] { 
			{ excel.getdata(0, 6, 2), excel.getdata(0, 7, 2) }, 
			{ excel.getdata(0, 6, 3), excel.getdata(0, 7, 3) },
			{ excel.getdata(0, 6, 4), excel.getdata(0, 7, 4) },
			{ excel.getdata(0, 6, 5), excel.getdata(0, 7, 5) } };

	}
	
	@Test (priority = 9, dataProvider = "mismatchPasswordValidation", retryAnalyzer=Retry.class)
	public void mismatch_Reset_Password_Validation(String password, String confirmPassword) throws IOException {
		driver.get(url.forgotPassword());
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
		forgotPasswordPage.enterFullName("Test Auto");
		forgotPasswordPage.enterPassword(password);
		forgotPasswordPage.enterConfirmPassword(confirmPassword);
		forgotPasswordPage.clickOnSubmitButton();
		String actualResult = forgotPasswordPage.mismatchResetPasswordValidation();
		String expectedResult = "Password and confirm password does not matched";
		assertEquals(actualResult, expectedResult);
		
	}
	
	
	
	@Test (priority = 10, retryAnalyzer=Retry.class)
	public void short_Password_Validation() {
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
		
		for(int col=2;col<=5;col++) {	
			
				forgotPasswordPage.enterPassword(excel.getdata(0, 10, col));
				forgotPasswordPage.enterConfirmPassword(excel.getdata(0, 11, col));				
				
				String actualResult = forgotPasswordPage.validateShortPassword();
				String expectedResult = "Password is too short.";
				assertEquals(actualResult, expectedResult);
				
				String actualResult1 = forgotPasswordPage.validateShortConfirmPassword();
				String expectedResult1 = "Confirm password is too short.";
				assertEquals(actualResult1, expectedResult1);			
			
		}	

		
	}
	
	
	@Test(priority = 11, dependsOnMethods = {"Verify_with_registered_email"}, retryAnalyzer=Retry.class)
	public void reset_Password_successfully() { 
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
		forgotPasswordPage.enterFullName("Test Auto");
		forgotPasswordPage.enterPassword(excel.getdata(0, 14, 2));
		forgotPasswordPage.enterConfirmPassword(excel.getdata(0, 15, 2));
		forgotPasswordPage.clickOnSubmitButton();
		String actualResult = forgotPasswordPage.ValidateResetPasswordSuccessfully();
		String expectedResult = "Your password has been reset successfully";
		assertEquals(actualResult, expectedResult);
		
	}
	
	@Test(priority = 12, dependsOnMethods = {"reset_Password_successfully"}, retryAnalyzer=Retry.class)
	public void reset_Password_twice() {
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
		forgotPasswordPage.enterFullName("Test Auto");
		forgotPasswordPage.enterPassword(excel.getdata(0, 14, 2));
		forgotPasswordPage.enterConfirmPassword(excel.getdata(0, 15, 2));
		forgotPasswordPage.clickOnSubmitButton();
		String actualResult = forgotPasswordPage.validateexpiredResetPasswordLinkErrorMessage();
		String expectedResult = "Sorry this password reset request has expired. Please request for a new reset link.";
		assertEquals(actualResult, expectedResult);
		
	}
		
	@Test(priority = 11, dependsOnMethods = {"Verify_with_registered_email"}, retryAnalyzer=Retry.class)
	public void reset_Password_and_login_successfully() { 
		forgotPasswordPage.ValidateResetPasswordButtonOnGmail();
		forgotPasswordPage.enterFullName("Test Auto");
		forgotPasswordPage.enterPassword(excel.getdata(0, 14, 2));
		forgotPasswordPage.enterConfirmPassword(excel.getdata(0, 15, 2));
		forgotPasswordPage.clickOnSubmitButton();
		String actualResult = forgotPasswordPage.ValidateResetPasswordSuccessfully();
		String expectedResult = "Your password has been reset successfully";
		assertEquals(actualResult, expectedResult);
		
		
		
	}
	


}
