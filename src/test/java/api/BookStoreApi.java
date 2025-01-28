package api;

import io.qameta.allure.Step;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class BookStoreApi {

    private static final Logger log = LoggerFactory.getLogger(BookStoreApi.class);

    public static void setAuthData(String authToken, String userIdentifier) {
    }

    @Step("Очистить все книги в Profile через API")
    public void deleteAllBooksFromProfile(String token, String userId) {
        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(successResponse204Spec);
    }

    @Step("Получить список всех книг из Book Store через API")
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

    @Step("Выбрать случайную книгу из Book Store через API")
    public static String getRandomIsbn() {
        List<BookModel> books = getBooks();
        if (books.isEmpty()) {
            throw new IllegalStateException("Список книг пуст.");
        }
        Random random = new Random();
        String isbn = books.get(random.nextInt(books.size())).getIsbn();
        log.info("Выбран ISBN: {}", isbn);
        return isbn;
    }

    @Step("Добавить выбранную книгу в Profile через API")
    public void addBookToProfile(String isbn, String token, String userId) {
        log.info("Добавляется книга с ISBN: {}", isbn);

        IsbnModel isbnModel = new IsbnModel(isbn);

        AddBookToProfileRequestModel request = new AddBookToProfileRequestModel();
        request.setUserId(userId);
        request.setCollectionOfIsbns(List.of(isbnModel));

        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(successResponse201Spec);

    }

}