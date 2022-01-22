import { Component, OnInit, OnDestroy } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { NursingObservationData } from './nursingObservationData';
import { NursingIntakeOutputJSON } from './nursingIntakeOutputJSON';
import { Chart } from 'angular-highcharts';
import { APIurl } from '../../../model/APIurl';
import { AppComponent } from '../app.component';
import { ExportCsv } from '../export-csv';
import * as $ from 'jquery/dist/jquery.min.js';
import {Observable} from 'rxjs/Rx';

@Component({
  selector: 'nursing-charts',
  templateUrl: './nursing-charts.component.html',
  styleUrls: ['./nursing-charts.component.css']
})
export class NursingChartsComponent implements OnInit, OnDestroy {

  router : Router;
  pupilSizeValue:string;
  http : Http;
  date : Date;
  comp : Date;
  apiData : APIurl;
  datetimedialog : Date;
  datetime: Date;
  VitalParameterWhichTab : string;
  nursingObservationData : NursingObservationData;
  childObject : any;
  loggedInUserObj : any;
  isNotesDisabled : boolean;
  tabVisible : string;
  flagIsSubmitPossible : true;
  BloodWhichTab : string;
  reactField : boolean;
  sizeField : boolean;
  hours : any;
  min : any;
  meri : any;
  selectedDate : any;
  tempHours : any;
  tempMinutes : any;
  tempSeconds : any;
  tempMeridium : any;
  todaysDate : any;
  bloodStartTimeHr : any;
  bloodStartTimeMin : any;
  bloodStartTimeMeridian : any;
  hrs : string;
  minutes : string;
  seconds : string;
  symptomaticSeizures : any;
  seizuresType : any;
  symptomatic : any;
  vanishSubmitResponseVariable : boolean;
  responseMessage : string;
  responseType : string;
  orderId : any;
  dropdownData : any;
  eventDate : any;
  clock : any;
  tickInterval : any;
  printDataObjVitalsToDateVitals: any;
  printDataObjVitalsFromDateVitals: any;
  showGraphView : boolean;
  hrList : any;
  cvpList : any;
  spo2List : any;
  rrList : any;
  systBp : any;
  diastBp :any;
  peripheralTempList : any;
  centralTempList : any;
  rbsList : any;
  etco2List : any;
  graphDataPopulate : boolean;
  heightChart : Chart;
  primaryFeedTotal : any;
  formulaTotal : any;
  feedTotal : any;
  lipidDelivered : any;
  tpnDelivered : any;
  gastricTotal : any;
  urineTotal : any;
  drainTotal : any;
  IntakeOutputEntryDate : any;
  nursingIntakeOutputData : NursingIntakeOutputJSON;
  isenteralOrderVisible : boolean;
  isparenteralOrderVisible : boolean;
  ismedicationOrderVisible : boolean;
  feedTypeArr : any[];
  isEditPrimary : any;
  nursingChartsTab : string;
  printDataObjEventsFromDate : any;
  printDataObjEventsToDate : any;
  printDataObjVentilatorFromDate : any;
  printDataObjVentilatorToDate : any;
  printDataObjBloodGasFromDate : any;
  printDataObjBloodGasToDate : any;
  printDataObjIntakeOutputFromDate : any;
  printDataObjIntakeOutputToDate : any;
  vitalNursingList : any;
  entryNursingList : any;
  isInputOutputOrderSectionVisible : boolean;
  isInputOutputExecutionSectionVisible : boolean;
  isAllExecutionChecked : boolean;
  isOtherAdditivesExecutionChecked : boolean;
  isParenteralExecutionChecked : boolean;
  isAdditionalPNExecutionChecked : boolean;
  isEnteralExecutionChecked : boolean;
  isEnteralOrderChecked : boolean;
  isParenteralOrderChecked : boolean;
  isAdditionalPNChecked : boolean;
  isOrderOtherAdditivesChecked : boolean;
  isOrderAllChecked : boolean;
  commentVitals : any;
  isExpandPN : boolean;
  isLoaderVisible : boolean;
  loaderContent : string;
  minDate : string;
  maxDate : Date;
  enFeedStr : string;
  totalEn : number;
  totalPn : number;
  totalUrine : number;
  totalFeed : number;
  urineMlKgHr : number;
  isOutputSummary : boolean;
  isEnteralSummary : boolean;
  isParenteralSummary : boolean;
  isOtherAdditivesSummary : boolean;
  totalCyanosis : number;
  totalRetractions : number;
  totalGrunting : number;
  totalAirEntry : number;
  totalRespiratoryRate : number;
  downesValueCalculated : number;
  downesTabContent : string;
  totalDownyScore : string;
  pollingData: any;
  calBrand : string;
  ironBrand : string;
  mctBrand : string;
  multiVitaminBrand : string;
  vitaminDBrand : string;
  isDisable : boolean;
  isShowDisable : boolean;
  totalRemainingEnVolume : number;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.flagIsSubmitPossible = true;
    this.VitalParameterWhichTab = 'vital';
    this.BloodWhichTab = 'blood';
    this.downesTabContent = 'downes';
    this.showGraphView = false;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.symptomaticSeizures = [];
    this.seizuresType = [];
    this.symptomatic = [];
    this.date = new Date();
    this.datetimedialog = new Date();
    this.datetime = new Date();
    this.nursingObservationData = new NursingObservationData();
    this.primaryFeedTotal = 0;
    this.formulaTotal = 0;
    this.feedTotal = 0;
    this.lipidDelivered = 0;
    this.tpnDelivered = 0;
    this.gastricTotal = 0;
    this.urineTotal = 0;
    this.drainTotal = 0;
    this.totalRemainingEnVolume = 0;
    this.isenteralOrderVisible= true;
    this.isparenteralOrderVisible = true;
    this.ismedicationOrderVisible = true;
    this.nursingIntakeOutputData = new NursingIntakeOutputJSON();
    this.nursingChartsTab = 'vital';
    this.tabVisible = 'vitalParameters';
    this.isInputOutputOrderSectionVisible = true;
    this.isInputOutputExecutionSectionVisible = true;
    this.isExpandPN = false;
    this.isLoaderVisible = false;
    this.totalEn = 0;
    this.totalPn = 0;
    this.totalUrine = 0;
    this.totalFeed = 0;
    this.urineMlKgHr = 0;
    this.totalCyanosis = null;
    this.totalRetractions = null;
    this.totalGrunting = null;
    this.totalAirEntry = null;
    this.totalRespiratoryRate = 0;
    this.downesValueCalculated = 0;
    this.totalDownyScore = "";
    this.calBrand = null;
    this.ironBrand = null;
    this.mctBrand = null;
    this.multiVitaminBrand = null;
    this.vitaminDBrand = null;
    this.isDisable = false;
    this.isShowDisable = false;
    this.init();
  }

  init() {
    this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM'){
      tempHr = '00';
    }else if(tempHr != 12 && tempMer == 'PM'){
      tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).admitDate) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    if(JSON.parse(localStorage.getItem('comingFromWhichModule')) != undefined){
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Vital Parameters'){
        this.nursingChartsTab = 'vital';
        this.tabVisible = 'vitalParameters';
        localStorage.removeItem('comingFromWhichModule');
      }
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Events'){
        this.nursingChartsTab = 'event';
        this.tabVisible = 'vitalParameters';
        localStorage.removeItem('comingFromWhichModule');
      }
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Ventilator'){
        this.nursingChartsTab = 'ventilator';
        this.tabVisible = 'ventilatorSetting';
        localStorage.removeItem('comingFromWhichModule');
      }
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Blood Gas'){
        this.nursingChartsTab = 'blood';
        this.tabVisible = 'bloodGas';
        localStorage.removeItem('comingFromWhichModule');
      }
      if(JSON.parse(localStorage.getItem('comingFromWhichModule')) == 'Intake/Output'){
        this.getNursingIntakeOutput();
        this.tabVisible = 'input-output';
        this.nursingChartsTab = 'input-output';
        localStorage.removeItem('comingFromWhichModule');
        if(localStorage.getItem('refFeedTypeList') != undefined){
          localStorage.removeItem('refFeedTypeList');
        }
        if(localStorage.getItem('totalEn') != undefined){
          localStorage.removeItem('totalEn');
        }
        if(localStorage.getItem('totalPn') != undefined){
          localStorage.removeItem('totalPn');
        }
        if(localStorage.getItem('totalUrine') != undefined){
          localStorage.removeItem('totalUrine');
        }
        if(localStorage.getItem('totalFeed') != undefined){
          localStorage.removeItem('totalFeed');
        }
        if(localStorage.getItem('urineMlKgHr') != undefined){
          localStorage.removeItem('urineMlKgHr');
        }
      }

    }
    this.isNotesDisabled = false;
    this.selectedDate = new Date();
    this.getNursingObservationInfo(this.tabVisible);
    this.getNursingDropdown();
    this.TimeCtrl();
    this.getNursingIntakeOutput();
    this.printPdfIntakeOutput('table');
    var todayDate = new Date();
    todayDate.setHours(0);
    todayDate.setMinutes(0);
    todayDate.setSeconds(0);
    var currentDate = new Date();
    this.printDataObjVitalsFromDateVitals = todayDate;
    this.printDataObjVitalsToDateVitals = currentDate;
    this.printDataObjEventsFromDate = todayDate;
    this.printDataObjEventsToDate = currentDate;
    this.printDataObjBloodGasFromDate = todayDate;
    this.printDataObjBloodGasToDate = currentDate;
    this.printDataObjVentilatorFromDate = todayDate;
    this.printDataObjVentilatorToDate = currentDate;
  };

  ngOnInit() {
    this.refrestVital();
  }

  refrestVital() {
    console.log("Polling activated");
    let timer = 300000; // in 5min
    this.pollingData = Observable.interval(timer)
    .switchMap(
      () => this.http.request(this.apiData.getVitalInfoByDate + "/" + this.childObject.uhid +"/"+ this.nursingObservationData.userDate,))

        .subscribe((res) => {
          this.handleVitalInfoByDate(res.json());
           console.log("polling Observable", res);// see console you get output every 5 sec
        },
        err => {
          console.log("Error occured.");
        }
      );
  }

  isEmpty(object : any) : boolean {
    if (object == null || object == '' || object == 'null' || object.length == 0) {
      return true;
    }
    else {
      return false;
    }
  };

  visibleTab = function(tabValue){
    this.isNotesDisabled = false;
    this.tabVisible = tabValue;
    this.flagIsSubmitPossible  = true;
    this.getNursingIntakeOutput();
    if(this.nursingChartsTab == 'blood'){
      var bloodGasTemp = 'blood';
      this.nursingObservationData.bloodGasInfo.emptyObj = bloodGasTemp;
    }
    if(this.nursingChartsTab == 'ventilator'){
      var ventilatorTemp = 'ventilator';
      this.nursingObservationData.ventilatorInfo.emptyObj = ventilatorTemp;
    }
    if(tabValue == 'vital') {
      this.refrestVital();
      console.log("Start Poll for vital");
    } else {
      this.pollingData.unsubscribe();
      console.log("Polling unsubscribed");
    }
      this.getNursingObservationInfo(this.tabVisible);
  };

  visibleTabBlood = function(tabValue){
    this.BloodWhichTab = tabValue;
    this.nursingChartsTab = tabValue;
    this.flagIsSubmitPossible  = true;//this flag is for checking time in nursing notes entries
    this.getNursingObservationInfo(this.tabVisible);
  };

