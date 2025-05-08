package Pages;

import DriverSetup.DriverManager;
import Utils.Scroll;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {

    //Locators
    private final Button continueShoppingBtn = new Button(AppiumBy.accessibilityId("test-CONTINUE SHOPPING"));
    private final Button checkoutBtn = new Button(AppiumBy.accessibilityId("test-CHECKOUT"));
    private final Button deleteBtn = new Button(By.xpath("//android.view.ViewGroup[@content-desc='test-Delete']/android.view.ViewGroup"));
    private final Button removeBtn = new Button (AppiumBy.accessibilityId("test-REMOVE"));
    private final Label pageTitle = new Label(By.xpath("//android.widget.TextView[@text=\"YOUR CART\"]"));
    private static final By productsNames = By.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private final By removeProdBtn = By.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']");

    //Constructor
    public CartPage(AndroidDriver driver) {
    }

    //actions
    @Step("Getting page title")
    public String pageTitle() {
        Waits.waitForElementVisible(DriverManager.getDriver(), pageTitle.getLocator());
        return pageTitle.getText();
    }

    @Step("Deleting product from cart: {productName}")
    public CartPage deleteProduct(String productName) {
        boolean found = false;
        int maxScrollAttempts = 5; // To Prevent infinite scrolling
        int attempts = 0;

        while (!found && attempts < maxScrollAttempts) {
            Waits.waitForElementVisible(DriverManager.getDriver(), productsNames);
            List<WebElement> products = DriverManager.getDriver().findElements(productsNames);

            for (WebElement product : products) {
                try {
                    WebElement titleElement = product.findElement(By.xpath("//android.widget.TextView[@text=\""+productName+"\"]"));
                    if (titleElement.getText().equalsIgnoreCase(productName)) {
                        // Find ADD TO CART button relative to this product
                        WebElement removeProductBtn = product.findElement(removeProdBtn);
                        Scroll.scrollToElement(removeProductBtn);
                        removeProductBtn.click();
                        found = true;
                        break;
                    }
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    continue;
                }
            }
            if (!found) {
                Scroll.scrollDown();
                attempts++;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Product not found after scrolling: " + productName);
        }
        return this;
    }

    @Step("Clicking on continue shopping")
    public void clickContinueShopping() {
        Scroll.scrollToText("CONTINUE SHOPPING");
        continueShoppingBtn.click();
    }

    @Step("clicking on checkout")
    public void clickCheckout() {
        Scroll.scrollToText("CHECKOUT");
        checkoutBtn.click();
    }

    @Step("clicking on remove")
    public CartPage clickRemove() {
        Scroll.swipeLeftUntilElement((WebElement) removeBtn);
        deleteBtn.click();
        return this;
    }

}