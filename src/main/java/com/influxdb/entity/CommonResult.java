package com.influxdb.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.influxdb.utils.CommonResultSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * class to return front-end
 *
 * @author qhy
 * @create 2022-10-22
 */
@JsonSerialize(using = CommonResultSerializer.class)
@Data
@ApiModel("common result")
public class CommonResult<T> {

    @ApiModelProperty("status code,such as 404,200")
    private int code;

    @ApiModelProperty("alert message for user,such as success and fail")
    private String message;

    @ApiModelProperty("data that user want,such as InfluxData")
    private T data;

    @ApiModelProperty("this has no particular usage yet")
    private Map<String, Object> resultMap = new HashMap<>();

    public CommonResult<T> code(HttpStatus status) {
        this.code = status.value();
        return this;
    }

    public CommonResult<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public CommonResult<T> message(String message) {
        this.message = message;
        return this;
    }

    public CommonResult<T> data(T data) {
        this.data = data;
        return this;
    }

    public CommonResult<T> success() {
        this.code(HttpStatus.OK);
        this.message(HttpStatus.OK.getReasonPhrase());
        return this;
    }

    public CommonResult<T> fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        this.message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return this;
    }

    public CommonResult<T> put(String key, Object value) {
        resultMap.put(key, value);
        return this;
    }

    public Object get(String key) {
        if ("code".equals(key)) {
            return code;
        }
        if ("message".equals(key)) {
            return message;
        }
        if ("data".equals(key)) {
            return data;
        }
        return resultMap.get(key);
    }

}
