package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.interactions.Actions;

import static java.lang.System.getProperty;

/**
 * We all rely on logs for debugging and analysing the failures.
 * While testing and working on an application with specific data or specific conditions,
 * logs help us in debugging and capturing the error messages,
 * giving more insights that are published in the Console tab of the Chrome DevTools.
 *
 * We can capture the console logs through our Selenium scripts
 * by calling the CDP Log commands as demonstrated below.
 */
public class AccessConsoleLogs {

    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        Actions act = new Actions(driver);
        WebElement toDoList= driver.findElement(By.id("toDoListBtn"));

        // click method - to click on a webElement
        act.moveToElement(toDoList).click();

        // double click - double click on a webElement
        act.moveToElement(toDoList).doubleClick();

        // Context click - Right click on a webElement
        act.moveToElement(toDoList).contextClick();

        //clickAndHold method - click and hold on a webElement without releasing
        act.moveToElement(toDoList).clickAndHold();

        // release -release the hold on a webElement
        act.moveToElement(toDoList).release();

        Actions act1 = new Actions(driver);
        WebElement toDoList1= driver.findElement(By.id("toDoListBtn"));

        // click method - to click on a webElement
        act1.click(toDoList);

        //clickAndHold method - click and hold on a webElement without releasing
        act1.clickAndHold(toDoList);

        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Log.enable());
        chromeDevTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: "+logEntry.getText());
                    System.out.println("level: "+logEntry.getLevel());
                });

        driver.get("https://play.google.com/log?format=json&hasfast=true&authuser=0");
    }
}
