# coding=UTF-8
'''
Created on Oct 12, 2018

@author: rocky_yan
'''
import threading

import sys
sys.path.append('/home/pi/peiyiyanworkspace/apps')
from time import sleep
from labs.common import SensorData
from labs.semesterproject import SmtpClientConnector
from labs.common import ActuatorData
from labs.semesterproject import TempActuatorEmulator
from sense_hat import SenseHat
from labs.semesterproject import CoAPClient

    
DEFAULT_RATE_IN_SEC=7

sensordata = SensorData.SensorData()
connector = SmtpClientConnector.SmtpClientConnector()
emulator = TempActuatorEmulator.TempActuatorEmulator()
sensehat = SenseHat()
CoAPClient = CoAPClient.CoAPClient()

actuatorData = ActuatorData.ActuatorData()

class TempSensorAdaptor (threading.Thread):
    enableEmulator = False
    rateInSec     = DEFAULT_RATE_IN_SEC
    lowVal = 0 
    highVal = 30
    curTemp = 0
    prevTempSet = 10
    isPrevTempSet = False
    alertDiff = 5    
    
    def _init_(self,rateInSec = DEFAULT_RATE_IN_SEC):
        super(TempSensorAdaptor,self)._init_()
        
        if rateInSec > 0:
            self.rateInSec = rateInSec
    
    def run(self):
        while True:          
            if self.enableEmulator:
                #TODO:get temperature from the Sensehat
                self.curTemp = sensehat.get_temperature()
                #TODO:use the POST method to post a topic which name is "temp", we can subscribe this topic from CoAP Server at Gateway.
                CoAPClient.handlePostTest("temp", str(self.curTemp))
                emulator.processMessage(self.curTemp)
                print(self.curTemp)
                sensordata.addValue(self.curTemp)
                print('\n--------------------:')
                print('New sensor readings:')
                print(str(self.curTemp))
                if (self.isPrevTempSet) == False:
                    self.prevTemp      = self.curTemp
                    self.isPrevTempSet = True
                else:
                    print(sensordata.__str__())
                    if (abs(self.curTemp - sensordata.getAvgValue()) >= self.alertDiff):
                        print('\n  Current temp exceeds average by > ' + str(self.alertDiff) + '. Triggering alert...')
                        self.sensorData = sensordata.__str__()
                        self.actuatorData = actuatorData.__str__()
                        
                        #trigger the SMTP and send email
                        connector.publishMessage('Exceptional sensor data', self.sensorData)
                        
                        
                sleep(self.rateInSec)
                
                
                
                
                
                
                
                
                
                