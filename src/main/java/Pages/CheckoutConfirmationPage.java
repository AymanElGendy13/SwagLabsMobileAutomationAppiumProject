package Pages;

import Utils.Scroll;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DriverSetup.DriverManager.getDriver;

public class CheckoutConfirmationPage {

    //Locators
    private final Button finishBtn = new Button(AppiumBy.accessibilityId("test-FINISH"));
    private final Button cancelBtn = new Button(AppiumBy.accessibilityId("test-CANCEL"));
    private final Label pageTitle = new Label(By.xpath("//android.widget.TextView[@text=\"CHECKOUT: OVERVIEW\"]"));
    private final Label prodPrice = new Label (By.xpath("//android.widget.TextView[contains(@text, 'Item total: $')]"));
    private final Label prodDescription = new Label(By.xpath("//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]"));

    //Constructor
    public CheckoutConfirmationPage(AndroidDriver driver) {
    }

    //Actions
    @Step("Getting page title")
    public String pageTitle() {
        Waits.waitForElementVisible(getDriver(), pageTitle.getLocator());
        return pageTitle.getText();
    }

    @Step("Clicking on Finish button")
    public void clickFinishBtn() {
        Scroll.scrollToText("FINISH");
        finishBtn.click();
    }

    @Step("Clicking on Cancel button")
    public void clickCancelBtn() {
        Scroll.scrollToText("FINISH");
        cancelBtn.click();
    }

    @Step("Getting product price")
    public double getProductPrice() {
        String price = prodPrice.getText();
        String priceValue = price.replaceAll("[^0-9.]", ""); // Removes all non-digit/non-dot chars
        double priceFinal = Double.parseDouble(priceValue);
        return Math.round(priceFinal * 100.0) / 100.0;
    }

    @Step("Getting product description")
    public String getProductDescription() {
        return prodDescription.getText();
    }

}