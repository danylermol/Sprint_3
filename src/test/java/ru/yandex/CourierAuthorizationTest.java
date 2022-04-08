package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierAuthorizationTest {

    int courierId;
    CourierCredentials randomCredentials;

    @Before
    public void setUp() {
        CourierClient courierClient = new CourierClient();
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        randomCredentials = randomValuesGenerator.getRandomCourierCredentials();
        courierClient.registerCourier(randomCredentials);

    }

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        courierClient.delete(courierId);

    }

    @Test
    public void courierAuthorizationReturns200CodeTest() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse response = courierClient.login(randomCredentials);
        int responseCode = response.extract().statusCode();
        courierId = response.extract().body().path("id");

        assertThat("При успешном логине должен вернуться код: 200", responseCode, equalTo(SC_OK));
        assertThat("При успешном логине вернулся id <> 0", courierId, is(not(0)));
    }

}
