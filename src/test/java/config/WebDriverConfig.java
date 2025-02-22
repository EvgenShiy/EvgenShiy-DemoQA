package config;

import org.aeonbits.owner.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Config.Sources({
        "system:properties",
        "system:env",
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})
public interface WebDriverConfig extends Config {

    Logger log = LoggerFactory.getLogger(WebDriverConfig.class);

    @Key("browserName")
    @DefaultValue("CHROME")
    Browser getBrowserName();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("browserVersion")
    @DefaultValue("125.0")
    String getBrowserVersion();

    @Key("remoteUrl")
    @DefaultValue("")
    String getRemoteUrl();

    @Key("pageLoadStrategy")
    @DefaultValue("eager")
    String getPageLoadStrategy();
}
