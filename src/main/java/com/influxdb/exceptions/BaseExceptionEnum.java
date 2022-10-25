package com.influxdb.exceptions;

public enum BaseExceptionEnum {
    DURATION_NOT_MATCH(14001, "duration is illegal!"),

    PRIMARY_EMAIL_REPEATED(14002, "PrimaryEmail is existed!");
    private Integer code;

    private String msg;

    BaseExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
