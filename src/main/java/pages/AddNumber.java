package pages;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddNumber {

	static WebDriver driver;
	WebDriverWait wait;
	WebDriverWait wait1;
	Actions action;

	@FindBy(xpath = "//button[@class='ngdialog-close']")
	WebElement closeDialog;
	@FindBy(xpath = "//a[@ui-sref='dummynumber']")
	WebElement numberLink;
	@FindBy(xpath = "//button[@href='#!/addNumber']")
	WebElement addNumberButton;
	@FindBy(xpath = "//label[contains(.,'Select Country')]")
	WebElement selectCountryLabel;
	@FindBy(xpath = "//input[@ng-model='vm.number.contactName']")
	WebElement numberName;
	@FindBy(xpath = "//div[@class='row is-flex fwidth topcountryrow_mrg']/div")
	WebElement topCountries;
	@FindBy(xpath = "(//h5[@class='ellipsis countrytitle ng-binding'][contains(.,'United States')])[1]")
	WebElement uscountry;
	@FindBy(xpath = "(//h5[@class='ellipsis countrytitle ng-binding'][contains(.,'United Kingdom')])[1]")
	WebElement ukcountry;
	@FindBy(xpath = "(//h5[@class='ellipsis countrytitle ng-binding'][contains(.,'Canada')])[1]")
	WebElement canadaCountry;
	@FindBy(xpath = "//button[contains(.,'Next')]")
	WebElement NextButton;
	@FindBy(xpath = "//button[contains(.,'Previous')]")
	WebElement previousButton;
	@FindBy(xpath = "//input[@placeholder='Enter a prefix or a number']")
	WebElement prefixNumberSearchBox;
	@FindBy(xpath = "//button[contains(.,'Search')]")
	WebElement searchButton;
	@FindBy(xpath = "//div[@class='alert alert-info customAlert ng-binding']")
	WebElement noSearchResultMsg;
	@FindBy(xpath = "//select[@ng-model='vm.numberSearch.searchBy']")
	WebElement searchByDropdown;
	@FindBy(xpath = "//button[@id='dropdownMenu1']")
	WebElement locationDropdown;
	@FindBy(xpath = "//input[contains(@placeholder,'Search')]")
	WebElement locationSearchBox;
	@FindBy(xpath = "//span[@ng-click='vm.searchLocation(city)'][contains(.,'London (203)')]")
	WebElement selectCity;

	@FindBy(xpath = "//div[@id='num-mon-1']")
	WebElement numMonthly;
	@FindBy(xpath = "//div[@id='num-annu-1']")
	WebElement numAnnualy;
	@FindBy(xpath = "//label[contains(.,'Monthly')]")
	WebElement monthly;
	@FindBy(xpath = "//label[contains(.,'Monthly')][@data-tooltip='your selected number is yearly subscription hence the plan must be yearly']")
	WebElement disabledMontly;

	@FindBy(xpath = "//a[@ui-sref='plan'][contains(.,'monetization_on Setting')]")
	WebElement setting;
	@FindBy(xpath = "//label[@id='label1']")
	WebElement bronzePlan;
	@FindBy(xpath = "//label[@id='label2']")
	WebElement silverPlan;
	@FindBy(xpath = "//label[@id='label3']")
	WebElement platinumPlan;
	@FindBy(xpath = "//label[@for='perio2']")
	WebElement montlyPlan;
	@FindBy(xpath = "//label[@for='perio1']")
	WebElement AnnuallyPlan;
	@FindBy(xpath = "//b[contains(text(),'Upgrade Plan')]")
	WebElement Upgradepran;
	@FindBy(xpath = "//b[contains(.,'Your plan has been upgraded successfully.')]")
	WebElement upgradeSuccessfullyMsg;
	@FindBy(xpath = "//div[@class='deparmentname_title rowtitle']/span[@class='ng-binding']")
	WebElement settingCredit;

	@FindBy(xpath = "//label[contains(.,'Annually')]")
	WebElement annually;
	@FindBy(xpath = "//a[@id='plan-select-1']")
	WebElement bronze;
	@FindBy(xpath = "//a[@id='plan-select-2']")
	WebElement silver;
	@FindBy(xpath = "//a[@id='plan-select-3']")
	WebElement platinum;
	@FindBy(xpath = "//button[@id='chkoutBtn']")
	WebElement checkout;

	@FindBy(xpath = "//a[@href='javascript:void(0);'][contains(.,'Skip')]")
	WebElement skipLink;
	@FindBy(xpath = "//div[@class='right num-pay-btn']/child::button[contains(.,'Pay')]")
	WebElement payButton;
	@FindBy(xpath = "//input[@type='checkbox'][@ng-model='user.isChecked']")
	WebElement userCheckbox;

	// checkout page xpath

	@FindBy(xpath = "//strong[contains(.,'Total')]/parent::div//following-sibling::div")
	WebElement totalAmount;
	@FindBy(xpath = "//input[@id='billing_address[first_name]']")
	WebElement cFirstName;
	@FindBy(xpath = "//input[@id='billing_address[last_name]']")
	WebElement cLastName;
	@FindBy(xpath = "//input[@id='billing_address[line1]']")
	WebElement cAddressLine1;
	@FindBy(xpath = "//input[@name='billing_address[line2]']")
	WebElement cAddressLine2;
	@FindBy(xpath = "//input[@id='billing_address[city]']")
	WebElement cCity;
	@FindBy(xpath = "//input[@name='billing_address[zip]']")
	WebElement cZip;
	@FindBy(xpath = "//select[@id='billing_address[country]']")
	WebElement cCountry;
	@FindBy(xpath = "//input[@id='card[number]']")
	WebElement cCardNumber;
	@FindBy(xpath = "//select[@id='card[expiry_month]']")
	WebElement cMonth;
	@FindBy(xpath = "//select[@id='card[expiry_year]']")
	WebElement cYear;
	@FindBy(xpath = "//input[@name='card[cvv]']")
	WebElement cCCV;
	@FindBy(xpath = "//input[@id='card.copy_billing_info.show']")
	WebElement cCheckbox;
	@FindBy(xpath = "//input[@type='submit']")
	WebElement cSubmit;

	@FindBy(xpath = "//button[contains(.,'Not now, I am done')]")
	WebElement notNowIamDone;
	@FindBy(xpath = "//button[contains(.,'Save')]")
	WebElement saveButton;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Number added successfully')]")
	WebElement savedNumberMsg;

	@FindBy(xpath = "//span[@class='caret']")
	WebElement myAccount;
	@FindBy(xpath = "//a[@href='javascript:void(0);'][contains(.,'Account Details')]")
	WebElement accountDetail;

	@FindBy(xpath = "(//td[@data-cb-invoice='Amount'])[1]")
	WebElement customerPortallatestPrice;

	@FindBy(xpath = "//button[@class='bluebtn waves-effect waves-light btn actnbtnwidnch_btn']")
	WebElement freeNumberAddButton;

	@FindBy(xpath = "//a[@class='numblsnamemarg ng-binding']")
	WebElement numberSetting;
	@FindBy(xpath = "//div[@id='deparment']")
	WebElement department;
	@FindBy(xpath = "//div[@id='callRecording']")
	WebElement callRecording;
	@FindBy(xpath = "//div[@id='music']")
	WebElement musicAndMessage;
	@FindBy(xpath = "//div[@id='openingHours']")
	WebElement openingHours;
	@FindBy(xpath = "//div[@id='userAllocation']")
	WebElement allocation;
	@FindBy(xpath = "//div[@id='ivr']")
	WebElement ivr;
	@FindBy(xpath = "//div[@id='extension']")
	WebElement extension;
	@FindBy(xpath = "//div[@id='callqueue']")
	WebElement callQueue;

	@FindBy(xpath = "//a[contains(text(),'Users')]")
	WebElement users;
	@FindBy(xpath = "(//a[@ng-if='userObj.userActive'])[1]")
	WebElement userSetting;
	@FindBy(xpath = "//span[@class='countrylist_numberselect ng-binding']")
	WebElement numberInUserSetting;

//	@FindBy(xpath = "//form[@id='users_login_submit']//input[@id='email']") WebElement chargebeEmail;
//	@FindBy(xpath = "//input[@id='password']") WebElement chargebeePassword;
//	@FindBy(xpath = "//input[@id='sign-in-submit']") WebElement chargebeeSigninButton;

	public AddNumber(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 20);
		wait1 = new WebDriverWait(this.driver, 30);
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

	public void closePopup() {
		wait_angular5andjs();
		action.sendKeys(Keys.ESCAPE).build().perform();
		wait_angular5andjs();
		closeDialog.click();
	}

	public void clickOnNumberLink() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}

		// wait_angular5andjs();
		wait.until(ExpectedConditions.elementToBeClickable(numberLink));
		numberLink.click();
	}

	public void clickOnAddNumberButton() {
		wait_angular5andjs();
		// wait.until(ExpectedConditions.elementToBeClickable(numberLink));
		addNumberButton.click();
	}

	public boolean isSelectCountryDisplay() {

		return selectCountryLabel.isDisplayed();
	}

	public void enterNameofNumber(String name) {
		numberName.sendKeys(name);
	}

	public void validateTopcountry() {

	}

	public int validateTop5CountriesCount() {
		return driver.findElements(By.xpath("//div[@class='row is-flex fwidth topcountryrow_mrg']/div")).size();
	}

	public boolean validateTop5CountriesNameAndCode() {

		int size = driver.findElements(By.xpath(
				"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
				.size();

		for (int i = 0; i < size; i++) {

			if (i == 0) {
				String actual = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
						.get(i).getText();
				String actualcode = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//div[@class='center countrycode ng-binding']"))
						.get(i).getText();

				String expected = "United States";
				String expectedcode = "1";
				if (actual.contentEquals(expected) && actualcode.contentEquals(expectedcode)) {
					return true;
				} else
					return false;

			}

			if (i == 1) {
				String actual = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
						.get(i).getText();
				String actualcode = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//div[@class='center countrycode ng-binding']"))
						.get(i).getText();

				String expected = "United Kingdom";
				String expectedcode = "44";
				if (actual.contentEquals(expected) && actualcode.contentEquals(expectedcode)) {
					return true;
				} else
					return false;

			}

			if (i == 2) {
				String actual = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
						.get(i).getText();
				String actualcode = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//div[@class='center countrycode ng-binding']"))
						.get(i).getText();

				String expected = "Canada";
				String expectedcode = "1";
				if (actual.contentEquals(expected) && actualcode.contentEquals(expectedcode)) {
					return true;
				} else
					return false;

			}

			if (i == 3) {
				String actual = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
						.get(i).getText();
				String actualcode = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//div[@class='center countrycode ng-binding']"))
						.get(i).getText();

				String expected = "Australia";
				String expectedcode = "61";
				if (actual.contentEquals(expected) && actualcode.contentEquals(expectedcode)) {
					return true;
				} else
					return false;

			}

			if (i == 4) {
				String actual = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//h5[@class='ellipsis countrytitle ng-binding']"))
						.get(i).getText();
				String actualcode = driver.findElements(By.xpath(
						"//div[@class='row is-flex fwidth topcountryrow_mrg']//div[@class='center countrycode ng-binding']"))
						.get(i).getText();

				String expected = "Germany";
				String expectedcode = "49";
				if (actual.contentEquals(expected) && actualcode.contentEquals(expectedcode)) {
					return true;
				} else
					return false;

			}

		}
		return false;

	}

	public void clickOnUSCountry() {
		uscountry.click();
	}

	public void clickOnUKCountry() {
		ukcountry.click();
	}

	public void clickOnCanadaCountry() {
		canadaCountry.click();
	}

	public boolean validateNumberList() {
		int size = driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']"))
				.size();
		if (size > 0) {
			return true;
		}
		return false;
	}

	public void selectNumber() {
		driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(0)
				.click();
	}

	public String getSeletedNumber() {
		return driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(0)
				.getText();
	}

	public String getNumeberAfterPurchased() {
		return driver.findElement(By.xpath("(//a[@class='numblsnamemarg ng-binding'])[1]")).getText();
	}

	public void selectNumber2() {
		driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(2)
				.click();
	}

	public boolean validateNumberListNextButton() {
		int size = driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']"))
				.size();
		NextButton.isDisplayed();
		if (size == 20) {
			if (NextButton.isDisplayed()) {
				NextButton.click();

				return true;
			}
		}
		return false;
	}

	public boolean validateNumberListPriviousButton() {
		int size = driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']"))
				.size();
		NextButton.isDisplayed();
		if (size == 20) {
			if (NextButton.isDisplayed()) {
				NextButton.click();
				if (previousButton.isDisplayed()) {
					previousButton.click();
					return true;
				}
			}
		}
		return false;
	}

	public void enterPrefixNumber(String prefixNumber) {

		prefixNumberSearchBox.sendKeys(prefixNumber);
	}

	public void clickOnSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
		wait_angular5andjs();
	}

	public String validatePrefixnumber() {
		wait_angular5andjs();
		String number = driver
				.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(0)
				.getText();
		return number.substring(2, 5);

	}

	public String validateNoResultFound() {
		wait_angular5andjs();
		return noSearchResultMsg.getText();
	}

	public void selectSearchByLocation() {
		Select dropdown = new Select(searchByDropdown);
		dropdown.selectByVisibleText("Location");
	}

	public void selectSearchByTollfree() {
		Select dropdown = new Select(searchByDropdown);
		dropdown.selectByVisibleText("Tollfree");
	}

	public void selectLocation(String enterArea) {
		wait_angular5andjs();
		locationDropdown.click();
		locationSearchBox.sendKeys(enterArea);
		wait_angular5andjs();
		wait.until(ExpectedConditions.elementToBeClickable(selectCity));
		selectCity.click();

	}

	public String validateLocationPrefixnumber() {
		wait_angular5andjs();
		String number = driver
				.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(0)
				.getText();
		return number.substring(3, 6);

	}

	public void selectVoiceNumber() {
		wait_angular5andjs();
		driver.findElements(By.xpath("//div[@class='row']//span[@class='numberlist_country ng-binding']")).get(0)
				.click();
	}

	public void selectNumberMontlyPlan() {
		wait.until(ExpectedConditions.visibilityOf(numMonthly));
		numMonthly.click();
	}

	public void clickOnSetting() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}

		wait_angular5andjs();
		wait.until(ExpectedConditions.elementToBeClickable(setting));
		setting.click();
	}

	public void selectUserMontlyPlan() {

		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(montlyPlan));
		montlyPlan.click();
	}

	public void selectUserAnuallyPlan() {

		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(AnnuallyPlan));
		AnnuallyPlan.click();
	}

	public void selectUserBronzePlan() {
		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(bronzePlan));
		bronzePlan.click();
	}

	public void selectUserSilverPlan() {
		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(silverPlan));
		silverPlan.click();
	}

	public void selectUserPlatinumPlan() {
		wait_angular5andjs();
		// wait.until(ExpectedConditions.visibilityOf(platinumPlan));
		platinumPlan.click();
	}

	public void clickOnUpgradeButton() {
		wait_angular5andjs();
		// wait.until(ExpectedConditions.visibilityOf(Upgradepran));
		Upgradepran.click();
	}

	public String validateUpdradeSuccessfullyMessage() {
		wait.until(ExpectedConditions.visibilityOf(upgradeSuccessfullyMsg));
		return upgradeSuccessfullyMsg.getText();

	}

	public String validateCredit() {
		return settingCredit.getText();
	}

	public boolean verifyMontlyDisabled() {
		return disabledMontly.isDisplayed();
	}

	public void selectNumberAnnualyPlan() {
		wait.until(ExpectedConditions.visibilityOf(numAnnualy));
		numAnnualy.click();
	}

	public void clickOnPayButton() {
		payButton.click();
	}

	public void selectMontly() {
		wait.until(ExpectedConditions.visibilityOf(monthly));
		monthly.click();
	}

	public void selectAnually() {
		wait.until(ExpectedConditions.visibilityOf(annually));
		annually.click();
	}

	public void selectBronze() {
		wait.until(ExpectedConditions.visibilityOf(bronze));
		bronze.click();
	}

	public void selectSilver() {
		wait.until(ExpectedConditions.visibilityOf(silver));
		silver.click();
	}

	public void selectPlatinum() {
		wait.until(ExpectedConditions.visibilityOf(platinum));
		platinum.click();
	}

	public void clickOnCheckoutButton() {
		wait.until(ExpectedConditions.visibilityOf(checkout));
		checkout.click();
	}

	public void clickOnSkipLink() {
		wait.until(ExpectedConditions.visibilityOf(skipLink));
		skipLink.click();
	}

	public String getTotalPrice() {
		return totalAmount.getText();
	}

	public void waitforCheckoutPage() {
		wait.until(ExpectedConditions.titleContains("Checkout"));
		// driver.getTitle();
	}

	public void filledCheckoutPage() {
		cFirstName.sendKeys("Test");
		cLastName.sendKeys("Automation");
		cAddressLine1.sendKeys("TestAddress1");
		cAddressLine2.sendKeys("TestAddress2");
		cCity.sendKeys("Ahmedabad");
		cZip.sendKeys("380015");

		Select country = new Select(cCountry);
		country.selectByValue("IN");

		cCardNumber.sendKeys("4111 1111 1111 1111");

		Select month = new Select(cMonth);
		month.selectByValue("12");

		Select year = new Select(cYear);
		year.selectByValue("2020");

		cCCV.sendKeys("100");

	}

	public void clickOnSubscripbeButton() {
		cSubmit.click();
	}

	public void waitForAddNumberPage() {
		wait1.until(ExpectedConditions.titleIs("Add Number | Callhippo.com"));
	}

	public void waitForSubscribePage() {
		wait.until(ExpectedConditions.titleContains("Subscribe | Callhippo.com"));
	}

	public void clickOnNotNowIamDone() {
		wait.until(ExpectedConditions.visibilityOf(notNowIamDone));
		notNowIamDone.click();
	}

	public boolean validateUserIsCheckboxIschecked() {
		return userCheckbox.isSelected();

	}

	public void clickOnSaveButton() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton));
		saveButton.click();
	}

	public String validateNumberSaveSuccessfully() {
		return savedNumberMsg.getText();
	}

	public void clickOnMyAccountMenu() {
		myAccount.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}

	}

	public void clickonAccountDetail() {
		accountDetail.click();
	}

	public void waitForCustomerPortalPage2() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driver.getTitle());
		// It returns no. of windows opened by WebDriver and will return Set of Strings
		Set<String> set = driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			String childWindow = itr.next().toString();
			driver.switchTo().window(childWindow);
		}
		wait.until(ExpectedConditions.titleContains("Customer Portal"));
	}

	public String validateCustomerPortalLatestPrice() {
		return customerPortallatestPrice.getText();
	}

	public void clickOnAddButton() {
		wait_angular5andjs();
		wait.until(ExpectedConditions.visibilityOf(freeNumberAddButton));
		freeNumberAddButton.click();
	}

	public void clickOnNumberSetting() {
		numberSetting.click();
	}

	public boolean isDepartmentDisplay() {
		return department.isDisplayed();
	}

	public boolean isCallrecordingDisplay() {
		return callRecording.isDisplayed();
	}

	public boolean isMusicAndMessageDisplay() {
		return musicAndMessage.isDisplayed();
	}

	public boolean isOpeningHoursDisplay() {
		return openingHours.isDisplayed();
	}

	public boolean isAllocationDisplay() {
		return allocation.isDisplayed();
	}

	public boolean isIVRDisplay() {
		return ivr.isDisplayed();
	}

	public boolean isExtensionDisplay() {
		return extension.isDisplayed();
	}

	public boolean isCallQueueDisplay() {
		return callQueue.isDisplayed();
	}

	public void clickOnUsers() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}

		// wait_angular5andjs();
		wait.until(ExpectedConditions.elementToBeClickable(users));
		users.click();
	}

	public void clickOnUserSetting() {
		userSetting.click();
	}

	public String getNumberFromUserSetting() {
		return numberInUserSetting.getText();
	}
}
