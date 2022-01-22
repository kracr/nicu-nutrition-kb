import { Component, NgModule, OnInit, Pipe, PipeTransform, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {Http, Response} from '@angular/http';
import { Bed } from './bed';
import { Patient } from './patient';
import { MandatoryFieldObj } from './mandatoryfieldobj';
import { MandatoryFieldTempObj } from './mandatoryFieldTempObj';
import { Data } from './data';
import { APIurl } from '../../../model/APIurl';
import { Query } from './query';
import { SettingReference } from './settingReference';
import { ExportCsv } from '../export-csv';
import { Keyvalue } from '../userpanel/Keyvalue';

declare var Services: any;
import * as $ from 'jquery';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  //pipes: [SearchUhidName]
})
export class DashboardComponent implements OnInit {
  bed : Bed;
  item : Patient;
  mandatoryFieldObj : MandatoryFieldObj;
  data : Data;
  messageLmp : string;
  room : string;
  hoursList : Array<string>;
  minutesList : Array<string>;
  dayInMillis : number;
  add280Days : number;
  warnLmp : boolean;
  mandatoryDataValid : boolean;
  notificationObject: any;
  isprocessingLabel: boolean;
  totalBeds : Array<Bed>;
  tempBedList : Array<Bed>;
  completeBedList : Array<Bed>;
  prevArr : Array<Bed>;
  http: Http;
  cities: string[];
  totalCount : number;
  vacant : number;
  occupied : number;
  roomString : string;
  roomList : Array<string>;
  query : Query;
  searchText : string;
  settingReference : SettingReference;
  keyValue : Keyvalue;
  apiData : APIurl;
  weightTrackingList : any;
  weeks : any;
  ageDays : number;
  diff : number;
  lifeWeeks : number;
  ageMonth : number;
  weeksAfterDayAdd : number;
  totalDaysOfGestation : number;
  lifeDays : number;
  num : number;
  dropdownData : any;
  dummyData : any;
  uhidValue : any;
  tobToaError : boolean;
  checkFlag : boolean;
  dobValid : boolean;
  arrayWithRangeLess : any;
  arrayWithRange : any;
  tempCopiedData : any;
  mandatoryFieldTempObj : MandatoryFieldTempObj;
  occupiedBed : Array<Bed>;
  isListVisible : boolean;
  isFilterApply : boolean;
  router : Router;
  monitorDataInterval : any;
  tabContentWeight : string;
  gestationDays : any;
  gestationWeeks : any;
  limitDol : any;
  tempNote :  any;
  isLoaderVisible : boolean;
  loaderContent : string;
  ageDaysdiff : number;
  cgaAgeDays : number;
  branchName : string;
  currentDate : Date;
  roomMap : any;
  hoursListMap : any;
  minutesListMap : any;
  arrayWithRangeLessMap : any;
  arrayWithRangeMap : any;
  doctorTasksList : any;
  nurseTasksList : any;
  loggedInUserObj : any;
  viewDoctorListEnable : boolean;
  doctorViewList : any;
  nurseViewList : any;
  selectedUhid : string;
  isNurseToDoListStatus : boolean;
  constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Loading...';
    this.router = router;
    this.apiData = new APIurl();
    this.totalCount = 0;
    this.vacant = 0;
    this.occupied = 0;
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.cities = ['Miami', 'Sao Paulo', 'New York'];
    this.http = http;
    this.hoursList = new Array<string>();
    this.minutesList = new Array<string>();
    this.totalBeds = new Array<Bed>();
    this.tempBedList = new Array<Bed>();
    this.completeBedList =  new Array<Bed>();
    this.occupiedBed = new Array<Bed>();
    this.prevArr = new Array<Bed>();
    this.query = new Query();
    this.settingReference = new SettingReference();
    this.keyValue = new Keyvalue();
    this.searchText = '';
    this.mandatoryDataValid = true;
    this.arrayWithRangeLess = [];
    this.arrayWithRange = [];
    this.arrayWithRangeLessMap = [];
    this.arrayWithRangeMap = [];
    this.mandatoryFieldTempObj = new MandatoryFieldTempObj();
    this.currentDate = new Date();
    this.roomMap = [];
    this.hoursListMap = [];
    this.minutesListMap = [];
    this.doctorTasksList = [];
    this.nurseTasksList = [];
    this.selectedUhid = '';
    this.viewDoctorListEnable = false;
    this.doctorViewList = [];
    this.nurseViewList = [];
    this.isNurseToDoListStatus = true;

    for(var i=0; i<=6; i++){
      this.keyValue = new Keyvalue();
      this.keyValue.key = "" + i + "";
      this.arrayWithRangeLess[i] = i;
      this.keyValue.value = i.toString();
      this.arrayWithRangeLessMap.push(this.keyValue);
    }

    var k = 0;
    for(var i=20; i<=40; i++){
      this.arrayWithRange[k] = i;
      this.keyValue = new Keyvalue();
      this.keyValue.key = "" + k + "";
      this.keyValue.value = i.toString();
      this.arrayWithRangeMap.push(this.keyValue);
      k = k + 1;
    }

