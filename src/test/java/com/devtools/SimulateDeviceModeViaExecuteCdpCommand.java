package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import java.util.HashMap;

/**
 * Most of the applications we build today are responsive
 * to cater to the needs of the end users
 * coming from a variety of platforms,
 * devices like phones, tablets, wearable devices, desktops  and orientations.
 *
 * As testers, we might want to place our application
 * in various dimensions to trigger the responsiveness of the application.
 *
 */
public class SimulateDeviceModeViaExecuteCdpCommand {

    public static void main(String[] args) throws InterruptedException {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        HashMap deviceMetrics = new HashMap()
        {{
            put("width", 600);
            put("height", 1000);
            put("mobile", true);
            put("deviceScaleFactor", 50);
        }};
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

        driver.get("https://www.zoomcar.com");
        Thread.sleep(3000);
        driver.quit();
    }
}