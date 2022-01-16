package com.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

import static java.lang.System.getProperty;

public class DoubleClick {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://api.jquery.com/dblclick/");
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe")));
        Actions action = new Actions(driver);
        WebElement doubleClickBox = driver.findElement(By.xpath("//span[text()='Double click the block']//parent::body/div"));

        File src = doubleClickBox.getScreenshotAs(OutputType.FILE);
        File dest = new File(PROJECT_PATH + "/Screenshots/before_double_click.png");
        FileHandler.copy(src, dest);
        System.out.println("Color Before: " +doubleClickBox.getCssValue("background-color"));

        action.doubleClick(doubleClickBox).build().perform();

        src = doubleClickBox.getScreenshotAs(OutputType.FILE);
        dest = new File(PROJECT_PATH + "/Screenshots/after_double_click.png");
        FileHandler.copy(src, dest);
        System.out.println("Color After: " + doubleClickBox.getCssValue("background-color"));

        Thread.sleep(3000);
        driver.quit();
    }
}
