package com.anton.smarthouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "devices")
@Data
public class DeviceEntity {
    @Id
    private String deviceId;
    private String name;
    @Field("userName")
    private String userName;
    private String topic;
    private String state;
    private String data;
    private List<String> recentData;
}
