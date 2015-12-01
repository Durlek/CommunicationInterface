package Sensors;

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
	public final boolean ptOutOfRange;
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
	
	public LCDData(JSONObject json)
	{
		gBarCount = returnData("BarCount", "G", json);
		hBarCount = returnData("BarCount", "H", json);
		gSubstanceIndex = returnData("SubstanceIndex", "G", json);
		hSubstanceSubstanceIndex = returnData("SubstanceIndex", "H", json);

		detectionMode = json.getInt("DetectionMode");
		
		JSONObject state = json.getJSONObject("State");
		gAlert = state.getBoolean("GAlert");
		hAlert = state.getBoolean("GAlert");
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
		ptOutOfRange = state.getBoolean("PTOutOfRange");
		fatalError = state.getBoolean("FatalError");
		crAboveLimit = state.getBoolean("CRAboveLimit");
		fanCAboveLimit = state.getBoolean("FanCAboveLimit");
		initialSelfTestFailure = state.getBoolean("InitialSelfTestFailure");
		healthCheckFailure = state.getBoolean("HealthCheckFailure");
		codeChecksumError = state.getBoolean("CodeChecksumError");
		eepromChecksumError = state.getBoolean("EEPROMChecksumError");
		hTOutSideLimits = state.getBoolean("HTOutSideLimits");
	
	}
	
	private int returnData(String dataName, String substanceCategory, JSONObject json) {
		if(json.getJSONArray("Data").getJSONObject(0).getString("SubstanceCategory").equals(substanceCategory))
		{
			return json.getJSONArray("Data").getJSONObject(0).getInt(dataName);
		}
		else if(json.getJSONArray("Data").length() == 2)
		{
			if(json.getJSONArray("Data").getJSONObject(1).getString("SubstanceCategory").equals(substanceCategory))
			{
				return json.getJSONArray("Data").getJSONObject(1).getInt(dataName);
			}
		}
		
		return 0;
	}
}
