package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierAuthorizationWithIncorrectCredentialsTest {

    String login;
    String password;
    int courierId;

    @Before
    public void setUp(){
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
    public void tearDown(){
        CourierClient courierClient = new CourierClient();
        courierClient.delete(courierId);
    }

    @Test
    public void authorizationWithIncorrectPasswordReturns404ErrorTest(){
        CourierClient courierClient = new CourierClient();
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        //Меняем пароль при правильном логине
        password = randomValuesGenerator.getRandomPassword();
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Учетная запись не найдена";

        assertThat("Должен вернуться код ответа 404", responseCode, equalTo(SC_NOT_FOUND));
        assertThat("В теле ответа должно быть сообщение (Учетная запись не найдена)", responseString, equalTo(expectedString));
    }

    @Test
    public void authorizationWithIncorrectLoginReturns404ErrorTest(){
        CourierClient courierClient = new CourierClient();
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        //Меняем пароль при правильном логине
        login = randomValuesGenerator.getRandomLogin();
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int responseCode = loginResponse.extract().statusCode();
        String responseString = loginResponse.extract().body().path("message");
        String expectedString = "Учетная запись не найдена";

        assertThat("Должен вернуться код ответа 404", responseCode, equalTo(SC_NOT_FOUND));
        assertThat("В теле ответа должно быть сообщение (Учетная запись не найдена)", responseString, equalTo(expectedString));
    }
}
