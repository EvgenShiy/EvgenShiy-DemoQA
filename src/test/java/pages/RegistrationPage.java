package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;
import pages.components.DropdownComponent;
import pages.components.ModalComponent;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    private final SelenideElement firstNameInput = $("#firstName"),
                            lastNameInput = $("#lastName"),
                            userEmailInput =  $("#userEmail"),
                            genderWrapper = $("#genterWrapper"),
                            userNumber = $("#userNumber"),
                            calendarInput = $("#dateOfBirthInput"),
                            subjectsInput = $("#subjectsInput"),
                            uploadPicture = $("#uploadPicture"),
                            hobbiesWrapper = $("#hobbiesWrapper"),
                            currentAddress = $("#currentAddress"),
                            submitButton =  $("button#submit");




    CalendarComponent calendarComponent = new CalendarComponent();
    ModalComponent modalComponent = new ModalComponent();
    DropdownComponent dropdownComponent = new DropdownComponent();

    public RegistrationPage openPage() {
        open("/automation-practice-form");
        executeJavaScript("$('#fixedban').remove();");
        executeJavaScript("$('footer').remove();");

        return this;
    }

    public RegistrationPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    public RegistrationPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    public RegistrationPage setUserEmail(String value) {
        userEmailInput.setValue(value);
        return this;
    }

    public RegistrationPage setGender(String value) {
        genderWrapper.$(byText(value)).click();
        return this;
    }

    public RegistrationPage setUserNumber(String value) {
        userNumber.setValue(value);
        return this;
    }

    public RegistrationPage setDateOfBirth(String day, String month, String year) {
        calendarInput.click();
        calendarComponent.setDate(day, month, year);
        return this;
    }

    public RegistrationPage setSubjectsInput(String value) {
        subjectsInput.setValue(value).pressTab();
        return this;
    }

    public RegistrationPage setHobbies(String value) {
        hobbiesWrapper.$(byText(value)).click();
        return this;
    }

    public RegistrationPage setCurrentAddress(String value) {
        currentAddress.setValue(value);
        return this;
    }

    public RegistrationPage setState(String value) {
        dropdownComponent.setState(value);
        return this;
    }

    public RegistrationPage setCity(String value) {
        dropdownComponent.setCity(value);
        return this;
    }

    public RegistrationPage uploadPicture(String value) {
        uploadPicture.uploadFromClasspath(value);
        return this;
    }

    public void pressSubmitButton() {
        submitButton.click();
    }

    public RegistrationPage checkResult(String key, String value) {
        modalComponent.checkResult(key, value);
        return this;
    }

    public void negativeCheck() {
        new ModalComponent().negativeCheck();
    }

}