package com.influxdb;

import com.influxdb.client.ChecksApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.Check;
import com.influxdb.client.domain.Label;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(classes = InfluxProjectApplication.class)
@Rollback
public class selectTest {
    @Autowired
    @Qualifier("InfluxDBClient")
    private InfluxDBClient influxDBClient;

    @Test
    public void influxdbTest(){
        //测试一下influxdbClient各个接口的用途
        // 先测一下checkApi
        ChecksApi checksApi = influxDBClient.getChecksApi();
        Check check = new Check();
        Label label = new Label();
        label.setName("version");
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(label);
        check.setLabels(labels);
        Check apiCheck = checksApi.createCheck(check);
        System.out.println(apiCheck);

    }
}
