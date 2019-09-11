package Base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	
	
	static WebDriver driver;
	
	public static WebDriver init(){
		 
		System.setProperty("webdriver.chrome.driver","..\\Web\\src\\main\\java\\config\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--use-fake-ui-for-media-stream");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		return driver;

		
	}
	
	

}
