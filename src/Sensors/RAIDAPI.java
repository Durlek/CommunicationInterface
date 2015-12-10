package Sensors;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class RAIDAPI extends Sensor
{
	
	public RAIDAPI(String uri) 
	{
		super(uri, "sensors/raid");
	}

	@Override
	public RAIDData fetchData() throws ConnectionFailedException, NoSuchSimulationException 
	{
		if(sessionID == null) throw new NoSuchSimulationException();
		return new RAIDData(super.connection.sendGET(sensorPath + "/" + sessionID));
	}
	
	/** Sends a 'toggle lib' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void toggleLib() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"toggle lib\","
				+ " \"Sensor\": \"raid\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'stop/start' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void toggleStartStop() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"stop/start\","
				+ " \"Sensor\": \"raid\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'cleaning on' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void cleaningOn() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"cleaning on\","
				+ " \"Sensor\": \"raid\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'cleaning off' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void cleaningOff() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"cleaning off\","
				+ " \"Sensor\": \"raid\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
}
