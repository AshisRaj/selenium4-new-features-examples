package com.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;

import java.io.File;

import static java.lang.System.getProperty;

public class Click {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        By emailLocator = RelativeLocator.with(By.tagName("input")).near(By.id("lbl-email"));
        driver.get("http://demo.guru99.com/test/newtours/");
        Actions action = new Actions(driver);
        WebElement signOnLink = driver.findElement(By.linkText("SIGN-ON"));

        File src = signOnLink.getScreenshotAs(OutputType.FILE);
        File dest = new File(PROJECT_PATH + "/Screenshots/before_click.png");
        FileHandler.copy(src, dest);

        action.click(signOnLink).build().perform();
        src = signOnLink.getScreenshotAs(OutputType.FILE);
        dest = new File(PROJECT_PATH + "/Screenshots/after_click.png");
        FileHandler.copy(src, dest);
        Assert.assertEquals(driver.getTitle(), "Sign-on: Mercury Tours");

        Thread.sleep(3000);
        driver.quit();
    }
}
