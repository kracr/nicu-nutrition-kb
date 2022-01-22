package com.inicu.postgres.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.inicu.models.*;
import com.inicu.postgres.entities.*;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface UserPanelService {

	boolean validateUser(String doctorId);

	UserListJson getUserList(String loggedUser, String branchName) throws InicuDatabaseExeption;

	boolean addDoctor(Doctor doc);

	GetRoleObj getRoles();

	ResponseMessageObject addUser(UserListPojo addUser) throws InicuDatabaseExeption;

	boolean setUserStatus(UserStatusJSON userStatus);

	HashMap<String, VwVitalDetail> getDashboardVitalDetail(String branchName) throws InicuDatabaseExeption;

	VwVitalDetail getDashboardVitalDetailByUhid(String uhid) throws InicuDatabaseExeption;

	boolean isDeviceConnected(String uhid);
	List<DashboardJSON> getDashboard(DashboardSearchFilterObj filterObj, String branchName) throws InicuDatabaseExeption;

	boolean setStatus(ManageRoleObj manageRoleObj);

	List<ExportBedDetailsCSV> getDashboardCsvData(String branchName);

	List<KeyValueObj> getImageData(String imageName, String branchname) throws InicuDatabaseExeption;

	void migrateDataToPostgres();

	List<String> getMessageList(String uhid) throws InicuDatabaseExeption;

	InfinityDeviceJsonObject getInifityDevices(String uhid, String bedid);

	ResponseMessageObject saveInifinityDeviceMapping(InfinityDeviceJsonObject bedDetail);

	HashMap<String, String> getAddSettingPreference();

	public List<DashboardJSON> searchPatients(DashboardSearchFilterObj filterobj);

	void disconnectInfinityDevice(InfinityDeviceJsonObject bedDetail);

	List<DashboardJSON> getDashboardUser(DashboardSearchFilterObj filterObj, String uhid) throws InicuDatabaseExeption;

	List<DashboardJSON> getReadmitChildData(DashboardSearchFilterObj filterObj, String uhid)
			throws InicuDatabaseExeption;

	List<WeightTrackingJSON> getWeightTrackingData(String branchName) throws InicuDatabaseExeption;

	ResponseMessageWithResponseObject saveWeightTrackingData(List<WeightTrackingJSON> weightTrackingObj,
			String loggedUser, String branchName);

	ResponseMessageWithResponseObject storeImage(String imageName, String imageData);

	ImagePOJO getImage(String imageName);

	boolean raiseAlarm(Timestamp ventilatorTime, Timestamp monitorTime);
	
	List<DoctorTasks> getDoctorTasks(String branchName);
	
	List<DoctorTasks> getNewDoctorTasks(String branchName);
	
	List<NurseTasks> getNurseTasks(String branchName);

	List<NurseTasks> getNurseTasks(String branchName, String uhid);


	BabyDetailPOJO getActiveBabies(String branchName);

//	List<DashboardJSON> getCounsellingSheet();
	List<DashboardJSON> getListOfDashboard(List<DashboardDeviceDetailView> listOfDashboard, List<Object[]> listOfBed);
	
	List<RoleManager> getRoleStatus(String roleId, String branchName, String uhid);
	AnthropometryProgressRate getAnthropometryGrowthRate(String uhid);

}
