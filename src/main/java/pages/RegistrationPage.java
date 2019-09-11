package pages;

import java.awt.image.CropImageFilter;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;

	@FindBy(xpath = "//input[@name='name']")
	WebElement fullname;
	@FindBy(xpath = "//input[@id='comapny_name']")
	WebElement companyName;
	@FindBy(xpath = "//input[@id='mobileNumber']")
	WebElement mobileNumber;
	@FindBy(xpath = "//input[@id='email']")
	WebElement email;
	@FindBy(xpath = "//input[@id='password']")
	WebElement password;
	@FindBy(xpath = "//button[@type='submit']")
	WebElement signup;
	@FindBy(xpath = "//button[@type='submit'][contains(@disabled,'disabled')]")
	WebElement isclickablesignup;

	// validation xpaths

	@FindBy(xpath = "//span[@class='help-block'][contains(.,'Enter a valid Mobile Number')]")
	WebElement numberValidation;
	@FindBy(xpath = "//div[@class='selected-flag']")
	WebElement flag;

	// Gamil xpaths
	@FindBy(xpath = "//input[@id='identifierId']")
	WebElement gemail;
	@FindBy(xpath = "(//span[contains(.,'Next')])[2]")
	WebElement gemailNext;
	@FindBy(xpath = "//input[@name='password']")
	WebElement gpassword;
	@FindBy(xpath = "(//span[contains(.,'Next')])[2]")
	WebElement gpasswordNext;
	@FindBy(xpath = "//span[contains(text(),'CallHippo | Confirm Your Email')]")
	WebElement mailtitle;
	@FindBy(xpath = "//a[contains(text(),'Verify')]")
	WebElement verifybtn;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Your account has been verified. Login to access your account.')]")
	WebElement accountVerified;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Sorry this confirmation request has expired. Please request for a new confirmation link.')]")
	WebElement linkExpired;

	// button[contains(@disabled,"disabled")]
	public RegistrationPage(WebDriver driver) {
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

	public String getTitle() {
		String signupTitle = driver.getTitle();
		return signupTitle;
	}

	public void enterFullname(String fullName) {

		fullname.sendKeys(fullName);

	}

	public void enterCompanyName(String companyName) {
		this.companyName.sendKeys(companyName);
	}

	public void enterMobile(String mobileNumber) {
		this.mobileNumber.sendKeys(mobileNumber);
	}

	public void enterEmail(String email) {
		this.email.sendKeys(email);
	}

	public void enterPassword(String password) {
		this.password.sendKeys(password);
	}

	public boolean signupIsClickable() {

		try {
			Thread.sleep(1000);

			WebElement w = isclickablesignup;
			driver.findElement(By.xpath("//button[@type='submit'][contains(@disabled,'disabled')]"));

			return true;
		} catch (Exception e) {

			return false;
		}

	}

	public boolean numberValidation() {

		try {
			driver.findElement(By.xpath("//span[@class='help-block'][contains(.,'Enter a valid Mobile Number')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void selectFlag() {
		flag.click();
		action.sendKeys(Keys.ARROW_DOWN).build().perform();
		action.sendKeys(Keys.ENTER).build().perform();

	}

	public String getFlagTitle() {

		return flag.getAttribute("title");

	}

	public boolean emailValidation() {

		try {
			driver.findElement(By.xpath("//span[@class='help-block error-block'][contains(.,'Enter a valid email.')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean passwordValidation() {

		try {
			driver.findElement(By.xpath("//span[@class='help-block'][contains(text(),'Password is too short.')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void clickOnSignupButton() {

		signup.click();
		// wait_angular5andjs();
	}

	public boolean fullNameValidation() {

		try {
			driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'User name cannot be less than 5 characters.')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean blockedEmailValidation() {

		try {
			driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'Domain with this name is not allowed for signup')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean registeredEmailValidation() {

		try {
			driver.findElement(
					By.xpath("//span[@ng-bind-html='message.content'][contains(.,'User already registered')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean nonRegisteredEmailValidation() {

		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'We have sent you an email with instructions to activate your account.')]"))));
			driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'We have sent you an email with instructions to activate your account.')]"));

			return true;
		} catch (Exception e) {

			return false;
		}

	}

	public void gmailLogin() {
		driver.navigate().to("https://gmail.com");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		gemail.sendKeys("jayadip@callhippo.com");
		gemailNext.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		gpassword.sendKeys("Jaydeep@8511");
		gpasswordNext.click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
		wait.until(ExpectedConditions.stalenessOf(
				driver.findElements(By.xpath("//span[contains(text(),'CallHippo | Confirm Your Email')]")).get(1)));
		driver.findElements(By.xpath("//span[contains(text(),'CallHippo | Confirm Your Email')]")).get(1).click();
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
		verifybtn.click();

		String mainWindow = driver.getWindowHandle();
		System.out.println(mainWindow);
		// It returns no. of windows opened by WebDriver and will return Set of Strings
		Set<String> set = driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			String childWindow = itr.next();
			System.out.println(childWindow);
			// Compare whether the main windows is not equal to child window. If not equal,
			// we will close.
			if (!mainWindow.equals(childWindow)) {
				driver.switchTo().window(childWindow);
				System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver.close();
			}
		}

	}

	// ---------------------------Start Gmail Login functions ---------------------

	public void validateMagicLickExpired() {
		gloginGmail();
		openMail();
		gClickOnMagicLoginlink();
		// ggetTitle();

	}

	public void ValidateVerifyButtonOnGmail() {
		gloginGmail();
		openMail();
		gClickOnRecentMagicLoginlink();
		ggetTitle();
	}

	public void gloginGmail() {
		driver.get(
				"https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");

		// driver.get("https://gmail.com");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		gemail.sendKeys("jayadip@callhippo.com");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		gemailNext.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		gpassword.sendKeys("Jaydeep@8511");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		gpasswordNext.click();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
	}

	public void gClickOnRecentMagicLoginlink() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Verify')]"))));
		int size = driver.findElements(By.xpath("//a[contains(text(),'Verify')]")).size();
		System.out.println(size);

		if (size < 2) {
			driver.findElement(By.xpath("//a[contains(text(),'Verify')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(text(),'Verify')]")).get(size - 1).click();

		}
	}

	public void gClickOnMagicLoginlink() {
		// magicLogin.click();
		// driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'Verify')]"))));
		int size = driver.findElements(By.xpath("//a[contains(text(),'Verify')]")).size();
		System.out.println(size);

		if (size > 0) {
			driver.findElement(By.xpath("//a[contains(text(),'Verify')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(text(),'Verify')]")).get(size - 1).click();

		}
	}

	public void ggetTitle() { 
		String mainWindow = driver.getWindowHandle();
		System.out.println(mainWindow);
		// It returns no. of windows opened by WebDriver and will return Set of Strings
		Set<String> set = driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			String childWindow = itr.next();
			System.out.println(childWindow);
			// Compare whether the main windows is not equal to child window. If not equal,
			// we will close.
			if (!mainWindow.equals(childWindow)) {
				driver.switchTo().window(childWindow);
				wait.until(ExpectedConditions.titleIs("Login | Callhippo.com"));
				System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);
				// return driver.switchTo().window(childWindow).getTitle();
				// driver.close();
			}
		}
		// return mainWindow;
	}

	public void openMail() {
		int attempts = 0;
		while (attempts < 2) {
			try {
				try {
					driver.findElements(By.xpath("//span[contains(text(),'CallHippo | Confirm Your Email')]")).get(1)
							.click();
				} catch (Exception e) {
					wait.until(ExpectedConditions.stalenessOf(
							driver.findElements(By.xpath("//span[contains(text(),'CallHippo | Confirm Your Email')]"))
									.get(1)));
					driver.findElements(By.xpath("//span[contains(text(),'CallHippo | Confirm Your Email')]")).get(1)
							.click();
				}

				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

	}

	// ---------------------------Start Gmail Login functions ---------------------

	public boolean accountVerifiedMsg() {

		try {
			driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'Your account has been verified. Login to access your account.')]"));
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		return false;
	}

	public boolean accountLinkExpiredMsg() {

		try {
			driver.findElement(By.xpath(
					"//span[@ng-bind-html='message.content'][contains(.,'Sorry this confirmation request has expired. Please request for a new confirmation link.')]"));
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		return false;
	}

}
