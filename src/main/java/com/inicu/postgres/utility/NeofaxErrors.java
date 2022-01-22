package com.inicu.postgres.utility;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.PrescriptionDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.AntenatalHistoryDetail;
import com.inicu.postgres.entities.AntenatalSteroidDetail;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.BirthToNicu;
import com.inicu.postgres.entities.ReasonAdmission;
import com.inicu.postgres.entities.RefMedSolutions;
import com.inicu.postgres.entities.RefMedfrequency;
import com.inicu.postgres.entities.RefMedicine;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaCnsAsphyxia;
import com.inicu.postgres.entities.SaCnsSeizures;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaMiscellaneous;
import com.inicu.postgres.entities.SaRespApnea;
import com.inicu.postgres.entities.SaRespPneumo;
import com.inicu.postgres.entities.SaRespPphn;
import com.inicu.postgres.entities.SaRespRds;
import com.inicu.postgres.entities.SaSepsis;
import com.inicu.postgres.entities.ScreenRop;
import com.inicu.postgres.entities.StableNote;
import com.inicu.postgres.service.NotesService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Repository
public class NeofaxErrors extends Thread{

	@Autowired
	PrescriptionDao prescriptionDao;
	
	@Autowired
	PatientDao patientDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	NotesService notesService;

	@Autowired
	NotesDAO notesDoa;

	@Autowired
	InicuDao inicuDoa;
	
	@Autowired
	SettingDao settingDao;

