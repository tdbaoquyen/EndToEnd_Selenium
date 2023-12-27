package test.TestCase;

import static org.testng.Assert.*;

import java.io.*;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import test.Base.DriverInstance;
import test.Pom.LoginPage;
import test.Utils.CaptureScreenshot;
import test.Utils.PropertiesFileUtils;

public class TC_LoginTest extends DriverInstance {
	public String keyWord = "Winter Top";
	WebDriverWait wait;
	
	@Test
	public void AccessLoginPage () throws InterruptedException {
		String LoginURL = PropertiesFileUtils.getProperty("icon_signIn");
		WebElement loginPageIcon = driver.findElement(By.xpath(LoginURL));
		loginPageIcon.click();
		wait = new WebDriverWait(driver, 10);
	}

	
	@Test (dataProvider = "Excel")
	public void TC01_LoginExcelAccount (String email, String pass) throws InterruptedException {

	//	Lấy ra method đăng nhập Account từ class LoginPage
		LoginPage loginPage = new LoginPage(driver);
		PageFactory.initElements(driver, loginPage);
		
		loginPage.accessLoginPage();
		loginPage.enterEmail(email);
		loginPage.enterPassword(pass);
		loginPage.clickLoginBtn();
		loginPage.clickSignOutBtn();
		
		System.out.println(" ==> TC01_LoginExcelAccount: SUCCESSFULL");
		Thread.sleep(1000);
	}
	
	
//	1 - KHỞI TẠO METHOD LẤY RA DỮ LIỆU TỪ FILE DATA EXCEL	

	@SuppressWarnings("resource")
	@DataProvider(name = "Excel")
	public Object[][] getDataFromExcel() throws IOException {
	
	//	khởi tạo đường dẫn tới folder chứa file data excel có sẵn	
		FileInputStream inputFile = new FileInputStream ("./dataExcel/assignment2_data_test.xlsx");
		
	//	khởi tạo method đọc và lấy dữ liệu từ file excel
		XSSFWorkbook workbook = new XSSFWorkbook(inputFile);
		XSSFSheet loginSheet = workbook.getSheetAt(0);
		
		int numberOfRowData = loginSheet.getPhysicalNumberOfRows();
		Object[][] dataExcel = new Object[numberOfRowData][2];
		// có 02 cột (email / password) & n hàng nên chỉ số tương ứng sẽ là từ 0 -> numberOfRow -1
		
	//	Lặp lại để lần lượt đọc các dữ liệu có trong file Excel
		for (int i=0; i< numberOfRowData; i++) {
			XSSFRow row = loginSheet.getRow(i);
			XSSFCell emailCell = row.getCell(0);
			XSSFCell passCell = row.getCell(1);
			
		// gán dữ liệu excel vào đối tượng Object[][]
			dataExcel[i][0] = emailCell.getStringCellValue(); // row i column 0
			dataExcel[i][1] = passCell.getStringCellValue();  // row i column 1
		}
		return dataExcel;
	}


//	2 - KHỞI TẠO METHOD REGISTER ACCOUNT
	@Test
	public void TC02_RegisterAccount () throws InterruptedException {
			Actions action = new Actions(driver);
		
			//	Lấy ra method truy cập trang LoginPage từ class LoginPage
			LoginPage loginPage = new LoginPage(driver);
			PageFactory.initElements(driver, loginPage);
			loginPage.accessLoginPage();
			
			wait = new WebDriverWait(driver, 5);
			Thread.sleep(1000);
			
			//	Đăng ký tài khoản mới
			WebElement enterName = driver.findElement(By.xpath("//input[@placeholder='Name']"));
			enterName.sendKeys(PropertiesFileUtils.getProperty("name"));
			Thread.sleep(1000);
			
			WebElement enterNewEmail = driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
			enterNewEmail.sendKeys(PropertiesFileUtils.getProperty("email"));
			Thread.sleep(1000);
			
			WebElement signUpBtn = driver.findElement(By.xpath("//button[contains(text(),'Signup')]"));
			signUpBtn.click();

			wait = new WebDriverWait(driver, 5);


			//	Điền thông tin tài khoản
			String signUp_URL = "https://automationexercise.com/signup";
			assertEquals(signUp_URL, driver.getCurrentUrl(), " ==> signUp_URL: NOT FOUND");
			
			String titleSignUpPage = "ENTER ACCOUNT INFORMATION";
			WebElement signUpPage_title = driver.findElement(By.xpath("//b[contains(text(),'Enter Account Information')]"));
			assertEquals(titleSignUpPage, signUpPage_title.getText(), " ==> signUpPage_title: INVALID");
			
			//	Chọn danh xưng
			WebElement radio_Mrs = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='id_gender2']")));
			radio_Mrs.click();
			Thread.sleep(1000);
						
