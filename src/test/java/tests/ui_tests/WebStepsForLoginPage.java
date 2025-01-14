package tests.ui_tests;

import io.qameta.allure.Step;
import pages.LoginPage;
import utils.RandomUtils;

public class WebStepsForLoginPage {

    LoginPage loginPage = new LoginPage();
    RandomUtils randomUtils = new RandomUtils();

    String userName = randomUtils.getRandomFirstName();
    String password = randomUtils.getRandomString(8);

    @Step("Открыть страницу login")
    public void openLoginPage() {
        loginPage.openPage();
    }

    @Step("Заполнить поле UserName значением: {username}")
    public void setUserName() {
        loginPage.setUsername(userName);
    }

    @Step("Заполнить поле Password значением: {password}")
    public void setPassword() {
        loginPage.setPassword(password);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton(){
        loginPage.clickLoginButton();
    }

    @Step("Нажать кнопку 'New User'")
    public void pressNewUserButton(){
        loginPage.clickNewUserButton();
    }

    @Step("Проверить успешную аутентификацию пользователя")
    public void verifySuccessfulLogin(){
        loginPage.verifyUserIsLoggedIn();
    }

    @Step("Проверить неудачную аутентификацию пользователя")
    public void verifyErrorDisplayedForInvalidCredentials(){
        loginPage.verifyErrorMessage("Invalid username or password!");
    }
}
