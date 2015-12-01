package Sensors;

import java.util.HashMap;

import org.json.JSONObject;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class LCDAPI extends Sensor 
{
	private JSONObject lastReceivedData;
	public static final int CWA = 0;
	public static final int TIC = 1;
	public static final int CWACont = 2;
	
	/**
	 * @param uri - server uri
	 */
	public LCDAPI(String uri) 
	{
		super(uri, "sensors/lcd");
	}

	public LCDData fetchData() throws ConnectionFailedException, NoSuchSimulationException 
	{
		if(sessionID == null) throw new NoSuchSimulationException();
		lastReceivedData = super.connection.sendGET(sensorPath + "/" + sessionID);
		return new LCDData(lastReceivedData);
	}
	
	/** Sends a 'NVG TOGGLE' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void nvgToggle() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"command\": \"nvg toggle\","
				+ " \"sensor\": \"lcd\","
				+ " \"id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent(sensorPath, jsonEvent);
	}
	
	/** Sends a 'AUDIBLE ALARM TOGGLE' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void audibleAlarmToggle() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"command\": \"alarm toggle\","
				+ " \"sensor\": \"lcd\","
				+ " \"id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent(sensorPath, jsonEvent);
	}
	
	/**
	 * @param mode one of LCDAPI.[CWA, TIC, CWACont]
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void updateDetectionMode(int mode) throws ConnectionFailedException, NoSuchSimulationException
	{
		// TODO: validate mode? if its not valid, should throw runtimeerror
		lastReceivedData.put("DetectionMode", mode);
		connection.sendPUT(sensorPath + "/" + sessionID, lastReceivedData.toString());
	}
	
	public void updatePosition(double longitude, double latitude) throws ConnectionFailedException, NoSuchSimulationException
	{
		HashMap<String, Double> pos = new HashMap<String, Double>();
		pos.put("Longitude", longitude);
		pos.put("Latitude", latitude);
		pos.put("Altitude", 0.0);
		lastReceivedData.put("Position", pos);
		connection.sendPUT(sensorPath + "/" + sessionID, lastReceivedData.toString());
	}
}
