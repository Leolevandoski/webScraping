package com.leo.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

	private static WebDriver driver;

	private DriverFactory() {

	}

	public static WebDriver getDriver() {

		if (driver == null) {
			
			System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
//			System.setProperty("webdriver.chrome.driver", "/eclipse/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-agent=YOUR_USER_AGENT");
//			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-bundled-ppapi-flash");
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
		}

		return driver;

	}

	public static void killDriver() {

		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

}
