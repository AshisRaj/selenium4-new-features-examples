package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.emulation.Emulation;

import java.util.Optional;

/**
 * Testing the location-based functionality of applications
 * such as different offers, currencies, taxation rules,
 * freight charges and date/time format for various geolocations
 * is difficult because setting up the infrastructure
 * for all of these physical geolocations is not a feasible solution.
 * With mocking the geolocation, we could cover all the aforementioned scenarios and more.
 */
public class MockGeoLocation {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Emulation.setGeolocationOverride(
            Optional.of(48.8584),
            Optional.of(2.2945),
            Optional.of(100)));

        driver.get("https://mycurrentlocation.net/");

        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}