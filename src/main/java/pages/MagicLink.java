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

public class MagicLink {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	WebDriver driver1;

	@FindBy(name = "email")
	WebElement email;
	@FindBy(xpath = "//div[@class='form-group margzero magiclinknewchbtn'][@style='']/button[contains(.,'Send Magic Link')]")
	WebElement magicLinkbtn;
	@FindBy(xpath = "//div[@class='md-checkbox']")
	WebElement magicLinkCheckbox;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Email is required')]")
	WebElement emailRequired;
	@FindBy(xpath = "//span[@ng-bind-html='message.content']")
	WebElement emailValidationMsg;
	@FindBy(xpath = "//span[@ng-bind-html='message.content']")
	WebElement errorMessage;

	// gmail xpaths
	@FindBy(xpath = "//input[@id='identifierId']")
	WebElement gemail;
	@FindBy(xpath = "(//span[contains(.,'Next')])[2]")
	WebElement gemailNext;
	@FindBy(xpath = "//input[@name='password']")
	WebElement gpassword;
	@FindBy(xpath = "(//span[contains(.,'Next')])[2]")
	WebElement gpasswordNext;
	@FindBy(xpath = "//span[contains(text(),'CallHippo: Magic Link')]")
	WebElement mailtitle;
	@FindBy(xpath = "//a[contains(.,'Magic login')]")
	WebElement magicLogin;

	public MagicLink(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 10);
		action = new Actions(this.driver);
	}

	public String getTitle() {
		String signupTitle = driver.getTitle();
		return signupTitle;
	}

	public void enterEmail(String email) {
		this.email.sendKeys(email);
	}

	public String IsMagicLinkButtonEnabled() {
		return magicLinkbtn.getText();
	}

	public void clickOnMagicLink() {
		magicLinkbtn.click();
	}

	public void clickOMagicLinkCheckbox() {

		if (magicLinkCheckbox.isSelected() == false) {
			magicLinkCheckbox.click();
		}
	}

	public String emailValidationMsg() {
		return emailValidationMsg.getText();

	}

	public String validateErrormessage() {

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
				// System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);

				return driver.switchTo().window(childWindow)
						.findElement(By.xpath("//span[@ng-bind-html='message.content']")).getText();
				// driver.close();
			}
		}
		return mainWindow;
	}

	public String validateErrormessageOfBlockedUser() {

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

				wait.until(ExpectedConditions.titleIs("Subscribe | Callhippo.com"));
				// System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);

				return driver.switchTo().window(childWindow)
						.findElement(By.xpath("//span[@ng-bind-html='message.content']")).getText();
				// driver.close();
			}
		}
		return mainWindow;
	}

	// ---------------------------Start Gmail Login functions ---------------------

	public void validateMagicLickExpired() {
		gloginGmail();
		openMail();
		gClickOnMagicLoginlink();
		// ggetTitle();

	}

	public void ValidateMagicLinOnGmail() {
		gloginGmail();
		openMail();
		gClickOnRecentMagicLoginlink();
		// ggetTitle();
	}

	public void gloginGmail() {

		driver.navigate().to(
				"https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin"); 
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
			Thread.sleep(6000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
	}

	public void gClickOnRecentMagicLoginlink() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Magic login')]"))));
		int size = driver.findElements(By.xpath("//a[contains(.,'Magic login')]")).size();
		System.out.println(size);

		if (size < 2) {
			driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(.,'Magic login')]")).get(size - 1).click();

		}
	}

	public void gClickOnMagicLoginlink() {
		// magicLogin.click();
		// driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Magic login')]"))));
		int size = driver.findElements(By.xpath("//a[contains(.,'Magic login')]")).size();
		System.out.println(size);

		if (size > 0) {
			driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
		} else {

			driver.findElements(By.xpath("//a[contains(.,'Magic login')]")).get(size - 1).click();

		}
	}

	public String ggetTitle() {
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
				wait.until(ExpectedConditions.titleIs("Dashboard | Callhippo.com"));
				System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);
				return driver.switchTo().window(childWindow).getTitle();
				// driver.close();
			}
		}
		return mainWindow;
	}

	public void openMail() {
		int attempts = 0;
		while (attempts < 2) {
			try {
				try {
					driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Magic Link')]")).get(1).click();
				} catch (Exception e) {
					wait.until(ExpectedConditions.stalenessOf(
							driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Magic Link')]")).get(1)));
					driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Magic Link')]")).get(1).click();
				}

				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

	}

	// ---------------------------Start Gmail Login functions ---------------------

}
