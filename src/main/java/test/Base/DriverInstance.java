package test.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import test.Utils.PropertiesFileUtils;

public class DriverInstance {
	protected WebDriver driver;
	String baseURL = PropertiesFileUtils.getProperty("base_url");
	
//	khởi tạo Chrome tự động với WebDriverManager	
	@BeforeClass
	public void openBrowser() throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(baseURL);
		
		Thread.sleep(1000);
	}
	
//	đóng Chrome sau khi hoàn tất kiểm thử
	@AfterClass
	public void closeBrowser() {
		driver.close();
		System.out.println("Close Chrome browser: SUCCESSFULL");
	}

}
