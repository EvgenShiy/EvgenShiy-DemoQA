package tests.api_tests;

import api.AccountApi;
import models.UserDataModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTests extends TestBase {

    @Test
    @Tag("API")
    @DisplayName("Регистрация нового рандомного пользователя через API")
    void registerRandomUserTest() {
        step("Регистрация рандомного пользователя", () -> {

            UserDataModel userData = AccountApi.registerRandomUser();

            step("Проверить, что регистрация прошла успешно", () -> {
                assertNotNull(userData.getUserName(),"UserName не должен быть null");
                assertNotNull(userData.getToken(), "Token не должен быть null");
                assertFalse(userData.getUserName().isEmpty(), "UserName не должен быть пустым");
                assertFalse(userData.getToken().isEmpty(), "Token не должен быть пустым");
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