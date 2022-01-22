package com.inicu.postgres.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Message.RecipientType;
import javax.persistence.Basic;

import com.inicu.device.draeger.EvitaXl;
import com.inicu.kafka.KafkaMessageReceiver;
import com.inicu.models.*;
import com.inicu.postgres.BabyRemoteView.RemoteViewServiceImpl;
import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.*;
import com.inicu.postgres.utility.*;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Multiset.Entry;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.UserServiceDAO;

import ca.uhn.hl7v2.hoh.util.repackage.Base64;

import static java.lang.Integer.parseInt;

/**
 * 
 * @author Oxyent Vikash
 *
 */

@Repository
public class PatientServiceImpl implements PatientService {

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	PrescriptionDao prescriptionDao;

	@Autowired
	private LoginService loginService;

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	PatientDao patientDao;

	@Autowired
	LogsService logService;

	@Autowired
	PrescriptionService prescriptionService;

	@Autowired
	GrowthChatService growthChartService;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	UserServiceDAO userServiceDao;

	SystematicServiceImpl sysObj = new SystematicServiceImpl();

	@Autowired
	MetabolicServiceImpl metabolicServiceImpl;

	@Autowired
	NotesService notesService;

	@Autowired
	UserPanelService userPanel;

	@Autowired
	RemoteViewServiceImpl remoteViewService;

	@Autowired
	private SettingsService settingService;
	// SimpleDateFormat dateFormatWtZ = new
	// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	PatientInfoAdmissonFormObj admissionFormObj = null;
	PatientInfoAddChildObj addChildObj = null;
	PatientInfoChildDetailsObj childDetailsObj = null;

	@Override
	public Object AdmitPatient(PatientInfoAdmissonFormObj admissionForm, PatientInfoAddChildObj addChild,
			PatientInfoChildDetailsObj childDetails, String doctorId, String operationFrom)
			throws ParseException, InicuDatabaseExeption {
		Object dashboardDeviceDetailView = null;
		String uhid = "";
		try {
			String operation = BasicConstants.INSERT;

			if (!BasicUtils.isEmpty(admissionForm.getUhid())) {

				uhid = admissionForm.getUhid().toString();
				// check if this user is available or not ...
				String queryStr = "select obj from BabyDetail obj where obj.uhid='" + uhid + "'";
				List<BabyDetail> babyDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr);
				BabyDetail babyDetails = new BabyDetail();
				if (babyDetailsList != null && babyDetailsList.size() > 0) {
					operation = BasicConstants.UPDATE;
					babyDetails = babyDetailsList.get(0);
					babyDetails = createBabyDetails(admissionForm, addChild, childDetails, babyDetails, doctorId);
				} else {
					babyDetails = createBabyDetails(admissionForm, addChild, childDetails, null, doctorId);
				}

				ParentDetail parentDetails = new ParentDetail();
				queryStr = "select obj from ParentDetail obj where obj.uhid='" + uhid + "'";
				List<ParentDetail> parentDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr);
				if (parentDetailsList != null && parentDetailsList.size() > 0) {
					parentDetails = parentDetailsList.get(0);
					parentDetails = createParentDetails(admissionForm, addChild, childDetails, parentDetails);
				} else {
					parentDetails = createParentDetails(admissionForm, addChild, childDetails, null);
				}

				MotherCurrentPregnancy motherCurrentPregnancy = new MotherCurrentPregnancy();
				queryStr = "select obj from MotherCurrentPregnancy obj where obj.uhid='" + uhid + "'";
				List<MotherCurrentPregnancy> motherDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr);
				if (motherDetailsList != null && motherDetailsList.size() > 0) {
					motherCurrentPregnancy = motherDetailsList.get(0);
					motherCurrentPregnancy = createMotherCurrentPregnancyDetails(admissionForm, addChild, childDetails,
							motherCurrentPregnancy);
				} else {
					motherCurrentPregnancy = createMotherCurrentPregnancyDetails(admissionForm, addChild, childDetails,
							null);
				}
				// saving patient visit at the time of admission...
				// date calculation..today-dob
				// java.util.Date utilAdmissionDate =
				// dateFormat.parse(admissionForm.getDateOfAdmission().toString());
				java.sql.Date sqlAdmissionDate = new java.sql.Date(new Date().getTime());
				BabyVisit babyFirstVisit = new BabyVisit();
				babyFirstVisit.setLoggeduser(doctorId);
				String queryBabyVisit = "select obj from BabyVisit obj where uhid='" + uhid + "' and visitdate='"
						+ sqlAdmissionDate + "'";
				List<BabyVisit> listBabyVisit = patientDao.getListFromMappedObjNativeQuery(queryBabyVisit);

				if (listBabyVisit != null && listBabyVisit.size() > 0) {
					babyFirstVisit = listBabyVisit.get(0);
				}
				babyFirstVisit = createBabyVisit(admissionForm, childDetails, babyFirstVisit, addChild);

				babyDetails.setLoggeduser(doctorId);

				Boolean isBabyAdmitted = patientDao.AdmitPatient(babyDetails, parentDetails, motherCurrentPregnancy,
						babyFirstVisit, addChild.getHeadToToe(), childDetails.getMaternalPastHistoryPastLsit(),
						operationFrom);

				String existingCheckQuery = "select obj from PatientDueVaccineDtls as obj where uid='" + uhid + "'";
				List<PatientDueVaccineDtls> existingCheckList = inicuDao.getListFromMappedObjQuery(existingCheckQuery);

				if (null == existingCheckList || existingCheckList.isEmpty()) {
					// populate vaccination
					String query = "select obj from VwVaccinationsPopulate as obj";
					List<VwVaccinationsPopulate> listVaccinations = inicuDao.getListFromMappedObjQuery(query);

					Iterator<VwVaccinationsPopulate> itr = listVaccinations.iterator();
					List<Object> pdvdList = new ArrayList<Object>();
					PatientDueVaccineDtls pdvd = null;
					VwVaccinationsPopulate vaccObj = null;
					Date dueDate = null;
					while (itr.hasNext()) {
						vaccObj = itr.next();

						if (vaccObj.getDueage().equalsIgnoreCase("birth")) {
							dueDate = babyDetails.getDateofbirth();
						} else { // handles only week parameter string
							String noOfWeeks = vaccObj.getDueage().substring(0, vaccObj.getDueage().indexOf("week"))
									.trim();
							dueDate = DateUtils.addWeeks(babyDetails.getDateofbirth(), Integer.valueOf(noOfWeeks));
						}

						pdvd = new PatientDueVaccineDtls();
						pdvd.setUid(uhid);
						pdvd.setVaccineinfoid(vaccObj.getVaccineinfoid());
						pdvd.setDuedate(dueDate);

						pdvdList.add(pdvd);
					}
					inicuDao.saveMultipleObject(pdvdList);
				}

				String query = "select obj from DashboardDeviceDetailView as obj where uhid='" + uhid + "'";
				List<DashboardDeviceDetailView> listPatientDeviceDetails = patientDao
						.getListFromMappedObjNativeQuery(query);
				if (listPatientDeviceDetails != null && listPatientDeviceDetails.size() > 0) {
					dashboardDeviceDetailView = listPatientDeviceDetails.get(0);
				}

				PatientInfoMasterObj patientMasterObj = new PatientInfoMasterObj();
				patientMasterObj.setAddChild(addChild);
				patientMasterObj.setAdmissionForm(admissionForm);
				patientMasterObj.setChildDetails(childDetails);
				String desc = mapper.writeValueAsString(patientMasterObj);
				if (desc.length() >= 4000)
					desc = desc.substring(0, 3999);
				logService.saveLog(desc, operation, doctorId, uhid, "Admission Form");
			} else {
				dashboardDeviceDetailView = new DashboardDeviceDetailView();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, doctorId, uhid,
					" Exception in Save Of Admission Form", BasicUtils.convertErrorStacktoString(ex));
		}
		return dashboardDeviceDetailView;
	}

	private BabyVisit createBabyVisit(PatientInfoAdmissonFormObj admissionForm, PatientInfoChildDetailsObj childDetails,
			BabyVisit babyFirstVisit, PatientInfoAddChildObj addChild) {

		try {

			// if (!admissionForm.getDateOfBirth().toString().isEmpty()){
			java.util.Date utilAdmissionDate;

			utilAdmissionDate = dateFormat.parse(admissionForm.getDateOfAdmission().toString());

			java.sql.Date sqlAdmissionDate = new java.sql.Date(utilAdmissionDate.getTime());

			java.util.Date utilBirthDate = dateFormat.parse(admissionForm.getDateOfBirth().toString());
			java.sql.Date sqlBirthDate = new java.sql.Date(utilBirthDate.getTime());

			java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
			long diffNicuDay = currentDate.getTime() - sqlAdmissionDate.getTime();
			long diffDaysNicuDay = (diffNicuDay / (24 * 60 * 60 * 1000)) + 1;
			long diff = 0;
			long diffDays = 0;
			diff = currentDate.getTime() - sqlBirthDate.getTime();
			diffDays = (diff / (24 * 60 * 60 * 1000)) + 1;

			if (addChild.getGestationByLmp() != null && addChild.getGestationByLmp().getWeeks() != null
					&& addChild.getGestationByLmp().getDays() != null) {
				babyFirstVisit.setGaAtBirth(
						addChild.getGestationByLmp().getWeeks() + "," + addChild.getGestationByLmp().getDays());
			}

			if (addChild.getActualGestation() != null && addChild.getActualGestation().getWeeks() != null
					&& addChild.getActualGestation().getDays() != null) {
				babyFirstVisit.setCorrectedGa(
						addChild.getActualGestation().getWeeks() + "," + addChild.getActualGestation().getDays());
			}

			if (!BasicUtils.isEmpty(admissionForm.getUhid()))
				babyFirstVisit.setUhid(admissionForm.getUhid().toString());
			if (!BasicUtils.isEmpty(diffDays))
				babyFirstVisit.setCurrentage(String.valueOf(diffDays));
			if (!BasicUtils.isEmpty(sqlAdmissionDate))
				babyFirstVisit.setVisitdate(sqlAdmissionDate);

			if (!BasicUtils.isEmpty(addChild.getBirthHeadCircum())) {
				babyFirstVisit.setCurrentdateheadcircum(Float.valueOf(addChild.getBirthHeadCircum().toString()));
			}
			if (!BasicUtils.isEmpty(addChild.getBirthLength())) {
				babyFirstVisit.setCurrentdateheight(Float.valueOf(addChild.getBirthLength().toString()));
			}
			if (!BasicUtils.isEmpty(addChild.getBirthWeight())) {
				babyFirstVisit.setCurrentdateweight(Float.valueOf(addChild.getBirthWeight().toString()));
			}

			if (!BasicUtils.isEmpty(addChild.getBirthWeight())) {
				if (BasicUtils.isEmpty(babyFirstVisit.getWorkingweight())) {
					babyFirstVisit.setWorkingweight(Float.valueOf(addChild.getBirthWeight().toString()));
				} else if (babyFirstVisit.getWorkingweight() < Float.valueOf(addChild.getBirthWeight().toString())) {
					babyFirstVisit.setWorkingweight(Float.valueOf(addChild.getBirthWeight().toString()));
				}
			}

			babyFirstVisit.setNicuday(diffDaysNicuDay + "");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return babyFirstVisit;
	}

	private MotherCurrentPregnancy createMotherCurrentPregnancyDetails(PatientInfoAdmissonFormObj admissionForm,
			PatientInfoAddChildObj addChild, PatientInfoChildDetailsObj childDetails,
			MotherCurrentPregnancy motherCurrentPregnancy) throws ParseException {

		MotherCurrentPregnancy motherDetails = null;
		if (motherCurrentPregnancy == null)
			motherDetails = new MotherCurrentPregnancy();
		else
			motherDetails = motherCurrentPregnancy;

		try {

			if (admissionForm.getUhid() != null && admissionForm.getUhid().toString() != null)
				motherDetails.setUhid(admissionForm.getUhid().toString());

			if (!BasicUtils.isEmpty(addChild.getInjectionVitaminK()))
				motherDetails.setVitaminkStatus(addChild.getInjectionVitaminK() + "");
			if (!BasicUtils.isEmpty(addChild.getAntenatalSteroids()))
				motherDetails.setAntenatalSteroids(addChild.getAntenatalSteroids() + "");
			if (!BasicUtils.isEmpty(addChild.getAnteNatalCheckup()))
				motherDetails.setAnc(addChild.getAnteNatalCheckup() + "");
			if (!BasicUtils.isEmpty(addChild.getFlatusTube()))
				motherDetails.setFlatusTube(addChild.getFlatusTube() + "");

			if (!BasicUtils.isEmpty(addChild.getIsFetalDistres()))
				motherDetails.setFetalDistress(Boolean.valueOf(addChild.getIsFetalDistres() + ""));

			if (!BasicUtils.isEmpty(addChild.getIsMeconiumPresent()))
				motherDetails.setFreshmeCorium(Boolean.valueOf(addChild.getIsMeconiumPresent() + ""));

			if (!BasicUtils.isEmpty(addChild.getIsShiftedToIPPR()))
				motherDetails.setIpprShifted(Boolean.valueOf(addChild.getIsShiftedToIPPR() + ""));

			if (!BasicUtils.isEmpty(addChild.getIsSurfactantGiven()))
				motherDetails.setSurfactantStatus(Boolean.valueOf(addChild.getIsSurfactantGiven() + ""));

			if (!BasicUtils.isEmpty(addChild.getSurfactantDose()))
				motherDetails.setSurfactantDose(Integer.valueOf(addChild.getSurfactantDose() + ""));
			if (!BasicUtils.isEmpty(addChild.getTimeToRegularRespiration()))
				motherDetails.setTimetoregularresps(addChild.getTimeToRegularRespiration() + "");

			if (!BasicUtils.isEmpty(addChild.getCtgNormal()))
				motherDetails.setCtgNormal(Boolean.valueOf(addChild.getCtgNormal() + ""));
			if (!BasicUtils.isEmpty(addChild.getCtgComments()))
				motherDetails.setCtgnormalComments(addChild.getCtgComments() + "");
			if (!BasicUtils.isEmpty(addChild.getResuscitationComments()))
				motherDetails.setResuscitationComments(addChild.getResuscitationComments() + "");
			if (!BasicUtils.isEmpty(childDetails.getMothersBloodGroup()))
				motherDetails.setMotherbloodgroup(childDetails.getMothersBloodGroup() + "");

			if (!BasicUtils.isEmpty(childDetails.getSmoke()))
				motherDetails.setSmokingStatus(Boolean.valueOf(childDetails.getSmoke() + ""));

			if (!BasicUtils.isEmpty(childDetails.getAlcohol()))
				motherDetails.setDrinkingStatus(Boolean.valueOf(childDetails.getAlcohol() + ""));
			if (!BasicUtils.isEmpty(childDetails.getgScore()))
				motherDetails.setGScore(childDetails.getgScore() + "");
			if (!BasicUtils.isEmpty(childDetails.getaScore()))
				motherDetails.setAScore(childDetails.getaScore() + "");
			if (!BasicUtils.isEmpty(childDetails.getpScore()))
				motherDetails.setPScore(childDetails.getpScore() + "");
			if (!BasicUtils.isEmpty(childDetails.getlScore()))
				motherDetails.setLScore(childDetails.getlScore() + "");
			if (!BasicUtils.isEmpty(childDetails.getLmp()))
				motherDetails.setLmp(CalendarUtility.dateFormatDB.parse(childDetails.getLmp() + ""));

			if (!BasicUtils.isEmpty(childDetails.getEdd()))
				motherDetails.setEdd(CalendarUtility.dateFormatDB.parse(childDetails.getEdd() + ""));
			if (!BasicUtils.isEmpty(childDetails.getDeliverymode()))
				motherDetails.setDeliverymode(childDetails.getDeliverymode() + "");
			if (!BasicUtils.isEmpty(childDetails.getDeliverymodeType()))
				motherDetails.setDeliverymodeType(childDetails.getDeliverymodeType() + "");

			if (!BasicUtils.isEmpty(childDetails.getBreechDelivery()))
				motherDetails.setBreechDelivery(Boolean.valueOf(childDetails.getBreechDelivery() + ""));

			if (!BasicUtils.isEmpty(childDetails.getAntinatalVaccinations()))
				motherDetails.setAntinatalVaccinations(Boolean.valueOf(childDetails.getAntinatalVaccinations() + ""));

			if (!BasicUtils.isEmpty(childDetails.getAntenatalSuplements()))
				motherDetails.setAntenatalSuplements(Boolean.valueOf(childDetails.getAntenatalSuplements() + ""));

			motherDetails.setMaternalIllness(childDetails.getMaternalIllness() + "");
			motherDetails.setMaternalMedications(childDetails.getMaternalMedications() + "");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return motherDetails;
	}

	private ParentDetail createParentDetails(PatientInfoAdmissonFormObj admissionForm, PatientInfoAddChildObj addChild,
			PatientInfoChildDetailsObj childDetails, ParentDetail parentDetails) {

		ParentDetail parentalDetails = null;
		if (parentDetails == null)
			parentalDetails = new ParentDetail();
		else
			parentalDetails = parentDetails;

		PersonalDetailsObj personalDetailsObj = admissionForm.getPersonalDetails();
		try {

			if (admissionForm.getUhid() != null && admissionForm.getUhid().toString() != null)
				parentalDetails.setUhid(admissionForm.getUhid().toString());

			if (personalDetailsObj.getMothersName() != null && personalDetailsObj.getMothersName().toString() != null)
				parentalDetails.setMothername(personalDetailsObj.getMothersName().toString());

			if (personalDetailsObj.getFathersName() != null && personalDetailsObj.getFathersName().toString() != null)
				parentalDetails.setFathername(personalDetailsObj.getFathersName().toString());

			if (personalDetailsObj.getMobileNumber() != null && personalDetailsObj.getMobileNumber().toString() != null)
				parentalDetails.setPrimaryphonenumber(personalDetailsObj.getMobileNumber().toString());

			if (personalDetailsObj.getLandlineNumber() != null
					&& personalDetailsObj.getLandlineNumber().toString() != null)
				parentalDetails.setSecondaryphonenumber(personalDetailsObj.getLandlineNumber().toString());

			if (personalDetailsObj.getEmailId() != null && personalDetailsObj.getEmailId().toString() != null)
				parentalDetails.setEmailid(personalDetailsObj.getEmailId().toString());

			if (personalDetailsObj.getAadharCardNumber() != null
					&& personalDetailsObj.getAadharCardNumber().toString() != null)
				parentalDetails.setAadharcard(personalDetailsObj.getAadharCardNumber().toString());

			if (personalDetailsObj.getAddress() != null && personalDetailsObj.getAddress().toString() != null)
				parentalDetails.setAddress(personalDetailsObj.getAddress().toString());

			if (personalDetailsObj.getVillage() != null && personalDetailsObj.getVillage().toString() != null)
				parentalDetails.setVillagename(personalDetailsObj.getVillage().toString());

			// setting emergency contact here..
			if (!BasicUtils.isEmpty(personalDetailsObj.getEmergencyContactName())) {
				parentalDetails.setEmergency_contactname(personalDetailsObj.getEmergencyContactName() + "");
			}

			if (!BasicUtils.isEmpty(personalDetailsObj.getEmergencyContactNo())) {
				parentalDetails.setEmergency_contactno(personalDetailsObj.getEmergencyContactNo() + "");
			}

			if (!BasicUtils.isEmpty(personalDetailsObj.getRelationship())) {
				parentalDetails.setRelationship(personalDetailsObj.getRelationship() + "");
			}

			if (!BasicUtils.isEmpty(childDetails.getMothersAge()))
				parentalDetails.setMotherage(Integer.valueOf(childDetails.getMothersAge() + ""));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return parentalDetails;
	}

	private BabyDetail createBabyDetails(PatientInfoAdmissonFormObj admissionForm, PatientInfoAddChildObj addChild,
			PatientInfoChildDetailsObj childDetails, BabyDetail babyDetail, String doctorId) throws ParseException {

		BabyDetail babyDetails = null;
		if (babyDetail == null)
			babyDetails = new BabyDetail();
		else
			babyDetails = babyDetail;
		try {

			if (admissionForm.getUhid() != null && admissionForm.getUhid().toString() != null)
				babyDetails.setUhid(admissionForm.getUhid().toString());
			babyDetails.setLoggeduser(doctorId);

			babyDetails.setActivestatus(true);

			if (addChild.getActualGestation() != null)
				if (addChild.getActualGestation().getWeeks() != null
						&& !addChild.getActualGestation().getWeeks().toString().isEmpty())
					babyDetails.setActualgestationweek(
							Integer.valueOf(addChild.getActualGestation().getWeeks().toString()));

			if (addChild.getActualGestation() != null)
				if (addChild.getActualGestation().getDays() != null
						&& !addChild.getActualGestation().getDays().toString().isEmpty())
					babyDetails.setActualgestationdays(
							Integer.valueOf(addChild.getActualGestation().getDays().toString()));

			if (!BasicUtils.isEmpty(addChild.getBp()))
				babyDetails.setAdmissionBp(addChild.getBp().toString());

			if (!BasicUtils.isEmpty(addChild.getPulserate()))
				babyDetails.setAdmissionPulserate(addChild.getPulserate() + "");

			if (!BasicUtils.isEmpty(addChild.getRr()))
				babyDetails.setAdmissionRr(addChild.getRr() + "");

			if (!BasicUtils.isEmpty(addChild.getSpo2()))
				babyDetails.setAdmissionSpo2(addChild.getSpo2() + "");

			if (!BasicUtils.isEmpty(addChild.getTemprature()))
				babyDetails.setAdmissionTemp(addChild.getTemprature() + "");

			babyDetails.setAdmissionstatus(true); // baby is not discharged
			// yet..

			if (!BasicUtils.isEmpty(addChild.getCry())) {
				String cry = addChild.getCry() + "";
				babyDetails.setCry(cry);
				if (cry.equalsIgnoreCase("Delayed")) {
					if (!BasicUtils.isEmpty(addChild.getTimeOfCry())) {
						TimeObj timeObj = addChild.getTimeOfCry();
						if (!timeObj.getMinutes().toString().isEmpty()) {
							String timeStr = timeObj.getHours().toString() + "," + timeObj.getMinutes().toString() + ","
									+ timeObj.getMeridium().toString();
							babyDetails.setTimeofcry(timeStr);
						}
					}
				}

			}

			if (!BasicUtils.isEmpty(admissionForm.getResidentDoctor()))
				babyDetails.setAdmittingdoctor(admissionForm.getResidentDoctor() + "");

			if (admissionForm.getPersonalDetails() != null
					&& admissionForm.getPersonalDetails().getMothersName() != null) {
				if ((admissionForm.getBabyName() + "").isEmpty()) {
					babyDetails.setBabyname("B/O " + admissionForm.getPersonalDetails().getMothersName().toString());
				} else {
					babyDetails.setBabyname(admissionForm.getBabyName() + "");
				}
			}

			if (!BasicUtils.isEmpty(addChild.getBirthHeadCircum()))
				babyDetails.setBirthheadcircumference(Float.valueOf(addChild.getBirthHeadCircum() + ""));

			if (!BasicUtils.isEmpty(addChild.getBirthInjuries()))
				babyDetails.setBirthinjuries(Boolean.valueOf(addChild.getBirthInjuries() + ""));

			if (!BasicUtils.isEmpty(addChild.getBirthInjuriesComments()))
				babyDetails.setBirthinjuriesComments(addChild.getBirthInjuriesComments() + "");

			if (!BasicUtils.isEmpty(addChild.getBirthLength()))
				babyDetails.setBirthlength(Float.valueOf(addChild.getBirthLength() + ""));

			if (!BasicUtils.isEmpty(addChild.getBirthMarks()))
				babyDetails.setBirthmarks(Boolean.valueOf(addChild.getBirthMarks() + ""));

			if (!BasicUtils.isEmpty(addChild.getBirthMarksComments()))
				babyDetails.setBirthmarksComments(addChild.getBirthMarksComments() + "");

			if (!BasicUtils.isEmpty(addChild.getBirthWeight()))
				babyDetails.setBirthweight(Float.valueOf(addChild.getBirthWeight() + ""));

			if (!BasicUtils.isEmpty(addChild.getBloodGroup()))
				babyDetails.setBloodgroup(addChild.getBloodGroup() + "");

			if (!BasicUtils.isEmpty(addChild.getComments()))
				babyDetails.setComments(addChild.getComments() + "");

			if (admissionForm.getDateOfBirth() != null && admissionForm.getDateOfBirth().toString() != null
					&& !admissionForm.getDateOfBirth().toString().isEmpty()) {
				java.util.Date utilDate = dateFormat.parse(admissionForm.getDateOfBirth().toString());
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				babyDetails.setDateofbirth(sqlDate);
			}

			if (admissionForm.getTimeOfBirth() != null && admissionForm.getTimeOfBirth().toString() != null) {
				TimeObj timeObj = admissionForm.getTimeOfBirth();
				if (!timeObj.getHours().toString().isEmpty()) {
					String timeStr = timeObj.getHours().toString() + "," + timeObj.getMinutes().toString() + ","
							+ timeObj.getMeridium().toString();
					babyDetails.setTimeofbirth(timeStr);
				}
			}

			if (admissionForm.getDateOfAdmission() != null && admissionForm.getDateOfAdmission().toString() != null
					&& !admissionForm.getDateOfAdmission().toString().isEmpty()) {
				java.util.Date utilDateAdmission = dateFormat.parse(admissionForm.getDateOfAdmission().toString());
				java.sql.Date sqlDateAdmission = new java.sql.Date(utilDateAdmission.getTime());
				babyDetails.setDateofadmission(sqlDateAdmission);
			}

			if (!BasicUtils.isEmpty(admissionForm.getDayOfLife()))
				babyDetails.setDayoflife(admissionForm.getDayOfLife() + "");
			if (!BasicUtils.isEmpty(admissionForm.getGender()))
				babyDetails.setGender(admissionForm.getGender() + "");

			if (addChild.getGestationByLmp() != null) {
				if (addChild.getGestationByLmp().getWeeks() != null
						&& !addChild.getGestationByLmp().getWeeks().toString().isEmpty())
					babyDetails
							.setGestationweekbylmp(Integer.valueOf(addChild.getGestationByLmp().getWeeks().toString()));
			}

			if (addChild.getGestationByLmp() != null) {
				if (addChild.getGestationByLmp().getDays() != null
						&& !addChild.getGestationByLmp().getDays().toString().isEmpty())
					babyDetails
							.setGestationdaysbylmp(Integer.valueOf(addChild.getGestationByLmp().getDays().toString()));
			}

			if (admissionForm.getIpopStatus() != null && admissionForm.getIpopStatus().toString() != null)
				babyDetails.setInoutPatientStatus(admissionForm.getIpopStatus().toString());

			if (admissionForm.getNicuLevel() != null && admissionForm.getNicuLevel().toString() != null)
				babyDetails.setNiculevelno(admissionForm.getNicuLevel().getKey().toString());

			if (admissionForm.getNicuRoomNo() != null && admissionForm.getNicuRoomNo().toString() != null)
				babyDetails.setNicuroomno(admissionForm.getNicuRoomNo().getKey().toString());

			if (admissionForm.getCriticality() != null && admissionForm.getCriticality().toString() != null
					&& !admissionForm.getCriticality().toString().equalsIgnoreCase("NA"))
				babyDetails.setCriticalitylevel(admissionForm.getCriticality().getKey().toString());

			Object newNicuBedNo = admissionForm.getNicuBedNo().getKey();
			Object oldNicuBedNo = babyDetails.getNicubedno();
			if (newNicuBedNo != null && !newNicuBedNo.toString().isEmpty()) {
				babyDetails.setNicubedno(newNicuBedNo.toString());
				if (oldNicuBedNo == null) {
					String query = "update RefBed set status='false' where bedid='" + newNicuBedNo.toString().trim()
							+ "'";
					patientDao.updateOrDeleteQuery(query);
				} else if (!oldNicuBedNo.toString().isEmpty()) {
					if (!oldNicuBedNo.toString().equalsIgnoreCase(newNicuBedNo.toString())) {
						String queryNewToInActive = "update RefBed set status='false' where bedid='"
								+ newNicuBedNo.toString().trim() + "'";
						patientDao.updateOrDeleteQuery(queryNewToInActive);
						String queryOldToActive = "update RefBed set status='true' where bedid='"
								+ oldNicuBedNo.toString().trim() + "'";
						patientDao.updateOrDeleteQuery(queryOldToActive);
					}
				} else {
					String queryNewToInActive = "update RefBed set status='false' where bedid='"
							+ newNicuBedNo.toString().trim() + "'";
					patientDao.updateOrDeleteQuery(queryNewToInActive);
				}

			} else {
				System.out.println("bed number is coming as null or empty!!");
			}

			// setting here baby previous reffered hospital details...

			if (!BasicUtils.isEmpty(admissionForm.getIpopStatus())) {

				String ipopStatus = admissionForm.getIpopStatus() + "";
				babyDetails.setInoutPatientStatus(ipopStatus);
				if (ipopStatus.equalsIgnoreCase("Out Born")) {
					if (!BasicUtils.isEmpty(admissionForm.getRefferedFrom())) {
						babyDetails.setRefferedfrom(admissionForm.getRefferedFrom() + "");
					}

					if (!BasicUtils.isEmpty(admissionForm.getCourseInRefferingHospital())) {
						babyDetails.setCourseinrefferinghospital(admissionForm.getCourseInRefferingHospital() + "");
					}

					if (!BasicUtils.isEmpty(admissionForm.getModeOfTransportation())) {
						babyDetails.setModeoftransportation(admissionForm.getModeOfTransportation() + "");
					}

					if (!BasicUtils.isEmpty(admissionForm.getTransportationAlongWith())) {
						babyDetails.setTransportationalongwith(admissionForm.getTransportationAlongWith() + "");
					}

					if (!BasicUtils.isEmpty(admissionForm.getReasonOfReference())) {
						babyDetails.setReasonofreference(admissionForm.getReasonOfReference() + "");
					}
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return babyDetails;
	}

	@Override
	public PatientInfoMasterObj getPateintInfoMasterObj(String patientId, String operationFor)
			throws InicuDatabaseExeption {

		// EntityManager em = InicuPostgresUtililty.em;
		String queryStr = "select obj from BabyDetail obj where obj.uhid='" + patientId + "'";
		List<BabyDetail> babyDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr);
		BabyDetail babyDetails = null;
		if (babyDetailsList != null && babyDetailsList.size() > 0) {
			babyDetails = babyDetailsList.get(0);
		} else {
			babyDetails = new BabyDetail();
		}

		ParentDetail parentDetails = null;
		String queryStr2 = "select obj from ParentDetail obj where obj.uhid='" + patientId + "'";
		List<ParentDetail> parentDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr2);
		if (parentDetailsList != null && parentDetailsList.size() > 0) {
			parentDetails = parentDetailsList.get(0);
		} else {
			parentDetails = new ParentDetail();
		}

		String queryBabyVisitBirth = "select obj from BabyVisit obj where uhid='" + patientId + "' and visitdate='"
				+ babyDetails.getDateofbirth() + "'";
		List<BabyVisit> listBabyVisitBirth = patientDao.getListFromMappedObjNativeQuery(queryBabyVisitBirth);

		String queryBabyVisitAdmission = "select obj from BabyVisit obj where uhid='" + patientId + "' and visitdate='"
				+ babyDetails.getDateofbirth() + "'";
		List<BabyVisit> listBabyVisitAdmission = patientDao.getListFromMappedObjNativeQuery(queryBabyVisitAdmission);

		String queryBabyVisitDischarge = "select obj from BabyVisit obj where uhid='" + patientId + "' and visitdate='"
				+ babyDetails.getDateofbirth() + "'";
		List<BabyVisit> listBabyVisitDischarge = patientDao.getListFromMappedObjNativeQuery(queryBabyVisitDischarge);

		return null;
	}

	private PatientInfoAddChildObj getPatientAddChildObj(BabyDetail babyDetails, ParentDetail parentDetails,
			MotherCurrentPregnancy motherDetails) throws Exception {
		PatientInfoAddChildObj patientInfoAddChildObj = new PatientInfoAddChildObj();
		try {

			if (babyDetails != null) {

				GestationObj gestationActualObj = new GestationObj();
				gestationActualObj.setWeeks(babyDetails.getActualgestationweek());
				gestationActualObj.setDays(babyDetails.getActualgestationdays());
				patientInfoAddChildObj.setActualGestation(gestationActualObj);

				GestationObj gestationByUsgObj = new GestationObj();
				gestationByUsgObj.setWeeks(babyDetails.getGestationweekbylmp());
				gestationByUsgObj.setDays(babyDetails.getGestationdaysbylmp());
				patientInfoAddChildObj.setGestationByLmp(gestationByUsgObj);

				patientInfoAddChildObj.setBloodGroup(babyDetails.getBloodgroup());

				patientInfoAddChildObj.setCry(babyDetails.getCry());

				if (!BasicUtils.isEmpty(babyDetails.getCry())) {
					if (babyDetails.getCry().equalsIgnoreCase("Delayed")) {
						if (babyDetails.getTimeofcry() != null) {

							String timeStr = babyDetails.getTimeofcry().toString();
							String[] timeArr = timeStr.split(",");
							TimeObj timeObj = new TimeObj();
							int length = timeArr.length;
							if (timeArr.length >= 1 && timeArr[0] != null)
								timeObj.setHours(timeArr[0]);

							if (timeArr.length >= 2) {
								if (timeArr[1] != null)
									timeObj.setMinutes(timeArr[1]);
							}
							if (timeArr.length >= 3) {
								if (timeArr[2] != null)
									timeObj.setMeridium(timeArr[2]);
							}
							patientInfoAddChildObj.setTimeOfCry(timeObj);
						}
					}
				}

				patientInfoAddChildObj.setBirthWeight(babyDetails.getBirthweight());
				patientInfoAddChildObj.setBirthLength(babyDetails.getBirthlength());
				patientInfoAddChildObj.setBirthHeadCircum(babyDetails.getBirthheadcircumference());

				patientInfoAddChildObj.setSpo2(babyDetails.getAdmissionSpo2());
				patientInfoAddChildObj.setRr(babyDetails.getAdmissionRr());
				patientInfoAddChildObj.setBp(babyDetails.getAdmissionBp());
				patientInfoAddChildObj.setTemprature(babyDetails.getAdmissionTemp());
				patientInfoAddChildObj.setPulserate(babyDetails.getAdmissionPulserate());

			}

			String queryBallardScore = "select obj from ScoreBallard as obj where uhid = '" + babyDetails.getUhid()
					+ "'";
			List<ScoreBallard> listBallardScore = inicuDao.getListFromMappedObjQuery(queryBallardScore);
			if (!BasicUtils.isEmpty(listBallardScore)) {
				// patientInfoAddChildObj.setBallardScorePastList(listBallardScore);
				patientInfoAddChildObj.setBallardScoreCurrent(listBallardScore.get(0));
			}

			if (motherDetails != null) {

				patientInfoAddChildObj.setInjectionVitaminK(motherDetails.getVitaminkStatus());
				patientInfoAddChildObj.setAntenatalSteroids(motherDetails.getAntenatalSteroids());
				patientInfoAddChildObj.setAnteNatalCheckup(motherDetails.getAnc());
				patientInfoAddChildObj.setFlatusTube(motherDetails.getFlatusTube());

			}
			String queryHeadToToe = "select obj from HeadtotoeExamination as obj where uhid='" + babyDetails.getUhid()
					+ "'";
			List<HeadtotoeExamination> listHeadToToe = inicuDao.getListFromMappedObjQuery(queryHeadToToe);
			if (!BasicUtils.isEmpty(listHeadToToe)) {
				patientInfoAddChildObj.setHeadToToe(listHeadToToe.get(0));
			} else {
				patientInfoAddChildObj.setHeadToToe(new HeadtotoeExamination());
			}

			patientInfoAddChildObj.setBirthMarks(babyDetails.getBirthmarks());
			patientInfoAddChildObj.setBirthMarksComments(babyDetails.getBirthmarksComments());
			patientInfoAddChildObj.setBirthInjuries(babyDetails.getBirthinjuries());
			patientInfoAddChildObj.setBirthInjuriesComments(babyDetails.getBirthinjuriesComments());
			patientInfoAddChildObj.setComments(babyDetails.getComments());

			patientInfoAddChildObj.setIsFetalDistres(motherDetails.getFetalDistress());
			patientInfoAddChildObj.setIsMeconiumPresent(motherDetails.getFreshmeCorium());
			patientInfoAddChildObj.setIsShiftedToIPPR(motherDetails.getIpprShifted());
			patientInfoAddChildObj.setIsSurfactantGiven(motherDetails.getSurfactantStatus());
			patientInfoAddChildObj.setSurfactantDose(motherDetails.getSurfactantDose());
			patientInfoAddChildObj.setTimeToRegularRespiration(motherDetails.getTimetoregularresps());

			String queryApgarScore = "select obj from ScoreApgar as obj where uhid='" + babyDetails.getUhid() + "'";
			List<ScoreApgar> listApgarScore = inicuDao.getListFromMappedObjQuery(queryApgarScore);
			if (!BasicUtils.isEmpty(listApgarScore)) {
				ApgarScoreObj apgarScoreObj = new ApgarScoreObj();
				for (ScoreApgar apgar : listApgarScore) {
					if (!BasicUtils.isEmpty(apgar.getApgarTime())) {

						if (apgar.getApgarTime() == 1) {
							apgarScoreObj.setOneMin(apgar);
						} else if (apgar.getApgarTime() == 5) {
							apgarScoreObj.setFiveMin(apgar);
						} else if (apgar.getApgarTime() == 10) {
							apgarScoreObj.setTenMin(apgar);
						}
					}
				}
				patientInfoAddChildObj.setApgarScore(apgarScoreObj);
			}

			patientInfoAddChildObj.setCtgNormal(motherDetails.getCtgNormal());
			patientInfoAddChildObj.setCtgComments(motherDetails.getCtgnormalComments());
			patientInfoAddChildObj.setResuscitationComments(motherDetails.getResuscitationComments());

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return patientInfoAddChildObj;
	}

	private PatientInfoChildDetailsObj getPatientChildDetailsObj(BabyDetail babyDetails, ParentDetail parentDetails,
			MotherCurrentPregnancy motherCurrentPreg) {
		PatientInfoChildDetailsObj patientChildDetailsObj = new PatientInfoChildDetailsObj();
		try {

			patientChildDetailsObj.setMothersAge(parentDetails.getMotherage());
			patientChildDetailsObj.setMothersBloodGroup(motherCurrentPreg.getMotherbloodgroup());
			patientChildDetailsObj.setSmoke(motherCurrentPreg.getSmokingStatus());
			patientChildDetailsObj.setAlcohol(motherCurrentPreg.getDrinkingStatus());
			patientChildDetailsObj.setgScore(motherCurrentPreg.getGScore());
			patientChildDetailsObj.setaScore(motherCurrentPreg.getAScore());
			patientChildDetailsObj.setpScore(motherCurrentPreg.getPScore());
			patientChildDetailsObj.setlScore(motherCurrentPreg.getLScore());
			patientChildDetailsObj.setLmp(motherCurrentPreg.getLmp());
			patientChildDetailsObj.setEdd(motherCurrentPreg.getEdd());
			patientChildDetailsObj.setDeliverymode(motherCurrentPreg.getDeliverymode());
			patientChildDetailsObj.setDeliverymodeType(motherCurrentPreg.getDeliverymodeType());
			patientChildDetailsObj.setBreechDelivery(motherCurrentPreg.getBreechDelivery());

			String queryMaternalPastHistory = "select obj from MaternalPasthistory as obj where uhid='"
					+ babyDetails.getUhid() + "'";
			List<MaternalPasthistory> listMaternalPastHistory = inicuDao
					.getListFromMappedObjQuery(queryMaternalPastHistory);
			if (!BasicUtils.isEmpty(listMaternalPastHistory)) {
				patientChildDetailsObj.setMaternalPastHistoryPastLsit(listMaternalPastHistory);
			}

			patientChildDetailsObj.setAntinatalVaccinations(motherCurrentPreg.getAntinatalVaccinations());
			patientChildDetailsObj.setAntenatalSuplements(motherCurrentPreg.getAntenatalSuplements());
			patientChildDetailsObj.setMaternalIllness(motherCurrentPreg.getMaternalIllness());
			patientChildDetailsObj.setMaternalMedications(motherCurrentPreg.getMaternalMedications());

		} catch (Exception ex) {
			throw ex;
		}
		return patientChildDetailsObj;
	}

	private PatientInfoAdmissonFormObj getPatientAdmissionFormObj(BabyDetail babyDetails, ParentDetail parentDetails,
			MotherCurrentPregnancy motherDetails) throws Exception {
		PatientInfoAdmissonFormObj patientAdmissionFormObj = new PatientInfoAdmissonFormObj();
		try {

			patientAdmissionFormObj.setUhid(babyDetails.getUhid());

			patientAdmissionFormObj.setBabyName(babyDetails.getBabyname());

			patientAdmissionFormObj.setGender(babyDetails.getGender());

			if (!BasicUtils.isEmpty(babyDetails.getInoutPatientStatus())) {

				String ipopStatus = babyDetails.getInoutPatientStatus();

				patientAdmissionFormObj.setIpopStatus(babyDetails.getInoutPatientStatus());

				if (ipopStatus.equalsIgnoreCase("Outborn")) {

					// setting reffered from entries here...
					if (!BasicUtils.isEmpty(babyDetails.getRefferedfrom())) {
						patientAdmissionFormObj.setRefferedFrom(babyDetails.getRefferedfrom());
					}

					if (!BasicUtils.isEmpty(babyDetails.getCourseinrefferinghospital())) {
						patientAdmissionFormObj
								.setCourseInRefferingHospital(babyDetails.getCourseinrefferinghospital());
					}

					if (!BasicUtils.isEmpty(babyDetails.getModeoftransportation())) {
						patientAdmissionFormObj.setModeOfTransportation(babyDetails.getModeoftransportation());
					}

					if (!BasicUtils.isEmpty(babyDetails.getTransportationalongwith())) {
						patientAdmissionFormObj.setTransportationAlongWith(babyDetails.getTransportationalongwith());
					}

					if (!BasicUtils.isEmpty(babyDetails.getReasonofreference())) {
						patientAdmissionFormObj.setReasonOfReference(babyDetails.getReasonofreference());
					}
				}
			}

			if (babyDetails.getNicuroomno() != null) {
				KeyValueObj roomObj = new KeyValueObj();
				RefRoom refRoom = getRefRoomInfo(babyDetails.getNicuroomno(), null);
				if (refRoom != null) {
					roomObj.setKey(refRoom.getRoomid());
					roomObj.setValue(refRoom.getRoomname());
				}
				patientAdmissionFormObj.setNicuRoomNo(roomObj);
			}

			if (babyDetails.getNicubedno() != null && !babyDetails.getNicubedno().toString().isEmpty()) {
				KeyValueObj bedObj = new KeyValueObj();
				RefBed refBed = getRefBedInfo(babyDetails.getNicubedno(), null);
				if (refBed != null) {
					bedObj.setKey(refBed.getBedid());
					bedObj.setValue(refBed.getBedname());
					patientAdmissionFormObj.setNicuBedNo(bedObj);
				}

			}

			if (babyDetails.getNiculevelno() != null) {
				KeyValueObj levelObj = new KeyValueObj();
				RefLevel refLevel = getRefLevelInfo(babyDetails.getNiculevelno(), null);
				if (refLevel != null) {
					levelObj.setKey(refLevel.getLevelid());
					levelObj.setValue(refLevel.getLevelname());
				}
				patientAdmissionFormObj.setNicuLevel(levelObj);
			}

			if (babyDetails.getCriticalitylevel() != null) {
				KeyValueObj criticalityObj = new KeyValueObj();
				String criticalityId = babyDetails.getCriticalitylevel();
				RefCriticallevel refCriticality = getCriticalityInfo(criticalityId, null);
				if (refCriticality != null) {
					criticalityObj.setKey(refCriticality.getCrlevelid());
					criticalityObj.setValue(refCriticality.getLevelname());
					patientAdmissionFormObj.setCriticality(criticalityObj);
				}
			}

			String timeStr = "";
			String[] timeArr = {};
			TimeObj timeObj = new TimeObj();

			Date dateDOB = null;
			if (babyDetails.getDateofbirth() != null && !babyDetails.getDateofbirth().toString().isEmpty()) {
				dateDOB = dateFormatDB.parse(babyDetails.getDateofbirth().toString());
				patientAdmissionFormObj.setDateOfBirth(dateFormat.format(dateDOB));
			}

			if (babyDetails.getTimeofbirth() != null) {
				timeStr = babyDetails.getTimeofbirth().toString();
				timeArr = timeStr.split(",");
				timeObj = new TimeObj();
				if (timeArr.length > 2) {
					if (timeArr[0] != null)
						timeObj.setHours(timeArr[0]);
					if (timeArr[1] != null)
						timeObj.setMinutes(timeArr[1]);
					if (timeArr[2] != null)
						timeObj.setMeridium(timeArr[2]);
				}

				patientAdmissionFormObj.setTimeOfBirth(timeObj);
			}

			Date dateDOA = null;
			if (babyDetails.getDateofadmission() != null && !babyDetails.getDateofadmission().toString().isEmpty()) {
				dateDOA = dateFormatDB.parse(babyDetails.getDateofadmission().toString());
				patientAdmissionFormObj.setDateOfAdmission(dateFormat.format(dateDOA));
			}

			long diff = new Date().getTime() - dateDOB.getTime();
			long ageDays = diff / (24 * 60 * 60 * 1000);// days..
			// increase of 1..
			ageDays = ageDays + 1;
			long ageMonth = ageDays / (30);

			if (ageMonth > 0)
				patientAdmissionFormObj.setDayOfLife(ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days");
			else
				patientAdmissionFormObj.setDayOfLife(ageDays + "days");

			patientAdmissionFormObj.setResidentDoctor(babyDetails.getAdmittingdoctor());

			if (parentDetails != null) {
				PersonalDetailsObj personlaDetailsObj = new PersonalDetailsObj();

				personlaDetailsObj.setMothersName(parentDetails.getMothername());
				personlaDetailsObj.setFathersName(parentDetails.getFathername());
				personlaDetailsObj.setMobileNumber(parentDetails.getPrimaryphonenumber());
				personlaDetailsObj.setLandlineNumber(parentDetails.getSecondaryphonenumber());
				personlaDetailsObj.setEmailId(parentDetails.getEmailid());
				personlaDetailsObj.setAddress(parentDetails.getAddress());
				personlaDetailsObj.setAadharCardNumber(parentDetails.getAadharcard());
				personlaDetailsObj.setVillage(parentDetails.getVillagename());

				// setting emergency here...
				personlaDetailsObj.setEmergencyContactName(parentDetails.getEmergency_contactname());
				personlaDetailsObj.setEmergencyContactNo(parentDetails.getEmergency_contactno());
				personlaDetailsObj.setRelationship(parentDetails.getRelationship());
				patientAdmissionFormObj.setPersonalDetails(personlaDetailsObj);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return patientAdmissionFormObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AdmissionFormMasterDropDownsObj getAdmissionFormMasterDropDowns(String branchName) {
		// ref bed ..
		AdmissionFormMasterDropDownsObj dropDowns = new AdmissionFormMasterDropDownsObj();
		dropDowns.setNicuRooms(getActiveRooms(branchName));
		dropDowns.setNicuInactiveBeds(getInactiveRooms(branchName));

		dropDowns.setNicuLevels(
				getRefObj("select obj.levelid,obj.levelname from " + BasicConstants.SCHEMA_NAME + ".ref_level as obj"));
		dropDowns.setSignificantMaterials(getRefObj(
				"select obj.smid,obj.material from " + BasicConstants.SCHEMA_NAME + ".ref_significantmaterial as obj"));
		dropDowns.setHistorys(
				getRefObj("select obj.hisid,obj.history from " + BasicConstants.SCHEMA_NAME + ".ref_history as obj"));
		dropDowns.setHeadToToes(getRefObj(
				"select obj.htid,obj.headtotoe from " + BasicConstants.SCHEMA_NAME + ".ref_headtotoe as obj"));
		dropDowns.setExaminations(getRefObj(
				"select obj.exid,obj.examination from " + BasicConstants.SCHEMA_NAME + ".ref_examination as obj"));
		dropDowns.setResidentDoctors(getResidantDoctors(branchName));
		dropDowns.setGestation(getRefObj("select obj.gesid,obj.gestation from " + BasicConstants.SCHEMA_NAME
				+ ".ref_gestation as obj order by obj.gesid"));
		dropDowns.setHours(getTime(BasicConstants.HOURS));
		dropDowns.setMinutes(getTime(BasicConstants.MINUTES));
		dropDowns.setSeconds(getTime(BasicConstants.SECONDS));

		dropDowns.setStateList(getStateList());
		dropDowns.setReasonOfAdmission(getReasonOfAdmissionList());
		return dropDowns;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private HashMap<String, HashMap<String, List<MasterAddress>>> getAddressMap() {

		LinkedHashMap<String, HashMap<String, List<MasterAddress>>> addressMap = new LinkedHashMap<String, HashMap<String, List<MasterAddress>>>();
		LinkedHashMap<String, List<MasterAddress>> distMap = null;
		List<MasterAddress> cityList = null;
		Long timeDBStart = System.currentTimeMillis();
		List<MasterAddress> dbList = inicuDao.getListFromMappedObjQuery(
				"select obj from MasterAddress obj order by obj.state, obj.district, obj.city");

		System.out.println("time in address DB: " + (System.currentTimeMillis() - timeDBStart));
		Long timeStart = System.currentTimeMillis();
		if (!BasicUtils.isEmpty(dbList)) {
			String state = "";
			String district = "";
			Iterator<MasterAddress> itr = dbList.iterator();
			while (itr.hasNext()) {
				MasterAddress obj = itr.next();
				if (state.isEmpty() || !obj.getState().equalsIgnoreCase(state)) {
					state = obj.getState();
					district = obj.getDistrict();
					distMap = new LinkedHashMap<String, List<MasterAddress>>();
					cityList = new ArrayList<MasterAddress>();
					cityList.add(obj);
					distMap.put(district, cityList);
					addressMap.put(state, distMap);
				} else if (district.isEmpty() || !obj.getDistrict().equalsIgnoreCase(district)) {
					district = obj.getDistrict();
					cityList = new ArrayList<MasterAddress>();
					cityList.add(obj);
					distMap.put(district, cityList);
				} else {
					cityList.add(obj);
				}
			}
		}
		System.out.println("time in address map: " + (System.currentTimeMillis() - timeStart));
		return addressMap;
	}

	@SuppressWarnings("unchecked")
	private List<ReasonOfAdmissionEventPOJO> getReasonOfAdmissionList() {

		List<ReasonOfAdmissionEventPOJO> returnList = new ArrayList<ReasonOfAdmissionEventPOJO>();
		List<ReasonOfAdmissionEventCausePOJO> causeList = null;
		ReasonOfAdmissionEventPOJO eventObj = null;
		String currentEvent = null;

		List<VwAssessmentcauseFinal> dbList = inicuDao
				.getListFromMappedObjQuery("select obj from VwAssessmentcauseFinal obj order by event");

		if (!BasicUtils.isEmpty(dbList)) {
			Iterator<VwAssessmentcauseFinal> itr = dbList.iterator();
			while (itr.hasNext()) {
				VwAssessmentcauseFinal obj = itr.next();
				if (currentEvent == null || !currentEvent.equalsIgnoreCase(obj.getEvent())) {
					currentEvent = obj.getEvent();
					eventObj = new ReasonOfAdmissionEventPOJO();
					eventObj.setEventName(currentEvent);

					causeList = new ArrayList<ReasonOfAdmissionEventCausePOJO>();
					causeList.add(new ReasonOfAdmissionEventCausePOJO(obj.getCauses()));

					eventObj.setCauseList(causeList);
					returnList.add(eventObj);
				} else {
					eventObj.getCauseList().add(new ReasonOfAdmissionEventCausePOJO(obj.getCauses()));
				}
			}
		}
		return returnList;
	}

	private List<String> getTime(String string) {
		List<String> hours = new ArrayList<String>();
		List<String> minutes = new ArrayList<String>();
		List<String> seconds = new ArrayList<String>();
		if (string.equalsIgnoreCase(BasicConstants.HOURS)) {
			for (int i = 0; i <= 12; i++) {
				if (i < 10) {
					hours.add("0" + i);
				} else {
					hours.add(String.valueOf(i));
				}
			}
			return hours;
		} else if (string.equalsIgnoreCase(BasicConstants.MINUTES)) {
			for (int i = 0; i <= 59; i++) {
				if (i < 10) {
					minutes.add("0" + i);
				} else {
					minutes.add(String.valueOf(i));
				}
			}
			return minutes;
		} else if (string.equalsIgnoreCase(BasicConstants.SECONDS)) {
			for (int i = 0; i <= 59; i++) {
				if (i < 10) {
					seconds.add("0" + i);
				} else {
					seconds.add(String.valueOf(i));
				}
			}
			return seconds;
		}
		return null;
	}

	// ************************************Master Tables
	// Access***********************************
	public List getActiveRooms(String branchName) {
		List<NicuRoomObj> nicuRooms = new ArrayList<NicuRoomObj>();
		List<KeyValueObj> beds = new ArrayList<KeyValueObj>();
		try {
			String query = "select obj from RefRoom as obj where isactive='true' and branchname = '" + branchName + "'";
			List<RefRoom> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj room = null;
			if (refList != null) {
				java.util.Iterator<RefRoom> iterator = refList.iterator();
				while (iterator.hasNext()) {
					NicuRoomObj nicuRoom = new NicuRoomObj();
					room = new KeyValueObj();
					RefRoom obj = iterator.next();
					room.setKey(obj.getRoomid());
					room.setValue(obj.getRoomname());
					nicuRoom.setRoom(room);
					nicuRoom.setBed(getActiveBeds(room.getKey().toString()));
					nicuRooms.add(nicuRoom);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nicuRooms;
	}

	public List getInactiveRooms(String branchName) {
		List<NicuRoomObj> nicuRooms = new ArrayList<NicuRoomObj>();
		List<KeyValueObj> beds = new ArrayList<KeyValueObj>();
		try {
			String query = "select obj from RefRoom as obj where isactive='true' and branchname = '" + branchName + "'";
			List<RefRoom> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj room = null;
			if (refList != null) {
				java.util.Iterator<RefRoom> iterator = refList.iterator();
				while (iterator.hasNext()) {
					NicuRoomObj nicuRoom = new NicuRoomObj();
					room = new KeyValueObj();
					RefRoom obj = iterator.next();
					room.setKey(obj.getRoomid());
					room.setValue(obj.getRoomname());
					nicuRoom.setRoom(room);
					nicuRoom.setBed(getInactiveBeds(room.getKey().toString()));
					nicuRooms.add(nicuRoom);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return nicuRooms;
	}

	public List getInactiveBeds(String roomId) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			String query = "";

			query = "select obj from RefBed as obj where status='false' and isactive='true' and roomid='" + roomId
					+ "'";

			List<RefBed> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null) {
				java.util.Iterator<RefBed> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					RefBed obj = iterator.next();
					keyValue.setKey(obj.getBedid());
					keyValue.setValue(obj.getBedname());
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	public List getActiveBeds(String roomId) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			String query = "";

			query = "select obj from RefBed as obj where status='true' and isactive='true' and roomid='" + roomId + "'";

			List<RefBed> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null) {
				java.util.Iterator<RefBed> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					RefBed obj = iterator.next();
					keyValue.setKey(obj.getBedid());
					keyValue.setValue(obj.getBedname());
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	public List getResidantDoctors(String branchName) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {

			String query = "select obj from User as obj where username not in ('test') and active=1 and branchname = '"
					+ branchName + "'";/* designation */

			List<User> refList = patientDao.getListFromMappedObjNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null) {
				java.util.Iterator<User> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					User obj = iterator.next();
					keyValue.setKey(obj.getUserName());
					keyValue.setValue(obj.getFirstName() + " " + obj.getLastName());

					String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + obj.getUserName()
							+ "'";/* designation */
					List result = userServiceDao.executeQuery(userObjQuery);
					if (result != null && result.size() > 0) {
						UserRolesTable userRoles = (UserRolesTable) result.get(0);
						if (userRoles != null) {
							String roleId = userRoles.getRoleId();
							if (roleId.equals("2") || roleId.equals("3")) {
								refKeyValueList.add(keyValue);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	@Override
	public RefCriticallevel getCriticalityInfo(Object crLevelId, Object criticalityDesc) {
		String query = "";
		if (crLevelId != null) {// based on crLevelId we will get its desc and
			// all;
			query = "select critical from RefCriticallevel as critical where crlevelid='" + crLevelId.toString().trim()
					+ "'";
		} else {// based on criticality description we will get its id and all;
			query = "select critical from RefCriticallevel as critical where levelname='"
					+ criticalityDesc.toString().trim().toUpperCase() + "'";
		}
		List<RefCriticallevel> refCriticalityList = null;
		try {
			refCriticalityList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (refCriticalityList != null && refCriticalityList.size() > 0) {
			return refCriticalityList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefExamination getExaminationInfo(Object examId, Object examDesc) {
		String query = "";
		if (examId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefExamination as obj where exid='" + examId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefExamination as obj where examination='" + examDesc.toString().trim() + "'";
		}
		List<RefExamination> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefBed getRefBedInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefBed as obj where bedid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefBed as obj where bedname='" + objDesc.toString().trim().toUpperCase() + "'";
		}
		List<RefBed> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefHeadtotoe getRefHeadToToeInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefHeadtotoe as obj where htid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefHeadtotoe as obj where headtotoe='" + objDesc.toString().trim() + "'";
		}
		List<RefHeadtotoe> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefHistory getRefHistoryInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefHistory as obj where hisid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefHistory as obj where history='" + objDesc.toString().trim() + "'";
		}
		List<RefHistory> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefLevel getRefLevelInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefLevel as obj where levelid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefLevel as obj where levelname='" + objDesc.toString().trim() + "'";
		}
		List<RefLevel> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefRoom getRefRoomInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefRoom as obj where roomid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefRoom as obj where roomname='" + objDesc.toString().trim() + "'";
		}
		List<RefRoom> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public RefSignificantmaterial getRefSignificantMaterialInfo(Object objId, Object objDesc) {
		String query = "";
		if (objId != null) {// based on crLevelId we will get its desc and all;
			query = "select obj from RefSignificantmaterial as obj where smid='" + objId.toString().trim() + "'";
		} else {// based on criticality description we will get its id and all;
			query = "select obj from RefSignificantmaterial as obj where material='" + objDesc.toString().trim() + "'";
		}
		List<RefSignificantmaterial> objList = null;
		try {
			objList = patientDao.getListFromMappedObjNativeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		} else {
			return null;
		}
	}
	// *********************************************************************************************

	@Override
	public Object addDoctorNotes(DoctorNotesObj doctorNotesInputObj, String loggedInUserId)
			throws InicuDatabaseExeption {
		// filling doctor_notes..

		VwDoctornotesFinal doctorNotesObj = doctorNotesInputObj.getNotes();
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		DoctorNote doctorNotes = new DoctorNote();

		doctorNotes.setUhid(doctorNotesObj.getUhid().toString());

		if (doctorNotesObj.getDiagnosis() != null)
			doctorNotes.setDiagnosis(doctorNotesObj.getDiagnosis().toString());

		if (doctorNotesObj.getIssues() != null)
			doctorNotes.setIssues(doctorNotesObj.getIssues().toString());

		if (doctorNotesObj.getPlan() != null)
			doctorNotes.setPlan(doctorNotesObj.getPlan().toString());

		if (doctorNotesObj.getDoctornotes() != null)
			doctorNotes.setDoctornotes(doctorNotesObj.getDoctornotes().toString());

		if (doctorNotesObj.getFollowupnotes() != null)
			doctorNotes.setFollowupnotes(doctorNotesObj.getFollowupnotes().toString());

		// CalendarUtility.setDefaultTimeZone(CalendarUtility.SERVER_CRUD_OPERATION);
		Timestamp timeStamp = new Timestamp(new Date().getTime());
		doctorNotes.setNoteentrytime(timeStamp);

		if (loggedInUserId != null)
			doctorNotes.setLoggedUser(loggedInUserId);

		try {
			// CalendarUtility.setDefaultTimeZone(CalendarUtility.SERVER_CRUD_OPERATION);
			patientDao.saveObject(doctorNotes);
			logService.saveLog(BasicUtils.getObjAsJson(doctorNotes), BasicConstants.INSERT, loggedInUserId,
					doctorNotesObj.getUhid().toString(), BasicConstants.PAGE_DOCTOR_NOTES);
		} catch (Exception ex) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("Doctor Notes Could not be Saved!!");
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUserId,
					doctorNotesObj.getUhid().toString(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
			return response;
		}
		// CalendarUtility.setDefaultTimeZone(CalendarUtility.CLIENT_CRUD_OPERATION);
		response.setMessage("Doctor Notes Added Successfully!!");
		return response;
	}

	@Override
	public Object saveDoctorNotes(DashboardJSON doctorNotesInputObj, String loggedInUserId)
			throws InicuDatabaseExeption {
		// filling doctor_notes.

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		DoctorNote doctorNotes = new DoctorNote();

		doctorNotes.setUhid(doctorNotesInputObj.getUhid().toString());

		if (doctorNotesInputObj.getNotes() != null)
			doctorNotes.setDoctornotes(doctorNotesInputObj.getNotes().toString());

		// CalendarUtility.setDefaultTimeZone(CalendarUtility.SERVER_CRUD_OPERATION);
		Timestamp timeStamp = new Timestamp(new Date().getTime());
		doctorNotes.setNoteentrytime(timeStamp);

		if (loggedInUserId != null)
			doctorNotes.setLoggedUser(loggedInUserId);

		response.setReturnedObject(doctorNotesInputObj);

		try {
			// CalendarUtility.setDefaultTimeZone(CalendarUtility.SERVER_CRUD_OPERATION);
			patientDao.saveObject(doctorNotes);
			logService.saveLog(BasicUtils.getObjAsJson(doctorNotes), BasicConstants.INSERT, loggedInUserId,
					doctorNotesInputObj.getUhid().toString(), BasicConstants.PAGE_DOCTOR_NOTES);
		} catch (Exception ex) {
			response.setType(BasicConstants.MESSAGE_FAILURE);
			response.setMessage("Doctor Notes Could not be Saved!!");
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUserId,
					doctorNotesInputObj.getUhid().toString(), "SAVE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
			return response;
		}
		// CalendarUtility.setDefaultTimeZone(CalendarUtility.CLIENT_CRUD_OPERATION);
		response.setMessage("Doctor Notes Added Successfully!!");
		return response;
	}

	@Override
	public Object getDoctorNotes(String uhid, String dob, String doa, String sex, String doctorId, String entryDate,
			String noteEntryTime) throws InicuDatabaseExeption {
		DoctorNotesObj doctorNotes = new DoctorNotesObj();
		// CalendarUtility.setDefaultTimeZone(CalendarUtility.CLIENT_CRUD_OPERATION);
		Date presentDate = null;
		java.sql.Date sqlPresentDate = null;
		java.util.Date dobDate = null;
		java.util.Date doaDate = null;

		VwDoctornotesFinal vwDoctorNote = new VwDoctornotesFinal();
		try {

			if (dob.length() >= 10 && doa.length() >= 10) {
				dobDate = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
						.parse(dob.substring(0, 10));
				doaDate = CalendarUtility.getDateformatdb(CalendarUtility.CLIENT_CRUD_OPERATION)
						.parse(doa.substring(0, 10));
				presentDate = CalendarUtility.getDateformatdb(CalendarUtility.SERVER_CRUD_OPERATION)
						.parse(entryDate.substring(0, 10));
				sqlPresentDate = new java.sql.Date(presentDate.getTime());

				String query = "select obj from VwDoctornotesFinal obj where uhid='" + uhid + "' and creationdate='"
						+ sqlPresentDate + "' order by creationtime desc";
				List<VwDoctornotesFinal> docotorNotesList = patientDao.getListFromMappedObjNativeQuery(query);

				// filling notes time drop down here..
				List<String> prevNotesTimeList = new ArrayList<String>();
				Iterator<VwDoctornotesFinal> iterator = docotorNotesList.iterator();
				while (iterator.hasNext()) {
					VwDoctornotesFinal notes = iterator.next();

					String entryTime = CalendarUtility.getDateformatampm(CalendarUtility.CLIENT_CRUD_OPERATION)
							.format(notes.getNoteentrytime());
					prevNotesTimeList.add(entryTime);
					if (noteEntryTime != null && noteEntryTime.equalsIgnoreCase(entryTime)) {
						if (noteEntryTime.equalsIgnoreCase("default")) {
							System.out.println("Default doctor notes returned.!!");
						} else {
							vwDoctorNote = notes;
						}
					}

				}
				doctorNotes.setNoteEntryTimes(prevNotesTimeList);

				if (vwDoctorNote.getDoctornoteid() == 0 && docotorNotesList.size() > 0) {
					if (noteEntryTime.equalsIgnoreCase("default")) {
						System.out.println("Default doctor notes returned.!!");
						String diagnosis = docotorNotesList.get(0).getDiagnosis();
						vwDoctorNote.setDiagnosis(diagnosis);
					} else {
						vwDoctorNote = docotorNotesList.get(0);
					}
				}

				if (noteEntryTime.equalsIgnoreCase("default")) {
					System.out.println("Default doctor notes returned.!!");
					if (BasicUtils.isEmpty(vwDoctorNote.getDiagnosis())
							|| vwDoctorNote.getDiagnosis().toString().equalsIgnoreCase("")) {
						String lastDiagnosisQuery = "select obj from VwDoctornotesFinal obj where uhid='" + uhid
								+ "' order by creationtime desc";
						List<VwDoctornotesFinal> lastDocotorNotesList = patientDao
								.getListFromMappedObjNativeQuery(lastDiagnosisQuery);
						if (lastDocotorNotesList.size() != 0) {
							Iterator<VwDoctornotesFinal> lastNotesiterator = lastDocotorNotesList.iterator();
							boolean diagnosisBlank = true;
							while (lastNotesiterator.hasNext() && diagnosisBlank) {
								String lastDiagnosis = lastNotesiterator.next().getDiagnosis();
								if (!lastDiagnosis.equalsIgnoreCase("")) {
									vwDoctorNote.setDiagnosis(lastDiagnosis);
									diagnosisBlank = false;
								}
							}
						}
					}

					String assessmentNotes = getDoctorNotes(uhid, sqlPresentDate);
					vwDoctorNote.setFollowupnotes(assessmentNotes);

					String defaultDoctorNotesPlan = getDoctorNotesDefaultPlan(uhid, sqlPresentDate);
					vwDoctorNote.setPlan(defaultDoctorNotesPlan);
				}

				java.sql.Date sqlDoaDate = new java.sql.Date(doaDate.getTime());
				vwDoctorNote.setDateofadmission(sqlDoaDate);
				vwDoctorNote.setGender(sex);
				vwDoctorNote.setUhid(uhid);
				long diff = presentDate.getTime() - dobDate.getTime();
				long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;// days..
				long ageMonth = ageDays / (30);
				// long ageYear =diff/(365*30*24*60*60*1000);
				if (ageMonth > 0)
					doctorNotes.setCurrentage(ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days");
				else
					doctorNotes.setCurrentage(ageDays + "days");

				long dayAfterAdmissionDiff = presentDate.getTime() - doaDate.getTime();
				long dayAfterAdmissionDays = (dayAfterAdmissionDiff / (24 * 60 * 60 * 1000)) + 1;// days..
				long dayAfterAdmissionMonth = dayAfterAdmissionDays / (30);
				// long ageYear =diff/(365*30*24*60*60*1000);
				if (dayAfterAdmissionMonth > 0)
					doctorNotes.setDayAfterAdmission(dayAfterAdmissionMonth + "months "
							+ (dayAfterAdmissionDays - (dayAfterAdmissionMonth * 30)) + "days");
				else
					doctorNotes.setDayAfterAdmission(dayAfterAdmissionDays + "days");

				// set total input and output
				vwDoctorNote.setUhid(uhid);
				doctorNotes.setNotes(vwDoctorNote);

				String queryTotalInputOutput = "select obj from VwNursingnotesTotalinputoutput obj where creationdate='"
						+ sqlPresentDate + "' and uhid='" + uhid + "'";
				List<VwNursingnotesTotalinputoutput> totalInptOuptList = patientDao
						.getListFromMappedObjNativeQuery(queryTotalInputOutput);
				if (totalInptOuptList != null && totalInptOuptList.size() > 0) {
					doctorNotes.setTotalinput(totalInptOuptList.get(0).getTotalinput());
					doctorNotes.setTotaloutput(totalInptOuptList.get(0).getTotaloutput());
				} // total input output...

				// set weight and hight data..
				String queryWeightHeight = "select obj from VwNursingnotesHeightweight obj where visitdate='"
						+ sqlPresentDate + "' and uhid='" + uhid + "'";
				List<VwNursingnotesHeightweight> heightWeightList = patientDao
						.getListFromMappedObjNativeQuery(queryWeightHeight);
				if (heightWeightList != null && heightWeightList.size() > 0) {
					VwNursingnotesHeightweight heightWeight = heightWeightList.get(0);
					// doctorNotes.setCurrentage(heightWeight.getCurrentage());
					doctorNotes.setCurrentdateheight(heightWeight.getCurrentdateheight());
					doctorNotes.setCurrentdateweight(heightWeight.getCurrentdateweight());
					doctorNotes.setDiffheight(heightWeight.getDiffheight());
					doctorNotes.setDiffweight(heightWeight.getDiffweight());
					doctorNotes.setVisitdate(presentDate);

				}

				// populate doctor notes with previous assesment sheet details.
				// String assessmentNotes = getDoctorNotes(uhid, sqlPresentDate, todayDate);
				// vwDoctorNote.setDoctornotes(assessmentNotes);
				// vwDoctorNote.setDiagnosis(assessmentNotes);
				// vwDoctorNote.setFollowupnotes(assessmentNotes);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, doctorId, uhid,
					"SAVE_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}
		// CalendarUtility.setDefaultTimeZone(CalendarUtility.CLIENT_CRUD_OPERATION);
		return doctorNotes;
	}

	private String getDoctorNotes(String uhid, java.sql.Date presentDate) {
		String doctorNotes = "";
		java.sql.Date iterableDate = presentDate;

		/*
		 * Date todaysDate = null; java.sql.Date sqlTodaysDate = null; try{ todaysDate =
		 * CalendarUtility.getDateformatdb(CalendarUtility.SERVER_CRUD_OPERATION)
		 * .parse(todayDate.substring(0, 10)); sqlTodaysDate = new
		 * java.sql.Date(todaysDate.getTime()); } catch(Exception ex){
		 * ex.printStackTrace(); }
		 */

		float babyWeigthKg = 0;
		String sqlTodaysDateFrom = presentDate.toString() + " 00:00:01";
		String sqlTodaysDateTo = presentDate.toString() + " 23:59:59";

		// fetch admission date of the uhid.
		String query = "SELECT baby FROM BabyDetail as baby WHERE uhid = '" + uhid.trim() + "'";
		List<BabyDetail> resultSet = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(resultSet)) {
			BabyDetail bd = resultSet.get(0);
			if (!BasicUtils.isEmpty(bd.getDateofadmission())) {
				Date admissionDate = bd.getDateofadmission();
				java.sql.Date admDate = new java.sql.Date(admissionDate.getTime());
				ViewSAJaundice jaundice = null;
				ViewSAMetabolic metabolic = null;
				VwSaRespsystem respSystem = null;
				VwSaCn cns = null;
				SaSepsi sepsis = null;
				SaCardiac cardiac = null;
				SaFeedgrowth feedGrowth = null;
				SaRenalfailure renal = null;
				SaMisc misc = null;

				String queryVwDoctorNoteDOB = "select obj from BabyDetail obj where uhid='" + uhid.trim() + "'";
				List<BabyDetail> listDoctorNotesDOB = patientDao.getListFromMappedObjNativeQuery(queryVwDoctorNoteDOB);
				if (listDoctorNotesDOB != null && listDoctorNotesDOB.size() > 0) {
					BabyDetail babyDetails = listDoctorNotesDOB.get(0);

					long diff = presentDate.getTime() - babyDetails.getDateofbirth().getTime();
					long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;// days..
					long ageMonth = ageDays / (30);
					// long ageYear =diff/(365*30*24*60*60*1000);
					if (ageMonth > 0)
						doctorNotes = doctorNotes + "Days of Life: " + ageMonth + "months "
								+ (ageDays - (ageMonth * 30)) + "days" + "\n";
					else
						doctorNotes = doctorNotes + "Days of Life: " + ageDays + "days" + "\n";

					String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid.trim()
							+ "' order by visitdate desc";
					List<BabyVisit> currentBabyVisitList = patientDao
							.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
					if (currentBabyVisitList != null && currentBabyVisitList.size() > 0) {
						BabyVisit currentBabyVisit = currentBabyVisitList.get(0);
						String[] correctedGAArr = currentBabyVisit.getCorrectedGa().split(",");
						if (correctedGAArr.length >= 2) {
							doctorNotes = doctorNotes + "Corrected Gestation Age: "
									+ (parseInt(correctedGAArr[0]) + (ageDays / 7)) + "weeks+"
									+ (parseInt(correctedGAArr[1]) + (ageDays % 7)) + " days" + "\n";
						} else if (correctedGAArr.length == 1) {
							doctorNotes = doctorNotes + "Corrected Gestation Age: "
									+ (parseInt(correctedGAArr[0]) + (ageDays / 7)) + "weeks+" + (ageDays % 7)
									+ " days" + "\n";
						}
						babyWeigthKg = currentBabyVisit.getCurrentdateweight() / 1000;
						doctorNotes = doctorNotes + "Today's weight: " + currentBabyVisit.getCurrentdateweight()
								+ " gms" + "\n";
					}

				}

				String fetchNursingNotes = "Select obj From NursingOutput as obj where uhid ='" + uhid.trim()
						+ "' and creationtime between '" + sqlTodaysDateFrom + "' AND '" + sqlTodaysDateTo
						+ "' order by creationtime desc";
				List<NursingOutput> nursingNotesList = patientDao.getListFromMappedObjNativeQuery(fetchNursingNotes);
				if (nursingNotesList != null && nursingNotesList.size() > 0) {

					float urineTot = (float) (Math.round(Float.valueOf(
							parseInt(nursingNotesList.get(0).getTotalUo().trim()) / babyWeigthKg / 24) * 10.0)
							/ 10.0);

					doctorNotes = doctorNotes + "Urine output: " + urineTot + " ml/kg/hr" + "\n";
					Iterator<NursingOutput> nursingOutputIterator = nursingNotesList.iterator();
					int stoolCount = 0;
					while (nursingOutputIterator.hasNext()) {
						NursingOutput nursingObj = nursingOutputIterator.next();
						if (nursingObj.getBowelStatus()) {
							stoolCount++;
						}
					}
					if (stoolCount > 1) {
						doctorNotes = doctorNotes + "Stool: " + stoolCount + " times" + "\n";
					} else {
						doctorNotes = doctorNotes + "Stool: " + stoolCount + " time" + "\n";
					}
				}

				String fetchBabyVitalsquery = "select obj from NursingVitalparameter as obj where uhid='" + uhid
						+ "' and to_char(creationtime,'YYYY-MM-DD')='" + presentDate + "' order by creationtime desc";

				List<NursingVitalparameter> listVitalInfo = patientDao
						.getListFromMappedObjNativeQuery(fetchBabyVitalsquery);
				if (listVitalInfo != null && listVitalInfo.size() > 0) {
					if (listVitalInfo.get(0).getHrRate() != null && listVitalInfo.get(0).getHrRate() != 0)
						doctorNotes = doctorNotes + "HR(pm): " + listVitalInfo.get(0).getHrRate() + "\n";
					if (listVitalInfo.get(0).getRrRate() != null && listVitalInfo.get(0).getRrRate() != 0)
						doctorNotes = doctorNotes + "RR(pm): " + listVitalInfo.get(0).getRrRate() + "\n";
					if (listVitalInfo.get(0).getSpo2() != null && !listVitalInfo.get(0).getSpo2().isEmpty())
						doctorNotes = doctorNotes + "SpO2: " + listVitalInfo.get(0).getSpo2() + "\n";
				}

				/*
				 * System.out.println("admission date" + admissionDate); while
				 * ((BasicUtils.isEmpty(jaundice) && (BasicUtils.isEmpty(metabolic)) &&
				 * (BasicUtils.isEmpty(respSystem)) && (BasicUtils.isEmpty(cns)) &&
				 * (BasicUtils.isEmpty(sepsis)) && (BasicUtils.isEmpty(cardiac)) &&
				 * (BasicUtils.isEmpty(renal)) && (BasicUtils.isEmpty(misc))) &&
				 * iterableDate.compareTo(admissionDate) >= 0) {
				 * 
				 * String fetchJaundice =
				 * "SELECT jaund FROM ViewSAJaundice as jaund WHERE jaund.uhidNo='" +
				 * uhid.trim() + "' " + "AND jaund.creationtime='" + iterableDate + "'";
				 * List<ViewSAJaundice> jaundResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchJaundice); if
				 * (!BasicUtils.isEmpty(jaundResult)) { jaundice = jaundResult.get(0);
				 * doctorNotes = doctorNotes + " " + jaundice.toString(); }
				 * 
				 * String fetchMetabolic =
				 * "SELECT meta FROM ViewSAMetabolic as meta WHERE meta.uhid = '" + uhid.trim()
				 * + "' " + "AND meta.creationtime='" + iterableDate + "'";
				 * List<ViewSAMetabolic> metaResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchMetabolic); if
				 * (!BasicUtils.isEmpty(metaResult)) { metabolic = metaResult.get(0);
				 * doctorNotes = doctorNotes + metabolic.toString()+"\n"; }
				 * 
				 * String fetchRespSystem =
				 * "SELECT resp FROM VwSaRespsystem as resp WHERE resp.uhid='" + uhid.trim() +
				 * "' " + "AND resp.creationtime='" + iterableDate + "'"; List<VwSaRespsystem>
				 * respResult = patientDao.getListFromMappedObjNativeQuery(fetchRespSystem); if
				 * (!BasicUtils.isEmpty(respResult)) { respSystem = respResult.get(0);
				 * doctorNotes = doctorNotes + respSystem.toString()+"\n"; }
				 * 
				 * String fetchCns = "SELECT cns FROM VwSaCn as cns WHERE cns.uhid='" +
				 * uhid.trim() + "' " + "AND cns.creationtime = '" + iterableDate + "'";
				 * List<VwSaCn> cnsResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchCns); if
				 * (!BasicUtils.isEmpty(cnsResult)) { cns = cnsResult.get(0); doctorNotes =
				 * doctorNotes + cns.toString()+"\n"; }
				 * 
				 * String fetchSepsis = "SELECT sepsis FROM SaSepsi as sepsis WHERE " +
				 * "sepsis.uhid='" + uhid.trim() + "' AND sepsis.creationtime = '" +
				 * iterableDate + "'"; List<SaSepsi> sepsisResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchSepsis); if
				 * (!BasicUtils.isEmpty(sepsisResult)) { sepsis = sepsisResult.get(0);
				 * doctorNotes = doctorNotes + sepsis.toString()+"\n"; }
				 * 
				 * String iterableDateFrom = iterableDate.toString() + " 00:00:01"; String
				 * iterableDateTo = iterableDate.toString() + " 23:59:59";
				 * 
				 * String fetchCardiac = "SELECT cardiac FROM SaCardiac as cardiac WHERE " +
				 * "cardiac.uhid='" + uhid.trim() + "' AND cardiac.creationtime between " + "'"
				 * + iterableDateFrom + "' AND '" + iterableDateTo +
				 * "' order by cardiac.creationtime desc"; List<SaCardiac> cardiacResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchCardiac); if
				 * (!BasicUtils.isEmpty(cardiacResult)) { cardiac = cardiacResult.get(0);
				 * doctorNotes = doctorNotes + cardiac.toString()+"\n"; }
				 * 
				 * String fetchFeedGrowth = "SELECT feed FROM SaFeedgrowth as feed WHERE " +
				 * "feed.uhid='" + uhid.trim() + "' AND feed.creationtime between " + "'" +
				 * iterableDateFrom + "' AND '" + iterableDateTo +
				 * "' order by feed.creationtime desc"; List<SaFeedgrowth> feedResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchFeedGrowth); if
				 * (!BasicUtils.isEmpty(feedResult)) { feedGrowth = feedResult.get(0);
				 * doctorNotes = doctorNotes + feedGrowth.toString()+"\n"; }
				 * 
				 * String fetchRenal = "SELECT renal FROM SaRenalfailure as renal WHERE " +
				 * "renal.uhid='" + uhid.trim() + "' AND renal.creationtime between " + "'" +
				 * iterableDateFrom + "' AND '" + iterableDateTo +
				 * "' order by renal.creationtime desc"; List<SaRenalfailure> renalResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchRenal); if
				 * (!BasicUtils.isEmpty(renalResult)) { renal = renalResult.get(0); doctorNotes
				 * = doctorNotes + renal.toString()+"\n"; }
				 * 
				 * String fetchMisc = "SELECT misc FROM SaMisc as misc WHERE " + "misc.uhid='" +
				 * uhid.trim() + "' AND misc.creationtime between " + "'" + iterableDateFrom +
				 * "' AND '" + iterableDateTo + "' order by misc.creationtime desc";
				 * List<SaMisc> miscResult =
				 * patientDao.getListFromMappedObjNativeQuery(fetchMisc); if
				 * (!BasicUtils.isEmpty(miscResult)) { misc = miscResult.get(0); doctorNotes =
				 * doctorNotes + misc.toString()+"\n"; }
				 * 
				 * iterableDate = new java.sql.Date(iterableDate.getTime() - 1 * 24 * 3600 *
				 * 1000l); }
				 */
			}
		}

		// doctorNotes = doctorNotes.replace("[,", "[");
		// System.out.println(doctorNotes);

		return doctorNotes;
	}

	public String getDoctorNotesDefaultPlan(String uhid, java.sql.Date presentDate) {
		String defaultPlan = "";
		float babyWeight = 0;
		List<KeyValueObj> frequencyDropDown = null;

		String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid.trim()
				+ "' order by visitdate desc";
		List<BabyVisit> currentBabyVisitList = patientDao.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
		if (currentBabyVisitList != null && currentBabyVisitList.size() > 0) {
			BabyVisit currentBabyVisit = currentBabyVisitList.get(0);
			babyWeight = currentBabyVisit.getCurrentdateweight();
		}
		// getting baby medication....
		String queryPrescription = "SELECT obj FROM BabyPrescription obj where uhid='" + uhid
				+ "' and isactive = 'true' order by startdate, starttime";
		List<BabyPrescription> prescriptionList = patientDao.getListFromMappedObjNativeQuery(queryPrescription);

		if (prescriptionList != null && prescriptionList.size() > 0) {
			frequencyDropDown = getRefObj("select obj.freqid,obj.freqvalue from ref_medfrequency obj");
		}
		/*
		 * if(prescriptionList!= null && prescriptionList.size()>0){ defaultPlan =
		 * defaultPlan+" Medication:"+"\n"; }
		 */
		Iterator<BabyPrescription> prescriptionIterator = prescriptionList.iterator();
		while (prescriptionIterator.hasNext()) {
			BabyPrescription babyPres = prescriptionIterator.next();
			float dose = 0;
			if (babyWeight != 0) {
				dose = (babyPres.getDose() / 1000) * (babyWeight);
			} else {
				dose = babyPres.getDose();
			}
			dose = Math.round(dose * 100) / 100;

			String medFreq = "";
			for (int i = 0; i < frequencyDropDown.size(); i++) {
				if (frequencyDropDown.get(i).getKey().toString().equalsIgnoreCase(babyPres.getFrequency())) {
					medFreq = frequencyDropDown.get(i).getValue().toString();
				}
			}
			defaultPlan = defaultPlan + babyPres.getMedicinename() + " : " + dose + " Mg/" + babyPres.getRoute() + " ("
					+ medFreq + ")\n";
		}
		// getting baby feeds..
		String feedQuery = "select obj from BabyfeedDetail obj where uhid='" + uhid + "' order by creationtime desc";
		List<BabyfeedDetail> babyFeedList = patientDao.getListFromMappedObjNativeQuery(feedQuery);
		if (babyFeedList != null && babyFeedList.size() > 0) {
			BabyfeedDetail babyFeed = babyFeedList.get(0);
			defaultPlan = defaultPlan + "\n" + "Total Fluid Intake (ml/kg/day): " + babyFeed.getTotalfluidMlDay()
					+ "\n";
		}

		return defaultPlan;
	}

	@Override
	public Boolean isExistingUhid(String uhid) {
		String query = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
		List patientList = patientDao.getListFromMappedObjNativeQuery(query);
		if (!BasicUtils.isEmpty(patientList)) {
			return true;
		}
		return false;
	}

	@Override
	public NursingNotesDropDownsObj getNursingNotesDropDowns() {
		List<KeyValueObj> masterMethod = getRefObj(
				"select obj.feedmethodid,obj.feedmethodname from ref_masterfeedmethod obj");
		KeyValueObj feedMethodOBj = new KeyValueObj();
		feedMethodOBj.setKey(BasicConstants.OTHERS);
		feedMethodOBj.setValue(BasicConstants.OTHERS);
		masterMethod.add(feedMethodOBj);

		List<KeyValueObj> masterType = getRefObj("select obj.feedtypeid,obj.feedtypename from ref_masterfeedtype obj");
		/*
		 * KeyValueObj feedTypeOBj = new KeyValueObj();
		 * feedTypeOBj.setKey(BasicConstants.OTHERS);
		 * feedTypeOBj.setValue(BasicConstants.OTHERS); masterType.add(feedTypeOBj);
		 */

		List<KeyValueObj> masterBO = getRefObj("select obj.masterboid,obj.masterboname from ref_masterbo obj");
		List<KeyValueObj> pupilDropDown = getRefObj(
				"select obj.reactivityid,obj.reactivity from  ref_pupilreactivity obj");
		List<KeyValueObj> catheterDropDown = getRefObj(
				"select obj.catheterslineid,obj.catheters_line from  ref_cathetersline obj");
		List<KeyValueObj> ventilatorModeDropDown = getRefObj(
				"select obj.ventmodeid,obj.ventilationmode from  ref_ventilationmode obj where status = 'active' order by ventmodeid");
		List<KeyValueObj> ventiAllModes = getRefObj(
				"select obj.ventmodeid,obj.ventilationmode from  ref_ventilationmode obj");

		List<KeyValueObj> frequencyDropDown = getRefObj("select obj.freqid,obj.freqvalue from ref_medfrequency obj");
		List<KeyValueObj> fluidDropDown = getRefObj("select obj.fluidid,obj.fluidvalue from ref_fluidtype obj");

		NursingNotesDropDownsObj dropDownObj = new NursingNotesDropDownsObj();
		dropDownObj.setFeedMethod(masterMethod);
		dropDownObj.setFeedType(masterType);
		dropDownObj.setFeedBo(masterBO);
		dropDownObj.setPupilReact(pupilDropDown);
		dropDownObj.setHours(getTime(BasicConstants.HOURS));
		dropDownObj.setMinutes(getTime(BasicConstants.MINUTES));
		dropDownObj.setSeconds(getTime(BasicConstants.SECONDS));
		dropDownObj.setCatheterLine(catheterDropDown);
		dropDownObj.setVentilatorMode(ventilatorModeDropDown);
		dropDownObj.setVentiAllModes(ventiAllModes);
		dropDownObj.setFrequencyMed(frequencyDropDown);
		dropDownObj.setFluidType(fluidDropDown);

		// for nec cause
		String queryAssessmentNec = "SELECT cause_id, cause_name FROM ref_causeofnpo";
		List<KeyValueObj> assessmentNec = getRefObj(queryAssessmentNec);
		dropDownObj.setCauseOfNpo(assessmentNec);

		String enAddtivesBrand = "select obj from RefEnAddtivesBrand obj";
		List<RefEnAddtivesBrand> enAddtivesList = inicuDao.getListFromMappedObjQuery(enAddtivesBrand);
		if (!BasicUtils.isEmpty(enAddtivesList)) {
			dropDownObj.setAdditivesbrandlist(enAddtivesList);
		}
		
		return dropDownObj;
	}

	public List getRefObj(String query) {
		List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
		try {
			// String query = "select obj.bpid,obj.blood_product from
			// kalawati.ref_blood_product obj";
			List<Object[]> refList = patientDao.getListFromNativeQuery(query);
			KeyValueObj keyValue = null;
			if (refList != null && !refList.isEmpty()) {
				Iterator<Object[]> iterator = refList.iterator();
				while (iterator.hasNext()) {
					keyValue = new KeyValueObj();
					Object[] obj = iterator.next();
					if (obj != null && obj[0] != null)
						keyValue.setKey(obj[0]);
					if (obj != null && obj[1] != null)
						keyValue.setValue(obj[1]);
					refKeyValueList.add(keyValue);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return refKeyValueList;
	}

	@Override
	public List<VwBloodProduct> getBloodProductsInfo(String uhid, String date) {
		List<VwBloodProduct> bloodList = null;
		try {

			String query = "select obj from VwBloodProduct as obj where uhid='" + uhid + "'";
			if (!date.isEmpty()) {
				query = query + " and creationdate='" + date + "'";
			}
			bloodList = patientDao.getListFromMappedObjNativeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bloodList;
	}

	@Override
	public ResponseMessageWithResponseObject addBloodProduct(VwBloodProduct bloodProduct, String loggedInUserId)
			throws InicuDatabaseExeption {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		String uhid = "";
		String bloodproduct = "";
		String dose = "";
		String duration = "";
		String bloodgroup = "";
		try {
			if (bloodProduct != null) {
				uhid = bloodProduct.getUhid();

				if (!BasicUtils.isEmpty(bloodProduct.getBloodproduct()))
					bloodproduct = bloodProduct.getBloodproduct();

				if (!BasicUtils.isEmpty(bloodProduct.getDose()))
					dose = bloodProduct.getDose();

				if (!BasicUtils.isEmpty(bloodProduct.getDuration()))
					duration = bloodProduct.getDuration();

				if (!BasicUtils.isEmpty(bloodProduct.getBloodgroup()))
					bloodgroup = bloodProduct.getBloodgroup();

				/*
				 * String query =
				 * "insert into BloodProduct(uhid,bloodproduct, dose, duration,bloodgroup,loggeduser) values("
				 * + "'" + uhid + "'" + ",'" + bloodproduct + "'" + ",'" + dose + "'" + ",'" +
				 * duration + "'" + ",'" + bloodgroup +"'"+ ",'" + loggedInUserId + "');";
				 */

				BloodProduct bloodProductObj = new BloodProduct();
				bloodProductObj.setUhid(uhid);
				bloodProductObj.setBloodproduct(bloodproduct);
				bloodProductObj.setDose(dose);
				bloodProductObj.setDuration(duration);
				bloodProductObj.setBloodgroup(bloodgroup);
				bloodProductObj.setLoggeduser(loggedInUserId);

				patientDao.saveObject(bloodProductObj);

				response.setType(BasicConstants.MESSAGE_SUCCESS);
				response.setMessage("Blood Products added successfully!!");
				logService.saveLog(BasicUtils.getObjAsJson(bloodProductObj), BasicConstants.INSERT, loggedInUserId,
						uhid, BasicConstants.PAGE_DOCTOR_NOTES);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setMessage(ex.getMessage());
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedInUserId,
					uhid, "GET_OBJECT", BasicUtils.convertErrorStacktoString(ex));
		}
		return response;
	}

	@Override
	public Object savePupilReactivity(String uhid, String reactivity, String pupilsize, String equality,
			String comments, java.sql.Date sqlPresentDate, String loggedUserId) {
		PupilReactivity obj = new PupilReactivity();
		if (!BasicUtils.isEmpty(uhid)) {
			/*
			 * String querySelect = "select obj from PupilReactivity obj where uhid='"+uhid+
			 * "' and creationtime >='"+sqlPresentDate+"'" + "order by creationtime";
			 * List<PupilReactivity> list =
			 * patientDao.getListFromMappedObjNativeQuery(querySelect);
			 */
			/*
			 * if(list!=null && list.size()>0 ){ obj = list.get(0);
			 * obj.setReactivity(reactivity); }else{ obj = new PupilReactivity();
			 * obj.setUhid(uhid); obj.setReactivity(reactivity); }
			 */
			obj = new PupilReactivity();
			obj.setUhid(uhid);
			/* obj.setReactivity(reactivity); */
			/* obj.setPupilsize(pupilsize); */
			try {
				patientDao.saveObject(obj);
				logService.saveLog(BasicUtils.getObjAsJson(obj), BasicConstants.INSERT, loggedUserId, uhid,
						BasicConstants.PAGE_DOCTOR_NOTES);
				return true;
			} catch (Exception e) {

				e.printStackTrace();
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public HashMap<String, List<String>> getDoctorNotesDropDowns() {
		HashMap<String, List<String>> dropDowns = new HashMap<String, List<String>>();
		List<String> hours = new ArrayList<String>();
		for (int i = 0; i <= 23; i++) {
			if (i < 10) {
				hours.add("0" + i);
			} else {
				hours.add(String.valueOf(i));
			}
		}
		dropDowns.put("hours", hours);
		return dropDowns;
	}

	@Override
	public List<HashMap<String, String>> getDoctorNotesGraph(String uhid, String todayDate, String dob) {
		// Object graph.....
		// HashMap<Object, Object> graphObject = new HashMap<Object, Object>();

		List<HashMap<String, String>> graphEntrySet = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> greaterEntrySet = new ArrayList<HashMap<String, String>>();

		try {
			java.util.Date todayDateUtil = dateFormat.parse(todayDate);
			java.util.Date dobUtil = dateFormat.parse(dob);
			java.sql.Date todayDateSql = new java.sql.Date(todayDateUtil.getTime());

			String query = "select obj.creationdate,obj.totalinput,obj.totaloutput from " + BasicConstants.SCHEMA_NAME
					+ ".vw_nursingnotes_totalinputoutput obj where uhid='" + uhid + "' and creationdate >='"
					+ todayDateSql + "' order by creationdate limit 7";
			greaterEntrySet = getGraphList(query);
			Iterator<HashMap<String, String>> iterator;
			int sizeOfList = greaterEntrySet.size();
			int restSize = 7 - sizeOfList;
			if (restSize > 0) {
				/*
				 * String queryRest =
				 * "select obj.creationdate,obj.totalinput,obj.totaloutput from "
				 * +BasicConstants.SCHEMA_NAME+"" +
				 * ".vw_doctornotes_final obj where uhid='"+uhid+
				 * "' and creationdate <'"+todayDateSql+
				 * "' order by creationdate limit "+restSize;
				 */
				String queryRest = "select obj.creationdate,obj.totalinput,obj.totaloutput from "
						+ BasicConstants.SCHEMA_NAME + "" + ".vw_nursingnotes_totalinputoutput obj where uhid='" + uhid
						+ "' and creationdate <'" + todayDateSql + "' order by creationdate limit " + restSize;
				List<HashMap<String, String>> entrySetRest = getGraphList(queryRest);
				iterator = entrySetRest.iterator();
				while (iterator.hasNext()) {
					graphEntrySet.add(iterator.next());
				}
			}
			iterator = greaterEntrySet.iterator();
			while (iterator.hasNext()) {
				graphEntrySet.add(iterator.next());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return graphEntrySet;
	}

	public List<HashMap<String, String>> getGraphList(String query) {
		List<HashMap<String, String>> graph = new ArrayList<HashMap<String, String>>();

		try {

			List<Object[]> listNursingNotes = patientDao.getListFromNativeQuery(query);

			if (listNursingNotes != null) {
				Iterator<Object[]> iterator = listNursingNotes.iterator();
				while (iterator.hasNext()) {
					HashMap<String, String> graphEntry = new HashMap<String, String>();
					Object[] nursingNote = iterator.next();
					String age = "";

					if (!BasicUtils.isEmpty(nursingNote[0]))
						graphEntry.put("date", dateFormat.format(nursingNote[0]));

					if (!BasicUtils.isEmpty(nursingNote[1]))
						graphEntry.put("input", String.valueOf(nursingNote[1]));

					if (!BasicUtils.isEmpty(nursingNote[2]))
						graphEntry.put("output", String.valueOf(nursingNote[2]));

					graph.add(graphEntry);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return graph;
	}

	@Override
	public void testSaveObject(Object pres) {
		try {
			patientDao.saveObject(pres);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Object, Object> printDoctorNotes(AssessmentPrintInfoObject printObj) {
		HashMap<Object, Object> notesOBj = new HashMap<Object, Object>();

		// get record from the object...
		try {
			System.out.println(printObj.getDateFrom());
			System.out.println(printObj.getDateTo());
			Date dateFrom = CalendarUtility.dateFormatUTF.parse(printObj.getDateFrom());
			Date dateTo = CalendarUtility.dateFormatUTF.parse(printObj.getDateTo());
			String hoursFrom = printObj.getTimeFrom();
			String hoursTo = printObj.getTimeTo();
			String uhid = printObj.getUhid();
			String moduleName = printObj.getModuleName();
			if (!BasicUtils.isEmpty(moduleName)) {
				Calendar calFrom = Calendar.getInstance();
				calFrom.setTime(dateFrom);
				// calFrom.set(Calendar.DAY_OF_MONTH,
				// calFrom.get(Calendar.DAY_OF_MONTH)-1);
				Calendar calTo = Calendar.getInstance();
				calTo.setTime(dateTo);

				if (!BasicUtils.isEmpty(hoursFrom)) {
					calFrom.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hoursFrom));
					calFrom.set(Calendar.MINUTE, 0);
				}

				if (!BasicUtils.isEmpty(hoursTo)) {
					calTo.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hoursTo));
					calTo.set(Calendar.MINUTE, 0);
				}
				System.out.println("horss ");
				System.out.println(hoursFrom + " " + hoursTo);
				System.out.println(calFrom.getTime());
				System.out.println(calTo.getTime());
				System.out.println("in UTC");
				System.out.println(CalendarUtility.timeStampFormat.format(calFrom.getTime()));
				System.out.println(CalendarUtility.timeStampFormat.format(calTo.getTime()));
				String strDateFrom = CalendarUtility.timeStampFormat.format(calFrom.getTime());
				String strDateTo = CalendarUtility.timeStampFormat.format(calTo.getTime());
				if (moduleName.equalsIgnoreCase("doctorNotes")) {

					String query = "select obj from SaJaundice as obj where uhid='" + uhid
							+ "' and to_char(creationtime,'yyyy-MM-dd HH:mm:ss')>='" + strDateFrom + "' and "
							+ "to_char(creationtime,'yyyy-MM-dd HH:mm:ss')<='" + strDateTo + "'";
					List<SaJaundice> jaundicePrintData = inicuDao.getListFromMappedObjQuery(query);
					notesOBj.put("notesDetails", jaundicePrintData);

					return notesOBj;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<VwDischargedPatientsFinal> getDischargedPatients(String uhid, String fromDateStr, String toDateStr,
			String branchName) {

		String startDate = "";
		String endDate = "";
		String assessmentName = "";

		Date fromDate = null;
		Date toDate = null;
		try {

			if (!BasicUtils.isEmpty(toDateStr) && !toDateStr.equalsIgnoreCase("null")) {
				toDate = BasicConstants.dateFormat.parse(toDateStr);
				toDate = new Timestamp(
						toDate.getTime() + TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset());
				endDate = BasicConstants.timeStampFormat.format(toDate);
			}
			if (!BasicUtils.isEmpty(fromDateStr) && !fromDateStr.equalsIgnoreCase("null")) {
				fromDate = BasicConstants.dateFormat.parse(fromDateStr);
				fromDate = new Timestamp(
						fromDate.getTime() + TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset());
				startDate = BasicConstants.timeStampFormat.format(fromDate);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String query = "select obj from VwDischargedPatientsFinal obj where obj.admissionstatus = 'FALSE' and obj.activestatus = 'TRUE' and branchname = '"
				+ branchName + "'";

		if (BasicUtils.isEmpty(uhid) || uhid.equalsIgnoreCase("null")) {
			if (BasicUtils.isEmpty(startDate)) {
				if (BasicUtils.isEmpty(endDate)) {// query only for status of
					// discharge = true

				} else {// search only for patient upto end date... and status
					// of discharge = true
					query = query + " and dischargeddate<= '" + endDate + "'";
				}
			} else {
				if (BasicUtils.isEmpty(endDate)) {// search only for patient
					// start from start date...
					// and status of discharge =
					// true
					query = query + " and dischargeddate>= '" + startDate + "'";
				} else {// search for patient start date to end date... and
					// status of discharge = true
					query = query + " and dischargeddate>= '" + startDate + "' and dischargeddate<= '" + endDate + "'";
				}
			}
		} else {

			if (BasicUtils.isEmpty(startDate)) {
				if (BasicUtils.isEmpty(endDate)) {// query for uhid and status
					// of discharge = true
					query = query + " and (uhid = '" + uhid + "'" + " or lower(babyname) LIKE " + "lower('" + '%' + uhid
							+ '%' + "')" + ")";
				} else {// search for uhid and patient upto end date... and
					// status of discharge = true
					query = query + " and (uhid = '" + uhid + " or lower(babyname) LIKE " + "lower('" + '%' + uhid + '%'
							+ "')" + ")" + " and dischargeddate<= '" + endDate + "'";
				}
			} else {
				if (BasicUtils.isEmpty(endDate)) {// search for uhid, patient
					// start from start date...
					// and status of discharge =
					// true
					query = query + " and (uhid = '" + uhid + "'" + " or lower(babyname) LIKE " + "lower('" + '%' + uhid
							+ '%' + "')" + ")" + " and dischargeddate>= '" + startDate + "'";
				} else {// search for uhid, patient start date to end date...
					// and status of discharge = true
					query = query + " and (uhid = '" + uhid + "'" + " or lower(babyname) LIKE " + "lower('" + '%' + uhid
							+ '%' + "')" + ")" + "  and dischargeddate>= '" + startDate + "' and dischargeddate<= '"
							+ endDate + "'";
				}
			}
		}

		query += " order by dischargeddate asc";
		System.out.println(query);

		List<VwDischargedPatientsFinal> babyDetailsList = patientDao.getListFromMappedObjNativeQuery(query);

		if (babyDetailsList == null || babyDetailsList.size() == 0) {
			babyDetailsList = new ArrayList<VwDischargedPatientsFinal>();
		}

		// getting the active assessments for all the discharged patients whose
		// discharge status is not Discharge
		for (int i = 0; i < babyDetailsList.size(); i++) {
			String queryActiveAssesments = "select DISTINCT associatedEvent from ActiveAssesmentFinal where uhid = '"
					+ babyDetailsList.get(i).getUhid() + "'";
			List<ActiveAssesmentFinal> activeAssesmentsList = patientDao
					.getListFromMappedObjNativeQuery(queryActiveAssesments);
			if (!BasicUtils.isEmpty(activeAssesmentsList)) {

				assessmentName = activeAssesmentsList.toString().replace("[", "").replace("]", "");

				babyDetailsList.get(i).setAssessmentname(assessmentName);
			}

		}

		for (int i = 0; i < babyDetailsList.size(); i++) {
			int epCountRds = 1;
			String queryEpCountRds = "SELECT MAX(episode_number) FROM sa_resp_rds WHERE uhid='"
					+ babyDetailsList.get(i).getUhid() + "'";
			List<Integer> epCountListRds = inicuDao.getListFromNativeQuery(queryEpCountRds);
			if (!BasicUtils.isEmpty(epCountListRds) && epCountListRds != null) {
				epCountRds = (int) epCountListRds.get(0);

				List<SaRespRds> rdsList = new ArrayList<SaRespRds>();
				rdsList = inicuDao.getListFromMappedObjQuery(
						HqlSqlQueryConstants.getRdsList(babyDetailsList.get(i).getUhid(), epCountRds));

				String respiratorySupportNotesRds = "";
				List<String> respSupportIdListRds = new ArrayList<>();
				if (!BasicUtils.isEmpty(rdsList)) {
					try {
						for (int i1 = 0; i1 < rdsList.size(); i1++) {
							SaRespRds rdsObj = (SaRespRds) rdsList.get(i1);
							respSupportIdListRds.add("'" + rdsObj.getResprdsid().toString() + "'");
						}

						if (!BasicUtils.isEmpty(respSupportIdListRds)) {
							String respSupportIdListStringRds = respSupportIdListRds.toString();
							respSupportIdListStringRds = respSupportIdListStringRds.replace("[", "");
							respSupportIdListStringRds = respSupportIdListStringRds.replace("]", "");

							String queryRespiratorySupport = "SELECT distinct rs_vent_type,rs_mech_vent_type,creationtime "
									+ "FROM respsupport WHERE uhid='" + babyDetailsList.get(i).getUhid()
									+ "' and eventid IN (" + respSupportIdListStringRds
									+ ") and rs_vent_type is not null order by creationtime";
							List<Object> respiratorySupportList = inicuDao
									.getListFromNativeQuery(queryRespiratorySupport);
							if (!BasicUtils.isEmpty(respiratorySupportList)) {

								Long respSupportDays = getRespSupportDays(respiratorySupportList,
										babyDetailsList.get(i).getUhid());
								if(respSupportDays == 0) {
									
								}else {
								System.out.println(respSupportDays);
								int respDays = respSupportDays.intValue();
								babyDetailsList.get(i).setRespSupportDays(respDays);
								}
							}
						}
					}

					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		for (int i = 0; i < babyDetailsList.size(); i++) {
			
			Date admissionDate = babyDetailsList.get(i).getDateofadmission();
			//String adDate = babyDetailsList.get(i).getTimeofadmission();
			Timestamp dischargedDate = babyDetailsList.get(i).getDischargeddate();
			String lengthOfStayStr = "";
			long dd = dischargedDate.getTime();
			long lengthOfStay =  ((dischargedDate.getTime() - admissionDate.getTime())/(1000*60*60*24));
			lengthOfStayStr = lengthOfStay + " Days.";
			
			if(lengthOfStay ==0) {
				String timeOfAdmission = babyDetailsList.get(i).getTimeofadmission();
				timeOfAdmission = timeOfAdmission.replaceFirst(",", ":");
				timeOfAdmission = timeOfAdmission.replace(",", " ");
				String admDate = admissionDate + " " + timeOfAdmission;
				//Timestamp time = (Timestamp)timeOfAdmission;
				SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
		                "yyyy-MM-dd hh:mm aa");
				SimpleDateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Date lFromDate1 = null;
				try {
					lFromDate1 = datetimeFormatter1.parse(admDate);
					String admDate1  = dateTimeFormat2.format(lFromDate1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("gpsdate :" + lFromDate1);
				Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
					Long timeDiff = dischargedDate.getTime() - fromTS1.getTime();
					 lengthOfStayStr = ((timeDiff / 3600000) + " hrs " + (timeDiff % 3600000) / 60000 + " mins");

			}
			babyDetailsList.get(i).setLengthOfStay(lengthOfStayStr);

			System.out.println("Offset Value here 3: "+offset);

			babyDetailsList.get(i).setDateTimeOfBirth(new Timestamp(BasicUtils.getDateTimeFromString(babyDetailsList.get(i).getDateofbirth(),babyDetailsList.get(i).getTimeofbirth()).getTime()- offset));
			babyDetailsList.get(i).setDateTimeOfAdmission(new Timestamp(BasicUtils.getDateTimeFromString(babyDetailsList.get(i).getDateofadmission(),babyDetailsList.get(i).getTimeofadmission()).getTime()- offset));
		}

		return babyDetailsList;

	}

	public Long getRespSupportDays(List<Object> respiratorySupportList, String uhid) {
		String respiratorySupportRdsHelperString = "";
		Object[] respiratorySupportObj = null;
		Long time, totalDuration, durationLFO2, durationHFO2, durationCPAP, durationNIMV, durationSIMV, durationPSV,
				durationHFO, currRespDuration, invasiveDol, nonInvasiveDol, oxygenSupplmentDol;
		time = totalDuration = durationLFO2 = durationHFO2 = durationCPAP = durationNIMV = durationSIMV = durationPSV = durationHFO = currRespDuration = invasiveDol = nonInvasiveDol = oxygenSupplmentDol = 0L;
		String timeString, prevRespiratorySupport, currRespiratorySupport;
		prevRespiratorySupport = currRespiratorySupport = null;
		Timestamp startTime, stopTime, stopTimeInvasive, stopTimeNonInvasive, stopTimeOxygen;
		startTime = stopTime = stopTimeInvasive = stopTimeNonInvasive = stopTimeOxygen = null;
		try {
			for (int i = 0; i < respiratorySupportList.size(); i++) {
				respiratorySupportObj = (Object[]) respiratorySupportList.get(i);
				if (respiratorySupportObj != null) {
					if (prevRespiratorySupport == null && (String) respiratorySupportObj[0] != null) {
						if (((String) respiratorySupportObj[0]).equalsIgnoreCase("Mechanical Ventilation"))
							prevRespiratorySupport = currRespiratorySupport = (String) respiratorySupportObj[1];
						else
							prevRespiratorySupport = currRespiratorySupport = (String) respiratorySupportObj[0];
					} else {
						if ((String) respiratorySupportObj[0] != null) {
							if (((String) respiratorySupportObj[0]).equalsIgnoreCase("Mechanical Ventilation"))
								currRespiratorySupport = (String) respiratorySupportObj[1];
							else
								currRespiratorySupport = (String) respiratorySupportObj[0];

							if (prevRespiratorySupport != currRespiratorySupport) {
								stopTime = (Timestamp) respiratorySupportObj[2];
								currRespDuration = Math.abs(stopTime.getTime() - startTime.getTime()) / (1000 * 60*60*24);
								totalDuration += currRespDuration;
								if (prevRespiratorySupport.equalsIgnoreCase("High Flow O2")) {
									durationHFO2 += currRespDuration;
									stopTimeOxygen = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("Low Flow O2")) {
									durationLFO2 += currRespDuration;
									stopTimeOxygen = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("CPAP")) {
									durationCPAP += currRespDuration;
									stopTimeNonInvasive = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("NIMV")) {
									durationNIMV += currRespDuration;
									stopTimeNonInvasive = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("SIMV")) {
									durationSIMV += currRespDuration;
									stopTimeInvasive = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("PSV")) {
									durationPSV += currRespDuration;
									stopTimeInvasive = stopTime;
								}
								if (prevRespiratorySupport.equalsIgnoreCase("HFO")) {
									durationHFO += currRespDuration;
									stopTimeInvasive = stopTime;
								}
								startTime = null;
								prevRespiratorySupport = currRespiratorySupport;
							}
						} else {

							if (i == respiratorySupportList.size() - 1) {
								if (respiratorySupportObj[2] != null) {
									stopTime = (Timestamp) respiratorySupportObj[2];
								}

								if (stopTime != null && startTime != null) {
									currRespDuration = Math.abs(stopTime.getTime() - startTime.getTime()) / (1000 * 60*60*24);
									totalDuration += currRespDuration;

									if (prevRespiratorySupport.equalsIgnoreCase("High Flow O2")) {
										durationHFO2 += currRespDuration;
										stopTimeOxygen = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("Low Flow O2")) {
										durationLFO2 += currRespDuration;
										stopTimeOxygen = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("CPAP")) {
										durationCPAP += currRespDuration;
										stopTimeNonInvasive = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("NIMV")) {
										durationNIMV += currRespDuration;
										stopTimeNonInvasive = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("SIMV")) {
										durationSIMV += currRespDuration;
										stopTimeInvasive = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("PSV")) {
										durationPSV += currRespDuration;
										stopTimeInvasive = stopTime;
									}
									if (prevRespiratorySupport.equalsIgnoreCase("HFO")) {
										durationHFO += currRespDuration;
										stopTimeInvasive = stopTime;
									}
									startTime = null;
									prevRespiratorySupport = currRespiratorySupport;
								}
							}
						}
					}

					if (startTime == null) {
						startTime = (Timestamp) respiratorySupportObj[2];
					}

//					if(respiratorySupportObj[1] == null || respiratorySupportObj[1].toString().equalsIgnoreCase("")) {
//						stopTime = (Timestamp) respiratorySupportObj[2];
//						currRespDuration = Math.abs(stopTime.getTime() - startTime.getTime()) / (1000 * 60);
//						
//					}
				}
			}

			if (totalDuration != 0) {
				boolean firstSupportdone = false;
				timeString = "minutes";
				time = totalDuration;
				if (totalDuration == 1) {
					timeString = "minute";
				}

				if (totalDuration >= (60 * 24)) {
					timeString = "day";
					if (totalDuration >= (120 * 24)) {
						timeString = "days";
					}
					time = totalDuration / (24 * 60);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DischargedSummaryObj getDischargeSummary(String uhid, String episodeId, String fromTime, String toTime) {
		// For Creating a New Line
		String htmlNextLine = System.getProperty("line.separator");

		DischargedSummaryObj dischargedSummaryObj = new DischargedSummaryObj();
		SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String dummyopfor = "";
		String branchName = "";
		try {
			// EntityManager em = InicuPostgresUtililty.em;
			String queryStr = "select obj from BabyDetail obj where obj.uhid='" + uhid + "' ";
			if (!BasicUtils.isEmpty(episodeId)) {
				queryStr += " and episodeid='" + episodeId + "'";
			}
			queryStr += " order by creationtime desc";
			List<BabyDetail> babyDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr);
			BabyDetail babyDetails = null;
			if (babyDetailsList != null && babyDetailsList.size() > 0) {
				babyDetails = babyDetailsList.get(0);
				branchName = babyDetails.getBranchname();
				String babyBloodGroup = babyDetails.getBloodgroup();

				String queryhospitalName = "select obj from RefHospitalbranchname obj where branchname='" + branchName
						+ "'";
				List<RefHospitalbranchname> refHospitalBranchNameList = patientDao
						.getListFromMappedObjNativeQuery(queryhospitalName);
				if (!BasicUtils.isEmpty(refHospitalBranchNameList)) {
					dischargedSummaryObj.setHospitalDetails(refHospitalBranchNameList.get(0));
				}


				// Implement the Time and Dat logic here
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();

				System.out.println("Offset Value here 1 :"+offset);

				// Set the date and time  of the baby
				babyDetails.setDateTimeOfBirth(new Timestamp(BasicUtils.getDateTimeFromString(babyDetails.getDateofbirth(),babyDetails.getTimeofbirth()).getTime()-offset));
				babyDetails.setDateTimeOfAdmission(new Timestamp(BasicUtils.getDateTimeFromString(babyDetails.getDateofadmission(),babyDetails.getTimeofadmission()).getTime()-offset));

				dischargedSummaryObj.setBabyDetails(babyDetails);

				ParentDetail parentDetails = null;
				String queryStr2 = "select obj from ParentDetail obj where obj.uhid='" + uhid + "'";
				List<ParentDetail> parentDetailsList = patientDao.getListFromMappedObjNativeQuery(queryStr2);
				if (parentDetailsList != null && parentDetailsList.size() > 0) {
					parentDetails = parentDetailsList.get(0);
					String address = "";
//					if (!BasicUtils.isEmpty(parentDetails.getFathername())) {
//						address += parentDetails.getFathername();
//					}

					if (!BasicUtils.isEmpty(parentDetails.getAddress())) {
						if (!address.isEmpty()) {
							address += ", ";
						}
						address += parentDetails.getAddress();
					}

					if (!BasicUtils.isEmpty(parentDetails.getAdd_state())) {
						if (!address.isEmpty()) {
							address += ", ";
						}
						address += parentDetails.getAdd_state();
					}

					// use getAdd_district and getAddress
					if (!BasicUtils.isEmpty(parentDetails.getAdd_district())) {
						if (!address.isEmpty()) {
							address += ", ";
						}
						address += parentDetails.getAdd_district();
					}

					if (!BasicUtils.isEmpty(parentDetails.getAdd_city())) {
						if (!address.isEmpty()) {
							address += ", ";
						}
						address += parentDetails.getAdd_city();
					}

					if (!BasicUtils.isEmpty(parentDetails.getAdd_pin())) {
						if (!address.isEmpty()) {
							address += ", ";
						}
						address += parentDetails.getAdd_pin();
					}

//					if (!BasicUtils.isEmpty(parentDetails.getFather_phone())) {
//						if (!address.isEmpty()) {
//							address += ", ";
//						}
//						address += parentDetails.getFather_phone();
//					}

					parentDetails.setAddress(address);
				}

				dischargedSummaryObj.setParenteDetail(parentDetails);

				// fetch active doctors list
				List<String> activeDoctors = new ArrayList<>();
				String fetchDoctorsQuery = HqlSqlQueryConstants.getAllActiveDoctors(branchName);
				activeDoctors = inicuDao.getListFromNativeQuery(fetchDoctorsQuery);
				if (!BasicUtils.isEmpty(activeDoctors))
					dischargedSummaryObj.setActiveDoctors(activeDoctors);

				// getPreviousAdviceOnDischarge
				List<DischargeAdviceDetail> prevDischargeAdviceDetail = new ArrayList<>();
				String fetchPrevDischargeAdviceDetail = "Select obj from DischargeAdviceDetail as obj where uhid='"
						+ uhid + "'";
				prevDischargeAdviceDetail = inicuDao.getListFromMappedObjQuery(fetchPrevDischargeAdviceDetail);
				dischargedSummaryObj.setPastadviceOnDischarge(prevDischargeAdviceDetail);

				

				// fetching Advice on Discharge drafts
				String fetchAdviceOnDischargefFixedValues = "Select obj from RefAdviceOnDischarge as obj where branchName='"
						+ branchName + "'" + "and status='true' order by ref_advice_on_discharge_id";
				List<RefAdviceOnDischarge> listOfFixedValuesForAdviceOnDischarge = new ArrayList<>();
				listOfFixedValuesForAdviceOnDischarge = inicuDao
						.getListFromMappedObjQuery(fetchAdviceOnDischargefFixedValues);
				dischargedSummaryObj.setAdviceTemplate(listOfFixedValuesForAdviceOnDischarge);

				// Antenatal Detail Fetching function
				String queryAntenatalDetail = "select obj from AntenatalHistoryDetail obj where obj.uhid='" + uhid
						+ "'";
				List<AntenatalHistoryDetail> antenatalDetailList = patientDao
						.getListFromMappedObjNativeQuery(queryAntenatalDetail);
				if (!BasicUtils.isEmpty(antenatalDetailList)) {
					dischargedSummaryObj.setBabyAntenatalHistoryDetails(antenatalDetailList.get(0));
				}

				// Get the Examination Notes
				String examinationNotes = "select obj from SystemicExaminationNotes as obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<SystemicExaminationNotes> examinationNotesList = inicuDao
						.getListFromMappedObjQuery(examinationNotes);
				if (!BasicUtils.isEmpty(examinationNotesList)) {
					dischargedSummaryObj.setSystemicExaminationNotes(examinationNotesList.get(0));
				}

				// BirthToNicu Detail Fetching function
				String queryBirthToNicuDetail = "select obj from BirthToNicu obj where obj.uhid='" + uhid + "'";
				List<BirthToNicu> birthToNicuDetailList = patientDao
						.getListFromMappedObjNativeQuery(queryBirthToNicuDetail);
				if (!BasicUtils.isEmpty(birthToNicuDetailList)) {
					dischargedSummaryObj.setBirthToNicuDetails(birthToNicuDetailList.get(0));
				}

				// General Physical Exam Result Fetching function
				String queryBabyPhysicalExamResult = "select obj from GenPhyExam obj where obj.uhid='" + uhid + "'";
				List<GenPhyExam> babyPhysicalExamResultList = patientDao
						.getListFromMappedObjNativeQuery(queryBabyPhysicalExamResult);
				if (!BasicUtils.isEmpty(babyPhysicalExamResultList)) {
					dischargedSummaryObj.setBabyPhysicalExamData(babyPhysicalExamResultList.get(0));
				}

				// Bpd Respiratory Note
				String bpdRespiratoryNote = getCausesOfBpdRespiratory(uhid);
				dischargedSummaryObj.setBpdRespiratoryNote(bpdRespiratoryNote);

				// Icd Code
				String icdCode = getIcdCodeList(uhid);
				dischargedSummaryObj.setIcdCode(icdCode);

				// Systemic Exam Result Fetching function
				String queryBabySystemicExamResult = "select obj from SystemicExam obj where obj.uhid='" + uhid + "'";
				List<SystemicExam> babySystemicExamResultList = patientDao
						.getListFromMappedObjNativeQuery(queryBabySystemicExamResult);
				if (!BasicUtils.isEmpty(babySystemicExamResultList)) {
					String babySystemicExamData = null;
					SystemicExam systemicExamObj = babySystemicExamResultList.get(0);
					List<String> systemicExamDataList = new ArrayList<>();
					if (systemicExamObj != null) {
						if (systemicExamObj.getApnea() != null && systemicExamObj.getApnea()) {
							systemicExamDataList.add("Apnea");
						}
						if (systemicExamObj.getAsphyxia() != null && systemicExamObj.getAsphyxia()) {
							systemicExamDataList.add("Asphyxia");
						}
						if (systemicExamObj.getClabsi() != null && systemicExamObj.getClabsi()) {
							systemicExamDataList.add("Clabsi");
						}
						if (systemicExamObj.getIvh() != null && systemicExamObj.getIvh()) {
							systemicExamDataList.add("Ivh");
						}
						if (systemicExamObj.getJaundice() != null && systemicExamObj.getJaundice()) {
							systemicExamDataList.add("Jaundice");
						}
						if (systemicExamObj.getPneumothorax() != null && systemicExamObj.getPneumothorax()) {
							systemicExamDataList.add("Pneumothorax");
						}
						if (systemicExamObj.getPphn() != null && systemicExamObj.getPphn()) {
							systemicExamDataList.add("Pphn");
						}
						if (systemicExamObj.getRds() != null && systemicExamObj.getRds()) {
							systemicExamDataList.add("Rds");
						}
						if (systemicExamObj.getSeizures() != null && systemicExamObj.getSeizures()) {
							systemicExamDataList.add("Seizures");
						}
						if (systemicExamObj.getSepsis() != null && systemicExamObj.getSepsis()) {
							systemicExamDataList.add("Sepsis");
						}
						if (systemicExamObj.getVap() != null && systemicExamObj.getVap()) {
							systemicExamDataList.add("Vap");
						}
						if (systemicExamObj.getShock() != null && systemicExamObj.getShock()) {
							systemicExamDataList.add("Shock");
						}
					}

					if (!BasicUtils.isEmpty(systemicExamDataList)) {
						babySystemicExamData = systemicExamDataList.toString();
						babySystemicExamData = babySystemicExamData.replace("[", "");
						babySystemicExamData = babySystemicExamData.replace("]", "");

						if (babySystemicExamData.contains(",")) {
							babySystemicExamData += " are assesed.";
						} else {
							babySystemicExamData += " is assesed.";
						}
					}
					dischargedSummaryObj.setBabySystemicExamData(babySystemicExamData);
				}

				String querySystemicExamNotes = "select obj from AdmissionNotes as obj where uhid = '" + uhid
						+ "' order by creationtime desc";
				List<AdmissionNotes> systemicNotesList = inicuDao.getListFromMappedObjQuery(querySystemicExamNotes);
				if (!BasicUtils.isEmpty(systemicNotesList)) {
					String systemicExamNotes = systemicNotesList.get(0).getSystemic_exam();

					dischargedSummaryObj.setSystemicExamNotes(systemicExamNotes);
				}
				// getting out come here..
				String query = "select obj from DischargeOutcome obj where uhid='" + uhid + "'";
				List<DischargeOutcome> outcomeList = inicuDao.getListFromMappedObjQuery(query);
				DischargeOutcome outcomeData = null;
				if (!BasicUtils.isEmpty(outcomeList)) {
					outcomeData = outcomeList.get(0);
					dischargedSummaryObj.setOutcome(outcomeData);
				}

				String queryBabyVisitBirth = "select obj from BabyVisit obj where uhid='" + uhid + "' and visitdate='"
						+ dateFormatDB.format(babyDetails.getDateofbirth()) + "' order by visitdate desc";
				List<BabyVisit> listBabyVisitBirth = patientDao.getListFromMappedObjNativeQuery(queryBabyVisitBirth);
				if (!BasicUtils.isEmpty(listBabyVisitBirth)) {
					BabyVisit babyVisitBirth = listBabyVisitBirth.get(0);
					GestationObj gestationActualObj = new GestationObj();
					String gestation = "";
					if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
						gestationActualObj.setWeeks(babyDetails.getGestationweekbylmp());
						gestation = babyDetails.getActualgestationweek() + " weeks ";
					}
					if (!BasicUtils.isEmpty(babyDetails.getActualgestationdays())) {
						gestationActualObj.setDays(babyDetails.getGestationdaysbylmp());
						gestation += babyDetails.getActualgestationdays() + " days";
					}
					babyVisitBirth.setActualGestation(gestation);

					dischargedSummaryObj.setBirthVisit(babyVisitBirth);

				}

				String queryBabyVisitAdmission = "select obj from BabyVisit obj where uhid='" + uhid
						+ "' and visitdate='" + dateFormatDB.format(babyDetails.getDateofadmission())
						+ "' order by visitdate desc";
				List<BabyVisit> listBabyVisitAdmission = patientDao
						.getListFromMappedObjNativeQuery(queryBabyVisitAdmission);

				if (BasicUtils.isEmpty(listBabyVisitAdmission)) {
					String queryBabyVisitAdmissionFirstEntry = "select obj from BabyVisit obj where uhid='" + uhid
							+ "' order by visitdate";
					List<BabyVisit> listBabyVisitAdmissionfirstentry = patientDao
							.getListFromMappedObjNativeQuery(queryBabyVisitAdmissionFirstEntry);
					if (listBabyVisitAdmissionfirstentry.size() > 0) {
						listBabyVisitAdmission = listBabyVisitAdmissionfirstentry;
					}
				}

				if (!BasicUtils.isEmpty(listBabyVisitAdmission)) {

					if (BasicUtils.isEmpty(listBabyVisitAdmission.get(0).getCurrentdateheadcircum())
							|| BasicUtils.isEmpty(listBabyVisitAdmission.get(0).getCurrentdateheight())
							|| BasicUtils.isEmpty(listBabyVisitAdmission.get(0).getCurrentdateweight())) {
						String queryBabyVisitAdmission2 = "select currentdateheight,currentdateheadcircum,currentdateweight "
								+ "from baby_visit where uhid='" + uhid
								+ "' and currentdateheight is not null and currentdateheadcircum is not null"
								+ " and currentdateweight is not null " + "order by visitdate ";
						List<Object[]> listBabyVisitAdmission2 = patientDao
								.getListFromNativeQuery(queryBabyVisitAdmission2);

						if (!BasicUtils.isEmpty(listBabyVisitAdmission2)) {

							if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[0])) {
								listBabyVisitAdmission.get(0).setCurrentdateheight(
										(Float.parseFloat(listBabyVisitAdmission2.get(0)[0].toString())));
							}
							if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[1])) {
								listBabyVisitAdmission.get(0).setCurrentdateheadcircum(
										(Float.parseFloat(listBabyVisitAdmission2.get(0)[1].toString())));
							}
							if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[2])) {
								listBabyVisitAdmission.get(0).setCurrentdateweight(
										(Float.parseFloat(listBabyVisitAdmission2.get(0)[2].toString())));
							}
						}
					}
					dischargedSummaryObj.setAdmissionVisit(listBabyVisitAdmission.get(0));
				}
//				if (BasicUtils.isEmpty(listBabyVisitAdmission)) {
//					String queryBabyVisitAdmission2 = "select currentdateheight,currentdateheadcircum,currentdateweight "
//							+ "from baby_visit where uhid='" + uhid
//							+ "' and currentdateheight is not null and currentdateheadcircum is not null"
//							+ " and currentdateweight is not null " + "order by visitdate ";
//					List<Object[]> listBabyVisitAdmission2 = patientDao
//							.getListFromNativeQuery(queryBabyVisitAdmission2);
//					if (!BasicUtils.isEmpty(listBabyVisitAdmission2)) {
//						if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[0])) {
//							listBabyVisitAdmission.get(0).setCurrentdateheight(
//									(Float.parseFloat(listBabyVisitAdmission2.get(0)[0].toString())));
//						}
//						if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[1])) {
//							listBabyVisitAdmission.get(0).setCurrentdateheadcircum(
//									(Float.parseFloat(listBabyVisitAdmission2.get(0)[1].toString())));
//						}
//						if (!BasicUtils.isEmpty(listBabyVisitAdmission2.get(0)[2])) {
//							listBabyVisitAdmission.get(0).setAdmissionWeight(
//									(Float.parseFloat(listBabyVisitAdmission2.get(0)[2].toString())));
//						}
//						dischargedSummaryObj.setAdmissionVisit(listBabyVisitAdmission.get(0));
//					} 
//				}
				String queryBabyVisitDischarge = "";
				if (!BasicUtils.isEmpty(outcomeData) && !BasicUtils.isEmpty(outcomeData.getEntrytime())) {
					queryBabyVisitDischarge = "select obj from BabyVisit obj where uhid='" + uhid + "' and visitdate='"
							+ dateFormatDB.format(outcomeData.getEntrytime()) + "' order by visitdate desc";

					List<BabyVisit> listBabyVisitDischarge = patientDao
							.getListFromMappedObjNativeQuery(queryBabyVisitDischarge);

					if (BasicUtils.isEmpty(listBabyVisitDischarge)) {

						String queryBabyDischargeFirstEntry = "select obj from BabyVisit obj where uhid='" + uhid
								+ "' order by visitdate desc";
						List<BabyVisit> listBabyVisitDischargefirstentry = patientDao
								.getListFromMappedObjNativeQuery(queryBabyDischargeFirstEntry);
						if (listBabyVisitDischargefirstentry.size() > 0) {
							listBabyVisitDischarge = listBabyVisitDischargefirstentry;
						}
					}

					BabyVisit babyVisitDischarge = new BabyVisit();

					if (!BasicUtils.isEmpty(listBabyVisitDischarge)) {
						babyVisitDischarge = listBabyVisitDischarge.get(0);
					}

					int dischargeGetaionDays = 0;
					Date dateOfBirth = babyDetails.getDateofbirth();
					long gestationDaysAfterBirth = (outcomeData.getEntrytime().getTime() - dateOfBirth.getTime())
							/ (24 * 60 * 60 * 1000);

					GestationObj gestationActualObj = new GestationObj();
					String gestation = "";
					if (!BasicUtils.isEmpty(babyDetails.getActualgestationweek())) {
						dischargeGetaionDays = babyDetails.getActualgestationweek() * 7;

					}
					if (!BasicUtils.isEmpty(babyDetails.getActualgestationdays())) {
						dischargeGetaionDays += babyDetails.getActualgestationdays();
					}

					long currentGestaionTotalDays = gestationDaysAfterBirth + dischargeGetaionDays;
					if (currentGestaionTotalDays % 7 == 0) {
						gestationActualObj.setWeeks(currentGestaionTotalDays / 7);
						babyVisitDischarge.setGestationWeek(Integer.valueOf(currentGestaionTotalDays / 7 + ""));
						babyVisitDischarge.setGestationDays(Integer.valueOf(0));
					} else {
						long actualDays = currentGestaionTotalDays % 7;
						babyVisitDischarge.setGestationDays(Integer.valueOf(actualDays + ""));
						babyVisitDischarge
								.setGestationWeek(Integer.valueOf((currentGestaionTotalDays - actualDays) / 7 + ""));
						gestationActualObj.setDays(actualDays);
						gestationActualObj.setWeeks((currentGestaionTotalDays - actualDays) / 7);
					}
					gestation = babyDetails.getActualgestationweek() + " weeks ";
					gestation += babyDetails.getActualgestationdays() + " days";
					babyVisitDischarge.setActualGestation(gestation);

					if ((BasicUtils.isEmpty(babyVisitDischarge.getCurrentdateheight()))
							|| (BasicUtils.isEmpty(babyVisitDischarge.getCurrentdateheadcircum()))) {
						String queryBabyVisitDischarge2 = "select currentdateheight,currentdateheadcircum ,currentdateweight "
								+ "from baby_visit where uhid='" + uhid
								+ "' and currentdateheight is not null and currentdateheadcircum is not null and currentdateheight is not null "
								+ "order by visitdate desc ";
						List<Object[]> listBabyVisitDischarge2 = patientDao
								.getListFromNativeQuery(queryBabyVisitDischarge2);
						if (!BasicUtils.isEmpty(listBabyVisitDischarge2)) {
							if (BasicUtils.isEmpty(babyVisitDischarge.getCurrentdateheight())) {
								babyVisitDischarge.setCurrentdateheight(
										(Float.parseFloat(listBabyVisitDischarge2.get(0)[0].toString())));
							}
							if (BasicUtils.isEmpty(babyVisitDischarge.getCurrentdateheadcircum())) {
								babyVisitDischarge.setCurrentdateheadcircum(
										(Float.parseFloat(listBabyVisitDischarge2.get(0)[1].toString())));
							}

							if (BasicUtils.isEmpty(babyVisitDischarge.getCurrentdateweight())) {
								babyVisitDischarge.setCurrentdateweight(
										(Float.parseFloat(listBabyVisitDischarge2.get(0)[2].toString())));
							}
						}
					}
					dischargedSummaryObj.setDischargeVisit(babyVisitDischarge);
				}

				DischargeNotesDetail dischargeNotes = new DischargeNotesDetail();

				String queryDischargeNotes = "select obj from DischargeNotesDetail obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<DischargeNotesDetail> listNotes = inicuDao.getListFromMappedObjQuery(queryDischargeNotes);
				if (!BasicUtils.isEmpty(listNotes)) {
					dischargeNotes = listNotes.get(0);
				}

				String paragraph = "<pre><span class='marker'>";
				String queryAdmissionNotes = "select obj from  AdmissionNotes obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<AdmissionNotes> listAdmissionNotes = inicuDao.getListFromMappedObjQuery(queryAdmissionNotes);
				String adminssionNotesDiagnosis = "";
				if (!BasicUtils.isEmpty(listAdmissionNotes)) {
					AdmissionNotes admNotes = listAdmissionNotes.get(0);

					if (!BasicUtils.isEmpty(admNotes.getDiagnosis())) {
						adminssionNotesDiagnosis = admNotes.getDiagnosis();
						
						dischargedSummaryObj.setFinalDiagnosis(adminssionNotesDiagnosis);
					}
					if (dischargeNotes.getIsAntenatalHistory()) {
						dischargeNotes.setAntenatalDetails(dischargeNotes.getAntenatalDetails());
					} else {
						dischargeNotes.setAntenatalDetails(admNotes.getAntenatal_history());
					}

					if (dischargeNotes.getIsBirthDetails()) {
						dischargeNotes.setBirthDetails(dischargeNotes.getBirthDetails());
					} else {
						dischargeNotes.setBirthDetails(admNotes.getBirth_to_inicu());
					}

					if (dischargeNotes.getIsStatusAtAdmission()) {
						dischargeNotes.setStatusAtAdmission(dischargeNotes.getStatusAtAdmission());
					} else {
						dischargeNotes.setStatusAtAdmission(admNotes.getStatus_at_admission());
					}
				}

				String queryDiagnosisDetails = "select obj from  DashboardFinalview obj where uhid='" + uhid + "'";
				List<DashboardFinalview> listDiagnosisDetails = inicuDao
						.getListFromMappedObjQuery(queryDiagnosisDetails);
				int isPresent = 0;
				if (!BasicUtils.isEmpty(listDiagnosisDetails)) {
					if (!BasicUtils.isEmpty(listDiagnosisDetails.get(0).getDiagnosis())) {

						// diagnosis note
						String diagnosisStr = listDiagnosisDetails.get(0).getDiagnosis().replaceAll(",", "/");

						int flag = 0;
						if(adminssionNotesDiagnosis.contains("Sepsis")){
							adminssionNotesDiagnosis = notesService.getSepsisType(adminssionNotesDiagnosis,uhid);
							flag =1;
						}

						if (diagnosisStr.contains("Sepsis")) {
							if(flag == 0) {
							diagnosisStr = notesService.getSepsisType(diagnosisStr, uhid);
							}else if (flag == 1){

								// replace the Sepsis with empty string
								diagnosisStr = diagnosisStr.replace("Sepsis","");
							}
						}

						diagnosisStr = adminssionNotesDiagnosis.replace(",", "/") + "/"
								+ diagnosisStr;
						// misc disease list
						String queryMiscDisease = "select obj from  SaMiscellaneous obj where uhid='" + uhid
								+ "' order by creationtime desc";
						List<SaMiscellaneous> listMiscDisease = inicuDao.getListFromMappedObjQuery(queryMiscDisease);
						String miscDiseaseDiagnosis = "";
						if (!BasicUtils.isEmpty(listMiscDisease)) {
							for (SaMiscellaneous miscObj : listMiscDisease) {

								if (!BasicUtils.isEmpty(miscObj.getDisease())) {
									miscDiseaseDiagnosis += miscObj.getDisease() + "/";
								} else {
									miscDiseaseDiagnosis += "Miscellaneous" + "/";
								}

							}
							if (!BasicUtils.isEmpty(miscDiseaseDiagnosis)) {
								if (BasicUtils.isEmpty(diagnosisStr)) {
									diagnosisStr += miscDiseaseDiagnosis;
								} else {
									diagnosisStr += "/" + miscDiseaseDiagnosis;
								}
							}
						}

						// misc2 disease list
						String queryMisc2Disease = "select obj from  SaMiscellaneous2 obj where uhid='" + uhid
								+ "' order by creationtime desc";
						List<SaMiscellaneous2> listMisc2Disease = inicuDao.getListFromMappedObjQuery(queryMisc2Disease);
						String misc2DiseaseDiagnosis = "";
						if (!BasicUtils.isEmpty(listMisc2Disease)) {
							for (SaMiscellaneous2 misc2Obj : listMisc2Disease) {
								if (!BasicUtils.isEmpty(misc2Obj.getDisease())) {
									misc2DiseaseDiagnosis += misc2Obj.getDisease() + "/";
								} else {
									misc2DiseaseDiagnosis += "Miscellaneous" + "/";
								}
							}
							if (!BasicUtils.isEmpty(misc2DiseaseDiagnosis)) {
								if (BasicUtils.isEmpty(diagnosisStr)) {
									diagnosisStr += misc2DiseaseDiagnosis;
								} else {
									diagnosisStr += "/" + misc2DiseaseDiagnosis;
								}
							}
						}
						
						String queryRop = "select obj from ScreenRop as obj where uhid = '" + uhid + "'";
						List<ScreenRop> ropLists = inicuDao.getListFromMappedObjQuery(queryRop);
						boolean leftRop = false;
						boolean rightRop = false;
						String ropDiagnosis = "";
						if(!BasicUtils.isEmpty(ropLists)) {
							for(ScreenRop ropObject: ropLists) {
								if(ropObject.getIs_rop()!=null && ropObject.getIs_rop()==true) {
									ropDiagnosis = "ROP";
									if(ropObject.getIs_rop_left()!=null && ropObject.getIs_rop_left() == true) {
										leftRop = true;
									}
									if(ropObject.getIs_rop_right()!=null && ropObject.getIs_rop_right() == true) {
										rightRop = true;
									}
								}
							}
							
								
							if(leftRop == true && rightRop == false) {
								ropDiagnosis = "ROP Left eye";
								diagnosisStr+= "/ " + ropDiagnosis;
							}
							else if(rightRop == true && leftRop == false) {
								ropDiagnosis = "/ " + "ROP Right eye";
								diagnosisStr+= "/ " + ropDiagnosis;
							}
							else if(rightRop == true && leftRop == true) {
								ropDiagnosis = "/ " + "ROP Both eyes";
								diagnosisStr+= "/ " + ropDiagnosis;				}
							else {
								diagnosisStr+= "/ " + ropDiagnosis;
							}
							
							
						}
						
						String[] daignosisArr = diagnosisStr.split("/");
						List<String> diagonsis = Arrays.asList(daignosisArr);

						if (Arrays.asList(daignosisArr).contains("Prematurity")
								|| Arrays.asList(daignosisArr).contains(" Prematurity")) {
							// If Prematurity if contained in the Array then insert it on the 2nd place
							isPresent = 1;
						}
						diagnosisStr = "";
						int index = 0;
						for (String daignosis : daignosisArr) {
							if (isPresent == 1) {
								index++;
							}
							if (diagnosisStr.isEmpty()) {
								diagnosisStr = daignosis;
							} else if (!diagnosisStr.contains(daignosis.trim())) {
								if (index == 3) {
									diagnosisStr += "/Prematurity";
									diagnosisStr += "/ " + daignosis.trim();
								} else {
									diagnosisStr += "/ " + daignosis.trim();
								}
							}
						}
						dischargedSummaryObj.setFinalDiagnosis(diagnosisStr);
					}
				}
				// handle unique in diagnosis...
				String finalDiagnosis = dischargedSummaryObj.getFinalDiagnosis();
				String finalDiagnosisUnique = "";
				if (!BasicUtils.isEmpty(finalDiagnosis)) {
					String[] finalDiagArr = finalDiagnosis.split("/");

					for (String diag : finalDiagArr) {
						if (finalDiagnosisUnique.isEmpty()) {
							finalDiagnosisUnique = diag.replace(" ", "");
						} else {
							/*
							 * if (!finalDiagnosisUnique.contains(diag.replace(" ", ""))) {
							 * finalDiagnosisUnique += "/ " + diag.replace(" ", ""); }
							 */
							finalDiagnosisUnique += "/ " + diag;
						}
					}
					finalDiagnosisUnique = finalDiagnosisUnique.trim();
					finalDiagnosisUnique = finalDiagnosisUnique.replace("Jaundice", "NNH");
					if (isPresent == 1) {
						finalDiagnosisUnique = finalDiagnosisUnique.replace("Apnea", "Apnea of prematurity");
					}
				}
				dischargedSummaryObj.setFinalDiagnosis(finalDiagnosisUnique.replace(",", "/"));

				// Growth Details
				String growthNotes, prevGrowthNotes;
				growthNotes = prevGrowthNotes = "";
				String queryGrowthNotes = "SELECT growth FROM discharge_notes_detail WHERE uhid='" + uhid + "'";
				List<String> growthNotesList = inicuDao.getListFromNativeQuery(queryGrowthNotes);
				if (!BasicUtils.isEmpty(growthNotesList)) {
					prevGrowthNotes += growthNotesList.get(0);
					dischargeNotes.setGrowth(prevGrowthNotes);
				}

				growthNotes += getGrowthDetails(uhid);
				dischargeNotes.setCurrGrowth(growthNotes);

				// Rds Notes
				String rdsNotes, prevRdsNotes;
				rdsNotes = prevRdsNotes = "";
				String queryEventStatusRds = "SELECT eventstatus FROM sa_resp_rds WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListRds = inicuDao.getListFromNativeQuery(queryEventStatusRds);
				String statusRds = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListRds)) {
					statusRds = (String) eventStatusListRds.get(eventStatusListRds.size() - 1);
				}

				if (!statusRds.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesRds(inicuDao, uhid, "", null, "");
					if (!BasicUtils.isEmpty(notes)) {
						rdsNotes += notes;
					}

					if (statusRds.equalsIgnoreCase("yes")) {
						rdsNotes += "Baby Continues to have RDS. ";
					}

					if (statusRds.equalsIgnoreCase("no")) {
						rdsNotes += "RDS is settled but baby is still under Observation. ";
					}
				} else {
					List<SaRespRds> oldRdsList = new ArrayList<SaRespRds>();
					oldRdsList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveRdsList(uhid));

					if (!BasicUtils.isEmpty(oldRdsList)) {
						rdsNotes += oldRdsList.get(oldRdsList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsRdsNotes() != null && dischargeNotes.getIsRdsNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getRdsNotes())) {
					prevRdsNotes = dischargeNotes.getRdsNotes();
					if (!BasicUtils.isEmpty(prevRdsNotes)) {
						dischargeNotes.setRdsNotes(prevRdsNotes);
					}
				}

				if (!BasicUtils.isEmpty(rdsNotes)) {
					dischargeNotes.setProvisionalNotesRds(rdsNotes);

				}

				// On Going res support detail
				RespSupport lastRespObject = this.getLastRespSupport(uhid);
				if (lastRespObject != null) {
					dischargedSummaryObj.setOngoingRespSupport(lastRespObject);
				}

				String currentMedicationQuery = "select medicinename,route,sum((abs(extract(day from coalesce(enddate,'"
						+ dateFormatDB.format(outcomeData.getEntrytime()) + "') - coalesce(startdate,'"
						+ dateFormatDB.format(babyDetails.getDateofadmission())
						+ "'))))),po_type from baby_prescription where uhid='" + uhid + "' and isactive=true"
						+ " group by 1,2,4";
				List<Object[]> currentMedicationQueryList = inicuDao.getListFromNativeQuery(currentMedicationQuery);

				String currentMedicineStr = "";
				if (!BasicUtils.isEmpty(currentMedicationQueryList)) {
					for (Object[] medObj : currentMedicationQueryList) {

						if (medObj[0] != null) {
							String preFix = "";
							if (medObj[1] != null) {
								if (medObj[1].toString().equals("IV") || medObj[1].toString().equals("IM")) {
									preFix = "Inj. ";
								} else if (medObj[1].toString().equals("Inhaled")) {
									preFix = "Inhaled. ";
								} else if (medObj[1].toString().equals("PO")) {
									if ((medObj[3] != null) && medObj[3].toString().equals("Syrup"))
										preFix = "Syp. ";
									else if ((medObj[3] != null) && medObj[3].toString().equals("Tablet"))
										preFix = "Tab. ";
								}
							}
							if (currentMedicineStr.equals("")) {
								currentMedicineStr += preFix + medObj[0] + "("
										+ Math.round(Float.parseFloat(medObj[2].toString())) + " days)";
							} else {
								currentMedicineStr += ", " + preFix + medObj[0] + "("
										+ Math.round(Float.parseFloat(medObj[2].toString())) + " days)";
							}
						}
					}
					dischargedSummaryObj.setOngoingMedication(currentMedicineStr);
				}

				// Apnea Notes
				String apneaNotes, prevApneaNotes;
				apneaNotes = prevApneaNotes = "";
				String queryEventStatusApnea = "SELECT eventstatus FROM sa_resp_apnea WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListApnea = inicuDao.getListFromNativeQuery(queryEventStatusApnea);
				String statusApnea = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListApnea)) {
					statusApnea = (String) eventStatusListApnea.get(eventStatusListApnea.size() - 1);
				}

				if (!statusApnea.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesApnea(inicuDao, uhid, "", null, "");
					if (!BasicUtils.isEmpty(notes)) {
						apneaNotes += notes;
					}

					if (statusApnea.equalsIgnoreCase("yes")) {
						apneaNotes += "Baby Continues to have Apnea. ";
					}

					if (statusApnea.equalsIgnoreCase("no")) {
						apneaNotes += "Apnea is settled but baby is still under Observation. ";
					}
				} else {
					List<SaRespApnea> oldApneaList = new ArrayList<SaRespApnea>();
					oldApneaList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveApneaList(uhid));

					if (!BasicUtils.isEmpty(oldApneaList)) {
						apneaNotes += oldApneaList.get(oldApneaList.size() - 1).getApneaComment();
					}
				}

				if (dischargeNotes.getIsApneaNotes() != null && dischargeNotes.getIsApneaNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getApneaNotes())) {
					prevApneaNotes = dischargeNotes.getApneaNotes();
					if (!BasicUtils.isEmpty(prevApneaNotes)) {
						dischargeNotes.setApneaNotes(prevApneaNotes);
					}
				}

				if (!BasicUtils.isEmpty(apneaNotes)) {
					dischargeNotes.setProvisionalNotesApnea(apneaNotes);

				}

				// PPHN Notes
				String pphnNotes, prevPphnNotes;
				pphnNotes = prevPphnNotes = "";
				String queryEventStatusPphn = "SELECT eventstatus FROM sa_resp_pphn WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListPphn = inicuDao.getListFromNativeQuery(queryEventStatusPphn);
				String statusPphn = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListPphn)) {
					statusPphn = (String) eventStatusListPphn.get(eventStatusListPphn.size() - 1);
				}

				if (!statusPphn.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesPPHN(inicuDao, uhid, "", null, "");
					if (!BasicUtils.isEmpty(notes)) {
						pphnNotes += notes;
					}

					if (statusPphn.equalsIgnoreCase("yes")) {
						pphnNotes += "Baby Continues to have PPHN. ";
					}

					if (statusPphn.equalsIgnoreCase("no")) {
						pphnNotes += "PPHN is settled but baby is still under Observation. ";
					}
				} else {
					List<SaRespPphn> oldPphnList = new ArrayList<SaRespPphn>();
					oldPphnList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactivePPHNList(uhid));

					if (!BasicUtils.isEmpty(oldPphnList)) {
						pphnNotes += oldPphnList.get(oldPphnList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsPphnNotes() != null && dischargeNotes.getIsPphnNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getPphnNotes())) {
					prevPphnNotes = dischargeNotes.getPphnNotes();
					if (!BasicUtils.isEmpty(prevPphnNotes)) {
						dischargeNotes.setPphnNotes(prevPphnNotes);
					}
				}

				if (!BasicUtils.isEmpty(pphnNotes)) {
					dischargeNotes.setProvisionalNotesPphn(pphnNotes);

				}

				// Pneumothorax Notes
				String pneumothoraxNotes, prevPneumothoraxNotes;
				pneumothoraxNotes = prevPneumothoraxNotes = "";
				String queryEventStatusPneumothorax = "SELECT eventstatus FROM sa_resp_pneumothorax WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListPneumothorax = inicuDao
						.getListFromNativeQuery(queryEventStatusPneumothorax);
				String statusPneumothorax = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListPneumothorax)) {
					statusPneumothorax = (String) eventStatusListPneumothorax
							.get(eventStatusListPneumothorax.size() - 1);
				}

				if (!statusPneumothorax.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesPneumothorax(inicuDao, uhid, "", null, "");
					if (!BasicUtils.isEmpty(notes)) {
						pneumothoraxNotes += notes;
					}

					if (statusPneumothorax.equalsIgnoreCase("yes")) {
						pneumothoraxNotes += "Baby Continues to have Pneumothorax. ";
					}

					if (statusPneumothorax.equalsIgnoreCase("no")) {
						pneumothoraxNotes += "Pneumothorax is settled but baby is still under Observation. ";
					}
				} else {
					List<SaRespPneumo> oldPneumoList = new ArrayList<SaRespPneumo>();
					oldPneumoList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactivePneumothoraxList(uhid));

					if (!BasicUtils.isEmpty(oldPneumoList)) {
						pneumothoraxNotes += oldPneumoList.get(oldPneumoList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsPneumoNotes() != null && dischargeNotes.getIsPneumoNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getPneumoNotes())) {
					prevPneumothoraxNotes = dischargeNotes.getPneumoNotes();
					if (!BasicUtils.isEmpty(prevPneumothoraxNotes)) {
						dischargeNotes.setPneumoNotes(prevPneumothoraxNotes);
					}
				}

				if (!BasicUtils.isEmpty(pneumothoraxNotes)) {
					dischargeNotes.setProvisionalNotesPneumothorax(pneumothoraxNotes);

				}

				// Jaundice Notes
				String jaundiceNotes, prevJaundiceNotes;
				jaundiceNotes = prevJaundiceNotes = "";
				String queryEventStatusJaundice = "SELECT jaundicestatus FROM sa_jaundice WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListJaundice = inicuDao.getListFromNativeQuery(queryEventStatusJaundice);
				String statusJaundice = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListJaundice)) {
					statusJaundice = (String) eventStatusListJaundice.get(eventStatusListJaundice.size() - 1);
				}

				if (!statusJaundice.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesJaundice(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						jaundiceNotes += notes;
					}

					if (statusJaundice.equalsIgnoreCase("yes")) {
						jaundiceNotes += "Baby Continues to have Jaundice. ";
					}

					if (statusJaundice.equalsIgnoreCase("no")) {
						jaundiceNotes += "Jaundice is settled but baby is still under Observation. ";
					}
				} else {
					List<SaJaundice> oldJaundiceList = new ArrayList<SaJaundice>();
					oldJaundiceList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveJaundiceList(uhid));

					if (!BasicUtils.isEmpty(oldJaundiceList)) {
						jaundiceNotes += oldJaundiceList.get(oldJaundiceList.size() - 1).getComment();
					}
				}

				if (dischargeNotes.getIsJaundice() != null && dischargeNotes.getIsJaundice()
						&& !BasicUtils.isEmpty(dischargeNotes.getJaundice())) {
					prevJaundiceNotes = dischargeNotes.getJaundice();
					if (!BasicUtils.isEmpty(prevJaundiceNotes)) {
						dischargeNotes.setJaundice(prevJaundiceNotes);
					}
				}

				if (!BasicUtils.isEmpty(jaundiceNotes)) {
					dischargeNotes.setProvisionalNotesJaundice(jaundiceNotes);
				}
				
				// Shock Notes
				String shockNotes, prevShockNotes;
				shockNotes = prevShockNotes = "";
				String queryEventStatusShock = "SELECT shockStatus FROM sa_shock WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListShock = inicuDao.getListFromNativeQuery(queryEventStatusShock);
				String statusShock = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListShock)) {
					statusShock = (String) eventStatusListShock.get(eventStatusListShock.size() - 1);
				}

				if (!statusShock.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesShock(inicuDao, uhid, "", "");
					if (!BasicUtils.isEmpty(notes)) {
						shockNotes += notes;
					}

					if (statusShock.equalsIgnoreCase("yes")) {
						shockNotes += "Baby Continues to have Shock. ";
					}

					if (statusShock.equalsIgnoreCase("no")) {
						shockNotes += "Shock is settled but baby is still under Observation. ";
					}
				} else {
					List<SaShock> oldShockList = new ArrayList<SaShock>();
					oldShockList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveShockList(uhid));

					if (!BasicUtils.isEmpty(oldShockList)) {
						shockNotes += oldShockList.get(oldShockList.size() - 1).getComment();
					}
				}

				if (dischargeNotes.getIsShock() != null && dischargeNotes.getIsShock()
						&& !BasicUtils.isEmpty(dischargeNotes.getShock())) {
					prevShockNotes = dischargeNotes.getShock();
					if (!BasicUtils.isEmpty(prevShockNotes)) {
						dischargeNotes.setShock(prevShockNotes);
					}
				}

				if (!BasicUtils.isEmpty(shockNotes)) {
					dischargeNotes.setProvisionalNotesShock(shockNotes);
				}

				/**
				 * Purpose: Feed intolerance inactive notes
				 * 
				 * @Updated on: 20/6/2019
				 * @author:Shweta Mohanani
				 * 
				 */

				// Feed intolerance Notes
				String feedintoleranceNotes, prevfeedintoleranceNotes;
				feedintoleranceNotes = prevfeedintoleranceNotes = "";
				String queryEventStatusFeedIntolerance = "SELECT feed_intolerance_status FROM sa_feed_intolerance WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListFeedIntolerance = inicuDao
						.getListFromNativeQuery(queryEventStatusFeedIntolerance);
				String statusFeedIntolerance = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListFeedIntolerance)) {
					statusJaundice = (String) eventStatusListFeedIntolerance
							.get(eventStatusListFeedIntolerance.size() - 1);
				}

				if (!statusJaundice.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesFeedIntolerance(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						feedintoleranceNotes += notes;
					}

					if (statusJaundice.equalsIgnoreCase("yes")) {
						feedintoleranceNotes += "Baby Continues to have Feed Intolerance. ";
					}

					if (statusJaundice.equalsIgnoreCase("no")) {
						feedintoleranceNotes += "Feed Intolerance is settled but baby is still under Observation. ";
					}
				} else {
					List<SaFeedIntolerance> oldFeedIntoleranceList = new ArrayList<SaFeedIntolerance>();
					oldFeedIntoleranceList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveFeedIntoleranceList(uhid));

					if (!BasicUtils.isEmpty(oldFeedIntoleranceList)) {
						feedintoleranceNotes += oldFeedIntoleranceList.get(oldFeedIntoleranceList.size() - 1)
								.getComment();
					}
				}

				if (dischargeNotes.getIsFeedIntolerance() != null && dischargeNotes.getIsFeedIntolerance()
						&& !BasicUtils.isEmpty(dischargeNotes.getIsFeedIntolerance())) {
					prevfeedintoleranceNotes = dischargeNotes.getJaundice();
					if (!BasicUtils.isEmpty(prevfeedintoleranceNotes)) {
						dischargeNotes.setFeedIntolerance(prevfeedintoleranceNotes);
					}
				}

				if (!BasicUtils.isEmpty(feedintoleranceNotes)) {
					dischargeNotes.setProvisionalNotesFeedIntolerance(feedintoleranceNotes);
				}

				// Asphyxia Notes
				String asphyxiaNotes, prevAsphyxiaNotes;
				asphyxiaNotes = prevAsphyxiaNotes = "";
				String queryEventStatusAsphyxia = "SELECT eventstatus FROM sa_cns_asphyxia WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListAsphyxia = inicuDao.getListFromNativeQuery(queryEventStatusAsphyxia);
				String statusAsphyxia = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListAsphyxia)) {
					statusAsphyxia = (String) eventStatusListAsphyxia.get(eventStatusListAsphyxia.size() - 1);
				}

				if (!statusAsphyxia.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesAsphyxia(inicuDao, uhid, "","");
					if (!BasicUtils.isEmpty(notes)) {
						asphyxiaNotes += notes;
					}

					if (statusAsphyxia.equalsIgnoreCase("yes")) {
						asphyxiaNotes += "Baby Continues to have Asphyxia. ";
					}

					if (statusAsphyxia.equalsIgnoreCase("no")) {
						asphyxiaNotes += "Asphyxia is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsAsphyxia> oldAsphyxiaList = new ArrayList<SaCnsAsphyxia>();
					oldAsphyxiaList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveAsphyxiaList(uhid));

					if (!BasicUtils.isEmpty(oldAsphyxiaList)) {
						asphyxiaNotes += oldAsphyxiaList.get(oldAsphyxiaList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsAsphyxiaNotes() != null && dischargeNotes.getIsAsphyxiaNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getAsphyxiaNotes())) {
					prevAsphyxiaNotes = dischargeNotes.getAsphyxiaNotes();
					if (!BasicUtils.isEmpty(prevAsphyxiaNotes)) {
						dischargeNotes.setAsphyxiaNotes(prevAsphyxiaNotes);
					}
				}

				if (!BasicUtils.isEmpty(asphyxiaNotes)) {
					dischargeNotes.setProvisionalNotesAsphyxia(asphyxiaNotes);
				}

				// Seizure Notes
				String seizureNotes, prevSeizureNotes;
				seizureNotes = prevSeizureNotes = "";
				String queryEventStatusSeizure = "SELECT eventstatus FROM sa_cns_seizures WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListSeizure = inicuDao.getListFromNativeQuery(queryEventStatusSeizure);
				String statusSeizure = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListSeizure)) {
					statusSeizure = (String) eventStatusListSeizure.get(eventStatusListSeizure.size() - 1);
				}

				if (!statusSeizure.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesSeizure(inicuDao, uhid, "","");
					if (!BasicUtils.isEmpty(notes)) {
						seizureNotes += notes;
					}

					if (statusSeizure.equalsIgnoreCase("yes")) {
						seizureNotes += "Baby Continues to have Seizure. ";
					}

					if (statusSeizure.equalsIgnoreCase("no")) {
						seizureNotes += "Seizure is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsSeizures> oldSeizureList = new ArrayList<SaCnsSeizures>();
					oldSeizureList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveSeizuresList(uhid));

					if (!BasicUtils.isEmpty(oldSeizureList)) {
						seizureNotes += oldSeizureList.get(oldSeizureList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsSeizureNotes() != null && dischargeNotes.getIsSeizureNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getSeizureNotes())) {
					prevSeizureNotes = dischargeNotes.getSeizureNotes();
					if (!BasicUtils.isEmpty(prevSeizureNotes)) {
						dischargeNotes.setSeizureNotes(prevSeizureNotes);
					}
				}

				if (!BasicUtils.isEmpty(seizureNotes)) {
					dischargeNotes.setProvisionalNotesSeizure(seizureNotes);
				}

				// Sepsis Notes
				String sepsisNotes, prevSepsisNotes;
				sepsisNotes = prevSepsisNotes = "";
				String queryEventStatusSepsis = "SELECT eventstatus FROM sa_infection_sepsis WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListSepsis = inicuDao.getListFromNativeQuery(queryEventStatusSepsis);
				String statusSepsis = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListSepsis)) {
					statusSepsis = (String) eventStatusListSepsis.get(eventStatusListSepsis.size() - 1);
				}

				if (!statusSepsis.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesSepsis(inicuDao, uhid, "", new Timestamp(System.currentTimeMillis()), "");
					if (!BasicUtils.isEmpty(notes)) {
						sepsisNotes += notes;
					}

					if (statusSepsis.equalsIgnoreCase("yes")) {
						sepsisNotes += "Baby continues to have Sepsis. ";
					}

					if (statusSepsis.equalsIgnoreCase("no")) {
						sepsisNotes += "Sepsis is settled but baby is still under Observation. ";
					}
				} else {
					List<SaSepsis> oldSepsisList = new ArrayList<SaSepsis>();
					oldSepsisList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveSepsisList(uhid));

					if (!BasicUtils.isEmpty(oldSepsisList)) {
						sepsisNotes += oldSepsisList.get(oldSepsisList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsSepsisNotes() != null && dischargeNotes.getIsSepsisNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getSepsisNotes())) {
					prevSepsisNotes = dischargeNotes.getSepsisNotes();
					if (!BasicUtils.isEmpty(prevSepsisNotes)) {
						dischargeNotes.setSepsisNotes(prevSepsisNotes);
					}
				}

				if (!BasicUtils.isEmpty(sepsisNotes)) {
					dischargeNotes.setProvisionalNotesSepsis(sepsisNotes);
				}

				// NEC Notes
				String necNotes, prevNecNotes;
				necNotes = prevNecNotes = "";
				String queryEventStatusNec = "SELECT eventstatus FROM sa_infection_nec WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListNec = inicuDao.getListFromNativeQuery(queryEventStatusNec);
				String statusNec = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListNec)) {
					statusNec = (String) eventStatusListNec.get(eventStatusListNec.size() - 1);
				}

				if (!statusNec.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesNec(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						necNotes += notes;
					}

					if (statusNec.equalsIgnoreCase("yes")) {
						necNotes += "Baby continues to have NEC. ";
					}

					if (statusNec.equalsIgnoreCase("no")) {
						necNotes += "NEC is settled but baby is still under Observation. ";
					}
				} else {
					List<SaNec> oldNecList = new ArrayList<SaNec>();
					oldNecList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveNecList(uhid));

					if (!BasicUtils.isEmpty(oldNecList)) {
						necNotes += oldNecList.get(oldNecList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsNecNotes() != null && dischargeNotes.getIsNecNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getNecNotes())) {
					prevNecNotes = dischargeNotes.getNecNotes();
					if (!BasicUtils.isEmpty(prevNecNotes)) {
						dischargeNotes.setNecNotes(prevNecNotes);
					}
				}

				if (!BasicUtils.isEmpty(necNotes)) {
					dischargeNotes.setProvisionalNotesNec(necNotes);
				}

				// Miscellaneous Notes
				String miscellaneousNotes, prevMiscellaneousNotes;
				miscellaneousNotes = prevMiscellaneousNotes = "";
				String queryEventStatusMiscellaneous = "SELECT miscellaneousstatus FROM sa_miscellaneous WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListMiscellaneous = inicuDao
						.getListFromNativeQuery(queryEventStatusMiscellaneous);
				String statusMiscellaneous = "inactive";
				if (!BasicUtils.isEmpty(eventStatusListMiscellaneous)) {
					statusMiscellaneous = (String) eventStatusListMiscellaneous
							.get(eventStatusListMiscellaneous.size() - 1);
				}

				if (!statusMiscellaneous.equalsIgnoreCase("inactive")) {
					String notes = sysObj.generateInactiveNotesMiscellaneous(inicuDao, uhid);
					if (!BasicUtils.isEmpty(notes)) {
						miscellaneousNotes += notes;
					}
				} else {
					List<SaMiscellaneous> oldMiscellaneousList = new ArrayList<SaMiscellaneous>();
					oldMiscellaneousList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldMiscellaneousList(uhid));

					if (!BasicUtils.isEmpty(oldMiscellaneousList)) {
						miscellaneousNotes += oldMiscellaneousList.get(oldMiscellaneousList.size() - 1).getComment();
					}
				}

				if (dischargeNotes.getIsMiscellaneousNotes() != null && dischargeNotes.getIsMiscellaneousNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getMiscellaneousNotes())) {
					prevMiscellaneousNotes = dischargeNotes.getMiscellaneousNotes();
					if (!BasicUtils.isEmpty(prevMiscellaneousNotes)) {
						dischargeNotes.setMiscellaneousNotes(prevMiscellaneousNotes);
					}
				}

				if (!BasicUtils.isEmpty(miscellaneousNotes)) {
					dischargeNotes.setProvisionalNotesMiscellaneous(miscellaneousNotes);
				}

				/**
				 * Purpose: Hospital Course for VAP , Clabsi , IntraUterine Infection , Renal ,
				 * Hypoglycemia , IVH , Hydrocephalus Neuromuscular Disorder , encephalopathy
				 * 
				 * @Updated on:
				 * @author:Ekpreet Kaur
				 * 
				 */

				// VAP Notes
				String vapNotes, prevVapNotes;
				vapNotes = prevVapNotes = "";
				String queryEventStatusVap = "SELECT eventstatus FROM sa_infection_vap WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListVap = inicuDao.getListFromNativeQuery(queryEventStatusVap);
				String statusVap = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListVap)) {
					statusVap = (String) eventStatusListVap.get(eventStatusListVap.size() - 1);
				}

				if (!statusVap.equalsIgnoreCase("Inactive")) {
					// add this when inactiveNotes of vap are ready
					String notes = sysObj.generateInactiveNotesVap(inicuDao, uhid, "");

					// remove this when inactiveNotes of vap are ready
//					String notes = "";
					if (!BasicUtils.isEmpty(notes)) {
						vapNotes += notes;
					}

					if (statusSeizure.equalsIgnoreCase("yes")) {
						vapNotes += "Baby Continues to have Vap. ";
					}

					if (statusSeizure.equalsIgnoreCase("no")) {
						vapNotes += "Vap is settled but baby is still under Observation. ";
					}
				} else {
					List<SaInfectVap> oldVapList = new ArrayList<SaInfectVap>();
					oldVapList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveVapList(uhid));

					if (!BasicUtils.isEmpty(oldVapList)) {
						vapNotes += oldVapList.get(oldVapList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsVapNotes() != null && dischargeNotes.getIsVapNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getVapNotes())) {
					prevVapNotes = dischargeNotes.getVapNotes();
					if (!BasicUtils.isEmpty(prevVapNotes)) {
						dischargeNotes.setVapNotes(prevVapNotes);
					}
				}

				if (!BasicUtils.isEmpty(vapNotes)) {
					dischargeNotes.setProvisionalNotesVap(vapNotes);
				}

				// CLABSI Notes
				String clabsiNotes, prevClabsiNotes;
				clabsiNotes = prevClabsiNotes = "";
				String queryEventStatusClabsi = "SELECT eventstatus FROM sa_infection_clabsi WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListClabsi = inicuDao.getListFromNativeQuery(queryEventStatusClabsi);
				String statusClabsi = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListClabsi)) {
					statusClabsi = (String) eventStatusListClabsi.get(eventStatusListClabsi.size() - 1);
				}

				if (!statusClabsi.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesClabsi(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						clabsiNotes += notes;
					}

					if (statusClabsi.equalsIgnoreCase("yes")) {
						clabsiNotes += "Baby continues to have Clabsi. ";
					}

					if (statusClabsi.equalsIgnoreCase("no")) {
						clabsiNotes += "Clabsi is settled but baby is still under Observation. ";
					}
				} else {
					List<SaInfectClabsi> oldClabsiList = new ArrayList<SaInfectClabsi>();
					oldClabsiList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveClabsiList(uhid));
					if (!BasicUtils.isEmpty(oldClabsiList)) {
						clabsiNotes += oldClabsiList.get(oldClabsiList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsClabsiNotes() != null && dischargeNotes.getIsClabsiNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getClabsiNotes())) {
					prevClabsiNotes = dischargeNotes.getClabsiNotes();
					if (!BasicUtils.isEmpty(prevClabsiNotes)) {
						dischargeNotes.setClabsiNotes(prevClabsiNotes);

					}
				}

				if (!BasicUtils.isEmpty(clabsiNotes)) {
					dischargeNotes.setProvisionalNotesClabsi(clabsiNotes);
				}

				// Intrauterine Infection Notes
				String intrauterineNotes, previousIntrauterineNotes;
				intrauterineNotes = previousIntrauterineNotes = "";
				String queryEventStatusIntrauterine = "SELECT eventstatus FROM sa_infection_intrauterine WHERE uhid='"
						+ uhid + "' order by timeofassessment asc";
				List<String> eventStatusListIntrauterine = inicuDao
						.getListFromNativeQuery(queryEventStatusIntrauterine);
				String statusIntrauterine = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListIntrauterine)) {
					statusIntrauterine = (String) eventStatusListIntrauterine
							.get(eventStatusListIntrauterine.size() - 1);
				}

				if (!statusIntrauterine.equalsIgnoreCase("Inactive")) {
//					String notes = "";
					String notes = sysObj.generateInactiveNotesIntrauterine(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						intrauterineNotes += notes;
					}

					if (statusIntrauterine.equalsIgnoreCase("yes")) {
						intrauterineNotes += ".Baby continues to have Intrauterine Infection. ";
					}

					if (statusIntrauterine.equalsIgnoreCase("no")) {
						intrauterineNotes += "Intrauterine Infection is settled but baby is still under Observation. ";
					}
				} else {
					List<SaInfectIntrauterine> oldIntrauterineList = new ArrayList<SaInfectIntrauterine>();
					oldIntrauterineList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveIntrauterineList(uhid));

					if (!BasicUtils.isEmpty(oldIntrauterineList)) {
						intrauterineNotes += oldIntrauterineList.get(oldIntrauterineList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsIntrauterineNotes() != null && dischargeNotes.getIsIntrauterineNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getIntrauterineNotes())) {
					previousIntrauterineNotes = dischargeNotes.getIntrauterineNotes();
					if (!BasicUtils.isEmpty(previousIntrauterineNotes)) {
						dischargeNotes.setIntrauterineNotes(previousIntrauterineNotes);
					}
				}

				if (!BasicUtils.isEmpty(intrauterineNotes)) {
					dischargeNotes.setProvisionalNotesIntrauterine(intrauterineNotes);
				}

				// Encephalopathy Notes
				String encephalopthyNotes, prevEncephalopathyNotes;
				encephalopthyNotes = prevEncephalopathyNotes = "";
				String queryEventStatusEncephalepathy = "SELECT eventstatus FROM sa_cns_encephalopathy WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListEncephalepathy = inicuDao
						.getListFromNativeQuery(queryEventStatusEncephalepathy);
				String statusEncephalepathy = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListEncephalepathy)) {
					statusEncephalepathy = (String) eventStatusListEncephalepathy
							.get(eventStatusListEncephalepathy.size() - 1);
				}

				if (!statusEncephalepathy.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesEncephalopathy(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						encephalopthyNotes += notes;
					}

					if (statusEncephalepathy.equalsIgnoreCase("yes")) {
						encephalopthyNotes += "Baby continues to have Encephalopathy. ";
					}

					if (statusEncephalepathy.equalsIgnoreCase("no")) {
						encephalopthyNotes += "Encephalopathy is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsEncephalopathy> oldEncephalopathyList = new ArrayList<SaCnsEncephalopathy>();
					oldEncephalopathyList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveEncephalopathyList(uhid));

					if (!BasicUtils.isEmpty(oldEncephalopathyList)) {
						encephalopthyNotes += oldEncephalopathyList.get(oldEncephalopathyList.size() - 1)
								.getProgressnotes();
					}
				}

				if (dischargeNotes.getIsEncephalopathyNotes() != null && dischargeNotes.getIsEncephalopathyNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getIsEncephalopathyNotes())) {
					prevEncephalopathyNotes = dischargeNotes.getEncephalopathyNotes();
					if (!BasicUtils.isEmpty(prevEncephalopathyNotes)) {
						dischargeNotes.setEncephalopathyNotes(prevEncephalopathyNotes);

					}
				}

				if (!BasicUtils.isEmpty(encephalopthyNotes)) {
					dischargeNotes.setProvisionalNotesEncephalopathy(encephalopthyNotes);
				}

				// Neuromuscular Disorder Notes
				String neuromuscularNotes, prevNeuromuscularNotes;
				neuromuscularNotes = prevNeuromuscularNotes = "";
				String queryEventStatusNeuromuscular = "SELECT eventstatus FROM sa_cns_neuromuscular_disorder WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListNeuromuscular = inicuDao
						.getListFromNativeQuery(queryEventStatusNeuromuscular);
				String statusNeuromuscular = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListNeuromuscular)) {
					statusNeuromuscular = (String) eventStatusListNeuromuscular
							.get(eventStatusListNeuromuscular.size() - 1);
				}

				if (!statusNeuromuscular.equalsIgnoreCase("Inactive")) {

					String notes = sysObj.generateInactiveNotesNeuromuscularDisorder(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						neuromuscularNotes += notes;
					}

					if (statusNeuromuscular.equalsIgnoreCase("yes")) {
						neuromuscularNotes += "Baby continues to have Neuromuscular Disorder. ";
					}

					if (statusNeuromuscular.equalsIgnoreCase("no")) {
						neuromuscularNotes += "Neuromuscular Disorder is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsNeuromuscularDisorders> oldNeuromuscularList = new ArrayList<SaCnsNeuromuscularDisorders>();
					oldNeuromuscularList = inicuDao.getListFromMappedObjQuery(
							HqlSqlQueryConstants.getOldInactiveNeuromuscularDisorderList(uhid));

					if (!BasicUtils.isEmpty(oldNeuromuscularList)) {
						neuromuscularNotes += oldNeuromuscularList.get(oldNeuromuscularList.size() - 1)
								.getProgressnotes();
					}
				}

				if (dischargeNotes.getIsNeuromuscularDisorderNotes() != null
						&& dischargeNotes.getIsNeuromuscularDisorderNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getNeuromuscularDisorderNotes())) {
					prevNeuromuscularNotes = dischargeNotes.getNeuromuscularDisorderNotes();
					if (!BasicUtils.isEmpty(prevNeuromuscularNotes)) {
						dischargeNotes.setNeuromuscularDisorderNotes(prevNeuromuscularNotes);
					}
				}

				if (!BasicUtils.isEmpty(neuromuscularNotes)) {
					dischargeNotes.setProvisionalNotesNeuromuscularDisorder(neuromuscularNotes);
				}

				// IVH Notes
				String ivhNotes, prevIVHNotes;
				ivhNotes = prevIVHNotes = "";
				String queryEventStatusIVH = "SELECT eventstatus FROM sa_cns_ivh WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListIVH = inicuDao.getListFromNativeQuery(queryEventStatusIVH);
				String statusIVH = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListIVH)) {
					statusIVH = (String) eventStatusListIVH.get(eventStatusListIVH.size() - 1);
				}

				if (!statusIVH.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesIVH(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						ivhNotes += notes;
					}

					if (statusIVH.equalsIgnoreCase("yes")) {
						ivhNotes += "Baby continues to have IVH. ";
					}

					if (statusIVH.equalsIgnoreCase("no")) {
						ivhNotes += "IVH is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsIvh> oldIVHList = new ArrayList<SaCnsIvh>();
					oldIVHList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveIVHList(uhid));

					if (!BasicUtils.isEmpty(oldIVHList)) {
						ivhNotes += oldIVHList.get(oldIVHList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsIVHNotes() != null && dischargeNotes.getIsIVHNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getIvhNotes())) {
					prevIVHNotes = dischargeNotes.getIvhNotes();
					if (!BasicUtils.isEmpty(prevIVHNotes)) {
						dischargeNotes.setIvhNotes(prevIVHNotes);
					}
				}

				if (!BasicUtils.isEmpty(ivhNotes)) {
					dischargeNotes.setProvisionalNotesIvh(ivhNotes);
				}

				// Hydrocephalus Notes
				String hydrocephalusNotes, prevHydrocephalusNotes;
				hydrocephalusNotes = prevHydrocephalusNotes = "";
				String queryEventStatusHydrocephalus = "SELECT eventstatus FROM sa_cns_hydrocephalus WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListHydrocephalus = inicuDao
						.getListFromNativeQuery(queryEventStatusHydrocephalus);
				String statusHydrocephalus = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListHydrocephalus)) {
					statusHydrocephalus = (String) eventStatusListHydrocephalus
							.get(eventStatusListHydrocephalus.size() - 1);
				}

				if (!statusHydrocephalus.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesHydrocephalus(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						hydrocephalusNotes += notes;
					}

					if (statusHydrocephalus.equalsIgnoreCase("yes")) {
						hydrocephalusNotes += "Baby continues to have Hydrocephalus. ";
					}

					if (statusHydrocephalus.equalsIgnoreCase("no")) {
						hydrocephalusNotes += "Hydrocephalus is settled but baby is still under Observation. ";
					}
				} else {
					List<SaCnsHydrocephalus> oldHydrocephalusList = new ArrayList<SaCnsHydrocephalus>();
					oldHydrocephalusList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveHydrocephalusList(uhid));

					if (!BasicUtils.isEmpty(oldHydrocephalusList)) {
						hydrocephalusNotes += oldHydrocephalusList.get(oldHydrocephalusList.size() - 1)
								.getProgressnotes();
					}
				}

				if (dischargeNotes.getIsHydrocephalusNotes() != null && dischargeNotes.getIsHydrocephalusNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getHydrocephalusNotes())) {
					prevHydrocephalusNotes = dischargeNotes.getHydrocephalusNotes();
					if (!BasicUtils.isEmpty(prevHydrocephalusNotes)) {
						dischargeNotes.setHydrocephalusNotes(prevHydrocephalusNotes);
					}
				}

				if (!BasicUtils.isEmpty(hydrocephalusNotes)) {
					dischargeNotes.setProvisionalNotesHydrocephalus(hydrocephalusNotes);
				}

				// Hypoglycemia Notes
				String hypoglycemiaNotes, prevHypoglycemiaNotes;
				hypoglycemiaNotes = prevHypoglycemiaNotes = "";
				String queryEventStatusHypoglycemia = "SELECT hypoglycemia_event FROM sa_hypoglycemia WHERE uhid='"
						+ uhid + "' order by assessment_time asc";
				List<String> eventStatusListHypoglycemia = inicuDao
						.getListFromNativeQuery(queryEventStatusHypoglycemia);
				String statusHypoglycemia = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListHypoglycemia)) {
					statusHypoglycemia = (String) eventStatusListHypoglycemia
							.get(eventStatusListHypoglycemia.size() - 1);
				}

				if (!statusHypoglycemia.equalsIgnoreCase("Inactive")) {
					String notes = metabolicServiceImpl.generateInactiveNotesHypoglycemia(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						hypoglycemiaNotes += notes;
					}

					if (statusHypoglycemia.equalsIgnoreCase("yes")) {
						hypoglycemiaNotes += "Baby continues to have Hypoglycemia. ";
					}

					if (statusHypoglycemia.equalsIgnoreCase("no")) {
						hypoglycemiaNotes += "Hypoglycemia is settled but baby is still under Observation. ";
					}
				} else {
					List<SaHypoglycemia> oldHypoglycemiaList = new ArrayList<SaHypoglycemia>();
					oldHypoglycemiaList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveHypoglycemiaList(uhid));

					if (!BasicUtils.isEmpty(oldHypoglycemiaList)) {
						hypoglycemiaNotes += oldHypoglycemiaList.get(oldHypoglycemiaList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsHypoglycemiaNotes() != null && dischargeNotes.getIsHypoglycemiaNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getHypoglycemiaNotes())) {
					prevHypoglycemiaNotes = dischargeNotes.getHypoglycemiaNotes();
					if (!BasicUtils.isEmpty(prevHypoglycemiaNotes)) {
						dischargeNotes.setHypoglycemiaNotes(prevHypoglycemiaNotes);

					}
				}

				if (!BasicUtils.isEmpty(hypoglycemiaNotes)) {
					dischargeNotes.setProvisionalNoteshypoglycemia(hypoglycemiaNotes);
				}

				// Renal Notes
				String renalNotes, prevRenalNotes;
				renalNotes = prevRenalNotes = "";
				String queryEventStatusRenal = "SELECT renal_status FROM sa_renalfailure WHERE uhid='" + uhid
						+ "' order by assessment_time asc";
				List<String> eventStatusListRenal = inicuDao.getListFromNativeQuery(queryEventStatusRenal);
				String statusRenal = "Inactive";
				if (!BasicUtils.isEmpty(eventStatusListRenal)) {
					statusRenal = (String) eventStatusListRenal.get(eventStatusListRenal.size() - 1);
				}

				if (!statusRenal.equalsIgnoreCase("Inactive")) {
					String notes = sysObj.generateInactiveNotesRenal(inicuDao, uhid, "");
					if (!BasicUtils.isEmpty(notes)) {
						renalNotes += notes;
					}

					if (statusRenal.equalsIgnoreCase("yes")) {
						renalNotes += "Baby continues to have Renal Failure. ";
					}

					if (statusRenal.equalsIgnoreCase("no")) {
						renalNotes += "Renal Failure is settled but baby is still under Observation. ";
					}
				} else {
					List<SaInfectClabsi> oldRenalList = new ArrayList<SaInfectClabsi>();
					oldRenalList = inicuDao
							.getListFromMappedObjQuery(HqlSqlQueryConstants.getOldInactiveRenalList(uhid));

					if (!BasicUtils.isEmpty(oldRenalList)) {
						renalNotes += oldRenalList.get(oldRenalList.size() - 1).getProgressnotes();
					}
				}

				if (dischargeNotes.getIsRenalNotes() != null && dischargeNotes.getIsRenalNotes()
						&& !BasicUtils.isEmpty(dischargeNotes.getRenalNotes())) {
					prevRenalNotes = dischargeNotes.getRenalNotes();
					if (!BasicUtils.isEmpty(prevRenalNotes)) {
						dischargeNotes.setRenalNotes(prevRenalNotes);
					}
				}

				if (!BasicUtils.isEmpty(renalNotes)) {
					dischargeNotes.setProvisionalNotesRenal(renalNotes);
				}

				// Stable Notes
//				stableNotes += getStableNotes(uhid);
				if (BasicUtils.isEmpty(rdsNotes) && BasicUtils.isEmpty(prevRdsNotes) && BasicUtils.isEmpty(apneaNotes)
						&& BasicUtils.isEmpty(prevApneaNotes) && BasicUtils.isEmpty(pphnNotes)
						&& BasicUtils.isEmpty(prevPphnNotes) && BasicUtils.isEmpty(pneumothoraxNotes)
						&& BasicUtils.isEmpty(prevPneumothoraxNotes) && BasicUtils.isEmpty(jaundiceNotes)
						&& BasicUtils.isEmpty(prevJaundiceNotes) && BasicUtils.isEmpty(asphyxiaNotes)
						&& BasicUtils.isEmpty(prevAsphyxiaNotes) && BasicUtils.isEmpty(seizureNotes)
						&& BasicUtils.isEmpty(prevSeizureNotes) && BasicUtils.isEmpty(clabsiNotes)
						&& BasicUtils.isEmpty(prevClabsiNotes) && BasicUtils.isEmpty(miscellaneousNotes)
						&& BasicUtils.isEmpty(prevMiscellaneousNotes)) {
					String stableNotes = "Baby remains stable during the nicu stay. ";
					dischargeNotes.setStableNotes(stableNotes);
				}

				// Procedure Details Fetching Function
				String procedureStr = "";
				procedureStr += getProcedureDetails(uhid);
				if (dischargeNotes.getIsProcedure() != null && dischargeNotes.getIsProcedure()
						&& !BasicUtils.isEmpty(dischargeNotes.getProcedure())) {
					String prevNotes = dischargeNotes.getProcedure();
					if (!BasicUtils.isEmpty(prevNotes)) {
						dischargeNotes.setProcedure(prevNotes);
					}
				}

				if (!BasicUtils.isEmpty(procedureStr)) {
					dischargeNotes.setCurrProcedure(procedureStr);
				}

				// Blood Product Notes
				String bloodProductStr = "";
				bloodProductStr += getBloodProductDetails(uhid);
				if (dischargeNotes.getIsBloodProduct() != null && dischargeNotes.getIsBloodProduct()
						&& !BasicUtils.isEmpty(dischargeNotes.getBloodProduct())) {
					String prevNotes = dischargeNotes.getBloodProduct();
					if (!BasicUtils.isEmpty(prevNotes)) {
						dischargeNotes.setBloodProduct(prevNotes);
					}
				}

				if (!BasicUtils.isEmpty(bloodProductStr)) {
					dischargeNotes.setCurrBloodProduct(bloodProductStr);
				}

				// Screening Notes
				List<ScreenNeurological> neurologicalList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningNeurologicalList(uhid));
                if(!BasicUtils.isEmpty(neurologicalList))
                {

                    for(int i = 0;i<neurologicalList.size();i++) {
                        if(neurologicalList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(neurologicalList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            neurologicalList.get(i).setScreening_message("Screening was done on " + date1 + " (CGA : "
                                    + neurologicalList.get(i).getCga_weeks() + " weeks " + neurologicalList.get(i).getCga_days() + " days and PNA : " + neurologicalList.get(i).getPna_days() + " days)");
                        }

                        if(neurologicalList.get(i).getReports()!=null)
                            neurologicalList.get(i).setReports("Reports - " + neurologicalList.get(i).getReports());

                        if(neurologicalList.get(i).getOther_comments()!=null)
                            neurologicalList.get(i).setOther_comments("Comments - " + neurologicalList.get(i).getOther_comments());
                    }
                }

				dischargedSummaryObj.setScreenNeurological(neurologicalList);

				List<ScreenHearing> hearingList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningHearingList(uhid));
                if(!BasicUtils.isEmpty(hearingList)) {
                    String oae = "";
                    String abr = "";
                    String hearingRiskFactors = "";
                    for(int i = 0; i <hearingList.size();i++) {

                        if(hearingList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(hearingList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            hearingList.get(i).setScreening_message("Screening was done on " + date1 + " (CGA : "
                                    + hearingList.get(i).getCga_weeks() + " weeks " + hearingList.get(i).getCga_days() + " days and PNA : " + hearingList.get(i).getPna_days() + " days)");
                        }

						if(hearingList.get(i).getRisk_factor()!=null && hearingList.get(i).getRisk_factor()!="") {
							hearingRiskFactors = "High risk factors were " + hearingList.get(i).getRisk_factor() + "." + htmlNextLine;
						}

                        if(hearingList.get(i).getOae_left()!=null && hearingList.get(i).getOae_left()!="") {
                            oae = "Left Ear: " + hearingList.get(i).getOae_left() + htmlNextLine;
                        }
                        if(hearingList.get(i).getOae_right()!=null && hearingList.get(i).getOae_right()!="") {
                            oae+= "Right Ear: " + hearingList.get(i).getOae_right();
                        }
                        hearingList.get(i).setOae(oae);

                        if(hearingList.get(i).getAbr_left()!=null && hearingList.get(i).getAbr_left()!="") {
                            abr = "Left Ear: " + hearingList.get(i).getAbr_left() +  htmlNextLine;
                        }
                        if(hearingList.get(i).getAbr_right()!=null && hearingList.get(i).getAbr_right()!="") {
                            abr += "Right Ear: " +  hearingList.get(i).getAbr_right();
                        }

                        hearingList.get(i).setAbr(abr);

                        if(hearingList.get(i).getTreatment()!=null && hearingList.get(i).getTreatment()!="") {
                            hearingList.get(i).setTreatment("Treatment - " + hearingList.get(i).getTreatment());
                        }
                    }
                }

				dischargedSummaryObj.setScreenHearing(hearingList);

				List<ScreenRop> ropList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningRopList(uhid));
                if(!BasicUtils.isEmpty(ropList)) {
                    String findings = "";
                    String laser = "";
                    for(int i =0; i<ropList.size();i++) {

                        if(ropList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(ropList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            ropList.get(i).setScreening_message("ROP was done on " + date1 + " (CGA : "
                                    + ropList.get(i).getCga_weeks() + " weeks " + ropList.get(i).getCga_days() + " days and PNA : " + ropList.get(i).getPna_days() + " days)");
                        }

                        if(ropList.get(i).getIs_rop() == null || ropList.get(i).getIs_rop() == false) {
                            findings = "Finding - No ROP";
                        }
                        if(ropList.get(i).getIs_rop()!=null && ropList.get(i).getIs_rop()==true) {
                            findings = "Finding - " + htmlNextLine;
                            if(ropList.get(i).getIs_rop_left()!=null && ropList.get(i).getIs_rop_left()==true) {
                                findings += "Left Eye: ";
                                if(ropList.get(i).getRop_left_zone()!=null)
                                    findings +=  ropList.get(i).getRop_left_zone() + " zone, ";
                                if(ropList.get(i).getRop_left_stage()!=null)
                                    findings+= ropList.get(i).getRop_left_stage() + " stage, ";
                            }
                            if(ropList.get(i).getRop_left_plus()!=null && ropList.get(i).getRop_left_plus() == true) {
                                findings+= "Plus disease: Present";
                            }
                            if(ropList.get(i).getRop_left_plus()!=null && ropList.get(i).getRop_left_plus() == false) {
                                findings+= "Plus disease: Absent";
                            }

                            if(ropList.get(i).getIs_rop_right()!=null && ropList.get(i).getIs_rop_right()==true) {
                                findings += "Right Eye: ";
                                if(ropList.get(i).getRop_right_zone()!=null) {
                                    findings += ropList.get(i).getRop_right_zone() + " zone, " ;
                                }

                                if(ropList.get(i).getRop_right_stage()!=null) {
                                    findings += ropList.get(i).getRop_right_stage() + " stage, " ;
                                }

                            }
                            if(ropList.get(i).getRop_right_plus()!=null && ropList.get(i).getRop_right_plus() == true) {

                                findings+= "Plus disease: Present";
                            }
                            if(ropList.get(i).getRop_right_plus()!=null && ropList.get(i).getRop_right_plus() == false) {
                                findings+= "Plus disease: Absent";
                            }
                            ropList.get(i).setFindings_message(findings);

                        }
                    }
                }
				dischargedSummaryObj.setScreenRop(ropList);

				List<ScreenUSG> usgList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningUSGList(uhid));
                if(!BasicUtils.isEmpty(usgList)) {
                    String usgNotes = "";
                    for(int i = 0; i<usgList.size(); i++) {
                        if(usgList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(usgList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            usgList.get(i).setScreening_message("Cranial USG Screening was done on " + date1 + " (CGA : "
                                    + usgList.get(i).getCga_weeks() + " weeks " + usgList.get(i).getCga_days() + " days and PNA : " + usgList.get(i).getPna_days() + " days)");
                        }

						if(usgList.get(i).getCurrent_findings()!=null) {
							usgList.get(i).setCurrent_findings("Findings - " + usgList.get(i).getCurrent_findings());
						}
                    }
                }
				dischargedSummaryObj.setScreenUsg(usgList);

				List<ScreenMetabolic> metabolicList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningMetabolicList(uhid));
                if(!BasicUtils.isEmpty(metabolicList)) {
                    for(int i = 0; i<metabolicList.size();i++) {
                        if(metabolicList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(metabolicList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            metabolicList.get(i).setScreening_message("Screening was done on " + date1 + " (CGA : "
                                    + metabolicList.get(i).getCga_weeks() + " weeks " + metabolicList.get(i).getCga_days() + " days and PNA : " + metabolicList.get(i).getPna_days() + " days)");
                        }
                        if(metabolicList.get(i).getMetabolic_screening()!=null) {
                            metabolicList.get(i).setMetabolic_screening("Metabolic screening of 8 tests was done - " + metabolicList.get(i).getMetabolic_screening() + htmlNextLine);
                        }
                        if(metabolicList.get(i).getScreening_panel()!=null && metabolicList.get(i).getScreening_panel() == true) {
                            metabolicList.get(i).setScreening_panel_message("Metabolic Screening of 123 conditions");
                        }
                    }
                }
				dischargedSummaryObj.setScreenMetabolical(metabolicList);
				
				List<ScreenMiscellaneous> MiscList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getScreeningMiscellaneousList(uhid));
                if(!BasicUtils.isEmpty(MiscList)) {
                    for(int i = 0; i < MiscList.size(); i++) {
                        if(MiscList.get(i).getScreening_time()!=null) {

                            java.sql.Date date = new java.sql.Date(MiscList.get(i).getScreening_time().getTime());
                            SimpleDateFormat sdf_formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String date1 = sdf_formatter.format(date);
                            System.out.println(date1);
                            MiscList.get(i).setScreening_message("Miscellaneous Screening was done on " + date1 + " (CGA : "
                                    + MiscList.get(i).getCga_weeks() + " weeks " + MiscList.get(i).getCga_days() + " days and PNA : " + MiscList.get(i).getPna_days() + " days)");
                        }
                        if(MiscList.get(i).getScreeningType()!=null) {
                            MiscList.get(i).setScreeningType("Screening investigation - " + MiscList.get(i).getScreeningType() + htmlNextLine);
                        }

                        if(MiscList.get(i).getCurrent_findings()!=null) {
                            MiscList.get(i).setCurrent_findings("Findings - " + MiscList.get(i).getCurrent_findings() + htmlNextLine);
                        }
                    }
                }
				dischargedSummaryObj.setScreenMisc(MiscList);

				// hiding update feature of screening on page 2 of discharge summary.

//				String screeningStr = "";				
//				String neuroScreeningStr = getNeuroScreeningDetail(uhid);
//				if(!BasicUtils.isEmpty(neuroScreeningStr)) {
//					screeningStr += "Neurological Screening : " + htmlNextLine + neuroScreeningStr 
//							+ htmlNextLine + htmlNextLine;
//				}
//				
//				String ropScreeningStr = getRopScreeningDetail(uhid);
//				if(!BasicUtils.isEmpty(ropScreeningStr)) {
//					screeningStr += "ROP Screening : " + htmlNextLine + ropScreeningStr 
//							+ htmlNextLine + htmlNextLine;
//				}
//				
//				String hearingScreeningStr = getHearingScreeningDetail(uhid);
//				if(!BasicUtils.isEmpty(hearingScreeningStr)) {
//					screeningStr += "Hearing Screening : " + htmlNextLine + hearingScreeningStr 
//							+ htmlNextLine + htmlNextLine;
//				}
//				
//				String metabolicScreeningStr = getMetabolicScreeningDetail(uhid);
//				if(!BasicUtils.isEmpty(metabolicScreeningStr)) {
//					screeningStr += "Metabolic Screening : " + htmlNextLine + metabolicScreeningStr 
//							+ htmlNextLine + htmlNextLine;
//				}
//				
//				String cranialScreeningStr = getCranialScreeningDetail(uhid);
//				if(!BasicUtils.isEmpty(cranialScreeningStr)) {
//					screeningStr += "Cranial USG Screening : " + htmlNextLine + cranialScreeningStr 
//							+ htmlNextLine + htmlNextLine;
//				}
//				
//				if(dischargeNotes.getIsScreening() != null && dischargeNotes.getIsScreening() && !BasicUtils.isEmpty(dischargeNotes.getScreening())) {
//					String prevNotes = dischargeNotes.getScreening();
//					if(!BasicUtils.isEmpty(prevNotes)) {
//						dischargeNotes.setScreening(prevNotes);
//					}
//				}
//				
//				if(!BasicUtils.isEmpty(screeningStr)){
//					dischargeNotes.setCurrScreening(screeningStr);
//				}

				// Nutrition Details Fetching Function
				String nutritionStr = "";
				nutritionStr += getNutritionDetails(uhid);
				if (dischargeNotes.getIsNutrition() != null && dischargeNotes.getIsNutrition()
						&& !BasicUtils.isEmpty(dischargeNotes.getNutrition())) {
					String prevNotes = dischargeNotes.getNutrition();
					if (!BasicUtils.isEmpty(prevNotes)) {
						dischargeNotes.setNutrition(prevNotes);
					}
				}

				if (!BasicUtils.isEmpty(nutritionStr)) {
					dischargeNotes.setCurrNutrition(nutritionStr);
				}

				// Vaccination Details Fetching Function
				String vaccinationStr = "";
				vaccinationStr += getVaccinationDetails(uhid);
				if (dischargeNotes.getIsVaccination() != null && dischargeNotes.getIsVaccination()
						&& !BasicUtils.isEmpty(dischargeNotes.getVaccination())) {
					String prevNotes = dischargeNotes.getVaccination();
					if (!BasicUtils.isEmpty(prevNotes)) {
						dischargeNotes.setVaccination(prevNotes);
					}
				}

				if (!BasicUtils.isEmpty(vaccinationStr)) {
					dischargeNotes.setCurrVaccination(vaccinationStr);
				}

				// Saving Discharge Notes
				dischargedSummaryObj.setDischargeNotes(dischargeNotes);

				// Obstetric Condtiton Fetching Function
				String obstetricCondtitonString = "";
				obstetricCondtitonString += getObstetricCondtiton(
						dischargedSummaryObj.getBabyAntenatalHistoryDetails());
				if (!BasicUtils.isEmpty(obstetricCondtitonString)) {
					dischargedSummaryObj.setObstetricCondtiton(obstetricCondtitonString);
				}

				// Maternal Investigation Fetching Function
				String maternalInvestigationString = "";
				maternalInvestigationString += getMaternalInvestigation(
						dischargedSummaryObj.getBabyAntenatalHistoryDetails());
				if (!BasicUtils.isEmpty(maternalInvestigationString)) {
					dischargedSummaryObj.setMaternalInvestigation(maternalInvestigationString);
				}

				// Antenatal Details
				String queryAnteanalHistory = "select obj from  AntenatalHistoryDetail obj where uhid='" + uhid + "'";
				List<AntenatalHistoryDetail> listAntinatalHistory = inicuDao
						.getListFromMappedObjQuery(queryAnteanalHistory);
				if (!BasicUtils.isEmpty(listAntinatalHistory)) {
					Timestamp edd = listAntinatalHistory.get(0).getEddTimestamp();
					if (!BasicUtils.isEmpty(edd)) {
						dischargedSummaryObj.setEdd(edd);
					}
				}

				// getting Continued Medication List on Discharge
				List<BabyPrescription> prescriptionList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getMedicationListForDischargeSummary(uhid));
				dischargedSummaryObj.setPrescriptionList(prescriptionList);

				// getting Medication List on Discharge
				String medicationQuery = "select medicinename,route,sum((abs(extract(day from coalesce(enddate,'"
						+ dateFormatDB.format(outcomeData.getEntrytime()) + "') - coalesce(startdate,'"
						+ dateFormatDB.format(babyDetails.getDateofadmission())
						+ "'))))),po_type,medicationtype from baby_prescription where uhid='" + uhid + "'"
						+ " group by 1,2,4,5";
				List<Object[]> medicationQueryList = inicuDao.getListFromNativeQuery(medicationQuery);

				String medicineStr = "";
				HashMap<String, Boolean> test = new HashMap<String, Boolean>();
				if (!BasicUtils.isEmpty(medicationQueryList)) {
					for (Object[] medObj : medicationQueryList) {

						if (medObj[1].toString().equals("IV") || medObj[1].toString().equals("IM")) {
							medObj[1] = "IV";
						}

						String key = medObj[0] + " " + medObj[1];
						if (medObj[0] != null) {
							if (medObj[0].toString().toLowerCase().indexOf("loading") != -1) {
								String[] arr = medObj[0].toString().split("\\(");
								medObj[0] = arr[0].toString().trim();
								key = medObj[0] + " " + medObj[1];
							}
							String preFix = "";
							if (medObj[1] != null) {
								if (medObj[1].toString().equals("IV") || medObj[1].toString().equals("IM")) {
									preFix = "Inj. ";
								} else if (medObj[1].toString().equals("Inhaled")) {
									preFix = "Inhaled";
								} else if (medObj[1].toString().equals("PO")) {
									if ((medObj[3] != null) && medObj[3].toString().equals("Syrup"))
										preFix = "Syp. ";
									else if ((medObj[3] != null) && medObj[3].toString().equals("Tablet"))
										preFix = "Tab. ";
								}
							}

							if ((medObj[4] != null) && !medObj[4].toString().equals("TYPE0035")) {
								if (medicineStr.equals("")) {
									medicineStr += preFix + medObj[0];
								} else {
									if (test.containsKey(key.trim())) {
										continue;
									}
									medicineStr += ", " + preFix + medObj[0];
								}
								if (key.toString().indexOf("IM") != -1) {
									medicineStr += "(" + medObj[1] + ")";
								}
								test.put(key, true);
							}
						}
					}
					dischargedSummaryObj.setDischargeMedicationList(medicineStr);
				}

				// Test Result Data Fetching Function
				String reportResult = "No";
				String babyDetailListQuery = "select obj from BabyDetail as obj where uhid='" + uhid + "'";
				List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailListQuery);
				if(babyDetailList != null && babyDetailList.size() > 0) {
					String branchName1 = babyDetailList.get(0).getBranchname();
					if(branchName1 != null && branchName1 != "") {
						String refHospitalbranchnameQuery = "select obj from RefHospitalbranchname as obj where branchname='" + branchName1 + "'";
						List<RefHospitalbranchname> refHospitalbranchnameList = inicuDao.getListFromMappedObjQuery(refHospitalbranchnameQuery);
						if(refHospitalbranchnameList != null && refHospitalbranchnameList.size() > 0) {
							if(!BasicUtils.isEmpty(refHospitalbranchnameList.get(0).getReportPrint())){
								reportResult = refHospitalbranchnameList.get(0).getReportPrint();
							}
						}
					}
				}
				List<TestResultsViewPOJO> testResultList = new ArrayList<TestResultsViewPOJO>();

				if(reportResult.equalsIgnoreCase("Yes")) {
					Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));
					Timestamp toDate = new Timestamp(Long.parseLong(toTime));
					TreeMap<String,List<TestItemResult>> testResultData = new TreeMap<>();
					TreeMap<String,List<String>> dataItems = new TreeMap<>();
	
					String queryTestResult = "select obj from TestItemResult as obj where prn='"
							+ uhid + "' and resultdate >= '" + fromDate + "' and resultdate <= '" + toDate + "' order by resultdate";
					List<TestItemResult> allTestResultList = inicuDao.getListFromMappedObjQuery(queryTestResult);	
					if(!BasicUtils.isEmpty(allTestResultList)){
						for(int i=0;i<allTestResultList.size();i++) {
							TestItemResult testItemResultObj = (TestItemResult)allTestResultList.get(i);
							if(testItemResultObj != null) {
								if(testItemResultObj.getResultdate() != null) {
									String testDate = (testItemResultObj.getResultdate()) + "";
									List<TestItemResult> singleDateTestDetail = new ArrayList<>();
									List<String> singleDateItemDetail = new ArrayList<>();
	
									if(testResultData.containsKey(testDate)) {
										singleDateTestDetail = testResultData.get(testDate);
									}
									singleDateTestDetail.add(testItemResultObj);
									testResultData.put(testDate, singleDateTestDetail);
									
									//Getting the unique item ids for every date available
									if(dataItems.containsKey(testDate)) {
										singleDateItemDetail = dataItems.get(testDate);
									}
									singleDateItemDetail.add(testItemResultObj.getItemid());
									dataItems.put(testDate, singleDateItemDetail);
								}
							}
						}
					}
					if(!BasicUtils.isEmpty(dataItems) && !BasicUtils.isEmpty(testResultData))
					{
						//window Size is used to divide the dates so that they could be rendered easily on HTML
						int windowSize = 0;
						//window number used as a key to save dates and items in hashmap
						int windowNumber = 0;
						List<String> itemList = new ArrayList<String>();
						List<String> dateList = new ArrayList<String>();
						HashMap<Integer,List<String>> parentDates = new HashMap<>();
						HashMap<Integer,Set<String>> parentItems = new HashMap<>();
						HashMap<String,TestNamePOJO> finalItemMap = new HashMap<>();
		
						for(String date : dataItems.keySet()) {
							windowSize++;
							List<String> items = dataItems.get(date);
							
							//making a list of items and dates on the basis of group 5
							itemList.addAll(items);
							dateList.add(date);
							TestResultsViewPOJO resultObj = new TestResultsViewPOJO();
							
							//when counting of 5 is completed then processing the result data and refreshing the date and items list created earlier
							if(windowSize == 5) {
								finalItemMap = new HashMap<>();
								windowNumber++;
								Set<String> itemUniqueList = new HashSet<String>(itemList);
		
								parentDates.put(windowNumber, dateList);
								parentItems.put(windowNumber, itemUniqueList);
								resultObj.setDates(dateList);
								for(String dateObj : dateList) {
									TestNamePOJO testname = new TestNamePOJO();
									List<String> itemObj = dataItems.get(dateObj);
									
									//this loop is used to insert empty test result objects in the list of respective dates so that the length of all dates would be same
									for(String item : itemUniqueList) {
										if(!itemObj.contains(item)) {
											TestItemResult newObj = new TestItemResult();
											newObj.setItemid(item);
											List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
											singleDateTestDetail.add(newObj);
											testResultData.put(dateObj, singleDateTestDetail);
										}
									}
									List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
									//this loop is used to divide the results on the basis of item name as the requirement is to show the results on the basis of tests
									for(TestItemResult obj : singleDateTestDetail) {
										if(!BasicUtils.isEmpty(obj.getItemid())) {
											List<TestItemResult> results = new ArrayList<>();
		
											if(finalItemMap.containsKey(obj.getItemid())) {
												testname = finalItemMap.get(obj.getItemid());
												if(!BasicUtils.isEmpty(testname.getItemResults())) {
													results = testname.getItemResults();
													results.add(obj);
												}
												if(BasicUtils.isEmpty(testname.getTestName()) && !BasicUtils.isEmpty(obj.getItemname()))
													testname.setTestName(obj.getItemname());
											}
											else {
												testname = new TestNamePOJO();
												results.add(obj);
												if(!BasicUtils.isEmpty(obj.getItemname()))
													testname.setTestName(obj.getItemname());
											}
											testname.setItemResults(results);
										
											finalItemMap.put(obj.getItemid(), testname);
										}
									}
								}
								List<TestNamePOJO> finalListItems = new ArrayList<>();
								if(!BasicUtils.isEmpty(finalItemMap)) {
									for(String itemId : finalItemMap.keySet()) {
										TestNamePOJO testResultsList = finalItemMap.get(itemId);
										finalListItems.add(testResultsList);
									}
								}
								resultObj.setItemList(finalListItems);
								testResultList.add(resultObj);
								itemList = new ArrayList<String>();
								dateList = new ArrayList<String>();
								finalItemMap = new HashMap<>();
								windowSize = 0;
							}
						}
						//condition to handle the case when the count of the dates is not multiple of 5 and it will handle the left out dates
						if(windowSize < 5 && windowSize > 0) {
							windowNumber++;
							Set<String> itemUniqueList = new HashSet<String>(itemList);
							TestResultsViewPOJO resultObj = new TestResultsViewPOJO();
		
							parentDates.put(windowNumber, dateList);
							parentItems.put(windowNumber, itemUniqueList);
							resultObj.setDates(dateList);
		
							for(String dateObj : dateList) {
								List<String> itemObj = dataItems.get(dateObj);
								for(String item : itemUniqueList) {
									if(!itemObj.contains(item)) {
										TestItemResult newObj = new TestItemResult();
										newObj.setItemid(item);
										List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
										singleDateTestDetail.add(newObj);
										testResultData.put(dateObj, singleDateTestDetail);
									}
								}
								List<TestItemResult> singleDateTestDetail = testResultData.get(dateObj);
								TestNamePOJO testname = new TestNamePOJO();
								for(TestItemResult obj : singleDateTestDetail) {
									if(!BasicUtils.isEmpty(obj.getItemid())) {
										List<TestItemResult> results = new ArrayList<>();
		
										if(finalItemMap.containsKey(obj.getItemid())) {
											testname = finalItemMap.get(obj.getItemid());
											if(!BasicUtils.isEmpty(testname.getItemResults())) {
												results = testname.getItemResults();
												results.add(obj);
											}
											if(BasicUtils.isEmpty(testname.getTestName()) && !BasicUtils.isEmpty(obj.getItemname()))
												testname.setTestName(obj.getItemname());
										}
										else {
											testname = new TestNamePOJO();
											results.add(obj);
											if(!BasicUtils.isEmpty(obj.getItemname()))
												testname.setTestName(obj.getItemname());
										}
										testname.setItemResults(results);
									
										finalItemMap.put(obj.getItemid(), testname);
									}
								}
							}
							List<TestNamePOJO> finalListItems = new ArrayList<>();
							if(!BasicUtils.isEmpty(finalItemMap)) {
								for(String itemId : finalItemMap.keySet()) {
									TestNamePOJO testResultsList = finalItemMap.get(itemId);
									finalListItems.add(testResultsList);
								}
							}
							resultObj.setItemList(finalListItems);
							testResultList.add(resultObj);
						}
					}
				}
				
				if (!BasicUtils.isEmpty(testResultList)) {
					dischargedSummaryObj.setTestResult(testResultList);
				}

				// Removed by ekpreet for now
				// getting device advice details data...
//				String queryAdviceDetails = "select obj from DischargeAdviceDetail obj where uhid='" + uhid + "'";
//				List<DischargeAdviceDetail> listSavedAdvice = inicuDao.getListFromMappedObjQuery(queryAdviceDetails);
//				List<DischargeAdviceDetail> refAdviceList = dischargedSummaryObj.getDischargeAdviceTemplates();
//				for (int i = 0; i < listSavedAdvice.size(); i++) {
//					for (int j = 0; j < refAdviceList.size(); j++) {
//						if (listSavedAdvice.get(i).getAdviceTempId()
//								.equalsIgnoreCase(refAdviceList.get(j).getAdviceTempId())) {
//							refAdviceList.set(j, listSavedAdvice.get(i));
//						}
//					}
//				}
//				dischargedSummaryObj.setDischargeAdviceTemplates(refAdviceList);

				// assessmentMessage if any assessment is still active or passive only for
				// outcome type - Discharge only
				String queryOutcomeTypeList = "select obj from DischargeOutcome obj where uhid = '" + uhid
						+ "' and outcome_type = 'Discharge' ";

				List<DischargeOutcome> outcomeDischargeList = null;

				outcomeDischargeList = inicuDao.getListFromMappedObjQuery(queryOutcomeTypeList);

				if (!BasicUtils.isEmpty(outcomeDischargeList)) {
					String assessmentMessageSql = HqlSqlQueryConstants.getDischargeBabyAssessmentStatus(uhid);
					List<Object[]> activeAssessmentList = inicuDao.getListFromNativeQuery(assessmentMessageSql);
					if (!BasicUtils.isEmpty(activeAssessmentList)) {
						String assessmentMessage = "";
						Iterator<Object[]> itr = activeAssessmentList.iterator();
						while (itr.hasNext()) {
							Object[] obj = itr.next();
							assessmentMessage = assessmentMessage.concat(obj[0] + ", ");
						}
						if (!assessmentMessage.isEmpty()) {
							assessmentMessage = assessmentMessage.substring(0, assessmentMessage.lastIndexOf(","));
							if (assessmentMessage.contains(",")) {
								assessmentMessage += " assessments are active. Please make all these assessments Inactive before discharging the patient.";
							} else {
								assessmentMessage += " assessment is active. Please make it Inactive before discharging the patient.";
							}
							dischargedSummaryObj.setAssessmentMessage(assessmentMessage);
						}
					}
				}
				
				String outcomeNotesQuery = "select obj from OutcomeNotes as obj where uhid = '" + uhid + "' order by creationtime desc";
				List<OutcomeNotes> outcomeNotesList = inicuDao.getListFromMappedObjQuery(outcomeNotesQuery);
				String outcomeNote = "";
				if(!BasicUtils.isEmpty(outcomeNotesList)) {
					OutcomeNotes obj = outcomeNotesList.get(0);
					
					if(dischargedSummaryObj.getOutcome()!=null && dischargedSummaryObj.getOutcome().getOutcomeType().equalsIgnoreCase(obj.getOutcome())) {
						if(obj.getOutcome().equalsIgnoreCase("Death")) {
							outcomeNote = obj.getDeathprogressNotes();
						}
						else {
						outcomeNote = obj.getProgressNote();
						}
					}
				}
				dischargedSummaryObj.setOutcomeNote(outcomeNote);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dischargedSummaryObj;
	}
	
//	public String getSepsisType(String diagnosis,String uhid) {
//		// TODO Auto-generated method stub
//
//
//		double antibioticUsage = 0;
//
//		Timestamp currentDate = null;
//		Timestamp currentDateNew = new Timestamp(new java.util.Date().getTime());
//
//		String medQuery = "select obj from BabyPrescription obj where uhid = '" + uhid + "' and medicationtype='TYPE0001' order by startdate asc";
//
//		List<BabyPrescription> medList = inicuDao.getListFromMappedObjQuery(medQuery);
//
//		for(BabyPrescription obj : medList) {
//
//		if(!BasicUtils.isEmpty(obj.getMedicineOrderDate())) {
//
//
//			if(BasicUtils.isEmpty(obj.getEnddate())){
//				   obj.setEnddate(currentDateNew);
//				}
//
//		if(currentDate == null || (currentDate != null && obj.getStartdate().getTime() > currentDate.getTime())) {
//
//
//
//		currentDate = obj.getEnddate();
//
//
//
//		antibioticUsage += ((obj.getEnddate().getTime() - obj.getMedicineOrderDate().getTime()) / (24 * 60 * 60 * 1000));
//
//		}else if(currentDate != null && obj.getEnddate().getTime() > currentDate.getTime()) {
//
//
//
//		antibioticUsage += ((obj.getEnddate().getTime() - currentDate.getTime()) / ( 60 * 60 * 1000));
//
//		currentDate = obj.getEnddate();
//
//		}
//		}
//
//		}
//
//		//returnObj.setAntibioticDaysSepsis(String.valueOf(antibioticUsage));
//
//		if(antibioticUsage < 120 ) {
//			diagnosis = diagnosis.replace("Sepsis", "Suspected Sepsis");
//		}
//
//		if(antibioticUsage >= 120) {
//			diagnosis = diagnosis.replace("Sepsis", "Clinical Sepsis");
//		}
//			String query1 = "Select obj from SaSepsis as obj where uhid = '" + uhid + "' order by creationtime desc";
//			List<SaSepsis> sepsisList = inicuDao.getListFromMappedObjQuery(query1);
//			if(!BasicUtils.isEmpty(sepsisList) && sepsisList!=null) {
//				if(sepsisList.get(0).getBloodCultureStatus()!=null && sepsisList.get(0).getBloodCultureStatus().equalsIgnoreCase("positive")) {
//					diagnosis = diagnosis.replace("Clinical Sepsis", "Confirmed Sepsis");
//					diagnosis = diagnosis.replace("Suspected Sepsis", "Confirmed Sepsis");
//					diagnosis = diagnosis.replace("Sepsis", "Confirmed Sepsis");
//					diagnosis = diagnosis.replace("Confirmed Confirmed","Confirmed" );
//				}
//			}
//
//		return diagnosis;
//	}
	
	public List<BabyPrescription> getPrescription(String uhid, String prescriptionType) {
		Timestamp dateSql = new Timestamp(new Date().getTime());
		String queryPrescription = "SELECT obj FROM BabyPrescription obj";
		if (prescriptionType.equalsIgnoreCase(BasicConstants.ACTIVE_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and (isactive ='true' or enddate >'"
					+ dateSql + "') order by startdate desc";
		} else if (prescriptionType.equalsIgnoreCase(BasicConstants.PAST_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and (isactive ='false' and enddate <='"
					+ dateSql + "') order by startdate desc";
		} else if (prescriptionType.equalsIgnoreCase(BasicConstants.CURRENT_PRESCRIPTION)) {
			queryPrescription = queryPrescription + " where uhid='" + uhid + "' and startdate='" + dateSql
					+ "' and isactive='true' order by startdate desc";
		}
		return prescriptionDao.getListFromMappedObjNativeQuery(queryPrescription);
	}

	private RespSupport getLastRespSupport(String uhid) {
		RespSupport returnObj = null;
		String fetchRespSupport = "SELECT obj FROM RespSupport as obj where uhid='" + uhid
				+ "'order by modificationtime desc";
		List<RespSupport> respSupportList = inicuDao.getListFromMappedObjQuery(fetchRespSupport);
		if (BasicUtils.isEmpty(respSupportList)) {
			returnObj = new RespSupport();
		} else {
			returnObj = respSupportList.get(0);
			returnObj.setRespsupportid(null);
		}

		String pco2NursingBloodGasQuery = "select pco2, spo2, ph, be, hco2 from nursing_bloodgas where uhid='" + uhid
				+ "' " + " order by creationtime desc limit 1";
		List<Object[]> pco2ValueList = inicuDao.getListFromNativeQuery(pco2NursingBloodGasQuery);
		if (pco2ValueList != null && pco2ValueList.size() > 0) {
			// returnObj.setRspCO2(pco2ValueList.get(0).toString());
			System.out.println(pco2ValueList.get(0));
			Object[] parametesObject = pco2ValueList.get(0);
			if (!BasicUtils.isEmpty(parametesObject[0])) {
				returnObj.setRspCO2(parametesObject[0].toString());
			}
			if (!BasicUtils.isEmpty(parametesObject[1])) {
				returnObj.setRsSpo2(parametesObject[1].toString());
			}
			if (!BasicUtils.isEmpty(parametesObject[2])) {
				returnObj.setph(parametesObject[2].toString());
			}
			if (!BasicUtils.isEmpty(parametesObject[3])) {
				returnObj.setBe(parametesObject[3].toString());
			}
			if (!BasicUtils.isEmpty(parametesObject[4])) {
				returnObj.setHco3(parametesObject[4].toString());
			}
		}
		return returnObj;
	}

	// Obsetretic Condition Fetching Function
	private String getObstetricCondtiton(AntenatalHistoryDetail babyAntenatalHistoryDetails)
			throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String obstetricCondtitonStr = "";
		try {
			if (babyAntenatalHistoryDetails.getFirstSteroidDetail() != null) {
				if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getFirstSteroidDetail().getSteroidname())) {
					String steroidName = babyAntenatalHistoryDetails.getFirstSteroidDetail().getSteroidname();
					if (steroidName.equalsIgnoreCase("Not Known")) {
						obstetricCondtitonStr += "Complete course of antenatal steroid given ";
					} else {
						if (!BasicUtils
								.isEmpty(babyAntenatalHistoryDetails.getFirstSteroidDetail().getNumberofdose())) {
							Integer doseNo = babyAntenatalHistoryDetails.getFirstSteroidDetail().getNumberofdose();
							if (steroidName.equalsIgnoreCase("dexa")) {
								if (doseNo >= 4) {
									obstetricCondtitonStr += "Complete course of antenatal steroid dexa given ";
								} else {
									obstetricCondtitonStr += "Incomplete course of antenatal steroid dexa given ";
								}
							}

						}

						if (!BasicUtils
								.isEmpty(babyAntenatalHistoryDetails.getFirstSteroidDetail().getNumberofdose())) {
							Integer doseNo = babyAntenatalHistoryDetails.getFirstSteroidDetail().getNumberofdose();
							if (steroidName.equalsIgnoreCase("beta")) {
								if (doseNo >= 2) {
									obstetricCondtitonStr += "Complete course of antenatal steroid beta given ";
								} else {
									obstetricCondtitonStr += "Incomplete course of antenatal steroid beta given ";
								}
							}

						}
					}

					if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getFirstSteroidDetail().getGivendate())) {
						obstetricCondtitonStr += "(" + getDateFromTimestamp(
								babyAntenatalHistoryDetails.getFirstSteroidDetail().getGivendate()) + ")";
					}
				}
			}

			if (!BasicUtils.isEmpty(obstetricCondtitonStr)) {
				obstetricCondtitonStr += ". ";
			}
			String antenatalRiskFactorStr = "";
			if (babyAntenatalHistoryDetails.getHypertension() != null
					&& babyAntenatalHistoryDetails.getHypertension() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Hypertension";
				} else {
					antenatalRiskFactorStr += ", Hypertension";
				}
			}
			if (babyAntenatalHistoryDetails.getGestationalHypertension() != null
					&& babyAntenatalHistoryDetails.getGestationalHypertension() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Gestational Hypertension";
				} else {
					antenatalRiskFactorStr += ", Gestational Hypertension";
				}
			}
			if (babyAntenatalHistoryDetails.getDiabetes() != null
					&& babyAntenatalHistoryDetails.getDiabetes() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Diabetes";
				} else {
					antenatalRiskFactorStr += ", Diabetes";
				}
			}
			if (babyAntenatalHistoryDetails.getGdm() != null && babyAntenatalHistoryDetails.getGdm() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "GDM";
				} else {
					antenatalRiskFactorStr += ", GDM";
				}
			}
			if (babyAntenatalHistoryDetails.getChronicKidneyDisease() != null
					&& babyAntenatalHistoryDetails.getChronicKidneyDisease() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Chronic kidney Disease";
				} else {
					antenatalRiskFactorStr += ", Chronic kidney Disease";
				}
			}
			if (babyAntenatalHistoryDetails.getHypothyroidism() != null
					&& babyAntenatalHistoryDetails.getHypothyroidism() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Hypothyroidism";
				} else {
					antenatalRiskFactorStr += ", Hypothyroidism";
				}
			}
			if (babyAntenatalHistoryDetails.getHyperthyroidism() != null
					&& babyAntenatalHistoryDetails.getHyperthyroidism() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Hyperthyroidism";
				} else {
					antenatalRiskFactorStr += ", Hyperthyroidism";
				}
			}
			if (babyAntenatalHistoryDetails.getFever() != null && babyAntenatalHistoryDetails.getFever() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Fever";
				} else {
					antenatalRiskFactorStr += ", Fever";
				}
			}
			if (babyAntenatalHistoryDetails.getUti() != null && babyAntenatalHistoryDetails.getUti() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "UTI";
				} else {
					antenatalRiskFactorStr += ", UTI";
				}
			}
			if (babyAntenatalHistoryDetails.getHistoryOfInfections() != null
					&& babyAntenatalHistoryDetails.getHistoryOfInfections() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "I/U infections";
				} else {
					antenatalRiskFactorStr += ", I/U infections";
				}
				if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getHistoryOfIvInfectionText())) {
					antenatalRiskFactorStr += " (" + babyAntenatalHistoryDetails.getHistoryOfIvInfectionText() + ")";
				}
			}
			if (babyAntenatalHistoryDetails.getProm() != null && babyAntenatalHistoryDetails.getProm() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "PROM";
				} else {
					antenatalRiskFactorStr += ", PROM";
				}
				if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getPromText())) {
					antenatalRiskFactorStr += " (" + babyAntenatalHistoryDetails.getPromText() + ")";
				}
			}
			if (babyAntenatalHistoryDetails.getPprom() != null && babyAntenatalHistoryDetails.getPprom() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "PPROM";
				} else {
					antenatalRiskFactorStr += ", PPROM";
				}
			}
			if (babyAntenatalHistoryDetails.getPrematurity() != null
					&& babyAntenatalHistoryDetails.getPrematurity() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Prematurity";
				} else {
					antenatalRiskFactorStr += ", Prematurity";
				}
			}
			if (babyAntenatalHistoryDetails.getChorioamniotis() != null
					&& babyAntenatalHistoryDetails.getChorioamniotis() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Chorioamnionitis";
				} else {
					antenatalRiskFactorStr += ", Chorioamnionitis";
				}
			}
			if (babyAntenatalHistoryDetails.getOligohydraminos() != null
					&& babyAntenatalHistoryDetails.getOligohydraminos() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Oligohydramnios";
				} else {
					antenatalRiskFactorStr += ", Oligohydramnios";
				}
			}
			if (babyAntenatalHistoryDetails.getPolyhydraminos() != null
					&& babyAntenatalHistoryDetails.getPolyhydraminos() == true) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += "Polyhydramnios";
				} else {
					antenatalRiskFactorStr += ", Polyhydramnios";
				}
			}
			if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getOtherRiskFactors())) {
				if (antenatalRiskFactorStr == "") {
					antenatalRiskFactorStr += babyAntenatalHistoryDetails.getOtherRiskFactors();
				} else {
					antenatalRiskFactorStr += ", " + babyAntenatalHistoryDetails.getOtherRiskFactors();
				}
			}
			if (antenatalRiskFactorStr != "") {
				// template += "Associated maternal risk factors are "+antenatalRiskFactorStr +
				// ".";
				if (antenatalRiskFactorStr.contains(",")) {
					obstetricCondtitonStr += "Antenatal risk factors were " + antenatalRiskFactorStr + ". ";
				} else {
					obstetricCondtitonStr += "Antenatal risk factor was " + antenatalRiskFactorStr + ". ";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obstetricCondtitonStr;
	}

	// Maternal Investigation Fetching Function
	private String getMaternalInvestigation(AntenatalHistoryDetail babyAntenatalHistoryDetails)
			throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String maternalInvestigationFinalStr = "";
		try {
			String maternalInvestigationStr = "";
			if (babyAntenatalHistoryDetails.getIshiv() != null && babyAntenatalHistoryDetails.getIshiv() == true) {
				if (babyAntenatalHistoryDetails.getHivType() != null
						&& babyAntenatalHistoryDetails.getHivType().equalsIgnoreCase("positive")) {
					maternalInvestigationStr += "HIV (positive)";
				}
			}
			if (babyAntenatalHistoryDetails.getIshepb() != null && babyAntenatalHistoryDetails.getIshepb() == true && ((babyAntenatalHistoryDetails.getHbsag()!=null && babyAntenatalHistoryDetails.getHbsag().equalsIgnoreCase("positive") ) || (babyAntenatalHistoryDetails.getHbeag()!=null && babyAntenatalHistoryDetails.getHbeag().equalsIgnoreCase("positive")))) {
				if (maternalInvestigationStr != "") {
					maternalInvestigationStr += ", Hep B ";
					/*
					 * if (babyAntenatalHistoryDetails.getHepbType() != null &&
					 * (babyAntenatalHistoryDetails.getHepbType().equalsIgnoreCase("HBsAg") ||
					 * babyAntenatalHistoryDetails.getHepbType().equalsIgnoreCase("HBeAg"))) {
					 * maternalInvestigationStr += ", Hep B (positive)"; }
					 */

				} else {
					maternalInvestigationStr += " Hep B ";
					/*
					 * if (babyAntenatalHistoryDetails.getHepbType() != null &&
					 * (babyAntenatalHistoryDetails.getHepbType().equalsIgnoreCase("HBsAg") ||
					 * babyAntenatalHistoryDetails.getHepbType().equalsIgnoreCase("HBeAg"))) {
					 * maternalInvestigationStr += " Hep B (negative)"; }
					 */
				}
				if (babyAntenatalHistoryDetails.getHbsag() != null
						&& (babyAntenatalHistoryDetails.getHbsag().equalsIgnoreCase("positive"))) {
					maternalInvestigationStr += " HbsAg(positive)";
				}
//				else if (babyAntenatalHistoryDetails.getHbsag() != null
//						&& (babyAntenatalHistoryDetails.getHbsag().equalsIgnoreCase("negative"))) {
//					maternalInvestigationStr += " HbsAg(negative)";
//				}
				if (babyAntenatalHistoryDetails.getHbeag() != null
						&& (babyAntenatalHistoryDetails.getHbeag().equalsIgnoreCase("positive"))) {
					maternalInvestigationStr += " HbeAg(positive)";
				} 
//				else if (babyAntenatalHistoryDetails.getHbeag() != null
//						&& (babyAntenatalHistoryDetails.getHbeag().equalsIgnoreCase("negative"))) {
//					maternalInvestigationStr += " HbeAg(negative)";
//				}
			}
			if (babyAntenatalHistoryDetails.getVdrl() != null && babyAntenatalHistoryDetails.getVdrl() == true) {
				if (maternalInvestigationStr != "") {
					if (babyAntenatalHistoryDetails.getVdrlType() != null
							&& babyAntenatalHistoryDetails.getVdrlType().equalsIgnoreCase("Reactive")) {
						maternalInvestigationStr += ", VDRL (reactive)";
					}
				} else {
					if (babyAntenatalHistoryDetails.getVdrlType() != null
							&& babyAntenatalHistoryDetails.getVdrlType().equalsIgnoreCase("Reactive")) {
						maternalInvestigationStr += " VDRL (reactive)";
					}
				}
			}
			
			if (babyAntenatalHistoryDetails.getIct()!= null && babyAntenatalHistoryDetails.getIct() == true) {
				if (maternalInvestigationStr != "") {
					if (babyAntenatalHistoryDetails.getIct() != null
							&& babyAntenatalHistoryDetails.getIct() == true) {
						maternalInvestigationStr += ", ICT (positive)";
					}
//					else {
//						maternalInvestigationStr += ", ICT (negative)";
//					}
				} else {
					if (babyAntenatalHistoryDetails.getIct() != null
							&& babyAntenatalHistoryDetails.getIct() == true) {
						maternalInvestigationStr += " ICT (positive)";
					}
//					else {
//						maternalInvestigationStr += " ICT (negative)";
//					}
				}
			}
			
			if (babyAntenatalHistoryDetails.getTorch()!= null && babyAntenatalHistoryDetails.getTorch() == true) {
				if (maternalInvestigationStr != "") {
					if (babyAntenatalHistoryDetails.getTorch() != null
							&& babyAntenatalHistoryDetails.getTorch() == true) {
						maternalInvestigationStr += ", Torch (positive)";
					}
//					else {
//						maternalInvestigationStr += ", Torch (negative)";
//					}
				} else {
					if (babyAntenatalHistoryDetails.getTorch() != null
							&& babyAntenatalHistoryDetails.getTorch() == true) {
						maternalInvestigationStr += " Torch (positive)";
					}
//					else {
//						maternalInvestigationStr += " Torch (negative)";
//					}
				}
			}
			
			if (babyAntenatalHistoryDetails.getIsOtherMeternalInvestigations() != null
					&& babyAntenatalHistoryDetails.getIsOtherMeternalInvestigations() == true) {
				if (maternalInvestigationStr != "") {
					if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getOtherMeternalInvestigations())) {
						maternalInvestigationStr += ", " + babyAntenatalHistoryDetails.getOtherMeternalInvestigations();
					}
				} else {
					if (!BasicUtils.isEmpty(babyAntenatalHistoryDetails.getOtherMeternalInvestigations())) {
						maternalInvestigationStr += ", " + babyAntenatalHistoryDetails.getOtherMeternalInvestigations();
					}
				}
			}

			if (maternalInvestigationStr != "") {
				if (maternalInvestigationStr.contains(",")) {
					maternalInvestigationFinalStr += "Investigations done were " + maternalInvestigationStr + ". ";
				} else {
					maternalInvestigationFinalStr += "Investigation done was " + maternalInvestigationStr + ". ";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maternalInvestigationFinalStr;
	}

	// Growth Details Fetching Function
	private String getGrowthDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String growthNotes = "";
		try {
			String queryGrowthDetail = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' order by creationtime";
			List<BabyVisit> growthNoteList = inicuDao.getListFromMappedObjQuery(queryGrowthDetail);
			if (!BasicUtils.isEmpty(growthNoteList)) {
				Float birthWeight, birthLength, birthHeadSize, minWeight, minLength, minHeadSize, dischargeWeight,
						dischargeLength, dischargeHeadSize;
				birthWeight = birthLength = birthHeadSize = minWeight = minLength = minHeadSize = dischargeWeight = dischargeLength = dischargeHeadSize = null;
				Timestamp birthDate, dischargeDate, regainBirthWeightDate, minWeightDate;
				birthDate = dischargeDate = regainBirthWeightDate = minWeightDate = null;
				boolean firstMinWeight, weightDrop;
				firstMinWeight = weightDrop = false;
				for (int i = 0; i < growthNoteList.size(); i++) {
					BabyVisit growthObj = growthNoteList.get(i);
					if (i == 0) {
						birthDate = growthObj.getCreationtime();
						if (growthObj.getCurrentdateweight() != null) {
							minWeight = birthWeight = growthObj.getCurrentdateweight();
						}

						if (growthObj.getCurrentdateheight() != null) {
							minLength = birthLength = growthObj.getCurrentdateheight();
						}

						if (growthObj.getCurrentdateheadcircum() != null) {
							minHeadSize = birthHeadSize = growthObj.getCurrentdateheadcircum();
						}
					} else {
						if (weightDrop && birthDate != null && growthObj.getCurrentdateweight() != null
								&& Float.compare(birthWeight, growthObj.getCurrentdateweight()) <= 0) {
							regainBirthWeightDate = growthObj.getCreationtime();
							weightDrop = false;
						}

						if (minWeight != null && growthObj.getCurrentdateweight() != null
								&& Float.compare(minWeight, growthObj.getCurrentdateweight()) > 0) {
							minWeight = growthObj.getCurrentdateweight();
							minWeightDate = growthObj.getCreationtime();
							if (!firstMinWeight) {
								weightDrop = true;
								firstMinWeight = true;
							}
						}

						if (minLength != null && growthObj.getCurrentdateheight() != null
								&& Float.compare(minLength, growthObj.getCurrentdateheight()) > 0) {
							minLength = growthObj.getCurrentdateheight();
						}

						if (minHeadSize != null && growthObj.getCurrentdateheadcircum() != null
								&& Float.compare(minHeadSize, growthObj.getCurrentdateheadcircum()) > 0) {
							minHeadSize = growthObj.getCurrentdateheadcircum();
						}

						if (i == growthNoteList.size() - 1) {
							dischargeDate = growthObj.getCreationtime();
							if (growthObj.getCurrentdateweight() != null) {
								dischargeWeight = growthObj.getCurrentdateweight();
							}

							if (growthObj.getCurrentdateheight() != null) {
								dischargeLength = growthObj.getCurrentdateheight();
							}

							if (growthObj.getCurrentdateheadcircum() != null) {
								dischargeHeadSize = growthObj.getCurrentdateheadcircum();
							}
						}
					}
				}

				String weightNotes, lengthNotes, headSizeNotes;
				Boolean weightFlag, lengthFlag, headFlag;
				weightFlag = lengthFlag = headFlag = false;
				weightNotes = lengthNotes = headSizeNotes = "";
				if (birthWeight != null) {
					weightNotes += "At birth, Baby's weight was " + Math.round(birthWeight) + " gms. ";
				}

				if (minWeight != null && birthWeight != null && Float.compare(minWeight, birthWeight) < 0) {
					weightNotes += "Baby's minimum weight was " + Math.round(minWeight) + " gms on (DOL# "
							+ getDayOfLife(uhid, minWeightDate) + "). ";
					weightFlag = true;
				}

				if (regainBirthWeightDate != null) {
					weightNotes += "Baby regained birth weight on (DOL# " + getDayOfLife(uhid, regainBirthWeightDate)
							+ "). ";
					weightFlag = true;
				}

				Float growthVelocity, meanWeight;
				Long duration;
				growthVelocity = meanWeight = 0f;
				duration = 0L;
				if (birthWeight != null && dischargeWeight != null) {
					meanWeight = (birthWeight + dischargeWeight) / (2000);
				}

				if (dischargeDate != null && regainBirthWeightDate != null) {
					duration = (dischargeDate.getTime() - regainBirthWeightDate.getTime()) / (1000 * 60 * 60 * 24);
				}

				if (dischargeWeight != null && birthWeight != null && meanWeight != 0f && duration != 0L) {
					growthVelocity = (dischargeWeight - birthWeight) / (meanWeight * duration);
				}

				if (growthVelocity != 0f) {
					weightNotes += "The growth velocity of the baby during NICU stay was "
							+ (Math.round(growthVelocity * 100.0) / 100.0) + " gm/kg/day. ";
					weightFlag = true;
				}

				if (birthHeadSize != null) {
					if (weightFlag == false) {
						headSizeNotes += "head size was " + (Math.round(birthHeadSize * 100.0) / 100.0) + " cms. ";
					} else {
						headSizeNotes += "At birth, Baby's head size was " + (Math.round(birthHeadSize * 100.0) / 100.0)
								+ " cms. ";
					}
				}
				Float headSizeInc = 0f;
				if (dischargeDate != null && birthDate != null) {
					duration = (dischargeDate.getTime() - birthDate.getTime()) / (1000 * 60 * 60 * 24 * 7);
				}

				if (dischargeHeadSize != null && birthHeadSize != null && duration != 0L) {
					headSizeInc = (dischargeHeadSize - birthHeadSize) / (duration);
				}

				if (headSizeInc != 0f) {
					headSizeNotes += "The head size increased " + (Math.round(headSizeInc * 100.0) / 100.0)
							+ " cm/week. ";
					headFlag = true;
				}

				if (birthLength != null) {
					if (headFlag == false) {
						lengthNotes += "length was " + (Math.round(birthLength * 100.0) / 100.0) + " cms. ";
					} else {
						lengthNotes += "At birth, Baby's length was " + (Math.round(birthLength * 100.0) / 100.0)
								+ " cms. ";
					}
				}
				Float lengthInc = 0f;
				if (dischargeDate != null && birthDate != null) {
					duration = (dischargeDate.getTime() - birthDate.getTime()) / (1000 * 60 * 60 * 24 * 7);
				}

				if (dischargeLength != null && birthLength != null && duration != 0L) {
					lengthInc = (dischargeLength - birthLength) / (duration);
				}

				if (lengthInc != 0f) {
					lengthNotes += "The length increased " + (Math.round(lengthInc * 100.0) / 100.0) + " cm/week. ";
				}

				if (!BasicUtils.isEmpty(weightNotes)) {
					growthNotes += weightNotes;
				}

				if (!BasicUtils.isEmpty(headSizeNotes)) {
					growthNotes += headSizeNotes;
				}

				if (!BasicUtils.isEmpty(lengthNotes)) {
					growthNotes += lengthNotes;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return growthNotes;
	}

//	//Stable Notes
//	private String getStableNotes(String uhid) throws InicuDatabaseExeption{
//		// TODO Auto-generated method stub
//		String stableNotes = "";
//		try {
//			String queryStableNotes = "select obj from StableNote as obj where uhid='" + uhid + "'";
//			List<StableNote> listStableNotes = inicuDao.getListFromMappedObjQuery(queryStableNotes);
//			if (!BasicUtils.isEmpty(listStableNotes)) {
//				List<String> investigationList = new ArrayList<>();
//				stableNotes += "Baby is clinically stable and active. ";
//				for (int i=0;i<listStableNotes.size();i++) {
//					StableNote stableNoteObj = (StableNote) listStableNotes.get(i);
//					List<String> vitalParamList = new ArrayList<>();
//					if (stableNoteObj.getHr() != null) {
//						vitalParamList.add("HR " + stableNoteObj.getHr().toString() + "bpm");
//					}
//					if (stableNoteObj.getRr() != null) {
//						vitalParamList.add("RR " + stableNoteObj.getRr().toString() + "bpm");
//					}
//					if (stableNoteObj.getSpo2() != null) {
//						vitalParamList.add("SpO2 " + stableNoteObj.getSpo2().toString() + "%");
//					}
//					
//					if(!BasicUtils.isEmpty(vitalParamList)) {
//						String vitalParamListString = vitalParamList.toString();
//						vitalParamListString = vitalParamListString.replace("[", "");
//						vitalParamListString = vitalParamListString.replace("]", "");
//						if(!vitalParamListString.contains(",")) {
//							stableNotes += "Baby's vital parameter is " + vitalParamListString + "(#DOL "
//									+ getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). ";
//						}
//						else {
//							stableNotes += "Baby's vital parameters are " + vitalParamListString + "(#DOL "
//									+ getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). ";
//
//						}
//					}
//					
//					if (stableNoteObj.getStoolStatus() != null && stableNoteObj.getStoolStatus()) {
//						stableNotes += "Baby had passed stool ";
//						if (stableNoteObj.getStoolTimes() != null) {
//							if(stableNoteObj.getStoolTimes() == 1) {
//								stableNotes += stableNoteObj.getStoolTimes() + " time ";
//							}
//							else {
//								if(stableNoteObj.getStoolTimes() != 0) {
//									stableNotes += stableNoteObj.getStoolTimes() + " times";
//								}
//							}
//						}	
//						stableNotes += "(#DOL " + getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). ";
//					}
//					
//					if (stableNoteObj.getUrineStatus() != null && stableNoteObj.getUrineStatus()) {
//						stableNotes += "Baby had passed urine ";
//						if (stableNoteObj.getStoolTimes() != null) {
//							if(stableNoteObj.getStoolTimes() == 1) {
//								stableNotes += stableNoteObj.getStoolTimes() + " time";
//							}
//							else {
//								if(stableNoteObj.getStoolTimes() != 0) {
//									stableNotes += stableNoteObj.getStoolTimes() + " times";
//								}
//							}
//						}	
//						stableNotes += "(#DOL " + getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). ";
//					}
//								
//					if (stableNoteObj.getBabyFeed() != null) {
//						BabyfeedDetail feedObj = stableNoteObj.getBabyFeed();
//						if (!BasicUtils.isEmpty(feedObj.getFeedText())) {
//							stableNotes += "Baby is on" + feedObj.getFeedText() + "(#DOL "
//								+ getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). ";
//						}
//					}
//					
//					if (!BasicUtils.isEmpty(stableNoteObj.getOrderSelectedText())) {
//					     String orderStringArray[] = stableNoteObj.getOrderSelectedText().split(",");
//					     for(String s : orderStringArray ) {
//					    	 if(!investigationList.contains(s)) {
//					    		 investigationList.add(s);
//					    	 }
//					     }
//					}
//					
//					if (!BasicUtils.isEmpty(stableNoteObj.getGeneralnote())) {
//						stableNotes += stableNoteObj.getGeneralnote() + "(#DOL " 
//								+ getDayOfLife(uhid,stableNoteObj.getCreationtime()) + "). "; 
//					}
//				}
//				
//				if (!BasicUtils.isEmpty(investigationList)) {
//				     String investigationListString = investigationList.toString();
//				     investigationListString = investigationListString.replace("[", "");
//				     investigationListString = investigationListString.replace("]", "");
//						if(!investigationListString.contains(",")) {
//						     stableNotes += "Investigation Ordered was "+ investigationListString + ". ";
//						}
//						else{
//						     stableNotes += "Investigation Ordered were "+ investigationListString + ". ";
//						}
//				}
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return stableNotes;
//	}

	// Procedure Details Fetching Function
	private String getProcedureDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String procedureStr = "";
		try {
			String queryProcedure = "select * from vw_procedures_usage where uhid = '" + uhid
					+ "' and procedure_type != 'Other'";
			List<Object> procedureList = inicuDao.getListFromNativeQuery(queryProcedure);
			if (!BasicUtils.isEmpty(procedureList)) {
				HashMap<String, Map<String, String>> proceduresDatesMap = new HashMap<>();
				String procedureName = "";
				for (int i = 0; i < procedureList.size(); i++) {
					Object[] procedureListObj = (Object[]) procedureList.get(i);
					if (!BasicUtils.isEmpty((String) procedureListObj[0])) {
						procedureName = (String) procedureListObj[0];
						if (!proceduresDatesMap.containsKey(procedureName)) {
							Map<String, String> procedureDates = new HashMap<>();
							proceduresDatesMap.put(procedureName, procedureDates);
						}
					}

					if ((Timestamp) procedureListObj[2] != null) {
						Map<String, String> procedureDates = proceduresDatesMap.get(procedureName);

						String startTime = String.valueOf((Timestamp) procedureListObj[2]);
						if ((Timestamp) procedureListObj[3] != null) {
							String endTime = getDateFromTimestamp((Timestamp) procedureListObj[3]);
							if (procedureDates.containsKey(startTime)) {
								String endTimeUpdate = procedureDates.get(startTime);
								endTimeUpdate += "," + endTime;
								procedureDates.put(startTime, endTimeUpdate);
							} else {
								procedureDates.put(startTime, endTime);
							}
						} else {
							if (procedureDates.containsKey(startTime)) {
								String endTimeUpdate = procedureDates.get(startTime);
								endTimeUpdate += "," + " ";
								procedureDates.put(startTime, endTimeUpdate);
							} else {
								procedureDates.put(startTime, " ");
							}
						}
						proceduresDatesMap.put(procedureName, procedureDates);
					}
				}

				String queryForOtherProcedure = "select obj from ProcedureOther as obj where uhid = '" + uhid + "'";
				List<ProcedureOther> otherProcedureList = inicuDao.getListFromMappedObjQuery(queryForOtherProcedure);
				for (int i = 0; i < otherProcedureList.size(); i++) {
					ProcedureOther otherProcedureObj = otherProcedureList.get(i);
					if (!BasicUtils.isEmpty(otherProcedureObj.getProcedurename())) {
						procedureName = otherProcedureObj.getProcedurename();
						if (!proceduresDatesMap.containsKey(procedureName)) {
							Map<String, String> procedureDates = new HashMap<>();
							proceduresDatesMap.put(procedureName, procedureDates);
						}
					}

					if (otherProcedureObj.getEntrytime() != null) {
						Map<String, String> procedureDates = proceduresDatesMap.get(procedureName);
						String entryTime = getDateFromTimestamp(otherProcedureObj.getEntrytime());
						procedureDates.put(entryTime, "");
						proceduresDatesMap.put(procedureName, procedureDates);
					}
				}

				List<String> procedureNameList = new ArrayList<>();
				Map<String, String> procedureDoneDates = null;
				String procedureDateString = "";
				int therapeuticProcedureCount = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				for (Map.Entry mapElement : proceduresDatesMap.entrySet()) {
					procedureName = (String) mapElement.getKey();
					procedureDoneDates = proceduresDatesMap.get(procedureName);
					if(procedureName.equalsIgnoreCase("Therapeutic Hypothermia")){
						String startDate = "";
						int count =0;
						for (Map.Entry<String, String> entry : procedureDoneDates.entrySet()) {
							therapeuticProcedureCount++;
							if(count<1) {
								startDate = monthDayYearformatter.format((java.util.Date) sdf.parse(entry.getKey()));
								count++;
							}
						}

						if(startDate!=""){
							procedureDateString += " Performed on "+ startDate;
						}
					}else {
						if (procedureDoneDates.size() > 1) {
							procedureDateString += "[";
						}

						for (Map.Entry<String, String> entry : procedureDoneDates.entrySet()) {
							procedureDateString += "(Start Date: " + monthDayYearformatter.format((java.util.Date) sdf.parse(entry.getKey()));
							if (!BasicUtils.isEmpty(entry.getValue())) {
								procedureDateString += ", Removal Date: " + entry.getValue();
							}
							procedureDateString += "),";
						}

						if (procedureDateString.trim().lastIndexOf(",") != -1) {
							procedureDateString = procedureDateString.substring(0, procedureDateString.length() - 1);
						}

						if (procedureDoneDates.size() > 1) {
							procedureDateString += "]";
						}
					}

					// Add procedure with done dates in in procedure name list
					procedureNameList.add(procedureName + procedureDateString);
					procedureDateString = "";
				}

				int procedureCount = (procedureList.size() + otherProcedureList.size());
				if(therapeuticProcedureCount>1){
					procedureCount = (procedureCount-therapeuticProcedureCount)+1;
				}

				procedureStr += "The baby had " + procedureCount + " ";
				if (procedureList.size() > 1) {
					procedureStr += "procedures during the hospital stay. These were ";
				} else {
					procedureStr += "procedure during the hospital stay which was ";
				}

				if (!BasicUtils.isEmpty(procedureNameList)) {
					String procedureNameListString = procedureNameList.toString();
					procedureNameListString = procedureNameListString.substring(1, procedureNameListString.length()-1);
					procedureStr += procedureNameListString + ". ";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return procedureStr;
	}

	// Blood Product Details Fetching Function
	private String getBloodProductDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String bloodProductStr = "";
		try {
			String queryBloodProduct = "select obj from DoctorBloodProducts as obj where uhid='" + uhid + "'";
			List<DoctorBloodProducts> allBloodProductList = inicuDao.getListFromMappedObjQuery(queryBloodProduct);
			if (!BasicUtils.isEmpty(allBloodProductList)) {
				if (allBloodProductList.size() > 1) {
					bloodProductStr += "Baby received " + allBloodProductList.size()
							+ " blood products during the hospital stay.";
				}
				for (int i = 0; i < allBloodProductList.size(); i++) {
					DoctorBloodProducts bloodProductObj = (DoctorBloodProducts) allBloodProductList.get(i);
					String bloodProductObjString = "";
					if (!BasicUtils.isEmpty(bloodProductObj.getBlood_product())) {
						String bloodProductName = bloodProductObj.getBlood_product();
						bloodProductObjString += "Baby received " + bloodProductName;
						List<String> bloodProductIndication = new ArrayList<>();
						if (bloodProductName.equalsIgnoreCase("Packed Cell")
								|| bloodProductName.equalsIgnoreCase("Whole Blood")) {
							if (bloodProductObj.getHematocrit() != null) {
								bloodProductIndication.add("HCT " + bloodProductObj.getHematocrit().toString() + "% ");
							}
							if (bloodProductObj.getIndication_hb() != null) {
								bloodProductIndication
										.add("Haemoglobin " + bloodProductObj.getIndication_hb().toString() + "g/dL ");
							}
							if (!BasicUtils.isEmpty(bloodProductObj.getIndication_resp())) {
								bloodProductIndication.add("baby was " + bloodProductObj.getIndication_resp() + " ");
							}
							if (bloodProductObj.getApneic_spell() != null && bloodProductObj.getApneic_spell()) {
								if (!BasicUtils.isEmpty(bloodProductObj.getApnea_count())) {
									bloodProductIndication.add("bay had apneic cells count "
											+ bloodProductObj.getApnea_count().toString() + " ");
								} else {
									bloodProductIndication.add("bay had apneic cells ");
								}
							}

						}

						if (bloodProductName.equalsIgnoreCase("FFP")) {
							if (bloodProductObj.getPtt_value() != null) {
								bloodProductIndication.add("PT " + bloodProductObj.getPtt_value().toString());
							}
							if (bloodProductObj.getAptt_value() != null) {
								bloodProductIndication.add("APPT " + bloodProductObj.getAptt_value().toString());
							}
							if (bloodProductObj.getPti_value() != null) {
								bloodProductIndication.add("PTI " + bloodProductObj.getPti_value().toString());
							}
							if (!BasicUtils.isEmpty(bloodProductObj.getBleeding())) {
								bloodProductIndication.add("baby had " + bloodProductObj.getBleeding() + " bleeding");
							}

						}

						if (bloodProductName.equalsIgnoreCase("PRP")
								|| bloodProductName.equalsIgnoreCase("Platelet Concentrate")
								|| bloodProductName.equalsIgnoreCase("Aphereis Platelet")) {
							if (bloodProductObj.getPlatelet_count() != null) {
								bloodProductIndication.add("platelet count "
										+ bloodProductObj.getPlatelet_count().toString() + "per L/uL");
							}
							if (!BasicUtils.isEmpty(bloodProductObj.getBleeding())) {
								if (bloodProductObj.getBleeding().equalsIgnoreCase("Yes")) {
									bloodProductIndication.add("baby had bleeding ");
								}
							}
							if (bloodProductObj.getSurgery() != null && bloodProductObj.getSurgery()) {
								bloodProductIndication.add("and baby also had surgery");
							}
						}
						String bloodProductIndicationString = "";
						if (!BasicUtils.isEmpty(bloodProductIndication)) {
							bloodProductIndicationString = bloodProductIndication.toString();
							bloodProductIndicationString = bloodProductIndicationString.replace("[", "(");
							bloodProductIndicationString = bloodProductIndicationString.replace("]", ")");
						}

						bloodProductObjString += bloodProductIndicationString;
						if (bloodProductObj.getAssessment_time() != null) {
							bloodProductObjString += " on " + getDateFromTimestamp(bloodProductObj.getAssessment_time())
									+ "(DOL#" + getDayOfLife(uhid, bloodProductObj.getAssessment_time()) + "). ";
						}
					}
					if (!BasicUtils.isEmpty(bloodProductObjString)) {
						bloodProductStr += bloodProductObjString;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bloodProductStr;
	}

	// Nutrition Details Fetching Function
	private String getNutritionDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String nutritionStr = "";
		TreeMap<Long, List<String>> nutritionMap = new TreeMap<>();
		Long babyFeedDOL, nursingIntakeOutputDOL, cummPnStart, cummNpoStart, cummPnEnd, cummNpoEnd, totalPnDays;
		cummPnStart = cummNpoStart = cummPnEnd = cummNpoEnd = totalPnDays = 0L;
		babyFeedDOL = nursingIntakeOutputDOL = null;
		try {
			String queryBabyFeed = "select obj from BabyfeedDetail as obj where uhid='" + uhid
					+ "' order by creationtime";
			List<BabyfeedDetail> babyFeedList = inicuDao.getListFromMappedObjQuery(queryBabyFeed);
			if (!BasicUtils.isEmpty(babyFeedList)) {
				Boolean isOnFeedAtDischarge = false;
				Float prevTotalEN, prevTotalPN, currTotalEN, currTotalPN;
				prevTotalEN = prevTotalPN = null;
				for (int i = 0; i < babyFeedList.size(); i++) {
					String nutritionMapInputString = "";
					BabyfeedDetail babyFeedListObj = (BabyfeedDetail) babyFeedList.get(i);
					babyFeedDOL = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
					if (!BasicUtils.isEmpty(babyFeedListObj.getFeedmethod())) {
						if (!babyFeedListObj.getFeedmethod().contains("METHOD01")) {
							isOnFeedAtDischarge = true;
						} else {
							isOnFeedAtDischarge = false;
						}
					}

					if (babyFeedListObj.getTotalenteralvolume() != null
							&& babyFeedListObj.getTotalparenteralvolume() != null) {
						currTotalEN = babyFeedListObj.getTotalenteralvolume() / (babyFeedListObj.getWorkingWeight());
						currTotalPN = babyFeedListObj.getTotalparenteralvolume() / (babyFeedListObj.getWorkingWeight());

						DecimalFormat df = new DecimalFormat();
						df.setMaximumFractionDigits(2);
						currTotalEN = Float.parseFloat(df.format(currTotalEN));
						currTotalPN = Float.parseFloat(df.format(currTotalPN));
						if ((prevTotalEN == null && prevTotalPN == null)) {
							if (Float.compare(currTotalEN, 0f) != 0 && Float.compare(currTotalPN, 0f) != 0) {
								nutritionMapInputString += "The baby was started on feeds @" + currTotalEN
										+ " ml/kg/day and Parenteral Nutrition @" + currTotalPN + " ml/kg/day";
								cummPnStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
							} else {
								if (Float.compare(currTotalEN, 0f) != 0) {
									nutritionMapInputString += "The baby was started on feeds @" + currTotalEN
											+ " ml/kg/day";
								}

								if (Float.compare(currTotalPN, 0f) != 0) {
									if (nutritionMapInputString != "") {
										nutritionMapInputString += ",";
									}

									nutritionMapInputString += "The baby was started on Parenteral Nutrition @"
											+ currTotalPN + " ml/kg/day";
									cummPnStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
								}
							}
						} else {
							if (Float.compare(currTotalEN, 0f) == 0 && Float.compare(currTotalPN, 0f) == 0) {
								nutritionMapInputString += "Parenteral Nutrition and feed both were Stopped.";
								cummPnEnd = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
								totalPnDays += (cummPnEnd - cummPnStart);
							} else {
								if (prevTotalEN != null && Float.compare(currTotalEN, 0f) == 0) {
									if (prevTotalPN == null && Float.compare(currTotalPN, 0f) != 0) {
										nutritionMapInputString += "The baby was made NPO @" + currTotalPN
												+ " ml/kg/day due to feed intolerance";
										cummNpoStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
									} else {
										nutritionMapInputString += "Feed was Stopped";
									}
								}

								if (prevTotalPN != null && Float.compare(currTotalPN, 0f) == 0) {
									if (nutritionMapInputString != "") {
										nutritionMapInputString += ",";
									}

									if (prevTotalEN == null && Float.compare(currTotalEN, 0f) != 0) {
										cummNpoEnd = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
										nutritionMapInputString += "The cummulative NPO ";
										long duration = (cummNpoEnd - cummNpoStart);
										totalPnDays += duration;
										if (duration == 1) {
											nutritionMapInputString += "day was 1 and ";
										} else {
											if (duration != 0) {
												nutritionMapInputString += "days were " + duration + " and ";
											}
										}
										nutritionMapInputString += "The baby was started on feed @" + currTotalEN
												+ " ml/kg/day";
									} else {
										if (prevTotalEN == null) {
											nutritionMapInputString += "Parenteral Nutrition was Stopped";
										}
									}
								}
							}

							if (prevTotalEN == null && Float.compare(currTotalEN, 0f) != 0) {
								if (nutritionMapInputString != "") {
									nutritionMapInputString += ",";
								}

								if (prevTotalPN != null && Float.compare(currTotalPN, 0f) == 0) {
									cummNpoEnd = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
									nutritionMapInputString += "The cummulative NPO ";
									long duration = (cummNpoEnd - cummNpoStart);
									totalPnDays += duration;
									if (duration == 1) {
										nutritionMapInputString += "day was 1 and ";
									} else {
										if (duration != 0) {
											nutritionMapInputString += "days were " + duration + " and ";
										}
									}
								}
								nutritionMapInputString += "The baby was started on feed @" + currTotalEN
										+ " ml/kg/day";
							}

							if (prevTotalPN == null && Float.compare(currTotalPN, 0f) != 0) {
								if (nutritionMapInputString != "") {
									nutritionMapInputString += ",";
								}

								if (prevTotalEN != null && Float.compare(currTotalEN, 0f) == 0) {
									nutritionMapInputString += "The baby was made NPO @" + currTotalPN
											+ " ml/kg/day due to feed intolerance";
									cummNpoStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
								} else {
									nutritionMapInputString += "The baby was started on Parenteral Nutrition @"
											+ currTotalPN + " ml/kg/day";
									cummPnStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
								}
							}

//							if(prevTotalEN != null && Float.compare(prevTotalEN,currTotalEN) != 0 
//									&& prevTotalPN != null && Float.compare(prevTotalPN,currTotalPN) != 0 
//									&& Float.compare(currTotalEN,0f) != 0 && Float.compare(currTotalPN,0f) != 0	) {
//								if(nutritionMapInputString != "") {
//									nutritionMapInputString += ",";
//								}
//								nutritionMapInputString += "baby accepted " + currTotalEN + "ml/kg/day and " + currTotalPN 
//										+ "ml/kg/day Parenteral Nutrition and feed respectively";
//							}
//							else {
//								if(prevTotalEN != null && Float.compare(prevTotalEN,currTotalEN) != 0 && Float.compare(currTotalEN,0f) != 0) {
//									if(nutritionMapInputString != "") {
//										nutritionMapInputString += ",";
//									}
//									nutritionMapInputString += "baby accepted " + currTotalEN + "ml/kg/day feed";
//								}
//								
//								if(prevTotalPN != null && Float.compare(prevTotalPN,currTotalPN) != 0 && Float.compare(currTotalPN,0f) != 0) {
//									if(nutritionMapInputString != "") {
//										nutritionMapInputString += ",";
//									}
//									
//									if(prevTotalEN == null && Float.compare(currTotalEN,0f) == 0) {
//										nutritionMapInputString += "baby accepted " + currTotalPN + "ml/kg/day NPO";
//									}
//									else {
//										nutritionMapInputString += "baby accepted " + currTotalPN + "ml/kg/day Parenteral Nutrition";
//									}
//								}
//							}
						}

						if (Float.compare(currTotalEN, 0f) != 0) {
							prevTotalEN = currTotalEN;
						} else {
							prevTotalEN = null;
						}

						if (Float.compare(currTotalPN, 0f) != 0) {
							prevTotalPN = currTotalPN;
						} else {
							prevTotalPN = null;
						}
					} else {
						if (babyFeedListObj.getTotalenteralvolume() != null) {
							currTotalEN = babyFeedListObj.getTotalenteralvolume()
									/ (babyFeedListObj.getWorkingWeight());
							DecimalFormat df = new DecimalFormat();
							df.setMaximumFractionDigits(2);
							currTotalEN = Float.parseFloat(df.format(currTotalEN));

							if (prevTotalEN == null) {
								if (nutritionMapInputString != "") {
									nutritionMapInputString += ",";
								}

								if (Float.compare(currTotalEN, 0f) != 0) {
									nutritionMapInputString += "The baby was started on feed @" + currTotalEN
											+ " ml/kg/day";
								}
							} else {
								if (prevTotalEN != null && Float.compare(currTotalEN, 0f) == 0) {
									if (nutritionMapInputString != "") {
										nutritionMapInputString += ",";
									}
									nutritionMapInputString += "Feed was Stopped";
								}

//								if(prevTotalEN != null && Float.compare(prevTotalEN,currTotalEN) != 0 && Float.compare(currTotalEN,0f) != 0) {
//									if(nutritionMapInputString != "") {
//										nutritionMapInputString += ",";
//									}
//									nutritionMapInputString += "baby accepted " + currTotalEN + "ml/kg/day feed";
//								}	
							}

							if (Float.compare(currTotalEN, 0f) != 0) {
								prevTotalEN = currTotalEN;
							} else {
								prevTotalEN = null;
							}
						}

						if (babyFeedListObj.getTotalparenteralvolume() != null) {
							currTotalPN = babyFeedListObj.getTotalparenteralvolume()
									/ (babyFeedListObj.getWorkingWeight());
							DecimalFormat df = new DecimalFormat();
							df.setMaximumFractionDigits(2);
							currTotalPN = Float.parseFloat(df.format(currTotalPN));
							if (prevTotalPN == null) {
								if (nutritionMapInputString != "") {
									nutritionMapInputString += ",";
								}

								if (Float.compare(currTotalPN, 0f) != 0) {
									nutritionMapInputString += "The baby was started on Parenteral Nutrition @"
											+ currTotalPN + " ml/kg/day";
								}
								cummPnStart = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
							} else {
								if (prevTotalPN != null && Float.compare(currTotalPN, 0f) == 0) {
									if (nutritionMapInputString != "") {
										nutritionMapInputString += ",";
									}
									nutritionMapInputString += "Parenteral Nutrition was Stopped";
									cummPnEnd = getDayOfLife(uhid, babyFeedListObj.getCreationtime());
									totalPnDays += (cummPnEnd - cummPnStart);
								}

//								if(prevTotalPN != null && Float.compare(prevTotalPN,currTotalPN) != 0 && Float.compare(currTotalPN,0f) != 0) {
//									if(nutritionMapInputString != "") {
//										nutritionMapInputString += ",";
//									}
//									nutritionMapInputString += "baby accepted " + currTotalPN + "ml/kg/day Parenteral Nutrition";									
//								}	
							}

							if (Float.compare(currTotalPN, 0f) != 0) {
								prevTotalPN = currTotalPN;
							} else {
								prevTotalPN = null;
							}
						}
					}

					if (!BasicUtils.isEmpty(nutritionMapInputString)) {
						List<String> tempList = new ArrayList<>();
						if (nutritionMap.containsKey(babyFeedDOL)) {
							tempList = nutritionMap.get(babyFeedDOL);
						}
						tempList.add(nutritionMapInputString);
						nutritionMap.put(babyFeedDOL, tempList);
					}
				}

				for (Map.Entry<Long, List<String>> entry : nutritionMap.entrySet()) {
					String nutritionFinalString = entry.getValue().toString();
					nutritionFinalString = nutritionFinalString.replace("[", "");
					nutritionFinalString = nutritionFinalString.replace("]", "");
					nutritionFinalString = nutritionFinalString.replace(",", ", ");

					if (!BasicUtils.isEmpty(nutritionFinalString)) {
						if (nutritionStr == "") {
							nutritionStr += "At the time of Admission, ";
						}
						nutritionStr += nutritionFinalString + "(#DOL" + entry.getKey().toString() + "). ";
					}
				}

				if (totalPnDays == 1) {
					nutritionStr += "The cummulative duration of PN was 1 day. ";
				} else {
					if (totalPnDays != 0) {
						nutritionStr += "The cummulative duration of PN were " + totalPnDays + " days. ";
					}
				}

				if (nutritionStr != "" && isOnFeedAtDischarge) {
					nutritionStr += "At discharge baby is on direct feeding. ";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nutritionStr;
	}

	// Vaccination Details Fetching Function
	private String getVaccinationDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String vaccinationSummary = "";
		try {
			String queryForVaccinationName = "select obj from BabyPrescription as obj where uhid='" + uhid
					+ "' and medicationtype='TYPE0035'";
			List<BabyPrescription> allVaccinationNameList = inicuDao.getListFromMappedObjQuery(queryForVaccinationName);
			if (!BasicUtils.isEmpty(allVaccinationNameList)) {
				// My code
//				String vaccinationSummary="";
				for (int i = 0; i < allVaccinationNameList.size(); i++) {
//					vaccinationNameList.add(allVaccinationNameList.get(i).getMedicinename());
					// change the time format
					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
					Timestamp startDate = allVaccinationNameList.get(i).getStartdate();
					String formattedTime = formatter.format(startDate.getTime());

					if (allVaccinationNameList.size() == 1) {
						vaccinationSummary += allVaccinationNameList.get(i).getMedicinename() + " was given on "
								+ formattedTime + " to baby during the NICU stay.";
					} else if (allVaccinationNameList.size() > 1) {
						if (vaccinationSummary == "") {
							vaccinationSummary += allVaccinationNameList.get(i).getMedicinename() + " was given on "
									+ formattedTime;
						} else {
							vaccinationSummary += ", " + allVaccinationNameList.get(i).getMedicinename()
									+ " was given on " + formattedTime;
						}
					}
				}

				if (vaccinationSummary != "" && allVaccinationNameList.size() > 1) {
					vaccinationSummary += " to baby during the NICU stay.";
				}

				// Krishna's Code
//				List<String> vaccinationNameList = new ArrayList<>();
//
//				for(int i=0;i<allVaccinationNameList.size();i++) {
//					vaccinationNameList.add(allVaccinationNameList.get(i).getMedicinename());
//				}
//
//				if(!BasicUtils.isEmpty(vaccinationNameList)) {
//					String vaccinationNameListString = vaccinationNameList.toString();
//					vaccinationNameListString = vaccinationNameListString.replace("[", "");
//					vaccinationNameListString = vaccinationNameListString.replace("]", "");
//
//					if(vaccinationNameListString.contains(",")) {
//						vaccinationStr += vaccinationNameListString + " were given to baby during the NICU stay. ";
//					}
//					else {
//						vaccinationStr += vaccinationNameListString + " was given to baby during the NICU stay. ";
//					}
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vaccinationSummary;
	}

	// Neurological Screening Detail Fetching Function
	public String getNeuroScreeningDetail(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String htmlNextLine = System.getProperty("line.separator");
		String neuroScreenStr = "";
		try {
			String queryScreeningNeuro = "select obj from ScreenNeurological as obj where uhid='" + uhid + "'";
			List<ScreenNeurological> allNeuroScreeningList = inicuDao.getListFromMappedObjQuery(queryScreeningNeuro);
			if (!BasicUtils.isEmpty(allNeuroScreeningList)) {
				String neuroScreeningStr = "Baby had " + allNeuroScreeningList.size()
						+ " Neurological Screenings during the hospital stay. ";
				for (int i = 0; i < allNeuroScreeningList.size(); i++) {
					ScreenNeurological neuroScreeningObj = (ScreenNeurological) allNeuroScreeningList.get(i);
					String neuroScreeningObjString = htmlNextLine + (i + 1) + ". Neurological Screening was done on "
							+ getDateFromTimestamp(neuroScreeningObj.getScreening_time()) + "( CGA ";
					if (neuroScreeningObj.getCga_weeks() != 0) {
						neuroScreeningObjString += neuroScreeningObj.getCga_weeks();
						if (neuroScreeningObj.getCga_weeks() != 1) {
							neuroScreeningObjString += " weeks ";
						} else {
							neuroScreeningObjString += " week ";
						}
					}
					if (neuroScreeningObj.getCga_days() != 0) {
						neuroScreeningObjString += neuroScreeningObj.getCga_days();
						if (neuroScreeningObj.getCga_days() != 1) {
							neuroScreeningObjString += " days ";
						} else {
							neuroScreeningObjString += " day ";
						}
					}
					neuroScreeningObjString += "and DOL# " + getDayOfLife(uhid, neuroScreeningObj.getScreening_time())
							+ ") ";
					List<String> neuroScreeningList = new ArrayList<>();
					String neuroScreeningAbnormalString;
					int abnormalCount = 0;
					if (neuroScreeningObj.getPosture_type() != null
							&& neuroScreeningObj.getPosture_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Posture ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getPosture_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getPosture_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getArm_traction_type() != null
							&& neuroScreeningObj.getArm_traction_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Arm Traction ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getArm_traction_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getArm_traction_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getLeg_traction_type() != null
							&& neuroScreeningObj.getLeg_traction_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Leg Traction ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getLeg_traction_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getLeg_traction_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getHead_extensor_type() != null
							&& neuroScreeningObj.getHead_extensor_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Head Extensor ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getHead_extensor_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getHead_extensor_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getHead_flexor_type() != null
							&& neuroScreeningObj.getHead_flexor_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Head Flexor ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getHead_flexor_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getHead_flexor_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getHead_lag_type() != null
							&& neuroScreeningObj.getHead_lag_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Head Lag ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getHead_lag_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getHead_lag_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getVentral_type() != null
							&& neuroScreeningObj.getVentral_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Ventral Suspension ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getVentral_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getVentral_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getBody_type() != null
							&& neuroScreeningObj.getBody_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Body Movement ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getBody_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getBody_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getTremor_startles_type() != null
							&& neuroScreeningObj.getTremor_startles_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Tremor & Startles ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getTremor_startles_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getTremor_startles_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getMoro_reflex_type() != null
							&& neuroScreeningObj.getMoro_reflex_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Moro Reflex ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getMoro_reflex_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getMoro_reflex_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getAuditory_type() != null
							&& neuroScreeningObj.getAuditory_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Auditory Orientation ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getAuditory_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getAuditory_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getVisual_type() != null
							&& neuroScreeningObj.getVisual_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Visual Orientation ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getVisual_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getVisual_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}
					if (neuroScreeningObj.getAlertness_type() != null
							&& neuroScreeningObj.getAlertness_type().equalsIgnoreCase("Abnormal")) {
						neuroScreeningAbnormalString = "Alertness ";
						if (!BasicUtils.isEmpty(neuroScreeningObj.getAlertness_value())) {
							neuroScreeningAbnormalString += ": " + neuroScreeningObj.getAlertness_value();
						}
						neuroScreeningList.add(neuroScreeningAbnormalString);
						abnormalCount++;
					}

					if (abnormalCount > 1) {
						neuroScreeningObjString += " and was abnormal,the finding of abnormal " + "extensions were ";

						String neuroScreeningListString = neuroScreeningList.toString();
						neuroScreeningListString = neuroScreeningListString.replace("[", "");
						neuroScreeningListString = neuroScreeningListString.replace("]", "");

						neuroScreeningObjString += neuroScreeningListString + ". ";
					} else {
						neuroScreeningObjString += " and was normal. ";
					}

					List<String> neuroScreeningListAbnormalSign = new ArrayList<>();
					if (neuroScreeningObj.getSuck() != null) {
						if (neuroScreeningObj.getSuck()) {
							neuroScreeningListAbnormalSign.add("Sucking");
						} else {
							neuroScreeningListAbnormalSign.add("No Sucking");
						}
					}
					if (neuroScreeningObj.getFacial() != null) {
						if (neuroScreeningObj.getFacial()) {
							neuroScreeningListAbnormalSign.add("Abnormal Facial");
						} else {
							neuroScreeningListAbnormalSign.add("No Abnormal Facial");
						}
					}
					if (neuroScreeningObj.getEye() != null) {
						if (neuroScreeningObj.getEye()) {
							neuroScreeningListAbnormalSign.add("Abnormal Eye");
						} else {
							neuroScreeningListAbnormalSign.add("No Abnormal Eye");
						}
					}
					if (neuroScreeningObj.getSunset() != null) {
						if (neuroScreeningObj.getSunset()) {
							neuroScreeningListAbnormalSign.add("Abnormal Sunset");
						} else {
							neuroScreeningListAbnormalSign.add("No Abnormal Sunset");
						}
					}
					if (neuroScreeningObj.getHand() != null) {
						if (neuroScreeningObj.getHand()) {
							neuroScreeningListAbnormalSign.add("Fisted Hand ");
						} else {
							neuroScreeningListAbnormalSign.add("No Fisted Hand");
						}
					}
					if (neuroScreeningObj.getClonus() != null) {
						if (neuroScreeningObj.getClonus()) {
							neuroScreeningListAbnormalSign.add("Anormal Clonus");
						} else {
							neuroScreeningListAbnormalSign.add("No Anormal Clonus");
						}
					}

					if (!BasicUtils.isEmpty(neuroScreeningListAbnormalSign)) {
						if (neuroScreeningListAbnormalSign.size() != 1) {
							neuroScreeningObjString += "The abnormal signs were ";
						} else {
							neuroScreeningObjString += "The abnormal sign was ";
						}
						String neuroScreeningListAbnormalSignString = neuroScreeningListAbnormalSign.toString();
						neuroScreeningListAbnormalSignString = neuroScreeningListAbnormalSignString.replace("[", "");
						neuroScreeningListAbnormalSignString = neuroScreeningListAbnormalSignString.replace("]", "");

						neuroScreeningObjString += neuroScreeningListAbnormalSignString + ". ";
					}

					neuroScreeningStr += neuroScreeningObjString;
				}
				neuroScreenStr += neuroScreeningStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return neuroScreenStr;
	}

	// ROP Screening Detail Fetching Function
	public String getRopScreeningDetail(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String htmlNextLine = System.getProperty("line.separator");
		String ropScreenStr = "";
		try {
			String queryScreeningRop = "select obj from ScreenRop as obj where uhid='" + uhid + "'";
			List<ScreenRop> allRopScreeningList = inicuDao.getListFromMappedObjQuery(queryScreeningRop);
			if (!BasicUtils.isEmpty(allRopScreeningList)) {
				String ropScreeningStr = "Baby had " + allRopScreeningList.size()
						+ " ROP Screenings during the hospital stay. ";
				for (int i = 0; i < allRopScreeningList.size(); i++) {
					ScreenRop ropScreeningObj = (ScreenRop) allRopScreeningList.get(i);
					String ropScreeningObjString = htmlNextLine + (i + 1) + ". ROP Screening was done on "
							+ getDateFromTimestamp(ropScreeningObj.getScreening_time()) + "( CGA ";
					if (ropScreeningObj.getCga_weeks() != 0) {
						ropScreeningObjString += ropScreeningObj.getCga_weeks();
						if (ropScreeningObj.getCga_weeks() != 1) {
							ropScreeningObjString += " weeks ";
						} else {
							ropScreeningObjString += " week ";
						}
					}
					if (ropScreeningObj.getCga_days() != 0) {
						ropScreeningObjString += ropScreeningObj.getCga_days();
						if (ropScreeningObj.getCga_days() != 1) {
							ropScreeningObjString += " days ";
						} else {
							ropScreeningObjString += " day ";
						}
					}
					ropScreeningObjString += "and DOL# " + getDayOfLife(uhid, ropScreeningObj.getScreening_time())
							+ "). ";

					if (ropScreeningObj.getIs_rop() != null) {
						if (ropScreeningObj.getIs_rop()) {
							ropScreeningObjString += "There was evidence of ROP. ";
							if (ropScreeningObj.getIs_rop_left() != null) {
								if (ropScreeningObj.getIs_rop_left()) {
									ropScreeningObjString += " Baby had  ";
									if (!BasicUtils.isEmpty(ropScreeningObj.getRop_left_zone())) {
										ropScreeningObjString += "Zone " + ropScreeningObj.getRop_left_zone() + ", ";
									}
									if (!BasicUtils.isEmpty(ropScreeningObj.getRop_left_stage())) {
										ropScreeningObjString += "Stage " + ropScreeningObj.getRop_left_stage()
												+ " ROP ";
									}
									if (ropScreeningObj.getRop_left_plus() != null) {
										if (ropScreeningObj.getRop_left_plus()) {
											ropScreeningObjString += "with evidence of plus disesase ";
										} else {
											ropScreeningObjString += "with no evidence of plus disesase ";
										}
									}
									ropScreeningObjString += "in the left eye.";
								}
							}
							if (ropScreeningObj.getIs_rop_right() != null) {
								if (ropScreeningObj.getIs_rop_left()) {
									ropScreeningObjString += " and ";
								}
								if (ropScreeningObj.getIs_rop_right()) {
									if (!BasicUtils.isEmpty(ropScreeningObj.getRop_right_zone())) {
										ropScreeningObjString += "Zone " + ropScreeningObj.getRop_right_zone() + ", ";

									}
									if (!BasicUtils.isEmpty(ropScreeningObj.getRop_right_stage())) {
										ropScreeningObjString += "Stage " + ropScreeningObj.getRop_right_stage()
												+ " ROP ";
									}
									if (ropScreeningObj.getRop_right_plus() != null) {
										if (ropScreeningObj.getRop_right_plus()) {
											ropScreeningObjString += "with evidence of plus disesase ";
										} else {
											ropScreeningObjString += "with no evidence of plus disesase ";

										}
									}
									ropScreeningObjString += "in the left eye.";
								}
							}
						}
					} else {
						ropScreeningObjString = "There was no evidence of ROP. ";
					}

					List<String> ropTreatment = new ArrayList<>();
					if (ropScreeningObj.getLeft_laser() != null && ropScreeningObj.getLeft_laser()
							&& ropScreeningObj.getRight_laser() != null && ropScreeningObj.getRight_laser()) {
						ropTreatment.add("laser in the left & right eye");
					} else {
						if (ropScreeningObj.getLeft_laser() != null && ropScreeningObj.getLeft_laser()) {
							ropTreatment.add("laser in the left eye");
						}

						if (ropScreeningObj.getRight_laser() != null && ropScreeningObj.getRight_laser()) {
							ropTreatment.add("laser in the right eye");
						}
					}

					if (ropScreeningObj.getLeft_avastin() != null && ropScreeningObj.getLeft_avastin()
							&& ropScreeningObj.getRight_avastin() != null && ropScreeningObj.getRight_avastin()) {
						ropTreatment.add("avast in the left & right eye");
					} else {
						if (ropScreeningObj.getLeft_avastin() != null && ropScreeningObj.getLeft_avastin()) {
							ropTreatment.add("avast in the left eye");
						}

						if (ropScreeningObj.getRight_avastin() != null && ropScreeningObj.getRight_avastin()) {
							ropTreatment.add("avast in the right eye");
						}
					}

					if (ropScreeningObj.getLeft_surgery() != null && ropScreeningObj.getLeft_surgery()
							&& ropScreeningObj.getRight_surgery() != null && ropScreeningObj.getRight_surgery()) {
						ropTreatment.add("surgery in the left & right eye");
					} else {
						if (ropScreeningObj.getLeft_surgery() != null && ropScreeningObj.getLeft_surgery()) {
							ropTreatment.add("surgery in the left eye");
						}

						if (ropScreeningObj.getRight_surgery() != null && ropScreeningObj.getRight_surgery()) {
							ropTreatment.add("surgery in the right eye");
						}
					}

					if (!BasicUtils.isEmpty(ropTreatment)) {
						String ropTreatmentString = ropTreatment.toString();
						ropTreatmentString = ropTreatmentString.replace("[", "");
						ropTreatmentString = ropTreatmentString.replace("]", "");
						ropScreeningObjString += "Baby was treated with  " + ropTreatmentString + ". ";
					}

					if (!BasicUtils.isEmpty(ropScreeningObj.getRisk_factor())) {
						if (ropScreeningObj.getRisk_factor().contains(",")) {
							ropScreeningObjString += "Risk Factors were " + ropScreeningObj.getRisk_factor() + ". ";
						} else {
							ropScreeningObjString += "Risk Factor was " + ropScreeningObj.getRisk_factor() + ". ";
						}
					}
					ropScreeningStr += ropScreeningObjString;
				}

				ropScreenStr += ropScreeningStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ropScreenStr;
	}

	// Hearing Screening Detail Fetching Function
	public String getHearingScreeningDetail(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String htmlNextLine = System.getProperty("line.separator");
		String hearingScreenStr = "";
		try {
			String queryScreeningHearing = "select obj from ScreenHearing as obj where uhid='" + uhid + "'";
			List<ScreenHearing> allHearingScreeningList = inicuDao.getListFromMappedObjQuery(queryScreeningHearing);
			if (!BasicUtils.isEmpty(allHearingScreeningList)) {
				String hearingScreeningStr = "Baby had " + allHearingScreeningList.size()
						+ " Hearing Screenings during the hospital stay. ";
				for (int i = 0; i < allHearingScreeningList.size(); i++) {
					ScreenHearing hearingScreeningObj = (ScreenHearing) allHearingScreeningList.get(i);
					String hearingScreeningObjString = htmlNextLine + (i + 1) + ". Hearing Screening was done on "
							+ getDateFromTimestamp(hearingScreeningObj.getScreening_time()) + "( CGA ";
					if (hearingScreeningObj.getCga_weeks() != 0) {
						hearingScreeningObjString += hearingScreeningObj.getCga_weeks();
						if (hearingScreeningObj.getCga_weeks() != 1) {
							hearingScreeningObjString += " weeks ";
						} else {
							hearingScreeningObjString += " week ";
						}
					}
					if (hearingScreeningObj.getCga_days() != 0) {
						hearingScreeningObjString += hearingScreeningObj.getCga_days();
						if (hearingScreeningObj.getCga_days() != 1) {
							hearingScreeningObjString += " days ";
						} else {
							hearingScreeningObjString += " day ";
						}
					}
					hearingScreeningObjString += "and DOL# "
							+ getDayOfLife(uhid, hearingScreeningObj.getScreening_time()) + "). ";

					String indicationString = "High Risk";
					if (!BasicUtils.isEmpty(hearingScreeningObj.getIndication())) {
						indicationString = hearingScreeningObj.getIndication();
					} else {
						indicationString = "";
					}

					if (indicationString != "") {
						hearingScreeningObjString += "It was a " + indicationString + " Screening ";
					}

					if (indicationString.equalsIgnoreCase("High Risk")) {
						if (!BasicUtils.isEmpty(hearingScreeningObj.getRisk_factor())) {
							hearingScreeningObjString += "with " + hearingScreeningObj.getRisk_factor()
									+ " as risk factor. ";
						} else {
							hearingScreeningObjString += ". ";
						}
					} else {
						hearingScreeningObjString += ". ";
					}

					if (!BasicUtils.isEmpty(hearingScreeningObj.getScreening_test())) {
						String screeningTestString = hearingScreeningObj.getScreening_test();
						hearingScreeningObjString += screeningTestString + " screen testing was done ";
						List<String> screeningTestList = new ArrayList<>();
						if (screeningTestString.equalsIgnoreCase("Both")) {
							if (!BasicUtils.isEmpty(hearingScreeningObj.getOae_left())) {
								screeningTestList
										.add("Left ear (" + hearingScreeningObj.getOae_left() + ") during OAE test");
							}
							if (!BasicUtils.isEmpty(hearingScreeningObj.getOae_right())) {
								screeningTestList
										.add("Right ear (" + hearingScreeningObj.getOae_right() + ") during OAE test");
							}
							if (!BasicUtils.isEmpty(hearingScreeningObj.getAbr_left())) {
								screeningTestList
										.add("Left ear (" + hearingScreeningObj.getAbr_left() + ") during ABR test");
							}
							if (!BasicUtils.isEmpty(hearingScreeningObj.getAbr_right())) {
								screeningTestList
										.add("Right ear (" + hearingScreeningObj.getAbr_right() + ") during ABR test");
							}
						} else {
							if (screeningTestString.equalsIgnoreCase("OAE")) {
								if (!BasicUtils.isEmpty(hearingScreeningObj.getOae_left())) {
									screeningTestList.add("Left ear (" + hearingScreeningObj.getOae_left() + ")");
								}
								if (!BasicUtils.isEmpty(hearingScreeningObj.getOae_right())) {
									screeningTestList.add("Right ear (" + hearingScreeningObj.getOae_right() + ")");
								}
							}

							if (screeningTestString.equalsIgnoreCase("ABR")) {
								if (!BasicUtils.isEmpty(hearingScreeningObj.getAbr_left())) {
									screeningTestList.add("Left ear (" + hearingScreeningObj.getAbr_left() + ")");
								}
								if (!BasicUtils.isEmpty(hearingScreeningObj.getAbr_right())) {
									screeningTestList.add("Right ear (" + hearingScreeningObj.getAbr_right() + ")");
								}
							}
						}

						if (!BasicUtils.isEmpty(screeningTestList)) {
							String screeningTestListString = screeningTestList.toString();
							screeningTestListString = screeningTestListString.replace("[", "");
							screeningTestListString = screeningTestListString.replace("]", "");
							if (screeningTestString.equalsIgnoreCase("Both")) {
								hearingScreeningObjString += ". " + screeningTestListString + ". ";
							} else {
								hearingScreeningObjString += "with " + screeningTestListString + ". ";
							}
						}

						if (hearingScreeningObj.getTreatment_given()
								&& !BasicUtils.isEmpty(hearingScreeningObj.getTreatment())) {
							hearingScreeningObjString += "Baby was treated with  " + hearingScreeningObj.getTreatment()
									+ ". ";
						}
					}

					hearingScreeningStr += hearingScreeningObjString;
				}
				hearingScreenStr += hearingScreeningStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hearingScreenStr;
	}

	// Metabolic Screening Detail Fetching Function
	public String getMetabolicScreeningDetail(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String htmlNextLine = System.getProperty("line.separator");
		String metabolicScreenStr = "";
		try {
			String queryScreeningMetabolic = "select obj from ScreenMetabolic as obj where uhid='" + uhid + "'";
			List<ScreenMetabolic> allMetabolicScreeningList = inicuDao
					.getListFromMappedObjQuery(queryScreeningMetabolic);
			if (!BasicUtils.isEmpty(allMetabolicScreeningList)) {
				String metabolicScreeningStr = "Baby had " + allMetabolicScreeningList.size()
						+ " Metabolic Screenings during the hospital stay. ";
				for (int i = 0; i < allMetabolicScreeningList.size(); i++) {
					ScreenMetabolic metabolicScreeningObj = (ScreenMetabolic) allMetabolicScreeningList.get(i);
					String metabolicScreenName = "Metabolic Screening";
					if (metabolicScreeningObj.getScreening_panel() != null
							&& metabolicScreeningObj.getScreening_panel()) {
						metabolicScreenName = "Metabolic Screening (123 Conditions) ";
					}

					if (metabolicScreeningObj.getMetabolic_screening() != null) {
						metabolicScreenName = "Metabolic Screening (8 Tests) ";
					}

					String metabolicScreeningObjString = htmlNextLine + (i + 1) + ". " + metabolicScreenName
							+ "was done on " + getDateFromTimestamp(metabolicScreeningObj.getScreening_time())
							+ "( CGA ";
					if (metabolicScreeningObj.getCga_weeks() != 0) {
						metabolicScreeningObjString += metabolicScreeningObj.getCga_weeks();
						if (metabolicScreeningObj.getCga_weeks() != 1) {
							metabolicScreeningObjString += " weeks ";
						} else {
							metabolicScreeningObjString += " week ";
						}
					}
					if (metabolicScreeningObj.getCga_days() != 0) {
						metabolicScreeningObjString += metabolicScreeningObj.getCga_days();
						if (metabolicScreeningObj.getCga_days() != 1) {
							metabolicScreeningObjString += " days ";
						} else {
							metabolicScreeningObjString += " day ";
						}
					}
					metabolicScreeningObjString += "and DOL# "
							+ getDayOfLife(uhid, metabolicScreeningObj.getScreening_time()) + ")";

					if (metabolicScreenName.equalsIgnoreCase("Meatbolic Screening (8 Tests)")
							&& !BasicUtils.isEmpty(metabolicScreeningObj.getMetabolic_screening())) {
						metabolicScreeningObjString += " and investigation of "
								+ metabolicScreeningObj.getMetabolic_screening() + " was ordered";
					}
					metabolicScreeningObjString += ". ";
					metabolicScreeningStr += metabolicScreeningObjString;
				}
				metabolicScreenStr += metabolicScreeningStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metabolicScreenStr;
	}

	// Cranial USG Screening Detail Fetching Function
	public String getCranialScreeningDetail(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		String htmlNextLine = System.getProperty("line.separator");
		String cranialScreenStr = "";
		try {
			String queryScreeningCranial = "select obj from ScreenUSG as obj where uhid='" + uhid + "'";
			List<ScreenUSG> allCranialScreeningList = inicuDao.getListFromMappedObjQuery(queryScreeningCranial);
			if (!BasicUtils.isEmpty(allCranialScreeningList)) {
				String cranialScreeningStr = "Baby had " + allCranialScreeningList.size()
						+ " Cranial USG Screenings during the hospital stay. ";
				for (int i = 0; i < allCranialScreeningList.size(); i++) {
					ScreenUSG cranialScreeningObj = (ScreenUSG) allCranialScreeningList.get(i);
					String cranialScreeningObjString = htmlNextLine + (i + 1) + ". Cranial USG Screening was done on "
							+ getDateFromTimestamp(cranialScreeningObj.getScreening_time()) + "( CGA ";
					if (cranialScreeningObj.getCga_weeks() != 0) {
						cranialScreeningObjString += cranialScreeningObj.getCga_weeks();
						if (cranialScreeningObj.getCga_weeks() != 1) {
							cranialScreeningObjString += " weeks ";
						} else {
							cranialScreeningObjString += " week ";
						}
					}
					if (cranialScreeningObj.getCga_days() != 0) {
						cranialScreeningObjString += cranialScreeningObj.getCga_days();
						if (cranialScreeningObj.getCga_days() != 1) {
							cranialScreeningObjString += " days ";
						} else {
							cranialScreeningObjString += " day ";
						}
					}
					cranialScreeningObjString += "and DOL# "
							+ getDayOfLife(uhid, cranialScreeningObj.getScreening_time()) + "). ";

					if (!BasicUtils.isEmpty(cranialScreeningObj.getBrain_parenchyma())) {
						cranialScreeningObjString += "Brain Parenchyma was "
								+ cranialScreeningObj.getBrain_parenchyma();
						if (!BasicUtils.isEmpty(cranialScreeningObj.getParenchyma_description())) {
							cranialScreeningObjString += " (" + cranialScreeningObj.getParenchyma_description() + "). ";
						} else {
							cranialScreeningObjString += ". ";
						}
					}

					if (!BasicUtils.isEmpty(cranialScreeningObj.getLateral_ventricle())) {
						cranialScreeningObjString += "Lateral Ventricle was "
								+ cranialScreeningObj.getLateral_ventricle();
						List<String> ventricleList = new ArrayList<>();
						if (cranialScreeningObj.getRight_ventricle() != null) {
							ventricleList.add(
									"Right Ventricle " + cranialScreeningObj.getRight_ventricle().toString() + "mm ");
						}
						if (cranialScreeningObj.getLeft_ventricle() != null) {
							ventricleList.add(
									"Left Ventricle " + cranialScreeningObj.getLeft_ventricle().toString() + "mm ");
						}
						if (cranialScreeningObj.getThird_ventricle() != null) {
							ventricleList.add(
									"Third Ventricle " + cranialScreeningObj.getThird_ventricle().toString() + "mm ");
						}

						if (!BasicUtils.isEmpty(ventricleList)) {
							String ventricleListString = ventricleList.toString();
							ventricleListString = ventricleListString.replace("[", "");
							ventricleListString = ventricleListString.replace("]", "");
							cranialScreeningObjString += "(" + ventricleListString + ")";
						}
						cranialScreeningObjString += ". ";
					}

					if (!BasicUtils.isEmpty(cranialScreeningObj.getIvh_type())) {
						cranialScreeningObjString += "Baby had " + cranialScreeningObj.getIvh_type();
						if (!BasicUtils.isEmpty(cranialScreeningObj.getIvh_side())) {
							cranialScreeningObjString += " on " + cranialScreeningObj.getIvh_side();
						}

						if (!BasicUtils.isEmpty(cranialScreeningObj.getIvh_description())) {
							cranialScreeningObjString += "(" + cranialScreeningObj.getIvh_description() + ")";
						}

						cranialScreeningObjString += ". ";
					}

					if (!BasicUtils.isEmpty(cranialScreeningObj.getVentriculomegaly())) {
						cranialScreeningObjString += "Baby had " + cranialScreeningObj.getVentriculomegaly()
								+ " Ventriculomegaly";
						if (!BasicUtils.isEmpty(cranialScreeningObj.getPvl_type())) {
							cranialScreeningObjString += " with " + cranialScreeningObj.getPvl_type();
						}

						if (!BasicUtils.isEmpty(cranialScreeningObj.getPvl_description())) {
							cranialScreeningObjString += "(" + cranialScreeningObj.getPvl_description() + ")";
						} else {
							cranialScreeningObjString += ". ";
						}
					}

					cranialScreeningStr += cranialScreeningObjString;
				}
				cranialScreenStr += cranialScreeningStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cranialScreenStr;
	}

	@Override
	public HashMap<Object, Object> getDischargeSummaryPrintRecords(String uhid) {
		HashMap<Object, Object> printRecords = new HashMap<Object, Object>();
		// get patient details...
		String queryPatientDetails = "select obj from PatientdischargePage1 obj where uhid ='" + uhid + "'";
		List<PatientdischargePage1> patientInfo = patientDao.getListFromMappedObjNativeQuery(queryPatientDetails);
		if (patientInfo != null && patientInfo.size() > 0) {
			printRecords.put("patientDetails", patientInfo.get(0));

			// get notes and medicine details...
			String queryNotesDetails = "select obj.doctornotes,obj.diagnosis,obj.issues,obj.plan from PatientdischargePage2 as obj "
					+ "where uhid='" + uhid + "'";
			List<Object[]> notesInfo = patientDao.getListFromMappedObjNativeQuery(queryNotesDetails);
			HashMap<String, List<String>> notesObject = new HashMap<String, List<String>>();
			Iterator<Object[]> iterator = notesInfo.iterator();
			List<String> notes = new ArrayList<String>();
			List<String> diagnosis = new ArrayList<String>();
			List<String> issues = new ArrayList<String>();
			List<String> plans = new ArrayList<String>();
			while (iterator.hasNext()) {
				Object[] doctorNotesObj = iterator.next();
				if (!BasicUtils.isEmpty(doctorNotesObj[0]) && !(doctorNotesObj[0] + "").equalsIgnoreCase("-")) {
					notes.add(doctorNotesObj[0] + "");
				}
				if (!BasicUtils.isEmpty(doctorNotesObj[1]) && !(doctorNotesObj[1] + "").equalsIgnoreCase("-")) {
					diagnosis.add(doctorNotesObj[1] + "");
				}
				if (!BasicUtils.isEmpty(doctorNotesObj[2]) && !(doctorNotesObj[2] + "").equalsIgnoreCase("-")) {
					issues.add(doctorNotesObj[2] + "");
				}
				if (!BasicUtils.isEmpty(doctorNotesObj[3]) && !(doctorNotesObj[3] + "").equalsIgnoreCase("-")) {
					plans.add(doctorNotesObj[3] + "");
				}

			}
			notesObject.put("notes", notes);
			notesObject.put("issues", issues);
			notesObject.put("diagnosis", diagnosis);
			notesObject.put("plans", plans);

			printRecords.put("notesDetails", notesObject);

			// get medical history..
			String queryMedicalHistory = "select obj from PatientdischargeMedicalhistory obj where uhid ='" + uhid
					+ "'";
			List<PatientdischargeMedicalhistory> medicalHistory = patientDao
					.getListFromMappedObjNativeQuery(queryMedicalHistory);
			printRecords.put("medicineDetails", medicalHistory);
		}
		return printRecords;
	}

	@Override
	public ResponseMessageWithResponseObject saveDischargeSummary(DischargedSummaryObj dischargedSummary) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();

		try {
			String uhid = dischargedSummary.getUhid();
			DischargeNotesDetail dischargeNotes = dischargedSummary.getDischargeNotes();
			dischargeNotes.setPrevProvisionalNotesRds(dischargeNotes.getProvisionalNotesRds());
			dischargeNotes.setPrevProvisionalNotesApnea(dischargeNotes.getProvisionalNotesApnea());
			dischargeNotes.setPrevProvisionalNotesPphn(dischargeNotes.getProvisionalNotesPphn());
			dischargeNotes.setPrevProvisionalNotesPneumothorax(dischargeNotes.getProvisionalNotesPneumothorax());
			dischargeNotes.setPrevProvisionalNotesJaundice(dischargeNotes.getProvisionalNotesJaundice());
			dischargeNotes.setPrevProvisionalNotesShock(dischargeNotes.getProvisionalNotesShock());
			dischargeNotes.setPrevProvisionalNotesFeedIntolerance(dischargeNotes.getProvisionalNotesFeedIntolerance());
			dischargeNotes.setPrevProvisionalNotesAsphyxia(dischargeNotes.getProvisionalNotesAsphyxia());
			dischargeNotes.setPrevProvisionalNotesSeizure(dischargeNotes.getProvisionalNotesSeizure());
			dischargeNotes.setPrevProvisionalNotesEncephalopathy(dischargeNotes.getProvisionalNotesEncephalopathy());
			dischargeNotes.setPrevProvisionalNotesIvh(dischargeNotes.getProvisionalNotesIvh());
			dischargeNotes.setPrevProvisionalNotesHydrocephalus(dischargeNotes.getProvisionalNotesHydrocephalus());
			dischargeNotes.setPrevProvisionalNotesNeuromuscularDisorder(dischargeNotes.getProvisionalNotesNeuromuscularDisorder());
			dischargeNotes.setPrevProvisionalNotesSepsis(dischargeNotes.getProvisionalNotesSepsis());
			dischargeNotes.setPrevProvisionalNotesNec(dischargeNotes.getProvisionalNotesNec());
			dischargeNotes.setPrevProvisionalNotesClabsi(dischargeNotes.getProvisionalNotesClabsi());
			dischargeNotes.setPrevProvisionalNotesvap(dischargeNotes.getProvisionalNotesVap());
			dischargeNotes.setPrevProvisionalNotesIntrauterine(dischargeNotes.getProvisionalNotesIntrauterine());
			dischargeNotes.setPrevProvisionalNotesMiscellaneous(dischargeNotes.getProvisionalNotesMiscellaneous());
			dischargeNotes.setPrevProvisionalNotesHypoglycemia(dischargeNotes.getPrevProvisionalNotesHypoglycemia());
			dischargeNotes.setUhid(uhid);
			inicuDao.saveObject(dischargeNotes);

			// save AdviceOnDischarge
//			if(dischargedSummary.getAdviceSelected()==true){
			List<DischargeAdviceDetail> dischargeAdviceDetail = dischargedSummary.getAdviceOnDischarge();
			inicuDao.saveMultipleObject(dischargeAdviceDetail);
//			}

			// update the final Diagonis string if changed
			if (dischargedSummary.getFinalDiagnosis() != null && dischargedSummary.getDiagnosisChanged() == true) {
				String updateDiagnosis = "update AdmissionNotes set diagnosis='" + dischargedSummary.getFinalDiagnosis()
						+ "' where uhid='" + uhid + "'";
				inicuDao.updateOrDeleteQuery(updateDiagnosis);
			}

			if (dischargedSummary.getDoctorChanged() != null && dischargedSummary.getDoctorChanged() == true) {
				String updateDiagnosis = "update BabyDetail set admittingdoctor='"
						+ dischargedSummary.getBabyDetails().getAdmittingdoctor() + "' where uhid='" + uhid + "'";
				inicuDao.updateOrDeleteQuery(updateDiagnosis);
			}

			Boolean isBabyDischarge = dischargedSummary.getIsDischargeBaby();
			if (!BasicUtils.isEmpty(isBabyDischarge)) {
				Timestamp dischargeDate = new Timestamp(new Date().getTime());
				// add discharge changes to the baby details and free the bed...
				String queryGetBabyDetails = "select obj from BabyDetail obj where uhid='" + dischargedSummary.getUhid()
						+ "' order by creationtime desc";
				List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(queryGetBabyDetails);
				if (babyDetailList != null && babyDetailList.size() > 0) {
					BabyDetail babyDetail = babyDetailList.get(0);
					babyDetail.setAdmissionstatus(false);
					babyDetail.setDischargestatus(dischargedSummary.getBabyStatus() + "");
					Timestamp dischargeDateFromOutcome = null;
					if (dischargedSummary.getOutcome() != null) {
						dischargeDateFromOutcome = dischargedSummary.getOutcome().getEntrytime();
					}
					if (!BasicUtils.isEmpty(dischargeDateFromOutcome))
						babyDetail.setDischargeddate(dischargeDateFromOutcome);
					else
						babyDetail.setDischargeddate(dischargeDate);
					if (!BasicUtils.isEmpty(dischargedSummary.getOutcome().getOutcomeType()))
						babyDetail.setDischargestatus(dischargedSummary.getOutcome().getOutcomeType());

					patientDao.saveObject(babyDetailList.get(0));

					String queryRefBed = "select obj from RefBed obj where bedid='"
							+ babyDetailList.get(0).getNicubedno() + "'";
					List<RefBed> refBedList = patientDao.getListFromMappedObjNativeQuery(queryRefBed);
					if (refBedList != null && refBedList.size() > 0) {
						refBedList.get(0).setStatus(true);
						patientDao.saveObject(refBedList.get(0));
					}

					// free connected device..
					try {
						String query = "SELECT obj FROM BedDeviceDetail as obj WHERE uhid='" + uhid + "' "
								+ "AND status = 'true' AND time_to is null order by creationtime desc";
						List<BedDeviceDetail> resultSet = inicuDao.getListFromMappedObjQuery(query);
						if (!BasicUtils.isEmpty(resultSet)) {
							BedDeviceDetail dev = resultSet.get(0);
							dev.setStatus(false);
							if (!BasicUtils.isEmpty(dischargeDateFromOutcome))
								dev.setTime_to(dischargeDateFromOutcome);
							else
								dev.setTime_to(new Timestamp(new Date().getTime()));

							patientDao.saveObject(dev);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// free the device data from the main Tables and shift to dummy table
					try{
						// - Monitor

						// insert
						String insertMonitorData="insert into device_monitor_detail_dump ( select * from device_monitor_detail where uhid ='"+uhid+"')";
						patientDao.executeInsertQuery(insertMonitorData);

						// delete the record from main table
						String deleteMonitorData="delete from device_monitor_detail where uhid='"+uhid+"'";
						inicuDao.updateOrDeleteNativeQuery(deleteMonitorData);


						// - Ventilator

						// insert
						String insertVentilatorData="insert into device_ventilator_detail_dump ( select * from device_ventilator_detail where uhid ='"+uhid+"')";
						patientDao.executeInsertQuery(insertVentilatorData);

						// delete the record from main table
						String deleteVentilatorData="delete from device_ventilator_detail where uhid='"+uhid+"'";
						inicuDao.updateOrDeleteNativeQuery(deleteVentilatorData);

						System.out.println("Update the main table and dummy Table");
					}catch (Exception e){
						e.printStackTrace();
					}

					// remove the Job Runnning On ICHR
					remoteViewService.deleteJobForDischargedBaby(uhid);
				}
			}

			//// fetch the previous list of advice on discharge
			List<DischargeAdviceDetail> prevDischargeAdvice = new ArrayList<>();
			String fetchPrevAdviceDetail = "Select obj from DischargeAdviceDetail as obj where uhid='" + uhid + "' order by discharge_advice_id";
			prevDischargeAdvice = inicuDao.getListFromMappedObjQuery(fetchPrevAdviceDetail);
			response.setReturnedObject(prevDischargeAdvice);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	@Override
	public List<DashboardJSON> getHl7PatientsList() {

		List<DashboardJSON> dashList = new ArrayList<DashboardJSON>();
		try {
			String query = "select obj from BabyDetail obj where nicubedno is null";
			List<BabyDetail> list = patientDao.getListFromMappedObjNativeQuery(query);
			if (!BasicUtils.isEmpty(list)) {
				Iterator<BabyDetail> iterator = list.iterator();
				while (iterator.hasNext()) {
					DashboardJSON dash = new DashboardJSON();
					BabyDetail babyDetails = iterator.next();
					dash.setUhid(babyDetails.getUhid());
					dash.setDob(babyDetails.getDateofbirth() + "");
					dash.setAdmitDate(babyDetails.getDateofadmission() + "");
					dash.setName(babyDetails.getBabyname() + "");
					dash.setBirthWeight(babyDetails.getBirthweight() + "");
					dash.setGender(babyDetails.getGender());
					// dash.setGestation(babyDetails.getGestationweekbylmp()+babyDetails.getGestationdaysbylmp()+"");
					dashList.add(dash);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dashList;
	}

	@Override
	public ResponseMessageWithResponseObject uplodaBabyDocument(String file, String uhid, String title) {

		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();

		// check if file with this title already exists
		String query = "select obj from BabyAddinfo as obj where isactive = 'true' and uhid='" + uhid
				+ "' and docTitle='" + title + "'";
		List<BabyAddinfo> babyAddInfoList = inicuDao.getListFromMappedObjQuery(query);
		if (!BasicUtils.isEmpty(babyAddInfoList)) {
			response.setMessage("Baby Document with this title already exists..!!");
			response.setType(BasicConstants.MESSAGE_FAILURE);
		} else {

			JSONObject jsonObj;
			String documentKey = "";
			String documentName = "";
			Object fileName = null;
			Object fileData = null;
			try {
				jsonObj = new JSONObject(file);
				if (jsonObj.get("fileData") != null)
					fileData = jsonObj.get("fileData");
				if (jsonObj.get("fileName") != null)
					fileName = jsonObj.get("fileName");
				String fileNameTemp = (fileName + "").substring(0, (fileName + "").indexOf("."));
				String fileTypeTemp = (fileName + "").substring((fileName + "").indexOf(".") + 1,
						(fileName + "").length());

				documentKey = fileNameTemp + "_" + title + "." + fileTypeTemp;
				documentName = fileName + "";
				fileName = documentKey;
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			File documentDir = new File(BasicConstants.WORKING_DIR + "/" + BasicConstants.UPLOAD_DOCUMENT_DIR);

			if (!documentDir.exists()) {
				try {
					System.out.println("creating image directory:");
					documentDir.mkdir();
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
				}
			}

			File babyDocDir = new File(
					BasicConstants.WORKING_DIR + "/" + BasicConstants.UPLOAD_DOCUMENT_DIR + "/" + uhid);
			if (!babyDocDir.exists()) {
				try {
					System.out.println("creating image directory:");
					babyDocDir.mkdir();
				} catch (Exception e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
				}
			}

			FileOutputStream osf = null;
			try {
				Base64 decoder = new Base64();
				if (!BasicUtils.isEmpty(fileData + "")) {
					String value = fileData.toString().split("base64,")[1];
					byte[] imgBytes = decoder.decode(value);

					osf = new FileOutputStream(new File(BasicConstants.WORKING_DIR + "/"
							+ BasicConstants.UPLOAD_DOCUMENT_DIR + "/" + uhid + "/" + fileName));
					osf.write(imgBytes);
					osf.flush();

					// save file title to the database with its some info...
					BabyAddinfo babyAddInfo = new BabyAddinfo();
					babyAddInfo.setDocName(documentName);
					babyAddInfo.setDocTitle(title);
					babyAddInfo.setIsactive(true);
					babyAddInfo.setUhid(uhid);
					babyAddInfo.setDocumentkey(documentKey);
					inicuDao.saveObject(babyAddInfo);
					response.setMessage("File uploaded successfully..!!");
					response.setType(BasicConstants.MESSAGE_SUCCESS);
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.setMessage(e.getMessage());

			} finally {
				try {
					osf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	@Override
	public List<HashMap<Object, Object>> getBabyDocuments(String uhid) {

		List<HashMap<Object, Object>> babyDocData = new ArrayList<HashMap<Object, Object>>();

		File directory = new File(BasicConstants.WORKING_DIR + "/" + BasicConstants.UPLOAD_DOCUMENT_DIR + "/" + uhid);
		if (directory.exists()) {
			HashMap<String, BabyAddinfo> mapBabyDocInfo = new HashMap<String, BabyAddinfo>();
			String query = "select obj from BabyAddinfo as obj where isactive = 'true' and uhid='" + uhid + "'";
			List<BabyAddinfo> babyAddInfoList = inicuDao.getListFromMappedObjQuery(query);
			if (!BasicUtils.isEmpty(babyAddInfoList)) {
				String fileName;
				for (BabyAddinfo babyAddInfo : babyAddInfoList) {

					mapBabyDocInfo.put(babyAddInfo.getDocumentkey(), babyAddInfo);
				}

			}
			File[] direcotryList = directory.listFiles(); // gives list of
			// directories
			// inside
			// iNicuRecords
			// directory

			try {
				for (int i = 0; i < direcotryList.length; i++) { // iterate over
					// list to
					// find the
					File fnew = direcotryList[i];

					if (fnew.exists()) {
						// file should exists in the hash map of table infor..
						HashMap<Object, Object> obj = new HashMap<Object, Object>();
						if (mapBabyDocInfo.get(fnew.getName()) != null) {
							obj.put("title", mapBabyDocInfo.get(fnew.getName()).getDocTitle());

							String encodedStr = java.util.Base64.getEncoder()
									.encodeToString(Files.readAllBytes(fnew.toPath()));
							obj.put("fileName", mapBabyDocInfo.get(fnew.getName()).getDocName());
							String fileName = fnew.getName();
							String fileType = fileName.substring(fileName.indexOf(".")).replace(".", "");
							String type = "";
							if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg")
									|| fileType.equalsIgnoreCase("png"))
								fileType = "image/" + fileType;
							else if (fileType.equalsIgnoreCase("pdf"))
								fileType = "application/pdf";
							else if (fileType.equalsIgnoreCase("csv"))
								fileType = "application/vnd.ms-excel";

							obj.put("value", "data:" + fileType + ";base64," + encodedStr);
							obj.put("id", mapBabyDocInfo.get(fnew.getName()).getAddinfoid());
							babyDocData.add(obj);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return babyDocData;
	}

	@Override
	public ResponseMessageWithResponseObject saveBallardScore(ScoreBallard ballardScore) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {

			ballardScore = (ScoreBallard) inicuDao.saveObject(ballardScore);

			String queryBallardScore = "select obj from ScoreBallard as obj where uhid = '" + ballardScore.getUhid()
					+ "'";
			List<ScoreBallard> listBallardScore = inicuDao.getListFromMappedObjQuery(queryBallardScore);
			if (!BasicUtils.isEmpty(listBallardScore)) {
				// patientInfoAddChildObj.setBallardScorePastList(listBallardScore);
				ballardScore = listBallardScore.get(0);
			}
			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Ballard Score Saved Successfully..!!");
			response.setReturnedObject(ballardScore);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject saveApgarScore(ApgarScoreObj apgarScore) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		try {

			String uhid = "";
			ScoreApgar oneMinAgar = apgarScore.getOneMin();
			if (!BasicUtils.isEmpty(oneMinAgar)) {

				if (oneMinAgar.getApgarTotalScore() != null) {
					oneMinAgar.setApgarTime(1);
					oneMinAgar = (ScoreApgar) inicuDao.saveObject(oneMinAgar);
					uhid = oneMinAgar.getUhid();
				}
			}
			ScoreApgar fiveMinAgar = apgarScore.getFiveMin();
			if (!BasicUtils.isEmpty(fiveMinAgar)) {
				if (fiveMinAgar.getApgarTotalScore() != null) {
					fiveMinAgar.setApgarTime(5);
					fiveMinAgar = (ScoreApgar) inicuDao.saveObject(fiveMinAgar);
					uhid = fiveMinAgar.getUhid();
				}

			}
			ScoreApgar tenMinApgar = apgarScore.getTenMin();
			if (!BasicUtils.isEmpty(tenMinApgar)) {

				if (tenMinApgar.getApgarTotalScore() != null) {
					tenMinApgar.setApgarTime(10);
					tenMinApgar = (ScoreApgar) inicuDao.saveObject(tenMinApgar);
					uhid = tenMinApgar.getUhid();
				}
			}
			// set of new time apgar ..

			if (!BasicUtils.isEmpty(uhid)) {
				String queryApgarScore = "select obj from ScoreApgar as obj where uhid='" + uhid + "'";
				List<ScoreApgar> listApgarScore = inicuDao.getListFromMappedObjQuery(queryApgarScore);
				if (!BasicUtils.isEmpty(listApgarScore)) {

					for (ScoreApgar apgar : listApgarScore) {
						if (!BasicUtils.isEmpty(apgar.getApgarTime())) {

							if (apgar.getApgarTime() == 1) {
								oneMinAgar = apgar;
							} else if (apgar.getApgarTime() == 5) {
								fiveMinAgar = apgar;
							} else if (apgar.getApgarTime() == 10) {
								tenMinApgar = apgar;
							}
						}
					}
				}
			}

			response.setType(BasicConstants.MESSAGE_SUCCESS);
			response.setMessage("Apgar Score Saved Successfully..!!");
			apgarScore.setOneMin(oneMinAgar);
			apgarScore.setFiveMin(fiveMinAgar);
			apgarScore.setTenMin(tenMinApgar);
			response.setReturnedObject(apgarScore);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ResponseMessageWithResponseObject deleteBabyDocument(String docId, String loggedUserId) {
		ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
		String queryDeleteDoc = "update BabyAddinfo set isactive='false' where addinfoid='" + docId + "'";
		inicuDao.updateOrDeleteQuery(queryDeleteDoc);
		response.setMessage("document deleted..!!");
		response.setType(BasicConstants.MESSAGE_SUCCESS);
		return response;
	}

	@Override
	public BabyDetail getBabyDetails(String uhid) throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		BabyDetail babyDetail = null;
		try {
			String getDetails = "Select obj from BabyDetail as obj where uhid='" + uhid + "'";
			List<BabyDetail> listBabyDetail = patientDao.getListFromMappedObjNativeQuery(getDetails);
			if (!BasicUtils.isEmpty(listBabyDetail)) {
				if (!BasicUtils.isEmpty(listBabyDetail.get(0)))
					babyDetail = listBabyDetail.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return babyDetail;
	}

	@Override
	public void updateDetailsOnReadmit(BabyDetail babyDetail, String newbedno, java.sql.Date reAdmissionDate)
			throws InicuDatabaseExeption {
		// TODO Auto-generated method stub
		try {
			String uhid = babyDetail.getUhid().toString().trim();

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date utilDischargeDate;
			utilDischargeDate = dateFormat1.parse(babyDetail.getDischargeddate().toString());
			java.sql.Date dischargeDate = new java.sql.Date(utilDischargeDate.getTime());

			java.util.Date utilAdmissionDate;
			utilAdmissionDate = dateFormat1.parse(babyDetail.getDateofadmission().toString());
			java.sql.Date admissionDate = new java.sql.Date(utilAdmissionDate.getTime());

			ReadmitHistory readmitHistory = new ReadmitHistory();
			readmitHistory.setAdmissiondate(admissionDate);
			readmitHistory.setUhid(uhid);
			readmitHistory.setDischargeddate(dischargeDate);
			readmitHistory.setReadmissiondate(reAdmissionDate);
			readmitHistory.setNicubedno(babyDetail.getNicubedno().trim());
			readmitHistory.setNicuroomno(babyDetail.getNicuroomno().trim());
			readmitHistory.setNiculevelno(babyDetail.getNiculevelno().trim());
			readmitHistory.setCriticalitylevel(babyDetail.getCriticalitylevel().trim());
			patientDao.saveObject(readmitHistory);

			String queryAdmStatus = "Update BabyDetail set admissionstatus='true', nicubedno='" + newbedno
					+ "' where uhid='" + uhid + "'";
			patientDao.updateOrDeleteQuery(queryAdmStatus);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<Object, Object> printAssessmentProgressNotes(AssessmentPrintInfoObject printObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseMessageWithResponseObject submitAdvanceAdmissionForm(AdvanceAdmitPatientPojo registrationObj,
			String loggedUser) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();

		BabyDetail babyObj = registrationObj.getBabyDetailObj();

		if (!BasicUtils.isEmpty(babyObj) && !BasicUtils.isEmpty(babyObj.getBranchname()) && babyObj.getBranchname().equalsIgnoreCase("Moti Nagar, Delhi")) {
			try {
				ParentDetail parentDetail = registrationObj.getPersonalDetailObj();
				String companyId = BasicConstants.ICHR_SCHEMA;;
				String Uhid = babyObj.getUhid();
				String fname = babyObj.getBabyname().trim();

				if(!BasicUtils.isEmpty(fname)){
					fname = fname.replaceAll(" ","%20");
				}
				String gender = babyObj.getGender();

				String pattern = "yyyy-MM-dd";
				String date = "";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


				if(!BasicUtils.isEmpty(babyObj.getDateofbirth())){
					date = simpleDateFormat.format(babyObj.getDateofbirth());
					System.out.println(date);
				}else{
					Date date1 = simpleDateFormat.parse(babyObj.getDobStr());
					date = simpleDateFormat.format(date1);
					System.out.println(date);
				}

				String DOB = date;
				String lname = babyObj.getBabyname().trim();

				if(!BasicUtils.isEmpty(lname)){
					lname = lname.replaceAll(" ","%20");
				}

				lname =  "";

				String phoneNumber = "";
				if (!BasicUtils.isEmpty(parentDetail.getMother_phone())) {
					phoneNumber = parentDetail.getMother_phone().trim();
				} else if (!BasicUtils.isEmpty(parentDetail.getFather_phone())) {
					phoneNumber = parentDetail.getFather_phone().trim();
				}

				String email = "";
				if (!BasicUtils.isEmpty(parentDetail.getMother_email())) {
					email = parentDetail.getMother_email().trim();
				} else if (!BasicUtils.isEmpty(parentDetail.getFather_email())) {
					email = parentDetail.getFather_email().trim();
				}

				String babyGestation = "";
				if(!BasicUtils.isEmpty(babyObj.getActualgestationweek())){
					babyGestation= babyObj.getActualgestationweek().toString();
				}else if(!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())){
					babyGestation= babyObj.getGestationweekbylmp().toString();
				}

				if(BasicConstants.ICHR_SETTINGS_ENABLED) {
					if (!BasicUtils.isEmpty(Uhid) && !BasicUtils.isEmpty(companyId) && !BasicUtils.isEmpty(fname) && !BasicUtils.isEmpty(gender) && !BasicUtils.isEmpty(DOB) && !BasicUtils.isEmpty(phoneNumber)) {
						String parameters = "uid=" + Uhid + "&companyId=" + companyId + "&fname=" + fname + "&lname=" + lname + "&gender=" + gender + "&DOB=" + DOB + "&phonenumber=" + phoneNumber + "&babyGestation=" + babyGestation;
						System.out.println("Parameters are :" + parameters);
						String response = null;
						try {
							response = SimpleHttpClient.executeHttpGet(BasicConstants.ADMIT_PATIENT + parameters);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("Post Result string returned" + response);
					} else {
						String parameters = "uid=" + Uhid + "&companyId=" + companyId + "&fname=" + fname + "&lname=" + lname + "&gender=" + gender + "&DOB=" + DOB + "&phonenumber=" + phoneNumber + "&babyGestation=" + babyGestation;
						System.out.println("Parameters are :" + parameters);
					}
				}
			}catch (Exception e){
				System.out.println("Exception caught :"+e);
			}
		}

		String fromWhere = registrationObj.getFromWhere();
		BirthToNicu birthToNicuObj =registrationObj.getBirthToNicuObj();
		AntenatalHistoryDetail antenatalTempHistory=registrationObj.getAntenatalHistoryObj();
		AntenatalSteroidDetail firstSteroidObjTemp = antenatalTempHistory.getFirstSteroidDetail();

		String branchName = babyObj.getBranchname();
		String tempUHIDQuery = "SELECT tempuhidtoggle FROM ref_hospitalbranchname WHERE branchname='" + branchName + "'";
		String tempuhidtoggle = "";
		List<Object[]> tempUHIDQueryResultSet = inicuDao.getListFromNativeQuery(tempUHIDQuery);
		if (!BasicUtils.isEmpty(tempUHIDQueryResultSet)) {
			tempuhidtoggle = String.valueOf(tempUHIDQueryResultSet.get(0));
		}

		String uhid = babyObj.getUhid();
		boolean isToCheckMandatory = false;
		if (!BasicUtils.isEmpty(fromWhere) && fromWhere.equalsIgnoreCase("systemic")) {
			isToCheckMandatory = true;
//			isToCheckMandatory = !fromWhere.equalsIgnoreCase("reasonForAdmission");
		}
		// Check the mandatory fields first
		if(babyObj.getIsassessmentsubmit()!=null && babyObj.getIsassessmentsubmit()==true
			&& isToCheckMandatory){
			AntenatalHistoryDetail antenatalObj=registrationObj.getAntenatalHistoryObj();
			BirthToNicu birthToNicu=registrationObj.getBirthToNicuObj();
			BabyDetail babyDetail = registrationObj.getBabyDetailObj();

			String ErrorMessage="";
			int errorCount=0;
			boolean mandatoryMissing=false;

			if (BasicUtils.isEmpty(babyObj.getBirthweight())) {
				if(babyObj.getIsassessmentsubmit()!=null && babyObj.getIsassessmentsubmit()==true) {
					babyObj.setIsassessmentsubmit(false);
				} else {
					ErrorMessage+="Missing Birth Weight.";
					mandatoryMissing=true;
					errorCount++;
				}
			}

			//Umbilical Doppler - null case
			if(BasicUtils.isEmpty(antenatalObj.getUmbilicalDoppler())){
				if(ErrorMessage!=""){
					ErrorMessage+=", Umbilical Doppler ";
				}else{
					ErrorMessage+="Umbilical Doppler ";
				}
				mandatoryMissing=true;
				errorCount++;
			}


			// MgSO4 - null case
			if(BasicUtils.isEmpty(antenatalObj.getMgso4())){
				if(ErrorMessage!=""){
					ErrorMessage+=", MgSo4 ";
				}else{
					ErrorMessage+="MgSo4 ";
				}
				mandatoryMissing=true;
				errorCount++;
			}

			// Mode of Delivery
			if(BasicUtils.isEmpty(antenatalObj.getModeOfDelivery())){
				if(ErrorMessage!=""){
					ErrorMessage+=", Mode of Delivery ";
				}else{
					ErrorMessage+="Mode of Delivery ";
				}
				mandatoryMissing=true;
				errorCount++;
			} else if (!BasicUtils.isEmpty(antenatalObj.getModeOfDelivery()) &&
					antenatalObj.getModeOfDelivery().equalsIgnoreCase("LSCS")
			) {
				// Don't do anything
			}

			// Delayed cord clamping and duration
			if( babyDetail.getInoutPatientStatus()!=null && babyDetail.getInoutPatientStatus().equalsIgnoreCase("In Born") &&
					BasicUtils.isEmpty(birthToNicu.getDelayedClamping())){
				if(ErrorMessage!=""){
					ErrorMessage+=", Delayed cord clamping ";
				}else{
					ErrorMessage+="Delayed cord clamping ";
				}
				mandatoryMissing=true;
				errorCount++;
			} else if (babyDetail.getInoutPatientStatus()!=null && babyDetail.getInoutPatientStatus().equalsIgnoreCase("In Born") &&
					!BasicUtils.isEmpty(birthToNicu.getDelayedClamping()) && birthToNicu.getDelayedClamping().equalsIgnoreCase("Yes")
					&& BasicUtils.isEmpty(birthToNicu.getChordDuration()) && BasicUtils.isEmpty(birthToNicu.getDuration_unit_chord())
			) {
				if(ErrorMessage!=""){
					ErrorMessage+=", Delayed cord duration ";
				}else{
					ErrorMessage+="Delayed cord duration ";
				}
				mandatoryMissing=true;
				errorCount++;
			}

			// Resuscitation
			if(babyDetail.getInoutPatientStatus()!=null && babyDetail.getInoutPatientStatus().equalsIgnoreCase("In Born") && BasicUtils.isEmpty(birthToNicu.getResuscitation())){
				if(ErrorMessage!=""){
					ErrorMessage+=", Resuscitation ";
				}else{
					ErrorMessage+="Resuscitation ";
				}
				mandatoryMissing=true;
				errorCount++;
			}

//			else if(babyDetail.getInoutPatientStatus()!=null && babyDetail.getInoutPatientStatus().equalsIgnoreCase("In Born") &&  !BasicUtils.isEmpty(birthToNicu.getResuscitation()) && birthToNicu.getResuscitation() == true){
//				// O2 Duration
//				if(!BasicUtils.isEmpty(birthToNicu.getResuscitationO2())){
//					if(BasicUtils.isEmpty(birthToNicu.getResuscitationO2Duration()) || BasicUtils.isEmpty(birthToNicu.getDurationO2Time())){
//						if(ErrorMessage!=""){
//							ErrorMessage+=", O2 Duration ";
//						}else{
//							ErrorMessage+="O2 Duration ";
//						}
//						mandatoryMissing=true;
//						errorCount++;
//					}
//				}
//
//
//				// PPV
//				if(!BasicUtils.isEmpty(birthToNicu.getResuscitationPpv())){
//					if(BasicUtils.isEmpty(birthToNicu.getResuscitationPpvDuration()) || BasicUtils.isEmpty(birthToNicu.getPpvTime())){
//						if(ErrorMessage!=""){
//							ErrorMessage+=", PPV Duration ";
//						}else{
//							ErrorMessage+="PPV Duration ";
//						}
//						mandatoryMissing=true;
//						errorCount++;
//					}
//				}
//
//				// Chest Compression
//				if(!BasicUtils.isEmpty(birthToNicu.getResuscitationChesttubeCompression())){
//					if(BasicUtils.isEmpty(birthToNicu.getResuscitationChesttubeCompressionDuration()) || BasicUtils.isEmpty(birthToNicu.getChestCompTime())){
//						if(ErrorMessage!=""){
//							ErrorMessage+=", Chest Compression Duration ";
//						}else{
//							ErrorMessage+="Chest Compression Duration ";
//						}
//						mandatoryMissing=true;
//						errorCount++;
//					}
//				}
//			}


			// for Maternal Medical Disease
			if((antenatalObj.getHypertension()!=null && antenatalObj.getHypertension()!=false) ||
					(antenatalObj.getGestationalHypertension()!=null &&  antenatalObj.getGestationalHypertension()!=false) ||
			(antenatalObj.getDiabetes()!=null &&  antenatalObj.getDiabetes()!=false) ||
					(antenatalObj.getGdm()!=null &&   antenatalObj.getGdm()!=false) ||
					(antenatalObj.getChronicKidneyDisease()!=null && antenatalObj.getChronicKidneyDisease()!=false) ||
					(antenatalObj.getHyperthyroidism()!=null && antenatalObj.getHyperthyroidism()!=false) ||
					(antenatalObj.getHypothyroidism()!=null && antenatalObj.getHypothyroidism()!=false) ||
					(antenatalObj.getNoneDisease()!=null && antenatalObj.getNoneDisease()!=false)){
				System.out.println("Mandatory Disease entered ");
			}else{
				if(ErrorMessage!=""){
					ErrorMessage+=", Maternal Medical Disease ";
				}else{
					ErrorMessage+="Maternal Medical Disease ";
				}
				mandatoryMissing=true;
				errorCount++;
			}


			// For Maternal Infections
			if((antenatalObj.getFever()!=null && antenatalObj.getFever()!=false) ||
					(antenatalObj.getUti()!=null && antenatalObj.getUti()!=false) ||
					(antenatalObj.getNoneInfection()!=null && antenatalObj.getNoneInfection()!=false) ||
							(antenatalObj.getHistoryOfInfections()!=null && antenatalObj.getHistoryOfInfections()!=false)){
				System.out.println("Maternal Infections entered ");
			}else{
				if(ErrorMessage!=""){
					ErrorMessage+=", Maternal Infections ";
				}else{
					ErrorMessage+="Maternal Infections ";
				}
				mandatoryMissing=true;
				errorCount++;
			}

			// For Other Risk Factor
			if((antenatalObj.getProm()!=null && antenatalObj.getProm()!=false) ||
					(antenatalObj.getPprom()!=null && antenatalObj.getPprom()!=false ) ||
					(antenatalObj.getPrematurity()!=null && antenatalObj.getPrematurity()!=false) ||
					(antenatalObj.getChorioamniotis()!=null && antenatalObj.getChorioamniotis()!=false) ||
					(antenatalObj.getOligohydraminos()!=null && antenatalObj.getOligohydraminos()!=false) ||
					(antenatalObj.getPolyhydraminos()!=null && antenatalObj.getPolyhydraminos()!=false) ||
					(antenatalObj.getNoneOther()!=null && antenatalObj.getNoneOther()!=false)){
				System.out.println("Other Risk Factor entered ");
			}else{
				if(ErrorMessage!=""){
					ErrorMessage+=", Antenatal Risk Factors ";
				}else{
					ErrorMessage+="Antenatal Risk Factor ";
				}
				mandatoryMissing=true;
				errorCount++;
			}


			if(errorCount>1){
				ErrorMessage+="are mandatory, Please select 'None' Otherwise";
			}else if(errorCount==1){
				ErrorMessage+="is mandatory, Please select 'None' Otherwise";
			}

			int steroidFlag=0;
			// Check the Antenatal Steroid is filled or not
			if(antenatalTempHistory.getIsAntenatalSteroidGiven()!=null) {
				if(antenatalTempHistory.getIsAntenatalSteroidGiven().equalsIgnoreCase("true")) {
					if (firstSteroidObjTemp.getAntenatalSteroidId() != null) {
						if (firstSteroidObjTemp.getSteroidname() != null) {
							if (firstSteroidObjTemp.getNumberofdose() != null) {
								steroidFlag = 1;
							} else {
								mandatoryMissing = true;
								if (ErrorMessage == "") {
									ErrorMessage += "Number of Doses missing in Antenatal Steroids";
								} else {
									ErrorMessage += ", Number of Doses missing in Antenatal Steroids";
								}
							}
						} else {
							mandatoryMissing = true;
							if (ErrorMessage == "") {
								ErrorMessage += "Antenatal Steroids is Missing";
							} else {
								ErrorMessage += ", Antenatal Steroids is Missing";
							}
						}
					}
				}
			}else{
				mandatoryMissing=true;
				if(ErrorMessage==""){
					ErrorMessage+="Antenatal Steroids is Missing";
				}else{
					ErrorMessage+=", Antenatal Steroids is Missing";
				}
			}
			
			if(birthToNicuObj.getInoutPatientStatus().equalsIgnoreCase("In Born")) {
				if(birthToNicuObj.getApgarFivemin() != null || birthToNicuObj.getApgarOnemin() != null || birthToNicuObj.getApgarTenmin() != null ){
					System.out.println("Other Risk Factor entered ");
				}else{
					if(ErrorMessage!=""){
						ErrorMessage+=", Apgar score is missing";
					}else{
						ErrorMessage+="Apgar score is missing";
					}
					mandatoryMissing=true;
					errorCount++;
				}
			}
			
			if(birthToNicuObj.getInoutPatientStatus().equalsIgnoreCase("Out Born")) {
				if(birthToNicuObj.getApgarFivemin() != null || birthToNicuObj.getApgarOnemin() != null || birthToNicuObj.getApgarTenmin() != null ){
					System.out.println("Other Risk Factor entered ");
				}else{
					if(birthToNicuObj.getApgarAvailable()==null || birthToNicuObj.getApgarAvailable() == true) {
					if(ErrorMessage!=""){
						ErrorMessage+=", Apgar score is missing";
					}else{
						ErrorMessage+="Apgar score is missing";
					}
					mandatoryMissing=true;
					errorCount++;
					}
				}
			}

			// Logic to check the Mulitple Pregnancy Mandatory field
			if(!BasicUtils.isEmpty(babyObj.getBabyType())){
				// Baby Type is selected please do nothing
			}else{
				if(ErrorMessage!=""){
					ErrorMessage+=", Multiple pregnancies is missing";
				}else{
					ErrorMessage+="Multiple pregnancies is missing";
				}
				mandatoryMissing=true;
				errorCount++;
			}

			if(mandatoryMissing==true){
				returnObj.setStatus(false);
				returnObj.setMessage(ErrorMessage);
				returnObj.setType(BasicConstants.MESSAGE_FAILURE);
				returnObj.setReturnedObject(registrationObj);
				return returnObj;
			}
		}


		// check the existing data persistence
		if (null == babyObj.getBabydetailid()) {
			String query = "select obj from BabyDetail as obj where uhid='" + uhid + "' and admissionstatus='true'";
			List<BabyDetail> patientList = inicuDao.getListFromMappedObjQuery(query);
			if (!BasicUtils.isEmpty(patientList)) {
				// baby already admitted
				returnObj.setType("Failure");
				returnObj.setReturnedObject(getAdvanceAdmissionForm(uhid, ""));
				returnObj.setMessage("Baby has already been admitted. Last submitted data retrieved !!");
				return returnObj;
			}else{
				// saved the baby object in the database now send the registered email
				try{
					String mailContent = "<p>New Baby "+ babyObj.getBabyname() +" is registered (By Doctor/Manually) with UHID: " + babyObj.getUhid()
							+ "<br><br>\n "
							+ "Regards<br>"
							+ "\n iNICU Team</p>";

					settingService.sendCustomEmail("New Baby Admission",mailContent,babyObj.getBranchname(),BasicConstants.BABY_ADMIT);
				}catch (Exception e){
					e.printStackTrace();;
				}
			}
		}

		ParentDetail personalObj = registrationObj.getPersonalDetailObj();
		List<ReasonAdmission> selectedReason = registrationObj.getSelectedReasonList();
		BirthToNicu birthNicuObj = registrationObj.getBirthToNicuObj();
		GenPhyExam genPhyExamObj = registrationObj.getGenPhyExamObj();
		SystemicExam systemExamObj = registrationObj.getSysAssessmentObj();
		AdmissionNotes admissionNotesObj = registrationObj.getAdmissionNotesObj();
		SystemicExaminationNotes systemicExaminationNotesObj = registrationObj.getSystemicExaminationNotes();

		try {
			personalObj.setUhid(uhid);
			birthNicuObj.setUhid(uhid);
			birthNicuObj.setLoggeduser(loggedUser);
			birthNicuObj.setInoutPatientStatus(babyObj.getInoutPatientStatus());
			genPhyExamObj.setUhid(uhid);
			genPhyExamObj.setLoggeduser(loggedUser);
			systemExamObj.setUhid(uhid);
			systemExamObj.setLoggeduser(loggedUser);
			admissionNotesObj.setUhid(uhid);
			admissionNotesObj.setLoggeduser(loggedUser);
			systemicExaminationNotesObj.setUhid(uhid);
			babyObj.setLoggeduser(loggedUser);
			babyObj.setActivestatus(true);
			babyObj.setAdmissionstatus(true);
			if (babyObj.getBabyname() == null || babyObj.getBabyname().isEmpty()) {
				babyObj.setBabyname("B/O " + personalObj.getMothername());
			}

			if (!BasicUtils.isEmpty(babyObj.getDobStr())) {
				try {
					System.out.println(babyObj.getDobStr() + " initalDOB");
					String[] strArr = babyObj.getDobStr().split("-");
					Date dobDate = new Date(parseInt(strArr[0]) - 1900, parseInt(strArr[1]) - 1,
							parseInt(strArr[2]));
					babyObj.setDateofbirth(dobDate);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
							BasicUtils.convertErrorStacktoString(e));
				}
			}
			Date doaDate = null;

			if (!BasicUtils.isEmpty(babyObj.getDoaStr())) {
				try {
					String[] strArr = babyObj.getDoaStr().split("-");
					doaDate = new Date(parseInt(strArr[0]) - 1900, parseInt(strArr[1]) - 1,
							parseInt(strArr[2]));
					babyObj.setDateofadmission(doaDate);
					if (!BasicUtils.isEmpty(tempuhidtoggle) && "Yes".equals(tempuhidtoggle)) {
						babyObj.setReceivingdate(doaDate);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
							BasicUtils.convertErrorStacktoString(e));
				}
			}

			if (!BasicUtils.isEmpty(tempuhidtoggle) && "Yes".equals(tempuhidtoggle)) {
				if (!BasicUtils.isEmpty(doaDate) && !BasicUtils.isEmpty(babyObj.getTimeofadmission())) {
					babyObj.setReceivingtime(babyObj.getTimeofadmission());
				}
			}

			if (!BasicUtils.isEmpty(personalObj.getFatherdobStr())) {
				try {
					Date fatherdobDate = CalendarUtility.dateFormatDB.parse(personalObj.getFatherdobStr());
					personalObj.setFatherdob(fatherdobDate);
				} catch (ParseException e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
							BasicUtils.convertErrorStacktoString(e));
				}
			}

			if (!BasicUtils.isEmpty(personalObj.getMotherdobStr())) {
				try {
					Date motherdobDate = CalendarUtility.dateFormatDB.parse(personalObj.getMotherdobStr());
					personalObj.setMotherdob(motherdobDate);
				} catch (ParseException e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
							BasicUtils.convertErrorStacktoString(e));
				}
			}

			if (!(BasicUtils.isEmpty(registrationObj.getAntenatalHistoryObj().getGestationbyLmpDays())
					|| BasicUtils.isEmpty(registrationObj.getAntenatalHistoryObj().getGestationbyLmpWeeks()))) {
				babyObj.setGestationdaysbylmp(registrationObj.getAntenatalHistoryObj().getGestationbyLmpDays());
				babyObj.setGestationweekbylmp(registrationObj.getAntenatalHistoryObj().getGestationbyLmpWeeks());
				babyObj.setActualgestationdays(registrationObj.getAntenatalHistoryObj().getGestationbyLmpDays());
				babyObj.setActualgestationweek(registrationObj.getAntenatalHistoryObj().getGestationbyLmpWeeks());
			}
			// downesScore
			if (registrationObj.isDowneFlag()) {
				ScoreDownes downesObj = registrationObj.getDownesObj();
				downesObj.setUhid(uhid);
				downesObj.setAdmission_entry(true);
				if (!BasicUtils.isEmpty(doaDate) && !BasicUtils.isEmpty(babyObj.getTimeofadmission())) {
					Timestamp timestamp = new Timestamp(doaDate.getTime());
					String[] toaArr = babyObj.getTimeofadmission().split(",");
					// "10,38,PM"
					if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
						timestamp.setHours(parseInt(toaArr[0]) + 12);
					} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
						timestamp.setHours(0);
					} else {
						timestamp.setHours(parseInt(toaArr[0]));
					}
					timestamp.setMinutes(parseInt(toaArr[1]));

					downesObj.setEntrydate(timestamp);
				}
				downesObj = (ScoreDownes) inicuDao.saveObject(downesObj);
				if (downesObj.getDownesscoreid() == null) {
					String downesGetSql = "select obj from ScoreDownes as obj where uhid='" + uhid
							+ "' and admission_entry='true' order by creationtime desc";
					List<ScoreDownes> downesGetList = inicuDao.getListFromMappedObjQuery(downesGetSql);
					if (!BasicUtils.isEmpty(downesGetList)) {
						downesObj = downesGetList.get(0);
					}
				}
				babyObj.setDownesscoreid(downesObj.getDownesscoreid().toString());
			}

			boolean bedAvailable = true;
			if (!BasicUtils.isEmpty(babyObj.getBabydetailid())) {
				String babyDetailSql = "select obj from BabyDetail as obj where babydetailid="
						+ babyObj.getBabydetailid() + " order by creationtime desc";
				List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailSql);
				BabyDetail pastbabyObj = babyDetailList.get(0);

				if (!pastbabyObj.getNicubedno().equalsIgnoreCase(babyObj.getNicubedno().trim())) {
					String getBedSql = "select obj from RefBed as obj where bedid='" + babyObj.getNicubedno().trim()
							+ "'";
					List<RefBed> bedList = inicuDao.getListFromMappedObjQuery(getBedSql);
					RefBed bedObj = bedList.get(0);

					if (bedObj.getStatus()) {
						bedObj.setStatus(false);
						inicuDao.saveObject(bedObj);
						String query = "update RefBed set status='true' where bedid='" + pastbabyObj.getNicubedno()
								+ "'";
						inicuDao.updateOrDeleteQuery(query);
					} else {
						bedAvailable = false;
					}
				}
			} else {
				String getBedSql = "select obj from RefBed as obj where bedid='" + babyObj.getNicubedno().trim() + "'";
				List<RefBed> bedList = inicuDao.getListFromMappedObjQuery(getBedSql);
				RefBed bedObj = bedList.get(0);

				if (bedObj.getStatus()) {
					bedObj.setStatus(false);
					inicuDao.saveObject(bedObj);
				} else {
					bedAvailable = false;
				}
			}

			if (!bedAvailable) {
				returnObj.setType("Failure");
				returnObj.setReturnedObject(registrationObj);
				returnObj.setMessage("Bed is not available now, Please select another vacant bed !!");
				return returnObj;
			}

			if (!BasicUtils.isEmpty(babyObj)) {

				AdmissionFormTracker admissionFormTracker = null;
				String pastAdmissionFormTrackerSql = "select obj from AdmissionFormTracker as obj where uhid='" + uhid
						+ "'";
				List<AdmissionFormTracker> pastAdmissionFormTracker = inicuDao
						.getListFromMappedObjQuery(pastAdmissionFormTrackerSql);

				if (BasicUtils.isEmpty(pastAdmissionFormTracker)) {
					admissionFormTracker = new AdmissionFormTracker();

					admissionFormTracker.setBabyname(babyObj.getBabyname());
					admissionFormTracker.setUhid(babyObj.getUhid());
					admissionFormTracker.setLoggeduser(babyObj.getLoggeduser());
					admissionFormTracker.setDateofbirth(babyObj.getDateofbirth());
					admissionFormTracker.setDateofadmission(babyObj.getDateofadmission());
					admissionFormTracker.setInoutStatus(babyObj.getInoutPatientStatus());

				} else {
					admissionFormTracker = pastAdmissionFormTracker.get(0);
				}
				if (!BasicUtils.isEmpty(babyObj.getUhid()) && !BasicUtils.isEmpty(babyObj.getLoggeduser())
						&& !BasicUtils.isEmpty(babyObj.getDateofbirth()) && !BasicUtils.isEmpty(babyObj.getBabyname())
						&& !BasicUtils.isEmpty(babyObj.getDateofadmission())
						&& !BasicUtils.isEmpty(babyObj.getGestationdaysbylmp())
						&& !BasicUtils.isEmpty(babyObj.getGestationweekbylmp())
						&& !BasicUtils.isEmpty(babyObj.getBirthweight())
						&& !BasicUtils.isEmpty(babyObj.getInoutPatientStatus())) {
					// do nothing
				}
				if (!BasicUtils.isEmpty(babyObj.getGestationdaysbylmp())
						&& BasicUtils.isEmpty(admissionFormTracker.getGestationdays())) {
					admissionFormTracker.setGestationdays(babyObj.getGestationdaysbylmp());
				}
				if (!BasicUtils.isEmpty(babyObj.getGestationweekbylmp())
						&& BasicUtils.isEmpty(admissionFormTracker.getGestationweek())) {
					admissionFormTracker.setGestationweek(babyObj.getGestationweekbylmp());
				}
				if (!BasicUtils.isEmpty(babyObj.getBirthweight())
						&& BasicUtils.isEmpty(admissionFormTracker.getBirthweight())) {
					admissionFormTracker.setBirthweight(babyObj.getBirthweight());
				}

				admissionFormTracker = (AdmissionFormTracker) inicuDao.saveObject(admissionFormTracker);
			}

			babyObj = (BabyDetail) inicuDao.saveObject(babyObj);

			BabyVisit visitObj = null;
			String existingCheckSql = "select obj from BabyVisit as obj where uhid='" + uhid
					+ "' and admission_entry='true'";
			List<BabyVisit> existingCheck = inicuDao.getListFromMappedObjQuery(existingCheckSql);

			if (BasicUtils.isEmpty(existingCheck)) {
				visitObj = new BabyVisit();
			} else {
				visitObj = existingCheck.get(0);
			}

			if (!(BasicUtils.isEmpty(babyObj.getBirthweight()) && BasicUtils.isEmpty(babyObj.getBirthlength())
					&& BasicUtils.isEmpty(babyObj.getBirthheadcircumference()))) {

				visitObj.setUhid(uhid);
				visitObj.setLoggeduser(loggedUser);
				visitObj.setEpisodeid(babyObj.getEpisodeid());
				visitObj.setAdmission_entry(true);
				visitObj.setCreationtime(new Timestamp(babyObj.getDateofbirth().getTime()));
				visitObj.setModificationtime(new Timestamp(babyObj.getDateofbirth().getTime()));
				visitObj.setVisitdate(babyObj.getDateofbirth());
				visitObj.setCurrentage(babyObj.getDayoflife());
				visitObj.setWorkingweight(babyObj.getBirthweight());
				if(babyObj.getBirthweight()!=null) {
				visitObj.setWeightForCal(babyObj.getBirthweight());
				}
				visitObj.setCurrentdateweight(babyObj.getBirthweight());
				visitObj.setCurrentdateheight(babyObj.getBirthlength());
				visitObj.setCurrentdateheadcircum(babyObj.getBirthheadcircumference());

				if (!(BasicUtils.isEmpty(babyObj.getAdmissionWeight())
						&& BasicUtils.isEmpty(babyObj.getAdmissionLength())
						&& BasicUtils.isEmpty(babyObj.getAdmissionHeadCircumference()))) {
					visitObj.setAdmissionLength(babyObj.getAdmissionLength());
					visitObj.setAdmissionHeadCircumference(babyObj.getAdmissionHeadCircumference());
					visitObj.setAdmissionWeight(babyObj.getAdmissionWeight());

					if (!(BasicUtils.isEmpty(babyObj.getAdmissionWeight()))
							&& !(BasicUtils.isEmpty(babyObj.getBirthweight()))) {
						if (babyObj.getAdmissionWeight() > babyObj.getBirthweight()) {
							visitObj.setWorkingweight(babyObj.getAdmissionWeight());
						}
					} else if (!(BasicUtils.isEmpty(babyObj.getAdmissionWeight()))) {
						visitObj.setWorkingweight(babyObj.getAdmissionWeight());
					}
				}

				String gestStr = ",";
				if (!(BasicUtils.isEmpty(babyObj.getGestationweekbylmp())
						|| BasicUtils.isEmpty(babyObj.getGestationdaysbylmp()))) {
					visitObj.setGestationWeek(babyObj.getGestationweekbylmp());
					visitObj.setGestationDays(babyObj.getGestationdaysbylmp());
					gestStr = babyObj.getGestationweekbylmp() + "," + babyObj.getGestationdaysbylmp();
				}

				visitObj.setGaAtBirth(gestStr);
				visitObj.setCorrectedGa(gestStr);

				inicuDao.saveObject(visitObj);
			}

			// PatientDueVaccineDtls
			try {
				String existingCheckQuery = "select obj from PatientDueVaccineDtls as obj where uid='" + uhid + "'";
				List<PatientDueVaccineDtls> existingCheckList = inicuDao.getListFromMappedObjQuery(existingCheckQuery);

				if (null == existingCheckList || existingCheckList.isEmpty()) {
					// populate vaccination
					String queryVaccination = "select obj from VwVaccinationsPopulate as obj";
					List<VwVaccinationsPopulate> listVaccinations = inicuDao
							.getListFromMappedObjQuery(queryVaccination);

					Iterator<VwVaccinationsPopulate> itr = listVaccinations.iterator();
					List<Object> pdvdList = new ArrayList<Object>();
					PatientDueVaccineDtls pdvd = null;
					VwVaccinationsPopulate vaccObj = null;
					Date dueDate = null;
					while (itr.hasNext()) {
						vaccObj = itr.next();

						if (vaccObj.getDueage().equalsIgnoreCase("birth")) {
							dueDate = babyObj.getDateofbirth();
						} else { // handles only week parameter string
							String noOfWeeks = vaccObj.getDueage().substring(0, vaccObj.getDueage().indexOf("week"))
									.trim();
							dueDate = DateUtils.addWeeks(babyObj.getDateofbirth(), Integer.valueOf(noOfWeeks));
						}

						pdvd = new PatientDueVaccineDtls();
						pdvd.setUid(uhid);
						pdvd.setVaccineinfoid(vaccObj.getVaccineinfoid());
						pdvd.setDuedate(dueDate);

						pdvdList.add(pdvd);
					}
					inicuDao.saveMultipleObject(pdvdList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Save admission Notes
			try {
				personalObj.setEpisodeid(babyObj.getEpisodeid());
				personalObj = (ParentDetail) inicuDao.saveObject(personalObj);

				if (!BasicUtils.isEmpty(selectedReason)) {
					Iterator<ReasonAdmission> itr = selectedReason.iterator();
					while (itr.hasNext()) {
						ReasonAdmission obj = itr.next();
						obj.setUhid(uhid);
						obj.setLoggeduser(loggedUser);
						obj.setEpisodeid(babyObj.getEpisodeid());
					}
					selectedReason = (List<ReasonAdmission>) inicuDao.saveMultipleObject(selectedReason);
				}

				birthNicuObj.setEpisodeid(babyObj.getEpisodeid());
				birthNicuObj = (BirthToNicu) inicuDao.saveObject(birthNicuObj);

				if (babyObj.getInoutPatientStatus().equalsIgnoreCase("Out Born")) {
					Iterator<OutbornMedicine> itr = registrationObj.getMedicineList().iterator();
					while (itr.hasNext()) {
						OutbornMedicine obj = itr.next();
						if (!(BasicUtils.isEmpty(obj.getMedicine_name()) && BasicUtils.isEmpty(obj.getMedicine_dose())
								&& BasicUtils.isEmpty(obj.getMedicine_cummulative_dose()))) {
							obj.setEpisodeid(babyObj.getEpisodeid());
							obj.setUhid(uhid);
							obj.setLoggeduser(loggedUser);
							obj = (OutbornMedicine) inicuDao.saveObject(obj);
						}
					}

					if (registrationObj.isLeveneFlag()) {
						ScoreLevene leveneObj = registrationObj.getLeveneObj();
						leveneObj.setUhid(uhid);
						leveneObj.setAdmission_entry(true);
						leveneObj = (ScoreLevene) inicuDao.saveObject(leveneObj);
						if (leveneObj.getLevenescoreid() == null) {
							String leveneGetSql = "select obj from ScoreLevene as obj where uhid='" + uhid
									+ "' and admission_entry='true' order by creationtime desc";
							List<ScoreLevene> leveneGetList = inicuDao.getListFromMappedObjQuery(leveneGetSql);
							leveneObj = leveneGetList.get(0);
						}
						birthNicuObj.setLeveneid(registrationObj.getLeveneObj().getLevenescoreid().toString());
					}
				}

				if (birthNicuObj.getRespsupport() != null && birthNicuObj.getRespsupport()) {
					RespSupport respSupportObj = registrationObj.getRespSupportObj();

					if (!(BasicUtils.isEmpty(respSupportObj.getRsVentType())
							|| respSupportObj.getRsVentType().contains("Unknown"))) {
						Timestamp creationTime = new Timestamp(new Date().getTime());
						if (!BasicUtils.isEmpty(babyObj.getDateofadmission())) {
							creationTime = new Timestamp(babyObj.getDateofadmission().getTime());
							if (!BasicUtils.isEmpty(babyObj.getTimeofadmission())) {
								String[] toaArr = babyObj.getTimeofadmission().split(",");
								// "10,38,PM"
								if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(parseInt(toaArr[0]) + 12);
								} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
									creationTime.setHours(0);
								} else {
									creationTime.setHours(parseInt(toaArr[0]));
								}
								creationTime.setMinutes(parseInt(toaArr[1]));
							}
							creationTime.setTime(creationTime.getTime() - (330 * 60000));
						}

						respSupportObj.setCreationtime(creationTime);
						respSupportObj.setModificationtime(creationTime);
						respSupportObj.setUhid(uhid);
						respSupportObj.setEventid(babyObj.getEpisodeid());
						respSupportObj.setEventname("initial assessment");
						if (babyObj.getInoutPatientStatus() != null
								&& babyObj.getInoutPatientStatus().equalsIgnoreCase("In Born")) {
							respSupportObj.setIsactive(true);
						} else {
							respSupportObj.setIsactive(false);
						}
						respSupportObj = (RespSupport) inicuDao.saveObject(respSupportObj);
					}
				}

				genPhyExamObj.setEpisodeid(babyObj.getEpisodeid());
				genPhyExamObj = (GenPhyExam) inicuDao.saveObject(genPhyExamObj);

				systemExamObj.setEpisodeid(babyObj.getEpisodeid());
				systemExamObj = (SystemicExam) inicuDao.saveObject(systemExamObj);

				admissionNotesObj.setEpisodeid(babyObj.getEpisodeid());
				admissionNotesObj = (AdmissionNotes) inicuDao.saveObject(admissionNotesObj);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// AntenatalHistoryDetail
			try {
				AntenatalHistoryDetail antenatalHistory = registrationObj.getAntenatalHistoryObj();
				antenatalHistory.setUhid(uhid);
				antenatalHistory.setEpisodeid(babyObj.getEpisodeid());
				AntenatalSteroidDetail firstSteroidObj = antenatalHistory.getFirstSteroidDetail();
				AntenatalSteroidDetail secondSteroidObj = antenatalHistory.getSecondSteroidDetail();
				antenatalHistory = (AntenatalHistoryDetail) inicuDao.saveObject(antenatalHistory);

				if (registrationObj.isBallardFlag()) {
					ScoreBallard ballardObj = registrationObj.getBallardObj();
					ballardObj.setUhid(uhid);
					ballardObj = (ScoreBallard) inicuDao.saveObject(ballardObj);
				}

				if (!BasicUtils.isEmpty(firstSteroidObj.getSteroidname())) {
					firstSteroidObj.setUhid(uhid);
					firstSteroidObj.setEpisodeid(babyObj.getEpisodeid().toString());
					firstSteroidObj = (AntenatalSteroidDetail) inicuDao.saveObject(firstSteroidObj);
					antenatalHistory.setFirstSteroidDetail(firstSteroidObj);
				}
				if (!BasicUtils.isEmpty(secondSteroidObj.getSteroidname())) {
					secondSteroidObj.setUhid(uhid);
					secondSteroidObj.setEpisodeid(babyObj.getEpisodeid());
					secondSteroidObj = (AntenatalSteroidDetail) inicuDao.saveObject(secondSteroidObj);
					antenatalHistory.setSecondSteroidDetail(secondSteroidObj);
				}

				if (!BasicUtils.isEmpty(antenatalHistory.getEddTimestampStr())) {
					Date eddDate = CalendarUtility.dateFormatDB.parse(antenatalHistory.getEddTimestampStr());
					Timestamp ts = new java.sql.Timestamp(eddDate.getTime());
					antenatalHistory.setEddTimestamp(ts);
				}

				if (!BasicUtils.isEmpty(antenatalHistory.getLmpTimestampStr())) {
					Date lmpDate = CalendarUtility.dateFormatDB.parse(antenatalHistory.getLmpTimestampStr());
					Timestamp ts = new java.sql.Timestamp(lmpDate.getTime());
					antenatalHistory.setLmpTimestamp(ts);
				}

				registrationObj.setAntenatalHistoryObj(antenatalHistory);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// Save the Systematic Examination Notes
			try {
				// save the Object
				if (registrationObj.isSystemicExamsNotesFlag() == true) {
					systemicExaminationNotesObj.setEpisodeid(babyObj.getEpisodeid());
					systemicExaminationNotesObj = (SystemicExaminationNotes) inicuDao
							.saveObject(systemicExaminationNotesObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			returnObj.setReturnedObject(registrationObj);
			returnObj.setMessage("Patient Added Successfully.");
			returnObj.setType("Success");
		} catch (Exception e) {
			e.printStackTrace();
			returnObj.setType("Failure");
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					" Exception in Sumbit Advance Admission Form", BasicUtils.convertErrorStacktoString(e));
		}

		// Restoring the data back
		// Baby Admitted Now update its record from dummy table if Exists
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String getDummyData="select obj from DeviceMonitorDetailDump as obj where uhid='"+uhid+"'";
					List<DeviceMonitorDetailDump> objectlist=inicuDao.getListFromMappedObjQuery(getDummyData);
					if(!BasicUtils.isEmpty(objectlist)){
						// drop the trigger Frist
						String dropTriggerCreationtime="DROP TRIGGER set_creationtime ON device_monitor_detail;";
						inicuDao.executeCustomeQuery(dropTriggerCreationtime);
						String dropTriggerModificationtime="DROP TRIGGER set_modificationtime ON device_monitor_detail;";
						inicuDao.executeCustomeQuery(dropTriggerModificationtime);

						// insert
						String insertMonitorData="insert into device_monitor_detail ( select * from device_monitor_detail_dump where uhid ='"+uhid+"')";
						patientDao.executeInsertQuery(insertMonitorData);

						// delete the record from main table
						String deleteMonitorData="delete from device_monitor_detail_dump where uhid='"+uhid+"'";
						inicuDao.updateOrDeleteNativeQuery(deleteMonitorData);

						// create the trigger
						String createTriggerCreationtime="CREATE TRIGGER set_creationtime"+
								" BEFORE INSERT ON device_monitor_detail" +
								" FOR EACH ROW EXECUTE PROCEDURE patientdevicedetail_creationtime()";

						inicuDao.executeCustomeQuery(createTriggerCreationtime);

						String createTriggerModificationtime="CREATE TRIGGER set_modificationtime"+
								" BEFORE INSERT ON device_monitor_detail" +
								" FOR EACH ROW EXECUTE PROCEDURE patientdevicedetail_creationtime()";
						inicuDao.executeCustomeQuery(createTriggerModificationtime);
					}

					String getDummyVentilatorData="select obj from DeviceVentilatorDataDump as obj where uhid='"+uhid+"'";
					List<DeviceVentilatorDataDump> objectlist2=inicuDao.getListFromMappedObjQuery(getDummyVentilatorData);
					if(!BasicUtils.isEmpty(objectlist2)){

						// - Ventilator
						// drop the trigger Frist
						String dropTriggerCreationtime="DROP TRIGGER set_creationtime ON device_ventilator_detail;";
						inicuDao.executeCustomeQuery(dropTriggerCreationtime);
						String dropTriggerModificationtime="DROP TRIGGER set_modificationtime ON device_ventilator_detail;";
						inicuDao.executeCustomeQuery(dropTriggerModificationtime);

						// insert
						String insertVentilatorData="insert into device_ventilator_detail ( select * from device_ventilator_detail_dump where uhid ='"+uhid+"')";
						patientDao.executeInsertQuery(insertVentilatorData);

						// delete the record from main table
						String deleteVentilatorData="delete from device_ventilator_detail_dump where uhid='"+uhid+"'";
						inicuDao.updateOrDeleteNativeQuery(deleteVentilatorData);

						// create the trigger
						String createTriggerCreationtime="CREATE TRIGGER set_creationtime"+
								" BEFORE INSERT ON device_ventilator_detail" +
								" FOR EACH ROW EXECUTE PROCEDURE device_ventilator_detail_creationtime()";

						inicuDao.executeCustomeQuery(createTriggerCreationtime);

						String createTriggerModificationtime="CREATE TRIGGER set_modificationtime"+
								" BEFORE INSERT ON device_ventilator_detail" +
								" FOR EACH ROW EXECUTE PROCEDURE patient_ventilator_data_modificationtime()";
						inicuDao.executeCustomeQuery(createTriggerModificationtime);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		return returnObj;
	}

	@Override
	public AdvanceAdmitPatientPojo getAdvanceAdmissionForm(String uhid, String loggedInUser) {
		AdvanceAdmitPatientPojo returnObj = new AdvanceAdmitPatientPojo();

		try {
			// episodeid need to be sent
			String babyDetailSql = "select obj from BabyDetail as obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailSql);

			if (!BasicUtils.isEmpty(babyDetailList)) {
				BabyDetail babyObj = babyDetailList.get(0);

				String consultantName = babyObj.getAdmittingdoctor();
				String branchname = babyObj.getBranchname();
				if(!BasicUtils.isEmpty(consultantName)) {
					String[] nameArr = consultantName.split(" ");
					StringBuilder userQuery = new StringBuilder("select obj from User as obj where firstname='" + nameArr[0] + "'");
					if (nameArr.length > 1 && !BasicUtils.isEmpty(nameArr[1])) {
						userQuery.append(" and lastname = '" + nameArr[1] + "'");
					}
					List<User> userList = inicuDao.getListFromMappedObjQuery(userQuery.toString());
					if(!BasicUtils.isEmpty(userList)) {
						String consultantUsername = userList.get(0).getUserName();
						if(!BasicUtils.isEmpty(consultantUsername)) {
							List<KeyValueObj> consultantSignatureImageData = userPanel.getImageData(consultantUsername, branchname);
							returnObj.setConsultantSignatureImage(consultantSignatureImageData);
						}
					}
				}

				if (!BasicUtils.isEmpty(loggedInUser)) {
					StringBuilder loggedUserFullName = null;
					String userListQuery = "select obj from User as obj where username='" + loggedInUser + "' and branchname = '"
							+ branchname + "'";
					List<User> userList = inicuDao.getListFromMappedObjQuery(userListQuery);
					if(!BasicUtils.isEmpty(userList)) {
						loggedUserFullName = new StringBuilder(userList.get(0).getFirstName());
						if (!BasicUtils.isEmpty(userList.get(0).getLastName())) {
							loggedUserFullName.append(" " + userList.get(0).getLastName());
						}

                        String roleId = "";
                        String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + loggedInUser
                                + "' and branchname = '" + branchname + "'";
                        List<UserRolesTable> userRoleList = inicuDao.getListFromMappedObjQuery(userObjQuery);
                        if (!BasicUtils.isEmpty(userRoleList)) {
                            roleId = userRoleList.get(0).getRoleId();
                        }

                        if(!BasicUtils.isEmpty(roleId)) {
                            if ("2".equalsIgnoreCase(roleId) || "3".equalsIgnoreCase(roleId)) {
                                loggedUserFullName.insert(0, "Dr. ");
                            }
                        }

						returnObj.setLoggedUserFullName(loggedUserFullName.toString());
					}
				}

				Date dobDate = CalendarUtility.dateFormatDB.parse(babyObj.getDateofbirth().toString());
				Date doaDate = CalendarUtility.dateFormatDB.parse(babyObj.getDateofadmission().toString());
				
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				System.out.println("Offset Value here 2: "+offset);
				if(offset < 10) {
					Date dateofadmission = new Timestamp(babyObj.getDateofadmission().getTime() + 19800000);
					Date dateofbirth = new Timestamp(babyObj.getDateofbirth().getTime() + 19800000);
					babyObj.setDateofadmission(dateofadmission);
					babyObj.setDateofbirth(dateofbirth);
				}

				babyObj.setDobObj(dobDate);
				babyObj.setDoaObj(doaDate);

				Timestamp dateTimeOfBirth = BasicUtils.getDateTimeFromString(babyObj.getDateofbirth(),babyObj.getTimeofbirth());
				System.out.println("Timestamp Value : "+dateTimeOfBirth);
				if(!BasicUtils.isEmpty(dateTimeOfBirth)){
					babyObj.setDateTimeOfBirth(new Timestamp(dateTimeOfBirth.getTime() - offset));
				}else{
					babyObj.setDateTimeOfBirth(new Timestamp(BasicUtils.getDateTimeFromString(babyObj.getDateofbirth(),"00,00,AM").getTime() -offset));
				}

				Timestamp dateTimeOfAdmission = BasicUtils.getDateTimeFromString(babyObj.getDateofadmission(),babyObj.getTimeofadmission());
				if(!BasicUtils.isEmpty(dateTimeOfAdmission)){
					babyObj.setDateTimeOfAdmission(new Timestamp(dateTimeOfAdmission.getTime() - offset));
				}else{
					babyObj.setDateTimeOfAdmission(new Timestamp(BasicUtils.getDateTimeFromString(babyObj.getDateofadmission(),"00,00,AM").getTime() -offset));
				}

				returnObj.setBabyDetailObj(babyObj);
				
				// episodeid need to be sent
				String parentDetailSql = "select obj from ParentDetail as obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<ParentDetail> parentDetailList = inicuDao.getListFromMappedObjQuery(parentDetailSql);

				if (!BasicUtils.isEmpty(parentDetailList)) {
					returnObj.setPersonalDetailObj(parentDetailList.get(0));
				}

				String ballardSql = "select obj from ScoreBallard as obj where uhid='" + uhid + "'";
				List<ScoreBallard> ballardList = inicuDao.getListFromMappedObjQuery(ballardSql);

				if (!BasicUtils.isEmpty(ballardList)) {
					returnObj.setBallardObj(ballardList.get(0));
				}

				String antenatalHistoryDetailSql = "select obj from AntenatalHistoryDetail as obj where uhid='" + uhid
						+ "' and episodeid='" + returnObj.getBabyDetailObj().getEpisodeid() + "'";
				List<AntenatalHistoryDetail> antenatalHistoryDetailList = inicuDao
						.getListFromMappedObjQuery(antenatalHistoryDetailSql);

				if (!BasicUtils.isEmpty(antenatalHistoryDetailList)) {
					AntenatalHistoryDetail antenatalHistory = antenatalHistoryDetailList.get(0);
					String querySteroid = HqlSqlQueryConstants.AntenatalSteroidDetail + " where uhid='" + uhid
							+ "' order by creationtime asc";
					List<AntenatalSteroidDetail> listSteroid = inicuDao.getListFromMappedObjQuery(querySteroid);
					if (!BasicUtils.isEmpty(listSteroid)) {
						AntenatalSteroidDetail firstSteroid = listSteroid.get(0);
						antenatalHistory.setFirstSteroidDetail(firstSteroid);
						if (listSteroid.size() > 1) {
							AntenatalSteroidDetail secondSteroid = listSteroid.get(1);
							antenatalHistory.setSecondSteroidDetail(secondSteroid);
						}
					}
					returnObj.setAntenatalHistoryObj(antenatalHistory);
				}

				String reasonSql = "select obj from ReasonAdmission as obj where uhid='" + uhid + "' and episodeid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "' " + "order by creationtime asc";
				List<ReasonAdmission> reasonList = inicuDao.getListFromMappedObjQuery(reasonSql);

				if (!BasicUtils.isEmpty(reasonList)) {
					returnObj.setSelectedReasonList(reasonList);
				}

				String leveneSql = "select obj from ScoreLevene as obj where uhid='" + uhid
						+ "' and admission_entry='true' order by creationtime desc";
				List<ScoreLevene> leveneList = inicuDao.getListFromMappedObjQuery(leveneSql);

				if (!BasicUtils.isEmpty(leveneList)) {
					returnObj.setLeveneObj(leveneList.get(0));
				}

				String birthToNicuSql = "select obj from BirthToNicu as obj where uhid='" + uhid + "' and episodeid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "'";
				List<BirthToNicu> birthToNicuList = inicuDao.getListFromMappedObjQuery(birthToNicuSql);

				if (!BasicUtils.isEmpty(birthToNicuList)) {
					returnObj.setBirthToNicuObj(birthToNicuList.get(0));
				}

				String respSupportSql = "select obj from RespSupport as obj where uhid='" + uhid + "' and eventid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "' and eventname='initial assessment'";
				List<RespSupport> respSupportList = inicuDao.getListFromMappedObjQuery(respSupportSql);

				if (!BasicUtils.isEmpty(respSupportList)) {
					returnObj.setRespSupportObj(respSupportList.get(0));
				} else if (returnObj.getBirthToNicuObj().getRespsupport() != null
						&& returnObj.getBirthToNicuObj().getRespsupport()) {
					returnObj.getRespSupportObj().setRsVentType("'Unknown'");
				}

				String medicineSql = "select obj from OutbornMedicine as obj where uhid='" + uhid + "' and episodeid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "'";
				List<OutbornMedicine> medicineList = inicuDao.getListFromMappedObjQuery(medicineSql);

				if (!BasicUtils.isEmpty(medicineList)) {
					returnObj.setMedicineList(medicineList);
				}

				String downesSql = "select obj from ScoreDownes as obj where uhid='" + uhid
						+ "' and admission_entry='true' order by creationtime desc";
				List<ScoreDownes> downesList = inicuDao.getListFromMappedObjQuery(downesSql);

				if (!BasicUtils.isEmpty(downesList)) {
					returnObj.setDownesObj(downesList.get(0));
				}

				String gpeSql = "select obj from GenPhyExam as obj where uhid='" + uhid + "' and episodeid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "'";
				List<GenPhyExam> gpeList = inicuDao.getListFromMappedObjQuery(gpeSql);

				if (!BasicUtils.isEmpty(gpeList)) {
					returnObj.setGenPhyExamObj(gpeList.get(0));
				}

				String seSql = "select obj from SystemicExam as obj where uhid='" + uhid + "' and episodeid='"
						+ returnObj.getBabyDetailObj().getEpisodeid() + "'";
				List<SystemicExam> seList = inicuDao.getListFromMappedObjQuery(seSql);

				if (!BasicUtils.isEmpty(seList)) {
					SystemicExam seObj = seList.get(0);
					if ((!BasicUtils.isEmpty(seObj.getRds()) && seObj.getRds())
							|| (!BasicUtils.isEmpty(seObj.getApnea()) && seObj.getApnea())
							|| (!BasicUtils.isEmpty(seObj.getPphn()) && seObj.getPphn())
							|| (!BasicUtils.isEmpty(seObj.getPneumothorax()) && seObj.getPneumothorax())) {
						seObj.setRespiratorySystem("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getRds()) && seObj.getRds() == false
							&& !BasicUtils.isEmpty(seObj.getApnea()) && seObj.getApnea() == false
							&& !BasicUtils.isEmpty(seObj.getPphn()) && seObj.getPphn() == false
							&& !BasicUtils.isEmpty(seObj.getPneumothorax()) && seObj.getPneumothorax() == false) {
						seObj.setRespiratorySystem("'No'");
					}

					if ((!BasicUtils.isEmpty(seObj.getSepsis()) && seObj.getSepsis())
							|| (!BasicUtils.isEmpty(seObj.getVap()) && seObj.getVap())
							|| (!BasicUtils.isEmpty(seObj.getClabsi()) && seObj.getClabsi())
							|| (!BasicUtils.isEmpty(seObj.getNec()) && seObj.getNec())
							|| (!BasicUtils.isEmpty(seObj.getIntrauterine()) && seObj.getIntrauterine())) {
						seObj.setInfection("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getSepsis()) && seObj.getSepsis() == false
							&& !BasicUtils.isEmpty(seObj.getVap()) && seObj.getVap() == false
							&& !BasicUtils.isEmpty(seObj.getClabsi()) && seObj.getClabsi() == false
							&& !BasicUtils.isEmpty(seObj.getNec()) && seObj.getNec() == false
							&& !BasicUtils.isEmpty(seObj.getIntrauterine()) && seObj.getIntrauterine() == false) {
						seObj.setInfection("'No'");
					}

					if ((!BasicUtils.isEmpty(seObj.getAsphyxia()) && seObj.getAsphyxia())
							|| (!BasicUtils.isEmpty(seObj.getSeizures()) && seObj.getSeizures())
							|| (!BasicUtils.isEmpty(seObj.getEncephalopathy()) && seObj.getEncephalopathy())
							|| (!BasicUtils.isEmpty(seObj.getHydrocephalus()) && seObj.getHydrocephalus())
							|| (!BasicUtils.isEmpty(seObj.getNeuromuscularDisorder())
									&& seObj.getNeuromuscularDisorder())
							|| (!BasicUtils.isEmpty(seObj.getIvh()) && seObj.getIvh())) {
						seObj.setCns("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getAsphyxia()) && seObj.getAsphyxia() == false
							&& !BasicUtils.isEmpty(seObj.getSeizures()) && seObj.getSeizures() == false
							&& !BasicUtils.isEmpty(seObj.getEncephalopathy()) && seObj.getEncephalopathy() == false
							&& !BasicUtils.isEmpty(seObj.getHydrocephalus()) && seObj.getHydrocephalus() == false
							&& !BasicUtils.isEmpty(seObj.getNeuromuscularDisorder())
							&& seObj.getNeuromuscularDisorder() == false) {
						seObj.setCns("'No'");
					}

					if ((!BasicUtils.isEmpty(seObj.getHypoglycemia()) && seObj.getHypoglycemia())) {
						seObj.setMetabolic("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getHypoglycemia()) && seObj.getHypoglycemia() == false) {
						seObj.setMetabolic("'No'");
					}

					if ((!BasicUtils.isEmpty(seObj.getFeedIntolerance()) && seObj.getFeedIntolerance())) {
						seObj.setGi("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getFeedIntolerance()) && seObj.getFeedIntolerance() == false) {
						seObj.setGi("'No'");
					}
					
					if ((!BasicUtils.isEmpty(seObj.getShock()) && seObj.getShock())) {
						seObj.setCvs("'Yes'");
					} else if (!BasicUtils.isEmpty(seObj.getShock()) && seObj.getShock() == false) {
						seObj.setCvs("'No'");
					}

					returnObj.setSysAssessmentObj(seObj);
				}

				String admissionNotesSql = "select obj from AdmissionNotes as obj where uhid='" + uhid
						+ "' and episodeid='" + returnObj.getBabyDetailObj().getEpisodeid()
						+ "' order by creationtime desc";
				List<AdmissionNotes> admissionNotesList = inicuDao.getListFromMappedObjQuery(admissionNotesSql);

				if (!BasicUtils.isEmpty(admissionNotesList)) {
					returnObj.setAdmissionNotesObj(admissionNotesList.get(0));
				}

				// Get the Examination Notes
				String examinationNotes = "select obj from SystemicExaminationNotes as obj where uhid='" + uhid
						+ "' and episodeid='" + returnObj.getBabyDetailObj().getEpisodeid()
						+ "' order by creationtime desc";
				List<SystemicExaminationNotes> examinationNotesList = inicuDao
						.getListFromMappedObjQuery(examinationNotes);
				if (!BasicUtils.isEmpty(examinationNotesList)) {
					returnObj.setSystemicExaminationNotes(examinationNotesList.get(0));
				}
				
				String kuppuswamyEducationQuery = "Select education from ref_kuppuswamy_education"; 
				List<String> eduList = inicuDao.getListFromNativeQuery(kuppuswamyEducationQuery);
				if(!BasicUtils.isEmpty(eduList)) {
					returnObj.setEducationList(eduList);
				}
				
				String kuppuswamyOccupationQuery = "Select occupation from ref_kuppuswamy_occupation"; 
				List<String> ocuList = inicuDao.getListFromNativeQuery(kuppuswamyOccupationQuery);
				if(!BasicUtils.isEmpty(ocuList)) {
					returnObj.setOccupationList(ocuList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@Override
	public AdvanceAdmitPatientPojo getAdvanceAdmissionFormReadmit(String uhid) {
		AdvanceAdmitPatientPojo returnObj = new AdvanceAdmitPatientPojo();

		try {
			// episodeid need to be sent
			String babyDetailSql = "select obj from BabyDetail as obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailSql);

			if (!BasicUtils.isEmpty(babyDetailList)) {
				BabyDetail babyObj = babyDetailList.get(0);
				String episodeId = babyObj.getEpisodeid();

				Date dobDate = CalendarUtility.dateFormatDB.parse(babyObj.getDateofbirth().toString());
				babyObj.setDobObj(dobDate);
				babyObj.setBabydetailid(null);
				babyObj.setNicuroomno(null);
				babyObj.setNicubedno(null);
				babyObj.setIsassessmentsubmit(false);
				babyObj.setEpisodeid(null);
				babyObj.setDischargeddate(null);
				babyObj.setDischargestatus(null);
				// status at admission
				babyObj.setConsciousness(null);
				babyObj.setWeight_galevel(null);
				babyObj.setLength_galevel(null);
				babyObj.setHc_galevel(null);
				babyObj.setWeight_centile(null);
				babyObj.setLength_centile(null);
				babyObj.setHc_centile(null);
				babyObj.setPonderal_index(null);
				babyObj.setCentral_temp(null);
				babyObj.setPeripheral_temp(null);
				babyObj.setHr(null);
				babyObj.setRr(null);
				babyObj.setSpo2(null);
				babyObj.setBp_type(null);
				babyObj.setBp_systolic(null);
				babyObj.setBp_diastolic(null);
				babyObj.setBp_mean(null);
				babyObj.setBp_lll(null);
				babyObj.setBp_lul(null);
				babyObj.setBp_rll(null);
				babyObj.setBp_rul(null);
				babyObj.setCrt(null);
				babyObj.setDownesscoreid(null);
				babyObj.setIsreadmitted(true);
				returnObj.setBabyDetailObj(babyObj);

				// episodeid need to be sent
				String parentDetailSql = "select obj from ParentDetail as obj where uhid='" + uhid
						+ "' order by creationtime desc";
				List<ParentDetail> parentDetailList = inicuDao.getListFromMappedObjQuery(parentDetailSql);

				if (!BasicUtils.isEmpty(parentDetailList)) {
					returnObj.setPersonalDetailObj(parentDetailList.get(0));
				}

				String antenatalHistoryDetailSql = "select obj from AntenatalHistoryDetail as obj where uhid='" + uhid
						+ "' and episodeid='" + episodeId + "'";
				List<AntenatalHistoryDetail> antenatalHistoryDetailList = inicuDao
						.getListFromMappedObjQuery(antenatalHistoryDetailSql);

				if (!BasicUtils.isEmpty(antenatalHistoryDetailList)) {
					AntenatalHistoryDetail antenatalHistory = antenatalHistoryDetailList.get(0);
					String querySteroid = HqlSqlQueryConstants.AntenatalSteroidDetail + " where uhid='" + uhid
							+ "' order by creationtime asc";
					List<AntenatalSteroidDetail> listSteroid = inicuDao.getListFromMappedObjQuery(querySteroid);
					if (!BasicUtils.isEmpty(listSteroid)) {
						AntenatalSteroidDetail firstSteroid = listSteroid.get(0);
						antenatalHistory.setFirstSteroidDetail(firstSteroid);
						if (listSteroid.size() > 1) {
							AntenatalSteroidDetail secondSteroid = listSteroid.get(1);
							antenatalHistory.setSecondSteroidDetail(secondSteroid);
						}
					}
					antenatalHistory.setAntenatalHistoryId(null);
					antenatalHistory.setEpisodeid(null);
					returnObj.setAntenatalHistoryObj(antenatalHistory);
				}

				String birthToNicuSql = "select obj from BirthToNicu as obj where uhid='" + uhid + "' and episodeid='"
						+ episodeId + "'";
				List<BirthToNicu> birthToNicuList = inicuDao.getListFromMappedObjQuery(birthToNicuSql);

				if (!BasicUtils.isEmpty(birthToNicuList)) {
					BirthToNicu birthToNicuObj = birthToNicuList.get(0);
					birthToNicuObj.setBirthToNicuId(null);
					birthToNicuObj.setEpisodeid(null);
					returnObj.setBirthToNicuObj(birthToNicuObj);
				}

				String admissionNotesSql = "select obj from AdmissionNotes as obj where uhid='" + uhid
						+ "' and episodeid='" + episodeId + "' order by creationtime desc";
				List<AdmissionNotes> admissionNotesList = inicuDao.getListFromMappedObjQuery(admissionNotesSql);

				if (!BasicUtils.isEmpty(admissionNotesList)) {
					AdmissionNotes admissionNotesObj = admissionNotesList.get(0);
					admissionNotesObj.setEpisodeid(null);
					admissionNotesObj.setAdmission_notes_id(null);
					admissionNotesObj.setReason_admission(null);
					admissionNotesObj.setDiagnosis(null);
					admissionNotesObj.setGen_phy_exam(null);
					admissionNotesObj.setStatus_at_admission(null);
					admissionNotesObj.setSystemic_exam(null);
					returnObj.setAdmissionNotesObj(admissionNotesObj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnObj;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, String> getStateList() {
		HashMap<String, String> returnMap = new HashMap<>();
		List<String> dbList = inicuDao
				.getListFromMappedObjQuery("select DISTINCT(obj.state) from MasterAddress obj order by obj.state");
		if (!BasicUtils.isEmpty(dbList)) {
			Iterator<String> itr = dbList.iterator();
			while (itr.hasNext()) {
				String stateName = itr.next();
				returnMap.put(stateName, stateName);
			}
		}
		return returnMap;
	}

	@Override
	public HashMap<String, String> getDistrictList(String state) {
		HashMap<String, String> returnMap = new HashMap<>();
		List<String> dbList = inicuDao
				.getListFromMappedObjQuery("select DISTINCT(obj.district) from MasterAddress obj where obj.state='"
						+ state + "' order by obj.district");

		if (!BasicUtils.isEmpty(dbList)) {
			Iterator<String> itr = dbList.iterator();
			while (itr.hasNext()) {
				String districtName = itr.next();
				returnMap.put(districtName, districtName);
			}
		}
		return returnMap;
	}

	@Override
	public HashMap<String, MasterAddress> getAddressList(String state, String district) {
		HashMap<String, MasterAddress> returnMap = new HashMap<>();
		List<MasterAddress> dbList = inicuDao
				.getListFromMappedObjQuery("select obj from MasterAddress obj where obj.state='" + state
						+ "' and obj.district='" + district + "' order by obj.city");

		if (!BasicUtils.isEmpty(dbList)) {
			Iterator<MasterAddress> itr = dbList.iterator();
			while (itr.hasNext()) {
				MasterAddress obj = itr.next();
				returnMap.put(obj.getCity(), obj);
			}
		}
		return returnMap;
	}

	@Override
	public HashMap<String, MasterAddress> getCityAllList(String state) {
		HashMap<String, MasterAddress> returnMap = new HashMap<>();
		List<MasterAddress> dbList = inicuDao.getListFromMappedObjQuery(
				"select obj from MasterAddress obj where obj.state='" + state + "' order by obj.city");

		if (!BasicUtils.isEmpty(dbList)) {
			Iterator<MasterAddress> itr = dbList.iterator();
			while (itr.hasNext()) {
				MasterAddress obj = itr.next();
				returnMap.put(obj.getCity(), obj);
			}
		}
		return returnMap;
	}

	@Override
	public MandatoryFormPojo getMandatoryFormData(String uhid) {
		MandatoryFormPojo returnObj = new MandatoryFormPojo();
		try {
			String babyDetailSql = "select obj from BabyDetail as obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailSql);

			// From antenatal steroid detail
			String getAntenalSteroidHistory="select obj from AntenatalSteroidDetail obj where uhid ='"+uhid+"' order by creationtime desc";
			List<AntenatalSteroidDetail> antenalSteroidList = inicuDao.getListFromMappedObjQuery(getAntenalSteroidHistory);

			if(!BasicUtils.isEmpty(antenalSteroidList)) {

				AntenatalSteroidDetail lastObject=antenalSteroidList.get(0);

				if(lastObject.getSteroidname()!=null){
					returnObj.setAntenatalSteroidGiven(true);
					returnObj.setSteroidName(lastObject.getSteroidname());
					returnObj.setGivenDate(lastObject.getGivendate());
					returnObj.setNumberofDose(lastObject.getNumberofdose());
				}else{
					returnObj.setAntenatalSteroidGiven(false);
				}
			}else{
				returnObj.setAntenatalSteroidGiven(false);
			}

			if (!BasicUtils.isEmpty(babyDetailList)) {
				BabyDetail babyDetailObj = babyDetailList.get(0);

				String[] time = babyDetailObj.getTimeofadmission().split(",");

				returnObj.setAdmissionDate(babyDetailObj.getDateofadmission());
				returnObj.setAdmissionTime(time[0] + ":" + time[1] + " " + time[2]);
				returnObj.setUhid(babyDetailObj.getUhid());
				returnObj.setGender(babyDetailObj.getGender());
				returnObj.setBirthWeight(babyDetailObj.getBirthweight());
				returnObj.setPatientType(babyDetailObj.getInoutPatientStatus());
				returnObj.setDobStr(babyDetailObj.getDateofbirth().toString());
				returnObj.setTobStr(babyDetailObj.getTimeofbirth());
				returnObj.setDoaStr(babyDetailObj.getDateofadmission().toString());
				returnObj.setToaStr(babyDetailObj.getTimeofadmission());
				returnObj.setBabyname(babyDetailObj.getBabyname());
				returnObj.setBabyType(babyDetailObj.getBabyType());
				returnObj.setBabyNumber(babyDetailObj.getBabyNumber());
//				returnObj.setMothername(babyDetailObj);

				String antenatalHistoryDetailSql = "select obj from AntenatalHistoryDetail as obj where uhid='" + uhid
						+ "' and episodeid='" + babyDetailObj.getEpisodeid() + "'";
				List<AntenatalHistoryDetail> antenatalHistoryDetailList = inicuDao
						.getListFromMappedObjQuery(antenatalHistoryDetailSql);

				if (!BasicUtils.isEmpty(antenatalHistoryDetailList)) {
					AntenatalHistoryDetail antenatalHistoryObj = antenatalHistoryDetailList.get(0);

					returnObj.setEdd_by(antenatalHistoryObj.getEddBy());
					returnObj.setNotknowntype(antenatalHistoryObj.getNotKnownType());
					returnObj.setTrimesterType(antenatalHistoryObj.getTrimesterType());
					returnObj.setGestationWeeks(antenatalHistoryObj.getGestationbyLmpWeeks());
					returnObj.setGestationDays(antenatalHistoryObj.getGestationbyLmpDays());

					if (antenatalHistoryObj.getLmpTimestamp() != null
							&& antenatalHistoryObj.getEddTimestamp() != null) {
						String lmpStr = CalendarUtility.dateFormatDB.format(antenatalHistoryObj.getLmpTimestamp());
						String eddStr = CalendarUtility.dateFormatDB.format(antenatalHistoryObj.getEddTimestamp());
						if (antenatalHistoryObj.getEtTimestamp() != null) {
							String etStr = CalendarUtility.dateFormatDB.format(antenatalHistoryObj.getEtTimestamp());
							returnObj.setEtTimestampStr(etStr);
						}

						returnObj.setLmpTimestampStr(lmpStr);
						returnObj.setEddTimestampStr(eddStr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
					" Exception in Update through Mandatory Popup Form", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	@Override
	public ResponseMessageWithResponseObject submitMandatoryForm(MandatoryFormPojo mandatoryFormObj, String uhid,
			String loggedUser) {
		ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
		System.out.println("Mandatory Form Obj: " + mandatoryFormObj);
		try {
			String babyDetailSql = "select obj from BabyDetail as obj where uhid='" + uhid
					+ "' order by creationtime desc";
			List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailSql);

			if (!BasicUtils.isEmpty(babyDetailList)) {
				BabyDetail babyDetailObj = babyDetailList.get(0);
				AntenatalHistoryDetail antenatalHistoryObj = null;

				if (!BasicUtils.isEmpty(mandatoryFormObj.getDobStr())) {
					try {
						System.out.println(mandatoryFormObj.getDobStr() + " initalDOB");
						String[] strArr = mandatoryFormObj.getDobStr().split("-");
						Date dobDate = new Date(parseInt(strArr[0]) - 1900, parseInt(strArr[1]) - 1,
								parseInt(strArr[2]));
						babyDetailObj.setDateofbirth(dobDate);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
								loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
								BasicUtils.convertErrorStacktoString(e));
					}
				}
				Date doaDate = null;

				if (!BasicUtils.isEmpty(mandatoryFormObj.getDoaStr())) {
					try {
						String[] strArr = mandatoryFormObj.getDoaStr().split("-");
						doaDate = new Date(parseInt(strArr[0]) - 1900, parseInt(strArr[1]) - 1,
								parseInt(strArr[2]));
						babyDetailObj.setDateofadmission(doaDate);
					} catch (NumberFormatException e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
								loggedUser, uhid, " Exception in Sumbit Advance Admission Form",
								BasicUtils.convertErrorStacktoString(e));
					}
				}

//
//				if (!BasicUtils.isEmpty(mandatoryFormObj.getDobStr())) {
//					Date dobDate = CalendarUtility.dateFormatDB.parse(mandatoryFormObj.getDobStr());
//					babyDetailObj.setDateofbirth(dobDate);
//				}
//
//				if (!BasicUtils.isEmpty(mandatoryFormObj.getDoaStr())) {
//					Date doaDate = CalendarUtility.dateFormatDB.parse(mandatoryFormObj.getDoaStr());
//					babyDetailObj.setDateofadmission(doaDate);
//
//				}

				babyDetailObj.setTimeofbirth(mandatoryFormObj.getTobStr());
				babyDetailObj.setTimeofadmission(mandatoryFormObj.getToaStr());
				babyDetailObj.setInoutPatientStatus(mandatoryFormObj.getPatientType());
				babyDetailObj.setBirthweight(mandatoryFormObj.getBirthWeight());
				babyDetailObj.setGender(mandatoryFormObj.getGender());

				babyDetailObj.setBabyType(mandatoryFormObj.getBabyType());
				babyDetailObj.setBabyNumber(mandatoryFormObj.getBabyNumber());

				if(mandatoryFormObj.getAdmissionDate()!= null){
					babyDetailObj.setDateofadmission(mandatoryFormObj.getAdmissionDate());
				}

				if(mandatoryFormObj.getAdmissionTime()!= null){
					babyDetailObj.setTimeofadmission(mandatoryFormObj.getAdmissionTime());
				}

				babyDetailObj.setGestationdaysbylmp(mandatoryFormObj.getGestationDays());
				babyDetailObj.setGestationweekbylmp(mandatoryFormObj.getGestationWeeks());
				babyDetailObj.setActualgestationdays(mandatoryFormObj.getGestationDays());
				babyDetailObj.setActualgestationweek(mandatoryFormObj.getGestationWeeks());

				Double centile = growthChartService.getFentonCentile(mandatoryFormObj.getGender().toLowerCase(),
						mandatoryFormObj.getGestationWeeks().toString(), "weight", mandatoryFormObj.getBirthWeight());
				babyDetailObj.setWeight_centile(centile.floatValue());
				if (centile < 10) {
					babyDetailObj.setWeight_galevel("SGA");
				} else if (centile < 97) {
					babyDetailObj.setWeight_galevel("AGA");
				} else {
					babyDetailObj.setWeight_galevel("LGA");
				}

				babyDetailObj = (BabyDetail) inicuDao.saveObject(babyDetailObj);


				if(mandatoryFormObj.getAntenatalSteroidGiven()==true){

				// get the antenatal Steroid Details and update them
				String getAntenalSteroidHistory="select obj from AntenatalSteroidDetail obj where uhid ='"+uhid+"' order by creationtime desc";
				List<AntenatalSteroidDetail> antenalSteroidList = inicuDao.getListFromMappedObjQuery(getAntenalSteroidHistory);

					if(!BasicUtils.isEmpty(antenalSteroidList)){
						AntenatalSteroidDetail lastObject=antenalSteroidList.get(0);
						lastObject.setSteroidname(mandatoryFormObj.getSteroidName());
						lastObject.setGivendate(mandatoryFormObj.getGivenDate());
						lastObject.setNumberofdose(mandatoryFormObj.getNumberofDose());
						lastObject = (AntenatalSteroidDetail) inicuDao.saveObject(lastObject);
					}else{
						AntenatalSteroidDetail lastObject=new AntenatalSteroidDetail();
						lastObject.setSteroidname(mandatoryFormObj.getSteroidName());
						lastObject.setGivendate(mandatoryFormObj.getGivenDate());
						lastObject.setNumberofdose(mandatoryFormObj.getNumberofDose());
						lastObject.setUhid(uhid);
						lastObject = (AntenatalSteroidDetail) inicuDao.saveObject(lastObject);
					}
				}

				try {
					BabyVisit visitObj = null;
					String existingCheckSql = "select obj from BabyVisit as obj where uhid='" + uhid
							+ "' and admission_entry='true'";
					List<BabyVisit> existingCheck = inicuDao.getListFromMappedObjQuery(existingCheckSql);

					if (BasicUtils.isEmpty(existingCheck)) {
						visitObj = new BabyVisit();
					} else {
						visitObj = existingCheck.get(0);
					}

					if (!(BasicUtils.isEmpty(mandatoryFormObj.getBirthWeight()))) {
					
						visitObj.setUhid(uhid);
						visitObj.setLoggeduser(loggedUser);
						visitObj.setEpisodeid(babyDetailObj.getEpisodeid());
						visitObj.setAdmission_entry(true);
						visitObj.setCreationtime(new Timestamp(babyDetailObj.getDateofbirth().getTime()));
						visitObj.setModificationtime(new Timestamp(babyDetailObj.getDateofbirth().getTime()));
						visitObj.setVisitdate(babyDetailObj.getDateofbirth());
						visitObj.setCurrentage(babyDetailObj.getDayoflife());
						visitObj.setWeightForCal(babyDetailObj.getBirthweight());
						visitObj.setWorkingweight(babyDetailObj.getBirthweight());
						visitObj.setCurrentdateweight(babyDetailObj.getBirthweight());
						visitObj.setCurrentdateheight(babyDetailObj.getBirthlength());
						visitObj.setCurrentdateheadcircum(babyDetailObj.getBirthheadcircumference());

						String gestStr = ",";
						if (!(BasicUtils.isEmpty(babyDetailObj.getGestationweekbylmp())
								|| BasicUtils.isEmpty(babyDetailObj.getGestationdaysbylmp()))) {
							visitObj.setGestationWeek(babyDetailObj.getGestationweekbylmp());
							visitObj.setGestationDays(babyDetailObj.getGestationdaysbylmp());
							gestStr = babyDetailObj.getGestationweekbylmp() + ","
									+ babyDetailObj.getGestationdaysbylmp();
						}

						visitObj.setGaAtBirth(gestStr);
						visitObj.setCorrectedGa(gestStr);

						try {
							java.util.Date dateOfB = babyDetailObj.getDateofbirth();
							String timeOfBirth = babyDetailObj.getTimeofbirth();
							Calendar birthDateCal = CalendarUtility.getDateTimeFromDateAndTime(dateOfB, timeOfBirth);
							Date date = birthDateCal.getTime();
							visitObj.setVisittime(new Time(date.getTime()));
						} catch (Exception e) {
							e.printStackTrace();
						}
						inicuDao.saveObject(visitObj);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
							loggedUser, uhid, " Exception in Update through Mandatory Popup Form - Visit Entry",
							BasicUtils.convertErrorStacktoString(e));
				}

				String antenatalHistoryDetailSql = "select obj from AntenatalHistoryDetail as obj where uhid='" + uhid
						+ "' and episodeid='" + babyDetailObj.getEpisodeid() + "'";
				List<AntenatalHistoryDetail> antenatalHistoryDetailList = inicuDao
						.getListFromMappedObjQuery(antenatalHistoryDetailSql);

				if (BasicUtils.isEmpty(antenatalHistoryDetailList)) {
					antenatalHistoryObj = new AntenatalHistoryDetail();
					antenatalHistoryObj.setUhid(uhid);
					antenatalHistoryObj.setEpisodeid(babyDetailObj.getEpisodeid());

				} else {
					antenatalHistoryObj = antenatalHistoryDetailList.get(0);
				}

				antenatalHistoryObj.setEddBy(mandatoryFormObj.getEdd_by());
				antenatalHistoryObj.setNotKnownType(mandatoryFormObj.getNotknowntype());
				antenatalHistoryObj.setTrimesterType(mandatoryFormObj.getTrimesterType());

				antenatalHistoryObj.setGestationbyLmpDays(mandatoryFormObj.getGestationDays());
				antenatalHistoryObj.setGestationbyLmpWeeks(mandatoryFormObj.getGestationWeeks());
				antenatalHistoryObj.setGetstationbyUsgDays(mandatoryFormObj.getGestationDays());
				antenatalHistoryObj.setGetstationbyUsgWeeks(mandatoryFormObj.getGestationWeeks());

				if (!(BasicUtils.isEmpty(mandatoryFormObj.getEddTimestampStr()))) {
					Date eddDate = CalendarUtility.dateFormatDB.parse(mandatoryFormObj.getEddTimestampStr());
					Timestamp ts = new java.sql.Timestamp(eddDate.getTime());
					antenatalHistoryObj.setEddTimestamp(ts);
				}

				if (!(BasicUtils.isEmpty(mandatoryFormObj.getLmpTimestampStr()))) {
					Date lmpDate = CalendarUtility.dateFormatDB.parse(mandatoryFormObj.getLmpTimestampStr());
					Timestamp ts1 = new java.sql.Timestamp(lmpDate.getTime());
					antenatalHistoryObj.setLmpTimestamp(ts1);
				}

				if (!(BasicUtils.isEmpty(mandatoryFormObj.getEtTimestampStr()))) {
					Date etDate = CalendarUtility.dateFormatDB.parse(mandatoryFormObj.getEtTimestampStr());
					Timestamp ts2 = new java.sql.Timestamp(etDate.getTime());
					antenatalHistoryObj.setEtTimestamp(ts2);
				}

				antenatalHistoryObj = (AntenatalHistoryDetail) inicuDao.saveObject(antenatalHistoryObj);
				returnObj.setReturnedObject(mandatoryFormObj);
				returnObj.setMessage("Details Updated Successfully.");
				returnObj.setType("Success");


				// Update the Gestation Age of the Baby in the ICHR Database
				if (!BasicUtils.isEmpty(babyDetailObj) && !BasicUtils.isEmpty(babyDetailObj.getBranchname()) && babyDetailObj.getBranchname().equalsIgnoreCase("Moti Nagar, Delhi")) {
					try {
						String companyId = BasicConstants.ICHR_SCHEMA;
						String Uhid = babyDetailObj.getUhid();
						String fname = babyDetailObj.getBabyname().trim();
						String gender = babyDetailObj.getGender();
						String phoneNumber = "";
						String babyGestation = "";
						String lname = babyDetailObj.getBabyname().trim();

						String pattern = "yyyy-MM-dd";
						String date = "";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

						if (!BasicUtils.isEmpty(fname)) {
							fname = fname.replaceAll(" ", "%20");
						}

						if (!BasicUtils.isEmpty(babyDetailObj.getDateofbirth())) {
							date = simpleDateFormat.format(babyDetailObj.getDateofbirth());
							System.out.println(date);
						} else {
							Date date1 = simpleDateFormat.parse(babyDetailObj.getDobStr());
							date = simpleDateFormat.format(date1);
							System.out.println(date);
						}

						String DOB = date;

						if (!BasicUtils.isEmpty(lname)) {
							lname = lname.replaceAll(" ", "%20");
						}
						lname = "";

						if (!BasicUtils.isEmpty(babyDetailObj.getActualgestationweek())) {
							babyGestation = babyDetailObj.getActualgestationweek().toString();
						} else if (!BasicUtils.isEmpty(babyDetailObj.getGestationweekbylmp())) {
							babyGestation = babyDetailObj.getGestationweekbylmp().toString();
						}

						if (!BasicUtils.isEmpty(Uhid) && !BasicUtils.isEmpty(companyId) && !BasicUtils.isEmpty(fname) && !BasicUtils.isEmpty(gender) && !BasicUtils.isEmpty(DOB)) {
							String parameters = "uid=" + Uhid + "&companyId=" + companyId + "&fname=" + fname + "&lname=" + lname + "&gender=" + gender + "&DOB=" + DOB + "&phonenumber=" + phoneNumber + "&babyGestation=" + babyGestation;
							System.out.println("Parameters are :" + parameters);
							String response = null;
							try {
								response = SimpleHttpClient.executeHttpGet(BasicConstants.ADMIT_PATIENT + parameters);
							} catch (Exception e) {
								e.printStackTrace();
							}
							System.out.println("Post Result string returned" + response);
						} else {
							String parameters = "uid=" + Uhid + "&companyId=" + companyId + "&fname=" + fname + "&lname=" + lname + "&gender=" + gender + "&DOB=" + DOB + "&phonenumber=" + phoneNumber + "&babyGestation=" + babyGestation;
							System.out.println("Parameters are :" + parameters);
						}
					} catch (Exception e) {
						System.out.println("Exception caught :" + e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObj.setType("Failure");
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
					" Exception in Update through Mandatory Popup Form", BasicUtils.convertErrorStacktoString(e));
		}
		return returnObj;
	}

	// General & Helper Functions
	// DOB Fetching Function
	public Long getDayOfLife(String uhid, Timestamp otherDate) throws InicuDatabaseExeption {
		List<BabyDetail> babyList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
		if (!BasicUtils.isEmpty(babyList)) {
			BabyDetail babyObj = babyList.get(0);
			java.sql.Timestamp dateOfBirth = new java.sql.Timestamp(babyObj.getDateofbirth().getTime());
			int hours = parseInt(babyObj.getTimeofbirth().substring(0, 2));
			int minutes = parseInt(babyObj.getTimeofbirth().substring(3, 5));
			if (babyObj.getTimeofbirth().substring(6, 8).equals("PM") && hours != 12) {
				hours = hours + 12;
			}
			dateOfBirth.setHours(hours);
			dateOfBirth.setMinutes(minutes);
			dateOfBirth.setSeconds(0);

			Long dayOfLife = (otherDate.getTime() - dateOfBirth.getTime()) / (1000 * 60 * 60 * 24);
			return dayOfLife;
		} else {
			return null;
		}
	}

	private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("dd-MM-yyyy");

	// Get Date from Timestamp
	public static String getDateFromTimestamp(Timestamp time) {
		String date = monthDayYearformatter.format((java.util.Date) time);
		return date;
	}

	private String getCausesOfBpdRespiratory(String uhid) {

		String bpdRespiratoryNotes = "";
		String pma = null;
		Integer gestationweek = 0;
		Integer gestationday = 0;
		boolean bpd = false;

		String bpdTempNote = null;
		String querybpdRespNote = "select obj from RespSupport obj where uhid='" + uhid
				+ "' and bpdRespNote is not null order by creationtime desc";
		List<RespSupport> bpdRespNoteList = inicuDao.getListFromMappedObjQuery(querybpdRespNote);
		if (!BasicUtils.isEmpty(bpdRespNoteList)) {
			bpdTempNote = bpdRespNoteList.get(0).getBpdRespNote();
		}

		if (!BasicUtils.isEmpty(bpdTempNote)) {

			String queryPma = "select obj from DashboardFinalview obj where uhid='" + uhid
					+ "' and dayoflife is not null order by creationtime desc";
			List<DashboardFinalview> pmaList = inicuDao.getListFromMappedObjQuery(queryPma);
			if (!BasicUtils.isEmpty(pmaList)) {
				pma = pmaList.get(0).getDayoflife();
			}
			String queryCurrentGestation = "select gestationweek,gestationdays from baby_visit" + " where uhid='" + uhid
					+ "' order by creationtime desc";
			List<Object[]> currentGestationList = inicuDao.getListFromNativeQuery(queryCurrentGestation);
			if (!BasicUtils.isEmpty(currentGestationList)) {
				gestationweek = parseInt(currentGestationList.get(0)[0].toString());
				gestationday = parseInt(currentGestationList.get(0)[1].toString());
			}
			// boolean bpdActive = false;
			if (bpdTempNote.contains("36")
					&& (!bpdTempNote.contains("bpdgest") || !bpdTempNote.contains("bpdpma"))) {
				bpdRespiratoryNotes = bpdTempNote;
				bpd = true;
			} else if (pma.contains("56") && (!bpdTempNote.contains("bpdgest") || !bpdTempNote.contains("bpdpma"))) {
				bpdRespiratoryNotes = bpdTempNote;
				bpd = true;
			} 
			
			if(bpd) {

				String queryDischargeStatus = "select obj from BabyDetail obj where uhid='" + uhid
						+ "' and admissionstatus = 'false' order by creationtime desc";
				List<BabyDetail> dischargeStatusList = inicuDao.getListFromMappedObjQuery(queryDischargeStatus);
				if (!BasicUtils.isEmpty(dischargeStatusList)) {
					// discharged

					String tempRespiratoryNote = "";
					String queryRespSupport = "select obj from RespSupport obj where uhid='" + uhid
							+ "' order by creationtime desc";
					List<RespSupport> respSupportList = inicuDao.getListFromMappedObjQuery(queryRespSupport);
					if (!BasicUtils.isEmpty(respSupportList)) {
						RespSupport respObj = respSupportList.get(0);
						String rsVentType = "";
						if (!BasicUtils.isEmpty(respObj.getRsVentType()) && (respObj.getRsVentType().equals("HFO")
								|| respObj.getRsVentType().equals("Mechanical Ventilation"))) {
							tempRespiratoryNote = " severe";
							rsVentType = respObj.getRsVentType();

						} else if (!BasicUtils.isEmpty(respObj.getRsVentType())
								&& (respObj.getRsVentType().equals("CPAP")
										|| respObj.getRsVentType().equals("Low Flow O2")
										|| respObj.getRsVentType().equals("High Flow O2")
										|| respObj.getRsVentType().equals("NIMV"))) {
							tempRespiratoryNote = " moderate";
							rsVentType = respObj.getRsVentType();
						} else {
							tempRespiratoryNote = " mild";
							rsVentType = "room air";
						}
						tempRespiratoryNote += tempRespiratoryNote + " at " + gestationweek
								+ " week of gestation.Presently the baby PMA is " + pma + " days and is on "
								+ rsVentType;
						if (!BasicUtils.isEmpty(respObj.getRsMap())) {
							tempRespiratoryNote += " with MAP " + respObj.getRsMap();
						}
						if (!BasicUtils.isEmpty(respObj.getRsFio2())) {
							tempRespiratoryNote += " and FiO2 " + respObj.getRsFio2() + ".";
						}
					}
					if (bpdTempNote.contains("bpdgest")) {
						bpdTempNote = bpdTempNote.replace("bpdgest", tempRespiratoryNote);
					} else if (bpdTempNote.contains("bpdpma")) {
						bpdTempNote = bpdTempNote.replace("bpdpma", tempRespiratoryNote);
					}
					bpdRespiratoryNotes = bpdTempNote;
				}
			}
		}
		return bpdRespiratoryNotes;
	}

	private String getIcdCodeList(String uhid) {

		String icdCode = "";

		String queryIcdCode = "select obj from SaJaundice obj where uhid='" + uhid
				+ "' and icdCauseofjaundice is not null order by creationtime desc";
		List<SaJaundice> icdCodeList = inicuDao.getListFromMappedObjQuery(queryIcdCode);
		if (!BasicUtils.isEmpty(icdCodeList)) {

			for (SaJaundice jaun : icdCodeList) {
				if (BasicUtils.isEmpty(icdCode)) {
					icdCode += jaun.getIcdCauseofjaundice().replaceAll(",", "/").replace("[", "").replace("]", "");
				} else {
					icdCode += "/"
							+ jaun.getIcdCauseofjaundice().replaceAll(",", "/").replace("[", "").replace("]", "");
				}
			}

			String[] icdCodeArr = icdCode.split("/");
			icdCode = "";
			for (String icdCodeStr : icdCodeArr) {
				if (icdCode.isEmpty()) {
					icdCode = icdCodeStr;
				} else if (!icdCode.contains(icdCodeStr.trim())) {
					icdCode += "/ " + icdCodeStr.trim();
				}
			}
		}

		return icdCode;
	}

	@Override
	public GeneralResponseObject getPrintData(printDataRequestObject requestObject, String branchName, String printDate, String loggedUser) {
		GeneralResponseObject generalResponseObject=new GeneralResponseObject();
		Timestamp tomorrow=null;
		Timestamp today=null;
		try {
			if(!BasicUtils.isEmpty(printDate)) {

//				Timestamp fromDate = new Timestamp(Long.parseLong(fromTime));

//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//				java.util.Date parsedDate = dateFormat.parse(printDate);

				today = new Timestamp(Long.parseLong(printDate));
				tomorrow = new Timestamp((today.getTime()) + (24 * 60 * 60 * 1000));

				today.setHours(8);
				today.setMinutes(0);
				today.setSeconds(0);

				tomorrow.setHours(8);
				tomorrow.setMinutes(0);
				tomorrow.setSeconds(0);
			}

//			HashMap<String,PrintDataPOJO> printData= new HashMap<>();
			ArrayList<PrintDataPOJO> printData= new ArrayList<>();
			List<String> uhidList=requestObject.getUhidList();

			if(!BasicUtils.isEmpty(uhidList) && uhidList.size()>0){
				for (String babyUhid: uhidList) {

					PrintDataPOJO printDataPOJO=new PrintDataPOJO();
					DailyProgressPOJO dailyProgressNotes=new DailyProgressPOJO();

					BabyDetail babyDetail=getBabyDetails(babyUhid);
					if(!BasicUtils.isEmpty(babyDetail)){

						// get the baby room number, bed number, criticality ,current weight and previous weight
						List<DashboardJSON> listOfDashboard = userPanel.getDashboardUser(new DashboardSearchFilterObj(), babyUhid);

						DashboardJSON lastObject=null;
						if(listOfDashboard!=null && listOfDashboard.size()>0) {
							lastObject = listOfDashboard.get(0);
							babyDetail.setRoomNumber(lastObject.getBabyRoom());
							babyDetail.setBedNumber(lastObject.getBed().getValue());
							babyDetail.setCondition(lastObject.getCondition());
							babyDetail.setCurrentdateweight(lastObject.getCurrentDayWeight());
							babyDetail.setPrevDateWeight(lastObject.getLastDayWeight());
						}
						printDataPOJO.setBabyDetail(babyDetail);
					}

					// check initialAssessment
					if(requestObject.isInitialAssessment()){
						// get the initial assessment data for uhid
						try {
							AdvanceAdmitPatientPojo advanceAdmitPatientPojo = getAdvanceAdmissionForm(babyUhid, loggedUser);
							printDataPOJO.setInitialAssessment(advanceAdmitPatientPojo);
						}catch (Exception e){
							System.out.println("Exception Caught Initial Assessment"+e);
							printDataPOJO.setInitialAssessment(null);
						}
					}

					// Daily Notes
					// check doctor notes
					if(requestObject.isDoctorsNotes()){
						try {
							DailyProgressNotes doctorsNotes = notesService.getDailyProgressNotes(babyUhid, today.toString(), null, null, branchName, loggedUser);
							dailyProgressNotes.setDoctorProgressNotes(doctorsNotes);
						}catch (Exception e){
							System.out.println("Exception Caught Daily Progress Notes"+e);
							dailyProgressNotes.setDoctorProgressNotes(null);
						}
					}

					// check doctor orders
					if(requestObject.isDoctorsOrders()){
						try {
							DoctorOrdersPOJO doctorOrders=notesService.getDoctorOrders(babyUhid,today.toString());
							dailyProgressNotes.setDoctorOrders(doctorOrders);
						}catch (Exception e){
							System.out.println("Exception Caught Doctors Orders"+e);
							dailyProgressNotes.setDoctorOrders(null);
						}
					}

					// check nursing output
					if(requestObject.isNursingOutput()){
						try {
							NursingAllDataPOJO nursingAllDataPOJO = notesService.getAllNursingData(babyUhid, String.valueOf(today.getTime()), String.valueOf(tomorrow.getTime()),branchName);
							dailyProgressNotes.setNursingOutput(nursingAllDataPOJO);
						}catch (Exception e){
							System.out.println("Exception Caught Nursing Output"+e);
							dailyProgressNotes.setNursingOutput(null);
						}
					}

					// check medication charts
					if(requestObject.isMedicationChart()){
						BabyPrescriptionObject prescription = new BabyPrescriptionObject();
						try {
							prescription = prescriptionService.getBabyPrescription(babyUhid, loggedUser, babyDetail.getGestationdaysbylmp(), babyDetail.getGestationweekbylmp(), today.toString(), parseInt(babyDetail.getDayoflife()), null, branchName);
							List<KeyValueObj> imageData = null;
							List<KeyValueObj> signatureImageData = null;
							imageData = settingService.getHospitalLogo(branchName);
							prescription.setLogoImage(imageData);
							try {
								signatureImageData = userPanel.getImageData(loggedUser, branchName);
								prescription.setSignatureImage(signatureImageData);
							} catch (Exception e) {
								e.printStackTrace();
							}
							dailyProgressNotes.setMedicationChart(prescription);
						} catch (Exception e) {
							System.out.println("Exception Caught Medication"+e);
							dailyProgressNotes.setMedicationChart(null);
						}
					}

					// check pn charts
					if(requestObject.isPnChart()){
						try {
							TpnFeedPojo tpnFeedPojo = notesService.getTpnFeedDetails(babyUhid, loggedUser);
							dailyProgressNotes.setPnChart(tpnFeedPojo);
						}catch (Exception e){
							System.out.println("Exception Caught PN Chart"+e);
							dailyProgressNotes.setMedicationChart(null);
						}
					}
					// changed the list type
					printDataPOJO.setDailyNotes(dailyProgressNotes);
					printData.add(printDataPOJO);
				}

				// get the Dropdown - PN Chart
                PrintoutDropdown printoutDropdown=new PrintoutDropdown();
				printoutDropdown.setPnChartsDropDownsObj(getNursingNotesDropDowns());

				generalResponseObject.setData(printData);
				generalResponseObject.setPrintoutDropdown(printoutDropdown);
				generalResponseObject.setStatus(true);
				generalResponseObject.setStatusCode(200);
				generalResponseObject.setMessage("Print Data");
			}else{
				generalResponseObject.setStatus(true);
				generalResponseObject.setStatusCode(304);
				generalResponseObject.setMessage("Print data not found");
			}

		} catch(Exception e) { //this generic but you can control another types of exception
			// look the origin of excption
			generalResponseObject.setStatus(false);
			generalResponseObject.setData(null);
			generalResponseObject.setStatusCode(500);
			generalResponseObject.setMessage("Internal Server Error");
			System.out.println(e);
		}

		return generalResponseObject;
	}

	@Override
	public GeneralResponseObject getPrintBabies(String branchName, String date) {
		Timestamp today=null;
		Timestamp tomorrow=null;
		GeneralResponseObject generalResponseObject=new GeneralResponseObject();
		List<BabyBasicDetail>  babyDetailList = new ArrayList<>();
		String uhidList="";
		String bedid="";
		String roomid="";

		if(!BasicUtils.isEmpty(date)) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parsedDate = dateFormat.parse(date);
				today = new java.sql.Timestamp(parsedDate.getTime());
				today.setHours(8);
				today.setMinutes(0);
				today.setSeconds(0);

				tomorrow = new java.sql.Timestamp(parsedDate.getTime()+24*60*60*1000);
				tomorrow.setHours(8);
				tomorrow.setMinutes(0);
				tomorrow.setSeconds(0);

				String fetchBabyQuery ="select uhid,babyname,baby_number,baby_type,nicuroomno,nicubedno from baby_detail where '"+ tomorrow+"'>(cast (concat(dateofadmission,' ',regexp_replace(regexp_replace(regexp_replace(timeofadmission,',',' '),',',' '),' ',':')) as timestamp)) " +
						"and (dischargeddate>='"+today+"' OR admissionstatus = 'true') and branchname='"+branchName+"'";

				List<Object[]> babyDetails = inicuDao.getListFromNativeQuery(fetchBabyQuery);

				if(!BasicUtils.isEmpty(babyDetails)) {

					for (Object[] babyObj : babyDetails) {
						// Store Uhid
						if (uhidList.isEmpty()) {
							uhidList += "'" + babyObj[0].toString() + "'";
						} else {
							uhidList += ", '" + babyObj[0].toString() + "'";
						}

						// Store bedId
						if (bedid.isEmpty()) {
							bedid += "'" + babyObj[5].toString() + "'";
						} else {
							bedid += ", '" + babyObj[5].toString() + "'";
						}

						//Store Roomid
						if (roomid.isEmpty()) {
							roomid += "'" + babyObj[4].toString() + "'";
						} else {
							roomid += ", '" + babyObj[4].toString() + "'";
						}
					}

					HashMap<String, String> refBedHash=new HashMap<>();
					HashMap<String, String> refRoomHash=new HashMap<>();
					if(!BasicUtils.isEmpty(roomid) && !BasicUtils.isEmpty(bedid)) {
						String fetchRefBed = HqlSqlQueryConstants.getRefBed(bedid, branchName);
						List<Object[]> listRefBed = userServiceDao.executeNativeQuery(fetchRefBed);
						refBedHash = getRefKeyValueObject(listRefBed);

						String fetchRefRoom = HqlSqlQueryConstants.getRefRoom(roomid, branchName);
						List<Object[]> listRefRoom = userServiceDao.executeNativeQuery(fetchRefRoom);
						refRoomHash = getRefKeyValueObject(listRefRoom);
					}

					for (Object[] babyObj:babyDetails) {
						BabyBasicDetail babyBasicDetail=new BabyBasicDetail(babyObj);
						if(babyObj[4]!=null) {
							babyBasicDetail.setNicuRoom(refRoomHash.get(babyObj[4].toString().trim()));
						}
						if(babyObj[5]!=null) {
							babyBasicDetail.setBedNumber(refBedHash.get(babyObj[5].toString().trim()));
						}
						babyDetailList.add(babyBasicDetail);
					}
					generalResponseObject.setStatus(true);
					generalResponseObject.setStatusCode(200);
					generalResponseObject.setMessage("Active Babies");
					generalResponseObject.setData(babyDetailList);
				}else {
					generalResponseObject.setStatus(false);
					generalResponseObject.setStatusCode(412);
					generalResponseObject.setMessage("No data available for the given date");
					generalResponseObject.setData(null);
				}
			} catch (ParseException e) {
				e.printStackTrace();
				generalResponseObject.setStatus(false);
				generalResponseObject.setStatusCode(500);
				generalResponseObject.setMessage("Internal server Error");
				generalResponseObject.setData(null);
			}
		}else{
			generalResponseObject.setStatus(false);
			generalResponseObject.setStatusCode(304);
			generalResponseObject.setMessage("Invalid date passed");
			generalResponseObject.setData(null);
		}
		return generalResponseObject;
	}

	public HashMap<String, String> getRefKeyValueObject(List<Object[]> refList) {
		HashMap<String, String> refValueHash = new HashMap<>();
		if (!refList.isEmpty()) {
			Iterator<Object[]> refListIterator = refList.iterator();
			while (refListIterator.hasNext()) {
				Object[] refObj = refListIterator.next();
				refValueHash.put(refObj[0].toString(), refObj[1].toString().substring(refObj[1].toString().length() - 1,
						refObj[1].toString().length()));
			}
		}
		return refValueHash;
	}
}
