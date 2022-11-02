package com.influxdb.service;

import com.influxdb.vo.InfluxData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SelectService {
    List<InfluxData> selectWithDate(String duration, String measurement);

    List<InfluxData> selectByTag(List<String> tag, String measurement);

    String exportCsvByTags(List<String> tag, String measurement);

}
