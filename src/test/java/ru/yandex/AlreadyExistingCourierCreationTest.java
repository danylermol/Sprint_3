package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class AlreadyExistingCourierCreationTest {
    int courierId;
    CourierCredentials randomCredentials;

    @Before
    public void setUp() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        randomCredentials = randomValuesGenerator.getRandomCourierCredentials();
        courierClient.registerCourier(randomCredentials);
        ValidatableResponse registerCourierResponse = courierClient.login(randomCredentials);
        courierId = registerCourierResponse.extract().body().path("id");

    }

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        courierClient.delete(courierId);
    }

    @Test
    public void creatingAlreadyExistingCourierReturn409CodeTest() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse responseAfterRegisterExistingCourierClient = courierClient.registerCourier(randomCredentials);
        String expectedMessage = "Этот логин уже используется";
        String actualMessage = responseAfterRegisterExistingCourierClient.extract().body().path("message");

        assertThat("Должен возвращаться код 409", responseAfterRegisterExistingCourierClient.extract().statusCode(), equalTo(SC_CONFLICT));
        assertThat("Должно вернуться тело с сообщением: Этот логин уже используется", actualMessage, equalTo(expectedMessage));

    }


}
