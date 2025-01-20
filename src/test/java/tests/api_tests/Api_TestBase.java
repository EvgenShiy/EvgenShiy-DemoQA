package tests.api_tests;

import helpers.PropertyLoader;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;

public class Api_TestBase {
    @BeforeAll
    public static void setUp() {

        PropertyLoader.loadCredentials();

        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.defaultParser = Parser.JSON;
    }
}
