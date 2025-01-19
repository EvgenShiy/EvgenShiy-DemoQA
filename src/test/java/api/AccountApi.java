package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.AuthResponseModelWithOptionalUserId;
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


    @Step("Регистрация нового рандомного пользователя с проверкой данных")
    public static AuthResponseModelWithOptionalUserId registerRandomUser() {
        logger.info("Регистрация нового рандомного пользователя...");

        AuthRequestModel request = generateRandomUserData();

        AuthResponseModelWithOptionalUserId response = given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(successResponse201Spec)
                .extract().as(AuthResponseModelWithOptionalUserId.class);

        logger.info("Пользователь {} успешно зарегистрирован. UserId: {}", request.getUserName(), response.getUserId());
        return response;
    }

    @Step("Получить данные профиля пользователя")
    public static UserProfileModel getUserProfile(String token) {
        logger.info("Запрос профиля пользователя с токеном: {}", token);

        UserProfileModel response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/Profile")
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);

        logger.info("Профиль пользователя получен: {}", response);
        return response;
    }

    @Step("Обновить данные профиля пользователя")
    public static UserProfileModel updateUserProfile(String token, UserProfileModel updatedProfile) {
        logger.info("Обновление данных профиля пользователя с токеном: {}", token);

        UserProfileModel response = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(updatedProfile)
                .when()
                .put("/Account/v1/Profile")
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);

        logger.info("Данные профиля обновлены: {}", response);
        return response;
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String token) {
        logger.info("Удаление пользователя с токеном: {}", token);

        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/Delete")
                .then()
                .spec(successResponse200Spec)
                .log().ifError();

        logger.info("Пользователь с токеном {} успешно удален.", token);
    }
}