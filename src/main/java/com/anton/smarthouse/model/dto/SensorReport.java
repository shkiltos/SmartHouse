package com.anton.smarthouse.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SensorReport {
    private String deviceId;
    private String name;
    private String data;
    private String dimension;
    private List<String> recentData;

    public SensorReport(String deviceId, String name, String data, String dimension, List<String> recentData) {
        this.deviceId = deviceId;
        this.name = name;
        this.data = data;
        this.dimension = dimension;
        this.recentData = recentData;
    }
}
