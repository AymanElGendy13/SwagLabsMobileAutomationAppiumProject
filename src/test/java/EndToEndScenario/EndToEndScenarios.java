package EndToEndScenario;

import Pages.*;
import Utils.JsonUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static DriverSetup.DriverManager.getDriver;

public class EndToEndScenarios extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckoutInformationPage checkoutInformationPage;
    private CheckoutConfirmationPage checkoutConfirmationPage;
    private CheckoutSuccessfulOrderPage checkoutSuccessfulOrderPage;
    private SoftAssert softAssert;
    JsonUtils testData;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        cartPage = new CartPage(getDriver());
        checkoutInformationPage = new CheckoutInformationPage(getDriver());
        checkoutConfirmationPage = new CheckoutConfirmationPage(getDriver());
        checkoutSuccessfulOrderPage = new CheckoutSuccessfulOrderPage(getDriver());

        softAssert = new SoftAssert();
        testData = new JsonUtils("E2EDataDriven");
    }


    @Test(priority = 1,groups = {"Regression"})
    public void ValidScenarioCompleteUserFlow() {
        loginPage.loginWithAutoFill(testData.getJsonData("users.firstUser"));
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        homePage.selectProduct(testData.getJsonData("products.productOne"))
                .selectProduct(testData.getJsonData("products.productTwo"))
                .selectProduct(testData.getJsonData("products.productThree"))
                .selectProduct(testData.getJsonData("products.productSix"));
        softAssert.assertEquals(homePage.getSelectedProductsCounter(), 4, "Product count is not correct");
        homePage.openCart();
        softAssert.assertEquals(cartPage.pageTitle(), "YOUR CART", "Cart title is not correct");

        cartPage.deleteProduct(testData.getJsonData("products.productThree")).clickCheckout();
        softAssert.assertEquals(checkoutInformationPage.pageTitle(), "CHECKOUT: INFORMATION", "Checkout page title is not correct");

        checkoutInformationPage.fillCheckoutInformation(
                testData.getJsonData("checkoutData.firstName"),
                testData.getJsonData("checkoutData.lastName"),
                testData.getJsonData("checkoutData.postalCode"));
        softAssert.assertEquals(checkoutConfirmationPage.pageTitle(), "CHECKOUT: OVERVIEW", "Checkout Confirmation title is not correct");

        checkoutConfirmationPage.clickFinishBtn();
        softAssert.assertEquals(checkoutSuccessfulOrderPage.pageTitle(), "CHECKOUT: COMPLETE!", "Checkout Successful title is not correct");

        softAssert.assertEquals(checkoutSuccessfulOrderPage.getSuccessfulMsg(), "THANK YOU FOR YOU ORDER", "Checkout Successful message is not correct");
        checkoutSuccessfulOrderPage.clickBackHomeBtn();
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        softAssert.assertAll();
    }


    @Test(priority = 2)
    public void ValidScenarioProductsDisplayOptions() {
        loginPage.loginWithAutoFill(testData.getJsonData("users.firstUser"));
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        homePage.openMenu().openProducts()
                .toggleView()
                .filterProductsByPriceHighToLow()
                .selectProduct(testData.getJsonData("products.productOne"))
                .selectProduct(testData.getJsonData("products.productFour"))
                .selectProduct(testData.getJsonData("products.productSix"))
                .openCart();
        softAssert.assertEquals(cartPage.pageTitle(), "YOUR CART", "Cart title is not correct");

        cartPage.clickCheckout();
        softAssert.assertEquals(checkoutInformationPage.pageTitle(), "CHECKOUT: INFORMATION", "Checkout page title is not correct");

        checkoutInformationPage.enterFirstname(testData.getJsonData("checkoutData.firstName"));

        checkoutInformationPage.fillCheckoutInformation("Test", "Confirm", "12345");
        softAssert.assertEquals(checkoutConfirmationPage.pageTitle(), "CHECKOUT: OVERVIEW", "Checkout Confirmation title is not correct");

        checkoutConfirmationPage.clickFinishBtn();
        softAssert.assertEquals(checkoutSuccessfulOrderPage.pageTitle(), "CHECKOUT: COMPLETE!", "Checkout Successful title is not correct");

        softAssert.assertEquals(checkoutSuccessfulOrderPage.getSuccessfulMsg(), "THANK YOU FOR YOU ORDER", "Checkout Successful message is not correct");
        checkoutSuccessfulOrderPage.clickBackHomeBtn();
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        softAssert.assertAll();
    }

    @Test(priority = 5)
    public void ValidScenarioUserFlowWithDifferentFeatures()
    {
        loginPage.loginWithAutoFill(testData.getJsonData("users.firstUser"));

        homePage.filterProductsByPriceHighToLow()
                .selectProduct(testData.getJsonData("products.productFour"))
                .selectProduct(testData.getJsonData("products.productSix"))
                .selectProduct(testData.getJsonData("products.productTwo"))
                .deselectProduct(testData.getJsonData("products.productTwo"))
                .openCart();

        cartPage.deleteProduct(testData.getJsonData("products.productSix"))
                    .clickCheckout();

        checkoutInformationPage.fillCheckoutInformation(
                testData.getJsonData("checkoutData.firstName"),
                testData.getJsonData("checkoutData.lastName"),
                testData.getJsonData("checkoutData.postalCode"));

        checkoutConfirmationPage.clickCancelBtn();

        homePage.selectProduct(testData.getJsonData("products.productFive"))
                .openCart();

        cartPage.deleteProduct(testData.getJsonData("products.productFour"))
                    .deleteProduct(testData.getJsonData("products.productFive"))
                    .clickContinueShopping();

    }

    @Test(priority = 6)
    public void ValidScenarioSecondaryAppFeaturesFlow() throws InterruptedException {

        loginPage.loginWithAutoFill(testData.getJsonData("users.firstUser"));

        homePage.openMenu().openWebView()
                           .enterUrl("https://www.github.com")
                           .goToSite()
                           .openMenu().openDrawingArea()
                           .drawOnDrawingArea()
                           .clearDrawing()
                           .drawOnDrawingArea()
                           .saveDrawing().confirmSavingToGallery()
                           .openGallery();
    }

    @Test(priority = 3,groups = {"Regression"})
    public void ValidScenarioUserFlowForPrices() {
        loginPage.loginWithAutoFill(testData.getJsonData("users.firstUser"));
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        homePage.openSpecificProduct(testData.getJsonData("products.productThree"));
        String prodDescription = homePage.getProductDescription();
        double prodPrice = homePage.getProductPrice();
        homePage.addSpecificProdToCart().openCart();
        softAssert.assertEquals(cartPage.pageTitle(), "YOUR CART", "Cart title is not correct");

        cartPage.clickCheckout();
        softAssert.assertEquals(checkoutInformationPage.pageTitle(), "CHECKOUT: INFORMATION", "Checkout page title is not correct");

        checkoutInformationPage.fillCheckoutInformation("Test", "Confirm", "12345");
        softAssert.assertEquals(checkoutConfirmationPage.pageTitle(), "CHECKOUT: OVERVIEW", "Checkout Confirmation title is not correct");
        softAssert.assertEquals(checkoutConfirmationPage.getProductPrice(), prodPrice, "Total Product Price is not correct");
        softAssert.assertEquals(checkoutConfirmationPage.getProductDescription(), prodDescription, "Product description is not correct");

        checkoutConfirmationPage.clickFinishBtn();
        softAssert.assertEquals(checkoutSuccessfulOrderPage.pageTitle(), "CHECKOUT: COMPLETE!", "Checkout Successful title is not correct");

        softAssert.assertEquals(checkoutSuccessfulOrderPage.getSuccessfulMsg(), "THANK YOU FOR YOU ORDER", "Checkout Successful message is not correct");
        checkoutSuccessfulOrderPage.clickBackHomeBtn();
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        softAssert.assertAll();

    }

    @Test(priority = 4)
    public void InvalidScenarioUserFlow() {
        loginPage.login(testData.getJsonData("users.firstUser"), loginPage.extractPassword());
        softAssert.assertEquals(homePage.pageTitle(), "PRODUCTS", "Home page title is not correct");

        homePage.openMenu().logout();

        loginPage.loginWithAutoFill(testData.getJsonData("users.secondUser"));
        softAssert.assertEquals(loginPage.getErrorMessage(), "Sorry, this user has been locked out.", "Error message is not correct");
        softAssert.assertAll();

    }

}