package com.influxdb.constants;

import java.util.ArrayList;

public class DurationConstants {

    public static final ArrayList<String> DURATIONS = new ArrayList<>();
    static {
        DURATIONS.add("1m");
        DURATIONS.add("5m");
        DURATIONS.add("15m");
        DURATIONS.add("1h");
        DURATIONS.add("3h");
        DURATIONS.add("6h");
        DURATIONS.add("12h");
        DURATIONS.add("24h");
        DURATIONS.add("2d");
        DURATIONS.add("7d");
        DURATIONS.add("30d");
    }
}
