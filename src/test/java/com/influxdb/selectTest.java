package com.influxdb;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.domain.Label;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest(classes = InfluxProjectApplication.class)
@Rollback
public class selectTest {
    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;

    @Value("${influx.bucket}")
    String bucket;

    @Test
    public void influxdbTest() {
        // 测试queryApi的功能
        // 通过这次测试结果可知，查到的record的getValue方法返回值组成：
        // result | table | _start | _stop | _time | _value | _field | _measurement
        // 剩下的都是tag
        QueryApi queryApi = influxDBClient.getQueryApi();
        String flux = String.format("from(bucket: \""+bucket+"\")\n" +
                "  |> range(start: -7d)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == \"gpu\")\n" +
                "  |> yield(name: \"mean\")");
        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            System.out.println("field:\tvalue");
            for (FluxRecord record : records) {
                System.out.println(record.getField() + ":\t" + record.getValue());
                Map<String, Object> values = record.getValues();
                for(Map.Entry<String, Object> entry:values.entrySet()){
                    System.out.println(entry.getKey()+" "+entry.getValue());
                }
                System.out.println("-------------");
            }
        }

    }

    @Test
    public void testDateformat(){
        System.out.println(Instant.now());
    }

    @Test
    public void bucketsTest(){
        BucketsApi bucketsApi = influxDBClient.getBucketsApi();
        Bucket mydb = bucketsApi.findBucketByName("mydb");
        List<BucketRetentionRules> retentionRules = mydb.getRetentionRules();
        // 这个大概是设置有效时间的
        for (BucketRetentionRules rules:retentionRules){
            System.out.println(rules.toString());
        }
        List<Label> labels = bucketsApi.getLabels(mydb);
        for(Label label:labels){
            System.out.println(label.getName());
        }
    }
}
