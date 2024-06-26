package com.joehxblog.action;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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


    @ParameterizedTest
    @CsvSource({"2024,4", "2026,3", "2027,5"})
    void testIsIndependenceDay(int year, int day) {
        var date = LocalDate.of(year, 7, day);

        assertTrue(checkout.isIndependenceDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,5", "2024,7,3", "2026,7,4", "2027,7,4", "2020,1,1"})
    void testIsNotIndependenceDay(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(checkout.isIndependenceDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,2", "2025,1", "2026,7"})
    void testIsLaborDay(int year, int day) {
        var date = LocalDate.of(year, 9, day);

        assertTrue(checkout.isLaborDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,9,9", "2025,9,8", "2024,6,3"})
    void testIsNotLaborDay(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(checkout.isLaborDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,4", "2026,7,3", "2027,7,5", "2024,9,2", "2025,9,1", "2026,9,7"})
    void testIsHoliday(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertTrue(checkout.isHoliday(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,5", "2024,7,3", "2026,7,4", "2027,7,4", "2020,1,1", "2024,9,9", "2025,9,8", "2024,6,3"})
    void testIsNotHoliday(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(checkout.isHoliday(date));
    }
}