package api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.requestSpec;
import static specs.ApiSpecs.successDeleteAllBooksResponse204Spec;

public class BookStoreApi {

    @Tag("DemoQaAPI")
    @DisplayName("Очистить все книги в Profile через API")
    public static void deleteAllBooksFromProfile(){

        String token = AuthorizationWithApi.getToken();
        String userId = AuthorizationWithApi.getUserId();

        step("Отправить DELETE запрос на удаление всех книг из Profile", ()->
                given(requestSpec)
               .header("Authorization", "Bearer " + token)
               .queryParam("UserId", userId)
               .when()
               .delete("/BookStore/v1/Books")
               .then()
               .spec(successDeleteAllBooksResponse204Spec));
    }

}
