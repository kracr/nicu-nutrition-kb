package com.inicu.postgres.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import com.inicu.models.CaclulatorDeficitPOJO;
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
import com.inicu.postgres.entities.EnFeedDetail;
import com.inicu.postgres.entities.NursingIntakeOutput;
import com.inicu.postgres.entities.OralfeedDetail;
import com.inicu.postgres.entities.ReasonAdmission;
import com.inicu.postgres.entities.RefMedSolutions;
import com.inicu.postgres.entities.RefMedfrequency;
import com.inicu.postgres.entities.RefMedicine;
import com.inicu.postgres.entities.RefNutritioncalculator;
import com.inicu.postgres.entities.RespSupport;
import com.inicu.postgres.entities.SaCnsAsphyxia;
import com.inicu.postgres.entities.SaJaundice;
import com.inicu.postgres.entities.SaRespPneumo;
import com.inicu.postgres.entities.SaRespPphn;
import com.inicu.postgres.entities.SaRespRds;
import com.inicu.postgres.entities.SaSepsis;
import com.inicu.postgres.entities.ScreenRop;
import com.inicu.postgres.service.NotesService;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Repository
public class EsphaganErrors extends Thread{

	@Autowired
	PrescriptionDao prescriptionDao;

	@Autowired
	NotesService notesService;

	@Autowired
	PatientDao patientDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	NotesDAO notesDoa;

	@Autowired
	InicuDao inicuDoa;
	
	@Autowired
	SettingDao settingDao;
	
