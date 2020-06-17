package com.anton.smarthouse.model.dto;

import com.anton.smarthouse.model.Camera;
import lombok.Data;

import java.util.List;

@Data
public class UserSettings {
    private String userName;
    private double maxEnergyConsumption;
    private String picture;
    private List<Camera> cams;
}
