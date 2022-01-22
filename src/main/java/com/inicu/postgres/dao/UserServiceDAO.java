package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inicu.models.AddUserJSON;
import com.inicu.models.DashboardJSON;
import com.inicu.models.DashboardSearchFilterObj;
import com.inicu.models.Doctor;
import com.inicu.models.GetRoleObj;
import com.inicu.models.ManageRoleObj;
import com.inicu.models.RoleObj;
import com.inicu.models.UserListPojo;
import com.inicu.models.UserStatusJSON;
import com.inicu.postgres.entities.DashboardDeviceDetailView;
import com.inicu.postgres.entities.Hl7DeviceMapping;
import com.inicu.postgres.entities.ReAdmitChildDetailView;
import com.inicu.postgres.entities.User;
import com.inicu.postgres.entities.UserInfoView;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public interface UserServiceDAO {

	boolean validateUser(String doctorId);

	List<UserInfoView> getUserList(String loggedUser, String branchName) throws InicuDatabaseExeption;
	
	boolean addDoctor(Doctor doc);

	GetRoleObj getRoles();

	boolean addUser(AddUserJSON addUser);
	
	List executeQuery(String query);

	boolean setStatus(UserStatusJSON userStatus);

	boolean saveObject(Object obj);

	List executeNativeQuery(String maxQuery);

	List<DashboardDeviceDetailView> getDashboardList(DashboardSearchFilterObj filterObj, String branchName);

	void removeObject(Object obj);

	void updateOrDelete(String delQuery);

	List getListFromMappedObjNativeQuery(String queryStr);

	void updateObject(Hl7DeviceMapping hl7DeviceMapping);

	void updateOrDelQuery(String query) throws Exception;
	
	List<DashboardDeviceDetailView> getDashboardListUser(DashboardSearchFilterObj filterObj, String uhid);
	
	List<ReAdmitChildDetailView> getReadmitChildList(DashboardSearchFilterObj filterObj, String uhid);
}
