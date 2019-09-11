package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NSAllocation {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "(//span[@class='numberlist_country ng-binding'])[1]") WebElement user1;
	@FindBy(xpath = "(//span[@class='numberlist_country ng-binding'])[2]") WebElement user2;
	@FindBy(xpath = "//p[contains(@ng-if,'options.message')]") WebElement popupTexts;
	@FindBy(xpath = "//button[@ng-repeat='button in options.buttons track by button.label'][contains(.,'Yes')]") WebElement popupYes;
	@FindBy(xpath = "//button[@ng-repeat='button in options.buttons track by button.label'][contains(.,'No')]") WebElement popupNo;
	@FindBy(xpath = "(//span[@class='lever cttoggel'])[5]") WebElement allocationToggle;
	@FindBy(xpath = "//span[@class='numberlist_country teamNameSpan ng-binding']") WebElement team1;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Allocation is changed to team')]")WebElement teamSuccessMessage;
	@FindBy(xpath = "//i[@id='g_reminder_alert_close']") WebElement addCreditPopup;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Allocation is changed to team')]")WebElement teamAllocationMessage;
	public NSAllocation(WebDriver driver) {
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
	
	public void clickOnUser1() {
		wait.until(ExpectedConditions.visibilityOf(user1));
		user1.click();
	}
	
	public void clickOnUser2() {
		wait.until(ExpectedConditions.visibilityOf(user2));
		user2.click();
	}
	
	public String ValidatPopupText() {
		wait.until(ExpectedConditions.visibilityOf(popupTexts));
		return popupTexts.getText();
	}
	
	public void clickOnPopupYesButton() {
		wait.until(ExpectedConditions.visibilityOf(popupYes));
		popupYes.click();
	}
	
	public void clickOnPopupNoButton() {
		wait.until(ExpectedConditions.visibilityOf(popupNo));
		popupNo.click();
	}
	
	public void waitUntilSuccessfulValidationMsgDisplay() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@ng-bind-html='message.content'][contains(.,'User deallocated successfully')]"))));
		
	}

	public void waitUntilSuccessfulValidationMsgDisplayDeallocate() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@ng-bind-html='message.content'][contains(.,'User allocated successfully')]"))));
		
	}
	
	
	public void clickOnAllocationToggle() {
		wait.until(ExpectedConditions.visibilityOf(allocationToggle));
		allocationToggle.click();
	}
	
	public void selectTeam() {
		wait.until(ExpectedConditions.visibilityOf(team1));
		team1.click();
	}
	
	public void waitForTeamValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(teamSuccessMessage));
	}
	
	public void closeCreditPopup() {
		wait.until(ExpectedConditions.visibilityOf(addCreditPopup));
		addCreditPopup.click();
	}
	
	public void waitUntilTeamAllocationMessageDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(teamAllocationMessage));
	}
	
	
}
