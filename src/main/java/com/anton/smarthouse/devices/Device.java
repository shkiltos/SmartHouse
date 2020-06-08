package com.anton.smarthouse.devices;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface Device {
    public String getTopic();
    public void setTopic(String value);
    public String getId();
    public String publish(String msg) throws MqttException;
}
