package iotConnectedDevicesGateway.labs.module6;

import java.util.logging.Logger;

// only include this if you have a ‘ConfigConst’ class...
//import {your package name}.labs.common.ConfigConst;
public class MqttPubClientTestApp
{
// static
	private static final Logger _Logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp _App;
/**
* @param args
*/
	public static void main(String[] args)
	{
		_App = new MqttPubClientTestApp();
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
	public MqttPubClientTestApp()
	{
		super();
	}
// public methods
/**
* Connect to the MQTT client, then:
* 1) If this is the subscribe app, subscribe to the given topic
* 2) If this is the publish app, publish a test message to the given topic
*/
	public void start()
	{
		_mqttClient = new MqttClientConnector("tcp","test.mosquitto.org",1883);
		_mqttClient.connect();
		String topicName = "ConnectedDevicesMQTT";
		String payload = "This is the final test";
		_mqttClient.publishMessage(topicName, 2, payload.getBytes());
		_mqttClient.disconnect();
	}
}