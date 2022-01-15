package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.network.model.Headers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.System.getProperty;

/**
 * Interacting with browser popups is not supported in Selenium,
 * as it is only able to engage with DOM elements.
 * This poses a challenge for pop-ups such as authentication dialogs.
 *
 * We can bypass this by using the CDP APIs to handle the authentication directly with DevTools.
 */
public class BasicAuthentication {
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        //Open website
        driver.get("https://jigsaw.w3.org/HTTP/");

        //Send authorization header
        Map<String, Object> headers = new HashMap<>();
        String basicAuth ="Basic " + new String(new Base64().encode(String.format("%s:%s", USERNAME, PASSWORD).getBytes()));
        headers.put("Authorization", basicAuth);
        chromeDevTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        //Click authentication test - this normally invokes a browser popup if unauthenticated
        driver.findElement(By.linkText("Basic Authentication test")).click();

        String loginSuccessMsg = driver.findElement(By.tagName("html")).getText();
        if(loginSuccessMsg.contains("Your browser made it!")) {
            System.out.println("Login successful");
        }
        else {
            System.out.println("Login failed");
        }

        driver.quit();
    }
}
