package com.inicu.postgres.controller;

import com.inicu.models.NotificationObject;
import com.inicu.postgres.Quartz.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SchedulerController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @RequestMapping(value = "/inicu/scheduler/createJob", method = RequestMethod.GET)
    public ResponseEntity<String> createJob(@RequestParam String name,
                                            @RequestParam String group,
                                            @RequestParam String description) {
        NotificationObject object = new NotificationObject();
//        pushNotificationService.createJob(name, description,group,object);
        return new ResponseEntity<String>("Job Created", HttpStatus.OK);
    }

    @RequestMapping(value ="/inicu/scheduler/deleteJob", method = RequestMethod.GET)
    public ResponseEntity<Void> deleteJob(@RequestParam String name, @RequestParam String group) {
        pushNotificationService.deleteJob(name,group);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/inicu/scheduler/findJob", method = RequestMethod.GET)
    public ResponseEntity<String> findJob(@RequestParam String name,
                                          @RequestParam String group) {
        pushNotificationService.findJob(name,group);
        return new ResponseEntity<String>("Checking", HttpStatus.OK);
    }

    @RequestMapping(value ="/inicu/scheduler/groups/updateJob",method = RequestMethod.GET)
    public ResponseEntity<Void> updateJob(@RequestParam String name,
                                          @RequestParam String group) {
//        pushNotificationService.updateJob(name,group);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/inicu/scheduler/pauseJob",method = RequestMethod.GET)
    public ResponseEntity<Void> pauseJob(@RequestParam String name, @RequestParam String group) {
        pushNotificationService.pauseJob(name,group);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value ="/inicu/scheduler/resumeJob",method =  RequestMethod.GET)
    public ResponseEntity<Void> resumeJob(@RequestParam String name, @RequestParam String group) {
        pushNotificationService.resumeJob(name,group);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @RequestMapping(value ="/inicu/scheduler/listRunningJobs",method =  RequestMethod.GET)
//    public ResponseEntity<Void> resumeJob(@RequestParam String name, @RequestParam String group) {
//        pushNotificationService.resumeJob(name,group);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
}
