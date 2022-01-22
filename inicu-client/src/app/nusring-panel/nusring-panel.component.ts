import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Http, Response} from '@angular/http';
import { APIurl } from '../../../model/APIurl';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'nusring-panel',
  templateUrl: './nusring-panel.component.html',
  styleUrls: ['./nusring-panel.component.css']
})
export class NusringPanelComponent implements OnInit {
  child : any;
  apiData : APIurl;
  http: Http;
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
  ageHours : any;
  diff : number;
  lifeWeeks : number;
  ageMonth : number;
  weeksAfterDayAdd : number;
  totalDaysOfGestation : number;
  lifeDays : number;
  tempData : any;
  monitorDataInterval : any;
  hideViewDetails : boolean;
  whichNursingPanelTab : string;
  sanitizer : DomSanitizer;
  changeValue : boolean;
  isNurse : boolean;
  isLoaderVisible : boolean;
  loaderContent : string;
  ageDaysdiff : number;
  cgaAgeDays : number;
  constructor(http: Http, private router: Router, sanitizer : DomSanitizer) {
    this.apiData = new APIurl();
    this.http = http;
    this.sanitizer = sanitizer;
    this.changeValue = false;
    this.ageHours = null;
    this.isNurse = false;
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
  runVideo(){
    this.changeValue = true;
    localStorage.setItem('runVideo', JSON.stringify(true));
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

  };
  setViewProfile(){
    if(this.router.url === '/admis/view-profile'){

    }
    else{
			var isEditProfile = true;
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
        // var audio = new Audio('_audio/deviceDisconnectedAlram.ogg');
        // if(!isNaN(audio.duration)){
        //   // audio file exists
        //   audio.play();
        // }
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

  selectNursingTab(selectedValue){
    this.whichNursingPanelTab = selectedValue;
    if(this.whichNursingPanelTab == 'Doctor-Order'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/doctorOrder/nursing-order');
    }
    else if(this.whichNursingPanelTab == 'anthropomery'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/anthropometry/nursing-anthropometry');
    }
    else if(this.whichNursingPanelTab == 'nursingcharts'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/charts/nursing-charts');
    }
    else if(this.whichNursingPanelTab =='nursingreports'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/nursing-lab/nursing-reports');
    }
    else if(this.whichNursingPanelTab =='nursingmedications'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/medications/nursing-medications');
    }
    else if(this.whichNursingPanelTab =='nursingtreatments'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/treatment/nursing-treatments');
    }
    else if(this.whichNursingPanelTab =='nursingprocedures'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/procedure/nursing-procedures');
    }
    else if(this.whichNursingPanelTab =='nursingnotes'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/notes/nursing-notes');
    }
    else if(this.whichNursingPanelTab =='nursing-output'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/output/nursing-output');
    }
    else if(this.whichNursingPanelTab =='doctor-panel'){
      this.isLoaderVisible = true;
      this.router.navigateByUrl('/stable/assessment-sheet-stable');
    }
    // TODO: TEST IT
    localStorage.removeItem('comingFromWhichModule');
  }

  changeOfRoutes = function(){
    this.isLoaderVisible = false;
    if(this.router.url==='/anthropometry/nursing-anthropometry'){
      this.whichNursingPanelTab = 'anthropomery';
    }else if(this.router.url==='/doctorOrder/nursing-order'){
      this.whichNursingPanelTab = 'Doctor-Order';
    }else if(this.router.url==='/charts/nursing-charts'){
      this.whichNursingPanelTab = 'nursingcharts';
    }else if(this.router.url==='/charts/nursing-charts'){
      this.whichNursingPanelTab = 'nursingcharts';
    }else if(this.router.url==='/nursing-lab/nursing-reports'){
      this.whichNursingPanelTab = 'nursingreports';
    }else if(this.router.url==='/medications/nursing-medications'){
      this.whichNursingPanelTab = 'nursingmedications';
    }else if(this.router.url==='/treatment/nursing-treatments'){
      this.whichNursingPanelTab = 'nursingtreatments';
    }else if(this.router.url==='/procedure/nursing-procedures'){
      this.whichNursingPanelTab = 'nursingprocedures';
    }else if(this.router.url==='/notes/nursing-notes'){
      this.whichNursingPanelTab = 'nursingnotes';
    }else if(this.router.url==='/output/nursing-output'){
      this.whichNursingPanelTab = 'nursing-output';
    }
  }


  ngOnInit() {
    this.isLoaderVisible = false;
    this.changeOfRoutes();
    this.getData();
    if(JSON.parse(localStorage.getItem('logedInUser')).userRole == '5'){
      this.isNurse = true;
    }
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
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) != undefined){
        console.log(JSON.parse(localStorage.getItem('comingFromWhichModule')));
        this.isLoaderVisible = false;
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Vital Parameters' || JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Events' || JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Ventilator' || JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Blood Gas' || JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Intake/Output'){
          this.selectNursingTab('nursingcharts');
        }
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Medications'){
          this.selectNursingTab('nursingmedications');
        }
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Orders'){
          this.selectNursingTab('nursingreports');
        }
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Nursing Anthropometry'){
            this.selectNursingTab('anthropomery');
        }
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Nursing Notes'){
            this.selectNursingTab('nursingnotes');
        }
        if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Nursing-output'){
          this.selectNursingTab('nursing-output');
          this.isLoaderVisible = false;
        }
      }else{
        // this.selectNursingTab('Doctor-Order');
      }
  }

}
