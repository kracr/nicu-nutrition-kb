package com.inicu.postgres.controller;

import com.google.gson.JsonObject;
import com.inicu.models.GeneralResponseObject;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.TwilioRoom;
import com.inicu.postgres.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TwilioController {

    @Autowired
    InicuDao inicuDao;

    @Autowired
    TwilioService twilioService;


    @RequestMapping(value = "inicu/getTwilioToken/{username}/{branchName}/{tokenType}", method = RequestMethod.GET)
    GeneralResponseObject getTwilioToken(@PathVariable("username") String username,
                                         @PathVariable("branchName") String branchName,
                                         @PathVariable("tokenType") String tokenType){
        GeneralResponseObject generalResponseObject = twilioService.getUserToken(username,branchName,tokenType);
        return  generalResponseObject;
    }

    @RequestMapping(value = "inicu/createTwilioRoom/", method = RequestMethod.POST)
    GeneralResponseObject createRoom(@RequestBody TwilioRoom twilioRoom){
        GeneralResponseObject generalResponseObject = twilioService.createRoom(twilioRoom);
        return  generalResponseObject;
    }


    @RequestMapping(value = "inicu/updateTwilioRoom/", method = RequestMethod.GET)
    GeneralResponseObject updateRoomStatus(@RequestBody TwilioRoom twilioRoom){
        GeneralResponseObject generalResponseObject = twilioService.updateRoomStatus(twilioRoom);
        return  generalResponseObject;
    }


    @RequestMapping(value = "inicu/sentReminderMail/", method = RequestMethod.POST)
    GeneralResponseObject sendMailReminder(@RequestBody String mailObject){
        GeneralResponseObject generalResponseObject = twilioService.sendMailReminderToStaff(mailObject);
        return  generalResponseObject;
    }

    @RequestMapping(value = "inicu/updateNurseVideoResponse/", method = RequestMethod.POST)
    GeneralResponseObject updateNurseVideoResponse(@RequestBody TwilioRoom roomObject){
        GeneralResponseObject generalResponseObject = twilioService.updatedNurseResponse(roomObject);
        return  generalResponseObject;
    }



}
