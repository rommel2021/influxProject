package com.influxdb.controller;

import com.influxdb.entity.CommonResult;
import com.influxdb.service.InsertService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/insert")
public class InsertController {
    @Resource
    InsertService insertService;

    @PostMapping("/query")
    public CommonResult insertWithQuery(@RequestParam String data){
        int result = insertService.insert(data);
        if (result == 1)
            return new CommonResult().success().code(200).message("success to insert data");
        return new CommonResult().fail().code(404).message("fail to insert");
    }

    @GetMapping("/")
    public CommonResult getData(){

    }
}
