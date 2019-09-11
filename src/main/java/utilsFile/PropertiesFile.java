package utilsFile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

public class PropertiesFile {

	public static Properties prop;

	public PropertiesFile() throws Exception {

		FileReader reader = new FileReader("..\\Web\\src\\main\\java\\config\\url Configuration.properties");

		prop = new Properties();
		prop.load(reader);
	}

	public String signUp() throws IOException {

		return prop.getProperty("signup");
	}

	public String signIn() throws IOException {

		return prop.getProperty("signin");
	}

	public String forgotPassword() throws IOException {

		return prop.getProperty("forgotPassword");
	}

	public String numbersPage() throws IOException {

		return prop.getProperty("numbersPage");
	}
}
