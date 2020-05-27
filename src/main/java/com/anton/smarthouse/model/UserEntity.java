package com.anton.smarthouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Data
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String picture;
    private Double maxEnergyConsumption;
    private LocalDateTime lastVisit;

    public UserEntity(String name, String email, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}