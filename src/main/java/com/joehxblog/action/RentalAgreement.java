package com.joehxblog.action;

import com.joehxblog.entity.Tool;

import java.time.LocalDate;
import java.util.StringJoiner;

public record RentalAgreement(
        Tool tool,
        int rentalDays,
        LocalDate checkoutDate
) {

    @Override
    public String toString() {
        return new StringJoiner("\n")
                .add("Tool code: " + tool.code())
                .add("Tool type: " + tool.type().name())
                .add("Tool brand: " + tool.brand())
                .add("Check out date: " + checkoutDate.toString())
                .toString();
    }
}
