package com.joehxblog.action;

import com.joehxblog.entity.Tool;

import java.time.LocalDate;
import java.util.StringJoiner;

public record RentalAgreement(
        Tool tool,
        int rentalDays,
        int discountPercent,
        LocalDate checkoutDate
) {

    public LocalDate dueDate() {
        return this.checkoutDate.plusDays(rentalDays);
    }

    @Override
    public String toString() {
        return new StringJoiner("\n")
                .add("Tool code: " + tool.code())
                .add("Tool type: " + tool.type().name())
                .add("Tool brand: " + tool.brand())
                .add("Rental days: " + rentalDays)
                .add("Check out date: " + checkoutDate.toString())
                .add("Due date: " + dueDate())
                .add("Daily rental charge: ")
                .add("Charge days: ")
                .add("Pre-discount charge: ")
                .add("Discount percent: " + discountPercent + "%")
                .add("Discount amount: ")
                .add("Final charge: ")
                .toString();
    }
}
