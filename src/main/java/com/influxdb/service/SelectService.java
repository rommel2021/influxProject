package com.influxdb.service;

import com.influxdb.vo.InfluxData;

import java.util.List;

public interface SelectService {
    List<InfluxData> selectWithDate(String duration, String measurement);

    InfluxData selectByTag(List<String> tag);
}
