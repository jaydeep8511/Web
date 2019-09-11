package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NSivr {
	
	static WebDriver driver;
	WebDriverWait wait;
	Actions action;

	@FindBy(xpath = "//div[@id='ivr']//span[@class='lever cttoggel']")WebElement ivrToggle;
	@FindBy(xpath = "//i[contains(@class,'fa fa-pencil small_pencil')]") WebElement editMessagePen;
	@FindBy(xpath = "//textarea[@name='ivrMessage']") WebElement ivrMessageArea;
	@FindBy(xpath = "//select[@ng-model='vm.number.ivrLanguage']") WebElement ivrLanguageDropdown;
	@FindBy(xpath = "//label[@for='woman']") WebElement femaleVoiceRadioButton;
	@FindBy(xpath = "//label[@for='man']")WebElement maleVoiceRadioButton;
	@FindBy(xpath = "//input[@id='numtext']") WebElement pressdigit;
	@FindBy(xpath = "//select[@ng-model='ivr.ivrValue']")WebElement actionDropdown;
	@FindBy(xpath = "(//i[@class='material-icons addic new_btn_ic_size_ivr'][contains(.,'save')])[1]") WebElement saveIcon;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Please insert appropriate values for IVR.')]") WebElement pressValidationMessage;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Please Select Number or User.')]")WebElement actionValidationMessage;
	@FindBy(xpath = "//i[@class='material-icons addic new_btn_ic_size_ivr'][contains(.,'add')]")WebElement addIcon;
	@FindBy(xpath = "//label[@for='ivrmessageType2'][contains(.,'Music')]") WebElement musicRadioButton;
	@FindBy(xpath = "//label[@for='ivrmessageType1'][contains(.,'Text')]") WebElement textRadioButton;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Ivr feature is not available in Basic Plan. Please Upgrade your plan.')]") WebElement ivrValidationMessage;
	
	@FindBy(xpath = "//a[@href='#ivr']") WebElement ivrSection;
	
	@FindBy(xpath = "//span[@class='help-block']") WebElement requireFieldMessage;
	@FindBy(xpath="//span[@ng-bind-html='message.content'][contains(.,'Special characters are not allowed.')]") WebElement specialCharactersValidationMsg;
	
	public NSivr(WebDriver driver) {
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
	
	public void clickonIVRToggle() {
		wait.until(ExpectedConditions.visibilityOf(ivrToggle));
		ivrToggle.click();
	}
	
	public void clickOnEditMessagePen() {
		wait.until(ExpectedConditions.visibilityOf(editMessagePen));
		editMessagePen.click();
	}
	
	public void clearIVRMessage() {
		wait.until(ExpectedConditions.visibilityOf(ivrMessageArea));
		ivrMessageArea.clear();
	}
	
	public void enterIVRMessage(String ivrMessage) {
		wait.until(ExpectedConditions.visibilityOf(ivrMessageArea));
		ivrMessageArea.sendKeys(ivrMessage);
	}
	
	public void selectLanguageForIVRMessage(String visibleTexts) {
		Select lang = new Select(ivrLanguageDropdown);
		lang.selectByVisibleText(visibleTexts);
	}
	
	public void clickOnMaleRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(maleVoiceRadioButton));
		maleVoiceRadioButton.click();
	}
	
	public void clickOnfemaleRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(femaleVoiceRadioButton));
		femaleVoiceRadioButton.click();
	}
	
	public void enterPressDigits(String number) {
		wait.until(ExpectedConditions.visibilityOf(pressdigit));
		pressdigit.sendKeys(number);
	}
	
	public void clearPressDigits() {
		wait.until(ExpectedConditions.visibilityOf(pressdigit));
		pressdigit.clear();
	}
	
	public void selectValueFromActionDropdown(String visibleText) {
		Select actiondropdown = new Select(actionDropdown);
		actiondropdown.selectByVisibleText(visibleText);
	}
	
	public void clickOnSaveIcon() {
		wait.until(ExpectedConditions.visibilityOf(saveIcon));
		saveIcon.click();
	}
	
	public void waitForPressValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(pressValidationMessage));
	}
	
	public void waitForActionValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(actionValidationMessage));
	}
	
	public void clickOnAddIcon() {
		wait.until(ExpectedConditions.visibilityOf(addIcon));
		addIcon.click();
	}
	
	public void waitForDigitAddedSuccessfullyMessage(String digit) {
		String el = "//span[@ng-bind-html='message.content'][contains(.,'IVR for digit "+digit+" is added successfully')]";
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(el))));
	}
	
	public void clickOnMusicRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(musicRadioButton));
		musicRadioButton.click();
	}
	
	public void clickOnTextRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(textRadioButton));
		textRadioButton.click();
	}
	
	public void waitForIVRValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(ivrValidationMessage));
	}
	
	public void clickOnIVRSection() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(ivrSection));
		ivrSection.click();
		Thread.sleep(3000);
	}
	
	public String requireFieldValidationForMessage() {
		wait.until(ExpectedConditions.visibilityOf(requireFieldMessage));
		return requireFieldMessage.getText();
	}
	
	
	public void waitUntilSpecialCharacterValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(specialCharactersValidationMsg));
	}
	
}
