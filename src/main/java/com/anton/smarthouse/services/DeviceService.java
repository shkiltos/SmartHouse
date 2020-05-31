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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static com.anton.smarthouse.devices.OnOffDevice.DEFAULT_SWITCH_PATTERN;

@Service
@Slf4j
public class DeviceService {

    public static final String brokerURL = "tcp://localhost:1883";

    public static final String dataStore = "mqttconnections";

    public static final String ASK_SENSORS_MSG = "-";

    public static final ConcurrentMap<String, List<Device>> userDevices = new ConcurrentHashMap<>();

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceEntity create(DeviceEntity device, String userId) {
        device.setUserId(userId);
        if (device.getSwitchPattern() == null) device.setSwitchPattern(DEFAULT_SWITCH_PATTERN);
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
        if (device.getSwitchPattern() == null) device.setSwitchPattern(DEFAULT_SWITCH_PATTERN);
        return deviceRepository.save(device);
    }

    public void delete(String deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public String publishMessage(UserEntity user, String deviceId, String msg) {
        List<OnOffDevice> activeDevices = userDevices.get(user.getEmail()).stream().filter(d -> d instanceof OnOffDevice).map(d -> (OnOffDevice) d).collect(Collectors.toList());
        Optional<OnOffDevice> device = activeDevices.stream().filter(d -> d.getId().equals(deviceId)).findFirst();
        if (device.isPresent()) {
            OnOffDevice onOffDevice = device.get();
            try {
                if (user.getMaxEnergyConsumption() != null && user.getMaxEnergyConsumption() != 0 && onOffDevice.getEnergyConsumption() > 0) {
                    if (exceedsEnergyLimit(user, onOffDevice, activeDevices)) {
                        return "exceed";
                    }
                }
                onOffDevice.publish(msg);
                return msg;
            } catch (MqttException e) {
                log.error("publish error");
            }
        }
        return "bad";
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
            Device d = null;
            try {
                d = establishDeviceConnection(user, device);
            } catch (MqttException | InterruptedException e) {
                log.error("Connection failed for device " + device.getName());
            }
            if (d != null) deviceList.add(d);
        }
        userDevices.put(user.getEmail(), deviceList);
    }

    private boolean exceedsEnergyLimit(UserEntity user, OnOffDevice device, List<OnOffDevice> activeDevices) {
        double currentEnergyConsumptionSum = 0;
        for (OnOffDevice ad : activeDevices) {
            if (!ad.getId().equals(device.getId()) && ad.isDeviceOn()) {
                currentEnergyConsumptionSum += ad.getEnergyConsumption();
            }
        }

        return currentEnergyConsumptionSum + device.getEnergyConsumption() > user.getMaxEnergyConsumption() ? true : false;
    }

    public Device establishDeviceConnection(UserEntity user, DeviceEntity deviceEntity) throws MqttException, InterruptedException {
        switch (deviceEntity.getType()) {
            case "onoffdevice": {
                OnOffDevice onOffDevice = new OnOffDevice(deviceEntity.getId(), deviceEntity.getState(), deviceEntity.getTopic(), deviceEntity.getSwitchPattern(), deviceEntity.getEnergyConsumption(), this);
                onOffDevice.subscribe();
                log.info("Connected onoffdevice: \"" + deviceEntity.getName() + "\" for " + user.getEmail());
                return onOffDevice;
            }
            case "sensor": {
                SensorDevice sensorDevice = new SensorDevice(deviceEntity.getId(), deviceEntity.getTopic(), this);
                sensorDevice.subscribe();
                log.info("Connected sensor: \"" + deviceEntity.getName() + "\" for " + user.getEmail());
                return sensorDevice;
            }
        }
        return null;
    }

    public void refreshDeviceConnection(UserEntity user, DeviceEntity deviceEntity) {
        List<Device> deviceList = userDevices.get(user.getEmail());
        if (deviceList != null) {
            deviceList.removeIf(d -> d.getId().equals(deviceEntity.getId()));
            try {
                deviceList.add(establishDeviceConnection(user, deviceEntity));
            } catch (MqttException | InterruptedException e) {
                log.error("Connection failed for device " + deviceEntity.getName());
            }
        }
    }
}