	public HashMap<String, Float> computeError(Timestamp creationTime,CaclulatorDeficitPOJO cuurentDeficitLast, BabyfeedDetail feed, String uhid, Timestamp dateofadmission,int gestationWeek,int gestationDays, Cell cell, Row rowMedEnergy, Row rowMedProtein, Row rowMedVitamina, Row rowMedVitamind, Row rowMedPhosphorus, Row rowMedIron, Row rowMedCalcium, int lengthStay, Timestamp fromDateOffsetTwentFourHour, Timestamp toDateOffsetTwentFourHour, List<Integer> missedLos) {
		float intakeEnergy = -1;
		float intakeProtein = -1;
		float intakeVitamina = -1;
		float intakeVitamind = -1;
		float intakeCalcium = -1;
		float intakePhosphorus = -1;
		float intakeIron = -1;

		float recommendedEnergy = -1;
		float recommendedProtein = -1;
		float recommendedVitamina = -1;
		float recommendedVitamind = -1;
		float recommendedCalcium = -1;
		float recommendedPhosphorus = -1;
		float recommendedIron = -1;

		float diffEnergy = -1;
		float diffProtein = -1;
		float diffVitamina = -1;
		float diffVitamind = -1;
		float diffCalcium = -1;
		float diffPhosphorus = -1;
		float diffIron = -1;

		String resultEnergy = "";
		String resultProtein = "";
		String resultVitamina = "";
		String resultVitamind = "";
		String resultCalcium = "";
		String resultPhosphorus = "";
		String resultIron = "";

		boolean isEnergyCorrect = true;
		boolean isProteinCorrect = true;
		boolean isVitaminaCorrect = true;
		boolean isVitamindCorrect = true;
		boolean isCalciumCorrect = true;
		boolean isPhosphorusCorrect = true;
		boolean isIronCorrect = true;

		Timestamp startDate = creationTime;

		Date df = new Date(startDate.getTime());
		float weight = -1;

		String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid + "' and visitdate='"
				+ df + "'";
		List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
		if(!BasicUtils.isEmpty(currentBabyVisitList)) {
			if(!BasicUtils.isEmpty(currentBabyVisitList.get(0).getCurrentdateweight()))
				weight = currentBabyVisitList.get(0).getCurrentdateweight();
		}


		int days = lengthStay - 1;

	   
		float currentGestationWeek = gestationWeek;
		float currentGestationDays = gestationDays;
		currentGestationDays += days;
		if(currentGestationDays > 6) {
			currentGestationWeek = currentGestationWeek + (currentGestationDays / 7);
			currentGestationDays = currentGestationDays % 7;
		}
		Float isRecommendationEnteral = 1f;
		String feedTypeEnergy = "";
		String feedTypeProtein = "";
		
		float duration = (toDateOffsetTwentFourHour.getTime() - fromDateOffsetTwentFourHour.getTime()) / (1000 * 60 * 60);
 		
 		float stay = duration / 24;

 		if(stay >= 1) {
 			stay = 1;
 		}
		//Energy
		if( cuurentDeficitLast.getEnteralIntake().get("Energy") != null && cuurentDeficitLast.getParentalIntake().get("Energy") != null && cuurentDeficitLast.getEnteralIntake().get("Energy") > 0 && cuurentDeficitLast.getParentalIntake().get("Energy") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffEnergy = 0;
				intakeEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
				recommendedEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
	
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
	 		}else {
				diffEnergy = ((cuurentDeficitLast.getEnteralIntake().get("Energy") + cuurentDeficitLast.getParentalIntake().get("Energy")) / feed.getWorkingWeight())
						- cuurentDeficitLast.getEshphaganIntake().get("Energy");
				intakeEnergy = (cuurentDeficitLast.getEnteralIntake().get("Energy") + cuurentDeficitLast.getParentalIntake().get("Energy")) / feed.getWorkingWeight();
				recommendedEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
				stay = 1;

				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
	 		}
			feedTypeEnergy = "EN+PN";

		}else if(cuurentDeficitLast.getEnteralIntake().get("Energy") != null && cuurentDeficitLast.getEnteralIntake().get("Energy") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffEnergy = 0;
				intakeEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
				recommendedEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
	
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
	 		}else {
				diffEnergy = (cuurentDeficitLast.getEnteralIntake().get("Energy") / feed.getWorkingWeight())
						- cuurentDeficitLast.getEshphaganIntake().get("Energy");
	
				intakeEnergy = cuurentDeficitLast.getEnteralIntake().get("Energy") / feed.getWorkingWeight();
				recommendedEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
	
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
	 		}
			feedTypeEnergy = "EN Only";
		}else if(cuurentDeficitLast.getParentalIntake().get("Energy") != null && cuurentDeficitLast.getParentalIntake().get("Energy") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffEnergy = 0;
				intakeEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
				recommendedEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
				stay = 1;
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}else {
				diffEnergy = (cuurentDeficitLast.getParentalIntake().get("Energy") / feed.getWorkingWeight())
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
				intakeEnergy = cuurentDeficitLast.getParentalIntake().get("Energy") / feed.getWorkingWeight();
				recommendedEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
				stay = 1;
				isRecommendationEnteral = 0f;
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
	 		}
			feedTypeEnergy = "PN Only";
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffEnergy = 0;
				intakeEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
				recommendedEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
	
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
				isRecommendationEnteral = 0f;
				feedTypeEnergy = "EN Only";
	 		}
		}

		//Protein
		if( cuurentDeficitLast.getEnteralIntake().get("Protein") != null && cuurentDeficitLast.getParentalIntake().get("Protein") != null && cuurentDeficitLast.getEnteralIntake().get("Protein") > 0 && cuurentDeficitLast.getParentalIntake().get("Protein") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffProtein = 0;
				intakeProtein = cuurentDeficitLast.getEnteralIntake().get("Protein");
				recommendedProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}

	 		}else {
	 			diffProtein = ((cuurentDeficitLast.getEnteralIntake().get("Protein") + cuurentDeficitLast.getParentalIntake().get("Protein")) / feed.getWorkingWeight())
					- cuurentDeficitLast.getEshphaganIntake().get("Protein");

				intakeProtein = (cuurentDeficitLast.getEnteralIntake().get("Protein") + cuurentDeficitLast.getParentalIntake().get("Protein")) / feed.getWorkingWeight();
				recommendedProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}
	 		}
			feedTypeProtein = "EN+PN";
		}else if(cuurentDeficitLast.getEnteralIntake().get("Protein") != null && cuurentDeficitLast.getEnteralIntake().get("Protein") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffProtein = 0;
				intakeProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
				recommendedProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}

	 		}else {
				diffProtein = (cuurentDeficitLast.getEnteralIntake().get("Protein") / feed.getWorkingWeight())
						- cuurentDeficitLast.getEshphaganIntake().get("Protein");
				intakeProtein = cuurentDeficitLast.getEnteralIntake().get("Protein") / feed.getWorkingWeight();
				recommendedProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}
	 		}
			feedTypeProtein = "EN Only";
		}else if(cuurentDeficitLast.getParentalIntake().get("Protein") != null && cuurentDeficitLast.getParentalIntake().get("Protein") > 0) {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffProtein = 0;
				intakeProtein = cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
				recommendedProtein = cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}

	 		}else {
				diffProtein = (cuurentDeficitLast.getParentalIntake().get("Protein") / feed.getWorkingWeight())
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
	
				intakeProtein = cuurentDeficitLast.getParentalIntake().get("Protein") / feed.getWorkingWeight();
				recommendedProtein = cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}
	 		}
			feedTypeProtein = "PN Only";
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffProtein = 0;
				intakeProtein = cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
				recommendedProtein = cuurentDeficitLast.getEshphaganParenteralIntake().get("Protein");
	
				if(diffProtein < 0) {
					isProteinCorrect = false;
				}
				feedTypeProtein = "EN Only";
	 		}
		}

	

		boolean isParenteralVitaminD = false;
		//Vitamina
		if( cuurentDeficitLast.getEnteralIntake().get("Vitamina") != null && cuurentDeficitLast.getParentalIntake().get("Vitamina") != null && cuurentDeficitLast.getEnteralIntake().get("Vitamina") > 0 && cuurentDeficitLast.getParentalIntake().get("Vitamina") > 0) {
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamina = 0;
				intakeVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
	
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
	 		}else {
	 			diffVitamina = ((cuurentDeficitLast.getEnteralIntake().get("Vitamina") + cuurentDeficitLast.getParentalIntake().get("Vitamina")))
						- cuurentDeficitLast.getEshphaganIntake().get("Vitamina");

				intakeVitamina = ((cuurentDeficitLast.getEnteralIntake().get("Vitamina") + cuurentDeficitLast.getParentalIntake().get("Vitamina")));
				recommendedVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
	 		}
			
			


		}else if(cuurentDeficitLast.getEnteralIntake().get("Vitamina") != null && cuurentDeficitLast.getEnteralIntake().get("Vitamina") > 0) {
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamina = 0;
				intakeVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
	
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
	 		}else {
	 			diffVitamina = (cuurentDeficitLast.getEnteralIntake().get("Vitamina"))
						- cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
				intakeVitamina = cuurentDeficitLast.getEnteralIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
	 		}
			
			
		}else if(cuurentDeficitLast.getParentalIntake().get("Vitamina") != null && cuurentDeficitLast.getParentalIntake().get("Vitamina") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamina = 0;
				intakeVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
	
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}

	 		}else {
	 			diffVitamina = (cuurentDeficitLast.getParentalIntake().get("Vitamina"))
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
				intakeVitamina = cuurentDeficitLast.getParentalIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
	 		}
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamina = 0;
				intakeVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
				recommendedVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
	
				if(diffVitamina < 0) {
					isVitaminaCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}
		}


		//Vitamind
		if( cuurentDeficitLast.getEnteralIntake().get("Vitamind") != null && cuurentDeficitLast.getParentalIntake().get("Vitamind") != null && cuurentDeficitLast.getEnteralIntake().get("Vitamind") > 0 && cuurentDeficitLast.getParentalIntake().get("Vitamind") > 0) {
			

			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamind = 0;
				intakeVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
	
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
	 		}else {
	 			diffVitamind = ((cuurentDeficitLast.getEnteralIntake().get("Vitamind") + cuurentDeficitLast.getParentalIntake().get("Vitamind")))
						- cuurentDeficitLast.getEshphaganIntake().get("Vitamind");

				intakeVitamind = ((cuurentDeficitLast.getEnteralIntake().get("Vitamind") + cuurentDeficitLast.getParentalIntake().get("Vitamind")));
				recommendedVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
	 		}

		}else if(cuurentDeficitLast.getEnteralIntake().get("Vitamind") != null && cuurentDeficitLast.getEnteralIntake().get("Vitamind") > 0) {
		
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamind = 0;
				intakeVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
	
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
	 		}else {
	 			diffVitamind = (cuurentDeficitLast.getEnteralIntake().get("Vitamind"))
						- cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
				intakeVitamind = cuurentDeficitLast.getEnteralIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
	 		}
			
		}else if(cuurentDeficitLast.getParentalIntake().get("Vitamind") != null && cuurentDeficitLast.getParentalIntake().get("Vitamind") > 0) {
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamind = 0;
				intakeVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
	
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}

	 		}else {
	 			diffVitamind = (cuurentDeficitLast.getParentalIntake().get("Vitamind"))
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
				intakeVitamind = cuurentDeficitLast.getParentalIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
	 		}
			isParenteralVitaminD = true;

		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffVitamind = 0;
				intakeVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
				recommendedVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
	
				if(diffVitamind < 0) {
					isVitamindCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}
		}

		//Calcium
		if( cuurentDeficitLast.getEnteralIntake().get("Calcium") != null && cuurentDeficitLast.getParentalIntake().get("Calcium") != null && cuurentDeficitLast.getEnteralIntake().get("Calcium") > 0 && cuurentDeficitLast.getParentalIntake().get("Calcium") > 0) {
		
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffCalcium = 0;
				intakeCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
	
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
	 		}else {
	 			diffCalcium = ((cuurentDeficitLast.getEnteralIntake().get("Calcium") + cuurentDeficitLast.getParentalIntake().get("Calcium")))
						- cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				intakeCalcium = ((cuurentDeficitLast.getEnteralIntake().get("Calcium") + cuurentDeficitLast.getParentalIntake().get("Calcium")));
				recommendedCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
	 		}


		}else if(cuurentDeficitLast.getEnteralIntake().get("Calcium") != null && cuurentDeficitLast.getEnteralIntake().get("Calcium") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffCalcium = 0;
				intakeCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
	
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
	 		}else {
	 			diffCalcium = (cuurentDeficitLast.getEnteralIntake().get("Calcium"))
						- cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				intakeCalcium = cuurentDeficitLast.getEnteralIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
	 		}
		}else if(cuurentDeficitLast.getParentalIntake().get("Calcium") != null && cuurentDeficitLast.getParentalIntake().get("Calcium") > 0) {
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffCalcium = 0;
				intakeCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
	
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}

	 		}else {
	 			diffCalcium = (cuurentDeficitLast.getParentalIntake().get("Calcium"))
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
				intakeCalcium = cuurentDeficitLast.getParentalIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
	 		}
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffCalcium = 0;
				intakeCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
				recommendedCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
	
				if(diffCalcium < 0) {
					isCalciumCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}
		}


		//Phosphorus
		if( cuurentDeficitLast.getEnteralIntake().get("Phosphorus") != null && cuurentDeficitLast.getParentalIntake().get("Phosphorus") != null && cuurentDeficitLast.getEnteralIntake().get("Phosphorus") > 0 && cuurentDeficitLast.getParentalIntake().get("Phosphorus") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffPhosphorus = 0;
				intakePhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
	
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
	 		}else {
	 			diffPhosphorus = ((cuurentDeficitLast.getEnteralIntake().get("Phosphorus") + cuurentDeficitLast.getParentalIntake().get("Phosphorus")))
						- cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				intakePhosphorus = ((cuurentDeficitLast.getEnteralIntake().get("Phosphorus") + cuurentDeficitLast.getParentalIntake().get("Phosphorus")));
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
	 		}

		}else if(cuurentDeficitLast.getEnteralIntake().get("Phosphorus") != null && cuurentDeficitLast.getEnteralIntake().get("Phosphorus") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffPhosphorus = 0;
				intakePhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
	
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
	 		}else {
	 			diffPhosphorus = (cuurentDeficitLast.getEnteralIntake().get("Phosphorus"))
						- cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				intakePhosphorus = cuurentDeficitLast.getEnteralIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
	 		}
		}else if(cuurentDeficitLast.getParentalIntake().get("Phosphorus") != null && cuurentDeficitLast.getParentalIntake().get("Phosphorus") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffPhosphorus = 0;
				intakePhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
	
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}

	 		}else {
	 			diffPhosphorus = (cuurentDeficitLast.getParentalIntake().get("Phosphorus"))
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
				intakePhosphorus = cuurentDeficitLast.getParentalIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
	 		}
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffPhosphorus = 0;
				intakePhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
				recommendedPhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
	
				if(diffPhosphorus < 0) {
					isPhosphorusCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}
		}

		//Iron
		if( cuurentDeficitLast.getEnteralIntake().get("Iron") != null && cuurentDeficitLast.getParentalIntake().get("Iron") != null && cuurentDeficitLast.getEnteralIntake().get("Iron") > 0 && cuurentDeficitLast.getParentalIntake().get("Iron") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffIron = 0;
				intakeIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
	
				if(diffIron < 0) {
					isIronCorrect = false;
				}
	 		}else {
	 			diffIron = ((cuurentDeficitLast.getEnteralIntake().get("Iron") + cuurentDeficitLast.getParentalIntake().get("Iron")))
						- cuurentDeficitLast.getEshphaganIntake().get("Iron");
				intakeIron = ((cuurentDeficitLast.getEnteralIntake().get("Iron") + cuurentDeficitLast.getParentalIntake().get("Iron")));
				recommendedIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
				if(diffIron < 0) {
					isIronCorrect = false;
				}
	 		}

		}else if(cuurentDeficitLast.getEnteralIntake().get("Iron") != null && cuurentDeficitLast.getEnteralIntake().get("Iron") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffIron = 0;
				intakeIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
	
				if(diffIron < 0) {
					isIronCorrect = false;
				}
	 		}else {
	 			diffIron = (cuurentDeficitLast.getEnteralIntake().get("Iron"))
						- cuurentDeficitLast.getEshphaganIntake().get("Iron");
				intakeIron = cuurentDeficitLast.getEnteralIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
				if(diffIron < 0) {
					isIronCorrect = false;
				}
	 		}
		}else if(cuurentDeficitLast.getParentalIntake().get("Iron") != null && cuurentDeficitLast.getParentalIntake().get("Iron") > 0) {
			
			
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffIron = 0;
				intakeIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
	
				if(diffIron < 0) {
					isIronCorrect = false;
				}

	 		}else {
	 			diffIron = (cuurentDeficitLast.getParentalIntake().get("Iron"))
						- cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
				intakeIron = cuurentDeficitLast.getParentalIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
				if(diffIron < 0) {
					isIronCorrect = false;
				}
	 		}
		}else {
			if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
				diffIron = 0;
				intakeIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
				recommendedIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
	
				if(diffIron < 0) {
					isIronCorrect = false;
				}
				isRecommendationEnteral = 0f;

	 		}
		}
 		
 		
		
		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);
 		
 		HashMap<String, Float> diffHashMap = new HashMap<String, Float>();
 		float previousStay = stay;
		if(intakeEnergy != -1) {
			if(days==0 && stay < 1) {
				if((intakeEnergy < (90*stay)) || (intakeEnergy > (120*stay)) && (diffEnergy != 0)) {
					if(((intakeEnergy - (90*stay) > 0) || (intakeEnergy - (120*stay) > 0))) {
						stay = 1;	
						recommendedEnergy = 105;
					}
				}
	 		}
			cell = rowMedEnergy.createCell(7*days + 49);
			cell.setCellValue(intakeEnergy);
			cell = rowMedEnergy.createCell(7*days + 50);
			cell.setCellValue(recommendedEnergy);
//			

			if((intakeEnergy < (90*stay)) || (intakeEnergy > (120*stay)) && (diffEnergy != 0)) {
				if(intakeEnergy < 90*stay) {
					cell = rowMedEnergy.createCell(7*days + 51);
					cell.setCellValue((90*stay));
					cell = rowMedEnergy.createCell(7*days + 52);
					cell.setCellValue(intakeEnergy - (90*stay));
				}else {
					cell = rowMedEnergy.createCell(7*days + 51);
					cell.setCellValue((120*stay));
					cell = rowMedEnergy.createCell(7*days + 52);
					cell.setCellValue(intakeEnergy - (120*stay));
				}
				cell = rowMedEnergy.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedEnergy.createCell(7*days + 51);
				cell.setCellValue(intakeEnergy);
				cell = rowMedEnergy.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedEnergy.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			float percentageEnergy = (intakeEnergy / recommendedEnergy) * 100;
			diffHashMap.put("Energy", percentageEnergy);
			cell = rowMedEnergy.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);
		}
		

		if(weight != -1) {
			cell = rowMedEnergy.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		

		

		if(intakeProtein != -1) {
			if(days==0) {
				
				if(babyDetailList.get(0).getBirthweight() < 2500) {
					if((intakeProtein < (3*stay)) || (intakeProtein > (4*stay)) && (diffProtein != 0)) {
						if(((intakeProtein - (3*stay) > 0) || (intakeProtein - (4*stay) > 0))) {
							stay = 1;	
							recommendedProtein = (float)3.5;
						}
					}
				}else {
					if((intakeProtein < (2*stay)) || (intakeProtein > (2*stay)) && (diffProtein != 0)) {
						if(((intakeProtein - (2*stay) > 0) || (intakeProtein - (3*stay) > 0))) {
							stay = 1;	
							recommendedProtein = (float)2.5;
						}
					}
				}
				
	 		}

			cell = rowMedProtein.createCell(7*days + 49);
			cell.setCellValue(intakeProtein);
			cell = rowMedProtein.createCell(7*days + 50);
			cell.setCellValue(recommendedProtein);
			
	
			
			if(babyDetailList.get(0).getBirthweight() < 2500) {
				if((intakeProtein < (3*stay)) || (intakeProtein > (4*stay)) && (diffProtein != 0)) {
					if(intakeProtein < 3*stay) {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((3*stay));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (3*stay));
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((4*stay));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (4*stay));
					}
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedProtein.createCell(7*days + 51);
					cell.setCellValue(intakeProtein);
					cell = rowMedProtein.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}else {
				if((intakeProtein < (2*stay)) || (intakeProtein > (3*stay)) && (diffProtein != 0)) {
					if(intakeProtein < 2*stay) {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((2*stay));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (2*stay));
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((3*stay));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (3*stay));
					}
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedProtein.createCell(7*days + 51);
					cell.setCellValue(intakeProtein);
					cell = rowMedProtein.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
			float percentageProtein = (intakeProtein / recommendedProtein) * 100;
			diffHashMap.put("Protein", percentageProtein);
			
			cell = rowMedProtein.createCell(7*days + 54);
			cell.setCellValue(feedTypeProtein);
		
		}


		if(weight != -1) {
			cell = rowMedProtein.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
	


		if(intakeVitamina != -1) {
			if(days==0 && stay == 1) {
				recommendedVitamina = recommendedVitamina / previousStay;

	 		}
			cell = rowMedVitamina.createCell(7*days + 49);
			cell.setCellValue(intakeVitamina);
			cell = rowMedVitamina.createCell(7*days + 50);
			cell.setCellValue(recommendedVitamina);
			
			
			
			
			float lower_limit = recommendedVitamina - ((10 * recommendedVitamina) / 100);
			float upper_limit = recommendedVitamina + ((10 * recommendedVitamina) / 100);
			if((intakeVitamina < (lower_limit*stay)) || (intakeVitamina > (upper_limit*stay)) && (diffVitamina != 0)) {
				if(intakeVitamina < lower_limit*stay) {
					cell = rowMedVitamina.createCell(7*days + 51);
					cell.setCellValue((recommendedVitamina*stay));
					cell = rowMedVitamina.createCell(7*days + 52);
					cell.setCellValue(intakeVitamina - (recommendedVitamina*stay));
				}else {
					cell = rowMedVitamina.createCell(7*days + 51);
					cell.setCellValue((recommendedVitamina*stay));
					cell = rowMedVitamina.createCell(7*days + 52);
					cell.setCellValue(intakeVitamina - (recommendedVitamina*stay));
				}
				cell = rowMedVitamina.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedVitamina.createCell(7*days + 51);
				cell.setCellValue(intakeVitamina);
				cell = rowMedVitamina.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedVitamina.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
		
			
			
			float percentageVitamina = (intakeVitamina / recommendedVitamina) * 100;

			diffHashMap.put("Vitamina", percentageVitamina);
			cell = rowMedVitamina.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);


		}
		

		if(weight != -1) {
			cell = rowMedVitamina.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		
		if(intakeVitamind != -1) {
			
			if(days==0 && stay == 1) {
				
				if(isParenteralVitaminD) {
					recommendedVitamind = recommendedVitamind / previousStay;

				}else {
					recommendedVitamind = (float)350;

				}
				
	 		}

			cell = rowMedVitamind.createCell(7*days + 49);
			cell.setCellValue(intakeVitamind);
			cell = rowMedVitamind.createCell(7*days + 50);
			cell.setCellValue(recommendedVitamind);
			
			
			
			
			
			if(isParenteralVitaminD) {

				float lower_limit = recommendedVitamind - ((10 * recommendedVitamind) / 100);
				float upper_limit = recommendedVitamind + ((10 * recommendedVitamind) / 100);
				if((intakeVitamind < (lower_limit*stay)) || (intakeVitamind > (upper_limit*stay)) && (diffVitamind != 0)) {
					if(intakeVitamind < lower_limit*stay) {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((stay*recommendedVitamind));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (stay*recommendedVitamind));
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((stay*recommendedVitamind));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (stay*recommendedVitamind));
					}
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedVitamind.createCell(7*days + 51);
					cell.setCellValue(intakeVitamind);
					cell = rowMedVitamind.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}else {
				if((intakeVitamind < (300*stay)) || (intakeVitamind > (400*stay)) && (diffVitamind != 0)) {
					if(intakeVitamind < 300*stay) {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((300*stay));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (300*stay));
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((400*stay));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (400*stay));
					}
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedVitamind.createCell(7*days + 51);
					cell.setCellValue(intakeVitamind);
					cell = rowMedVitamind.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
			
			float percentageVitamind = (intakeVitamind / recommendedVitamind) * 100;

			diffHashMap.put("Vitamind", percentageVitamind);
			cell = rowMedVitamind.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);

		}
		

		if(weight != -1) {
			cell = rowMedVitamind.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		

		if(intakeCalcium != -1) {
			
			if(days==0 && stay==1) {
				recommendedCalcium = 210;
	 		}

			cell = rowMedCalcium.createCell(7*days + 49);
			cell.setCellValue(intakeCalcium);
			cell = rowMedCalcium.createCell(7*days + 50);
			cell.setCellValue(recommendedCalcium);
			
			
			float lower_limit = recommendedCalcium - ((10 * recommendedCalcium) / 100);
			float upper_limit = recommendedCalcium + ((10 * recommendedCalcium) / 100);
			if((intakeCalcium < (lower_limit*stay)) || (intakeCalcium > (upper_limit*stay)) && (diffCalcium != 0)) {
				if(intakeCalcium < lower_limit*stay) {
					cell = rowMedCalcium.createCell(7*days + 51);
					cell.setCellValue((recommendedCalcium*stay));
					cell = rowMedCalcium.createCell(7*days + 52);
					cell.setCellValue(intakeCalcium - (recommendedCalcium*stay));
				}else {
					cell = rowMedCalcium.createCell(7*days + 51);
					cell.setCellValue((recommendedCalcium*stay));
					cell = rowMedCalcium.createCell(7*days + 52);
					cell.setCellValue(intakeCalcium - (recommendedCalcium*stay));
				}
				cell = rowMedCalcium.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedCalcium.createCell(7*days + 51);
				cell.setCellValue(intakeCalcium);
				cell = rowMedCalcium.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedCalcium.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
			
			
			
			float percentageCalcium = (intakeCalcium / recommendedCalcium) * 100;

			diffHashMap.put("Calcium", percentageCalcium);
			cell = rowMedCalcium.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);

		}
		

		if(weight != -1) {
			cell = rowMedCalcium.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		

		if(intakePhosphorus != -1) {
			
			if(days==0 && stay == 1) {
				recommendedPhosphorus = 100;
	 		}

			cell = rowMedPhosphorus.createCell(7*days + 49);
			cell.setCellValue(intakePhosphorus);
			cell = rowMedPhosphorus.createCell(7*days + 50);
			cell.setCellValue(recommendedPhosphorus);
			
			float lower_limit = recommendedPhosphorus - ((10 * recommendedPhosphorus) / 100);
			float upper_limit = recommendedPhosphorus + ((10 * recommendedPhosphorus) / 100);
			if((intakePhosphorus < (lower_limit*stay)) || (intakePhosphorus > (upper_limit*stay)) && (diffPhosphorus != 0)) {
				if(intakePhosphorus < lower_limit*stay) {
					cell = rowMedPhosphorus.createCell(7*days + 51);
					cell.setCellValue((recommendedPhosphorus*stay));
					cell = rowMedPhosphorus.createCell(7*days + 52);
					cell.setCellValue(intakePhosphorus - (recommendedPhosphorus*stay));
				}else {
					cell = rowMedPhosphorus.createCell(7*days + 51);
					cell.setCellValue((recommendedPhosphorus*stay));
					cell = rowMedPhosphorus.createCell(7*days + 52);
					cell.setCellValue(intakePhosphorus - (recommendedPhosphorus*stay));
				}
				cell = rowMedPhosphorus.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedPhosphorus.createCell(7*days + 51);
				cell.setCellValue(intakePhosphorus);
				cell = rowMedPhosphorus.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedPhosphorus.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
			
		
			float percentagePhosphorus = (intakePhosphorus / recommendedEnergy) * 100;

			diffHashMap.put("Phosphorus", percentagePhosphorus);
			cell = rowMedPhosphorus.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);

		}
		

		if(weight != -1) {
			cell = rowMedPhosphorus.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		
		if(intakeIron != -1) {

			if(days==0 && stay == 1) {
				recommendedIron = 8;
	 		}
			cell = rowMedIron.createCell(7*days + 49);
			cell.setCellValue(intakeIron);
			cell = rowMedIron.createCell(7*days + 50);
			cell.setCellValue(recommendedIron);
			
			
			if((intakeIron < (6*stay)) || (intakeIron > (10*stay)) && (diffIron != 0)) {
				if(intakeIron < 6*stay) {
					cell = rowMedIron.createCell(7*days + 51);
					cell.setCellValue((6*stay));
					cell = rowMedIron.createCell(7*days + 52);
					cell.setCellValue(intakeIron - (6*stay));
				}else {
					cell = rowMedIron.createCell(7*days + 51);
					cell.setCellValue((10*stay));
					cell = rowMedIron.createCell(7*days + 52);
					cell.setCellValue(intakeIron - (10*stay));
				}
				cell = rowMedIron.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedIron.createCell(7*days + 51);
				cell.setCellValue(intakeIron);
				cell = rowMedIron.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedIron.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
			
		
			float percentageIron = (intakeIron / recommendedIron) * 100;

			diffHashMap.put("Iron", percentageIron);
			cell = rowMedIron.createCell(7*days + 54);
			cell.setCellValue(feedTypeEnergy);

		}
	

		if(weight != -1) {
			cell = rowMedIron.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		
		diffHashMap.put("Parenteral", isRecommendationEnteral);
		diffHashMap.put("stay", stay);
				
		//Impute previous missed LOS
		for(int i= 0; i < missedLos.size(); i++) {
			days = missedLos.get(i) - 1;

			if(intakeEnergy != -1) {
				cell = rowMedEnergy.createCell(7*days + 49);
				cell.setCellValue(intakeEnergy);
				cell = rowMedEnergy.createCell(7*days + 50);
				cell.setCellValue(recommendedEnergy);
//				

				if((intakeEnergy < (90*stay)) || (intakeEnergy > (120*stay)) && (diffEnergy != 0)) {
					if(intakeEnergy < 90*stay) {
						cell = rowMedEnergy.createCell(7*days + 51);
						cell.setCellValue(90*stay);
						cell = rowMedEnergy.createCell(7*days + 52);
						cell.setCellValue(intakeEnergy - (90*stay));
					}else {
						cell = rowMedEnergy.createCell(7*days + 51);
						cell.setCellValue(120*stay);
						cell = rowMedEnergy.createCell(7*days + 52);
						cell.setCellValue(intakeEnergy - (120*stay));
					}
					cell = rowMedEnergy.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedEnergy.createCell(7*days + 51);
					cell.setCellValue(intakeEnergy);
					cell = rowMedEnergy.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedEnergy.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
				
			}
			

			if(weight != -1) {
				cell = rowMedEnergy.createCell(7*days + 55);
				cell.setCellValue(weight);
			}

			

			if(intakeProtein != -1) {


				cell = rowMedProtein.createCell(7*days + 49);
				cell.setCellValue(intakeProtein);
				cell = rowMedProtein.createCell(7*days + 50);
				cell.setCellValue(recommendedProtein);
				
		
				
				if(babyDetailList.get(0).getBirthweight() < 2500) {
					if((intakeProtein < (3*stay)) || (intakeProtein > (4*stay)) && (diffProtein != 0)) {
						if(intakeProtein < 3*stay) {
							cell = rowMedProtein.createCell(7*days + 51);
							cell.setCellValue(3*stay);
							cell = rowMedProtein.createCell(7*days + 52);
							cell.setCellValue(intakeProtein - (3*stay));
						}else {
							cell = rowMedProtein.createCell(7*days + 51);
							cell.setCellValue(4*stay);
							cell = rowMedProtein.createCell(7*days + 52);
							cell.setCellValue(intakeProtein - (4*stay));
						}
						cell = rowMedProtein.createCell(7*days + 53);
						cell.setCellValue("Deviation");
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue(intakeProtein);
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(0);
						cell = rowMedProtein.createCell(7*days + 53);
						cell.setCellValue("No Error");
					}
				}else {
					if((intakeProtein < (2*stay)) || (intakeProtein > (3*stay)) && (diffProtein != 0)) {
						if(intakeProtein < 2*stay) {
							cell = rowMedProtein.createCell(7*days + 51);
							cell.setCellValue(2*stay);
							cell = rowMedProtein.createCell(7*days + 52);
							cell.setCellValue(intakeProtein - (2*stay));
						}else {
							cell = rowMedProtein.createCell(7*days + 51);
							cell.setCellValue(3*stay);
							cell = rowMedProtein.createCell(7*days + 52);
							cell.setCellValue(intakeProtein - (3*stay));
						}
						cell = rowMedProtein.createCell(7*days + 53);
						cell.setCellValue("Deviation");
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue(intakeProtein);
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(0);
						cell = rowMedProtein.createCell(7*days + 53);
						cell.setCellValue("No Error");
					}
				}
			}
			

			if(weight != -1) {
				cell = rowMedProtein.createCell(7*days + 55);
				cell.setCellValue(weight);
			}

		

			if(intakeVitamina != -1) {

				cell = rowMedVitamina.createCell(7*days + 49);
				cell.setCellValue(intakeVitamina);
				cell = rowMedVitamina.createCell(7*days + 50);
				cell.setCellValue(recommendedVitamina);
				float lower_limit = recommendedVitamina - ((10 * recommendedVitamina) / 100);
				float upper_limit = recommendedVitamina + ((10 * recommendedVitamina) / 100);
				if((intakeVitamina < (lower_limit*stay)) || (intakeVitamina > (upper_limit*stay)) && (diffVitamina != 0)) {
					if(intakeVitamina < lower_limit*stay) {
						cell = rowMedVitamina.createCell(7*days + 51);
						cell.setCellValue((recommendedVitamina*stay));
						cell = rowMedVitamina.createCell(7*days + 52);
						cell.setCellValue(intakeVitamina - (recommendedVitamina*stay));
					}else {
						cell = rowMedVitamina.createCell(7*days + 51);
						cell.setCellValue((recommendedVitamina*stay));
						cell = rowMedVitamina.createCell(7*days + 52);
						cell.setCellValue(intakeVitamina - (recommendedVitamina*stay));
					}
					cell = rowMedVitamina.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedVitamina.createCell(7*days + 51);
					cell.setCellValue(intakeVitamina);
					cell = rowMedVitamina.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedVitamina.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
				
				
			}
			

			if(weight != -1) {
				cell = rowMedVitamina.createCell(7*days + 55);
				cell.setCellValue(weight);
			}

			if(intakeVitamind != -1) {

				cell = rowMedVitamind.createCell(7*days + 49);
				cell.setCellValue(intakeVitamind);
				cell = rowMedVitamind.createCell(7*days + 50);
				cell.setCellValue(recommendedVitamind);
				
				if(isParenteralVitaminD) {
					float lower_limit = recommendedVitamind - ((10 * recommendedVitamind) / 100);
					float upper_limit = recommendedVitamind + ((10 * recommendedVitamind) / 100);
					if((intakeVitamind < (lower_limit*stay)) || (intakeVitamind > (upper_limit*stay)) && (diffVitamind != 0)) {
						if(intakeVitamind < lower_limit*stay) {
							cell = rowMedVitamind.createCell(7*days + 51);
							cell.setCellValue((recommendedVitamind*stay));
							cell = rowMedVitamind.createCell(7*days + 52);
							cell.setCellValue(intakeVitamind - (recommendedVitamind*stay));
						}else {
							cell = rowMedVitamind.createCell(7*days + 51);
							cell.setCellValue((recommendedVitamind*stay));
							cell = rowMedVitamind.createCell(7*days + 52);
							cell.setCellValue(intakeVitamind - (recommendedVitamind*stay));
						}
						cell = rowMedVitamind.createCell(7*days + 53);
						cell.setCellValue("Deviation");
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue(intakeVitamind);
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(0);
						cell = rowMedVitamind.createCell(7*days + 53);
						cell.setCellValue("No Error");
					}
				}else {
					if((intakeVitamind < (300*stay)) || (intakeVitamind > (400*stay)) && (diffVitamind != 0)) {
						if(intakeVitamind < 300*stay) {
							cell = rowMedVitamind.createCell(7*days + 51);
							cell.setCellValue((300*stay));
							cell = rowMedVitamind.createCell(7*days + 52);
							cell.setCellValue(intakeVitamind - (300*stay));
						}else {
							cell = rowMedVitamind.createCell(7*days + 51);
							cell.setCellValue((400*stay));
							cell = rowMedVitamind.createCell(7*days + 52);
							cell.setCellValue(intakeVitamind - (400*stay));
						}
						cell = rowMedVitamind.createCell(7*days + 53);
						cell.setCellValue("Deviation");
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue(intakeVitamind);
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(0);
						cell = rowMedVitamind.createCell(7*days + 53);
						cell.setCellValue("No Error");
					}
				}
			}
			

			if(weight != -1) {
				cell = rowMedVitamind.createCell(7*days + 55);
				cell.setCellValue(weight);
			}

			if(intakeCalcium != -1) {

				cell = rowMedCalcium.createCell(7*days + 49);
				cell.setCellValue(intakeCalcium);
				cell = rowMedCalcium.createCell(7*days + 50);
				cell.setCellValue(recommendedCalcium);
				float lower_limit = recommendedCalcium - ((10 * recommendedCalcium) / 100);
				float upper_limit = recommendedCalcium + ((10 * recommendedCalcium) / 100);
				if((intakeCalcium < (lower_limit*stay)) || (intakeCalcium > (upper_limit*stay)) && (diffCalcium != 0)) {
					if(intakeCalcium < lower_limit*stay) {
						cell = rowMedCalcium.createCell(7*days + 51);
						cell.setCellValue((recommendedCalcium*stay));
						cell = rowMedCalcium.createCell(7*days + 52);
						cell.setCellValue(intakeCalcium - (recommendedCalcium*stay));
					}else {
						cell = rowMedCalcium.createCell(7*days + 51);
						cell.setCellValue((recommendedCalcium*stay));
						cell = rowMedCalcium.createCell(7*days + 52);
						cell.setCellValue(intakeCalcium - (recommendedCalcium*stay));
					}
					cell = rowMedCalcium.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedCalcium.createCell(7*days + 51);
					cell.setCellValue(intakeCalcium);
					cell = rowMedCalcium.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedCalcium.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
			

			if(weight != -1) {
				cell = rowMedCalcium.createCell(7*days + 55);
				cell.setCellValue(weight);
			}

			if(intakePhosphorus != -1) {

				cell = rowMedPhosphorus.createCell(7*days + 49);
				cell.setCellValue(intakePhosphorus);
				cell = rowMedPhosphorus.createCell(7*days + 50);
				cell.setCellValue(recommendedPhosphorus);
				
				float lower_limit = recommendedPhosphorus - ((10 * recommendedPhosphorus) / 100);
				float upper_limit = recommendedPhosphorus + ((10 * recommendedPhosphorus) / 100);
				if((intakePhosphorus < (lower_limit*stay)) || (intakePhosphorus > (upper_limit*stay)) && (diffPhosphorus != 0)) {
					if(intakePhosphorus < lower_limit*stay) {
						cell = rowMedPhosphorus.createCell(7*days + 51);
						cell.setCellValue((recommendedPhosphorus*stay));
						cell = rowMedPhosphorus.createCell(7*days + 52);
						cell.setCellValue(intakePhosphorus - (recommendedPhosphorus*stay));
					}else {
						cell = rowMedPhosphorus.createCell(7*days + 51);
						cell.setCellValue((recommendedPhosphorus*stay));
						cell = rowMedPhosphorus.createCell(7*days + 52);
						cell.setCellValue(intakePhosphorus - (recommendedPhosphorus*stay));
					}
					cell = rowMedPhosphorus.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedPhosphorus.createCell(7*days + 51);
					cell.setCellValue(intakePhosphorus);
					cell = rowMedPhosphorus.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedPhosphorus.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
			

			if(weight != -1) {
				cell = rowMedPhosphorus.createCell(7*days + 55);
				cell.setCellValue(weight);
			}
			if(intakeIron != -1) {


				cell = rowMedIron.createCell(7*days + 49);
				cell.setCellValue(intakeIron);
				cell = rowMedIron.createCell(7*days + 50);
				cell.setCellValue(recommendedIron);
				
				if((intakeIron < (6*stay)) || (intakeIron > (10*stay)) && (diffIron != 0)) {
					if(intakeIron < 6*stay) {
						cell = rowMedIron.createCell(7*days + 51);
						cell.setCellValue((6*stay));
						cell = rowMedIron.createCell(7*days + 52);
						cell.setCellValue(intakeIron - (6*stay));
					}else {
						cell = rowMedIron.createCell(7*days + 51);
						cell.setCellValue((10*stay));
						cell = rowMedIron.createCell(7*days + 52);
						cell.setCellValue(intakeIron - (10*stay));
					}
					cell = rowMedIron.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedIron.createCell(7*days + 51);
					cell.setCellValue(intakeIron);
					cell = rowMedIron.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedIron.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
		

			if(weight != -1) {
				cell = rowMedIron.createCell(7*days + 55);
				cell.setCellValue(weight);
			}
		}
		if(intakeEnergy != -1) {

			missedLos = new ArrayList<Integer>();
		}

		return diffHashMap;
	}
	
	
	
	
	public void computeErrorLast(Timestamp creationTime,CaclulatorDeficitPOJO cuurentDeficitLast, BabyfeedDetail feed, String uhid, Timestamp dateofadmission,int gestationWeek,int gestationDays, Cell cell, Row rowMedEnergy, Row rowMedProtein, Row rowMedVitamina, Row rowMedVitamind, Row rowMedPhosphorus, Row rowMedIron, Row rowMedCalcium, int lengthStay,HashMap<String, Float> currentDiff) {
		float intakeEnergy = -1;
		float intakeProtein = -1;
		float intakeVitamina = -1;
		float intakeVitamind = -1;
		float intakeCalcium = -1;
		float intakePhosphorus = -1;
		float intakeIron = -1;

		float recommendedEnergy = -1;
		float recommendedProtein = -1;
		float recommendedVitamina = -1;
		float recommendedVitamind = -1;
		float recommendedCalcium = -1;
		float recommendedPhosphorus = -1;
		float recommendedIron = -1;

		float diffEnergy = -1;
		float diffProtein = -1;
		float diffVitamina = -1;
		float diffVitamind = -1;
		float diffCalcium = -1;
		float diffPhosphorus = -1;
		float diffIron = -1;

		String resultEnergy = "";
		String resultProtein = "";
		String resultVitamina = "";
		String resultVitamind = "";
		String resultCalcium = "";
		String resultPhosphorus = "";
		String resultIron = "";

		boolean isEnergyCorrect = true;
		boolean isProteinCorrect = true;
		boolean isVitaminaCorrect = true;
		boolean isVitamindCorrect = true;
		boolean isCalciumCorrect = true;
		boolean isPhosphorusCorrect = true;
		boolean isIronCorrect = true;

		Timestamp startDate = creationTime;

		Date df = new Date(startDate.getTime());
		float weight = -1;

		String queryCurrentBabyVisit = "select obj from BabyVisit as obj where uhid='" + uhid + "' and visitdate='"
				+ df + "'";
		List<BabyVisit> currentBabyVisitList = notesDoa.getListFromMappedObjNativeQuery(queryCurrentBabyVisit);
		if(!BasicUtils.isEmpty(currentBabyVisitList)) {
			if(!BasicUtils.isEmpty(currentBabyVisitList.get(0).getCurrentdateweight()))
				weight = currentBabyVisitList.get(0).getCurrentdateweight();
		}


		int days = lengthStay - 1;

	   
		float currentGestationWeek = gestationWeek;
		float currentGestationDays = gestationDays;
		currentGestationDays += days;
		if(currentGestationDays > 6) {
			currentGestationWeek = currentGestationWeek + (currentGestationDays / 7);
			currentGestationDays = currentGestationDays % 7;
		}
		
		Float recommendationEnteral = currentDiff.get("Parenteral");

		//Energy
		boolean isParenteralVitaminD = false;
		if(recommendationEnteral == 1f) {
			if(!BasicUtils.isEmpty(currentDiff.get("Energy"))) {
				intakeEnergy = ((currentDiff.get("Energy")) * cuurentDeficitLast.getEshphaganIntake().get("Energy")) / 100;
				recommendedEnergy = cuurentDeficitLast.getEshphaganIntake().get("Energy");
				diffEnergy = intakeEnergy - recommendedEnergy;
				if(diffEnergy < 0) {
					isEnergyCorrect = false;
				}
			}
		
			if(!BasicUtils.isEmpty(currentDiff.get("Protein"))) {

			intakeProtein = ((currentDiff.get("Protein")) * cuurentDeficitLast.getEshphaganIntake().get("Protein")) / 100;
			recommendedProtein = cuurentDeficitLast.getEshphaganIntake().get("Protein");
			diffProtein = intakeProtein - recommendedProtein;
			if(diffProtein < 0) {
				isProteinCorrect = false;
			}
			}
			
			if(!BasicUtils.isEmpty(currentDiff.get("Vitamina"))) {

			intakeVitamina = ((currentDiff.get("Vitamina")) * cuurentDeficitLast.getEshphaganIntake().get("Vitamina")) / 100;
			recommendedVitamina = cuurentDeficitLast.getEshphaganIntake().get("Vitamina");
			diffVitamina = intakeVitamina - recommendedVitamina;
			if(diffVitamina < 0) {
				isVitaminaCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Vitamind"))) {

			intakeVitamind = ((currentDiff.get("Vitamind")) * cuurentDeficitLast.getEshphaganIntake().get("Vitamind")) / 100;
			recommendedVitamind = cuurentDeficitLast.getEshphaganIntake().get("Vitamind");
			diffVitamind = intakeVitamind - recommendedVitamind;
			if(diffVitamind < 0) {
				isVitamindCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Calcium"))) {

			intakeCalcium = ((currentDiff.get("Calcium")) * cuurentDeficitLast.getEshphaganIntake().get("Calcium")) / 100;
			recommendedCalcium = cuurentDeficitLast.getEshphaganIntake().get("Calcium");
			diffCalcium = intakeCalcium - recommendedCalcium;
			if(diffCalcium < 0) {
				isCalciumCorrect = false;
			}
			}
			
			if(!BasicUtils.isEmpty(currentDiff.get("Phosphorus"))) {

			intakePhosphorus = ((currentDiff.get("Phosphorus")) * cuurentDeficitLast.getEshphaganIntake().get("Phosphorus")) / 100;
			recommendedPhosphorus = cuurentDeficitLast.getEshphaganIntake().get("Phosphorus");
			diffPhosphorus = intakePhosphorus - recommendedPhosphorus;
			if(diffPhosphorus < 0) {
				isPhosphorusCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Iron"))) {

			intakeIron = ((currentDiff.get("Iron")) * cuurentDeficitLast.getEshphaganIntake().get("Iron")) / 100;
			recommendedIron = cuurentDeficitLast.getEshphaganIntake().get("Iron");
			diffIron = intakeIron - recommendedIron;
			if(diffIron < 0) {
				isIronCorrect = false;
			}
			}
		}else {
			if(!BasicUtils.isEmpty(currentDiff.get("Energy"))) {

			intakeEnergy = ((currentDiff.get("Energy")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy")) / 100;
			recommendedEnergy = cuurentDeficitLast.getEshphaganParenteralIntake().get("Energy");
			diffEnergy = intakeEnergy - recommendedEnergy;
			if(diffEnergy < 0) {
				isEnergyCorrect = false;
			}
			}
			
			
			
			if(!BasicUtils.isEmpty(currentDiff.get("Vitamina"))) {

			intakeVitamina = ((currentDiff.get("Vitamina")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina")) / 100;
			recommendedVitamina = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamina");
			diffVitamina = intakeVitamina - recommendedVitamina;
			if(diffVitamina < 0) {
				isVitaminaCorrect = false;
			}
			
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Vitamind"))) {

			intakeVitamind = ((currentDiff.get("Vitamind")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind")) / 100;
			recommendedVitamind = cuurentDeficitLast.getEshphaganParenteralIntake().get("Vitamind");
			diffVitamind = intakeVitamind - recommendedVitamind;
			if(diffVitamind < 0) {
				isVitamindCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Calcium"))) {

			intakeCalcium = ((currentDiff.get("Calcium")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium")) / 100;
			recommendedCalcium = cuurentDeficitLast.getEshphaganParenteralIntake().get("Calcium");
			diffCalcium = intakeCalcium - recommendedCalcium;
			if(diffCalcium < 0) {
				isCalciumCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Phosphorus"))) {

			intakePhosphorus = ((currentDiff.get("Phosphorus")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus")) / 100;
			recommendedPhosphorus = cuurentDeficitLast.getEshphaganParenteralIntake().get("Phosphorus");
			diffPhosphorus = intakePhosphorus - recommendedPhosphorus;
			if(diffPhosphorus < 0) {
				isPhosphorusCorrect = false;
			}
			}
			if(!BasicUtils.isEmpty(currentDiff.get("Iron"))) {

			intakeIron = ((currentDiff.get("Iron")) * cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron")) / 100;
			recommendedIron = cuurentDeficitLast.getEshphaganParenteralIntake().get("Iron");
			diffIron = intakeIron - recommendedIron;
			if(diffIron < 0) {
				isIronCorrect = false;
			}
			}
			isParenteralVitaminD = true;

		}

		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);
		if(intakeEnergy != -1) {
			cell = rowMedEnergy.createCell(7*days + 49);
			cell.setCellValue(intakeEnergy);
			cell = rowMedEnergy.createCell(7*days + 50);
			cell.setCellValue(recommendedEnergy);
//			

			if((intakeEnergy < (90)) || (intakeEnergy > (120)) && intakeEnergy != 0) {
				if(intakeEnergy < 90) {
					cell = rowMedEnergy.createCell(7*days + 51);
					cell.setCellValue((90));
					cell = rowMedEnergy.createCell(7*days + 52);
					cell.setCellValue(intakeEnergy - (90));
				}else {
					cell = rowMedEnergy.createCell(7*days + 51);
					cell.setCellValue((120));
					cell = rowMedEnergy.createCell(7*days + 52);
					cell.setCellValue(intakeEnergy - (120));
				}
				cell = rowMedEnergy.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedEnergy.createCell(7*days + 51);
				cell.setCellValue(intakeEnergy);
				cell = rowMedEnergy.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedEnergy.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
		}

		if(weight != -1) {
			cell = rowMedEnergy.createCell(7*days + 55);
			cell.setCellValue(weight);
		}

		

		if(intakeProtein != -1) {


			cell = rowMedProtein.createCell(7*days + 49);
			cell.setCellValue(intakeProtein);
			cell = rowMedProtein.createCell(7*days + 50);
			cell.setCellValue(recommendedProtein);
			
	
			
			if(babyDetailList.get(0).getBirthweight() < 2500) {
				if((intakeProtein < (3)) || (intakeProtein > (4)) && (diffProtein != 0)) {
					if(intakeProtein < 3) {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((3));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (3));
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue((4));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (4));
					}
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedProtein.createCell(7*days + 51);
					cell.setCellValue((intakeProtein));
					cell = rowMedProtein.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}else {
				if((intakeProtein < (2)) || (intakeProtein > (3)) && (diffProtein != 0)) {
					if(intakeProtein < 2) {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue(((2)));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (2));
					}else {
						cell = rowMedProtein.createCell(7*days + 51);
						cell.setCellValue(((3)));
						cell = rowMedProtein.createCell(7*days + 52);
						cell.setCellValue(intakeProtein - (3));
					}
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedProtein.createCell(7*days + 51);
					cell.setCellValue((intakeProtein));
					cell = rowMedProtein.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedProtein.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
		
			
			
		
		}

		if(weight != -1) {
			cell = rowMedProtein.createCell(7*days + 55);
			cell.setCellValue(weight);
		}

		
		if(intakeVitamina != -1) {

			cell = rowMedVitamina.createCell(7*days + 49);
			cell.setCellValue(intakeVitamina);
			cell = rowMedVitamina.createCell(7*days + 50);
			cell.setCellValue(recommendedVitamina);
			
			
			float lower_limit = recommendedVitamina - ((10 * recommendedVitamina) / 100);
			float upper_limit = recommendedVitamina + ((10 * recommendedVitamina) / 100);
			if((intakeVitamina < (lower_limit)) || (intakeVitamina > (upper_limit)) && (diffVitamina != 0)) {
				if(intakeVitamina < lower_limit) {
					cell = rowMedVitamina.createCell(7*days + 51);
					cell.setCellValue((recommendedVitamina));
					cell = rowMedVitamina.createCell(7*days + 52);
					cell.setCellValue(intakeVitamina - (recommendedVitamina));
				}else {
					cell = rowMedVitamina.createCell(7*days + 51);
					cell.setCellValue((recommendedVitamina));
					cell = rowMedVitamina.createCell(7*days + 52);
					cell.setCellValue(intakeVitamina - (recommendedVitamina));
				}
				cell = rowMedVitamina.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedVitamina.createCell(7*days + 51);
				cell.setCellValue(intakeVitamina);
				cell = rowMedVitamina.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedVitamina.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
			
			
		}
		
		if(weight != -1) {
			cell = rowMedVitamina.createCell(7*days + 55);
			cell.setCellValue(weight);
		}

		if(intakeVitamind != -1) {

			cell = rowMedVitamind.createCell(7*days + 49);
			cell.setCellValue(intakeVitamind);
			cell = rowMedVitamind.createCell(7*days + 50);
			cell.setCellValue(recommendedVitamind);
			if(isParenteralVitaminD) {

				float lower_limit = recommendedVitamind - ((10 * recommendedVitamind) / 100);
				float upper_limit = recommendedVitamind + ((10 * recommendedVitamind) / 100);
				if((intakeVitamind < (lower_limit)) || (intakeVitamind > (upper_limit)) && (diffVitamind != 0)) {
					if(intakeVitamind < lower_limit) {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((recommendedVitamind));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (recommendedVitamind));
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((recommendedVitamind));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (recommendedVitamind));
					}
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedVitamind.createCell(7*days + 51);
					cell.setCellValue(intakeVitamind);
					cell = rowMedVitamind.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}else {
				if((intakeVitamind < (300)) || (intakeVitamind > (400)) && (diffVitamind != 0)) {
					if(intakeVitamind < 300) {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((300));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (300));
					}else {
						cell = rowMedVitamind.createCell(7*days + 51);
						cell.setCellValue((400));
						cell = rowMedVitamind.createCell(7*days + 52);
						cell.setCellValue(intakeVitamind - (400));
					}
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("Deviation");
				}else {
					cell = rowMedVitamind.createCell(7*days + 51);
					cell.setCellValue(intakeVitamind);
					cell = rowMedVitamind.createCell(7*days + 52);
					cell.setCellValue(0);
					cell = rowMedVitamind.createCell(7*days + 53);
					cell.setCellValue("No Error");
				}
			}
		}
		
		if(weight != -1) {
			cell = rowMedVitamind.createCell(7*days + 55);
			cell.setCellValue(weight);
		}

		if(intakeCalcium != -1) {

			cell = rowMedCalcium.createCell(7*days + 49);
			cell.setCellValue(intakeCalcium);
			cell = rowMedCalcium.createCell(7*days + 50);
			cell.setCellValue(recommendedCalcium);
			
			float lower_limit = recommendedCalcium - ((10 * recommendedCalcium) / 100);
			float upper_limit = recommendedCalcium + ((10 * recommendedCalcium) / 100);
			if((intakeCalcium < (lower_limit)) || (intakeCalcium > (upper_limit)) && (diffCalcium != 0)) {
				if(intakeCalcium < lower_limit) {
					cell = rowMedCalcium.createCell(7*days + 51);
					cell.setCellValue((recommendedCalcium));
					cell = rowMedCalcium.createCell(7*days + 52);
					cell.setCellValue(intakeCalcium - (recommendedCalcium));
				}else {
					cell = rowMedCalcium.createCell(7*days + 51);
					cell.setCellValue((recommendedCalcium));
					cell = rowMedCalcium.createCell(7*days + 52);
					cell.setCellValue(intakeCalcium - (recommendedCalcium));
				}
				cell = rowMedCalcium.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedCalcium.createCell(7*days + 51);
				cell.setCellValue(intakeCalcium);
				cell = rowMedCalcium.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedCalcium.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
		}
		if(weight != -1) {
			cell = rowMedCalcium.createCell(7*days + 55);
			cell.setCellValue(weight);
		}

		if(intakePhosphorus != -1) {

			cell = rowMedPhosphorus.createCell(7*days + 49);
			cell.setCellValue(intakePhosphorus);
			cell = rowMedPhosphorus.createCell(7*days + 50);
			cell.setCellValue(recommendedPhosphorus);
			
			float lower_limit = recommendedPhosphorus - ((10 * recommendedPhosphorus) / 100);
			float upper_limit = recommendedPhosphorus + ((10 * recommendedPhosphorus) / 100);
			if((intakePhosphorus < (lower_limit)) || (intakePhosphorus > (upper_limit)) && (diffPhosphorus != 0)) {
				if(intakePhosphorus < lower_limit) {
					cell = rowMedPhosphorus.createCell(7*days + 51);
					cell.setCellValue((recommendedPhosphorus));
					cell = rowMedPhosphorus.createCell(7*days + 52);
					cell.setCellValue(intakePhosphorus - (recommendedPhosphorus));
				}else {
					cell = rowMedPhosphorus.createCell(7*days + 51);
					cell.setCellValue((recommendedPhosphorus));
					cell = rowMedPhosphorus.createCell(7*days + 52);
					cell.setCellValue(intakePhosphorus - (recommendedPhosphorus));
				}
				cell = rowMedPhosphorus.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedPhosphorus.createCell(7*days + 51);
				cell.setCellValue(intakePhosphorus);
				cell = rowMedPhosphorus.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedPhosphorus.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
			
		}
		
		if(weight != -1) {
			cell = rowMedPhosphorus.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
		if(intakeIron != -1) {


			cell = rowMedIron.createCell(7*days + 49);
			cell.setCellValue(intakeIron);
			cell = rowMedIron.createCell(7*days + 50);
			cell.setCellValue(recommendedIron);
			if((intakeIron < (6)) || (intakeIron > (10)) && (diffIron != 0)) {
				if(intakeIron < 6) {
					cell = rowMedIron.createCell(7*days + 51);
					cell.setCellValue((6));
					cell = rowMedIron.createCell(7*days + 52);
					cell.setCellValue(intakeIron - (6));
				}else {
					cell = rowMedIron.createCell(7*days + 51);
					cell.setCellValue((10));
					cell = rowMedIron.createCell(7*days + 52);
					cell.setCellValue(intakeIron - (10));
				}
				cell = rowMedIron.createCell(7*days + 53);
				cell.setCellValue("Deviation");
			}else {
				cell = rowMedIron.createCell(7*days + 51);
				cell.setCellValue(intakeIron);
				cell = rowMedIron.createCell(7*days + 52);
				cell.setCellValue(0);
				cell = rowMedIron.createCell(7*days + 53);
				cell.setCellValue("No Error");
			}
			
		}
		

		if(weight != -1) {
			cell = rowMedIron.createCell(7*days + 55);
			cell.setCellValue(weight);
		}
	}
	
	public CaclulatorDeficitPOJO getDeficitFeedCalculatorOrder(String uhid, BabyfeedDetail feedList,
 			List<RefNutritioncalculator> nutritionList, String currentWeight, Timestamp fromDateOffsetTwentFourHour, Timestamp toDateOffsetTwentFourHour, String type, int gestationWeek, int lengthStay) {

		boolean isEntryAvailable = false;
		
		List<NursingIntakeOutput> nursingIntakeOutputList = inicuDoa.getListFromMappedObjQuery(
 				HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));

 		CaclulatorDeficitPOJO calculator = new CaclulatorDeficitPOJO();
 		String oralFeed = "select obj from NursingIntakeOutput obj where uhid = '"  + uhid  + "' and entry_timestamp >= '"  + fromDateOffsetTwentFourHour  +
 				"' and entry_timestamp < '"  + toDateOffsetTwentFourHour  + "' order by entry_timestamp asc, creationtime asc";
 		List<NursingIntakeOutput> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);

 		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);

 		Timestamp currentTimeNurse = new Timestamp(new Date().getTime());
 		Timestamp pastTimeNurse = new Timestamp(new Date().getTime());
 		int counter = 1;
 		if (!BasicUtils.isEmpty(oralFeedList)) {
 			isEntryAvailable = true;
 			HashMap<String, Float> enteral = new HashMap<String, Float>();
 			for (NursingIntakeOutput oral : oralFeedList) {
 				currentTimeNurse = new Timestamp(oral.getEntry_timestamp().getTime());
 				boolean isEntryValid = false;
 				if(counter == 1) {
 					isEntryValid = true;
	 				pastTimeNurse = new Timestamp(oral.getEntry_timestamp().getTime());
 				}else {
 					if(currentTimeNurse.getTime() - pastTimeNurse.getTime() < 0) {
 						int yu = 1;
 					}
 					if(currentTimeNurse.getTime() - pastTimeNurse.getTime() > (10*60*1000)) {
 						isEntryValid = true;
 					}
 				}
 				
 				if(isEntryValid) {
 					counter++;
 					pastTimeNurse = new Timestamp(oral.getEntry_timestamp().getTime());

	 				for (RefNutritioncalculator nutrition : nutritionList) {
	
	 					if (!BasicUtils.isEmpty(oral.getPrimaryFeedType()) && oral.getPrimaryFeedType().equalsIgnoreCase(nutrition.getFeedtypeId()) && !BasicUtils.isEmpty(oral.getPrimaryFeedValue())) {
	
 							if (enteral.get(BasicConstants.ENERGY) != null) {
 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
 										 + ((oral.getPrimaryFeedValue() * nutrition.getEnergy()) / 100));
 							} else {
 								enteral.put(BasicConstants.ENERGY,
 										oral.getPrimaryFeedValue() * nutrition.getEnergy() / 100);
 							}

 							if (enteral.get(BasicConstants.PROTEIN) != null) {
 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getProtein()) / 100));
 							} else {
 								enteral.put(BasicConstants.PROTEIN,
 										oral.getPrimaryFeedValue() * nutrition.getProtein() / 100);
 							}

 							if (enteral.get(BasicConstants.FAT) != null) {
 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getFat()) / 100));
 							} else {
 								enteral.put(BasicConstants.FAT, oral.getPrimaryFeedValue() * nutrition.getFat() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINa) != null) {
 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getVitamina()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINa,
 										oral.getPrimaryFeedValue() * nutrition.getVitamina() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getVitamind()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										oral.getPrimaryFeedValue() * nutrition.getVitamind() / 100);
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										 + ((oral.getPrimaryFeedValue() * nutrition.getCalcium()) / 100));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										oral.getPrimaryFeedValue() * nutrition.getCalcium() / 100);
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getPhosphorus()) / 100));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										oral.getPrimaryFeedValue() * nutrition.getPhosphorus() / 100);
 							}
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										+  ((oral.getPrimaryFeedValue() * nutrition.getIron()) / 100));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										oral.getPrimaryFeedValue() * nutrition.getIron() / 100);
 							}
	
	 					}
	 					
	 					if (!BasicUtils.isEmpty(oral.getFormulaType()) && oral.getFormulaType().equalsIgnoreCase(nutrition.getFeedtypeId()) && !BasicUtils.isEmpty(oral.getFormulaValue())) {
	 						
 							if (enteral.get(BasicConstants.ENERGY) != null) {
 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
 										 + ((oral.getFormulaValue() * nutrition.getEnergy()) / 100));
 							} else {
 								enteral.put(BasicConstants.ENERGY,
 										oral.getFormulaValue() * nutrition.getEnergy() / 100);
 							}

 							if (enteral.get(BasicConstants.PROTEIN) != null) {
 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
 										+  ((oral.getFormulaValue() * nutrition.getProtein()) / 100));
 							} else {
 								enteral.put(BasicConstants.PROTEIN,
 										oral.getFormulaValue() * nutrition.getProtein() / 100);
 							}

 							if (enteral.get(BasicConstants.FAT) != null) {
 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
 										+  ((oral.getFormulaValue() * nutrition.getFat()) / 100));
 							} else {
 								enteral.put(BasicConstants.FAT, oral.getFormulaValue() * nutrition.getFat() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINa) != null) {
 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 										+  ((oral.getFormulaValue() * nutrition.getVitamina()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINa,
 										oral.getFormulaValue() * nutrition.getVitamina() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((oral.getFormulaValue() * nutrition.getVitamind()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										oral.getFormulaValue() * nutrition.getVitamind() / 100);
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										 + ((oral.getFormulaValue() * nutrition.getCalcium()) / 100));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										oral.getFormulaValue() * nutrition.getCalcium() / 100);
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((oral.getFormulaValue() * nutrition.getPhosphorus()) / 100));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										oral.getFormulaValue() * nutrition.getPhosphorus() / 100);
 							}
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										+  ((oral.getFormulaValue() * nutrition.getIron()) / 100));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										oral.getFormulaValue() * nutrition.getIron() / 100);
 							}
	
	 					}
	 				}
 				}
 			}

 			
			
 			if (!BasicUtils.isEmpty(feedList)) {
				for (RefNutritioncalculator nutrition : nutritionList) {

					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN001")){
 						if(feedList.getCalsyrupTotal() != null){
 							
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
 							}
 						}
					}
 					
 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE07") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN010")){
 						
 						
 						if(feedList.getCalsyrupTotal() != null){
 							
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
 							}
 						}
					}
 					
 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE28") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN006")){
 						
 						
 						if(feedList.getCalsyrupTotal() != null){
 							
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
 							}
 						}
					}

					if(!BasicUtils.isEmpty(feedList.getVitamindTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19")){
						if (enteral.get(BasicConstants.VITAMINd) != null) {
							enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
									+  ((feedList.getVitamindTotal() * nutrition.getVitamind())));
						} else {
							enteral.put(BasicConstants.VITAMINd,
									feedList.getVitamindTotal() * nutrition.getVitamind());
						}
					}

					if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN002")){
						if (enteral.get(BasicConstants.IRON) != null) {
							enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
									 + ((feedList.getIronTotal() * nutrition.getIron())));
						} else {
							enteral.put(BasicConstants.IRON,
									feedList.getIronTotal() * nutrition.getIron());
						}
					}
					
					if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE29") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN008")){
						if (enteral.get(BasicConstants.IRON) != null) {
							enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
									 + ((feedList.getIronTotal() * nutrition.getIron())));
						} else {
							enteral.put(BasicConstants.IRON,
									feedList.getIronTotal() * nutrition.getIron());
						}
					}
					
					if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE30") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN012")){
						if (enteral.get(BasicConstants.IRON) != null) {
							enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
									 + ((feedList.getIronTotal() * nutrition.getIron())));
						} else {
							enteral.put(BasicConstants.IRON,
									feedList.getIronTotal() * nutrition.getIron());
						}
					}
					
					if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE31") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN014")){
						if (enteral.get(BasicConstants.IRON) != null) {
							enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
									 + ((feedList.getIronTotal() * nutrition.getIron())));
						} else {
							enteral.put(BasicConstants.IRON,
									feedList.getIronTotal() * nutrition.getIron());
						}
					}
				}
 			}

 			calculator.setEnteralIntake(enteral);
 		}

 		Float energyNurseIntake = (float) 0;
 		Float proteinNurseIntake =  (float)0;
 		Float phosphorusNurseIntake = (float) 0;
 		Float vitaminaNurseIntake =  (float)0;
 		Float vitamindNurseIntake =  (float)0;
 		Float calciumNurseIntake =  (float)0;
 		Float ironNurseIntake = (float) 0;
 		if(calculator.getEnteralIntake().get(BasicConstants.ENERGY) != null && calculator.getEnteralIntake().get(BasicConstants.ENERGY) >0) {
 			energyNurseIntake = calculator.getEnteralIntake().get(BasicConstants.ENERGY);
 		}
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.PROTEIN) != null && calculator.getEnteralIntake().get(BasicConstants.PROTEIN) >0) {

 			proteinNurseIntake = calculator.getEnteralIntake().get(BasicConstants.PROTEIN);
 		}

 		if(calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) != null && calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) >0) {

 			phosphorusNurseIntake = calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS);
 		}

 		if(calculator.getEnteralIntake().get(BasicConstants.VITAMINa) != null && calculator.getEnteralIntake().get(BasicConstants.VITAMINa) >0) {

 			vitaminaNurseIntake = calculator.getEnteralIntake().get(BasicConstants.VITAMINa);
 		}

 		if(calculator.getEnteralIntake().get(BasicConstants.VITAMINd) != null && calculator.getEnteralIntake().get(BasicConstants.VITAMINd) >0) {

 			vitamindNurseIntake = calculator.getEnteralIntake().get(BasicConstants.VITAMINd);
 		}

 		if(calculator.getEnteralIntake().get(BasicConstants.CALCIUM) != null && calculator.getEnteralIntake().get(BasicConstants.CALCIUM) >0) {

 			calciumNurseIntake = calculator.getEnteralIntake().get(BasicConstants.CALCIUM);
 		}

 		if(calculator.getEnteralIntake().get(BasicConstants.IRON) != null && calculator.getEnteralIntake().get(BasicConstants.IRON) >0) {

 			ironNurseIntake = calculator.getEnteralIntake().get(BasicConstants.IRON);
 		}
 		
 		calculator = new CaclulatorDeficitPOJO();

 		float duration = (toDateOffsetTwentFourHour.getTime() - fromDateOffsetTwentFourHour.getTime()) / (1000 * 60 * 60);
 		
 		float stay = duration / 24;
 		
 		if(stay >= 1) {
 			stay = 1;
 		}
 		
 		Date dob =  babyDetailList.get(0).getDateofbirth(); 
 		String tob =  babyDetailList.get(0).getTimeofbirth(); 
 		
 		int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();
 		
 		
	 		String enFeedDetail = "Select obj from EnFeedDetail obj where uhid = '"  + uhid  + "' and babyfeedid = " + feedList.getBabyfeedid() + " order by creationtime";
	 		List<EnFeedDetail> enFeedList = notesDoa.getListFromMappedObjNativeQuery(enFeedDetail);
	 		String feedType = "";
	 		if(!BasicUtils.isEmpty(feedList.getFeedtype())) {
	 			String[] feedTypeArr = feedList.getFeedtype().replace("[", "").replace("]", "").split(",");
				feedType = feedTypeArr[0].trim();
	 		}if(!BasicUtils.isEmpty(feedList.getFeedTypeSecondary())) {
	 			String[] feedTypeArr = feedList.getFeedTypeSecondary().replace("[", "").replace("]", "").split(",");
				feedType = feedTypeArr[0].trim();
	 		}
	 		if(!BasicUtils.isEmpty(feedList.getTotalenteralvolume()) && BasicUtils.isEmpty(enFeedList) && !BasicUtils.isEmpty(feedType) && !BasicUtils.isEmpty(feedList.getIsenternalgiven()) && feedList.getIsenternalgiven() == true) {
	 			EnFeedDetail enFeedNew = new EnFeedDetail();
	 			enFeedNew.setNo_of_feed(1);
	 			enFeedNew.setFeed_volume(feedList.getTotalenteralvolume());
	 			enFeedList.add(enFeedNew);
	 		}
	 		
			//Merging date of admission and time of admission
			Timestamp currentTime = new Timestamp(new Date().getTime());
			if (!BasicUtils.isEmpty(dob)) {
				currentTime = new Timestamp(dob.getTime());
				if (!BasicUtils.isEmpty(tob)) {
					String[] toaArr = tob.split(",");
					// "10,38,PM"
					if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
						currentTime.setHours(Integer.parseInt(toaArr[0]) + 12);
					} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
						currentTime.setHours(0);
					} else {
						currentTime.setHours(Integer.parseInt(toaArr[0]));
					}
					currentTime.setMinutes(Integer.parseInt(toaArr[1]));
				}
				currentTime = new Timestamp(currentTime.getTime() - offset);
			}
			Timestamp dateofbirth = new Timestamp(currentTime.getTime());
			
			int dol = lengthStay;
	 			
	 		if (!BasicUtils.isEmpty(enFeedList) && !BasicUtils.isEmpty(feedType)) {
	 		
				
	 			HashMap<String, Float> enteral = new HashMap<String, Float>();
	 			for (EnFeedDetail oral : enFeedList) {
	 				for (RefNutritioncalculator nutrition : nutritionList) {
	
	 					if (feedType.equalsIgnoreCase(nutrition.getFeedtypeId())) {
	
	 						if (oral.getFeed_volume() != null && oral.getNo_of_feed() != null) {
	 							if (enteral.get(BasicConstants.ENERGY) != null) {
	 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
	 										 + ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getEnergy()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.ENERGY,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getEnergy() / 100);
	 							}
	
	 							if (enteral.get(BasicConstants.PROTEIN) != null) {
	 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getProtein()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.PROTEIN,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getProtein() / 100);
	 							}
	
	 							if (enteral.get(BasicConstants.FAT) != null) {
	 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getFat()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.FAT, oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getFat() / 100);
	 							}
	 							if (enteral.get(BasicConstants.VITAMINa) != null) {
	 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getVitamina()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINa,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getVitamina() / 100);
	 							}
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getVitamind()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getVitamind() / 100);
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										 + ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getCalcium()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getCalcium() / 100);
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getPhosphorus()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getPhosphorus() / 100);
	 							}
	 							if (enteral.get(BasicConstants.IRON) != null) {
	 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
	 										+  ((oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getIron()) / 100));
	 							} else {
	 								enteral.put(BasicConstants.IRON,
	 										oral.getFeed_volume() * oral.getNo_of_feed() * nutrition.getIron() / 100);
	 							}
	
	 						}
	 					}
	 				}
	 			}
	
	 			if (!BasicUtils.isEmpty(feedList)) {
	 				for (RefNutritioncalculator nutrition : nutritionList) {
	
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN001")){
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	 					
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE07") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN010")){
	 						
	 						
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	 					
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE28") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN006")){
	 						
	 						
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	
						if(!BasicUtils.isEmpty(feedList.getVitamindTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19")){
							if (enteral.get(BasicConstants.VITAMINd) != null) {
								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
										+  ((feedList.getVitamindTotal() * nutrition.getVitamind())));
							} else {
								enteral.put(BasicConstants.VITAMINd,
										feedList.getVitamindTotal() * nutrition.getVitamind());
							}
						}
	
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN002")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE29") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN008")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE30") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN012")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE31") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN014")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
	 				}
	 			}
	
	 			calculator.setEnteralIntake(enteral);
	 		}else if (BasicUtils.isEmpty(enFeedList) && !BasicUtils.isEmpty(feedType)) {
	 		
	 			HashMap<String, Float> enteral = new HashMap<String, Float>();
	 			for (RefNutritioncalculator nutrition : nutritionList) {
	
	 				if (feedType.equalsIgnoreCase(nutrition.getFeedtypeId())) {
	
	 					if (feedList.getFeedduration() != null && feedList.getFeedvolume() != null && feedList.getDuration() != null && !feedList.getFeedduration().equalsIgnoreCase("Continuous") && !feedList.getFeedduration().equalsIgnoreCase("0") ) {
							Integer duration1 = (int) Float.parseFloat(feedList.getFeedduration());
							Integer noOfFeeds = feedList.getDuration() / duration1;
							if (enteral.get(BasicConstants.ENERGY) != null) {
								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
										 + ((feedList.getFeedvolume() * noOfFeeds * nutrition.getEnergy()) / 100));
							} else {
								enteral.put(BasicConstants.ENERGY,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getEnergy() / 100);
							}
	
							if (enteral.get(BasicConstants.PROTEIN) != null) {
								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getProtein()) / 100));
							} else {
								enteral.put(BasicConstants.PROTEIN,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getProtein() / 100);
							}
	
							if (enteral.get(BasicConstants.FAT) != null) {
								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getFat()) / 100));
							} else {
								enteral.put(BasicConstants.FAT, feedList.getFeedvolume() * noOfFeeds * nutrition.getFat() / 100);
							}
							if (enteral.get(BasicConstants.VITAMINa) != null) {
								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getVitamina()) / 100));
							} else {
								enteral.put(BasicConstants.VITAMINa,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getVitamina() / 100);
							}
							if (enteral.get(BasicConstants.VITAMINd) != null) {
								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getVitamind()) / 100));
							} else {
								enteral.put(BasicConstants.VITAMINd,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getVitamind() / 100);
							}
							if (enteral.get(BasicConstants.CALCIUM) != null) {
								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
										 + ((feedList.getFeedvolume() * noOfFeeds * nutrition.getCalcium()) / 100));
							} else {
								enteral.put(BasicConstants.CALCIUM,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getCalcium() / 100);
							}
							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getPhosphorus()) / 100));
							} else {
								enteral.put(BasicConstants.PHOSPHORUS,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getPhosphorus() / 100);
							}
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										+  ((feedList.getFeedvolume() * noOfFeeds * nutrition.getIron()) / 100));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getFeedvolume() * noOfFeeds * nutrition.getIron() / 100);
							}
	
	 					}
	 				}
	 			}
	 			
	
	 			if (!BasicUtils.isEmpty(feedList)) {
	 				for (RefNutritioncalculator nutrition : nutritionList) {
	
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN001")){
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	 					
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE07") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN010")){
	 						
	 						
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	 					
	 					if(nutrition.getFeedtypeId().equalsIgnoreCase("TYPE28") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN006")){
	 						
	 						
	 						if(feedList.getCalsyrupTotal() != null){
	 							
	 							if (enteral.get(BasicConstants.VITAMINd) != null) {
	 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getVitamind())));
	 							} else {
	 								enteral.put(BasicConstants.VITAMINd,
	 										feedList.getCalsyrupTotal() * nutrition.getVitamind());
	 							}
	 							if (enteral.get(BasicConstants.CALCIUM) != null) {
	 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getCalcium())));
	 							} else {
	 								enteral.put(BasicConstants.CALCIUM,
	 										feedList.getCalsyrupTotal() * nutrition.getCalcium());
	 							}
	 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
	 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
	 										+  ((feedList.getCalsyrupTotal() * nutrition.getPhosphorus())));
	 							} else {
	 								enteral.put(BasicConstants.PHOSPHORUS,
	 										feedList.getCalsyrupTotal() * nutrition.getPhosphorus());
	 							}
	 						}
						}
	
						if(!BasicUtils.isEmpty(feedList.getVitamindTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19")){
							if (enteral.get(BasicConstants.VITAMINd) != null) {
								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
										+  ((feedList.getVitamindTotal() * nutrition.getVitamind())));
							} else {
								enteral.put(BasicConstants.VITAMINd,
										feedList.getVitamindTotal() * nutrition.getVitamind());
							}
						}
	
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN002")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE29") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN008")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE30") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN012")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
						
						if(!BasicUtils.isEmpty(feedList.getIronTotal()) && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE31") && !BasicUtils.isEmpty(feedList.getCalBrand()) && feedList.getCalBrand().equalsIgnoreCase("EN014")){
							if (enteral.get(BasicConstants.IRON) != null) {
								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
										 + ((feedList.getIronTotal() * nutrition.getIron())));
							} else {
								enteral.put(BasicConstants.IRON,
										feedList.getIronTotal() * nutrition.getIron());
							}
						}
	 				}
	 			}
	 			calculator.setEnteralIntake(enteral);
	 		}
 		
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.ENERGY) != null && calculator.getEnteralIntake().get(BasicConstants.ENERGY) >= 0 && energyNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.ENERGY) < energyNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.ENERGY, energyNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.ENERGY) == null && energyNurseIntake > 0) {
				calculator.getEnteralIntake().put(BasicConstants.ENERGY, energyNurseIntake);
 		}
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.PROTEIN) != null && calculator.getEnteralIntake().get(BasicConstants.PROTEIN) >= 0 && proteinNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.PROTEIN) < proteinNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.PROTEIN, proteinNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.PROTEIN) == null && proteinNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.PROTEIN, proteinNurseIntake);
		}
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) != null && calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) >= 0 && phosphorusNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) < phosphorusNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.PHOSPHORUS, phosphorusNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.PHOSPHORUS) == null && phosphorusNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.PHOSPHORUS, phosphorusNurseIntake);
		}
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.VITAMINa) != null && calculator.getEnteralIntake().get(BasicConstants.VITAMINa) >= 0 && vitaminaNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.VITAMINa) < vitaminaNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.VITAMINa, vitaminaNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.VITAMINa) == null && vitaminaNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.VITAMINa, vitaminaNurseIntake);
		}
 		
 		if(calculator.getEnteralIntake().get(BasicConstants.VITAMINd) != null && calculator.getEnteralIntake().get(BasicConstants.VITAMINd) >= 0 && vitamindNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.VITAMINd) < vitamindNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.VITAMINd, vitamindNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.VITAMINd) == null && vitamindNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.VITAMINd, vitamindNurseIntake);
		}

 		if(calculator.getEnteralIntake().get(BasicConstants.CALCIUM) != null && calculator.getEnteralIntake().get(BasicConstants.CALCIUM) >= 0 && calciumNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.CALCIUM) < calciumNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.CALCIUM, calciumNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.CALCIUM) == null && calciumNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.CALCIUM, calciumNurseIntake);
		}

 		if(calculator.getEnteralIntake().get(BasicConstants.IRON) != null && calculator.getEnteralIntake().get(BasicConstants.IRON) >= 0 && ironNurseIntake > 0) {
 			if(calculator.getEnteralIntake().get(BasicConstants.IRON) < ironNurseIntake) {
 				calculator.getEnteralIntake().put(BasicConstants.IRON, ironNurseIntake);
 			}
 		}else if(calculator.getEnteralIntake().get(BasicConstants.IRON) == null && ironNurseIntake > 0) {
			calculator.getEnteralIntake().put(BasicConstants.IRON, ironNurseIntake);
		}




 		
 		// parental....
 		if(type.equalsIgnoreCase("order")) {
	 		HashMap<String, Float> parental = new HashMap<String, Float>();
	
	
	 		if (!BasicUtils.isEmpty(feedList)) {
	 			BabyfeedDetail FeedParental = feedList;
	 			
	 			if((!BasicUtils.isEmpty(FeedParental.getGirvalue()) && Float.valueOf(FeedParental.getGirvalue()) > 0) || (!BasicUtils.isEmpty(FeedParental.getTotalparenteralvolume()) && FeedParental.getTotalparenteralvolume() > 0)) {
	 				calculator.getEnteralIntake().put(BasicConstants.IRON, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.CALCIUM, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.VITAMINd, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.VITAMINa, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.PHOSPHORUS, (float)0);
	 			}
	
	 			if (currentWeight != null && !currentWeight.isEmpty()) {
	 				Float workingWeight = Float.valueOf(currentWeight);
	 				Float energyParenteral = null;
	 				if (FeedParental.getAminoacidConc() != null) {
	 					energyParenteral = FeedParental.getAminoacidConc() * 4 * workingWeight;
	 				}
	 				if (FeedParental.getAminoacidConc() != null) {
	 					parental.put(BasicConstants.PROTEIN, FeedParental.getAminoacidConc() * workingWeight);
	 					stay = 1;
	 				}
	 				if (FeedParental.getLipidConc() != null) {
	 					parental.put(BasicConstants.FAT, FeedParental.getLipidConc() * workingWeight);
	 					if (energyParenteral != null)
	 						energyParenteral = energyParenteral  + FeedParental.getLipidConc() * 10 * workingWeight;
	 					else
	 						energyParenteral = FeedParental.getLipidConc() * 10 * workingWeight;
	 				}
	
	 				if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
	 					stay = 1;
	 					Float gir = Float.valueOf(FeedParental.getGirvalue());
	 					if(gir > 20) {
	 						if(!BasicUtils.isEmpty(FeedParental.getIsReadymadeSolutionGiven()) && !FeedParental.getIsReadymadeSolutionGiven() && !BasicUtils.isEmpty(FeedParental.getDextroseVolumemlperday()) && !BasicUtils.isEmpty(FeedParental.getCurrentDextroseConcentration())) {
	 							gir = (float) 0.007 * FeedParental.getDextroseVolumemlperday() * FeedParental.getCurrentDextroseConcentration();
	 						}else if(!BasicUtils.isEmpty(FeedParental.getIsReadymadeSolutionGiven()) && FeedParental.getIsReadymadeSolutionGiven() && !BasicUtils.isEmpty(FeedParental.getReadymadeFluidVolume())) {
	 							if(!BasicUtils.isEmpty(FeedParental.getReadymadeDextroseConcentration())) {
		 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * FeedParental.getReadymadeDextroseConcentration();
	 							}
		 						else if(!BasicUtils.isEmpty(FeedParental.getReadymadeFluidType())) {
	 								if(FeedParental.getReadymadeFluidType().contains("D10")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 10;
	 								}else if(FeedParental.getReadymadeFluidType().contains("D25")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 25;
	 								}else if(FeedParental.getReadymadeFluidType().contains("D50")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 50;
	 								}else  {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 5;
	 								}
	 							}
	 						}
	 					}
	 					if (energyParenteral != null)
	 						energyParenteral = Float.valueOf(energyParenteral  + gir * 4.9 * workingWeight  + "");
	 					else
	 						energyParenteral = Float.valueOf(gir * 4.9 * workingWeight  + "");
	 				}
	 				if(!BasicUtils.isEmpty(FeedParental.getBolusVolume()) && !BasicUtils.isEmpty(FeedParental.getBolusType()) && FeedParental.getBolusType().equalsIgnoreCase("10% dextrose")) {
	 					Float girBolus = (float) 0.007 * FeedParental.getBolusVolume() * 10;
	 					if (energyParenteral != null)
	 						energyParenteral = Float.valueOf(energyParenteral  + girBolus * 4.9 * workingWeight  + "");
	 					else
	 						energyParenteral = Float.valueOf(girBolus * 4.9 * workingWeight  + "");
	 				}
	 				parental.put(BasicConstants.ENERGY, energyParenteral);
	
	 				if (FeedParental.getCalciumVolume() != null && FeedParental.getCalciumVolume() != 0) {
	 					parental.put(BasicConstants.CALCIUM, FeedParental.getCalciumVolume() * 9 * workingWeight);
	 				}
	
	 				
	 				calculator.setParentalIntake(parental);
	 			}
	 		}
 		}
 		
 		
 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganIntake = new HashMap<String, Float>();
 				if(babyDetailList.get(0).getBirthweight() < 2500) {
 				
			
					eshphaganIntake.put("Energy", (float) 105*stay);
					eshphaganIntake.put("Protein", (float) 3.5*stay);
					eshphaganIntake.put("Fat",(float) 8*stay);
					eshphaganIntake.put("Vitamina", (float)1250*stay);
					eshphaganIntake.put("Vitamind", (float)350*stay);
					eshphaganIntake.put("Calcium", (float)210*stay);
					eshphaganIntake.put("Phosphorus", (float)100*stay);
					eshphaganIntake.put("Iron", (float)8*stay);

				}
				else
				{
			
					eshphaganIntake.put("Energy", (float) 105*stay);
					eshphaganIntake.put("Protein", (float) 2.5*stay);
					eshphaganIntake.put("Fat",(float) 8*stay);
					eshphaganIntake.put("Vitamina", (float)1250*stay);
					eshphaganIntake.put("Vitamind", (float)350*stay);
					eshphaganIntake.put("Calcium", (float)210*stay);
					eshphaganIntake.put("Phosphorus", (float)100*stay);
					eshphaganIntake.put("Iron", (float)8*stay);
				}

 			calculator.setEshphaganIntake(eshphaganIntake);
 		} 			

		
 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganParenteralIntake = new HashMap<String, Float>();
 			
 			if(babyDetailList.get(0).getBirthweight() < 2500) {
 				
				eshphaganParenteralIntake.put("Energy", (float) 105*stay);
				eshphaganParenteralIntake.put("Protein", (float) 3.5*stay);
				eshphaganParenteralIntake.put("Fat",(float) 8*stay);
				eshphaganParenteralIntake.put("Vitamina", (float)2333*stay);
				eshphaganParenteralIntake.put("Vitamind", (float)400*stay);
				eshphaganParenteralIntake.put("Calcium", (float)210*stay);
				eshphaganParenteralIntake.put("Phosphorus", (float)100*stay);
				eshphaganParenteralIntake.put("Iron", (float)8*stay);

			}else{
				
						
				eshphaganParenteralIntake.put("Energy", (float) 105*stay);
				eshphaganParenteralIntake.put("Protein", (float) 2.5*stay);
				eshphaganParenteralIntake.put("Fat",(float) 8*stay);
				eshphaganParenteralIntake.put("Vitamina", (float)2333*stay);
				eshphaganParenteralIntake.put("Vitamind", (float)400*stay);
				eshphaganParenteralIntake.put("Calcium", (float)210*stay);
				eshphaganParenteralIntake.put("Phosphorus", (float)100*stay);
				eshphaganParenteralIntake.put("Iron", (float)8*stay);
				
 			}

 			
 			calculator.setEshphaganParenteralIntake(eshphaganParenteralIntake);
      }

 		return calculator;
	}

	public CaclulatorDeficitPOJO getDeficitFeedCalculatorInput(String uhid, BabyfeedDetail feedList,
 			List<RefNutritioncalculator> nutritionList, String currentWeight, Timestamp fromDateOffsetTwentFourHour, Timestamp toDateOffsetTwentFourHour, String type) {

 		List<NursingIntakeOutput> nursingIntakeOutputList = inicuDoa.getListFromMappedObjQuery(
 				HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));

 		CaclulatorDeficitPOJO calculator = new CaclulatorDeficitPOJO();
 		String oralFeed = "select obj from OralfeedDetail obj where uhid = '"  + uhid  + "' and entrydatetime >= '"  + fromDateOffsetTwentFourHour  +
 				"' and entrydatetime <= '"  + toDateOffsetTwentFourHour  + "' order by entrydatetime desc, creationtime desc";
 		List<OralfeedDetail> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);

 		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);

 		if (!BasicUtils.isEmpty(oralFeedList)) {
 			HashMap<String, Float> enteral = new HashMap<String, Float>();
 			for (OralfeedDetail oral : oralFeedList) {
 				for (RefNutritioncalculator nutrition : nutritionList) {

 					if (oral.getFeedtypeId().equalsIgnoreCase(nutrition.getFeedtypeId())) {

 						if (oral.getTotalFeedVolume() != null) {
 							if (enteral.get(BasicConstants.ENERGY) != null) {
 								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
 										 + ((oral.getTotalFeedVolume() * nutrition.getEnergy()) / 100));
 							} else {
 								enteral.put(BasicConstants.ENERGY,
 										oral.getTotalFeedVolume() * nutrition.getEnergy() / 100);
 							}

 							if (enteral.get(BasicConstants.PROTEIN) != null) {
 								enteral.put(BasicConstants.PROTEIN, enteral.get(BasicConstants.PROTEIN)
 										+  ((oral.getTotalFeedVolume() * nutrition.getProtein()) / 100));
 							} else {
 								enteral.put(BasicConstants.PROTEIN,
 										oral.getTotalFeedVolume() * nutrition.getProtein() / 100);
 							}

 							if (enteral.get(BasicConstants.FAT) != null) {
 								enteral.put(BasicConstants.FAT, enteral.get(BasicConstants.FAT)
 										+  ((oral.getTotalFeedVolume() * nutrition.getFat()) / 100));
 							} else {
 								enteral.put(BasicConstants.FAT, oral.getTotalFeedVolume() * nutrition.getFat() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINa) != null) {
 								enteral.put(BasicConstants.VITAMINa, enteral.get(BasicConstants.VITAMINa)
 										+  ((oral.getTotalFeedVolume() * nutrition.getVitamina()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINa,
 										oral.getTotalFeedVolume() * nutrition.getVitamina() / 100);
 							}
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((oral.getTotalFeedVolume() * nutrition.getVitamind()) / 100));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										oral.getTotalFeedVolume() * nutrition.getVitamind() / 100);
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										 + ((oral.getTotalFeedVolume() * nutrition.getCalcium()) / 100));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										oral.getTotalFeedVolume() * nutrition.getCalcium() / 100);
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((oral.getTotalFeedVolume() * nutrition.getPhosphorus()) / 100));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										oral.getTotalFeedVolume() * nutrition.getPhosphorus() / 100);
 							}
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										+  ((oral.getTotalFeedVolume() * nutrition.getIron()) / 100));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										oral.getTotalFeedVolume() * nutrition.getIron() / 100);
 							}

 						}
 					}
 				}
 			}

 			if (!BasicUtils.isEmpty(nursingIntakeOutputList)) {
 				for (NursingIntakeOutput addtive : nursingIntakeOutputList) {
 					for (RefNutritioncalculator nutrition : nutritionList) {

 						if(addtive.getCalciumVolume() != null && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE20")){
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((addtive.getCalciumVolume() * nutrition.getVitamind())));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										addtive.getCalciumVolume() * nutrition.getVitamind());
 							}
 							if (enteral.get(BasicConstants.CALCIUM) != null) {
 								enteral.put(BasicConstants.CALCIUM, enteral.get(BasicConstants.CALCIUM)
 										+  ((addtive.getCalciumVolume() * nutrition.getCalcium())));
 							} else {
 								enteral.put(BasicConstants.CALCIUM,
 										addtive.getCalciumVolume() * nutrition.getCalcium());
 							}
 							if (enteral.get(BasicConstants.PHOSPHORUS) != null) {
 								enteral.put(BasicConstants.PHOSPHORUS, enteral.get(BasicConstants.PHOSPHORUS)
 										+  ((addtive.getCalciumVolume() * nutrition.getPhosphorus())));
 							} else {
 								enteral.put(BasicConstants.PHOSPHORUS,
 										addtive.getCalciumVolume() * nutrition.getPhosphorus());
 							}
 						}

 						if(addtive.getVitamindVolume() != null && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE19")){
 							if (enteral.get(BasicConstants.VITAMINd) != null) {
 								enteral.put(BasicConstants.VITAMINd, enteral.get(BasicConstants.VITAMINd)
 										+  ((addtive.getVitamindVolume() * nutrition.getVitamind())));
 							} else {
 								enteral.put(BasicConstants.VITAMINd,
 										addtive.getVitamindVolume() * nutrition.getVitamind());
 							}
 						}

 						if(addtive.getVitamindVolume() != null && nutrition.getFeedtypeId().equalsIgnoreCase("TYPE09")){
 							if (enteral.get(BasicConstants.IRON) != null) {
 								enteral.put(BasicConstants.IRON, enteral.get(BasicConstants.IRON)
 										 + ((addtive.getVitamindVolume() * nutrition.getPhosphorus())));
 							} else {
 								enteral.put(BasicConstants.IRON,
 										addtive.getVitamindVolume() * nutrition.getPhosphorus());
 							}
 						}
 					}
 				}
 			}

 			calculator.setEnteralIntake(enteral);
 		}

 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganIntake = new HashMap<String, Float>();

 					if(babyDetailList.get(0).getBirthweight() < 1000) {

 						eshphaganIntake.put("Energy", (float) 130);
 						eshphaganIntake.put("Protein", (float) 3.8);
 						eshphaganIntake.put("Fat",(float) 8);
 						eshphaganIntake.put("Vitamina", (float)700);
 						eshphaganIntake.put("Vitamind", (float)800);
 						eshphaganIntake.put("Calcium", (float)2.5);
 						eshphaganIntake.put("Phosphorus", (float)90);
 						eshphaganIntake.put("Iron", (float)2);

 						}

 					else
 					{
 						eshphaganIntake.put("Energy", (float) 110);
 						eshphaganIntake.put("Protein", (float) 3.4);
 						eshphaganIntake.put("Fat",(float) 8);
 						eshphaganIntake.put("Vitamina", (float)700);
 						eshphaganIntake.put("Vitamind", (float)800);
 						eshphaganIntake.put("Calcium", (float)2.5);
 						eshphaganIntake.put("Phosphorus", (float)90);
 						eshphaganIntake.put("Iron", (float)2);
 					}

 			calculator.setEshphaganIntake(eshphaganIntake);
 		}

 		if (!BasicUtils.isEmpty(babyDetailList)) {
 			HashMap<String, Float> eshphaganParenteralIntake = new HashMap<String, Float>();

 			if(babyDetailList.get(0).getBirthweight() < 1000) { //ELBW

 				eshphaganParenteralIntake.put("Energy", (float) 75);
 				eshphaganParenteralIntake.put("Protein", (float) 3.5);
 				eshphaganParenteralIntake.put("Fat",(float) 4);
 				eshphaganParenteralIntake.put("Vitamina", (float)700);
 				eshphaganParenteralIntake.put("Vitamind", (float)40);
 				eshphaganParenteralIntake.put("Calcium", (float)1.5);
 				eshphaganParenteralIntake.put("Phosphorus", (float)80);
 				eshphaganParenteralIntake.put("Iron", (float)0);



 				}

 			else //VLBW
 			{
 				eshphaganParenteralIntake.put("Energy", (float) 60);
 				eshphaganParenteralIntake.put("Protein", (float) 3.5);
 				eshphaganParenteralIntake.put("Fat",(float) 4);
 				eshphaganParenteralIntake.put("Vitamina", (float)700);
 				eshphaganParenteralIntake.put("Vitamind", (float)40);
 				eshphaganParenteralIntake.put("Calcium", (float)1.5);
 				eshphaganParenteralIntake.put("Phosphorus", (float)80);
 				eshphaganParenteralIntake.put("Iron", (float)0);
 			}

 			calculator.setEshphaganParenteralIntake(eshphaganParenteralIntake);
 		}

 		// parental....
 		if(type.equalsIgnoreCase("order")) {
	 		HashMap<String, Float> parental = new HashMap<String, Float>();
	
	
	 		if (!BasicUtils.isEmpty(feedList)) {
	 			BabyfeedDetail FeedParental = feedList;
	
	 			if (currentWeight != null && !currentWeight.isEmpty()) {
	 				Float workingWeight = Float.valueOf(currentWeight);
	 				Float energyParenteral = null;
	 				if (FeedParental.getAminoacidConc() != null) {
	 					energyParenteral = FeedParental.getAminoacidConc() * 4 * workingWeight;
	 				}
	 				if (FeedParental.getAminoacidConc() != null)
	 					parental.put(BasicConstants.PROTEIN, FeedParental.getAminoacidConc() * workingWeight);
	 				if (FeedParental.getLipidConc() != null) {
	 					parental.put(BasicConstants.FAT, FeedParental.getLipidConc() * workingWeight);
	 					if (energyParenteral != null)
	 						energyParenteral = energyParenteral  + FeedParental.getLipidConc() * 10 * workingWeight;
	 					else
	 						energyParenteral = FeedParental.getLipidConc() * 10 * workingWeight;
	 				}
	
	 				if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
	 					Float gir = Float.valueOf(FeedParental.getGirvalue());
	 					if (energyParenteral != null)
	 						energyParenteral = Float.valueOf(energyParenteral  + gir * 4.9 * workingWeight  + "");
	 					else
	 						energyParenteral = Float.valueOf(gir * 4.9 * workingWeight  + "");
	 				}
	 				parental.put(BasicConstants.ENERGY, energyParenteral);
	
	 				if (FeedParental.getCalciumVolume() != null && FeedParental.getCalciumVolume() != 0) {
	 					parental.put(BasicConstants.CALCIUM, FeedParental.getCalciumVolume() * 9 * workingWeight);
	 				}
	
	 				calculator.setParentalIntake(parental);
	 			}
	 		}
 		}

 		return calculator;
 	}


	@Override
	public void run(){
		XSSFWorkbook workbook = null;
		try {


	        XSSFWorkbook finalWorkbook = new XSSFWorkbook();
	        XSSFSheet sheet = finalWorkbook.createSheet("Energy");
	        XSSFSheet sheet2 = finalWorkbook.createSheet("Protein");
	        XSSFSheet sheet4 = finalWorkbook.createSheet("Vitamina");
	        XSSFSheet sheet5 = finalWorkbook.createSheet("Vitamind");
	        XSSFSheet sheet6 = finalWorkbook.createSheet("Calcium");
	        XSSFSheet sheet7 = finalWorkbook.createSheet("Phosphorus");
	        XSSFSheet sheet8 = finalWorkbook.createSheet("Iron");


			int rowEnergyCount = 0;
	    	Row rowMedEnergy = sheet.createRow(0);
	    	++rowEnergyCount;
			int columnCount = 0;
			Cell cell = rowMedEnergy.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
			
			
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedEnergy.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedEnergy.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			
		
			Row rowMed1 = sheet.createRow(1);
	    	for(int i = 1; i < 200; i++) {

	    	

	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedEnergy.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
				
				
				
			}

	    	int rowProteinCount = 0;
	    	Row rowMedProtein = sheet2.createRow(0);
	    	++rowProteinCount;
			columnCount = 0;
			cell = rowMedProtein.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
		
			
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedProtein.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedProtein.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			rowMed1 = sheet2.createRow(1);
	    	for(int i = 1; i < 200; i++) {

	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedProtein.createCell(columnCount);
	            cell1.setCellValue("Weight");
			}


	    
	    	int rowVitaminaCount = 0;
	    	Row rowMedVitamina = sheet4.createRow(0);
	    	++rowVitaminaCount;
	    	columnCount = 0;
	    	cell = rowMedVitamina.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
			
		
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedVitamina.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedVitamina.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			
			rowMed1 = sheet4.createRow(1);

	    	for(int i = 1; i < 200; i++) {
	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamina.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
			}

	    	int rowVitamindCount = 0;
	    	Row rowMedVitamind = sheet5.createRow(0);
	    	++rowVitamindCount;
			columnCount = 0;
			cell = rowMedVitamind.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
			
			
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedVitamind.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedVitamind.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			rowMed1 = sheet5.createRow(1);
	    	for(int i = 1; i < 200; i++) {
	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedVitamind.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
			
			}

	    	int rowCalciumCount = 0;
	    	Row rowMedCalcium = sheet6.createRow(0);
	    	++rowCalciumCount;
			columnCount = 0;
			cell = rowMedCalcium.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
			
		
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedCalcium.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedCalcium.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			rowMed1 = sheet6.createRow(1);
	    	for(int i = 1; i < 200; i++) {
	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedCalcium.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
		
			}

	    	int rowPhosphorusCount = 0;
	    	Row rowMedPhosphorus = sheet7.createRow(0);
	    	++rowPhosphorusCount;
			columnCount = 0;
			cell = rowMedPhosphorus.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
		
			
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedPhosphorus.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedPhosphorus.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			
			rowMed1 = sheet7.createRow(1);

		   	for(int i = 1; i < 200; i++) {
		   		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedPhosphorus.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
		
			}

	    	int rowIronCount = 0;
	    	Row rowMedIron = sheet8.createRow(0);
	    	++rowIronCount;
	    	columnCount = 0;
	    	cell = rowMedIron.createCell(columnCount);
			cell.setCellValue("UHID");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("LOS");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Gestation.Weeks");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Gestation.Days");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Birth.Weight");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Date.of.Birth");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Gender");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Antenatal.steroids");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Mode.of.delivery");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Single.Multiple");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Inborn.Outborn");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("APGAR.ONE");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("APGAR.FIVE");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Hypertension");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Gestational.Hypertension");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Diabetes");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Gestational.Diabetes");
			
			
		
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("CHRONIC.KIDNEY.DISEASE");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Hypothyroidism");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Hyperthyroidism");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("None.Disease");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Fever");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("UTI");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("History.Of.Infections");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("None.Infection");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("PROM");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("PPROM");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Prematurity");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Chorioamniotis");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Oligohydraminos");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Polyhydraminos");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("None.Other");
			cell = rowMedIron.createCell(++columnCount);
			
			cell.setCellValue("RDS");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("RDS_TTNB");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("RDS_MAS");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Jaundice");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Sepsis");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Asphyxia");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Pneumothorax");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("PPHN");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("isInvasive");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("RESUSCITAION");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Initial.Steps");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("O2");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("PPV");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Chest.Compression");
			
	
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Umbilical.Doppler");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Abnormal.Umbilical.Doppler");
			cell = rowMedIron.createCell(++columnCount);
			cell.setCellValue("Phototherapy.Duration");
			rowMed1 = sheet8.createRow(1);

	    	for(int i = 1; i < 200; i++) {
	    		cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            Cell cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Given");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Recommended");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Recommended.Considered");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Diff");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Result");
	            
	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("EN.PN");

	            cell = rowMed1.createCell(++columnCount);
	            cell.setCellValue(i);

	            cell1 = rowMedIron.createCell(columnCount);
	            cell1.setCellValue("Weight");
				
			}
	    	
	    	
	    	
	    	
	    	String babyQuery = "";
			//Site1
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
			
			List<Float> energyListDeviationcat1 = new ArrayList<Float>();
			List<Float> proteinListDeviationcat1 = new ArrayList<Float>();
			List<Float> energyListDeviationcat2 = new ArrayList<Float>();
			List<Float> proteinListDeviationcat2 = new ArrayList<Float>();
			List<Float> energyListDeviationcat3 = new ArrayList<Float>();
			List<Float> proteinListDeviationcat3 = new ArrayList<Float>();
			List<Float> energyListDeviationcat4 = new ArrayList<Float>();
			List<Float> proteinListDeviationcat4 = new ArrayList<Float>();
			
			List<Float> gestationcat1 = new ArrayList<Float>();
			List<Float> gestationcat2 = new ArrayList<Float>();
			List<Float> gestationcat3 = new ArrayList<Float>();
			List<Float> gestationcat4 = new ArrayList<Float>();


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
					mod = "LSCS";
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
					
					String babyType = "";
					if(!BasicUtils.isEmpty(obj[10])) {
						babyType = obj[10].toString();
					}
	
				
					

					Timestamp nextTime = new Timestamp(creationTime.getTime());
					boolean isTodayEntry = false;
					if(creationTime.getHours() >= 8) {
						nextTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
						nextTime.setHours(8);
						nextTime.setMinutes(0);
					}else {
						isTodayEntry = true;
						nextTime = new Timestamp(creationTime.getTime());
						nextTime.setHours(8);
						nextTime.setMinutes(0);
					}

					columnCount = 0;
					HashMap<Integer, Integer> energyMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> proteinMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> vitaminaMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> vitamindMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> calciumMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> phosphorusMap = new HashMap<Integer, Integer>();
					HashMap<Integer, Integer> ironMap = new HashMap<Integer, Integer>();



					for(int i = columnCount; i < 200; i++) {
						energyMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						proteinMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						vitaminaMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						vitamindMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						calciumMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						phosphorusMap.put(i, -1);
					}

					for(int i = columnCount; i < 200; i++) {
						ironMap.put(i, -1);
					}
					columnCount = 0;
					double los = Math.ceil(((dod.getTime() - creationTime.getTime()) / (1000 * 60 * 60 * 24))) + 1;
					boolean isLOSNegative = false;
					if(los < 1) {
						isLOSNegative = true;
					}

					rowEnergyCount = rowEnergyCount + 1;
					rowMedEnergy = sheet.createRow(rowEnergyCount);
					
					
					cell = rowMedEnergy.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedEnergy.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedEnergy.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
		
					
				
				
				
					
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(isInvasive);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedEnergy.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					rowMedProtein = sheet2.createRow(++rowProteinCount);
					columnCount = 0;

					cell = rowMedProtein.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedProtein.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedProtein.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
					
					
				
				
				
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(isInvasive);

					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedProtein.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					
				
					rowMedVitamina = sheet4.createRow(++rowVitaminaCount);
					columnCount = 0;

					cell = rowMedVitamina.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedVitamina.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedVitamina.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
					
					
				
				
				
					
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(isInvasive);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedVitamina.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					rowMedVitamind = sheet5.createRow(++rowVitamindCount);
					columnCount = 0;

					cell = rowMedVitamind.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedVitamind.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedVitamind.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
				
					
				
				
				
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(isInvasive);

					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedVitamind.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					rowMedCalcium = sheet6.createRow(++rowCalciumCount);
					columnCount = 0;

					cell = rowMedCalcium.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedCalcium.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedCalcium.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
					
					
				
				
				
				
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(isInvasive);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedCalcium.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					rowMedPhosphorus = sheet7.createRow(++rowPhosphorusCount);
					columnCount = 0;

					cell = rowMedPhosphorus.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedPhosphorus.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedPhosphorus.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
					
					
				
				
				
					
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(isInvasive);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedPhosphorus.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					rowMedIron = sheet8.createRow(++rowIronCount);
					columnCount = 0;

					cell = rowMedIron.createCell(columnCount);
					cell.setCellValue(obj[0].toString());
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(los);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(gestationWeek);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(gestationDays);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(obj[7].toString());
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(obj[2].toString());
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(obj[8].toString());
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isSteroidGiven);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(mod);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(babyType);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(obj[9].toString());
					cell = rowMedIron.createCell(++columnCount);
					if (apgarOne == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarOne + "");
					
					cell = rowMedIron.createCell(++columnCount);
					if (apgarFive == -1)
						cell.setCellValue("");
					else
						cell.setCellValue(apgarFive + "");
					
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(hypertension);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(gestationalHypertension);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(diabetes);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(gestationalDiabetes);
					
					
				
				
				
					
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isChronicDisease);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(hypothyroidism);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(hyperthyroidism);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(noneDisease);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(fever);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(uti);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(history_of_infections);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(noneInfection);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(prom);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(pprom);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(prematurity);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(chorioamniotis);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(oligohydraminos);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(polyhydraminos);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(noneOther);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isRds);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isRdsTTNB);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isRdsMAS);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isJaundice);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isProbableSepsis);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isAsphyxia);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isPneumothorax);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isPPHN);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(isInvasive);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(resuscitation);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(resuscitationInitialSteps);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(resuscitationO2);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(resuscitationPPV);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(resuscitationChestCompression);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(umblicalDoppler);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(umblicalDopplerType);
					cell = rowMedIron.createCell(++columnCount);
					cell.setCellValue(phototherapyDays);
					boolean firstLoop = false;
					boolean isNutritionOrdered = false;
				
					BabyfeedDetail lastFeed = new BabyfeedDetail();
					HashMap<String, Float> lastDiff = new HashMap<String, Float>();
					CaclulatorDeficitPOJO cuurentDeficitLastLatest = new CaclulatorDeficitPOJO();

					int counter = 1;
					int lengthOfStay = 0;
					boolean gotFirstOrder = false;
					List<Integer> missedLos = new ArrayList<Integer>();
					if(obj[0].toString().equalsIgnoreCase("190500245")) {
						int wq = 1;
					}
					while(creationTime.getTime() < dod.getTime() && (lengthOfStay < los)) {
						lengthOfStay++;
						CaclulatorDeficitPOJO cuurentDeficitLast = new CaclulatorDeficitPOJO();
						Timestamp startingTime = new Timestamp(creationTime.getTime());
						Timestamp endingTime = new Timestamp(nextTime.getTime());
						List<BabyfeedDetail> feedListObj = (List<BabyfeedDetail>) notesDoa.getListFromMappedObjNativeQuery(
								HqlSqlQueryConstants.getBabyfeedDetailList(obj[0].toString(), startingTime, endingTime));
						
						boolean isPNAndBreastMilkGiven = false;
						if(!BasicUtils.isEmpty(feedListObj)) {
							BabyfeedDetail feed = feedListObj.get(0);
							if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03") && !BasicUtils.isEmpty(feed.getGirvalue())) {
								isPNAndBreastMilkGiven = true;
							}
							if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
								isPNAndBreastMilkGiven = true;
							}
						}
						if(!isPNAndBreastMilkGiven) {

							if(!BasicUtils.isEmpty(feedListObj)) {
								BabyfeedDetail feed = feedListObj.get(0);
								gotFirstOrder = true;
								String nutritionCalculator = "select obj from RefNutritioncalculator obj";
								List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
								
	//									cuurentDeficitLast = getDeficitFeedCalculatorInput(obj[0].toString(), feed, nutritionList, feed.getWorkingWeight() +"", creationTime,nextTime,"order");
								cuurentDeficitLast = getDeficitFeedCalculatorOrder(obj[0].toString(), feed, nutritionList, feed.getWorkingWeight() +"", creationTime,nextTime,"order",gestationWeek,lengthOfStay);
								if(!BasicUtils.isEmpty(feed.getEntryDateTime()))
									lastDiff = computeError(feed.getEntryDateTime(),cuurentDeficitLast,feed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay, creationTime,nextTime,missedLos);
								else
									lastDiff = computeError(feed.getCreationtime(),cuurentDeficitLast,feed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay, creationTime,nextTime,missedLos);	
								lastFeed = feed;
								cuurentDeficitLastLatest = cuurentDeficitLast;
								if(!BasicUtils.isEmpty(lastDiff.get("Energy"))) {
									isNutritionOrdered = true;
									missedLos = new ArrayList<Integer>();
								}else {
									missedLos.add(lengthOfStay);
								}
	
								counter = 1;
	
								
							}
							else if(isNutritionOrdered){
								String nutritionCalculator = "select obj from RefNutritioncalculator obj";
								List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
								CaclulatorDeficitPOJO cuurentDeficitLastNew = getDeficitFeedCalculatorOrder(obj[0].toString(), lastFeed, nutritionList, lastFeed.getWorkingWeight() +"", creationTime,nextTime,"order",gestationWeek,lengthOfStay);
	//							cuurentDeficitLastNew.setEshphaganIntake(cuurentDeficitLastLatest.getEshphaganIntake());
	//							cuurentDeficitLastNew.setEshphaganParenteralIntake(cuurentDeficitLastLatest.getEshphaganParenteralIntake());
	
								if(!BasicUtils.isEmpty(lastFeed.getEntryDateTime())) {
									Timestamp newTime = new Timestamp(lastFeed.getEntryDateTime().getTime() + (counter * 1440 * 60 * 1000));
									computeErrorLast(newTime,cuurentDeficitLastNew,lastFeed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay,lastDiff);
								}
								else {
									Timestamp newTime = new Timestamp(lastFeed.getCreationtime().getTime() + (counter * 1440 * 60 * 1000));
									computeErrorLast(newTime,cuurentDeficitLastNew,lastFeed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay,lastDiff);	
								}
								counter++;
							}else {
								missedLos.add(lengthOfStay);
	
							}
						}
						if(!firstLoop) {
							creationTime.setHours(8);
							creationTime.setMinutes(0);
							nextTime.setHours(8);
							nextTime.setMinutes(0);
						}
						if(!firstLoop) {
							if(isTodayEntry)
								creationTime = new Timestamp(creationTime.getTime());
							else
								creationTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
						}else {
							creationTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
						}
						firstLoop = true;
						nextTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
					}
					if(creationTime.getTime() < dod.getTime() && (lengthOfStay >= los)) {
						CaclulatorDeficitPOJO cuurentDeficitLast = new CaclulatorDeficitPOJO();
						Timestamp startingTime = new Timestamp(creationTime.getTime() - 9000000);
						Timestamp endingTime = new Timestamp(nextTime.getTime() - 9000000);
						List<BabyfeedDetail> feedListObj = (List<BabyfeedDetail>) notesDoa.getListFromMappedObjNativeQuery(
								HqlSqlQueryConstants.getBabyfeedDetailList(obj[0].toString(), startingTime, endingTime));
						boolean isPNAndBreastMilkGiven = false;
						if(!BasicUtils.isEmpty(feedListObj)) {
							BabyfeedDetail feed = feedListObj.get(0);
							if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03") && !BasicUtils.isEmpty(feed.getGirvalue())) {
								isPNAndBreastMilkGiven = true;
							}
							if(!BasicUtils.isEmpty(feed) && !BasicUtils.isEmpty(feed.getFeedmethod()) && feed.getFeedmethod().contains("METHOD03")) {
								isPNAndBreastMilkGiven = true;
							}
						}
						if(!BasicUtils.isEmpty(feedListObj) && !isPNAndBreastMilkGiven) {
							for(BabyfeedDetail feed : feedListObj) {
								gotFirstOrder = true;
								String nutritionCalculator = "select obj from RefNutritioncalculator obj";
 								List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
								
//									cuurentDeficitLast = getDeficitFeedCalculatorInput(obj[0].toString(), feed, nutritionList, feed.getWorkingWeight() +"", creationTime,nextTime,"order");
								cuurentDeficitLast = getDeficitFeedCalculatorOrder(obj[0].toString(), feed, nutritionList, feed.getWorkingWeight() +"", creationTime,nextTime,"order",gestationWeek,lengthOfStay);
								if(!BasicUtils.isEmpty(feed.getEntryDateTime()))
									lastDiff = computeError(feed.getEntryDateTime(),cuurentDeficitLast,feed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay, creationTime,nextTime,missedLos);
								else
									lastDiff = computeError(feed.getCreationtime(),cuurentDeficitLast,feed,obj[0].toString(),dateofadmission,gestationWeek,gestationDays,cell,rowMedEnergy,rowMedProtein,  rowMedVitamina,  rowMedVitamind,  rowMedPhosphorus,  rowMedIron,  rowMedCalcium,lengthOfStay, creationTime,nextTime,missedLos);	
								lastFeed = feed;
								cuurentDeficitLastLatest = cuurentDeficitLast;
								isNutritionOrdered = true;
								counter = 1;
								missedLos = new ArrayList<Integer>();

							}
						}
					}
					
					if(isLOSNegative == false) {
					
						//Gestation
						double gestDays =  ((double)gestationDays/7);
						float gesweeks = (float) gestationWeek;
						float gestation_value = (float)(gesweeks + gestDays);
						
						String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile_value = " + gestation_value + "  where uhid = '" + obj[0].toString()  +"';";
				   	    System.out.println(updateType);
				   	    settingDao.executeInsertQuery(updateType);
				   	    
				   	    if(gestation_value <= 32)
				   	    	gestationcat1.add(gestation_value);
				   	    else if(gestation_value > 32 && gestation_value <= 34)
				   	    	gestationcat2.add(gestation_value);
				   	    else if(gestation_value > 34 && gestation_value <= 37)
				   	    	gestationcat3.add(gestation_value);
				   	    else if(gestation_value > 37)
				   	    	gestationcat4.add(gestation_value);
						
				   	
				   	    //Energy
						Float diff = 0f;
						for(int j = 51; j < 50 + los*7; j=j+7) {
							if(!BasicUtils.isEmpty(sheet.getRow(rowEnergyCount).getCell(j+1))) {
								cell = sheet.getRow(rowEnergyCount).getCell(j+1);
								float localDiff = (float) cell.getNumericCellValue();
								if(localDiff < 0) {
									diff += (localDiff * localDiff);
								}
							}
						}
						diff = (float) Math.sqrt(diff);
						
						
						updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation_value = " + diff + "  where uhid = '" + obj[0].toString()  +"';";
				   	    System.out.println(updateType);
				   	    settingDao.executeInsertQuery(updateType);
				   	   
				   	    if(gestation_value <= 32)
				   	    	energyListDeviationcat1.add(diff);
				   	    else if(gestation_value > 32 && gestation_value <= 34)
				   	    	energyListDeviationcat2.add(diff);
				   	    else if(gestation_value > 34 && gestation_value <= 37)
				   	    	energyListDeviationcat3.add(diff);
				   	    else if(gestation_value > 37)
				   	    	energyListDeviationcat4.add(diff);
						
						
				   	    //Protein
						diff = 0f;
						for(int j = 51; j < 50 + los*7; j=j+7) {
							if(!BasicUtils.isEmpty(sheet.getRow(rowProteinCount).getCell(j+1))) {
								cell = sheet.getRow(rowProteinCount).getCell(j+1);
								float localDiff = (float) cell.getNumericCellValue();
								if(localDiff < 0) {
									diff += (localDiff * localDiff);
								}
							}
						}
						diff = (float) Math.sqrt(diff);
						
						updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation_value = " + diff + "  where uhid = '" + obj[0].toString()  +"';";
				   	    System.out.println(updateType);
				   	    settingDao.executeInsertQuery(updateType);
						
				   	    if(gestation_value <= 32)
				   	    	proteinListDeviationcat1.add(diff);
				   	    else if(gestation_value > 32 && gestation_value <= 34)
				   	    	proteinListDeviationcat2.add(diff);
				   	    else if(gestation_value > 34 && gestation_value <= 37)
				   	    	proteinListDeviationcat3.add(diff);
				   	    else if(gestation_value > 37)
				   	    	proteinListDeviationcat4.add(diff);
						
				   	    
				   	    
				   	    //Skipping those uhids in excel where admission status is true 
						String babyQueryLimit = "select obj from BabyDetail obj where uhid= '" + obj[0].toString()  + "'";
						List<BabyDetail> babyListLimit = prescriptionDao.getListFromMappedObjNativeQuery(babyQueryLimit);
						if(!BasicUtils.isEmpty(babyListLimit)) {
							if(babyListLimit.get(0).getAdmissionstatus() == true) {
								--rowProteinCount;
								--rowEnergyCount;
								--rowVitaminaCount;
								--rowVitamindCount;
								--rowCalciumCount;
								--rowPhosphorusCount;
								--rowIronCount;
							}
						}
					}else {
						--rowProteinCount;
						--rowEnergyCount;
						--rowVitaminaCount;
						--rowVitamindCount;
						--rowCalciumCount;
						--rowPhosphorusCount;
						--rowIronCount;
					}

				}
			}	
			
			int quartileType = 3;
			float length = energyListDeviationcat1.size() + 1;
			Collections.sort(energyListDeviationcat1);  
			float newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            int newArraySize1 = (int) (newArraySize);
            float quartileEnergycat1 = (energyListDeviationcat1.get(newArraySize1) + energyListDeviationcat1.get(newArraySize1+1)) / 2;
            
            length = energyListDeviationcat2.size() + 1;
			Collections.sort(energyListDeviationcat2);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileEnergycat2 = (energyListDeviationcat2.get(newArraySize1) + energyListDeviationcat2.get(newArraySize1+1)) / 2;
            
            length = energyListDeviationcat3.size() + 1;
			Collections.sort(energyListDeviationcat3);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileEnergycat3 = (energyListDeviationcat3.get(newArraySize1) + energyListDeviationcat3.get(newArraySize1+1)) / 2;
            
            length = energyListDeviationcat4.size() + 1;
			Collections.sort(energyListDeviationcat4);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileEnergycat4 = (energyListDeviationcat4.get(newArraySize1) + energyListDeviationcat4.get(newArraySize1+1)) / 2;
            
            length = proteinListDeviationcat1.size() + 1;
			Collections.sort(proteinListDeviationcat1);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileProteincat1 = (proteinListDeviationcat1.get(newArraySize1) + proteinListDeviationcat1.get(newArraySize1+1)) / 2;
        
            length = proteinListDeviationcat2.size() + 1;
			Collections.sort(proteinListDeviationcat2);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileProteincat2 = (proteinListDeviationcat2.get(newArraySize1) + proteinListDeviationcat2.get(newArraySize1+1)) / 2;
            
            length = proteinListDeviationcat3.size() + 1;
			Collections.sort(proteinListDeviationcat3);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileProteincat3 = (proteinListDeviationcat3.get(newArraySize1) + proteinListDeviationcat3.get(newArraySize1+1)) / 2;
            
            length = proteinListDeviationcat4.size() + 1;
			Collections.sort(proteinListDeviationcat4);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartileProteincat4 = (proteinListDeviationcat4.get(newArraySize1) + proteinListDeviationcat4.get(newArraySize1+1)) / 2;
            
            length = gestationcat1.size() + 1;
			Collections.sort(gestationcat1);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartilegestationcat1 = (gestationcat1.get(newArraySize1) + gestationcat1.get(newArraySize1+1)) / 2;
        
            length = gestationcat2.size() + 1;
			Collections.sort(gestationcat2);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartilegestationcat2 = (gestationcat2.get(newArraySize1) + gestationcat2.get(newArraySize1+1)) / 2;
            
            length = gestationcat3.size() + 1;
			Collections.sort(gestationcat3);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartilegestationcat3 = (gestationcat3.get(newArraySize1) + gestationcat3.get(newArraySize1+1)) / 2;
            
            length = gestationcat4.size() + 1;
			Collections.sort(gestationcat4);  
			newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            newArraySize1 = (int) (newArraySize);
            float quartilegestationcat4 = (gestationcat4.get(newArraySize1) + gestationcat4.get(newArraySize1+1)) / 2;
            
            
            String babyQueryLimit = "select obj from BabyDetail obj where gestation_quartile_value is not null";
			List<BabyDetail> babyListLimit = prescriptionDao.getListFromMappedObjNativeQuery(babyQueryLimit);
			if(!BasicUtils.isEmpty(babyListLimit)) {
				for(BabyDetail baby : babyListLimit) {
					
					float gestation_value = baby.getGestation_quartile_value();
					
					if(gestation_value <= 32) {
						if(baby.getEnergy_deviation_value() >= quartileEnergycat1) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getProtein_deviation_value() >= quartileProteincat1) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getGestation_quartile_value() >= quartilegestationcat1) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
					}
			   	    else if(gestation_value > 32 && gestation_value <= 34) {
			   	    	if(baby.getEnergy_deviation_value() >= quartileEnergycat2) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getProtein_deviation_value() >= quartileProteincat2) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getGestation_quartile_value() >= quartilegestationcat2) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
			   	    }
			   	    else if(gestation_value > 34 && gestation_value <= 37) {
			   	    	if(baby.getEnergy_deviation_value() >= quartileEnergycat3) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getProtein_deviation_value() >= quartileProteincat3) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getGestation_quartile_value() >= quartilegestationcat3) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
			   	    }
			   	    else if(gestation_value > 37) {
			   	    	if(baby.getEnergy_deviation_value() >= quartileEnergycat4) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set energy_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getProtein_deviation_value() >= quartileProteincat4) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set protein_deviation = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
						
						if(baby.getGestation_quartile_value() >= quartilegestationcat4) {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = true  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}else {
							String updateType = "update " + BasicConstants.SCHEMA_NAME + ".baby_detail set gestation_quartile = false  where uhid = '" + baby.getUhid() +"';";
					   	    System.out.println(updateType);
					   	    settingDao.executeInsertQuery(updateType);
						}
			   	    }
				}
			}
        
			
            
			
			
			int counterMinLOS = 0;
			for(int i = 2; i< sheet.getLastRowNum() + 1; i++) {
				
				
				cell = sheet2.getRow(i).getCell(1);
				double gestation = cell.getNumericCellValue();
				int limitLOS = 0;
				if(gestation < 32) {
					limitLOS = 10;
				}else if(gestation < 34) {
					limitLOS = 2;
				}else if(gestation < 37) {
					limitLOS = 2;
				}else {
					limitLOS = 2;					
				}
				
				System.out.println("next" + i);
				cell = sheet.getRow(i).getCell(1);
			
				double los = cell.getNumericCellValue();
				System.out.println("los" + los);
				double counterDeviation = 0;
				double recommended = 0;
				double diff = 0;
				double vectorDiff = 0;
				counterMinLOS = 0;
				for(int j = 51; j < 50 + los*7; j=j+7) {
					counterMinLOS++;
					if(!BasicUtils.isEmpty(sheet.getRow(i).getCell(j)) && counterMinLOS <= limitLOS) {
						if(!BasicUtils.isEmpty(sheet.getRow(i).getCell(j+2))) {
							cell = sheet.getRow(i).getCell(j+2);
							String result = cell.getStringCellValue();
							if(result.equalsIgnoreCase("Deviation")) {
								cell = sheet.getRow(i).getCell(j);
								recommended += cell.getNumericCellValue();
								cell = sheet.getRow(i).getCell(j+1);
								double localDiff = cell.getNumericCellValue();
								if(localDiff < 0) {
									counterDeviation++;
									vectorDiff += localDiff;
									diff += (localDiff * localDiff);
								}
							}
						}
					}
				}
				if(recommended > 0) {
					double ratio = Math.sqrt(diff/counterDeviation) / 105;
					double vector = vectorDiff / 105;
					
					Row row1 = sheet.getRow(i);
					Cell cell1 = row1.createCell(1418);
					cell1.setCellValue(ratio);
					
					cell1 = row1.createCell(1419);
					cell1.setCellValue(vector);
					
					if(vector > 0) {
						cell1 = row1.createCell(1420);
						cell1.setCellValue("Positive");
					}else if(vector < 0){
						cell1 = row1.createCell(1420);
						cell1.setCellValue("Negative");
					}

				}
			}
			
			for(int i = 2; i< sheet2.getLastRowNum() + 1; i++) {
				
				
				System.out.println("next" + i);
				cell = sheet2.getRow(i).getCell(1);
			
				double los = cell.getNumericCellValue();
				System.out.println("los" + los);
			
				double recommended = 0;
				double diff = 0;
				double vectorDiff = 0;
				int counterDeviation = 0;
				for(int j = 51; j < 50 + los*7; j=j+7) {
					if(!BasicUtils.isEmpty(sheet2.getRow(i).getCell(j))) {
						if(!BasicUtils.isEmpty(sheet2.getRow(i).getCell(j+2))) {
							cell = sheet2.getRow(i).getCell(j+2);
							String result = cell.getStringCellValue();
							if(result.equalsIgnoreCase("Deviation")) {
								cell = sheet2.getRow(i).getCell(j);
								recommended += cell.getNumericCellValue();
								cell = sheet2.getRow(i).getCell(j+1);
								double localDiff = cell.getNumericCellValue();
								vectorDiff += localDiff;
								diff += (localDiff * localDiff);
								counterDeviation++;
							}
						}
					}
				}
				if(recommended > 0) {
					double ratio = Math.sqrt(diff/los) / 3;
					double vector = vectorDiff / 3;
					
					Row row1 = sheet2.getRow(i);
					Cell cell1 = row1.createCell(1418);
					cell1.setCellValue(ratio);
					
					cell1 = row1.createCell(1419);
					cell1.setCellValue(vector);
					
					if(vector > 0) {
						cell1 = row1.createCell(1420);
						cell1.setCellValue("Positive");
					}else if(vector < 0){
						cell1 = row1.createCell(1420);
						cell1.setCellValue("Negative");
					}
				}
			}
			
			
			
			
			for(int i = 2; i< sheet.getLastRowNum() + 1; i++) {
				System.out.println("next" + i);
				cell = sheet2.getRow(i).getCell(1);
			
				
				List<Double> zscoreList = new ArrayList<Double>();

				cell = sheet.getRow(i).getCell(1);
				double los = cell.getNumericCellValue();
				System.out.println("los" + los);
				for(int j = 51; j < 50 + los*7; j=j+7) {
					List<Double> deviationList = new ArrayList<Double>();
					for(int k = 2; k< sheet.getLastRowNum() + 1; k++) {
						cell = sheet.getRow(k).getCell(j+1);
						if(!BasicUtils.isEmpty(cell) && !BasicUtils.isEmpty(cell.getNumericCellValue())) {
							double deviation = cell.getNumericCellValue();
							if(deviation >= 0) {
								deviationList.add(0.0);
							}else {
								deviationList.add(deviation);
							}
						}
					}
					if(deviationList.size() > 1) {
						double sum = 0;
				        double average = 0;
				        double max = 0;
				        double min = deviationList.get(0);
						for(int p=0; p<deviationList.size(); p++ )
				        {
				            sum = sum + deviationList.get(p);
				            if(deviationList.get(p) > max)
				            {
				                max = deviationList.get(p);
				            }
				            
				            if(deviationList.get(p) < min)
				            {
				                min = deviationList.get(p);
				            }
				            
				        }
				        average = sum / deviationList.size();
				       
				      
				        Row row1 = sheet.getRow(i);
				        
						cell = sheet.getRow(i).getCell(j+1);
						if(!BasicUtils.isEmpty(cell) && !BasicUtils.isEmpty(cell.getNumericCellValue())) {

							double deviation = cell.getNumericCellValue();
							if(deviation>=0) {
//								zscoreList.add(0.0);
								Cell cell1 = row1.createCell(j+3);
								cell1.setCellValue(0.0);
							}else {
								double finalOutput = 0;
								finalOutput = (deviation - max) / (min-max);
								zscoreList.add(finalOutput);
								
								Cell cell1 = row1.createCell(j+3);
								cell1.setCellValue(finalOutput);
							}
							
						}
					}
				}
				double finalSum = 0;
				 for(double num: zscoreList) {
				        
					 finalSum = finalSum + num;
			    }
				 if(zscoreList.size() > 0) {
					 finalSum = finalSum / zscoreList.size();
				 }
				 
				Row row1 = sheet.getRow(i);
				Cell cell1 = row1.createCell(1440);
				cell1.setCellValue(finalSum);
					
			}
				
			try (FileOutputStream outputStream = new FileOutputStream("Site1_Nutrition_Training.xlsx")) {
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
