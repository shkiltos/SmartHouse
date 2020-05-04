package com.anton.smarthouse.services;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.devices.OnOffDevice;
import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.repository.DeviceRepository;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class DeviceService {

    public static final String brokerURL = "tcp://localhost:1883";

    public static final String dataStore = "mqttconnections";

    public static final ConcurrentMap<String, List<Device>> userDevices = new ConcurrentHashMap<>();

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {this.deviceRepository = deviceRepository;}

    public void switchDevice(String msg) {
        Device d = userDevices.get("toha.srednii@gmail.com").get(0);
        if (d instanceof OnOffDevice) {
            OnOffDevice onOffDevice = (OnOffDevice)d;
            try {
                onOffDevice.publish(msg);
            } catch (MqttException e) {
                System.out.println("publish error");
            }
        } else {
            System.out.println("cast error");
        }

    }

    public void initDevices() {
        List<Device> deviceList = new ArrayList<>();
        userDevices.put("toha.srednii@gmail.com", deviceList);

        try {
            OnOffDevice onOffDevice = new OnOffDevice(false, "wemos/led");
            onOffDevice.subscribe();
//            onOffDevice.publish();
            deviceList.add(onOffDevice);

            System.out.println(userDevices.get("toha.srednii@gmail.com"));
        } catch (MqttException | InterruptedException e) {
//            e.printStackTrace();
            System.out.println("error");
        }
    }

    public List<DeviceEntity> getUserDevices(String userName) {
        return deviceRepository.findDeviceEntitiesByUserName(userName);
    }

}
