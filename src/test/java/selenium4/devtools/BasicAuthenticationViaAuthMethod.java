package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * If a website uses basic or digest authentication,
 * it will prompt a dialog (browser popup) that cannot be handled through Selenium
 * as it is only able to engage with DOM elements.
 * To go around that, it is possible to register an authentication method
 * to access the content needed for the test.
 */
public class BasicAuthenticationViaAuthMethod {

    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    public static void main(String[] args) throws Exception {

        //Set up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        // Register an authentication method
        driver.register(() -> new UsernameAndPassword(USERNAME, PASSWORD));

        //Open website
        driver.get("https://jigsaw.w3.org/HTTP/");
        //Click authentication test - this normally invokes a browser popup if unauthenticated
        driver.findElement(By.linkText("Basic Authentication test")).click();

        String loginSuccessMsg = driver.findElement(By.tagName("html")).getText();
        if(loginSuccessMsg.contains("Your browser made it!")) {
            System.out.println("Login successful");
        }
        else {
            System.out.println("Login failed");
        }

        Thread.sleep(3000);
        driver.quit();
    }
}
