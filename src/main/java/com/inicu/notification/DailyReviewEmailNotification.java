package com.inicu.notification;

import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.models.SinJSON;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.mail.Message;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class DailyReviewEmailNotification implements Runnable {

    @Autowired
    AnalyticsServiceImpl analyticsObj;

    @Autowired
    InicuDatabaseExeption databaseException;

    @Autowired
    UserServiceDAO userServiceDao;

    @Autowired
    InicuDao inicuDao;

    public DailyReviewEmailNotification(){

    }

    private ArrayList<HashMap<String,String>> vitalParamtersList;
    private ArrayList<HashMap<String,String>> ventilatorParamters;

    BabyDetail getBabyObject(List<BabyDetail> getBabies,String uhid){
        for(BabyDetail obj:getBabies){
            if(obj.getUhid().equalsIgnoreCase(uhid)){
                return obj;
            }
        }
        return null;
    }

    HashMap<String, String> getObjectWithUhid(String uhid){

        for(int i=0;i<vitalParamtersList.size();i++)
        {
            HashMap<String,String> temp=vitalParamtersList.get(i);
            if(temp.get("UHID").equalsIgnoreCase(uhid)){
                System.out.println(vitalParamtersList);
                vitalParamtersList.remove(i);
                return temp;
            }
        }
        return null;
    }


    HashMap<String, String> getVentilatorObjectWithUhid(String uhid){

        for(int i=0;i<ventilatorParamters.size();i++)
        {
            HashMap<String,String> temp=ventilatorParamters.get(i);
            if(temp.get("UHID").equalsIgnoreCase(uhid)){
                System.out.println(ventilatorParamters);
                ventilatorParamters.remove(i);
                return temp;
            }
        }
        return null;
    }

    public Boolean checkRopStatus(BabyDetail babyObject, ScreenRop obj){

        Boolean conditionApplied=false;
        int limitDol=Integer.parseInt(babyObject.getDayoflife());
        float birthweight=babyObject.getBirthweight();

        if(babyObject.getActualgestationweek() < 28 || birthweight < 1200){
            if(limitDol <= 21){
                conditionApplied=true;
            }
        }else if((babyObject.getActualgestationweek() >= 28
                && babyObject.getActualgestationweek()  < 34)
                || (birthweight >= 1200 && birthweight < 2000)){
            if(limitDol <= 28){
                conditionApplied=true;
            }
        }
        return conditionApplied;
    }

    public String getDowneObject(List<String> list, String uhid){
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).equalsIgnoreCase(uhid)){
                return list.get(i);
            }
        }
        return null;
    }

    public Boolean checkReassessEntry(String uhid, Date ReassessDate, Date todayTime){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate1 = dateFormat.parse(ReassessDate.toString());
            Timestamp timestamp1 = new Timestamp(parsedDate1.getTime());

            Date parsedDate2 = dateFormat.parse(todayTime.toString());
            Timestamp timestamp2 = new Timestamp(parsedDate2.getTime());

            if(timestamp1.compareTo(timestamp2)>0){
                return false;
            }else if(timestamp1.compareTo(timestamp2)==0){
                String checkForEntry="select obj from NursingMedication obj where uhid='"+uhid+"'and given_time >= '"+ timestamp1
                        + "' order by given_time desc";
                List<NursingMedication> medicationGivenList=inicuDao.getListFromMappedObjQuery(checkForEntry);

                if(medicationGivenList.size()>0){
                    return true;
                }
            }
        }catch (Exception e){
            System.out.println("Exception is handled here "+e);
        }
        return false;
    }

    public String getSilvermanObject(List<String> list, String uhid){
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).equalsIgnoreCase(uhid)){
                return list.get(i);
            }
        }
        return null;
    }
    public Boolean getRdsObjectWithId(List<String> list,String uhid){
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).equalsIgnoreCase(uhid)){
                return true;
            }
        }
        return false;
    }
    public ScreenRop getRopObject(List<ScreenRop> list,String uhid){
        for(int i=0;i<list.size();i++)
        {
            ScreenRop temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)){
                return temp;
            }
        }
        return null;
    }

    public int getDailyWeight(List<BabyVisit> list,String uhid) {
        int finalCount=0;
        for(int i=0;i<list.size();i++)
        {
            BabyVisit temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)) {
                if (temp.getCurrentdateweight() != null) {
                    finalCount = finalCount + 1;
                }
            }
        }
        return finalCount;
    }

    public AntenatalHistoryDetail getAntenalHistoryObject(List<AntenatalHistoryDetail> list,String uhid){
        ArrayList<AntenatalHistoryDetail> tempObject=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            AntenatalHistoryDetail temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)) {
                tempObject.add(temp);
            }
        }
        if(tempObject.size()>0){
            return tempObject.get(0);
        }
        return null;
    }

    public List<AntenatalSteroidDetail> getAntenalSteroidObject(List<AntenatalSteroidDetail> list,String uhid){
        ArrayList<AntenatalSteroidDetail> tempObject=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            AntenatalSteroidDetail temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)) {
                tempObject.add(temp);
            }
        }

        return tempObject;
    }

    public List<NursingVitalparameter> getNursingVitalParameterList(List<NursingVitalparameter> list,String uhid){
        ArrayList<NursingVitalparameter> tempObject=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            NursingVitalparameter temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)) {
                tempObject.add(temp);
            }
        }
        return tempObject;
    }

    public List<NursingVentilaor> getNursingVentilatorList(List<NursingVentilaor> list,String uhid){
        ArrayList<NursingVentilaor> tempObject=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            NursingVentilaor temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid)) {
                tempObject.add(temp);
            }
        }
        return tempObject;
    }

    public List<BabyPrescription> getActiveMedication(List<BabyPrescription> list,String uhid){
        ArrayList<BabyPrescription> tempObject=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            BabyPrescription temp=list.get(i);
            if(temp.getUhid().equalsIgnoreCase(uhid) && temp.getIsactive()==true) {
                tempObject.add(temp);
            }
        }
        return tempObject;
    }

    public int calculateMedicationGivenCount(List<NursingMedication> list,Long presid,int frequencyType){
        int givenCount=0;
        for(int i=0;i<list.size();i++)
        {
            NursingMedication temp=list.get(i);
            if(temp.getBaby_presid().equalsIgnoreCase(presid.toString())) {
                if(frequencyType!=0) {
                    if(!BasicUtils.isEmpty(temp.getNext_dose())){
                        givenCount=givenCount+1;
                    }else{
                        return givenCount;
                    }
                }else{
                    givenCount=givenCount+1;
                }
            }
        }
        return givenCount;
    }

    public String initialAssessmentData(String uhidStr,List<BabyDetail> getBabies,String episodeStr) {
        String message = "";

        ArrayList<HashMap<String,String>> antenalHistory=new ArrayList<HashMap<String, String>>();
        try {
            // From antenal history detail
            String getAntenalHisytory="select obj from AntenatalHistoryDetail obj where uhid in (" + uhidStr + ") and episodeid in ("+episodeStr+") order by creationtime desc";
            List<AntenatalHistoryDetail> antenalHistoryList = inicuDao.getListFromMappedObjQuery(getAntenalHisytory);

            // From antenatal steroid detail
            String getAntenalSteroidHistory="select obj from AntenatalSteroidDetail obj where uhid in (" + uhidStr + ") and episodeid in ("+episodeStr+") order by creationtime desc";
            List<AntenatalSteroidDetail> antenalSteroidList = inicuDao.getListFromMappedObjQuery(getAntenalSteroidHistory);

            // From birth_to_nicu
            String getBirthToNicuData="select obj from BirthToNicu obj where uhid in (" + uhidStr + ") and episodeid in ("+episodeStr+") order by creationtime desc";
            List<BirthToNicu> birthToNicuList = inicuDao.getListFromMappedObjQuery(getBirthToNicuData);

            if(!BasicUtils.isEmpty(birthToNicuList)) {
                for (int i = 0; i < birthToNicuList.size(); i++) {

                    HashMap<String, String> tempMap = new HashMap<>();

                    BirthToNicu tempNicuObj = birthToNicuList.get(i);
                    String uhid =tempNicuObj.getUhid();
                    BabyDetail tempBaby=getBabyObject(getBabies,uhid);

                    // Birth Status
                    if(tempBaby.getBabyname()!=null){
                        tempMap.put("Baby Name",tempBaby.getBabyname());
                    }else{
                        tempMap.put("Baby Name","NO");
                    }

                    if(tempNicuObj.getUhid()!=null){
                        tempMap.put("UHID",tempNicuObj.getUhid());
                    }else{
                        tempMap.put("UHID","NO");
                    }

                    if(tempBaby.getAdmissionHeadCircumference()!=null){
                        tempMap.put("Head circumference","YES");
                    }else{
                        tempMap.put("Head circumference","NO");
                    }


                    if(tempBaby.getLength_centile()!=null){
                        tempMap.put("Length","YES");
                    }else{
                        tempMap.put("Length","NO");
                    }

                    // Birth To NICU detail
                    if (!BasicUtils.isEmpty(tempNicuObj)) {
                        // get the APGAR value
                        if (tempNicuObj.getInoutPatientStatus() != null && tempNicuObj.getInoutPatientStatus().equalsIgnoreCase("In Born")) {
                            int flag = 0;
                            if (tempNicuObj.getApgarOnemin() != null) {
                                flag = 1;
                            }

                            if (tempNicuObj.getApgarFivemin() != null) {
                                if (flag != 0) {
                                    flag = 1;
                                }
                            }
                            if (flag == 1) {
                                tempMap.put("Apgar (1,5)", "YES");
                            } else {
                                tempMap.put("Apgar (1,5)", "NO");
                            }
                        } else if(tempNicuObj.getInoutPatientStatus() != null && tempNicuObj.getInoutPatientStatus().equalsIgnoreCase("Out Born")) {

                            int flag = 0;
                            if (tempNicuObj.getApgarOnemin() != null) {
                                flag = 1;
                            }

                            if (tempNicuObj.getApgarFivemin() != null) {
                                if (flag != 0) {
                                    flag = 1;
                                }
                            }
                            if (flag == 1) {
                                tempMap.put("Apgar (1,5)", "YES");
                            } else {
                                tempMap.put("Apgar (1,5)", "-");
                            }
                        }
                    } else {
                        tempMap.put("Apgar (1,5)", "NO");
                    }


                    // Antenatal Detail
                    if(antenalHistoryList.size()>0) {
                        AntenatalHistoryDetail antenatalObj = getAntenalHistoryObject(antenalHistoryList,uhid);
                        if (!BasicUtils.isEmpty(antenatalObj)) {

                            //Antenatal Investigation
                            int flag = 0;

                            // Hepatitis B
                            if (antenatalObj.getIshepb() != null) {
                                if (antenatalObj.getHepbType() != null) {
                                    flag = 1;
                                }
                            }

                            // VDRL
                            if (antenatalObj.getVdrl() != null) {
                                if (antenatalObj.getVdrlType() != null) {
                                    flag = 1;
                                }
                            }

                            if ( flag==1) {
                                tempMap.put("Antenatal Investigations", "YES");
                            } else {
                                tempMap.put("Antenatal Investigations", "NO");
                            }

                            flag = 0;

                            // Antenatal Risk Factor
                            if (antenatalObj.getHypertension() != null || antenatalObj.getGdm() != null || antenatalObj.getDiabetes() != null || antenatalObj.getPprom() != null
                                    || antenatalObj.getGestationalHypertension() != null || antenatalObj.getNoneDisease() != null || antenatalObj.getNoneInfection() != null ||
                                    antenatalObj.getNoneOther() != null
                            ) {
                                flag = 1;
                            }

                            if (flag == 1) {
                                tempMap.put("Antenatal Risk Factor", "YES");
                            } else {
                                tempMap.put("Antenatal Risk Factor", "NO");
                            }


                            flag = 0;
                            // get Antenatal umbirical Dupler
                            if (antenatalObj.getUmbilicalDoppler() != null) {
                                if (antenatalObj.getUmbilicalDoppler() == "abnormal" && antenatalObj.getAbnormalUmbilicalDopplerType() != null) {
                                    tempMap.put("Umbilical doppler", "YES");
                                } else if (antenatalObj.getUmbilicalDoppler().equalsIgnoreCase("normal")) {
                                    tempMap.put("Umbilical doppler", "YES");
                                } else {
                                    tempMap.put("Umbilical doppler", "NO");
                                }
                            } else {
                                tempMap.put("Umbilical doppler", "NO");
                            }

                        } else {
                            tempMap.put("Antenatal Investigations", "NO");
                            tempMap.put("Antenatal Risk Factor", "NO");
                            tempMap.put("Umbilical doppler", "NO");
                        }
                    }else{
                        tempMap.put("Antenatal Investigations", "NO");
                        tempMap.put("Antenatal Risk Factor", "NO");
                        tempMap.put("Umbilical doppler", "NO");
                    }

                    // Steroid Detail
                    if(antenalSteroidList.size()>0) {
                        List<AntenatalSteroidDetail> steroidObj = getAntenalSteroidObject(antenalSteroidList,uhid);
                        if (!BasicUtils.isEmpty(steroidObj)) {

                            int steroidFlag=0;

                            for (AntenatalSteroidDetail obj: steroidObj) {
                                if (obj.getSteroidname() != null) {
                                    if (obj.getNumberofdose() != null) {
                                        steroidFlag = 1;
                                    }
                                }
                            }

                            if(steroidFlag==1){
                                tempMap.put("Antenatal steroids", "YES");
                            } else {
                                tempMap.put("Antenatal steroids", "NO");
                            }
                        } else {
                            tempMap.put("Antenatal steroids", "NO");
                        }
                    }else{
                        tempMap.put("Antenatal steroids", "NO");
                    }
                    // Initial Assessment Completed in 2 hr of admission ?

                    Boolean assessmentStatus=tempBaby.getIsassessmentsubmit();
                    if(assessmentStatus!=null){
                        if(assessmentStatus==true) {
                            tempMap.put("Assessment Completed", "YES");
                        }else{
                            tempMap.put("Assessment Completed", "NO");
                        }
                    }else{
                        tempMap.put("Assessment Completed", "NO");
                    }
                    antenalHistory.add(tempMap);
                }
            }

            if (!BasicUtils.isEmpty(antenalHistory)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Timestamp today = new Timestamp((new Date().getTime()));
                Timestamp yesterday = new Timestamp((new Date().getTime()) - (24 * 60 * 60 * 1000));

//                Date date = new Date();
//                System.out.println(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date));


                today.setHours(8);
                today.setMinutes(0);
                today.setSeconds(0);

                yesterday.setHours(8);
                yesterday.setMinutes(0);
                yesterday.setSeconds(0);

                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                message +="<b> Duration </b><br>";

                message += "<b> "+formatter.format(yesterday.getTime())+" - "+formatter.format(today.getTime())+" </b> <br><br>";

                message += "<b> Daily Initial Assessment Record: </b>";

                message += "<br><br><table style=\"border: 1px solid black;\"><tr>"
                        + "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>UHID</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Head circumference</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Length</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Apgar (1,5)</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Antenatal Investigations</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Antenatal Risk Factor</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Umbilical doppler</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Antenatal steroids</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Assessment Completed</span></td><tr>";

                for (HashMap<String,String> obj:antenalHistory) {
                    message += "<tr><td style=\"border: 1px solid black;\"><span>" + obj.get("Baby Name")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("UHID")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Head circumference")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Length")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Apgar (1,5)")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Antenatal Investigations")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Antenatal Risk Factor")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Umbilical doppler")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Antenatal steroids")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Assessment Completed")
                            + "</span></td></tr>";
                }
                message += "</table>";

                message += "<br><br>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
            databaseException.newException(receiverArray, Message.RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
                    "Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
        }
        return message;
    }

    public String getDoctorPanelData(String uhidstr,List<BabyDetail> getBabies) {
        String message="";
        try {

            Timestamp today = new Timestamp((new Date().getTime()));
            Timestamp yesterday = new Timestamp((new Date().getTime()) - (24 * 60 * 60 * 1000));

            today.setHours(8);
            today.setMinutes(0);
            today.setSeconds(0);

            yesterday.setHours(8);
            yesterday.setMinutes(0);
            yesterday.setSeconds(0);

            int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
                    - TimeZone.getDefault().getRawOffset();

            today = new Timestamp(today.getTime() - offset);
            yesterday = new Timestamp(yesterday.getTime() - offset);

            // Get the Rds record from the database
            String RdsObjects="select distinct uhid from sa_resp_rds where uhid in ("+uhidstr+") order by uhid";
            List<String> RdsObjectList=inicuDao.getListFromNativeQuery(RdsObjects);

            // Get the  Downe's and Silverman's Score
            String downesScore="select distinct uhid from score_downes where uhid in ("+uhidstr+") and downesscore is not null order by uhid";
            List<String> downesList=inicuDao.getListFromNativeQuery(downesScore);

            String silvermansScore="select distinct uhid from score_silverman where uhid in ("+uhidstr+") and silvermanscore is not null order by uhid";
            List<String> silvermansList=inicuDao.getListFromNativeQuery(silvermansScore);

            // Get the Rop Screen Data (reassess_date,is_rop,uhid)
            String ropScreening="select obj from ScreenRop obj where uhid in ("+uhidstr +")";
            List<ScreenRop> ropList=inicuDao.getListFromMappedObjQuery(ropScreening);

            //Medication Data
            String currentActiveMedication="select obj from BabyPrescription obj where uhid in ("+uhidstr+") and startdate >= '"
                    + yesterday + "' and startdate <= '"
                    + today
                    + "' order by startdate desc";
            List<BabyPrescription> medicationList=inicuDao.getListFromMappedObjQuery(currentActiveMedication);

            String medicationGiven="select obj from NursingMedication obj where uhid in ("+uhidstr+")and given_time >= '"
                    + yesterday + "' and given_time <= '"
                    + today
                    + "' order by given_time desc";
            List<NursingMedication> medicationGivenList=inicuDao.getListFromMappedObjQuery(medicationGiven);


            ArrayList<HashMap<String,String>> doctorDetailList=new ArrayList<>();
            if(!BasicUtils.isEmpty(getBabies)){
                for (BabyDetail babyObj:getBabies) {
                    HashMap<String,String> tempMap=new HashMap<>();

                    tempMap.put("Baby Name","-");
                    tempMap.put("UHID", "-");
                    tempMap.put("Downe's/Silverman's","-");
                    tempMap.put("ROP screening", "-");
                    tempMap.put("Medications", "-");


                    // Birth Status
                    if(babyObj.getBabyname()!=null){
                        tempMap.put("Baby Name",babyObj.getBabyname());
                    }

                    if (babyObj.getUhid() != null) {
                        tempMap.put("UHID", babyObj.getUhid());
                    }

                    // For Downe's and Silver man Score
                    if(RdsObjectList.size()>0){

                        Boolean isEntryPresent=getRdsObjectWithId(RdsObjectList,babyObj.getUhid());

                        if(isEntryPresent) {
                            if (downesList.size() > 0 || silvermansList.size() > 0) {

                                String tempdowne = null;
                                if (!BasicUtils.isEmpty(downesList)) {
                                    tempdowne = getDowneObject(downesList, babyObj.getUhid());
                                }
                                String tempSilvermanObject = null;
                                if (!BasicUtils.isEmpty(silvermansList)) {
                                    tempSilvermanObject = getSilvermanObject(silvermansList, babyObj.getUhid());
                                }

                                if (tempdowne != null || tempSilvermanObject != null) {
                                    tempMap.put("Downe's/Silverman's", "YES");
                                }else{
                                    tempMap.put("Downe's/Silverman's", "NO");
                                }
                            }else{
                                tempMap.put("Downe's/Silverman's", "NO");
                            }
                        }
                    }

                    // For ROP screening
                    if(ropList.size()>0){
                        ScreenRop tempObjet=getRopObject(ropList,babyObj.getUhid());

                        if(tempObjet!=null && tempObjet.getIs_rop_left()!=null && tempObjet.getIs_rop_left()==true){

                            if(tempObjet.getRop_left_zone()!=null && tempObjet.getRop_left_plus()!=null && tempObjet.getRop_left_stage()!=null){
                                tempMap.put("ROP screening", "YES");
                            }

                            if(!BasicUtils.isEmpty(tempObjet.getReassess_date())){
                                checkReassessEntry(babyObj.getUhid(),tempObjet.getReassess_date(),today);
                            }
                        }else if (tempObjet!=null && tempObjet.getIs_rop_right()!=null && tempObjet.getIs_rop_right()==true){
                            if(tempObjet.getRop_right_stage()!=null && tempObjet.getRop_right_plus()!=null && tempObjet.getRop_right_zone()!=null){
                                tempMap.put("ROP screening", "YES");
                            }
                        }else if(tempObjet!=null){
                            Boolean ropStatus=checkRopStatus(babyObj,tempObjet);
                            if(ropStatus==false){
                                tempMap.put("ROP screening", "YES");
                            }else{
                                tempMap.put("ROP screening", "NO");
                            }
                        }
                    }

                    // Medication logic
                    if(medicationList.size()>0){
                        List<BabyPrescription> tempMedication=getActiveMedication(medicationList,babyObj.getUhid());
                        String MedicationMissedStr="";
                        if(!BasicUtils.isEmpty(tempMedication)) {
                            for (BabyPrescription obj : tempMedication) {
                                int notGivenCount=0;
                                Long presid = obj.getBabyPresid();
                                String frequency = obj.getFrequency();
                                int frequencyInhours = 0;
                                int frequencyType=0;
                                if (frequency != null) {
                                    switch (frequency) {
                                        case "FR1":
                                            frequencyInhours = 24 / 24;
                                            break;
                                        case "FR2":
                                            frequencyInhours = 24 / 12;
                                            break;
                                        case "FR3":
                                            frequencyInhours = 24 / 8;
                                            break;
                                        case "FR4":
                                            frequencyInhours = 24 / 6;
                                            break;
                                        case "FR5":
                                            frequencyInhours = 24 / 4;
                                            break;
                                        case "FR8":
                                            frequencyInhours = 24 / 3;
                                            break;
                                        case "FR12":
                                            frequencyInhours = 24 / 2;
                                            break;
                                        case "FR18":
                                            frequencyInhours = 0;
                                            break;
                                        case "FR24":
                                            frequencyInhours = 24 / 1;
                                            break;
                                        case "FR36":
                                            frequencyInhours=1;
                                            frequencyType=36;
                                            break;
                                        case "FR48":
                                            frequencyInhours=1;
                                            frequencyType=48;
                                            break;
                                        default:
                                            frequencyInhours = 0;
                                    }
                                }
                                System.out.println(frequencyInhours);
                                int givenCount = calculateMedicationGivenCount(medicationGivenList, presid,frequencyType);
                                if(frequency!=null){
                                    notGivenCount=(frequencyInhours-givenCount);
                                }
                                if(notGivenCount!=0) {
                                    if(MedicationMissedStr.isEmpty()){
                                        MedicationMissedStr+=obj.getMedicinename()+"("+notGivenCount+")";
                                    }else{
                                        MedicationMissedStr+=", "+obj.getMedicinename()+"("+notGivenCount+")";
                                    }
                                }
                            }
                            if(MedicationMissedStr.isEmpty()){
                                tempMap.put("Medications", "YES");
                            }else{
                                tempMap.put("Medications",MedicationMissedStr);
                            }
                        }
                    }
                    doctorDetailList.add(tempMap);
                }
            }

            // Html
            if (!BasicUtils.isEmpty(doctorDetailList)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                message += "<b> Daily Doctor Panel Entry Record: </b>";

                message += "<br><br><table style=\"border: 1px solid black;\"><tr>"
                        + "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>UHID</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Downe's/Silverman's Done</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>ROP screening</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Medications (Missed doses)</span></td></tr>";


                for (HashMap<String,String> obj:doctorDetailList) {
                    message += "<tr><td style=\"border: 1px solid black;\"><span>" + obj.get("Baby Name")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("UHID")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Downe's/Silverman's")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("ROP screening")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("Medications")
                            + "</span></td></tr>";

                }
                message += "</table>";

                message += "<br><br>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
            databaseException.newException(receiverArray, Message.RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
                    "Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
        }
        return message;
    }

    public String getNursingVitalParameters(String uhidStr,List<BabyDetail> getBabies){
        String message = "";

        HashMap<String, String> template=new HashMap<>();
        template.put("Baby Name","NO");
        template.put("UHID", "NO");
        template.put("DOA","-");
        template.put("TOA","-");
        template.put("HR", "0");
        template.put("RR", "0");
        template.put("SPO2", "0");
        template.put("Daily Weight", "0");
        template.put("Central Temperature", "0");
        template.put("Peripheral Temperature", "0");
        try {
            // Cycle runs from 8AM to 8AM.
            Timestamp today = new Timestamp((new Date().getTime()));
            Timestamp yesterday = new Timestamp((new Date().getTime()) - (24 * 60 * 60 * 1000));

            today.setHours(8);
            today.setMinutes(0);
            today.setSeconds(0);

            yesterday.setHours(8);
            yesterday.setMinutes(0);
            yesterday.setSeconds(0);

            Date yesterdayDate=new java.sql.Date((new Date().getTime()) - (24 * 60 * 60 * 1000));

            String queryNursingVitalParameters= "Select obj from NursingVitalparameter obj where uhid in (" + uhidStr + ") and entrydate >= '"
                    + yesterday + "' and entrydate <= '"
                    + today
                    + "' order by entrydate desc";
            List<NursingVitalparameter> vitalparameters = inicuDao.getListFromMappedObjQuery(queryNursingVitalParameters);

            String dailyWeight="select obj from BabyVisit obj where uhid in ("+uhidStr+") and visitdate >= '"+yesterdayDate+"' order by visitdate desc";
            List<BabyVisit> dailyWeightList=inicuDao.getListFromMappedObjQuery(dailyWeight);

            vitalParamtersList=new ArrayList<HashMap<String,String>>();

            if(!BasicUtils.isEmpty(getBabies)) {
                for(BabyDetail obj: getBabies){

                    HashMap<String, String> tempMap = new HashMap<>();

                    tempMap.put("Baby Name","-");
                    tempMap.put("UHID", "-");
                    tempMap.put("DOA","-");
                    tempMap.put("TOA","-");
                    tempMap.put("HR", "0");
                    tempMap.put("RR", "0");
                    tempMap.put("SPO2", "0");
                    tempMap.put("Daily Weight", "0");
                    tempMap.put("Central Temperature", "0");
                    tempMap.put("Peripheral Temperature", "0");

                    // Baby Name
                    if(obj.getBabyname()!=null){
                        tempMap.put("Baby Name",obj.getBabyname());
                    }

                    if(obj.getTimeofadmission()!=null){
                        String[] timeofAdmission=obj.getTimeofadmission().split(",",3);
                        tempMap.put("TOA",timeofAdmission[0]+":"+timeofAdmission[1]+" "+timeofAdmission[2]);
                    }

                    if(obj.getDateofadmission()!=null){
                        tempMap.put("DOA",obj.getDateofadmission().toString());
                    }

                    if(obj.getUhid()!=null){
                        tempMap.put("UHID", obj.getUhid());
                    }


                    // update the today's weight Field
                    tempMap.put("Daily Weight",String.valueOf(getDailyWeight(dailyWeightList,obj.getUhid())));

                    // Personal Details of baby ends here

                    // Now Get NusingVital parameter list for this uhid;
                    List<NursingVitalparameter> nursingVitalparameterslist=getNursingVitalParameterList(vitalparameters,obj.getUhid());
                    if(nursingVitalparameterslist.size()>0) {

                        for(NursingVitalparameter nurseObj:nursingVitalparameterslist) {

                            if (nurseObj.getHrRate() != null) {
                                int count = Integer.parseInt(tempMap.get("HR")) + 1;
                                tempMap.put("HR", String.valueOf(count));
                            }

                            if (nurseObj.getRrRate() != null) {
                                int count = Integer.parseInt(tempMap.get("RR")) + 1;
                                tempMap.put("RR", String.valueOf(count));
                            }

                            if (nurseObj.getSpo2() != null) {
                                int count = Integer.parseInt(tempMap.get("SPO2")) + 1;
                                tempMap.put("SPO2", String.valueOf(count));
                            }

                            if (nurseObj.getCentraltemp() != null) {
                                int count = Integer.parseInt(tempMap.get("Central Temperature")) + 1;
                                tempMap.put("Central Temperature", String.valueOf(count));
                            }

                            if (nurseObj.getPeripheraltemp() != null) {
                                int count = Integer.parseInt(tempMap.get("Peripheral Temperature")) + 1;
                                tempMap.put("Peripheral Temperature", String.valueOf(count));
                            }
                        }
                    }
                    vitalParamtersList.add(tempMap);
                    System.out.println(vitalParamtersList);
                }
            }

            System.out.println(vitalParamtersList);
            // Html Content begins here
            if (!BasicUtils.isEmpty(vitalParamtersList)) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                message += "<b> Daily Vital Paramters Entry Record: </b>";

                message += "<br><br><table style=\"border: 1px solid black;\"><tr>"
                        + "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>UHID</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>DOA</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>TOA</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>HR</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>RR</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>SPO2</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Daily Weight</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Central Temperature</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Peripheral Temperature</span></td></tr>";


                for (HashMap<String,String> obj:vitalParamtersList) {

                    int Timeconstraint=24;
                    BabyDetail tempBaby=getBabyObject(getBabies,obj.get("UHID"));
                    Date admissionDate=tempBaby.getDateofadmission();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date AdmissionDate = dateFormat.parse(admissionDate.toString());
                        Timestamp timestamp1 = new Timestamp(admissionDate.getTime());

                        Date parsedDate2 = dateFormat.parse(yesterday.toString());
                        Timestamp timestamp2 = new Timestamp(parsedDate2.getTime());

                        if (timestamp1.compareTo(timestamp2) == 0) {
                            String admissionTime=tempBaby.getTimeofadmission();
                            if(admissionTime!=null){
                                String[] time=admissionTime.split(",", 3);
                                String meridian=time[2];
                                if(meridian.equalsIgnoreCase("PM")){
                                    Timeconstraint=(24-(Integer.parseInt(time[0])+12))+8;
                                    if(Timeconstraint<0){
                                        Timeconstraint=Timeconstraint*(-1);
                                    }
                                }else if(meridian.equalsIgnoreCase("AM")){
                                    int totalTime=Integer.parseInt(time[0])-8;
                                    if(totalTime<0){
                                        totalTime=totalTime*(-1);
                                    }
                                    Timeconstraint=24-(totalTime+1);
                                }
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    message += "<tr><td style=\"border: 1px solid black;\"><span>" + obj.get("Baby Name")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("UHID")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("DOA")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("TOA")
                            + "</span></td>";

                    if(Integer.parseInt(obj.get("HR"))<Timeconstraint && Integer.parseInt(obj.get("HR"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("HR")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("HR"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("RR"))<Timeconstraint && Integer.parseInt(obj.get("RR"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("RR")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("RR"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("SPO2"))<Timeconstraint && Integer.parseInt(obj.get("SPO2"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("SPO2")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("SPO2"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Daily Weight"))>0){
                        message += "<td style=\"border: 1px solid black;\"><span>"+"YES"+"</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "NO"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Central Temperature"))<Timeconstraint && Integer.parseInt(obj.get("Central Temperature"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("Central Temperature")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("Central Temperature"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Peripheral Temperature"))<Timeconstraint && Integer.parseInt(obj.get("Peripheral Temperature"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("Peripheral Temperature")
                                + ")</span></td></tr>";
                    }else if(Integer.parseInt(obj.get("Peripheral Temperature"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td></tr>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td></tr>";
                    }
                }
                message += "</table>";

                message += "<br><br>";
            }
        }catch (Exception e){
            e.printStackTrace();
            String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
            databaseException.newException(receiverArray, Message.RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
                    "Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
        }
        return message;
    }

    public String getVentilatorData(String uhidStr,List<BabyDetail> getBabies) {
        String message = "";

        try {
            // Cycle runs from 8AM to 8AM.
            Timestamp today = new Timestamp((new Date().getTime()));
            Timestamp yesterday = new Timestamp((new Date().getTime()) - (24 * 60 * 60 * 1000));

            today.setHours(8);
            today.setMinutes(0);
            today.setSeconds(0);

            yesterday.setHours(8);
            yesterday.setMinutes(0);
            yesterday.setSeconds(0);


            String queryVentilatorToFindLast = "Select obj from NursingVentilaor obj where uhid in (" + uhidStr + ") and entrydate >= '" + yesterday + "' and entrydate <= '" + today
                    + "' order by entrydate desc";

            List<NursingVentilaor> ventilatorList = inicuDao.getListFromMappedObjQuery(queryVentilatorToFindLast);
            System.out.println(ventilatorList);


            ventilatorParamters=new ArrayList<HashMap<String,String>>();
            if(!BasicUtils.isEmpty(getBabies)) {
                for (BabyDetail obj : getBabies) {

                    HashMap<String, String> tempMap = new HashMap<>();

                    tempMap.put("Baby Name","-");
                    tempMap.put("UHID", "-");
                    tempMap.put("PEEP/MAP", "0");
                    tempMap.put("PEEP", "0");
                    tempMap.put("PIP", "0");
                    tempMap.put("Fio2", "0");
                    tempMap.put("MAP", "0");
                    tempMap.put("Minute Volume", "0");
                    tempMap.put("Tidal Volume", "0");


                    // Birth Status
                    if(obj.getBabyname()!=null){
                        tempMap.put("Baby Name",obj.getBabyname());
                    }

                    if (obj.getUhid() != null) {
                        tempMap.put("UHID", obj.getUhid());
                    }

                    List<NursingVentilaor> ventilaorTempList=getNursingVentilatorList(ventilatorList,obj.getUhid());

                    if(ventilaorTempList.size()>0) {
                        for (NursingVentilaor ventObj:ventilaorTempList) {
                            String ventMode = ventObj.getVentmode();
                            if (ventMode.equalsIgnoreCase("VM0006") || ventMode.equalsIgnoreCase("VM0005")) {

                                tempMap.put("PEEP/MAP", "0");
                                if (ventObj.getPeepCpap() != null) {
                                    int count = Integer.parseInt(tempMap.get("PEEP")) + 1;
                                    tempMap.put("PEEP", String.valueOf(count));
                                }
                                if (ventObj.getPip() != null) {
                                    int count = Integer.parseInt(tempMap.get("PIP")) + 1;
                                    tempMap.put("PIP", String.valueOf(count));
                                }

                                if (ventObj.getFio2() != null) {
                                    int count = Integer.parseInt(tempMap.get("Fio2")) + 1;
                                    tempMap.put("Fio2", String.valueOf(count));
                                }

                                if (ventObj.getMap() != null) {
                                    int count = Integer.parseInt(tempMap.get("MAP")) + 1;
                                    tempMap.put("MAP", String.valueOf(count));
                                }

                                if (ventObj.getMinuteVolume() != null) {
                                    int count = Integer.parseInt(tempMap.get("Minute Volume")) + 1;
                                    tempMap.put("Minute Volume", String.valueOf(count));
                                }

                                if (ventObj.getTidalVolume() != null) {
                                    int count = Integer.parseInt(tempMap.get("Tidal Volume")) + 1;
                                    tempMap.put("Tidal Volume", String.valueOf(count));
                                }
                            } else {
                                if (ventObj.getPeepCpap() != null) {
                                    int count = Integer.parseInt(tempMap.get("PEEP/MAP")) + 1;
                                    tempMap.put("PEEP/MAP", String.valueOf(count));
                                }
                                tempMap.put("PEEP", "0");
                                tempMap.put("PIP", "0");
                                tempMap.put("Fio2", "0");
                                tempMap.put("MAP", "0");
                                tempMap.put("Minute Volume", "0");
                                tempMap.put("Tidal Volume", "0");
                            }
                        }
                    }
                    ventilatorParamters.add(tempMap);
                }
            }

            // Html Content begins here
            if (!BasicUtils.isEmpty(ventilatorParamters)) {
                message += "<b> Daily Ventilator Entry Record: </b>";

                message += "<br><br><table style=\"border: 1px solid black;\"><tr>"
                        + "<td style=\"border: 1px solid black;\"><span>Baby Name</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>UHID</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>PEEP/MAP</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>PEEP</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>PIP</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Fio2</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>MAP</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Minute Volume</span></td>"
                        + "<td style=\"border: 1px solid black;\"><span>Tidal Volume</span></td></tr>";


                for (HashMap<String,String> obj:ventilatorParamters) {
                    int Timeconstraint=24;
                    BabyDetail tempBaby=getBabyObject(getBabies,obj.get("UHID"));
                    Date admissionDate=tempBaby.getDateofadmission();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date AdmissionDate = dateFormat.parse(admissionDate.toString());
                        Timestamp timestamp1 = new Timestamp(admissionDate.getTime());

                        Date parsedDate2 = dateFormat.parse(yesterday.toString());
                        Timestamp timestamp2 = new Timestamp(parsedDate2.getTime());

                        if (timestamp1.compareTo(timestamp2) == 0) {
                            String admissionTime=tempBaby.getTimeofadmission();
                            if(admissionTime!=null){
                                String[] time=admissionTime.split(",", 3);
                                String meridian=time[2];
                                if(meridian.equalsIgnoreCase("PM")){
                                    Timeconstraint=(24-(Integer.parseInt(time[0])+12))+8;
                                    if(Timeconstraint<0){
                                        Timeconstraint=Timeconstraint*(-1);
                                    }
                                }else if(meridian.equalsIgnoreCase("AM")){
                                    int totalTime=Integer.parseInt(time[0])-8;
                                    if(totalTime<0){
                                        totalTime=totalTime*(-1);
                                    }
                                    Timeconstraint=24-(totalTime+1);
                                }
                            }
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }

                    message += "<tr><td style=\"border: 1px solid black;\"><span>" + obj.get("Baby Name")
                            + "</span></td>";
                    message += "<td style=\"border: 1px solid black;\"><span>" + obj.get("UHID")
                            + "</span></td>";

                    if(Integer.parseInt(obj.get("PEEP/MAP"))<Timeconstraint && Integer.parseInt(obj.get("PEEP/MAP"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("PEEP/MAP")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("PEEP/MAP"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("PEEP"))<Timeconstraint && Integer.parseInt(obj.get("PEEP"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("PEEP")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("PEEP"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("PIP"))<Timeconstraint && Integer.parseInt(obj.get("PIP"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("PIP")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("PIP"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Fio2"))<Timeconstraint && Integer.parseInt(obj.get("Fio2"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("Fio2")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("Fio2"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("MAP"))<Timeconstraint && Integer.parseInt(obj.get("MAP"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("MAP")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("MAP"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Minute Volume"))<Timeconstraint && Integer.parseInt(obj.get("Minute Volume"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("Minute Volume")
                                + ")</span></td>";
                    }else if(Integer.parseInt(obj.get("Minute Volume"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td>";
                    }

                    if(Integer.parseInt(obj.get("Tidal Volume"))<Timeconstraint && Integer.parseInt(obj.get("Tidal Volume"))!=0){
                        message += "<td style=\"border: 1px solid black;\"><span>NO(" + obj.get("Tidal Volume")
                                + ")</span></td></tr>";
                    }else if(Integer.parseInt(obj.get("Tidal Volume"))==Timeconstraint){
                        message += "<td style=\"border: 1px solid black;\"><span>" + "YES"
                                + "</span></td></tr>";
                    }else{
                        message += "<td style=\"border: 1px solid black;\"><span>" + "-"
                                + "</span></td></tr>";
                    }
                }
                message += "</table>";

                message += "<br><br>";
            }

        } catch (Exception e) {
            e.printStackTrace();
            String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
            databaseException.newException(receiverArray, Message.RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
                    "Sending scheduled SIN Sheet Mail", BasicUtils.convertErrorStacktoString(e));
        }
        return message;
    }


    @Override
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void run() {
        System.out.println("in Daily Review Email notification");
        List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());

        if(!BasicUtils.isEmpty(branchNameList)) {
            for(String branchName : branchNameList) {

                List<NotificationEmail> emailList = inicuDao
                        .getListFromMappedObjQuery(HqlSqlQueryConstants.getNotificationEmailList() + " where is_adoption ='true' and branchname = '" + branchName + "'");
                if (!BasicUtils.isEmpty(emailList)) {
                    try {
                        // will configure time-zone with DB
                        Date current = new Date();
                        current.setHours(9);
                        current.setMinutes(0);
                        int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
                        long fromTime = current.getTime() + offset;

                        String fromDateStr = "" + (fromTime - BasicConstants.DAY_VALUE);
                        String toDateStr = "" + (fromTime);

                        String message = "";

                        String babyObjList = "select obj from BabyDetail obj where admissionstatus='true' and dischargestatus is null and branchname='" + branchName
                                + "' order by creationtime desc";
                        List<BabyDetail> getBabies = inicuDao.getListFromMappedObjQuery(babyObjList);
                        String uhidStr = "";
                        String episodeStr="";
                        for (int i = 0; i < getBabies.size(); i++) {
                            if (uhidStr.isEmpty()) {
                                uhidStr += "'" + getBabies.get(i).getUhid() + "'";
                            } else {
                                uhidStr += ", '" + getBabies.get(i).getUhid() + "'";
                            }

                            if (episodeStr.isEmpty()) {
                                episodeStr += "'" + getBabies.get(i).getEpisodeid() + "'";
                            } else {
                                episodeStr += ", '" + getBabies.get(i).getEpisodeid() + "'";
                            }
                        }

                        message += initialAssessmentData(uhidStr, getBabies,episodeStr);
                        message += getNursingVitalParameters(uhidStr, getBabies);
                        message += getVentilatorData(uhidStr, getBabies);
                        message += getDoctorPanelData(uhidStr,getBabies);

                        HashMap<Message.RecipientType, List<String>> emailMap = new HashMap<Message.RecipientType, List<String>>() {
                            private static final long serialVersionUID = 1L;
                            {
                                put(Message.RecipientType.TO, new ArrayList<String>());
                                put(Message.RecipientType.CC, new ArrayList<String>());
                            }
                        };

                        for (NotificationEmail item : emailList) {
                            if (item.getEmail_type()) {
                                emailMap.get(Message.RecipientType.TO).add(item.getUser_email());
                            } else {
                                emailMap.get(Message.RecipientType.CC).add(item.getUser_email());
                            }
                        }
                        String fullHospitalName = BasicConstants.COMPANY_ID + " ( " + branchName + " )";

                        BasicUtils.sendMailWithMultipleType(emailMap, message + "<br><br>",
                                "Daily Review Record", fullHospitalName);
                    } catch(Exception e){
                        e.printStackTrace();
                        String[] receiverArray = {BasicConstants.MAIL_ID_RECIEVER};
                        databaseException.newException(receiverArray, Message.RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
                                "Sending scheduled Usage Sheet Mail", BasicUtils.convertErrorStacktoString(e));
                    }
                 }
                }
            }
       }
}
