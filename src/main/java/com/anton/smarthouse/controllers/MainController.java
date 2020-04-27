package com.anton.smarthouse.controllers;

import com.anton.smarthouse.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
class MainController {

    @Autowired
    public MainController() {}

//    @GetMapping(value = "/user")
//    public User getUser() {
//        return userService.getUser();
//    }

    @GetMapping(value = "/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("ok");
    }
}
