package api;

import models.BookModel;
import models.GetListOfBooksModel;
import models.IsbnModel;
import org.junit.jupiter.api.DisplayName;
import models.AddBookToProfileRequestModel;

import java.util.List;
import java.util.Random;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class BookStoreApi {

    //@DisplayName("Очистить все книги в Profile через API")
    public static void deleteAllBooksFromProfile() {
        String token = AuthorizationWithApi.getToken();
        String userId = AuthorizationWithApi.getUserId();

        step("Отправить DELETE запрос на удаление всех книг из Profile", () ->
                given(requestSpec)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("UserId", userId)
                        .when()
                        .delete("/BookStore/v1/Books")
                        .then()
                        .spec(successResponse204Spec));
    }

    //@DisplayName("Получить список всех книг из Book Store через API")
    public static List<BookModel> getBooks() {
        GetListOfBooksModel response = given(requestSpec)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(successResponse200Spec)
                .extract()
                .as(GetListOfBooksModel.class);

        return response.getBooks();
    }

    //@DisplayName("Получить случайную книгу из Book Store через API")
    public static String getRandomIsbn() {
        List<BookModel> books = getBooks();
        if (books.isEmpty()) {
            throw new IllegalStateException("Список книг пуст.");
        }
        Random random = new Random();
        return books.get(random.nextInt(books.size())).getIsbn();
    }

    //@DisplayName("Добавить рандомную книгу в Profile через API")
    public static void addBookToProfile(String randomIsbn) {
        String isbn = getRandomIsbn();

        IsbnModel isbnModel = new IsbnModel(isbn);


        AddBookToProfileRequestModel request = new AddBookToProfileRequestModel(
                AuthorizationWithApi.getUserId(),
                List.of(isbnModel)
        );

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