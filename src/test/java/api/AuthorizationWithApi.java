package api;

import data.AuthData;
import io.restassured.response.Response;
import models.AuthRequestModel;
import models.AuthResponseModel;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.successResponse200Spec;
import static specs.ApiSpecs.requestSpec;
import static io.qameta.allure.Allure.step;

public class AuthorizationWithApi {


    //@DisplayName("Авторизация пользователя через API")
    public static AuthResponseModel login() {
        AuthRequestModel authRequest = new AuthRequestModel(AuthData.login, AuthData.password);

        Response response = step("Отправить POST запрос на авторизацию пользователя", () ->
                given()
                        .spec(requestSpec)
                        .body(authRequest)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(successResponse200Spec)
                        .extract().response());

        return response.as(AuthResponseModel.class);
    }


    //@DisplayName("Получение токена")
    public static String getToken() {
        AuthResponseModel authResponse = login();
        return authResponse.getToken();
    }


    //@DisplayName("Получение userId")
    public static String getUserId() {
        AuthResponseModel authResponse = login();
        return authResponse.getUserId();
    }
}
