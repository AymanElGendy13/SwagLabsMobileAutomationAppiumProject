package Pages;

import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DriverSetup.DriverManager.getDriver;

public class CheckoutSuccessfulOrderPage {

    //Locators
    private final Label successfulMsg = new Label(AppiumBy.androidUIAutomator("new UiSelector().text(\"THANK YOU FOR YOU ORDER\")"));
    private final Button backHomeBtn = new Button(AppiumBy.accessibilityId("test-BACK HOME"));
    private final Label pageTitle = new Label(By.xpath("//android.widget.TextView[@text=\"CHECKOUT: COMPLETE!\"]"));

    //Constructors
    public CheckoutSuccessfulOrderPage(AndroidDriver driver) {
    }

    //Actions
    @Step("Getting page title")
    public String pageTitle() {
        Waits.waitForElementVisible(getDriver(), pageTitle.getLocator());
        return pageTitle.getText();
    }

    @Step("Getting successful message")
    public String getSuccessfulMsg() {
       return successfulMsg.getText();
    }

    @Step("Clicking on back home button")
    public void clickBackHomeBtn() {
        backHomeBtn.click();
    }

}
