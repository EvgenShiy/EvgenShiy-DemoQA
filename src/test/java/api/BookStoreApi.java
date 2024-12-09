package api;

import io.qameta.allure.Step;
import models.BookModel;
import models.GetListOfBooksModel;
import models.IsbnModel;
import models.AddBookToProfileRequestModel;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static data.AuthData.USER_ID;
import static data.AuthData.USER_TOKEN;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class BookStoreApi {

    @Step("Очистить все книги в Profile через API")
    public static void deleteAllBooksFromProfile() {

        step("Отправить DELETE запрос на удаление всех книг из Profile", () ->
                given(requestSpec)
                        .header("Authorization", "Bearer " + USER_TOKEN)
                        .queryParam("UserId", USER_ID)
                        .when()
                        .delete("/BookStore/v1/Books")
                        .then()
                        .spec(successResponse204Spec));
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
        return books.get(random.nextInt(books.size())).getIsbn();
    }

    @Step("Добавить выбранную книгу в Profile через API")
    public BookStoreApi addBookToProfile() {

        String isbn = getRandomIsbn();

        IsbnModel isbnModel = new IsbnModel(isbn);
        AddBookToProfileRequestModel request = new AddBookToProfileRequestModel();
        request.setUserId(USER_ID);
        request.setCollectionOfIsbns(List.of(isbnModel));

        given(requestSpec)
                .header("Authorization", "Bearer " + USER_TOKEN)
                .body(request)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(successResponse201Spec);

        return this;
    }

}