package user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.user.User;
import org.user.UserChecks;
import org.user.UserClient;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class CreateUserNegativeTest {
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();

    @Parameterized.Parameter()
    public String email;

    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameter(2)
    public String name;

    @Parameterized.Parameter(3)
    public String message;

    @Parameterized.Parameters(name = "email: {0}, password: {1}, name: {2}, message: {3}")
    public static Object[][] params() {
        return new Object[][]{
                {"darina@bk.ru", "12345678aA", "Darina", "User already exists"},
                {"darina1@bk.ru", "", "Darina", "Email, password and name are required fields"},
                {"darina1@bk.ru", "12345678aA", "", "Email, password and name are required fields"},
                {"", "12345678aA", "Darina", "Email, password and name are required fields"},
        };
    }

    @Test
    @DisplayName("Create user without 1 required field and with same data")
    @Description("Negative test for /user endpoint")
    public void testCreateNewUserAndLogin() {
        User user = new User(email, password, name);
        var response = client.create(user);
        var messageResponse = check.creationFailed(response);
        assertThat(messageResponse, containsString(message));
    }

}
