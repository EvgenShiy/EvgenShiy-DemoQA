package tests.api_tests;

import api.AccountApi;
import api.BookStoreApi;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.UserProfileModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileAPITests extends Api_TestBase {

    private static final Logger log = LoggerFactory.getLogger(ProfileAPITests.class);

    @Test
    @Tag("API")
    @DisplayName("Удаление всех книг из Profile через API")
    void deleteAllBooksFromProfileAPITest() {

        final String[] userName = new String[1];
        final String[] userPassword = new String[1];
        final String[] userId = new String[1];
        final String[] token = new String[1];

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

        step("Получить токен пользователя", () -> {
            token[0] = AccountApi.generateAuthToken(userName[0], userPassword[0]).getToken();
            log.info("Получен токен: {}", token[0]);
        });

        step("Проверить, что токен получен", () -> {
            assertNotNull(token[0], "Токен не должен быть null");
            log.info("Токен пользователя получен: {}", token[0]);
        });

        UserProfileModel initialProfile = step("Получить данные профиля", () ->
                AccountApi.getUserProfile(token[0], userId[0])
        );

        step("Проверить, что профиль содержит корректные данные", () -> {
            assertEquals(userName[0], initialProfile.getUserName(), "Имя пользователя не совпадает");
            log.info("Профиль пользователя успешно получен: {}", initialProfile);
        });

        BookStoreApi bookStoreApi = new BookStoreApi();

        step("Добавить первую случайную книгу в профиль", () -> {
            String isbn1 = BookStoreApi.getRandomIsbn();
            bookStoreApi.addBookToProfile(isbn1, token[0], userId[0]);
            log.info("Первая книга с ISBN {} добавлена в профиль", isbn1);
        });

        step("Добавить вторую случайную книгу в профиль", () -> {
            String isbn2 = BookStoreApi.getRandomIsbn();
            bookStoreApi.addBookToProfile(isbn2,token[0], userId[0]);
            log.info("Вторая книга с ISBN {} добавлена в профиль", isbn2);
        });

        step("Удалить все книги из профиля", () -> {
            bookStoreApi.deleteAllBooksFromProfile(token[0], userId[0]);
            log.info("Все книги успешно удалены из профиля");
        });

        step("Удалить пользователя", () ->
                AccountApi.deleteUser(token[0], userId[0])
        );

        log.info("Пользователь успешно удален.");
    }
}
