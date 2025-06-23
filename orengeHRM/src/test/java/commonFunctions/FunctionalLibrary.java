package commonFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionalLibrary {
	
	public static WebDriver driver;
	public static Properties compro;
	
	
	public static WebDriver startBrowser() throws Throwable, Throwable {
		
		compro= new Properties();
		compro.load(new FileInputStream("./ORGproperties/org.properties"));
		
		if(compro.getProperty("browser").equalsIgnoreCase("chrome")) {
			driver= new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(compro.getProperty("browser").equalsIgnoreCase("edg")) {
			driver= new EdgeDriver();
			driver.manage().window().maximize();
		}
		else {
			try {
			throw new IllegalAccessException("Invalid browser value ");
			} catch (Throwable e) {
				System.out.println(e.getMessage());
			}
		}
		
		return driver;
		
	}
	
	public static void lanchUrl() {
		driver.get(compro.getProperty("url"));
	}

	
	public static void Typeaction(String locatertype ,String locatervalue ,String Testdata) {
		
		if(locatertype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatervalue)).clear();
			driver.findElement(By.xpath(locatervalue)).sendKeys(Testdata);
		}
		if(locatertype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatervalue)).clear();
			driver.findElement(By.id(locatervalue)).sendKeys(Testdata);
		}
		if(locatertype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatervalue)).clear();
			driver.findElement(By.name(locatervalue)).sendKeys(Testdata);
		}
		
	}
	
	public static void ClickAction(String locatertype ,String locatervalue) {
		
		if(locatertype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatervalue)).click();			
		}
		if(locatertype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatervalue)).sendKeys(Keys.ENTER);			
		}
		if(locatertype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatervalue)).sendKeys(Keys.ENTER);			
		}
		
	}
	
	public static void vefirycurrentURL(String Testdata) {
		String expected_URL="dashbord";
		String Actual_URL=driver.getCurrentUrl();
		try {
			Assert.assertEquals(Actual_URL, expected_URL, "Invalid Title value");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void closebrowser() {
		driver.quit();
	}
	public static void waitforelement(String locatertype ,String locatervalue ,String Testdata) {
		
		if(locatertype.equalsIgnoreCase("xpath")) {
			WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Testdata)));
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(locatervalue))));	
		}
		if(locatertype.equalsIgnoreCase("id")) {
			WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Testdata)));
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(locatervalue))));	
		}
		if(locatertype.equalsIgnoreCase("name")) {
			WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Testdata)));
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name(locatervalue))));	
		}
		
	}
	public static void Threadsleep(String Testdata) throws Throwable, Throwable {
		Thread.sleep(Integer.parseInt(Testdata));
	}
}
