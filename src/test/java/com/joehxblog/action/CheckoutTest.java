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

    @ParameterizedTest
    @ValueSource(ints = {22, 23})
    void testIsWeekend(int day) {
        var date = LocalDate.of(2024, 6, day);

        assertTrue(checkout.isWeekend(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 25, 26, 27, 28})
    void testIsNotWeekend(int day) {
        var date = LocalDate.of(2024, 6, day);

        assertFalse(checkout.isWeekend(date));
    }

    @ParameterizedTest
    @CsvSource({
      "2024, 6, 24, CHNS, 5, 5",
      "2024, 6, 20, CHNS, 5, 3",
      "2024, 6, 20, LADW, 5, 5",
      "2024, 7,  1, LADW, 5, 4",
      "2024, 6, 28, JAKR, 7, 4"
    })
    void testCalculateChargeDays(int year, int month, int day, String toolCode, int rentalDayCount, int expectedChargeDays) {
        var date = LocalDate.of(year, month, day);
        var toolType = checkout.getTools().getTool(toolCode).type();

        var actualChargeDays = checkout.calculateChargeDays(date, rentalDayCount, toolType);

        assertEquals(expectedChargeDays, actualChargeDays);
    }
}