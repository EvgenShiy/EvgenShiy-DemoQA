package helpers.extensions;

import api.AccountApi;
import api.BookStoreApi;
import config.CredentialsConfig;
import lombok.Getter;
import models.AuthResponseModel;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

public class LoginExtension implements BeforeEachCallback {

    private static final Logger log = LoggerFactory.getLogger(LoginExtension.class);
    private static final CredentialsConfig credentials =
            ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    @Getter
    private static String token;
    @Getter
    private static String userId;

    @Override
    public void beforeEach(ExtensionContext context) {

        String username = credentials.getUsername();
        String password = credentials.getPassword();

        log.info("DEBUG: Загруженный username = {}", username);
        log.info("DEBUG: Загруженный password = {}", password);

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalStateException("Error: Login or password must not be null.");
        }

        AuthResponseModel authResponse = AccountApi.getAuthData(username, password);

        if (authResponse.getToken() == null || authResponse.getUserId() == null) {
            throw new IllegalStateException("Error: Authorization failed. AuthResponse is null or invalid.");
        }

        token = authResponse.getToken();
        userId = authResponse.getUserId();

        log.info("DEBUG: Получен токен = {}", token);
        log.info("DEBUG: Получен userId = {}", userId);

        step("Добавить cookies для аутентификации в браузер", () -> {
            open("/favicon.ico");

            getWebDriver().manage().addCookie(new Cookie("token", token));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("userID", userId));

            log.info("DEBUG: Cookies добавлены в браузер.");
        });

        step("Добавить токен и userId в BookStoreApi", () -> {
            BookStoreApi.setAuthData(token, userId);
            log.info("DEBUG: Токен и userId переданы в BookStoreApi.");
        });
    }
}