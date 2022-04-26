package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UniqueCourierCreatingTest {

    CourierCredentials randomCredentials;
    int courierId;

    @After
    public void tearDown() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse loginResponse = courierClient.login(randomCredentials);
        courierId = loginResponse.extract().body().path("id");
        courierClient.delete(courierId);
    }

    @Test
    public void creatingNewUniqueCourierReturnCode201Test() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        randomCredentials = randomValuesGenerator.getRandomCourierCredentials();
        ValidatableResponse registerResponse = courierClient.registerCourier(randomCredentials);

        assertThat("Код ответа при создании курьера должен быть = 201", registerResponse.extract().statusCode(), equalTo(SC_CREATED));
    }

    @Test
    public void creatingNewUniqueCourierReturnTrueBodyMessageTest() {
        RandomValuesGenerator randomValuesGenerator = new RandomValuesGenerator();
        CourierClient courierClient = new CourierClient();
        randomCredentials = randomValuesGenerator.getRandomCourierCredentials();
        ValidatableResponse registerResponse = courierClient.registerCourier(randomCredentials);
        String expectedBodyMessage = "true";
        String actualBodyMessage = registerResponse.extract().body().path("ok").toString();

        assertThat("В тело пришло неверное сообщение при создании курьера", actualBodyMessage, equalTo(expectedBodyMessage));
    }

}