			//	Nhập password
			WebElement signUpPage_pass = driver.findElement(By.xpath("//input[@id='password']"));
			signUpPage_pass.sendKeys(PropertiesFileUtils.getProperty("password"));
			Thread.sleep(1000);
						
			//	Nhập ngày sinh
			WebElement signUpPage_day = driver.findElement(By.xpath("//select[@id='days']"));
			signUpPage_day.sendKeys("10");
			Thread.sleep(1000);
			
			//	Nhập tháng sinh
			WebElement signUpPage_month = driver.findElement(By.xpath("//select[@id='months']"));
			signUpPage_month.sendKeys("October");
			Thread.sleep(1000);
			
			//	Nhập năm sinh
			WebElement signUpPage_year = driver.findElement(By.xpath("//select[@id='years']"));
			signUpPage_year.sendKeys("2000");
			Thread.sleep(1000);
			
			//	Click checkBox: signUp
			WebElement signUpPage_checkBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='newsletter']")));
			signUpPage_checkBox.click();
			Thread.sleep(1000);

			action.sendKeys(Keys.PAGE_DOWN).perform();
			Thread.sleep(1000);
						
			//	Nhập First Name
			WebElement signUpPage_firstName = driver.findElement(By.xpath("//input[@id='first_name']"));
			signUpPage_firstName.sendKeys("Tester");
			Thread.sleep(1000);

			//	Nhập Last Name
			WebElement signUpPage_lastName = driver.findElement(By.xpath("//input[@id='last_name']"));
			signUpPage_lastName.sendKeys("newest");
			Thread.sleep(1000);


			//	Nhập Address
			WebElement signUpPage_Address = driver.findElement(By.xpath("//input[@id='address1']"));
			signUpPage_Address.sendKeys("123 Le Duan");
			Thread.sleep(1000);

			// Nhập Country
			WebElement signUpPage_Country = driver.findElement(By.xpath("//select[@id='country']"));
			signUpPage_Country.sendKeys("Singapore");
			Thread.sleep(1000);

			//	Nhập State
			WebElement signUpPage_State = driver.findElement(By.xpath("//input[@id='state']"));
			signUpPage_State.sendKeys("Bugis");
			Thread.sleep(1000);

			//	Nhập City
			WebElement signUpPage_City = driver.findElement(By.xpath("//input[@id='city']"));
			signUpPage_City.sendKeys("Bugis");
			Thread.sleep(1000);

			//	Nhập ZipCode
			WebElement signUpPage_ZipCode = driver.findElement(By.xpath("//input[@id='zipcode']"));
			signUpPage_ZipCode.sendKeys("12345");
			Thread.sleep(1000);

			action.sendKeys(Keys.PAGE_DOWN).perform();
			
			//	Nhập Phone
			WebElement signUpPage_Phone = driver.findElement(By.xpath("//input[@id='mobile_number']"));
			signUpPage_Phone.sendKeys("123456789");
			Thread.sleep(1000);
			
			// Click Create Account
			WebElement signUpPage_CreateBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Create Account')]")));
			signUpPage_CreateBtn.click();
			Thread.sleep(1000);
			
			wait = new WebDriverWait(driver, 5);
			Thread.sleep(2000);
			
			//	Kiểm thử trang thông báo khởi tạo tài khoản
			String createPage_titlePage = "ACCOUNT CREATED!";
			WebElement createPage_title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'Account Created!')]")));
			assertEquals(createPage_titlePage, createPage_title.getText(), " ==> createPage_title: INVALID");
			
			String createPageMsg = "Congratulations! Your new account has been successfully created!";
			WebElement createPage_Msg = driver.findElement(By.xpath("//p[contains(text(),'Congratulations! Your new account has been success')]"));
			assertEquals(createPageMsg, createPage_Msg.getText(), " ==> createPage_Msg: INVALID");
			
			WebElement createPage_Continue = driver.findElement(By.xpath("//a[contains(text(),'Continue')]"));
			createPage_Continue.click();
			Thread.sleep(1000);
			
			System.out.println(" ==> TC02_RegisterAccount: SUCCESSFULL");
	}
