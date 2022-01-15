package com.devtools;

/**
 * Page loading strategy: Defines the current session’s page loading strategy.
 * By default, when Selenium WebDriver loads a page, it follows the normal pageLoadStrategy.
 * It is always recommended stopping download of additional resources (like images, css, js),
 * when the page loading takes lot of time.
 *
 * Consider example, if you want to validate the website
 * blocking all the .css files (that is website without styles),
 * you need to block all the urls/files which are having extensions with .css.
 * Similarly, you might have a scenario to test blocking certain API calls.
 *
 * These scenarios can be tested using Chrome DevToos Protocol(CDP) in selenium 4.
 */
import com.google.common.collect.ImmutableList;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.System.getProperty;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.Optional;

public class BlockURLAndResources {

    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        // Blocks all css, jpg, png and js files to be downloaded/loaded
        chromeDevTools.send(Network.setBlockedURLs(ImmutableList.of("*.css", "*.jpg","*.png","*.js")));

        chromeDevTools.addListener(Network.loadingFailed(),
                loadingFailed -> System.out.println("Blocking reason: "
                        + loadingFailed.getBlockedReason().get()));

        //Hitting the URL you want to test
        driver.get("https://www.google.com");

        chromeDevTools.send(Network.disable());

        // To close the browser window
        //driver.quit();
    }
}
