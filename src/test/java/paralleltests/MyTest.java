package paralleltests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyTest {

	private RemoteWebDriver driver;

	@BeforeMethod
	@Parameters({ "browser"})
	public void setup(String browser) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setBrowserName(browser);
		capabilities.setPlatform(Platform.LINUX);
		WebDriverManager.getInstance(browser).setup();

		driver = new RemoteWebDriver(new URL("http://<EC2 instance IP>:4444/wd/hub"), capabilities);

	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testSearch() {
		driver.get("https://www.google.com");
		driver.findElementByName("q").sendKeys("Selenium");
		driver.findElementByName("btnK").click();
		String expectedTitle = "Selenium - Google Search";
		String actualTitle = driver.getTitle();
		assert expectedTitle.equals(actualTitle);
	}

}
