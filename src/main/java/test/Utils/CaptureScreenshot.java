package test.Utils;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;

public class CaptureScreenshot {
	static String fileName = null;
	
//	KHAI BÁO METHOD CHỤP ẢNH MÀN HÌNH SCREENSHOT	
	public static void takeScreenshot(WebDriver driver, String testcaseName) {
	//	bắt ngoại lệ nếu không tìm thấy đường dẫn
		try {		
			// thiết lập cấu trúc tên file ảnh
			fileName = "./screenshots/" + testcaseName + java.time.LocalDate.now() + ".png";
			
			// gọi hàm chụp screenshot
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File inputFile = screenshot.getScreenshotAs(OutputType.FILE);
			
			// tạo đối tượng file ảnh với tên file ảnh đã khởi tạo và copy ảnh vào folder screenshot
			File direction = new File(fileName);
			FileHandler.copy(inputFile, direction);
		}
		catch (Exception ex) {
			System.out.println("Error while taking screenshot" + ex.getMessage());
		}	
	}

//	KHAI BÁO METHOD ĐÍNH KÈM ẢNH SCREENSHOT VÀO BÁO CÁO REPORTNG
	public static void attachScreenshot() {
	//	bắt ngoại lệ nếu không tìm thấy đường dẫn
		try {
			// gọi method để upload Screenshot lên ReportNG
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			File imageFile = new File(fileName);
			
			// upload screenshot image lên reportNG
			Reporter.log("<br><a href=" + imageFile.getAbsolutePath() + ">"	+ "<img src=\"" 
					+ imageFile.getAbsolutePath() + "\"width='360; height='240'/></a><br>");
		}
		catch (Exception ex) {
			System.out.println("Error while attaching image on ReportNG" + ex.getMessage());
		}
	}

}
