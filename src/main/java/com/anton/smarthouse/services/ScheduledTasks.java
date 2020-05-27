package com.anton.smarthouse.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.devices.SensorDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

    private final DeviceService deviceService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public ScheduledTasks(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Scheduled(fixedRate = 106000)    //fixed delay
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 15000)
    public void askAllSensors() {
        deviceService.userDevices.forEach((email, dList) -> {
            dList.forEach(d -> {
                if (d instanceof SensorDevice) {
                    try {
                        ((SensorDevice) d).publish("-");
                    } catch (Exception e) {
                        log.error("mqtt exception occurred");
                    }
                }
            });
        });
    }
}
