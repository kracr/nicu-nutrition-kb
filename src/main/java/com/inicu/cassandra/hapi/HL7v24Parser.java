package com.inicu.cassandra.hapi;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.postgres.utility.BasicConstants;


@Repository
public class HL7v24Parser {

	@Autowired
	DeviceDataService deviceDataService;
	
	
	public static void parse(String hl7Message){
			
			String hl7msg = hl7Message;
			try{
//				String hl7Message = "MSH|^~\\&|NIHON KOHDEN|NIHON KOHDEN|CLIENT APP|CLIENT FACILITY|20170525210100||ORU^R01^ORU_R01|20170525000003|P|2.4|||NE|AL||ASCII||ASCII PID||||||||O PV1||I|^^BED-001^192.168.1.2:1 ORC|RE OBR|1|||VITAL|||20170525210100||||||||||||||||||A OBX|1|NM|007001^VITAL PR(spo2)|1|94|/min|||||F|||20170525210100||| OBX|2|NM|007000^VITAL SpO2|1|100|%|||||F|||20170525210100||| OBX|3|NM|072007^VITAL rPR(spo2)|1|94|/min|||||F|||20170525210100|||";
				JSONObject hl7Json = new JSONObject();
				JSONObject dataJson = new JSONObject();
//				HapiContext context = new DefaultHapiContext();
//				Parser parser = context.getGenericParser();
//				Message hl7Msg = parser.parse(hl7Message);	
	//
//				ORU_R01 oruMsg = (ORU_R01) hl7Msg;
//				String timestamp = oruMsg.getMSH().getDateTimeOfMessage().encode();
//				if(!BasicUtils.isEmpty(timestamp)){
//					hl7Json.put("timestamp", timestamp);
//				}
				hl7Json.put("device", "Nihon Kohden");

//				String hl7msg = oruMsg.toString();




				if(hl7msg.contains("SpO2")){
					String spo2WithPipe = hl7msg.substring(hl7msg.indexOf("SpO2"), hl7msg.indexOf("%"));
					int counter = 0;
					for( int i=0; i<spo2WithPipe.length(); i++ ) {
						if( spo2WithPipe.charAt(i) == '|' ) {
							counter++;
						} 
					}

					String[] spo2 = spo2WithPipe.split("\\|",counter+1);
					System.out.println(spo2[counter-1]);

					dataJson.put("SPO2",spo2[counter-1]);
				}

				if(hl7msg.contains("PR(spo2)")){
					String pRwithMin = hl7msg.substring(hl7msg.indexOf("PR(spo2)"),hl7msg.indexOf("min"));
					int counter = 0;
					for( int i=0; i<pRwithMin.length(); i++ ) {
						if( pRwithMin.charAt(i) == '|' ) {
							counter++;
						} 
					}

					String[] pr = pRwithMin.split("\\|",counter+1);
					System.out.println(pr[counter-1]);
					dataJson.put("PR", pr[counter-1]);

					hl7Json.put("data", dataJson);

					System.out.println(hl7Json);
					Date date = new Date();
//					Date date = Util.convertToDate(timestamp);

					DeviceDataService deviceService = BasicConstants.applicatonContext.getBean(DeviceDataService.class);
					// bbox1 is inicu medical box conatining beaglebone boxes.
					
					deviceService.saveDataToCassandra(date,dataJson.toString(),"bbox1");

				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}
}
