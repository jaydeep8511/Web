package pages;

import static org.testng.Assert.expectThrows;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NSOpeningHours {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "//a[contains(.,'Always Opened')]") WebElement alwaysOpen;
	@FindBy(xpath = "//a[contains(.,'Always Closed')]")WebElement alwaysClose;
	@FindBy(xpath = "//a[text()='Custom']") WebElement custom;
	@FindBy(xpath = "//div[@ng-bind-html='vm.getTimezoneName(vm.module.timezone);']") WebElement timezoneText;
	@FindBy(xpath = "(//i[@class='fa fa-pencil'])[2]") WebElement editTimeZonePen;
	@FindBy(xpath="//select[@ng-model='vm.module.timezone']")WebElement timezoneDropdown;
	@FindBy(xpath = "//i[@class='fa fa-floppy-o']")WebElement saveImageButton;
	@FindBy(xpath = "//a[@class='linkButton'][contains(.,'Set the custom time from here')]") WebElement setCustomTimeLink;

	public NSOpeningHours(WebDriver driver) {
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
	
	public void clickOnAlwaysOpenButton() {
		wait.until(ExpectedConditions.visibilityOf(alwaysOpen));
		alwaysOpen.click();
	}
	
	public void clickOnAlwaysClosedButton() {
		wait.until(ExpectedConditions.visibilityOf(alwaysClose));
		alwaysClose.click();
	}
	
	public void clickOnCustomButton() {
		wait.until(ExpectedConditions.visibilityOf(custom));
		custom.click();
	}
	
	public String validateTimeZone() {
		wait.until(ExpectedConditions.visibilityOf(timezoneText));
		return timezoneText.getText();
	}
	
	public void clickOnEditTimezonePen() {
		wait.until(ExpectedConditions.visibilityOf(editTimeZonePen));
		editTimeZonePen.click();
	}
	
	public void SelectTimezone(String enterTimezone) {
		wait.until(ExpectedConditions.visibilityOf(timezoneDropdown));
		Select timezone = new Select(timezoneDropdown);
		timezone.selectByVisibleText(enterTimezone);
	}

	public void clickOnSaveImageButton() {
		wait.until(ExpectedConditions.visibilityOf(saveImageButton));
		saveImageButton.click();
	}
	
	public void clickOnSetTheCustomTimeLink() {
		wait.until(ExpectedConditions.visibilityOf(setCustomTimeLink));
		setCustomTimeLink.click();
	}
	
	public String validateCurrectPageTitle() {
		wait.until(ExpectedConditions.titleContains("Numbers - Custom Availability | Callhippo.com"));
		return driver.getTitle();
	}
	
	public void selectTime() {
		action.dragAndDrop(driver.findElement(By.xpath("//div[contains(text(),'01:00')]")), driver.findElement(By.xpath("//div[contains(text(),'05:00')]"))).build().perform();
		action.dragAndDropBy(driver.findElement(By.xpath("//div[contains(text(),'01:00')]")), 25, 50);
		
	
	}
}
