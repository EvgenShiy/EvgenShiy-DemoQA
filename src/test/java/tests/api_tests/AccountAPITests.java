package tests.api_tests;

import api.AccountApi;
import config.CredentialsConfig;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import lombok.extern.slf4j.Slf4j;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorResponseModel;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.RandomUtils;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tag("API")
@Feature("Account")
@Owner("Shiianova E.")
public class AccountAPITests extends Api_TestBase {

    private static final CredentialsConfig credentials = ConfigFactory.create(CredentialsConfig.class, System.getProperties());

    @Test
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();
            log.info("[USER REGISTRATION] Сгенерирован UserName: {}, Password: {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        step("Регистрация сгенерированного пользователя", () -> {
            AuthResponseModel response = AccountApi.registerUser(userName, userPassword);

            step("Проверка успешной регистрации", () -> {
                assertNotNull(response.getUsername(), "[VALIDATION] UserName не должен быть null");
                assertNotNull(response.getUserId(), "[VALIDATION] UserId не должен быть null");

                log.info("[USER REGISTRATION SUCCESS] UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
            });
        });
    }

    @Test
    @DisplayName("Удаление пользователя через API")
    void deleteUserTest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();
            log.info("[USER GENERATION] UserName: {}, Password: {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        AuthResponseModel response = step("Регистрация пользователя", () -> AccountApi.registerUser(userName, userPassword));

        final String userId = response.getUserId();
        log.info("[USER REGISTERED] UserName = {}, UserId = {}", userName, userId);

        String token = step("Получение токена пользователя", () -> AccountApi.generateAuthToken(userName, userPassword).getToken());

        assertNotNull(token, "[VALIDATION] Токен не должен быть null");
        log.info("[TOKEN RECEIVED] Token = {}", token);

        step("Удаление пользователя", () -> {
            AccountApi.deleteUser(token, userId);
            log.info("[USER DELETED] UserId = {}", userId);
        });
    }

    @Test
    @DisplayName("Проверка ошибки при регистрации пользователя с некорректными данными")
    void verifyErrorRegistrationUserMessageTest() {

        AuthRequestModel userData = step("Генерация пользователя с невалидными данными", () -> {
            AuthRequestModel result = AccountApi.generateInvalidUserData();
            log.info("[INVALID USER GENERATION] UserName: {}, Password: {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        step("Попытка регистрации пользователя с невалидными данными", () -> {
            ErrorResponseModel response = AccountApi.registerUserWithError(userName, userPassword);

            step("Проверка сообщения об ошибке", () -> {
                assertEquals("1300", response.getCode(), "[ERROR VALIDATION] Код ошибки должен быть 1300");
                assertEquals("Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.",
                        response.getMessage(),
                        "[ERROR VALIDATION] Сообщение об ошибке должно совпадать с ожидаемым");

                log.info("[REGISTRATION ERROR] Code = {}, Message = {}", response.getCode(), response.getMessage());
            });
        });
    }

    @Test
    @Issue("HOMEWORK-1391")
    @DisplayName("Проверка ошибки при неверном пароле для зарегистрированного пользователя")
    void verifyErrorForInvalidPasswordTest() {

        final String userName = step("Получение UserName зарегистрированного пользователя", () -> {
            String name = credentials.getUsername();
            log.info("[CREDENTIALS LOADED] UserName = {}", name);
            return name;
        });

        final String invalidPassword = step("Генерация неверного пароля", () -> {
            String password = new RandomUtils().generateStrongPassword(12);
            log.info("[INVALID PASSWORD GENERATED] {}", password);
            return password;
        });

        step("Попытка авторизации с неверным паролем", () -> {
            AuthRequestModel authRequest = new AuthRequestModel();
            authRequest.setUserName(userName);
            authRequest.setPassword(invalidPassword);

            ErrorResponseModel errorResponse = AccountApi.loginWithError(authRequest);

            step("Проверка сообщения об ошибке", () -> {
                assertEquals("1200", errorResponse.getCode(), "[ERROR VALIDATION] Код ошибки должен быть 1200");
                assertEquals("Invalid username or password!", errorResponse.getMessage(), "[ERROR VALIDATION] Сообщение об ошибке должно совпадать с ожидаемым");

                log.info("[LOGIN ERROR] Code = {}, Message = {}", errorResponse.getCode(), errorResponse.getMessage());
            });
        });
    }
}