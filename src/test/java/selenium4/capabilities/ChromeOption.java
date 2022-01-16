package selenium4.capabilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeOption {
    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.setAcceptInsecureCerts(false);

        // This is to route the network call via proxy
        // e.g. to perform security testing via ZAP
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("localhost:8080");
        chromeOptions.setCapability("proxy", proxy);

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.google.com/");

        Thread.sleep(3000);
        driver.quit();
    }
}
