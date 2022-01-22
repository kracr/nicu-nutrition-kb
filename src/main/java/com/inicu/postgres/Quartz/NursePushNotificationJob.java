package com.inicu.postgres.Quartz;

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.RemoteViewPushNotifcation;
import com.inicu.postgres.BabyRemoteView.RemoteViewServiceImpl;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NursePushNotificationJob implements Job {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RemoteViewServiceImpl remoteViewService;

    @Autowired
    private InicuDao inicuDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            System.out.println("[ ---------------  Executing JOB Started ------------------ ]");

            // Initially create topic using the branchname
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String uhid = dataMap.getString("uhid");

            // Get Baby Details by UHID
            String babyDetailQuery = "SELECT obj FROM BabyDetail obj WHERE uhid='"+uhid+"'";
            List<BabyDetail> babyDetailList = inicuDao.getListFromMappedObjQuery(babyDetailQuery);
            String branchName = babyDetailList.get(0).getBranchname().trim();

            // get the Baby Detail Object
            RemoteViewPushNotifcation pushNotification = remoteViewService.getViewMessage(babyDetailList.get(0),"Custom");
            pushNotification.setBranchname(branchName);
            pushNotification.setActive(true);

            // save the notification before triggering the event
            if(pushNotification.isMorningEnabled() || pushNotification.isEveningEnabled()){
                inicuDao.saveObject(pushNotification);

                System.out.println(pushNotification.getMessage());
                template.convertAndSend("/topic/pushNotification/"+branchName, pushNotification);
            }
            System.out.println("[ -------------------- Executing JOB Ended ---------------]");
        }catch (Exception e){
            System.out.println("Caught the Exception :"+e.getMessage());
            e.printStackTrace();
        }
    }
}


