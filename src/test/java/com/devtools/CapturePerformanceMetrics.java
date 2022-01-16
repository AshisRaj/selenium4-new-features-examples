package com.devtools;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.performance.Performance;
import org.openqa.selenium.devtools.v96.performance.model.Metric;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * In today’s fast world while we are iteratively building software at such a fast pace,
 * we should aim to detect performance bottlenecks iteratively too.
 * Poor performing websites and slower loading pages make unhappy customers.
 *
 * Can we validate these metrics along with our functional regression on every build? Yes, we can!
 *
 * The CDP command to capture performance metrics is Performance.enable.
 * Information for this command can be found in the documentation.
 */

public class CapturePerformanceMetrics {

    public static void main(String[] args) throws Exception {

        //Setting up the driver
        WebDriverManager.chromedriver().setup();

        //Initialize the driver
        ChromeDriver driver = new ChromeDriver();
        DevTools chromeDevTools = driver.getDevTools();
        chromeDevTools.createSession();

        chromeDevTools.send(Performance.enable(Optional.of(Performance.EnableTimeDomain.TIMETICKS)));

        driver.get("https://www.google.org");

        List<Metric> metrics = chromeDevTools.send(Performance.getMetrics());
        List<String> metricNames = metrics.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());

        chromeDevTools.send(Performance.disable());

        List<String> metricsToCheck = Arrays.asList(
                "Timestamp", "Documents", "Frames", "JSEventListeners",
                "LayoutObjects", "MediaKeySessions", "Nodes",
                "Resources", "DomContentLoaded", "NavigationStart");

        metricsToCheck.forEach( metric -> System.out.println(metric +
                " is : " + metrics.get(metricNames.indexOf(metric)).getValue()));

        chromeDevTools.send(Performance.disable());
        chromeDevTools.close();

        Thread.sleep(3000);
        driver.quit();
    }
}
