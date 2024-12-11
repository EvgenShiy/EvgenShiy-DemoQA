package api;

import io.qameta.allure.Step;
import models.*;

import java.util.List;
import java.util.Random;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.ApiSpecs.*;

public class BookStoreApi {

    private static String token;
    private static String userId;

    public static void setAuthData(String authToken, String userIdentifier) {
        token = authToken;
        userId = userIdentifier;
    }

    @Step("Очистить все книги в Profile через API")
    public void deleteAllBooksFromProfile() {
        step("Отправить DELETE запрос на удаление всех книг из Profile", () ->
                given(requestSpec)
                        .header("Authorization", "Bearer " + token)
                        .queryParam("UserId", userId)
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
        String isbn = books.get(random.nextInt(books.size())).getIsbn();
        System.out.println("Сгенерирован ISBN: " + isbn);  // Логирование для отладки
        return isbn;
    }

    @Step("Добавить выбранную книгу в Profile через API")
    public BookStoreApi addBookToProfile(String isbn) {
        System.out.println("Добавляется книга с ISBN: " + isbn);  // Для отладки

        // Создаем объект ISBN модели
        IsbnModel isbnModel = new IsbnModel(isbn);

        // Создаем запрос для добавления книги
        AddBookToProfileRequestModel request = new AddBookToProfileRequestModel();
        request.setUserId(userId);  // Устанавливаем userId
        request.setCollectionOfIsbns(List.of(isbnModel));  // Передаем список с объектом isbnModel

        // Отправляем запрос для добавления книги в профиль
        given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(successResponse201Spec);

        return this;
    }
}