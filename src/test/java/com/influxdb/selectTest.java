package com.influxdb;

import com.influxdb.client.BucketsApi;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.domain.Label;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.vo.InfluxData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void exportCsvTest1() throws Exception {
        byte[] bytes = new byte[0];
        ArrayList<Object[]> cellList = new ArrayList<>();
        Object[] string1 = {"hello","ccc"};
        cellList.add(string1);
        // 创建一个字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, "UTF-8");

        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT);
        csvPrinter.printRecords(cellList);
        csvPrinter.flush();
        String s = byteArrayOutputStream.toString("UTF-8");
        System.out.println(s);
        bytes = byteArrayOutputStream.toString("UTF-8").getBytes();
        if (csvPrinter != null) {
            csvPrinter.close();
        }
        bufferedWriter.close();
        outputStreamWriter.close();
        byteArrayOutputStream.close();

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void exportCsvTest2()  {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("qqq");//每一个strings是一行
        strings.add("aaa");
        ArrayList<InfluxData> dataList = new ArrayList<>();
        

        if (strings!=null && strings.size() > 0){
            // 表格头
            String[] headArr = new String[]{"PointId", "X", "Y"};
            //CSV文件路径及名称
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String filePath = "D:\\java"; //CSV文件路径
            String fileName = "CSV_"+ df.format(localDateTime) +".csv";//CSV文件名称
            File csvFile = null;
            BufferedWriter csvWriter = null;
            try {
                csvFile = new File(filePath + File.separator + fileName);
                File parent = csvFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                csvFile.createNewFile();

                // GB2312使正确读取分隔符","
                csvWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

                // 写入文件头部标题行
                csvWriter.write(String.join(",", headArr));
                csvWriter.newLine();

                // 写入文件内容
                for (String point : strings) {
                    csvWriter.write(point);
                    csvWriter.newLine();
                }
                csvWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
