package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.UserDataModel;
import models.UserProfileModel;
import utils.RandomUtils;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.successResponse200Spec;
import static specs.ApiSpecs.requestSpec;

public class AccountApi {

    @Step("Получить данные авторизации зарегистрированного пользователя через API")
    public static AuthResponseModel getAuthData(String userName, String userPassword) {

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(userName);
        request.setPassword(userPassword);

        return given()
                        .spec(requestSpec)
                        .body(request)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(successResponse200Spec)
                        .extract().as(AuthResponseModel.class);
    }

    @Step("Регистрация нового пользователя с сохранением данных в переменные")
    public static UserDataModel registerRandomUser() {
        RandomUtils randomUtils = new RandomUtils();

        String randomUserName = randomUtils.getRandomFirstName();
        String randomPassword = randomUtils.getRandomString(8);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(randomUserName);
        request.setPassword(randomPassword);

        given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(successResponse200Spec);

        UserDataModel userData = new UserDataModel();
        userData.setUserName(randomUserName);
        userData.setPassword(randomPassword);
        return userData;
    }


    @Step("Получить данные профиля пользователя")
    public static UserProfileModel getUserProfile(String token) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/Profile")
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);
    }

    @Step("Обновить данные профиля пользователя")
    public static UserProfileModel updateUserProfile(String token, UserProfileModel updatedProfile) {
        return given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(updatedProfile)
                .when()
                .put("/Account/v1/Profile")
                .then()
                .spec(successResponse200Spec)
                .extract().as(UserProfileModel.class);
    }

    @Step("Удалить пользователя")
    public static void deleteUser(String token) {
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/Delete")
                .then()
                .spec(successResponse200Spec);
    }
}