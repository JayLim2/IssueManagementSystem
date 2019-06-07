package org.sergei.komarov.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Handlers {
    private static final DateTimeFormatter WEEK_NAME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static int tryParseInt(String integer) {
        ofNullable(integer);

        int value = 0;
        try {
            value = Integer.parseInt(integer.trim());
        } catch (NumberFormatException e) {
            System.out.println("Parsing integer exception.");
        }
        return value;
    }

    public static float tryParseFloat(String floatValue) {
        ofNullable(floatValue);

        float value = 0f;
        try {
            value = Float.parseFloat(floatValue.trim());
        } catch (NumberFormatException e) {
            System.out.println("Parsing float exception.");
        }
        return value;
    }

    private static void ofNullable(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    public static String getCurrentWeek() {
        LocalDate now = LocalDate.now().with(DayOfWeek.MONDAY);
        return WEEK_NAME_FORMATTER.format(now);
    }

    public static float aggregateTotal(float[] values) {
        if (values == null) {
            throw new NullPointerException();
        }

        float sum = 0;
        for (float value : values) {
            sum += value;
        }

        return sum;
    }
}
