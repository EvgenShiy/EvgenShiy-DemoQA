package tests.ui_tests;

import helpers.Attach;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class RegistrationFormTests extends TestBase {


    @Test
    @Tag("demoqa")
    @Owner("shiianovaen")
    @DisplayName("Проверка заполнения всех полей формы регистрации")
    public void successRegistrationWithFullValueTest() {

        WebSteps steps = new WebSteps();

        steps.openStudentRegistrationForm();
        steps.setFirstName();
        steps.setLastName();
        steps.setUserEmail();
        steps.setGender();
        steps.setUserNumber();
        steps.setDateOfBirth();
        steps.setSubjectsInput();
        steps.setHobbies();
        steps.uploadPicture();
        steps.setCurrentAddress();
        steps.setState();
        steps.setCity();
        steps.pressSubmitButton();

        steps.checkResultStudentName();
        steps.checkResultStudentEmail();
        steps.checkResultGender();
        steps.checkResultMobile();
        steps.checkResultDateOfBirth();
        steps.checkResultSubjects();
        steps.checkResultHobbies();
        steps.checkResultPicture();
        steps.checkResultStateAndCity();

        Attach.addVideo();

    }

    @Test
    @Tag("demoqa")
    @Owner("shiianovaen")
    @DisplayName("Проверка заполнения только обязательных полей формы регистрации")
    public void successRegistrationWithMinimumValueTest() {

        WebSteps steps = new WebSteps();

        steps.openStudentRegistrationForm();
        steps.setFirstName();
        steps.setLastName();
        steps.setGender();
        steps.setUserNumber();
        steps.pressSubmitButton();

        steps.checkResultStudentName();
        steps.checkResultGender();
        steps.checkResultMobile();
    }

    @Test
    @Tag("demoqa")
    @Owner("shiianovaen")
    @DisplayName("Проверка подсветки обязательных полей формы регистрации, если они не заполнены")
    public void requiredFieldsHighlightOnSubmitTest() {

        WebSteps steps = new WebSteps();

        steps.openStudentRegistrationForm();
        steps.pressSubmitButton();

        steps.checkEmptyRequiredFields();
    }
}