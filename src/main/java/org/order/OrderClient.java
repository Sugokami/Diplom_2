package org.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.Client;

import static org.hamcrest.Matchers.*;
import static java.net.HttpURLConnection.*;

public class OrderClient extends Client {
    public static final String ROOT = "/orders";

    @Step("Create an order with Token")
    public ValidatableResponse create(Order order, String accessToken) {
        return spec()
                .headers("Authorization", accessToken)
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Create an order without Token")
    public ValidatableResponse createWithoutToken(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Order created successfully")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .assertThat().body("success", is(true));
    }

    @Step("Order created unsuccessfully")
    public void createdUnsuccessfully(ValidatableResponse response, int statusCode) {
        response
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body("message", notNullValue());
    }

    @Step("Send get request")
    public Response sendGetRequestAuthorized(String accessToken) {
        Response response = spec().headers("Authorization", accessToken).get(ROOT);
        return response;
    }

    @Step("Send get request")
    public Response sendGetRequestUnauthorized() {
        Response response = spec().get(ROOT);
        return response;
    }

    @Step("Assert that response is not nullable")
    public void compareResponseIsNotNull(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode)
                .and().body(notNullValue());
    }
}
