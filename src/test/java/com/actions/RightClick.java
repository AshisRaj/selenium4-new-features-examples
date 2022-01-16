package com.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.System.getProperty;

public class RightClick {
    final static String PROJECT_PATH = getProperty("user.dir");

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://swisnl.github.io/jQuery-contextMenu/demo.html");
        WebElement rightBtn = driver.findElement(By.className("btn"));

        Actions action = new Actions(driver);
        action.contextClick(rightBtn).perform();

        File source = rightBtn.getScreenshotAs(OutputType.FILE);
        File dest = new File(PROJECT_PATH + "/Screenshots/right_click.png");

        FileHandler.copy(source, dest);

        List<WebElement> elements = driver.findElements(By.cssSelector("li span"));
        System.out.println("WebElements After Right Click:");
        for (WebElement element : elements) {
            System.out.println("\t" + element.getText());
        }

        Thread.sleep(3000);
        driver.quit();
    }
}
