import api.AuthorizationWithApi;
import data.AuthData;
import models.AuthResponseModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationWithApiTests {

    @Test
    void successfulLoginTest() {
        // Выполняем авторизацию
        AuthResponseModel authResponse = AuthorizationWithApi.login();

        // Проверяем, что ответ не null
        assertNotNull(authResponse, "Response should not be null");

        // Проверяем, что логин и токен в ответе соответствуют ожидаемым
        assertEquals(AuthData.login, authResponse.getUsername(), "Username should match");
        assertNotNull(authResponse.getToken(), "Token should not be null");
    }

    @Test
    void getTokenTest() {
        // Получаем токен
        String token = AuthorizationWithApi.getToken();

        // Проверяем, что токен не пустой
        assertNotNull(token, "Token should not be null");
        assertFalse(token.isEmpty(), "Token should not be empty");
    }
}
