package com.inicu.postgres.serviceImpl;

import com.google.gson.JsonObject;
import com.inicu.models.GeneralResponseObject;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.TwilioRoom;
import com.inicu.postgres.service.TwilioService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.SimpleHttpClient;
import com.twilio.Twilio;
import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VideoGrant;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TwilioServiceImpl implements TwilioService {


    @Autowired
    InicuDao inicuDao;

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @param username: Name of the user
     * @param  branchName : Branch Name
     * @param  tokenType : Room Create Token | Room Join Token
     * */
    @Override
    public GeneralResponseObject getUserToken(String username, String branchName, String tokenType) {

        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try {

            // Required for all types of tokens
            String twilioAccountSid = BasicConstants.TWILIO_SID;
            String twilioApiKey = BasicConstants.TWILIO_API_KEY;
            String twilioApiSecret = BasicConstants.TWILIO_SECRET_KEY;

            // Required for Video
            String identity = username + "-" + branchName;

            // Create Video grant
            VideoGrant grant = new VideoGrant().setRoom("cool room");

            // Create access token
            AccessToken token = new AccessToken.Builder(
                    twilioAccountSid,
                    twilioApiKey,
                    twilioApiSecret
            ).identity(identity).grant(grant).build();

            System.out.println(token.toJwt());

            generalResponseObject = BasicUtils.getResonseObject(true,200,"Token Generated",token.toJwt());
        }catch (Exception e){
            e.printStackTrace();

            generalResponseObject = BasicUtils.getResonseObject(false,500,"Internal Server Error",null);
        }

        return generalResponseObject;
    }

    /**
     * @param twilioRoom : Twilio Room Object
     * */
    @Override
    public GeneralResponseObject createRoom(TwilioRoom twilioRoom) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try
            {

            twilioRoom.setActive(true);
            Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
            twilioRoom.setCreatedAt(currentDateTime);

            twilioRoom = (TwilioRoom) inicuDao.saveObject(twilioRoom);

            // get the updated Twilio Room Object
            String getRoomObject  = "SELECT obj FROM TwilioRoom as obj WHERE username ='"+twilioRoom.getUserName()+"' AND isactive = 'true' order by creationtime desc";
            List<TwilioRoom> twilioRoomList = inicuDao.getListFromMappedObjQuery(getRoomObject);

            TwilioRoom twilioRoom1= null;
            if(twilioRoomList.size()>0){
                twilioRoom1= twilioRoomList.get(0);
            }
            // get the
            generalResponseObject = BasicUtils.getResonseObject(true,200,"Room Created",twilioRoom1);

            // now fire the event for other users to join the room
            template.convertAndSend("/topic/videoCall/"+twilioRoom.getParticipantName(), twilioRoom1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return generalResponseObject;
    }

    /**
     * @param twilioRoom : Twilio Room Object
     * */
    @Override
    public GeneralResponseObject updateRoomStatus(TwilioRoom twilioRoom) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();

        try{
            twilioRoom.setActive(false);
            inicuDao.saveObject(twilioRoom);
            generalResponseObject = BasicUtils.getResonseObject(true,200,"Room Status Updated",null);

            // update the room status on the server so that no other user can join the completed room
//            Twilio.init(BasicConstants.TWILIO_SID, BasicConstants.TWILIO_AUTH_KEY);
//            Room room = Room.updater(
//                    twilioRoom.getRoomid(),    Room.RoomStatus.COMPLETED)
//                    .update();
//
//            System.out.println(room.getUniqueName());

        }catch (Exception e){
            e.printStackTrace();
        }

        return generalResponseObject;
    }


    /**
    * Join the Existing Room using the rooom object
    * @param TwilioRoom : Room Object
    * */
    @Override
    public GeneralResponseObject joinRoom(TwilioRoom twilioRoom) {
        return null;
    }

    /**
     *  Send the Reminder Email to stafff selected
     * @param TwilioRoom : Room Object
     * */
    @Override
    public GeneralResponseObject sendMailReminderToStaff(String stringToParse) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();
        try {
            JSONObject jsonObject = new JSONObject(stringToParse);

            String callName = jsonObject.get("callerName").toString();
            String receiverUsername = jsonObject.get("receivedName").toString();
            String responseMessage  = "";

            // get the user phonenumber to send the Text SMS
            String getNumber = "select mobile from inicuuser where username = '" + receiverUsername + "'";
            List<BigInteger> numberList = inicuDao.getListFromNativeQuery(getNumber);

            if(!BasicUtils.isEmpty(numberList) && numberList.size()>0 && !BasicUtils.isEmpty(numberList.get(0))){
                String number = numberList.get(0).toString();
                StringBuilder messageToSend = new StringBuilder();
                messageToSend.append("Hi, Dr. ");
                messageToSend.append(callName);
                messageToSend.append(" will Start Tele-Rounds in next 10 minutes. Please make sure you are online.");

                // according to WebXion Standard
                System.out.println("inside new webxion sms gateway");
                String webxionApiKey = "LgXzs9fgM0Kt5QFhYCV5ug";
                String senderID = "ICHRIN";
                String apiKey = "APIKey=" + URLEncoder.encode(webxionApiKey, "UTF-8");
                String sender = "&senderid=" + URLEncoder.encode(senderID, "UTF-8");
                String dcs = "&DCS=0";
                String misc = "&channel=2" + dcs + "&flashsms=0";
                String numbers = "&number=" + URLEncoder.encode(number, "UTF-8");

                String message = "&text=" + URLEncoder.encode(messageToSend.toString(), "UTF-8");
                String route = "&route=13";

                String dataWeb = "http://sms.ichrcloud.com/api/mt/SendSMS?" + apiKey + sender + misc + numbers + route + message;

                Thread t1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("<------------ Sending Message ----------------->");
                            SimpleHttpClient.executeHttpGet(dataWeb);
                            System.out.println("<------------ Message Sent! ----------------->");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t1.start();
                responseMessage = "Reminder Message Sent!";
            }else{
                responseMessage = "Mobile Number not available for selected staff";
            }

            generalResponseObject.setMessage("Mail Sent");
            generalResponseObject.setStatus(true);
            generalResponseObject.setStatusCode(200);
            generalResponseObject.setData(responseMessage);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            generalResponseObject.setMessage("Internal Server Error");
            generalResponseObject.setStatus(false);
            generalResponseObject.setStatusCode(500);
            generalResponseObject.setData("Failed to sent the SMS");
        }

        return generalResponseObject;
    }

    @Override
    public GeneralResponseObject updatedNurseResponse(TwilioRoom twilioRoom) {
        GeneralResponseObject generalResponseObject = new GeneralResponseObject();

        try{
            if(twilioRoom.getCallAccepted()) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                twilioRoom.setAcceptedTimestamp(currentTimestamp);
            }
            inicuDao.saveObject(twilioRoom);
            generalResponseObject = BasicUtils.getResonseObject(true,200,"Reponse Saved",null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return generalResponseObject;
    }
}
