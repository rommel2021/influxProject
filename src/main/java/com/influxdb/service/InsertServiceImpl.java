package com.influxdb.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service("insertService")
@Slf4j
public class InsertServiceImpl implements InsertService {
    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;
    @Override
    public int insert(String query) {
        String pattern = "[!-z]+,([!-z]+=[!-z]+,?)+ [!-z]+=[!-z]+";
        boolean isMatch = Pattern.matches(pattern, query);
        if (!isMatch) {
            return 401;
        }
        String[] s = query.split(" ");
        String[] measurementAndTags = s[0].split(",");
        String[] fields = s[1].split(",");
        HashMap<String, String> tags = new HashMap<>();
        for(int i=1;i<measurementAndTags.length;i++){
            String[] keyValue = measurementAndTags[i].split("=");
            tags.put(keyValue[0], keyValue[1]);
        }
        HashMap<String, Object> fieldsMap = new HashMap<>();
        for(int i=0;i<fields.length;i++){
            String[] keyValue = fields[i].split("=");
            fieldsMap.put(keyValue[0], keyValue[1]);
        }
        Point point = Point.measurement(measurementAndTags[0]).addTags(tags).addFields(fieldsMap)
                .time(Instant.now(), WritePrecision.MS);
        System.out.println(point);
        try{
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            writeApi.writePoint(point);
        } catch (Exception e){
            e.printStackTrace();
            return 501;
        }
//        String data = "gpu,version=RTX3060 value=0.98";
        influxDBClient.close();
        return 200;
    }
}
