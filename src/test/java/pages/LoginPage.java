package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    private final SelenideElement usernameField = $("#userName");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton = $("#login");
    private final SelenideElement errorMessage = $("#name");
    private final SelenideElement logoutButton = $("#submit");

    @Step("Открыть страницу Login")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginPage setUsername(String username) {
        usernameField.setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Нажать кнопку Login")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    @Step("Проверить, что отображается сообщение об ошибке: {expectedMessage}")
    public LoginPage verifyErrorMessage(String expectedMessage) {
        errorMessage.shouldBe(visible).shouldHave(text(expectedMessage));
        return this;
    }

    @Step("Проверить, что кнопка Logout отображается")
    public LoginPage verifyUserIsLoggedIn() {
        logoutButton.shouldBe(visible);
        return this;
    }

    @Step("Авторизоваться с логином {username} и паролем {password}")
    public LoginPage login(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLoginButton();
        return this;
    }
}