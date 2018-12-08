'''
Created on Dec 4, 2018

@author: rocky_yan
'''
from coapthon.client.helperclient import HelperClient
import sys
sys.path.append('/home/pi/peiyiyanworkspace/apps')

class CoAPClient: 
    
    
    config = None
    serverAddr = None
    host = "192.168.0.119" 
    port = 5683

    def __init__(self):

        print('\tHost: ' + self.host) 
        print('\tPort: ' + str(self.port))
        
        if not self.host or self.host.isspace(): 
            print("Using default host: " + self.host)
        if self.port < 1024 or self.port > 65535: 
            print("Using default port: " + self.port)
            
        self.serverAddr = (self.host, self.port)
        self.url = "coap://" + self.host + ":" + str(self.port)
    
    
    def initClient(self): 
        try:
            self.client = HelperClient(server=(self.host, self.port))
            print("Created CoAP client ref: " + str(self.client))
            print(" coap://" + self.host + ":" + str(self.port)) 
        except Exception:
            print("Failed to create CoAP helper client reference using host: " + self.host) 
            pass
    
    
    def handleGetTest(self, resource):
    
        print("Testing GET for resource: " + resource)
        
        self.initClient()
        
        response = self.client.get(resource)
        
        if response: 
            print(response.pretty_print())
        else:
            print("No response received for GET using resource: " + resource)
            
        self.client.stop()
    
    #
    def handlePostTest(self, resource, payload):
    
    
        print("Testing POST for resource: " + resource + ", payload: " + payload)
        
        self.initClient()
        
        response = self.client.post(resource, payload)
        
        if response: 
            print(response.pretty_print())
        else:
            print("No response received for POST using resource: " + resource)
        self.client.stop()
