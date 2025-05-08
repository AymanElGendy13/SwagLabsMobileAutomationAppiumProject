package core.ui;

import Utils.Logs;
import Utils.Waits;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Label extends Element {
    public Label(By locator) {
        super(locator);
    }

    @Override
    protected WebElement waitForElement() {
        return Waits.waitForElementVisible(getDriver(), locator);
    }

    public String getText() {
        try {
            Logs.info("Getting text from label: " + locator);
            return waitAndScroll().getText();
        } catch (Exception e) {
            Logs.error("Failed to get text from label: " + e.getMessage());
            throw new RuntimeException("Failed to get label text", e);
        }
    }

}