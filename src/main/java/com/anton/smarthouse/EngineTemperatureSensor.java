package com.anton.smarthouse;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;
import java.util.concurrent.Callable;

public class EngineTemperatureSensor implements Callable<Void> {

    public static final String TOPIC = "wemos/led";

    private IMqttClient client;
    private Random rnd = new Random();

    public EngineTemperatureSensor(IMqttClient client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {

        if ( !client.isConnected()) {
            System.out.println("[I31] Client not connected.");
            return null;
        }

        MqttMessage msg = readEngineTemp();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(TOPIC, msg);

        return null;
    }

    /**
     * This method simulates reading the engine temperature
     * @return
     */
    private MqttMessage readEngineTemp() {
        double temp =  80 + rnd.nextDouble() * 20.0;
        byte[] payload = String.format("T:%04.2f",temp).getBytes();
        MqttMessage msg = new MqttMessage(payload);
        return msg;
    }
}