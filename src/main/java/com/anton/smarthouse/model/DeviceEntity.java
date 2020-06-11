package com.anton.smarthouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Queue;

@Document(collection = "devices")
@Data
public class DeviceEntity {
    @Id
    private String id;
    private String name;
    private String userId;
    private String topic;
    private String type;
    private String state;
    private String dimension;
    private String switchPattern;
    private String description;
    private String data;
    private List<String> recentData;
    private Double energyConsumption;
    private String image;

    public DeviceEntity(String id, String name, String userId, String topic) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.topic = topic;
    }
}
