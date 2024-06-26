package com.joehxblog.action;

import com.joehxblog.entity.ToolType;
import com.joehxblog.entity.Tools;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import static java.time.DayOfWeek.*;

public class Checkout {
    private Tools tools = new Tools();

    public RentalAgreement checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) {
        var tool = tools.getTool(toolCode);

        return new RentalAgreement(
                tool,
                rentalDayCount,
                discountPercent,
                checkoutDate,
                calculateDueDate(checkoutDate, rentalDayCount)
        );
    }

    public int calculateChargeDays(LocalDate checkoutDate, int rentalDayCount, ToolType toolType) {
        return (int) IntStream.range(0, rentalDayCount)
                .mapToObj(checkoutDate::plusDays)
                .filter(date -> !toolType.weekendCharge() || isWeekend(date))
                .filter(date -> !toolType.holidayCharge() || isHoliday(date))
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
