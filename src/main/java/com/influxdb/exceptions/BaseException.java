package com.influxdb.exceptions;

import java.util.Objects;

public class BaseException extends RuntimeException{
    protected  Integer code;

    public BaseException(BaseExceptionEnum baseExceptionEnum){
        super(baseExceptionEnum.getMsg());
        this.code = baseExceptionEnum.getCode();
    }

    public BaseException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof BaseException)) {
            return false;
        }
        BaseException that = (BaseException) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
