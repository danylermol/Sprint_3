package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
public class GetOrderListTest {
    @Test
    public void getOrderListWithoutParametersReturns200CodeAndNotNullBody(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.getOrderList();

        assertThat("Должен вернуться код 200", response.extract().statusCode(), equalTo(SC_OK));
        assertThat("Ключ orders в теле ответа не должен быть пустым", response.extract().body().path("orders"), notNullValue());
    }

}
