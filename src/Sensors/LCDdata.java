package Sensors;

import org.json.JSONArray;
import org.json.JSONObject;

public class LCDData implements SensorData 
{
	
	public final int gBarCount;
	public final int hBarCount;
	public final int gSubstanceIndex;
	public final int hSubstanceSubstanceIndex;
	public final int detectionMode;
	public final boolean gAlert;
	public final boolean hAlert;
	public final boolean ticAlert;
	public final boolean ticMode;
	public final boolean lowSieve;
	public final boolean changeSievePack;
	public final boolean lowBattery;
	public final boolean changeBattery;
	public final boolean gHighDoseAlert;
	public final boolean gMediumDoseAlert;
	public final boolean hHighDoseAlert;
	public final boolean initialSelfTest;
	public final boolean coronaBurnOff;
	public final boolean audioFault;
	public final boolean pTOutOfRange;
	public final boolean fatalError;
	public final boolean crAboveLimit;
	public final boolean fanCAboveLimit;
	public final boolean initialSelfTestFailure;
	public final boolean healthCheckFailure;
	public final boolean codeChecksumError;
	public final boolean eepromChecksumError;
	public final boolean hTOutSideLimits;
	public final boolean audibleAlarm;
	public final boolean nvg;
	
	public LCDData(JSONObject lastReceivedData)
	{
		gBarCount = returnData("BarCount", "G", lastReceivedData);
		hBarCount = returnData("BarCount", "H", lastReceivedData);
		gSubstanceIndex = returnData("SubstanceIndex", "G", lastReceivedData);
		hSubstanceSubstanceIndex = returnData("SubstanceIndex", "H", lastReceivedData);

		detectionMode = lastReceivedData.getInt("DetectionMode");
		
		JSONObject state = lastReceivedData.getJSONObject("State");
		gAlert = state.getBoolean("GAlert");
		hAlert = state.getBoolean("HAlert");
		ticAlert = state.getBoolean("TICAlert");
		ticMode = state.getBoolean("TICMode");
		lowSieve = state.getBoolean("LowSieve");
		changeSievePack = state.getBoolean("ChangeSievePack");
		lowBattery = state.getBoolean("LowBattery");
		changeBattery = state.getBoolean("ChangeBattery");
		gHighDoseAlert = state.getBoolean("GHighDoseAlert");
		gMediumDoseAlert = state.getBoolean("GMediumDoseAlert");
		hHighDoseAlert = state.getBoolean("HHighDoseAlert");
		initialSelfTest = state.getBoolean("InitialSelfTest");
		coronaBurnOff = state.getBoolean("CoronaBurnOff");
		pTOutOfRange = state.getBoolean("PTOutOfRange");
		audioFault = state.getBoolean("AudioFault");
		fatalError = state.getBoolean("FatalError");
		crAboveLimit = state.getBoolean("CRAboveLimit");
		fanCAboveLimit = state.getBoolean("FanCAboveLimit");
		initialSelfTestFailure = state.getBoolean("InitialSelfTestFailure");
		healthCheckFailure = state.getBoolean("HealthCheckFailure");
		codeChecksumError = state.getBoolean("CodeChecksumError");
		eepromChecksumError = state.getBoolean("EEPROMChecksumError");
		hTOutSideLimits = state.getBoolean("HTOutSideLimits");
		audibleAlarm = state.getBoolean("AudibleAlarm");
		nvg = state.getBoolean("NVG");
	
	}
	
	private int returnData(String dataName, String substanceCategory, JSONObject json) 
	{
		JSONArray data = json.getJSONArray("Data");
		
		if (data.length() == 0) return 0;
		
		if(data.getJSONObject(0).getString("SubstanceCategory").equals(substanceCategory))
		{
			return data.getJSONObject(0).getInt(dataName);
		}

		if(data.length() == 1) return 0;
			
		if(data.getJSONObject(1).getString("SubstanceCategory").equals(substanceCategory))
		{
			return data.getJSONObject(1).getInt(dataName);
		}
		
		return 0;
	}
}
