package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProfilePage {

    private static final Logger log = LoggerFactory.getLogger(ProfilePage.class);

    private final SelenideElement userNameValue = $("#userName-value");
    private final SelenideElement closeSmallModalOkButton = $("#closeSmallModal-ok");

    @Step("Открыть страницу Profile")
    public ProfilePage openPage() {
        log.info("Открытие страницы Profile");
        open("/profile");
        return this;
    }

    @Step("Проверить корректное отображение username в Profile")
    public ProfilePage checkUserName() {
        String login = System.getProperty("profileUserName", "defaultLogin");
        log.info("Проверка отображения username: {}", login);
        userNameValue.shouldHave(text(login));
        return this;
    }

    private SelenideElement findBookByIsbn(String isbn) {
        log.info("Поиск книги с ISBN: {}", isbn);
        return $(".rt-tr-group").find("a[href='/profile?book=" + isbn + "']");
    }

    @Step("Проверить наличие книги в Profile")
    public ProfilePage checkBookInProfile(boolean shouldExist, String isbn) {
        log.info("Проверка наличия книги с ISBN: {}, shouldExist: {}", isbn, shouldExist);
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
        log.info("Попытка удаления книги с ISBN: {}", isbn);

        SelenideElement book = findBookByIsbn(isbn);

        book.shouldBe(visible.because("Книга с ISBN " + isbn + " отсутствует, удаление невозможно."), Duration.ofSeconds(10));

        if ($$("iframe").size() > 0) {
            log.info("Удаление iframe для обеспечения доступности элемента.");
            executeJavaScript("document.querySelectorAll('iframe').forEach(iframe => iframe.remove());");
        }

        log.info("Клик по кнопке удаления книги.");
        book.closest(".rt-tr-group").shouldBe(visible).find("#delete-record-undefined").shouldBe(visible).click();

        log.info("Подтверждение удаления.");
        closeSmallModalOkButton.shouldBe(visible, enabled).click();

        log.info("Проверка, что книга удалена.");
        book.closest(".rt-tr-group")
                .shouldNot(exist.because("Книга с ISBN " + isbn + " не была удалена."));

        return this;
    }
}