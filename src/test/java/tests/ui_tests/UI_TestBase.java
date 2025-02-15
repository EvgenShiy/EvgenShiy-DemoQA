/*package tests.ui_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import helpers.Attach;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class UI_TestBase {
    private static final WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.defaultParser = Parser.JSON;

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = config.getPageLoadStrategy();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.browser = config.getBrowserName().browserToLowerCase();
        Configuration.browserVersion = config.getBrowserVersion();

        String remoteUrl = config.getRemoteUrl();

        String rwhost = System.getProperty("rwhost");
        System.out.println("DEBUG: remoteUrl from config = " + remoteUrl);
        System.out.println("DEBUG: rwhost from system properties = " + rwhost);

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Configuration.remote = remoteUrl;
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
        } else {
            if (Objects.equals(Configuration.browser, "chrome")) {
                WebDriverManager.chromedriver().setup();
            } else if (Objects.equals(Configuration.browser, "firefox")) {
                WebDriverManager.firefoxdriver().setup();
            } else if (Objects.equals(Configuration.browser, "edge")) {
                WebDriverManager.edgedriver().setup();
            }
        }
        Configuration.browserCapabilities = capabilities;
    }


    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Screenshot");
        if (!Configuration.browser.equals("firefox")) {
            Attach.pageSource();
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();
        closeWebDriver();
    }
}

 */
package tests.ui_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import helpers.Attach;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.json.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class UI_TestBase {
    private static final WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.defaultParser = Parser.JSON;

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = config.getPageLoadStrategy();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.browser = config.getBrowserName().browserToLowerCase();
        Configuration.browserVersion = config.getBrowserVersion();

        String remoteUrl = config.getRemoteUrl();
        System.out.println("DEBUG: remoteUrl from config = " + remoteUrl);

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Configuration.remote = remoteUrl;
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("enableVideo", true);
            selenoidOptions.put("sessionTimeout", "3m");
            selenoidOptions.put("name", "MyTestSession");
            capabilities.setCapability("selenoid:options", selenoidOptions);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");

// Убираем пустой массив extensions
            options.setExperimentalOption("extensions", null);
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability("browserVersion", Configuration.browserVersion);
            capabilities.setCapability("platformName", "LINUX"); // заменяем "any" на "LINUX"


        } else {
            if (Objects.equals(Configuration.browser, "chrome")) {
                WebDriverManager.chromedriver().setup();
            } else if (Objects.equals(Configuration.browser, "firefox")) {
                WebDriverManager.firefoxdriver().setup();
            } else if (Objects.equals(Configuration.browser, "edge")) {
                WebDriverManager.edgedriver().setup();
            }
        }

        System.out.println("DEBUG: Final JSON capabilities = " + new Json().toJson(capabilities));

        Configuration.browserCapabilities = capabilities;
        System.out.println("DEBUG: JSON capabilities = " + new Json().toJson(Configuration.browserCapabilities));
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Screenshot");
        if (!Configuration.browser.equals("firefox")) {
            Attach.pageSource();
            Attach.browserConsoleLogs();
        }
        Attach.addVideo();
        closeWebDriver();
    }
}