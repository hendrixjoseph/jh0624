package com.joehxblog.action;

import com.joehxblog.entity.ToolType;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

import static java.time.DayOfWeek.*;

public class CheckoutUtils {
    private CheckoutUtils() {}

    public static int calculateChargeDays(LocalDate checkoutDate, int rentalDayCount, ToolType toolType) {
        return (int) IntStream.range(0, rentalDayCount)
                .mapToObj(checkoutDate::plusDays)
                .filter(date -> toolType.weekendCharge() || !isWeekend(date))
                .filter(date -> toolType.holidayCharge() || !isHoliday(date))
                .count();
    }

    public static int calculatePrediscountCharge(int chargeDays, int dailyCharge) {
        return chargeDays * dailyCharge;
    }

    public static boolean isWeekend(LocalDate date) {
        var weekday = date.getDayOfWeek();

        return weekday == SATURDAY || weekday == SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    public static boolean isLaborDay(LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER
                && date.getDayOfMonth() < 8
                && date.getDayOfWeek() == MONDAY;
    }

    public static boolean isIndependenceDay(LocalDate date) {
        return date.getMonth() == Month.JULY && (
                date.getDayOfWeek() == FRIDAY && date.getDayOfMonth() == 3
                        || date.getDayOfWeek() == MONDAY && date.getDayOfMonth() == 5
                        || !isWeekend(date) && date.getDayOfMonth() == 4
        );
    }

    public static LocalDate calculateDueDate(LocalDate checkoutDate, int rentalDayCount) {
        return checkoutDate.plusDays(rentalDayCount);
    }
}
