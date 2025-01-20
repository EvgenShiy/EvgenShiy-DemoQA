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

    private static final Logger logger = LoggerFactory.getLogger(AccountApi.class);

    @Step("Получить данные авторизации зарегистрированного пользователя через API")
    public static AuthResponseModel getAuthData(String userName, String userPassword) {
        logger.info("Отправка запроса на авторизацию для пользователя: {}", userName);

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

        logger.info("Ответ на запрос авторизации получен. Токен: {}", response.getToken());
        return response;
    }

    @Step("Генерация токена для авторизации через API")
    public static AuthResponseModel generateAuthToken(String userName, String userPassword) {
        logger.info("Генерация токена для пользователя: {}", userName);

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

        logger.info("Токен успешно сгенерирован: {}", response.getToken());
        return response;
    }

    public static AuthRequestModel generateRandomUserData() {
        logger.info("Генерация данных для нового рандомного пользователя...");

        RandomUtils randomUtils = new RandomUtils();

        String randomUserName = randomUtils.getRandomFirstName();
        String randomPassword = randomUtils.generateStrongPassword(12);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(randomUserName);
        request.setPassword(randomPassword);

        logger.info("Сгенерированы данные: UserName = {}, Password = {}", randomUserName, randomPassword);
        return request;
    }


    @Step("Регистрация нового пользователя")
    public static AuthResponseModel registerUser(String userName, String password) {
        logger.info("Регистрация нового пользователя с данными: UserName = {}", userName);

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

        logger.info("Пользователь {} успешно зарегистрирован. UserId: {}", userName, response.getUserId());
        return response;
    }

    @Step("Попытка регистрация нового пользователя с невалидным паролем")
    public static ErrorResponseModel registerUserWithError(String userName, String password) {
        logger.info("Попытка регистрация нового пользователя с данными: UserName = {}", userName);

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

        logger.info("Пользователь с данными: UserName = {} не зарегистрирован", userName);
        return response;
    }

    @Step("Получить данные профиля пользователя")
    public static UserProfileModel getUserProfile(String token, String userId) {
        logger.info("Запрос профиля пользователя с токеном: {}", token);

        UserProfileModel response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);

        logger.info("Профиль пользователя получен: {}", response);
        return response;
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String token, String userId) {
        logger.info("Удаление пользователя с ID: {}", userId);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/User/" + userId)
                .then()
                .spec(successResponse204Spec)
                .log().ifError();

        logger.info("Пользователь с ID {} успешно удален.", userId);
    }

    @Step("Проверить, авторизован ли пользователь через API")
    public static boolean isUserAuthorized(String token) {
        logger.info("Проверка авторизации пользователя с токеном: {}", token);

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