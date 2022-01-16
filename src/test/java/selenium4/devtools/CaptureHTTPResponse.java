package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.Optional;

public class CaptureHTTPResponse {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        chromeDevTools.addListener(Network.responseReceived(), responseReceieved -> {

            System.out.println("Response Url => " + responseReceieved.getResponse().getUrl());
            System.out.println("Response Status => " + responseReceieved.getResponse().getStatus());
            System.out.println("Response Status Text => " + responseReceieved.getResponse().getStatusText());
            System.out.println("Response Headers => " + responseReceieved.getResponse().getHeaders().toString());
            System.out.println("Response MIME Type => " + responseReceieved.getResponse().getMimeType());
            System.out.println("Response Time => " + responseReceieved.getResponse().getResponseTime().get().toJson());

            System.out.println("------------------------------------------------------");

        });
        //Hitting the URL you want to test
        driver.get("https://www.booking.com");

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
