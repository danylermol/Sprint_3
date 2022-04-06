package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UniqueCourierCreatingTest {

    String login;
    String password;
    int courierId;

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials(login, password);
        ValidatableResponse loginResponse = courierClient.login(courierCredentials);
        courierId = loginResponse.extract().body().path("id");
        courierClient.delete(courierId);
    }

    @Test
    public void creatingNewUniqueCourierReturnCode201Test() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        login = randomValuesGenerator.getRandomLogin();
        password = randomValuesGenerator.getRandomPassword();
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse registerResponse = courierClient.registerCourier(credentials);

        assertThat("Код ответа при создании курьера должен быть = 201", registerResponse.extract().statusCode(), equalTo(SC_CREATED));
    }

    @Test
    public void creatingNewUniqueCourierReturnTrueBodyMessageTest() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        login = randomValuesGenerator.getRandomLogin();
        password = randomValuesGenerator.getRandomPassword();
        CourierCredentials credentials = new CourierCredentials(login, password);
        ValidatableResponse registerResponse = courierClient.registerCourier(credentials);
        String expectedBodyMessage = "true";
        String actualBodyMessage = registerResponse.extract().body().path("ok").toString();

        assertThat("В тело пришло неверное сообщение при создании курьера", actualBodyMessage, equalTo(expectedBodyMessage));
    }

}
