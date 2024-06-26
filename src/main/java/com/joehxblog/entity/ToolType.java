package com.joehxblog.entity;

public record ToolType(String name, int dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
    public ToolType(String name, int dailyCharge, boolean weekendCharge, boolean holidayCharge) {
        this(name, dailyCharge, true, weekendCharge, holidayCharge);
    }
}
