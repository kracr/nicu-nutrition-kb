import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { TempObject } from './tempObject';
import { OutcomeRecord } from './outcomeRecord';
import * as $ from 'jquery/dist/jquery.min.js';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { AppComponent } from '../app.component';
@Component({
  selector: 'outcomes',
  templateUrl: './outcomes.component.html',
  styleUrls: ['./outcomes.component.css']
})
export class OutcomesComponent implements OnInit {
  isLoaderVisible : boolean;
  loaderContent : string;
  http: Http;
  hoursList : Array<string>;
  minutesList : Array<string>;
  outcomeRecord : any;
  apiData : APIurl;
  tempObject : TempObject;
  date : Date;
  datetimedialog : Date;
  datetime: Date;
  minDate : String;
  maxDate : Date;
  dischargeMedicationList : any;
  tempList : any
  message : any;
  selectAll : boolean;
  constructor(http: Http,private router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.http = http;
    this.tempObject = new TempObject;
    this.date = new Date();
    this.datetimedialog = new Date();
    this.datetime = new Date();
    this.apiData = new APIurl();
    this.dischargeMedicationList = [];
    this.tempList = [];
    this.selectAll = null;
    this.message = 'Baby cannot be discharged Seizures, Asphyxia, Apnea, Pneumothorax, PPHN assessments are active. Please make all these assessments Inactive before discharging the patient.'
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
    this.getOutcomeData();
  };

getOutcomeData  = function() {

    let child = JSON.parse(localStorage.getItem('selectedChild'));
    try{
        this.http.request(this.apiData.getOutcome + child.uhid + "/",)
        .subscribe(res => {
    this.outcomeRecord = res.json();
    this.dischargeMedicationList = [];
    if(this.outcomeRecord.medicationList != null && this.outcomeRecord.medicationList.length > 0){
      for(var i = 0; i < this.outcomeRecord.medicationList.length; i++){
          this.dischargeMedicationList.push(this.outcomeRecord.medicationList[i]);
      }
      this.outcomeRecord.medicationList = [];
    }

    if(this.outcomeRecord.entrytime != null && this.outcomeRecord.entrytime != ''){
      this.outcomeRecord.entrytime =  new Date(this.outcomeRecord.entrytime);
    }
    else{
      this.outcomeRecord.entrytime = new Date();
    }

    if(this.outcomeRecord.deathDate != null && this.outcomeRecord.deathDate != ''){
      this.outcomeRecord.deathDate =  new Date(this.outcomeRecord.deathDate);
    }
    else{
      this.outcomeRecord.deathDate = new Date();
    }

    this.outcomeRecord.appointmenttime = new Date(this.outcomeRecord.entrytime.getTime() + (2 * 24 * 60 * 60 * 1000));
    this.outcomeRecord.makeSummaryFlag = 'true';
    console.log(this.outcomeRecord);

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

   saveDischargedSummary = function(){
     $("#CancelotherPopUp").removeClass("showing");
     $("#otherDischargeOptionOverlay").removeClass("show");
     console.log(this.outcomeRecord);
      let child = JSON.parse(localStorage.getItem('selectedChild'));
			this.outcomeRecord.uhid = child.uhid;
			this.outcomeRecord.episodeid = "empty";
			console.log(this.outcomeRecord.entrytime);
			if(this.outcomeRecord.outcomeType == "Death"){
				console.log(this.outcomeRecord.deathDate);
			}else{this.outcomeRecord.deathDate = null}
      console.log(this.outcomeRecord);
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
      try
          {
            this.http.post(this.apiData.saveOutcome, this.outcomeRecord).
              subscribe(res => {
                this.handleOutcomeData(res.json());
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

    handleOutcomeData(response: any){
      console.log("outcome...");
      console.log(response);
      this.tempObject.submitType = response.type;
      this.tempObject.submitMessage = response.message;
      this.isLoaderVisible = false;
      this.loaderContent = 'Saving...';
      if(this.outcomeRecord.makeSummaryFlag == "true"){
        this.router.navigateByUrl('/discharge/discharge-summary');
      }else{
        this.getOutcomeData();
      }
  	}
   getDischargePopup = function() {
      if(this.outcomeRecord != null && this.outcomeRecord != undefined && this.outcomeRecord != ''){
        if(this.outcomeRecord.reason != null && this.outcomeRecord.reason != undefined && this.outcomeRecord.reason != ''){
          if(this.outcomeRecord.outcomeType == 'Discharge'){
            console.log("continue");
            $("#CancelPopUp").addClass("showing");
            $("#dischargeRejectOverlay").addClass("show");
          }
          if(this.outcomeRecord.outcomeType == 'LAMA' || this.outcomeRecord.outcomeType == 'Death' ||
            this.outcomeRecord.outcomeType == 'Transfer' || this.outcomeRecord.outcomeType == 'Discharge On Request'){
            $("#CancelotherPopUp").addClass("showing");
            $("#otherDischargeOptionOverlay").addClass("show");
          }
        }else{
              this.saveDischargedSummary();
        }
      }
    }

    getDischargePrint = function(){
      this.router.navigateByUrl('/discharge/case-summary-print');
    }

    cancelOkPopUp(){
      $("#CancelPopUp").removeClass("showing");
      $("#dischargeRejectOverlay").removeClass("show");
    };

    cancelPopUp(){
      $("#CancelotherPopUp").removeClass("showing");
      $("#otherDischargeOptionOverlay").removeClass("show");
    };

    submitDischarge = function() {
  		console.log('submitDischarge');
  		if(this.dischargeMedicationList != null && this.outcomeRecord.outcomeType != 'Death'){
        if(this.dischargeMedicationList.length > 0){
          //To Mark Stopped Medication
          this.isMedicationStopped = [];
          for(var i = 0; i < this.dischargeMedicationList.length; i++) {
            var obj = this.dischargeMedicationList[i];
            if(obj.isactive){
              this.isMedicationStopped[i] = false;
            }
          }

          for(var i = 0; i < this.dischargeMedicationList.length; i++) {
            var obj = this.dischargeMedicationList[i];
            if(obj.isactive){
              this.showMedicationPopUp();
              break;
            }
          }
        }
        else{
          if(this.dischargeMedicationList == null){
            console.log('Medication List is Empty');
          }
          this.saveDischargedSummary();
        }
      }
      else{
        console.log('Medication List is Null');
        this.saveDischargedSummary();
      }
    }

    showMedicationPopUp = function() {
      this.cancelPopUp();
      $("#medicationDischargePopup").addClass("showing");
      $("#MedicationDischargeOverlay").addClass("show");
    };
    closeMedicationPopUp = function(){
      $("#medicationDischargePopup").removeClass("showing");
      $("#MedicationDischargeOverlay").removeClass("show");
    };

    saveMedicine(){
      for(var i = 0; i < this.dischargeMedicationList.length; i++) {
        var obj = this.dischargeMedicationList[i];
        if(obj.isactive == true && obj.enddate != null){
          obj.enddate = new Date(obj.enddate);
          obj.isactive = false;
          this.outcomeRecord.medicationList.push(obj);
          console.log("List So Far : " + obj.enddate );
        }
        else{
          if(obj.isactive == true && obj.isContinue != null && obj.isContinue == true
              && obj.continueReason != null && obj.continueReason != ''){
            this.outcomeRecord.medicationList.push(obj);
            console.log("List So Far : " + obj.isContinue + " " + obj.continueReason);
          }
          else{
            obj.isContinue = null;
            this.tempList.push(obj);
          }
        }
      }
      if(this.tempList.length == 0){
        this.saveDischargedSummary();
      }
      else{
        this.dischargeMedicationList = [];
        this.dischargeMedicationList = this.tempList;
        this.tempList = [];
      }
      this.closeMedicationPopUp();
    }

    stopAllMed(selectAll : any) {
      if(this.selectAll == null || this.selectAll == false){
        this.selectAll = true;
      }
      else{
        this.selectAll = false;
      }

      for(var index=0;index<this.dischargeMedicationList.length;index++){
        this.toggleIscontinue(index);
        if(this.selectAll == true){
          this.dischargeMedicationList[index].isStopped = true;
          this.dischargeMedicationList[index].enddate = new Date();
        }
        else{
          this.dischargeMedicationList[index].isStopped = false;
          this.dischargeMedicationList[index].enddate = null;
        }
      }
    }

    setEndDate(index : any){
      if(this.dischargeMedicationList[index].isStopped == null || this.dischargeMedicationList[index].isStopped == false){
        this.dischargeMedicationList[index].isStopped = true;
      }
      else{
        this.dischargeMedicationList[index].isStopped = false;
      }
      this.updateEndDate(index);
    }

    updateEndDate(index : any){
      this.toggleIscontinue(index);
      if(this.dischargeMedicationList[index].isStopped == true){
        this.dischargeMedicationList[index].enddate = new Date();
      }
      else{
        this.dischargeMedicationList[index].enddate = null;
      }
    }

    commentBoxEnable(index : any){
      this.dischargeMedicationList[index].isContinue = true;
      this.dischargeMedicationList[index].isStopped = false;
      this.dischargeMedicationList[index].enddate = null;
      this.selectAll = false;
    }

    toggleIscontinue(index : any){
      this.dischargeMedicationList[index].isContinue = false;
      this.dischargeMedicationList[index].continueReason = "";
    }


    ngOnInit() {
    }

}
