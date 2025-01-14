package tests.ui_tests;

import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class LoginTests extends TestBase {

    WebStepsForLoginPage webStepsForLoginPage = new WebStepsForLoginPage();

    @Test
    @Tag("UI")
    @Owner("shiianovaen")
    @DisplayName("Проверка успешной аутентификации зарегистрированного пользователя")
    void successesLoginTest() {
        webStepsForLoginPage.verifySuccessfulLoginExistingUser();
    }

    @Test
    @Tag("UI")
    @Owner("shiianovaen")
    @DisplayName("Проверка неудачной аутентификации пользователя")
    void unSuccessesLoginTest() {
        webStepsForLoginPage.openLoginPage();
        webStepsForLoginPage.setRandomUserName();
        webStepsForLoginPage.setRandomPassword();
        webStepsForLoginPage.pressLoginButton();
        webStepsForLoginPage.verifyErrorDisplayedForInvalidCredentials();
    }
}