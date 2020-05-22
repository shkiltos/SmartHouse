package com.anton.smarthouse.controllers;

import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.services.DeviceService;
import com.anton.smarthouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;

    @Autowired
    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @GetMapping(value = "/hasConnection")
    public boolean hasConnection() {
        UserEntity user = userService.getUser();
        if (user == null) return false;
        return deviceService.hasConnection(user);
    }

    @GetMapping(value = "/init")
    public boolean init() {
        UserEntity user = userService.getUser();
        if (user == null) return false;
        deviceService.initDevices(user, deviceService.findAll(user.getId()));
        return true;
    }

    @GetMapping
    public ResponseEntity<List<DeviceEntity>> findAll() {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.findAll(user.getId()));
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceEntity> findById(@PathVariable String deviceId) {
        Optional<DeviceEntity> device = deviceService.findById(deviceId);

        return device.map(d -> ResponseEntity.ok().body(d))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeviceEntity> create(@RequestBody DeviceEntity device) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.create(device, user.getId()));
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceEntity> update(@PathVariable String deviceId, @RequestBody DeviceEntity device) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.update(deviceId, device, user.getId()));
    }

    @DeleteMapping("/{deviceId}")
    public void delete(@PathVariable String deviceId) {
        deviceService.delete(deviceId);
    }

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "msg") String message) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return deviceService.publishMessage(user, deviceId, message) ? ResponseEntity.ok().body(message) : ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/refreshUserDevices")
    public void refreshUserDevices(@RequestParam(value = "userName") String userName) {
        //add devices to user-devices map
    }
}
