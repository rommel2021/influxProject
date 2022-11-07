package com.influxdb.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * 用于存放返回来的数据
 * @author qhy
 * @create 2022-10-24
 */
@Data
@AllArgsConstructor
@ApiModel("influx data")
public class InfluxData {
    private String result;

    private Long table;

    private Instant start;

    private Instant end;

    private Instant time;

    private Object value;

    private String field;

    private String measurement;

    private Map<String, String> tags;

    public String[] toStringArray(){
        return null;
    }

}
