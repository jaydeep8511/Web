package utilsFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilitylib {

	XSSFWorkbook wb;
	XSSFSheet sheet1;
	File src;

	public Utilitylib(String excelPath) {
		try {
			src = new File(excelPath);
			FileInputStream fis = new FileInputStream(src);
			wb = new XSSFWorkbook(fis);
			sheet1 = wb.getSheetAt(0);
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	public String getdata(int sheetNumber, int row, int colum) {

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

	

	public String date() throws Exception {
		// Create object of SimpleDateFormat class and decide the format
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		// get current date time with Date()
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date);
		// System.out.println(date1);
		return date1;
	}

	public String dateOnly() throws Exception {
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

}
