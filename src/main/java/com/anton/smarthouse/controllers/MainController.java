package com.anton.smarthouse.controllers;

import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.model.UserSettings;
import com.anton.smarthouse.services.DeviceService;
import com.anton.smarthouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {this.userService = userService;}

    @GetMapping(value = "/user")
    public UserEntity getUser() {
        return userService.getUser();
    }

    @PostMapping(value = "/user/settings")
    public UserSettings getUser(@RequestBody UserSettings settings) {
        return userService.updateSettings(settings);
    }

    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ok");
    }
}
