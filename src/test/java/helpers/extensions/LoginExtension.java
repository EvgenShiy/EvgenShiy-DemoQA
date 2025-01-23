package helpers.extensions;

import api.AccountApi;
import api.BookStoreApi;
import lombok.Getter;
import org.openqa.selenium.Cookie;
import models.AuthResponseModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pages.ProfilePage;
import utils.UIUtils;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

public class LoginExtension implements BeforeEachCallback {

    @Getter
    private static String token;
    @Getter
    private static String userId;

    @Override
    public void beforeEach(ExtensionContext context) {
        String username = System.getProperty("profileUserName", "defaultLogin");
        String password = System.getProperty("profileUserPassword", "defaultPassword");

        if (username == null || password == null || "defaultLogin".equals(username) || "defaultPassword".equals(password)) {
            throw new IllegalStateException("Error: Login or password must not be null or use default values.");
        }

        AuthResponseModel authResponse = AccountApi.getAuthData(username, password);

        if (authResponse == null || authResponse.getToken() == null || authResponse.getUserId() == null) {
            throw new IllegalStateException("Error: Authorization failed. AuthResponse is null or invalid.");
        }

        token = authResponse.getToken();
        userId = authResponse.getUserId();

        step("Добавить cookies для аутентификации в браузер", () -> {
            open("/favicon.ico");

            // Удаление рекламных блоков и ненужных элементов
            UIUtils.removeAdvertisements();

            getWebDriver().manage().addCookie(new Cookie("token", token));
            getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
            getWebDriver().manage().addCookie(new Cookie("userID", userId));
        });

        step("Проверить успешную авторизацию", () -> {
            ProfilePage profilePage = new ProfilePage();
            profilePage.openPage().checkUserName();
        });

        step("Добавить токен и userId в BookStoreApi", () -> {
            BookStoreApi.setAuthData(token, userId);
        });
    }
}
