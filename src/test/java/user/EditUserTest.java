package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.user.Login;
import org.user.User;
import org.user.UserChecks;
import org.user.UserClient;

@RunWith(Parameterized.class)
public class EditUserTest {

    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();

    @Parameterized.Parameter()
    public String email;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String name;
    @Parameterized.Parameter(3)
    public String change;
    @Parameterized.Parameter(4)
    public String meaning;
    private String accessToken;

    @Before
    public void setUp() {
        User user = new User("darisha@bk.ru", "yandex2023", "loveBurger");
        accessToken = client.create(user).extract().path("accessToken");
    }

    @Parameterized.Parameters(name = "email: {0}, password: {1}, name: {2}, change: {3}, meaning: {4}")
    public static Object[][] params() {
        return new Object[][]{
                {"druzhok@pirozhok.ru", "yandex2023", "loveBurger", "email", "druzhok@pirozhok.ru"},
                {"darisha@bk.ru", "2023yandex2023", "loveBurger", "password", "2023yandex2023"},
                {"darisha@bk.ru", "yandex2023", "significant", "name", "significant"},
        };
    }

    @Test
    @DisplayName("User editing")
    @Description("Basic test for positive user editing. Change email, password, name fields and check it")
    public void UserEditingTest() {
        String body = String.format("{\"%s\": \"%s\"}", change, meaning);
        ValidatableResponse editUserResponse = client.editUserResponse(body, accessToken);
        check.checkUserEditedSuccess(editUserResponse, email, name);
        ValidatableResponse loginResponse = client.logIn(new Login(email, password));
        check.checkUserLoginSuccess(loginResponse, email, name);
    }

    @Test
    @DisplayName("Unauthorized user editing")
    @Description("Negative test for user editing when user is unauthorized")
    public void UserEditingUnauthorizedTest() {
        ValidatableResponse editUserResponse = client.editUserUnauthorized(new User(email, password, name));
        check.checkUserUnauthorized(editUserResponse);
    }

    @After
    public void deleteUser() {
        if (accessToken != null)
            client.delete(accessToken);
    }
}
