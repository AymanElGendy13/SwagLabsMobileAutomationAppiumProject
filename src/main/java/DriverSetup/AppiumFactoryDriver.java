package DriverSetup;

import Utils.PropertiesUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;
import java.time.Duration;

public class AppiumFactoryDriver {

    public static AndroidDriver createDriver(String platformName) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(PropertiesUtil.getPropertyValue("androidDeviceName"))
                .setApp(PropertiesUtil.getPropertyValue("androidAppPath"))
                .setAppActivity(PropertiesUtil.getPropertyValue("androidAppActivity"))
                .setAutomationName(PropertiesUtil.getPropertyValue("automationName"))
                .setAppWaitDuration(Duration.ofSeconds(Integer.parseInt(PropertiesUtil.getPropertyValue("explicitWait"))))
                .setAutoGrantPermissions(true)
                .setNoReset(false);

        // Use the local service URL if running locally, otherwise use the configured URL
        if ("local".equalsIgnoreCase(PropertiesUtil.getPropertyValue("executionType"))) {
            return new AndroidDriver(DriverManager.getService().getUrl(), options);
        } else {
            return new AndroidDriver(new URL(PropertiesUtil.getPropertyValue("appiumServerUrl")), options);
        }
    }
}