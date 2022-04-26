package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CourierAuthorizationWithIncorrectCredentialsTest {

    int courierId;
    CourierCredentials randomCredentials;

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
    public void authorizationWithIncorrectPasswordReturns404ErrorTest() {
        CourierClient courierClient = new CourierClient();
        //Меняем пароль при правильном логине
        randomCredentials.setPassword(RandomStringUtils.randomAlphabetic(7));
        ValidatableResponse loginResponse = courierClient.login(randomCredentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Учетная запись не найдена";

        assertThat("Должен вернуться код ответа 404", responseCode, equalTo(SC_NOT_FOUND));
        assertThat("В теле ответа должно быть сообщение (Учетная запись не найдена)", responseString, equalTo(expectedString));
    }

    @Test
    public void authorizationWithIncorrectLoginReturns404ErrorTest() {
        CourierClient courierClient = new CourierClient();
        //Меняем логин при правильном логине
        randomCredentials.setPassword(RandomStringUtils.randomAlphabetic(7));
        ValidatableResponse loginResponse = courierClient.login(randomCredentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Учетная запись не найдена";

        assertThat("Должен вернуться код ответа 404", responseCode, equalTo(SC_NOT_FOUND));
        assertThat("В теле ответа должно быть сообщение (Учетная запись не найдена)", responseString, equalTo(expectedString));
    }
}
