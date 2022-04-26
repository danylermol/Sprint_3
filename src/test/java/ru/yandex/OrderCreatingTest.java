package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(Parameterized.class)

public class OrderCreatingTest {
    private final List<String> color;

    public OrderCreatingTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK", "GREY")},
                {List.of("GREY")},
                {List.of()},
        };
    }

    @Test
    public void bodyTrackValueIsNotNullWithAnyColor() {
        OrderClient orderClient = new OrderClient();
        OrderParameters orderParameters = new OrderParameters(color);
        ValidatableResponse response = orderClient.createOrder(orderParameters);
        int statusCode = response.extract().statusCode();
        assertThat("Должен возвращаться код 201", statusCode, equalTo(SC_CREATED));
        assertThat("Тело дожно содержать не пустое поле Track", response.extract().body().path("track"), notNullValue());

        System.out.println(response.extract().body().path("track").toString());

    }
}



