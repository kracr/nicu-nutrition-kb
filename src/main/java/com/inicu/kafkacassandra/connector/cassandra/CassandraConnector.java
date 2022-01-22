/**
 * 
 */
package com.inicu.kafkacassandra.connector.cassandra;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

import static java.lang.System.out;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.inicu.cassandra.service.DeviceDataService;
import com.inicu.kafkacassandra.connector.constants.Constants;
import com.inicu.kafkacassandra.connector.datamodel.CustomHardwareDataModel;
import com.inicu.postgres.utility.BasicUtils;

/**
 * @author sanoob
 *
 */

public class CassandraConnector {


	
	public static final int CUSTOM_HARDWARE_MODE = 0;
	public static final int SOFTWARE_LAYER_MODE = 1;
	private static String errorMessage = "";
	private Cluster cluster;
	private String serverIP;
	private String keyspace;
	private Session session;
	private int port = 0;

	/*
	 * constructor which initializes cassandra connection
	 * 
	 * @param serverIP: Cassandra server location (in this case localhost)
	 * 
	 * @param keyspace: Cassandra keyspace ('inicu' in our case)
	 * 
	 * @param port: Default Cassandra port (9042)
	 * 
	 */
	public CassandraConnector(String serverIP, String keyspace, int port) {
		this.serverIP = serverIP;
		this.keyspace = keyspace;
		this.port = port;
		connect(this.serverIP, this.port); // 9042
	}

	/*
	 * method to connect to Cassandra
	 */
	public void connect(final String node, final int port) {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
		final Metadata metadata = cluster.getMetadata();
		out.printf("Connected to cluster: %s\n", metadata.getClusterName());
		for (final Host host : metadata.getAllHosts()) {
			out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect(keyspace);
	}

	/*
	 * method to push data into cassandra
	 * 
	 * @param mode: 2 modes as of now CUSTOM_HARDWARE_MODE - push data coming
	 * from the intel board connected with draeger SOFTWARE_LAYER_MODE - push
	 * data acquired from Philips/GE monitor
	 * 
	 * jsonData: JSON data object which is to be pushed into cassandra
	 */
	public boolean pushDatatoCassandra(JSONObject jsonData, int mode) {

		try {

			if (mode == CUSTOM_HARDWARE_MODE) {
				
				System.out.println("inside data parser");
				JSONObject dataJson = new JSONObject(jsonData.getString(Constants.DEVICE_DATA_JSON_KEY));
				
				System.out.println("json:"+dataJson);
				
//				double spontRR = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_SRR));
//				double occPres = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_OCCP));
//				double peepBP = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_PeepBP));
//				double peakBP = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_PeakBP));
//				double minAirP = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_MinAP));
//				double platPrss = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_PlatPRESS));
//				double meanBP = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_MeanBP));
//				double inspo = Double.parseDouble(dataJson.getString(Constants.JSON_DATA_KEY_INS));

				// String query = new
				// CustomQueryClass().getInsertQueryforCustomHardware(
				// new
				// CustomHardwareDataModel(jsonData.getString(Constants.DEVICE_NAME_JSON_KEY),
				// jsonData.getString(Constants.DEVICE_ID_JSON_KEY),
				// jsonData.getString(Constants.DEVICE_DATA_JSON_KEY),
				// jsonData.getString(Constants.DEVICE_TIME_JSON_KEY)));
//				String query = new CustomQueryClass().getInsertQueryforCustomHardware(
//						new CustomHardwareDataModel(jsonData.getString(Constants.DEVICE_NAME_JSON_KEY),
//								jsonData.getString(Constants.DEVICE_ID_JSON_KEY),
//								jsonData.getString(Constants.DEVICE_DATA_JSON_KEY),
//								jsonData.getString(Constants.DEVICE_TIME_JSON_KEY), inspo, meanBP, minAirP, occPres,
//								peakBP, peepBP, platPrss, spontRR));
//				session.execute(query);

			} else if (mode == SOFTWARE_LAYER_MODE) {

				String query = new CustomQueryClass().getInsertQueryforSoftLayer(
						jsonData.getString(Constants.SOFT_JSON_PAT_ID_KEY),
						jsonData.getString(Constants.SOFT_JSON_EVENT_TIME_KEY),
						jsonData.getString(Constants.SOFT_JSON_DEV_TIM_NANO_KEY),
						jsonData.getString(Constants.SOFT_JSON_DEV_TIM_SEC_KEY),
						jsonData.getString(Constants.SOFT_JSON_INST_ID_KEY),
						jsonData.getString(Constants.SOFT_JSON_METRIC_ID_KEY),
						jsonData.getString(Constants.SOFT_JSON_PAT_NAME_KEY),
						jsonData.getString(Constants.SOFT_JSON_PRES_TIME_NANO_KEY),
						jsonData.getString(Constants.SOFT_JSON_PRES_TIME_SEC_KEY),
						jsonData.getString(Constants.SOFT_JSON_UNIT_ID_KEY),
						jsonData.getString(Constants.SOFT_JSON_VALUE_KEY),
						jsonData.getString(Constants.SOFT_JSON_DOMAIN_ID_KEY),
						jsonData.getString(Constants.SOFT_JSON_VEND_MET_ID_KEY));
				session.execute(query);
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
			return false;
		}

	}
	

	/*
	 * get the last error message from the exception handler
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public void pushDatatoCassandra(JSONObject pushObject,
			int mode, String deviceTime, String deviceType, String uhid) {
		try{
			if (mode == CUSTOM_HARDWARE_MODE) {
				
				CustomHardwareDataModel dataModel = new CustomHardwareDataModel(deviceType,
						null, 
						pushObject.get(Constants.DEVICE_DATA_JSON_KEY).toString(), 
						deviceTime,
						uhid);
				
				String query = new CustomQueryClass().getInsertQueryforCustomHardware(dataModel);
				session.execute(query);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
