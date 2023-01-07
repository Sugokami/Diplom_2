package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.user.Login;
import org.user.User;
import org.user.UserChecks;
import org.user.UserClient;

public class CreateLoginPositiveTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;

    @Test
    @DisplayName("Create new user valid data")
    @Description("Basic test for /user endpoint")
    public void testCreateNewUserAndLogin() {
        User user = new User("darisha@bk.ru", "yandex2023", "loveBurger");
        var response = client.create(user);
        check.createdSuccessfully(response);
        Login login1 = Login.from(user);
        ValidatableResponse loginResponse = client.logIn(login1);
        accessToken = check.loggedInSuccessfully(loginResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null)
            client.delete(accessToken);
    }
}
