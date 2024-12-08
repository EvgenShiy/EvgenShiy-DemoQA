package api;

import models.BookModel;
import models.GetListOfBooksModel;
import models.IsbnModel;
import models.AddBookToProfileRequestModel;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class BookStoreApi {

    //Очистить все книги в Profile через API
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

    //Получить список всех книг из Book Store через API
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

    //Получить случайную книгу из Book Store через API
    public static String getRandomIsbn() {
        List<BookModel> books = getBooks();
        if (books.isEmpty()) {
            throw new IllegalStateException("Список книг пуст.");
        }
        Random random = new Random();
        return books.get(random.nextInt(books.size())).getIsbn();
    }

    public static String addBookToProfile() {

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
                        .spec(successResponse201Spec)
        );

        // Установить cookies для UI
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", AuthorizationWithApi.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", AuthorizationWithApi.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", AuthorizationWithApi.getToken()));

        // Возвращаем выбранный ISBN для последующей проверки
        return isbn;
    }

}