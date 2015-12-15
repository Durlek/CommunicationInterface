package Sensors;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class LCDAPI extends Sensor 
{
	public static final int CWA = 0;
	public static final int TIC = 1;
	public static final int CWACont = 2;
	private static final int IGNORE = 3;
	
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
		return new LCDData(super.connection.sendGET(sensorPath + "/" + sessionID));
	}
	
	/** Sends a 'NVG TOGGLE' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void nvgToggle() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"nvg toggle\","
				+ " \"Sensor\": \"lcd\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'AUDIBLE ALARM TOGGLE' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void audibleAlarmToggle() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"audible alarm toggle\","
				+ " \"Sensor\": \"lcd\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'silent current alarm' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void silenceAlarm() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"silent current alarm\","
				+ " \"Sensor\": \"lcd\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'reset sieve pack timer' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void resetSievPackTimer() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"reset sieve pack timer\","
				+ " \"Sensor\": \"lcd\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/** Sends a 'restart' event to the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void restart() throws ConnectionFailedException, NoSuchSimulationException
	{
		String jsonEvent = "{"
				+ " \"Command\": \"restart\","
				+ " \"Sensor\": \"lcd\","
				+ " \"Id\": \"" + sessionID + "\""
				+ "}";
		connection.sendPOSTEvent("sensors/event", jsonEvent);
	}
	
	/**
	 * @param mode one of LCDAPI.[CWA, TIC, CWACont]
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void updateDetectionMode(int mode) throws ConnectionFailedException, NoSuchSimulationException
	{
		// TODO: validate mode? if its not valid, should throw runtimeerror
		connection.sendPUT(sensorPath + "/" + sessionID, "{\"DetectionMode\": " + mode + "}");
	}
	
	public void updatePosition(double longitude, double latitude) throws ConnectionFailedException, NoSuchSimulationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"Position\":{\"Longitude\":");
		sb.append(longitude);
		sb.append(", \"Latitude\":");
		sb.append(latitude);
		sb.append(", \"Altitude\": 0},");
		// TODO: explain why this is needed
		sb.append("\"DetectionMode\": ");
		sb.append(IGNORE);
		sb.append("}}");
		connection.sendPUT(sensorPath + "/" + sessionID, sb.toString());
	}
}
