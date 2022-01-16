package selenium4.devtools;

import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.devtools.v96.network.Network;
import  org.openqa.selenium.devtools.v96.network.model.Headers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Adding extra header to the HTTP request is helpful when our application under test
 * exposes filters requests or exposes specific features depending on the received headers.
 */
public class AddCustomHeaders {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        //enable Network
        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //set custom header
        String basicAuth ="Basic " + new String(new Base64().encode(String.format("%s:%s", "dummy", "dummy").getBytes()));
        Map<String, Object> extraHeaders = new HashMap()
         {{
            put("Authorization", basicAuth);
            put("testing", "selenium");
         }};
        chromeDevTools.send(Network.setExtraHTTPHeaders(new Headers(extraHeaders)));

        //add event listener to verify that requests are sending with the custom header
        chromeDevTools.addListener(Network.requestWillBeSent(),
            requestSent ->
            {
                requestSent.getRequest().getHeaders().toJson().forEach((k, v) ->
                    {
                        System.out.println((k + ":" + v));
                    });
            });

        driver.get("https://google.com");

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
