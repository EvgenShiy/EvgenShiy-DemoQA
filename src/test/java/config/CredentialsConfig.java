package config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "system:properties", // Читаем из -DprofileUserName и -DprofileUserPassword
        //"classpath:properties/credentials.properties"
})
public interface CredentialsConfig extends Config {

    @Key("username")
    String getUsername();

    @Key("password")
    String getPassword();

}