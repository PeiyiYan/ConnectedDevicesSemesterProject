package iotConnectedDevicesGateway.labs.module8;

import java.util.logging.Logger;

// only include this if you have a ‘ConfigConst’ class...
//import {your package name}.labs.common.ConfigConst;
public class TempActuatorSubscriberApp {
// static
	private static final Logger _Logger = Logger.getLogger(TempSensorPublisherApp.class.getName());
	private static TempActuatorSubscriberApp _App;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new TempActuatorSubscriberApp();
		try {
			_App.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

// params
	private MqttClientConnector _mqttClient;

// constructors
	/**
	 * Default.
	 */
	public TempActuatorSubscriberApp() {
		super();
	}

// public methods
	/**
	 * Connect to the MQTT client, then: 1) If this is the subscribe app, subscribe
	 * to the given topic 2) If this is the publish app, publish a test message to
	 * the given topic
	 */
	public void start() {
		String _host = "things.ubidots.com";
		String _userName = "A1E-rM4nKgQpTjSiZWtkLFurahk0TDzs9C";
		String _authToken= null;
		String _pemFileName = "/Users/rocky_yan/Desktop/ubidots_cert.pem";
		

		_mqttClient = new MqttClientConnector(_host,_userName, _authToken,_pemFileName);
		_mqttClient.connect();
		String topicName = "/v1.6/devices/homeiotgateway/tempactuator";

		_mqttClient.subscribeToTopic(topicName, 2);

	}
}