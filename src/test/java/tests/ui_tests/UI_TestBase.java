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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class UI_TestBase {
    private static final Logger log = LoggerFactory.getLogger(UI_TestBase.class);
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

        log.info("DEBUG: remoteUrl from config = {}", remoteUrl);
        log.info("DEBUG: System.getProperty(\"remoteUrl\") = {}", System.getProperty("remoteUrl"));
        log.info("DEBUG: rwhost from system properties = {}", rwhost);
        log.info("DEBUG: remoteUrl из ConfigFactory = '{}'", config.getRemoteUrl());
        log.info("DEBUG: remoteUrl из ConfigFactory = {}", config.getRemoteUrl());
        log.info("DEBUG: System.getProperty(\"env\") = {}", System.getProperty("env"));


        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            log.info("INFO: Тесты запустятся на удаленном сервере: {}", remoteUrl);
            Configuration.remote = remoteUrl;
        } else {
            log.info("INFO: Тесты запустятся локально.");
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