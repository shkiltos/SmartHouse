package com.anton.smarthouse.controllers;

import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/init")
    public String init() {
        deviceService.initDevices();
        return "turned on";
    }

    @PostMapping("/switchDevice")
    public boolean switchDevice(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "msg") String message) {
        deviceService.switchDevice(message);
        return true;
    }

    @GetMapping(value = "/getUserDevices")
    public List<DeviceEntity> getUserDevices(@RequestParam(value = "userName") String userName) {
        return deviceService.getUserDevices(userName);
    }

    @GetMapping(value = "/refreshUserDevices")
    public void refreshUserDevices(@RequestParam(value = "userName") String userName) {
        //add devices to user-devices map
    }
}
