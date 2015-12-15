package Sensors;
import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;
import HttpClient.HttpClient;

public abstract class Sensor 
{
	protected String sensorPath;
	protected String sessionID;
	protected HttpClient connection;

	/** Constructor
	 * @param uri - server uri
	 * @param sensorPath - sensor subpath (example: sensor/lcd)
	 */
	public Sensor(String uri, String sensorPath)
	{
		this.connection = new HttpClient(uri);
		this.sensorPath = sensorPath;
	}

	/** Sets uri on the httpClient
	 * @param uri - server uri
	 */
	public void setUri(String uri)
	{
		this.connection.setUri(uri);
	}

	/** Gets uri from the httpClient
	 */
	public String getUri()
	{
		return this.connection.getUri();
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
	public void updatePosition(double longitude, double latitude) throws ConnectionFailedException, NoSuchSimulationException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{\"Position\":{\"Longitude\":");
		sb.append(longitude);
		sb.append(", \"Latitude\":");
		sb.append(latitude);
		sb.append(", \"Altitude\": 0}}");
		connection.sendPUT(sensorPath + "/" + sessionID, sb.toString());
	}

	/** Start the simulation of the sensor on the server
	 * @param longitude initial position
	 * @param latitude  initial position
	 * @throws ConnectionFailedException if a connection to the server was not established
	 */
	public void startSimulation(double longitude, double latitude) throws ConnectionFailedException 
	{
		sessionID = connection.sendPOST(sensorPath, "{"
				+ " \"Altitude\": 0"
				+ ",\"Longitude\":" + longitude
				+ ",\"Latitude\":" + latitude + "}");
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
