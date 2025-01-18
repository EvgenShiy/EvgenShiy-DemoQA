package tests.api_tests;

import helpers.PropertyLoader;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;

public class ApiTestBase {
    @BeforeAll
    public static void setUp() {
        // Загрузка конфигурации и переменных среды
        PropertyLoader.loadCredentials();

        // Настройка RestAssured
        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.defaultParser = Parser.JSON;
    }
}