populateNursingObserVationSheet = function(obj){
  console.log(obj);
  var tempObj = obj;

  this.nursingObservationData.vitalParametersInfo.emptyObj = tempObj;
  this.nursingObservationData.userDate = new Date(tempObj.entryDate);
  console.log(this.nursingObservationData.userDate);
  this.calculateTime('no-vital');
}

populateNursingObserVationSheetEvent = function(obj){
  console.log(obj);
  var tempObj = obj;

  this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj = tempObj;
  console.log(new Date(tempObj.creationtime));
  this.eventDate = new Date(tempObj.creationtime);
}

viewBloodGas = function(obj){
  console.log(obj);
  var tempObj = obj;

  this.nursingObservationData.bloodGasInfo.emptyObj = tempObj;
  this.nursingObservationData.userDate = new Date(tempObj.entryDate);
  console.log(this.nursingObservationData.userDate);
  this.calculateTime('find-vital');
}

viewVentilator = function(obj){
  console.log(obj);
  var tempObj = obj;

  this.nursingObservationData.ventilatorInfo.emptyObj = tempObj;
  this.nursingObservationData.userDate = new Date(tempObj.entryDate);
  console.log(this.nursingObservationData.userDate);
  this.calculateTime('find-vital');

}

printPdfVital(execute){
  this.isShowDisable = true;
  console.log('printPdfVital');
  this.printDataObjVitalsFromDateVitals = new Date(this.printDataObjVitalsFromDateVitals);
  this.printDataObjVitalsToDateVitals = new Date(this.printDataObjVitalsToDateVitals);
  var timezoneOffset = new Date().getTimezoneOffset();

  var fromTime = this.printDataObjVitalsFromDateVitals.getTime() + timezoneOffset;
  var toTime = this.printDataObjVitalsToDateVitals.getTime() + timezoneOffset;

  try{
    this.http.request(this.apiData.getNursingVitalPrint + "/" + this.childObject.uhid + "/" + fromTime + "/" + toTime + "/",)
    .subscribe(res => {
      this.handleGetVitalParametersPrintData(res.json(), execute);
    }, err => console.log(err), () => this.isShowDisable = false
    )}
  catch(e){
    console.log("Exception in http service:"+e);
  };
};

handleGetVitalParametersPrintData(response : any, execute : any){
  if(response != null){
    console.log(response);
    if(execute == 'csv') {
      if(response.vitalList != null && response.vitalList.length > 0) {
        for(var i = 0; i < response.vitalList.length; i++) {
          var obj = response.vitalList[i];
          obj.creationtime = this.dateformatter(new Date(obj.creationtime), "gmt", "standard");
          obj.modificationtime = this.dateformatter(new Date(obj.modificationtime), "gmt", "standard");
          obj.entryDate = this.dateformatter(new Date(obj.entryDate), "gmt", "standard");
        }
        this.exportVitalCsv(response.vitalList, "INICU-Vitals.csv");
      }
    } else if (execute == 'table') {
      this.vitalNursingList = response.vitalList;
    } else if (execute == 'print') {
      response.whichTab = 'Vital Parameters';
      response.from_time = this.printDataObjVitalsFromDateVitals.getTime();
      response.to_time = this.printDataObjVitalsToDateVitals.getTime();;
      localStorage.setItem('printNursingDataObjNotes', JSON.stringify(response));
      this.router.navigateByUrl('/nursing/nursing-print');
    }
  }
}

