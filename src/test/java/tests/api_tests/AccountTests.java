package tests.api_tests;

import api.AccountApi;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.AuthResponseModelWithOptionalUserId;
import models.UserProfileModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTests extends ApiTestBase {

    private static final Logger logger = LoggerFactory.getLogger(AccountTests.class);

    @Test
    @Tag("API")
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {
        step("Зарегистрировать рандомного пользователя", () -> {

            AuthResponseModelWithOptionalUserId response = AccountApi.registerRandomUser();

            step("Проверить, что регистрация прошла успешно", () -> {
                assertNotNull(response.getUsername(), "UserName не должен быть null");
                assertNotNull(response.getUserId(), "UserId не должен быть null");
                assertFalse(response.getUsername().isEmpty(), "UserName не должен быть пустым");
                assertFalse(response.getUserId().isEmpty(), "UserId не должен быть пустым");

                logger.info("Пользователь успешно зарегистрирован: UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
            });
        });
    }

    @Test
    @Tag("API")
    @DisplayName("Изменение данных пользователя через API")
    void updateUserDataTest() {

        step("Генерация данных для нового пользователя", () -> {
            AccountApi.generateRandomUserData();
        });

        String userName = AccountApi.generateRandomUserData().getUserName();
        String userPassword = AccountApi.generateRandomUserData().getPassword();

        logger.info("Сгенерированы данные для пользователя: UserName = {}, Password = {}", userName, userPassword);

        String token = step("Получить токен пользователя", () ->
                AccountApi.generateAuthToken(userName, userPassword).getToken()
        );

        step("Проверить, что токен получен", () -> {
            assertNotNull(token, "Токен не должен быть null");
            logger.info("Токен пользователя получен: {}", token);
        });

        UserProfileModel initialProfile = step("Получить данные профиля", () ->
                AccountApi.getUserProfile(token)
        );

        step("Проверить, что профиль содержит корректные данные", () -> {
            assertEquals(userName, initialProfile.getUserName(), "Имя пользователя не совпадает");
            logger.info("Профиль пользователя успешно получен: {}", initialProfile);
        });

        UserProfileModel updatedProfile = new UserProfileModel();
        updatedProfile.setUserName("Updated_" + userName);
        updatedProfile.setEmail("updated_email@example.com");

        UserProfileModel responseAfterUpdate = step("Обновить данные профиля", () ->
                AccountApi.updateUserProfile(token, updatedProfile)
        );

        step("Проверить обновленные данные профиля", () -> {
            assertEquals(updatedProfile.getUserName(), responseAfterUpdate.getUserName(), "Имя пользователя не обновлено");
            assertEquals(updatedProfile.getEmail(), responseAfterUpdate.getEmail(), "Email не обновлен");
            logger.info("Данные профиля успешно обновлены: {}", responseAfterUpdate);
        });

        step("Удалить пользователя", () ->
                AccountApi.deleteUser(token)
        );

        logger.info("Пользователь успешно удален.");
    }
}