	@Override
	public void run(){
		XSSFWorkbook workbook = null;
		try {
			
				        
	        XSSFWorkbook finalWorkbook = new XSSFWorkbook();
	        XSSFSheet sheet = finalWorkbook.createSheet("Dose");
	        XSSFSheet sheet2 = finalWorkbook.createSheet("Frequency");

			int rowCount = 0;
	    	Row rowMed = sheet.createRow(rowCount);
	    	++rowCount;
			int columnCount = 0;
			Cell cell = rowMed.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			
			int columnCellCount = 48;
			Row rowMed1 = sheet.getRow(0);
			for(int i = 0; i < 250; i++) {
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Medicine.Name");
	    		cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Given");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Recommended");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Recommended.Consideration");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Diff");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Result");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("DOL");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Error.Days");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Dose.Unit");
			}
	    	
	    	int rowFrequencyCount = 0;
	    	rowMed = sheet2.createRow(rowFrequencyCount);
	    	++rowFrequencyCount;
			columnCount = 0;
			cell = rowMed.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMed.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			columnCellCount = 48;
			rowMed1 = sheet2.getRow(0);
			for(int i = 0; i < 250; i++) {
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Medicine.Name");
	    		cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Given");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Recommended");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Recommended.Consideration");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Diff");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Result");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("DOL");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Error.Days");
				cell = rowMed1.createCell(++columnCellCount);
				cell.setCellValue("Frequency.Unit");
			}
	    	
			String babyQuery = "";
			//Apollo
			if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("apollo")) {
				babyQuery = "select distinct(b.uhid),b.dateofadmission,b.dateofbirth,b.dischargeddate,b.timeofadmission,b.gestationweekbylmp,b.gestationdaysbylmp,b.birthweight,b.gender,b.inout_patient_status,b.baby_type,b.timeofbirth,b.episodeid from " + BasicConstants.SCHEMA_NAME + 
			    		".baby_detail as b where b.uhid IN " +
				    	"(				select a.uhid from "+BasicConstants.SCHEMA_NAME+ ".babyfeed_detail as a where  " + 
				    	"					 (b.dischargestatus = 'Discharge' or b.admissionstatus is true) " + 
				    	"					and b.branchname ILIKE '%MOTI%'" +   
				    	"					and b.dateofadmission >= '2018-07-01'" + 
				    	"					and b.gestationweekbylmp is not null" + 
				    	"					and b.birthweight is not null " + 
				    	"					and (b.isreadmitted is null or b.isreadmitted is false)" + 
				    	"					and b.uhid NOT IN ('RSHI.0000014177','RSHI.0000012200')" + 
				    	"	) order by b.dateofadmission";
			}
			//Kalawati
			else if(BasicConstants.SCHEMA_NAME.equalsIgnoreCase("kalawati")) {
				babyQuery = "select distinct(b.uhid),b.dateofadmission,b.dateofbirth,b.dischargeddate,b.timeofadmission,b.gestationweekbylmp,b.gestationdaysbylmp,b.birthweight,b.gender,b.inout_patient_status,b.baby_type,b.timeofbirth,b.episodeid from " + BasicConstants.SCHEMA_NAME + 
			    		".baby_detail as b where b.uhid IN " +
				    	"(				select a.uhid from "+BasicConstants.SCHEMA_NAME+ ".babyfeed_detail as a where " + 
				    	"					 (b.dischargestatus = 'Discharge' or b.admissionstatus is true) " + 
				    	"					and b.birthweight is not null " + 
				    	"					and b.gestationweekbylmp is not null" + 
				    	"					and b.dateofadmission >= '2018-01-01'" + 
				    	"					and (b.isreadmitted is null or b.isreadmitted is false)" +
				    	"					and b.uhid NOT IN ('180902397','180103775','180403434')" + 
				    	"	) order by b.dateofadmission";
			}

			List<Object[]> activeAssessmentList = inicuDoa.getListFromNativeQuery(babyQuery);
			Iterator<Object[]> itr = activeAssessmentList.iterator();

			while (itr.hasNext()) {
				boolean isValidDataPN = true;

				Object[] obj = itr.next();
				Date doa = (Date) obj[1];
				Timestamp dod = new Timestamp(new Date().getTime());
				if(BasicUtils.isEmpty(obj[3])) {
					dod = new Timestamp(new Date().getTime());
				}else {
					dod = (Timestamp) obj[3];
				}
				
				Date dob = (Date) obj[2];
				String tob = (String) obj[11];
				String toa = (String) obj[4];
				
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				//Merging date of admission and time of admission
				Timestamp creationTime = new Timestamp(new Date().getTime());
				if (!BasicUtils.isEmpty(doa)) {
					creationTime = new Timestamp(doa.getTime());
					if (!BasicUtils.isEmpty(toa)) {
						if(toa.contains(",")) {
							String[] toaArr = toa.split(",");
							// "10,38,PM"
							if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
							} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(0);
							} else {
								creationTime.setHours(Integer.parseInt(toaArr[0]));
							}
							creationTime.setMinutes(Integer.parseInt(toaArr[1]));
						}else if(toa.contains(":")) {
							String[] toaArr = toa.split(":");
							// "10,38,PM"
							if (toa.contains("PM") && !toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
							} else if (toa.contains("AM") && toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(0);
							} else {
								creationTime.setHours(Integer.parseInt(toaArr[0]));
							}
							creationTime.setMinutes(0);
						}
					}
					creationTime = new Timestamp(creationTime.getTime() - offset);
				}
				Timestamp dateofadmission = new Timestamp(creationTime.getTime());
				
				if((dod.getTime() - dateofadmission.getTime() / (24*60*60*1000)) <= 1) {
					isValidDataPN = false;
				}

				if(!BasicUtils.isEmpty(obj[3])) {
					List<BabyfeedDetail> feedListObjTemp = (List<BabyfeedDetail>) notesDoa.getListFromMappedObjNativeQuery(
							HqlSqlQueryConstants.getBabyfeedListOrder(obj[0].toString()));
					if(!BasicUtils.isEmpty(feedListObjTemp)) {
						BabyfeedDetail tempFeed = feedListObjTemp.get(0);
		 				if (!BasicUtils.isEmpty(tempFeed.getGirvalue()) && !tempFeed.getGirvalue().trim().isEmpty()) {
		 					isValidDataPN = false;
		 				}
					}
				}
				if(isValidDataPN) {

					boolean isSteroidGiven = false;
					String mod = "";
					
					//Newly Added
					boolean hypertension = false;
					boolean gestationalHypertension = false;
					boolean diabetes = false;
					boolean gestationalDiabetes = false;
					boolean isRds = false;
					String resuscitation = "";
					boolean isChronicDisease = false;
					boolean hypothyroidism = false;
					boolean hyperthyroidism = false;
					boolean noneDisease = false;
					boolean fever = false;
					boolean uti = false;
					boolean history_of_infections = false;
					boolean noneInfection = false;
					boolean prom = false;
					boolean pprom = false;
					boolean prematurity = false;
					boolean chorioamniotis = false;
					boolean oligohydraminos = false;
					boolean polyhydraminos = false;
					boolean noneOther = false;
	
					long invasiverespDays = 0;
					long nonInvasiverespDays = 0;
					boolean isJaundice = false;
					long phototherapyHours = 0;
					boolean isSepsis = false;
					boolean isBloodCulture = false;
					boolean isProbableSepsis = false;
					boolean isAsphyxia = false;
					boolean isPneumothorax = false;
					boolean isPPHN = false;
					Integer apgarOne = -1;
					Integer apgarFive = -1;
					String episodeId = (String) obj[12];
					
					boolean isInvasive = false;
					
					boolean resuscitationInitialSteps = false;
					boolean resuscitationO2 = false;
					boolean resuscitationPPV = false;
					boolean resuscitationChestCompression = false;
					String umblicalDoppler = "";
					String umblicalDopplerType = "";
					long phototherapyDays = 0;
					boolean isRdsTTNB = false;
					boolean isRdsMAS = false;
					
					String BirthToNicuQuery = "select obj from BirthToNicu obj where uhid = '" + obj[0].toString() + "' and episodeid = '" + episodeId + "' order by creationtime desc";
					List<BirthToNicu> BirthToNicuQueryList = prescriptionDao.getListFromMappedObjNativeQuery(BirthToNicuQuery);
					if(!BasicUtils.isEmpty(BirthToNicuQueryList)) {
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getApgarOnemin())) {
							apgarOne = BirthToNicuQueryList.get(0).getApgarOnemin();
						}
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getApgarFivemin())) {
							apgarFive = BirthToNicuQueryList.get(0).getApgarFivemin();
						}
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getResuscitation()) && BirthToNicuQueryList.get(0).getResuscitation()) {
							resuscitation = "true";
						}
						else if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getResuscitation()) && !BirthToNicuQueryList.get(0).getResuscitation()) {
							resuscitation = "false";
						}
						
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getResuscitationO2()) && BirthToNicuQueryList.get(0).getResuscitationO2()) {
							resuscitationO2 = true;
						}
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getInitialStep()) && BirthToNicuQueryList.get(0).getInitialStep()) {
							resuscitationInitialSteps = true;
						}
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getResuscitationPpv()) && BirthToNicuQueryList.get(0).getResuscitationPpv()) {
							resuscitationPPV = true;
						}
						if(!BasicUtils.isEmpty(BirthToNicuQueryList.get(0).getResuscitationChesttubeCompression()) && BirthToNicuQueryList.get(0).getResuscitationChesttubeCompression()) {
							resuscitationChestCompression = true;
						}
					
					}
	
					String antenatalQuery = "select obj from AntenatalHistoryDetail obj where uhid = '" + obj[0].toString() + "' and episodeid = '" + episodeId + "' order by creationtime desc";
					List<AntenatalHistoryDetail> antenatalList = prescriptionDao.getListFromMappedObjNativeQuery(antenatalQuery);
					if(!BasicUtils.isEmpty(antenatalList)) {
						if(!BasicUtils.isEmpty(antenatalList.get(0).getIsAntenatalSteroidGiven()) && antenatalList.get(0).getIsAntenatalSteroidGiven().equalsIgnoreCase("true")) {
							isSteroidGiven = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getModeOfDelivery())) {
							mod = antenatalList.get(0).getModeOfDelivery();
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getHypertension()) && antenatalList.get(0).getHypertension() == true) {
							hypertension = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getGestationalHypertension()) && antenatalList.get(0).getGestationalHypertension() == true) {
							gestationalHypertension = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getDiabetes()) && antenatalList.get(0).getDiabetes() == true) {
							diabetes = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getGdm()) && antenatalList.get(0).getGdm() == true) {
							gestationalDiabetes = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getChronicKidneyDisease()) && antenatalList.get(0).getChronicKidneyDisease() == true) {
							isChronicDisease = true;
						}
	
						if(!BasicUtils.isEmpty(antenatalList.get(0).getHypothyroidism()) && antenatalList.get(0).getHypothyroidism() == true) {
							hypothyroidism = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getHyperthyroidism()) && antenatalList.get(0).getHyperthyroidism() == true) {
							hyperthyroidism = true;
						}
	
						if(!BasicUtils.isEmpty(antenatalList.get(0).getNoneDisease()) && antenatalList.get(0).getNoneDisease() == true) {
							noneDisease = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getFever()) && antenatalList.get(0).getFever() == true) {
							fever = true;
						}
						
						if(!BasicUtils.isEmpty(antenatalList.get(0).getUti()) && antenatalList.get(0).getUti() == true) {
							uti = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getHistoryOfInfections()) && antenatalList.get(0).getHistoryOfInfections() == true) {
							history_of_infections = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getNoneInfection()) && antenatalList.get(0).getNoneInfection() == true) {
							noneInfection = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getProm()) && antenatalList.get(0).getProm() == true) {
							prom = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getPprom()) && antenatalList.get(0).getPprom() == true) {
							pprom = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getPrematurity()) && antenatalList.get(0).getPrematurity() == true) {
							prematurity = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getChorioamniotis()) && antenatalList.get(0).getChorioamniotis() == true) {
							chorioamniotis = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getOligohydraminos()) && antenatalList.get(0).getOligohydraminos() == true) {
							oligohydraminos = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getPolyhydraminos()) && antenatalList.get(0).getPolyhydraminos() == true) {
							polyhydraminos = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getNoneOther()) && antenatalList.get(0).getNoneOther() == true) {
							noneOther = true;
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getUmbilicalDoppler())) {
							umblicalDoppler = antenatalList.get(0).getUmbilicalDoppler();
						}
						if(!BasicUtils.isEmpty(antenatalList.get(0).getAbnormalUmbilicalDopplerType())) {
							umblicalDopplerType = antenatalList.get(0).getAbnormalUmbilicalDopplerType();
						}
					}
					
					
					String rdsQuery = "select obj from SaRespRds obj where uhid = '" + obj[0].toString() + "' and episode_number = 1 and eventstatus = 'Yes' order by assessment_time asc";
					List<SaRespRds> saRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
					if(!BasicUtils.isEmpty(saRespRdsList)) {
						isRds = true;
					}
					
					if(isRds) {
						for(SaRespRds resp : saRespRdsList) {
							if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0001")) {
								isRdsTTNB = true;
								isRds = false;
							}
							if(!BasicUtils.isEmpty(resp.getCauseofrds()) && resp.getCauseofrds().contains("RDS0003")) {
								isRdsMAS = true;
								isRds = false;
							}
						}
						
						String reasonQuery = "select obj from ReasonAdmission obj where uhid = '" + obj[0].toString() + "'";
						List<ReasonAdmission> reasonList = prescriptionDao.getListFromMappedObjNativeQuery(reasonQuery);
						for(ReasonAdmission reason : reasonList) {
							if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("TTNB")){
								isRdsTTNB = true;
								isRds = false;
							}
							if(!BasicUtils.isEmpty(reason.getCause_value()) && reason.getCause_value().equalsIgnoreCase("MAS")){
								isRdsMAS = true;
								isRds = false;
							}
						}
					}
					
					
					String jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + obj[0].toString() + "' and episode_number = 1 and jaundicestatus = 'Yes' and phototherapyvalue='Start' order by assessment_time asc";
					List<SaJaundice> jaunList = prescriptionDao.getListFromMappedObjNativeQuery(jaundiceQuery);
					if(!BasicUtils.isEmpty(jaunList)) {
						isJaundice = true;
					}
					jaundiceQuery = "select obj from SaJaundice obj where uhid = '" + obj[0].toString() + "' order by assessment_time asc";
					jaunList = prescriptionDao.getListFromMappedObjNativeQuery(jaundiceQuery);
					
					Timestamp startTime = null;
					Timestamp currentTime = null;
	
					for(SaJaundice obj1 : jaunList) {
	
						
						if (startTime == null && !BasicUtils.isEmpty(obj1.getPhototherapyvalue()) && (obj1.getPhototherapyvalue().equalsIgnoreCase("Start") || obj1.getPhototherapyvalue().equalsIgnoreCase("Continue"))) {
							startTime = obj1.getAssessmentTime();
	
							 
						
						}else if(!BasicUtils.isEmpty(obj1.getPhototherapyvalue()) && obj1.getPhototherapyvalue().equalsIgnoreCase("Stop") && startTime != null) {
							currentTime = obj1.getAssessmentTime();
							phototherapyDays += ((currentTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
							
							startTime = null;
							currentTime = null;
						}
						
					}
					
					
					
					
					
					Timestamp currentDate = null;
					long antibioticUsage = 0;
					String sepsisQuery = "select obj from SaSepsis obj where uhid = '" + obj[0].toString() + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
					List<SaSepsis> sepsisLists = prescriptionDao.getListFromMappedObjNativeQuery(sepsisQuery);
					if(!BasicUtils.isEmpty(sepsisLists)) {
						isSepsis = true;
					}
			
					
				
					if(isSepsis) {
	
						String antibioticQuery = "select p.uhid from " + BasicConstants.SCHEMA_NAME + ".vw_antibiotic_duration as p where p.antibiotic_duration >= 5 and p.medicationtype = 'TYPE0001' and p.uhid = '" + obj[0].toString() + "'";
						List<Object[]> antibioticQueryList = inicuDoa.getListFromNativeQuery(antibioticQuery);
						if(!BasicUtils.isEmpty(antibioticQueryList)) {
							isProbableSepsis = true;
	
						}
	
					}
				
					String asphyxiaQuery = "select obj from SaCnsAsphyxia obj where uhid = '" + obj[0].toString() + "' and eventstatus = 'yes' and episode_number = 1 order by assessment_time asc";
					List<SaCnsAsphyxia> asphyxiaLists = prescriptionDao.getListFromMappedObjNativeQuery(asphyxiaQuery);
	
					if(!BasicUtils.isEmpty(asphyxiaLists)) {
						isAsphyxia = true;
					}
					
					String pneumoQuery = "select obj from SaRespPneumo obj where uhid = '" + obj[0].toString() + "' and eventstatus = 'Yes' and episode_number = 1 order by assessment_time asc";
					List<SaRespPneumo> pneumoLists = prescriptionDao.getListFromMappedObjNativeQuery(pneumoQuery);
	
					if(!BasicUtils.isEmpty(pneumoLists)) {
						isPneumothorax = true;
					}
					
					String pphnQuery = "select obj from SaRespPphn obj where uhid = '" + obj[0].toString() + "' and eventstatus = 'Yes' and episode_number = 1  order by assessment_time asc";
					List<SaRespPphn> pphnLists = prescriptionDao.getListFromMappedObjNativeQuery(pphnQuery);
	
					if(!BasicUtils.isEmpty(pphnLists)) {
						isPPHN = true;
					}
					String bpdQuery = "select obj from RespSupport obj where uhid = '" + obj[0].toString() + "' order by creationtime";
					List<RespSupport> bpdList = prescriptionDao.getListFromMappedObjNativeQuery(bpdQuery);
					for(RespSupport resp : bpdList) {
						if(!BasicUtils.isEmpty(resp.getRsVentType()) && (resp.getRsVentType().equalsIgnoreCase("HFO") || resp.getRsVentType().equalsIgnoreCase("Mechanical Ventilation"))) {
							isInvasive = true;
						}
					}
	
		
					
					
					int gestationWeek = (Integer) obj[5];
					int gestationDays = (Integer) obj[6];				
					int columnCountDose = 0;
					int columnCountFrequency = 0;
					int columnCountVolume = 0;
					int columnCountType = 0;
					int columnCountTime = 0;
					
					long columnDoseError = 0;
					boolean isMedGiven = false;
					long columnFrequencyError = 0;
					long columnVolumeError = 0;
					long columnTypeError = 0;
					long columnTimeError = 0;
					
					long columnDoseTotal = 0;
					long columnFrequencyTotal = 0;
					long columnVolumeTotal = 0;
					long columnTypeTotal = 0;
					long columnTimeTotal = 0;
				
					
					String babyType = "";
					if(!BasicUtils.isEmpty(obj[10])) {
						babyType = obj[10].toString();
					}
	
					if(true) {
						
						Timestamp dateofbirth = new Timestamp(new Date().getTime());
						if (!BasicUtils.isEmpty(tob)) {
							dateofbirth = new Timestamp(dob.getTime());
							if (!BasicUtils.isEmpty(tob)) {
								
								if(tob.contains(",")) {
									String[] toaArr = tob.split(",");
									// "10,38,PM"
									if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
										dateofbirth.setHours(Integer.parseInt(toaArr[0]) + 12);
									} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
										dateofbirth.setHours(0);
									} else {
										dateofbirth.setHours(Integer.parseInt(toaArr[0]));
									}
									dateofbirth.setMinutes(Integer.parseInt(toaArr[1]));
								}else if(toa.contains(":")) {
									String[] toaArr = toa.split(":");
									// "10,38,PM"
									if (toa.contains("PM") && !toaArr[0].equalsIgnoreCase("12")) {
										dateofbirth.setHours(Integer.parseInt(toaArr[0]) + 12);
									} else if (toa.contains("AM") && toaArr[0].equalsIgnoreCase("12")) {
										dateofbirth.setHours(0);
									} else {
										dateofbirth.setHours(Integer.parseInt(toaArr[0]));
									}
									dateofbirth.setMinutes(0);
								}	
							}
							dateofbirth = new Timestamp(dateofbirth.getTime() - offset);
						}
	
						double los = Math.ceil(((dod.getTime() - creationTime.getTime()) / (1000 * 60 * 60 * 24))) + 1;
						boolean isLOSNegative = false;
						if(los < 1) {
							isLOSNegative = true;
						}
						
						++rowCount;
						++rowFrequencyCount;
						Row rowMedDose = sheet.createRow(rowCount);
						
						
						cell = rowMedDose.createCell(columnCountDose);
						cell.setCellValue(obj[0].toString());
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(los);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(gestationWeek);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(gestationDays);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(obj[7].toString());
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(obj[2].toString());
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(obj[8].toString());
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isSteroidGiven);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(mod);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(babyType);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(obj[9].toString());
						cell = rowMedDose.createCell(++columnCountDose);
						if (apgarOne == -1)
							cell.setCellValue("");
						else
							cell.setCellValue(apgarOne + "");
						
						cell = rowMedDose.createCell(++columnCountDose);
						if (apgarFive == -1)
							cell.setCellValue("");
						else
							cell.setCellValue(apgarFive + "");
						
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(hypertension);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(gestationalHypertension);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(diabetes);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(gestationalDiabetes);
						
						
					
					
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isChronicDisease);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(hypothyroidism);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(hyperthyroidism);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(noneDisease);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(fever);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(uti);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(history_of_infections);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(noneInfection);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(prom);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(pprom);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(prematurity);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(chorioamniotis);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(oligohydraminos);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(polyhydraminos);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(noneOther);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isRds);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isRdsTTNB);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isRdsMAS);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isJaundice);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isProbableSepsis);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isAsphyxia);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isPneumothorax);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isPPHN);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(isInvasive);
	
						
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(resuscitation);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(resuscitationInitialSteps);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(resuscitationO2);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(resuscitationPPV);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(resuscitationChestCompression);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(umblicalDoppler);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(umblicalDopplerType);
						cell = rowMedDose.createCell(++columnCountDose);
						cell.setCellValue(phototherapyDays);
						
						
						Row rowMedFrequency = sheet2.createRow(rowFrequencyCount);
		
						cell = rowMedFrequency.createCell(columnCountFrequency);
						cell.setCellValue(obj[0].toString());
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(los);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(gestationWeek);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(gestationDays);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(obj[7].toString());
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(obj[2].toString());
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(obj[8].toString());
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isSteroidGiven);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(mod);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(babyType);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(obj[9].toString());
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						if (apgarOne == -1)
							cell.setCellValue("");
						else
							cell.setCellValue(apgarOne + "");
						
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						if (apgarFive == -1)
							cell.setCellValue("");
						else
							cell.setCellValue(apgarFive + "");
						
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(hypertension);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(gestationalHypertension);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(diabetes);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(gestationalDiabetes);
						
					
						
					
					
					
						
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isChronicDisease);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(hypothyroidism);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(hyperthyroidism);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(noneDisease);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(fever);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(uti);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(history_of_infections);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(noneInfection);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(prom);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(pprom);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(prematurity);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(chorioamniotis);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(oligohydraminos);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(polyhydraminos);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(noneOther);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isRds);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isRdsTTNB);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isRdsMAS);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isJaundice);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isProbableSepsis);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isAsphyxia);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isPneumothorax);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isPPHN);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(isInvasive);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(resuscitation);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(resuscitationInitialSteps);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(resuscitationO2);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(resuscitationPPV);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(resuscitationChestCompression);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(umblicalDoppler);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(umblicalDopplerType);
						cell = rowMedFrequency.createCell(++columnCountFrequency);
						cell.setCellValue(phototherapyDays);
						
						int neofaxStatus = -1;
						boolean isDoseCorrectGlobal = true;
						
				        String medQuery = "select obj from BabyPrescription obj where uhid = '" + obj[0].toString() + "' and medicinename IS not null and startdate is not null order by startdate asc";
						List<BabyPrescription> medList = prescriptionDao.getListFromMappedObjNativeQuery(medQuery);
						
	
						for(BabyPrescription medicine : medList) {
							boolean isDoseError = false;
							boolean isFrequencyError = false;
							boolean isVolumeError = false;
							boolean isTypeError = false;
							boolean isTimeError = false;
							
							String medname = "";
							float dose = -1;
							float orderedDose = -1;
							int time = -1;
							int recTime = -1;
							int recVolume = -1;
							int volume = -1;
							String route = "";
							String mode = "";
							String frequency = "";
							String diluentType = "";
							String recDiluentType = "";
							Timestamp startDate = medicine.getStartdate();
	
							int days = (int) ((startDate.getTime() - dateofbirth.getTime()) / (1000 * 60 * 60 * 24));
							
							boolean bolus = false;
							if(!BasicUtils.isEmpty(medicine.getDose())) {
								float currentGestationWeek = gestationWeek;
								float currentGestationDays = gestationDays;
								route = medicine.getRoute();
								medname = medicine.getMedicinename();
								if(!BasicUtils.isEmpty(medicine.getFrequency()))
	
								frequency = medicine.getFrequency();
								dose = medicine.getDose();
								orderedDose = dose;
								mode = medicine.getFreq_type();
								if(!BasicUtils.isEmpty(medicine.getBolus()))
									bolus = medicine.getBolus();
								if(!BasicUtils.isEmpty(medicine.getInf_time()))
									time = medicine.getInf_time();
								
								diluentType = medicine.getDilution_type();
								if(!BasicUtils.isEmpty(diluentType) && diluentType.equalsIgnoreCase("Saline")) {
									diluentType = "NS";
								}
								if(!BasicUtils.isEmpty(diluentType) && diluentType.equalsIgnoreCase("5% Dextrose")) {
									diluentType = "D5W";
								}
								
								if(!BasicUtils.isEmpty(medicine.getInf_volume()))
									volume = Math.round(medicine.getInf_volume());
								
								currentGestationDays += days;
								if(currentGestationDays > 6) {
									currentGestationWeek = currentGestationWeek + (currentGestationDays / 7);
									currentGestationDays = currentGestationDays % 7;
								}
								
								String queryRecommendedMedDose = "select obj from RefMedicine obj where isactive='true' and medname = '"
										+ medname + "' and upper_dol >= " + days + " and lower_dol <= " + days + " and upper_gestation >= "
										+ currentGestationWeek + " and lower_gestation <= " + currentGestationWeek;
								List<RefMedicine> refMedicinesDoseTypeList = prescriptionDao.getListFromMappedObjNativeQuery(queryRecommendedMedDose);
								if (!BasicUtils.isEmpty(refMedicinesDoseTypeList)) {
									
									neofaxStatus = 0;
									float lower_dose = -1;
									float upper_dose = -1;
									int infusion_time = -1;
									int infusion_volume = -1;
									int lowerFreqValue = -1;
									int higherFreqValue = -1;
									String unit = "";
									boolean isDoseCorrect = false;
									boolean isVolumeCorrect = false;
									boolean isFrequencyCorrect = false;
									boolean isTimeCorrect = false;
									boolean isDiluentTypeCorrect = false;
									boolean bolusRec = false;
									float doseUnit = -1;
									
									for(RefMedicine refMedicine : refMedicinesDoseTypeList) {
										dose = orderedDose;
	
										//Diluent Type
										String queryMedSolution = "select obj from RefMedSolutions obj where medname= '" + medname  + "'";
										List<RefMedSolutions> diluentList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedSolution);
										if(!BasicUtils.isEmpty(diluentList)) {
											for(RefMedSolutions medSol : diluentList) {
												if(medSol.getDiluenttype().equalsIgnoreCase(diluentType)) {
													recDiluentType = medSol.getDiluenttype();
													isDiluentTypeCorrect = true;
												}
											}
										}else {
											isDiluentTypeCorrect = true;
										}
										if(route.equalsIgnoreCase("PO")) {
											isDiluentTypeCorrect = true;
										}
										if(!BasicUtils.isEmpty(refMedicine.getRoute())) {
											if(refMedicine.getRoute().equalsIgnoreCase(route)) {
												
											}
											else {
												continue;
											}
										}
										if(!BasicUtils.isEmpty(refMedicine.getMode())) {
											if(refMedicine.getMode().equalsIgnoreCase(mode)) {
												
											}
											else {
												continue;
											}
										}
										if(!BasicUtils.isEmpty(refMedicine.getBolus())) {
											if(refMedicine.getBolus() == (bolus)) {
												
											}
											else {
												continue;
											}
										}
										
										if(!BasicUtils.isEmpty(refMedicine.getBolus()))
											bolusRec = refMedicine.getBolus();
										if(!BasicUtils.isEmpty(refMedicine.getDoseUnit()))
											unit = refMedicine.getDoseUnit();
										if(!BasicUtils.isEmpty(refMedicine.getLowerDose()))
											lower_dose = refMedicine.getLowerDose();
										if(!BasicUtils.isEmpty(refMedicine.getUpperDose()))
											upper_dose = refMedicine.getUpperDose();
										if(!BasicUtils.isEmpty(refMedicine.getInfusionTime()))
											infusion_time = refMedicine.getInfusionTime();
										if(!BasicUtils.isEmpty(refMedicine.getInfusionVolume()))
											infusion_volume = refMedicine.getInfusionVolume();
										
										
										//Handling per day per dose
										if(!BasicUtils.isEmpty(dose) && !BasicUtils.isEmpty(frequency) && !BasicUtils.isEmpty(refMedicine.getPerday()) && !BasicUtils.isEmpty(medicine.getDose_unit_time())) {
											String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + frequency  + "'";
											List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
											if(!BasicUtils.isEmpty(refMedFreqList)) {
												String lowerFreqTypeDummy = refMedFreqList.get(0).getFreqvalue();
												String freqListDummy[] = lowerFreqTypeDummy.split(" hr");
												Integer lowerFreqValueDummy = Integer.parseInt(freqListDummy[0]);
												float value = 24 / lowerFreqValueDummy;
												if(refMedicine.getPerday().equalsIgnoreCase("per dose") && medicine.getDose_unit_time().equalsIgnoreCase("day")) {
													dose = dose / value;
												}else if(refMedicine.getPerday().equalsIgnoreCase("per day") && medicine.getDose_unit_time().equalsIgnoreCase("dose")) {
													dose = dose * value;
												}
											}
										}
										isMedGiven = true;

										if(upper_dose != -1) {
											if(!BasicUtils.isEmpty(refMedicine.getDoseUnit()) && !BasicUtils.isEmpty(medicine.getDose_unit())) {
												if(refMedicine.getDoseUnit().equalsIgnoreCase("mg/kg") && medicine.getDose_unit().equalsIgnoreCase("mg/kg")) {
													float lower_limit = lower_dose - ((10 * lower_dose) / 100);
													float upper_limit = upper_dose + ((10 * upper_dose) / 100);
													if(dose >= lower_limit && dose <= upper_limit) {
														isDoseCorrect = true;
													}
												}
												else if(refMedicine.getDoseUnit().equalsIgnoreCase("μg/kg") && medicine.getDose_unit().equalsIgnoreCase("mg/kg")) {
													dose = dose*1000;
													float lower_limit = lower_dose - ((10 * lower_dose) / 100);
													float upper_limit = upper_dose + ((10 * upper_dose) / 100);
													if(dose >= lower_limit && dose <= upper_limit) {
														isDoseCorrect = true;
													}
												}
												else if(refMedicine.getDoseUnit().equalsIgnoreCase("mg/kg") && medicine.getDose_unit().equalsIgnoreCase("μg/kg")) {
													dose = dose/1000;
													float lower_limit = lower_dose - ((10 * lower_dose) / 100);
													float upper_limit = upper_dose + ((10 * upper_dose) / 100);
													if(dose >= lower_limit && dose <= upper_limit) {
														isDoseCorrect = true;
													}
												}else {
													float lower_limit = lower_dose - ((10 * lower_dose) / 100);
													float upper_limit = upper_dose + ((10 * upper_dose) / 100);
													if(dose >= lower_limit && dose <= upper_limit) {
														isDoseCorrect = true;
													}
												}
											}else {
												float lower_limit = lower_dose - ((10 * lower_dose) / 100);
												float upper_limit = upper_dose + ((10 * upper_dose) / 100);
												if(dose >= lower_limit && dose <= upper_limit) {
													isDoseCorrect = true;
												}
											}
												
										}else {
											float lower_limit = lower_dose - ((10 * lower_dose) / 100);
											float upper_limit = lower_dose + ((10 * lower_dose) / 100);
											if(dose >= lower_limit && dose <= upper_limit) {
												isDoseCorrect = true;
											}
										}
									
										//Time
										if(!BasicUtils.isEmpty(refMedicine.getInfusionTime())) {
											recTime = refMedicine.getInfusionTime();
											if(time == refMedicine.getInfusionTime()) {
												isTimeCorrect = true;
											}
										}else {
											isTimeCorrect = true;
										}
										
										//Volume
										if(!BasicUtils.isEmpty(refMedicine.getInfusionVolume())) {
											recVolume = refMedicine.getInfusionVolume();
											if(volume == refMedicine.getInfusionVolume()) {
												isVolumeCorrect = true;
											}
										}else {
											isVolumeCorrect = true;
										}
										
										//Frequency
										
										if(!BasicUtils.isEmpty(refMedicine.getLowerFrequency())) {
											String lowerFreqType = refMedicine.getLowerFrequency();
											String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + lowerFreqType  + "'";
											List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
											if(!BasicUtils.isEmpty(refMedFreqList)) {
												lowerFreqType = refMedFreqList.get(0).getFreqvalue();
												String freqList[] = lowerFreqType.split(" hr");
												lowerFreqValue = Integer.parseInt(freqList[0]);
											}
											if(!BasicUtils.isEmpty(refMedicine.getUpperFrequency())){
												String higherFreqType = refMedicine.getUpperFrequency();
												queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + higherFreqType  + "'";
												refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
												if(!BasicUtils.isEmpty(refMedFreqList)) {
													higherFreqType = refMedFreqList.get(0).getFreqvalue();
													String freqList[] = higherFreqType.split(" hr");
													higherFreqValue = Integer.parseInt(freqList[0]);
												}
											}
											if(frequency == "") {
												isFrequencyCorrect = true;
											}
											else if(higherFreqValue != -1) {
												int actualFreqValue = -1;
												String higherFreqType = frequency;
												queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + higherFreqType  + "'";
												refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
												if(!BasicUtils.isEmpty(refMedFreqList)) {
													higherFreqType = refMedFreqList.get(0).getFreqvalue();
													String freqList[] = higherFreqType.split(" hr");
													actualFreqValue = Integer.parseInt(freqList[0]);
												}
												
												if(actualFreqValue >= lowerFreqValue && actualFreqValue <= higherFreqValue) {
													isFrequencyCorrect = true;
												}
											}else {
												int actualFreqValue = -1;
												String higherFreqType = frequency;
												queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + higherFreqType  + "'";
												refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
												if(!BasicUtils.isEmpty(refMedFreqList)) {
													higherFreqType = refMedFreqList.get(0).getFreqvalue();
													String freqList[] = higherFreqType.split(" hr");
													actualFreqValue = Integer.parseInt(freqList[0]);
												}
												if(actualFreqValue == lowerFreqValue) {
													isFrequencyCorrect = true;
												}
											}
										}else if(BasicUtils.isEmpty(refMedicine.getLowerFrequency())) {
											isFrequencyCorrect = true;
										}
										
										if(!BasicUtils.isEmpty(medicine.getFreq_type()) && medicine.getFreq_type().equalsIgnoreCase("Continuous")) {
											isFrequencyCorrect = true;
										}
									}
									
									if ((isDoseCorrect == false) && (isDoseCorrectGlobal == true))
					                    isDoseCorrectGlobal = false;
									
									int dol = (int) ((startDate.getTime() - dateofadmission.getTime()) / (1000 * 60 * 60 * 24));
	
									HashMap<String, String> mappingGenericName = new HashMap<String, String>();
									  mappingGenericName.put("Amikacin", "Amikacin");
					                  mappingGenericName.put("Ampho-b (Liposomal)", "Amphotericin B Liposome");
					                  mappingGenericName.put("Amphotericin-B (conventional)", "Amphotericin B");
					                  mappingGenericName.put("Azithromycin", "Azithromycin");
					                  mappingGenericName.put("Caffeine", "Caffeine citrate");
					                  mappingGenericName.put("Caffeine (loading)", "Caffeine citrate");
					                  mappingGenericName.put("CAPNEA", "Caffeine citrate");
					                  mappingGenericName.put("Cefotaxim (Biotax)", "Cefotaxime");
					                  mappingGenericName.put("Ceftriaxone", "CefTRIAXone");
					                  mappingGenericName.put("Ciprofloxacin", "Ciprofloxacin");
					                  mappingGenericName.put("Colisitin", "Colistin");
					                  mappingGenericName.put("Dobutamine", "DOBUTamine");
					                  mappingGenericName.put("Domperidone", "Domperidone");
					                  mappingGenericName.put("Dopamine", "DOPamine");
					                  mappingGenericName.put("Epinephrine", "EPINEPHrine (Adrenaline)");
					                  mappingGenericName.put("Fluconazole", "Fluconazole");
					                  mappingGenericName.put("Hydrocortisone", "Hydrocortisone");
					                  mappingGenericName.put("Ibuprofen (oral)", "Ibuprofen Lysine");
					                  mappingGenericName.put("Levtiracetram(Loading dose)", "LevETIRAcetam");
					                  mappingGenericName.put("Linezolid", "Linezolid");
					                  mappingGenericName.put("Meropenem", "Meropenem");
					                  mappingGenericName.put("Morphine", "Morphine");
					                  mappingGenericName.put("Octreotide", "Octreotide");
					                  mappingGenericName.put("Paracetamol (IV)", "Acetaminophen");
					                  mappingGenericName.put("Paracetamol (oral)", "Acetaminophen");
					                  mappingGenericName.put("Phenobarbitone(loading dose)", "PHENobarbital");
					                  mappingGenericName.put("Phenytoin(loading dose)", "Phenytoin");
					                  mappingGenericName.put("Piperacilin Tazobactam", "Piperacillin Tazobactam");
					                  mappingGenericName.put("Ranitidine", "Ranitidine");
					                  mappingGenericName.put("Sildenafil", "Sildenafil");
					                  mappingGenericName.put("Zosyn (Piperacilin Tazobactam)", "Piperacillin Tazobactam");
					                  mappingGenericName.put("Vancomycin", "Vancomycin");
					                  mappingGenericName.put("Vitamin K", "Vitamin K1");
									
									String genericMedicineName = mappingGenericName.get(medicine.getMedicinename());
									if(BasicUtils.isEmpty(genericMedicineName)) {
										genericMedicineName = medicine.getMedicinename() + " Missing Medicine";
									}
									if(true) {
										cell = rowMedDose.createCell(++columnCountDose);
										cell.setCellValue(genericMedicineName);
										cell = rowMedDose.createCell(++columnCountDose);
										if(dose == -1) {
											cell.setCellValue("");
										}else {
											cell.setCellValue(dose);
										}
										if(lower_dose != -1) {
											if(upper_dose != -1) {
												cell = rowMedDose.createCell(++columnCountDose);
												cell.setCellValue(lower_dose + "-" + upper_dose);
												
												if(dose >= lower_dose && dose <= upper_dose) {
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue(dose);
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue(0);
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue("No Error");
												}else {
													if(dose < lower_dose) {
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(lower_dose + "");
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(dose - lower_dose + "");
													}
													else {
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(upper_dose + "");
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(dose - upper_dose + "");
													}
													
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue("Deviation");
													isDoseError = true;
												}
											}else {
												cell = rowMedDose.createCell(++columnCountDose);
												cell.setCellValue(lower_dose);
												
												float lower_limit = lower_dose - ((10 * lower_dose) / 100);
												float upper_limit = lower_dose + ((10 * lower_dose) / 100);
												if(dose >= lower_limit && dose <= upper_limit) {
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue(dose);
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue(0);
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue("No Error");
												}else {
													if(dose < lower_limit) {
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(lower_dose + "");
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(dose - lower_dose + "");
													}
													else {
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(lower_dose + "");
														cell = rowMedDose.createCell(++columnCountDose);
														cell.setCellValue(dose - lower_dose + "");
													}
													cell = rowMedDose.createCell(++columnCountDose);
													cell.setCellValue("Deviation");
													isDoseError = true;
												}
			
											}
										}else {
											cell = rowMedDose.createCell(++columnCountDose);
											cell.setCellValue("");
											cell = rowMedDose.createCell(++columnCountDose);
											cell.setCellValue("");
											cell = rowMedDose.createCell(++columnCountDose);
											cell.setCellValue("");
											cell = rowMedDose.createCell(++columnCountDose);
											cell.setCellValue("No Error");
										}
										cell = rowMedDose.createCell(++columnCountDose);
										cell.setCellValue(dol);
										long errorDay = 1;
										if(!BasicUtils.isEmpty(medicine.getStartdate()) && !BasicUtils.isEmpty(medicine.getEnddate()))
											errorDay = ((medicine.getEnddate().getTime() - medicine.getStartdate().getTime()) / (1000 * 60 * 60 * 24)) + 1;
										
										if(errorDay < 0) {
											errorDay = 1;
										}
										
										cell = rowMedDose.createCell(++columnCountDose);
										cell.setCellValue(errorDay);
										
										cell = rowMedDose.createCell(++columnCountDose);
										if(!BasicUtils.isEmpty(medicine.getDose_unit()))
											cell.setCellValue(medicine.getDose_unit());
										else
											cell.setCellValue("");
		
										if(!BasicUtils.isEmpty(frequency)) {
										
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											cell.setCellValue(genericMedicineName);
											
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											int frequencyGiven = 0; 
											int frequencyRecommended = 0; 
	
											String higherFreqType = frequency;
											String queryMedfrequency = "select obj from RefMedfrequency obj where freqid= '" + higherFreqType  + "'";
											List<RefMedfrequency> refMedFreqList = prescriptionDao.getListFromMappedObjNativeQuery(queryMedfrequency);
											if(!BasicUtils.isEmpty(refMedFreqList)) {
												higherFreqType = refMedFreqList.get(0).getFreqvalue();
												String freqList[] = higherFreqType.split(" hr");
												int actualFreqValue = Integer.parseInt(freqList[0]);
												cell.setCellValue(actualFreqValue + "");
												frequencyGiven = actualFreqValue;
											}else {
												cell.setCellValue("");	
											}
			
											
											
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											if(lowerFreqValue != -1 && higherFreqValue != -1) {
												cell.setCellValue(lowerFreqValue + " - " + higherFreqValue);
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue(lowerFreqValue + " - " + higherFreqValue);
												frequencyRecommended = lowerFreqValue;
											}
	
											else if(lowerFreqValue != -1) {
												cell.setCellValue(lowerFreqValue);
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue(lowerFreqValue);
												frequencyRecommended = lowerFreqValue;
											}
											else {
												cell.setCellValue("");
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue("");
											}
											
											
			
											if(isFrequencyCorrect) {
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue(0);
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue("No Error");
											}
											else {
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue(frequencyGiven - frequencyRecommended);
												cell = rowMedFrequency.createCell(++columnCountFrequency);
												cell.setCellValue("Deviation");
											    isFrequencyError = true;
											}
			
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											cell.setCellValue(dol);
											
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											cell.setCellValue(errorDay);
											cell = rowMedFrequency.createCell(++columnCountFrequency);
											cell.setCellValue("hr");
										}
											
										
										
									
										
										columnDoseTotal += errorDay;
										columnFrequencyTotal += errorDay;
										columnVolumeTotal += errorDay;
										columnTypeTotal += errorDay;
										columnTimeTotal += errorDay;
						
										if(isDoseError) {
											columnDoseError += errorDay;
										}
										if(isFrequencyError) {
											columnFrequencyError += errorDay;
										}
										if(isVolumeError) {
											columnVolumeError += errorDay;
										}
										if(isTypeError) {
											columnTypeError += errorDay;
										}
										if(isTimeError) {
											columnTimeError += errorDay;
										}
									}
										
	
								}else {
								}
							}else {
	
							}
								
						}
						
						
						boolean isSurfactantGiven = false;
						rdsQuery = "select obj from SaRespRds obj where uhid = '" + obj[0].toString() + "' and episode_number = 1 and eventstatus = 'Yes' order by assessment_time asc";
						saRespRdsList = prescriptionDao.getListFromMappedObjNativeQuery(rdsQuery);
						
						for(SaRespRds respObj : saRespRdsList) {
							if(!BasicUtils.isEmpty(respObj.getSurfactantDose())){
								
								isSurfactantGiven = true;
						
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue("Surfactant");
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue(respObj.getSurfactantDose());
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue("4");
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue("4");
								if(Float.parseFloat(respObj.getSurfactantDose()) > 4.0) {
									cell = rowMedDose.createCell(++columnCountDose);
									cell.setCellValue(Float.parseFloat(respObj.getSurfactantDose()) - 4.0);
									cell = rowMedDose.createCell(++columnCountDose);
									cell.setCellValue("Deviation");
								}
								else {
									cell = rowMedDose.createCell(++columnCountDose);
									cell.setCellValue(0);
									cell = rowMedDose.createCell(++columnCountDose);
									cell.setCellValue("No Error");
								}
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue(1);
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue(1);
								cell = rowMedDose.createCell(++columnCountDose);
								cell.setCellValue("ml/kg");
								
								
								
								
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue("Surfactant");
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue(1);
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue("1");
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue("1");
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue(0);
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue("No Error");
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue(1);
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue(1);
								cell = rowMedFrequency.createCell(++columnCountFrequency);
								cell.setCellValue("hr");
							}
						}
						
						if(isDoseCorrectGlobal == false) 
				            neofaxStatus = 1;

			            
			            if(neofaxStatus == -1) {
			                if(isSurfactantGiven == true)
			                	neofaxStatus = 0;
			            }

			            if(isLOSNegative == false) {
				            
				            String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set medication_deviation = " + neofaxStatus + "  where uhid = '" + obj[0].toString()  +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
					   	    
					   	    
					   	    
					   	    String babyQueryLimit = "select obj from BabyDetail obj where uhid= '" + obj[0].toString()  + "'";
							List<BabyDetail> babyListLimit = prescriptionDao.getListFromMappedObjNativeQuery(babyQueryLimit);
							if(!BasicUtils.isEmpty(babyListLimit)) {
								if(babyListLimit.get(0).getAdmissionstatus() == true) {
									--rowCount;
									--rowFrequencyCount;
								}
							}
			            }else {
			            	--rowCount;
							--rowFrequencyCount;
			            }
	
	
						if(isMedGiven) {
							cell = rowMedDose.createCell(2300);
							cell.setCellValue(columnDoseError);
						}
						
						
						
						
					}
					
			
				}
				
				
				
				
			}
			
			try (FileOutputStream outputStream = new FileOutputStream("Site1_Neofax_Training.xlsx")) {
				finalWorkbook.write(outputStream);
				System.out.println("file written");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
	
			try {
				workbook.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}