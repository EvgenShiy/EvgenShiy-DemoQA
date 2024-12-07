package tests;

import api.AuthorizationWithApi;
import api.BookStoreApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.TestBase;

import static data.AuthData.login;
import static io.qameta.allure.Allure.step;

public class ProfileTests extends TestBase {

    private String randomIsbn;

    @Test
    @DisplayName("Полный сценарий: работа с книгами через API и UI")
    void deleteBookFromProfileOnUiTest() {

        step("Авторизация через API", () -> AuthorizationWithApi.login());

        step("Очистить все книги из профиля через API", () -> BookStoreApi.deleteAllBooksFromProfile());

        randomIsbn = BookStoreApi.getRandomIsbn();

        step("Добавить случайную книгу в профиль через API", () -> {
            BookStoreApi.addBookToProfile(randomIsbn); // Используем уже инициализированную переменную
        });

        ProfilePage profilePage = new ProfilePage();

        step("Открыть страницу профиля", profilePage::openPge);

        step("Проверить отображение имени пользователя на UI", () -> profilePage.checkUserName(login));

        step("Проверить наличие книг в профиле на UI", () -> profilePage.checkBookInProfile(randomIsbn));

        step("Удалить книгу из профиля через UI", () -> profilePage.deleteBookInProfile(randomIsbn));

        step("Проверить отсутствие книг в профиле на UI", () -> profilePage.checkDeleteResultOnUi(randomIsbn));
    }
}