package tests.ui_tests;

import api.BookStoreApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;
import tests.TestBase;

public class ProfileTests extends UiTestBase {

    @Test
    @Tag("UI")
    @DisplayName("Удаление книги из Profile на UI")
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