/*	
//	2 - KHỞI TẠO METHOD LOGIN ACCOUNT FROM Properties	
	@Test
	public void TC02_LoginAccount () throws InterruptedException {
		
		//	Chuyển về trang LoginPage
			String LoginURL = PropertiesFileUtils.getProperty("icon_signIn");
			WebElement loginPageIcon = driver.findElement(By.xpath(LoginURL));
			loginPageIcon.click();
			wait = new WebDriverWait(driver, 5);

			WebElement inputEmail = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_email")));
			inputEmail.sendKeys(PropertiesFileUtils.getProperty("email"));
			Thread.sleep(1000);
			
			WebElement inputPass = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_pass")));
			inputPass.sendKeys(PropertiesFileUtils.getProperty("password"));
			Thread.sleep(1000);
		
			WebElement btnLogin = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_button")));
			btnLogin.click();
			wait = new WebDriverWait(driver, 10);
						
		//	Kiểm tra thông tin login account
			WebElement nameAccount = driver.findElement(By.xpath("//b[contains(text(),'quyen0989')]"));
			assertEquals("quyen0989", nameAccount.getText(), "Name of Account: INVALID");
			
			System.out.println(" ==> TC02_LoginAccount: SUCCESSFULL");
			Thread.sleep(1000);
		}
*/	

//	3 - KHỞI TẠO METHOD TÌM KIẾM SẢN PHẨM THEO KEYWORD
	@Test 
	public void TC03_accessProductPage () throws InterruptedException {
		// Truy cập Product Page
		WebElement productPage_icon = driver.findElement(By.xpath("//a[@href='/products']"));
		assertEquals(true, productPage_icon.isDisplayed(), " ==> Product Page: NOT FOUND");
		productPage_icon.click();

		System.out.println(" ==> TC03_accessProductPage: SUCCESSFULL");
		Thread.sleep(1000);
	
		//	Thực hiện nhập keyWord và tìm kiếm
		WebElement searchBox = driver.findElement(By.id("search_product"));
		assertEquals(true, searchBox.isDisplayed(), " ==> Enter search Product: UNSUCCESSFULL");
		searchBox.sendKeys(keyWord);
		
		Thread.sleep(1000);
		
		WebElement submitIcon = driver.findElement(By.id("submit_search"));
		assertEquals(true, submitIcon.isDisplayed(), " ==> Click submitIcon: UNSUCCESSFULL");
		submitIcon.click();
		
		System.out.println(" ==> TC04_searchProduct: SUCCESSFULL");
		Thread.sleep(1000);			
	}
	
	
//	4 - KHỞI TẠO METHOD KIỂM TRA THÔNG TIN KẾT QUẢ TRẢ VỀ
	@Test 
	public void TC05_verifyProductResult () throws InterruptedException {
	
	//	Kiểm tra tiêu đề "Searched Product"
		WebElement titleSearchPage = driver.findElement(By.xpath("//h2[contains(text(),'Searched Products')]"));
		assertEquals("SEARCHED PRODUCTS", titleSearchPage.getText(), "Title Search page: INVALID");
		
	//	Kiểm tra số lượng kết quả trả về
		List<WebElement> listOfResults = driver.findElements(By.className("product-image-wrapper"));
		assertEquals(1, listOfResults.size(), "Quantity of Product = 1: INVALID");

		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).perform();
		
		Thread.sleep(1000);
		
	//	Kiểm tra tên sản phẩm trả về
		WebElement resultName = driver.findElement(By.xpath("//p[contains(text(),'Winter Top')]"));
		assertEquals(keyWord, resultName.getText(), "Name of Product: INVALID");
		
	//	Kiểm tra hình ảnh sản phẩm trả về
		WebElement resultImage = driver.findElement(By.xpath("//img[@src='/get_product_picture/5']"));
		assertEquals(true, resultImage.isDisplayed(), "Image of Product: INVALID");
		
	//	Kiểm tra đơn giá sản phẩm trả về
		WebElement resultPrice = driver.findElement(By.xpath("(//h2[contains(text(),'Rs. 600')])"));
		assertEquals("Rs. 600", resultPrice.getText(), "Price of Product: INVALID");
		
