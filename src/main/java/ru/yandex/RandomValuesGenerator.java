package ru.yandex;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomValuesGenerator {

    public String getRandomLogin() {
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        return courierLogin;
    }

    public String getRandomPassword() {
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        return courierPassword;
    }

    public String getRandomFirstName() {
        String firstName = RandomStringUtils.randomAlphabetic(7);
        return firstName;
    }

    public String getRandomLastName() {
        String lastName = RandomStringUtils.randomAlphabetic(7);
        return lastName;
    }

    public String getRandomAddress() {
        String address = RandomStringUtils.randomAlphabetic(13);
        return address;
    }

    public String getRandomMetroStation() {
        String metroStation = RandomStringUtils.randomAlphabetic(10);
        return metroStation;
    }

    public String getRandomPhone() {
        String phone = RandomStringUtils.randomAlphabetic(12);
        return phone;
    }

    public int getRandomRentTime() {
        int rentTime = (int) (Math.random() * 11);
        return rentTime;
    }

    public String getRandomDeliveryDate() {
        String deliveryDate = RandomStringUtils.randomAlphabetic(10);
        return deliveryDate;
    }

    public String getRandomComment() {
        String comment = RandomStringUtils.randomAlphabetic(25);
        return comment;
    }


}

