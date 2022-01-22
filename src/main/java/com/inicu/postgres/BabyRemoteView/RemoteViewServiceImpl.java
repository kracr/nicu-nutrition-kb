package com.inicu.postgres.BabyRemoteView;

import com.inicu.models.AudioResponse;
import com.inicu.models.GeneralResponseObject;
import com.inicu.models.NotificationObject;
import com.inicu.models.ViewBabyResponseObject;
import com.inicu.postgres.Quartz.PushNotificationService;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.*;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.SimpleHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class RemoteViewServiceImpl implements RemoteView {

    @Autowired
    InicuDao inicuDao;

    @Autowired
    SettingDao settingDao;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private SimpMessagingTemplate template;

    public RemoteViewServiceImpl(){
        System.out.println("Welcome to remote view constructor");
    }

    @Override
    public void saveNurseConfirmation(RemoteViewPushNotifcation pushNotification) {
        try{
            String uhid = pushNotification.getUhid();
            String response = pushNotification.getResponse().trim();
            String delayTime = pushNotification.getDelayMessage();
            String messageType = pushNotification.getTimeType();

            if(!BasicUtils.isEmpty(uhid)) {
                if(response.equalsIgnoreCase("DELAY")){
                    // get the baby_view_timings for this baby
                    String getQuery = "SELECT obj FROM BabyViewTimings obj WHERE uhid ='"+ uhid +"'";
                    List<BabyViewTimings> babyViewTimingsList = inicuDao.getListFromMappedObjQuery(getQuery);

                    String[] mytime = delayTime.split(" ");
                    int minutes = Integer.parseInt(mytime[0]);

                    BabyViewTimings babyViewTimings = babyViewTimingsList.get(0);
                    if(messageType.equalsIgnoreCase("Morning")){
                        String[] morningFromTime = babyViewTimings.getMorningFromTime().split(",");
                        String[] morningToTime = babyViewTimings.getMorningToTime().split(",");

                        int updatedMinute = Integer.parseInt(morningFromTime[1]);;
                        int updatedHour = Integer.parseInt(morningFromTime[0]);
                        String updatedStr = morningFromTime[2].trim();

                        if(Integer.parseInt(morningFromTime[1])+minutes<60){
                            updatedMinute = Integer.parseInt(morningFromTime[1])+minutes;
                        }else{
                            updatedMinute = (-1)*(60 - (Integer.parseInt(morningFromTime[1])+minutes));
                            updatedHour = Integer.parseInt(morningFromTime[0])+1;

                            if(updatedHour == 12){
                                if(morningFromTime[2].trim().equalsIgnoreCase("AM")){
                                    updatedStr = "PM";
                                }else{
                                    updatedStr = "AM";
                                }
                            } else if (updatedHour>12){
                                updatedHour = updatedHour%12;
                            }
                        }

                        int updatedToHour =Integer.parseInt(morningToTime[0]);
                        int updatedToMinute =  Integer.parseInt(morningToTime[1]);
                        String updatedToStr = morningToTime[2].trim();

                        if(Integer.parseInt(morningToTime[1])+minutes<60){
                            updatedToMinute = Integer.parseInt(morningToTime[1])+minutes;
                        }else{
                            updatedToMinute = (-1)*(60 - (Integer.parseInt(morningToTime[1])+minutes));
                            updatedToHour = Integer.parseInt(morningToTime[0])+1;

                            if(updatedToHour == 12){
                                if(morningToTime[2].trim().equalsIgnoreCase("AM")){
                                    updatedToStr = "PM";
                                }else{
                                    updatedToStr = "AM";
                                }
                            }else if (updatedToHour>12){
                                updatedToHour = updatedToHour%12;
                            }
                        }

                        // Now update the Table as per the time
                        String currentHourStr = updatedHour <10 ? "0"+updatedHour : updatedHour+"";
                        String currentMinuteStr = updatedMinute <10 ? "0"+updatedMinute : updatedMinute+"";

                        String currentToHourStr = updatedToHour <10 ? "0"+updatedToHour : updatedToHour+"";
                        String currentToMinuteStr = updatedToMinute <10 ? "0"+updatedToMinute : updatedToMinute+"";

                        String newFromTime = currentHourStr +","+currentMinuteStr+","+updatedStr;
                        String newToTime = currentToHourStr +","+currentToMinuteStr+","+updatedToStr;

                        String updateTimeQuery = "update baby_view_timings set morning_from_time ='"+newFromTime+"', morning_to_time='" + newToTime +"', nurse_confirmation='" + response + "', delay_time ='"+delayTime+"' where uhid='" + uhid+ "'";
                        inicuDao.updateOrDeleteNativeQuery(updateTimeQuery);
                        System.out.println("Updated Time");
                    }
                    else if(messageType.equalsIgnoreCase("Evening")) {
                        String[] morningFromTime = babyViewTimings.getEveningFromTime().split(",");
                        String[] morningToTime = babyViewTimings.getEveningToTime().split(",");

                        int updatedMinute = Integer.parseInt(morningFromTime[1]);;
                        int updatedHour = Integer.parseInt(morningFromTime[0]);
                        String updatedStr = morningFromTime[2].trim();

                        if(Integer.parseInt(morningFromTime[1])+minutes<=60){
                            updatedMinute = Integer.parseInt(morningFromTime[1])+minutes;
                        }else{
                            updatedMinute = (-1)*(60 - (Integer.parseInt(morningFromTime[1])+minutes));
                            updatedHour = Integer.parseInt(morningFromTime[0])+1;

                            if(updatedHour == 12){
                                if(morningFromTime[2].trim().equalsIgnoreCase("AM")){
                                    updatedStr = "PM";
                                }else{
                                    updatedStr = "AM";
                                }
                            }else if (updatedHour>12){
                                updatedHour = updatedHour%12;
                            }

                        }

                        int updatedToHour =Integer.parseInt(morningToTime[0]);
                        int updatedToMinute =  Integer.parseInt(morningToTime[1]);
                        String updatedToStr = morningToTime[2].trim();

                        if(Integer.parseInt(morningToTime[1])+minutes<=60){
                            updatedToMinute = Integer.parseInt(morningToTime[1])+minutes;
                        }else{
                            updatedToMinute = (-1)*(60 - (Integer.parseInt(morningToTime[1])+minutes));
                            updatedToHour = Integer.parseInt(morningToTime[0])+1;

                            if(updatedToHour == 12){
                                if(morningToTime[2].trim().equalsIgnoreCase("AM")){
                                    updatedToStr = "PM";
                                }else{
                                    updatedToStr = "AM";
                                }
                            }else if (updatedToHour>12){
                                updatedToHour = updatedToHour%12;
                            }
                        }

                        // Now update the Table as per the time
                        String currentHourStr = updatedHour <10 ? "0"+updatedHour : updatedHour+"";
                        String currentMinuteStr = updatedMinute <10 ? "0"+updatedMinute : updatedMinute+"";

                        String currentToHourStr = updatedToHour <10 ? "0"+updatedToHour : updatedToHour+"";
                        String currentToMinuteStr = updatedToMinute <10 ? "0"+updatedToMinute : updatedToMinute+"";

                        String newFromTime = currentHourStr +","+currentMinuteStr+","+updatedStr;
                        String newToTime = currentToHourStr +","+currentToMinuteStr+","+updatedToStr;

                        String updateTimeQuery = "update baby_view_timings set evening_from_time ='"+newFromTime+"', evening_to_time='" + newToTime +"', nurse_confirmation='" + response + "', delay_time ='"+delayTime+"' where uhid='" + uhid+ "'";
                        inicuDao.updateOrDeleteNativeQuery(updateTimeQuery);
                        System.out.println("Updated Time");
                    }
                    // update the Job Message
                    updateJob(uhid);
                }
                else{
                    String updateQuery = "update baby_view_timings set nurse_confirmation = '" + response + "' where uhid ='" + uhid + "'";
                    inicuDao.updateOrDeleteNativeQuery(updateQuery);
                }
                // update the action taken on notification in database
                pushNotification.setActive(false);
                inicuDao.saveObject(pushNotification);
//                template.convertAndSend("/topic/updateNurseConfirmation/", pushNotification);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public String saveAudioStatus(AudioResponse audioResponse) {
        String result = "";

        String uhid = audioResponse.getUhid();
        boolean response = audioResponse.isAudioEnabled();

        // get the baby Object
        String updateQuery = "update baby_detail set audio_enabled = '" + response +"' where uhid='" + uhid + "'";
        if(!BasicUtils.isEmpty(updateQuery)){
            // save the object
            inicuDao.updateOrDeleteNativeQuery(updateQuery);
            result = "Updated audio status";
        }else{
            System.out.println("Failed to update the status");
            result = "Failed to update the audio status";
        }

        return result;
    }

    @Override
    public GeneralResponseObject getPendingNotifications(String branchname) {
        GeneralResponseObject generalResponseObject;
        String getRemoteViewNotifications = "SELECT obj FROM RemoteViewPushNotifcation as obj WHERE branchname = '"+branchname+"' and is_active = 'true' order by creationtime desc";
        List<RemoteViewPushNotifcation> remoteViewPushNotifcationList = inicuDao.getListFromMappedObjQuery(getRemoteViewNotifications);
        if(remoteViewPushNotifcationList!=null && remoteViewPushNotifcationList.size()>0){
            generalResponseObject = BasicUtils.getResonseObject(true,200,"Notification List",remoteViewPushNotifcationList);
        }else{
            generalResponseObject = BasicUtils.getResonseObject(true,200,"Notification List",null);
        }
        return generalResponseObject;
    }

    @Override
    public GeneralResponseObject saveBabyViewTimings(List<BabyViewTimings> babyViewTimingsList, String branchName) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try {
            List<BabyViewTimings> babyViewTimingsList1 = new ArrayList<>();
            String uhidList = "";

            // update the list with those babies whose edit value is true
            for (BabyViewTimings babyViewObject : babyViewTimingsList) {
                if (!BasicUtils.isEmpty(babyViewObject.getValueEdit()) && babyViewObject.getValueEdit() == true) {
                    babyViewTimingsList1.add(babyViewObject);
                    if (uhidList.isEmpty()) {
                        uhidList += "'" + babyViewObject.getUhid() + "'";
                    } else {
                        uhidList += ", '" + babyViewObject.getUhid() + "'";
                    }
                }
            }

            if (babyViewTimingsList1.size() > 0) {
                // save the list in the database
                List<BabyViewTimings> returnedResult = (List<BabyViewTimings>) inicuDao.saveMultipleObject(babyViewTimingsList1);
                generalResponseObject = BasicUtils.getResonseObject(true, 200, "Success", returnedResult);
            } else {
                generalResponseObject = BasicUtils.getResonseObject(true, 304, "List Empty", null);
            }

            if(BasicConstants.ICHR_SETTINGS_ENABLED) {
                System.out.println("[ ::: ICHR SETTINGS ENABLED :::]");
                final String uhidThreadList = uhidList;
                Thread myCurrentThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("[ ::: Inside Job Thread run Method :::]");
                        HashMap<String, String> parentsMap = new HashMap<>();
                        if (babyViewTimingsList1.size() > 0) {

                            String getParentNameQuery = "SELECT obj FROM ParentDetail as obj where uhid in (" + uhidThreadList + ")";
                            List<ParentDetail> parentDetailList = inicuDao.getListFromMappedObjQuery(getParentNameQuery);
                            // Loop over the parentDetails list and fill the hash map
                            for (ParentDetail parentObject : parentDetailList) {
                                String parentName = "";
                                if (!BasicUtils.isEmpty(parentObject.getMothername())) {
                                    parentName = parentObject.getMothername();
                                } else if (!BasicUtils.isEmpty(parentObject.getFathername())) {
                                    parentName = parentObject.getFathername();
                                }
                                parentsMap.put(parentObject.getUhid(), parentName);
                            }
                        }

                        // send the updated babies list to Ichr database
                        for (BabyViewTimings babyObject : babyViewTimingsList1) {

                            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                            String parentName = parentsMap.get(babyObject.getUhid());
                            StringBuilder smsMessage = new StringBuilder();
                            StringBuilder smsReminderMessage = new StringBuilder();
                            String link = BasicConstants.ICHR_APP_LINK;

                            smsMessage.append("Greetings from iCHR Team! Dear ");
                            smsReminderMessage.append("Greetings from iCHR Team! Dear ");

                            if (parentName != null && parentName != "") {
                                smsMessage.append(parentName);
                                smsReminderMessage.append(parentName);
                            } else {
                                smsMessage.append("parents");
                                smsReminderMessage.append("parents");
                            }

                            // Now Update the CronJob Running respective to that baby
                            createJob(babyObject, branchName);

                            // update the string with new timings updated by the admin/ Doctor;
                            String morningFromTimeMsgStr = BasicUtils.getTimeString(babyObject.getMorningFromTime());
                            String morningToTimeMsgStr = BasicUtils.getTimeString(babyObject.getMorningToTime());

                            String eveningFromTimeMsgStr = BasicUtils.getTimeString(babyObject.getEveningFromTime());
                            String eveningToTimeMsgStr = BasicUtils.getTimeString(babyObject.getEveningToTime());

                            String message = "Greetings from iCHR Team! Kindly view your baby remotely during ";
                            String generalMessgae = "during ";

                            if (!BasicUtils.isEmpty(babyObject.getMorningTimeEnabled()) && babyObject.getMorningTimeEnabled() == true) {
                                message += morningFromTimeMsgStr + " to " + morningToTimeMsgStr + " in morning";
                                generalMessgae += morningFromTimeMsgStr + " to " + morningToTimeMsgStr + " in morning";

                                postParameters.add(new BasicNameValuePair("morningFromTime", babyObject.getMorningFromTime().trim()));
                                postParameters.add(new BasicNameValuePair("morningToTime", babyObject.getMorningToTime().trim()));
                            } else {

                                postParameters.add(new BasicNameValuePair("morningFromTime", null));
                                postParameters.add(new BasicNameValuePair("morningToTime", null));
                            }

                            if (!BasicUtils.isEmpty(babyObject.getEveningTimeEnabled()) && babyObject.getEveningTimeEnabled() == true) {
                                if (babyObject.getMorningTimeEnabled() == true) {
                                    message += " and ";
                                    generalMessgae += " and ";
                                }
                                message += eveningFromTimeMsgStr + " to " + eveningToTimeMsgStr + " in evening";
                                generalMessgae += eveningFromTimeMsgStr + " to " + eveningToTimeMsgStr + " in evening";

                                postParameters.add(new BasicNameValuePair("eveningFromTime", babyObject.getEveningFromTime().trim()));
                                postParameters.add(new BasicNameValuePair("eveningToTime", babyObject.getEveningToTime().trim()));
                            } else {
                                postParameters.add(new BasicNameValuePair("eveningFromTime", null));
                                postParameters.add(new BasicNameValuePair("eveningToTime", null));
                            }

                            message += ".";
                            generalMessgae += ".";

                            smsMessage.append(",remote view time for your baby is set. Kindly view your baby on iCHR app " + link + " " + generalMessgae + " Thank you.");
                            smsReminderMessage.append(",remote view for your baby will start in next 10 minutes. " +
                                    "Please ensure you are logged into the iCHR app " + link + " to view live video of your baby. Thank you.");
                            try {
                                String companyId = BasicConstants.ICHR_SCHEMA;
                                postParameters.add(new BasicNameValuePair("companyId", companyId));
                                postParameters.add(new BasicNameValuePair("uid", babyObject.getUhid()));
                                postParameters.add(new BasicNameValuePair("message", message));
                                postParameters.add(new BasicNameValuePair("smsMessage", smsMessage.toString()));
                                postParameters.add(new BasicNameValuePair("smsReminderMsg", smsReminderMessage.toString()));
                                postParameters.add(new BasicNameValuePair("requestType", smsMessage.toString()));
                                postParameters.add(new BasicNameValuePair("jobName", "simple"));

                                // Get the Gestation of the Baby and save in the ichr database
                                String gestationWeek = getBabyGestation(babyObject.getUhid());
                                postParameters.add(new BasicNameValuePair("gestationWeek", gestationWeek));

                                // now try making the post request using the ICHR android post method
                                if(!BasicUtils.isEmpty(BasicConstants.PUSH_NOTIFICATION)) {
                                    String response = SimpleHttpClient.executeHttpPost(BasicConstants.PUSH_NOTIFICATION, postParameters);
                                    System.out.println("[ Create Job Thread Finished AND RESPONSE RECEIVED]"+response);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                System.out.println("[ :: Create Job Thread started :: ]");
                myCurrentThread.start();
            }else{
                System.out.println("[ ::: ICHR SETTINGS NOT ENABLED :::]");
            }
        } catch (Exception e) {
            System.out.printf("Exception: " + e);
        }
        return generalResponseObject;
    }

    @Override
    public RemoteViewPushNotifcation getViewMessage(BabyDetail babyDetail,String messageType){
        System.out.println("[ :: CREATING NOTIFICATION MESSAGE :: ]");

        RemoteViewPushNotifcation pushNotification = new RemoteViewPushNotifcation();
        pushNotification.setUhid(babyDetail.getUhid());

        String message="";

        boolean eveningTime = false;
        boolean morningTime = false;

        String getViewTimings = "select obj from BabyViewTimings obj where uhid ='" + babyDetail.getUhid().trim() + "'";
        List<BabyViewTimings> babyViewTimingsList = inicuDao.getListFromMappedObjQuery(getViewTimings);

        BabyViewTimings babyViewTimings = null;
        if(babyViewTimingsList!=null && babyViewTimingsList.size()>0){
            babyViewTimings = babyViewTimingsList.get(0);
        }

        if(babyViewTimings!=null) {

            int morningFromHour = 0;
            int morningToHour = 0;

            int morningFromMinute = 0;
            int morningToMinute = 0;

            int eveningFromHour = 0;
            int evevningToHour = 0;

            int eveningFromMinute = 0;
            int evevningToMinute = 0;

            // Morning time
            String[] morningFromTime = babyViewTimings.getMorningFromTime().split(",");
            if (morningFromTime[2].equalsIgnoreCase("PM") && Integer.parseInt(morningFromTime[0]) != 12) {
                morningFromHour = 12 + Integer.parseInt(morningFromTime[0]);
                morningFromMinute = Integer.parseInt(morningFromTime[1]);
            } else if (morningFromTime[2].equalsIgnoreCase("AM") && Integer.parseInt(morningFromTime[0]) == 12) {
                morningFromHour = 0;
                morningFromMinute = Integer.parseInt(morningFromTime[1]);
            } else {
                morningFromHour = Integer.parseInt(morningFromTime[0]);
                morningFromMinute = Integer.parseInt(morningFromTime[1]);
            }

            String[] morningToTime = babyViewTimings.getMorningToTime().split(",");
            if (morningToTime[2].equalsIgnoreCase("PM") && Integer.parseInt(morningToTime[0]) != 12) {
                morningToHour = 12 + Integer.parseInt(morningToTime[0]);
                morningToMinute = Integer.parseInt(morningToTime[1]);
            } else if (morningToTime[2].equalsIgnoreCase("AM") && Integer.parseInt(morningToTime[0]) == 12) {
                morningToHour = 0;
                morningToMinute = Integer.parseInt(morningToTime[1]);
            } else {
                morningToHour = Integer.parseInt(morningToTime[0]);
                morningToMinute = Integer.parseInt(morningToTime[1]);
            }


            // Evening Time
            String[] eveningFromTime = babyViewTimings.getEveningFromTime().split(",");
            if (eveningFromTime[2].equalsIgnoreCase("PM") && Integer.parseInt(eveningFromTime[0]) != 12) {
                eveningFromHour = 12 + Integer.parseInt(eveningFromTime[0]);
                eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
            } else if (eveningFromTime[2].equalsIgnoreCase("AM") && Integer.parseInt(eveningFromTime[0]) == 12) {
                eveningFromHour = 0;
                eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
            } else {
                eveningFromHour = Integer.parseInt(eveningFromTime[0]);
                eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
            }

            String[] eveningToTime = babyViewTimings.getEveningToTime().split(",");
            if (eveningToTime[2].equalsIgnoreCase("PM") && Integer.parseInt(eveningToTime[0]) != 12) {
                evevningToHour = 12 + Integer.parseInt(eveningToTime[0]);
                evevningToMinute = Integer.parseInt(eveningToTime[1]);
            } else if (eveningToTime[2].equalsIgnoreCase("AM") && Integer.parseInt(eveningToTime[0]) == 12) {
                evevningToHour = 0;
                evevningToMinute = Integer.parseInt(eveningToTime[1]);
            } else {
                evevningToHour = Integer.parseInt(eveningToTime[0]);
                evevningToMinute = Integer.parseInt(eveningToTime[1]);
            }


            // get current Hour and Minutes
            Date date = new Date();
            int offset = BasicUtils.getOffsetValue();
            System.out.println("[ Offset Value :: "+ offset);
            Timestamp currentTime = new Timestamp(date.getTime()+ offset);
            int currentHour = currentTime.getHours();
            int currentMinute = currentTime.getMinutes();

            String currentMinuteStr = currentMinute <10 ? "0"+currentMinute : currentMinute+"";
            String currentHourStr = currentHour <10 ? "0"+currentHour : currentHour+"";

            boolean flag = false;
            LocalTime time = LocalTime.parse(currentHourStr+":"+currentMinuteStr);


            System.out.println("[ Generate Notification Message  Timings ]");
            System.out.println("Current Time :"+time);


            // check morning Hours
            boolean inBetween = BasicUtils.isBetween(time, LocalTime.of(morningFromHour, morningFromMinute), LocalTime.of(morningToHour, morningToMinute));
            if (inBetween && babyViewTimings.getMorningTimeEnabled()){
                morningTime = true;
            }

            System.out.println("Morning Time From :"+LocalTime.of(morningFromHour, morningFromMinute));
            System.out.println("Morning Time To :"+LocalTime.of(morningToHour, morningToMinute));
            System.out.println("Morning Time Match :"+morningTime);

            // check evening Hours
            inBetween = BasicUtils.isBetween(time, LocalTime.of(eveningFromHour, eveningFromMinute), LocalTime.of(evevningToHour, evevningToMinute));
            if (inBetween && babyViewTimings.getEveningTimeEnabled()){
                eveningTime = true;
            }

            System.out.println("Evening Time From :"+LocalTime.of(eveningFromHour, eveningFromMinute));
            System.out.println("Evening Time To :"+LocalTime.of(evevningToHour, evevningToMinute));
            System.out.println("Evening Time Match :"+eveningTime);

            if(messageType.equalsIgnoreCase("Custom")) {
                if (morningTime) {

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Parents of " + babyDetail.getBabyname());

                    if(!BasicUtils.isEmpty(babyDetail.getBabyType())){
                        stringBuilder.append(" " + babyDetail.getBabyType());
                    }

                    if(!BasicUtils.isEmpty(babyDetail.getBabyNumber())){
                        stringBuilder.append(" " + babyDetail.getBabyNumber());
                    }

                    stringBuilder.append(", UHID: " + babyDetail.getUhid() +
                            " are requesting remote video view of the baby. Scheduled view time is " + BasicUtils.getTimeString(babyViewTimings.getMorningFromTime()) + " - " + BasicUtils.getTimeString(babyViewTimings.getMorningToTime()) + "."+
                            " \n\n Do you want to start the remote view now? ");

                    message = stringBuilder.toString();
                    pushNotification.setTimeType("Morning");
                } else if (eveningTime) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Parents of " + babyDetail.getBabyname());

                    if(!BasicUtils.isEmpty(babyDetail.getBabyType())){
                        stringBuilder.append(" " + babyDetail.getBabyType());
                    }

                    if(!BasicUtils.isEmpty(babyDetail.getBabyNumber())){
                        stringBuilder.append(" " + babyDetail.getBabyNumber());
                    }

                    stringBuilder.append(", UHID: " + babyDetail.getUhid() +
                            " are requesting remote video view of the baby. Scheduled view time is " + BasicUtils.getTimeString(babyViewTimings.getEveningFromTime()) + " - " + BasicUtils.getTimeString(babyViewTimings.getEveningToTime()) + "."+
                            " \n\n Do you want to start the remote view now? ");

                    message = stringBuilder.toString();
                    pushNotification.setTimeType("Evening");
                }
            }
            pushNotification.setMessage(message);
            pushNotification.setEveningEnabled(eveningTime);
            pushNotification.setMorningEnabled(morningTime);
        }

        return pushNotification;
    }

    @Override
    public ViewBabyResponseObject getLiveVideoMessage(String uhid, String videoURL) {
        ViewBabyResponseObject viewBabyResponseObject  = new ViewBabyResponseObject();
        try{

            boolean admissionstatus = false;
            int notInNicu = -1;
            boolean timingMatching = false;

            String staffBusyString = "Video declined by nurse. Please Contact Hospital.";
            String nurseNotAnsweredString = "Nurse Confirmation is pending!";

            // get the Nurse Confirmation
            String getNurseConfirmationQuery = "SELECT obj from BabyViewTimings obj where uhid='" + uhid + "' order by creationtime desc";
            List<BabyViewTimings> nurseConfirmation = inicuDao.getListFromMappedObjQuery(getNurseConfirmationQuery);

            // get baby admission status
            String getTimings = "SELECT obj from BabyDetail obj where uhid='" + uhid + "' order by creationtime desc";
            List<BabyDetail> babyDetailList = settingDao.getListFromMappedObjNativeQuery(getTimings);

            if(babyDetailList!=null && babyDetailList.size()>0){
                admissionstatus = babyDetailList.get(0).getAdmissionstatus();
                viewBabyResponseObject.setAdmissionStatus(admissionstatus);
            }else{
                notInNicu = 1;
            }

            if (videoURL != null && videoURL.length()>0) {
                viewBabyResponseObject.setVideoAvailable(true);
                viewBabyResponseObject.setLiveVideoUrl(videoURL);
                viewBabyResponseObject.setStatusCode(200);
                viewBabyResponseObject.setMessage("Live Streaming Enabled!");
            } else {
                // Tiny is not connected to the baby
                // 204 data not available
                viewBabyResponseObject.setAdmissionStatus(false);
                viewBabyResponseObject.setVideoAvailable(false);
                viewBabyResponseObject.setLiveVideoUrl("");
                viewBabyResponseObject.setMessage("Camera is not connected, please contact hospital");
                viewBabyResponseObject.setStatusCode(204);
            }

            // if baby admission status is true then check for timings
            if(admissionstatus) {
                // get the Timings for the specified uhid
                String getTimingsQuery = "SELECT obj from BabyViewTimings obj where uhid='" + uhid + "' order by creationtime desc";
                List<BabyViewTimings> timingsList = settingDao.getListFromMappedObjNativeQuery(getTimingsQuery);

                if (timingsList.size() > 0) {
                    BabyViewTimings lastObject = timingsList.get(0);

                    int morningFromHour = 0;
                    int morningToHour = 0;

                    int morningFromMinute = 0;
                    int morningToMinute = 0;

                    int eveningFromHour = 0;
                    int eveningToHour = 0;

                    int eveningFromMinute = 0;
                    int eveningToMinute = 0;

                    // Morning time
                    String[] morningFromTime = lastObject.getMorningFromTime().split(",");
                    if (morningFromTime[2].equalsIgnoreCase("PM") && Integer.parseInt(morningFromTime[0]) != 12) {
                        morningFromHour = 12 + Integer.parseInt(morningFromTime[0]);
                        morningFromMinute = Integer.parseInt(morningFromTime[1]);
                    } else if (morningFromTime[2].equalsIgnoreCase("AM") && Integer.parseInt(morningFromTime[0]) == 12) {
                        morningFromHour = 0;
                        morningFromMinute = Integer.parseInt(morningFromTime[1]);
                    } else {
                        morningFromHour = Integer.parseInt(morningFromTime[0]);
                        morningFromMinute = Integer.parseInt(morningFromTime[1]);
                    }

                    String[] morningToTime = lastObject.getMorningToTime().split(",");
                    if (morningToTime[2].equalsIgnoreCase("PM") && Integer.parseInt(morningToTime[0]) != 12) {
                        morningToHour = 12 + Integer.parseInt(morningToTime[0]);
                        morningToMinute = Integer.parseInt(morningToTime[1]);
                    } else if (morningToTime[2].equalsIgnoreCase("AM") && Integer.parseInt(morningToTime[0]) == 12) {
                        morningToHour = 0;
                        morningToMinute = Integer.parseInt(morningToTime[1]);
                    } else {
                        morningToHour = Integer.parseInt(morningToTime[0]);
                        morningToMinute = Integer.parseInt(morningToTime[1]);
                    }


                    // Evening Time
                    String[] eveningFromTime = lastObject.getEveningFromTime().split(",");
                    if (eveningFromTime[2].equalsIgnoreCase("PM") && Integer.parseInt(eveningFromTime[0]) != 12) {
                        eveningFromHour = 12 + Integer.parseInt(eveningFromTime[0]);
                        eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
                    } else if (eveningFromTime[2].equalsIgnoreCase("AM") && Integer.parseInt(eveningFromTime[0]) == 12) {
                        eveningFromHour = 0;
                        eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
                    } else {
                        eveningFromHour = Integer.parseInt(eveningFromTime[0]);
                        eveningFromMinute = Integer.parseInt(eveningFromTime[1]);
                    }

                    String[] eveningToTime = lastObject.getEveningToTime().split(",");
                    if (eveningToTime[2].equalsIgnoreCase("PM") && Integer.parseInt(eveningToTime[0]) != 12) {
                        eveningToHour = 12 + Integer.parseInt(eveningToTime[0]);
                        eveningToMinute = Integer.parseInt(eveningToTime[1]);
                    } else if (eveningToTime[2].equalsIgnoreCase("AM") && Integer.parseInt(eveningToTime[0]) == 12) {
                        eveningToHour = 0;
                        eveningToMinute = Integer.parseInt(eveningToTime[1]);
                    } else {
                        eveningToHour = Integer.parseInt(eveningToTime[0]);
                        eveningToMinute = Integer.parseInt(eveningToTime[1]);
                    }

                    // get current Hour and Minutes
                    boolean flag = false;
                    Date date = new Date();
                    int offset = BasicUtils.getOffsetValue();
                    Timestamp currentTime = new Timestamp(date.getTime()+ offset);
                    int currentHour = currentTime.getHours();
                    int currentMinute = currentTime.getMinutes();

                    String currentHourStr = currentHour <10 ? "0"+currentHour : currentHour+"";
                    String currentMinuteStr = currentMinute <10 ? "0"+currentMinute : currentMinute+"";
                    LocalTime time = LocalTime.parse(currentHourStr+":"+currentMinuteStr);

                    // Morning From Time
                    String morningFromHourStr = morningFromHour <10 ? "0"+morningFromHour : morningFromHour+"";
                    String morningFromMinuteStr = morningFromMinute <10 ? "0"+morningFromMinute : morningFromMinute+"";
                    LocalTime startMorningTime = LocalTime.parse(morningFromHourStr+":"+morningFromMinuteStr);

                    String morningToHourStr = morningToHour <10 ? "0"+morningToHour : morningToHour+"";
                    String morningToMinuteStr = morningToMinute <10 ? "0"+morningToMinute : morningToMinute+"";
                    LocalTime endMorningTime = LocalTime.parse(morningToHourStr+":"+morningToMinuteStr);

                    // For Evening
                    String eveningFromHourStr = eveningFromHour <10 ? "0"+eveningFromHour : eveningFromHour+"";
                    String eveningFromMinuteStr = eveningFromMinute <10 ? "0"+eveningFromMinute : eveningFromMinute+"";
                    LocalTime starteveningTime = LocalTime.parse(eveningFromHourStr+":"+eveningFromMinuteStr);

                    String eveningToHourStr = eveningToHour <10 ? "0"+eveningToHour : eveningToHour+"";
                    String eveningToMinuteStr = eveningToMinute <10 ? "0"+eveningToMinute : eveningToMinute+"";
                    LocalTime endeveningTime = LocalTime.parse(eveningToHourStr+":"+eveningToMinuteStr);


                    System.out.println("Current time Value :"+time);
                    System.out.println("Current Hour :"+currentHour+" and Current Minute :"+currentMinute);
                    System.out.println("Morning Values:"+startMorningTime+" and "+ endMorningTime);
                    System.out.println("Evening Values:"+starteveningTime+" and "+ endeveningTime);

                    // check morning Hours
//					boolean inBetween = isBetween(time, LocalTime.of(morningFromHour, morningFromMinute), LocalTime.of(morningToHour, morningToMinute));
                    boolean inBetween = BasicUtils.isBetween(time,startMorningTime, endMorningTime);
                    if (inBetween){
                        flag = true;
                    }

                    // check evening Hours
                    inBetween = BasicUtils.isBetween(time, starteveningTime, endeveningTime);
                    if (inBetween){
                        flag = true;
                    }

                    if(flag){
                        System.out.println("Timing Matched !");
                        viewBabyResponseObject.setTimingMatching(true);
                        timingMatching = true;
                    }else if (flag == false){
                        // Out of scope access -> status code 403
                        System.out.println("Timing Not Matched !");
                        viewBabyResponseObject.setTimingMatching(false);
                        viewBabyResponseObject.setLiveVideoUrl("");
                        viewBabyResponseObject.setMessage(getViewTimingMessage(lastObject));
                        viewBabyResponseObject.setStatusCode(403);
                        timingMatching = false;
                    }
                }else{
                    viewBabyResponseObject.setStatusCode(304);
                    viewBabyResponseObject.setTimingMatching(false);
                    viewBabyResponseObject.setLiveVideoUrl("");
                    viewBabyResponseObject.setMessage("Kindly contact doctor to set your child view timings");
                }
            }
            else if (admissionstatus == false && notInNicu == -1){
                viewBabyResponseObject.setStatusCode(304);
                viewBabyResponseObject.setAdmissionStatus(false);
                viewBabyResponseObject.setTimingMatching(false);
                viewBabyResponseObject.setLiveVideoUrl("");
                viewBabyResponseObject.setMessage("Baby is discharged");
            }else if(notInNicu == 1){
                viewBabyResponseObject.setStatusCode(304);
                viewBabyResponseObject.setLiveVideoUrl("");
                viewBabyResponseObject.setTimingMatching(false);
                viewBabyResponseObject.setMessage("Baby is not in NICU");
            }

            if(nurseConfirmation!=null && nurseConfirmation.size()>0){

                BabyViewTimings babyViewTimings = nurseConfirmation.get(0);
                String confirmation = babyViewTimings.getNurseConfirmation();

                if(!BasicUtils.isEmpty(confirmation) && confirmation.length()>0) {

                    // Timing is matching but Nurse declined the confirmation
                    if (confirmation.equalsIgnoreCase("NO")) {
                        // Update the Message and set admission status to false
                        viewBabyResponseObject.setAdmissionStatus(true);
                        viewBabyResponseObject.setVideoAvailable(false);
                        viewBabyResponseObject.setTimingMatching(false);
                        viewBabyResponseObject.setLiveVideoUrl("");
                        viewBabyResponseObject.setMessage(staffBusyString);
                        viewBabyResponseObject.setStatusCode(204);
                    }

                    if (confirmation.equalsIgnoreCase("YES")) {
                        // DO Nothing if Confirmation is YES
                    }

                    if (confirmation.equalsIgnoreCase("DELAY")) {
                        String delayTime = babyViewTimings.getDelayTime();

                        String delayMessage = "Video is delayed by nurse. Please try after " +delayTime+".";

                        viewBabyResponseObject.setAdmissionStatus(true);

                        viewBabyResponseObject.setVideoAvailable(false);
                        viewBabyResponseObject.setTimingMatching(false);

                        viewBabyResponseObject.setLiveVideoUrl("");
                        viewBabyResponseObject.setMessage(delayMessage);
                        viewBabyResponseObject.setStatusCode(204);

                        babyViewTimings.setNurseConfirmation("");
                        babyViewTimings.setDelayTime("");
                        inicuDao.saveObject(babyViewTimings);
                    }

                    // update the confirmation value to null in the table

                    if(timingMatching == false) {
                        babyViewTimings.setNurseConfirmation("");
                        babyViewTimings.setDelayTime("");
                        inicuDao.saveObject(babyViewTimings);
                    }
                }
                else if(timingMatching == true){
                    viewBabyResponseObject.setAdmissionStatus(true);

                    viewBabyResponseObject.setTimingMatching(false);
                    viewBabyResponseObject.setVideoAvailable(false);

                    viewBabyResponseObject.setStatusCode(200);
                    viewBabyResponseObject.setMessage(nurseNotAnsweredString);
                }
            }
        }catch (Exception e){
            System.out.println("Caught Exception:"+e);
        }

        return viewBabyResponseObject;
    }

    public String getViewTimingMessage(BabyViewTimings lastObject) {

        String morningFromTimeMsgStr = BasicUtils.getTimeString(lastObject.getMorningFromTime());
        String morningToTimeMsgStr = BasicUtils.getTimeString(lastObject.getMorningToTime());

        String eveningFromTimeMsgStr = BasicUtils.getTimeString(lastObject.getEveningFromTime());
        String eveningToTimeMsgStr = BasicUtils.getTimeString(lastObject.getEveningToTime());

        String message = "Kindly view your baby remotely during ";

        if (!BasicUtils.isEmpty(lastObject.getMorningTimeEnabled()) && lastObject.getMorningTimeEnabled() == true) {
            message += morningFromTimeMsgStr + " to " + morningToTimeMsgStr + " in morning";
        }

        if (!BasicUtils.isEmpty(lastObject.getEveningTimeEnabled()) && lastObject.getEveningTimeEnabled() == true) {
            if (lastObject.getMorningTimeEnabled() == true) {
                message += " and ";
            }
            message += eveningFromTimeMsgStr + " to " + eveningToTimeMsgStr + " in evening";
        }
        message += ".";

        return message;
    }

    // remove the Job Running On ICHR if the baby get discharged
    public void deleteJobForDischargedBaby(String uhid){
        try {


            // remove the job running on iNICU Server also
            String jobName = "Job-"+uhid;
            pushNotificationService.deleteJob(jobName,"Notification");


            // remove the job running on ICHR server also

            System.out.println("Removing the Job Creating on the ICHR server");
            String companyId = BasicConstants.ICHR_SCHEMA;;
            String requestType = "deleteJobWithUhid";
            // now send the notification
//					String myNotificationParameters = "companyId="+companyId+"&message="+messageStr+"&uhid="+babyObject.getUhid();
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("companyId", companyId));
            postParameters.add(new BasicNameValuePair("uid", uhid));
            postParameters.add(new BasicNameValuePair("requestType", requestType));

            postParameters.add(new BasicNameValuePair("message", null));
            postParameters.add(new BasicNameValuePair("smsMessage", null));
            postParameters.add(new BasicNameValuePair("smsReminderMsg",null));
            postParameters.add(new BasicNameValuePair("jobName","simple" ));

            postParameters.add(new BasicNameValuePair("morningFromTime", null));
            postParameters.add(new BasicNameValuePair("morningToTime", null));
            postParameters.add(new BasicNameValuePair("eveningFromTime", null));
            postParameters.add(new BasicNameValuePair("eveningToTime", null));

            // now try making the post request using the ICHR android post method
            String ichrResponse = SimpleHttpClient.executeHttpPost(BasicConstants.PUSH_NOTIFICATION,postParameters);
            System.out.println("Notification Sent and response received is :"+ichrResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createJob(BabyViewTimings babyObject,String branchName){

        NotificationObject object = new NotificationObject();
        object.setUhid(babyObject.getUhid());
        object.setMorningTimeEnabled(babyObject.getMorningTimeEnabled());
        object.setEveningTimeEnabled(babyObject.getEveningTimeEnabled());
        object.setMorningFromTime(babyObject.getMorningFromTime());
        object.setEveningFromTime(babyObject.getEveningFromTime());
        object.setBranchName(branchName);

        // Get Baby Details by UHID
        synchronized (this) {
            pushNotificationService.createJob( object);
        }
    }

    private void updateJob(String uhid){

        // get the baby_view_timings for this baby
        String getQuery = "SELECT obj FROM BabyViewTimings obj WHERE uhid ='"+ uhid +"'";
        List<BabyViewTimings> babyViewTimingsList = inicuDao.getListFromMappedObjQuery(getQuery);
        BabyViewTimings babyObject  = babyViewTimingsList.get(0);

        NotificationObject object = new NotificationObject();
        object.setUhid(babyObject.getUhid());
        object.setMorningTimeEnabled(babyObject.getMorningTimeEnabled());
        object.setEveningTimeEnabled(babyObject.getEveningTimeEnabled());
        object.setMorningFromTime(babyObject.getMorningFromTime());
        object.setEveningFromTime(babyObject.getEveningFromTime());
        object.setBranchName(null);
        // Get Baby Details by UHID
        synchronized (this) {
            pushNotificationService.createJob(object);
        }
    }

    private String getBabyGestation(String uhid){
        String getQuery = "select gestationweekbylmp,actualgestationweek from baby_detail where uhid ='" + uhid + "' order by creationtime desc";
        List<Object[]> gestationList = inicuDao.getListFromNativeQuery(getQuery);

        String gestationWeek = "";
        if (gestationList != null && gestationList.size() > 0) {
            Object[] babyGestationObject = gestationList.get(0);
            if (babyGestationObject[0] != null && babyGestationObject[0].toString() != "") {
                gestationWeek = babyGestationObject[0].toString();
            } else if (babyGestationObject[1] != null && babyGestationObject[1].toString() != "") {
                gestationWeek = babyGestationObject[1].toString();
            }
        }
        return gestationWeek;
    }

    public int returnHour(String TimeStr){
        String[] timeStr = TimeStr.split(" ");
        int hour = 0;

        if (timeStr[1].equalsIgnoreCase("PM") && Integer.parseInt(timeStr[0]) != 12) {
            hour = 12 + Integer.parseInt(timeStr[0]);
        } else if (timeStr[1].equalsIgnoreCase("AM") && Integer.parseInt(timeStr[0]) == 12) {
            hour = 24;
        } else {
            hour = Integer.parseInt(timeStr[0]);
        }
        return hour;
    }

    public String getNurseUsername(List<NurseBabyMapping> list,String uhid){
        String name = "";
        for (NurseBabyMapping babyObject:list) {
            if(babyObject.getUhid().equalsIgnoreCase(uhid)){
                name = babyObject.getNurse()+"("+babyObject.getNurseUsername()+")";
            }
        }
        return name;
    }

    // Code Related to Tele Rounds -> Phase - 2
    @Override
    public GeneralResponseObject getTeleRoundsDetails(String branchName) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        String currentShift = "shift1";

        // get current Hour and Minutes
        Date date = new Date();
        String customDate = null;
        int offset = BasicUtils.getOffsetValue();
        Timestamp currentTime = new Timestamp(date.getTime()+ offset);
        int currentHour = currentTime.getHours();
        int currentMinute = currentTime.getMinutes();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        customDate = simpleDateFormat.format(date);

        // check the current shift as per the shift setting
        String getNurseShiftSettings = "SELECT obj FROM NurseShiftSettings as obj WHERE branchname ='"+ branchName +"'";
        List<NurseShiftSettings> nurseShiftSettingsList = inicuDao.getListFromMappedObjQuery(getNurseShiftSettings);
        if(!BasicUtils.isEmpty(nurseShiftSettingsList)){

            NurseShiftSettings nurseShiftSettings = nurseShiftSettingsList.get(0);

            // get the shift Type
            int shiftType = nurseShiftSettings.getShiftType();

            String currentHourStr = currentHour <10 ? "0"+currentHour : currentHour+"";
            String currentMinuteStr = currentMinute <10 ? "0"+currentMinute : currentMinute+"";
            LocalTime time = LocalTime.parse(currentHourStr+":"+currentMinuteStr);

            int morningFromHour = returnHour(nurseShiftSettings.getShift1From());
            int morningToHour =  returnHour(nurseShiftSettings.getShift1To());

            if(currentHour>= morningFromHour &&  currentHour<morningToHour){
                currentShift = "shift1";
            }

            int AfternoonFromHour = returnHour(nurseShiftSettings.getShift2From());
            int AfternoonToHour = returnHour(nurseShiftSettings.getShift2To());

            if(currentHour>= AfternoonFromHour &&  currentHour<AfternoonToHour){
                currentShift = "shift2";
            }

            if(shiftType == 3) {

                int eveningFromHour = returnHour(nurseShiftSettings.getShift3From());
                int eveningToHour = returnHour(nurseShiftSettings.getShift3To());

                if(currentHour>= eveningFromHour &&  currentHour<eveningToHour){
                    currentShift = "shift3";
                }
            }
        }

        if(!BasicUtils.isEmpty(currentShift) && currentShift.length() > 0){
//			String getNurseMapped = "SELECT obj FROM NurseBabyMapping as obj";
            String getNurseMapped = "SELECT obj FROM NurseBabyMapping as obj WHERE date(date_of_shift) = '" + customDate + "' and shift_type = '" + currentShift + "'";
            List<NurseBabyMapping> mappedNurseList = inicuDao.getListFromMappedObjQuery(getNurseMapped);

            if(!BasicUtils.isEmpty(mappedNurseList)){
                // get the Room Details where these particular nurses have been mapped

                String uhidListStr = "";

                for (NurseBabyMapping nurseBabyObject: mappedNurseList) {
                    if(uhidListStr == "") {
                        uhidListStr = "'"+nurseBabyObject.getUhid()+"'";
                    }else{
                        uhidListStr += ",'"+nurseBabyObject.getUhid()+"'";
                    }
                }


                String queryRoomName = "select a.uhid,b.roomname from baby_detail a inner join ref_room b" +
                        " on a.nicuroomno = b.roomid" +
                        " where a.uhid in (" + uhidListStr + ")";

                List<Object[]> roomList = inicuDao.getListFromNativeQuery(queryRoomName);

                // Create  a HashMap for saving nurse room wise
                Map<String,ArrayList<String>> nurseList = new HashMap<>();
                for (Object[] myObject: roomList) {

                    String uhid = myObject[0].toString();
                    String roomName = myObject[1].toString();

                    if(!BasicUtils.isEmpty(nurseList.get(roomName))){
                        ArrayList<String> tempList = nurseList.get(roomName);

                        // get nurse user name who has been assigned to baby with uhid
                        String nurseUsername = getNurseUsername(mappedNurseList,uhid);

                        if(!tempList.contains(nurseUsername)) {
                            tempList.add(nurseUsername);
                        }

                        nurseList.put(roomName,tempList);
                    }else{
                        ArrayList<String> tempList = new ArrayList<>();

                        // get nurse user name who has been assigned to baby with uhid
                        String nurseUsername = getNurseUsername(mappedNurseList,uhid);
                        tempList.add(nurseUsername);

                        nurseList.put(roomName,tempList);
                    }
                }

                generalResponseObject.setData(nurseList);
                generalResponseObject.setStatusCode(200);
                generalResponseObject.setStatus(true);
                generalResponseObject.setMessage("Room List");
            }
        }

        return generalResponseObject;
    }
}
