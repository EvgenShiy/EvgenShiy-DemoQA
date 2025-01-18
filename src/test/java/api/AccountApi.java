package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;
import models.UserDataModel;
import models.UserProfileModel;
import utils.RandomUtils;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

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

    @Step("Регистрация нового рандомного пользователя с проверкой данных")
    public static AuthResponseModel registerRandomUser() {
        RandomUtils randomUtils = new RandomUtils();

        String randomUserName = randomUtils.getRandomFirstName();
        String randomPassword = randomUtils.generateStrongPassword(randomUserName);

        AuthRequestModel request = new AuthRequestModel();
        request.setUserName(randomUserName);
        request.setPassword(randomPassword);

        return given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/User")
                .then()
                .spec(successResponse201Spec)
                .extract().as(AuthResponseModel.class);
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
                .spec(successResponse200Spec)
                .log().ifError();
    }

}