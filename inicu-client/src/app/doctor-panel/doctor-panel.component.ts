import { Component, NgModule, OnInit, Pipe, PipeTransform, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import {Http, Response} from '@angular/http';
import { APIurl } from '../../../model/APIurl';
import { DoctorPanelObj } from './doctorpanelObj';

@Component({
  selector: 'doctor-panel',
  templateUrl: './doctor-panel.component.html',
  styleUrls: ['./doctor-panel.component.css']
})
export class DoctorPanelComponent implements OnInit {
  child : any;
  apiData : APIurl;
  http: Http;
  sanitizer : DomSanitizer;
  displayMonitor : boolean;
  displayVentilator: boolean;
  displayBlood : boolean;
  timeOfBirthValue : string;
  gestWeek : string;
  gestDays : string;
  gestationDays : number;
  gestationWeeks : number;
  weeks : any;
  ageDays : number;
  cgaAgeDays : number;
  ageHours : any;
  changeValue : boolean;
  diff : number;
  lifeWeeks : number;
  ageMonth : number;
  weeksAfterDayAdd : number;
  totalDaysOfGestation : number;
  lifeDays : number;
  tempData : any;
  monitorDataInterval : any;
  hideViewDetails : boolean;
  doctorPanelObj : DoctorPanelObj;
  isLoaderVisible : boolean;
  loaderContent : string;
  constructor(http: Http, private router: Router, sanitizer : DomSanitizer) {
    this.apiData = new APIurl();
    this.doctorPanelObj = new DoctorPanelObj();
    this.http = http;
    this.sanitizer = sanitizer;
    this.changeValue = false;
    this.ageHours = null;
    this.isLoaderVisible = false;
    this.loaderContent = 'Loading...';
  }

  cameraURL = function(){
    return this.sanitizer.bypassSecurityTrustUrl('/cam/camera');
  }
  closeBabyVideo = function(){
			this.changeValue = false;
      localStorage.setItem('runVideo', JSON.stringify(false));
	}
  getData(){
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
			if(this.child.timeOfBirth !=null){
				this.timeOfBirthValue = this.child.timeOfBirth.replace(",", ":").replace(","," ");
				console.log("in the Time Of Birth");
			}
			if(this.child.gestation != null){
    			this.gestWeek = this.child.gestation.substr(0, 2);
        	this.gestDays = this.child.gestation.substr(9, 1);
    	}

			window.setInterval(function(){
        if(localStorage.getItem('selectedChild') != null){
          this.child = JSON.parse(localStorage.getItem('selectedChild'));
            if(this.child.gestation != null){
            	this.gestWeek = this.child.gestation.substr(0, 2);
                this.gestDays = this.child.gestation.substr(9, 1);
            }
          }
      }, 1000);
      this.calculateOtherDetails();
			// if(this.child.dob != null){
			// 	// this.this.diff = ;
	    //   // console.log(this.this.diff);
	    //   this.this.ageDays = parseInt(new Date() - Date.parse(this.child.dob) / (24 * 60 * 60 * 1000)) + 1 + '';
			// }

			// document.find("body").attr("class","");
      // this.userDetails= JSON.parse(localStorage.getItem('logedInUser'));
      // console.log("The User Details are");
      // console.log(this.userDetails);
      // this.settingRefObj = JSON.parse(localStorage.getItem('settingReference'));
      if(this.child.ecg == true){
        this.displayMonitor = true;
      }
      else{
        this.displayMonitor = false;
      }
      if(this.child.oxygen == true){
        this.displayVentilator = true;
      }
      else{
        this.displayVentilator = false;
      }
      if(this.child.blood == true){
        this.displayBlood = true;
      }
      else{
      	this.displayBlood = false;
      }
  }

  calculateOtherDetails = function() {
    try{
      var child = JSON.parse(localStorage.getItem('selectedChild'));
      var gestationString = child.gestation;
      console.log("calculate gestation and age");
      console.log(child.dob);
      console.log(child.timeOfBirth);

      var currentDateTime = new Date();
      var obj = JSON.parse(JSON.stringify(currentDateTime));
      var newDate = Date.parse(obj);
      var tempDate = new Date(newDate);
      tempDate.setHours(0);
      tempDate.setMinutes(0);

      this.diff = tempDate.getTime() - Date.parse(child.dob);

      this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
      this.diff = Math.ceil(this.diff);
      this.ageDays = parseInt(this.diff);

      var dateTimeBirth = new Date(child.dob);

      var tobStr = child.timeOfBirth.split(',');
      if(tobStr[2] == 'PM' && parseInt(tobStr[0]) != 12) {
        dateTimeBirth.setHours(12 + parseInt(tobStr[0]));
      } else if (tobStr[2] == 'AM' && parseInt(tobStr[0]) == 12){
          dateTimeBirth.setHours(0);
      } else {
          dateTimeBirth.setHours(parseInt(tobStr[0]));
      }
      dateTimeBirth.setMinutes(parseInt(tobStr[1]));

      if(gestationString) {
        var weeks = gestationString.split("Weeks");
        var days = gestationString.match("Weeks(.*)Days");
        this.gestationWeeks = parseInt(weeks[0]);
        this.gestationDays = parseInt(days[1]);
      }
      else {
        this.gestationWeeks = 0;
        this.gestationDays = 0;
      }

      if (this.ageDays > 7) {
        this.lifeWeeks = (this.ageDays / 7);
        this.lifeWeeks = parseInt(this.lifeWeeks);
        this.lifeDays = this.ageDays - this.lifeWeeks * 7;
        this.gestationWeeks += this.lifeWeeks;
        this.lifeDays = parseInt(this.lifeDays);
        this.totalDaysOfGestation = this.lifeDays + this.gestationDays;
        if (this.totalDaysOfGestation > 6) {

          this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
          this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
          this.gestationWeeks += parseInt(this.weeksAfterDayAdd + "");
          this.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");

        } else {
          this.gestationDays = parseInt(this.totalDaysOfGestation + "");
        }
      } else {
        this.totalDaysOfGestation = this.ageDays + this.gestationDays;
        if (this.totalDaysOfGestation > 6) {
          this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
          this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
          this.gestationWeeks += parseInt(this.weeksAfterDayAdd + "");
          this.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");

        } else {
          this.gestationDays = parseInt(this.totalDaysOfGestation + "");
        }
      }
      if(this.gestationDays == 0){
        this.gestationDays = 6;
        this.gestationWeeks = this.gestationWeeks - 1;
      }
      else{
        this.gestationDays = this.gestationDays - 1;
      }
      var currentDateTimeobj = JSON.parse(JSON.stringify(currentDateTime));
      var dateTimeBirthobj = JSON.parse(JSON.stringify(dateTimeBirth));
      currentDateTimeobj = Date.parse(currentDateTimeobj);
      currentDateTimeobj= new Date(currentDateTimeobj);
      dateTimeBirthobj = Date.parse(dateTimeBirthobj);
      dateTimeBirthobj = new Date(dateTimeBirthobj);
      currentDateTimeobj.setHours(0);
      currentDateTimeobj.setMinutes(0);
      dateTimeBirthobj.setHours(0);
      dateTimeBirthobj.setMinutes(0);

      //DOL should decrease according to time of birth. DOL should increase if it crosses the current time
      if(((currentDateTime.getTime() - currentDateTimeobj.getTime()) - (dateTimeBirth.getTime() - dateTimeBirthobj.getTime())) < 0){
        this.ageDays = this.ageDays - 1;
      }

      var diffHrs = (currentDateTime.getTime() - dateTimeBirth.getTime()) / (60 * 60 * 1000);

      var tempChild = JSON.parse(localStorage.getItem('selectedChild'));
      tempChild.dayOfLife = this.ageDays + " days";
      localStorage.setItem('selectedChild', JSON.stringify(tempChild));

      if(this.ageDays < 6) {
        this.ageHours = Math.round(diffHrs);
      }

    }
    catch(e){
      this.gestationWeeks = null;
      this.gestationDays = null;
      console.log(this.gestationWeeks);
      console.log(this.gestationDays);
      console.warn("Exception in initialising the controller auto initialise method: "+e);
    }
     localStorage.setItem("gestationDays",this.gestationDays);
     localStorage.setItem("gestationWeeks",this.gestationWeeks);
     localStorage.setItem("dol",this.ageDays);
  };

  setViewProfile(){
    	if(this.router.url === '/admis/view-profile'){

      }
      else{
  			var isEditProfile = true;
        this.isLoaderVisible = true;
        localStorage.setItem('isEditProfile', JSON.stringify(isEditProfile));
        this.router.navigateByUrl('/admis/view-profile');
      }
    }



  // below function is used to render the video
  // addListeners() {
  //   document.getElementById('pic').addEventListener('mousedown', mouseDown, false);
  //   window.addEventListener('mouseup', mouseUp, false);
  // }
  // mouseUp() {
  //   window.removeEventListener('mousemove', divMove, true);
  // }
  // mouseDown(){
  //   window.addEventListener('mousemove', divMove, true);
  // }
  // divMove(e){
  //   var div = document.getElementById('picDiv');
  //   div.style.position = 'fixed';
  //   div.style.top = e.clientY + 'px';
  //   div.style.left = e.clientX + 'px';
	// /* console.log(div.style.top +" " + e.clientY);
  //
	// console.log(div.style.left  + " " + e.clientX); */
  // }
  // addListeners();
  pushDeviceDataIntoMainChildObj = function(alarm){
      var obj1 = this.child;
      obj1.alarm = alarm;
      // if(alarm==true){
      //   var audio = new Audio('_audio/deviceDisconnectedAlram.ogg');
      //   if(!isNaN(audio.duration)){
      //     // audio file exists
      //     audio.play();
      //   }
      // }
      var obj2 = this.tempData;
      if(obj2 != undefined && obj2 != null){
        obj1.HR = obj2.heartrate;
        obj1.hr = obj2.heartrate;
        if(obj2.hr_rate != null && obj2.entrydate > obj2.starttime) {
          obj1.HR = obj2.hr_rate;
          obj1.hr = obj2.hr_rate;
        }
        obj1.SPO2 = obj2.spo2;
        obj1.spo2 = obj2.spo2;
        if(obj2.spo21 != null && obj2.entrydate > obj2.starttime) {
          obj1.SPO2 = obj2.spo21;
          obj1.spo2 = obj2.spo21;
        }

        obj1.RR = obj2.ecgResprate;
        obj1.rr = obj2.ecgResprate;
        obj1.PR = obj2.pulserate;
        obj1.pr = obj2.pulserate;
        obj1.deviceTime = obj2.entrydate;
        if((obj2.entrydate > obj2.starttime) && obj2.entrydate != null && obj2.entrydate != undefined){
          obj1.deviceTime = obj2.entrydate;
        }
        if((obj2.entrydate < obj2.starttime) && obj2.starttime != null && obj2.starttime != undefined){
          obj1.deviceTime = obj2.starttime;
        }
        obj1.sys_bp = obj2.sysBp;
        obj1.dia_bp = obj2.diaBp;
        obj1.mean_bp = obj2.meanBp;
        obj1.fio2 = obj2.fio2;
        obj1.pip = obj2.pip;
        obj1.peep = obj2.peep;
        obj1.temp = obj2.skintemp;
        if((obj1.deviceTime == null || obj1.deviceTime == undefined) && obj2.entrydate != null && obj2.entrydate != undefined){
          obj1.deviceTime = obj2.entrydate;
        }
        localStorage.setItem('selectedChild', JSON.stringify(this.child));
      }
  }
        changeOfRoutes = function(){
          this.isLoaderVisible = false;
          if(this.router.url==='/respiratory/assessment-sheet-respiratory' || this.router.url==='/jaundice/assessment-sheet-jaundice' || this.router.url==='/infection/assessment-sheet-infection' || this.router.url==='/cns/assessment-sheet-cns' || this.router.url==='/cardiac/assessment-sheet-cardiac' || this.router.url==='/metabolic/assessment-sheet-metabolic' || this.router.url==='/miscellaneous/assessment-sheet-miscellaneous'|| this.router.url === '/stable/assessment-sheet-stable' || this.router.url === '/renal/assessment-sheet-renal'){
            this.doctorPanelObj.whichDoctorPanelTab = 'assessment';
          }
          if(this.router.url==='/lab/lab-reports'){
            this.doctorPanelObj.whichDoctorPanelTab = 'labreport';
          }
          if(this.router.url==='/feed/nutrition'){
            this.doctorPanelObj.whichDoctorPanelTab = 'nutrition';
          }
          if(this.router.url==='/med/medications'){
            this.doctorPanelObj.whichDoctorPanelTab = 'medications';
          }
          if(this.router.url==='/blood/blood-product'){
            this.doctorPanelObj.whichDoctorPanelTab = 'bloodproduct';
          }
          if(this.router.url==='/trends-graph/trends'){
            this.doctorPanelObj.whichDoctorPanelTab = 'trends';
          }
          if(this.router.url==='/growth/growth-chart'){
            this.doctorPanelObj.whichDoctorPanelTab = 'growthchart';
          }
          if(this.router.url==='/screen/screening'){
            this.doctorPanelObj.whichDoctorPanelTab = 'screening';
          }
          if(this.router.url==='/out/outcomes'){
            this.doctorPanelObj.whichDoctorPanelTab = 'outcomes';
          }
          if(this.router.url==='/proceed/procedures'){
              this.doctorPanelObj.whichDoctorPanelTab = 'procedures';
          }
          if(this.router.url==='/summary/progress-notes'){
              this.doctorPanelObj.whichDoctorPanelTab = 'progress-notes';
          }
        }
        runVideo(){
          this.changeValue = true;
          localStorage.setItem('runVideo', JSON.stringify(true));
        }
        selectTab(selectedValue){
          this.isLoaderVisible = true;
            this.doctorPanelObj.whichDoctorPanelTab = selectedValue;
            if(this.doctorPanelObj.whichDoctorPanelTab == 'assessment'){
              this.router.navigateByUrl('/stable/assessment-sheet-stable');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'labreport'){
              this.router.navigateByUrl('/lab/lab-reports');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'nutrition'){
              this.router.navigateByUrl('/feed/nutrition');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'medications'){
              this.router.navigateByUrl('/med/medications');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'bloodproduct'){
              this.router.navigateByUrl('/blood/blood-product');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'procedures'){
              this.router.navigateByUrl('/proceed/procedures');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'progress-notes'){
              this.router.navigateByUrl('/summary/progress-notes');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'trends'){
              this.router.navigateByUrl('/trends-graph/trends');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'growthchart'){
              this.router.navigateByUrl('/growth/growth-chart');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'screening'){
              this.router.navigateByUrl('/screen/screening');
            }
            if(this.doctorPanelObj.whichDoctorPanelTab == 'outcomes'){
              this.router.navigateByUrl('/out/outcomes');
            }
            if(selectedValue == 'nursing-panel'){
              this.router.navigateByUrl('/anthropometry/nursing-anthropometry');
            }
        }
  ngOnInit() {
    this.changeOfRoutes();
    this.getData();
      this.monitorDataInterval = setInterval(() => {
      this.child = JSON.parse(localStorage.getItem('selectedChild'));
      if(this.child != null && this.child != undefined && this.child.uhid != null && this.child.uhid != undefined){
        try{
          this.http.request(this.apiData.getDashboardVitalDetailByUhid + this.child.uhid +"/").subscribe((res: Response) => {
            if(res.json() != undefined){
                this.tempData = res.json().vwVitalObj;
                this.pushDeviceDataIntoMainChildObj(res.json().alarm);
              }
            });
          }catch(e){
            console.log("Exception in http service:"+e);
          };
        }
        else{
          clearInterval(this.monitorDataInterval);
          }
        }, 60000);
      console.log('In the Doctor Pannel Component');
  }

}
