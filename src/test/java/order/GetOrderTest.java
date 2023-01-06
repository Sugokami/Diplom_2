package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.order.OrderClient;
import org.user.Login;
import org.user.UserChecks;
import org.user.UserClient;

import static java.net.HttpURLConnection.*;

public class GetOrderTest {
    private final OrderClient order = new OrderClient();
    private final UserClient client = new UserClient();
    private final UserChecks check = new UserChecks();
    private String accessToken;

    @Test
    @DisplayName("Show an orderlist to unauthorized user")
    @Description("Negative test for orders")
    public void testGetOrderListUnauthorizedUser() {
        Response response = order.sendGetRequestUnauthorized();
        order.compareResponseIsNotNull(response, HTTP_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Show an orderlist to authorized user")
    @Description("Basic test for orders")
    public void testGetOrderListAuthorizedUser() {
        Login login1 = new Login ("darina@bk.ru", "12345678aA");
        ValidatableResponse loginResponse = client.logIn(login1);
        accessToken = check.loggedInSuccessfully(loginResponse);
        Response response = order.sendGetRequestAuthorized(accessToken);
        order.compareResponseIsNotNull(response, HTTP_OK);
    }
}
