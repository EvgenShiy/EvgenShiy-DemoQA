package tests.ui_tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class UI_TestBase {
    private static final Logger log = LoggerFactory.getLogger(UI_TestBase.class);

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.defaultParser = Parser.JSON;

        WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

        String remoteUrl = config.getRemoteUrl();
        String env = System.getProperty("env", "local");

        log.info("DEBUG: remoteUrl из ConfigFactory = {}", remoteUrl);
        log.info("DEBUG: env = {}", env);

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = config.getPageLoadStrategy();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.browser = config.getBrowserName().browserToLowerCase();
        Configuration.browserVersion = config.getBrowserVersion();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if ("remote".equals(env) && remoteUrl != null && !remoteUrl.isEmpty()) {
            log.info("INFO: Запускаем тесты удаленно на {}", remoteUrl);
            Configuration.remote = remoteUrl;
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
        } else {
            log.info("INFO: Запускаем тесты локально.");
            if ("chrome".equalsIgnoreCase(Configuration.browser)) {
                WebDriverManager.chromedriver().setup();
            } else if ("firefox".equalsIgnoreCase(Configuration.browser)) {
                WebDriverManager.firefoxdriver().setup();
            } else if ("edge".equalsIgnoreCase(Configuration.browser)) {
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