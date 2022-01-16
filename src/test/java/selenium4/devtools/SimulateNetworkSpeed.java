package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.network.model.ConnectionType;
import org.openqa.selenium.devtools.v96.security.Security;

import java.util.Optional;

/**
 * Consider we have a scenario, we need to test our website how
 * it loads under slow internet connection, like 2G, 3G, 4G, etc.
 * If our website is tested under slow bandwidth conditions and
 * if it is optimized to work in slow bandwidth it works normally.
 * The Selenium 4 with Chrome DevTools Protocol provides option
 * to control the network bandwidth it is called network emulation.
 */
public class SimulateNetworkSpeed {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        //simulate network and then launch
        chromeDevTools.send(Network.emulateNetworkConditions(
                false,
                20,
                20,
                50,
                Optional.of(ConnectionType.CELLULAR2G)
        ));

        driver.get("https://www.google.com");

        chromeDevTools.send(Security.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}