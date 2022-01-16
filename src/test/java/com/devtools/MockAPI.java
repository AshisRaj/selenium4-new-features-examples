package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.fetch.Fetch;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.Optional;

/**
 *  Consider the example, we have a website which internally calls some API,
 *  now what happens if we change the API parameters, what happens if we change the request information.
 *  Mocking can be done in selenium using CDP commands
 *
 *  Let’s consider the example,
 *  Navigate to https://rahulshettyacademy.com/angularAppdemo/
 *  Click on Virtual Library
 *  It loads the table with book details.
 *  If you open the Chrome Network Tab,
 *  internally it is making call to /GetBook.php?AuthorName=Shetty
 *  Now, let’s change the network URL parameters programmatically,
 *  We have /GetBook.php?AuthorName=Shetty API call
 *  for that lets replace Shetty with Unkown and lets see what happens
 */
public class MockAPI {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        chromeDevTools.addListener(Fetch.requestPaused(), req -> {
            if (req.getRequest().getUrl().contains("=shetty")) {
                String mock = req.getRequest().getUrl().replace("=shetty", "=Unknown");
                chromeDevTools.send(Fetch.continueRequest(req.getRequestId(), Optional.of(mock), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty()));
            }
            else {
                chromeDevTools.send(Fetch.continueRequest(req.getRequestId(), Optional.of(req.getRequest().getUrl()),
                    Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
            }
        });

        driver.get("https://www.rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[contains(text(),'Virtual Library')] ")).click();

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();

    }
}