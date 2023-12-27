package test.Pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import test.Utils.PropertiesFileUtils;

public class LoginPage {
	@SuppressWarnings("unused")
	private WebDriverWait wait;
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}
	
	public void accessLoginPage () throws InterruptedException {
		String LoginURL = PropertiesFileUtils.getProperty("icon_signIn");
		WebElement loginPageIcon = driver.findElement(By.xpath(LoginURL));
		loginPageIcon.click();
		wait = new WebDriverWait(driver, 10);
	}
	
	public void enterEmail (String email) throws InterruptedException {
		WebElement inputEmail = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_email")));
		inputEmail.sendKeys(email);
		
		Thread.sleep(1000);
	}
	
	public void enterPassword (String pass) throws InterruptedException {
		WebElement inputPass = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_pass")));
		inputPass.sendKeys(pass);
		
		Thread.sleep(1000);
	}
	
	public void clickLoginBtn () throws InterruptedException {
		WebElement btnLogin = driver.findElement(By.xpath(PropertiesFileUtils.getProperty("login_button")));
		btnLogin.click();
		
		Thread.sleep(1000);
	}
	
	public void clickSignOutBtn () throws InterruptedException {
		String signoutURL = PropertiesFileUtils.getProperty("icon_signOut");
		WebElement signoutIcon = driver.findElement(By.xpath(signoutURL));
		signoutIcon.click();
														
		Thread.sleep(1000);
	}

}
