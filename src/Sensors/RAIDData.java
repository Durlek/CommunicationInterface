package Sensors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class RAIDData implements SensorData 
{
	public final byte SubstanceLibrary;
	public final int DeviceState;
	public final int DeviceError;
	public final int InternalState;
	public final List<RAIDMeasuredData> data;
	
	public RAIDData(JSONObject lastReceivedData) 
	{
		JSONArray rawDataList = lastReceivedData.getJSONArray("Data");
		List<RAIDMeasuredData> dataList = new ArrayList<RAIDMeasuredData>();
		for (Object obj : rawDataList)
		{
			dataList.add(new RAIDMeasuredData((JSONObject)obj));
		}
		
		this.data = Collections.unmodifiableList(dataList);
		
		JSONObject state = lastReceivedData.getJSONObject("State");
		SubstanceLibrary = (byte) state.getInt("SubstanceLibrary");
		DeviceState = state.getInt("DeviceState");
		DeviceError = state.getInt("DeviceError");
		InternalState = state.getInt("InternalState");
		
	}
	
	public static class RAIDMeasuredData
	{
		public final int BarCount;
		public final int SubstanceClass;
		public final int ConcentrationUnit;
		public final int Concentration;
		public final String Substance;
		
		public RAIDMeasuredData (JSONObject data)
		{
			BarCount = data.getInt("BarCount");
			SubstanceClass = data.getInt("SubstanceClass");
			ConcentrationUnit = data.getInt("ConcentrationUnit");
			Concentration = data.getInt("Concentration");
			Substance = data.getString("Substance");
		}
	}
}
