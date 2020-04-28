package com.anton.smarthouse.controllers;

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
}
