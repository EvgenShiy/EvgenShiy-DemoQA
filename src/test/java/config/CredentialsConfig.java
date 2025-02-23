package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/credentials.properties",
        "system:properties"
})
public interface CredentialsConfig extends Config {

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();

}