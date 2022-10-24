package com.influxdb.service;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.ChecksApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.entity.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class SelectServiceImpl implements SelectService {

    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;

    /**
     *
     * @param date 日期
     * @return 数据
     */
    @Override
    public Data selectWithDate(String date) {

        return null;
    }

    @Override
    public Data selectByTag(List<String> tag) {
        return null;
    }
}
