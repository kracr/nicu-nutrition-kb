package com.inicu.cassandra.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.inicu.models.*;
import com.inicu.postgres.entities.QuestionnaireResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.inicu.analytics.entities.FittingResult;
import com.inicu.cassandra.entities.CameraImageStream;
import com.inicu.cassandra.models.AddDeviceMasterJson;
import com.inicu.cassandra.models.AddDeviceTemplateJson;
import com.inicu.cassandra.models.MonitorDummyDataJson;
import com.inicu.cassandra.models.MonitorJSON;
import com.inicu.cassandra.models.NewRegisteredDevicesJSON;
import com.inicu.postgres.entities.DeviceDetail;
import com.inicu.postgres.entities.VwVitalCerebralExport;

/**
 * Service interface to perform CRUD operations.
 * @author OXYENT
 * @version 1.0
 */

@Repository
public interface DeviceDataService {

	public void startService(List<String> listOfId);
	public List<PatientDataJsonObject> getPatientData(String timeStamp);
	public void migrateDataToPostgres();
	public MonitorJSON getPatientECGData(String id);
	public MonitorJSON getPatientBPData(String id);
	public MonitorJSON getPatientPulseData(String id);
	public List<PatientDataJsonObject> getPatientVariableData(String id);
	public ResponseMessageObject startPhilipsMonitoring(AddDeviceTemplateJson deviceData);
	public AddDeviceMasterJson getDeviceTemplateData(String uhid, String bedid, String branchName);
	public MonitorJSON getPatientDummyData(String param);
	public MonitorJSON getStoredDeviceData(String patientId,String parameter);
	public List<PatientDataJsonObject> getStoredDeviceNumericData(String patientId);
	public List<PatientDataJsonObject> getDummyVariableData();
	public List<PatientDataJsonObject> getDummyVentilatorData();
	public List<PatientDataJsonObject> showDummyVentilatorData();
	public MonitorDummyDataJson getAllMonitorData();
	public void dataMigrationService();
	public MonitorDummyDataJson getMonitorData(String uhid, Date currentTime);
	public void saveInifinityDataCassandra(Date date, String data, String bedId);
	public ResponseMessageObject startDeviceMonitoring(AddDeviceTemplateJson deviceData);
	public MonitorWaveformData getDeviceWaveFormData(String uhid,
			Date currentTime);
	public String getUhidFromDeviceId(String devId);
	public ResponseMessageObject disconnectDevice(String uhid, String deviceId);
	/*public DeviceGraphDataJson getGraphData(String uhid, String currentDate);*/
	public DeviceGraphDataJson getDevGraphData(DeviceGraphDataJson graphData, String entryDate, String endDate);
	
	//Get Active Filters Data
	public ActiveFilterGraphDataJson getActiveFiltersData(String uhid, String entryDate, String endDate);

	//Get Camera Image For Trends
	public List<CameraImageStream> getCameraImageTrends(String uhid, Timestamp deviceTime, Boolean flag);

	//Get Camera Image For Trends With Pagination
	public List<CameraImageStream> getCameraImageTrendsWithPagination(String uhid, Timestamp deviceTime, Boolean flag, Integer pageNumber, Integer pageSize);

//	public JSONObject getTokenForichr(String uhid,String deviceId,String message);
	public String getLiveVideoCredentialForichr(String uhid);
	public RegisterDeviceMasterJson getRegisteredDevice();
	public RegisterDeviceMasterJson registerDevice(
			RegisterDeviceMasterJson response);
	public ResponseMessageObject addBoard(String boardName);
	public InicuDeviceMastJson getInicuDeviceDetails();
	public ResponseMessageObject addBeagleBoneDevices(String boardName,
			String boxName, String macid);
	public ResponseMessageObject deleteBoard(String macid);
	public void saveDataToCassandra(Date date, String string, String string2);
	public ResponseMessageObject mapInicuDeviceWithPatient(AddDeviceTemplateJson deviceData, String entryDate);
	public ResponseMessageObject disconnectInicuDevices(
			AddDeviceTemplateJson device, String date);
	public void saveDataReceivedFromBox(JSONObject json);
	public void saveMultipleDataReceivedFromBox(JSONArray jsonArray);
	public void saveDeviceMonitorDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid, Long deviceTime);
	public void saveDeviceCerebralOximeterDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid, Long deviceTime);
	public void saveApneaEventDetail(HashMap<String, Float> mappedMean, String uhid, Long deviceTime);
	public void saveVentilatorDetail(HashMap<String, Float> mappedMean,
			String uhid, String beddeviceid, Long deviceTime);
	public void saveDeviceIncubatorWarmerDetail(HashMap<String, Float> mappedMean, String uhid, String beddeviceid,
			Long deviceTime);
	
	public String getUhidFromBoxName(String devId);
	public void pushKafkaDataToCassandra(String uhid, JSONObject pushObject,
			String deviceTime, String deviceType);
	public void saveDeviceVentDetailKdah(HashMap<String, Float> mappedMean,
			String uhid, String beddeviceid, Long deviceTime);
	public void saveVentDataToPilotPostgres(String jsonData);
	public void saveDeviceDataPostgres(String jsonData);
	public void saveMutlipleBloodGasDeviceDataPostgres(String jsonArrayData);


	//Get Uhid for Camera
	public String getCameraUhid(String boxName);
	
	//Get Capturing Stautus
	public String getCapturingStatus(String uhid);

	//Set Capturing Status
	public void setCapturingStatus(JSONObject jsonCapturingStatus);

	//attach a box with uhid for camera
	public void saveImageReceivedFromBoxForCamera(JSONObject jsonData);
	
	//get feeds from table
	public List<String> getCameraFeed(String uhid);
	
	//delete old feeds from camera_feed
	public void deleteCameraFeed(String timeout);
	boolean macidExists(String mac);
	boolean savedevDetailsObject(RegisteredDevicesJson devDetails);
	public NewRegisteredDevicesJSON getRegisteredDevicesNew(String branchName);
	public ResponseMessageObject addMacidToDeviceDetail(String macid);
	public String getDeviceId(String message);
	
	public String getDeviceSettingForProgram(String macid);
	boolean macidExistsInMacIDTable(String macid);
	public void saveDeviceDataException(JSONObject json);
	public void insertRegressionModelIntoDatabase(FittingResult fittingResultBaseMeanHR_HM) throws Exception;
	public InicuServerTimeJson getServerTime();
	public DeviceDetail getDeviceDetail(String boxSerialNo,String boardName);
	List<VitalCerebralExportPOJO> getExportVitalData(String uhid, String entryDate, String endDate);

	public void saveQuestionnaireResult(QuestionnaireResult questionnaireResult);
	public String startVideoRecording(String uhid);
	public String stopVideoRecording(String uhid);
	ResponseMessageObject importDeviceData(String uhid, String fileName, String referenceDate);

	public void saveCarescapeACKDataCassandra(String finalPatientID, String finalBedID, String timestamp, Date date,
		      String data);
}
