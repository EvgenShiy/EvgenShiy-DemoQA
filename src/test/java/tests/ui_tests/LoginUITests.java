package tests.ui_tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.UI_Steps.WebStepsForLoginPage;
import utils.RandomUtils;

@Tag("UI")
@Feature("Account")
@Owner("Shiianova E.")
public class LoginUITests extends UI_TestBase {

    private final WebStepsForLoginPage webStepsForLoginPage = new WebStepsForLoginPage();
    private final RandomUtils randomUtils = new RandomUtils();

    @Test
    @DisplayName("Проверка успешной аутентификации зарегистрированного пользователя")
    void successesLoginTest() {
        webStepsForLoginPage.verifySuccessfulLoginExistingUser();
    }

    @Test
    @DisplayName("Проверка неудачной аутентификации пользователя")
    void unSuccessesLoginTest() {

        String randomUser = randomUtils.getRandomFirstName();
        String randomPass = randomUtils.getRandomString(8);

        webStepsForLoginPage.openLoginPage();
        webStepsForLoginPage.setRandomUserName(randomUser);
        webStepsForLoginPage.setRandomPassword(randomPass);
        webStepsForLoginPage.pressLoginButton();
        webStepsForLoginPage.verifyErrorDisplayedForInvalidCredentials();
    }

    @Test
    @DisplayName("Проверка подсветки обязательных полей красной рамкой при отсутствии ввода")
    public void shouldHighlightMandatoryFieldsWhenEmptyTest() {
        webStepsForLoginPage.openLoginPage();
        webStepsForLoginPage.pressLoginButton();
        webStepsForLoginPage.verifyMandatoryFieldHighlight("userName");
        webStepsForLoginPage.verifyMandatoryFieldHighlight("password");
    }
}