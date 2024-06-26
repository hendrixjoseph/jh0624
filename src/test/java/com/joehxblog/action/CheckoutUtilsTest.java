package com.joehxblog.action;

import com.joehxblog.entity.Tools;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static com.joehxblog.action.CheckoutUtils.*;
import static com.joehxblog.action.CheckoutUtils.calculatePrediscountCharge;
import static org.junit.jupiter.api.Assertions.*;

class CheckoutUtilsTest {

    @ParameterizedTest
    @CsvSource({"2024,4", "2026,3", "2027,5"})
    void testIsIndependenceDay(int year, int day) {
        var date = LocalDate.of(year, 7, day);

        assertTrue(isIndependenceDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,5", "2024,7,3", "2026,7,4", "2027,7,4", "2020,1,1"})
    void testIsNotIndependenceDay(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(isIndependenceDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,2", "2025,1", "2026,7"})
    void testIsLaborDay(int year, int day) {
        var date = LocalDate.of(year, 9, day);

        assertTrue(isLaborDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,9,9", "2025,9,8", "2024,6,3"})
    void testIsNotLaborDay(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(isLaborDay(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,4", "2026,7,3", "2027,7,5", "2024,9,2", "2025,9,1", "2026,9,7"})
    void testIsHoliday(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertTrue(isHoliday(date));
    }

    @ParameterizedTest
    @CsvSource({"2024,7,5", "2024,7,3", "2026,7,4", "2027,7,4", "2020,1,1", "2024,9,9", "2025,9,8", "2024,6,3"})
    void testIsNotHoliday(int year, int month, int day) {
        var date = LocalDate.of(year, month, day);

        assertFalse(isHoliday(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {22, 23})
    void testIsWeekend(int day) {
        var date = LocalDate.of(2024, 6, day);

        assertTrue(isWeekend(date));
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 25, 26, 27, 28})
    void testIsNotWeekend(int day) {
        var date = LocalDate.of(2024, 6, day);

        assertFalse(isWeekend(date));
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
        var toolType = new Tools().getTool(toolCode).type();

        var actualChargeDays = calculateChargeDays(date, rentalDayCount, toolType);

        assertEquals(expectedChargeDays, actualChargeDays);
    }

    @ParameterizedTest
    @CsvSource({
            " 1, 199,  199",
            " 2, 199,  398",
            "10, 199, 1990"
    })
    void testCalculatePrediscountCharge(int chargeDays, int dailyCharge, int expectedPrediscountCharge) {
        var actualPrediscountCharge = calculatePrediscountCharge(chargeDays, dailyCharge);

        assertEquals(expectedPrediscountCharge, actualPrediscountCharge);
    }

    @ParameterizedTest
    @CsvSource({
            " 100,  10,  10",
            " 399,  10,  40",
            " 316,  10,  32",
            " 315,  10,  32",
            " 314,  10,  31",
            "2999,  10, 300",
            " 342, 100, 342"
    })
    void testCalculateDiscountAmount(int prediscountCharge, int discountPercent, int expectedDiscountAmount) {
        var actualDiscountAmount = calculateDiscountAmount(prediscountCharge, discountPercent);

        assertEquals(expectedDiscountAmount, actualDiscountAmount);
    }
}