printPdfEvent(execute){
  console.log('printPdfEvent');
  this.printDataObjEventsFromDate = new Date(this.printDataObjEventsFromDate);
  this.printDataObjEventsToDate = new Date(this.printDataObjEventsToDate);
  var timezoneOffset = new Date().getTimezoneOffset();

  var fromTime = this.printDataObjEventsFromDate.getTime() + timezoneOffset;
  var toTime = this.printDataObjEventsToDate.getTime() + timezoneOffset;

  try{
    this.http.request(this.apiData.getNursingEventPrint + "/" + this.childObject.uhid + "/" + fromTime + "/" + toTime + "/",)
    .subscribe(res => {
      this.handleGetEventPrintData(res.json(), execute);
    },
    err => {
      console.log("Error occured.")
    });
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
};

handleGetEventPrintData(response : any, execute : any){
  if(response != null){
    console.log(response);
    if(execute == 'csv') {
      if(response.episodeList != null && response.episodeList.length > 0) {
        for(var i = 0; i < response.episodeList.length; i++) {
          var obj = response.episodeList[i];
          obj.creationtime = this.dateformatter(new Date(obj.creationtime), "gmt", "standard");
          obj.modificationtime = this.dateformatter(new Date(obj.modificationtime), "gmt", "standard");
        }
        this.exportVitalCsv(response.episodeList, "INICU-Episodes.csv");
      }
    } else if (execute == 'table') {
      this.entryNursingList = response.episodeList;
    } else if (execute == 'print') {
        response.whichTab = 'Events';
        response.from_time = this.printDataObjEventsFromDate.getTime();
        response.to_time = this.printDataObjEventsToDate.getTime();;
        localStorage.setItem('printNursingDataObjNotes', JSON.stringify(response));
        this.router.navigateByUrl('/nursing/nursing-print');
    }
  }
}

printPdfVentilator(execute){
  console.log('printPdfVentilator');
  this.printDataObjVentilatorFromDate = new Date(this.printDataObjVentilatorFromDate);
  this.printDataObjVentilatorToDate = new Date(this.printDataObjVentilatorToDate);
  var timezoneOffset = new Date().getTimezoneOffset();

  var fromTime = this.printDataObjVentilatorFromDate.getTime() + timezoneOffset;
  var toTime = this.printDataObjVentilatorToDate.getTime() + timezoneOffset;

  try{
    this.http.request(this.apiData.getNursingVentilatorPrint + "/" + this.childObject.uhid + "/" + fromTime + "/" + toTime + "/",)
    .subscribe(res => {
      this.handleGetVentilatorPrintData(res.json(), execute);
    },
    err => {
      console.log("Error occured.")
    });
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
};

handleGetVentilatorPrintData(response : any, execute : any){
  if(response != null){
    console.log(response);
    if(execute == 'csv') {
      if(response.ventilatorList != null && response.ventilatorList.length > 0) {
        for(var i = 0; i < response.ventilatorList.length; i++) {
          var obj = response.ventilatorList[i];
          obj.entryDate = this.dateformatter(new Date(obj.entryDate), "gmt", "standard");
          obj.creationtime = this.dateformatter(new Date(obj.creationtime), "gmt", "standard");
          obj.modificationtime = this.dateformatter(new Date(obj.modificationtime), "gmt", "standard");
        }
        this.exportVitalCsv(response.ventilatorList, "INICU-Nursing-Ventilator.csv");
      }
    } else if (execute == 'table') {
      this.nursingObservationData.ventilatorInfo.previousData = response.ventilatorList;
    } else if (execute == 'print') {
        response.whichTab = 'Ventilator';
        response.ventModeDropDown = this.dropdownData.ventilatorMode;
        response.from_time = this.printDataObjVentilatorFromDate.getTime();
        response.to_time = this.printDataObjVentilatorToDate.getTime();;
        localStorage.setItem('printNursingDataObjNotes', JSON.stringify(response));
        this.router.navigateByUrl('/nursing/nursing-print');
    }
  }
}

printPdfBloodGas(execute){
  console.log('printPdfBloodGas');
  this.printDataObjBloodGasFromDate = new Date(this.printDataObjBloodGasFromDate);
  this.printDataObjBloodGasToDate = new Date(this.printDataObjBloodGasToDate);
  var timezoneOffset = new Date().getTimezoneOffset();

  var fromTime = this.printDataObjBloodGasFromDate.getTime() + timezoneOffset;
  var toTime = this.printDataObjBloodGasToDate.getTime() + timezoneOffset;

  try{
    this.http.request(this.apiData.getNursingBGPrint + "/" + this.childObject.uhid + "/" + fromTime + "/" + toTime + "/",)
    .subscribe(res => {
      this.handleGetBloodGasPrintData(res.json(), execute);
    },
    err => {
      console.log("Error occured.")
    });
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
};

handleGetBloodGasPrintData(response : any, execute : any){
  if(response != null){
    console.log(response);
    if(execute == 'csv') {
      if(response.bloodGasList != null && response.bloodGasList.length > 0) {
        for(var i = 0; i < response.bloodGasList.length; i++) {
          var obj = response.bloodGasList[i];
          obj.entryDate = this.dateformatter(new Date(obj.entryDate), "gmt", "standard");
          obj.creationtime = this.dateformatter(new Date(obj.creationtime), "gmt", "standard");
          obj.modificationtime = this.dateformatter(new Date(obj.modificationtime), "gmt", "standard");
        }
        this.exportVitalCsv(response.bloodGasList, "INICU-Nursing-Blood-Gas.csv");
      }
    } else if (execute == 'table') {
      this.nursingObservationData.bloodGasInfo.previousData = response.bloodGasList;
    } else if (execute == 'print') {
        response.whichTab = 'Blood Gas';
        response.from_time = this.printDataObjBloodGasFromDate.getTime();
        response.to_time = this.printDataObjBloodGasToDate.getTime();;
        localStorage.setItem('printNursingDataObjNotes', JSON.stringify(response));
        this.router.navigateByUrl('/nursing/nursing-print');
    }
  }
}

printPdfIntakeOutput(execute){
  console.log('printPdfIntakeOutput');
  this.isShowDisable = true;
  this.printDataObjIntakeOutputFromDate = new Date(this.printDataObjIntakeOutputFromDate);
  this.printDataObjIntakeOutputToDate = new Date(this.printDataObjIntakeOutputToDate);

  var fromTime = this.printDataObjIntakeOutputFromDate.getTime();
  var toTime = this.printDataObjIntakeOutputToDate.getTime();

  try{
    this.http.request(this.apiData.getNursingIntakeOutputPrint + "/" + this.childObject.uhid + "/" + fromTime + "/" + toTime + "/",)
    .subscribe(res => {
      this.handleGetIntakeOutputPrintData(res.json(), execute);
      this.isShowDisable = false;
    },err => console.log(err), () => this.isShowDisable = false
    );
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
};

handleGetIntakeOutputPrintData(response : any, execute : any){
  if(response != null){
    console.log(response);
    response.intakeOutputList = this.calculateTotalIntakeOutput(response.intakeOutputList);
    this.isOutputSummary = false;
    this.isEnteralSummary = false;
    this.isParenteralSummary = false;
    this.isOtherAdditivesSummary = false;
    if(response.intakeOutputList.length > 0){
      for(var i=0;i<response.intakeOutputList.length;i++){
        if((this.isOutputSummary != true) && (response.intakeOutputList[i].vomitPassed != null || response.intakeOutputList[i].stoolPassed != null || response.intakeOutputList[i].urinePassed != null || response.intakeOutputList[i].abdomenGirth != null || response.intakeOutputList[i].gastricAspirate != null || response.intakeOutputList[i].urine != null || response.intakeOutputList[i].stool != null || response.intakeOutputList[i].vomit || null && response.intakeOutputList[i].drain != null || response.intakeOutputList[i].outputComment != null)){
            this.isOutputSummary = true;
        }
        if((this.isEnteralSummary != true) && (response.intakeOutputList[i].primaryFeedValue != null || response.intakeOutputList[i].formulaValue != null || response.intakeOutputList[i].actualFeed != null || response.intakeOutputList[i].enteralComment || null)){
          this.isEnteralSummary = true;
        }
        if((this.isParenteralSummary != true) && (response.intakeOutputList[i].lipid_delivered != null || response.intakeOutputList[i].tpn_delivered != null || response.intakeOutputList[i].parenteralComment != null || response.intakeOutputList[i].readymadeDeliveredFeed != null)){
          this.isParenteralSummary = true;
        }
        if((this.isOtherAdditivesSummary != true) && (response.intakeOutputList[i].calciumVolume != null || response.intakeOutputList[i].ironVolume != null || response.intakeOutputList[i].mvVolume != null || response.intakeOutputList[i].otherAdditiveVolume != null || response.intakeOutputList[i].calciumComment != null || response.intakeOutputList[i].ironComment != null || response.intakeOutputList[i].mvComment != null || response.intakeOutputList[i].otherAdditiveComment != null)){
          this.isOtherAdditivesSummary = true;
        }
      }
    }
    if(execute == 'csv') {
      if(response.intakeOutputList != null && response.intakeOutputList.length > 0) {
        for(var i = 0; i < response.intakeOutputList.length; i++) {
          var obj = response.intakeOutputList[i];
          obj.entry_timestamp = this.dateformatter(new Date(obj.entry_timestamp), "gmt", "standard");
          obj.creationtime = this.dateformatter(new Date(obj.creationtime), "gmt", "standard");
          obj.modificationtime = this.dateformatter(new Date(obj.modificationtime), "gmt", "standard");
        }
        this.exportVitalCsv(response.intakeOutputList, "INICU-Nursing-Intake-Output.csv");
      }
    } else if (execute == 'table') {
      this.nursingIntakeOutputData.pastNursingIntakeList = response.intakeOutputList;
    } else if (execute == 'print') {
      response.whichTab = 'Intake/Output';
      response.from_time = this.printDataObjIntakeOutputFromDate.getTime();
      response.to_time = this.printDataObjIntakeOutputToDate.getTime();;
      localStorage.setItem('printNursingDataObjNotes', JSON.stringify(response));
      localStorage.setItem('refFeedTypeList', JSON.stringify(this.nursingIntakeOutputData.refFeedTypeList));
      localStorage.setItem('totalEn', JSON.stringify(this.totalEn));
      localStorage.setItem('totalPn', JSON.stringify(this.totalPn));
      localStorage.setItem('totalUrine', JSON.stringify(this.totalUrine));
      localStorage.setItem('totalFeed', JSON.stringify(this.totalFeed));
      localStorage.setItem('urineMlKgHr', JSON.stringify(this.urineMlKgHr));
      this.router.navigateByUrl('/nursing/nursing-print');
    }
  }
}

exportVitalCsv(dataForCSV : any, fileName : any) {
  var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
  var finalCSVData = encodeURI(csvData);

  var downloadLink = document.createElement("a");
  downloadLink.setAttribute("href", finalCSVData);
  downloadLink.setAttribute("download", fileName);
  downloadLink.click();
}

getNursingDropdown= function(){
  var dataParam={};
  try{

    this.http.request(this.apiData.getNursingDropdown,dataParam)
    .subscribe(res => {
      this.handleDropDownData(res.json());
    },
    err => {
      console.log("Error occured.")
    }

  );
}
catch(e){
  console.log("Exception in http service:"+e);
}
};

handleDropDownData = function(response : any){
  this.dropdownData = response;
}

getNursingObservationInfo = function(tabValue){
  setTimeout(() => {
    this.vanishSubmitResponseVariable = false;
  }, 3000);


  if(tabValue == 'blood') {
    tabValue = 'bloodGas';
  } else if(tabValue == 'ventilator') {
    tabValue = 'ventilatorSetting';
  }

  this.cyanosis = null;
  this.retractions = null;
  this.grunting = null;
  this.airEntry = null;
  this.respiratoryRate = null;
  this.downesScoreTotalGrade = null;
  this.totalDownyScore = null;

  var todayDate = new Date();
  /*if(selectDateStandard == todayDate)
  {

  this.isNotesDisabled = false;
  this.notToday = false;
  this.fieldDisable = false;
}
else
{
this.notToday = true;
this.isNotesDisabled = true;
this.fieldDisable = true;
}*/
this.reactField = true;
this.sizeField = true;

this.hours = "";
this.min ="";
this.meri = "";

try{

  this.http.request(this.apiData.getNursingData + "/" + tabValue + "/" +this.selectedDate+'/'+ this.childObject.uhid + "/" + this.loggedInUserObj.loggedUser,)
  .subscribe(res => {
    this.handleGetVitalParametersData(res.json());
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


handleGetVitalParametersData = function(response: any){
  console.log(response);
  this.nursingObservationData = response;
  this.setNotesCurrentTime(); //setting time as constant..

  this.nursingObservationData.nnEntryTime.hours = this.tempHours;
  this.nursingObservationData.nnEntryTime.minutes = this.tempMinutes;
  this.nursingObservationData.nnEntryTime.seconds = this.tempSeconds;
  this.nursingObservationData.nnEntryTime.meridium = this.tempMeridium;
  this.nursingObservationData.userDate = this.todaysDate;
  this.eventDate = this.todaysDate;
  this.vitalCheck = true;
  if(this.tabVisible == 'Intake'){
    var feedMethodListObject = [];
    if(this.nursingObservationData.intakeInfo.babyfeedbydoctor.feedmethodList!=null &&
      this.nursingObservationData.intakeInfo.babyfeedbydoctor.feedmethodList.length>0){
        for(var index=0;index<this.nursingObservationData.intakeInfo.babyfeedbydoctor.feedmethodList.length;index++){
          for(var index2 = 0;index2<this.dropdownData.feedMethod.length;index2++){
            if(this.nursingObservationData.intakeInfo.babyfeedbydoctor.feedmethodList[index]==
              this.dropdownData.feedMethod[index2].key){
                var objectTemp = [];
                objectTemp = this.dropdownData.feedMethod[index2];
                feedMethodListObject.push(objectTemp);
              }
            }
          }
          this.nursingObservationData.intakeInfo.babyfeedbydoctor.feedmethodList = feedMethodListObject;
        }
      }

      var presentWeight = (this.nursingObservationData.babyVisit.presentWeight*1);
      //setting present weight for output..
      /*if(this.tabVisible=='Output'){
      this.calculateUrine();
    }*/
    if(this.nursingObservationData.outputInfo.emptyObj!=null ){
      this.totalUoTemp = this.nursingObservationData.outputInfo.emptyObj.totalUo;
    }
      this.entryNursingList = [];
      this.vitalNursingList = [];
    if(this.nursingObservationData.vitalParametersInfo.previousData !=null && this.nursingObservationData.vitalParametersInfo.previousData!= undefined &&  this.nursingObservationData.vitalParametersInfo.previousData.length>0){
      for(var i =0; i<this.nursingObservationData.vitalParametersInfo.previousData.length ; i++){
        if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousEpisodeData.creationtime!=null){
            this.entryNursingList.push(this.nursingObservationData.vitalParametersInfo.previousData[i].previousEpisodeData);
        }
        else if (this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.creationtime!=null){
          this.vitalNursingList.push(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData);
        }
        else{

        }

      }
    }
    console.log(this.entryNursingList);
    console.log(this.vitalNursingList);

    if(this.nursingObservationData.intakeInfo.emptyObj !=null && this.nursingObservationData.intakeInfo.emptyObj.ivtotal!=null
      && this.nursingObservationData.intakeInfo.emptyObj.ivtotal != 0){
        this.ivTotalTemp = this.nursingObservationData.intakeInfo.emptyObj.ivtotal;
        if(this.nursingObservationData.intakeInfo.babyfeedbydoctor!=null &&
          this.nursingObservationData.intakeInfo.babyfeedbydoctor.ivfluidrate!=null){
            this.ivTotalTemp = (this.ivTotalTemp*1)+(this.nursingObservationData.intakeInfo.babyfeedbydoctor.ivfluidrate*1);
          }
        }else if(this.nursingObservationData.intakeInfo.babyfeedbydoctor!=null &&
          this.nursingObservationData.intakeInfo.babyfeedbydoctor.ivfluidrate!=null){
            this.ivTotalTemp = this.nursingObservationData.intakeInfo.babyfeedbydoctor.ivfluidrate;
          }

          //			this.isSubmitPossible();

          //		this.setTodayAssessmentDateTime();

        };

        setNotesCurrentTime = function(){
          var date  = new Date();
          this.hrs = date.getHours();
          this.minutes = date.getMinutes();
          this.seconds = date.getSeconds();
          var ampm = this.hrs >= 12 ? 'PM' : 'AM';

          //console.log("hours "+hours+ "minutes "+minutes+"ampm "+ampm);
          this.hrs = (this.hrs*1) % 12;
          this.hrs = (this.hrs*1) ? (this.hrs*1) : 12; // the hour '0' should be '12'

          this.minutes = (this.minutes*1) < 10 ? '0'+ this.minutes : this.minutes;
          this.seconds = (this.seconds*1) <10 ? '0' + this.seconds : this.seconds;
          var strTime = this.hrs + ':' + this.minutes + ':' + ampm;

          //console.log("hours **********"+hours);
          if((this.hrs*1)<10){
          this.hrs = '0'+this.hrs;
        }

        this.tempHours = this.hrs+'';
        this.tempMinutes = this.minutes+'';
        this.tempSeconds = this.seconds + '';
        this.tempMeridium = ampm;
        this.todaysDate = date;
        //console.log("time is ");
        //console.log(this.nursingObservationData.nnEntryTime.hours);
        //console.log(hours+ 'h' +minutes +'min '+ampm+ 'meridiem');

        //setting tabs done by priya nursing notes..
        if(this.nursingObservationData != null && this.nursingObservationData != undefined && this.nursingObservationData != ''){
        this.nursingObservationData.time =this.hrs+':'+this.minutes+':'+ampm;
        this.hours = this.hrs+'';
        this.min = this.minutes+'';
        this.meri = ampm+'';
      }

      //setting blood product start time ..as current time
      this.bloodStartTimeHr = this.hrs+'';
      this.bloodStartTimeMin = this.minutes+'';
      this.bloodStartTimeMeridian = ampm+'';

      //setting bolus start time...as current time
      this.bolusStartTimeHr = this.hrs+'';
      this.bolusStartTimeMin = this.minutes+'';
      this.bolusStartTimeMeridian =  ampm+'';

    }
    seizureRefresh = function() {
      this.symptomaticSeizures = [];
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizureDuration = null;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.duration_unit_seizure = null;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.symptomaticStatus = null;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizureType = "";
      this.createSymptomaticValueSeizures();
    }

    createSymptomaticValueSeizures = function() {
      console.log("createSymptomaticValueSeizures");
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.symptomaticValue = "";
      var tempSymptomaticStr = "";
      if(this.symptomaticSeizures!=null){

        if(this.symptomaticSeizures.apnea == true){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = "Apnea";
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Apnea";
        }

        if(this.symptomaticSeizures.SympTachycardia == true){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = "Tachycardia";
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Tachycardia";
        }

        if(this.symptomaticSeizures.Sympbradycardia==true){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = "Bradycardia";
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Bradycardia";
        }

        if(this.symptomaticSeizures.Sympdesaturation==true){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = "Desaturation";
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Desaturation";
        }

        if(this.symptomaticSeizures.Symppallor==true){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = "Pallor";
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Pallor";
        }

        if(this.symptomaticSeizures.SympOther == true && this.symptomaticSeizures.othersText != null && this.symptomaticSeizures.othersText != ''){
          if(tempSymptomaticStr=='')
          tempSymptomaticStr = this.symptomaticSeizures.othersText;
          else
          tempSymptomaticStr =  tempSymptomaticStr + ", " + this.symptomaticSeizures.othersText;
        } else {
          this.symptomaticSeizures.othersText = "";
        }
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.symptomaticValue = tempSymptomaticStr;
        console.log(this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.symptomaticValue);
      }
    }

    createSeizuresTypeText = function() {
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizureType = "";
      var tempSeizuresTypeStr = "";
      if(this.seizuresType!=null){

        if(this.seizuresType.Tonic==true){
          tempSeizuresTypeStr = "Tonic";
        }

        if(this.seizuresType.Clonic==true){
          if(tempSeizuresTypeStr=='')
          tempSeizuresTypeStr = "Clonic";
          else
          tempSeizuresTypeStr += ", " +  "Clonic";
        }

        if(this.seizuresType.tonicClonic==true){
          if(tempSeizuresTypeStr=='')
          tempSeizuresTypeStr = "Tonic Clonic";
          else
          tempSeizuresTypeStr += ", " +  "Tonic Clonic";
        }

        if(this.seizuresType.Myoclonic==true){
          if(tempSeizuresTypeStr=='')
          tempSeizuresTypeStr = "Myoclonic";
          else
          tempSeizuresTypeStr += ", " +  "Myoclonic";
        }

        if(this.seizuresType.Subtle==true){
          if(tempSeizuresTypeStr=='')
          tempSeizuresTypeStr = "Subtle";
          else
          tempSeizuresTypeStr += ", " +  "Subtle";
        }

        if(this.seizuresType.othersType == true && this.seizuresType.othersValue != null
          && this.seizuresType.othersValue != ''){
            if(tempSeizuresTypeStr=='')
            tempSeizuresTypeStr = this.seizuresType.othersValue;
            else
            tempSeizuresTypeStr += ", " + this.seizuresType.othersValue;
          } else {
            this.seizuresType.othersValue = "";
          }
          this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizureType = tempSeizuresTypeStr;
          console.log(this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizureType);
        }
      }

      selectDesaturation = function(){
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.disaturation = true;
      }

      clearRecovery = function() {
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.recovery = null;
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.duration_unit_apnea = null;
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.apneaDuration = null;
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.spo2 = null;
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.bradycardia = null;
        this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.hr = null;
      }

      calGlycemiaEvent= function(){
        console.log("calGlycemiaEvent");
        if(this.nursingObservationData.vitalParametersInfo.emptyObj.rbs != null && this.nursingObservationData.vitalParametersInfo.emptyObj.rbs != "") {
          if((this.nursingObservationData.vitalParametersInfo.emptyObj.rbs * 1) < 40) {
            this.nursingObservationData.vitalParametersInfo.emptyObj.hypoglycemia = true;
            this.nursingObservationData.vitalParametersInfo.emptyObj.hyperglycemia = false;
          } else if((this.nursingObservationData.vitalParametersInfo.emptyObj.rbs * 1) > 150) {
            this.nursingObservationData.vitalParametersInfo.emptyObj.hypoglycemia = false;
            this.nursingObservationData.vitalParametersInfo.emptyObj.hyperglycemia = true;
          } else {
            this.nursingObservationData.vitalParametersInfo.emptyObj.hypoglycemia = false;
            this.nursingObservationData.vitalParametersInfo.emptyObj.hyperglycemia = false;
          }
        } else {
          this.nursingObservationData.vitalParametersInfo.emptyObj.hypoglycemia = false;
          this.nursingObservationData.vitalParametersInfo.emptyObj.hyperglycemia = false;
        }
      }


      createSymptomaticValue = function() {
        console.log("createSymptomaticValue");
        this.nursingObservationData.vitalParametersInfo.emptyObj.symptomaticValue = "";
        var tempSymptomaticStr = "";
        if(this.symptomatic!=null){
          if(this.symptomatic.lethargy==true){
            tempSymptomaticStr = "Lethargy";
          }

          if(this.symptomatic.apnea==true){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = "Apnea";
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Apnea";
          }

          if(this.symptomatic.seizures==true){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = "Seizures";
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Seizures";
          }

          if(this.symptomatic.rds==true){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = "RDS";
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " +  "RDS";
          }

          if(this.symptomatic.hypothermia==true){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = "Hypothermia";
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Hypothermia";
          }

          if(this.symptomatic.diuresis==true){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = "Diuresis";
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " +  "Diuresis";
          }

          if(this.symptomatic.others==true && this.symptomatic.otherText != null && this.symptomatic.otherText != ''){
            if(tempSymptomaticStr=='')
            tempSymptomaticStr = this.symptomatic.otherText;
            else
            tempSymptomaticStr =  tempSymptomaticStr + ", " + this.symptomatic.otherText;
          }
          this.nursingObservationData.vitalParametersInfo.emptyObj.symptomaticValue = tempSymptomaticStr;
        }
      }

    calculateTime = function(vitalFilter){
        console.log(this.nursingObservationData.userDate);

        if(this.nursingObservationData.userDate==null || this.nursingObservationData.userDate==undefined){
          console.log("date selected error");
          return;
        }


        var currentAgeData = this.nursingObservationData.userDate;
        var selectedHours = this.nursingObservationData.userDate.getHours();
        var selectedMins = this.nursingObservationData.userDate.getMinutes();

        if (selectedHours >= 12){
          var selectedMeridian = 'PM';
          if (selectedHours > 12){
            selectedHours = selectedHours*1 - 12;
          }
        }
        else{
          var selectedMeridian = 'AM';
        }
        if(selectedHours < 10){
          selectedHours = "0"+ selectedHours;
        }
        if(selectedMins < 10){
          selectedMins = "0"+ selectedMins;
        }

        this.nursingObservationData.nnEntryTime.hours  = selectedHours;
        this.nursingObservationData.nnEntryTime.minutes  = selectedMins;
        this.nursingObservationData.nnEntryTime.meridium  = selectedMeridian;

        console.log(selectedHours);
        console.log(selectedMins);
        console.log(selectedMeridian);


        this.nursingObservationData.creationtime = this.nursingObservationData.userDate;
        console.log(this.nursingObservationData.creationtime  + "just checking");

        if(this.nursingChartsTab == "vital" && vitalFilter == "find-vital"){
          try{
            this.http.request(this.apiData.getVitalInfoByDate + "/" + this.childObject.uhid +"/"+ this.nursingObservationData.userDate,)
            .subscribe(res => {
              this.handleVitalInfoByDate(res.json());
            },
            err => {
              console.log("Error occured.")
            }
          );
          }
          catch(e){
            console.log("Exception in http service:"+e);
          };
        }else if(this.nursingChartsTab == "ventilator" && vitalFilter == "find-ventilator"){
          try{
            this.http.request(this.apiData.getVentilatorInfoByDate + "/" + this.childObject.uhid +"/"+ this.nursingObservationData.userDate,)
            .subscribe(res => {
              this.handleVentilatorInfoByDate(res.json());
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

    handleVentilatorInfoByDate = function(response : any){
      console.log(response);
      if(this.nursingChartsTab == 'ventilator'){
        if(response.pip != null && response.pip != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.pip = response.pip;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.peepCpap = "";
        if(response.peepCpap != null && response.peepCpap != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.peepCpap = response.peepCpap;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.pressureSupply = "";
        if(response.pressureSupply != null && response.pressureSupply != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.pressureSupply = response.pressureSupply;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.map = "";
        if(response.map != null && response.map != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.map = response.map;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.freqRate = "";
        if(response.freqRate != null && response.freqRate != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.freqRate = response.freqRate;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.tidalVolume = "";
        if(response.tidalVolume != null && response.tidalVolume != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.tidalVolume = response.tidalVolume;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.minuteVolume = "";
        if(response.minuteVolume != null && response.minuteVolume != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.minuteVolume = response.minuteVolume;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.ti = "";
        if(response.ti != null && response.ti != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.ti = response.ti;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.fio2 = "";
        if(response.fio2 != null && response.fio2 != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.fio2 = response.fio2;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.fio2 = "";
        if(response.flowPerMin != null && response.flowPerMin != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.flowPerMin = response.flowPerMin;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.flowPerMin = "";
        if(response.noPpm != null && response.noPpm != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.noPpm = response.noPpm;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.noPpm = "";
        if(response.dco2 != null && response.dco2 != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.dco2 = response.dco2;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.dco2 = "";
        if(response.amplitude != null && response.amplitude != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.amplitude = response.amplitude;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.amplitude = "";
        if(response.frequency != null && response.frequency != undefined)
          this.nursingObservationData.ventilatorInfo.emptyObj.frequency = response.frequency;
        else
          this.nursingObservationData.ventilatorInfo.emptyObj.frequency = "";
      }
    }

    handleVitalInfoByDate = function(response : any){
      console.log(response);
      if(this.nursingChartsTab == 'vital'){
        if(response.pulserate != null && response.pulserate != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.pulserate = response.pulserate;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.pulserate = "";
        if(response.rrRate != null && response.rrRate != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.rrRate = response.rrRate;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.rrRate = "";
        if(response.hrRate != null && response.hrRate != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.hrRate = response.hrRate;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.hrRate = "";
        if(response.spo2 != null && response.spo2 != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.spo2 = response.spo2;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.spo2 = "";
        if(response.systBp != null && response.systBp != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.systBp = response.systBp;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.systBp = "";
        if(response.diastBp != null && response.diastBp != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.diastBp = response.diastBp;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.diastBp = "";
        if(response.meanBp != null && response.meanBp != undefined)
          this.nursingObservationData.vitalParametersInfo.emptyObj.meanBp = response.meanBp;
        else
          this.nursingObservationData.vitalParametersInfo.emptyObj.meanBp = "";
      }
    }
    downes = function() {
      console.log("downes initiated");
      $("#downesOverlay").css("display", "block");
      $("#downesScorePopup").addClass("showing");
    };
    //		below code is used to close the downe score
    closeModalDownes = function(){
      console.log("downes closing");
      $("#downesOverlay").css("display", "none");
      $("#downesScorePopup").toggleClass("showing");
    };
    closeModalDownesOnSave = function(){
      console.log(this.grunting);
      this.checkValidDownes();
      if(this.checkValidDownes()){
        $("#downesNotValid").css("display","none");
        this.closeModalDownes();
        this.totalDownyScore = this.downesScoreTotalGrade;
        this.nursingObservationData.vitalParametersInfo.emptyObj.downesscore = this.totalDownyScore;
      }
      else{
        $("#downesNotValid").css("display","inline-block");
      }
    }
    checkValidDownes = function(){
      console.log("checking downes");
      var downesScoreValid = true;
      if(this.cyanosis == null || this.cyanosis == undefined){
        downesScoreValid = false;
      }
      else if(this.retractions == null || this.retractions == undefined){
        downesScoreValid = false;
      }
      else if(this.grunting == null || this.grunting == undefined){
        downesScoreValid = false;
      }
      else if(this.airEntry == null || this.airEntry == undefined){
        downesScoreValid = false;
      }
      else if(this.respiratoryRate == null || this.respiratoryRate == undefined){
        downesScoreValid = false;
      }
      return downesScoreValid;
    }
    totalDownesValue = function(downesValue){
      if(downesValue == "cyanosis"){
        this.totalCyanosis = parseInt(this.cyanosis);
        this.nursingObservationData.vitalParametersInfo.emptyObj.cynosis = this.totalCyanosis;
      }if(downesValue == "retractions"){
        this.totalRetractions = parseInt(this.retractions);
        this.nursingObservationData.vitalParametersInfo.emptyObj.retractions = this.totalRetractions;
      }if(downesValue == "grunting"){
        this.totalGrunting = parseInt(this.grunting);
        this.nursingObservationData.vitalParametersInfo.emptyObj.grunting = this.totalGrunting;
      }if(downesValue == "airEntry"){
        this.totalAirEntry = parseInt(this.airEntry);
        this.nursingObservationData.vitalParametersInfo.emptyObj.airentry = this.totalAirEntry;
      }if(downesValue == "respiratoryRate"){
        this.totalRespiratoryRate = parseInt(this.respiratoryRate);
        this.nursingObservationData.vitalParametersInfo.emptyObj.respiratoryrate = this.totalRespiratoryRate;
      }

      this.downesValueCalculated = this.totalCyanosis + this.totalRetractions + this.totalGrunting + this.totalAirEntry + this.totalRespiratoryRate;
      this.downesScoreTotalGrade = this.downesValueCalculated;
    }

    getSelectedDate = function(dateValue) {
      this.selectedDate = new Date(dateValue);
      //var invalidDate = angular.isUndefined(dateValue);
      /*this.selectedDate = dateService.formatter(dateValue,"gmt","default").slice(0,12);
      if(invalidDate == true)
      {
      this.selectedDate = dateService.formatter(new Date(),"gmt","default").slice(0,12);

    }
    enableForm();*/
    this.getNursingObservationInfo(this.tabVisible);
  };

  nursingVitalValid = function(){
    if(this.nursingObservationData.vitalParametersInfo.emptyObj.vpPosition == null || this.nursingObservationData.vitalParametersInfo.emptyObj.vpPosition == undefined || this.nursingObservationData.vitalParametersInfo.emptyObj.vpPosition == "" || this.nursingObservationData.vitalParametersInfo.emptyObj.baby_color == null || this.nursingObservationData.vitalParametersInfo.emptyObj.baby_color == undefined || this.nursingObservationData.vitalParametersInfo.emptyObj.baby_color == ""){
      return false;
    }
    else{
      return true;
    }
  }

  nursingVentilatorValid = function(){
    if(this.nursingObservationData.ventilatorInfo.emptyObj.ventmode == null || this.nursingObservationData.ventilatorInfo.emptyObj.ventmode == undefined || this.nursingObservationData.ventilatorInfo.emptyObj.ventmode == ""){
      return false;
    }
    else{
      return true;
    }
  }

  setNursingObservationInfo = function(){

      console.log("welcome");
      this.comp = new Date();
      if(this.tabVisible == "vitalParameters"){
        if(this.nursingVitalValid() == false){
          return;
        }
      }
      if(this.tabVisible == "ventilator"){
        if(this.nursingVentilatorValid() == false){
          return;
        }
      }

      if(Date.parse(this.nursingObservationData.userDate) <= Date.parse(this.comp)){

        this.nursingObservationData.time= this.tempHours +":"+this.tempMinutes +":"+this.tempMeridium;
        this.nursingObservationData.loggedUser = this.loggedInUserObj.loggedUser;
        this.nursingObservationData.uhid = this.childObject.uhid;
        console.log(this.nursingObservationData.uhid);
        console.log(this.nursingObservationData.userDate + "date");
        var tabSubmit = this.tabVisible;
        if(this.tabVisible == 'bloodGas' && this.BloodWhichTab != 'blood') {
          tabSubmit = 'ventilatorSetting';
        }
        if(tabSubmit == 'blood') {
          tabSubmit = 'bloodGas';
        } else if(tabSubmit == 'ventilator') {
          tabSubmit = 'ventilatorSetting';
        }

        if(this.nursingObservationData.userDate != null) {
          this.nursingObservationData.creationtime = this.nursingObservationData.userDate;
          this.nursingObservationData.entryDate = this.nursingObservationData.creationtime;
          this.nursingObservationData.userDate = this.dateformatter(this.nursingObservationData.userDate, "gmt", "standard");
        }
        else{
          this.nursingObservationData.creationtime = new Date();
          this.nursingObservationData.entryDate = this.nursingObservationData.creationtime;
          this.nursingObservationData.userDate = this.dateformatter(this.nursingObservationData.creationtime, "gmt", "standard");
        }
        console.log(this.nursingObservationData.creationtime + "new date");
        console.log(this.nursingObservationData.userDate + "new date");

        // if(this.nursingObservationData.vitalParametersInfo.emptyObj.baby_color  != 'Other'){
          //this.nursingObservationData.vitalParametersInfo.emptyObj.baby_color_other = null;
        // }
        this.isLoaderVisible = true;
        this.loaderContent = 'Saving ...';
        try
        {
          this.http.post(this.apiData.setNursingData + tabSubmit + "/" +this.selectedDate+  "/" + this.childObject.uhid, this.nursingObservationData).
          subscribe(res => {
            this.handleNursingData(res.json());
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

handleNursingData(response: any){
  this.isLoaderVisible = false;
  this.loaderContent = '';
  this.vanishSubmitResponseVariable = true;
  this.responseMessage = response.message;
  this.responseType = response.type;
  console.log(this.responseMessage);
  console.log(this.responseType);
  console.log(this.tabVisible);
  console.log(this.BloodWhichTab);
  this.getNursingObservationInfo(this.tabVisible);
}

dateformatter = function(date :Date, inFormat : string, outFormat :string){
  if(inFormat == "gmt"){
    if (outFormat == "utf")
    {
      // let tzoffset : any = (new Date(date)).getTimezoneOffset() * 60000; //offset in milliseconds
      // let dateStrObj : number= new Date(date) - tzoffset;
      // let localISOTime : any = (new Date(dateStrObj)).toISOString().slice(0,-1);
      // localISOTime = localISOTime+"Z";
      // return localISOTime;
    }
    else if(outFormat == "standard"){
      let newdate = new Date(date);
      let dd : any = newdate.getDate();
      let mm : any = newdate.getMonth();
      mm = mm+1;
      let yy = newdate.getFullYear();
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

getBabyBloodGasInfoBySampleId = function(){

  try{
    this.http.request(this.apiData.getBabyBloodGasInfoBySampleId + "/" + this.childObject.uhid +"/"+ this.orderId+ "/" + this.loggedInUserObj.loggedUser,)
    .subscribe(res => {
      this.handleInitailBloodGasData(res.json());
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
handleInitailBloodGasData = function(response: any){
  this.nursingObservationData.bloodGasInfo.emptyObj = response;
}

decrementDate = function(){
  this.selectedDate = new Date(new Date(this.selectedDate).getTime() - (24*60*60*1000));
  //enableForm();
  //this.getNursingIntakeOutput();
  this.visibleTab(this.tabVisible);
};

incrementDate = function(){
  this.selectedDate = new Date(new Date(this.selectedDate).getTime() + (24*60*60*1000));
  //enableForm();
  //this.getNursingIntakeOutput();
  this.visibleTab(this.tabVisible);
};

nursingEpisodeValid = function(){
  // if(this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.apnea
  //   || this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.bradycardia
  //   || this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.tachycardia
  //   || this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.disaturation
  //   || this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.seizures) {
  //     return true;
  //   } else {
  //     return false;
  //   }
  return true;
  }

  setNursingEpisode = function(){
    if (this.nursingEpisodeValid()) {
      console.log("in setNursingEpisode");
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.loggeduser = this.loggedInUserObj.loggedUser;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.uhid = this.childObject.uhid;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.creationtime = this.eventDate;
      this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.nnVitalparameterTime = this.tempHours + ":" +
      this.tempMinutes + ":" + this.tempMeridium;

      console.log(this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.nnVitalparameterTime);
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
      try
      {
        this.http.post(this.apiData.setNursingEpisode, this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj).
        subscribe(res => {
          this.handleEventData(res.json());
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

handleEventData = function(response : any){

  this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj = response.returnedObject;
  this.isLoaderVisible = false;
  this.loaderContent = '';
  this.vanishSubmitResponseVariable = true;
  this.responseMessage = response.message;
  this.responseType = response.type;
  console.log(this.responseMessage);
  console.log(this.responseType);

  this.getNursingObservationInfo(this.tabVisible);
}
calculateEntryTime = function(){
  console.log(this.eventDate);
  if(this.eventDate==null || this.eventDate==undefined){
    console.log("date selected error");
    return;
  }
  this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.creationtime = this.eventDate;
  console.log(this.nursingObservationData.vitalParametersInfo.nursingEpisodeEmptyObj.creationtime  + "just checking");
}

TimeCtrl = function() {
  this.clock = "loading clock..."; // initialise the time variable
  this.tickInterval = 1000 //ms

  var tick = function() {
    this.clock = Date.now() // get the current time
    this.setNotesCurrentTime();
    setTimeout(() => {tick;
    }, this.tickInterval);
  };

  // Start the timer
  setTimeout(() => {tick;
}, this.tickInterval);
}

disableGraph = function(){
  this.showGraphView = false;
}

printGraph = function(){
  console.log("welcome");
  this.hrList = [];
  this.cvpList = [];
  this.spo2List = [];
  this.rrList = [];
  this.systBp = [];
  this.diastBp = [];
  this.peripheralTempList = [];
  this.centralTempList = [];
  this.rbsList = [];
  this.etco2List = [];
  this.graphDataPopulate = true;
  for (var i=0; i<this.nursingObservationData.vitalParametersInfo.previousData.length;i++){
    if(null == this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.nnVitalparameterTime) {
      continue;
    }
    var time  = this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.nnVitalparameterTime;
    var timeArr  = time.split(":");
    var time24 = null;
    if(timeArr[2]=="AM"){
      time24  = (timeArr[0]*1);
    } else if(timeArr[0] == "12"){
      time24  = (timeArr[0]*1);
    } else{
      time24  = (timeArr[0]*1)+ 12;
    }
    if(this.graphDataPopulate) {

      if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.hrRate != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.hrRate != undefined){
        this.hrList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.hrRate)*1]);
      }
      if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.cvp != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.cvp != undefined){
      this.cvpList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.cvp)*1]);
      }
    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.spo2 != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.spo2 != undefined){
      this.spo2List.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.spo2)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rrRate != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rrRate != undefined){
      this.rrList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rrRate)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.systBp != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.systBp != undefined){
      this.systBp.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.systBp)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.diastBp != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.diastBp != undefined){
      this.diastBp.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.diastBp)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.peripheraltemp != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.peripheraltemp != undefined){
      this.peripheralTempList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.peripheraltemp)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.centraltemp != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.centraltemp != undefined){
      this.centralTempList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.centraltemp)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rbs != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rbs != undefined){
      this.rbsList.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.rbs)*1]);
    }

    if(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.etco2 != null && this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.etco2 != undefined){
      this.etco2List.push([time24,(this.nursingObservationData.vitalParametersInfo.previousData[i].previousVitalParamData.etco2)*1]);
    }
  }
}
console.log(this.rbsList);

this.showGraphView = true;
this.graphDataPopulate = false;

var tempChart = []
tempChart = [
  {
    name: 'HR',
    marker: {enabled: false},
    color: '#ff4d4d',
    data: this.hrList
  },
  {
    name: 'Spo2',
    marker: {enabled: false},
    color: '#b52f2f',
    data: this.spo2List
  },
  {
    name: 'RR',
    marker: {enabled: false},
    color: '#000000',
    data: this.rrList
  },
  {
    name: 'CVP',
    marker: {enabled: false},
    color: '#00cc44',
    data: this.cvpList
  },
  {
    name: 'Syst Bp',
    marker: {enabled: false},
    color: '#54b54f',
    data: this.systBp
  },
  {
    name: 'Diast Bp',
    marker: {enabled: false},
    color: '#275625',
    data: this.diastBp
  },
  {
    name: 'Peripheral Temp',
    marker: {enabled: false},
    color: '#648aa8',
    data: this.peripheralTempList
  },
  {
    name: 'Central Bp',
    marker: {enabled: false},
    color: '#c5c7d6',
    data: this.centralTempList
  },
  {
    name: 'RBS',
    marker: {enabled: false},
    color: '#cdafce',
    data: this.rbsList
  },
  {
    name: 'Etco2',
    marker: {enabled: false},
    color: '#602059',
    data: this.etco2List
  }
];

this.heightChart = new Chart({
  chart: {
    type: 'spline'
  },

  title: {
    text: 'Vital'
  },

  xAxis : {
  title : {
    text : 'Hours'
  },
  allowDecimals : false,
  tickInterval : 1,
  min : 0,
  max : 24
},
yAxis: {
  title: {
    text: 'Vitals'
  },
  allowDecimals : false,
  tickInterval : 40,
  min : 0,
  max : 200
},
credits: {
  enabled: false
},
series: tempChart
});


};

  expandPN =  function(){
    this.isExpandPN = !this.isExpandPN;
  }

  bolusExecuted() {
    var bolusStr = "Baby was given " + this.nursingIntakeOutputData.babyFeedObj.bolusType + " bolus : "	+ this.nursingIntakeOutputData.babyFeedObj.bolusTotalFeed
    + " ml @ " + this.nursingIntakeOutputData.babyFeedObj.bolus_rate + " ml/hr over " + this.nursingIntakeOutputData.babyFeedObj.bolus_infusiontime + " min.";

    if(this.nursingIntakeOutputData.currentNursingObj.parenteralComment == null) {
      this.nursingIntakeOutputData.currentNursingObj.parenteralComment = '';
    }

    if(this.nursingIntakeOutputData.currentNursingObj.bolus_executed) {
      this.nursingIntakeOutputData.currentNursingObj.parenteralComment += bolusStr;
    } else if(this.nursingIntakeOutputData.currentNursingObj.parenteralComment.includes(bolusStr)) {
      this.nursingIntakeOutputData.currentNursingObj.parenteralComment = this.nursingIntakeOutputData.currentNursingObj.parenteralComment.replace(bolusStr, '');
    }
  }

  saveNursingIntakeOutput = function() {
    this.hours = this.nursingIntakeOutputData.currentNursingObj.entry_timestamp.getHours();
    this.min = this.nursingIntakeOutputData.currentNursingObj.entry_timestamp.getMinutes();
    this.meri = "PM";

    if(this.hours < 12) {
      this.meri = "AM";
    } else if(this.hours > 12){
      this.hours -= 12;
    }

    if(this.hours < 10) {
      this.hours = "0" + this.hours;
    }

    if(this.min < 10) {
      this.min = "0" + this.min;
    }

    this.nursingIntakeOutputData.currentNursingObj.nursingEntryTime = this.hours + ":" + this.min + ":" + this.meri;
    this.nursingIntakeOutputData.currentNursingObj.uhid = this.childObject.uhid;
    this.nursingIntakeOutputData.currentNursingObj.loggeduser = this.loggedInUserObj.loggedUser;

    if(this.nursingIntakeOutputData.babyFeedObj != null && this.nursingIntakeOutputData.babyFeedObj.babyfeedid != null) {
      this.nursingIntakeOutputData.currentNursingObj.babyfeedid = "" + this.nursingIntakeOutputData.babyFeedObj.babyfeedid;
    }
    this.isLoaderVisible = true;
    this.loaderContent = 'Saving ...';

    this.printDataObjIntakeOutputFromDate = new Date();
    this.printDataObjIntakeOutputFromDate.setHours(8);
    this.printDataObjIntakeOutputFromDate.setMinutes(0);
    this.printDataObjIntakeOutputFromDate.setSeconds(0);
    this.printDataObjIntakeOutputToDate = new Date();

    if(this.printDataObjIntakeOutputToDate.getHours() < 8) {
      this.printDataObjIntakeOutputFromDate = new Date(this.printDataObjIntakeOutputFromDate.getTime() - (24 * 60 * 60 * 1000));
    }

    var fromTime = this.printDataObjIntakeOutputFromDate.getTime();
    var toTime = this.printDataObjIntakeOutputToDate.getTime();

    try{
      this.http.post(this.apiData.saveNursingIntakeOutput + '/'+ this.childObject.uhid + "/" + + fromTime + "/" + toTime + "/" + this.loggedInUserObj.loggedUser, this.nursingIntakeOutputData.currentNursingObj)
      .subscribe(res => {
        this.nursingIntakeOutputData = res.json();
        this.isLoaderVisible = false;
        this.loaderContent = '';
        console.log(this.nursingIntakeOutputData);
        this.populateOrderString();
        this.populateTotal();
        this.nursingIntakeOutputData.currentNursingObj.entry_timestamp = new Date();
        this.nursingIntakeOutputData.pastNursingIntakeList = this.calculateTotalIntakeOutput(this.nursingIntakeOutputData.pastNursingIntakeList);
        this.calculateRemainingIntake(this.nursingIntakeOutputData.currentNursingIntakeList);

       },
        err => {
          console.log("Error occured.")
        });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  getNursingIntakeOutput = function() {
    this.IntakeOutputEntryDate = this.dateformatter(new Date(this.selectedDate), "gmt", "standard");
    console.log(this.IntakeOutputEntryDate);

    this.hours = "";
    this.min ="";
    this.meri = "";

    this.printDataObjIntakeOutputFromDate = new Date();
    this.printDataObjIntakeOutputFromDate.setHours(8);
    this.printDataObjIntakeOutputFromDate.setMinutes(0);
    this.printDataObjIntakeOutputFromDate.setSeconds(0);
    this.printDataObjIntakeOutputToDate = new Date();

    if(this.printDataObjIntakeOutputToDate.getHours() < 8) {
      this.printDataObjIntakeOutputFromDate = new Date(this.printDataObjIntakeOutputFromDate.getTime() - (24 * 60 * 60 * 1000));
    }

    var fromTime = this.printDataObjIntakeOutputFromDate.getTime();
    var toTime = this.printDataObjIntakeOutputToDate.getTime();

    try{
        this.http.request(this.apiData.getNursingIntakeOutput + this.childObject.uhid  + "/" + fromTime + "/" + toTime + "/").subscribe(res => {
          this.nursingIntakeOutputData = res.json();
          this.isOutputSummary = false;
          this.isEnteralSummary = false;
          this.isParenteralSummary = false;
          this.isOtherAdditivesSummary = false;
          if(this.nursingIntakeOutputData.pastNursingIntakeList != null && this.nursingIntakeOutputData.pastNursingIntakeList.length > 0){
            for(var i = 0; i < this.nursingIntakeOutputData.pastNursingIntakeList.length; i++){
              if((this.isOutputSummary != true) && (this.nursingIntakeOutputData.pastNursingIntakeList[i].urinePassed != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].stoolPassed != null ||
                this.nursingIntakeOutputData.pastNursingIntakeList[i].vomitPassed != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].abdomenGirth != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].gastricAspirate != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].urine != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].stool != null
                || this.nursingIntakeOutputData.pastNursingIntakeList[i].vomit != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].drain != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].outputComment != null)){
                  this.isOutputSummary = true;
              }
              if((this.isEnteralSummary != true) && (this.nursingIntakeOutputData.pastNursingIntakeList[i].route == 'Breast Feed' || this.nursingIntakeOutputData.pastNursingIntakeList[i].primaryFeedValue != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].formulaValue != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].actualFeed != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].enteralComment != null)){
                this.isEnteralSummary = true;
              }
              if((this.isParenteralSummary != true) && (this.nursingIntakeOutputData.pastNursingIntakeList[i].lipid_delivered != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].tpn_delivered != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].parenteralComment != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].readymadeDeliveredFeed != null)){
                this.isParenteralSummary = true;
              }
              if((this.isOtherAdditivesSummary != true) && (this.nursingIntakeOutputData.pastNursingIntakeList[i].calciumVolume != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].ironVolume != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].mvVolume != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].otherAdditiveVolume != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].calciumComment != null
                || this.nursingIntakeOutputData.pastNursingIntakeList[i].ironComment != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].mvComment != null || this.nursingIntakeOutputData.pastNursingIntakeList[i].otherAdditiveComment != null)){
                this.isOtherAdditivesSummary = true;
              }
            }
          }

          console.log(this.nursingIntakeOutputData);
          this.populateOrderString();
          this.populateTotal();
          this.Output = true;
          this.nursingIntakeOutputData.currentNursingObj.entry_timestamp = new Date();

          this.isOrderAllChecked = false;
          this.allOrderChecked();
          this.isAllExecutionChecked = false;
          this.allExecutionChecked();

          if(this.nursingIntakeOutputData.babyFeedObj == null || this.nursingIntakeOutputData.babyFeedObj.babyfeedid == null){
            this.Enteral = false;
            this.isenteralOrderVisible = false;
            this.enteralDisabled = true;

            this.Parenteral = false;
            this.isparenteralOrderVisible = false;
            this.parenteralDisabled = true;
          } else {
            if(this.nursingIntakeOutputData.babyFeedObj.isenternalgiven) {
              this.Enteral = true;
              this.enteralDisabled = false;
            } else {
              this.Enteral = false;
              this.enteralDisabled = true;
            }

            if(this.nursingIntakeOutputData.babyFeedObj.isparentalgiven) {
              this.Parenteral = true;
              this.parenteralDisabled = false;
            } else {
              this.Parenteral = false;
              this.parenteralDisabled = true;
            }
          }
          this.nursingIntakeOutputData.pastNursingIntakeList = this.calculateTotalIntakeOutput(this.nursingIntakeOutputData.pastNursingIntakeList);
          this.calculateRemainingIntake(this.nursingIntakeOutputData.currentNursingIntakeList);

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

  calculateRemainingIntake(dataList : any){
    this.totalRemainingEnVolume = 0;
    if(dataList != null && dataList.length != 0){
      for(var i = 0; i < dataList.length; i++){
        var obj = dataList[i];
        if(obj.actualFeed != null){
          this.totalRemainingEnVolume += (obj.actualFeed);
          this.totalRemainingEnVolume = this.round(this.totalRemainingEnVolume, 2);
        }
      }

    }
  }

  calculateTotalIntakeOutput(dataList : any){
    this.totalEn = 0;
    this.totalPn = 0;
    this.totalUrine = 0;
    this.totalFeed = 0;
    this.urineMlKgHr = 0;

    if(dataList != null && dataList.length != 0){
      var weight = null;
      if(this.childObject.workingWeight != null) {
        weight = this.childObject.workingWeight / 1000;
      }
      else{
        if(this.childObject.todayWeight != null) {
          weight = this.childObject.todayWeight / 1000;
        }
      }

      for(var i = (dataList.length - 1); i >= 0; i--){

        var obj = dataList[i];
        if(obj.urine != null){
          this.totalUrine += parseFloat(obj.urine);
          this.totalUrine = this.round(this.totalUrine, 2);
        }
        obj.cummulativeUrine = this.totalUrine;

        if(obj.actualFeed != null){
          this.totalEn += (obj.actualFeed);
          this.totalEn = this.round(this.totalEn, 2);
        }
        obj.cummulativeEn = this.totalEn;

        if(obj.lipid_delivered != null){
          this.totalPn += (obj.lipid_delivered);
        }

        if(obj.tpn_delivered != null){
          this.totalPn += (obj.tpn_delivered);
        }

        if(obj.readymadeDeliveredFeed != null){
          this.totalPn += (obj.readymadeDeliveredFeed);
        }
      }
      if(this.totalPn != undefined && this.totalPn != null){
        this.totalPn = this.round(this.totalPn, 2);
      }

      if(this.totalUrine != null && weight != null) {
        var hours = Math.round((this.printDataObjIntakeOutputToDate.getTime() - this.printDataObjIntakeOutputFromDate.getTime()) / (60 * 60 * 1000));
        this.urineMlKgHr = this.totalUrine / (weight * hours);
      }
      this.totalFeed = this.totalEn + this.totalPn;
    }
    return dataList;
  }

  populateOrderString = function() {
    this.primaryFeed = "";
    this.feedTypeArr = [];
    if (!(this.nursingIntakeOutputData.babyFeedObj == null || this.nursingIntakeOutputData.babyFeedObj.feedtype == null || this.nursingIntakeOutputData.babyFeedObj.feedtype == "")) {
      this.feedTypeArr = this.nursingIntakeOutputData.babyFeedObj.feedtype.replace("[", "").replace("]", "").split(",");
      for (var i = 0; i < this.nursingIntakeOutputData.refFeedTypeList.length; i++) {
        if (this.feedTypeArr[0] == this.nursingIntakeOutputData.refFeedTypeList[i].feedtypeid) {
          this.primaryFeed = this.nursingIntakeOutputData.refFeedTypeList[i].feedtypename;
        }
      }
    }

    this.formulaType = "";
    if (!(this.nursingIntakeOutputData.babyFeedObj == null || this.nursingIntakeOutputData.babyFeedObj.feedtype == null || this.nursingIntakeOutputData.babyFeedObj.feedtype == "")) {
      if(this.nursingIntakeOutputData.babyFeedObj.feedTypeSecondary != null) {
        var secondaryFeedArr = this.nursingIntakeOutputData.babyFeedObj.feedTypeSecondary.replace("[", "").replace("]", "").split(",");
        for(var index =0; index<secondaryFeedArr.length;index++){
          for (var i = 0; i < this.nursingIntakeOutputData.refFeedTypeList.length; i++) {

            if (secondaryFeedArr[index] == this.nursingIntakeOutputData.refFeedTypeList[i].feedtypeid) {
              if(this.formulaType=='')
                this.formulaType = this.nursingIntakeOutputData.refFeedTypeList[i].feedtypename;
              else{
                this.formulaType += ", "+this.nursingIntakeOutputData.refFeedTypeList[i].feedtypename;
              }
            }
          }
        }
      }
    }

    this.enFeedStr = "";
    if(this.nursingIntakeOutputData.babyFeedObj != null && this.nursingIntakeOutputData.babyFeedObj.isenternalgiven) {
      if(this.nursingIntakeOutputData.babyFeedObj.feedduration != 'Continuous'){
        if(this.nursingIntakeOutputData.enFeedList != null && this.nursingIntakeOutputData.enFeedList.length > 0) {
          for(var i = 0; i < this.nursingIntakeOutputData.enFeedList.length; i++) {
            if(i == 0) {
              this.enFeedStr = this.nursingIntakeOutputData.enFeedList[i].feed_volume + ' (ml/feed) for ' + this.nursingIntakeOutputData.enFeedList[i].no_of_feed + ' feed';
            } else {
              this.enFeedStr += ', ' + this.nursingIntakeOutputData.enFeedList[i].feed_volume + ' (ml/feed) for ' + this.nursingIntakeOutputData.enFeedList[i].no_of_feed + ' feed';
            }
          }
        } else {
          if(this.nursingIntakeOutputData.feedMethodStr == 'Breast Feed') {
            this.enFeedStr = 'Give EN';
            this.isDisable = true;
            if(this.nursingIntakeOutputData.babyFeedObj.isAdLibGiven) {
              this.nursingIntakeOutputData.babyFeedObj.feedduration = null;
            }
          } else {
            this.enFeedStr = this.nursingIntakeOutputData.babyFeedObj.feedvolume + ' (ml/feed)';
          }
        }
      }else if(this.nursingIntakeOutputData.babyFeedObj.feedduration != null && this.nursingIntakeOutputData.babyFeedObj.feedduration == 'Continuous'){
        var remVolume = this.round((this.nursingIntakeOutputData.babyFeedObj.totalfeedMlDay / this.nursingIntakeOutputData.babyFeedObj.duration), 2);
        this.enFeedStr = remVolume + ' (ml/hr) continuously ';
      }
    }
    if(this.nursingIntakeOutputData.babyFeedObj != null && this.nursingIntakeOutputData.babyFeedObj.isenternalgiven && this.nursingIntakeOutputData.addtivesbrandList != null) {
      for(var i = 0; i < this.nursingIntakeOutputData.addtivesbrandList.length; i++) {

        if(this.nursingIntakeOutputData.babyFeedObj.calBrand != null && this.nursingIntakeOutputData.addtivesbrandList[i].enaddtivesbrandid == this.nursingIntakeOutputData.babyFeedObj.calBrand){
          this.calBrand = this.nursingIntakeOutputData.addtivesbrandList[i].brandname;
        }

        if(this.nursingIntakeOutputData.babyFeedObj.ironBrand != null && this.nursingIntakeOutputData.addtivesbrandList[i].enaddtivesbrandid == this.nursingIntakeOutputData.babyFeedObj.ironBrand){
          this.ironBrand = this.nursingIntakeOutputData.addtivesbrandList[i].brandname;
        }

        if(this.nursingIntakeOutputData.babyFeedObj.multiVitaminBrand != null && this.nursingIntakeOutputData.addtivesbrandList[i].enaddtivesbrandid == this.nursingIntakeOutputData.babyFeedObj.multiVitaminBrand){
          this.multiVitaminBrand = this.nursingIntakeOutputData.addtivesbrandList[i].brandname;
        }

        if(this.nursingIntakeOutputData.babyFeedObj.vitaminDBrand != null && this.nursingIntakeOutputData.addtivesbrandList[i].enaddtivesbrandid == this.nursingIntakeOutputData.babyFeedObj.vitaminDBrand){
          this.vitaminDBrand = this.nursingIntakeOutputData.addtivesbrandList[i].brandname;
        }

        if(this.nursingIntakeOutputData.babyFeedObj.mctBrand != null && this.nursingIntakeOutputData.addtivesbrandList[i].enaddtivesbrandid == this.nursingIntakeOutputData.babyFeedObj.mctBrand){
          this.mctBrand = this.nursingIntakeOutputData.addtivesbrandList[i].brandname;
        }
      }
    }
  }

  populateTotal = function(){
    this.primaryFeedTotal = 0;
    this.formulaTotal = 0;
    this.feedTotal = 0;
    this.lipidDelivered = 0;
    this.tpnDelivered = 0;
    this.gastricTotal = 0;
    this.urineTotal = 0;
    this.drainTotal = 0;

    if(this.nursingIntakeOutputData.pastNursingIntakeList!=null && this.nursingIntakeOutputData.pastNursingIntakeList.length>0){
      for(var i =0; i < this.nursingIntakeOutputData.pastNursingIntakeList.length; i++) {
        var pastEntry = this.nursingIntakeOutputData.pastNursingIntakeList[i];
        this.lipidDelivered += pastEntry.lipid_delivered*1;
        this.tpnDelivered += pastEntry.tpn_delivered*1;
        this.primaryFeedTotal += (pastEntry.primaryFeedValue*1);
        this.formulaTotal += (pastEntry.formulaValue*1);
        this.feedTotal += (pastEntry.actualFeed*1);
        this.gastricTotal += (pastEntry.gastricAspirate*1);
        this.urineTotal += (pastEntry.urine*1);
        this.drainTotal += (pastEntry.drain*1);

      }
    }
  }

  saveFlag = function(item){
    console.log(item);
    item.saveFlag = true;
    if(item.route != 'Breast Feed' && (item.primaryFeedType == null || item.primaryFeedType == "" || item.primaryFeedValue == null || item.primaryFeedValue == "")
        && (item.lipid_delivered == null || item.lipid_delivered =="") && (item.bolusDeliveredFeed == null || item.bolusDeliveredFeed =="") && (item.tpn_delivered == null || item.tpn_delivered =="")
        && (item.abdomenGirth == null || item.abdomenGirth =="") && (item.gastricAspirate == null || item.gastricAspirate =="") && (item.urine == null || item.urine =="")
        && (item.stool == null || item.stool =="") && (item.vomit == null || item.vomit =="") && (item.drain == null || item.drain =="") && (item.others == null || item.others =="")) {
      item.saveFlag = false;
    }
  }

  feedType = function(item) {
    if(item.primaryFeedType == null || item.primaryFeedType == "") {
      item.selectedPrimaryFeedType = null;
      return;
    }

    for(var i = 0; i < this.nursingIntakeOutputData.refFeedTypeList.length; i++) {
      if(this.nursingIntakeOutputData.refFeedTypeList[i].feedtypeid == item.primaryFeedType) {
        item.selectedPrimaryFeedType = this.nursingIntakeOutputData.refFeedTypeList[i].feedtypename;
        return;
      }
    }
  }

  lipidRemCal = function(item) {
    item.lipid_remaining = item.lipid_total_volume - item.lipid_delivered;
    item.lipid_remaining = this.round(item.lipid_remaining, 2);
    if(item.lipid_remaining < 0){
      item.lipid_remaining = item.lipid_total_volume;
      item.lipid_delivered = null;
    }
    this.saveFlag(item);
  }

  tpnRemCal = function(item) {
    item.tpn_remaining = item.tpn_total_volume - item.tpn_delivered;
    item.tpn_remaining = this.round(item.tpn_remaining, 2);
    if(item.tpn_remaining < 0){
      item.tpn_remaining = item.tpn_total_volume;
      item.tpn_delivered = null;
    }
    this.saveFlag(item);
  }

  aminoRemCal = function(item){
    item.aminoRemainingFeed = item.aminoTotalFeed - item.aminoDeliveredFeed;
    item.aminoRemainingFeed = this.round(item.aminoRemainingFeed, 2);
    if(item.aminoRemainingFeed < 0){
      item.aminoRemainingFeed = item.aminoTotalFeed;
      item.aminoDeliveredFeed = null;
    }
    this.saveFlag(item);
  }

  calciumRemCal = function(item) {
    item.calciumRemainingFeed = item.calciumTotalFeed - item.calciumDeliveredFeed;
    item.calciumRemainingFeed = this.round(item.calciumRemainingFeed, 2);
    if(item.calciumRemainingFeed < 0){
      item.calciumRemainingFeed = item.calciumTotalFeed;
      item.calciumDeliveredFeed = null;
    }
    this.saveFlag(item);
  }

  additionalPNRemCal = function(item) {
    item.additionalPNRemainingFeed = item.additionalPNTotalFeed - item.additionalPNDeliveredFeed;
    item.additionalPNRemainingFeed = this.round(item.additionalPNRemainingFeed, 2);
    if(item.additionalPNRemainingFeed < 0){
      item.additionalPNRemainingFeed = item.additionalPNTotalFeed;
      item.additionalPNDeliveredFeed = null;
    }
    this.saveFlag(item);
  }

  bolusRemCal = function(item) {
    item.bolusRemainingFeed = item.bolusTotalFeed - item.bolusDeliveredFeed;
    item.bolusRemainingFeed = this.round(item.bolusRemainingFeed, 2);
    if(item.bolusRemainingFeed < 0){
      item.bolusRemainingFeed = item.bolusTotalFeed;
      item.bolusDeliveredFeed = null;
    }
    this.saveFlag(item);
  }

  readymadeRemCal = function(item) {
    item.readymadeRemainingFeed = item.readymadeTotalFeed - item.readymadeDeliveredFeed;
    item.readymadeRemainingFeed = this.round(item.readymadeRemainingFeed, 2);
    if(item.readymadeRemainingFeed < 0){
      item.readymadeRemainingFeed = item.readymadeTotalFeed;
      item.readymadeDeliveredFeed = null;
    }
    this.saveFlag(item);
  }

  calActualFeed = function(item, type) {
    item.actualFeed = 0;

    if(type == 'primary'){
      if(item.primaryFeedValue != null && item.primaryFeedValue != "") {
        item.actualFeed += (item.primaryFeedValue);
        item.actualFeed = this.round(item.actualFeed, 2);
      }
      if(!this.isEmpty(this.nursingIntakeOutputData.babyFeedObj.feedvolume)){
        var remainingVolume = this.nursingIntakeOutputData.babyFeedObj.feedvolume - item.primaryFeedValue;
        if(remainingVolume < 0){
          item.primaryFeedValue = null;
          item.actualFeed = null;
          item.formulaValue = null;
        }else if(remainingVolume > 0 && item.formulaType != null){
          item.formulaValue = remainingVolume;
          item.actualFeed += (item.formulaValue);
          item.actualFeed = this.round(item.actualFeed, 2);
        }else{
          item.formulaValue = null;
        }
      }
    }

    if(type == 'secondary'){
      if(item.primaryFeedValue != null && item.primaryFeedValue != "") {
        item.actualFeed += (item.primaryFeedValue);
        item.actualFeed = this.round(item.actualFeed, 2);
      }
      if(item.formulaValue != null && item.formulaValue != "" && !this.isEmpty(this.nursingIntakeOutputData.babyFeedObj.feedvolume)) {
        item.actualFeed += (item.formulaValue);
        item.actualFeed = this.round(item.actualFeed, 2);
        if(item.actualFeed > this.nursingIntakeOutputData.babyFeedObj.feedvolume){
          item.actualFeed = item.primaryFeedValue;
          item.formulaValue = null;
        }
      }
    }
    this.saveFlag(item);
  }

  round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

  enteralOrderVisible = function(){
    this.isenteralOrderVisible = !this.isenteralOrderVisible;
  }
  parenteralOrderVisible = function(){
    this.isparenteralOrderVisible = !this.isparenteralOrderVisible;
  }
  medicationOrderVisible = function(){
    this.ismedicationOrderVisible = !this.ismedicationOrderVisible;
  }
  editPrimary = function(indexValue){
    console.log(indexValue);
    this.isEditPrimary = indexValue;
  }
  inputOutputOrderSectionVisible(){
    this.isInputOutputOrderSectionVisible = !this.isInputOutputOrderSectionVisible;
  }
  inputOutputExecutionSectionVisible(){
    this.isInputOutputExecutionSectionVisible = !this.isInputOutputExecutionSectionVisible;
  }
  allExecutionChecked(){
    this.isAllExecutionChecked = !this.isAllExecutionChecked;
    if(this.isAllExecutionChecked == true){
      this.isOtherAdditivesExecutionChecked = true;
      this.isParenteralExecutionChecked = true;
      this.isAdditionalPNExecutionChecked = true;
      this.isEnteralExecutionChecked = true;
    }
    else{
      this.isOtherAdditivesExecutionChecked = false;
      this.isParenteralExecutionChecked = false;
      this.isEnteralExecutionChecked = false;
      this.isAdditionalPNExecutionChecked = false;
    }
  }
  allOrderChecked(){
    this.isOrderAllChecked = !this.isOrderAllChecked;
    if(this.isOrderAllChecked == true){
      this.isEnteralOrderChecked =true;
      this.isParenteralOrderChecked =true;
      this.isAdditionalPNChecked = true;
      this.isOrderOtherAdditivesChecked =true;
    }else{
      this.isEnteralOrderChecked =false;
      this.isParenteralOrderChecked =false;
      this.isAdditionalPNChecked = false;
      this.isOrderOtherAdditivesChecked =false;
    }
  }

  viewPreviousOrders() {
    if(this.nursingIntakeOutputData.babyFeedList != null && this.nursingIntakeOutputData.babyFeedList.length > 0){
      console.log("View Calculations initiated");
      $("#viewCalculationOverlay").css("display", "block");
      $("#viewCalculationPopup").addClass("showing");
    }
  };

  closePreviousOrders() {
    console.log("View Calculations closing");
    $("#viewCalculationOverlay").css("display", "none");
    $("#viewCalculationPopup").toggleClass("showing");
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


  ordersParenteralPrint() {
    localStorage.setItem('ordersParenteralPrint', JSON.stringify(this.nursingIntakeOutputData.babyFeedObj));
    console.log("Print this", this.nursingIntakeOutputData.babyFeedObj);
  }

  selectPupilSize(){
    if(this.nursingObservationData.vitalParametersInfo.emptyObj.rightPupilSize===this.nursingObservationData.
      vitalParametersInfo.emptyObj.leftPupilSize){
      this.nursingObservationData.vitalParametersInfo.emptyObj.isPupilEqual=true;
    }
    else{
      this.nursingObservationData.vitalParametersInfo.emptyObj.isPupilEqual=false;
    }
  }

  ngOnDestroy() {
    console.log("nursing-charts component unmounted");
    this.pollingData.unsubscribe();
  }

}
