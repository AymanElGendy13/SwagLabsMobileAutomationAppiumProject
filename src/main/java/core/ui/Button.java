package core.ui;

import Utils.Logs;
import Utils.Scroll;
import Utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.stream.Collectors;

public class Button extends Label {

    public Button(By locator) {
        super(locator);
    }

    @Override
    protected WebElement waitForElement() {
        return Waits.waitForElementClickable(getDriver(), locator);
    }

    public void click() {
        Logs.info("Clicking on button: " + locator);
        Scroll.scrollToElement(waitAndScroll());
        getDriver().findElement(locator).click();
    }

}