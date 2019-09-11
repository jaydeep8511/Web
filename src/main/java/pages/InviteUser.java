package pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InviteUser {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	
	@FindBy(xpath = "//a[contains(.,'supervisor_account Users')]")WebElement users;
	@FindBy(xpath = "//button[text()='Invite User']") WebElement inviteUserButton;
	@FindBy(xpath = "//input[@id='email']") WebElement email;
	@FindBy(xpath = "//input[@type='checkbox']") WebElement numberCheckbox;
	@FindBy(xpath = "//button[@id='inviteBtn']") WebElement inviteButton;
	//span[@ng-bind-html='message.content'][contains(.,'Your invitation sent successfully')]
	@FindBy(xpath = "//input[@name='fullName']") WebElement fullName;
	@FindBy(xpath = "//input[@id='password']") WebElement password;
	@FindBy(xpath = "//input[@id='confirm_password']")WebElement confirmPassword;
	@FindBy(xpath = "//button[@type='submit']") WebElement submitButton;
	@FindBy(xpath = "//b[contains(.,'Your account has been activated.')]")WebElement activatedMessage;
	
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

	
	public InviteUser(WebDriver driver) {
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
	
	public void clickOnUsersLink() {
		wait.until(ExpectedConditions.visibilityOf(users));
		users.click();
	}
	
	public void clickOnInviteUserButton() {
		wait.until(ExpectedConditions.visibilityOf(inviteUserButton));
		inviteUserButton.click();
	}
	
	public String validateTitle() {
		
		wait.until(ExpectedConditions.titleContains("Invite User | Callhippo.com"));
		return driver.getTitle();

	}
	
	public void enterEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(this.email));
		this.email.sendKeys(email);
		
	}
	
	public void clickOnNumberCheckbox() {
		wait.until(ExpectedConditions.visibilityOf(numberCheckbox));
		numberCheckbox.click();
	}

	public void clickOnInviteButton() {
		wait.until(ExpectedConditions.elementToBeClickable(inviteButton));
		inviteButton.click();
	}
	
	public void waitUntilSuccessfulValidationMsgDisplay() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[@ng-bind-html='message.content'][contains(.,'Your invitation sent successfully')]"))));
		
	}
	
	public void enterFullName(String fullName) {
		wait.until(ExpectedConditions.visibilityOf(this.fullName));
		this.fullName.sendKeys(fullName);
	}
	
	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOf(this.password));
		this.password.sendKeys(password);
	}
	
	public void enterconfirmPassword(String confirmPassword) {
		wait.until(ExpectedConditions.visibilityOf(this.confirmPassword));
		this.confirmPassword.sendKeys(confirmPassword);
	}
	
	public void clickOnSubmitButton() {
	wait.until(ExpectedConditions.elementToBeClickable(submitButton));
	submitButton.click();
	}
	
	public String validateAccountActivatedMessage() {
		wait.until(ExpectedConditions.visibilityOf(activatedMessage));
		return activatedMessage.getText();
	}
	
	//-------------------------start gamil login logic ---------------------
	
	public void validateMagicLickExpired() {
		gloginGmail();
		openMail();
		gClickOnAcceptInvitationlink();
		// ggetTitle();

	}

	public void ValidateVerifyButtonOnGmail() {
	//	gloginGmail();
		openMail();
		gClickOnRecentAcceptInvitationlink();
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

	public void gClickOnRecentAcceptInvitationlink() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Accept Invitation')]"))));
		int size = driver.findElements(By.xpath("//a[contains(.,'Accept Invitation')]")).size();
		System.out.println(size);

		if (size < 2) {
			driver.findElement(By.xpath("//a[contains(.,'Accept Invitation')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(.,'Accept Invitation')]")).get(size - 1).click();

		}
	}

	public void gClickOnAcceptInvitationlink() {
		// magicLogin.click();
		// driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Accept Invitation')]"))));
		int size = driver.findElements(By.xpath("//a[contains(.,'Accept Invitation')]")).size();
		System.out.println(size);

		if (size > 0) {
			driver.findElement(By.xpath("//a[contains(.,'Accept Invitation')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(.,'Accept Invitation')]")).get(size - 1).click();

		}
	}

	public void ggetTitle() { 
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
//		String mainWindow = driver.getWindowHandle();
//		System.out.println(mainWindow);
		// It returns no. of windows opened by WebDriver and will return Set of Strings
		Set<String> set = driver.getWindowHandles();
		// Using Iterator to iterate with in windows
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			
			String childWindow = itr.next();
			System.out.println(childWindow);
			// Compare whether the main windows is not equal to child window. If not equal,
			// we will close.
//			if (!mainWindow.equals(childWindow)) {
				driver.switchTo().window(childWindow);
				//wait.until(ExpectedConditions.titleIs("Register | Callhippo.com"));
				//System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);
				// return driver.switchTo().window(childWindow).getTitle();
				// driver.close();
//			}
		}
		// return mainWindow;
	}

	public void openMail() {
		
		driver.get(
				"https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");

		// driver.get("https://gmail.com");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		int attempts = 0;
		while (attempts < 2) {
			try {
				try {
					driver.findElements(By.xpath("//span[contains(text(),'invited you to join CallHippo')]")).get(1)
							.click();
				} catch (Exception e) {
					wait.until(ExpectedConditions.stalenessOf(
							driver.findElements(By.xpath("//span[contains(text(),'invited you to join CallHippo')]"))
									.get(1)));
					driver.findElements(By.xpath("//span[contains(text(),'invited you to join CallHippo')]")).get(1)
							.click();
				}

				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

	}
	
	//------------ end gmail login logic------------------------------
	
}
