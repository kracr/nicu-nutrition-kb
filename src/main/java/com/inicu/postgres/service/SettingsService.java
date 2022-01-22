package com.inicu.postgres.service;

import java.sql.Timestamp;
import java.util.List;

import com.inicu.models.*;
//import com.inicu.models.NurseBabyPOJO;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.utility.InicuDatabaseExeption;

public interface SettingsService {

	public BedManagementJSON getBedList(String loggedUser, String branchName) throws InicuDatabaseExeption;

	public ResponseMessageWithResponseObject addNicuBed(String roomno, String bedno, String loggedUser, String branchName)
			throws InicuDatabaseExeption;

	public ResponseMessageWithResponseObject addRoom(String roomNo, String loggedUser, String branchName) throws InicuDatabaseExeption;

	public ResponseMessageWithResponseObject deleteBed(String bedid, String loggedUser, String branchName) throws InicuDatabaseExeption;

	public ResponseMessageWithResponseObject changeStatus(String bedid);

	public ResponseMessageWithResponseObject roomChange(String bedId, String roomNo, String loggedUser, String branchName)
			throws InicuDatabaseExeption;

	public ResponseMessageWithResponseObject deleteRoom(String roomid, String loggedUser, String branchName) throws InicuDatabaseExeption;

	public DrugsListJson getListOfMedicine();

	public ResponseMessageObject deleteMedicine(String medid, String loggedUserId) throws InicuDatabaseExeption;

	public ResponseMessageObject addMedicine(Medicines m) throws InicuDatabaseExeption;

	public SettingMasterJsonObj getSettingDetails(String details);

	public ResponseMessageObject saveSetting(SettingMasterJsonObj settingObj);

	public ResponseMessageObject saveHospitalLogo(String imgStr, String branchname);

	public List<KeyValueObj> getHospitalLogo(String branchName);

	public String generateBoxName();

	public String generateSerial();

	public RegisterDeviceDropdownJSon getDropdowns();

	public String generateNeoName();

	public String addBox(BoxJSON box, String branchName);

	public List<String> getListOfAVLmacids();

	public String getIp(String neo_serial);

	boolean isMacAvailable(String macid);

	public ResponseMessageWithResponseObject deleteBox(String neo_serial);

	public ResponseMessageObject editBoxDetails(BoxJSON box);

	public List<DeviceException> getDeviceExceptions(String boxSerialNo);

	public TestsListJSON getTestsDetails(String branchName);

	public ResponseMessageWithResponseObject saveInvestigationPannel(String pannelName, String loggedUser,String branchName, List<TestPOJO> testDetails);

	public ResponseMessageWithResponseObject deleteInvestigationPannel(String pannelName,String branchName);
	
	public ResponseMessageWithResponseObject editInvestigationPannel(String oldPannelName,String newPannelName, String loggedUser,String branchName, List<TestPOJO> testDetails);
	
	public List<DeviceHeartbeat> getDeviceHeartBeat(DeviceHeartBeatModel beat);

	public ResponseMessageObject mapTests(MappedListPOJO mappedJson) throws InicuDatabaseExeption;

	public EmailManagementJSON getEmailData(String branchname);

	public EmailManagementJSON saveEmailData(NotificationEmail emailObj);
	
	public ResponseMessageObject updateUhid(String oldUhid, String newUhid);
	
	public RefNursingOutputParameters getNursingOuputParameters(String branchName);
	
	public RefHospitalbranchname getApneaEventParameters(String branchName);

	public GeneralResponseObject getNurseShiftMapping(String branchName , Timestamp dateOfShift);
	public GeneralResponseObject getDoctorShiftMapping(String branchName , Timestamp dateOfShift);

	public  ResponseMessageObject saveNurseShiftMapping(List<UserShiftMapping> nurseShiftList);

	public GeneralResponseObject getNurseBabyMapping(Timestamp dateOfShift , String branchName );
	
	public  ResponseMessageObject saveNurseBabyMapping(List<NurseBabyPOJO> nurseBabyPOJO , Timestamp dateOfShift , String branchName);
	
	public DisplayDischargedPatientJSON displayDetails(String dischargedUhid, String branchName);
	
	public ResponseMessageObject readmit(String dischargedUhid, String branchName, String nicubedno, String roomnumber);

	public NurseShiftSettingPOJO getNurseShiftSettings(String branchName);

	public ResponseMessageObject saveNurseShiftSettings(String branchName ,NurseShiftSettings nurseShiftSettings);

	public List<NurseBabyPOJO> getAllMappedNurseBaby(Timestamp dateOfShift, String branchName);

	public NurseShiftDetail getNurseDetail(String branchName, String dateOfShift, Integer nurseId);

	public ResponseMessageObject updateAssessmentStatus(String uhid);

	public AssessmentStatusPOJO checkAssessmentStatus(String uhid);

	public DisplayPatientDataJSON validateDischarge(String uhidToDischarge, String branchName);

	public ResponseMessageObject dischargePatient(String uhidToDischarge, String branchName);

	public ResponseMessageObject mergeUhid(String dummyUhid, String originalUhid);

	public ResponseMessageObject configureTempUhid(String state, String branchName);

	public MergeUhidDetailPojo mergeDetailsForUHID(String dummyUhid, String originalUhid);

	public GeneralResponseObject saveClinicalSetting(List<ClinicalAlertSettings> clinicalSetting);
	public GeneralResponseObject deleteItemById(Long alertId);

	public void sendCustomEmail(String subject, String message,String branchName,String mailType);
}
