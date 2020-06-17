package com.anton.smarthouse.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "dashboardScheme")
@Data
public class DashboardScheme {
    @Id
    private String id;
    private String userId;
    private String title;
    private List<DashboardSchemeItem> dashboardSchemeItems;
    private Binary image;
}
