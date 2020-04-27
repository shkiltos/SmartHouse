package com.anton.smarthouse;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SmarthouseApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void name() {
	}

	@Test
	public void whenSendSingleMessage_thenSuccess() throws Exception {

		String publisherId = UUID.randomUUID().toString();
		MqttClient publisher = new MqttClient("tcp://localhost:1883",publisherId);

		String subscriberId = UUID.randomUUID().toString();
		MqttClient subscriber = new MqttClient("tcp://localhost:1883",subscriberId);

		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);


		subscriber.connect(options);
		publisher.connect(options);

		CountDownLatch receivedSignal = new CountDownLatch(1);

		subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
			byte[] payload = msg.getPayload();
			System.out.println(String.format("[I46] Message received: topic=" + topic + ", payload=" + new String(payload)));
			receivedSignal.countDown();
		});


		Callable<Void> target = new EngineTemperatureSensor(publisher);
		target.call();

		receivedSignal.await(1, TimeUnit.MINUTES);

		System.out.println("[I56] Success !");
	}


	@Test
	public void whenSendMultipleMessages_thenSuccess() throws Exception {

		String publisherId = UUID.randomUUID().toString();
		MqttClient publisher = new MqttClient("tcp://localhost:1883",publisherId);

		String subscriberId = UUID.randomUUID().toString();
		MqttClient subscriber = new MqttClient("tcp://iot.eclipse.org:1883",subscriberId);


		MqttConnectOptions options = new MqttConnectOptions();
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setConnectionTimeout(10);


		publisher.connect(options);
		subscriber.connect(options);

		CountDownLatch receivedSignal = new CountDownLatch(10);

		subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
			byte[] payload = msg.getPayload();
			System.out.println(String.format("[I46] Message received: topic=" + topic + ", payload=" + new String(payload)));
			receivedSignal.countDown();
		});


		Callable<Void> target = new EngineTemperatureSensor(publisher);

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> {
			try {
				target.call();
			}
			catch(Exception ex) {
				throw new RuntimeException(ex);
			}
		}, 1, 1, TimeUnit.SECONDS);


		receivedSignal.await(1, TimeUnit.MINUTES);
		executor.shutdown();

		assertTrue(receivedSignal.getCount() == 0 , "Countdown should be zero");

		System.out.println("[I105] Success !");
	}
}
