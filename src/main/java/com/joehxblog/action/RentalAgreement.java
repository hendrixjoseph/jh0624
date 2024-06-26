package com.joehxblog.action;

import com.joehxblog.entity.Tool;

import java.time.LocalDate;
import java.util.StringJoiner;

public record RentalAgreement(
        Tool tool,
        int rentalDays,
        int discountPercent,
        LocalDate checkoutDate,
        LocalDate dueDate,
        int chargeDays,
        int prediscountCharge,
        int discountAmount,
        int finalCharge
) {

    private String moneyFormat(int money) {
        return "$" + money / 10 + "." + money % 100;
    }

    @Override
    public String toString() {
        return new StringJoiner("\n")
                .add("Tool code: " + tool.code())
                .add("Tool type: " + tool.type().name())
                .add("Tool brand: " + tool.brand())
                .add("Rental days: " + rentalDays)
                .add("Check out date: " + checkoutDate)
                .add("Due date: " + dueDate())
                .add("Daily rental charge: "  + moneyFormat(tool.type().dailyCharge()))
                .add("Charge days: " + chargeDays)
                .add("Pre-discount charge: " + moneyFormat(prediscountCharge))
                .add("Discount percent: " + discountPercent + "%")
                .add("Discount amount: " + moneyFormat(discountAmount))
                .add("Final charge: " + moneyFormat(finalCharge))
                .toString();
    }
}
