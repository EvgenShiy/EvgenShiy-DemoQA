package tests.api_tests;

import helpers.extensions.WithLogin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

public class LoginTests extends TestBase {

    @Test
    @Tag("API")
    @DisplayName("Успешная авторизация существующего пользователя")
    @WithLogin
    void successesLoginUserTest() {

    }
}
