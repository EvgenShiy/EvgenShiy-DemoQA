package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/credentials.properties"
})
public interface CredentialsConfig extends Config {
    @Key("profileUserName")
    String getUsername();

    @Key("profileUserPassword")
    String getPassword();
}