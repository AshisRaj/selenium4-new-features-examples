package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In case your website changes depending on your end-user’s time zone,
 * you can test that functionality by specifying a time zone you want to test in.
 * You can configure your tests to run on a custom time zone.
 */
public class MockTimeZone {
    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("timezoneId", "America/Los_Angeles");
        chromeDevTools.send(Network.enable(Optional.of(100000000), Optional.empty(), Optional.empty()));
        ((ChromeDriver)driver).executeCdpCommand("Emulation.setTimezoneOverride", deviceMetrics);

        driver.get("https://momentjs.com/");

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
