package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Base.TestBase;
import pages.AddNumber;
import pages.LoginPage;
import pages.RegistrationPage;
import utilsFile.PropertiesFile;
import utilsFile.Retry;
import utilsFile.Utilitylib;

public class AddNumberTest {

	static AddNumber addNumber;
	static LoginPage loginPage;
	static WebDriver driver;
	static Utilitylib excel;
	static RegistrationPage registrationPage;
	static PropertiesFile url;

	public AddNumberTest() throws Exception {
		url = new PropertiesFile();
		excel = new Utilitylib("..\\Web\\src\\main\\java\\config\\Signup.xlsx");
	}

	@BeforeMethod
	public void initialization() throws IOException {

		driver = TestBase.init();
		driver.get(url.signIn());

		addNumber = PageFactory.initElements(driver, AddNumber.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
	}

	@AfterMethod
	public void teardown() {
		 driver.quit();
	}

	@Test(priority = 1, retryAnalyzer = Retry.class)
	public void verify_loging_Successfully() { 
		loginPage.enterEmail("jayadip+30_08_2019_18_19_23@callhippo.com");
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResult = loginPage.loginSuccessfully();
		String expectedResult = "Dashboard | Callhippo.com";
		assertEquals(actualResult, expectedResult);

		addNumber.closePopup();
		addNumber.closePopup();

	}

	@Test(priority = 2, retryAnalyzer = Retry.class)
	public void verify_addNumberPage() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		String actualTitle = addNumber.getTitle("Add Number | Callhippo.com");
		String expectedTitle = "Add Number | Callhippo.com";
		assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 3, retryAnalyzer = Retry.class)
	public void verify_validation_of_number_name() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		// addNumber.getTitle("Add Number | Callhippo.com");
		boolean actualResult = addNumber.isSelectCountryDisplay();
		boolean expectedResult = false;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 4, retryAnalyzer = Retry.class)
	public void verify_country_list_to_add_number() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		// addNumber.getTitle("Add Number | Callhippo.com");
		boolean actualResult = addNumber.isSelectCountryDisplay();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);

		int actualCount = addNumber.validateTop5CountriesCount();
		int expectedCount = 5;
		assertEquals(actualCount, expectedCount);

		boolean actualName = addNumber.validateTop5CountriesNameAndCode();
		boolean expectedName = true;
		assertEquals(actualName, expectedName);
	}

	@Test(priority = 5, retryAnalyzer = Retry.class)
	public void verify_select_Country_And_Display_Number() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();

		boolean actualNumberResult = addNumber.validateNumberList();
		boolean expectedNumberResult = true;
		assertEquals(actualNumberResult, expectedNumberResult);
	}

	@Test(priority = 6, retryAnalyzer = Retry.class)
	public void Verify_Next_Button_in_Display_number_page() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();

		boolean actualResult = addNumber.validateNumberListNextButton();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 7, retryAnalyzer = Retry.class)
	public void Verify_previous_Button_in_Display_number_page() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();

		boolean actualResult = addNumber.validateNumberListPriviousButton();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 8, retryAnalyzer = Retry.class)
	public void Verify_search_Number_with_prefix() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.enterPrefixNumber("218");
		addNumber.clickOnSearchButton();

		String actualResult = addNumber.validatePrefixnumber();
		String expectedResult = "218";
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 9, retryAnalyzer = Retry.class)
	public void Verify_search_Number_with_prefix_and_Result_not_found() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.enterPrefixNumber("99999999");
		addNumber.clickOnSearchButton();

		String actualResult = addNumber.validateNoResultFound();
		String expectedResult = "No results for your search!";
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 10, retryAnalyzer = Retry.class)
	public void Verify_search_Number_with_location_By_search_NamePrefix() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUKCountry();
		addNumber.selectSearchByLocation();
		addNumber.selectLocation("London");
		// addNumber.enterPrefixNumber("218");
		addNumber.clickOnSearchButton();

		String actualResult = addNumber.validateLocationPrefixnumber();
		String expectedResult = "203";
		assertEquals(actualResult, expectedResult);
	}

	@Test(priority = 11, retryAnalyzer = Retry.class)
	public void Verify_search_Number_with_location_By_search_CodePrefix() {
		verify_loging_Successfully();
		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUKCountry();
		addNumber.selectSearchByLocation();
		addNumber.selectLocation("203");
		// addNumber.enterPrefixNumber("218");
		addNumber.clickOnSearchButton();

		String actualResult = addNumber.validateLocationPrefixnumber();
		String expectedResult = "203";
		assertEquals(actualResult, expectedResult);
	}

	public static void Verify_select_number_from_list_for_voice_service() throws Exception {

		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get(url.signUp());
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get(url.signIn());
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//		verify_loging_Successfully();

	}

	@Test(priority = 12, retryAnalyzer = Retry.class)
	public void verify_purchase_number_of_Voice_service() {

		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUKCountry();
		addNumber.selectSearchByLocation();
		addNumber.selectLocation("London");
		// addNumber.enterPrefixNumber("218");
		addNumber.clickOnSearchButton();
		addNumber.selectVoiceNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$9.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 13, retryAnalyzer = Retry.class)
	public void verify_purchase_number_of_Voice_and_SMS_service() {

		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnCanadaCountry();
		addNumber.selectNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$9.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 14, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_without_selecting_any_plan() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		
//		addNumber.selectSearchByTollfree();
//		addNumber.clickOnSearchButton();
		
		addNumber.selectNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$8.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 15, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_annualy_without_selecting_any_plan() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();
		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$72.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 16, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_montly_with_bronze_montly() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$18.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 17, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_montly_with_bronze_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$104.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 18, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_annually_with_bronze_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$168.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 19, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_with_silver_Monthly() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectSilver();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$18.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 20, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_with_silver_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectSilver();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$180.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 21, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_annually_with_silver_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectSilver();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$180.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 22, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_with_platinum_Monthly() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectPlatinum();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$40.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 23, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_with_platinum_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectPlatinum();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$420.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 24, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_Annually_with_platinum_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectPlatinum();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$420.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 25, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_Annually_with_platinum_monthly() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		boolean actualResult = addNumber.verifyMontlyDisabled();
		boolean expectedResult = true;
		assertEquals(actualResult, expectedResult);

//		addNumber.selectAnually();
//		addNumber.selectPlatinum();
//		addNumber.clickOnCheckoutButton();
//		
//		//addNumber.clickOnSkipLink();
//
//		addNumber.waitforCheckoutPage();
//
//		String actualPrice = addNumber.getTotalPrice();
//		String expectedPrice = "$420.00";
//		assertEquals(actualPrice, expectedPrice);
//
//		addNumber.filledCheckoutPage();
//		addNumber.clickOnSubscripbeButton();
//
//		addNumber.waitForAddNumberPage();
//		addNumber.clickOnNotNowIamDone();
//		addNumber.clickOnSaveButton();
//
//		String actualMessage = addNumber.validateNumberSaveSuccessfully();
//		String expectedMessage = "Number added successfully";
//		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 26, retryAnalyzer = Retry.class)
	public void verify_Save_number_info_without_allocating_to_any_user() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$19.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
//		addNumber.clickOnSaveButton();

		boolean atualResult = addNumber.validateUserIsCheckboxIschecked();
		boolean expectedResult = true;
		assertEquals(atualResult, expectedResult);

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);

	}

	@Test(priority = 27, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_when_Bronze_monthly_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserBronzePlan();
		addNumber.selectUserMontlyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$10.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$1.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$8.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}

	@Test(priority = 28, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_when_Silver_monthly_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserSilverPlan();
		addNumber.selectUserMontlyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$18.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$2.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.clickOnAddButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$18.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}
	
	@Test(priority = 29, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_when_Platinum_monthly_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserPlatinumPlan();
		addNumber.selectUserMontlyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$40.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$5.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.clickOnAddButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$40.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}
	
	@Test(priority = 30, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_Annully_when_Bronze_Annually_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserBronzePlan();
		addNumber.selectUserAnuallyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$96.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$1.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$72.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}
	
	@Test(priority = 31, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_Annually_when_Silver_Annually_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserSilverPlan();
		addNumber.selectUserAnuallyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$180.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$2.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.clickOnAddButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$180.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}
	
	@Test(priority = 32, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_Annually_when_Platinum_Annually_plan_running() throws Exception {
		String date = excel.date();
		String email = "jayadip+" + date + "@callhippo.com";

		driver.get("https://jenkins-app.callhippo.com/#!/signup");
		registrationPage.enterFullname("tstAutomation");
		registrationPage.enterCompanyName("appitSimple");
		registrationPage.enterMobile("918511695975");
		registrationPage.enterEmail(email);
		registrationPage.enterPassword("12345678");
		registrationPage.clickOnSignupButton();
		boolean actualResultr = registrationPage.nonRegisteredEmailValidation();
		boolean expectedResultr = true;
		// SoftAssert sa = new SoftAssert();
		assertEquals(actualResultr, expectedResultr);

		registrationPage.ValidateVerifyButtonOnGmail();
		boolean actualVerifyMsg = registrationPage.accountVerifiedMsg();
		boolean expectedVerifyMsg = true;
		assertEquals(actualVerifyMsg, expectedVerifyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/login");
		loginPage.enterEmail(email);
		loginPage.enterPassword("12345678");
		loginPage.clickOnLogin();

		String actualResultl = loginPage.loginSuccessfully();
		String expectedResultl = "Dashboard | Callhippo.com";
		assertEquals(actualResultl, expectedResultl);

		addNumber.closePopup();
		addNumber.closePopup();
//-------------------------------------------------
		addNumber.clickOnSetting();
		addNumber.selectUserPlatinumPlan();
		addNumber.selectUserAnuallyPlan();
		addNumber.clickOnUpgradeButton();
		addNumber.waitforCheckoutPage();
		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$420.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForSubscribePage();

		String actualSuccessfullyMsg = addNumber.validateUpdradeSuccessfullyMessage();
		String expectedSuccessfullyMsg = "Your plan has been upgraded successfully.";
		assertEquals(actualSuccessfullyMsg, expectedSuccessfullyMsg);

		driver.get("https://jenkins-app.callhippo.com/#!/plan");

		String actualCredit = addNumber.validateCredit();
		String expectedCredit = "$5.00";
		assertEquals(actualCredit, expectedCredit);

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.clickOnAddButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$420.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);

	}
	
	@Test(priority = 33, retryAnalyzer = Retry.class)
	public void verify_purchase_2nd_number_montly() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectMontly();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$18.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		
		
		//addNumber.clickOnNumberLink();
		
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 2");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber2();

		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage1 = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage1 = "Number added successfully";
		assertEquals(actualMessage1, expectedMessage1);
		
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$8.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);
		

	}
	
	@Test(priority = 34, retryAnalyzer = Retry.class)
	public void verify_purchase_2nd_number_Annually() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.selectAnually();
		addNumber.selectBronze();
		addNumber.clickOnCheckoutButton();

		// addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$168.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		
		
		//addNumber.clickOnNumberLink();
		
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 2");
		addNumber.clickOnUSCountry();
		addNumber.selectNumber2();

		addNumber.selectNumberAnnualyPlan();
		addNumber.clickOnPayButton();

		addNumber.waitForAddNumberPage();

		addNumber.clickOnSaveButton();

		String actualMessage1 = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage1 = "Number added successfully";
		assertEquals(actualMessage1, expectedMessage1);
		
		
		addNumber.clickOnMyAccountMenu();
		addNumber.clickonAccountDetail();
		addNumber.waitForCustomerPortalPage2();
		
		String actualLatestPrice = addNumber.validateCustomerPortalLatestPrice();
		String expectedLatestPrice = "$72.00";
		assertEquals(actualLatestPrice, expectedLatestPrice);
		

	}
	
	@Test(priority = 35, retryAnalyzer = Retry.class)
	public void verify_all_modules_display_in_Numbers_setting_page() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		
