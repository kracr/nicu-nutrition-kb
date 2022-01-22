package com.inicu.postgres.daoImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inicu.models.AddUserJSON;
import com.inicu.models.DashboardSearchFilterObj;
import com.inicu.models.Doctor;
import com.inicu.models.GetRoleObj;
import com.inicu.models.ManageRoleObj;
import com.inicu.models.RoleObj;
import com.inicu.models.UserStatusJSON;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.DashboardDeviceDetailView;
import com.inicu.postgres.entities.Hl7DeviceMapping;
import com.inicu.postgres.entities.ReAdmitChildDetailView;
import com.inicu.postgres.entities.User;
import com.inicu.postgres.entities.UserInfoView;
import com.inicu.postgres.service.LogsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InicuPostgresUtililty;

@Repository
public class UserServiceDAOImpl implements UserServiceDAO {

	@Autowired
	LogsService logsService;

	@Autowired
	InicuPostgresUtililty inicuPostgresUtility;

	@Autowired
	InicuDaoImpl inicuDao;

	@Autowired
	private InicuDatabaseExeption databaseException;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public boolean validateUser(String doctorId) {
		// return true if the user has rights to see list.
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfoView> getUserList(String loggedUser, String branchName) throws InicuDatabaseExeption {

		String query="Select UserInfo from UserInfoView as UserInfo where branchname = '" + branchName +
				"'";
//		String query="Select UserInfo from UserInfoView as UserInfo where branchname = '" + branchName +
//				"' order by UserInfo.rolename='Admin' desc," +
//				" UserInfo.rolename='SuperUser' desc," +
//				" UserInfo.rolename='Sr. Doctor' desc," +
//				" UserInfo.rolename='Jr. Doctor' desc," +
//				" UserInfo.rolename='Nurse' desc, " +
//				" UserInfo.rolename='Frontdesk' desc";

		List<UserInfoView> userInfo = null;
		try {
			userInfo = inicuPostgresUtility.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, "",
					"FAIL_ACCESS", BasicUtils.convertErrorStacktoString(e));
		}
		return userInfo;
	}

	@Override
	public boolean addDoctor(Doctor doc) {
		if (doc != null) {
			// save the doctor to database;
		}
		return true;
	}

	@Override
	public GetRoleObj getRoles() {
		GetRoleObj getRole = new GetRoleObj();
		Set<String> listOfRoles = new LinkedHashSet<>();
		// String fetchRoles="SELECT distinct(roleName) FROM Role";
		// List<String> listOfRoles =
		// inicuPostgresUtility.executeMappedObjectCustomizedQuery(fetchRoles);
		// if(!BasicUtils.isEmpty(listOfRoles)){
		// getRole.setAllRoles(listOfRoles);
		// }

		String fetchRolesSeq = "SELECT column_name FROM information_schema.columns WHERE table_schema = '"
				+ BasicConstants.SCHEMA_NAME + "' AND table_name = 'rolemanagement_final' "
				+ "order by ordinal_position";
		List<String> listRole = inicuPostgresUtility.executePsqlDirectQuery(fetchRolesSeq);
		if (!BasicUtils.isEmpty(listRole)) {
			getRole.setAllModules(listRole);
			String query = "SELECT * from rolemanagement_final";
			List<Object[]> resultSet = inicuPostgresUtility.executePsqlDirectQuery(query);
			if (!BasicUtils.isEmpty(resultSet)) {
				List<ManageRoleObj> listOfManRole = new ArrayList<>();
				for (int i = 0; i < resultSet.size(); i++) {
					ManageRoleObj manageRole = new ManageRoleObj();
					Object[] objArr = resultSet.get(i);
					String moduleName = objArr[0].toString();
					manageRole.setModuleName(moduleName);
					List<RoleObj> listOfRoleObj = new ArrayList<>();
					for (int j = 0; j < listRole.size(); j++) {
						if (j == 0) {
							continue;
						}
						RoleObj roleObj = new RoleObj();
						String roleName = listRole.get(j).toString();
						listOfRoles.add(roleName);
						roleObj.setRoleName(roleName);
						// set status as well
						String status = objArr[j].toString();
						{
							if (!BasicUtils.isEmpty(status)) {
								HashMap<String, Boolean> stringMap = new HashMap<>();
								if (status.equalsIgnoreCase("R")) {
									stringMap.put("read", true);
									stringMap.put("write", false);
								} else if (status.equalsIgnoreCase("W")) {
									stringMap.put("read", false);
									stringMap.put("write", true);
								} else if (status.equalsIgnoreCase("RW")) {
									stringMap.put("read", true);
									stringMap.put("write", true);
								} else {
									stringMap.put("read", false);
									stringMap.put("write", false);
								}
								roleObj.setStatus(stringMap);
							}
						}
						listOfRoleObj.add(roleObj);
						getRole.setAllRoles(new ArrayList<String>(listOfRoles));
					}

					manageRole.setListOfRoleObj(listOfRoleObj);
					listOfManRole.add(manageRole);
				}
				getRole.setValues(listOfManRole);
			}
		}
		return getRole;
	}

