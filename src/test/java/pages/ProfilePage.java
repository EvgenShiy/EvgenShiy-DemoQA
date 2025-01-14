package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class ProfilePage {

    private final SelenideElement userNameValue = $("#userName-value");
    private final SelenideElement closeSmallModalOkButton = $("#closeSmallModal-ok");

    @Step("Открыть страницу Profile")
    public ProfilePage openPage(){
        open("/profile");
        return this;
    }

    @Step("Проверить корректное отображение username в Profile")
    public ProfilePage checkUserName(){
        String login = System.getProperty("profileUserName", "defaultLogin");
        userNameValue.shouldHave(text(login));
        return this;
    }

    private SelenideElement findBookByIsbn(String isbn) {
        return $(".rt-tr-group").find("a[href='/profile?book=" + isbn + "']");
    }

    @Step("Проверить наличие книги в Profile")
    public ProfilePage checkBookInProfile(boolean shouldExist, String isbn) {
        SelenideElement book = findBookByIsbn(isbn);

        if (shouldExist) {
            book.should(exist);
        } else {
            book.shouldNot(exist);
        }

        return this;
    }

    @Step("Удалить добавленную книгу через UI")
    public ProfilePage deleteBookInProfile(String isbn) {
        SelenideElement book = findBookByIsbn(isbn);

        if (!book.exists()) {
            throw new AssertionError("Книга с ISBN " + isbn + " отсутствует, удаление невозможно.");
        }

        executeJavaScript("document.querySelectorAll('iframe').forEach(iframe => iframe.remove());");

        book.closest(".rt-tr-group").find("#delete-record-undefined").shouldBe(visible).click();

        closeSmallModalOkButton.shouldBe(visible, enabled).click();

        book.shouldNot(exist);
        return this;
    }
}