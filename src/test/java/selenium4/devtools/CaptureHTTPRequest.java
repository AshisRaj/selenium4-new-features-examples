package selenium4.devtools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.Optional;

/**
 * We can capture the HTTP requests the application is invoking
 * and access the method, data, headers and lot more.
 */
public class CaptureHTTPRequest {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        chromeDevTools.addListener(Network.requestWillBeSent(),
            requestSent -> {
                System.out.println("Request ID => " + requestSent.getRequestId());
                System.out.println("Request URI => " + requestSent.getRequest().getUrl());
                System.out.println("Request Method => " + requestSent.getRequest().getMethod());
                System.out.println("Request Headers => " + requestSent.getRequest().getHeaders().toString());
                // requestSent.getRequest().getHeaders().toJson().forEach((k, v) -> System.out.println((k + ":" + v)));
                System.out.println("------------------------------------------------------");

                Optional<String> postData = requestSent.getRequest().getPostData();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                postData.ifPresent(p -> System.out.println("Request Body: \n" + gson.toJson(new JsonParser().parse(p))));
            });

        //Hitting the URL you want to test
        driver.get("https://www.booking.com");

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
