package steps.UI_Steps;

import config.CredentialsConfig;
import io.qameta.allure.Step;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LoginPage;
import utils.RandomUtils;

public class WebStepsForLoginPage {

    private static final Logger log = LoggerFactory.getLogger(WebStepsForLoginPage.class);

    private final LoginPage loginPage = new LoginPage();
    private static final CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    @Getter
    private String lastGeneratedUserName;
    @Getter
    private String lastGeneratedPassword;

    @Step("Открыть страницу login")
    public void openLoginPage() {
        loginPage.openPageRemoveBanners();
    }

    @Step("Заполнить поле UserName рандомным значением: {0}")
    public void setRandomUserName(String randomUserName) {
        lastGeneratedUserName = randomUserName;
        loginPage.setUsername(lastGeneratedUserName);
    }

    @Step("Заполнить поле Password рандомным значением: {0}")
    public void setRandomPassword(String randomPassword) {
        lastGeneratedPassword = randomPassword;
        loginPage.setPassword(lastGeneratedPassword);
    }

    @Step("Нажать кнопку 'Login'")
    public void pressLoginButton() {
        loginPage.clickLoginButton();
    }

    @Step("Авторизоваться зарегистрированным пользователем")
    public void verifySuccessfulLoginExistingUser() {

        String username = System.getProperty("profileUserName", credentials.getUsername());
        String password = System.getProperty("profileUserPassword", credentials.getPassword());


        log.info("Загруженный логин: {}", username);
        log.info("Загруженный пароль: {}", password);

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalStateException(
                    "Ошибка: Логин или пароль отсутствуют."
            );
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

    @Step("Проверить, что поле username подсвечено красной рамкой как обязательное")
    public void verifyUsernameFieldHighlightedAsMandatory() {
        loginPage.verifyUsernameFieldHighlightedAsMandatory();
    }

    @Step("Проверить, что поле password подсвечено красной рамкой как обязательное")
    public void verifyPasswordFieldHighlightedAsMandatory() {
        loginPage.verifyPasswordFieldHighlightedAsMandatory();
    }
}