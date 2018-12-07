package iotConnectedDevicesGateway.labs.module7;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
//import com.labbenchstudios.iot.labs.common.ConfigConst;(I DO NOT USE THE ConfigConst)

public class CoapClientConnector {
	// we cannot change _Logger anytime
	private static final Logger _Logger = Logger.getLogger(CoapClientConnector.class.getName());
	// define params
	private String _protocol;
	private String _host;
	private String _port;
	private String _serverAddr;
	private CoapClient _clientConn;
	private boolean _isInitialized;

	// This is the constructor
	public CoapClientConnector(String protocol, String host, String port, boolean isSecure) {
		super();
		_protocol = protocol;
		_host = host;
		_port = port;
		_isInitialized = false;
		if (isSecure) {
			_protocol = "californium.eclipse.org";
			_port = "5683";
		} else {
			_protocol = "coap";
			_port = "5683";
		}
		if (host != null && host.trim().length() > 0) {
			_host = host;
		} else {
			_host = "localhost";
		}
		// NOTE: URL does not have a protocol handler for "coap",
		// so we need to construct the URL manually
		_serverAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for server conn: " + _serverAddr);
	}

	// public methods
	public void runTests(String resourceName) {
		try {
			_isInitialized = false;
			initClient(resourceName);
			_Logger.info("Current URI: " + getCurrentUri());
			String payload = "This is the sample payload.";
			pingServer();
			discoverResources();
			sendGetRequest();
			sendGetRequest(true);
			sendPostRequest(payload, false);
			sendPostRequest(payload, true);
			sendPutRequest(payload, false);
			sendPutRequest(payload, true);
			sendDeleteRequest();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to issue request to CoAP server.", e);
		}
	}

	private void initClient() {
		initClient(null);
	}

//initialize the Client
	private void initClient(String resourceName) {
		if (_isInitialized) {
			return;
		}
		if (_clientConn != null) {
			_clientConn.shutdown();
			_clientConn = null;
		}
		try {
			if (resourceName != null) {
				_serverAddr += "/" + resourceName;
			}
			_clientConn = new CoapClient(_serverAddr);
			_Logger.info("Created client connection to server / resource: " + _serverAddr);
			System.out.println("Created client connection to server / resource: " + _serverAddr);
			_isInitialized = true;
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to connect to broker: " + getCurrentUri(), e);
			System.out.println(e.toString());
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the CoAP client URI (if set, otherwise returns the _serverAddr, or
	 * null).
	 *
	 * @return String
	 */
	public String getCurrentUri() {
		return (_clientConn != null ? _clientConn.getURI() : _serverAddr);
	}

	public void discoverResources() {
		_Logger.info("Issuing discover...");
		initClient();
		Set<WebLink> weblink = _clientConn.discover();
		if (weblink != null) {
			for (WebLink wl : weblink) {
				_Logger.info(" --> WebLink: " + wl.getURI());
			}
		}
	}

	public void pingServer() {
		_Logger.info("Sending ping to server");
		initClient();
		if (_clientConn.ping()) {
			_Logger.info(_host + "is ready");
		} else {
			_Logger.info(_host + "is unready");
		}
	}

	public void sendDeleteRequest() {
		initClient();
		handleDeleteRequest();
	}

	public void sendDeleteRequest(String resourceName) {
		_isInitialized = false;
		initClient(resourceName);
		handleDeleteRequest();
	}

	public void sendGetRequest() {
		initClient();
		handleGetRequest(false);
	}

	public void sendGetRequest(String resourceName) {
		_isInitialized = false;
		initClient(resourceName);
		handleGetRequest(false);
	}

	public void sendGetRequest(boolean useNON) {
		initClient();
		handleGetRequest(useNON);
	}

	public void sendGetRequest(String resourceName, boolean useNON) {
		_isInitialized = false;
		initClient(resourceName);
		sendGetRequest(useNON);
	}

	public void sendPostRequest(String payload, boolean useCON) {
		initClient();
		handlePostRequest(payload, useCON);
	}

	public void sendPostRequest(String resourceName, String payload, boolean useCON) {
		_isInitialized = false;
		initClient(resourceName);
		handlePostRequest(payload, useCON);
	}

	public void sendPutRequest(String payload, boolean useCON) {
		initClient();
		handlePutRequest(payload, useCON);
	}

	public void sendPutRequest(String resourceName, String payload, boolean useCON) {
		_isInitialized = false;
		initClient(resourceName);
		handlePutRequest(payload, useCON);
	}

	// private methods
	private void handleDeleteRequest() {
		_Logger.info("Sending Delete...");
		CoapResponse response = null;
		response = _clientConn.delete();
		if (response != null) {
			_Logger.info(
					"Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
		} else {
			_Logger.warning("No response received.");
		}
	}

	private void handleGetRequest(boolean useNON) {
		_Logger.info("Sending GET...");
		  CoapResponse response = null;
		  if (useNON) {
			  _clientConn.useNONs().useEarlyNegotiation(32).get();
		  }
		  response = _clientConn.get();
		  if (response != null) {
			  _Logger.info(
					  "Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
		  } else {
			  _Logger.warning("No response received.");
		  }
	}

	private void handlePutRequest(String payload, boolean useCON) {
		_Logger.info("Sending PUT...");
		CoapResponse response = null;
		if (useCON) {
			_clientConn.useCONs().useEarlyNegotiation(32).get();
		}
		response = _clientConn.put(payload, MediaTypeRegistry.TEXT_PLAIN);
		if (response != null) {
			_Logger.info(
					"Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
		} else {
			_Logger.warning("No response received.");
		}
	}

	private void handlePostRequest(String payload, boolean useCON) {
		_Logger.info("Sending POST...");
		CoapResponse response = null;
		if (useCON) {
			_clientConn.useCONs().useEarlyNegotiation(32).get();
		}
		response = _clientConn.post(payload, MediaTypeRegistry.TEXT_PLAIN);
		if (response != null) {
			_Logger.info(
					"Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
		} else {
			_Logger.warning("No response received.");
		}
	}

}