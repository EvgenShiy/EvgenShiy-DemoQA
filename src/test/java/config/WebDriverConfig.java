package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/local.properties"
})

public interface WebDriverConfig extends Config {   //TODO -- не используются как Sources переменные среды из Дженкинса.

                                                    //https://matteobaccan.github.io/owner/docs/loading-strategies/
                                                    //    -- тут есть примеры как их подключать.


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
    URL getRemoteUrl();

    @Key("pageLoadStrategy")
    @DefaultValue("eager")
    String getPageLoadStrategy();
}
