package api;

import models.AddBookToProfileRequestModel;
import models.BookModel;
import models.GetListOfBooksModel;
import models.IsbnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.List;
import java.util.Random;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

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
               .spec(successResponse204Spec));
    }


    @Tag("DemoQaAPI")
    @DisplayName("Получить список всех книг из Book Store через API")
    public static List<BookModel> getBooks() {
        GetListOfBooksModel response = (GetListOfBooksModel) given(requestSpec)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(successResponse200Spec)
                .extract()
                .as(GetListOfBooksModel.class);

        return response.getBooks();
    }

    @Tag("DemoQaAPI")
    @DisplayName("Получить случайную книгу из Book Store через API")
    public static String getRandomIsbn() {
        List<BookModel> books = getBooks();
        Random random = new Random();
        return books.get(random.nextInt(books.size())).getIsbn();
    }


    @Tag("DemoQaAPI")
    @DisplayName("Добавить рандомную книгу в Profile через API")
    public static void addBookToProfile(String isbn){

        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn(isbn);

        AddBookToProfileRequestModel request = new AddBookToProfileRequestModel(AuthorizationWithApi.getUserId(), List.of(isbnModel));

        step("Добавить книгу в Profile", () ->
                given(requestSpec)
                        .header("Authorization", "Bearer " + AuthorizationWithApi.getToken())
                        .body(request)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .spec(successResponse201Spec));
    }
}
