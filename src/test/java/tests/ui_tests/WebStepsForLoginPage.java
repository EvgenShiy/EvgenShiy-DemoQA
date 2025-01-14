package tests.ui_tests;

import io.qameta.allure.Step;
import pages.LoginPage;
import utils.RandomUtils;

public class WebStepsForLoginPage {

    LoginPage loginPage = new LoginPage();
    RandomUtils randomUtils = new RandomUtils();

    String randomUserName = randomUtils.getRandomFirstName();
    String randomPassword = randomUtils.getRandomString(8);

    @Step("Открыть страницу login")
    public void openLoginPage() {
        loginPage.openPage();
    }

    @Step("Заполнить поле UserName рандомным значением: {randomUserName}")
    public void setRandomUserName() {
        loginPage.setUsername(randomUserName);
    }

    @Step("Заполнить поле Password рандомным значением: {randomPassword}")
    public void setRandomPassword() {
        loginPage.setPassword(randomPassword);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton(){
        loginPage.clickLoginButton();
    }

    @Step("Нажать кнопку 'New User'")
    public void pressNewUserButton(){
        loginPage.clickNewUserButton();
    }

    @Step("Авторизоваться зарегистрированным пользователем с логином {username} и паролем {password}")
    public void verifySuccessfulLoginExistingUser(){
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
    public void verifyErrorDisplayedForInvalidCredentials(){
        loginPage.verifyErrorMessage("Invalid username or password!");
    }
}
