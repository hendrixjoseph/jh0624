package com.joehxblog.action;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    Checkout checkout = new Checkout();

    @ParameterizedTest
    @CsvSource({
            "JAKR, 2015, 9, 3, 5, 101",
            "LADW, 2020, 7, 2, 3, 10",
            "CHNS, 2015, 7, 2, 5, 0",
            "JAKD, 2015, 9, 3, 6, 0",
            "JAKR, 2015, 7, 2, 9, 0",
            "JAKR, 2020, 7, 2, 4, 50"
    })
    void test(String toolCode, int year, int month, int day, int rentalDays, int discount) {
        var rentalAgreement = checkout.checkout(
                toolCode,
                rentalDays,
                discount,
                LocalDate.of(year, month, day)
        );

        System.out.println(rentalAgreement);
    }


}