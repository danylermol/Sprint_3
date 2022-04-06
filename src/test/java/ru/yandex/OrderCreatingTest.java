package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)

public class OrderCreatingTest {
    private final List<String> color;
    private final int expected;


    public OrderCreatingTest(List<String> color, int expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK"), 201},
                {List.of("BLACK", "GREY"), 201},
                {List.of("GREY"), 201},
                {List.of(), 201},
        };
    }

    @Test
    public void bodyTrackValueIsNotNullWithAnyColor() {
        OrderClient orderClient = new OrderClient();
        OrderParameters orderParameters = new OrderParameters(color);
        ValidatableResponse response = orderClient.createOrder(orderParameters);
        assertThat("Должен возвращаться код 201", response.extract().statusCode(), equalTo(SC_CREATED));
        assertThat("Тело дожно содержать не пустое поле Track", response.extract().body().path("track"), notNullValue());

    }
}



