package tests.ui_tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.RegistrationPage;
import utils.RandomUtils;

public class WebSteps {

    RegistrationPage registrationPage = new RegistrationPage();
    RandomUtils randomUtils = new RandomUtils();

    String firstName = randomUtils.getRandomFirstName();
    String lastName = randomUtils.getRandomLastName();
    String userEmail = randomUtils.getRandomEmail();
    String gender = randomUtils.getRandomGender();
    String phone = randomUtils.getRandomPhone();
    String currentAddress = randomUtils.getRandomAddress();
    String dayOfBirth = randomUtils.getRandomDayOfBirth();
    String monthOfBirth = randomUtils.getRandomMonthOfBirth();
    String yearOfBirth = randomUtils.getRandomYearOfBirth();
    String subjectsInput = randomUtils.getRandomSubject();
    String hobbies = randomUtils.getRandomHobby();
    String foto = randomUtils.getRandomFoto();
    String state = randomUtils.getRandomState();
    String city = randomUtils.getRandomCity(state);

    @Step("Открыть страницу формы регистрации студента")
    public void openStudentRegistrationForm() {
        registrationPage.openPage();
    }

    @Step("Заполнить поле 'First Name' случайным значением {firstName}")
    public void setFirstName() {
        registrationPage.setFirstName(firstName);
    }

    @Step("Заполнить поле 'Last Name' случайным значением {lastName}")
    public void setLastName(){
        registrationPage.setLastName(lastName);
    }

    @Step("Заполнить поле 'Email' случайным значением {userEmail}")
    public void setUserEmail(){
        registrationPage.setUserEmail(userEmail);
    }

    @Step("Выбрать случайный пол {gender}")
    public void setGender(){
        registrationPage.setGender(gender);
    }

    @Step("Заполнить поле 'Mobile' случайным номером телефона {phone}")
    public void setUserNumber(){
        registrationPage.setUserNumber(phone);
    }

    @Step("Выбрать случайную дату рождения в поле 'Date of Birth' {dayOfBirth} {monthOfBirth} {yearOfBirth}")
    public void setDateOfBirth(){
        registrationPage.setDateOfBirth(dayOfBirth, monthOfBirth, yearOfBirth);
    }

    @Step("Выбрать случайный предмет {subjectsInput} в поле 'Subjects'")
    public void setSubjectsInput(){
        registrationPage.setSubjectsInput(subjectsInput);
    }

    @Step("Выбрать случайное хобби {hobbies} в поле 'Hobbies'")
    public void setHobbies(){
        registrationPage.setHobbies(hobbies);
    }

    @Step("Загрузить случайную картинку {foto} в поле 'Picture'")
    public void uploadPicture(){
        registrationPage.uploadPicture(foto);
    }

    @Step("Заполнить случайный адрес {currentAddress} в поле 'Current Address'")
    public void setCurrentAddress(){
        registrationPage.setCurrentAddress(currentAddress);
    }

    @Step ("Выбрать случайный штат {state} из списка 'Select State'")
    public void setState(){
        registrationPage.setState(state);
    }

    @Step ("Выбрать случайный город {city} из списка 'Select City'")
    public void setCity(){
        registrationPage.setCity(city);
    }

    @Step("Нажать кнопку 'Submit'")
    public void pressSubmitButton(){
        registrationPage.pressSubmitButton();
    }

    @Step ("Проверить, что поле 'Student Name' корректно заполнено значениями {firstName} и {lastName}")
    public void checkResultStudentName(){
        registrationPage.checkResult("Student Name", firstName + " " + lastName);
    }

    @Step("Проверить, что поле 'Student Email' корректно заполнено значением {userEmail}")
    public void checkResultStudentEmail(){
        registrationPage.checkResult("Student Email", userEmail);
    }

    @Step("Проверить, что поле 'Gender' корректно заполнено значением {gender}")
    public void checkResultGender(){
        registrationPage.checkResult("Gender", gender);
    }

    @Step("Проверить, что поле 'Mobile' корректно заполнено значением {phone}")
    public void checkResultMobile(){
        registrationPage.checkResult("Mobile", phone);
    }

    @Step("Проверить, что поле 'Date of Birth' корректно заполнено значениями {dayOfBirth} {monthOfBirth} {yearOfBirth}")
    public void checkResultDateOfBirth(){
        registrationPage.checkResult("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth);
    }

    @Step("Проверить, что поле 'Subjects' корректно заполнено значением {subjectsInput}")
    public void checkResultSubjects(){
        registrationPage.checkResult("Subjects", subjectsInput);
    }

    @Step("Проверить, что поле 'Hobbies' корректно заполнено значением {hobbies}")
    public void checkResultHobbies(){
        registrationPage.checkResult("Hobbies", hobbies);
    }

    @Step("Проверить, что поле 'Picture' корректно заполнено значением {foto}")
    public void checkResultPicture(){
        registrationPage.checkResult("Picture", foto);
    }

    @Step("Проверить, что поле 'State and City' корректно заполнено значениями {state} и {city}")
    public void checkResultStateAndCity(){
        registrationPage.checkResult("State and City", state + " " + city);
    }

    @Step("Проверить, что обязательные поля подсвечиваются, если они не заполнены")
    public void checkEmptyRequiredFields(){
        registrationPage.negativeCheck();
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
