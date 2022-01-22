package com.inicu.device.utility;

import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.entities.BedDeviceDetail;
import com.inicu.postgres.entities.DeviceDetail;
import com.inicu.postgres.entities.DeviceException;
import com.inicu.postgres.entities.VwVitalDetail;
import com.inicu.postgres.service.SettingsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class CheckDeviceExceptions extends Thread {

    @Autowired
    InicuDao inicuDao;

    @Autowired
    SettingsService settingsService;

    private final int timeout = 5;

    private Map<String, Boolean> babyStateMap;

    public CheckDeviceExceptions(){
        babyStateMap = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("["+BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: DEVICE EXCEPTION THREAD STARTS");
        while (true) {
            String sql = "SELECT v FROM VwVitalDetail v where branchname = '" + BasicConstants.BRANCH_NAME + "'";
            List<VwVitalDetail> dataList = inicuDao.getListFromMappedObjQuery(sql);

            int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
                    - TimeZone.getDefault().getRawOffset();

            if (offset != 0) {
                for (VwVitalDetail vitalObj : dataList) {
                    if (!BasicUtils.isEmpty(vitalObj.getEntrydate())) {
                        vitalObj.setEntrydate(new Timestamp(vitalObj.getEntrydate().getTime() - offset));
                    }
                }
            }

            HashMap<String, VwVitalDetail> returnMap = new HashMap<String, VwVitalDetail>();
            if (!BasicUtils.isEmpty(dataList)) {

                Iterator<VwVitalDetail> itr = dataList.iterator();

                while (itr.hasNext()) {
                    VwVitalDetail obj = itr.next();

                    String bedQuery = "SELECT b FROM BedDeviceDetail b where status = 'true' and time_to is null and uhid = '"
                            + obj.getUhid() + "'";
                    List<BedDeviceDetail> bedDeviceList = inicuDao.getListFromMappedObjQuery(bedQuery);

                    if (!BasicUtils.isEmpty(bedDeviceList)) {
                        for (int i = 0; i < bedDeviceList.size(); i++) {

                            String deviceId = bedDeviceList.get(i).getDeviceid();
                            String uhid = bedDeviceList.get(i).getUhid();

                            String deviceQuery = "SELECT d FROM DeviceDetail d where inicu_deviceid = '" + deviceId + "'";
                            List<DeviceDetail> deviceList = inicuDao.getListFromMappedObjQuery(deviceQuery);

                            if (!BasicUtils.isEmpty(deviceList)) {

                                String deviceType = deviceList.get(0).getDevicetype();
                                String deviceName = deviceList.get(0).getDeviceserialno();

                                String hashKey = uhid+"_"+deviceType+"_"+deviceName;

                                String exceptionsQuery = "SELECT e FROM DeviceException e where deviceserialno = '"
                                        + deviceName + "' order by creationtime desc";

                                List<DeviceException> exceptionList = inicuDao.getListFromMappedObjQuery(exceptionsQuery);
                                Timestamp currentDate = new Timestamp((new java.util.Date().getTime()));
                                currentDate = new Timestamp(currentDate.getTime() - (6 * 60 * 1000));

                                String exceptionMessage = "";
                                if (!BasicUtils.isEmpty(exceptionList)) {
                                    String[] exceptionParts = exceptionList.get(0).getExceptionmessage().split("##");
                                    if (deviceType.equalsIgnoreCase("Monitor") && !BasicUtils.isEmpty(exceptionParts)
                                            && exceptionParts.length > 1) {
                                        if (!BasicUtils.isEmpty(obj.getStarttime()) && (obj.getStarttime()
                                                .getTime() < (exceptionList.get(0).getCreationtime().getTime()))) {

                                            exceptionMessage = "Monitor : " + exceptionParts[0];

                                        } else if (obj.getStarttime() == null) {
                                            exceptionMessage = "Monitor : " + exceptionParts[0];


                                        } else if (!BasicUtils.isEmpty(obj.getStarttime())
                                                && (obj.getStarttime().getTime() < (currentDate.getTime()))) {

                                            exceptionMessage = "Monitor : TP Link is not working";

                                            if (!BasicUtils.isEmpty(obj.getMonitorlatesttime())
                                                    && (obj.getMonitorlatesttime().getTime() > (currentDate.getTime()))) {
                                                exceptionMessage = "Monitor - Previous data is getting updated";
                                            }
                                        }
                                    } else if (deviceType.equalsIgnoreCase("Ventilator")
                                            && !BasicUtils.isEmpty(exceptionParts) && exceptionParts.length > 1) {
                                        if (!BasicUtils.isEmpty(obj.getStart_time()) && (obj.getStart_time()
                                                .getTime() < (exceptionList.get(0).getCreationtime().getTime()))) {
                                            exceptionMessage = "Ventilator : " + exceptionParts[0];
                                        } else if (obj.getStart_time() == null) {
                                            exceptionMessage = "Ventilator : " + exceptionParts[0];
                                        } else if (!BasicUtils.isEmpty(obj.getStart_time())
                                                && (obj.getStart_time().getTime() < (currentDate.getTime()))) {
                                            exceptionMessage = "Ventilator : TP Link is not working";
                                            if (!BasicUtils.isEmpty(obj.getVentilatorlatesttime())
                                                    && (obj.getVentilatorlatesttime().getTime() > (currentDate.getTime()))) {
                                                exceptionMessage = "Ventilator : Previous data is getting updated";
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (deviceType.equalsIgnoreCase("Monitor")) {
                                        if (!BasicUtils.isEmpty(obj.getStarttime())
                                                && (obj.getStarttime().getTime() < (currentDate.getTime()))) {
                                            exceptionMessage = "Monitor : TP Link is not working";

                                            if (!BasicUtils.isEmpty(obj.getMonitorlatesttime())
                                                    && (obj.getMonitorlatesttime().getTime() > (currentDate.getTime()))) {
                                                exceptionMessage = "Monitor - Previous data is getting updated";
                                            }
                                        } else if (obj.getStarttime() == null) {
                                            exceptionMessage = "Monitor : TP Link is not working";
                                        }


                                    } else if (deviceType.equalsIgnoreCase("Ventilator")) {
                                        if (!BasicUtils.isEmpty(obj.getStart_time())
                                                && (obj.getStart_time().getTime() < (currentDate.getTime()))) {
                                            exceptionMessage = "Ventilator : TP Link is not working";
                                            if (!BasicUtils.isEmpty(obj.getVentilatorlatesttime())
                                                    && (obj.getVentilatorlatesttime().getTime() > (currentDate.getTime()))) {
                                                exceptionMessage = "Ventilator : Previous data is getting updated";
                                            }
                                        } else if (obj.getStart_time() == null) {
                                            exceptionMessage = "Ventilator : TP Link is not working";
                                        }
                                    }
                                }

                                boolean mailToSend = true;

                                // Check if the status is same as before then don't send the mail again
                                if(!BasicUtils.isEmpty(babyStateMap.get(hashKey))) {
                                    boolean currentState = babyStateMap.get(hashKey);

                                    // CASE - 1 ( status is same don't send the mail)
                                    if (!exceptionMessage.equalsIgnoreCase("") && currentState == true) {
                                        mailToSend = false;
                                    }

                                    // Case -2 -> status is false then again the exception occured send the mail
                                    if (!exceptionMessage.equalsIgnoreCase("") && currentState == false) {
                                        mailToSend = true;
                                        babyStateMap.put(hashKey,true);
                                    }

                                    if (exceptionMessage.equalsIgnoreCase("")  && currentState == true) {
                                        babyStateMap.put(hashKey,false);
                                        mailToSend = false;
                                    }
                                }else if (!exceptionMessage.equalsIgnoreCase("")){
                                    // CASE - 1 -> the map does not contains the key insert in the Map and sent the mail
                                    babyStateMap.put(hashKey,true);
                                    mailToSend = true;
                                }

                                // check if the exception message is not null
                                if (!BasicUtils.isEmpty(exceptionMessage) && (exceptionMessage.indexOf("Previous") == -1) && mailToSend) {
                                    System.out.println("Current State Of the Map ----->");
                                    babyStateMap.forEach((key,value)-> System.out.println("Key :"+key+", Value :"+value));
                                    System.out.println("Current State Updated ----->");


                                    String babyName = "";
                                    // get the name of the baby
                                    String babyNameQuery = "select babyname,baby_type,baby_number from baby_detail where uhid ='" + uhid + "' order by creationtime desc";
                                    List<Object[]> nameList = inicuDao.getListFromNativeQuery(babyNameQuery);

                                    if(!BasicUtils.isEmpty(nameList) && nameList.size()>0){
                                          Object[] nameObject = nameList.get(0);
                                          if(!BasicUtils.isEmpty(nameObject[0])){
                                              babyName += nameObject[0].toString();
                                          }

                                        if(!BasicUtils.isEmpty(nameObject[1])){
                                            if(babyName.length()>0) babyName+=" ";
                                            babyName += nameObject[1].toString();
                                        }

                                        if(!BasicUtils.isEmpty(nameObject[2])){
                                            if(babyName.length()>0) babyName+=" ";
                                            babyName += nameObject[2].toString();
                                        }
                                    }

                                    String subject = "Device status ("+ uhid + ")";
                                    String message = "<p>Kindly check the connection for the below device: <br><br>"

                                            + "UHID: " + uhid + "<br>";

                                            if(!BasicUtils.isEmpty(babyName)){
                                                message += "Baby Name: " + babyName +"<br>";
                                            }

                                             message += "Exception: " + exceptionMessage + "<br>"
                                            + "Device Id: " + deviceId + "<br>"
                                            + "Device Name: " + deviceName + "<br>"
                                            + "Device Type: " + deviceType + "<br>"
                                            + "<br><br>"
                                            + "Regards<br>"
                                            + "\n iNICU Team</p>";

                                    System.out.println("<------------------ Sending Device Exception E-Mail ----------------->");
                                    settingsService.sendCustomEmail(subject, message, BasicConstants.BRANCH_NAME,BasicConstants.DEVICE_EXCEPTION);
                                    System.out.println("<------------------ Mail Sent! ----------------->");
                                }

                            }  // end of device list

                        }  // end of bedDevices loop
                    }
                }
            }
            try {
                // Wait for 5 Minutes
                System.out.println("["+BasicConstants.timeStampFormat.format(new Timestamp(System.currentTimeMillis())) + "] :: DEVICE EXCEPTION THREAD SLEEPS");
                Thread.sleep(Integer.valueOf(timeout) * 60000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
