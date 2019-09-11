package utilsFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.io.FileUtils;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Utilitylib1 {

	XSSFWorkbook wb;
	XSSFSheet sheet1;
	File src;

	public Utilitylib1(String excelPath) {
		try {
			src = new File(excelPath);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
			sheet1 = wb.getSheetAt(0);
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	public String getdata(int sheetNumber, int row, int colum) throws Exception {

		try {
			sheet1 = wb.getSheetAt(sheetNumber);
			String data = wb.getSheetAt(sheetNumber).getRow(row).getCell(colum).getStringCellValue();
			return data;
		} catch (Exception e) {
			System.out.println(
					"\n\n\n\n Error:  May excel sheet's cell data type is not supported, please change the cell datatype in excel! \n\n\n\n\n ");
		}
		return null;

	}

	public double getdataNumeric(int sheetNumber, int row, int colum) throws Exception {
		sheet1 = wb.getSheetAt(sheetNumber);
		double data = wb.getSheetAt(sheetNumber).getRow(row).getCell(colum).getNumericCellValue();
		return data;
	}

	public int getNumberOfSheets() throws Exception {
		int scount = wb.getNumberOfSheets();
		return scount;

	}

	public void write(int sheetNumber, int row, int colum, String text) {
		try {

			sheet1 = wb.getSheetAt(sheetNumber);
			sheet1.getRow(row).createCell(colum).setCellValue(text);
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
			fout.flush();
			fout.close();
			// wb.close();

		} catch (IOException e) {

			System.out.println(e);
		}

	}

	public static void Screenshot(WebDriver driver, String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			File file1 = new File("./ScreenShots/" + dateOnly());
			if (!file1.exists()) {
				if (file1.mkdir()) {
					File file2 = new File("./ScreenShots/" + dateOnly() + "/" + date() + " " + screenshotName + ".png");
					FileUtils.copyFile(source, file2);
					System.out.println("Directory is created!");
				} else {
					System.out.println("Already created..");
				}

			} else {
				File file2 = new File("./ScreenShots/" + dateOnly() + "/" + date() + " " + screenshotName + ".png");
				FileUtils.copyFile(source, file2);
			}

			System.out.println("Screenshot taken...");
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

	}

	private static String date() throws Exception {
		// Create object of SimpleDateFormat class and decide the format
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy (HH.mm.ss) ");
		// get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date);
		// System.out.println(date1);
		return date1;
	}

	private static String dateOnly() throws Exception {
		// Create object of SimpleDateFormat class and decide the format
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date);
		// System.out.println(date1);
		return date1;
	}

	public int find(int sorceSheetNumber, int sorceRow, int sorceColumn, int findSheetNumber, int findRowNumber)
			throws Exception {

		int index = 0;
		sheet1 = wb.getSheetAt(sorceSheetNumber);
		for (int i = 1; i < 20; i++)
			if (wb.getSheetAt(sorceSheetNumber).getRow(sorceRow).getCell(sorceColumn).getStringCellValue()
					.equals(wb.getSheetAt(findSheetNumber).getRow(findRowNumber).getCell(i).getStringCellValue()))
				return wb.getSheetAt(findSheetNumber).getRow(findRowNumber).getCell(i).getColumnIndex();
		return index;
	}

	public static void scriptexcel(WebDriver driver, Utilitylib1 ut, int sheetIndex, int rowCountFrom, int rowCountTo,
			int loopValueForSendkey) throws Exception {

		for (int loopValueOfSendkey = 0; loopValueOfSendkey < loopValueForSendkey; loopValueOfSendkey++)

		{
			int rcountValue = rowCountFrom;

			int rowcount = rowCountTo;
			// -----------------------------variable need to set

			// ----------------------------columns defined----------------------------//
			int colValue = 5;
			int colAction = 6;
			int colSubAction = 7;
			int colPropertyType = 8;
			int colPropertyValue = 9;
			int colStatus = 10;

			for (int rcount = rcountValue; rcount < rowcount + 1; rcount++) {

				String data = ut.getdata(sheetIndex, rcount, colAction);

				String get = "Get";
				String click = "Click";
				String wait1 = "Wait";
				String submit = "Submit";
				String clear = "Clear";
				String sendkey = "SendKey";
				String isdisplay = "IsDisplay";
				String dropdownvalue = "DropdownValue";
				String swithToFrame = "SwitchTOFrame";
				String mouseHover = "MoveToElement";
				String swithToDefaultContent = "SwitchToDefaultContent";
				String screenshot = "Screenshot";
				String mouseAction = "MouseAction";
				String switchToWindow = "SwitchToWindow";
				String switchToWindowAndClose = "SwitchToWindowAndClose";
				String imageUpload = "UploadFile";

				if (data.equals(get)) {

					try {
						driver.get(ut.getdata(sheetIndex, rcount, colPropertyValue));
						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {

						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
					// Action
				} else if (data.equals(mouseAction)) {
					try {

						String subAction = ut.getdata(sheetIndex, rcount, colSubAction);
						String enter = "ENTER";
						String arroDown = "ARROW_DOWN";
						String arroUp = "ARROW_UP";
						String arrowLeft = "ARROW_LEFT";
						String arrowRight = "ARROW_RIGHT";
						String Esc = "ESCAPE";
						String pageDown = "PAGE_DOWN";
						String pageUP = "PAGE_UP";
						String tab = "TAB";

						if (subAction.equals(enter)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ENTER).build().perform();

						} else if (subAction.equals(arroDown)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ARROW_DOWN).build().perform();

						} else if (subAction.equals(arroUp)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ARROW_UP).build().perform();

						} else if (subAction.equals(arrowLeft)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ARROW_LEFT).build().perform();

						} else if (subAction.equals(arrowRight)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ARROW_RIGHT).build().perform();

						} else if (subAction.equals(Esc)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.ESCAPE).build().perform();

						} else if (subAction.equals(pageDown)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.PAGE_DOWN).build().perform();

						} else if (subAction.equals(pageUP)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.PAGE_UP).build().perform();

						} else if (subAction.equals(tab)) {
							Actions ac = new Actions(driver);
							ac.sendKeys(Keys.TAB).build().perform();

						} else {
							System.out.println("Mouse Action not performed");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(screenshot)) {
					try {

						String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
						char charvalue = checkvalue.charAt(0); // get fisrt character of username
						String firstcharater = String.valueOf(charvalue); // convert char to string

						String valueOfChar = "{";

						if (firstcharater.contentEquals(valueOfChar))// compare two first string
						{
							int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

							Utilitylib1.Screenshot(driver, ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

						} else {

							Utilitylib1.Screenshot(driver, ut.getdata(sheetIndex, rcount, colValue));

						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(mouseHover)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {
							WebElement element = driver
									.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();

						} else if (propertyType.equals(id)) {
							WebElement element = driver
									.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(className)) {
							WebElement element = driver
									.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(name)) {
							WebElement element = driver
									.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(tagName)) {
							WebElement element = driver
									.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(linkText)) {
							WebElement element = driver
									.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(partialLinkText)) {
							WebElement element = driver
									.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else if (propertyType.equals(cssSelector)) {
							WebElement element = driver
									.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)));
							Actions action = new Actions(driver);
							action.moveToElement(element).build().perform();
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(swithToFrame)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String id = "id";
						String name = "name";

						if (propertyType.equals(id)) {
							driver.switchTo().frame(ut.getdata(sheetIndex, rcount, colPropertyValue));
						} else if (propertyType.equals(name)) {
							driver.switchTo().frame(ut.getdata(sheetIndex, rcount, colPropertyValue));
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(swithToDefaultContent)) {
					try {

						driver.switchTo().defaultContent();

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(click)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {
							driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(id)) {
							driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(className)) {
							driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(name)) {
							driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(tagName)) {
							driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(linkText)) {
							driver.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue))).click();
						} else if (propertyType.equals(partialLinkText)) {
							driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.click();
						} else if (propertyType.equals(cssSelector)) {
							driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.click();
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(isdisplay)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {
							driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(id)) {
							driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(className)) {
							driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(name)) {
							driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(tagName)) {
							driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(linkText)) {
							driver.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(partialLinkText)) {
							driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else if (propertyType.equals(cssSelector)) {
							driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)));
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(clear)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {
							driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(id)) {
							driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(className)) {
							driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(name)) {
							driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(tagName)) {
							driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(partialLinkText)) {
							driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.clear();
						} else if (propertyType.equals(linkText)) {
							driver.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue))).clear();
						} else if (propertyType.equals(cssSelector)) {
							driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.clear();
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(dropdownvalue)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {

							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}

						} else if (propertyType.equals(id)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}

						} else if (propertyType.equals(className)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}

						} else if (propertyType.equals(name)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(tagName)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(partialLinkText)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver.findElement(
										By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver.findElement(
										By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(linkText)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(cssSelector)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								WebElement mySelectElement = driver
										.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));
							} else {
								WebElement mySelectElement = driver
										.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)));
								Select dropdown = new Select(mySelectElement);
								dropdown.selectByVisibleText(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(sendkey)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String linkText = "LinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {

							////////////////////////////////////////
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));
							}

							///////////////////////////////////////

						} else if (propertyType.equals(id)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));

							}
						} else if (propertyType.equals(className)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));

							}
						} else if (propertyType.equals(name)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(tagName)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));

							}
						} else if (propertyType.equals(linkText)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.linkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));
							}
						} else if (propertyType.equals(partialLinkText)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));

							}
						} else if (propertyType.equals(cssSelector)) {
							String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
							char charvalue = checkvalue.charAt(0); // get fisrt character of username
							String firstcharater = String.valueOf(charvalue); // convert char to string

							String valueOfChar = "{";

							if (firstcharater.contentEquals(valueOfChar))// compare two first string
							{
								int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

								driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

							} else {
								driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)))
										.sendKeys(ut.getdata(sheetIndex, rcount, colValue));

							}
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(submit)) {
					try {

						String propertyType = ut.getdata(sheetIndex, rcount, colPropertyType);
						String xpath = "xpath";
						String id = "id";
						String className = "class";
						String name = "name";
						String tagName = "tagName";
						String partialLinkText = "partialLinkText";
						String cssSelector = "cssSelector";

						if (propertyType.equals(xpath)) {
							driver.findElement(By.xpath(ut.getdata(sheetIndex, rcount, colPropertyValue))).submit();
						} else if (propertyType.equals(id)) {
							driver.findElement(By.id(ut.getdata(sheetIndex, rcount, colPropertyValue))).submit();
						} else if (propertyType.equals(className)) {
							driver.findElement(By.className(ut.getdata(sheetIndex, rcount, colPropertyValue))).submit();
						} else if (propertyType.equals(name)) {
							driver.findElement(By.name(ut.getdata(sheetIndex, rcount, colPropertyValue))).submit();
						} else if (propertyType.equals(tagName)) {
							driver.findElement(By.tagName(ut.getdata(sheetIndex, rcount, colPropertyValue))).submit();
						} else if (propertyType.equals(partialLinkText)) {
							driver.findElement(By.partialLinkText(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.submit();
						} else if (propertyType.equals(cssSelector)) {
							driver.findElement(By.cssSelector(ut.getdata(sheetIndex, rcount, colPropertyValue)))
									.submit();
						} else {
							System.out.println("Attribute is not found");
						}

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(wait1)) {
					try {

						double i = ut.getdataNumeric(sheetIndex, rcount, colPropertyValue);

						Thread.sleep((long) i);

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(switchToWindow)) {
					try {

						double i = ut.getdataNumeric(sheetIndex, rcount, colPropertyValue);
						int windowindex = (int) i;

						ArrayList<String> windall = new ArrayList<String>(driver.getWindowHandles());

						driver.switchTo().window(windall.get(windowindex));

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(switchToWindowAndClose)) {
					try {

						double i = ut.getdataNumeric(sheetIndex, rcount, colPropertyValue);
						int windowindex = (int) i;

						ArrayList<String> windall = new ArrayList<String>(driver.getWindowHandles());

						driver.switchTo().window(windall.get(windowindex));
						driver.close();

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else if (data.equals(imageUpload)) {
					try {
						////////////////////////////////////////
						String checkvalue = ut.getdata(sheetIndex, rcount, colValue); // get string {username}
						char charvalue = checkvalue.charAt(0); // get fisrt character of username
						String firstcharater = String.valueOf(charvalue); // convert char to string

						String valueOfChar = "{";

						if (firstcharater.contentEquals(valueOfChar))// compare two first string
						{
							int getcolum = ut.find(sheetIndex, rcount, colValue, 1, 0);

							Thread.sleep(2000);
							Runtime.getRuntime().exec(ut.getdata(1, 1 + loopValueOfSendkey, getcolum));

						} else {

							Thread.sleep(2000);
							Runtime.getRuntime().exec(ut.getdata(sheetIndex, rcount, colValue));

						}

						///////////////////////////////////////

						System.out.println("Attribute is not found");

						ut.write(sheetIndex, rcount, colStatus, "Pass");
					} catch (NoSuchElementException e) {
						ut.write(sheetIndex, rcount, colStatus, "Fail");
					}
				} else {
					int count = rcount;
					System.out.println("Action value is blank in row: " + count + " !");

				}
				Thread.sleep(1000);

			}
			System.out.println("----------------------------- Script loop count :" + loopValueOfSendkey + 1
					+ "---------------------------------------");
		}

	}

}