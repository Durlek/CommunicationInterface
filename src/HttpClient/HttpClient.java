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
	private String hostname;
	
	public HttpClient(String hostname) 
	{
//		if(!hostname.contains("http://")) hostname = "http://" + hostname;
		this.hostname = hostname;
	}
	
	public boolean testConnection()
	{
		URL url;
		try 
		{
			url = new URL(hostname + "/Check");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			boolean result = in.readLine() == "123";
			in.close();
			
			return result;
		} 
		catch (IOException e) 
		{
			return false;
		}
	}
	
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
	
	/* Handles POST events, where a response from server is expected; Example: Start simulation */
	public String sendPOST(String urlParam) throws ConnectionFailedException
	{
		try
		{
			URL url = new URL(hostname + "/" + urlParam); 
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			
			con.setDoOutput(true);
			sendEmptyDataStream(con);
			
			if (con.getResponseCode() != 201) throw new ConnectionFailedException();
			
			return con.getHeaderField("Location");
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	
	public void sendPOSTEvent(String urlParam, String jsonEvent) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(hostname + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			
			con.setDoOutput(true);
			
			OutputStreamWriter sendData = new OutputStreamWriter(con.getOutputStream());
			sendData.write(jsonEvent);
			sendData.flush();
			sendData.close();
			System.out.println(con.getResponseCode());
			if (con.getResponseCode() == 404) throw new NoSuchSimulationException();
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}

	/* Handles GET events, where response from server is expected; Example: getData */
	public JSONObject sendGET(String urlParam) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(hostname + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(false);
			
			if (con.getResponseCode() == 404) throw new NoSuchSimulationException();
			
			return returnResponse(con);
		}
		catch (IOException e) {
			throw new ConnectionFailedException();
		}
	}
	
	/* Handles PUT events; 
	 * Require description of how JsonData should be structured.
	 * */
	public void sendPUT(String urlParam, String JsonData) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(hostname + "/" + urlParam);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty( "Content-Type", "application/json" );
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			
			OutputStreamWriter sendData = new OutputStreamWriter(con.getOutputStream());
			sendData.write(JsonData);
			sendData.flush();
			sendData.close();
	
			con.connect();
			
			if (con.getResponseCode() == 404) throw new NoSuchSimulationException();
			if (con.getResponseCode() != 200) throw new ConnectionFailedException();
		} 
		catch (IOException e)
		{
			throw new ConnectionFailedException();
		}
	}
	
	/* Handles DELETE events; */
	public void sendDELETE(String urlParam) throws ConnectionFailedException, NoSuchSimulationException
	{
		try
		{
			URL url = new URL(hostname + "/" + urlParam);
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
