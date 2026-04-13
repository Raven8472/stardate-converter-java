package com.raven8472.stardate;

import java.time.LocalDate;
import java.util.Locale;

public class StardateCalculator {

    private static final int BASE_YEAR = 2323;
    private static final double UNITS_PER_YEAR = 1000.0;

    public double convert(LocalDate date) {
        int year = date.getYear();
        int dayOfYear = date.getDayOfYear();
        int daysInYear = date.isLeapYear() ? 366 : 365;

        return UNITS_PER_YEAR * (year - BASE_YEAR)
            + (UNITS_PER_YEAR * (dayOfYear - 1) / daysInYear);
    }

    public String format(LocalDate date) {
        return String.format(Locale.US, "%.2f", convert(date));
    }

    public LocalDate reverse(double stardate) {
        int yearOffset = (int) Math.floor(stardate / UNITS_PER_YEAR);
        int year = BASE_YEAR + yearOffset;
        int daysInYear = LocalDate.of(year, 1, 1).isLeapYear() ? 366 : 365;

        double fractionalYear = (stardate / UNITS_PER_YEAR) - yearOffset;
        int dayIndex = (int) Math.round(fractionalYear * daysInYear);
        dayIndex = Math.max(0, Math.min(daysInYear - 1, dayIndex));

        return LocalDate.ofYearDay(year, dayIndex + 1);
    }

    public String formatReverse(double stardate) {
        return reverse(stardate).toString();
    }
}
