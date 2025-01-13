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

    @Step("Заполнить поле Password значением: {username}")
    public void setPassword() {
        loginPage.setPassword(password);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton(){
        loginPage.clickLoginButton();
    }
}