    // console.log("in the Declaring Total Beds Arr");
    // console.log(this.totalBeds);
		for(var i=0;i<=12;++i){
      this.keyValue = new Keyvalue();
      this.keyValue.key = "" + i + "";
			if(i<10){
				this.hoursList.push("0"+i.toString());
        this.keyValue.value = "0"+i.toString();
      }
			else{
				this.hoursList.push(i.toString());
        this.keyValue.value = i.toString();
      }
      this.hoursListMap.push(this.keyValue);
		}
		for(var i=0;i<60;++i){
      this.keyValue = new Keyvalue();
      this.keyValue.key = "" + i + "";
			if(i<10){
				this.minutesList.push("0"+i.toString());
        this.keyValue.value = "0"+i.toString();
      }
			else{
				this.minutesList.push(i.toString());
        this.keyValue.value = i.toString();
      }
      this.minutesListMap.push(this.keyValue);
		}
		this.dayInMillis = 24*60*60*1000;
		this.add280Days =  280*24*60*60*1000;
    this.mandatoryDataValid = true;
    this.isprocessingLabel = true;
    this.isListVisible = true;
    this.init();
  }

  init() {
  if(JSON.parse(localStorage.getItem('logedInUser')) == undefined){
    this.router.navigateByUrl('/');
  }

      this.isprocessingLabel = true;
      this.getAdmissionBeds();
  };

  printCouncellingSheet = function(){
    localStorage.setItem('councellingData', JSON.stringify(this.totalBeds));
    this.router.navigateByUrl('/dash/counselling-sheet-print');
  };

  exportCsv() {
    try{
      this.http.post(this.apiData.dashboardExportCsv, JSON.stringify({someParameter: 1,someOtherParameter: 2})).subscribe((res: Response) => {
          var fileName = "Patient-Bed-details.csv";
          var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(res.json());
          var finalCSVData = encodeURI(csvData);
          console.log("final csv");
          console.log(finalCSVData);
          var downloadLink = document.createElement("a");
          downloadLink.setAttribute("href", finalCSVData);
          downloadLink.setAttribute("download", fileName);
          downloadLink.click();
     });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  goToAdmitPatient = function(selectedBed){
        console.log(selectedBed);
        this.isLoaderVisible = true;
        delete window.localStorage["selectedChild"];
        localStorage.setItem('selectedChild', JSON.stringify(selectedBed));
        localStorage.setItem('settingReference', JSON.stringify(this.settingReference));
        var isEditProfile = false
        localStorage.setItem('isEditProfile', JSON.stringify(isEditProfile));
        console.log('Data is set onto the Localstorage');
        this.router.navigate(['/admis/admissionform']);
        // var loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
      };

  getChildData(bed) {
            	this.isLoaderVisible = true;
              console.log(bed);
            	console.log(bed.birthWeight);
            	console.log(bed.dob);
            	console.log(bed.gender);
            	console.log(bed.gestation);
            	console.log(bed.timeOfBirth);

              if(bed.dob == null || bed.dob == undefined || bed.dob == '' || bed.timeOfBirth == null || bed.timeOfBirth == undefined || bed.timeOfBirth == '' || bed.gender==null || bed.gender == undefined || bed.gender == '' ||  bed.gestation == null ||  bed.gestation == undefined ||  bed.gestation == '' ||  bed.birthWeight == null || bed.birthWeight == undefined || bed.birthWeight == ''){
                 this.isLoaderVisible = false;
                 this.callingRequiredFieldPopup(bed);
            	}
            	else{
            	   this.redirectingToPannel(bed);
            	}
      }
      redirectingToPannel = function(bed){
          //delete window.localStorage["selectedChild"];
          localStorage.removeItem('selectedChild');
          localStorage.setItem('selectedChild', JSON.stringify(bed));
          var isEditProfile = false
          localStorage.setItem('isEditProfile', JSON.stringify(isEditProfile));
          localStorage.setItem('settingReference', JSON.stringify(this.settingReference));
          console.log('Data is set onto the Localstorage from redirectingToPannel');
          var loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
          if (loggedInUserObj.userRole == '5') {
            this.router.navigateByUrl('/anthropometry/nursing-anthropometry');
          } else {
              this.router.navigateByUrl('/stable/assessment-sheet-stable');
          }
      }

      getNotifications(){
        console.log('inside getNotifications' + new Date());
          try{
            this.http.request(this.apiData.getNotifications)
              .subscribe((res: Response) => {
                this.notificationObject = res.json();
                console.log('after API response getNotifications' + new Date());

                for (var i = 0; i < this.totalBeds.length; i++) {
                  for(var indexGT12Hrs=0;indexGT12Hrs<this.notificationObject.assessmentGT12Hrs.length;indexGT12Hrs++){
                    if(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].uhid== this.totalBeds[i].uhid){
                      this.totalBeds[i].notificationObject.count = this.totalBeds[i].notificationObject.count*1+1;
                      if(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventtype == 'assessment') {
                        this.totalBeds[i].notificationObject.meessageArr.push(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventstatus+": Baby assessment was done 12hr before");
                      } else if(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventtype == 'procedure'){
                        this.totalBeds[i].notificationObject.meessageArr.push(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventstatus + " inserted for more than 24 hrs ago.");
                      } else if(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventtype == 'baby_visit'){
                        this.totalBeds[i].notificationObject.meessageArr.push(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventstatus + " last updated for more than 7 days ago.");
                      } else if(this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventtype == 'medicine'){
                        this.totalBeds[i].notificationObject.meessageArr.push("Kindly assess "+ this.notificationObject.assessmentGT12Hrs[indexGT12Hrs].eventstatus + " medicine");
                      }
                    }
                  }

                  //BP List
                  for(var indexBpList=0;indexBpList<this.notificationObject.BPList.length;indexBpList++){
                    if(this.notificationObject.BPList[indexBpList][0]== this.totalBeds[i].uhid){
                      this.totalBeds[i].notificationObject.countForBpAndRbs = this.totalBeds[i].notificationObject.countForBpAndRbs*1+1;
                      this.totalBeds[i].notificationObject.meessageArrForBpAndRbs.push("Latest BP recorded was "+ this.notificationObject.BPList[indexBpList][1] + " mmHg");
                    }
                  }

                  //RBS List
                  for(var indexRbsList=0;indexRbsList<this.notificationObject.RBSList.length;indexRbsList++){
                    if(this.notificationObject.RBSList[indexRbsList][0]== this.totalBeds[i].uhid){
                      this.totalBeds[i].notificationObject.countForBpAndRbs = this.totalBeds[i].notificationObject.countForBpAndRbs*1+1;
                      this.totalBeds[i].notificationObject.meessageArrForBpAndRbs.push("Latest RBS recorded was "+ this.notificationObject.RBSList[indexRbsList][1] + " mg/dL");
                    }
                  }

                  //Total Urine Elapsed List
                  for(var indexUrineList=0;indexUrineList<this.notificationObject.TotalUrineElapsedList.length;indexUrineList++){
                    if(this.notificationObject.TotalUrineElapsedList[indexUrineList][0]== this.totalBeds[i].uhid){
                      this.totalBeds[i].notificationObject.countForBpAndRbs = this.totalBeds[i].notificationObject.countForBpAndRbs*1+1;
                      this.totalBeds[i].notificationObject.meessageArrForBpAndRbs.push("Urine Output recorded was "+ this.notificationObject.TotalUrineElapsedList[indexUrineList][1] + " ml/kg/hr");
                    }
                  }

                  if(this.totalBeds[i].monitorException != null) {
                      this.totalBeds[i].notificationObject.meessageArr.push(this.totalBeds[i].monitorException);
                      this.totalBeds[i].notificationObject.count = this.totalBeds[i].notificationObject.count + 1 ;
                  }
                  if(this.totalBeds[i].ventilatorException != null) {
                      this.totalBeds[i].notificationObject.meessageArr.push(this.totalBeds[i].ventilatorException);
                      this.totalBeds[i].notificationObject.count = this.totalBeds[i].notificationObject.count + 1 ;
                  }
                }
           });
          }catch(e){
            console.log("Exception in http service:"+e);
          };
      }

  getAdmissionBeds() {
    console.log('inside getAdmissionBeds' + new Date());
    try {
        this.http.post(this.apiData.getDashboard + "/" + this.branchName,
          JSON.stringify({
          someParameter: 1,
          someOtherParameter: 2
          })).subscribe(res => {
            this.handleadmission(res.json());
            console.log('after API response getAdmissionBeds' + new Date());
            this.getNotifications();
         },
          err => {
            console.log("Error occured.")
          });
      } catch(e){
        console.log("Exception in http service:"+e);
      };
   }

   handleadmission(responseData: any) {
      this.totalBeds = responseData;
      this.calculateCga();
      this.displayMonitorDataContinuously();
      this.getPatientDataContinuously();
      this.queryFilter();
      console.log("total beds = response data");
      console.log(this.totalBeds);
      this.isprocessingLabel = false;
      this.roomString = '';
      this.totalCount = 0;
      this.vacant = 0;
      this.occupied = 0;
      for (var i = 0; i < responseData.length; i++) {
        var tempBed = new Bed();
        this.totalBeds[i].whichDeviceDataVisble = "Monitor";
        this.totalBeds[i].uniqueNumber = "uniqueNumber"+i;
        this.totalCount = this.totalCount+1;
        if(responseData[i].isvacant === true ) {
          this.vacant= this.vacant+1;
        } else {
          this.occupied = this.occupied+1;
        }

        this.totalBeds[i].notificationObject = {
          "count":0,
          "meessageArr" : [],
          "countForBpAndRbs":0,
          "meessageArrForBpAndRbs" : []
        };

        if (this.roomString == '') {
          this.roomString = responseData[i].room.value;
        } else if (!this.roomString.includes(responseData[i].room.value.trim())) {
          this.roomString = this.roomString + "," + responseData[i].room.value;
        }
      }
      this.roomList = this.roomString.split(",");
      if(this.roomList != null && this.roomList.length > 0){
        for(var i=0; i< this.roomList.length; i++){
          this.keyValue = new Keyvalue();
          this.keyValue.key = "" + i + "";
          this.keyValue.value = this.roomList[i];
          this.roomMap.push(this.keyValue);
        }
      }
   }

   submitNoteData = function(tempData){
     try
     {
       this.http.post(this.apiData.setDoctorNotesData + "test/",this.tempNote).
       subscribe(res => {
         var response = res.json();
         if(response.type == "success"){
							console.log("in the Response Success part");
							$("#notesOverlay").css("display", "none");
							$("#notesPopup").toggleClass("showing");
						}
       },
       err => {
         console.log("Error occured.")
       }
     );
     }
     catch(e){
       console.log("Exception in http service:"+e);
     };
   }

   OpenNotesTrack = function(bedData){
			console.log(bedData);
			this.tempCopiedData = Object.assign({}, bedData);
			this.tempNote = bedData;
			console.log("notes opened");
			$("#notesOverlay").css("display", "block");
			$("#notesPopup").addClass("showing");
		};

		closeNotesTrack = function(){
			console.log(this.tempCopiedData)
			for(var i=0;i< this.totalBeds.length;i++){
				if(this.tempCopiedData.uhid == this.totalBeds[i].uhid ){
					this.totalBeds[i] = Object.assign({},this.tempCopiedData);
					break;
				}
			}
			$("#notesOverlay").css("display", "none");
			$("#notesPopup").toggleClass("showing");
		};

   calculateCga = function() {

			console.log(this.totalBeds.length);

			for(var i=0; i < this.totalBeds.length;i++){
				var gestationString = this.totalBeds[i].gestation;
        var todayDate = new Date();

        var obj = JSON.parse(JSON.stringify(todayDate));
        var newDate = Date.parse(obj);
        var tempDate = new Date(newDate);
        tempDate.setHours(0);
        tempDate.setMinutes(0);

				this.diff = tempDate.getTime() - Date.parse(this.totalBeds[i].dob);
	      this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
        this.diff = Math.ceil(this.diff);
        this.diff = parseInt(this.diff);
        this.ageDays = this.diff;

        var dateTimeBirth = new Date(this.totalBeds[i].dob);

        var tobStr = this.totalBeds[i].timeOfBirth.split(',');
        if(tobStr[2] == 'PM' && parseInt(tobStr[0]) != 12) {
          dateTimeBirth.setHours(12 + parseInt(tobStr[0]));
        } else if (tobStr[2] == 'AM' && parseInt(tobStr[0]) == 12){
            dateTimeBirth.setHours(0);
        } else {
            dateTimeBirth.setHours(parseInt(tobStr[0]));
        }
        dateTimeBirth.setMinutes(parseInt(tobStr[1]));

        if(gestationString) {
            this.weeks = gestationString.split("Weeks");
            this.days = gestationString.match("Weeks(.*)Days");
            this.gestationWeeks = parseInt(this.weeks[0]);
            this.gestationDays = parseInt(this.days[1]);
            this.totalBeds[i].gestationweekbylmp = this.gestationWeeks;
            this.totalBeds[i].gestationdaysbylmp = this.gestationDays;
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
        this.totalBeds[i].gestationDays = this.gestationDays;
        this.totalBeds[i].gestationWeeks = this.gestationWeeks;

        var todayDateobj = JSON.parse(JSON.stringify(todayDate));
        var dateTimeBirthobj = JSON.parse(JSON.stringify(dateTimeBirth));
        todayDateobj = Date.parse(todayDateobj);
        todayDateobj = new Date(todayDateobj);
        dateTimeBirthobj = Date.parse(dateTimeBirthobj);
        dateTimeBirthobj = new Date(dateTimeBirthobj);
        todayDateobj.setHours(0);
        todayDateobj.setMinutes(0);
        dateTimeBirthobj.setHours(0);
        dateTimeBirthobj.setMinutes(0);

        //DOL should decrease according to time of birth. DOL should increase if it crosses the current time
        if(((todayDate.getTime() - todayDateobj.getTime()) - (dateTimeBirth.getTime() - dateTimeBirthobj.getTime())) < 0){
          this.ageDays = this.ageDays - 1;
        }

        this.totalBeds[i].dayOfLife = this.ageDays + " days";

        var dol = this.totalBeds[i].dayOfLife;
        var birthWeight = this.totalBeds[i].birthWeight;

    		if (!this.isEmpty(this.gestationWeeks) && !this.isEmpty(dol) && !this.isEmpty(birthWeight)) {

    			var parts = dol.split("days");
    			this.limitDol = parts[0].replace(" ", "");
    			this.limitDol = parseInt(this.limitDol);
          var ultrasoundDueDate = "";
          var ropDueDate = "";
    			// code for Ultrasound Screen
    			if(this.gestationWeeks < 32 || (parseFloat(birthWeight)) < 1500){
    				if(this.limitDol <= 3){
    					var incrementalDate = 3*24*60*60*1000;
    					var newDate =  Date.parse(this.totalBeds[i].dob) + (incrementalDate) ;
    					var ultrasoundDate = new Date(newDate);
              ultrasoundDate = this.dateformatter(ultrasoundDate, "gmt", "standard");
    					ultrasoundDueDate = "USG : " + ultrasoundDate;
    				}
    				else if(this.limitDol > 3 && this.limitDol <= 7){
    					var incrementalDate = 7*24*60*60*1000;
    					var newDate =  Date.parse(this.totalBeds[i].dob) + (incrementalDate) ;
    					var ultrasoundDate = new Date(newDate);
              ultrasoundDate = this.dateformatter(ultrasoundDate, "gmt", "standard");
    					ultrasoundDueDate = "USG : " + ultrasoundDate;
    				}
            else if(this.limitDol > 7 && this.limitDol <= 28){
              var incrementalDate = 28*24*60*60*1000;
              var newDate =  Date.parse(this.totalBeds[i].dob) + (incrementalDate) ;
              var ultrasoundDate = new Date(newDate);
              ultrasoundDate = this.dateformatter(ultrasoundDate, "gmt", "standard");
              ultrasoundDueDate = "USG : " + ultrasoundDate;
            }
    			}

    			// code for ROP Screen
    			if(this.gestationWeeks < 28 || parseFloat(birthWeight) < 1200){
    				if(this.limitDol <= 21){
    					var newDate =  Date.parse(this.totalBeds[i].dob) + (10*24*60*60*1000) + (10*24*60*60*1000) + (24*60*60*1000) ;
    					var ropDate = new Date(newDate);
              ropDate = this.dateformatter(ropDate, "gmt", "standard");
    					ropDueDate = "ROP : " + ropDate;
    				}
    			}

    			else if((this.gestationWeeks >= 28 && this.gestationWeeks < 34) ||
    					(parseFloat(birthWeight) >= 1200 && parseFloat(birthWeight) < 2000)){
    				if(this.limitDol <= 28){
    					var newDate =  Date.parse(this.totalBeds[i].dob) + (10*24*60*60*1000) + (10*24*60*60*1000) + (8*24*60*60*1000);
    					var ropDate = new Date(newDate);
              ropDate = this.dateformatter(ropDate, "gmt", "standard");
    					ropDueDate = "ROP : " + ropDate;
    				}
    			}
          if((this.totalBeds[i].ropAlert == null || this.totalBeds[i].ropAlert == '') && ropDueDate != ""){
            this.totalBeds[i].ropAlert = ropDueDate;
          }
      		this.totalBeds[i].ultrasoundAlert = ultrasoundDueDate;
    		}


			}
		}

   displayMonitorDataContinuously() {
     this.monitorDataInterval = setInterval(this.getPatientDataContinuously(), 120000);
    }

    getPatientDataContinuously(){
      if(this.router.url==='/dash/dashboard') {
        try{
          this.http.request(this.apiData.getDashboardVitalDetail + "/" + this.branchName)
            .subscribe((res: Response) => {
              this.pushDeviceDataIntoTotalbedArr(res.json());
         });
        }catch(e){
          console.log("Exception in http service:"+e);
        };
      } else {
        clearTimeout(this.monitorDataInterval);
      }
    }

    pushDeviceDataIntoTotalbedArr(tempData : any) {
      console.log("In the pushDeviceDataIntoTotalbedArr");
      for (var i = 0; i < this.totalBeds.length; i++){
        if(this.totalBeds[i].isvacant == false){
          var obj1 = this.totalBeds[i];
          var obj2 = tempData[this.totalBeds[i].uhid];
          obj1.monitorException = obj2.monitorException;
          obj1.ventilatorException = obj2.ventilatorException;
          if(obj2 != undefined && obj2 != null){
            obj1.HR = obj2.heartrate;
            obj1.hr = obj2.heartrate;
            // if(obj2.heartrate == null){
            //   obj1.HR = obj2.hr_rate;
            //   obj1.hr = obj2.hr_rate;
            // }
            if(obj2.hr_rate != null && obj2.entrydate > obj2.starttime) {
              obj1.HR = obj2.hr_rate;
              obj1.hr = obj2.hr_rate;
            }
            // else {
            //   obj1.HR = obj2.heartrate;
            //   obj1.hr = obj2.heartrate;
            // }
            obj1.SPO2 = obj2.spo2;
            obj1.spo2 = obj2.spo2;
            // if(obj2.spo2 == null){
            //   obj1.SPO2 = obj2.spo21;
            //   obj1.spo2 = obj2.spo21;
            // }
            if(obj2.spo21 != null && obj2.entrydate > obj2.starttime) {
              obj1.SPO2 = obj2.spo21;
              obj1.spo2 = obj2.spo21;
            }
            // else{
            //   obj1.SPO2 = obj2.spo2;
            //   obj1.spo2 = obj2.spo2;
            // }
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

            if(obj2.start_time != null && obj2.start_time != undefined){
              obj1.ventilatorTime = obj2.start_time;
            }

            if((obj1.deviceTime == null || obj1.deviceTime == undefined) && obj2.entrydate != null && obj2.entrydate != undefined){
              obj1.deviceTime = obj2.entrydate;
            }

          }
        }
      }
    }

    getBedId = function(bed) {
			if((this.isEmpty(bed.birthWeight)) || (this.isEmpty(bed.dob))||(this.isEmpty(bed.gender))||(this.isEmpty(bed.gestation))||(this.isEmpty(bed.timeOfBirth))){
				this.callingRequiredFieldPopup(bed);
			}else{
				delete window.localStorage["selectedChild"];
				localStorage.setItem('selectedChild', JSON.stringify(bed));
        this.router.navigate(['/device/add-device']);
			}

		}

  showBedMessage(bedNo : any, uhid : any,type:any) {
      var boxWidth = $(".dashboard-bed-box").width();
      console.log(boxWidth);
      if(type == "exception"){
        var bedId = "#message_" + bedNo;
        var bedMessageId = "bedMessage_" + bedNo;
        bedMessageId = "#" + bedMessageId;
        $(bedMessageId).addClass("show");
        $(bedMessageId).attr('tabindex', -1);
        $(bedMessageId).focus();
        var position = $(bedId).position();
        var top = position.top;
        var left = position.left;
        console.log(left);
        top = top + 35;
        left = 20;
        console.log(left);
        console.log(boxWidth);
        $(bedMessageId).css({
          "top": top,
          "left": left,
          "width": boxWidth
        });

      }
      if(type == "BpAndRbs"){
        var bpAndRbsMessageId = "bedMessage_one" + bedNo;
        bpAndRbsMessageId = "#" + bpAndRbsMessageId;
        $(bpAndRbsMessageId).addClass("show");
        $(bpAndRbsMessageId).attr('tabindex', -1);
        $(bpAndRbsMessageId).focus();
        $(bpAndRbsMessageId).css({
          "top": 60,
          "left": 50,
          "width": 300
        });
      }
    };

    hideTooltip(messageId : any,type:any) {
      if(type == "BpAndRbs"){
        messageId = "bedMessage_one" + messageId;
        messageId = "#" + messageId;
        $(messageId).removeClass("show");
      }
      if(type == "exception"){
        messageId = "bedMessage_" + messageId;
        messageId = "#" + messageId;
        $(messageId).removeClass("show");
      }
    }

    calculateETLmp =  function(){

          var dayInMillis = 24*60*60*1000;
    			var timeInMillisByEt = new Date(this.mandatoryFieldObj.etTimestampStr).getTime();
    			var lmpTimeInMillis = ((timeInMillisByEt*1) - (14*24*60*60*1000));
    			var dateLmp = new Date(lmpTimeInMillis);
    			console.log("Edd date value: " + dateLmp);
          this.mandatoryFieldObj.lmpTimestampStr = this.dateformatter(dateLmp, "utf", "gmt");


    			var timeInMillisByLmp = new Date(this.mandatoryFieldObj.lmpTimestampStr).getTime();
    			var eddTimeInMillis = ((timeInMillisByLmp*1) + (280*dayInMillis));
    			var dateEdd = new Date(eddTimeInMillis);
    			console.log("Edd date value: " + dateEdd);

          this.mandatoryFieldObj.eddTimestampStr = this.dateformatter(dateEdd, "utf", "gmt");
          this.calculateEddLmp();

    		}

  calculateEddLmp(){
			if(this.mandatoryFieldObj.edd_by!=null){

				if(this.mandatoryFieldObj.edd_by=='notKnown'){
					this.mandatoryFieldObj.lmpTimestampStr = null;
					this.mandatoryFieldObj.eddTimestampStr = null;
					this.mandatoryFieldObj.gestationWeeks = null;
					this.mandatoryFieldObj.gestationDays = null;
				}else{
					if(this.mandatoryFieldObj.edd_by=='lmp' && this.mandatoryFieldObj.lmpTimestampStr != null && this.mandatoryFieldObj.lmpTimestampStr != undefined){
						var timeInMillisByLmp = new Date(this.mandatoryFieldObj.lmpTimestampStr).getTime();
						var eddTimeInMillis = ((timeInMillisByLmp*1) + (280*this.dayInMillis));
						var dateEdd = new Date(eddTimeInMillis);
						console.log("Edd date value: " + dateEdd);
						this.mandatoryFieldObj.eddTimestampStr = new Date(dateEdd);
					}else if(this.mandatoryFieldObj.edd_by=='usg' && this.mandatoryFieldObj.eddTimestampStr != null && this.mandatoryFieldObj.eddTimestampStr != undefined){
						var timeInMillis = new Date(this.mandatoryFieldObj.eddTimestampStr).getTime();

						//remove hours-minutes part from millis
						var timeInMillisRemainder = timeInMillis % this.dayInMillis;
						console.log("Remainder Time: "+ timeInMillisRemainder);
						var lmpTimeInMillis = (timeInMillis-timeInMillisRemainder) +this.dayInMillis - this.add280Days;

						var dateLmp = new Date(lmpTimeInMillis);
						console.log("LMP date value: " + dateLmp);
						this.mandatoryFieldObj.lmpTimestampStr = new Date(dateLmp);
          }

					if(this.mandatoryFieldObj.dobStr!=null && this.mandatoryFieldObj.dobStr!=''){
						this.mandatoryFieldObj.dobNullMessage   ="";
						var timeInMillis = new Date(this.mandatoryFieldObj.eddTimestampStr).getTime();
						var dobInMillis = new Date(this.mandatoryFieldObj.dobStr).getTime();
						var gestInMillis = dobInMillis - (timeInMillis - this.add280Days);

						var noOfDays = (gestInMillis / this.dayInMillis);
						noOfDays = Math.round(noOfDays);
						var gestDays = noOfDays % 7;

						var gestWeeks = (noOfDays - gestDays)/7;
						if(gestWeeks > 40) {
							this.warnLmp = true;
							this.messageLmp = "Gestation can't be more than 40 weeks";
						} else if(gestWeeks <20) {
							this.warnLmp = true;
							this.messageLmp = "Gestation can't be less than 20 weeks";
						} else {
							this.messageLmp = "";
							console.log("TOTAL DAYS: "+noOfDays+"Gest Weeks: " + gestWeeks +" days: " +gestDays);
							this.mandatoryFieldObj.gestationWeeks = gestWeeks;
							this.mandatoryFieldObj.gestationDays = gestDays;
						}
					}else{
						this.mandatoryFieldObj.dobNullMessage = "Please enter DOB";
					}
				}
			}
		}
    clearCondition(){
			if(this.query.condition){
				this.query.condition = '';
			}
		}

  queryFilter(){
    for(var i =0;i< this.totalBeds.length;i++){
      var babyObj = this.totalBeds[i];
      babyObj.isVisible = false;
      if((this.query.level == '' || this.query.level == babyObj.level)
        &&(this.query.babyRoom == '' || this.query.babyRoom == babyObj.babyRoom)
        && (this.query.condition == '' || this.query.condition == babyObj.condition)) {
        babyObj.isVisible = true;
      }
    }
  }

  cardView(){
    this.isListVisible = true;
  }

  listView(){
    this.isListVisible = false;
    this.searchText = '';
    this.removeFilter();
  }

  removeFilter(){
    this.query.condition = '';
    this.query.babyRoom = '';
    this.query.level = '';
    this.queryFilter();
  }

    calculateOtherDetails = function() {
   			console.log(this.weightTrackingList.length);

   			for(var i=0; i < this.weightTrackingList.length;i++){
   				var gestationString = this.weightTrackingList[i].gaAtBirth;
   				if(gestationString){
            var todayDate = new Date();
            todayDate.setHours(0);
            todayDate.setMinutes(0);

            var as = todayDate.toString();
   					this.diff = Date.parse(as) - Date.parse(this.weightTrackingList[i].dateofbirth);
            this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
            this.diff = Math.ceil(this.diff);
            this.ageDays = parseInt(this.diff);
            this.weightTrackingList[i].nicudays = this.ageDays;

   					if(this.weightTrackingList[i].gestationweekbylmp != null && this.weightTrackingList[i].gestationweekbylmp != undefined && this.weightTrackingList[i].gestationdaysbylmp != null && this.weightTrackingList[i].gestationdaysbylmp != undefined)
   					{
	   					this.weightTrackingList[i].gestationWeek = this.weightTrackingList[i].gestationweekbylmp;
	   					this.weightTrackingList[i].gestationDays = this.weightTrackingList[i].gestationdaysbylmp;
	   					if (this.ageDays > 7) {
                this.lifeWeeks = (this.ageDays / 7);
                this.lifeWeeks = parseInt(this.lifeWeeks);
                this.lifeDays = this.ageDays - this.lifeWeeks * 7;
	   						this.weightTrackingList[i].gestationWeek += this.lifeWeeks;
	   						this.totalDaysOfGestation = this.lifeDays + this.weightTrackingList[i].gestationDays;
	   						if (this.totalDaysOfGestation > 6) {
                  this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
                  this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
	   							this.weightTrackingList[i].gestationWeek += parseInt(this.weeksAfterDayAdd + "");
	   							this.weightTrackingList[i].gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");
	   						} else {
	   							this.weightTrackingList[i].gestationDays = parseInt(this.totalDaysOfGestation + "");
	   						}
	   					} else {
	   						this.totalDaysOfGestation = this.ageDays + this.weightTrackingList[i].gestationDays;
	   						if (this.totalDaysOfGestation > 6) {
                  this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
                  this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
	   							this.weightTrackingList[i].gestationWeek += parseInt(this.weeksAfterDayAdd + "");
	   							this.weightTrackingList[i].gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");
	   						} else {
	   							this.weightTrackingList[i].gestationDays = parseInt(this.totalDaysOfGestation + "");
	   						}
	   					}
              if(this.weightTrackingList[i].gestationDays == 0){
                this.weightTrackingList[i].gestationDays = 6;
                this.weightTrackingList[i].gestationWeek = this.weightTrackingList[i].gestationWeek - 1;
              }
              else{
                this.weightTrackingList[i].gestationDays = this.weightTrackingList[i].gestationDays - 1;
              }
   					}
   					else{
   						var q = 1;
   					}
   				}
   				else{
   					var s = 1;
   				}
   			}
   		}

  openNurseTasks = function(){
    this.searchText = '';
      try{
        this.http.request(this.apiData.getNurseTasks + "/" + this.branchName ,)
        .subscribe(res => {
          this.handleNurseTasksData(res.json());
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    }
  }

  handleNurseTasksData = function(response : any){
    this.nurseTasksList = [];
    this.nurseViewList = [];
    if(response != null){
      for(var i =0; i<response.length ; i++){
        this.nurseTasksList[i] = response[i];
        if(response[i].isSelected == true){
          this.nurseViewList.push(response[i]);
        }
      }
    }
    if(this.nurseTasksList.length > 0){
      $("#nurseTasksOverlay").css("display", "block");
      $("#nurseTasksPopup").addClass("showing");
    }
  }

  showToDoListNurse = function(obj : any){
    var recordFound = false;
    for(var i = 0; i < this.nurseViewList.length; i++){
      if(obj.uhid == this.nurseViewList[i].uhid){
        this.nurseViewList.splice(i,1);
        recordFound = true;
        obj.isSelected = false;
        if(obj.uhid == this.selectedUhid){
          this.selectedUhid = '';
        }
        break;
      }
    }
    if(recordFound == false){
      obj.isSelected = true;
      this.selectedUhid = obj.uhid;
      this.nurseViewList.push(obj);
    }
  }

  openDoctorTasks = function(){
    localStorage.removeItem('doctorViewList');
    this.searchText = '';
      try{
        this.http.request(this.apiData.getDoctorTasks + "/" + this.branchName ,)
        .subscribe(res => {
          this.handleDoctorTasksData(res.json());
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    }
  }

  openNewDoctorTasks = function(){
      try{
        this.http.request(this.apiData.getNewDoctorTasks + "/" + this.branchName ,)
        .subscribe(res => {
          this.handleDoctorTasksData(res.json());
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    }
  }

  handleDoctorTasksData = function(response : any){
    this.doctorViewList = [];
    this.doctorTasksList = [];
    if(response != null){
      for(var i =0; i<response.length ; i++){
        this.doctorTasksList[i] = response[i];
        if(response[i].isSelected == true){
          this.doctorViewList.push(response[i]);
        }
      }
    }
    if(this.doctorTasksList.length > 0){
      this.viewDoctorListEnable = false;
      $("#doctorTasksOverlay").css("display", "block");
      $("#doctorTasksPopup").addClass("showing");
    }
  }

  refreshSearchUhid = function(){
    this.searchText = '';
  }

  saveDoctorTasks = function(){
    console.log("in submit funct");

    try{
      this.http.post(this.apiData.saveDoctorTasks + "/" + this.loggedInUserObj.loggedUser + "/" + this.branchName ,this.doctorTasksList
      ).subscribe(res => {
        this.handleDoctorTasksData(res.json());
      },
      err => {
        console.log("Error occured.")
      }

    );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveNurseTasks = function(){
    console.log("in submit funct");

    try{
      this.http.post(this.apiData.saveNurseTasks + "/" + this.loggedInUserObj.loggedUser + "/" + this.branchName ,this.nurseTasksList
      ).subscribe(res => {
        this.handleNurseTasksData(res.json());
      },
      err => {
        console.log("Error occured.")
      }

    );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  closeDoctorTasks = function(){
    this.searchText = '';
 		$("#doctorTasksOverlay").css("display", "none");
    $("#doctorTasksPopup").toggleClass("showing");
      //window.location.reload();
      // this.router.navigate(['/dash/dashboard']);
      // this.getAdmissionBeds();
  }

  closeNurseTasks = function(){
    this.searchText = '';
    $("#nurseTasksOverlay").css("display", "none");
    $("#nurseTasksPopup").toggleClass("showing");
      //window.location.reload();
      // this.router.navigate(['/dash/dashboard']);
      // this.getAdmissionBeds();
  }

  viewDoctorTasks = function(){
    this.viewDoctorListEnable = !this.viewDoctorListEnable;
  }

  showToDoListDoctors = function(obj : any){
    var recordFound = false;
    for(var i = 0; i < this.doctorViewList.length; i++){
      if(obj.uhid == this.doctorViewList[i].uhid){
        this.doctorViewList.splice(i,1);
        recordFound = true;
        obj.isSelected = false;
        if(obj.uhid == this.selectedUhid){
          this.selectedUhid = '';
        }
        break;
      }
    }
    if(recordFound == false){
      obj.isSelected = true;
      this.selectedUhid = obj.uhid;
      this.doctorViewList.push(obj);
    }
  }

  printDoctorTasks = function(){
    if(this.doctorViewList != null && this.doctorViewList.length > 0){
      localStorage.setItem('doctorViewList', JSON.stringify(this.doctorViewList));
      this.router.navigateByUrl('/dash/doctor-task-print');
    }
  }

  printNurseTasks = function(){
    if(this.nurseViewList != null && this.nurseViewList.length > 0){
      localStorage.setItem('nurseViewList', JSON.stringify(this.nurseViewList));
      this.router.navigateByUrl('/dash/doctor-task-print');
    }
  }

  statusInvestigation = function(obj : any){
    obj.statusInvestigation = !obj.statusInvestigation;
  }

  statusAddMedicine = function(obj : any){
    obj.statusAddMedicine = !obj.statusAddMedicine;
  }

  statusStopMedicine = function(obj : any){
    obj.statusStopMedicine = !obj.statusStopMedicine;
  }

  statusComments = function(obj : any){
    obj.statusComments = !obj.statusComments;
  }

  statusReports = function(obj : any){
    obj.statusReports = !obj.statusReports;
  }

  statusFeeds = function(obj : any){
    obj.statusFeeds = !obj.statusFeeds;
  }

  statusAssessments = function(obj : any){
    obj.statusAssessments = !obj.statusAssessments;
  }

  statusVitals = function(obj : any){
    obj.statusVitals = !obj.statusVitals;
  }

  statusVentilator = function(obj : any){
    obj.statusVentilator = !obj.statusVentilator;
  }

  statusIntake = function(obj : any){
    obj.statusIntake = !obj.statusIntake;
  }

  statusInvestigations = function(obj : any){
    obj.statusInvestigations = !obj.statusInvestigations;
  }

  statusOutput = function(obj : any){
    obj.statusOutput = !obj.statusOutput;
  }

  statusMedications = function(obj : any){
    obj.statusMedications = !obj.statusMedications;
  }

  statusPreparationMedication = function(obj : any){
    obj.statusPreparationMedication = !obj.statusPreparationMedication;
  }

  statusPreparationNutrition = function(obj : any){
    obj.statusPreparationNutrition = !obj.statusPreparationNutrition;
  }

  OpenWeightTrack = function(){
   			console.log("weight tracking chart opened");
         this.weightTracking();
  }
  weightTracking = function(){
   		console.log("in integration funct");
      this.dummyData = null;
      try{
        this.http.request(this.apiData.weightTracking + "/" + this.branchName ,this.dummyData)
        .subscribe(res => {
          this.handleGetWeightData(res.json());
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    }
  }

    handleGetWeightData = function(response : any){
      this.dropdownData = response;
      this.weightTrackingList =[];
      console.log(response.length);
      for(var i =0; i<response.length ; i++){
        this.weightTrackingList[i] = response[i];
      }
      this.calculateOtherDetails();
      $("#weightTrackigOverlay").css("display", "block");
      $("#weightTrackigPopup").addClass("showing");


      console.log(this.weightTrackingList);
    }

    closeWeightTrack = function(){
   			$("#weightTrackigOverlay").css("display", "none");
	      $("#weightTrackigPopup").toggleClass("showing");
        //window.location.reload();
        this.router.navigate(['/dash/dashboard']);
        this.getAdmissionBeds();
    	}

    submitWeightTrack = function(){
   			console.log("in submit funct");
   			console.log(this.weightTrackingList);

        try{
          this.http.post(this.apiData.saveWeigthTracking +"/test/" + this.branchName ,this.weightTrackingList
          ).subscribe(res => {
            this.handlePostWeightData(res.json());
          },
          err => {
            console.log("Error occured.")
          }

        );
        }
        catch(e){
          console.log("Exception in http service:"+e);
        };
      }
      handlePostWeightData = function(response : any){
        console.log("Weight Tracking data"+JSON.stringify(response));
        for(var i =0; i<response.returnedObject.length ; i++){
          this.weightTrackingList[i] = response.returnedObject[i];
        }
      }

  closeRequiredFieldPopup = function(){
    	$("#ballardOverlay").css("display", "none");
			$('body').css('overflow', 'auto');
			$("#callingRequiredPopup").toggleClass("showing");
    }

validateMandatoryData = function(){

  $("#emptyAdmitType").css("display","none");
  $("#emptyBirthWeight").css("display","none");
  $("#emptyDob").css("display","none");
  $("#emptyGender").css("display","none");
  $("#emptyHr").css("display","none");
  $("#emptyLmpEdd").css("display","none");
  $("#emptyEdd").css("display","none");
  $("#emptyGestation").css("display","none");
  $("#emptyEtEdd").css("display","none");

  if(this.mandatoryFieldObj.patientType == null || this.mandatoryFieldObj.patientType == ""){
    $("#emptyAdmitType").css("display","block");
    return false;
  }
  if(this.mandatoryFieldObj.birthWeight == null || this.mandatoryFieldObj.birthWeight == ""){
    $("#emptyBirthWeight").css("display","block");
    return false;
  }
  if(this.mandatoryFieldObj.dobStr == null || this.mandatoryFieldObj.dobStr == ""){
    $("#emptyDob").css("display","block");
    return false;
  }
  if(this.mandatoryFieldObj.patientType == 'In Born') {
  if(this.mandatoryFieldTempObj.timeOfBirthHours == undefined || this.mandatoryFieldTempObj.timeOfBirthHours == null || this.mandatoryFieldTempObj.timeOfBirthHours == "") {
    document.getElementById("tobhr").focus();
    $("#emptyHr").css("display","block");
    return false;
  }
}

if(!(this.mandatoryFieldTempObj.timeOfBirthHours == undefined || this.mandatoryFieldTempObj.timeOfBirthHours == null || this.mandatoryFieldTempObj.timeOfBirthHours == "")) {
  if(this.mandatoryFieldTempObj.timeOfBirthMinutes == undefined || this.mandatoryFieldTempObj.timeOfBirthMinutes == null || this.mandatoryFieldTempObj.timeOfBirthMinutes == "") {
    document.getElementById("tobmin").focus();
    $("#emptyHr").css("display","block");
    return false;
  } else if(this.mandatoryFieldTempObj.timeOfBirthMeridium == undefined || this.mandatoryFieldTempObj.timeOfBirthMeridium == null || this.mandatoryFieldTempObj.timeOfBirthMeridium == "") {
    document.getElementById("tobmer").focus();
    $("#emptyHr").css("display","block");
    return false;
  }
}

this.dobValid =this.validateTobtoa();

if(this.dobValid == true){
  return false;
}

if(this.mandatoryFieldObj.gender == undefined || this.mandatoryFieldObj.gender == null || this.mandatoryFieldObj.gender == "") {
  document.getElementById("gender").focus();
  $("#emptyGender").css("display","block");
  return false;
}

if(this.mandatoryFieldObj.edd_by == null || this.mandatoryFieldObj.edd_by == ""){
  $("#emptyEdd").css("display","block");
  return false;
}

if(this.mandatoryFieldObj.edd_by != null && this.mandatoryFieldObj.edd_by != ""){
  $("#emptyEdd").css("display","none");
  if(this.mandatoryFieldObj.edd_by != "notKnown"){
    if(this.mandatoryFieldObj.lmpTimestampStr == null || this.mandatoryFieldObj.lmpTimestampStr == ""){
      $("#emptyLmpEdd").css("display","block");
      return false;
    }
    if(this.mandatoryFieldObj.eddTimestampStr == null || this.mandatoryFieldObj.eddTimestampStr == ""){
      $("#emptyLmpEdd").css("display","block");
      return false;
    }
    if(this.mandatoryFieldObj.edd_by == "ivf"){
      if(this.mandatoryFieldObj.etTimestampStr == null || this.mandatoryFieldObj.etTimestampStr == ""){
        $("#emptyEtEdd").css("display","block");
        return false;
      }
    }

    return true;
  }
  if(this.mandatoryFieldObj.gestationWeeks == null){
    $("#emptyGestation").css("display","block");
    return false;
  }
  if(this.mandatoryFieldObj.gestationDays == null){
    $("#emptyGestation").css("display","block");
    return false;
  }
}
  return true;
}

validateTobtoa = function(bed){
  this.tobToaError = false;
  this.mandatoryFieldTempObj.doaObj = new Date(this.mandatoryFieldObj.doaStr);
if(!(this.mandatoryFieldObj.dobStr == null || this.mandatoryFieldObj.doaStr == null)) {
  if(new Date(this.mandatoryFieldObj.dobStr).getFullYear() == new Date(this.mandatoryFieldTempObj.doaObj).getFullYear()) {
      if(new Date(this.mandatoryFieldObj.dobStr).getMonth() == new Date(this.mandatoryFieldTempObj.doaObj).getMonth()) {
        if(new Date(this.mandatoryFieldObj.dobStr).getDate() == new Date(this.mandatoryFieldTempObj.doaObj).getDate()) {
        if(!((this.mandatoryFieldTempObj.timeOfBirthHours == undefined || this.mandatoryFieldTempObj.timeOfBirthHours == null)
            || (this.mandatoryFieldTempObj.timeOfBirthMinutes == undefined || this.mandatoryFieldTempObj.timeOfBirthMinutes == null)
            || (this.mandatoryFieldTempObj.timeOfBirthMeridium == undefined || this.mandatoryFieldTempObj.timeOfBirthMeridium == null))) {
          if(!((this.mandatoryFieldTempObj.timeOfAdmHours == undefined || this.mandatoryFieldTempObj.timeOfAdmHours == null)
              || (this.mandatoryFieldTempObj.timeOfAdmMinutes == undefined || this.mandatoryFieldTempObj.timeOfAdmMinutes == null)
              || (this.mandatoryFieldTempObj.timeOfAdmMeridium == undefined || this.mandatoryFieldTempObj.timeOfAdmMeridium == null))){
            this.checkFlag = false;
            if(this.mandatoryFieldTempObj.timeOfBirthMeridium == "AM") {
              if(this.mandatoryFieldTempObj.timeOfAdmMeridium == "AM") {
                this.checkFlag = true;
              }
            } else if(this.mandatoryFieldTempObj.timeOfBirthMeridium == "PM") {
              if(this.mandatoryFieldTempObj.timeOfAdmMeridium == "AM") {
                this.tobToaError = true;
              } else {
                this.checkFlag = true;
              }
            }
            if(this.checkFlag) {
              var birthHours = this.mandatoryFieldTempObj.timeOfBirthHours * 1;
              var addmissionHours = this.mandatoryFieldTempObj.timeOfAdmHours * 1;

              if(this.mandatoryFieldTempObj.timeOfBirthHours == "12") {
                birthHours = 0;
              }

              if(this.mandatoryFieldTempObj.timeOfAdmHours == "12") {
                addmissionHours = 0;
              }

              if(birthHours > addmissionHours) {
                this.tobToaError = true;
              } else if((birthHours == addmissionHours)
                  && (this.mandatoryFieldTempObj.timeOfBirthMinutes * 1 > this.mandatoryFieldTempObj.timeOfAdmMinutes * 1)) {
                this.tobToaError = true;
              }
            }
          }
        }
      }
  }
}
}
return this.tobToaError;
}

  saveMandatoryData = function(){
      if(this.mandatoryFieldObj.edd_by != 'usg' && this.mandatoryFieldObj.edd_by != 'lmp'){
        if(this.mandatoryFieldObj.notknowntype == null || this.mandatoryFieldObj.notknowntype ==""){
            this.mandatoryFieldObj.notknowntype ="clinical estimate";
          }
      }

      console.log(this.mandatoryFieldObj.notknowntype);
      this.mandatoryDataValid = this.validateMandatoryData();
      if(this.mandatoryDataValid){
        this.mandatoryFieldObj.tobStr = this.mandatoryFieldTempObj.timeOfBirthHours + ","+this.mandatoryFieldTempObj.timeOfBirthMinutes+","+ this.mandatoryFieldTempObj.timeOfBirthMeridium;
        this.mandatoryFieldObj.dobStr = this.dateformatter(this.mandatoryFieldObj.dobStr, "gmt", "standard");
        if(this.mandatoryFieldObj.lmpTimestampStr != null && this.mandatoryFieldObj.lmpTimestampStr != undefined){
          this.mandatoryFieldObj.lmpTimestampStr = this.dateformatter(this.mandatoryFieldObj.lmpTimestampStr, "gmt", "standard");
        }
        if(this.mandatoryFieldObj.eddTimestampStr != null && this.mandatoryFieldObj.eddTimestampStr != undefined){
          this.mandatoryFieldObj.eddTimestampStr = this.dateformatter(this.mandatoryFieldObj.eddTimestampStr, "gmt", "standard");
        }
        if(this.mandatoryFieldObj.etTimestampStr != null && this.mandatoryFieldObj.etTimestampStr != undefined){
          this.mandatoryFieldObj.etTimestampStr = this.dateformatter(this.mandatoryFieldObj.etTimestampStr, "gmt", "standard");
        }
        console.log(this.mandatoryFieldObj.dobStr);
        console.log(this.mandatoryFieldObj.lmpTimestampStr);
        console.log(this.mandatoryFieldObj.eddTimestampStr);
        console.log(this.mandatoryFieldObj);

        try{
          this.http.post(this.apiData.saveMandatoryData + this.uhidValue + "/" + "test" + "/", this.mandatoryFieldObj
          ).subscribe(res => {
            this.handleMandatoryResponseDate(res.json());
          },
          err => {
            console.log("Error occured.")
          }

        );
        }
        catch(e){
          console.log("Exception in http service:"+e);
        };
      }
}


handleMandatoryResponseDate = function(response : any){

  console.log(response);
  if(response.type == "Success"){
    console.log("in the Response Success part");
    this.admitToDrPanel(this.uhidValue);
    $("#ballardOverlay").css("display", "none");
    $('body').css('overflow', 'auto');
    $("#callingRequiredPopup").toggleClass("showing");
  }
}

 admitToDrPanel = function(uhid) {
			console.log("comming into the admitToDrPanel");

      try{
        this.http.request(this.apiData.getChildData + uhid + "/",)
          .subscribe((res: Response) => {
            this.handleChildData(res.json());
       });
      }catch(e){
        console.log("Exception in http service:"+e);
      };
    }

    handleChildData = function(response : any){
      if(response != null && response.length > 0) {
        localStorage.setItem('selectedChild', JSON.stringify(response[0]));
        var isEditProfile = false
        localStorage.setItem('isEditProfile', JSON.stringify(isEditProfile));
        var loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
        if (loggedInUserObj.userRole == '4' || loggedInUserObj.userRole == '5') {
          this.router.navigateByUrl('/stable/assessment-sheet-stable');
        } else {
          this.router.navigateByUrl('/stable/assessment-sheet-stable');
        }
      }
    }

    dateformatter = function(date :Date, inFormat : string, outFormat :string){
      if(inFormat == "gmt"){
        if (outFormat == "utf")
        {
          // var tzoffset : any = (new Date(date)).getTimezoneOffset() * 60000; //offset in milliseconds
          // var dateStrObj : number= new Date(date) - tzoffset;
          // var localISOTime : any = (new Date(dateStrObj)).toISOString().slice(0,-1);
          // localISOTime = localISOTime+"Z";
          // return localISOTime;
        }
        else if(outFormat == "standard"){
          var newdate = new Date(date);
          var dd : any = newdate.getDate();
          var mm : any = newdate.getMonth();
          mm = mm+1;
          var yy = newdate.getFullYear();
          if ( dd < 10 ){
            dd = '0'+dd;
          }
          if ( mm < 10 ){
            mm = '0'+mm;
          }
          return yy+"-"+mm+"-"+dd ;
        }
      }else if(inFormat == "utf"){
        if (outFormat == "gmt")
        {
          var fullDate = new Date(date);
          fullDate = new Date(fullDate.getTime() + (fullDate.getTimezoneOffset() * 60000));
          return fullDate;
        }
      }
    }

    callingRequiredFieldPopup = function(bed){
    	this.getMandatoryFormData(bed);
			this.uhidValue = bed.uhid;
		}

    	// var tobMin;
    	// var tobHour;
    	// var tobMeridian;
    	// var toaHours;
    	// var toaMins;
    	// var toaMeridian;
    getMandatoryFormData =  function(bed){

    try{
      this.http.request(this.apiData.getMandatoryFormData + bed.uhid + "/",)
        .subscribe((res: Response) => {
          this.mandatoryFieldObj = res.json();
          this.handleMandatoryFormData();
     });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  handleMandatoryFormData = function(){
    console.log("in the showVideo function");
    $("#ballardOverlay").css("display", "block");
    $("#callingRequiredPopup").addClass("showing");
    $('body').css('overflow', 'hidden');
    var tobMin;
    var tobHour;
    var tobMeridian;
    var toaHours;
    var toaMins;
    var toaMeridian;
    console.log("The object is");
    console.log(this.mandatoryFieldObj);
    if(this.mandatoryFieldObj.dobStr != null && this.mandatoryFieldObj.dobStr != ""){
      this.mandatoryFieldObj.dobStr = new Date(this.mandatoryFieldObj.dobStr);
    }

    if(this.mandatoryFieldObj.lmpTimestampStr != null && this.mandatoryFieldObj.lmpTimestampStr != ""){
      this.mandatoryFieldObj.lmpTimestampStr = new Date(this.mandatoryFieldObj.lmpTimestampStr);
    }

    if(this.mandatoryFieldObj.eddTimestampStr != null && this.mandatoryFieldObj.eddTimestampStr != ""){
      this.mandatoryFieldObj.eddTimestampStr = new Date(this.mandatoryFieldObj.eddTimestampStr);
    }

    if(this.mandatoryFieldObj.etTimestampStr != null && this.mandatoryFieldObj.etTimestampStr != ""){
      this.mandatoryFieldObj.etTimestampStr = new Date(this.mandatoryFieldObj.etTimestampStr);
    }

    if(this.mandatoryFieldObj.tobStr != null && this.mandatoryFieldObj.tobStr != ""){
      if(this.mandatoryFieldObj.tobStr.includes("undefined")){
        this.mandatoryFieldTempObj.timeOfBirthHours = null;
        this.mandatoryFieldTempObj.timeOfBirthMinutes = null;
      }else{
        console.log("time: "+this.mandatoryFieldObj.tobStr.slice(0,2) + "---" +this.mandatoryFieldObj.tobStr.slice(3,5) + "--" + this.mandatoryFieldObj.tobStr.slice(6,8));
        this.mandatoryFieldTempObj.timeOfBirthHours = this.mandatoryFieldObj.tobStr.slice(0,2);
        this.mandatoryFieldTempObj.timeOfBirthMinutes = this.mandatoryFieldObj.tobStr.slice(3,5);
        tobMeridian =this.mandatoryFieldObj.tobStr.slice(6,8);
        if(tobMeridian == 'AM'){
          this.mandatoryFieldTempObj.timeOfBirthMeridium = "AM";
        }else if(tobMeridian == 'PM'){
          this.mandatoryFieldTempObj.timeOfBirthMeridium = "PM";
        }
      }
    }

    if(this.mandatoryFieldObj.toaStr != null && this.mandatoryFieldObj.toaStr != ""){
      console.log("time: "+this.mandatoryFieldObj.toaStr.slice(0,2) + "---" +this.mandatoryFieldObj.toaStr.slice(3,5) + "--" + this.mandatoryFieldObj.tobStr.slice(6,8));
      this.mandatoryFieldTempObj.timeOfAdmHours = this.mandatoryFieldObj.toaStr.slice(0,2);
      this.mandatoryFieldTempObj.timeOfAdmMinutes = this.mandatoryFieldObj.toaStr.slice(3,5);
      toaMeridian =this.mandatoryFieldObj.toaStr.slice(6,8);
      if(toaMeridian == 'AM'){
        this.mandatoryFieldTempObj.timeOfAdmMeridium = "AM";
      }else if(toaMeridian == 'PM'){
        this.mandatoryFieldTempObj.timeOfAdmMeridium = "PM";
      }
    }
  }
  isEmpty(object : any) : boolean {
    if (object == null || object == '' || object == 'null' || object.length == 0) {
      return true;
    }
    else {
      return false;
    }
  };

  printAnthroprometry(){
    console.log('in printAnthroprometry');
    console.log(this.weightTrackingList);
    localStorage.setItem('anthroprometryData', JSON.stringify(this.weightTrackingList));
    this.router.navigateByUrl('/dash/counselling-sheet-print');
  }

  ngOnInit() {
    setInterval(function(){
        $(".blinktodiv").toggleClass("backgroundRed");
     },1000)
  }

}
