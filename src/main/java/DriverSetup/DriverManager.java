package DriverSetup;

import Utils.PropertiesUtil;
import Utils.Logs;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Step;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static AppiumDriverLocalService service;

    public static AndroidDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("Driver not initialized!");
        }
        return driver.get();
    }

    public static AppiumDriverLocalService getService() {
        if (service == null || !service.isRunning()) {
            throw new IllegalStateException("Appium service not running!");
        }
        return service;
    }

    @Step("Initialize Appium Server and Driver for {platformName}")
    public static void startDriver(String platformName) {
        try {
            // Start Appium server if execution is local
            if ("local".equalsIgnoreCase(PropertiesUtil.getPropertyValue("executionType"))) {
                startAppiumServer();
            }
            // Create driver instance
            AndroidDriver newDriver = AppiumFactoryDriver.createDriver(platformName);
            driver.set(newDriver);
            Logs.info("Started Appium driver for " + platformName);
        } catch (Exception e) {
            Logs.error("Failed to start driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    @Step("Start Appium Server")
    private static void startAppiumServer() {
        try {
            Map<String, String> environment = new HashMap<>();
            environment.put("PATH", System.getenv("PATH"));

            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File(getAppiumJsPath()))
                    .withIPAddress(getServerIp())
                    .usingPort(getServerPort())
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                    .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                    .withEnvironment(environment)
                    .build();

            service.start();
            Logs.info("Appium server started on: " + service.getUrl());
        } catch (Exception e) {
            Logs.error("Failed to start Appium server: " + e.getMessage());
            throw new RuntimeException("Appium server startup failed", e);
        }
    }

    @Step("Quit Driver and Stop Appium Server")
    public static void quitDriverAndService() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                Logs.info("Driver quit successfully");
            } catch (Exception e) {
                Logs.error("Failed to quit driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }

        if (service != null && service.isRunning()) {
            try {
                service.stop();
                Logs.info("Appium server stopped successfully");
            } catch (Exception e) {
                Logs.error("Failed to stop Appium server: " + e.getMessage());
            }
        }
    }

    private static String getAppiumJsPath() {
        // Default path for Windows if not specified in properties
        return "C:\\Users\\Ayman\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    }

    private static String getServerIp() {
        String url = PropertiesUtil.getPropertyValue("appiumServerUrl");
        return url.split(":")[1].substring(2); // Extract IP from "http://127.0.0.1"
    }

    private static int getServerPort() {
        String url = PropertiesUtil.getPropertyValue("appiumServerUrl");
        return Integer.parseInt(url.split(":")[2]); // Extract port from URL
    }
}