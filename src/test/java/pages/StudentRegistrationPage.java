package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.components.CalendarComponent;
import pages.components.DropdownComponent;
import pages.components.ModalComponent;

import java.time.Duration;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class StudentRegistrationPage {

    private final SelenideElement firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            genderWrapper = $("#genterWrapper"),
            userNumber = $("#userNumber"),
            calendarInput = $("#dateOfBirthInput"),
            subjectsInput = $("#subjectsInput"),
            uploadPicture = $("#uploadPicture"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            currentAddress = $("#currentAddress"),
            submitButton = $("button#submit");

    CalendarComponent calendarComponent = new CalendarComponent();
    ModalComponent modalComponent = new ModalComponent();
    DropdownComponent dropdownComponent = new DropdownComponent();

    @Step("Открыть страницу регистрации")
    public StudentRegistrationPage openPage() {
        open("/automation-practice-form");
        return this;
    }

    @Step("Удалить баннеры на странице регистрации")
    public StudentRegistrationPage removeBanners() {
        sleep(2000);
        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");
        executeJavaScript("$('iframe').remove();");
        return this;
    }

    @Step("Дождаться исчезновения рекламы")
    public StudentRegistrationPage waitForAdToDisappear() {
        $("#fixedban").should(disappear, Duration.ofSeconds(5));
        $("footer").should(disappear, Duration.ofSeconds(5));
        return this;
    }

    @Step("Открыть страницу регистрации и удалить баннеры")
    public StudentRegistrationPage openPageRemoveBanners() {
        openPage();
        removeBanners();
        waitForAdToDisappear();
        return this;
    }

    public StudentRegistrationPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    public StudentRegistrationPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    public StudentRegistrationPage setUserEmail(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    public StudentRegistrationPage setGender(String value) {
        genderWrapper.$(byText(value)).click();
        return this;
    }

    public StudentRegistrationPage setUserNumber(String value) {
        userNumber.setValue(value);
        return this;
    }

    public StudentRegistrationPage setDateOfBirth(String day, String month, String year) {
        calendarInput.click();
        calendarComponent.setDate(day, month, year);
        return this;
    }

    public StudentRegistrationPage setSubjectsInput(String value) {
        subjectsInput.setValue(value).pressTab();
        return this;
    }

    public StudentRegistrationPage setHobbies(String value) {
        hobbiesWrapper.$(byText(value)).click();
        return this;
    }

    public StudentRegistrationPage setCurrentAddress(String value) {
        currentAddress.setValue(value);
        return this;
    }

    public StudentRegistrationPage setState(String value) {
        dropdownComponent.setState(value);
        return this;
    }

    public StudentRegistrationPage setCity(String value) {
        dropdownComponent.setCity(value);
        return this;
    }

    public StudentRegistrationPage uploadPicture(String value) {
        uploadPicture.uploadFromClasspath(value);
        return this;
    }

    public void pressSubmitButton() {
        submitButton.click();
    }

    public StudentRegistrationPage checkResult(String key, String value) {
        modalComponent.checkResult(key, value);
        return this;
    }

    public void negativeCheck() {
        new ModalComponent().negativeCheck();
    }
}