package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.order.Order;
import org.order.OrderClient;
import org.user.Login;
import org.user.UserChecks;
import org.user.UserClient;

public class CreateOrderTest {
    private final OrderClient order = new OrderClient();
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    Order order2 = new Order(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
    private String accessToken;

    @Test
    @DisplayName("Create new order / authorized user")
    @Description("Basic test for /order endpoint")
    public void testCreateNewOrderAuthorizedUser() {
        Login login1 = new Login("darina@bk.ru", "12345678aA");
        ValidatableResponse loginResponse = client.logIn(login1);
        accessToken = check.loggedInSuccessfully(loginResponse);
        var response = order.create(order2, accessToken);
        order.createdSuccessfully(response);
    }

    @Test
    @DisplayName("Create new order / unauthorized user")
    @Description("Basic test for /order endpoint")
    public void testCreateNewOrderUnauthorizedUser() {
        var response = order.createWithoutToken(order2);
        order.createdSuccessfully(response);
    }
}
