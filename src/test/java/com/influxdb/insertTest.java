package com.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest(classes = InfluxProjectApplication.class)
@Rollback
public class insertTest {
    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;

    @Test
    public void fieldAndTag() {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
        Map<String, String> tags = new HashMap<>();
        tags.put("version","3070");
        tags.put("p", "240w");
        Point gpu = Point.measurement("gpu").addTags(tags)
                        .addField("price","￥3499")
                        .addField("number",996)
                        .time(Instant.now(),WritePrecision.MS);
        writeApi.writePoint(gpu);
    }
}