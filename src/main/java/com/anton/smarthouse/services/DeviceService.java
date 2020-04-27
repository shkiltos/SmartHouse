package com.anton.smarthouse.services;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.devices.OnOffDevice;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class DeviceService {

    public static final ConcurrentMap<String, List<Device>> userDevices = new ConcurrentHashMap<>();

    public DeviceService() {}

    public void turnOnDevice() {
        List<Device> deviceList = new ArrayList<>();
        userDevices.put("anton@mail.com", deviceList);

        try {
            OnOffDevice onOffDevice = new OnOffDevice(false, "wemos/led");
            onOffDevice.subscribe();
//            onOffDevice.publish();
            deviceList.add(onOffDevice);

            System.out.println(userDevices.get("anton@mail.com"));
        } catch (MqttException | InterruptedException e) {
//            e.printStackTrace();
            System.out.println("error");
        }
    }

}
