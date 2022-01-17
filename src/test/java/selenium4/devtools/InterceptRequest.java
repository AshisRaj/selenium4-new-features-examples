package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.fetch.Fetch;
import org.openqa.selenium.devtools.v96.network.Network;

import java.util.Optional;

public class InterceptRequest {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
        chromeDevTools.addListener(Fetch.requestPaused(),
                request -> {
                    String url = request.getRequest().getUrl();
                    if(url.contains("/v1/users"))
                        url = url.replace("/v1/users", "/v2/users");
                    chromeDevTools.send(Fetch.continueRequest(request.getRequestId(),
                            Optional.of(url),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()));
                });

        driver.get("https://gorest.co.in/public/v1/users");
    }
}
