package Pages;

import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DriverSetup.DriverManager.getDriver;

public class CheckoutInformationPage {

    //Locators
    private final TextBox firstname = new TextBox (AppiumBy.accessibilityId("test-First Name"));
    private final TextBox lastName = new TextBox (AppiumBy.accessibilityId("test-Last Name"));
    private final TextBox postalCode = new TextBox (AppiumBy.accessibilityId("test-Zip/Postal Code"));
    private final Button continueBtn = new Button (AppiumBy.accessibilityId("test-CONTINUE"));
    private final Button cancelBtn = new Button (By.xpath("//android.widget.TextView[@text='CANCEL']"));
    private final Label pageTitle = new Label (By.xpath("//android.widget.TextView[@text=\"CHECKOUT: INFORMATION\"]"));

    //Constructors
    public CheckoutInformationPage(AndroidDriver driver) {
    }

    //Actions
    @Step("Getting page title")
    public String pageTitle() {
        Waits.waitForElementVisible(getDriver(), pageTitle.getLocator());
        return pageTitle.getText();
    }

    @Step("Filling checkout information: {firstName}, {lastName}, {postalCode}")
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        this.firstname.sendData(firstName);
        this.lastName.sendData(lastName);
        this.postalCode.sendData(postalCode);
        this.continueBtn.click();
    }

    @Step("Filling checkout information: {firstName}")
    public void enterFirstname(String firstName) {
        this.firstname.sendData(firstName);
        this.continueBtn.click();
    }

    @Step("Cancelling checkout")
    public void cancelCheckout() {
        this.cancelBtn.click();
    }

}