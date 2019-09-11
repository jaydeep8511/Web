package pages;

import static org.testng.Assert.expectThrows;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NumberSettingPage {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "(//button[@class='circlest btn btn-sm btn-info'])[1]") WebElement settingIcon;
	@FindBy(xpath = "(//button[@class='circledelete btn btn-sm btn-danger'])[1]") WebElement deleteIcon;
	@FindBy(xpath="//div[@class='deparmentname_title']/span[@class='ng-binding']") WebElement number;
	@FindBy(xpath = "//button[@class='circlest btn btn-sm btn-info']") WebElement editName;
	@FindBy(xpath = "(//span[@class='ng-binding ng-scope'])[2]") WebElement getname;
	@FindBy(xpath = "//input[@id='contactName']") WebElement departmentNameTextbox;
	@FindBy(xpath = "//span[@ng-bind-html='message.content']") WebElement validationMessage;
	@FindBy(xpath = "//div[@class='btn-group']/a[@ng-click='vm.saveName(vm.number.contactName);']") WebElement saveButton;
	@FindBy(xpath = "//span[contains(@class,'helperror_block')]") WebElement requiredFieldMsg;
	@FindBy(xpath = "//div[@id='deparment']//div[@class='form-group settingsectiondiv_pad']//div[@class='panelnumbername']//div[@class='welcomemessage_nopad']//div//span[@class='lever cttoggel']") WebElement showCahllhippoToggle;
	@FindBy(xpath = "//span[@ng-show='true']") WebElement numberVerification;
	@FindBy(xpath = "//a[contains(@title,'Please upgrade your plan to enable call recording')]") WebElement callRecordingUpgrade;
	@FindBy(xpath = "//li[@class='ch_pointercls ng-scope']//a[contains(text(),'Numbers')]")WebElement clickOnNumber;
	@FindBy(xpath = "//div[@class='line-switch-switch chrecording']//span[@class='lever cttoggel']") WebElement callRecordingToggle;
	
	
	
	public NumberSettingPage(WebDriver driver) {
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

	public String getTitle(String title) {
		wait.until(ExpectedConditions.titleContains(title));
		String homepageTitle = driver.getTitle();
		return homepageTitle;
	}

	public boolean validateSettingIcon() {
		return settingIcon.isDisplayed();
	}
	
	public boolean validateDeleteIcon() {
		return deleteIcon.isDisplayed();
	}

	public String verifyNumberSettingPage() {
		wait.until(ExpectedConditions.titleContains("Number Settings | Callhippo.com"));
		return driver.getTitle();
	}
	
	public String getNumber() {
		return number.getText();
	}
	
	public void clickOnEditNameIcon() {
		editName.click();
	}
	
	public String getDepartmentName() {
		return getname.getText();
	}
	
	public void clearDepartmentNameTextBox() {
		departmentNameTextbox.clear();
	}
	
	public void enterDepartmentName(String DepartmentName) {
		departmentNameTextbox.sendKeys(DepartmentName);
	}
	
	public String validationMessage() {
		wait.until(ExpectedConditions.visibilityOf(validationMessage));
		return validationMessage.getText();
		
	}
	
	public void invisiblityOfValidationMessage() {
		wait.until(ExpectedConditions.invisibilityOf(validationMessage));
		// validationMessage.getText(); 
		
	}
	
	public void clickOnSaveButton() {
		wait.until(ExpectedConditions.visibilityOf(saveButton));
		saveButton.click();
	}
	
	public String requiredFieldValidationMessage() {
		return requiredFieldMsg.getText();
	}
	
	public void waitUntilSuccessfulValidationMsgDisplay() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@ng-bind-html='message.content'][contains(.,'Number updated successfully')]"))));
		
	}

	public void waitUntilErrorValidationMsgDisplay() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@ng-bind-html='message.content'][contains(.,'Special characters are not allowed.')]"))));
		
	}
	
	public void clickOnShowCallhippoToggle() {
		showCahllhippoToggle.click();
		wait_angular5andjs();
	}
	
	public String validateNumberVerification() {
		wait.until(ExpectedConditions.visibilityOf(numberVerification));
		return numberVerification.getText();
	}
	
	public String validateCallrecordingUpgradeButton() {
		return callRecordingUpgrade.getText();
	}
	
	public void clickOnCallRecordingUpgradeButton() {
		callRecordingUpgrade.click();
		wait_angular5andjs();
	}
	
	public void clickOnNumberLink() {
		wait.until(ExpectedConditions.visibilityOf(clickOnNumber));
		clickOnNumber.click();
	
	}
	
	public void clickOnCallRecordingToggle() {
		wait.until(ExpectedConditions.visibilityOf(callRecordingToggle));
		callRecordingToggle.click();
		wait_angular5andjs();
	}
	
	
	
}
