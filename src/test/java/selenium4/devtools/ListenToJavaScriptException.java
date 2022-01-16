package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListenToJavaScriptException {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        List<JavascriptException> jsExceptionsList = new ArrayList<JavascriptException>();
        Consumer<JavascriptException> addEntry = jsExceptionsList::add;
        chromeDevTools.getDomains().events().addJavascriptExceptionListener(addEntry);

        driver.get("https://facebook.com");

        WebElement link2click = driver.findElement(By.name("login"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                link2click, "onclick", "throw new Error('My Error');");
        link2click.click();

        Thread.sleep(1000);
        for (JavascriptException jsException : jsExceptionsList) {
            System.out.println("JS exception message: " + jsException.getMessage());
            System.out.println("JS exception system information: " + jsException.getSystemInformation());
            jsException.printStackTrace();
        }

        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
