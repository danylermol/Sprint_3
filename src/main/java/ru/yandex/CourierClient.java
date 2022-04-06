package ru.yandex;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step
    public ValidatableResponse login(CourierCredentials credentials) {

        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();

    }

    @Step
    public ValidatableResponse registerCourier(CourierCredentials courierCredentials) {

        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH)
                .then();

    }

    @Step
    public ValidatableResponse delete(int courierId) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();

    }

}
