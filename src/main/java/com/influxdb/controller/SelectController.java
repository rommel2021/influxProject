package com.influxdb.controller;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.entity.CommonResult;
import com.influxdb.service.SelectService;
import com.influxdb.vo.InfluxData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/select")
public class SelectController {

    @Resource
    SelectService selectService;

    @GetMapping("/date")
    public CommonResult selectWithDate(@RequestParam String duration, @RequestParam String measurement){
        List<InfluxData> data = selectService.selectWithDate(duration, measurement);
        return new CommonResult().success().data(data);
    }

    /**
     * 这里的tag传参数的时候只需要在字符串之间加逗号就可以
     * @param tag
     * @param measurement
     * @return
     */
    @GetMapping("/tag")
    public CommonResult selectWithTag(@RequestParam List<String> tag, @RequestParam String measurement){
        List<InfluxData> influxData = selectService.selectByTag(tag, measurement);
        return new CommonResult().success().data(influxData);
    }
}
