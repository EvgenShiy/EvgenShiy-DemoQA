package tests.api_tests;

import api.AccountApi;
import lombok.extern.slf4j.Slf4j;
import models.AuthRequestModel;
import models.AuthResponseModelWithOptionalUserId;
import models.UserProfileModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class AccountTests extends ApiTestBase {

    @Test
    @Tag("API")
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
            AuthResponseModelWithOptionalUserId response = AccountApi.registerUser(userName[0], userPassword[0]);

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
    @DisplayName("Изменение данных пользователя через API")
    void updateUserDataTest() {

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
            AuthResponseModelWithOptionalUserId response = AccountApi.registerUser(userName[0], userPassword[0]);

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

        UserProfileModel updatedProfile = new UserProfileModel();
        updatedProfile.setUserName("Updated_" + userName[0]);
        updatedProfile.setEmail("updated_email@example.com");

        UserProfileModel responseAfterUpdate = step("Обновить данные профиля", () ->
                AccountApi.updateUserProfile(token, updatedProfile)
        );

        step("Проверить обновленные данные профиля", () -> {
            assertEquals(updatedProfile.getUserName(), responseAfterUpdate.getUserName(), "Имя пользователя не обновлено");
            assertEquals(updatedProfile.getEmail(), responseAfterUpdate.getEmail(), "Email не обновлен");
            log.info("Данные профиля успешно обновлены: {}", responseAfterUpdate);
        });

        step("Удалить пользователя", () ->
                AccountApi.deleteUser(token)
        );

        log.info("Пользователь успешно удален.");
    }
}