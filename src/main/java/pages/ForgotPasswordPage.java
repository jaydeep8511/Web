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

public class ForgotPasswordPage {

	static WebDriver driver;
	WebDriverWait wait;
	Actions action;
	WebDriver driver1;

	@FindBy(name = "email")
	WebElement email;
	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnResetPassword;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Email is required')]")
	WebElement emailRequired;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Email does not exist')]")
	WebElement emailDoesnotExist;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'An email has been sent to reset your password')]")
	WebElement registeredemail;
	@FindBy(xpath = "//span[contains(.,'Back to Sign In')]") WebElement backToSignin;
	
	//Reset password page 
	@FindBy(xpath = "//input[@name='fullName']") WebElement fullName;
	@FindBy(xpath = "//input[@id='password']") WebElement resetpassword;
	@FindBy(xpath = "//input[@id='confirm_password']") WebElement confirmPassword; 
	@FindBy(xpath = "//button[@type='submit']") WebElement submit;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Password and confirm password does not matched')]") WebElement mismatchPasswordValidation;
	@FindBy(xpath = "//span[@class='help-block'][contains(.,'Password is too short.')]") WebElement shortpassword; 
	@FindBy(xpath = "//span[@class='help-block'][contains(.,'Confirm password is too short.')]") WebElement shortConfirmPassword;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Sorry this password reset request has expired. Please request for a new reset link.')]") WebElement ExpiredResetPasswordLink;
	@FindBy(xpath = "//span[@ng-bind-html='message.content'][contains(.,'Your password has been reset successfully')]") WebElement resetPasswordSuccessfully; 
	

	// gmail xpath
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
	@FindBy(xpath = "//a[contains(.,'Reset Password')]")
	WebElement resetPassword;

	public ForgotPasswordPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(this.driver, 30);
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

	public void enterEmail(String email) {
		this.email.sendKeys(email);
	}

	public boolean emailValidation() {

		try {
			driver.findElement(By.xpath("//span[@class='help-block error-block'][contains(.,'Enter a valid email.')]"));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void clickOnResetPassword() {
		btnResetPassword.click();
	}

	public String ValidateEmailRequiredField() {
		return emailRequired.getText();
	}

	public String ValidateEmailDoesnotExist() {
		return emailDoesnotExist.getText();
	}

	public String validateWithRegisteredEmail() {
		return registeredemail.getText();
	}

	
	// ---------------------------Start Gmail Login functions ---------------------

		public void validateMagicLickExpired() {
			gloginGmail();
			openMail();
			gClickOnResetPasswordlink();
			 ggetTitle();

		}

		public void ValidateResetPasswordButtonOnGmail() {
			gloginGmail();
			openMail();
			gClickOnRecentResetPasswordlink();
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

		public void gClickOnRecentResetPasswordlink() {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Reset Password')]"))));
			int size = driver.findElements(By.xpath("//a[contains(.,'Reset Password')]")).size();
			System.out.println(size);

			if (size < 2) {
				driver.findElement(By.xpath("//a[contains(.,'Reset Password')]")).click();
			} else {

				driver.findElements(By.xpath("//a[contains(.,'Reset Password')]")).get(size - 1).click();

			}
		}

		public void gClickOnResetPasswordlink() {
			// magicLogin.click();
			// driver.findElement(By.xpath("//a[contains(.,'Magic login')]")).click();
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(.,'Reset Password')]"))));
			int size = driver.findElements(By.xpath("//a[contains(.,'Reset Password')]")).size();
			System.out.println(size);

			if (size > 0) {
				driver.findElement(By.xpath("//a[contains(.,'Reset Password')]")).click();
			} else {

				driver.findElements(By.xpath("//a[contains(.,'Reset Password')]")).get(size - 1).click();

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
					wait.until(ExpectedConditions.titleContains("Reset Password | Callhippo.com"));
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
						driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1)
								.click();
					} catch (Exception e) {
						wait.until(ExpectedConditions.stalenessOf(
								driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]"))
										.get(1)));
						driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1)
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
	public void ValidateGmailResetPasswordLink() {
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
			Thread.sleep(6000);
		} catch (InterruptedException e) {
		}
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
//	
//		wait.until(ExpectedConditions.stalenessOf(
//				driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1)));
		
		int attempts =0;
		while(attempts < 2) {
	        try {
	        	try {
	    			driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1).click();
	    		} catch (Exception e) {
	    			wait.until(ExpectedConditions.stalenessOf(
	    					driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1)));
	    			driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1).click();
	    		}
	           
	            break;
	        } catch(StaleElementReferenceException e) {
	        }
	        attempts++;
	    }
	
		/*
		try {
			driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1).click();
		} catch (Exception e) {
			wait.until(ExpectedConditions.stalenessOf(
					driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1)));
			driver.findElements(By.xpath("//span[contains(text(),'CallHippo: Reset your password')]")).get(1).click();
		}
		*/
		
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

		//driver.findElements(resetpassword).Size();
		
		//int value = driver.findElements(By.xpath("//a[contains(.,'Reset Password')]")).size();
		//System.out.println(value);
//		String xpath = "//a[contains(.,'Reset Password')][@xpath='"+1+"']";
//		driver.findElement(By.xpath(xpath)).click();

		
		
		resetPassword.click();

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
				wait.until(ExpectedConditions.titleIs("Reset Password | Callhippo.com"));
				System.out.println(driver.switchTo().window(childWindow).getTitle());
				// driver1 =driver.switchTo().window(childWindow);

				// driver.close();
			}
		}

	}

	public String validateSigninLink() {
		backToSignin.click();
		wait.until(ExpectedConditions.titleIs("Login | Callhippo.com"));
		return driver.getTitle();
	}

	public boolean submitIsClickable() {

		try {
			Thread.sleep(1000);

			
			driver.findElement(By.xpath("//button[@type='submit'][contains(.,'Submit')][contains(@disabled,'disabled')]"));

			return true;
		} catch (Exception e) {

			return false;
		}

	}
	
	public void enterFullName(String fullNmae) {
		//driver = driver1;
		this.fullName.clear();
		this.fullName.sendKeys(fullNmae);
		//driver1.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		//driver = driver1;
		
	}
	
	public void enterPassword(String password) {
		//driver = driver1;
		resetpassword.clear();
		resetpassword.sendKeys(password);
		//driver1.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		//driver = driver1;
		
	}
	
	public void enterConfirmPassword(String confirmPassword) {
		this.confirmPassword.clear();
		this.confirmPassword.sendKeys(confirmPassword);
	}
	
	public void clickOnSubmitButton() {
		submit.click();
	}
	
	public String mismatchResetPasswordValidation() {
		return mismatchPasswordValidation.getText();
	}
	
	public String validateShortPassword() {
		return shortpassword.getText();
	}
	
	public String validateShortConfirmPassword() {
		return shortConfirmPassword.getText();
	}
	
	public String validateexpiredResetPasswordLinkErrorMessage() {
		return ExpiredResetPasswordLink.getText();
		
	}
	
	public String ValidateResetPasswordSuccessfully() {
		
		return resetPasswordSuccessfully.getText();
	}
	

}
