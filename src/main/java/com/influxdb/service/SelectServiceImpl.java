package com.influxdb.service;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.constants.DataAttributeConstants;
import com.influxdb.constants.DurationConstants;
import com.influxdb.exceptions.BaseException;
import com.influxdb.exceptions.BaseExceptionEnum;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.vo.InfluxData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    String bucket;

    /**
     * @param duration    持续时间
     * @param measurement 表名
     * @return 数据
     * @create 2022-10-25
     */
    @Override
    public List<InfluxData> selectWithDate(String duration, String measurement) {

        //check duration and measurement valid
        if (!DurationConstants.DURATIONS.contains(duration)) {
            throw new BaseException(BaseExceptionEnum.DURATION_NOT_MATCH);
        }

        QueryApi queryApi = influxDBClient.getQueryApi();
        String flux = String.format("from(bucket: \"" + bucket + "\")\n" +
                "  |> range(start: -" + duration + ")\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"" + measurement + "\")\n" +
                "  |> yield(name: \"mean\")");
        List<FluxTable> fluxTables = queryApi.query(flux);
        ArrayList<InfluxData> influxDataList = new ArrayList<>();
        for (FluxTable fluxTable : fluxTables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                Map<String, Object> values = record.getValues();
                // 这个record就是一个包含一条数据所有属性的对象
                String result = (String) values.get(DataAttributeConstants.RESULT);
                Long table = (Long) values.get(DataAttributeConstants.TABLE);
                Instant start = (Instant) values.get(DataAttributeConstants.START);
                Instant stop = (Instant) values.get(DataAttributeConstants.STOP);
                Instant time = (Instant) values.get(DataAttributeConstants.TIME);
                Object value = values.get(DataAttributeConstants.VALUE);
                String field = (String) values.get(DataAttributeConstants.FIELD);
                String realMeasurement = (String) values.get(DataAttributeConstants.MEASUREMENT);
                HashMap<String, String> tags = new HashMap<>();
                for (Map.Entry entry:values.entrySet()){
                    if (!DataAttributeConstants.ATTRIBUTE_LIST.contains(entry.getKey())) {
                        tags.put((String) entry.getKey(), (String) entry.getValue());
                    }
                }
                InfluxData influxData = new InfluxData(result, table, start, stop, time, value, field, realMeasurement, tags);
                influxDataList.add(influxData);
            }
        }
        return influxDataList;
    }

    @Override
    public InfluxData selectByTag(List<String> tag) {
        return null;
    }
}
