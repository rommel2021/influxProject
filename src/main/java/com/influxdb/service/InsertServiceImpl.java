package com.influxdb.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class InsertServiceImpl implements InsertService {
    @Autowired
    private InfluxDB influxDB;
    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;
    @Override
    public int insert(String data) {
//        int count = (int) (Math.random() * 100);
//        Pong pong = influxDB.ping();
//        log.info(pong.toString());
//        System.out.println(pong);
//
//        Point point = Point.measurement("gpu")
//                .tag("version", "RTX3060")  // url字段
//                .addField("count", count)        // 统计数据
//                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
//                .build();
//
//        // 往test库写数据
//        try{
//            influxDB.write("mydb", "autogen", point);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        log.info("上报统计数据：" + count);
        // todo 用正则表达式来匹配语句是否合法
        try{
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
            writeApi.writeRecord(WritePrecision.NS, data);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

//        String data = "gpu,version=RTX3060 value=0.98";

        influxDBClient.close();
        return 1;
    }
}
