package tests.api_tests;

import api.AccountApi;
import api.BookStoreApi;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.UserProfileModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("API")
@Feature("Bookstore")
@Owner("Shiianova E.")
public class ProfileAPITests extends Api_TestBase {

    private static final Logger log = LoggerFactory.getLogger(ProfileAPITests.class);

    @Test
    @DisplayName("Удаление всех книг из Profile через API")
    void deleteAllBooksFromProfileAPITest() {

        AuthRequestModel userData = step("Генерация данных для нового пользователя", () -> {
            AuthRequestModel result = AccountApi.generateRandomUserData();
            log.info("[USER GENERATION] Сгенерированы данные: UserName = {}, Password = {}", result.getUserName(), result.getPassword());
            return result;
        });

        final String userName = userData.getUserName();
        final String userPassword = userData.getPassword();

        AuthResponseModel response = step("Регистрация пользователя", () -> AccountApi.registerUser(userName, userPassword));
        final String userId = response.getUserId();

        step("Проверить, что регистрация прошла успешно", () -> {
            log.info("[USER REGISTRATION SUCCESS] UserName = {}, UserId = {}", response.getUsername(), response.getUserId());
        });

        String token = step("Получение токена пользователя", () -> AccountApi.generateAuthToken(userName, userPassword).getToken());

        step("Проверить, что токен получен", () -> {
            assertNotNull(token, "[VALIDATION] Токен не должен быть null");
            log.info("[TOKEN RECEIVED] Token = {}", token);
        });

        UserProfileModel initialProfile = step("Получение профиля пользователя", () -> AccountApi.getUserProfile(token, userId));

        step("Проверить, что профиль содержит корректные данные", () -> {
            assertEquals(userName, initialProfile.getUserName(), "[PROFILE VALIDATION] Имя пользователя не совпадает");
            log.info("[PROFILE VALIDATION SUCCESS] Профиль пользователя получен: {}", initialProfile);
        });

        BookStoreApi bookStoreApi = new BookStoreApi();

        Set<String> uniqueIsbns = new HashSet<>();
        while (uniqueIsbns.size() < 2) {
            uniqueIsbns.add(BookStoreApi.getRandomIsbn());
        }
        String[] isbns = uniqueIsbns.toArray(new String[0]);

        step("Добавить первую книгу в профиль", () -> {
            bookStoreApi.addBookToProfile(isbns[0], token, userId);
            log.info("[BOOK ADDITION] Первая книга добавлена: ISBN {}", isbns[0]);
        });

        step("Добавить вторую книгу в профиль", () -> {
            bookStoreApi.addBookToProfile(isbns[1], token, userId);
            log.info("[BOOK ADDITION] Вторая книга добавлена: ISBN {}", isbns[1]);
        });

        step("Удалить все книги из профиля", () -> {
            bookStoreApi.deleteAllBooksFromProfile(token, userId);
            log.info("[BOOK DELETION SUCCESS] Все книги удалены из профиля");
        });
        
        step("Удалить пользователя после выполнения теста", () -> AccountApi.deleteUser(token, userId));
        log.info("[USER DELETION SUCCESS] Пользователь успешно удален.");
    }
}
