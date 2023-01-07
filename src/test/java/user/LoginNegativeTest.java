package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.user.Login;
import org.user.UserChecks;
import org.user.UserClient;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class LoginNegativeTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();

    @Parameterized.Parameter()
    public String email;

    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameters(name = "email: {0}, password: {1}")
    public static Object[][] params() {
        return new Object[][]{
                {"darina@bk.ru", "12345678aAa"},
                {"darinka@bk.ru", "12345678aAa"},
        };
    }

    @Test
    @DisplayName("Login user with wrong email or password")
    @Description("Negative test for login user")
    public void testCheckUserNameAndResponseBody() {
        Login login1 = new Login(email, password);
        ValidatableResponse loginResponse = client.logIn(login1);
        var messageResponse = check.loggedInUnsuccessfully(loginResponse);
        assertThat(messageResponse, containsString("email or password are incorrect"));
    }
}
