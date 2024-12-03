package api;

import data.AuthData;
import io.restassured.response.Response;
import models.AuthRequestModel;
import models.AuthResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.loginResponse200Spec;
import static specs.ApiSpecs.requestSpec;
import static io.qameta.allure.Allure.step;

public class AuthorizationWithApi {

    @Tag("DemoQaAPI")
    @DisplayName("Авторизация пользователя через API")
    public static AuthResponseModel login() {
        AuthRequestModel authRequest = new AuthRequestModel(AuthData.login, AuthData.password);

        Response response = step("Отправить POST запрос на авторизацию пользователя", () ->
                given()
                        .spec(requestSpec)
                        .body(authRequest)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(loginResponse200Spec)
                        .extract().response());

        return response.as(AuthResponseModel.class);
    }

    @Tag("DemoQaAPI")
    @DisplayName("Получение токена")
    public static String getToken() {
        AuthResponseModel authResponse = login();
        return authResponse.getToken();
    }
}
