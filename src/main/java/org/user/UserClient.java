package org.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.Client;

import static java.net.HttpURLConnection.*;

public class UserClient extends Client {
    public static final String ROOT = "/auth";

    @Step("Create user")
    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when()
                .post(ROOT + "/register")
                .then().log().all();
    }

    @Step("Login in service")
    public ValidatableResponse logIn(Login login1) {
        return spec()
                .body(login1)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }

    @Step("Delete user")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .headers("Authorization", accessToken)
                .when()
                .delete(ROOT + "/user")
                .then().log().all().assertThat().statusCode(HTTP_ACCEPTED);

    }

    @Step("Edit user unauthorized. Send PATCH request to /api/auth/user")
    public ValidatableResponse editUserUnauthorized(Object body) {
        return spec()
                .body(body)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }

    @Step("Edit user. Send PATCH request to /api/auth/user")
    public ValidatableResponse editUserResponse(Object body, String accessToken) {
        return spec()
                .headers("Authorization", accessToken)
                .and()
                .body(body)
                .when()
                .patch(ROOT + "/user")
                .then().log().all();
    }

}
