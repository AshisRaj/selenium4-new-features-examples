package selenium4.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

/**
 * Mutation observation is the ability to capture DOM events you are interested in.
 * For example, you might want to know if an element has changed its property value.
 *
 * Before, the approach to this was to query the element continuously
 * until the desired change occurred.
 *
 * Now you can observe the changes in the DOM and assert over them
 * when an incoming event notifies you about the expected change.
 *
 * This example waits for changes in a span element,
 * then it is altered via JavaScript (for demonstration purposes),
 * and finally we assert the expected state.
 * On a real test, the simulated JavaScript alteration would be a real event
 * making the web application change.
 */

public class ObserveChangesInDOM {

    public static void main(String[] args) throws InterruptedException {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        ((HasLogEvents) driver).onLogEvent(domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));

        driver.get("https://www.google.com");
        WebElement span = driver.findElement(By.cssSelector("span"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('cheese', 'gouda');", span);

        assertThat(latch.await(10, SECONDS), is(true));
        assertThat(seen.get().getAttributeName(), is("cheese"));
        assertThat(seen.get().getCurrentValue(), is("gouda"));
   }
}
