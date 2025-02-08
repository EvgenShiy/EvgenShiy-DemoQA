package tests.api_tests;

import api.AccountApi;
import helpers.PropertyLoader;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import lombok.extern.slf4j.Slf4j;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorResponseModel;
import models.UserProfileModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.RandomUtils;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class AccountAPITests extends Api_TestBase {

    @Test
    @Tag("API")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();

            log.info("Сгенерированы данные пользователя: UserName = {}, Password = {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        step("Регистрация сгенерированного пользователя", () -> {
            AuthResponseModel response = AccountApi.registerUser(userName, userPassword);

            step("Проверить, что регистрация прошла успешно", () -> {
                assertNotNull(response.getUsername(), "UserName не должен быть null");
                assertNotNull(response.getUserId(), "UserId не должен быть null");
                assertFalse(response.getUsername().isEmpty(), "UserName не должен быть пустым");
                assertFalse(response.getUserId().isEmpty(), "UserId не должен быть пустым");

                log.info("Пользователь успешно зарегистрирован: UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
            });
        });
    }

    @Test
    @Tag("API")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Удаление пользователя через API")
    void deleteUserTest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();

            log.info("Сгенерированы данные пользователя: UserName = {}, Password = {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        AuthResponseModel response = step("Регистрация сгенерированного пользователя", () ->{
            return AccountApi.registerUser(userName, userPassword);
        });

        final String userId = response.getUserId();

        step("Проверить, что регистрация прошла успешно", () -> {
            log.info("Пользователь успешно зарегистрирован: UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
        });

        String token = step("Получить токен пользователя", () ->
                AccountApi.generateAuthToken(userName, userPassword).getToken()
        );

        step("Проверить, что токен получен", () -> {
            assertNotNull(token, "Токен не должен быть null");
            log.info("Токен пользователя получен: {}", token);
        });

        UserProfileModel initialProfile = step("Получить данные профиля", () ->
                AccountApi.getUserProfile(token, userId)
        );

        step("Проверить, что профиль содержит корректные данные", () -> {
            assertEquals(userName, initialProfile.getUserName(), "Имя пользователя не совпадает");
            log.info("Профиль пользователя успешно получен: {}", initialProfile);
        });

        step("Удалить пользователя", () ->
                AccountApi.deleteUser(token, userId)
        );

        log.info("Пользователь успешно удален.");
    }

    @Test
    @Tag("API")
    @Feature("Account")
    @Owner("Shiianova E.")
    @DisplayName("Проверка отображения корректного сообщения об ошибке при регистрации нового рандомного пользователя некорректными данными через API")
    void verifyErrorRegistrationUserMessageTest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();

            log.info("Сгенерированы данные пользователя: UserName = {}, Password = {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        step("Регистрация сгенерированного пользователя", () -> {
            ErrorResponseModel response = AccountApi.registerUserWithError(userName, userPassword);

            step("Проверить, что попытка регистрации прошла неудачно", () -> {
                assertEquals("1300", response.getCode(), "Код ошибки должен быть 1300");
                assertEquals("Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.",
                        response.getMessage(),
                        "Сообщение об ошибке должно совпадать с ожидаемым");

                log.info("Получена ожидаемая ошибка: Code = {}, Message = {}", response.getCode(), response.getMessage());
            });
        });
    }

    @Test
    @Tag("API")
    @Feature("Account")
    @Issue("HOMEWORK-1391")
    @Owner("Shiianova E.")
    @DisplayName("Проверка сообщения об ошибке при неверном пароле для зарегистрированного пользователя")
    void verifyErrorForInvalidPasswordTest() {

        final String[] userName = new String[1];
        final String[] invalidPassword = new String[1];

        step("Получение зарегистрированного UserName из файла credential.properties", () -> {
            userName[0] = PropertyLoader.getPropertyFromFile("src/test/resources/properties/credentials.properties", "profileUserName");

            if (userName[0] == null || userName[0].isEmpty()) {
                throw new RuntimeException("UserName не найден в файле credential.properties");
            }

            log.info("Получен зарегистрированный UserName: {}", userName[0]);
        });

        step("Генерация неверного пароля", () -> {
            RandomUtils userData = new RandomUtils();
            invalidPassword[0] = userData.generateStrongPassword(12);
            log.info("Сгенерирован неверный пароль: {}", invalidPassword[0]);
        });

        step("Попытка авторизации с неверным паролем", () -> { //  Issue("HOMEWORK-1391") в JIRA
            AuthRequestModel authRequest = new AuthRequestModel();
            authRequest.setUserName(userName[0]);
            authRequest.setPassword(invalidPassword[0]);

            ErrorResponseModel errorResponse = AccountApi.loginWithError(authRequest);

            step("Проверка сообщения об ошибке", () -> {
                assertEquals("1200", errorResponse.getCode(), "Код ошибки должен быть 1200");
                assertEquals("Invalid username or password!", errorResponse.getMessage(), "Сообщение об ошибке должно совпадать с ожидаемым");

                log.info("Получена ожидаемая ошибка: Code = {}, Message = {}", errorResponse.getCode(), errorResponse.getMessage());
            });
        });
    }

}