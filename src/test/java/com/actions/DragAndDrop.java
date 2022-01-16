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

public class DragAndDrop {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://jqueryui.com/droppable/");

        driver.switchTo().frame(0);

        WebElement source = driver.findElement(By.id("draggable"));
        WebElement destination = driver.findElement(By.id("droppable"));

        Actions action = new Actions(driver);
        //Alternate way: action.clickAndHold(source).moveToElement(destination).release().build().perform();
        action.clickAndHold(source).release(destination).build().perform();

        File src = destination.getScreenshotAs(OutputType.FILE);
        File dest = new File(PROJECT_PATH + "/Screenshots/drag_and_release.png");
        FileHandler.copy(src, dest);

        Thread.sleep(3000);
        driver.quit();
    }
}
