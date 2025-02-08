package tests.ui_tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.UI_Steps.WebStepsForLoginPage;

public class LoginUITests extends UI_TestBase {

    WebStepsForLoginPage webStepsForLoginPage = new WebStepsForLoginPage();

    @Test
    @Tag("UI")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Проверка успешной аутентификации зарегистрированного пользователя")
    void successesLoginTest() {
        webStepsForLoginPage.verifySuccessfulLoginExistingUser();
    }

    @Test
    @Tag("UI")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Проверка неудачной аутентификации пользователя")
    void unSuccessesLoginTest() {
        webStepsForLoginPage.openLoginPage();
        webStepsForLoginPage.setRandomUserName();
        webStepsForLoginPage.setRandomPassword();
        webStepsForLoginPage.pressLoginButton();
        webStepsForLoginPage.verifyErrorDisplayedForInvalidCredentials();
    }

    @Test
    @Tag("UI")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Проверка подсветки обязательных полей красной рамкой при отсутствии ввода")
    public void shouldHighlightMandatoryFieldsWhenEmptyTest() {
        webStepsForLoginPage.openLoginPage();
        webStepsForLoginPage.pressLoginButton();
        webStepsForLoginPage.verifyMandatoryFieldHighlight("userName");
        webStepsForLoginPage.verifyMandatoryFieldHighlight("password");
    }
}