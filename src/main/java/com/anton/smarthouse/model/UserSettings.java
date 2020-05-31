package com.anton.smarthouse.model;

import lombok.Data;

import java.util.List;

@Data
public class UserSettings {
    private String userName;
    private double maxEnergyConsumption;
    private String picture;
    private List<Camera> cams;
}
