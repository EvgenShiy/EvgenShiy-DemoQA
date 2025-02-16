package tests.ui_tests;

import api.BookStoreApi;
import helpers.extensions.WithLogin;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;

import static helpers.extensions.LoginExtension.*;

@Tag("UI")
@Feature("Bookstore")
@Owner("Shiianova E.")
public class ProfileUITests extends UI_TestBase {

    @Test
    @DisplayName("Удаление книги из Profile на UI")
    @WithLogin
    void deleteBookFromProfileOnUiTest() {

        BookStoreApi bookStoreApi = new BookStoreApi();
        bookStoreApi.deleteAllBooksFromProfile(getToken(), getUserId());

        String isbn = BookStoreApi.getRandomIsbn();

        bookStoreApi.addBookToProfile(isbn, getToken(), getUserId());

        ProfilePage profilePage = new ProfilePage();
        profilePage
                .openPage()
                .checkUserName()
                .checkBookInProfile(true, isbn)
                .deleteBookInProfile(isbn)
                .checkBookInProfile(false, isbn);
    }
}