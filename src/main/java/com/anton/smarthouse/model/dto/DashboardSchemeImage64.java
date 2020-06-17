package com.anton.smarthouse.model.dto;

import com.anton.smarthouse.model.DashboardScheme;
import com.anton.smarthouse.model.DashboardSchemeItem;
import lombok.Data;
import org.bson.types.Binary;

import java.util.Base64;
import java.util.List;

@Data
public class DashboardSchemeImage64 {
    private String id;
    private String title;
    private List<DashboardSchemeItem> dashboardSchemeItems;
    private String image;

    public DashboardSchemeImage64(DashboardScheme dashboardScheme) {
        this.id = dashboardScheme.getId();
        this.title = dashboardScheme.getTitle();
        this.dashboardSchemeItems = dashboardScheme.getDashboardSchemeItems();
        this.image = dashboardScheme.getImage() == null ? null : Base64.getEncoder().encodeToString(dashboardScheme.getImage().getData());
    }
}
