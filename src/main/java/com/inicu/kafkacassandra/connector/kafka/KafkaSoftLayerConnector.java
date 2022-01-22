/**
 * 
 */
package com.inicu.kafkacassandra.connector.kafka;

import org.json.JSONObject;

import com.inicu.deviceadapter.library.InicuDeviceAdapter;
import com.inicu.deviceadapter.library.datamodel.InicuDeviceEvent;
import com.inicu.deviceadapter.library.inicudevicelistener.InicuDeviceListener;
import com.inicu.kafkacassandra.connector.cassandra.CassandraConnector;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.kafkacassandra.connector.util.Util;

/**
 * @author sanoob
 *
 */
public class KafkaSoftLayerConnector implements InicuDeviceListener {

	private CassandraConnector cassandraConnector;
	private InicuDeviceAdapter swlayerDeviceAdapter;
	
	public KafkaSoftLayerConnector() {
		Util.consolePrint("KAFKA SoftLayer Listener", "[starting..]");
		swlayerDeviceAdapter = new InicuDeviceAdapter(Constants.SOFT_LAYER_CONSUMER);
		swlayerDeviceAdapter.addListener(this);
		Util.consolePrint("KAFKA "+Constants.KAFKA_HOST, "[listening]");
		Util.consolePrint("KAFKA SoftLayer Listener", "[ok]");
		Util.consolePrint("CASSANDRA SoftLayer connection", "[starting..]");
		cassandraConnector = new CassandraConnector(Constants.CASSANDRA_HOST, Constants.CASSANDRA_KEYSPACE,
				Constants.CASSANDRA_PORT);
		Util.consolePrint("CASSANDRA SoftLayer connection", "[ok]");
		
		
	}
	
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
						cassandraConnector.pushDatatoCassandra(pushObject, CassandraConnector.SOFTWARE_LAYER_MODE);

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
		
		pushDataToCassandra(event.getData());
		
	}
	

}
