package com.inicu.postgres.serviceImpl;

import ca.uhn.hl7v2.hoh.util.repackage.Base64;
import com.inicu.models.*;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.service.ProceduresService;
import com.inicu.postgres.service.SystematicService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.Message.RecipientType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.inicu.postgres.entities.EtIntubationReason;

/**
 * @author Sourabh Verma
 */
@Service
public class ProceduresServiceImpl implements ProceduresService {

  @Autowired
  InicuDao inicuDao;

  @Autowired
  private InicuDatabaseExeption databaseException;

  @Autowired
  private SystematicService systematicService;

  @Override
  @SuppressWarnings("unchecked")
  public TherapeuticHypothermiaPOJO getHypothermia(String uhid, String loggeduser, String branchName){
    TherapeuticHypothermiaPOJO returnObj=new TherapeuticHypothermiaPOJO();

    // get the past history

    String fetchPastHypothermiaObject = "SELECT obj FROM TherapeuticHypothermia as obj where uhid='" + uhid
        + "' order by entry_time desc";

    List<TherapeuticHypothermia> hypoResults = inicuDao.getListFromMappedObjQuery(fetchPastHypothermiaObject);
    if(!BasicUtils.isEmpty(hypoResults)) {
      returnObj.getPastEvents().setPastTherapeuticHypothermia(hypoResults);
    }
    
    
    Timestamp lastEntryDate =null;
    
    if(!BasicUtils.isEmpty(hypoResults) && !BasicUtils.isEmpty(hypoResults.get(0).getLastEntryDate()))
      lastEntryDate = hypoResults.get(0).getLastEntryDate();
  
     

    // Get the Details for the Therapeutic Hypothermia
    TherapeuticHypothermia currentTherapeuticHypoObj=returnObj.getCurrentEvent().getTherapeutic_hypothermia_object();
    
    
    
    TherapeuticHypothermia pastHypothermiaObject = new TherapeuticHypothermia();
    if(!BasicUtils.isEmpty(hypoResults) && !BasicUtils.isEmpty(hypoResults.get(0)))
       pastHypothermiaObject = hypoResults.get(0);
    
    
    
    if(!BasicUtils.isEmpty(hypoResults)) {
      if(!BasicUtils.isEmpty(pastHypothermiaObject.getDeviceActive()) && 
          !BasicUtils.isEmpty(pastHypothermiaObject.getTherapeuticHypothermiaActive())) {
        
        if(pastHypothermiaObject.getDeviceActive() ==  true && pastHypothermiaObject.getTherapeuticHypothermiaActive() == true) {
          if(!BasicUtils.isEmpty(pastHypothermiaObject.getDeviceStartStopTime()))
            currentTherapeuticHypoObj.setDeviceStartStopTime(pastHypothermiaObject.getDeviceStartStopTime());
        }else if(pastHypothermiaObject.getDeviceActive() ==  false && pastHypothermiaObject.getTherapeuticHypothermiaActive() == false) {
            currentTherapeuticHypoObj.setDeviceStartStopTime(null);

        }
          
          
        
      }
    }

    //get GA
    String getGaQuery="select obj from BabyDetail obj where uhid='"+uhid+"'order by creationtime desc";

    List<BabyDetail> gaResult = inicuDao.getListFromMappedObjQuery(getGaQuery);

    String finalDate="";
    if(!BasicUtils.isEmpty(gaResult))
    {
      Integer ga=gaResult.get(0).getActualgestationweek();
      currentTherapeuticHypoObj.setGestationAge(ga.toString());

      // get the time of admission
      Date dateofBirth=gaResult.get(0).getDateofadmission();
      String timeOfBirth=gaResult.get(0).getTimeofadmission();

      String[] newtime=timeOfBirth.split(",",3);

      int hour=0;
      int minutes=0;
      int tempHour=Integer.parseInt(newtime[0]);
      int tempMinutes=Integer.parseInt(newtime[1]);

      if(newtime[2].equalsIgnoreCase("PM")) {
        if(tempHour==12)http://localhost:8000/sys/saveCns/
            hour=tempHour+1;
        else
          hour=tempHour+12+1;

        minutes=tempMinutes+0;

      }else if (newtime[2].equalsIgnoreCase("AM")) {
        if(tempHour==12)
          hour=1;
        else
          hour=tempHour;

        minutes=tempMinutes+0;
      }

      String finalTime="";
      if(hour!=0) {
        finalTime = Integer.toString(hour) +":"+ Integer.toString(minutes);
      }

      DateFormat formatter = new SimpleDateFormat("HH:mm");


      if(finalTime!="") {
        java.sql.Time timeValue=null;
        try {
          timeValue = new java.sql.Time(formatter.parse(finalTime).getTime());
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        finalDate=dateofBirth.toString()+" "+timeValue.toString();
      }

      System.out.println("Date is :"+finalDate);
    }

    //get Age



    //get Apgar at
    String getApgarQuery="select obj from BirthToNicu obj where uhid='"+uhid+"' and apgar_fivemin<'5' order by creationtime desc";
    List<BirthToNicu> resultSet = inicuDao.getListFromMappedObjQuery(getApgarQuery);

    if(!BasicUtils.isEmpty(resultSet))
    {
      currentTherapeuticHypoObj.setApgarAt(resultSet.get(0).getApgarFivemin().toString());
    }

    // get PPV

    // get Ph of first one hour after birth
    String getPhQuery="select obj from NursingBloodGas obj where uhid='"+uhid+"' and ph<'7' and be<'16' and lactate > '10' and entrydate <'"+finalDate+"' order by creationtime desc";
    List<NursingBloodGas> bloodGasResult =  inicuDao.getListFromMappedObjQuery(getPhQuery);
    if(!BasicUtils.isEmpty(bloodGasResult))
    {
      currentTherapeuticHypoObj.setPh(bloodGasResult.get(0).getPh());
      currentTherapeuticHypoObj.setBaseExcess(bloodGasResult.get(0).getBe());
      currentTherapeuticHypoObj.setLactate(bloodGasResult.get(0).getLactate());
      
    }
    
    Date date = new Date();
    Timestamp currentDate = new Timestamp(date.getTime());
    System.out.println(""+currentDate);
    
    if(!BasicUtils.isEmpty(lastEntryDate))
      currentTherapeuticHypoObj.setLastEntryDate(lastEntryDate);
    else
      currentTherapeuticHypoObj.setLastEntryDate(currentDate);
    
    
    


    returnObj.getCurrentEvent()
        .setTherapeutic_hypothermia_object(currentTherapeuticHypoObj);


    // get the past data of the Hypopthermia

    String getPastDataQuery="select obj from TherapeuticHypothermia obj where uhid='"+uhid+"' order by entry_time desc";
    List<TherapeuticHypothermia> PastHypoResult =  inicuDao.getListFromMappedObjQuery(getPastDataQuery);
    if(!BasicUtils.isEmpty(PastHypoResult))
    {
      returnObj.getPastEvents().setPastTherapeuticHypothermia(PastHypoResult);
    }
  
  return returnObj;
  }

  public String saveHypothermia(TherapeuticHypothermia object)
  {
    try {
      TherapeuticHypothermia saveObject=(TherapeuticHypothermia)inicuDao.saveObject(object);
      if (object.getLeveneEnabled()) {
  		ScoreLevene leveneObj = object.getLeveneObj();
  		leveneObj.setUhid(object.getUhid());
  		leveneObj.setAdmission_entry(true);
  		try {
  			leveneObj = (ScoreLevene) inicuDao.saveObject(leveneObj);
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		if (leveneObj.getLevenescoreid() == null) {
  			String leveneGetSql = "select obj from ScoreLevene as obj where uhid='" + object.getUhid()
  					+ "' and admission_entry='true' order by creationtime desc";
  			List<ScoreLevene> leveneGetList = inicuDao.getListFromMappedObjQuery(leveneGetSql);
  			leveneObj = leveneGetList.get(0);
  		}
  		//birthNicuObj.setLeveneid(registrationObj.getLeveneObj().getLevenescoreid().toString());
  	}
      if(saveObject!=null)
      {
        return "saved";
      }
    } catch (Exception e)
    {
      e.printStackTrace();
      return "save Failed Database Error";
    }
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ProceduresMasterPojo getProcedures(String uhid, String loggeduser, String branchName) {
    ProceduresMasterPojo returnObj = new ProceduresMasterPojo(uhid, loggeduser);
    try {
      List<KeyValueObj> orderInvestigation = getRefObj(HqlSqlQueryConstants.getRefTestList());
      returnObj.setOrderInvestigation(orderInvestigation);

      // getting order object
      String queryRefTestsList = "select obj from RefTestslist as obj where not (obj.assesmentCategory like ('%None%')) order by assesmentCategory";
      List<RefTestslist> listRefTests = inicuDao.getListFromMappedObjQuery(queryRefTestsList);

      HashMap<Object, List<RefTestslist>> testsListMap = new HashMap<Object, List<RefTestslist>>();
      for (RefTestslist test : listRefTests) {
        List<RefTestslist> categoryList = null;
        if (testsListMap.get(test.getAssesmentCategory()) != null) {
          categoryList = testsListMap.get(test.getAssesmentCategory());
        } else {
          categoryList = new ArrayList<RefTestslist>();
        }
        categoryList.add(test);
        testsListMap.put(test.getAssesmentCategory(), categoryList);
      }
      returnObj.setTestsList(testsListMap);
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get order Investigation Object in Procedures", BasicUtils.convertErrorStacktoString(e));
    }

    // peripheral get service start/api/inicu/getLabOrders/2456723/
    try {
      List<PeripheralCannula> peripheralList = inicuDao
          .getListFromMappedObjQuery(HqlSqlQueryConstants.getPeripheralList(uhid));
      if (!BasicUtils.isEmpty(peripheralList)) {
        returnObj.setPastPeripheralList(peripheralList);
        List<PeripheralCannula> currentPeripheralList = inicuDao
            .getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentPeripheralList(uhid));
        if (!BasicUtils.isEmpty(currentPeripheralList)) {
          returnObj.setCurrentPeripheralList(currentPeripheralList);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get peripheral procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // peripheral get service end

    // Central Line get service start
    try {
      List<CentralLine> centralLineList = inicuDao
          .getListFromMappedObjQuery(HqlSqlQueryConstants.getCentralLineList(uhid));
      if (!BasicUtils.isEmpty(centralLineList)) {
        returnObj.setPastCentralLineList(centralLineList);
        List<CentralLine> currentCentralLineList = inicuDao
            .getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentCentralLineList(uhid));
        if (!BasicUtils.isEmpty(currentCentralLineList)) {
          returnObj.setCurrentCentralLineList(currentCentralLineList);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
          "Get Central Line procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Central Line get service end

    // Lumbar Puncture get service start
    try {
      List<LumbarPuncture> lumbarPunctureList = inicuDao
          .getListFromMappedObjQuery(HqlSqlQueryConstants.getLumbarPunctureList(uhid));
      returnObj.setPastLumbarPunctureList(lumbarPunctureList);
      
      //new LumbarPuncture object
      LumbarPuncture emptyLumbarPunctureObj = new LumbarPuncture();
      
      //current LumbarPuncture list
      String getLumbarPunctureList = HqlSqlQueryConstants.getLumbarPunctureList(uhid);
          
              //+ "order by time_in desc";
      List<LumbarPuncture> currentLumbarPuncturelist = inicuDao.getListFromMappedObjQuery(getLumbarPunctureList);
      if (!BasicUtils.isEmpty(currentLumbarPuncturelist)) {
        emptyLumbarPunctureObj.setUhid(currentLumbarPuncturelist.get(0).getUhid());
        //newPeritonealDialysisObj.setSajaundiceId(currentPeritonealDialysislist.get(0).getSajaundiceId());
        emptyLumbarPunctureObj.setLoggeduser(currentLumbarPuncturelist.get(0).getLoggeduser());
        
      }

      String prodcedureCareListQuery = "select pre_procedure_care_id,pre_procedure_care_name from ref_lp_pre_procedure_care ";
      List<KeyValueObj> procedureCareList = getRefObj(prodcedureCareListQuery);
      
      if (!BasicUtils.isEmpty(procedureCareList)) {
        emptyLumbarPunctureObj.setProcedureCareList(procedureCareList);
      }
      
      if (!BasicUtils.isEmpty(emptyLumbarPunctureObj)) {
        returnObj.setEmptyLumbarPunctureObj(emptyLumbarPunctureObj);            
      }
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
          "Get LumbarPuncture procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Lumbar Puncture get service end

    // Vtap get service start
    try {
      List<Vtap> vtapList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getVtapList(uhid));
      returnObj.setPastVtapList(vtapList);
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
          "Get Vtap procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Vtap get service end

    // Et intubation get service start
    try {
      //Current ET Intubation List
      List<EtIntubation> currentEtIntubationList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getCurrentEtIntubationList(uhid));
      if (!BasicUtils.isEmpty(currentEtIntubationList)) {
        returnObj.setCurrentEtIntubationList(currentEtIntubationList);
//        returnObj.setCurrentEtIntubationObj(currentEtIntubationList.get(0));
        
        //Past ET Intubation List
        List<EtIntubation> pastEtIntubationList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getEtIntubationList(uhid));
        if (!BasicUtils.isEmpty(pastEtIntubationList)) {
          returnObj.setPastEtIntubationList(pastEtIntubationList);
//          returnObj.setPastEtIntubationTableList(generatePastEtIntubationTableList(pastEtIntubationList));
        }     
      }
      
      //ET Intubation Reason List
      List<EtIntubationReason> etIntubationReasonList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.EtIntubationReason);
      if (!BasicUtils.isEmpty(etIntubationReasonList)) {
        returnObj.setEtIntubationReasonList(etIntubationReasonList);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get ET-Intubation procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Et intubation get service end

            // Chest Tube get service start
            try {
              // chest tube get service here
              String getChestTube = HqlSqlQueryConstants.ChestTubeProcedure + " where uhid='" + uhid + "'and isactive='true'";
              List<ProcedureChesttube> chestTubelist = inicuDao.getListFromMappedObjQuery(getChestTube);

              if (!BasicUtils.isEmpty(chestTubelist)) {

                Iterator<ProcedureChesttube> chestTubeIterator = chestTubelist.iterator();
                while (chestTubeIterator.hasNext()) {
                  ProcedureChesttube chestTubeObj = chestTubeIterator.next();
                  if (!BasicUtils.isEmpty(chestTubeObj.getIsTubeRemoved()) && !chestTubeObj.getIsTubeRemoved()) {

            if (!BasicUtils.isEmpty(chestTubeObj.getChesttubeValue())
                && BasicUtils.isEmpty(chestTubeObj.getChestTubeAdjustedValue())) {
              chestTubeObj.setIsTubeReadyToAdjust(true);
            }
            if (!BasicUtils.isEmpty(chestTubeObj.getChestTubeAdjustedValue())
                && chestTubeObj.getChestTubeAdjustedValue().equalsIgnoreCase("clamp")) {
              chestTubeObj.setIsClampReadyToAdjust(true);
            }
            if (!BasicUtils.isEmpty(chestTubeObj.getIschesttubeLeft()) && chestTubeObj.getIschesttubeLeft()) {
              returnObj.getChestTubeObj().getLeftTubes().add(chestTubeObj);
            } else if(!BasicUtils.isEmpty(chestTubeObj.getIschesttubeRight()) && chestTubeObj.getIschesttubeRight()) {
              returnObj.getChestTubeObj().getRightTubes().add(chestTubeObj);
            }
          }
        }

      }

      PneumothoraxPOJO pneumothorax = systematicService.getPneumothorax(uhid);
      returnObj.setPneumothorax(pneumothorax);

    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get Chest Tube Procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Chest Tube get service end
    
    // Exchange Transfusion get service start
      try {
        // Exchange Transfusion get service here      
        
        //to check whether exchange is planned or done from jaundice 
        String getIsPlannedQuery = "select obj from SaJaundice as obj where uhid='" + uhid + "' and exchangetrans IS NOT NULL order by creationtime desc ";
        List<SaJaundice> getIsPlannedQuerylist = inicuDao.getListFromMappedObjQuery(getIsPlannedQuery);
        SaJaundice isPlanned = null;
        if (!BasicUtils.isEmpty(getIsPlannedQuerylist)) {
          SaJaundice saJaundiceObj = getIsPlannedQuerylist.get(0);
          if(saJaundiceObj.getExchangetrans() != null) {
            isPlanned = saJaundiceObj;
          }
        }
        returnObj.setIsPlanned(isPlanned);
        
        if(isPlanned != null) { 
          //set doctor list for checked by
          returnObj.setDoctorList(getDoctorList("doctor", branchName));
          
          //query for all exchange transfusion
          String getAllExchangeTransfusionQuery = HqlSqlQueryConstants.ExchangeTransfusionProcedure + " where uhid='" 
              + uhid + "' and sajaundice_id!='" + isPlanned.getSajaundiceid() + "' order by time_in desc";
          List<ProcedureExchangeTransfusion> allExchangeTransfusionlist = inicuDao.getListFromMappedObjQuery(getAllExchangeTransfusionQuery);
          if (!BasicUtils.isEmpty(allExchangeTransfusionlist)) {
            returnObj.setAllExchangeTransfusionList(allExchangeTransfusionlist);
          }
          
          //new exchange transfusion object
          ProcedureExchangeTransfusion newExchangeTransfusionObj = new ProcedureExchangeTransfusion();
          
          //currrent exchange transfusion list
          String getCurrentExchangeTransfusionQuery = HqlSqlQueryConstants.ExchangeTransfusionProcedure + " where uhid='" 
              + uhid + "' and sajaundice_id='" + isPlanned.getSajaundiceid() + "' order by time_in desc";
          List<ProcedureExchangeTransfusion> currentExchangeTransfusionlist = inicuDao.getListFromMappedObjQuery(getCurrentExchangeTransfusionQuery);
          if (!BasicUtils.isEmpty(currentExchangeTransfusionlist)) {
            newExchangeTransfusionObj.setUhid(currentExchangeTransfusionlist.get(0).getUhid());
            newExchangeTransfusionObj.setSajaundiceId(currentExchangeTransfusionlist.get(0).getSajaundiceId());
            newExchangeTransfusionObj.setLoggeduser(currentExchangeTransfusionlist.get(0).getLoggeduser());
            newExchangeTransfusionObj.setEpisodeNumber(currentExchangeTransfusionlist.get(0).getEpisodeNumber());
            
            String isNewExchangeTransfusionQuery = HqlSqlQueryConstants.ExchangeTransfusionProcedure + " where sajaundice_id='" 
                + isPlanned.getSajaundiceid() + "'";
            List<ProcedureExchangeTransfusion> isNewExchangeTransfusionList = inicuDao.getListFromMappedObjQuery(isNewExchangeTransfusionQuery);
            if(!BasicUtils.isEmpty(isNewExchangeTransfusionList)) {
              newExchangeTransfusionObj.setExchangeType(currentExchangeTransfusionlist.get(0).getExchangeType());
              newExchangeTransfusionObj.setRoute(currentExchangeTransfusionlist.get(0).getRoute());
              newExchangeTransfusionObj.setRouteCentralType(currentExchangeTransfusionlist.get(0).getRouteCentralType());
              newExchangeTransfusionObj.setAliquot(currentExchangeTransfusionlist.get(0).getAliquot());
              newExchangeTransfusionObj.setCycles(currentExchangeTransfusionlist.get(0).getCycles());
              newExchangeTransfusionObj.setExchangeBloodGroup(currentExchangeTransfusionlist.get(0).getExchangeBloodGroup());
              newExchangeTransfusionObj.setBloodProduct(currentExchangeTransfusionlist.get(0).getBloodProduct());
              newExchangeTransfusionObj.setBloodProduct1(currentExchangeTransfusionlist.get(0).getBloodProduct1());
              newExchangeTransfusionObj.setBloodProduct2(currentExchangeTransfusionlist.get(0).getBloodProduct2());
              newExchangeTransfusionObj.setCollectionDate(currentExchangeTransfusionlist.get(0).getCollectionDate());
              newExchangeTransfusionObj.setExpiryDate(currentExchangeTransfusionlist.get(0).getExpiryDate());
              newExchangeTransfusionObj.setBagNo(currentExchangeTransfusionlist.get(0).getBagNo());
              newExchangeTransfusionObj.setCheckedBy(currentExchangeTransfusionlist.get(0).getCheckedBy());
              newExchangeTransfusionObj.setBabyBloodGroup(currentExchangeTransfusionlist.get(0).getBabyBloodGroup());
              newExchangeTransfusionObj.setVolume(currentExchangeTransfusionlist.get(0).getVolume());
              newExchangeTransfusionObj.setWorkingWeight(currentExchangeTransfusionlist.get(0).getWorkingWeight());
              newExchangeTransfusionObj.setBloodBagImage(currentExchangeTransfusionlist.get(0).getBloodBagImage());
              newExchangeTransfusionObj.setMotherBloodGroup(currentExchangeTransfusionlist.get(0).getMotherBloodGroup());

            }
            returnObj.setCurrentExchangeTransfusionList(currentExchangeTransfusionlist);
          }
          returnObj.setExchangeTransfusionObj(newExchangeTransfusionObj);           
          
          
          //date list query for exchange transfusion 
          String getUniqueExchangeTransfusionDateQuery = "select DISTINCT(assessmentTime),sajaundiceid from SaJaundice where uhid='" + uhid + "' and exchangetrans='true' and sajaundiceid!='" +isPlanned.getSajaundiceid()  + "' order by assessmentTime desc ";
          List<Object> uniqueExchangeTransfusionDateList = inicuDao.getListFromMappedObjQuery(getUniqueExchangeTransfusionDateQuery);
          if (!BasicUtils.isEmpty(uniqueExchangeTransfusionDateList)) {
            List<Object> exchangeDateList  = new ArrayList<>();
            List<String> presentDateList = new ArrayList<>();
            for(int i=0;i<uniqueExchangeTransfusionDateList.size();i++) {
              Object[] uniqueExchangeTransfusionDate = (Object[]) uniqueExchangeTransfusionDateList.get(i);
              Timestamp doneDate = (Timestamp) uniqueExchangeTransfusionDate[0];
              DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
              uniqueExchangeTransfusionDate[0] = f.format(doneDate);
              
              if(presentDateList.contains(f.format(doneDate).toString()) != true) {
                presentDateList.add(f.format(doneDate).toString());
                exchangeDateList.add(uniqueExchangeTransfusionDate);
              }
            }
            returnObj.setExchangeDateList(exchangeDateList);
          } 
        }
        
        //Weight for Calculation
        String queryChildWeightForCal = "select weight_for_cal from baby_visit where uhid ='" + uhid 
            + "' order by creationtime desc";
        List<Float> resultWeightForCalSet = inicuDao.getListFromNativeQuery(queryChildWeightForCal);
        Float prevWeightForCal = null;
        if (!BasicUtils.isEmpty(resultWeightForCalSet)) {
          prevWeightForCal = resultWeightForCalSet.get(0);
        }
        returnObj.setWorkingWeight(prevWeightForCal);
        
        //Baby Detail Object
        List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getBabyDetailList(uhid));
        if(!BasicUtils.isEmpty(babyDetailList)) {
          returnObj.setBabyDetailObj(babyDetailList.get(0));
        }
        
        //Mother Blood Group Detail
        String queryMotherBloodGroup = "select obj from  AntenatalHistoryDetail as obj where uhid ='" + uhid 
            + "' order by creationtime desc";
        List<AntenatalHistoryDetail> motherBloodGroupList = inicuDao.getListFromMappedObjQuery(queryMotherBloodGroup);
        if(!BasicUtils.isEmpty(motherBloodGroupList)) {
          String motherBloodGroup = "";
          if(!BasicUtils.isEmpty(motherBloodGroupList.get(0).getMotherBloodGroupAbo())) {
            motherBloodGroup += motherBloodGroupList.get(0).getMotherBloodGroupAbo() + " ";
          }

          if(!BasicUtils.isEmpty(motherBloodGroupList.get(0).getMotherBloodGroupRh())) {
            if(motherBloodGroupList.get(0).getMotherBloodGroupRh().equalsIgnoreCase("negative") || motherBloodGroupList.get(0).getMotherBloodGroupRh().equalsIgnoreCase("-ve")) {
              motherBloodGroup += "-ve";
            }
            else {
              if(motherBloodGroupList.get(0).getMotherBloodGroupRh().equalsIgnoreCase("positive") || motherBloodGroupList.get(0).getMotherBloodGroupRh().equalsIgnoreCase("+ve")) {
                motherBloodGroup += "+ve";
              }
              else {
                motherBloodGroup += motherBloodGroupList.get(0).getMotherBloodGroupRh();
              } 
            }
          }
          
          if(!BasicUtils.isEmpty(motherBloodGroup)) {
            returnObj.setMotherBloodGroup(motherBloodGroup);
          }
          else {
            returnObj.setMotherBloodGroup(null);
          }
        }
        
        //image for blood bag
        ImagePOJO bloodBagImage = getImage("bloodbag_" + uhid + "_pic.png");
        returnObj.setBloodBagImage(bloodBagImage);
        
      } catch (Exception e) {
        e.printStackTrace();
        String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
        databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
            "Get Exchange Transfusion Procedures", BasicUtils.convertErrorStacktoString(e));
      }
        // Exchange Transfusion get service end

    
    // EtSuction get service start
    try {
      List<EtSuction> etSuctionList = inicuDao
          .getListFromMappedObjQuery(HqlSqlQueryConstants.getEtSuctionList(uhid));
      if (!BasicUtils.isEmpty(etSuctionList)) {
        returnObj.setPasEtSuctionList(etSuctionList);
      }
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
          "Get Central Line procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // EtSuction get service end

    // Procedure Other get service start
    try {
      List<ProcedureOther> procedureOtherList = inicuDao
          .getListFromMappedObjQuery(HqlSqlQueryConstants.getProcedureOtherList(uhid));
      returnObj.setPastOtherList(procedureOtherList);
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get Procedure Other procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // Procedure Other get service end
    
    // Peritoneal Dialysis get service start
        try {
          //query for all Peritoneal Dialysis
          String getAllPeritonealDialysisQuery = HqlSqlQueryConstants.PeritonealDialysisProcedure + " where uhid='" 
              + uhid + "' order by pd_date_time desc";
          List<PeritonealDialysis> allPeritonealDialysislist = inicuDao.getListFromMappedObjQuery(getAllPeritonealDialysisQuery);
          if (!BasicUtils.isEmpty(allPeritonealDialysislist)) {
            returnObj.setAllPeritonealDialysisObjList(allPeritonealDialysislist);
          }
          
          //new PeritonealDialysis object
          PeritonealDialysis newPeritonealDialysisObj = new PeritonealDialysis();
          
          //current PeritonealDialysis list
          String getPeritonealDialysisQuery = HqlSqlQueryConstants.PeritonealDialysisProcedure + " where uhid='" 
              + uhid + "' ";
                  //+ "order by time_in desc";
          List<PeritonealDialysis> currentPeritonealDialysislist = inicuDao.getListFromMappedObjQuery(getPeritonealDialysisQuery);
          if (!BasicUtils.isEmpty(currentPeritonealDialysislist)) {
            newPeritonealDialysisObj.setUhid(currentPeritonealDialysislist.get(0).getUhid());
            //newPeritonealDialysisObj.setSajaundiceId(currentPeritonealDialysislist.get(0).getSajaundiceId());
            newPeritonealDialysisObj.setLoggeduser(currentPeritonealDialysislist.get(0).getLoggeduser());
            
          }
          List<PeritonealDialysis> newPeritonealDialysisObjList = new ArrayList<PeritonealDialysis>();
          returnObj.setPeritonealDialysisObjList(newPeritonealDialysisObjList);
          
          String indicationListQuery = "select indication_id,indication_name from ref_pd_indication ";
          List<KeyValueObj> indicationList = getRefObj(indicationListQuery);
          
          if (!BasicUtils.isEmpty(indicationList)) {
            newPeritonealDialysisObj.setIndicationList(indicationList);
          }
          
          String prodcedureCareListQuery = "select pre_procedure_care_id,pre_procedure_care_name from ref_pd_pre_procedure_care ";
          List<KeyValueObj> procedureCareList = getRefObj(prodcedureCareListQuery);
          
          if (!BasicUtils.isEmpty(procedureCareList)) {
            newPeritonealDialysisObj.setProcedureCareList(procedureCareList);
          }
          
          if (!BasicUtils.isEmpty(newPeritonealDialysisObj)) {
            returnObj.setPeritonealDialysisObj(newPeritonealDialysisObj);           
          }
          
        } catch (Exception e) {
          e.printStackTrace();
          String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
          databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", uhid,
              "Get Peritoneal Dialysis procedures", BasicUtils.convertErrorStacktoString(e));
        }
        // Peritoneal Dialysis get service end

    
    int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
        - TimeZone.getDefault().getRawOffset();

    // Cycle runs from 8AM to 8AM.
    Timestamp today = new Timestamp((new java.util.Date().getTime()));
    Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
    Timestamp yesterday = new Timestamp((new java.util.Date().getTime()) - (24 * 60 * 60 * 1000));
    Timestamp tomorrow = new Timestamp((new java.util.Date().getTime()) + (24 * 60 * 60 * 1000));

    today.setHours(8);
    today.setMinutes(0);
    today.setSeconds(0);

    yesterday.setHours(8);
    yesterday.setMinutes(0);
    yesterday.setSeconds(0);
    
    tomorrow.setHours(8);
    tomorrow.setMinutes(0);
    tomorrow.setSeconds(0);

    currentDate = new Timestamp(currentDate.getTime() - offset);
    today = new Timestamp(today.getTime() - offset);
    yesterday = new Timestamp(yesterday.getTime() - offset);
    tomorrow = new Timestamp(tomorrow.getTime() - offset);    
    // Procedure Get orders from Doctor Procedures for Nursing to Execute
    try {
      
      if(currentDate.getHours() >= 8){
        List<ProcedureOther> procedureOtherList = inicuDao
            .getListFromMappedObjQuery(HqlSqlQueryConstants.getProcedureOrderList(uhid,today,tomorrow));
        returnObj.setPastprocedureOtherOrdersList(procedureOtherList);
      }
      else{
        List<ProcedureOther> procedureOtherList = inicuDao
            .getListFromMappedObjQuery(HqlSqlQueryConstants.getProcedureOrderList(uhid,yesterday,today));
        returnObj.setPastprocedureOtherOrdersList(procedureOtherList);
      }
      
      
    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggeduser, uhid,
          "Get Procedure Other procedures", BasicUtils.convertErrorStacktoString(e));
    }
    // end
    
    return returnObj;
  }

  public List<Object> generatePastEtIntubationTableList(List<EtIntubation> pastEtIntubationList) {
    List<Object> pastEtIntubationTableList = new ArrayList<>();
    try {
      Object[] pastEtIntubationTableObj = new Object[3];
      for(int i=0;i<pastEtIntubationList.size();i++) {
        String etIntubationType = "Intubation";
        String etIntubationNote,etIntubationReason;
        etIntubationNote = etIntubationReason = null;
        if(pastEtIntubationList.get(i).getIsReintubation() != null && pastEtIntubationList.get(i).getIsReintubation() == true) {
          etIntubationType = "Reintubation";
        }
        else{
          if(pastEtIntubationList.get(i).getIsextubation() != null) { 
            etIntubationType = "Extubation";
          }
        }
        
        if(etIntubationType.equalsIgnoreCase("Extubation")) {
          etIntubationNote = "Extubation was done on " + pastEtIntubationList.get(i).getRemoval_timestamp().toString(); 
        }
        else {
          if(etIntubationType.equalsIgnoreCase("Reintubation")) {
            etIntubationNote = "Reintubation was done on " + pastEtIntubationList.get(i).getRemoval_timestamp().toString(); 
          }
          else {
            etIntubationNote = "Intubation was done on " + pastEtIntubationList.get(i).getRemoval_timestamp().toString(); 
          }
        }
        
        if(etIntubationType.equalsIgnoreCase("Extubation")) {
          if(pastEtIntubationList.get(i).getRemovalReason().contains(",")) {
            etIntubationReason = "Reason for Extubation were " + pastEtIntubationList.get(i).getRemovalReason();  
          }
          else {
            etIntubationReason = "Reason for Extubation was " + pastEtIntubationList.get(i).getRemovalReason(); 
          }
        }
        
        if(etIntubationType.equalsIgnoreCase("Intubation")) {
          if(pastEtIntubationList.get(i).getReasonIntubation().contains(",")) {
            etIntubationReason = "Reason for Intubation were " + pastEtIntubationList.get(i).getReasonIntubation(); 
          }
          else {
            etIntubationReason = "Reason for Intubation was " + pastEtIntubationList.get(i).getReasonIntubation();  
          }
        }
        
        if(etIntubationType.equalsIgnoreCase("Reintubation")) {
          if(pastEtIntubationList.get(i).getReasonReintubation().contains(",")) {
            etIntubationReason = "Reason for Reintubation were " + pastEtIntubationList.get(i).getReasonReintubation(); 
          }
          else {
            etIntubationReason = "Reason for Reintubation was " + pastEtIntubationList.get(i).getReasonReintubation();  
          }
        }
        
        pastEtIntubationTableObj[0] = etIntubationType;
        pastEtIntubationTableObj[1] = etIntubationNote;
        pastEtIntubationTableObj[2] = etIntubationReason;
        
        pastEtIntubationTableList.add(pastEtIntubationTableObj);
      }

    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "default", "",
          "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
    }

    return pastEtIntubationTableList;
  }

  @Override
  @SuppressWarnings("unchecked")
  public ImagePOJO getImage(String imageName) {// image name with its
                          // format...default is .png

    ImagePOJO imageData = new ImagePOJO();
    File fnew = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageName);
    try {
      if (fnew.exists()) {
        BufferedImage image = ImageIO.read(fnew);
        if (!BasicUtils.isEmpty(image)) {
          String encodedStr = java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(fnew.toPath()));
          encodedStr = "data:image/png;base64," + encodedStr;
          imageData.setName(imageName);
          imageData.setData(encodedStr);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "default", "",
          "FAIL_OBJECT", BasicUtils.convertErrorStacktoString(e));
    }

    return imageData;

  }
  
  @Override
  @SuppressWarnings("unchecked")
  public ResponseMessageWithResponseObject storeImage(String imageName, String imageData) {

    ResponseMessageWithResponseObject response = new ResponseMessageWithResponseObject();
    File imageDir = new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH);
    if (!imageDir.exists()) {
      System.out.println("creating image directory:");
      try {
        imageDir.mkdir();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (!BasicUtils.isEmpty(imageName)) {
      // store image
      try {
        Base64 decoder = new Base64();
        FileOutputStream osf;

        if (!BasicUtils.isEmpty(imageData)) {
          // write images onto the path
          String value = imageData.split("base64,")[1];
          byte[] imgBytes = decoder.decode(value);
          osf = new FileOutputStream(
              new File(BasicConstants.WORKING_DIR + BasicConstants.IMG_PATH + imageName));
          osf.write(imgBytes);
          osf.flush();
        }

      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }

    response.setType(BasicConstants.MESSAGE_SUCCESS);
    response.setMessage("Image stored successfully.");
    return response;
  }


  @SuppressWarnings("unchecked")
  private List<KeyValueObj> getDoctorList(String type, String branchName) {
    List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
    try {
      List<User> refList = inicuDao.getListFromMappedObjQuery(HqlSqlQueryConstants.getUserList(branchName));
      if (refList != null) {
        KeyValueObj keyValue = null;
        Iterator<User> iterator = refList.iterator();
        while (iterator.hasNext()) {
          keyValue = new KeyValueObj();
          User obj = iterator.next();
          keyValue.setKey(obj.getUserName());
          keyValue.setValue(obj.getFirstName() + " " + obj.getLastName());
          String userObjQuery = "SELECT user FROM UserRolesTable as user WHERE userName='" + obj.getUserName()
              + "' and branchname = '" + branchName + "'";
          List<UserRolesTable> result = inicuDao.getListFromMappedObjQuery(userObjQuery);
          if (result != null && result.size() > 0) {
            UserRolesTable userRoles = (UserRolesTable) result.get(0);
            if (userRoles != null) {
              String roleId = userRoles.getRoleId();
              if (type.equalsIgnoreCase("doctor")) {
                if (roleId.equals("2") || roleId.equals("3")) {
                  refKeyValueList.add(keyValue);
                }
              } else if (type.equalsIgnoreCase("nurse")) {
                if (roleId.equals("5")) {
                  refKeyValueList.add(keyValue);
                }
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
  @SuppressWarnings("unchecked")
  public ResponseMessageWithResponseObject saveProcedures(String uhid, String loggedUser, String branchName, String procedureName,
      ProceduresMasterPojo proceduresMasterObj) {
    ResponseMessageWithResponseObject returnObj = new ResponseMessageWithResponseObject();
    try {
      // code to save procedure
      if (procedureName.equalsIgnoreCase(BasicConstants.Peripheral_Cannula)) {
        List<PeripheralCannula> currentList = proceduresMasterObj.getCurrentPeripheralList();
        currentList = (List<PeripheralCannula>) inicuDao.saveMultipleObject(currentList);
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Central_Line)) {
        List<CentralLine> currentList = proceduresMasterObj.getCurrentCentralLineList();
        currentList = (List<CentralLine>) inicuDao.saveMultipleObject(currentList);
        
        for(CentralLine line : currentList) {
	        
	        NurseExecutionOrders order = new NurseExecutionOrders();
			
			if(!BasicUtils.isEmpty(line.getAntiCoagulentType()) && BasicUtils.isEmpty(line.getCentralLineId()) && !BasicUtils.isEmpty(line.getInsertion_timestamp())) {
				order.setEventname("Central Line");
				order.setIsExecution(false);
				order.setOrderText("Heparin is started");
				order.setAssessmentdate(line.getInsertion_timestamp());
				order.setUhid(uhid);
				order.setLoggeduser(loggedUser);
	
				inicuDao.saveObject(order);
			}
        }
		
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Lumbar_Puncture)) {
        LumbarPuncture lumbarPunctureObj = proceduresMasterObj.getEmptyLumbarPunctureObj();
        lumbarPunctureObj.setUhid(uhid);
        lumbarPunctureObj.setLoggeduser(loggedUser);
        lumbarPunctureObj = (LumbarPuncture) inicuDao.saveObject(lumbarPunctureObj);
        saveOrderInvestigation(proceduresMasterObj.getTestsList(), lumbarPunctureObj.getLumbar_puncture_id(),
            uhid, loggedUser, BasicConstants.Lumbar_Puncture, lumbarPunctureObj.getLumbar_timestamp());
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Vtap)) {
        Vtap vtapObj = proceduresMasterObj.getEmptyVtapObj();
        vtapObj.setUhid(uhid);
        vtapObj.setLoggeduser(loggedUser);
        vtapObj = (Vtap) inicuDao.saveObject(vtapObj);
        saveOrderInvestigation(proceduresMasterObj.getTestsList(), vtapObj.getVtap_id(), uhid, loggedUser,
            BasicConstants.Vtap, vtapObj.getVtap_timestamp());
      } else if (procedureName.equalsIgnoreCase(BasicConstants.ET_Intubation)) {
//        List<EtIntubation> currentList = proceduresMasterObj.getCurrentEtIntubationList();
//        currentList = (List<EtIntubation>) inicuDao.saveMultipleObject(currentList);
        
        //To Store Latest Et Intubation Details
        EtIntubation currenEtIntubationObj = (EtIntubation) proceduresMasterObj.getCurrentEtIntubationObj();
        if (currenEtIntubationObj.getIsextubation() != null && currenEtIntubationObj.getIsextubation()) {

            String progressNotesQuery = "select obj from EtIntubation as obj where uhid ='" + uhid
                    + "' and removal_timestamp is null order by creationtime desc";
            List<EtIntubation> progressNotesResultSet = inicuDao.getListFromMappedObjQuery(progressNotesQuery);
            String insertionProgressNotes = null;
            String etId = null;
            if (!BasicUtils.isEmpty(progressNotesResultSet)) {
                if(!BasicUtils.isEmpty(progressNotesResultSet.get(0).getProgressnotes())) {
                    insertionProgressNotes = progressNotesResultSet.get(0).getProgressnotes();
                }
                if(!BasicUtils.isEmpty(progressNotesResultSet.get(0).getEt_intubation_id())) {
                    etId = String.valueOf(progressNotesResultSet.get(0).getEt_intubation_id());
                }
            }

            String progressNotes = "";
            if (currenEtIntubationObj.getProgressnotes() != null) {
                progressNotes = currenEtIntubationObj.getProgressnotes();
                progressNotes = progressNotes.substring(progressNotes.indexOf("by") + 2);
                insertionProgressNotes += progressNotes;
            }
            String updateQuery = "update et_intubation set isextubation ='" + currenEtIntubationObj.getIsextubation()
                    + "' , removal_reason = '" + currenEtIntubationObj.getRemovalReason() + "' ,removal_timestamp = '"
                    + currenEtIntubationObj.getRemoval_timestamp() + "' ,loggeduser ='" + currenEtIntubationObj.getLoggeduser()
                    + "' ,progressnotes ='" + insertionProgressNotes + "' where uhid = '"  + uhid + "' and et_intubation_id ='"
                    + etId + "'";
            try {
                inicuDao.updateOrDeleteNativeQuery(updateQuery);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            inicuDao.saveObject(currenEtIntubationObj);
        }
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Procedure_Other)) {
        ProcedureOther otherObj = proceduresMasterObj.getEmptyOtherObj();
        otherObj.setUhid(uhid);
        otherObj.setLoggeduser(loggedUser);
        otherObj = (ProcedureOther) inicuDao.saveObject(otherObj);
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Procedure_ChestTube)) {


      }else if (procedureName.equalsIgnoreCase(BasicConstants.Procedure_ExchangeTransfusion)) {
        List<ProcedureExchangeTransfusion> exchangeTransfusionObjList = proceduresMasterObj.getExchangeTransfusionObjList();
        if(!BasicUtils.isEmpty(exchangeTransfusionObjList)) {
          for(int i=0;i<exchangeTransfusionObjList.size();i++){
            Timestamp doneDate = exchangeTransfusionObjList.get(i).getTimeIn();
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            exchangeTransfusionObjList.get(i).setDoneDate(f.format(doneDate));
          }
          exchangeTransfusionObjList = (List<ProcedureExchangeTransfusion>) inicuDao.saveMultipleObject(exchangeTransfusionObjList);
        }
        
      } else if (procedureName.equalsIgnoreCase(BasicConstants.Procedure_ETSuction)) {
        EtSuction etSuction = (EtSuction) inicuDao
            .saveObject(proceduresMasterObj.getEmptyEtSuctionObj());
        
        if (etSuction.getEt_suction_id() != null) {
          saveOrderInvestigation(proceduresMasterObj.getTestsList(), etSuction.getEt_suction_id(),
              etSuction.getUhid(), etSuction.getLoggeduser(), "Et Suction",
              etSuction.getOrderTime());
        }
        
      }/*else if (procedureName.equalsIgnoreCase(BasicConstants.Peritoneal_Dialysis)) {
        List<PeritonealDialysis> peritonealDialysisObjList = proceduresMasterObj.getPeritonealDialysisObjList();
        if(!BasicUtils.isEmpty(peritonealDialysisObjList)) {
          for(int i=0;i<peritonealDialysisObjList.size();i++){
            Timestamp doneDate = peritonealDialysisObjList.get(i).getTimeIn();
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
            peritonealDialysisObjList.get(i).setDoneDate(f.format(doneDate));
          }
          peritonealDialysisObjList = (List<PeritonealDialysis>) inicuDao.saveMultipleObject(peritonealDialysisObjList);
        }
        
      }*/
      returnObj.setMessage("Procedure: " + procedureName + " saved successfully!");
      returnObj.setType(BasicConstants.MESSAGE_SUCCESS);
    
    } catch (Exception e) {
      e.printStackTrace();
      returnObj.setType(BasicConstants.MESSAGE_FAILURE);
      String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
      databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, loggedUser, uhid,
          "Saving procedures " + procedureName, BasicUtils.convertErrorStacktoString(e));
    }
    returnObj.setReturnedObject(getProcedures(uhid, loggedUser,branchName));
    return returnObj;
  }

  @SuppressWarnings("unchecked")
  public List<KeyValueObj> getRefObj(String query) {
    List<KeyValueObj> refKeyValueList = new ArrayList<KeyValueObj>();
    try {
      List<Object[]> refList = inicuDao.getListFromNativeQuery(query);
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

  private boolean CheckSampleSelected(String TestName)
  {
    List<String> names = BasicUtils.getNameOfTestWithoutSample();
    String LowerTestName = TestName.toLowerCase();
    for(String name : names)
    {
      if(LowerTestName.contains(name))
      {
        return false;
      }
    }

    return true;

  }

  private void saveOrderInvestigation(HashMap<Object, List<RefTestslist>> testsList, Long assessmentdid, String uhid,
      String userId, String pageName, Timestamp orderTime) throws Exception {
    if (!BasicUtils.isEmpty(testsList)) {
      Set<Object> keySetToAssess = testsList.keySet();
      Iterator<Object> iterator = keySetToAssess.iterator();
      while (iterator.hasNext()) {
        Object key = iterator.next();
        List<RefTestslist> listAssessCategoryTestsList = testsList.get(key);
        for (RefTestslist tests : listAssessCategoryTestsList) {
          if (tests.getIsSelected() != null && tests.getIsSelected()) {
            InvestigationOrdered investigationOrder = new InvestigationOrdered();
            investigationOrder.setAssesment_type(pageName);
            investigationOrder.setAssesmentid(String.valueOf(assessmentdid));
            investigationOrder.setUhid(uhid);
            investigationOrder.setCategory(tests.getAssesmentCategory());
            investigationOrder.setTestcode(tests.getTestcode());
            investigationOrder.setTestname(tests.getTestname());
            investigationOrder.setTestslistid(tests.getTestid());
            investigationOrder.setInvestigationorder_user(userId);
            investigationOrder.setOrder_status("ordered");
            investigationOrder.setInvestigationorder_time(orderTime);
            investigationOrder.setsamplevisible(CheckSampleSelected(tests.getTestname()));
            inicuDao.saveObject(investigationOrder);
          }
        }
      }
    }
  }
}