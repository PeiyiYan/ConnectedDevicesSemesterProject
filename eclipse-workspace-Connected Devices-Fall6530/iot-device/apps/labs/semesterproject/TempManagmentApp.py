'''
Created on Dec 6, 2018

@author: rocky_yan
'''

from time import sleep

import sys
sys.path.append('/home/pi/peiyiyanworkspace/apps')
from labs.semesterproject import TemSensorAdaptor
TemperatureSensorAdaptor = TemSensorAdaptor.TempSensorAdaptor()
TemperatureSensorAdaptor.daemon = True

print("Starting system performance app daemon thread...")
TemperatureSensorAdaptor.enableEmulator = True
#run start
#This is the main class to control all the connected devices
TemperatureSensorAdaptor.start()

while (True):
    sleep(7)
    pass
