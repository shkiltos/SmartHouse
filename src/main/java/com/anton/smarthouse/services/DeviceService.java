package com.anton.smarthouse.services;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.devices.OnOffDevice;
import com.anton.smarthouse.devices.SensorDevice;
import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class DeviceService {

    public static final String brokerURL = "tcp://localhost:1883";

    public static final String dataStore = "mqttconnections";

    public static final ConcurrentMap<String, List<Device>> userDevices = new ConcurrentHashMap<>();

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceEntity create(DeviceEntity device, String userId) {
        device.setUserId(userId);
        return deviceRepository.save(device);
    }

    public Optional<DeviceEntity> findById(String deviceId) {
        return deviceRepository.findById(deviceId);
    }

    public List<DeviceEntity> findAll(String userId) {
        return deviceRepository.findDeviceEntitiesByUserId(userId);
    }

    public DeviceEntity update(String id, DeviceEntity device, String userId) {
        device.setId(id);
        device.setUserId(userId);
        return deviceRepository.save(device);
    }

    public void delete(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public void switchDevice(String msg) {
        Device d = userDevices.get("toha.srednii@gmail.com").get(0);
        if (d instanceof OnOffDevice) {
            OnOffDevice onOffDevice = (OnOffDevice) d;
            try {
                onOffDevice.publish(msg);
            } catch (MqttException e) {
                log.error("publish error");
            }
        } else {
            log.error("cast error");
        }

    }

    public void initDevices(UserEntity user, List<DeviceEntity> devices) {
        List<Device> deviceList = new ArrayList<>();
        for (DeviceEntity device : devices) {
            try {
                switch (device.getType()) {
                    case "onoffdevice": {
                        OnOffDevice onOffDevice = new OnOffDevice(device.getState().equals("0") ? false : true, device.getTopic());
                        onOffDevice.subscribe();
                        deviceList.add(onOffDevice);
                        log.info("Added onoffdevice " + device.getName() + " for " + user.getEmail());
                        break;
                    }
                    case "sensor": {
                        SensorDevice sensorDevice = new SensorDevice(device.getTopic());
                        sensorDevice.subscribe();
                        deviceList.add(sensorDevice);
                        log.info("Added sensor " + device.getName() + " for " + user.getEmail());
                        break;
                    }
                }
//                OnOffDevice onOffDevice = new OnOffDevice(false, "wemos/led");
//                onOffDevice.subscribe();
//                onOffDevice.publish();
//                deviceList.add(onOffDevice);
            } catch (MqttException | InterruptedException e) {
//            e.printStackTrace();
                log.info("init failed for device " + device.getName());
            }
        }

        userDevices.put(user.getEmail(), deviceList);
    }

}
