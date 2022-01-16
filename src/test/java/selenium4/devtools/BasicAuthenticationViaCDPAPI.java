package selenium4.devtools;

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

/**
 * If a website uses basic or digest authentication,
 * it will prompt a dialog (browser popup) that cannot be handled through Selenium
 * as it is only able to engage with DOM elements.
 * We can bypass this by using the CDP APIs to handle the authentication directly with DevTools.
 */
public class BasicAuthenticationViaCDPAPI {

    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        // Setting up the DevTool and session
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

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
