package org.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;

import static org.hamcrest.Matchers.*;

public class UserChecks {
    @Step("User created successfully")
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Step("Courier creation failed")
    public String creationFailed(ValidatableResponse response) {
        String path = response
                .assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("message", not(blankOrNullString()))
                .extract()
                .path("message");
        return path;
    }

    @Step("Successful login")
    public String loggedInSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", is(true))
                .extract()
                .path("accessToken");
    }

    @Step("Successful login for edited user's profile")
    public String checkUserLoginSuccess(ValidatableResponse loginResponse, String email, String name) {
        return loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", is(true)).and()
                .assertThat().body("user.email", equalTo(email.toLowerCase())).and()
                .body("user.name", equalTo(name)).and()
                .body("accessToken", notNullValue()).toString();
    }

    @Step("Login failed")
    public String loggedInUnsuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("message", not(blankOrNullString()))
                .extract()
                .path("message");
    }

    @Step("Check user edited successfully")
    public void checkUserEditedSuccess(ValidatableResponse response, String email, String name) {
        response
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body("success", equalTo(true)).and()
                .body("user.email", equalTo(email.toLowerCase())).and()
                .body("user.name", equalTo(name));
    }

    @Step("Check get user unauthorized")
    public void checkUserUnauthorized(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", equalTo(false)).and()
                .body("message", equalTo("You should be authorised"));
    }
}
