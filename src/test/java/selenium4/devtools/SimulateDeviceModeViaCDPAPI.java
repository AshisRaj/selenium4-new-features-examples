package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.emulation.Emulation;

import java.util.HashMap;
import java.util.Optional;

public class SimulateDeviceModeViaCDPAPI {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        //set device first and then launch
        chromeDevTools.send(Emulation.setDeviceMetricsOverride(375,
                812,
                50,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        // Alternatively call like below
        // HashMap deviceMetrics = new HashMap()
        // {{
        // put("width", 600);
        // put("height", 1000);
        // put("mobile", true);
        //put("deviceScaleFactor", 50);
        // }};
        //chromeDevTools.send(new Command<>("Emulation.setDeviceMetricsOverride", deviceMetrics));

        driver.get("https://www.zoomcar.com");

        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
