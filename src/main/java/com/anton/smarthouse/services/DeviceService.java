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

    public boolean sendMessage(UserEntity user, String deviceId, String msg) {
        Optional<Device> device = userDevices.get(user.getEmail()).stream().filter(d -> d.getId().equals(deviceId)).findFirst();
        if(device.isPresent()) {
            if (device.get() instanceof OnOffDevice) {
                OnOffDevice onOffDevice = (OnOffDevice) device.get();
                try {
                    onOffDevice.publish(msg);
                    return true;
                } catch (MqttException e) {
                    log.error("publish error");
                }
            } else {
                log.error("cast error");
            }
        }
        return false;
    }

    public boolean updateData(String deviceId, String data) {
        Optional<DeviceEntity> deviceOptional = findById(deviceId);
        if (!deviceOptional.isPresent()) return false;
        DeviceEntity device = deviceOptional.get();
        device.setData(data);
        deviceRepository.save(device);
        return true;
    }

    public boolean updateState(String deviceId, String state) {
        Optional<DeviceEntity> deviceOptional = findById(deviceId);
        if (!deviceOptional.isPresent()) return false;
        DeviceEntity device = deviceOptional.get();
        device.setState(state);
        deviceRepository.save(device);
        return true;
    }

    public boolean hasConnection(UserEntity user) {
        return this.userDevices.containsKey(user.getEmail());
    }

    public void initDevices(UserEntity user, List<DeviceEntity> devices) {
        List<Device> deviceList = new ArrayList<>();
        for (DeviceEntity device : devices) {
            try {
                switch (device.getType()) {
                    case "onoffdevice": {
                        OnOffDevice onOffDevice = new OnOffDevice(device.getId(), device.getState(), device.getTopic(), this);
                        onOffDevice.subscribe();
                        deviceList.add(onOffDevice);
                        log.info("Added onoffdevice: \"" + device.getName() + "\" for " + user.getEmail());
                        break;
                    }
                    case "sensor": {
                        SensorDevice sensorDevice = new SensorDevice(device.getId(), device.getTopic(), this);
                        sensorDevice.subscribe();
                        deviceList.add(sensorDevice);
                        log.info("Added sensor: \"" + device.getName() + "\" for " + user.getEmail());
                        break;
                    }
                }
//                OnOffDevice onOffDevice = new OnOffDevice(false, "wemos/led");
//                onOffDevice.subscribe();
//                onOffDevice.publish();
//                deviceList.add(onOffDevice);
            } catch (MqttException | InterruptedException e) {
                log.error("init failed for device " + device.getName());
            }
        }

        userDevices.put(user.getEmail(), deviceList);
    }
}
