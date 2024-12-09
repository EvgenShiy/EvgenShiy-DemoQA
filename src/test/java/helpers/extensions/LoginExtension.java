package helpers.extensions;

import api.AuthorizationWithApi;
import org.openqa.selenium.Cookie;
import models.AuthResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import static data.AuthData.USER_NAME;
import static data.AuthData.USER_PASSWORD;
import static data.AuthData.USER_ID;
import static data.AuthData.USER_TOKEN;
import static data.AuthData.EXPIRES;
import static data.AuthData.CREATE_DATE;
import static data.AuthData.IS_ACTIVE;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        // Отладочный вывод
        System.out.println("System Property profileUserName: " + System.getProperty("profileUserName"));
        System.out.println("System Property profileUserPassword: " + System.getProperty("profileUserPassword"));

        // Если переменные не переданы, попробуйте использовать значения по умолчанию
        String username = System.getProperty("profileUserName", "login");
        String password = System.getProperty("profileUserPassword", "password");

        // Проверка, что данные все равно передаются корректно
        if (username.equals("login") || password.equals("password")) {
            System.out.println("Warning: Login or password are default values.");
        }

        // Получаем данные для авторизации через API
        AuthResponseModel authResponse = AuthorizationWithApi.getAuthData(username, password);

        open("/favicon.ico");

        // Добавляем cookies для аутентификации в браузер
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));

        // Заполняем глобальные данные для тестов
        USER_ID = authResponse.getUserId();
        USER_TOKEN = authResponse.getToken();
        EXPIRES = authResponse.getExpires();
        CREATE_DATE = authResponse.getCreatedDate();
        IS_ACTIVE = authResponse.getIsActive();
    }
}
