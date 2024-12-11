package tests;

import api.BookStoreApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;


import static data.AuthData.USER_NAME;

public class ProfileTests extends TestBase {

    @Test
    @Tag("DemoQaAPI")
    @DisplayName("Полный сценарий: работа с книгами через API и UI")
    @WithLogin
    void deleteBookFromProfileOnUiTest() {

        BookStoreApi bookStoreApi = new BookStoreApi();
        bookStoreApi.deleteAllBooksFromProfile();

        String isbn = BookStoreApi.getRandomIsbn();

        ProfilePage profilePage = new ProfilePage();

        profilePage
                .openPage()
                .checkUserName(USER_NAME)
                .checkBookInProfile(true, isbn) // Проверяем, что книга есть
                .deleteBookInProfile(isbn)      // Удаляем книгу
                .checkBookInProfile(false, isbn); // Проверяем, что книги нет
    }

}
