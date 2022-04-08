package ru.yandex;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomValuesGenerator {

public CourierCredentials getRandomCourierCredentials(){
    String courierLogin = RandomStringUtils.randomAlphabetic(10);
    String courierPassword = RandomStringUtils.randomAlphabetic(10);
    return new CourierCredentials(courierLogin, courierPassword);
}

    public int getRandomRentTime() {
        int rentTime = (int) (Math.random() * 11);
        return rentTime;
    }

    public String getRandomStringValue() {
    String randomStringValue = RandomStringUtils.randomAlphabetic(10);
    return randomStringValue;
    }

}

