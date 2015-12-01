package Sensors;
import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;
import HttpClient.HttpClient;

abstract class Sensor 
{
	protected String sensorPath;
	protected String sessionID;
	protected HttpClient connection;

	/**
	 * @param uri - server uri
	 * @param sensorPath - sensor subpath (example: sensor/lcd)
	 */
	public Sensor(String uri, String sensorPath)
	{
		this.connection = new HttpClient(uri);
		this.sensorPath = sensorPath;
	}
	
	/** Gets sensor data from server
	 * @return a data object holding all values belonging to the sensor
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public abstract SensorData fetchData() throws ConnectionFailedException, NoSuchSimulationException;
	
	/** Update the sensors position (sends data to server)
	 * @param longitude
	 * @param latitude
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public abstract void updatePosition(double longitude, double latitude) throws ConnectionFailedException, NoSuchSimulationException;

	/** Start the simulation of the sensor on the server
	 * @param longitude initial position
	 * @param latitude  initial position
	 * @throws ConnectionFailedException if a connection to the server was not established
	 */
	public void startSimulation(double longitude, double latitude) throws ConnectionFailedException 
	{
		sessionID = connection.sendPOST(sensorPath);
		// Ghetto fix - should send position above
		try {
			updatePosition(longitude, latitude);
		} catch (NoSuchSimulationException e) {}
	}
	
	/** Stop the simulation of the sensor on the server
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if this object is not associated with a sensor on the server
	 */
	public void endSimulation() throws ConnectionFailedException, NoSuchSimulationException
	{
		if(sessionID == null) throw new NoSuchSimulationException();
		
		connection.sendDELETE(sensorPath + "/" + sessionID);
		sessionID = null;
	}
}