//		addNumber.selectSearchByTollfree();
//		addNumber.clickOnSearchButton();
		
		String numberBeforePurchased = addNumber.getSeletedNumber();
		addNumber.selectNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$8.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		String NumberAfterPurchased = addNumber.getNumeberAfterPurchased();
		assertEquals(numberBeforePurchased, NumberAfterPurchased);
		
		addNumber.clickOnNumberSetting();
		boolean department = addNumber.isDepartmentDisplay();
		boolean callRecording = addNumber.isCallrecordingDisplay();
		boolean musicAndMessage = addNumber.isMusicAndMessageDisplay();
		boolean openingHours = addNumber.isOpeningHoursDisplay();
		boolean allocation = addNumber.isAllocationDisplay();
		boolean ivr = addNumber.isIVRDisplay();
		boolean extension = addNumber.isExtensionDisplay();
		boolean callQueue = addNumber.isCallQueueDisplay();
		
		assertEquals(department, true);
		assertEquals(callRecording, true);
		assertEquals(musicAndMessage, true);
		assertEquals(openingHours, true);
		assertEquals(allocation, true);
		assertEquals(ivr, true);
		assertEquals(extension, true);
		assertEquals(callQueue, true);
	}
	
	@Test(priority = 36, retryAnalyzer = Retry.class)
	public void verify_purchase_1st_number_monthly_without_selecting_any_plan2() {
		try {
			Verify_select_number_from_list_for_voice_service();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addNumber.clickOnNumberLink();
		addNumber.clickOnAddNumberButton();
		addNumber.enterNameofNumber("Number 1");
		addNumber.clickOnUSCountry();
		
//		addNumber.selectSearchByTollfree();
//		addNumber.clickOnSearchButton();
		
		String numberBeforePurchased = addNumber.getSeletedNumber();
		addNumber.selectNumber();
		addNumber.selectNumberMontlyPlan();
		addNumber.clickOnPayButton();
		addNumber.clickOnSkipLink();

		addNumber.waitforCheckoutPage();

		String actualPrice = addNumber.getTotalPrice();
		String expectedPrice = "$8.00";
		assertEquals(actualPrice, expectedPrice);

		addNumber.filledCheckoutPage();
		addNumber.clickOnSubscripbeButton();

		addNumber.waitForAddNumberPage();
		addNumber.clickOnNotNowIamDone();
		addNumber.clickOnSaveButton();

		String actualMessage = addNumber.validateNumberSaveSuccessfully();
		String expectedMessage = "Number added successfully";
		assertEquals(actualMessage, expectedMessage);
		
		addNumber.clickOnUsers();
		addNumber.clickOnUserSetting();
		
		String getNumberFromUserSetting = addNumber.getNumberFromUserSetting();
		assertEquals(numberBeforePurchased, getNumberFromUserSetting);
	}

}
