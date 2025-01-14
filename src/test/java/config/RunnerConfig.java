package config;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;


public class RunnerConfig {

    private final  WebDriverConfig config;

    public RunnerConfig() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
        createWebDriver();
    }

    private void createWebDriver() {

        if (config.getRemoteUrl() == null) {
            WebDriverManager.chromedriver().setup();
        } else {
            Configuration.remote = config.getRemoteUrl().toString();

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));

            Configuration.browserCapabilities = capabilities;
        }

        switch (config.getBrowserName()) {
            case CHROME, FIREFOX: {
                Configuration.browser = config.getBrowserName().browserToLowerCase();
                break;
            }
            default:
                throw new RuntimeException("No such driver");
        }
        Configuration.pageLoadStrategy = "eager";
        Configuration.browserVersion = config.getBrowserVersion();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.pageLoadStrategy = config.getPageLoadStrategy();
    }
}