	@Override
	public boolean addUser(AddUserJSON addUser) {
		return false;
	}

	/**
	 * executing customized queries
	 */
	public List executeQuery(String query) {
		try {
			return inicuPostgresUtility.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean setStatus(UserStatusJSON userStatus) {
		boolean success = false;
		String userName = userStatus.getUsername();
		String status = userStatus.getStatus();
		String branchname=userStatus.getBranchname();
		if (!BasicUtils.isEmpty(userName) && !BasicUtils.isEmpty(status)) {
			try {
				String query="select obj from User obj where branchname='"+branchname+"' and username='"+userName+ "' and (active=1 OR active=0)";
				List<User> userList= (List<User>) inicuDao.getListFromMappedObjQuery(query);
//				User user = inicuPostgresUtility.getEntityManager().find(User.class, userName);
				User user=null;
				if(userList!=null){
					 user= userList.get(0);
				}
				if (user != null) {
					int userstatus = 0;
					if (status.equalsIgnoreCase("true")) {
						userstatus = 1;
					} else if(status.equalsIgnoreCase("false")) {
						userstatus = 0;
						Calendar cal = Calendar.getInstance();
						Date date = new Date(cal.getTimeInMillis());
						user.setInactiveDate(date);
					} else if(status.equalsIgnoreCase("delete")){
						userstatus = -1;
						Calendar cal = Calendar.getInstance();
						Date date = new Date(cal.getTimeInMillis());
						user.setInactiveDate(date);
					}
					user.setActive(userstatus);
					inicuPostgresUtility.saveObject(user);
				}

				// logs
				String desc = mapper.writeValueAsString(user);
				String action = BasicConstants.UPDATE;
				String loggeduser = null;
				if (!BasicUtils.isEmpty(userStatus.getUserId())) {
					loggeduser = userStatus.getUserId();
				} else {
					loggeduser = "1234";
				}

				String pageName = BasicConstants.USER_LIST;
				logsService.saveLog(desc, action, loggeduser, null, pageName);
				success = true;
			} catch (Exception e) {
				success = false;
			}
		}
		return success;
	}

	@Override
	public boolean saveObject(Object obj) {
		try {
			inicuPostgresUtility.saveObject(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List executeNativeQuery(String query) {
		return inicuPostgresUtility.executePsqlDirectQuery(query);
	}

	@Override
	public List<DashboardDeviceDetailView> getDashboardList(DashboardSearchFilterObj filterObj, String branchName) {

		String query = "Select DashboardInfo from DashboardDeviceDetailView as DashboardInfo where branchname = '" + branchName + "' order by admitDate";
		/*
		 * if(!BasicUtils.isEmpty(filterObj)){ query = query+" where "
		 * if(!BasicUtils.isEmpty(filterObj.getLevel())){ query = query
		 * +" where" }
		 * 
		 * }
		 */
		List<DashboardDeviceDetailView> dashboardInfo = null;
		try {
			dashboardInfo = inicuPostgresUtility.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashboardInfo;
	}

	@Override
	public void removeObject(Object obj) {
		try {
			inicuPostgresUtility.removeObject(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrDelete(String delQuery) {
		try {
			inicuPostgresUtility.updateOrDelQuery(delQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List getListFromMappedObjNativeQuery(String queryStr) {
		List listObj = null;
		try {
			listObj = inicuPostgresUtility.executeMappedObjectCustomizedQuery(queryStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listObj;
	}

	@Override
	public void updateObject(Hl7DeviceMapping hl7DeviceMapping) {
		try {
			inicuPostgresUtility.updateObject(hl7DeviceMapping);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrDelQuery(String query) throws Exception {
		// TODO Auto-generated method stub
		inicuPostgresUtility.updateOrDelNativeQuery(query);
	}

	@Override
	public List<DashboardDeviceDetailView> getDashboardListUser(DashboardSearchFilterObj filterObj, String uhid) {

		String query = "Select DashboardInfo from DashboardDeviceDetailView as DashboardInfo where uhid='"+uhid+"'";
		/*
		 * if(!BasicUtils.isEmpty(filterObj)){ query = query+" where "
		 * if(!BasicUtils.isEmpty(filterObj.getLevel())){ query = query
		 * +" where" }
		 * 
		 * }
		 */
		List<DashboardDeviceDetailView> dashboardInfo = null;
		try {
			dashboardInfo = inicuPostgresUtility.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashboardInfo;
	}

	@Override
	public List<ReAdmitChildDetailView> getReadmitChildList(DashboardSearchFilterObj filterObj, String uhid) {
		// TODO Auto-generated method stub
		String query = "Select ChildInfo from ReAdmitChildDetailView as ChildInfo where uhid='"+uhid+"'";
		
		List<ReAdmitChildDetailView> childInfo = null;
		try {
			childInfo = inicuPostgresUtility.executeMappedObjectCustomizedQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childInfo;
	}
}
