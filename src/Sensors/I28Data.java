package Sensors;

import org.json.JSONObject;

public class I28Data implements SensorData 
{
	public final int currentDoseRate;
	public final int peakDoseRate;
	public final int accumulatedDose;
	public final int errorCode;
	
	public I28Data(JSONObject lastReceivedData)
	{
		JSONObject data = lastReceivedData.getJSONObject("Data");
		currentDoseRate = data.getInt("currentDoseRate");
		peakDoseRate = data.getInt("peakDoseRate");
		accumulatedDose = data.getInt("AccumulatedDose");
		
		JSONObject state = lastReceivedData.getJSONObject("State");
		errorCode = state.getInt("ErrorCode");
	}
}
