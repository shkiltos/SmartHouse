package com.anton.smarthouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "history")
@Data
public class HistoryItemEntity {
    @Id
    private String id;
    private String deviceId;
    private String state;
    private LocalDateTime date;

    public HistoryItemEntity(String deviceId, String state, LocalDateTime date) {
        this.deviceId = deviceId;
        this.state = state;
        this.date = date;
    }
}
