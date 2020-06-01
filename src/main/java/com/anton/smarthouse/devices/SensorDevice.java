package com.anton.smarthouse.devices;

import com.anton.smarthouse.services.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.anton.smarthouse.services.DeviceService.*;

@Slf4j
public class SensorDevice implements Device {
    private String id;
    private String topic;
    private String data;
    private IMqttClient client;

    private final DeviceService deviceService;

    public SensorDevice(String id, String topic, DeviceService deviceService) {
        this.deviceService = deviceService;
        this.id = id;
        this.topic = topic;
        try {
            String clientId = UUID.randomUUID().toString();
            this.client = new MqttClient(brokerURL, clientId, new MqttDefaultFilePersistence(dataStore));

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
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

    public void subscribe() throws MqttException{
        client.subscribe(this.topic, (topic, msg) -> {
            String payload = new String(msg.getPayload());
            if (payload.equals(ASK_SENSORS_MSG)) return;
            log.info("Message received: topic=" + topic + ", payload=" + payload);
            updateData(payload);
        });
    }

    private void updateData(String payload) {
        String previousData = this.data;
        this.data = payload;
        this.deviceService.updateData(this.id, payload, previousData);
        log.info("Updated data for " + this.id + " : " + this.topic + " to " + this.data);
    }

    public String publish(String message) throws MqttException {
//        if (!client.isConnected()) {
//            log.error("Client not connected.");
//            return null;
//        }
        MqttMessage msg = new MqttMessage(message.getBytes());
//        msg.setQos(0);
//        msg.setRetained(true);
        client.publish(topic, msg);
//
//        return msg.toString();
        return message;
    }

}
