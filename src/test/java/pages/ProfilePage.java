package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private final SelenideElement
            firstBookSelector = $$(".rt-tr-group").first(),
            deleteFirstBookSelector = $("#closeSmallModal-ok");

    @Step("Открыть страницу Profile")
    public ProfilePage openPge(){
        open("/profile");
        return this;
    }

    @Step("Проверить корректное отображение User Name в Profile")
    public ProfilePage checkUserName(String login){
        $("#userName-value").shouldHave(text(login));
        return this;
    }

    @Step("Проверить наличие добавленной книги в Profile")
    public ProfilePage checkBookInProfile(String isbn){
        firstBookSelector.$("a[href='/profile?book=" + isbn + "']").shouldBe(exist);

        return this;
    }

    @Step("Удалить добавленную книгу через UI")
    public ProfilePage deleteBookInProfile(String isbn){
        firstBookSelector.$("#delete-record-undefined").click();
        deleteFirstBookSelector.click();

        return this;
    }

    @Step("Проверить, что книга удалилась")
    public ProfilePage checkDeleteResultOnUi(String isbn) {

        firstBookSelector.$("a[href='/profile?book=" + isbn + "']").shouldNot(exist);

        return this;
    }
}
