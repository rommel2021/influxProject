package com.influxdb.dto;


import com.influxdb.annotations.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * 传入的数据
 * @create 2022-11-2
 */
@Data
@AllArgsConstructor
public class InsertVo {
    @Column(measurement = true)
    String measurement;

    @Column(tag = true)
    String location;

    @Column
    String value;

    @Column(timestamp = true)
    Instant timestamp;


}
