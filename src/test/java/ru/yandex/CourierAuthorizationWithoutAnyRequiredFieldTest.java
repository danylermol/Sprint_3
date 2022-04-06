package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationWithoutAnyRequiredFieldTest {

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
        ValidatableResponse responseLogin = courierClient.login(credentials);
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
        CourierCredentials credentials = new CourierCredentials();
        credentials.setPassword(password);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Недостаточно данных для входа";

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));
        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));

    }

    @Test
    public void authorizationWithoutPasswordTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(login);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Недостаточно данных для входа";

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));
        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));
    }

    @Test
    public void authorizationWithoutLoginAndPasswordTest() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials credentials = new CourierCredentials();
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Недостаточно данных для входа";

        assertThat("Должен вернуться код ответа 400", responseCode, equalTo(SC_BAD_REQUEST));
        assertThat("В теле ответа должно быть сообщение (Недостаточно данных для входа)", responseString, equalTo(expectedString));
    }
}
