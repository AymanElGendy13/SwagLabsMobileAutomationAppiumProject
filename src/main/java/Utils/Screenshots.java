package Utils;

import DriverSetup.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    public static final String SCREENSHOTS_PATH = "test-outputs/Screenshots";

    private Screenshots() {
        super();
    }

    public static void takeScreenShot(String screenshotname) {
        try {
            File screenshot = DriverManager.getDriver().getScreenshotAs(OutputType.FILE);
            File filePath = new File(SCREENSHOTS_PATH + "/" + screenshotname + ".png");
            FileUtils.copyFile(screenshot, filePath);
            Logs.info("Screenshot taken: " + filePath);
            AllureUtil.attachScreenshotsToAllureReport(screenshotname, filePath.getPath());
        } catch (IOException e) {
            Logs.error("Failed to take screenshot: " + e.getMessage());
        }
    }
}
