import org.json.JSONObject;

import HttpClient.HttpClient;
import Sensors.AP2CeAPI;
import Sensors.AP2CeData;
import Sensors.LCDAPI;
import Sensors.LCDData;

// Simple test to see if HttpClient works. More extensive tests to fulfill SRS will need to be made.
public class mainTest { 

	public static void main(String[] args) {
		String hostname = "http://localhost:8732";
		HttpClient client = new HttpClient(hostname); // Make sure host / url is correct.	
		
		try {
//Raw tests of HttpClient
//			System.out.println("http://localhost:2000/sensor/ap2ce");
//			String Id = client.sendPOST("sensors/ap2ce");
//			System.out.println(Id);
//			System.out.println(client.sendGET("sensors/ap2ce/" + Id));
//			JSONObject testJSON = new JSONObject("{"
//			+ " \"Data\": [{"
//			+ "		\"BarCount\": 5,"
//			+ "		\"VolumeConcentration\": 20,"
//			+ "		\"SubstanceIndex\": 3"
//			+ "	},"
//			+ " {"
//			+ "		\"BarCount\": 5,"
//			+ "		\"VolumeConcentration\": 20,"
//			+ "		\"SubstanceIndex\": 3"
//			+ "	}],
//			+ "	\"Id\": \"abc\","
//			+ "	\"Position\": {"
//			+ "		\"Altitude\": 0,"
//			+ "		\"Latitude\": 1,"
//			+ "		\"Longitude\": 2"
//			+ " },"
//			+ "	\"State\": {"
//			+ " 	\"AudioFault\": true,"
//			+ " 	\"CRAboveLimit\": false,"
//			+ " 	\"ChangeBattery\": false,"
//			+ " 	\"ChangeSievePack\": false,"
//			+ " 	\"CodeChecksumError\": false,"
//			+ " 	\"CoronaBurnOff\": false,"
//			+ " 	\"EEPROMChecksumError\": false,"
//			+ " 	\"FanCAboveLimit\": false,"
//			+ " 	\"FatalError\": false,"
//			+ " 	\"GAlert\": false,"
//			+ " 	\"GHighDoseAlert\": false,"
//			+ " 	\"GMediumDoseAlert\": false,"
//			+ " 	\"HAlert\": false,"
//			+ " 	\"HHighDoseAlert\": false,"
//			+ " 	\"HTOutSideLimits\": false,"
//			+ " 	\"HealthCheckFailure\": false,"
//			+ " 	\"InitialSelfTest\": false,"
//			+ " 	\"InitialSelfTestFailure\": false,"
//			+ " 	\"LowBattery\": false,"
//			+ " 	\"LowSieve\": false,"
//			+ " 	\"PTOutOfRange\": false,"
//			+ " 	\"TICAlert\": false,"
//			+ " 	\"TICMode\": false"
//			+ "}"
//			+ "}");
//			System.out.println(testJSON.getJSONArray("Data").getJSONObject(0).getInt("SubstanceIndex"));
			
//			JSONObject obj = new JSONObject("{"
//			+ "		\"Longitude\": 5,"
//			+ "		\"Latitude\": 20,"
//			+ "		\"Altitude\": 3"
//			+ "	}");
//			System.out.println(obj.toString());
//			client.sendPUT("sensors/ap2ce/" + Id, obj.toString());
//			ADD PUT VARIANT HERE!
//			client.sendDELETE("sensors/ap2ce/" + Id);
			
//Sensor function tests
			/*
			System.out.println("START TESTS");
			System.out.println("START TEST AP2C");
			AP2CeAPI AP2CTest = new AP2CeAPI(hostname);
			AP2CTest.startSimulation();
			AP2CeData data = AP2CTest.fetchData();
			
			System.out.println(data.batteryLow);
			AP2CTest.updatePosition(1, 2);
			
			AP2CTest.updatePosition(15, 5);
			System.out.println(data.gBarCount);
			
			System.out.println(data.batteryLow);
			System.out.println(data.detectorReady);
			System.out.println(data.deviceFault);
			System.out.println(data.hydrogenTankEmpty);
			System.out.println(data.purge);
			AP2CTest.endSimulation();
			System.out.println("END TEST AP2C");
			*/
			System.out.println("START TEST LCD");
			LCDAPI LCDTest = new LCDAPI(hostname);
			LCDTest.startSimulation();
			LCDTest.nvgToggle();
			LCDData data = LCDTest.fetchData();
			//LCDTest.updatePosition(15, 5);
			System.out.println(data.gBarCount);
			System.out.println(data.gSubstanceIndex);
			System.out.println(data.detectionMode);
			System.out.println(data.audioFault);
			System.out.println(data.changeBattery);
			System.out.println(data.changeSievePack);
			System.out.println(data.codeChecksumError);
			System.out.println(data.coronaBurnOff);
			System.out.println(data.crAboveLimit);
			System.out.println(data.eepromChecksumError);
			System.out.println(data.fanCAboveLimit);
			System.out.println(data.fatalError);
			System.out.println(data.gAlert);
			System.out.println(data.gHighDoseAlert);
			System.out.println(data.gMediumDoseAlert);
			System.out.println(data.hAlert);
			System.out.println(data.healthCheckFailure);
			System.out.println(data.hHighDoseAlert);
			System.out.println(data.hTOutSideLimits);
			System.out.println(data.initialSelfTest);
			System.out.println(data.initialSelfTestFailure);
			System.out.println(data.lowBattery);
			System.out.println(data.lowSieve);
			System.out.println(data.pTOutOfRange);
			System.out.println(data.ticAlert);
			System.out.println(data.ticMode);
			System.out.println("CHANGING DETECTION MODE");
			System.out.println(data.detectionMode);
			LCDTest.updateDetectionMode(LCDAPI.TIC);
			data = LCDTest.fetchData();
			System.out.println(data.detectionMode);
			//LCDTest.endSimulation();
			System.out.println("END TEST LCD");
			
			System.out.println("END TESTS");
			
			/*
			 * If these tests pass then all normal calls to WISE from java succeed.
			 * Events are not handled. AP2C has none, LCD has a few. TODO: Fix events.
			 */
			
			
/* Notes that might be relevant: 
 * In WISE we create a groundVehicle when we start simulations. 
 * These are never removed when endsimulation is called. Fix?
 * 
 * */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			System.out.println(client.getJson(1)); // Test get Json
//			
//			JSONObject obj = new JSONObject();
//			obj.put(1, 1); // Change values to test when we know exactly what needs to be in here!
//			
//			StringWriter out = new StringWriter();
//		    obj.writeJSONString(out);
//			client.sendEvent(out.toString()); //Test sending event
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}