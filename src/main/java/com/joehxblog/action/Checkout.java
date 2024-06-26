package com.joehxblog.action;

import com.joehxblog.entity.ToolType;
import com.joehxblog.entity.Tools;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import static java.time.DayOfWeek.*;

public class Checkout {
    private final Tools tools = new Tools();

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
        var prediscountCharge = chargeDays * tool.type().dailyCharge();
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

    public int calculateChargeDays(LocalDate checkoutDate, int rentalDayCount, ToolType toolType) {
        return (int) IntStream.range(0, rentalDayCount)
                .mapToObj(checkoutDate::plusDays)
                .filter(date -> toolType.weekendCharge() || !isWeekend(date))
                .filter(date -> toolType.holidayCharge() || !isHoliday(date))
                .count();
    }

    public boolean isWeekend(LocalDate date) {
        var weekday = date.getDayOfWeek();

        return weekday == SATURDAY || weekday == SUNDAY;
    }

    public boolean isHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    public boolean isLaborDay(LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER
            && date.getDayOfMonth() < 8
            && date.getDayOfWeek() == MONDAY;
    }

    public boolean isIndependenceDay(LocalDate date) {
        return date.getMonth() == Month.JULY && (
                date.getDayOfWeek() == FRIDAY && date.getDayOfMonth() == 3
             || date.getDayOfWeek() == MONDAY && date.getDayOfMonth() == 5
             || !isWeekend(date) && date.getDayOfMonth() == 4
        );
    }

    public LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {
        return checkoutDate.plusDays(rentalDayCount);
    }
}
