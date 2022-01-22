package com.inicu.postgres.service;

import com.google.gson.JsonObject;
import com.inicu.models.GeneralResponseObject;
import com.inicu.postgres.entities.TwilioRoom;
import org.json.JSONObject;

public interface TwilioService {

    public GeneralResponseObject getUserToken(String username,String branchName,String tokenType);
    public GeneralResponseObject createRoom(TwilioRoom twilioRoom);
    public GeneralResponseObject updateRoomStatus(TwilioRoom twilioRoom);
    public GeneralResponseObject joinRoom(TwilioRoom twilioRoom);
    public GeneralResponseObject sendMailReminderToStaff(String mailObject);
    public GeneralResponseObject updatedNurseResponse(TwilioRoom twilioRoom);
}


