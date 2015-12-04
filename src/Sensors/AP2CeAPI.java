package Sensors;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class AP2CeAPI extends Sensor 
{
	
	public AP2CeAPI(String uri) 
	{
		super(uri, "sensors/ap2ce");
	}
	
	@Override
	public AP2CeData fetchData() throws ConnectionFailedException, NoSuchSimulationException
	{	
		if(sessionID == null) throw new NoSuchSimulationException();
		return new AP2CeData(super.connection.sendGET(sensorPath + "/" + sessionID));
	}
}
