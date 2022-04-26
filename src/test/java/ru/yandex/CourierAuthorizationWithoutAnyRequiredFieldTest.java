package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationWithoutAnyRequiredFieldTest {

    CourierCredentials randomCredentials;
    int courierId;

    @Before
    public void setUp() {
        CourierClient courierClient = new CourierClient();
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        randomCredentials = randomValuesGenerator.getRandomCourierCredentials();
        courierClient.registerCourier(randomCredentials);
        ValidatableResponse responseLogin = courierClient.login(randomCredentials);
        courierId = responseLogin.extract().body().path("id");
    }

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        courierClient.delete(courierId);
    }

    @Test
    public void authorizationWithoutLoginTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setPassword(randomCredentials.getPassword());
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int responseCode = loginResponse.extract().statusCode();

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));

        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Недостаточно данных для входа";

        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));

    }

    @Test
    public void authorizationWithoutPasswordTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin(randomCredentials.getLogin());
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int responseCode = loginResponse.extract().statusCode();

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));

        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Недостаточно данных для входа";

        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));
    }

    @Test
    public void authorizationWithoutLoginAndPasswordTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        int responseCode = loginResponse.extract().statusCode();

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));
        String responseString = loginResponse.extract().body().path("message");

        String expectedString = "Недостаточно данных для входа";

        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));
    }
}