//		Kiểm tra link hiển thị chi tiết sản phẩm
		WebElement resultDetailLink = driver.findElement(By.cssSelector("a[href='/product_details/5']"));
		assertEquals("View Product", resultDetailLink.getText(), "Link to view Product: INVALID");
			
	//	Kiểm tra button "Add to cart"
		WebElement resultAddtoCartBtn = driver.findElement(By.xpath("//div[@class='productinfo text-center']//a[@class='btn btn-default add-to-cart'][normalize-space()='Add to cart']"));
		assertEquals("Add to cart", resultAddtoCartBtn.getText(), "Add to Cart button: INVALID");
			
		System.out.println(" ==> TC05_verifyProductResult: SUCCESSFULL");
		Thread.sleep(1000);	
	}
	
	
//	5 - KHỞI TẠO METHOD KIỂM TRA VIEW DETAIL PRODUCT
	@Test 
	public void TC06_verifyDetailProduct () throws InterruptedException {	
		
	//	Thực hiện: click "View Product"
		WebElement resultDetail_Link = driver.findElement(By.cssSelector("a[href='/product_details/5']"));
		resultDetail_Link.click();
		
		wait = new WebDriverWait(driver, 5);
		Thread.sleep(2000);
		
		WebElement productName = driver.findElement(By.xpath("//h2[contains(text(),'Winter Top')]"));
		assertEquals(keyWord, productName.getText(), " ==> Product Name: INVALID");
		
		WebElement productCategory = driver.findElement(By.xpath("//p[contains(text(),'Category: Women > Tops')]"));
		assertEquals(true, productCategory.isDisplayed(), " ==> Product category: NOT FOUND");
		
		WebElement productPrice = driver.findElement(By.xpath("//span[contains(text(),'Rs. 600')]"));
		assertEquals(true, productPrice.isDisplayed(), " ==> Product price: NOT FOUND");
		
		WebElement productAvailability = driver.findElement(By.xpath("//b[contains(text(),'Availability:')]"));
		assertEquals(true, productAvailability.isDisplayed(), " ==> Product Availability: NOT FOUND");
		
		WebElement productCondition = driver.findElement(By.xpath("//b[contains(text(),'Condition:')]"));
		assertEquals(true, productCondition.isDisplayed(), " ==> Product Condition: NOT FOUND");
		
		WebElement productBrand = driver.findElement(By.xpath("//b[contains(text(),'Brand:')]"));
		assertEquals(true, productBrand.isDisplayed(), " ==> productBrand: NOT FOUND");
	}
	
	
