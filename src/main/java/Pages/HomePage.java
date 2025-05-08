package Pages;

import Utils.Logs;
import Utils.Scroll;
import Utils.Waits;
import core.ui.Button;
import core.ui.Label;
import core.ui.TextBox;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static DriverSetup.DriverManager.getDriver;

public class HomePage {

    int selectedProductsCounter;
    double totalProductsPrice;

    //Locators
    //Menu
    private final Button menuBtn = new Button(AppiumBy.accessibilityId("test-Menu"));
    private final Button closeMenuBtn = new Button(AppiumBy.accessibilityId("test-Close"));
    private final Button productsBtn = new Button(AppiumBy.accessibilityId("test-ALL ITEMS"));
    private final Button webViewBtn = new Button(AppiumBy.accessibilityId("test-WEBVIEW"));
    private final Button gotoSiteBtn = new Button(AppiumBy.accessibilityId("test-GO TO SITE"));
    private final TextBox urlTextBox = new TextBox(AppiumBy.accessibilityId("test-enter a https url here..."));
    //Drawing Area
    private final Button drawingBtn = new Button(AppiumBy.accessibilityId("test-DRAWING"));
    private final Button saveDrawingBtn = new Button (AppiumBy.accessibilityId("test-SAVE"));
    private final Button clearDrawingBtn = new Button (AppiumBy.accessibilityId("test-CLEAR"));
    private final Button allowAppPermsToAccessGallBtn = new Button(By.id("com.android.permissioncontroller:id/permission_allow_button"));
    private final Button denyAppPermsToAccessGallBtn = new Button(By.id("com.android.permissioncontroller:id/permission_deny_button"));
    private final Button confirmationSavinToGalleryBtn = new Button(By.id("android:id/button1"));
    private final By webkitWebViewDrawArea = By.xpath(".//android.webkit.WebView");
    //SUB-PAGE
    private final Label prodPrice = new Label(AppiumBy.accessibilityId("test-Price"));
    private final Label prodDescription = new Label(By.xpath("//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[2]"));
    private final Button continueShoppingBtn = new Button(AppiumBy.accessibilityId("test-BACK TO PRODUCTS"));
    private static final Button subProdAddToCartBtn = new Button(AppiumBy.accessibilityId("test-ADD TO CART"));
    //Main Page
    private final Button logoutBtn = new Button(AppiumBy.accessibilityId("test-LOGOUT"));
    private final Button openCartBtn = new Button(AppiumBy.accessibilityId("test-Cart"));
    private final Button filterBtn = new Button(AppiumBy.accessibilityId("test-Modal Selector Button"));
    private final Button priceHighToLowFilterOption = new Button(By.xpath("//android.widget.TextView[@text=\"Price (high to low)\"]"));
    private final By productsNames =  By.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private final By prodNameTitle = By.xpath(".//android.widget.TextView[@content-desc='test-Item title']");
    private final Label webViewSiteOpenedDescription = new Label(By.id("hero-section-brand-heading"));
    private final Label waitFactor = new Label (By.xpath("//android.widget.TextView[@text=\"Join the world’s most widely adopted AI-powered developer platform.\"]"));
    private final Label homePageTitle = new Label(By.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]"));
    private final By addToCartBtn = By.xpath(".//android.view.ViewGroup[@content-desc='test-ADD TO CART']");
    private final By removeFromCartBtn = By.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']");
    private final Button otherDisplay = new Button(AppiumBy.accessibilityId("test-Toggle"));

    //Constructors
    public HomePage(AndroidDriver driver) {
    }

    //Actions
    @Step("Getting page title")
    public String pageTitle() {
        Waits.waitForElementVisible(getDriver(), homePageTitle.getLocator());
        return homePageTitle.getText();
    }

    @Step("Opening menu")
    public HomePage openMenu() {
        Waits.waitForElementVisible(getDriver(), menuBtn.getLocator());
        menuBtn.click();
        return this;
    }

