package com.influxdb.controller;

import com.influxdb.entity.CommonResult;
import com.influxdb.service.InsertService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/insert")
public class InsertController {
    @Resource
    InsertService insertService;

    /**
     * 通过一个插入语句来加入数据项
     * @param query influxdb的一条插入语句
     * @return 插入成功还是失败
     */
    @PostMapping("/query")
    @ApiOperation("insert data to influxdb by a query")
    public CommonResult insertWithQuery(@RequestParam String query){
        int status = insertService.insert(query);
        if (status == 200)
            return new CommonResult().success().code(status).message("success to insert data");
        return new CommonResult().fail().code(status).message("fail to insert");
    }


}
