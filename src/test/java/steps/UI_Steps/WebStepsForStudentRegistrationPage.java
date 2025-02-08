package steps.UI_Steps;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.StudentRegistrationPage;
import utils.StudentData;

public class WebStepsForStudentRegistrationPage {

    StudentRegistrationPage studentRegistrationPage = new StudentRegistrationPage();
    StudentData studentData = StudentData.generate();

    @Step("Открыть страницу формы регистрации студента")
    public void openStudentRegistrationForm() {
        studentRegistrationPage.openPage();
    }

    @Step("Заполнить поле 'First Name' случайным значением")
    public void setFirstName() {
        studentRegistrationPage.setFirstName(studentData.firstName);
    }

    @Step("Заполнить поле 'Last Name' случайным значением")
    public void setLastName() {
        studentRegistrationPage.setLastName(studentData.lastName);
    }

    @Step("Заполнить поле 'Email' случайным значением")
    public void setUserEmail() {
        studentRegistrationPage.setUserEmail(studentData.email);
    }

    @Step("Выбрать случайный пол")
    public void setGender() {
        studentRegistrationPage.setGender(studentData.gender);
    }

    @Step("Заполнить поле 'Mobile' случайным номером телефона")
    public void setUserNumber() {
        studentRegistrationPage.setUserNumber(studentData.phone);
    }

    @Step("Выбрать случайную дату рождения в поле 'Date of Birth'")
    public void setDateOfBirth() {
        studentRegistrationPage.setDateOfBirth(studentData.dayOfBirth, studentData.monthOfBirth, studentData.yearOfBirth);
    }

    @Step("Выбрать случайный предмет в поле 'Subjects'")
    public void setSubjectsInput() {
        studentRegistrationPage.setSubjectsInput(studentData.subject);
    }

    @Step("Выбрать случайное хобби в поле 'Hobbies'")
    public void setHobbies() {
        studentRegistrationPage.setHobbies(studentData.hobby);
    }

    @Step("Загрузить случайную картинку в поле 'Picture'")
    public void uploadPicture() {
        studentRegistrationPage.uploadPicture(studentData.photo);
    }

    @Step("Заполнить случайный адрес в поле 'Current Address'")
    public void setCurrentAddress() {
        studentRegistrationPage.setCurrentAddress(studentData.address);
    }

    @Step("Выбрать случайный штат из списка 'Select State'")
    public void setState() {
        studentRegistrationPage.setState(studentData.state);
    }

    @Step("Выбрать случайный город из списка 'Select City'")
    public void setCity() {
        studentRegistrationPage.setCity(studentData.city);
    }

    @Step("Нажать кнопку 'Submit'")
    public void pressSubmitButton() {
        studentRegistrationPage.pressSubmitButton();
    }

    @Step("Проверить, что поле 'Student Name' заполнено корректно")
    public void checkResultStudentName() {
        studentRegistrationPage.checkResult("Student Name", studentData.firstName + " " + studentData.lastName);
    }

    @Step("Проверить, что поле 'Student Email' заполнено корректно")
    public void checkResultStudentEmail() {
        studentRegistrationPage.checkResult("Student Email", studentData.email);
    }

    @Step("Проверить, что поле 'Gender' заполнено корректно")
    public void checkResultGender() {
        studentRegistrationPage.checkResult("Gender", studentData.gender);
    }

    @Step("Проверить, что поле 'Mobile' заполнено корректно")
    public void checkResultMobile() {
        studentRegistrationPage.checkResult("Mobile", studentData.phone);
    }

    @Step("Проверить, что поле 'Date of Birth' заполнено корректно")
    public void checkResultDateOfBirth() {
        studentRegistrationPage.checkResult("Date of Birth", studentData.dayOfBirth + " " + studentData.monthOfBirth + "," + studentData.yearOfBirth);
    }

    @Step("Проверить, что поле 'Subjects' заполнено корректно")
    public void checkResultSubjects() {
        studentRegistrationPage.checkResult("Subjects", studentData.subject);
    }

    @Step("Проверить, что поле 'Hobbies' заполнено корректно")
    public void checkResultHobbies() {
        studentRegistrationPage.checkResult("Hobbies", studentData.hobby);
    }

    @Step("Проверить, что поле 'Picture' заполнено корректно")
    public void checkResultPicture() {
        studentRegistrationPage.checkResult("Picture", studentData.photo);
    }

    @Step("Проверить, что поле 'State and City' заполнено корректно")
    public void checkResultStateAndCity() {
        studentRegistrationPage.checkResult("State and City", studentData.state + " " + studentData.city);
    }

    @Step("Проверить, что обязательные поля подсвечиваются, если они не заполнены")
    public void checkEmptyRequiredFields() {
        studentRegistrationPage.negativeCheck();
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
