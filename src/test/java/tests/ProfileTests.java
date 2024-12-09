package tests;

import api.AuthorizationWithApi;
import api.BookStoreApi;
import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfilePage;


import static data.AuthData.USER_NAME;
import static io.qameta.allure.Allure.step;

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
                .openPge()
                .checkUserName(USER_NAME)
                .checkBookInProfile(isbn)
                .deleteBookInProfile(isbn)
                .checkDeleteResultOnUi(isbn);

    }
}
