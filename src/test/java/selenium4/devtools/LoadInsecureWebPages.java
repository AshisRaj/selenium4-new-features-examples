package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.security.Security;

/**
 *** Loading Insecure Website ***
 The browser usually blocks loading the HTTPS website if the certificate has some errors.
 Many times, we will not be able to proceed with such website automation.
 CDP provides the option to ignore those certificate errors
 and we can load that website on the browser and test it.
 */
public class LoadInsecureWebPages {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Security.enable());
        chromeDevTools.send(Security.setIgnoreCertificateErrors(true));

        driver.get("https://expired.badssl.com/");

        chromeDevTools.send(Security.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
