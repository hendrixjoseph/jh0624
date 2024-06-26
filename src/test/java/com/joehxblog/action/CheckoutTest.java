package com.joehxblog.action;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    static Stream<Arguments> testValues() {
        return Stream.of(
                Arguments.of("JAKR", 2015, 9, 3, 5, 101),
                Arguments.of("LADW", 2020, 7, 2, 3, 10),
                Arguments.of("CHNS", 2015, 7, 2, 5, 0),
                Arguments.of("JAKD", 2015, 9, 3, 6, 0),
                Arguments.of("JAKR", 2015, 7, 2, 9, 0),
                Arguments.of("JAKR", 2020, 7, 2, 4, 50)
        );
    }

    Checkout checkout = new Checkout();

    @ParameterizedTest
    @MethodSource("testValues")
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