package com.anton.smarthouse.devices;

import com.anton.smarthouse.EngineTemperatureSensor;
import com.anton.smarthouse.services.DeviceService;
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
    public String id;
    private String topic;
    private String state;
    private String onState;
    private String offState;
    private double energyConsumption;
    private IMqttClient client;

    public static final String DEFAULT_SWITCH_PATTERN = "1:0";

    private final DeviceService deviceService;

    public OnOffDevice(String id, String state, String topic, String switchPattern, String energyConsumption, DeviceService deviceService) {
        this.deviceService = deviceService;
        this.id = id;
        this.topic = topic;
        this.energyConsumption = energyConsumption == null ? 0 : Float.parseFloat(energyConsumption);
        String[] parts = switchPattern.split(":");
        this.onState = parts[0];
        this.offState = parts[1];
        this.state = state != null ? state : offState;
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

    public void subscribe() throws MqttException, InterruptedException {
//        CountDownLatch receivedSignal = new CountDownLatch(1);

//        client.subscribe(this.topic, (topic, msg) -> {
//            byte[] payload = msg.getPayload();
//            log.info(String.format("Message received: topic=" + topic + ", payload=" + new String(payload)));
////            receivedSignal.countDown();
//        });
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
        this.updateState(msg.toString());

        return msg.toString();
    }

    private void updateState(String msg) {
        this.state = msg;
        this.deviceService.updateState(this.id, this.state);
        log.info("Updated state for " + this.id + " : " + this.topic + " to " + this.state);
    }

    public boolean isDeviceOn() {
        return this.state.equals(this.onState);
    }
}
