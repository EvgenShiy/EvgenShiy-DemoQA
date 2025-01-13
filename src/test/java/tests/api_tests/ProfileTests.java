package tests.api_tests;

import api.BookStoreApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.TestBase;

public class ProfileTests extends TestBase {

    @Test
    @Tag("API")
    @DisplayName("Полный сценарий: работа с книгами через API и UI")
    @WithLogin
    void deleteBookFromProfileOnUiTest() {

        BookStoreApi bookStoreApi = new BookStoreApi();
        bookStoreApi.deleteAllBooksFromProfile();

        String isbn = BookStoreApi.getRandomIsbn();

        bookStoreApi.addBookToProfile(isbn);

        ProfilePage profilePage = new ProfilePage();
        profilePage
                .openPage()
                .checkUserName()
                .checkBookInProfile(true, isbn)
                .deleteBookInProfile(isbn)
                .checkBookInProfile(false, isbn);
    }
}