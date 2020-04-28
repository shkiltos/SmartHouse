package com.anton.smarthouse.services;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.devices.OnOffDevice;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
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

    public DeviceService() {}

    public void switchDevice(String msg) {
        Device d = userDevices.get("anton@mail.com").get(0);
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
