package HttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import Exceptions.ConnectionFailedException;
import Exceptions.NoSuchSimulationException;

public class HttpClient
{
	private String uri;
	
	/** Constructor
	 * @param uri - server uri
	 */
	public HttpClient(String uri)
	{
		setUri(uri);
	}
	
	/** Sets uri for the http server to connect to
	 * @param uri - server uri
	 */
	public void setUri(String uri)
	{
		// Ghetto fix - "http://" and "" throws different exceptions
		if (uri.trim().equals("http://") || uri.trim().equals("https://")) {
			this.uri = "";
		} else {
			this.uri = uri;	
		}
	}

	/** Gets uri
	 * @return a string with the currently set uri
	 */
	public String getUri()
	{
		return uri;
	}
	
	/** Tests the connection between the http client and server set on the uri
	 * @return a boolean with the result of the test
	 */
	public boolean testConnection()
	{
		URL url;
		try 
		{
			url = new URL(uri + "/Check");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			boolean result = in.readLine().equals("123");
			in.close();
			
			return result;
		} 
		catch (IOException e) 
		{
			return false;
		}
	}
	
	/** Returns response from http server; Internally used.
	 * @return a data object holding all values belonging to the sensor
	 * @param con - established connection
	 * @throws ConnectionFailedException if a connection to the server was not established
	 */
	private JSONObject returnResponse(HttpURLConnection con) throws ConnectionFailedException
	{
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));		
			JSONObject response = new JSONObject(in.readLine());
			in.close();
		
			return response;
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	
	/** Sends an empty data stream to the http server. Internally used.
	 * @return a data object holding all values belonging to the sensor
	 * @param con - established connection
	 * @throws ConnectionFailedException if a connection to the server was not established
	 */
	private void sendEmptyDataStream(HttpURLConnection con) throws ConnectionFailedException
	{
		try {
			DataOutputStream sendData = new DataOutputStream(con.getOutputStream());
			sendData.writeBytes("");
			sendData.flush();
			sendData.close();
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	

	/** Handles POST, where a response from server is expected; Example: Start simulation
	 * @return a String containing the ID for the simulation.
	 * @param urlParam - specifies sensor
	 * @param jsonData - jsondata containing longitude, latitude and altitude
	 * @throws ConnectionFailedException if a connection to the server was not established
	 */
	public String sendPOST(String urlParam, String jsonData) throws ConnectionFailedException
	{
		try
		{
			URL url = new URL(uri + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty( "Content-Type", "application/json" );
			con.setRequestMethod("POST");
			
			con.setDoOutput(true);

			OutputStreamWriter sendData = new OutputStreamWriter(con.getOutputStream());
			sendData.write(jsonData);
			sendData.flush();
			sendData.close();
			
			con.connect();

			int status = con.getResponseCode();
			if (status != 201) throw new ConnectionFailedException(status);
			
			return con.getHeaderField("Location");
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	
	/** Handles POST EVENT
	 * @param urlParam - specifies sensor
	 * @param jsonData - jsondata containing event type
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if the simulation does not exist
	 */
	public void sendPOSTEvent(String urlParam, String jsonEvent) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(uri + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty( "Content-Type", "application/json" );
			con.setRequestMethod("POST");
			
			con.setDoOutput(true);
			
			con.connect();
			
			OutputStreamWriter sendData = new OutputStreamWriter(con.getOutputStream());
			sendData.write(jsonEvent);
			sendData.flush();
			sendData.close();

			int status = con.getResponseCode();
			if (status == 404) throw new NoSuchSimulationException();
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}

	/** Handles GET events, where response from server is expected; Example: fetchData
	 * @param urlParam - specifies sensor
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if the simulation does not exist
	 */
	public JSONObject sendGET(String urlParam) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(uri + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(false);

			int status = con.getResponseCode();
			if (status == 404) throw new NoSuchSimulationException();
			if (status != 200) throw new ConnectionFailedException(status);
			
			return returnResponse(con);
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	
	/** Handles PUT events; 
	 * @param urlParam - specifies sensor
	 * @param JsonData - json containing attributes to be altered in database
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if the simulation does not exist
	 */
	public void sendPUT(String urlParam, String JsonData) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(uri + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty( "Content-Type", "application/json" );
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			
			OutputStreamWriter sendData = new OutputStreamWriter(con.getOutputStream());
			sendData.write(JsonData);
			sendData.flush();
			sendData.close();
	
			con.connect();
			
			int status = con.getResponseCode();
			
			if (status == 404) throw new NoSuchSimulationException();
			if (status != 200) throw new ConnectionFailedException(status);
		} 
		catch (IOException e)
		{
			throw new ConnectionFailedException();
		}
	}
	
	/** Handles DELETE events
	 * @param urlParam - specifies sensor
	 * @throws ConnectionFailedException if a connection to the server was not established
	 * @throws NoSuchSimulationException if the simulation does not exist
	 */
	public void sendDELETE(String urlParam) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(uri + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("DELETE");
			con.setDoOutput(false);
			
			if (con.getResponseCode() == 404) throw new NoSuchSimulationException();
		}
		catch (IOException e)
		{
			throw new ConnectionFailedException();
		}
	}
}