//	6 - KHỞI TẠO METHOD CLICK ADD TO CART	
	@Test
	public void TC07_actionAddtoCart () throws InterruptedException {
		
	//	Thực hiện: nhập số lượng product
		WebElement quantityBox = driver.findElement(By.xpath("//input[@id='quantity']"));
		assertEquals(true, quantityBox.isDisplayed(), " ==> quantityBox: NOT FOUND");
		quantityBox.clear();
		quantityBox.sendKeys("3");
		Thread.sleep(1000);
		
	//	Thực hiện: click Add to cart
		WebElement AddtoCartBtn = driver.findElement(By.xpath("//button[normalize-space()='Add to cart']"));
		assertEquals(true, AddtoCartBtn.isDisplayed(), " ==> AddtoCartBtn: NOT FOUND");
		AddtoCartBtn.click();
		
		wait = new WebDriverWait(driver, 5);
		Thread.sleep(2000);
		
	//	Kiểm tra hiển thị popup Added to Cart	
		WebElement addToCartPopup = driver.findElement(By.className("modal-content"));
		assertEquals(true, addToCartPopup.isDisplayed(), " ==> popupAddtoCart: INVALID");

		wait = new WebDriverWait(driver, 5);
		
	//	Kiểm tra AddedMsg
		WebElement AddedMsg = driver.findElement(By.xpath("//h4[contains(text(),'Added!')]"));
		assertEquals("Added!", AddedMsg.getText(), " ==> AddedMsg: INVALID");
		
	//	Kiểm tra Add to Cart message
		WebElement addToCartMsg = driver.findElement(By.xpath("//p[contains(text(),'Your product has been added to cart.')]"));
		String expectMsg = "Your product has been added to cart.";
		assertEquals(expectMsg, addToCartMsg.getText(), " ==> addToCartMsg: INVALID");
		
	//	Kiểm tra link View Cart
		WebElement viewCartLink = driver.findElement(By.xpath("//u[contains(text(),'View Cart')]"));
		assertEquals("View Cart", viewCartLink.getText(), "Link View Cart: INVALID");
		
	//	Kiểm tra button "Continue Shopping"
		WebElement continueShoppingBtn = driver.findElement(By.xpath("//button[contains(text(),'Continue Shopping')]"));
		assertEquals("Continue Shopping", continueShoppingBtn.getText(), "Button \"Continue Shopping\": INVALID");
		
		System.out.println(" ==> TC06_verifyClickAddToCart: SUCCESSFULL");
		Thread.sleep(2000);		
	}
	
	
//	KHỞI TẠO METHOD CLICK "VIEW CART"
	@Test 
	public void TC07_verifyViewCart () throws InterruptedException {
		
		//	Thực hiện click View Cart
		WebElement viewCart_Link = driver.findElement(By.xpath("//u[contains(text(),'View Cart')]"));
		viewCart_Link.click();
		
	//	Kiểm tra khả năng chuyển trang
		WebElement titleCartPage = driver.findElement(By.xpath("//li[contains(text(),'Shopping Cart')]"));
		assertEquals("Shopping Cart", titleCartPage.getText(), "Title of Cart page: INVALID");
				
	//	Kiểm tra hiển thị thông tin Cart
		WebElement CartInfor = driver.findElement(By.xpath("//div[@id='cart_info']"));
		assertEquals(true, CartInfor.isDisplayed(), "Cart information table: NOT FOUND");
				
	//	Kiểm tra hiển thị các thành phần của Cart
		WebElement cartItem = driver.findElement(By.xpath("//td[contains(text(),'Item')]"));
		assertEquals("Item", cartItem.getText(), "\"Item\" element: INVALID");
				
		WebElement cartDescription = driver.findElement(By.xpath("//td[contains(text(),'Description')]"));
		assertEquals("Description", cartDescription.getText(), "\"Description\" element: INVALID");
				
		WebElement cartPrice = driver.findElement(By.xpath("//td[contains(text(),'Price')]"));
		assertEquals("Price", cartPrice.getText(), "\"Price\" element: INVALID");
				
		WebElement cartQuantity = driver.findElement(By.xpath("//td[contains(text(),'Quantity')]"));
		assertEquals("Quantity", cartQuantity.getText(), "\"Quantity\" element: INVALID");
				
		WebElement cartTotal = driver.findElement(By.xpath("//td[contains(text(),'Total')]"));
		assertEquals("Total", cartTotal.getText(), "\"Total\" element: INVALID");
				
		WebElement cartDeleteBtn = driver.findElement(By.xpath("//a[@class='cart_quantity_delete']"));
		assertEquals(true, cartDeleteBtn.isDisplayed(), "Delete button: NOT FOUND");
				
		System.out.println(" ==> TC07_verifyViewCart: SUCCESSFULL");
		Thread.sleep(3000);
	}
	

