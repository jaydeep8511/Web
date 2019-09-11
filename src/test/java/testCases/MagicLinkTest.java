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
import pages.MagicLink;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class MagicLinkTest {

	
	MagicLink magicLink;
	WebDriver driver;
	static Utilitylib excel;
	PropertiesFile url;
	
	public MagicLinkTest() {
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\MagicLink.xlsx");
	}

	
	@BeforeMethod
	public void initialization() throws Exception {
		url = new PropertiesFile();
		driver = TestBase.init();
		//driver.get("https://staging-app.callhippo.com/#!/forgotpassword");

		magicLink = PageFactory.initElements(driver, MagicLink.class);

	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	
	@Test (priority = 1, retryAnalyzer=Retry.class) 
	public void VerifyTitle() throws IOException {
		driver.get(url.forgotPassword());
		String actualResult = magicLink.getTitle();
		String expectedResult = "Forgot Password | Callhippo.com";
		assertEquals(actualResult, expectedResult);
		 
		
	}
	
	@Test (priority = 2, retryAnalyzer=Retry.class) 
	public void Verify_magicLink_button_enabled_by_clicking_on_checkbox() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.clickOMagicLinkCheckbox();
		String actualMagiclinkText = magicLink.IsMagicLinkButtonEnabled();
		String expectedMagiclinkText = "Send Magic Link";
		assertEquals(actualMagiclinkText, expectedMagiclinkText);
	}
	
	@Test (priority = 3, retryAnalyzer=Retry.class)
	public void Verify_Email_is_required_field() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		String actualResult = magicLink.emailValidationMsg();
		String expectedResult = "Email is required";
		assertEquals(actualResult, expectedResult);
		
	}
	
	@DataProvider(name = "EmailValidation") 
	public static Object[][] invalidEmails() {

		return new Object[][] { { excel.getdata(0, 1, 2) }, { excel.getdata(0, 1, 3) }, { excel.getdata(0, 1, 4) },
				{ excel.getdata(0, 1, 5) }, { excel.getdata(0, 1, 6) } };

	}

	@Test( priority = 4, dataProvider = "EmailValidation", retryAnalyzer=Retry.class)
	public void verify_email_validation_with_Invalid_email(String email) throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(email);
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		String actualResult = magicLink.emailValidationMsg();
		String expectedResult = "Email is required";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 5, retryAnalyzer=Retry.class) 
	public void verify_email_validation_with_nonRegistered_email() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 2, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		String actualResult = magicLink.emailValidationMsg();
		String expectedResult = "Email does not exist";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 6, retryAnalyzer=Retry.class) 
	public void verify_email_validation_with_Registered_email() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 3, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		String actualResult = magicLink.emailValidationMsg();
		String expectedResult = "Magic link is sent to your email";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test (priority = 7, dependsOnMethods = "verify_email_validation_with_Registered_email", retryAnalyzer=Retry.class)
	public void verify_MagicLink_on_Gmail_inbox() {
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.ggetTitle();
		String expectedTitle = "Dashboard | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
		
	}
	
	@Test (priority = 8, dependsOnMethods = "verify_email_validation_with_Registered_email", retryAnalyzer=Retry.class)
	public void verify_Expired_MagicLink_on_Gmail_inbox() throws IOException {
		verify_email_validation_with_Registered_email();
		magicLink.validateMagicLickExpired();
		String actualTitle = magicLink.validateErrormessage();
		String expectedTitle = "Your link is expired";
		assertEquals(actualTitle, expectedTitle);
	}
	
	
	
	@Test(priority = 9, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_blocked_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 6, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualResult = magicLink.validateErrormessageOfBlockedUser();
		String expectedResult = "Your account has been blocked due blocked for automation testing";
		assertEquals(actualResult, expectedResult);
	}
	
	@Test(priority = 10, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Unblocked_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 7, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.ggetTitle();
		String expectedTitle = "Dashboard | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
	}
	
	
	@Test(priority = 11, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Login_attempt_fail_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 8, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.validateErrormessage();
		String expectedTitle = "Account blocked due to multiple attempts, kindly check your email to unblock.";
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 12, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Cancel_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 9, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.ggetTitle();
		String expectedTitle = "Dashboard | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 13, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Reactive_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 10, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.ggetTitle();
		String expectedTitle = "Dashboard | Callhippo.co m";
		assertEquals(actualTitle, expectedTitle);
	}
	
	
	@Test(priority = 14, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Deleted_Main_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 11, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.validateErrormessage();
		String expectedTitle = "Your account has been deactivated. Please contact us at support@callhippo.com .";
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 15, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Deleted_sub_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 12, 3));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.validateErrormessage();
		String expectedTitle = "Your account has been deactivated. Please contact us at support@callhippo.com .";
		assertEquals(actualTitle, expectedTitle);
	}
	
	
	@Test(priority = 16, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Hard_Deleted_main_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 13, 2));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		magicLink.ValidateMagicLinOnGmail();
		String actualTitle = magicLink.validateErrormessage();
		String expectedTitle = "Your account has been permanently deleted upon request.";
		assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 17, retryAnalyzer=Retry.class) 
	public void verify_MagicLink_with_Hard_Deleted_sub_user() throws IOException {
		driver.get(url.forgotPassword());
		magicLink.enterEmail(excel.getdata(0, 14, 3));
		magicLink.clickOMagicLinkCheckbox();
		magicLink.clickOnMagicLink();
		String actualResult = magicLink.emailValidationMsg();
		String expectedResult = "Email does not exist";
		assertEquals(actualResult, expectedResult);
	}
}
