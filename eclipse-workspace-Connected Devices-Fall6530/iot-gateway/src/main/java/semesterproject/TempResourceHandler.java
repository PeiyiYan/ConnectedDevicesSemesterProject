package semesterproject;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TempResourceHandler extends CoapResource {
	
	String _host = "things.ubidots.com";
	String _userName = "A1E-rM4nKgQpTjSiZWtkLFurahk0TDzs9C";
	String _authToken= null;
	String _pemFileName = "/Users/rocky_yan/Desktop/ubidots_cert.pem";
	
	// static
	private static final Logger _Logger = Logger.getLogger(TempResourceHandler.class.getName());

	// constructors

	public TempResourceHandler() {
		super("temp");
		// TODO Auto-generated constructor stub
	}

	public TempResourceHandler(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param visible
	 */
	public TempResourceHandler(String name, boolean visible) {
		super(name, visible);
	}

	// public methods

	@Override
	/**
	 * Post is to create a resource
	 *
	 */
	public void handlePOST(CoapExchange ce) {
		String responseMsg = "Here's the reponse to temp request::" + super.getName();
		
		//get the temperature from the CoAP Client which is in the Raspberry Pi

		System.out.println(ce.getRequestText());
		
		//Start Mqtt clientCloud and publish the temperature to the Ubidots
		MqttClientConnectorCloud _mqttClient = new MqttClientConnectorCloud(_host,_userName, _authToken,_pemFileName);
		_mqttClient.connect();
		String topicName1 = "/v1.6/devices/homeiotgateway/tempsensor";
		String topicName2 = "/v1.6/devices/homeiotgateway/tempactuator";
		String topicName3 = "/v1.6/devices/homeiotgateway/averagetemp";
		String payload = ce.getRequestText();
		_mqttClient.publishMessage(topicName1, 1, payload.getBytes());
		
		//_mqttClient.disconnect(); we can disconnect it or publish it all the time.
		System.out.println("publishing success");
		
		//catch the variable from Ubidots(you need to subscribe the same topic as published, same qos as well)
		_mqttClient.subscribeToTopic(topicName1, 1);
		_mqttClient.subscribeToTopic(topicName2, 1);
		_mqttClient.subscribeToTopic(topicName3, 1);
		//when we finished the subscription, the messageArrived function will be implemented automatically.
		//So we can publish the return temperature to the mosquitto server

		ce.respond(ResponseCode.VALID, responseMsg);

		_Logger.info("Handling POST:" + responseMsg);
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());
	}

	@Override
	/**
	 * Put is to update a resource
	 *
	 */
	public void handlePUT(CoapExchange ce) {
		String responseMsg = "Here's the reponse to temp request::" + super.getName();

		ce.respond(ResponseCode.VALID, responseMsg);

		_Logger.info("Handling PUT:" + responseMsg);
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());
	}

	@Override
	/**
	 * Get is to read or get a resource
	 *
	 */
	public void handleGET(CoapExchange ce) {

		String responseMsg = "Here's the reponse to temp request::" + super.getName();

		ce.respond(ResponseCode.VALID, responseMsg);

		_Logger.info("Handling GET:" + responseMsg);
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());
	}

	@Override
	/**
	 * Post is to delete a resource
	 *
	 */
	public void handleDELETE(CoapExchange ce) {
		String responseMsg = "Here's the reponse to temp request::" + super.getName();

		ce.respond(ResponseCode.VALID, responseMsg);

		_Logger.info("Handling DELETE:" + responseMsg);
		_Logger.info(ce.getRequestCode().toString() + ": " + ce.getRequestText());

	}
}
