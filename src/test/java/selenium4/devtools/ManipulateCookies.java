package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.network.model.Cookie;
import org.testng.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * We can clear browser cookies, delete all cookies, get and set cookie or cookies.
 * Network.deleteCookies : Deletes browser cookies with matching name and URL or domain/path pair.
 * Network.getCookies : Returns all browser cookies for the current URL. Depending on the backend support, will return detailed cookie information in the cookies field.
 * Network.setCookie : Sets a cookie with the given cookie data.
 * Network.setCookies : Sets given cookies.
 */
public class ManipulateCookies {
    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        driver.get("https://google.com");

        //Getting all cookies
        List<Cookie> cookies = chromeDevTools.send(Network.getAllCookies());
        cookies.forEach(cookie -> System.out.println(cookie.getName()));
        List<String> cookieName = cookies.stream().map(cookie -> cookie.getName()).sorted().collect(Collectors.toList());

        Set<org.openqa.selenium.Cookie> seleniumCookie = driver.manage().getCookies();
        List<String> selCookieName = seleniumCookie.stream().map(selCookie -> selCookie.getName()).sorted().collect(Collectors.toList());
        Assert.assertEquals(cookieName, selCookieName);

        //Clearing browser cookies
        chromeDevTools.send(Network.clearBrowserCookies());
        List<Cookie> cookiesAfterClearing = chromeDevTools.send(Network.getAllCookies());
        Assert.assertTrue(cookiesAfterClearing.isEmpty());

        chromeDevTools.send(Network.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
