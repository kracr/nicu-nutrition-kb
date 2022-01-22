package com.inicu.device.geb450monitor.carescapeACK;

//import java.sql.Timestamp;
//import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.postgres.utility.BasicConstants;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v23.message.ORU_R01;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.parser.Parser;

public class GEB450ParseData {

	private static int countSubstring(String main, String sub) {

		int count = 0;
		String str = main;
		String findStr = sub;
		int lastIndex = 0;

		while (lastIndex != -1) {

			lastIndex = str.indexOf(findStr, lastIndex);

			if (lastIndex != -1) {
				count++;
				lastIndex += findStr.length();
			}
		}

		return count;

	}
	@SuppressWarnings(value = { "DEBUG" })
	public static void parse(String hl7Message) {

		HapiContext context = null;
		Parser parser = null;
		Message hl7Msg;
		ORU_R01 oruMsg;
		ORU_R01_ORDER_OBSERVATION orderObservation;

		String timestamp = "";
		String bedId = "";
		
		try {
			if (hl7Message != null && !hl7Message.isEmpty()) {
				System.out.println("Inside HL7Parser, data is : " + hl7Message);
				context = new DefaultHapiContext();
				parser = context.getPipeParser();
				hl7Msg = parser.parse(hl7Message);
				oruMsg = (ORU_R01) hl7Msg;
				orderObservation = oruMsg.getRESPONSE().getORDER_OBSERVATION();

				String PID = oruMsg.getRESPONSE().getPATIENT().getPID().encode();
				PID = PID.replace("^", " ");
				PID = PID.replace('|', ' ');
				PID = PID.replace("   ", " ");
				String[] patientID = PID.split(" ");
				String finalPatientID = patientID[1];
				timestamp = oruMsg.getMSH().getDateTimeOfMessage().encode();
//				System.out.println("time stamp : " + timestamp + ", PatientID " + finalPatientID);
				bedId = oruMsg.getRESPONSE().getPATIENT().getVISIT().getPV1().getPv13_AssignedPatientLocation()
						.encode();
				bedId = bedId.replace("^", " ");
				String[] onlybedId = bedId.split(" ");
				String finalBedID = onlybedId[1];
//				System.out.println("Bed id : " + finalBedID);

				/*
				 * parse the OBX segment
				 */
				JSONObject datajson = new JSONObject();
				int count = countSubstring(hl7Message, "OBX");
				System.out.println(" count value : " + count);
				for (int index = 0; index < count; index++) {

					OBX obx = orderObservation.getOBSERVATION(index).getOBX();
					String obx5encoded = obx.getObx5_ObservationValue(0).encode();
					String obxId = obx.getObx3_ObservationIdentifier().encode();
					datajson.put(obxId, obx5encoded);
//					System.out.println("the message is : " + obx5encoded);
//					System.out.println("the message is 1 : " + obxId);
				}
				Long longdate = new Date().getTime();
				Date Ddate = new Date(longdate);
//				Timestamp date=new Timestamp(Ddate.getTime()); 
				
	            
				System.out.println("parsed data is : " + datajson.toString());

				GEB450ParseData geb450ParseData = new GEB450ParseData();
				geb450ParseData.updateCassandra(finalPatientID, finalBedID, timestamp, Ddate, datajson.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void updateCassandra(String finalPatientID, String finalBedID, String timestamp, Date date, String data) {
		DeviceDataService deviceService = BasicConstants.applicatonContext.getBean(DeviceDataService.class);
		deviceService.saveCarescapeACKDataCassandra(finalPatientID, finalBedID, timestamp, date, data);
	}

}
