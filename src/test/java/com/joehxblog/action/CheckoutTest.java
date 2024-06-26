package com.joehxblog.action;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    Checkout checkout = new Checkout();

    @Test
    void test() {
        var rentalAgreement = checkout.checkout(
                "JAKR",
                5,
                101,
                LocalDate.of(2015, 9, 3)
        );

        System.out.println(rentalAgreement);
    }
}