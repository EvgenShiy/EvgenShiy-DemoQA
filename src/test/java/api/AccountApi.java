package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.ErrorResponseModel;
import models.UserProfileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RandomUtils;

import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApi.class);

    @Step("Получить данные авторизации зарегистрированного пользователя через API")
    public static AuthResponseModel getAuthData(String userName, String userPassword) {
        log.info("Отправка запроса на авторизацию для пользователя: {}", userName);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(userName);
        request.setPassword(userPassword);

        AuthResponseModel response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(successResponse200Spec)
                .extract().as(AuthResponseModel.class);

        log.info("Ответ на запрос авторизации получен. Токен: {}", response.getToken());
        return response;
    }

    @Step("Генерация токена для авторизации через API")
    public static AuthResponseModel generateAuthToken(String userName, String userPassword) {
        log.info("Генерация токена для пользователя: {}", userName);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(userName);
        request.setPassword(userPassword);

        AuthResponseModel response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(successResponse200Spec)
                .extract().as(AuthResponseModel.class);

        log.info("Токен успешно сгенерирован: {}", response.getToken());
        return response;
    }

    public static AuthRequestModel generateRandomUserData() {
        log.info("Генерация данных для нового рандомного пользователя...");

        RandomUtils randomUtils = new RandomUtils();

        String randomUserName = randomUtils.getRandomFirstName();
        String randomPassword = randomUtils.generateStrongPassword(12);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(randomUserName);
        request.setPassword(randomPassword);

        log.info("Сгенерированы данные: UserName = {}, Password = {}", randomUserName, randomPassword);
        return request;
    }


    @Step("Регистрация нового пользователя")
    public static AuthResponseModel registerUser(String userName, String password) {
        log.info("Регистрация нового пользователя с данными: UserName = {}", userName);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(userName);
        request.setPassword(password);

        AuthResponseModel response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(successResponse201Spec)
                .extract().as(AuthResponseModel.class);

        log.info("Пользователь {} успешно зарегистрирован. UserId: {}", userName, response.getUserId());
        return response;
    }

    @Step("Попытка регистрация нового пользователя с невалидным паролем")
    public static ErrorResponseModel registerUserWithError(String userName, String password) {
        log.info("Попытка регистрация нового пользователя с данными: UserName = {}", userName);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(userName);
        request.setPassword(password);

        ErrorResponseModel  response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(errorResponse400Spec)
                .extract().as(ErrorResponseModel .class);

        log.info("Пользователь с данными: UserName = {} не зарегистрирован", userName);
        return response;
    }

    @Step("Получить данные профиля пользователя")
    public static UserProfileModel getUserProfile(String token, String userId) {
        log.info("Запрос профиля пользователя с токеном: {}", token);

        UserProfileModel response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);

        log.info("Профиль пользователя получен: {}", response);
        return response;
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String token, String userId) {
        log.info("Удаление пользователя с ID: {}", userId);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/User/" + userId)
                .then()
                .spec(successResponse204Spec)
                .log().ifError();

        log.info("Пользователь с ID {} успешно удален.", userId);
    }

    @Step("Проверить, авторизован ли пользователь через API")
    public static boolean isUserAuthorized(String token) {
        log.info("Проверка авторизации пользователя с токеном: {}", token);

        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/Authorized")
                .then()
                .spec(successResponse200Spec)
                .extract()
                .body()
                .as(Boolean.class);
    }

}