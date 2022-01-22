package com.inicu.postgres.BabyRemoteView;

import com.inicu.models.AudioResponse;
import com.inicu.models.GeneralResponseObject;
import com.inicu.models.PushNotification;
import com.inicu.postgres.Quartz.PushNotificationService;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyViewTimings;
import com.inicu.postgres.entities.RemoteViewPushNotifcation;
import com.inicu.postgres.utility.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class RemoteViewController {

    @Autowired
    RemoteViewServiceImpl remoteViewService;

    @Autowired
    InicuDao inicuDao;

    @Autowired
    SettingDao settingDao;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    private SimpMessagingTemplate template;

    // Phase 1
    @RequestMapping(value = "/inicu/updateNurseConfirmation/", method = RequestMethod.POST)
    public String updateNurseConfirmation(@RequestBody RemoteViewPushNotifcation pushNotification)  {
        // updated the response in the table
        remoteViewService.saveNurseConfirmation(pushNotification);
        sendRemoteViewNotification();
        return "Updated";
    }

    @RequestMapping(value ="/inicu/getRemoteViewPendingNotification/{branchname}",method = RequestMethod.GET)
    public GeneralResponseObject getPendingRemoteNotifications(@PathVariable("branchname") String branchName){
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try{
            generalResponseObject = remoteViewService.getPendingNotifications(branchName);
        }catch (Exception e){
            System.out.println("Exception Caught :"+e);
        }
        System.out.println("Okay I am here lets check the value:"+generalResponseObject.getData());
        return generalResponseObject;
    }

    @RequestMapping(value ="/inicu/remoteView/saveBabyViewTimings/{branchname}",method = RequestMethod.POST)
    public GeneralResponseObject saveBabyViewTimings(@RequestBody List<BabyViewTimings> babyViewTimingsList,
                                                     @PathVariable("branchname") String branchName){
        GeneralResponseObject obj=new GeneralResponseObject();
        try {
            obj = remoteViewService.saveBabyViewTimings(babyViewTimingsList,branchName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @RequestMapping(value = "/inicu/updateAudioStatus/", method = RequestMethod.POST)
    public String updateNurseConfirmation(@RequestBody AudioResponse audioResponse)  {
        // updated the response in the table
        String response = remoteViewService.saveAudioStatus(audioResponse);
        return response;
    }

    // Phase 2

    /**
     * get the staff list based on the room who has
     * been assigned shift on current day
     * Get the Room - Nurse Hashmap
     * */
    @RequestMapping(value = "/inicu/getTeleRoundsDetails/{branchname}",method = RequestMethod.GET)
    public ResponseEntity<GeneralResponseObject> getTeleRoundsDetails(@PathVariable("branchname") String branchName){
        GeneralResponseObject generalResponseObject = remoteViewService.getTeleRoundsDetails(branchName);
        return new ResponseEntity<GeneralResponseObject>(generalResponseObject, HttpStatus.OK);
    }


    // Cron Jobs Related Controller
    // Including the pending request
    @SendTo("/topic/pushNotification")
    @RequestMapping(value = "/inicu/sendRemoteViewPushNotification/", method = RequestMethod.GET)
    public String sendRemoteViewNotification() {
        String branchName = "Moti Nagar, Delhi";

        // get the pending nurse confirmation request
        String getRemoteViewNotifications = "SELECT obj FROM RemoteViewPushNotifcation as obj WHERE branchname = '"+branchName+"' and is_active = 'true' order by creationtime desc";
        List<RemoteViewPushNotifcation> remoteViewPushNotifcationList = inicuDao.getListFromMappedObjQuery(getRemoteViewNotifications);

        template.convertAndSend("/topic/pushNotification/"+branchName, remoteViewPushNotifcationList);
        return "Notifications successfully sent to Angular !";
    }

    // Send Single Push Notification
    @SendTo("/topic/pushNotification")
    @RequestMapping(value = "/inicu/sendCustomPushNotification/", method = RequestMethod.POST)
    public String sendCustomRemoteViewNotification(@RequestBody RemoteViewPushNotifcation remoteViewPushNotifcation) {
        String branchName = "Moti Nagar, Delhi";

        // get the pending nurse confirmation request
        List<RemoteViewPushNotifcation> remoteViewPushNotifcationList = new ArrayList<>();
        remoteViewPushNotifcationList.add(remoteViewPushNotifcation);

        template.convertAndSend("/topic/pushNotification/"+branchName, remoteViewPushNotifcationList);
        return "Notifications successfully sent to Angular !";
    }

    @RequestMapping(value = "/inicu/getRunningJobs/", method = RequestMethod.GET)
    public HashMap<String, String> getRunningJobs() {

        return pushNotificationService.getAllScheduledJobs();
    }

    @RequestMapping(value = "/inicu/deleteRunningJobs/{name}/{group}", method = RequestMethod.GET)
    public String deleteRunningJobs(@PathVariable("name") String name,@PathVariable("group") String group) {
        pushNotificationService.deleteJob(name,group);
        return "Deleted";
    }

    // UNDER PROGRESS -  INCOMPLETE
    @RequestMapping(value = "/inicu/getNurseConfirmation/", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponseObject> getNurseConfirmation(@RequestParam("uhid") String uhid,
                                                                      @RequestParam("phonenumber") String phonenumber,
                                                                      @RequestParam("otp") String otp) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();

        String nurseNotAssigned ="No Nurse is assigned to baby, Please Contact the hospital";
        String confirmMessage = "Confirmed!";
        String responseFalseMessage = "Remote view is denied by the nurse for now. Please try in sometime or contact the hospital.";

        // get the Current/ Today's Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDateObject = new Date(System.currentTimeMillis());
        String dateOfShift = sdf.format(currentDateObject);

        // Nurse Details
        String NurseAssigned = "";
        boolean nurseNotLoggedIn = false;
        boolean responsePending = true;
        BabyDetail babyDetail= null;
        try {
            // Get Baby Details by UHID
            String babyDetailQuery = "SELECT obj FROM BabyDetail obj WHERE uhid='"+uhid+"'";
            List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailQuery);

            if(babyDetailList.size()>0){
                babyDetail = babyDetailList.get(0);
            }

            RemoteViewPushNotifcation pushNotification = remoteViewService.getViewMessage(babyDetail,"Custom");

            // Now send the Request to the Browser for the confirmation - TO Do
            boolean responseReceived =  true;
            if(pushNotification.getMessage()!=null && pushNotification.getMessage().length()!=0){
                template.convertAndSend("/topic/pushNotification/"+babyDetail.getBranchname(), pushNotification);
            }else{
                responseReceived = false;
            }

            if(responseReceived == true){
                generalResponseObject = BasicUtils.getResonseObject(true,200,confirmMessage,null);
            }else{
                generalResponseObject = BasicUtils.getResonseObject(false,403,responseFalseMessage,null);
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<GeneralResponseObject>(generalResponseObject, HttpStatus.OK);
    }

}
