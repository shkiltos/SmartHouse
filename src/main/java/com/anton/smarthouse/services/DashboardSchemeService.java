package com.anton.smarthouse.services;

import com.anton.smarthouse.model.DashboardScheme;
import com.anton.smarthouse.model.DashboardSchemeItem;
import com.anton.smarthouse.repository.DashboardSchemeRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DashboardSchemeService {

    private final DashboardSchemeRepository dashboardSchemeRepository;

    @Autowired
    public DashboardSchemeService(DashboardSchemeRepository dashboardSchemeRepository) {
        this.dashboardSchemeRepository = dashboardSchemeRepository;
    }

    public List<DashboardScheme> findAll(String userId) {
        return dashboardSchemeRepository.findAllByUserId(userId);
    }

    public String createDashboardScheme(String userId, String title, MultipartFile file, List<DashboardSchemeItem> dashboardSchemeItems) throws IOException {
        DashboardScheme dashboardScheme = new DashboardScheme();
        dashboardScheme.setUserId(userId);
        dashboardScheme.setTitle(title.replaceAll("^\"+|\"+$", ""));
        dashboardScheme.setDashboardSchemeItems(dashboardSchemeItems);
        if (file != null) {
            dashboardScheme.setImage(
                    new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } else {
            dashboardScheme.setImage(null);
        }
        dashboardScheme = dashboardSchemeRepository.insert(dashboardScheme);
        return dashboardScheme.getId();
    }

    public String updateDashboardScheme(String userId, String id, String title, MultipartFile file, List<DashboardSchemeItem> dashboardSchemeItems) throws IOException {
        DashboardScheme dashboardScheme = dashboardSchemeRepository.findById(id.replaceAll("^\"+|\"+$", "")).get();
        dashboardScheme.setUserId(userId.replaceAll("^\"+|\"+$", ""));
        dashboardScheme.setTitle(title.replaceAll("^\"+|\"+$", ""));
        dashboardScheme.setDashboardSchemeItems(dashboardSchemeItems);
        if (file != null) {
            dashboardScheme.setImage(
                    new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        }
        dashboardScheme = dashboardSchemeRepository.save(dashboardScheme);
        return dashboardScheme.getId();
    }

    public DashboardScheme getDashboardScheme(String id) {
        return dashboardSchemeRepository.findById(id).get();
    }

    public void delete(String schemeId) {
        dashboardSchemeRepository.deleteById(schemeId);
    }
}
