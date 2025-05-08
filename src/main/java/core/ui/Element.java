package core.ui;

import DriverSetup.DriverManager;
import Utils.Logs;
import Utils.Scroll;
import Utils.Waits;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class Element {

    protected By locator;

    public Element(By locator) {
        this.locator = locator;
    }

    protected AndroidDriver getDriver() {
        Logs.info("Getting driver from ThreadLocal");
        return DriverManager.getDriver(); // Gets driver from ThreadLocal
    }

    // Element uses waitForElementPresent as default
    protected WebElement waitForElement() {
        return Waits.waitForElementPresent(getDriver(), locator);
    }

    protected WebElement waitAndScroll() {
        WebElement element = waitForElement(); // Get the element first
        Scroll.scrollToElement(element); // Pass WebElement to scroll
        return element;
    }

    public By getLocator() {
        return locator;
    }

}