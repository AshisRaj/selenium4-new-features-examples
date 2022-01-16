package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/**
 * Intercepting a network is applicable to scenarios where an end-to-end test is expected
 * to run on a website that consists of a few non-existing pages,
 * which are planned to be developed in the future.
 *
 * Use this API to capture the network events of a website and intercept them as required.
 *
 * The following test script performs network interception
 * by mocking the response of the request sent to a non-existent web page.
 */
public class InterceptNetwork {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        Supplier<InputStream> message = () ->
            new ByteArrayInputStream("Creamy, delicious cheese!".getBytes(StandardCharsets.UTF_8));

        NetworkInterceptor interceptor = new NetworkInterceptor(driver,
             Route.matching(req -> true)
                .to(() -> req -> new HttpResponse()
                    .setStatus(200)
                    .addHeader("Content-Type", StandardCharsets.UTF_8.toString())
                    .setContent(message)));

        // This site is non-existing
        driver.get("https://example-sausages-site.com");
        String source = driver.getPageSource();
        System.out.println(source);

        if (source.contains("delicious cheese!")) {
            System.out.println("Passed: Source contains the contents");
        } else {
            System.out.println("Failed: Content was not found in the source");
        }
        interceptor.close();
    }
}
