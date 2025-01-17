package tests.api_tests;

import api.AccountApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class AccountTests extends TestBase {

    AccountApi accountApi = new AccountApi();

    @Test
    @Tag("API")
    @DisplayName("Удаление книги из Profile на UI")
    @WithLogin
    void deleteBookFromProfileOnUiTest(){

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