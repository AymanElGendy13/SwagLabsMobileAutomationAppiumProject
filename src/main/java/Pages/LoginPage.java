package Pages;

import DriverSetup.DriverManager;
import Utils.Scroll;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {

    //Locators
    private final TextBox username = new TextBox(AppiumBy.accessibilityId("test-Username"));
    private final TextBox password = new TextBox(AppiumBy.accessibilityId("test-Password"));
    private final Button loginButton = new Button(AppiumBy.accessibilityId("test-LOGIN"));
    private final Button standardUserAutoFill = new Button(By.xpath("//android.widget.TextView[@text='standard_user']"));
    private final Button lockedOutUserAutoFill = new Button(By.xpath("//android.widget.TextView[@text='locked_out_user']"));
    private final Button problemUserAutoFill = new Button(By.xpath("//android.widget.TextView[@text='problem_user']"));
    private final Label extractPassword = new Label(By.xpath("//android.widget.TextView[@text='secret_sauce']"));
    private final Label errorMessage = new Label(AppiumBy.xpath("//android.widget.TextView[@text=\"Sorry, this user has been locked out.\"]"));


    //Constructor
    public LoginPage(AndroidDriver driver) {
    }

    //Actions
    @Step("Login with username: {username} and password: {password}")
    public void login (String username, String password) {
        this.username.sendData(username);
        this.password.sendData(password);
        loginButton.click();
    }

    @Step("Login with username: {username}")
    public void loginWithAutoFill(String username) {
        Waits.waitForElementVisible(DriverManager.getDriver(), this.username.getLocator());
        switch (username) {
            case "standard_user":
                standardUserAutoFill.click();
                break;
            case "locked_out_user":
                lockedOutUserAutoFill.click();
                break;
            case "problem_user":
                problemUserAutoFill.click();
                break;
            default:
                throw new IllegalArgumentException("Invalid username: " + username);
        }
        loginButton.click();
    }

    @Step("Getting password")
    public String extractPassword() {
        Scroll.scrollToText("And the password for all users is:");
        return extractPassword.getText();
    }

    @Step("Getting error message")
    public String getErrorMessage() {
        Waits.waitForElementVisible(DriverManager.getDriver(), errorMessage.getLocator());
        return errorMessage.getText();
    }
}
