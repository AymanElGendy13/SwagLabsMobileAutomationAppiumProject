# Swag Labs Mobile Automation Framework Using Appium

   <a href="https://appium.io/">
      <img alt="Appium" src="https://raw.githubusercontent.com/appium/appium/master/packages/appium/docs/overrides/assets/images/appium-logo-horiz.png" width="500">
   </a>

![Java](https://img.shields.io/badge/Java-23-red) ![Appium](https://img.shields.io/badge/Appium-2.18-green) ![JavaClient](https://img.shields.io/badge/Javaclient-9.4-red) ![TestNG](https://img.shields.io/badge/TestNG-7.10.2-orange) ![Allure](https://img.shields.io/badge/Reporting-AllureReports-blue)

End-to-end **mobile test automation framework** using **Appium** for the **Swag Labs Android app**, covering core business workflows.

### Application Under Test
Link: [Swag Labs Android App](https://github.com/saucelabs/sample-app-mobile/releases)

---

## 🚀 Key Features

- **End-to-End Mobile Test Coverage**:
  - Login, Product Browsing, Cart, Checkout, etc.
- **Page Object Model + Fluent Interface** for mobile actions.
- **Cross-Platform Support**: Android & iOS.
- **Smart Element Waits**.
- **Robust Logging and Reporting**:
  - Allure HTML reports with screenshots.
  - Log4j2 logs per test run.
- **Parallel Test Execution** support on multiple devices using Browserstack.
- **Device Farm & Emulator Compatibility**.
- **CI/CD Ready** (Jenkins compatible).

---

## 🛠️ Technologies Used

| Component              | Technology Stack          |
|------------------------|---------------------------|
| Mobile Automation      | Appium 2.18             |
| Language               | Java 23                   |
| Test Framework         | TestNG 7.10.2              |
| Device Interaction     | Appium Driver (UiAutomator2/XCUITest) |
| Build Tool             | Maven                     |
| Reporting              | Allure + Log4j2           |
| Design Pattern         | POM + Fluent Pattern      |
| Device Compatibility   | Android, iOS              |
| Emulators Supported    | AVD, BrowserStack, Sauce Labs |

---

## 📂 Project Structure

```
AppiumProject/
├── .idea/ # IntelliJ config files
├── allure-report/ # Allure generated reports
├── ApplicationApk/ # APK/IPA files for local execution
├── logs/ # Log4j2 execution logs
├── screenshots/ # Captured screenshots on failures

├── src/
│ ├── main/
│ │ ├── java/
│ │ │ ├── core.ui/ # Reusable UI actions and wrappers
│ │ │ ├── DriverSetup/ # Appium driver and capabilities setup
│ │ │ ├── Listeners/ # Custom TestNG listeners (screenshot, logs)
│ │ │ ├── Pages/ # Page Object classes for each app screen
│ │ │ └── Utils/ # Helper utilities (e.g., JSON reader, wait handlers)
│ │ └── resources/ # Config files like appium.properties

│ └── test/
│ ├── java/
│ │ └── EndToEndScenario/ # Test classes, includes BaseTest and scenarios
│ └── resources/ # Test data files, JSON payloads, etc.

├── test-outputs/ # TestNG generated output
├── allure-results/ # Raw Allure result files
├── screenshots/ # Captured test screenshots
├── target/ # Maven build output

├── TestRunners/
│ ├── testng-e2e.xml # TestNG suite for E2E flows
│ └── testng-regression.xml # TestNG suite for regression suite

├── pom.xml # Maven build & dependency file
└── .gitignore # Git ignore configuration
```


## 🔧 Installation & Execution

```bash
# Clone the repository
git clone https://github.com/AymanElGendy13/SwagLabsMobileAutomationAppiumProject/

# Navigate into the project
cd SwagLabsMobileAutomationAppiumProject

#Open Emulator on Android Studio - All details provided inside config.properties file
#Run specific suites via TestNG
mvn test -P"$Profile" --> Replace "Profile" with any of the following profiles: EndToEnd - Regression
```
