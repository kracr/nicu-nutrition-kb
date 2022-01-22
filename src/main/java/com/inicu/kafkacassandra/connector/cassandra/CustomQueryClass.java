/**
 * 
 */
package com.inicu.kafkacassandra.connector.cassandra;

import com.inicu.kafkacassandra.connector.datamodel.CustomHardwareDataModel;

/**
 * @author sanoob
 *
 */
public class CustomQueryClass {

	public String getInsertQueryforCustomHardware(CustomHardwareDataModel dataModel) {

		String queryString;
		queryString = "INSERT INTO inicu.patient_devicedata_inicu (uhid, device_type, device_time, data)"
				+ "VALUES('"+dataModel.getUhid()+"', "
						+ "'"+dataModel.getDeviceName()+"', "
								+ "'"+dataModel.getDeviceTime()+"', "
										+ " '"+dataModel.getData()+"')";

		return queryString;

	}

	public String getInsertQueryforSoftLayer(String patient_id, String event_time, String device_time_nano_sec,
			String device_time_sec, String instance_id, String metric_id, String patient_name,
			String presentation_time_nanosec, String presentation_time_sec, String unit_id, String value,
			String domainId, String vendor_metric_id) {
		String queryString;
		queryString = "INSERT INTO inicu.patientdata (patient_id, event_time, device_time_nano_sec, device_time_sec, instance_id, metric_id, patient_name, presentation_time_nanosec, presentation_time_sec, unit_id, value, domainid, vendor_metric_id)"
				+ "VALUES('" + patient_id + "','" + event_time + "','" + device_time_nano_sec + "','" + device_time_sec
				+ "','" + instance_id + "','" + metric_id + "','" + patient_name + "','" + presentation_time_nanosec
				+ "','" + presentation_time_sec + "','" + unit_id + "','" + value + "','" + domainId + "','"
				+ vendor_metric_id + "');";

		return queryString;
	}

	/*
	 * 
	 * patient_id text, event_time timestamp, device_time_nano_sec text,
	 * device_time_sec text, instance_id text, metric_id text, patient_name
	 * text, presentation_time_nanosec text, presentation_time_sec text, unit_id
	 * text, value text, domainId text, vendor_metric_id text,
	 */

}
