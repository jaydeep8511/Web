package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NSMusicAndMessages {
	
	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "(//div[@id='music']//div[@class='form-group settingsectiondiv_pad']//span[@class='lever cttoggel'])[1]") WebElement welcomeMessageToggle;
	@FindBy(xpath = "//input[@name='welcomeAudioText']") WebElement welcomeMessageText;
	@FindBy(xpath = "//span[@class='help-block']") WebElement requireFieldMessage;
	@FindBy(xpath="//span[@ng-bind-html='message.content'][contains(.,'Special characters are not allowed.')]") WebElement specialCharactersValidationMsg;
	@FindBy(xpath = "(//div[@id='music']//div[@class='form-group settingsectiondiv_pad']//div[@class='welcomemessage_content']//button[contains(.,'Save')])[1]")WebElement welcomeSaveButton;
	@FindBy(xpath = "(//select[@id='ivrSelection'])[1]") WebElement welcomeLanguageDropdown;
	@FindBy(xpath = "//label[@for='womanWelcomeMessage']") WebElement welcomeFemaleVoice;
	@FindBy(xpath = "//label[@for='manWelcomeMessage']") WebElement welcomeManVoice;
	@FindBy(xpath = "//label[@for='messageInfoType2']") WebElement musicRadioButton;
	@FindBy(xpath = "(//span[@class='ng-binding'][contains(.,'Upload File')])[1]") WebElement musicUploadFile;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Your file has been uploaded successfully')]")WebElement fileuploadMessage;
	@FindBy(xpath = "//span[@class='help-block fileValidationColor'][contains(.,'File is too large, max size is 5MB.')]") WebElement musicFileErrorMessage;
	
	@FindBy(xpath = "(//div[@id='music']//div[@class='form-group settingsectiondiv_pad']//span[@class='lever cttoggel'])[2]") WebElement voicemailToggle; 
	@FindBy(xpath = "//input[@name='voiceMailText']") WebElement voicemailTextbox;
	@FindBy(xpath = "(//div[@id='music']//div[@class='form-group settingsectiondiv_pad']//div[@class='welcomemessage_content']//button[contains(.,'Save')])[2]")WebElement voicemailSaveButton;
	@FindBy(xpath = "//button[@class='upgraderecordbtn']") WebElement SelectPlanButton;
	@FindBy(xpath = "(//select[@id='ivrSelection'])[2]") WebElement voicemailLanguageDropdown;
	@FindBy(xpath = "//label[@for='womanVoiceMail']") WebElement voicemailFemaleVoice;
	@FindBy(xpath = "//label[@for='manVoiceMail']") WebElement voicemailMaleVoice;
	@FindBy(xpath = "//a[@class='rcclose-modal']/*[1]") WebElement closeSelectPlanPopup;
	@FindBy(xpath = "//label[@for='voiceMailType2']") WebElement voicemailAudio;
	@FindBy(xpath = "(//span[@class='ng-binding'][contains(.,'Upload File')])[1]") WebElement audioUploadFile;
	@FindBy(xpath = "//span[@name='VoiceMailFile']") WebElement noFileUploaded;
	
	@FindBy(xpath = "//a[@ng-click='vm.ctPlan()']") WebElement transcriptionUpdrade;
	@FindBy(xpath = "(//div[@id='music']//div[@class='form-group settingsectiondiv_pad']//span[@class='lever cttoggel'])[3]") WebElement transcriptionToggle;

	@FindBy(xpath = "//span[@class='ng-binding'][contains(.,'Upload File')]") WebElement callHoldUploadFileButton;
	@FindBy(xpath = "//span[contains(@name,'callHoldFile')]") WebElement thereIsNoAudio;
	
	
	public NSMusicAndMessages(WebDriver driver) {
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
	
	public void clickOnWelcomeMessageToggle() {
		welcomeMessageToggle.click();
	}
	
	public void clearWelcomeMessageText() {
		welcomeMessageText.clear();
	}
	
	public void enterWelcomeMessage(String messageText) {
		welcomeMessageText.sendKeys(messageText);
	}
	
	public String validateRequiredFieldValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(requireFieldMessage));
		return requireFieldMessage.getText();
	}
	
	public void waitUntilSpecialCharacterValidationMessage() {
		wait.until(ExpectedConditions.visibilityOf(specialCharactersValidationMsg));
	}
	
	public void clickOnWelcomeSaveButton() {
		welcomeSaveButton.click();
	}
	
	public void selectLanguageFromWelcomeLanguageDropdown(String language) {
		Select lan = new Select(welcomeLanguageDropdown);
		lan.selectByVisibleText(language);
	}
	
	public void clickOnFemaleVoiceForWelcomeMessage() {
		welcomeFemaleVoice.click();
	}
	public void clickOnMaleVoiceForWelcomeMessage() {
		welcomeManVoice.click();
	}
	

	public void clickOnMusicRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(musicRadioButton));
		musicRadioButton.click();
	}
	
	public void clickOnUploadFileForMusic() {
		wait.until(ExpectedConditions.visibilityOf(musicUploadFile));
		musicUploadFile.click();
	}
	
	public void waitUntilFileUploadedMessageDisplay() {
		wait.until(ExpectedConditions.visibilityOf(fileuploadMessage));
	}
	
	public String validateMusicFileErrorMessage() {
		wait.until(ExpectedConditions.visibilityOf(musicFileErrorMessage));
		return musicFileErrorMessage.getText();
	}
	
	public void clickOnVoicemailToggle() {
		voicemailToggle.click();
	}
	
	
	public void clearVoicemailText() {
		voicemailTextbox.clear();
	}
	
	public void enterVoicemailMessage(String messageText) {
		voicemailTextbox.sendKeys(messageText);
	}
	
	public String getVoicemailText() {
		wait.until(ExpectedConditions.visibilityOf(voicemailTextbox));
		return voicemailTextbox.getText();
	}
	
	public void clickOnVoicemailSaveButton() {
		voicemailSaveButton.click();
	}
	
	public void clickOnSelectPlanButton() {
		wait.until(ExpectedConditions.visibilityOf(SelectPlanButton));
		SelectPlanButton.click();
	}
	
	public boolean validateSelectPlanPopupIsDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(SelectPlanButton));
		return SelectPlanButton.isDisplayed();
	}
	
	public void selectLanguageForVoicemailLanguageDropdown(String language) {
		Select lan = new Select(voicemailLanguageDropdown);
		lan.selectByVisibleText(language);
	}
	
	public void clickOnFemaleVoiceForVoicemail() {
		voicemailFemaleVoice.click();
	}
	
	public void clickOnMaleVoiceForVoicemail() {
		voicemailMaleVoice.click();
	}
	
	public void closeSelectPlanPopup() {
		wait.until(ExpectedConditions.visibilityOf(closeSelectPlanPopup));
		closeSelectPlanPopup.click();
	}
	
	public void clickOnVoicemailAdioRadioButton() {
		wait.until(ExpectedConditions.visibilityOf(voicemailAudio));
		voicemailAudio.click();
	}
	
	public void clickOnAudioUploadFile() {
		wait.until(ExpectedConditions.visibilityOf(audioUploadFile));
		audioUploadFile.click();
	}
	
	public String validateFileNotUploaded() {
		wait.until(ExpectedConditions.visibilityOf(noFileUploaded));
		return noFileUploaded.getText();
	}
	
	public boolean ValidateVoicemailTranscriptionUpgradeButtonIsDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(transcriptionUpdrade));
		return transcriptionUpdrade.isDisplayed();
	}
	
	public void clickOnUpgradeButtonOfVoiceMailTranscription() {
		transcriptionUpdrade.click();
	}
	
	public void clickOnTranscriptionToggle() {
		transcriptionToggle.click();
	}
	
	public void clickOnCallHoldFileUploadButton() {
		wait.until(ExpectedConditions.visibilityOf(callHoldUploadFileButton));
		callHoldUploadFileButton.click();
	}
	
	public String validateThereIsNoAudioText() {
		wait.until(ExpectedConditions.visibilityOf(thereIsNoAudio));
		return thereIsNoAudio.getText();
	}
}
