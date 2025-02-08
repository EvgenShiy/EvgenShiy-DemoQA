package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementClickInterceptedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.as;

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

    private void removeIframes() {
        log.info("Удаление всех iframe с страницы.");
        executeJavaScript("document.querySelectorAll('iframe').forEach(iframe => iframe.remove());");
    }

    private SelenideElement getBookRow(String isbn) {
        log.info("Получение строки книги с ISBN: {}", isbn);
        return $(".rt-tr-group").find("a[href='/profile?book=" + isbn + "']").closest(".rt-tr-group");
    }

    @Step("Проверить наличие книги в Profile")
    public ProfilePage checkBookInProfile(boolean shouldExist, String isbn) {
        log.info("Проверка наличия книги с ISBN: {}, shouldExist: {}", isbn, shouldExist);
        SelenideElement book = getBookRow(isbn);

        assertThat(book.exists())
                .as("Ожидаемое наличие книги с ISBN %s", isbn)
                .isEqualTo(shouldExist);

        return this;
    }

    @Step("Удалить добавленную книгу через UI")
    public ProfilePage deleteBookInProfile(String isbn) {
        log.info("Попытка удаления книги с ISBN: {}", isbn);

        removeIframes(); // Используем уже существующий метод

        SelenideElement bookRow = getBookRow(isbn).shouldBe(visible, Duration.ofSeconds(10));

        SelenideElement deleteButton = bookRow.find("#delete-record-undefined")
                .shouldBe(visible.because("Кнопка удаления должна быть видимой"))
                .scrollIntoView(true);

        assertThat(deleteButton.is(not(visible.because("Кнопка перекрыта другим элементом"))))
            .as("Кнопка удаления не должна быть перекрыта")
                .isFalse();

        deleteButton.click();

        closeSmallModalOkButton.shouldBe(visible, enabled).click();

        bookRow.shouldNot(exist.because("Книга с ISBN " + isbn + " не была удалена."));

        return this;
    }


}