//	KHỞI TẠO METHOD CLICK DELETE BUTTON
	@Test 
	public void TC08_verifyDeleteBtn () throws InterruptedException {
		
	//	Thực hiện click button Delete in Cart
		WebElement cartDelete_Btn = driver.findElement(By.xpath("//a[@class='cart_quantity_delete']"));
		cartDelete_Btn.click();
		wait = new WebDriverWait(driver, 10);
		
		Thread.sleep(1000);
	
	//	Kiểm tra thông báo Empty Cart	
		WebElement emptyCartMsg = driver.findElement(By.xpath("//b[normalize-space()='Cart is empty!']"));
		assertEquals(true, emptyCartMsg.isDisplayed(), "Empty Cart Msg: INVALID");
				
	//	Kiểm tra button click here
		WebElement linkHERE = driver.findElement(By.xpath("//p[contains(text(),'Click')]//a"));
		assertEquals(true, linkHERE.isDisplayed(), "Link \"here\" to buy products: NOT FOUND");
		linkHERE.click();
		
		wait = new WebDriverWait(driver, 10);
		
		Thread.sleep(2000);
		
	//	Kiểm tra tính năng chuyển về trang Product
		String Product_URL = PropertiesFileUtils.getProperty("product_url");
		assertEquals(Product_URL,driver.getCurrentUrl(), "Access to Product Page: NOT FOUND");
				
		System.out.println(" ==> TC08_verifyDeleteBtn: SUCCESSFULL");
		Thread.sleep(1000);
	}

	
//	7 - KHỞI TẠO METHOD XÓA TÀI KHOẢN ĐÃ TẠO
	@Test
	public void TC09_deleteExistAccount () throws InterruptedException {
		//	Click button Delete Account 
		WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Delete Account']")));
		deleteBtn.click();
		 
		wait = new WebDriverWait(driver, 5);
		Thread.sleep(2000);
		 
		//	Kiểm thử trang thông báo
		WebElement detelePage_title = driver.findElement(By.xpath("//b[contains(text(),'Account Deleted!')]"));
		assertEquals("ACCOUNT DELETED!", detelePage_title.getText(), " ==> detelePage_title: INVALID");
		
		String DeleteMsg = "Your account has been permanently deleted!";
		WebElement deleteMessage = driver.findElement(By.xpath("//p[contains(text(),'Your account has been permanently deleted!')]"));
		assertEquals(DeleteMsg, deleteMessage.getText(), " ==> deleteMessage: INVALID");
		
		//	Click button Continue
		WebElement detelePage_Continue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Continue')]")));
		detelePage_Continue.click();
		
		wait = new WebDriverWait(driver, 5); 
		 
		 System.out.println(" ==> TC09_deleteExistAccount: SUCCESSFULL");
		 Thread.sleep(1000);
		 
	}
	
//	KHỞI TẠO METHOD ĐỂ CHỤP MÀN HÌNH CÁC FAILED TESTCASE	
	@AfterMethod
	public void takeScreenshot (ITestResult result) throws InterruptedException {
		// ITestResult để lấy ra trạng thái, tên và tham số của từng testcase
		// phương thức này được gọi mỗi khi @Test thực thi xong
		
		// upload FAILURE screenshot lên reportNG
		Reporter.setCurrentTestResult(result);
	
		//Kiểm tra kết quả mỗi testcase @Test:
		if (ITestResult.FAILURE == result.getStatus()) {
			try {
				// 1 - Lấy tham số đầu vào của @Test vừa chạy (email = [0] và pass = [1]
				String email = (String)result.getParameters()[0];
				
				// 2 - Lấy phần tên email để làm tên screenshot bằng thư viện String: indexOf() và substring()
				int index = email.indexOf("@");
				String accountName = email.substring(0,index);
				
				// gọi method takeScreenshot và attachScreenshot từ class CaptureScreenshot
				CaptureScreenshot.takeScreenshot(driver, accountName);
				CaptureScreenshot.attachScreenshot();
			}
			catch (Exception ex) {
				System.out.println("Error while attaching ScreenShot on ReportNG!  ==>" + ex.getMessage());
			}
		}
	}
	
}
