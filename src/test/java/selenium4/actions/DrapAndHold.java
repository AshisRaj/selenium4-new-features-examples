package selenium4.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;

import static java.lang.System.getProperty;

public class DrapAndHold {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://jqueryui.com/draggable/");
        WebElement dragFrame = driver.findElement(By.xpath("//*[@id=\"content\"]/iframe"));

        driver.switchTo().frame(0);
        WebElement dragBox = driver.findElement(By.id("draggable"));

        Actions action = new Actions(driver);
        action.clickAndHold(dragBox).moveByOffset(40, 30).build().perform();
        Thread.sleep(2000);

        driver.switchTo().parentFrame();
        File source = dragFrame.getScreenshotAs(OutputType.FILE);
        File dest = new File(PROJECT_PATH + "/Screenshots/drag_and_hold.png");

        FileHandler.copy(source, dest);

        Thread.sleep(3000);
        driver.quit();
    }
}
