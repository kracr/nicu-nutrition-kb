package com.inicu.postgres.BabyRemoteView;

import com.inicu.models.AudioResponse;
import com.inicu.models.GeneralResponseObject;
import com.inicu.models.ViewBabyResponseObject;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyViewTimings;
import com.inicu.postgres.entities.RemoteViewPushNotifcation;

import java.util.List;

public interface RemoteView {

     GeneralResponseObject saveBabyViewTimings(List<BabyViewTimings> babyViewTimingsList, String branchName);

     void saveNurseConfirmation(RemoteViewPushNotifcation pushNotification);
     RemoteViewPushNotifcation getViewMessage(BabyDetail babyDetail, String messageTyp);

     String saveAudioStatus(AudioResponse audioResponse);

     GeneralResponseObject getTeleRoundsDetails(String branchname);

    GeneralResponseObject getPendingNotifications(String branchname);

    ViewBabyResponseObject getLiveVideoMessage(String uhid, String videoUrl);

}
