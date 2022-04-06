package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class AlreadyExistingCourierCreationTest {
    String login;
    String password;
    int courierId;

    @Before
    public void setUp() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        login = randomValuesGenerator.getRandomLogin();
        password = randomValuesGenerator.getRandomPassword();
        CourierCredentials courierCredentials = new CourierCredentials(login, password);
        courierClient.registerCourier(courierCredentials);
        ValidatableResponse registerCourierResponse = courierClient.login(courierCredentials);
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
        CourierCredentials courierCredentials = new CourierCredentials(login, password);
        ValidatableResponse responseAfterRegisterExistingCourierClient = courierClient.registerCourier(courierCredentials);
        String expectedMessage = "Этот логин уже используется";
        String actualMessage = responseAfterRegisterExistingCourierClient.extract().body().path("message");

        assertThat("Должен возвращаться код 409", responseAfterRegisterExistingCourierClient.extract().statusCode(), equalTo(SC_CONFLICT));
        assertThat("Должно вернуться тело с сообщением: Этот логин уже используется", actualMessage, equalTo(expectedMessage));

    }


}
