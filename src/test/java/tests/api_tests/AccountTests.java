package tests.api_tests;

import api.AccountApi;
import models.AuthResponseModel;
import models.AuthResponseModelWithOptionalUserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTests extends ApiTestBase {

    @Test
    @Tag("API")
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {
        step("Регистрация рандомного пользователя", () -> {

            // Регистрация пользователя
            AuthResponseModelWithOptionalUserId response = AccountApi.registerRandomUser();

            step("Проверить, что регистрация прошла успешно", () -> {
                assertNotNull(response.getUsername(), "UserName не должен быть null");
                assertNotNull(response.getUserId(), "UserId не должен быть null");
                assertFalse(response.getUsername().isEmpty(), "UserName не должен быть пустым");
                assertFalse(response.getUserId().isEmpty(), "UserId не должен быть пустым");
            });
        });
    }
}

/*
1. регистрация нового пользователя рандомными данными:
отправить запрос не регистрацию с рандомными данными
проверить успешную регистрацию

2. Изменение данных пользователя:
отправить запрос не регистрацию с рандомными данными
проверить успешную регистрацию
отправить запрос на изменение данных
проверить, что данные изменились

3. Удаление аккаунта
отправить запрос не регистрацию с рандомными данными
проверить успешную регистрацию
отправить запрос на удаление
проверить успешное удаление
 */