package semesterproject;

public class GatewayManagementApp{



	public void run() {
		//Start CoAP server
		try {
			CoapServerConnector coapserver = new CoapServerConnector();
			coapserver.start();
		}catch(Exception e) {
			System.out.println("Start Failed");
		}
	}
	
	//This is the main class to control all the connected gateway
	public static void main(String[] args) {
		GatewayManagementApp gatewayApp = new GatewayManagementApp();
		gatewayApp.run();
	}
	
}