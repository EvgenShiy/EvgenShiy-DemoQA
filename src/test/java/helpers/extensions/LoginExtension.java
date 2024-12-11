package helpers.extensions;

import api.AuthorizationWithApi;
import api.BookStoreApi;
import org.openqa.selenium.Cookie;
import models.AuthResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import static io.qameta.allure.Allure.step;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {

        String username = System.getProperty("profileUserName", "defaultLogin");
        String password = System.getProperty("profileUserPassword", "defaultPassword");

        System.out.println("System Property profileUserName: " + username);
        System.out.println("System Property profileUserPassword: " + password);

        // Проверка данных
        if ("defaultLogin".equals(username) || "defaultPassword".equals(password)) {
            System.out.println("Warning: Default login or password is being used.");
        }

        if (username == null || password == null) {
            throw new IllegalStateException("Error: Login or password must not be null.");
        }

        // Получаем данные для авторизации через API
        AuthResponseModel authResponse = AuthorizationWithApi.getAuthData(username, password);

        if (authResponse == null || authResponse.getToken() == null) {
            throw new IllegalStateException("Error: Authorization failed. AuthResponse is null or invalid.");
        }

        // Добавляем cookies для авторизации
        step("Добавить cookies для аутентификации в браузер", () -> {
            open("/favicon.ico");

            getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        });

        // Проверяем успешную авторизацию
        step("Проверить успешную авторизацию", () -> {
            open("/profile");
            $("#userName-value").shouldHave(text(username));
        });

        // Сохраняем токен и userId в BookStoreApi
        step("Добавить токен и userId в BookStoreApi", () -> {
            BookStoreApi.setAuthData(authResponse.getToken(), authResponse.getUserId());
        });
    }
}
