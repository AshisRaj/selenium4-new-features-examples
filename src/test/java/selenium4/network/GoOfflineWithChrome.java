package selenium4.network;

import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;

import java.util.HashMap;
import java.util.Map;

public class GoOfflineWithChrome {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();

        Map map = new HashMap();
        map.put("offline", true);
        map.put("latency", 5);
        map.put("download_throughput", 500);
        map.put("upload_throughput", 1024);

        CommandExecutor executor = ((ChromeDriver)driver).getCommandExecutor();
        Response response = executor.execute(
                new Command(((ChromeDriver)driver).getSessionId(), "setNetworkConditions", ImmutableMap.of("network_conditions", ImmutableMap.copyOf(map))));

        driver.get("https://www.google.com/");

        Thread.sleep(3000);
        driver.quit();
    }
}
