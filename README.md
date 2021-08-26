# mqttclient
Back up copy

# AVIN
Repository for AVIN related files

Prior to using the two sets of code (one for publishing client, one for receiving client), be aware of the following issues.

1. Currently, it's set up such that the payload will be sent as string byte. In the near future it will be updated to support payloads in binary.
2. The clients are currently able to publish and receive the HVAC data Vehicle.AmbientAirTemperature and Vehicle.Cabin.HVAC.AmbientAirTemperature.
It is currently unable to support other HVAC data such as:
- Vehicle.Cabin.HVAC.IsAirConditioningActive
- Vehicle.Cabin.HVAC.IsRecirculationActive
- Vehicle.Cabin.HVAC.Station.Row.<N>.<Left or Right>.Temperature
- Vehicle.Cabin.HVAC.Station.Row.<N>.<Left or Right>.FanSpeed
Updates will soon be made to also support these HVAC values.
3. Currently, the topics are missing /VSS/ at the front. So instead of /VSS/Vehicle/AmbientAirTemperature, it uses the topic /Vehicle/AmbientAirTemperature. 
Updates will soon be made so that the clients use topics with /VSS/ at the start.
