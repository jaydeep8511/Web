package pages;

import static org.testng.Assert.expectThrows;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class User1PhoneApp {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "//input[@id='email']") WebElement email;
	@FindBy(xpath = "//input[@id='password']") WebElement password;
	@FindBy(xpath = "//input[@id='button-call']") WebElement loginButton;
	@FindBy(xpath = "//input[@id='telNumber']")WebElement enterNumber;
	@FindBy(xpath = "//i[@class='icsize material-icons']/ancestor::div[@class='callbtn_green']") WebElement dial;
	
	public User1PhoneApp(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 10);
		action = new Actions(this.driver);
	}
	
	public void wait_angular5andjs() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		wait.until(ExpectedConditions.jsReturnsValue(
				"return angular.element(document).injector().get('$http').pendingRequests.length === 0"));
		wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active==0"));

	}
	
	public void enterEmail(String email) {
		this.email.sendKeys(email);
	}
	
	public void enterPassword(String password) {
		this.password.sendKeys(password);
	}
	
	public void clickOnLoginButton() {
		loginButton.click();
		validateDialerHomepageOpened();
	}
	
	public void validateDialerHomepageOpened() {
		wait_angular5andjs();
		wait.until(ExpectedConditions.titleContains("Dial | Callhippo.com"));
	}
	
	
	public void enterNumber(String enterNumber) {
		this.enterNumber.sendKeys(enterNumber);
		wait_angular5andjs();
	}
	
	public void makeAcall() {
		wait.until(ExpectedConditions.visibilityOf(dial));
		dial.click();
		wait_angular5andjs();
	}
	
}
