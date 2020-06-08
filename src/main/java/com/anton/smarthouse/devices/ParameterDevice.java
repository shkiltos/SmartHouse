package com.anton.smarthouse.devices;

import com.anton.smarthouse.services.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.UUID;

import static com.anton.smarthouse.services.DeviceService.brokerURL;
import static com.anton.smarthouse.services.DeviceService.dataStore;

@Slf4j
public class ParameterDevice implements Device {
    public String id;
    private String topic;
    private String state;
    private double energyConsumption;
    private IMqttClient client;

    private final DeviceService deviceService;

    public ParameterDevice(String id, String state, String topic, Double energyConsumption, DeviceService deviceService) {
        this.deviceService = deviceService;
        this.id = id;
        this.topic = topic;
        this.energyConsumption = energyConsumption;
        this.state = state;
        try {
            String clientId = UUID.randomUUID().toString();
            this.client = new MqttClient(brokerURL, clientId, new MqttDefaultFilePersistence(dataStore));

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);  //be careful with that
            options.setConnectionTimeout(10);
            client.connect(options);

        } catch (MqttSecurityException e) {
            log.error("error mqtt security");
        } catch (MqttException e) {
            log.error("error mqtt");
        }
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String value) {
        this.topic = value;
    }

    public String getId() {
        return this.id;
    }

    public double getEnergyConsumption() {
        return this.energyConsumption;
    }

    public String publish(String str) throws MqttException {
        if (!client.isConnected()) {
            log.error("Client not connected.");
            return null;
        }
        MqttMessage msg = new MqttMessage(str.getBytes());
        msg.setQos(0);
//        msg.setRetained(true);
        client.publish(topic, msg);
        this.updateState(msg.toString());

        return msg.toString();
    }

    private void updateState(String msg) {
        this.state = msg;
        this.deviceService.updateState(this.id, this.state);
        log.info("Updated state for " + this.id + " : " + this.topic + " to " + this.state);
    }
}
