package Utils;

import DriverSetup.DriverManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.NoSuchElementException;

import static DriverSetup.DriverManager.getDriver;

public class Scroll {

    public static boolean scrollDown() {
        WebDriver driver = DriverManager.getDriver();
        return (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                "left", 100,
                "top", 100,
                "width", 800,
                "height", 1500,
                "direction", "down",
                "percent", 0.75,
                "speed", 2000
        ));
    }

    public static void scrollToText(String text)
    {
        getDriver().findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    public static boolean scrollToElement(WebElement element) {
        // Try to interact with the element first (Appium will auto-scroll)
        try {
            if (element.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            // Element not visible, will scroll
        }

        // If not visible, perform controlled scrolling
        int attempts = 0;
        while (attempts < 5) {
            scrollDown();
            try {
                if (element.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
                // Continue scrolling
            }
            attempts++;
        }
        return false;
    }

    public static void swipeLeftUntilElement(WebElement element) {
        while (true) {
            try {
                if (element.isDisplayed()) {
                    break; // Stop swiping when element is found
                }
            } catch (NoSuchElementException e) {
                // Element not found, continue swiping
            }

            new AndroidTouchAction((PerformsTouchActions) getDriver())
                    .press(PointOption.point(900, 1000)) // Start from the right side
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(200, 1000)) // Move left
                    .release()
                    .perform();
        }
    }

    public static void scrollToTop() {
        WebDriver driver = DriverManager.getDriver();
        boolean canScrollMore = true;
        while (canScrollMore) {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100,
                    "top", 100,
                    "width", 800,
                    "height", 1500,
                    "direction", "up",
                    "percent", 0.9
            ));
        }
    }


}