package helpers.extensions;

import api.AuthorizationWithApi;
import models.AuthResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static data.AuthData.*;
import static java.lang.System.getProperty;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        // Приоритет: системные свойства > данные из файла
        USER_NAME = getProperty("profileUserName", getProperty("profileUserName"));
        USER_PASSWORD = getProperty("profileUserPassword", getProperty("profileUserPassword"));

        if (USER_NAME == null || USER_PASSWORD == null) {
            throw new IllegalStateException("Логин или пароль не заданы ни в системных свойствах, ни в credentials.properties");
        }

        AuthResponseModel authResponse = AuthorizationWithApi.getAuthData(USER_NAME, USER_PASSWORD);

        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();
    }
}
