package Sensors;

import HttpClient.HttpClient;

public class ConnectionTester {
	public static boolean test(String uri) {
		HttpClient httpClient = new HttpClient(uri);
		return httpClient.testConnection();
	}
}
