package semesterproject;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;

public class CoapServerConnector {
		
	// static
	private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
	// private var's
	private CoapServer _coapServer;

	// constructors
	/**
	 * Default.
	 *
	 */
	public CoapServerConnector() {
		super();
		_coapServer = new CoapServer();
		TempResourceHandler tempHandler = new TempResourceHandler();
		addResource(tempHandler);
	}

	// public methods
	public void addResource(CoapResource resource) {
		if (resource != null) {
			_coapServer.add(resource);
		}
	}
	
	
	/**
	 *  This is going to start the CoAP server
	 */
	public void start() {
		if (_coapServer == null) {
			_Logger.info("Creating CoAP server instance and 'temp' handler...");

		}
		_Logger.info("Starting CoAP server...");
		_coapServer.start();
		
	}

	/**
	 *  This is going to stop the CoAP server
	 */
	public void stop() {
		_Logger.info("Stopping CoAP server...");
		_coapServer.stop();
	}
}