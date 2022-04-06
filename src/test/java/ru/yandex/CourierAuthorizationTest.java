package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierAuthorizationTest {

    String login;
    String password;
    int courierId;

    @Before
    public void setUp() {
        CourierClient courierClient = new CourierClient();
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        login = randomValuesGenerator.getRandomLogin();
        password = randomValuesGenerator.getRandomPassword();
        CourierCredentials credentials = new CourierCredentials(login, password);
        courierClient.registerCourier(credentials);

    }

    @After
    public void tearDown(){
        CourierClient courierClient = new CourierClient();
        courierClient.delete(courierId);

    }

    @Test
    public void courierAuthorizationReturns200CodeTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse response = courierClient.login(credentials);
        int responseCode = response.extract().statusCode();
        courierId = response.extract().body().path("id");

        assertThat("При успешном логине должен вернуться код: 200", responseCode, equalTo(SC_OK));
        assertThat("При успешном логине вернулся id <> 0", courierId, is(not(0)));
    }

}
