package com.anton.smarthouse.controllers;

import com.anton.smarthouse.exception.NotFoundEntity;
import com.anton.smarthouse.model.DashboardScheme;
import com.anton.smarthouse.model.DashboardSchemeItem;
import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.model.dto.DashboardSchemeImage64;
import com.anton.smarthouse.services.DashboardSchemeService;
import com.anton.smarthouse.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/dashboardScheme")
public class DashboardSchemeController {

    private final DashboardSchemeService dashboardSchemeService;
    private final UserService userService;

    @Autowired
    public DashboardSchemeController(DashboardSchemeService dashboardSchemeService, UserService userService) {
        this.dashboardSchemeService = dashboardSchemeService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ApiOperation(value = "Method of getting all user dashboard schemes from DB")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dashboard scheme found: ", response = DeviceEntity.class),
            @ApiResponse(code = 404, message = "Exception occurred : ", response = String.class)})
    public List<DashboardSchemeImage64> findAll() {
        UserEntity user = userService.getUser();
        if (user == null) throw new NotFoundEntity("User not found");
        return dashboardSchemeService.findAll(user.getId()).stream().map(s -> new DashboardSchemeImage64(s)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> createDashboardScheme(@RequestPart("title") String title,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("dashboardItems") List<DashboardSchemeItem> dashboardItems) throws IOException {
        UserEntity user = userService.getUser();
        if (user == null) throw new NotFoundEntity("User not found");
        String id = dashboardSchemeService.createDashboardScheme(user.getId(),title, image, dashboardItems);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/noImage")
    public ResponseEntity<String> createDashboardSchemeWithoutImage(@RequestPart("title") String title,
                                                                   @RequestPart("dashboardItems") List<DashboardSchemeItem> dashboardItems) throws IOException {
        UserEntity user = userService.getUser();
        if (user == null) throw new NotFoundEntity("User not found");
        String id = dashboardSchemeService.createDashboardScheme(user.getId(),title, null, dashboardItems);
        return ResponseEntity.ok(id);
    }

    @PutMapping
    public ResponseEntity<String> updateDashboardScheme(@RequestPart("id") String id,
                                        @RequestPart("title") String title,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("dashboardItems") List<DashboardSchemeItem> dashboardItems) throws IOException {
        UserEntity user = userService.getUser();
        if (user == null) throw new NotFoundEntity("User not found");
        String schemeId = dashboardSchemeService.updateDashboardScheme(user.getId(), id, title, image, dashboardItems);
        return ResponseEntity.ok(schemeId);
    }

    @PutMapping("noImage")
    public ResponseEntity<String> updateDashboardSchemeWithoutImage(@RequestPart("id") String id,
                                        @RequestPart("title") String title,
                                        @RequestPart("dashboardItems") List<DashboardSchemeItem> dashboardItems) throws IOException {
        UserEntity user = userService.getUser();
        if (user == null) throw new NotFoundEntity("User not found");
        String schemeId = dashboardSchemeService.updateDashboardScheme(user.getId(), id, title, null, dashboardItems);
        return ResponseEntity.ok(schemeId);
    }

    @GetMapping("/{id}")
    public DashboardSchemeImage64 getDashboardScheme(@PathVariable String id) {
        DashboardScheme dashboardScheme = dashboardSchemeService.getDashboardScheme(id);
        return new DashboardSchemeImage64(dashboardScheme);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        dashboardSchemeService.delete(id);
    }
}
