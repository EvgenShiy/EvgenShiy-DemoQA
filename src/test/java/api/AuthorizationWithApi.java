package api;

import io.qameta.allure.Step;
import models.AuthRequestModel;
import models.AuthResponseModel;


import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.successResponse200Spec;
import static specs.ApiSpecs.requestSpec;

public class AuthorizationWithApi {

    @Step("Получить данные авторизации пользователя")
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
}
