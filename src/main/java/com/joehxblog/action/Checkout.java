package com.joehxblog.action;

import com.joehxblog.entity.Tools;

import java.time.LocalDate;

public class Checkout {
    private Tools tools = new Tools();

    public RentalAgreement checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        return new RentalAgreement(
                tools.getTool(toolCode),
                rentalDayCount,
                discountPercent,
                checkoutDate,
                calculateDueDate(checkoutDate, rentalDayCount)
        );
    }

    private LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {
        return checkoutDate.plusDays(rentalDayCount);
    }
}
