package com.influxdb.controller;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/select")
public class SelectController {

    @GetMapping("/date")
    public CommonResult insertWithQuery(@RequestParam String date){

        return new CommonResult();
    }

    @GetMapping("/tag")
    public CommonResult insertWithTag(@RequestParam List<String> tag){

        return new CommonResult();
    }
}
