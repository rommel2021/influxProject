package com.influxdb.service;

import com.influxdb.entity.Data;

import java.util.List;

public interface SelectService {
    Data selectWithDate(String date);

    Data selectByTag(List<String> tag);
}
