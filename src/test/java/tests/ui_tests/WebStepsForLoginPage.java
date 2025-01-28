package tests.ui_tests;

import io.qameta.allure.Step;
import lombok.Getter;
import pages.LoginPage;
import utils.RandomUtils;

public class WebStepsForLoginPage {

    private final LoginPage loginPage = new LoginPage();
    private final RandomUtils randomUtils = new RandomUtils();

    @Getter
    private String lastGeneratedUserName;
    @Getter
    private String lastGeneratedPassword;

    @Step("Открыть страницу login")
    public void openLoginPage() {
        loginPage.openPage();
    }

    @Step("Заполнить поле UserName рандомным значением: {lastGeneratedUserName}")
    public void setRandomUserName() {
        lastGeneratedUserName = randomUtils.getRandomFirstName();
        loginPage.setUsername(lastGeneratedUserName);
    }

    @Step("Заполнить поле Password рандомным значением: {lastGeneratedPassword}")
    public void setRandomPassword() {
        lastGeneratedPassword = randomUtils.getRandomString(8);
        loginPage.setPassword(lastGeneratedPassword);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton() {
        loginPage.clickLoginButton();
    }

    @Step("Нажать кнопку 'New User'")
    public void pressNewUserButton() {
        loginPage.clickNewUserButton();
    }

    @Step("Авторизоваться зарегистрированным пользователем с логином {username} и паролем {password}")
    public void verifySuccessfulLoginExistingUser() {
        helpers.PropertyLoader.loadCredentials();

        String username = System.getProperty("profileUserName");
        String password = System.getProperty("profileUserPassword");

        if (username == null || password == null) {
            throw new IllegalStateException("Error: Логин или пароль не загружены из файла properties.");
        }
        openLoginPage();
        loginPage.setUsername(username);
        loginPage.setPassword(password);
        pressLoginButton();
        loginPage.verifyLogoutButtonIsVisible();
    }

    @Step("Проверить неудачную аутентификацию пользователя")
    public void verifyErrorDisplayedForInvalidCredentials() {
        loginPage.verifyErrorMessage("Invalid username or password!");
    }

    @Step("Проверить, что поле {fieldName} подсвечено красной рамкой как обязательное")
    public void verifyMandatoryFieldHighlight(String fieldName) {
        loginPage.verifyFieldHighlightedAsMandatory(fieldName);
    }
}
