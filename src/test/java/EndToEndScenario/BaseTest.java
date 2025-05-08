package EndToEndScenario;

import DriverSetup.DriverManager;
import Utils.PropertiesUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import Listeners.TestNGListeners;

@Listeners(TestNGListeners.class)
public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String platform = PropertiesUtil.getPropertyValue("platformName");
        DriverManager.startDriver(platform);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriverAndService();
    }


}