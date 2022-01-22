import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { DailyProgress } from './dailyProgress';
import { APIurl } from '../../../model/APIurl';
import { AppComponent } from '../app.component';
import { ExportCsv } from '../export-csv';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'nursing-anthropometry',
  templateUrl: './nursing-anthropometry.component.html',
  styleUrls: ['./nursing-anthropometry.component.css']
})
export class NursingAnthropometryComponent implements OnInit {

  isFullTableVisible : boolean;
  pivotDate : any;
  dailyProgressDate : any;
  username : string;
	child : any;
  apiData : APIurl;
	gestationString : string;
	uhid : string;
	dateOfBirth : any;
	doa : any;
  admitDate : any;
	sex : string;
	todayWeight : any;
  workingWeight : any;
  router : Router;
  http : Http;
  vanishSubmitResponseVariable : boolean;
  responseMessage : string;
  responseType : string;
  d : any;
  dailyProgress : DailyProgress;
  date : Date;
  datetimedialog : Date;
  datetime: Date;
  weeks : any;
  ageDays : number;
  diff : number;
  lifeWeeks : number;
  ageMonth : number;
  weeksAfterDayAdd : number;
  totalDaysOfGestation : number;
  lifeDays : number;
  isAnthropometryVisible : boolean;
  isExpand :boolean;
  gaWeekAtBirth : any;
  gaDayAtBirth : any;
  commentVitals : any;
  fromDateNull : boolean;
	toDateNull : boolean;
	fromToError : boolean;
	fromDateTableNull : boolean;
	toDateTableNull : boolean;
	fromToTableError : boolean;
  copiedSentData : Array<any>;
  isLoaderVisible : boolean;
  loaderContent : string;
  minDate : string;
  maxDate : Date;
  ageDaysdiff : number;
  weightLabel : boolean;
  cgaAgeDays : number;
  toDateTable : Date;
  fromDateTable : Date;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.pivotDate = new Date();
    this.dailyProgressDate = this.pivotDate;
    this.isFullTableVisible = false;
    this.username = "test";
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
    this.workingWeight = JSON.parse(localStorage.getItem('selectedChild')).workingWeight;
    this.gestationString = this.child.gestation;
    this.uhid = this.child.uhid;
    this.dateOfBirth = this.child.dob;
    this.doa = this.child.admitDate;
    this.admitDate = this.child.admitDate;
    this.sex = this.child.gender;
    this.date = new Date();
    this.datetimedialog = new Date();
    this.datetime = new Date();
    this.dailyProgress = new DailyProgress();
    this.isAnthropometryVisible = true;
    this.isExpand = false;
    this.copiedSentData = [];
    this.isLoaderVisible = false;
    this.weightLabel = false;
    this.toDateTable = new Date();
    this.fromDateTable = new Date();
    this.init();
  }

  init() {
    this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM'){
      tempHr = '00';
    }else if(tempHr != 12 && tempMer == 'PM'){
      tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    this.fromDateTable = new Date(this.minDate);
    this.getDailyProgressData();
  };
  weightValidate(){
    if(this.dailyProgress.babyVisit.currentdateweight.includes('.')){
      this.weightLabel = true;
      this.dailyProgress.babyVisit.currentdateweight = null;
    }else{
      this.weightLabel = false;
    }
  }
  anthropometryVisible(){
    this.isAnthropometryVisible = !this.isAnthropometryVisible;
  }
  clickExpand(){
    this.isExpand = !this.isExpand;
  }

   getDailyProgressData  = function() {
     try{
       this.http.request(this.apiData.getDailyProgress + "/" + this.dailyProgressDate + "/"+ this.dateOfBirth+ "/"+ this.doa + "/" + this.uhid + "/" + this.username,)
       .subscribe(res => {
         this.dailyProgress = res.json();

         this.copiedSentData = Object.assign([],this.dailyProgress.listBabyVisit);
         console.log(this.dailyProgress);
         this.calculateOtherDetails();

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

   setBabyVisits = function() {

     console.log("in setBaby Visit");

			var childObjectTemp = JSON.parse(localStorage.getItem('selectedChild'));

			if(childObjectTemp.workingWeight==null){
				childObjectTemp.workingWeight = this.dailyProgress.babyVisit.currentdateweight;
				this.dailyProgress.babyVisit.workingweight = this.dailyProgress.babyVisit.currentdateweight;
			}else{
				if(childObjectTemp.workingWeight*1 < this.dailyProgress.babyVisit.currentdateweight*1){
					childObjectTemp.workingWeight = this.dailyProgress.babyVisit.currentdateweight;
					this.dailyProgress.babyVisit.workingweight = this.dailyProgress.babyVisit.currentdateweight;
				}
			}
			childObjectTemp.currentdateweight = childObjectTemp.workingWeight;
			console.log(this.dailyProgressDate);
			this.d = new Date();
			this.d = this.d.setHours(0, 0, 0, 0);
			var currentDate = JSON.parse(JSON.stringify(this.dailyProgressDate));
      currentDate = new Date(currentDate);
			currentDate = currentDate.setHours(0, 0, 0, 0);
			if (currentDate == this.d){
				childObjectTemp.todayWeight = this.dailyProgress.babyVisit.currentdateweight;
				childObjectTemp.difference = childObjectTemp.todayWeight - childObjectTemp.lastDayWeight;
			}
      else if(this.d - currentDate < (86400000*2)){
				childObjectTemp.lastDayWeight = this.dailyProgress.babyVisit.currentdateweight;
				if(childObjectTemp.todayWeight != null && childObjectTemp.todayWeight != undefined){
					childObjectTemp.difference = childObjectTemp.todayWeight - childObjectTemp.lastDayWeight;
				}
			}
			else{

			}

			this.todayWeight = childObjectTemp.todayWeight;
			console.log("today weight and max is set "+this.todayWeight+" "+childObjectTemp);
			localStorage.removeItem("selectedChild");
			localStorage.setItem('selectedChild',JSON.stringify(childObjectTemp));
			this.dailyProgress.babyVisit.creationtime = this.dailyProgressDate;
      console.log(this.dailyProgressDate);
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
      try
          {

            this.http.post(this.apiData.setBabyVisit + this.dailyProgressDate + "/" + this.uhid + "/", this.dailyProgress).
              subscribe(res => {
                this.handleAnthropometryData(res.json());
           },
                err => {
                  console.log("Error occured.")
                }
               );
          }
          catch(e){
            console.log("Exception in http service:"+e);
          };
			// console.log(loggedInUserObj.userName);
}
      handleAnthropometryData(response: any){
        console.log("outcome...");
        console.log(response);
        this.dailyProgress.babyVisit = response.returnedObject;
        this.isLoaderVisible = false;
        this.loaderContent = '';
        this.vanishSubmitResponseVariable = true;
				this.responseMessage = response.message;
				this.responseType = response.type;
        this.getDailyProgressData();
    	}

      viewFullTable = function(){
			this.isFullTableVisible = !this.isFullTableVisible;
		}

    calculateOtherDetails = function() {
        console.log("calculate gestation and age");
        console.log(this.dailyProgressDate);
        console.log(this.child.dob);

        //console.log(formattedDate.getTime());
        if(!this.dailyProgressDate){
          this.dailyProgressDate = new Date();
        }
        var obj = JSON.parse(JSON.stringify(this.dailyProgressDate));
        var newDate = Date.parse(obj);
        var tempDate = new Date(newDate);
        tempDate.setHours(0);
        tempDate.setMinutes(0);

        this.diff = tempDate.getTime() - Date.parse(this.child.dob);
        this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
        this.diff = Math.ceil(this.diff);
        this.ageDays = parseInt(this.diff);
        var dateTimeBirth = new Date(this.child.dob);

        var tobStr = this.child.timeOfBirth.split(',');
        if(tobStr[2] == 'PM' && parseInt(tobStr[0]) != 12) {
          dateTimeBirth.setHours(12 + parseInt(tobStr[0]));
        } else if (tobStr[2] == 'AM' && parseInt(tobStr[0]) == 12){
            dateTimeBirth.setHours(0);
        } else {
            dateTimeBirth.setHours(parseInt(tobStr[0]));
        }
        dateTimeBirth.setMinutes(parseInt(tobStr[1]));
        // long ageYear =diff/(365*30*24*60*60*1000);

        if(this.gestationString) {
          this.weeks = this.gestationString.split("Weeks");
          var days = this.gestationString.match("Weeks(.*)Days");
          this.weeks = parseInt(this.weeks[0]);
          days = parseInt(days[1]);
          this.dailyProgress.babyVisit.gestationWeek = this.weeks;
          this.dailyProgress.babyVisit.gestationDays = days;
          this.gaWeekAtBirth = this.weeks;
          this.gaDayAtBirth = days;
        }
        else {
          this.weeks=0;
          this.dailyProgress.babyVisit.gestationWeek = 0;
          this.dailyProgress.babyVisit.gestationDays = 0;
          this.gaWeekAtBirth = null;
          this.gaDayAtBirth = null;
        }

        if (this.ageDays > 7) {
          this.lifeWeeks = (this.ageDays / 7);
          this.lifeWeeks = parseInt(this.lifeWeeks);
          this.lifeDays = this.ageDays - this.lifeWeeks * 7;
          this.dailyProgress.babyVisit.gestationWeek += this.lifeWeeks;
          this.lifeDays = parseInt(this.lifeDays);
          this.totalDaysOfGestation = this.lifeDays + this.dailyProgress.babyVisit.gestationDays;
          if (this.totalDaysOfGestation > 6) {
            this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
            this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
            this.dailyProgress.babyVisit.gestationWeek += parseInt(this.weeksAfterDayAdd + "");
            this.dailyProgress.babyVisit.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");
          } else {
            this.dailyProgress.babyVisit.gestationDays = parseInt(this.totalDaysOfGestation + "");
          }
        } else {
          this.totalDaysOfGestation = this.ageDays + this.dailyProgress.babyVisit.gestationDays;
          if (this.totalDaysOfGestation > 6) {
            this.weeksAfterDayAdd = (this.totalDaysOfGestation / 7);
            this.weeksAfterDayAdd = parseInt(this.weeksAfterDayAdd);
            this.dailyProgress.babyVisit.gestationWeek += parseInt(this.weeksAfterDayAdd + "");
            this.dailyProgress.babyVisit.gestationDays = parseInt((this.totalDaysOfGestation - this.weeksAfterDayAdd * 7) + "");
          } else {
            this.dailyProgress.babyVisit.gestationDays = parseInt(this.totalDaysOfGestation + "");
          }
        }
        if(this.dailyProgress.babyVisit.gestationDays == 0){
          this.dailyProgress.babyVisit.gestationDays = 6;
          this.dailyProgress.babyVisit.gestationWeek = this.dailyProgress.babyVisit.gestationWeek - 1;
        }
        else{
          this.dailyProgress.babyVisit.gestationDays = this.dailyProgress.babyVisit.gestationDays - 1;
        }

        var dailyProgressDateobj = JSON.parse(JSON.stringify(this.dailyProgressDate));
        var dateTimeBirthobj = JSON.parse(JSON.stringify(dateTimeBirth));
        dailyProgressDateobj = Date.parse(dailyProgressDateobj);
        dailyProgressDateobj= new Date(dailyProgressDateobj);
        dateTimeBirthobj = Date.parse(dateTimeBirthobj);
        dateTimeBirthobj = new Date(dateTimeBirthobj);
        dailyProgressDateobj.setHours(0);
        dailyProgressDateobj.setMinutes(0);
        dateTimeBirthobj.setHours(0);
        dateTimeBirthobj.setMinutes(0);

        //DOL should decrease according to time of birth. DOL should increase if it crosses the current time
        if(((this.dailyProgressDate.getTime() - dailyProgressDateobj.getTime()) - (dateTimeBirth.getTime() - dateTimeBirthobj.getTime())) < 0){
          this.ageDays = this.ageDays - 1;
        }

        this.dailyProgress.babyVisit.currentage = this.ageDays;
};

openCommentsModal = function(comment : any){
  this.commentVitals = comment;
  $("#commentsOverlay").css("display", "none");
  $("#NursingVitalsPopup").toggleClass("showing");
  $('body').css('overflow', 'auto');
}
closeModalNursingVitals = function(){
  console.log("closeNursingVitalsPopup closing");
  $("#commentsOverlay").css("display", "none");
  $('body').css('overflow', 'auto');
  $("#NursingVitalsPopup").toggleClass("showing");
  this.commentVitals = null;
}

filterNursingAnthropometry(fromDateTable : Date, toDateTable : Date, flag : String) {

  this.fromDateTableNull = false;
  this.toDateTableNull = false;
  this.fromToTableError = false;

  if(fromDateTable == null) {
    this.fromDateTableNull = true;
  } else if(toDateTable == null) {
    this.toDateTableNull = true;
  } else if(fromDateTable >= toDateTable) {
    this.fromToTableError = true;
  } else {
    var data = [];
    this.copiedSentData = [];
    for(var i=0; i < this.dailyProgress.listBabyVisit.length; i++) {
      var item = this.dailyProgress.listBabyVisit[i];

      if(item.creationtime >= fromDateTable.getTime() && item.creationtime <= toDateTable.getTime()) {
          var obj = Object.assign({}, item);
          data.push(obj);
          this.copiedSentData.push(obj);
      }
    }
    data.push(this.dailyProgress.birthBabyVisit);

    if(flag == 'print') {
      this.printOrder(data,fromDateTable,toDateTable);
    } else if(flag == 'csv') {
      this.exportOrdersCsv(data);
    }
  }
}

printOrder(dataForPrint : any, fromDateTable : Date, toDateTable : Date){
  var tempArr = [];
  tempArr = dataForPrint;
  dataForPrint = {};
  console.log(dataForPrint);
  dataForPrint.data = tempArr;
  dataForPrint.whichTab = 'Nursing Anthropometry';
  dataForPrint.from_time = fromDateTable.getTime();
  dataForPrint.to_time = toDateTable.getTime();;
  localStorage.setItem('printNursingDataObjNotes', JSON.stringify(dataForPrint));
  this.router.navigateByUrl('/nursing/nursing-print');
}
exportOrdersCsv(dataForCSV : any) {
  var fileName = "INICU-Nursing-Anthropometry.csv";
  var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
  var finalCSVData = encodeURI(csvData);

  var downloadLink = document.createElement("a");
  downloadLink.setAttribute("href", finalCSVData);
  downloadLink.setAttribute("download", fileName);
  downloadLink.click();
}

populateAnthropometryObservationSheet = function(obj){
  console.log(obj);
  var tempObj = obj;
  this.ageDays = tempObj.currentage;
  this.dailyProgress.babyVisit = tempObj;
  this.dailyProgressDate = new Date(tempObj.visitdate + ' ' + tempObj.visittime);
  console.log(this.dailyProgressDate);
}
  ngOnInit() {
  }

}
