package com.joehxblog.action;

import com.joehxblog.entity.ToolType;
import com.joehxblog.entity.Tools;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import static com.joehxblog.action.CheckoutUtils.*;
import static java.time.DayOfWeek.*;

public class Checkout {
    private final Tools tools;

    public Checkout() {
        this(new Tools());
    }

    public Checkout(Tools tools) {
        this.tools = tools;
    }

    public Tools getTools() {
        return this.tools;
    }

    public RentalAgreement checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        if (rentalDayCount < 1) {
            throw new RuntimeException("Rental Day Count must be 1 or greater.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new RuntimeException("Discount Percent must be between 0 and 100.");
        }

        var tool = tools.getTool(toolCode);
        var chargeDays = calculateChargeDays(checkoutDate, rentalDayCount, tool.type());
        var prediscountCharge = calculatePrediscountCharge(chargeDays, tool.type().dailyCharge());
        var discountAmount = prediscountCharge * discountPercent / 100;
        var finalCharge = prediscountCharge - discountAmount;

        return new RentalAgreement(
                tool,
                rentalDayCount,
                discountPercent,
                checkoutDate,
                calculateDueDate(checkoutDate, rentalDayCount),
                chargeDays,
                prediscountCharge,
                discountAmount,
                finalCharge
        );
    }
}
