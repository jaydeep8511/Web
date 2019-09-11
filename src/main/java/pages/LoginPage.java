package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "//input[@id='email']") WebElement email;
	@FindBy(xpath = "//input[@id='password']") WebElement password;
	@FindBy(xpath = "//button[@id='web_login']") WebElement login;
	@FindBy(xpath = "//span[@class='help-block'][contains(.,'Email is required.')]") WebElement requiredEmail;
	@FindBy(xpath = "//span[@class='help-block'][contains(.,'Password is required.')]") WebElement requiredPassword;
	@FindBy(xpath ="//span[@class='help-block error-block'][contains(.,'Enter a valid email.')]") WebElement emailValidation;
	@FindBy(xpath ="//span[contains(@ng-bind-html,'message.content')]") WebElement credentialValidation;
	@FindBy(xpath = "//a[@href='#!/forgotpassword']") WebElement forgotPassword;
	
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 10);
		action = new Actions(this.driver);
	}
	
	public String getTitle() {
		wait.until(ExpectedConditions.titleContains("Login | Callhippo.com"));
		String signupTitle = driver.getTitle();
		return signupTitle;
	}
	
	public void clickOnForgotPasswordLink() {
		wait.until(ExpectedConditions.elementToBeClickable(forgotPassword));
		forgotPassword.click();
	}
	
	public String validateforgotPasswordPageTitle() {
		wait.until(ExpectedConditions.titleContains("Forgot Password | Callhippo.com"));
		return driver.getTitle();
	}
	
	public void enterEmail(String email) {
		this.email.clear();
		this.email.sendKeys(email);
	}
	
	public void enterPassword(String password) {
		this.password.clear();
		this.password.sendKeys(password);
	}
	
	public void clickOnLogin() {
		login.click();
	}
	
	public void clearEmail() {
		email.clear();
	}
	
	public void clearPassword() {
		password.clear();
	}
	
	public String requiredEmailMessage() {
		return requiredEmail.getText();
	}
	
	public String requiredPasswordMessage() {
		return requiredPassword.getText();
	}
	
	public boolean IsdisabledLoginButton() {
		
		try {
			driver.findElement(By.xpath("//button[@id='web_login'][@disabled='disabled']"));
			return true; 
		} catch (Exception e) {
			return false;
		}
	}
	
	public String emailValidation() {
			return emailValidation.getText();
	}
	
	public String loginValidation() {
		return credentialValidation.getText();
	}
	
	public String loginSuccessfully() {
		wait.until(ExpectedConditions.titleContains("Dashboard | Callhippo.com"));
		return driver.getTitle();
	}
	
	
	
	
}
