package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateTeam {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "//a[contains(.,'supervisor_account Teams')]") WebElement teamLink;
	@FindBy(xpath = "//button[contains(.,'Create Team')]") WebElement createTeamButton;
	@FindBy(xpath = "//input[@name='name']") WebElement teamName;
	@FindBy(xpath = "//label[@for='simultaneously']") WebElement simultaneouslyRedioButton;
	@FindBy(xpath = "(//li[@class='cascading_ct_li ng-scope'])[1]") WebElement user1;
	@FindBy(xpath = "(//li[@class='cascading_ct_li ng-scope'])[1]") WebElement user2;
	@FindBy(xpath = "//button[@ng-repeat='button in options.buttons track by button.label'][contains(.,'Yes')]") WebElement yesButton;
	@FindBy(xpath = "//button[@ng-repeat='button in options.buttons track by button.label'][contains(.,'No')]") WebElement noButton;
	@FindBy(xpath = "//button[contains(.,'Create')]") WebElement createButton;
	@FindBy(xpath="//span[@ng-bind-html='message.content'][contains(.,'Team created successfully')]") WebElement validationMessage;
	@FindBy(xpath = "//i[@id='g_reminder_alert_close']") WebElement addCreditPopup;
	
	
	public CreateTeam(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 20);
		action = new Actions(this.driver);
	}

	public void wait_angular5andjs() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		wait.until(ExpectedConditions.jsReturnsValue(
				"return angular.element(document).injector().get('$http').pendingRequests.length === 0"));
		wait.until(ExpectedConditions.jsReturnsValue("return jQuery.active==0"));

	}
	
	public void clickOnTeamLink() {
		wait.until(ExpectedConditions.visibilityOf(teamLink));
		teamLink.click();
	}
	
	public void clickOnCreateTeamLink() {
		wait.until(ExpectedConditions.visibilityOf(createTeamButton));
		createTeamButton.click();
	}
	
	public void enterTeamName(String teamName) {
		wait.until(ExpectedConditions.visibilityOf(this.teamName));
		this.teamName.sendKeys(teamName);
	}
	
	public void clickOnSimultaneouslyRedioButton() {
		wait.until(ExpectedConditions.visibilityOf(simultaneouslyRedioButton));
		simultaneouslyRedioButton.click();
	}
	
	public void selectUser1() {
		wait.until(ExpectedConditions.visibilityOf(user1));
		user1.click();
	}
	
	public void selectUser2() {
		wait.until(ExpectedConditions.visibilityOf(user2));
		user2.click();
	}
	
	public void clickOnYesButton() {
		wait.until(ExpectedConditions.visibilityOf(yesButton));
		yesButton.click();
	}
	
	public void clickOnNoButton() {
		wait.until(ExpectedConditions.visibilityOf(noButton));
		noButton.click();
	}
	
	public void clickOnCreateButton() {
		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(createButton));
		createButton.click();
		
		
		
	}
	
	public void waitForvalidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(validationMessage));
	
	}
	
	public void closeCreditPopup() {
		wait.until(ExpectedConditions.visibilityOf(addCreditPopup));
		addCreditPopup.click();
	}
}
