package ru.yandex;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse createOrder(OrderParameters orderParameters){

        return given()
                .spec(getBaseSpec())
                .body(orderParameters)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step
    public ValidatableResponse getOrderList(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}
