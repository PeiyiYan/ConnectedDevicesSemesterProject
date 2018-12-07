'''
Created on Oct 12, 2018

@author: rocky_yan
'''
from time import sleep
from labs.module03 import TempSensorAdaptor
TemperatureSensorAdaptor = TempSensorAdaptor.TempSensorAdaptor()
TemperatureSensorAdaptor.daemon = True

print("Starting system performance app daemon thread...")
TemperatureSensorAdaptor.enableEmulator = True

TemperatureSensorAdaptor.start()

while (True):
    sleep(7)
    pass