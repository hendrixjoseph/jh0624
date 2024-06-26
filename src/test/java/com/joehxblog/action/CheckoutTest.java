package com.joehxblog.action;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    Checkout checkout = new Checkout();

    @ParameterizedTest
    @CsvSource({
    /* inputs */  "LADW, 2020, 7, 2, 3, 10," +
    /* outputs */ "2020, 7, 5, 2, 398, 40, 358",

    /* inputs */  "CHNS, 2015, 7, 2, 5, 0," +
    /* outputs */ "2015, 7, 7, 3, 447, 0, 447",

    /* inputs */ "JAKD, 2015, 9, 3, 6, 0," +
    /* outputs */ "2015, 9, 9, 3, 897, 0, 897",

    /* inputs */ "JAKR, 2015, 7, 2, 9, 0," +
    /* outputs */ "2015, 7, 11, 6, 1794, 0, 1794",

    /* inputs */ "JAKR, 2020, 7, 2, 4, 50," +
    /* outputs */ "2020, 7, 6, 1, 299, 150, 149"
    })
    void testCheckout(
            /* inputs */
            String toolCode,
            int checkoutYear,
            int checkoutMonth,
            int checkoutDay,
            int rentalDays,
            int discount,

            /* outputs */
            int dueDateYear,
            int dueDateMonth,
            int dueDateDay,
            int expectedChargeDays,
            int expectedPrediscountCharge,
            int expectedDiscountAmount,
            int expectedFinalCharge
            ) {

        var checkoutDate = LocalDate.of(checkoutYear, checkoutMonth, checkoutDay);

        var actualRentalAgreement = checkout.checkout(
                toolCode,
                rentalDays,
                discount,
                checkoutDate
        );

        var expectedDueDate = LocalDate.of(dueDateYear, dueDateMonth, dueDateDay);

        var expectedRentalAgreement = new RentalAgreement(
                checkout.getTools().getTool(toolCode),
                rentalDays,
                discount,
                checkoutDate,
                expectedDueDate,
                expectedChargeDays,
                expectedPrediscountCharge,
                expectedDiscountAmount,
                expectedFinalCharge
        );

        assertEquals(expectedRentalAgreement, actualRentalAgreement);
    }

    @Test
    void test1Special() {
        var exception = assertThrows(RuntimeException.class, () ->
                checkout.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3)));

        assertEquals("Discount Percent must be between 0 and 100.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
      "0, 10, Rental Day Count must be 1 or greater.",
      "1, -10, Discount Percent must be between 0 and 100.",
      "1, 101, Discount Percent must be between 0 and 100."

    })
    void testThrowsException(int rentalDays, int discount, String expectedMessage) {
        var exception = assertThrows(RuntimeException.class, () -> checkout.checkout("", rentalDays, discount, LocalDate.now()));
        assertEquals(expectedMessage, exception.getMessage());
    }


}