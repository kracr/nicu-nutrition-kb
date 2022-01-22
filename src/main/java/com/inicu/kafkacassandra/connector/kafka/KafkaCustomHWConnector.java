/**
 * 
 */
package com.inicu.kafkacassandra.connector.kafka;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.deviceadapter.library.InicuDeviceAdapter;
import com.inicu.deviceadapter.library.datamodel.InicuConsumerModel;
import com.inicu.deviceadapter.library.datamodel.InicuDeviceEvent;
import com.inicu.deviceadapter.library.inicudevicelistener.InicuDeviceListener;
import com.inicu.kafkacassandra.connector.cassandra.CassandraConnector;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.kafkacassandra.connector.util.Util;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;

/**
 * @author sanoob
 *
 */

@Repository
public class KafkaCustomHWConnector implements InicuDeviceListener {

	private CassandraConnector cassandraConnector;
	private InicuDeviceAdapter hwDeviceAdapter;

	@Autowired DeviceDataService deviceDataService;
	/*
	 * start cassandra connector and initialize kafka connection
	 */
//	public KafkaCustomHWConnector() {
//		
//		//uncomment for standard hardware layer consumer
//		//		hwDeviceAdapter = new InicuDeviceAdapter(Constants.HW_LAYER_CONSUMER);
//		
//		//uncomment for hardware layer consumer from property file.
//		String isKafka = BasicConstants.props
//				.getProperty(BasicConstants.PROP_KAFKA_CONNECTOR);
//		
//		if(!BasicUtils.isEmpty(isKafka)&&isKafka.equalsIgnoreCase("true")){
//			
//			Util.consolePrint("KAFKA Custom H/W layer Listener", "[starting..]");
//
//			String kafkaHost = BasicConstants.props
//					.getProperty(BasicConstants.PROP_KAFKA_HOST);
//			
//			String kafkaPort = BasicConstants.props
//					.getProperty(BasicConstants.PROP_KAFKA_PORT);
//			
//			String kafkagroupid = BasicConstants.props
//					.getProperty(BasicConstants.PROP_KAFKA_GROUP_ID);
//			
//			String kafkaTopic = BasicConstants.props
//					.getProperty(BasicConstants.PROP_KAFKA_CONNECTOR_TOPIC);
//			
//			InicuConsumerModel HW_LAYER_CONSUMER = new InicuConsumerModel(kafkaHost, kafkaPort, kafkaTopic, kafkagroupid);
//			hwDeviceAdapter = new InicuDeviceAdapter(HW_LAYER_CONSUMER);
//			hwDeviceAdapter.addListener(this);
//			Util.consolePrint("KAFKA "+kafkaHost, "[listening]");
//			Util.consolePrint("KAFKA Custom H/W layer Listener", "[ok]");
//			Util.consolePrint("CASSANDRA Custom H/W layer connection", "[starting..]");
//			cassandraConnector = new CassandraConnector(Constants.CASSANDRA_HOST, Constants.CASSANDRA_KEYSPACE,
//					Constants.CASSANDRA_PORT);
//			Util.consolePrint("CASSANDRA Custom H/W layer connection", "[ok]");
//			
//			System.out.println("connector called.");			
//		}
//
//	}

	/*
	 * pushes data to cassandra table in a new thread.
	 * converts string to json object and pushes
	 * 
	 * @param data: string data from kafka listener
	 * 
	 */
	public void pushDataToCassandra(String data) {
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						
						JSONObject pushObject = new JSONObject(data);
						System.out.println(pushObject);
						 String devId = pushObject.get(Constants.DEVICE_ID_JSON_KEY).toString();
						 System.out.println("dev id:"+devId);
						 if(!BasicUtils.isEmpty(devId)){
							 String uhid = deviceDataService.getUhidFromBoxName(devId);
//							 String uhid = "521198";
							 if(!BasicUtils.isEmpty(uhid)){
								 String deviceTime = pushObject.getString(Constants.DEVICE_TIME_JSON_KEY);
								 String deviceType = pushObject.get(Constants.DEVICE_NAME_JSON_KEY).toString();
								 cassandraConnector.pushDatatoCassandra(pushObject, CassandraConnector.CUSTOM_HARDWARE_MODE,deviceTime,deviceType,uhid);								 
							 }
						 }
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDataAvailable(InicuDeviceEvent event) {
//		Util.consolePrint("data", event.getData());
		//System.out.println("data"+event.getData());
		System.out.println("cassandra device data available :");
		pushDataToCassandra(event.getData());

	}
	
	public static void main1 (String args[]){
		new KafkaCustomHWConnector();
	}
}
