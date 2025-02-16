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

    @Step("Удалить баннеры на странице")
    public LoginPage removeBanners() {
        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");
        return this;
    }

    @Step("Открыть страницу Login и удалить баннеры на странице")
    public LoginPage openPageRemoveBanners() {
        openPage();
        removeBanners();
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginPage setUsername(String username) {
        usernameField.setValue(username);
        return this;
    }

    @Step("Ввести пароль: {password}")
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
    public LoginPage verifyLogoutButtonIsVisible() {
        logoutButton.shouldBe(visible);
        return this;
    }

    @Step("Проверить, что поле {fieldName} подсвечено красной рамкой как обязательное")
    public LoginPage verifyFieldHighlightedAsMandatory(String fieldName) {  // TODO Если хочется проверить цвета двух полей, таким образом, просто лучше всего написать на это два метода по четыре строчки каждый
        SelenideElement field;
        switch (fieldName.toLowerCase()) {
            case "username":
                field = usernameField;
                break;
            case "password":
                field = passwordField;
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
        field.shouldHave(cssValue("border-color", "rgb(220, 53, 69)"));
        return this;
    }
}