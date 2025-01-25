package tests.api_tests;

import api.AccountApi;
import helpers.PropertyLoader;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tag("API")
@Owner("Shiianova E.")
public class AccountAPITests extends Api_TestBase {

    @Test
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {

        final String[] userName = new String[1];
        final String[] userPassword = new String[1];

        step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel userData = AccountApi.generateRandomUserData();
            userName[0] = userData.getUserName();
            userPassword[0] = userData.getPassword();

            log.info("Сгенерированы данные для пользователя: UserName = {}, Password = {}", userName[0], userPassword[0]);
        });

        step("Регистрация сгенерированного пользователя", () -> {
            AuthResponseModel response = AccountApi.registerUser(userName[0], userPassword[0]);

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
    @DisplayName("Удаление пользователя через API")
    void deleteUserTest() {

        final String[] userName = new String[1];
        final String[] userPassword = new String[1];
        final String[] userId = new String[1];

        step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel userData = AccountApi.generateRandomUserData();
            userName[0] = userData.getUserName();
            userPassword[0] = userData.getPassword();

            log.info("Сгенерированы данные для пользователя: UserName = {}, Password = {}", userName[0], userPassword[0]);
        });

        step("Регистрация сгенерированного пользователя", () -> {
            AuthResponseModel response = AccountApi.registerUser(userName[0], userPassword[0]);

            userId[0] = response.getUserId();

            step("Проверить, что регистрация прошла успешно", () -> {
                log.info("Пользователь успешно зарегистрирован: UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
            });
        });

        String token = step("Получить токен пользователя", () ->
                AccountApi.generateAuthToken(userName[0], userPassword[0]).getToken()
        );

        step("Проверить, что токен получен", () -> {
            assertNotNull(token, "Токен не должен быть null");
            log.info("Токен пользователя получен: {}", token);
        });

        UserProfileModel initialProfile = step("Получить данные профиля", () ->
                AccountApi.getUserProfile(token, userId[0])
        );

        step("Проверить, что профиль содержит корректные данные", () -> {
            assertEquals(userName[0], initialProfile.getUserName(), "Имя пользователя не совпадает");
            log.info("Профиль пользователя успешно получен: {}", initialProfile);
        });

        step("Удалить пользователя", () ->
                AccountApi.deleteUser(token, userId[0])
        );

        log.info("Пользователь успешно удален.");
    }

    @Test
    @DisplayName("Проверка отображения корректного сообщения об ошибке при регистрации нового рандомного пользователя некорректными данными через API")
    void verifyErrorRegistrationUserMessageTest() {

        String[] userName = new String[1];
        String[] userPassword = new String[1];

        step("Генерация данных для нового пользователя", () -> {
            RandomUtils userData = new RandomUtils();
            userName[0] = userData.getRandomFirstName();
            userPassword[0] = userData.getRandomString(7);

            log.info("Сгенерированы данные для пользователя: UserName = {}, Password = {}", userName[0], userPassword[0]);
        });

        step("Регистрация сгенерированного пользователя", () -> {
            ErrorResponseModel response = AccountApi.registerUserWithError(userName[0], userPassword[0]);

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

        step("Попытка авторизации с неверным паролем", () -> { // TODO ЗАВЕСТИ БАГ-РЕПОРТ ДЛЯ JIRA
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