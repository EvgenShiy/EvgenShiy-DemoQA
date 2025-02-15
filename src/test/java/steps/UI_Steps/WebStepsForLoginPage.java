package steps.UI_Steps;

import config.CredentialsConfig;
import io.qameta.allure.Step;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.LoginPage;
import utils.RandomUtils;

public class WebStepsForLoginPage {

    private final LoginPage loginPage = new LoginPage();
    private final RandomUtils randomUtils = new RandomUtils();
    private static final CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    @Getter
    private String lastGeneratedUserName;
    @Getter
    private String lastGeneratedPassword;

    @Step("Открыть страницу login")
    public void openLoginPage() {
        loginPage.openPageRemoveBanners();
    }

    @Step("Заполнить поле UserName рандомным значением: {lastGeneratedUserName}")
    public void setRandomUserName(String randomUserName) {
        this.lastGeneratedUserName = randomUserName;
        loginPage.setUsername(this.lastGeneratedUserName);
    }

    @Step("Заполнить поле Password рандомным значением: {lastGeneratedPassword}")
    public void setRandomPassword(String randomPassword) {
        this.lastGeneratedPassword = randomPassword;
        loginPage.setPassword(this.lastGeneratedPassword);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton() {
        loginPage.clickLoginButton();
    }

    @Step("Авторизоваться зарегистрированным пользователем с логином {username} и паролем {password}")
    public void verifySuccessfulLoginExistingUser() {

        String username = credentials.getUsername();
        String password = credentials.getPassword();


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
