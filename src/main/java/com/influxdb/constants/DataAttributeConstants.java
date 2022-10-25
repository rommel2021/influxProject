package com.influxdb.constants;

import java.util.ArrayList;

public class DataAttributeConstants {
    public static final ArrayList<String> ATTRIBUTE_LIST = new ArrayList<>();

    static {
        ATTRIBUTE_LIST.add(DataAttributeConstants.RESULT);
        ATTRIBUTE_LIST.add(DataAttributeConstants.TABLE);
        ATTRIBUTE_LIST.add(DataAttributeConstants.START);
        ATTRIBUTE_LIST.add(DataAttributeConstants.STOP);
        ATTRIBUTE_LIST.add(DataAttributeConstants.TIME);
        ATTRIBUTE_LIST.add(DataAttributeConstants.VALUE);
        ATTRIBUTE_LIST.add(DataAttributeConstants.FIELD);
        ATTRIBUTE_LIST.add(DataAttributeConstants.MEASUREMENT);
    }

    public static final String RESULT = "result";

    public static final String TABLE = "table";

    public static final String START = "_start";

    public static final String STOP = "_stop";

    public static final String TIME = "_time";

    public static final String VALUE = "_value";

    public static final String FIELD = "_field";

    public static final String MEASUREMENT = "_measurement";


}
