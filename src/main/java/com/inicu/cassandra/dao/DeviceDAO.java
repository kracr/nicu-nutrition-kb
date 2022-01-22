package com.inicu.cassandra.dao;

import java.util.List;

import com.inicu.cassandra.entities.DeviceDataCarescapeACK;
import com.inicu.cassandra.entities.DeviceDataInfinity;
import com.inicu.cassandra.entities.PatientDeviceDataInicu;
import com.inicu.cassandra.entities.PatientMonitorData;
import com.inicu.cassandra.models.MonitorJSON;
import com.inicu.models.PatientDataJsonObject;
import com.inicu.postgres.entities.CameraFeed;
import com.inicu.postgres.entities.PatientData;

/**
 * DAO interface to perform CRUD operation.
 * @author Dipin
 * @version 1.0
 */

public interface DeviceDAO {
	public void startService(PatientData pd);
	public List<PatientDataJsonObject> getData(String timeStamp);
	public MonitorJSON getECGData(String id);
	public MonitorJSON getBPData(String id);
	public MonitorJSON getPulseData(String id);
	public List<PatientDataJsonObject> getVariableData(String id);
	public MonitorJSON getDummyData(String param);
	public List<PatientDataJsonObject> getDummyVariableData();
	public List<PatientDataJsonObject> getDummyVentilatorData();
	public List<PatientDataJsonObject> getDummyVariableData(Integer randomMinute,
			Integer randomSecond);
	public MonitorJSON getPatientDummyData(String string, Integer randomMinute,
			Integer randomSecond);
	public List executeNativeQuery(String query);
	public void createPatientMonitorData(PatientMonitorData pd);
	public void create(DeviceDataInfinity dev);
	public void inseriNICURecord(PatientDeviceDataInicu pd);
	public void create(DeviceDataCarescapeACK dev);
	
}
