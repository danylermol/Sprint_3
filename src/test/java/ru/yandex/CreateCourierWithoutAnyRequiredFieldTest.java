package ru.yandex;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierWithoutAnyRequiredFieldTest {

    @Test
    public void createCourierWithoutPasswordReturnError400Test() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setLogin(RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse registerCourierResponseWithoutPassword = courierClient.registerCourier(courierCredentials);
        String messageActual = registerCourierResponseWithoutPassword.extract().body().path("message");
        String messageExpected = "Недостаточно данных для создания учетной записи";

        assertThat("Должна вернуться ошибка 400", registerCourierResponseWithoutPassword.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("В теле должно быть сообщение: Недостаточно данных для создания учетной записи", messageExpected, equalTo(messageActual));

    }

    @Test
    public void createCourierWithoutLoginReturnError400Test() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        courierCredentials.setPassword(RandomStringUtils.randomAlphabetic(10));
        ValidatableResponse response = courierClient.registerCourier(courierCredentials);
        String messageActual = response.extract().body().path("message");
        String messageExpected = "Недостаточно данных для создания учетной записи";

        assertThat("Должна вернуться ошибка 400", response.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("В теле должно быть сообщение: Недостаточно данных для создания учетной записи", messageExpected, equalTo(messageActual));

    }

    @Test
    public void createCourierWithoutLoginAndPasswordReturnError400Test() {
        CourierClient courierClient = new CourierClient();
        CourierCredentials courierCredentials = new CourierCredentials();
        ValidatableResponse response = courierClient.registerCourier(courierCredentials);
        String messageActual = response.extract().body().path("message");
        String messageExpected = "Недостаточно данных для создания учетной записи";

        assertThat("Должна вернуться ошибка 400", response.extract().statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat("В теле должно быть сообщение: Недостаточно данных для создания учетной записи", messageExpected, equalTo(messageActual));
    }
}
