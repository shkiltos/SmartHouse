package com.anton.smarthouse.controllers;

import com.anton.smarthouse.exception.NotFoundEntity;
import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.dto.SensorReport;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.services.DeviceService;
import com.anton.smarthouse.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public boolean initConnection() {
        UserEntity user = userService.getUser();
        if (user == null) return false;
        deviceService.initDevices(user, deviceService.findAll(user.getId()));
        return true;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ApiOperation(value = "Method of getting all user devices from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devices found: ", response = DeviceEntity.class),
            @ApiResponse(code = 404, message = "Exception occurred : ", response = String.class)})
    public ResponseEntity<List<DeviceEntity>> findAll() {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.findAll(user.getId()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/sensors")
    @ApiOperation(value = "Method of getting all user sensors from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sensors found: ", response = DeviceEntity.class),
            @ApiResponse(code = 404, message = "Exception occurred : ", response = String.class)})
    public List<SensorReport> getSensorReports() {
        UserEntity user = userService.getUser();
        if (user == null) new NotFoundEntity(String.format("User not found"));
        return deviceService.getSensorReports(user.getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{deviceId}")
    @ApiOperation(value = "Method of getting all user devices from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Device found: ", response = DeviceEntity.class),
            @ApiResponse(code = 404, message = "Device not found : ", response = String.class)})
    public DeviceEntity findById(@PathVariable String deviceId) {
        return deviceService.findById(deviceId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "Method of adding a new device to DB")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Device created: ", response = DeviceEntity.class),
            @ApiResponse(code = 400, message = "Exception occurred : ", response = String.class)})
    public ResponseEntity<DeviceEntity> create(@RequestBody DeviceEntity device) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        DeviceEntity de = deviceService.create(device, user.getId());
        if (de != null) deviceService.refreshDeviceConnection(user, device);
        return ResponseEntity.status(HttpStatus.CREATED).body(de);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceEntity> update(@PathVariable String deviceId, @RequestBody DeviceEntity device) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        DeviceEntity de = deviceService.update(deviceId, device, user.getId());
        if (de != null) deviceService.refreshDeviceConnection(user, device);
        return ResponseEntity.ok().body(de);
    }

    @DeleteMapping("/{deviceId}")
    public void delete(@PathVariable String deviceId) {
        deviceService.delete(deviceId);
    }

    @PostMapping("/publishOnOffMessage")
    public ResponseEntity<String> publishOnOffMessage(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "msg") String message) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.publishOnOffMessage(user, deviceId, message));
    }

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "msg") String message) {
        UserEntity user = userService.getUser();
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(deviceService.publishMessage(user, deviceId, message));
    }

    @GetMapping(value = "/refreshUserDevices")
    public void refreshUserDevices(@RequestParam(value = "userName") String userName) {
        //add devices to user-devices map
    }
}
