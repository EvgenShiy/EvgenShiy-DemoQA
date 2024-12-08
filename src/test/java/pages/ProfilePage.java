package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfilePage {

    private final SelenideElement
            bookSelector = $$(".rt-tr-group").first(),
            deleteBookSelector = $("#closeSmallModal-ok");

    @Step("Открыть страницу Profile")
    public ProfilePage openPge(){
        open("/profile");

        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");
        Selenide.executeJavaScript("document.querySelectorAll('.advertisement-class').forEach(el => el.style.display = 'none');");

        return this;
    }

    @Step("Проверить корректное отображение User Name в Profile")
    public ProfilePage checkUserName(String login){
        $("#userName-value").shouldHave(text(login));
        return this;
    }

    @Step("Проверить наличие добавленной книги в Profile")
    public void checkBookInProfile(String isbn) {
        bookSelector.$("a[href='/profile?book=" + isbn + "']").shouldBe(exist);
    }

    @Step("Удалить добавленную книгу через UI")
    public ProfilePage deleteBookInProfile(String isbn){
        bookSelector.$("#delete-record-undefined").click();
        deleteBookSelector.click();

        return this;
    }

    @Step("Проверить, что книга удалилась")
    public ProfilePage checkDeleteResultOnUi(String isbn) {

        bookSelector.$("a[href='/profile?book=" + isbn + "']").shouldNot(exist);

        return this;
    }
}
