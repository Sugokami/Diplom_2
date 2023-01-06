package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.order.Order;
import org.order.OrderClient;
import org.user.Login;
import org.user.UserChecks;
import org.user.UserClient;

import static java.net.HttpURLConnection.*;

@RunWith(Parameterized.class)
public class CreateOrderNegativeTest {
    private final OrderClient order = new OrderClient();
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;
    @Parameterized.Parameter()
    public String[] ingredients;

    @Parameterized.Parameter(1)
    public int statusCode;

    @Parameterized.Parameters(name = "ingredients: {0}, statusCode: {1}")
    public static Object[][] params() {
        return new Object[][]{
                {new String[]{"61c0c5a71d1f82001bdaaa812"}, HTTP_INTERNAL_ERROR},
                {new String[]{""}, HTTP_INTERNAL_ERROR},
                {new String[1], HTTP_BAD_REQUEST},
        };
    }

    @Test
    @DisplayName("Create new order (incorrect hash or no ingredients)/ authorized user")
    @Description("Negative test for /order endpoint")
    public void testCreateNewOrderNegativeAuthorizedUser() {
        Login login1 = new Login("darina@bk.ru", "12345678aA");
        ValidatableResponse loginResponse = client.logIn(login1);
        accessToken = check.loggedInSuccessfully(loginResponse);
        Order order2 = new Order(ingredients);
        var response = order.create(order2, accessToken);
        order.createdUnsuccessfully(response, statusCode);
    }

    @Test
    @DisplayName("Create new order (incorrect hash or no ingredients)/ unauthorized user")
    @Description("Negative test for /order endpoint")
    public void testCreateNewOrderNegativeUnauthorizedUser() {
        Order order2 = new Order(ingredients);
        var response = order.createWithoutToken(order2);
        order.createdUnsuccessfully(response, statusCode);
    }
}
