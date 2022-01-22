package com.inicu.postgres.Quartz;

import com.google.gson.JsonObject;
import com.inicu.models.NotificationObject;
import com.inicu.models.PushNotification;
import com.inicu.postgres.utility.BasicUtils;
import org.quartz.*;
import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import static java.time.ZoneId.systemDefault;
import static org.hibernate.jpa.internal.QueryImpl.LOG;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.quartz.CronExpression.isValidExpression;


import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

@Component
public class PushNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(PushNotificationService.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private Scheduler scheduler;

    private static String GroupId = "Notification";

    private static final String CRON_EVERY_TWO_MINUTES = "0 0/3 * ? * * *";

    @PostConstruct
    private void init() {
        scheduler = schedulerFactoryBean.getScheduler();
        try{
            if(scheduler!=null) {
                System.out.println("Scheduler not null");
                scheduler.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String createJob(NotificationObject dataObject) {
        try {

            if (scheduler == null) {
                scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
                scheduler.start();
            }else{
                System.out.println("Scheduler already present in the application context");
            }

            // Unschedule a particular trigger from the job (a job may have more than one trigger)
//            scheduler.unscheduleJob(triggerKey("trigger1", "group1"));

            // Schedule the job with the trigger
//            scheduler.deleteJob(jobKey("job1", "group1"));

            // print all the running jobs and trigger and group name
            getAllScheduledJobs();

            String triggerName = "Trigger-" + dataObject.getUhid();
            String jobName = "Job-" + dataObject.getUhid();

            JobKey jobKey = new JobKey(jobName, GroupId);
            TriggerKey triggerKey = new TriggerKey(triggerName, GroupId);



            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("uhid", dataObject.getUhid());
            jobDataMap.put("branchName", dataObject.getBranchName());
            String jobDescription = "Job Created for "+dataObject.getUhid();

            JobDetail jobDetail = newJob().ofType(NursePushNotificationJob.class)
                    .storeDurably()
                    .withIdentity(jobKey)
                    .usingJobData(jobDataMap)
                    .withDescription(jobDescription)
                    .build();

            String cronTime = getCronTimeString(dataObject);
            logger.debug("[ Cron Created  :" + cronTime +" ]");

            Trigger trigger;
            if(scheduler.checkExists(triggerKey)) {
                scheduler.unscheduleJob(triggerKey);
            }

            trigger = getTrigger(triggerKey,cronTime);

            // delete the job if its already running
            deleteJob(jobKey);

            if(jobDetail!=null && trigger!=null) {
                scheduler.scheduleJob(jobDetail, trigger);
                System.out.println("Scheduling Job Done");
            }else{
                System.out.println("Something Wrong Please Check");
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "Job Created";
    }

    public String findJob(String name, String group) {
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.getJobDetail(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Found the job";
    }

    public void deleteJob(JobKey jobKey) {
        try {
            if (scheduler == null) {
                scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
                scheduler.start();
            }

            if(scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
                System.out.println("Job deleted");
            }else {
                System.out.println("No Job with name :"+jobKey.getName()+" present/running");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateJob(String uhid) {
        try {
            if (scheduler == null) {
                scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
                scheduler.start();
            }

            String name = "Job-"+uhid;
            JobKey jobKey = new JobKey(name, GroupId);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (jobDetail != null) {
                scheduler.addJob(jobDetail, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(String name, String group) {
        // delete the existing job
        try {
            if (scheduler == null) {
                scheduler = new org.quartz.impl.StdSchedulerFactory().getScheduler();
                scheduler.start();
            }

            System.out.println("Job Deleting");
            JobKey jobKey = new JobKey(name, group);
            System.out.println("JOB KEY :"+jobKey);
            scheduler.deleteJob(jobKey);
            System.out.println("Job Deletion Done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseJob(String name, String group) {
        // pause Job
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resumeJob(String name, String group) {
        // resume Job
        try {
            JobKey jobKey = new JobKey(name, group);
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Trigger getTrigger(TriggerKey triggerName, String cronJonTime) {
        try {
            System.out.println("Creating Trigger for Cron Time :"+cronJonTime);
            if (!isValidExpression(cronJonTime))
                throw new IllegalArgumentException("Provided expression " + cronJonTime + " is not a valid cron expression");
            return newTrigger()
                    .withIdentity(triggerName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronJonTime)
                            .inTimeZone(TimeZone.getTimeZone(systemDefault())))
                    .usingJobData("cronTime", cronJonTime)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCronTimeString(NotificationObject dataObject) {

        String cronJonTime = "";
        int currentEveningHour = 0;
        int currentEveningMinute = 0;
        int currentHour = 0;
        int currentMinute = 0;

        // For Demo and Production server
        int offset = BasicUtils.getOffsetValue();

        // For Local Testing
//        int offset = 0;

        if (dataObject.getMorningTimeEnabled() && dataObject.getMorningTimeEnabled() == true){
            String[] time = dataObject.getMorningFromTime().split(",");
            int hour = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            String meridian = time[2];

            if (meridian.equalsIgnoreCase("PM")) {
                // to convert this into 24 hour format
                if (hour == 12) {
                    hour = 12;
                } else {
                    hour = hour + 12;
                }
            } else if (meridian.equalsIgnoreCase("AM")) {
                if (hour == 12) {
                    hour = 0;
                }
            }

            Date date = new Date();
            date.setHours(hour);
            date.setMinutes(minutes);
            Timestamp currentTime = new Timestamp(date.getTime() - offset);

            currentHour = currentTime.getHours();
            currentMinute = currentTime.getMinutes();

            logger.debug("[ Current Morning Hour and Minute is :" + currentHour + " and " + currentMinute+" ]");
        }

        if (dataObject.getEveningTimeEnabled() != null && dataObject.getEveningTimeEnabled() == true) {
            String[] eveningTimeStr = dataObject.getEveningFromTime().split(",");

            int eveningHour = Integer.parseInt(eveningTimeStr[0]);
            int eveningMinutes = Integer.parseInt(eveningTimeStr[1]);
            String eveningTimeTypeStr = eveningTimeStr[2];

            if (eveningTimeTypeStr.equalsIgnoreCase("PM")) {

                // to convert this into 24 hour format
                if (eveningHour == 12) {
                    eveningHour = 12;
                } else {
                    eveningHour = eveningHour + 12;
                }
            } else if (eveningTimeTypeStr.equalsIgnoreCase("AM")) {
                if (eveningHour == 12) {
                    eveningHour = 0;
                }
            }

            Date nextdate = new Date();
            nextdate.setHours(eveningHour);
            nextdate.setMinutes(eveningMinutes);

            Timestamp currentEveningTime = new Timestamp(nextdate.getTime() - offset);
            currentEveningHour = currentEveningTime.getHours();
            currentEveningMinute = currentEveningTime.getMinutes();

            logger.debug("[ Current Evening Hour and Minute is :" + currentEveningHour + " and " + currentEveningMinute+" ]");
        }

        // update the cronJob Time as per the time received
        if(dataObject.getEveningTimeEnabled() && dataObject.getMorningTimeEnabled()) {
            cronJonTime = "0 " + currentMinute + "," + currentEveningMinute + " " + currentHour + "," + currentEveningHour + " * * ?";
        }else if (dataObject.getEveningTimeEnabled()) {
            cronJonTime = "0 " + currentEveningMinute +" "+currentEveningHour + " * * ?";
        } else if (dataObject.getMorningTimeEnabled()) {
            cronJonTime = "0 " + currentMinute +" "+currentHour + " * * ?";
        }
        return cronJonTime;
    }

    @PreDestroy
    public void preDestroy() throws SchedulerException {
        scheduler.shutdown(true);
    }

     public HashMap<String,String> getAllScheduledJobs(){
        HashMap<String,String> jobNames = new HashMap<>();
        try {
            System.out.println("[ ----------  Print All the Running Jobs ------------ ]");
            for (String groupName : scheduler.getJobGroupNames()) {

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date nextFireTime = triggers.get(0).getNextFireTime();

                    System.out.println("[JobKey] :"+jobKey);
                    System.out.println("[jobName] : " + jobName + " [groupName] : "
                            + jobGroup + " - " + nextFireTime);
                    jobNames.put(jobName,"Job Name : "+jobName+"| Job Key : "+jobKey.toString()+"| Next Fire Time: "+nextFireTime);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobNames;
    }

    public void createTestJob(){
        try {
            JobDetail job = newJob(NursePushNotificationJob.class).withIdentity("Id1", "Rome").build();
            Trigger trigger = newTrigger()
                    .withIdentity("Test Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(CRON_EVERY_TWO_MINUTES)
                            .withMisfireHandlingInstructionFireAndProceed()
                            .inTimeZone(TimeZone.getTimeZone(systemDefault())))
                    .usingJobData("cronTime", CRON_EVERY_TWO_MINUTES)
                    .build();

        scheduler.scheduleJob(job, trigger);
        Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}