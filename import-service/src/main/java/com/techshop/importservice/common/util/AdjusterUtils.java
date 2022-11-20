package com.techshop.importservice.common.util;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

public class AdjusterUtils {
    private static final Map<String, TemporalAdjuster> Adjuster = new HashMap<String, TemporalAdjuster>() {
        {
            put("day", TemporalAdjusters.ofDateAdjuster(d -> d));
            put("week", TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
            put("month", TemporalAdjusters.firstDayOfMonth());
            put("year", TemporalAdjusters.firstDayOfYear());
        }
    };

    public static Map<String, TemporalAdjuster> getAdjuster() {
        return Adjuster;
    }
}
