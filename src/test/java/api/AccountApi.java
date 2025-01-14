package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;
import utils.RandomUtils;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.successResponse200Spec;
import static specs.ApiSpecs.requestSpec;

public class AccountApi {

    @Step("Получить данные авторизации зарегистрированного пользователя")
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

    @Step("Успешная регистрация нового пользователя")
    public static AuthResponseModel registerNewUser(String userName, String userPassword) {

        AuthRequestModel request = new AuthRequestModel();
        RandomUtils randomUtils = new RandomUtils();

        String randomUserName = randomUtils.getRandomFirstName();
        String randomPassword = randomUtils.getRandomString(8);

        request.setUserName(randomUserName);
        request.setPassword(randomPassword);

        return given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(successResponse200Spec)
                .extract().as(AuthResponseModel.class);

    }
}