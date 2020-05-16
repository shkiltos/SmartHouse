package com.anton.smarthouse.devices;

import com.anton.smarthouse.EngineTemperatureSensor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.anton.smarthouse.services.DeviceService.brokerURL;
import static com.anton.smarthouse.services.DeviceService.dataStore;

@Slf4j
public class OnOffDevice implements Device {
    private String topic;
    private boolean state;
    private IMqttClient client;

    public OnOffDevice(boolean state, String topic) {
        this.state = state;
        this.topic = topic;
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

    public void subscribe() throws MqttException, InterruptedException {
//        CountDownLatch receivedSignal = new CountDownLatch(1);

        client.subscribe(this.topic, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            log.info(String.format("Message received: topic=" + topic + ", payload=" + new String(payload)));
//            receivedSignal.countDown();
        });
//        receivedSignal.await(10, TimeUnit.MINUTES);
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

        return msg.toString();
    }
}
