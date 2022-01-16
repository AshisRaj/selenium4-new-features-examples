package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.log.Log;
import org.openqa.selenium.devtools.v96.network.Network;

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

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Log.enable());
        chromeDevTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: "+logEntry.getText());
                    System.out.println("level: "+logEntry.getLevel());
                });

        driver.get("https://play.google.com/log?format=json&hasfast=true&authuser=0");

        chromeDevTools.send(Log.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
