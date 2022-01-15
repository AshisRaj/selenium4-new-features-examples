package com.waits;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;

import static java.lang.System.getProperty;

public class Wait {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws IOException {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        // TimeoutException: timeout: Timed out receiving message from renderer: 0.388
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(1));

        driver.get("https://jqueryui.com/droppable/");
    }
}
