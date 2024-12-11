package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class ProfilePage {

    private final SelenideElement
            bookSelector = $$(".rt-tr-group").first(),
            deleteBookSelector = $("#closeSmallModal-ok");

    @Step("Открыть страницу Profile")
    public ProfilePage openPage(){
        open("/profile");

        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");
        Selenide.executeJavaScript("document.querySelectorAll('.advertisement-class').forEach(el => el.style.display = 'none');");

        return this;
    }

    @Step("Проверить корректное отображение username в Profile")
    public ProfilePage checkUserName(){
        String login = System.getProperty("profileUserName", "defaultLogin");
        //$("#userName-value").shouldHave(text(System.getProperty("login")));
        $("#userName-value").shouldHave(text(login));
        return this;
    }

    @Step("Проверить наличие книги в Profile")
    public ProfilePage checkBookInProfile(boolean shouldExist, String isbn) {
        boolean isPresent = bookSelector.$("a[href='/profile?book=" + isbn + "']").exists();

        if (shouldExist && !isPresent) {
            throw new AssertionError("Книга с ISBN " + isbn + " отсутствует в профиле.");
        } else if (!shouldExist && isPresent) {
            throw new AssertionError("Книга с ISBN " + isbn + " все еще присутствует в профиле.");
        }

        return this;
    }

    @Step("Удалить добавленную книгу через UI")
    public ProfilePage deleteBookInProfile(String isbn) {
        if (!bookSelector.$("a[href='/profile?book=" + isbn + "']").exists()) {
            throw new AssertionError("Книга с ISBN " + isbn + " отсутствует, удаление невозможно.");
        }
        bookSelector.$("#delete-record-undefined").click();
        deleteBookSelector.click();

        return this;
    }
}