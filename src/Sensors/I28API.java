package Sensors;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class I28API extends Sensor 
{
	public I28API(String uri) 
	{
		super(uri, "sensors/i28");
	}

	@Override
	public I28Data fetchData() throws ConnectionFailedException, NoSuchSimulationException 
	{
		if(sessionID == null) throw new NoSuchSimulationException();
		return new I28Data(super.connection.sendGET(sensorPath + "/" + sessionID));
	}
	
	/** Sends a 'reset accumulated dose rate' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void resetAccumulatedDoseRate() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"reset accumulated dose rate\","
				+ " \"Sensor\": \"i28\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	public void resetPeakDoseRate()  throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"reset peak dose rate\","
				+ " \"Sensor\": \"i28\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
}