    @Step("Closing menu")
    public HomePage closeMenu() {
        closeMenuBtn.click();
        return this;
    }

    @Step("Opening products")
    public HomePage openProducts() {
        productsBtn.click();
        return this;
    }

    @Step("Opening WebView")
    public HomePage openWebView() {
        webViewBtn.click();
        return this;
    }

    @Step("Logging out")
    public void logout() {
        logoutBtn.click();
    }

    @Step("Opening Website")
    public HomePage goToSite() throws InterruptedException {
        gotoSiteBtn.click();
        // Wait for the web view to load --> Testing purposes
        Thread.sleep(7000);    //  --> Testing purposes
        return this;
    }

    @Step("Entering URL: {url}")
    public HomePage enterUrl(String url) {
        urlTextBox.sendData(url);
        return this;
    }

    public String getWebViewSiteOpenedDescription() {
        Waits.waitForElementVisible(getDriver(), waitFactor.getLocator());
        return webViewSiteOpenedDescription.getText();
    }

    @Step("Opening Drawing Area")
    public HomePage openDrawingArea() {
        drawingBtn.click();
        return this;
    }

    @Step("Drawing a Letter On Drawing Area")
    public HomePage drawOnDrawingArea() {

        Waits.waitForElementVisible(getDriver(), saveDrawingBtn.getLocator());
        WebElement canvas = getDriver().findElement(webkitWebViewDrawArea);

        int canvasX = canvas.getLocation().getX();
        int canvasY = canvas.getLocation().getY();
        int canvasWidth = canvas.getSize().getWidth();
        int canvasHeight = canvas.getSize().getHeight();

        int zSize = 750;
        int startX = canvasX + (canvasWidth - zSize) / 2;
        int startY = canvasY + (canvasHeight - zSize) / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence drawZ = new Sequence(finger, 0);

        drawZ.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        drawZ.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        drawZ.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX + zSize, startY));
        drawZ.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, startY + zSize));
        drawZ.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX + zSize, startY + zSize));
        drawZ.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getDriver().perform(List.of(drawZ));
        return this;
    }

    @Step("Saving drawing")
    public HomePage saveDrawing() {
        saveDrawingBtn.click();
        return this;
    }

    @Step("Clearing drawing")
    public HomePage clearDrawing() {
        clearDrawingBtn.click();
        return this;
    }

    public HomePage allowAppPermissionsToAccessGallery() {
        allowAppPermsToAccessGallBtn.click();
        return this;
    }

    public HomePage denyAppPermissionsToAccessGallery() {
        denyAppPermsToAccessGallBtn.click();
        return this;
    }

    @Step("Confirming saving to gallery")
    public HomePage confirmSavingToGallery() {
        Waits.waitForElementClickable(getDriver(), confirmationSavinToGalleryBtn.getLocator());
        confirmationSavinToGalleryBtn.click();
        return this;
    }

    @Step("Opening last image in gallery")
    public void openGallery()
    {
        AndroidDriver driver = getDriver();
        ((JavascriptExecutor) driver).executeScript("mobile: startActivity", Map.of(
                "intent", "com.google.android.apps.photos/com.google.android.apps.photos.home.HomeActivity"
        ));

        try {
            // First check for and handle the "Update" popup if it appears
            By notNowButton = By.xpath("//*[contains(@text, 'Not now')]");
            Waits.waitForElementVisible(getDriver(), notNowButton);
            driver.findElement(notNowButton).click();
            Logs.info("Clicked 'Not now' on the update prompt");
            // Then proceed with the original image click
            Waits.waitForElementVisible(getDriver(), By.xpath("(//android.widget.ImageView)[1]"));
            driver.findElement(By.xpath("(//android.widget.ImageView)[1]")).click(); // Click first image
        } catch (Exception e) {
            Logs.info("Error opening last image: " + e.getMessage());
        }
    }

    @Step("Selecting product: {productName}")
    public HomePage selectProduct(String productName) {
        boolean found = false;
        int maxScrollAttempts = 5; // Prevent infinite scrolling
        int attempts = 0;

        // Wait for the products to be visible
        Waits.waitForElementVisible(getDriver(), productsNames);

        while (!found && attempts < maxScrollAttempts) {

            // Find all product elements
            List<WebElement> products = getDriver().findElements(productsNames);

            for (WebElement product : products) {
                try {
                    WebElement titleElement = product.findElement(prodNameTitle);

                    if (titleElement.getText().equalsIgnoreCase(productName)) {
                        // Find ADD TO CART button relative to this product
                        WebElement addToCartButton = product.findElement(addToCartBtn);
                        // Scroll to the button if needed
                        Scroll.scrollToText(productName);
                        addToCartButton.click();
                        found = true;
                        selectedProductsCounter++;
                        break;
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }

            if (!found) {
                // Scroll down to load more products
                Scroll.scrollDown();
                attempts++;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Product not found after scrolling: " + productName);
        }

        return this;
    }

    @Step("Opening specific product: {productName}")
    public void openSpecificProduct(String productName) {
        Waits.waitForElementVisible(getDriver(), productsNames);
        Scroll.scrollToText(productName);
        getDriver().findElement(By.xpath("//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\""+productName+"\"]")).click();
    }

    @Step("Adding specific product to cart")
    public HomePage addSpecificProdToCart() {
        Waits.waitForElementVisible(getDriver(), subProdAddToCartBtn.getLocator());
        Scroll.scrollToText("ADD TO CART");
        subProdAddToCartBtn.click();
        return this;
    }

    @Step("Deselecting product: {productName}")
    public HomePage deselectProduct(String productName) {
        boolean found = false;
        int maxScrollAttempts = 5; // Prevent infinite scrolling
        int attempts = 0;


        // Wait for the products to be visible
        Waits.waitForElementVisible(getDriver(), productsNames);

        while (!found && attempts < maxScrollAttempts) {

            // Find all product elements
            List<WebElement> products = getDriver().findElements(productsNames);

            for (WebElement product : products) {
                try {
                    WebElement titleElement = product.findElement(prodNameTitle);

                    if (titleElement.getText().equalsIgnoreCase(productName)) {
                        // Find ADD TO CART button relative to this product
                        WebElement removeButton = product.findElement(removeFromCartBtn);

                        // Scroll to the button if needed
                        Scroll.scrollToElement(removeButton);
                        removeButton.click();
                        found = true;
                        selectedProductsCounter--;
                        break;
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }

            if (!found) {
                // Scroll down to load more products
                Scroll.scrollDown();
                attempts++;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Product not found after scrolling: " + productName);
        }
        return this;
    }

    @Step("Getting selected products counter")
    public int getSelectedProductsCounter() {
        return selectedProductsCounter;
    }

    public HomePage toggleView() {
        otherDisplay.click();
        return this;
    }

    @Step("Filtering products by price high to low")
    public HomePage filterProductsByPriceHighToLow() {
        filterBtn.click();
        priceHighToLowFilterOption.click();
        return this;
    }

    @Step("Opening cart")
    public void openCart() {
        Waits.waitForElementVisible(getDriver(), openCartBtn.getLocator());
        openCartBtn.click();
    }

    //SUB-MENU
    @Step("Getting Product Price")
    public double getProductPrice() {
        Waits.waitForElementVisible(getDriver(), prodPrice.getLocator());
        String priceText = prodPrice.getText();
        String priceValue = priceText.replaceAll("[^0-9.]", "");
        double price = Double.parseDouble(priceValue);
        return Math.round(price * 100.0) / 100.0;
    }

    @Step("Getting Product Description")
    public String getProductDescription(){
        Waits.waitForElementVisible(getDriver(), prodDescription.getLocator());
        return prodDescription.getText();
    }

    @Step("Clicking continue shopping")
    public HomePage continueShopping() {
        continueShoppingBtn.click();
        return this;
    }
}