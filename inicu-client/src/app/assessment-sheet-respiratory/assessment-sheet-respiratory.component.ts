import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { RespiratorySystemObj } from './respiratorySystemObj';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery/dist/jquery.min.js';
import { PastpneumothoraxTempObj } from './pastpneumothoraxTempObj';
import { DownesScoreList } from './downesTempObject';
import { PneumoTempObj } from './PneumoTempObj';
@Component({
  selector: 'assessment-sheet-respiratory',
  templateUrl: './assessment-sheet-respiratory.component.html',
  styleUrls: ['./assessment-sheet-respiratory.component.css']
})
export class AssessmentSheetRespiratoryComponent {
  respiratorySystemObj : RespiratorySystemObj;
  router : Router;
  http : Http;
  apiData : APIurl;
  warningMessage : string;
  PneumoTempObj : any;
  fio2Temp : any;
  childObject : any;
  dropdownData : any;
  PphnTempObj : any;
  pneumothoraxTempObj : any;
  selectAll : any;
  respiratoryDistressValue : boolean;
  isclinicalSectionDistressVisible : boolean;
  isActionDistressVisible : boolean;
  isPlanDistressVisible : boolean;
  isCauseDistressVisible : boolean;
  isrespGraph : boolean;
  isProgressNotesDistressVisible : boolean;
  RDSTempObj : any;
  totalUpperChest : number;
  totalLowerChest : number;
  isChestTubeSelectPneumo : boolean;
  totalExpir : number;
  totalNaras : number;
  totalXiphoid : number;
  SilverValueCalculated : number;
  assessmentTempObj : any;
  ApneaTempObj : any;
  OtherTempObj : any;
  orderSelectedText : string;
	pastOrderSelected : string;
	treatmentSelectedText : string;
  surfactantDosePneumoMessage : string;
	pastTreatmentSelectedText : string;
	pastCauseOfRespDistressStr : string;
	pastRiskRespDistressStr : string;
  pastPneumoOrderSelectedText : string;
  otherMedTablePneumothoraxText : string;
	causeOfPneumoStr : string;
	pastCauseOfPneumoStr : string;
  needleLeft : boolean;
	rdsHourFlag : boolean;
	pneumoHourFlag : boolean;
	apneaHourFlag : boolean;
	pphnHourFlag : boolean;
	otherHourFlag : boolean;
  responseRespSubmit : any;
  responseType : any;
  responseMessage : any;
	inotopstext : string;
	otherMedTableText : string;
	otherMedTablePPHNText : string;
	otherMedTableFlag : boolean;
	otherMedTablePPHNFlag : boolean;
	otherMedTablePneumothoraxFlag : boolean;
	rangeArr : Array<any>;
	treatmentPneumoSelectedText : string;
  tubenoLeft : number;
  tubenoRight : number;
  pastpneumothoraxTempObj : PastpneumothoraxTempObj;
  hoursList : Array<any>;
  minutesList : Array<any>;
  ageOnsetPreviousExceed : boolean;
  isRdsSelectPneumo : boolean;
  isRdsSavedPneumo : boolean;
  rsAmplitude : string;
	rsEt : string;
	rsFio2 : string;
	rsFlowRate : string;
	rsFrequency : string;
	rsIt : string;
	rsMap : string;
	rsMechVentType : string;
	rsMv : string;
	rsPeep : string;
	rsPip : string;
	rsTv : string;
	rsVentType : string;
  isSurfactantSelectPneumo : boolean;
  isAntibioticsSelectPneumo : boolean;
  isNeedleAspirationSelectPneumo : boolean;
  isOtherSelectPneumo : boolean;
  rangePip : Array<any>;
  rangePeep : Array<any>;
  rangeFiO2 : Array<any>;
  rangeIt : Array<any>;
  rangeRate : Array<any>;
  rangeMap : Array<any>;
  range1150Arr : Array<any>;
  hourDayDiffAgeAtAssessmentPneumo : any;
  ageAtAssessmentPneumoHourFlag : boolean;
  rangeDecimalArr : Array<any>;
  response : any;
  whichModuleRespirstorySupport : string;
	respiratorySupportPopupView : boolean;
  isRdsValid : boolean;
  isSurfactantInsure : boolean;
  isSurfactantSavedPneumo : boolean;
  isAntibioticsSavedPneumo : boolean;
  isChestTubeSavedPneumo : boolean;
  isNeedleAspirationSavedPneumo : boolean;
  isOtherSavedPneumo : boolean;
  inactiveMessageText : string;
	inactiveModule : string;
  pneumoTreatmentStatus : any;
	inactiveWithRespSupport : boolean;
  investOrderSelected : Array<any>;
  investOrderNotOrdered : Array<any>;
  expanded : boolean;
  isResp : string;
  needleRight : boolean;
  isPlanApneaVisible : boolean;
  isApneaCauseVisible : boolean;
  respGraphViewToggle : boolean;
  isProgressNotesActionVisible : boolean;
  isActionApneaVisible : boolean;
  isclinicalSectionApneaVisible : boolean;
	isScoreDistressVisible : boolean;
  SelectedInotrope : string;
	DopamineYes : any;
	DoputamineYes : any;
	EpinephrineValue : any;
	VasopressinValue : any;
	MilrinoneValue : any;
	totalInotrope : number;
  resp : boolean;
  loggedUser : string;
  eventName : string;
  responseSiverman : any;
  downesScoreList : DownesScoreList;
  responseDownes : any;
  totalCyanosis : number;
  totalRetractions : number;
  totalGrunting : number;
  totalAirEntry : number;
  totalRespiratoryRate : number;
  downesValueCalculated : number;
  isPPHNSurfactantInsure : boolean;
  passiveMessage : string;
  isActionPPHNVisible : boolean;
  isPlanPPHNVisible : boolean;
	isclinicalSectionPPHNVisible : boolean;
  isPphnprogressNotesVisible : boolean;
  isPphnCauseVisible : boolean;
  printDataObj : any;
  isApneaRespiratorySelect : boolean;
	isApneaRespiratorySaved : boolean;
  isApneaCaffeineSelect : boolean;
	isApneaCaffeineSaved : boolean;
  isApneaOtherSelect : boolean;
	isApneaOtherSaved : boolean;
  treatmentApneaTabsVisible : boolean;
	apneaTreatmentStatus : any;
  ageAtAssessmentApneaHourFlag : boolean;
  fromInsureCont : boolean;
  ageAtAssessmentRdsHourFlag : boolean;
  symptomsArr : Array<any>;
  silvermanTabContent : string;
  downesTabContent : string;
  tabContent : string;
  isclinicalSectionPhenumothoraxVisible : boolean;
  isPhenumothoraxsurfactantInsure : boolean;
  isActionPhenumothoraxVisible : boolean;
  isCausePneumothoraxVisible : boolean;
  isProgressNotesPneumothoraxVisible : boolean;
  isPlanPneumothoraxVisible : boolean;
  treatmentTabsVisiblePneumo : boolean;
  treatmentVisiblePastPneumo : boolean;
  listTestsByCategory : Array<any>;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromToTableError : boolean;
  minDate : string;
  maxDate : Date;
  isLoaderVisible : boolean;
  loaderContent : string;
  pastPrescriptionList : any;
  currentEvent : string;
  currentDate : Date;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.currentDate = new Date();
    this.respiratorySystemObj = new RespiratorySystemObj();
    this.respiratoryDistressValue = true;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.isclinicalSectionDistressVisible = true;
    this.isActionDistressVisible = true;
    this.isPlanDistressVisible = true;
    this.isCauseDistressVisible = true;
    this.isrespGraph = true;
    this.isProgressNotesDistressVisible = true;
    this.totalUpperChest = 0;
    this.totalLowerChest = 0; this.totalExpir = 0;
    this.totalNaras =0;
    this.totalXiphoid = 0;
    this.downesScoreList = new DownesScoreList;
    this.SilverValueCalculated = 0;
    this.assessmentTempObj = {
				"rdsEpisodeNumber" : 1,
				"apneaEpisodeNumber" : 1,
				"pphnEpisodeNumber" : 1,
				"pneumothoraxEpisodeNumber" : 1,
				"othersEpisodeNumber":1,
				"bpdEpisodeNumber":1
		};
    this.ApneaTempObj = {};
    this.OtherTempObj = {};
    this.orderSelectedText = "";
		this.pastOrderSelected = "";
		this.treatmentSelectedText = "";
		this.pastTreatmentSelectedText = "";
		this.pastCauseOfRespDistressStr = "";
		this.pastRiskRespDistressStr = "";
    this.hourDayDiffAgeAtAssessmentPneumo = 0;
    this.ageAtAssessmentPneumoHourFlag = false;
		this.pastPneumoOrderSelectedText = "";
    this.warningMessage = "";
		this.causeOfPneumoStr = "";
		this.pastCauseOfPneumoStr = "";
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.rdsHourFlag = false;
		this.pneumoHourFlag = false;
		this.apneaHourFlag = false;
		this.pphnHourFlag = false;
		this.otherHourFlag = false;
    this.needleRight = true;
    this.needleLeft = true;
		this.inotopstext = "";
		this.otherMedTableText = "View Past Medication";
		this.otherMedTablePPHNText = "View Past Medication";
		this.otherMedTableFlag = false;
		this.otherMedTablePPHNFlag = false;
		this.otherMedTablePneumothoraxFlag = false;
		this.rangeArr = [];
		this.treatmentPneumoSelectedText = "";
    this.tubenoLeft = 1;
		this.tubenoRight = 1;
    this.pastpneumothoraxTempObj = new PastpneumothoraxTempObj;
    this.pneumothoraxTempObj = {};
    this.pneumothoraxTempObj.needleAspHour = null;
    this.pastpneumothoraxTempObj.pneumoTreatmentSelectedText = "";
    this.pneumothoraxTempObj.needleAspMin = null;
    this.pneumothoraxTempObj.needleAspMeridian = "";
    this.pneumothoraxTempObj.chestHour = null;
    this.pneumothoraxTempObj.chestMin = null;
    this.pneumothoraxTempObj.chestMeridian = "";
    this.pneumothoraxTempObj.needleAspDate = new Date();
    this.pneumothoraxTempObj.chestTubeDate = new Date();
    this.tubenoLeft = 1;
    this.tubenoRight = 1;
    this.listTestsByCategory = [];
    this.selectAll = null;
    for(var i =1;i<=100;i++){
			this.rangeArr.push(i);
		}
		this.hoursList = [];
		for(var i=1;i<=12;++i){
			if(i<10)
				{this.hoursList.push("0"+i.toString());}
			else
				{this.hoursList.push(i.toString());}
		}
		this.minutesList = [];
		for(var i=0;i<60;++i){
			if(i<10)
				{this.minutesList.push("0"+i.toString());}
			else
				{this.minutesList.push(i.toString());}
		}
    this.ageOnsetPreviousExceed = false;
    this.rsAmplitude = "";
		this.rsEt = "";
		this.rsFio2 = "";
		this.rsFlowRate ="";
		this.rsFrequency = "";
		this.rsIt = "";
		this.rsMap = "";
		this.rsMechVentType = "";
		this.rsMv = "";
		this.rsPeep = "";
		this.rsPip = "";
		this.rsTv = "";
		this.rsVentType ="";
    this.rangePip=[];
    this.rangePeep = [];
    this.rangeFiO2 = [];
		this.rangeIt = [];
    this.rangeRate = [];
    this.rangeMap = [];
		for(var i=10; i<=50; ++i)
			{this.rangePip.push(i.toString());}
		for(var i=3;i<=12;++i)
			{this.rangePeep.push(i.toString());}
		for(var i=21;i<=100;++i)
			{this.rangeFiO2.push(i.toString());}
		for (var i = 0.10; i <=0.81; i+=0.01)
			{this.rangeIt.push(i.toFixed(2).toString());}
		for(var i=10;i<=120;i+=1)
			{this.rangeRate.push(i.toFixed(2).toString());}
		for(var i=3;i<=25;++i)
			{this.rangeMap.push(i.toString());}
		for(var i =1;i<=100;i++){
			this.rangeArr.push(i.toString());
		}
		this.range1150Arr = [];
		for(var i =1;i<=150;i++){
			this.range1150Arr.push(i.toString());
		}
    this.rangeDecimalArr = [];
		for(var i =0.01;i<=0.90;i = i + 0.01){
			this.rangeDecimalArr.push(Math.round(i * 100) / 100);
		}
    this.whichModuleRespirstorySupport = "";
		this.respiratorySupportPopupView = false;
    this.isRdsValid = true;
    this.isSurfactantInsure = false;
    this.inactiveMessageText = "";
		this.inactiveModule = "";
		this.inactiveWithRespSupport = false;
    this.investOrderSelected = [];
		this.investOrderNotOrdered = [];
    this.expanded = false;
    this.isResp = "";
    this.isPlanApneaVisible = false;
    this.isApneaCauseVisible = false;
    this.respGraphViewToggle = true;
    this.isProgressNotesActionVisible = true;
    this.isActionApneaVisible = false;
    this.isclinicalSectionApneaVisible = true;
  	this.isScoreDistressVisible = false;
    this.SelectedInotrope = "";
		this.DopamineYes = null;
		this.DoputamineYes = null;
		this.EpinephrineValue = null;
		this.VasopressinValue = null;
		this.MilrinoneValue = null;
		this.totalInotrope = 0;
    this.resp = true;
    this.eventName = "";
    this.totalCyanosis = 0;
    this.totalRetractions = 0;
    this.totalGrunting = 0;
    this.totalAirEntry = 0;
    this.totalRespiratoryRate = 0;
    this.downesValueCalculated = 0;
    this.SilverValueCalculated = 0;
    this.totalUpperChest = 0;
    this.totalLowerChest = 0;
    this.totalExpir = 0;
    this.totalNaras = 0;
    this.totalXiphoid = 0;
    this.isPPHNSurfactantInsure = false;
    this.passiveMessage = "Respiratory Support is on, do you want to stop it?";
    this.isActionPPHNVisible = false;
    this.isPlanDistressVisible = false;
  	this.isclinicalSectionPPHNVisible = true;
    this.isPphnprogressNotesVisible = true;
    this.isPphnCauseVisible = false;
    this.printDataObj = {};
    this.isApneaRespiratorySelect = false;
		this.isApneaRespiratorySaved = false;
		this.isApneaCaffeineSelect = false;
		this.isApneaCaffeineSaved = false;
		this.isApneaOtherSelect = false;
		this.isApneaOtherSaved = false;
		this.treatmentApneaTabsVisible = true;
		this.apneaTreatmentStatus = undefined;
    this.ageAtAssessmentApneaHourFlag = false;
    this.PphnTempObj ={
      "ageAtAssessmentPphnHourFlag" : false,
      "ageAtAssessmentRdsHourFlag" : false,
      "isSurfactantSelectPphn" : false,
		  "isAntibioticsSelectPphn" : false,
		  "isInotropesSelectPphn" : false,
		  "isVasodilatorsSelectPphn" : false,
		  "isSedationSelectPphn" : false,
		  "isRespSelectPphn" : false,
		  "isOtherSelectPphn" : false,
      "treatmentPphnTabsVisible" : true,
		  "pphnTreatmentStatus" : 'Continue'};
    this.fromInsureCont = false;
    this.symptomsArr = [];
    this.silvermanTabContent = 'silver';
    this.downesTabContent = 'downes';
    this.tabContent = 'neuro';
    this.isclinicalSectionPhenumothoraxVisible = true;
    this.isActionPhenumothoraxVisible = false;
    this.isPhenumothoraxsurfactantInsure = false;
    this.isCausePneumothoraxVisible = false;
    this.isProgressNotesPneumothoraxVisible = true;
    this.isPlanPneumothoraxVisible = false;
    this.isLoaderVisible = true;
    this.loaderContent = 'Loading...';
    this.pastPrescriptionList = [];
    this.currentEvent = 'RDS';
    this.init();
  }

  init() {
    this.RDSTempObj = {};
    this.RDSTempObj.assessmentDate = "454";
    this.PneumoTempObj = {};
    this.PneumoTempObj.tempPastPrescriptionList = [];
    this.PneumoTempObj.mapForNumber = {
      "1" : "First",
      "2" : "Second",
      "3" : "Third",
      "4" : "Fourth",
      "5" : "Fifth",
      "6" : "Sixth"
    };
    this.pastpneumothoraxTempObj.pneumoTreatmentSelectedText = "";
    this.fio2Temp = null;
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
    this.getDropDowns();
    this.getDataResp();
  };
  bolusAction = function(item) {
		if(item.bolus){
			item.freq_type =  null;
			item.frequency =  null;
		}
	}
	routeAction = function(item) {
		if(item.route != 'IV' && item.route != 'PO'){
			item.rate =  null;
		}
	}
	freqTypeAction = function(item) {
		if(item.freq_type == 'continuous'){
			item.rate =  null;
			item.frequency =  null;
		}
	}
  clinicalSectionDistressVisible = function(){
		this.isclinicalSectionDistressVisible = !this.isclinicalSectionDistressVisible ;
	}
	actionSectionDistressVisible = function(){
		this.isActionDistressVisible = !(this.isActionDistressVisible);
	}
	scoreSectionDistressVisible = function(){
		this.isScoreDistressVisible = !this.isScoreDistressVisible;
	}
	planSectionDistressVisible = function(){
		this.isPlanDistressVisible = !this.isPlanDistressVisible;
	}
	progressNotesDistressSectionVisible = function(){
		this.isProgressNotesDistressVisible = !this.isProgressNotesDistressVisible;
	}
	causeRDSSectionVisible = function(){
		this.isCauseDistressVisible = !this.isCauseDistressVisible;
	}
	jaundiceView = function(){
		this.isrespGraph = !this.isrespGraph;
	}
  getDropDowns= function(){
    var dataParam={};
    try{
      this.http.request(this.apiData.getDropDowns,dataParam)
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
onInsureContinuePneumoThorax = function(){
  this.PneumoTempObj.fromInsureContinue = true;
  this.saveTreatmentPneumo('surfactant');
  this.PneumoTempObj.fromInsureContinue = false;
}
switchPastMedTablePneumothorax = function() {
  if(this.otherMedTablePneumothoraxFlag) {
    this.otherMedTablePneumothoraxText = "View Past Medication";
  } else {
    this.otherMedTablePneumothoraxText = "Hide Past Medication";
  }
  this.otherMedTablePneumothoraxFlag = !this.otherMedTablePneumothoraxFlag;
}
handleDropDownData = function(response : any){
  this.dropdownData = response;
}
getDataResp = function () {
  this.PphnTempObj = {};
  this.pneumothoraxTempObj = {};
  this.PphnTempObj.isPastTreamentAvailable = false;
  try{

    this.http.request(this.apiData.getDataResp + this.childObject.uhid + '/test',)
    .subscribe(res => {
      this.respiratorySystemObj = res.json();
      this.isLoaderVisible = false;
    	if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus == 'Yes') {
    		this.respiratorySystemObj.respSystemObject.eventName = "respDistress";
    	}

      this.pastPrescriptionList = [];
      if(this.respiratorySystemObj.respSystemObject.pastPrescriptionList != null && this.respiratorySystemObj.respSystemObject.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.respiratorySystemObj.respSystemObject.pastPrescriptionList.length; i++){
          if(this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].eventname == 'RDS' || this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].eventname == 'Apnea'
            || this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].eventname == 'PPHN' || this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].eventname == 'Pneumothorax'){
              var startDate = new Date(this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].medicineOrderDate);
              var medContiuationDayCount = Math.abs(Math.ceil((this.currentDate.getTime() - startDate.getTime())/(1000*60*60*24)));
              if(medContiuationDayCount == 0){
                medContiuationDayCount = 1;
              }
              this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].medicineDay = medContiuationDayCount;
            this.pastPrescriptionList.push(this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i]);
          }
        }
        this.respiratorySystemObj.respSystemObject.pastPrescriptionList = [];
      }

    	//for episodes number.........
    	this.assessmentTempObj = this.indcreaseEpisodeNumber(this.respiratorySystemObj.respSystemObject.pastTableList);
    	this.rdsTreatmentStatus = {};
    	this.othersTreatmentStatus = {};
    	this.pneumoTreatmentStatus = {};
      this.setTodayAssessment(new Date()); //apnea
      this.setTodayAssessmentRDS(new Date());
      this.setTodayAssessmentPPHN(new Date());

    	this.pastRespSupportVentType = "";
    	if (this.respiratorySystemObj.respSystemObject.respSupport!=null
    			&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null
    			&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!="") {
    		this.pastRespSupportVentType = this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
    	}
    	var date = new Date();
    	this.formatAMPM(date);
      this.PneumoTempObj.assessmentDate = new Date();
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentDate = this.PneumoTempObj.assessmentDate;
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime = this.PneumoTempObj.assessmentDate;
    	this.currentHrs = date.getHours();
    	if(localStorage.getItem('isComingFromProcedureTube')!=null){
    		var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromProcedureTube'));
    		if(isComingFromPrescription=="Yes"){
    			this.PulmonaryDistressVisible();
    		}
    	}
    	if(localStorage.getItem('isComingFromPrescription')!=null){
    		var isComingFromPrescription = JSON.parse(localStorage.getItem('isComingFromPrescription'));
    		if(isComingFromPrescription){
    			this.setPresRedirectVariable();
    		}
    		this.initTreatmentRedirectApnea();
    		this.initTreatmentRedirectRds();
    	  this.initTreatmentRedirectPneumo();
    		localStorage.removeItem("isComingFromPrescription");
    	} else {
    		this.initTreatmentVariablesApnea();
    		this.initTreatmentVariablesRds();
    	  this.initTreatmentVariablesPneumo();
    	}

    	var isComingFromCNSEvent = JSON.parse(localStorage.getItem('isComingFromCNSEvent'));
    	if(isComingFromCNSEvent != null){
    		localStorage.removeItem("isComingFromCNSEvent");
    		localStorage.setItem('redirectBackToCns',JSON.stringify(true));
    		var eventTarget = JSON.parse(localStorage.getItem('eventTarget'));
    		this.respiratorySystemObj.systemStatus = 'Yes';
    		if(eventTarget == "RDS") {
    			this.respiratoryDistressVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "respDistress";
    			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = 'Yes';
    		} else if (eventTarget == "PPHN") {
    			this.PPHNVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "PPHN";
    			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus = 'Yes';
    		}
    	}else{
    		localStorage.removeItem("isComingFromCNSEvent");
    	}
    	if(JSON.parse(localStorage.getItem('admissionform')) != null){
    		localStorage.removeItem("admissionform");
    		localStorage.setItem('redirectBackToAdmission',JSON.stringify(true));
    		var eventName = JSON.parse(localStorage.getItem('eventName'));
    		this.respiratorySystemObj.systemStatus = 'Yes';
    		if(eventName == "RDS" || eventName == "Respiratory Distress") {
    			this.respiratoryDistressVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "respDistress";
    			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = 'Yes';
    		} else if (eventName == "Apnea") {
    			this.apneaDistressVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "apnea";
    			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = 'Yes';
    		} else if (eventName === "PPHN") {
    			this.PPHNVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "pphn";
    			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus = 'Yes';
    		} else if (eventName == "Pneumothorax") {
    			this.PulmonaryDistressVisible();
    			this.respiratorySystemObj.respSystemObject.eventName = "pneumothorax";
    			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus = 'Yes';
    		}
    	}else{
    		localStorage.removeItem("admissionform");
    	}
    	this.printDataObj.timeFrom = "08";
    	this.printDataObj.timeTo = "08";
    	this.printDataObj.dateFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    	this.printDataObj.dateTo = new Date();
    	console.log(this.respiratorySystemObj);
    	this.creatingProgressNotesTemplate();
    	this.creatingProgressNotesApneaTemplate();
    	this.creatingProgressNotesPphnTemplate();
      this.creatingProgressNotesPneumoTemplate();
    	this.totalSilvermanScore = "";
    	this.totalDownyScore = "";
    	this.needleDate = "";
    	this.needleHour = "";
    	this.needleMin = "";
    	this.needleMeridian = "";
    	//assessments hypoglycemia communications operations.........
    	if(localStorage.getItem('isComingFromSymptomaticEvent')!=null){
    		var isComingFromSymptomaticEvent = JSON.parse(localStorage.getItem('isComingFromSymptomaticEvent'));
    		if(isComingFromSymptomaticEvent){
    			if(localStorage.getItem("hypoglycemiaTempObj")!=null){
    				var hypoglycemiaTempObj = JSON.parse(localStorage.getItem("hypoglycemiaTempObj"));
    				if(hypoglycemiaTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}else if(hypoglycemiaTempObj.symptomaticEvent=='rds'){
    					this.respiratoryDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = "Yes";
    				}
    			} else if(localStorage.getItem("hyperglycemiaTempObj")!=null){
    				var hyperglycemiaTempObj = JSON.parse(localStorage.getItem("hyperglycemiaTempObj"));
    				if(hyperglycemiaTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				} else if(hyperglycemiaTempObj.symptomaticEvent=='rds') {
    					this.respiratoryDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = "Yes";
    				}
    			} else if(localStorage.getItem("hypernatremiaTempObj")!=null){
    				var hyernatremiaTempObj = JSON.parse(localStorage.getItem("hypernatremiaTempObj"));
    				if(hyernatremiaTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}
    			} else if(localStorage.getItem("hyponatremiaTempObj")!=null){
    				var hyponatremiaTempObj = JSON.parse(localStorage.getItem("hyponatremiaTempObj"));
    				if(hyponatremiaTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}
    			} else if(localStorage.getItem("iemTempObj")!=null){
    				var iemTempObj = JSON.parse(localStorage.getItem("iemTempObj"));
    				if(iemTempObj.symptomaticEvent=='rds'){
    					this.respiratoryDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = "Yes";
    				}
    			} else if(localStorage.getItem("AcidosisTempObj")!=null){
    				var AcidosisTempObj = JSON.parse(localStorage.getItem("AcidosisTempObj"));
    				if(AcidosisTempObj.symptomaticEvent=='rds'){
    					this.respiratoryDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = "Yes";
    				}
    				else if(AcidosisTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}
    			}
    		}
    		localStorage.removeItem('isComingFromSymptomaticEvent');
    		localStorage.setItem('isRedirectBackToAssessmentSymptomatic',"true");
    	}
    	if(localStorage.getItem('isComingFromCNSSymptomaticEvent')!=null) {
    		var isComingFromCNSSymptomaticEvent = JSON.parse(localStorage.getItem('isComingFromCNSSymptomaticEvent'));
    		if(isComingFromCNSSymptomaticEvent){
    			if(localStorage.getItem("seizuresTempObj")!=null){
    				var seizuresTempObj = JSON.parse(localStorage.getItem("seizuresTempObj"));
    				if(seizuresTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}
    			}
    		}
    		localStorage.removeItem('isComingFromCNSSymptomaticEvent');
    		localStorage.setItem('isRedirectBackToCNSAssessmentSymptomatic',"true");
    	}
    	if(localStorage.getItem('isComingFromInfectionSymptomaticEvent')!=null) {
    		var isComingFromInfectionSymptomaticEvent = JSON.parse(localStorage.getItem('isComingFromInfectionSymptomaticEvent'));
    		if(isComingFromInfectionSymptomaticEvent){
    			if(localStorage.getItem("sepsisTempObj")!=null){
    				var sepsisTempObj = JSON.parse(localStorage.getItem("sepsisTempObj"));
    				if(sepsisTempObj.symptomaticEvent=='apnea'){
    					this.apneaDistressVisible();
    					this.respiratorySystemObj.systemStatus = "Yes";
    					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Yes";
    				}
    			}
    		}
    		localStorage.removeItem('isComingFromInfectionSymptomaticEvent');
    		localStorage.setItem('isRedirectBackToInfectAssessmentSymptomatic',"true");
    	}
    	var isComingFromInfectionTreatment = JSON.parse(localStorage.getItem('isComingFromInfectionTreatment'));
    	if(isComingFromInfectionTreatment != null){
    		localStorage.removeItem("isComingFromInfectionTreatment");
    		localStorage.setItem('redirectBackToInfection',JSON.stringify(true));
    		this.respiratoryDistressVisible();
    	}else{
    		localStorage.removeItem("isComingFromInfectionTreatment");
    	}
    	var respStr = "";
    	var pastRespSupportObj	= this.populateRespSupportText(this.respiratorySystemObj.respSystemObject.respSupport);
    	this.pastRespSupportText = "Baby was put on "+ pastRespSupportObj.respVentType
    	+" respiratory support (" + pastRespSupportObj.respStr + "). ";
    	//RDS ------------------past populate changes.................................//
    	if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
    			&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0){
    		for(var index = 0; index<this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length;index++){
    			var utcDate = this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[index].creationtime;
    			var localDate = new Date(utcDate);
    			this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[index].creationtime = localDate;
    		}
    		this.populatePastSelectedText("RDS"
    				,this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress["0"],pastRespSupportObj.respStr);
    	}
    	//RDS age at onset enable/disable
    	if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
    			&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0){
    		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus=='Inactive'){
    			this.pastAgeInactiveRds = false;
    		} else {
    			this.pastAgeInactiveRds = true;
    		}
    	} else {
    		this.pastAgeInactiveRds = false;
    	}
    	// PPHN age at onset enable/disable
    	if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn!=null
    			&& this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0){
    		if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus=='Inactive'){
    			this.pastAgeInactivePphn = false;
    		} else {
    			this.pastAgeInactivePphn = true;
    		}
    	} else {
    		this.pastAgeInactivePphn = false;
    		if(this.respiratorySystemObj.respSystemObject.respSupport != null
    				&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
    				&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
    			console.log('respiratory support object PPHN');
    			var symptomArr = [];
    			symptomArr.push('TRE010');
    			this.isRespSavedPphn = true;
    			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = symptomArr;
    		}
    	}
    	//Pneumothorax + set default value for pneumothorax..
    	if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo!=null
    			&& this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0){
    		if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus=='Inactive'){
    			this.pastAgeInactivePneumothorax = false;
    		} else {
    			this.pastAgeInactivePneumothorax = true;
    		}
    	} else {
    		this.pastAgeInactivePneumothorax = false;
    	}
    	this.pneumothoraxTempObj.rightSelectedTubeValue = 0;
    	this.pneumothoraxTempObj.leftSelectedTubeValue = 0;
    	//Apnea age at onset enable/disable
    	if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea!=null
    			&& this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0){
    		if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus=='Inactive'){
    			this.pastAgeInactiveApnea = false;
    		} else {
    			this.pastAgeInactiveApnea = true;
    		}
    	} else {
    		this.pastAgeInactiveApnea = false;
    	}
    	this.creatingProgressNotesApneaTemplate();
    	//Others age at onset enable/disable
    	if(this.respiratorySystemObj.respSystemObject.others.pastOtherList!=null
    			&& this.respiratorySystemObj.respSystemObject.others.pastOtherList.length>0){
    		if(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].eventstatus=='Inactive'){
    			this.pastAgeInactiveOthers = false;
    		} else {
    			this.pastAgeInactiveOthers = true;
    		}
    	} else {
    		this.pastAgeInactiveOthers = false;
    	}
    	//ordering vent mode for calculation...
    	for(var indexVentMode=0;indexVentMode<this.respiratorySystemObj.dropDowns.ventMode.length;indexVentMode++){
    		this.respiratorySystemObj.dropDowns.ventMode[indexVentMode].indexVentModeValue = indexVentMode;
    	}
    	//past populate PPHN
    	this.PphnTempObj.treatmentVisiblePast = false;
    	if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn!=null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0){
    		for(var index = 0; index<this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length;index++){
    			var utcDate = this.respiratorySystemObj.respSystemObject.pphn.pastPphn[index].creationtime;
    			var localDate = new Date(utcDate);
    			this.respiratorySystemObj.respSystemObject.pphn.pastPphn[index].creationtime = localDate;
    		}
    		if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].treatmentSelectedText != null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].treatmentSelectedText != ""){
    			this.PphnTempObj.treatmentVisiblePast = true;
    			this.PphnTempObj.pastTreatmentPphnText = this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].treatmentSelectedText;
    		} else {
    			this.PphnTempObj.treatmentVisiblePast = false;
    		}
    		this.populatePastSelectedText("PPHN" ,this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0],pastRespSupportObj.respStr);
    	}
    	//apnea
    	if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea!=null && this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0){
    		for(var index = 0; index<this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length;index++){
    			var utcDate = this.respiratorySystemObj.respSystemObject.apnea.pastApnea[index].creationtime;
    			var localDate = new Date(utcDate);
    			this.respiratorySystemObj.respSystemObject.apnea.pastApnea[index].creationtime = localDate;
    		}
    		this.populatePastSelectedText("Apnea"
    				,this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0],pastRespSupportObj.respStr);
    	}
    	//others past text
    	if(this.respiratorySystemObj.respSystemObject.others.pastOtherList!=null && this.respiratorySystemObj.respSystemObject.others.pastOtherList.length>0){
    		for(var index = 0; index<this.respiratorySystemObj.respSystemObject.others.pastOtherList.length;index++){
    			var utcDate = this.respiratorySystemObj.respSystemObject.others.pastOtherList[index].creationtime;
    			var localDate = new Date(utcDate);
    			this.respiratorySystemObj.respSystemObject.others.pastOtherList[index].creationtime = localDate;
    		}
    		this.pastOtherOrderInvestigationStr = "";
    		this.pastTreatmentOtherText = "";
    		if(this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction!=null &&
    				this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction!=''){
    			if(this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction.includes("TRE023")){
    				if(this.pastTreatmentOtherText==''){
    					this.pastTreatmentOtherText = "Surfactant";
    				} else {
    					this.pastTreatmentOtherText += ", "+"Surfactant";
    				}
    			}
    			if(this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction.includes("TRE024")){
    				if(this.pastTreatmentOtherText==''){
    					this.pastTreatmentOtherText = "Respiratory Support";
    				} else {
    					this.pastTreatmentOtherText += ", "+"Respiratory Support";
    				}
    			}
    			if(this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction.includes("TRE025")){
    				if(this.pastTreatmentOtherText==''){
    					this.pastTreatmentOtherText = "Antibiotics";
    				} else {
    					this.pastTreatmentOtherText += ", "+"Antibiotics";
    				}
    			}
    			if(this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentaction.includes("other")){
    				if(this.pastTreatmentOtherText==''){
    					this.pastTreatmentOtherText = this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentOther;
    				} else {
    					this.pastTreatmentOtherText += ", "+this.respiratorySystemObj.respSystemObject.others.pastOtherList["0"].treatmentOther;
    				}
    			}
    		}
    		if(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].orderInvestigationList != null) {
    			for(var i=0; i < this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].orderInvestigationList.length;i++) {
    				if(this.pastOtherOrderInvestigationStr == "") {
    					this.pastOtherOrderInvestigationStr = this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].orderInvestigationList[i];
    				} else {
    					this.pastOtherOrderInvestigationStr += ", "+this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].orderInvestigationList[i];
    				}
    			}
    		}
    	}
    	//pneumothorax
    	if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo!=null
    			&& this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0){
    		for(var index = 0; index<this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length;index++){
    			var utcDate = this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[index].creationtime;
    			var localDate = new Date(utcDate);
    			this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[index].creationtime = localDate;
    		}
    		this.populatePastSelectedText("pneumothorax"
    				,this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0],pastRespSupportObj.respStr);
    	}
    	if((this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastRightTubes!=null
    			&& this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastRightTubes.length>0)
    			|| (this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastLeftTubes!=null
    					&& this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastLeftTubes.length>0)){
    		var treatmentindex = 0;
    		for(var index=0 ; index<this.respiratorySystemObj.dropDowns.treatmentAction.length; index++){
    			if(this.respiratorySystemObj.dropDowns.treatmentAction[index].key=="TRE019"){
    				treatmentindex = index;
    				break;
    			}
    		}
    		this.respiratorySystemObj.dropDowns.treatmentAction[treatmentindex].flag = true;
    		this.onCheckMultiCheckBoxValue("TRE019",'pneumoTreatmentAction',true);
    	}

    	if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastRightTubes!=null
    			&& this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastRightTubes.length>0){
    		this.tubenoRight = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastRightTubes.length+1;
    	} else {
    		this.tubenoRight = 1;
    	}
    	if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastLeftTubes!=null
    			&& this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastLeftTubes.length>0){
    		this.tubenoLeft = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.pastLeftTubes.length+1;
    	} else {
    		this.tubenoLeft = 1;
    	}
    	this.symtomsArray = [];
    	this.symptomsArray = this.respiratorySystemObj.respSystemObject.apnea.pastNursingEpisode;
    	this.firstEvent = this.symptomsArray[0];
    	this.creatingProgressNotesPphnTemplate();
    },
    err => {
      console.log("Error occured.")
    }
  );
}
catch(e){
  console.log("Exception in http service:"+e);
}
this.checkSilvermanAndSubmit = function(){
				// adding conditions for inactive validation
				console.log(this.respiratorySystemObj);
				if(this.respiratorySystemObj.respSystemObject.eventName!=null && this.respiratorySystemObj.respSystemObject.eventName!='')
					this.eventName = this.respiratorySystemObj.respSystemObject.eventName;

				if(this.respiratorySystemObj.systemStatus=='No'){
					//passive respiratory system
					if(this.respiratorySystemObj.respSystemObject.pastTableList.length==0 || this.respiratorySystemObj.respSystemObject.pastTableList[0].respSystemStatus!='Yes'){
						this.warningMessage = "Cannot make Respiratory System passive.";
            this.showWarningPopUp();
					} else{

						//check for each disease state
						var problemDiseases = [];
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0 && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus!='Inactive')
							problemDiseases.push("Respiratory Distress");
						if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0 && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus!='Inactive')
							problemDiseases.push("PPHN");
						if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0 && this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus!='Inactive')
							problemDiseases.push("Apnea");
						if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0 && this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus!='Inactive')
							problemDiseases.push("Pneumothorax");
						if(problemDiseases.length==0){
							console.log(this.respiratorySystemObj);
							var passiveTime = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime ;
							this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime = passiveTime;
							this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime = passiveTime;
							this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime = passiveTime;
							console.log(this.respiratorySystemObj);
							this.submitResp();
						} else{
							var msg = problemDiseases.join();
							if(problemDiseases.length>1){
								this.warningMessage = "Cannot make Respiratory System passive. "+msg+" are not inactive.";
                this.showWarningPopUp();
              }
							else{
								this.warningMessage = "Cannot make Respiratory System passive. "+msg+" is not inactive.";
                this.showWarningPopUp();
              }
						}
					}
				} else if(this.respiratorySystemObj.systemStatus=='Inactive'){
					//inactivate respiratory system
					if(this.respiratorySystemObj.respSystemObject.pastTableList.length==0 || this.respiratorySystemObj.respSystemObject.pastTableList[0].respSystemStatus!='No')
					{
						this.warningMessage = "Cannot make Respiratory System inactive.";
            this.showWarningPopUp();
					} else{
						this.showOkPopUp('respSystem','Respiratory System Assessment');
					}
				}
				else if(this.eventName=='respDistress'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus=='' || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus==null){
						this.warningMessage = "Cannot submit.";
            this.showWarningPopUp();
					}
					else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus=='Inactive'){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0 && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus!='Inactive' && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus=='No')
							this.showOkPopUp('respDistress','Respiratory Distress Assessment');
						else{
							this.warningMessage = "Cannot make Respiratory Distress inactive";
              this.showWarningPopUp();
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus=='No'){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length==0){
							this.warningMessage = "Cannot make Respiratory Distress Passive";
              this.showWarningPopUp();
            }
						else {
							if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
								console.log("inside if");
								this.saveDownesScore();
							} else {
								this.submitResp();
							}
							if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
								this.saveSilvermanScore();
							}
						}
					}
					else {
						if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
							console.log("inside if");
							this.saveDownesScore();
						} else {
							this.submitResp();
						}
						if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
							this.saveSilvermanScore();
						}
					}
				} else if(this.eventName=='pphn'){
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus==null || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus=='' ){
						this.warningMessage = "Cannot submit.";
            this.showWarningPopUp();
          }
					else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus=='Inactive'){
						if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0 && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus!='Inactive' && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus=='No')
							this.showOkPopUp('pphn','PPHN Assessment');
						else{
							this.warningMessage = "Cannot make PPHN inactive";
              this.showWarningPopUp();
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus=='No'){
						if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length==0){
							this.warningMessage = "Cannot make PPHN Passive";
              this.showWarningPopUp();
            }
						else {
							if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
								console.log("inside if");
								this.saveDownesScore();
							} else {
								this.submitResp();
							}
							if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
								this.saveSilvermanScore();
							}
						}
					}
					else {
						if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
							console.log("inside if");
							this.saveDownesScore();
						} else {
							this.submitResp();
						}
						if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
							this.saveSilvermanScore();
						}
					}
				} else if(this.eventName=='apnea'){
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus==null || this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus==''){
						this.warningMessage = "Cannot submit.";
            this.showWarningPopUp();
					}
					else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus=='Inactive'){
						if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0 && this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus!='Inactive' && this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus=='No' )
							this.showOkPopUp('apnea','Apnea Assessment');
						else{
							this.warningMessage = "Cannot make Apnea inactive";
              this.showWarningPopUp();
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus=='No'){
						if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length==0){
							this.warningMessage = "Cannot make Apnea Passive";
              this.showWarningPopUp();
            }
						else {
							if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
								console.log("inside if");
								this.saveDownesScore();
							} else {
								this.submitResp();
							}
							if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
								this.saveSilvermanScore();
							}
						}
					}
					else {
						if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
							console.log("inside if");
							this.saveDownesScore();
						} else {
							this.submitResp();
						}
						if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
							this.saveSilvermanScore();
						}
					}
				} else if(this.eventName=='other'){
					if(this.respiratorySystemObj.respSystemObject.others.currentOther.eventstatus=='Inactive')
						this.showOkPopUp('other','Other Assessment')
						else{
							if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
								console.log("inside if");
								this.saveDownesScore();
							} else {
								this.submitResp();
							}
							if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
								this.saveSilvermanScore();
							}
						}
				} else if(this.eventName=='pneumothorax'){
					if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus==null || this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus==''){
						this.warningMessage = "Cannot submit.";
            this.showWarningPopUp();
					}
					else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus=='Inactive'){
						if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0 && this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus!='Inactive' && this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus=='No')
							this.showOkPopUp('pphn','Pneumothorax Assessment');
						else{
							this.warningMessage = "Cannot make Pneumothorax inactive";
              this.showWarningPopUp();
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus=='No'){
						if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length==0){
							this.warningMessage = "Cannot make Pneumothorax Passive";
              this.showWarningPopUp();
            }
						else {
							if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
								console.log("inside if");
								this.saveDownesScore();
							} else {
								this.submitResp();
							}
							if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
								this.saveSilvermanScore();
							}
						}
					}
					else {
						if((this.totalDownyScore != undefined) && (this.totalDownyScore != null) && (this.totalDownyScore != "")){
							console.log("inside if");
							this.saveDownesScore();
						} else {
							this.submitResp();
						}
						if((this.totalSilvermanScore != undefined) && (this.totalSilvermanScore != null) && (this.totalSilvermanScore != "")){
							this.saveSilvermanScore();
						}
					}
				}
			}

  this.initTreatmentRedirectPneumo = function(){
			this.treatmentVisiblePastPneumo = true;
			this.treatmentTabsVisiblePneumo = true;
			this.pneumoTreatmentStatus.value = 'Change';
			console.log("initTreatmentRedirectPneumo");
			console.log(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList);
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList != null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.length > 0) {
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE016')) {
					this.isRdsSavedPneumo = true;
				}
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE015')) {
					this.isSurfactantSavedPneumo = true;
				}
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE017')) {
					this.isAntibioticsSavedPneumo = true;
				}
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE018')) {
					this.isNeedleAspirationSavedPneumo = true;
				}
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE019')) {
					this.isChestTubeSavedPneumo = true;
				}
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('other')) {
					this.isOtherSavedPneumo = true;
				}
			}
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0){
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo["0"].treatmentaction!=null){
					this.treatmentVisiblePastPneumo = true;
				}else{
					this.treatmentVisiblePastPneumo = false;
				}
			} else {
				this.treatmentTabsVisiblePneumo = true;
				this.treatmentVisiblePastPneumo = false;
			}
      this.creatingProgressNotesPneumoTemplate();
		}
    this.initTreatmentVariablesPneumo = function(){
			//init all tabs status
			this.isSurfactantSelectPneumo = false;
			this.isSurfactantSavedPneumo = false;
			this.isAntibioticsSelectPneumo = false;
			this.isAntibioticsSavedPneumo = false;
			this.isNeedleAspirationSelectPneumo = false;
			this.isNeedleAspirationSavedPneumo = false;
			this.isChestTubeSelectPneumo = false;
			this.isChestTubeSavedPneumo = false;
			this.isRdsSelectPneumo = false;
			this.isRdsSavedPneumo = false;
			this.isOtherSelectPneumo = false;
			this.isOtherSavedPneumo = false;
			this.treatmentTabsVisiblePneumo = true;
			this.pneumoTreatmentStatus = {};
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo["0"].treatmentaction!=null){
				this.treatmentVisiblePastPneumo = true;
				this.treatmentTabsVisiblePneumo = false;
			} else {
				this.treatmentTabsVisiblePneumo = true;
				this.treatmentVisiblePastPneumo = false;
				if(this.respiratorySystemObj.respSystemObject.respSupport != null
						&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
						&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
					console.log('respiratory support object Pneumothorax');
					var symptomArr = [];
					symptomArr.push('TRE016');
					this.isRdsSavedPneumo = true;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList = symptomArr;
				}
			}
		}
    this.validatingRds = function() {
				if (this.respiratoryDistressValue){
					if (this.hourDayDiffAgeAtAssessmentRds == null || this.hourDayDiffAgeAtAssessmentRds == undefined){
						this.hourDayDiffAgeAtAssessmentRds = 0;
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays == this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment < this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset){
							$("#ageatassessmentvalidinputRds").css("display","block");
              this.isLoaderVisible = false;
							return false;
						}
						else{
							$("#ageatassessmentvalidinputRds").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays == false && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours == true){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment);
						ageAtAssessment -= this.hourDayDiffAgeAtAssessmentRds;
						ageAtAssessment /= 24;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset){
							$("#ageatassessmentvalidinputRds").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputRds").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays == true && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours == false){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment);
						ageAtAssessment *= 24;
						ageAtAssessment += this.hourDayDiffAgeAtAssessmentRds;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset){
							$("#ageatassessmentvalidinputRds").css("display","block");
              this.isLoaderVisible = false;
            	return false;
						}
						else{
							$("#ageatassessmentvalidinputRds").css("display","none");
							return true;
						}
					}
					else{
						$("#ageatassessmentvalidinputRds").css("display","none");
						return true;
					}
				}
				else if(this.apneaDistressValue){
					if (this.hourDayDiffAgeAtAssessmentApnea == null || this.hourDayDiffAgeAtAssessmentApnea == undefined){
						this.hourDayDiffAgeAtAssessmentApnea = 0;
					}
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours == this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours){
						if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment < this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset){
							$("#ageatassessmentvalidinputApnea").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputApnea").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours == false && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours == true){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment);
						ageAtAssessment -= this.hourDayDiffAgeAtAssessmentApnea;
						ageAtAssessment /= 24;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset){
							$("#ageatassessmentvalidinputApnea").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputApnea").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours == true && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours == false){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment);
						ageAtAssessment *= 24;
						ageAtAssessment += this.hourDayDiffAgeAtAssessmentApnea;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset){
							$("#ageatassessmentvalidinputApnea").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputApnea").css("display","none");
							return true;
						}
					}
					else{
						$("#ageatassessmentvalidinputApnea").css("display","none");
						return true;
					}
				}
				else if(this.PPHNValue){
					if (this.hourDayDiffAgeAtAssessment == null || this.hourDayDiffAgeAtAssessment == undefined){
						this.hourDayDiffAgeAtAssessment = 0;
					}
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays == this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours){
						if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment < this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset){
							$("#ageatassessmentvalidinputPphn").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPphn").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays == false && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours == true){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment);
						ageAtAssessment -= this.hourDayDiffAgeAtAssessment;
						ageAtAssessment /= 24;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset){
							$("#ageatassessmentvalidinputPphn").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPphn").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays == true && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours == false){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment);
						ageAtAssessment *= 24;
						ageAtAssessment += this.hourDayDiffAgeAtAssessment;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset){
							$("#ageatassessmentvalidinputPphn").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPphn").css("display","none");
							return true;
						}
					}
					else{
						$("#ageatassessmentvalidinputPphn").css("display","none");
						return true;
					}
				}
				else if (this.PulmonaryDistressValue){
					if (this.hourDayDiffAgeAtAssessmentPneumo == null || this.hourDayDiffAgeAtAssessmentPneumo == undefined){
						this.hourDayDiffAgeAtAssessmentPneumo = 0;
					}
					if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays == this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays){
						if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment < this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset){
							$("#ageatassessmentvalidinputPneumo").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPneumo").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays == false && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays == true){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment);
						ageAtAssessment -= this.hourDayDiffAgeAtAssessmentPneumo;
						ageAtAssessment /= 24;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset){
							$("#ageatassessmentvalidinputPneumo").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPneumo").css("display","none");
							return true;
						}
					}
					else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays == true && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays == false){
						var ageAtAssessment = Object.assign({},this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment);
						ageAtAssessment *= 24;
						ageAtAssessment += this.hourDayDiffAgeAtAssessmentPneumo;
						ageAtAssessment = Math.round(ageAtAssessment);
						if(ageAtAssessment < this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset){
							$("#ageatassessmentvalidinputPneumo").css("display","block");
              this.isLoaderVisible = false;
              return false;
						}
						else{
							$("#ageatassessmentvalidinputPneumo").css("display","none");
							return true;
						}
					}
					else{
						$("#ageatassessmentvalidinputPneumo").css("display","none");
						return true;
					}
				}
				else{
					return true;
				}
			}
      this.submitResp = function(){
        this.isLoaderVisible = true;
        this.loaderContent = 'Saving...';
				if(this.validatingRds()){
					console.log(this.respiratorySystemObj);
					this.calTimeOfAssessment('Apnea');
					this.calTimeOfAssessment('Pphn');
					this.creatingProgressNotesPphnTemplate();
					this.creatingProgressNotesApneaTemplate();
					this.creatingProgressNotesPneumoTemplate();
					this.respiratorySystemObj.uhid = this.childObject.uhid;
					this.respiratorySystemObj.loggedUser = this.loggedUser;
					if (this.respiratoryDistressValue) {
						this.respiratorySystemObj.respSystemObject.eventName  =  "respDistress";
					} else if (this.PPHNValue) {
						this.respiratorySystemObj.respSystemObject.eventName  =  "pphn";
					} else if (this.apneaDistressValue) {
						this.creatingProgressNotesApneaTemplate();
						this.respiratorySystemObj.respSystemObject.eventName  =  "apnea";
					} else if (this.OthersDistressValue) {
						this.respiratorySystemObj.respSystemObject.eventName  =  "other";
					} else if (this.PulmonaryDistressValue){
						this.respiratorySystemObj.respSystemObject.eventName  =  "pneumothorax";
					}
					console.log(this.respiratorySystemObj.respSystemObject.eventName);
					console.log("The data before saving is");
					console.log(this.respiratorySystemObj);
					this.ageAtAssessmentApneaHourFlag = false;
					this.ageAtAssessmentRdsHourFlag = false;
					this.ageAtAssessmentPneumoHourFlag = false
					this.PphnTempObj.ageAtAssessmentPphnHourFlag = false;
          console.log(this.respiratorySystemObj);
          try
          {
            this.http.post(this.apiData.saveResp,
              this.respiratorySystemObj).subscribe(res => {
                this.vanishSubmitResponseVariable = true;
								this.responseType = res.json().type;
								this.responseMessage = res.json().message;
                this.isLoaderVisible = false;
                this.loaderContent = '';
                setTimeout(() => {
                  this.vanishSubmitResponseVariable = false;
                }, 3000);

								if(JSON.parse(localStorage.getItem('redirectBackToAdmission')) != null) {
									var assessmentComment = "";
									var eventName = JSON.parse(localStorage.getItem('eventName'));
									if(eventName == "RDS" || eventName == "Respiratory Distress") {
										assessmentComment = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.progressnotes;
									} else if (eventName == "Apnea") {
										assessmentComment = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment;
									} else if (eventName == "PPHN") {
										assessmentComment = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.progressnotes;
									} else if (eventName == "Pneumothorax") {
										assessmentComment = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.progressnotes;
									}
                  localStorage.setItem('assessmentComment', JSON.stringify(assessmentComment));
									if(JSON.parse(localStorage.getItem('isComingFromView'))) {
									   this.router.navigateByUrl('/admis/view-profile');
                  } else {
                    this.router.navigateByUrl('/admis/admissionform');
									}
									localStorage.removeItem('isComingFromView');
								}
                this.responseRespSubmit =res.json();
								if(localStorage.getItem('isRedirectBackToAssessmentSymptomatic')!=null){
									var isRedirectBackToAssessmentSymptomatic = JSON.parse(localStorage.getItem('isRedirectBackToAssessmentSymptomatic'));
									if(isRedirectBackToAssessmentSymptomatic){
										localStorage.setItem('isComingBackFromSymptomaticOtherAssessments',JSON.stringify(true));
										localStorage.removeItem('isRedirectBackToAssessmentSymptomatic');
                    this.router.navigateByUrl('/metabolic/assessment-sheet-metabolic');
									}
								} else if(localStorage.getItem('isRedirectBackToCNSAssessmentSymptomatic')!=null){
									var isRedirectBackToCNSAssessmentSymptomatic = JSON.parse(localStorage.getItem('isRedirectBackToCNSAssessmentSymptomatic'));
									if(isRedirectBackToCNSAssessmentSymptomatic){
										localStorage.setItem('isComingBackFromSymptomaticOtherAssessments',JSON.stringify(true));
										localStorage.removeItem('isRedirectBackToCNSAssessmentSymptomatic');
                    this.router.navigateByUrl('/cns/assessment-sheet-cns');
									}
								} else if(localStorage.getItem('isRedirectBackToInfectAssessmentSymptomatic')!=null){
									var isRedirectBackToInfectAssessmentSymptomatic = JSON.parse(localStorage.getItem('isRedirectBackToInfectAssessmentSymptomatic'));
									if(isRedirectBackToInfectAssessmentSymptomatic){
										localStorage.setItem('isComingFromInfectionSymptomaticEvent',JSON.stringify(true));
										localStorage.removeItem('isRedirectBackToInfectAssessmentSymptomatic');
                    this.router.navigateByUrl('/infection/assessment-sheet-infection');
									}
								} else if(JSON.parse(localStorage.getItem('redirectBackToCns'))!=null) {
									var redirectBackToCns = JSON.parse(localStorage.getItem('redirectBackToCns'));
									if(redirectBackToCns!=null){
										localStorage.setItem('isComingFromRespiratory',JSON.stringify(true));
										localStorage.removeItem("redirectBackToCns");
                    this.router.navigateByUrl('/cns/assessment-sheet-cns');
									}
								}else if (JSON.parse(localStorage.getItem('isComingFromProcedureTube'))!=null){
                    this.router.navigateByUrl('/proceed/procedures');
								}else{
									var redirectBackToInfection = JSON.parse(localStorage.getItem('redirectBackToInfection'));
									if(redirectBackToInfection!=null){
										localStorage.setItem('isComingFromRespRds',JSON.stringify(true));
										localStorage.removeItem("redirectBackToInfection");
                    this.router.navigateByUrl('/infection/assessment-sheet-infection');
									}
									console.log("response on submit"+JSON.stringify(this.responseRespSubmit));
									this.getDataResp();
									this.orderSelectedText = "";
									this.totalDownyScore = "";
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
			}
//			below function is used to populating the apnea past history into the current object
			this.populatingApnea = function(map_value){
				var apneaObj = map_value;
				console.log(apneaObj);
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea = apneaObj;
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList !=null){
					for(var apneaCause = 0;apneaCause <this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList.length;apneaCause++){
						for(var i=0;i<this.respiratorySystemObj.dropDowns.causeOfApnea.length;i++){
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList[apneaCause] == this.respiratorySystemObj.dropDowns.causeOfApnea[i].key){
								this.respiratorySystemObj.dropDowns.causeOfApnea[i].flag = true;
							}
						}
					}
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan !=null){
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList = [];
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan.replace("[","").replace("]","");
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan.includes(",")){
						this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan.split(",");
					}else{
						this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList.push(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlan);
					}
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList.length != null){
						for(var i=0;i<this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList.length;i++){
							for(var j=0;j<this.respiratorySystemObj.dropDowns.apneaPlan.length;j++){
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.planList[i] == this.respiratorySystemObj.dropDowns.apneaPlan[j].key){
									this.respiratorySystemObj.dropDowns.apneaPlan[j].flag = true;
								}
							}
						}
					}
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype != null){
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = [];
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype.replace("[","").replace("]","");
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype.includes(",")){
						this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype.split(",");
					}else{
						this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.push(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype);
					}
					for(var i =0;i<this.respiratorySystemObj.dropDowns.treatmentAction.length;i++){
						if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype.includes(this.respiratorySystemObj.dropDowns.treatmentAction[i].key)){
							this.respiratorySystemObj.dropDowns.treatmentAction[i].isApneaFalg = true;
						}
					}
				}
			};
    }
  silverman = function() {
    console.log("silverman initiated");
    $("#silvermanOverlay").css("display", "block");
    $("#silvermanScorePopup").addClass("showing");
  };
//	code for close the Silverman Popup
  closeModalSilverman = function(){
    console.log("silverman closing");
    $("#silvermanOverlay").css("display", "none");
    $("#silvermanScorePopup").toggleClass("showing");
  };
  closeModalSilvermanOnSave = function(){
    this.closeModalSilverman();
    this.totalSilvermanScore = this.silvermanScoreTotalGrade;
    this.creatingProgressNotesTemplate();
  };
//		below code is used to calculate the value of silverman score
  totalSilverValue = function(silverValue){
    if(silverValue == "upperChest"){
      this.totalUpperChest = this.UpperChest;
    }if(silverValue == "lowerChest"){
      this.totalLowerChest = this.LowerChest;
    }if(silverValue == "xiphoid"){
      this.totalXiphoid = this.XiphoidRetract;
    }if(silverValue == "naras"){
      this.totalNaras = this.NarasDilat;
    }if(silverValue == "expir"){
      this.totalExpir = this.ExpirGrunt;
    }
    this.SilverValueCalculated = this.totalUpperChest + this.totalLowerChest + this.totalExpir + this.totalNaras + this.totalXiphoid;
    this.silvermanScoreTotalGrade = this.SilverValueCalculated;
  }
  createMedInstruction = function(item){
		if(item!=null){
			this.saveTreatmentRds('rdsAntibiotics');
			this.saveTreatmentPphn("antibiotic");
			this.saveTreatmentPphn("inotropes");
			this.saveTreatmentPphn("sedation");
			var dose = null;
			if(this.respiratorySystemObj.workingWeight != null){
				if(item.dose!=null){
					dose = item.dose * this.respiratorySystemObj.workingWeight;
					if(item.frequency != null
							&& item.frequency != "") {
						for(var i=0; i < this.respiratorySystemObj.dropDowns.medicineFrequency.length; i++) {
							var item = this.respiratorySystemObj.dropDowns.medicineFrequency[i];
							if(item.freqid == item.frequency){
								if(item.freqvalue != "Other") {
									dose /= item.frequency_int;
								}
							}
						}
					}
					dose = Math.round(dose * 100) / 100;
				}else{
					dose="---";
				}
			}else{
				dose = Math.round(item.dose * 100) / 100;
			}
			item.calculateddose = item.medicinename + ": " + dose + " mg/dose";
		}
	}
  indcreaseEpisodeNumber = function(list){
		var rdsTime = new Date();
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime != undefined
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime != null) {
			if(typeof this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime === 'number') {
				rdsTime = new Date(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime);
			} else {
				rdsTime = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime;
			}
		}
		var apneaTime = new Date();
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime != undefined
				&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime != null) {
			if(typeof this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime === 'number') {
				apneaTime = new Date(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime);
			} else {
				apneaTime = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime;
			}
		}
		var pphnTime = new Date();
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime != undefined
				&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime != null) {
			if(typeof this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime === 'number') {
				pphnTime = new Date(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime);
			} else {
				pphnTime = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime;
			}
		}
		var pneumoTime = new Date();
		if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime != undefined
				&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime != null) {
			if(typeof this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime === 'number') {
				pneumoTime = new Date(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime);
			} else {
				pneumoTime = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime;
			}
		}
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.episodeNumber = 1;
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.episodeNumber = 1;
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.episodeNumber = 1;
		this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.episodeNumber = 1;
		if(list != undefined && list != null){
			for(var index=0;index<list.length;index++){
				var obj = list[index];
				if(obj.eventstatus=='Inactive'){
					if(obj.event=="Respiratory Distress" && obj.creationtime <= rdsTime.getTime()){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.episodeNumber++;
					}else if(obj.event=="Apnea" && obj.creationtime <= apneaTime.getTime()){
						this.respiratorySystemObj.respSystemObject.apnea.currentApnea.episodeNumber++;
					}else if(obj.event=="PPHN" && obj.creationtime <= pphnTime.getTime()){
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.episodeNumber++;
					}else if(obj.event=="Pneumothorax" && obj.creationtime <= pneumoTime.getTime()){
						this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.episodeNumber++;
					}else if(obj.event=="Others"){
						this.assessmentTempObj.othersEpisodeNumber++;
					}
				}
			}
		}
		return 	this.assessmentTempObj;
	}
  composePresStartTime = function (item,id) {
		console.log('composePresStartTime');
		console.log(id);
		if(item.startdate != undefined && item.startdate != null){
			var startDate = new Date(item.startdate);
			startDate.setHours(0,0,0,0);
			var doa = new Date(this.childObject.admitDate);
			doa.setHours(0,0,0,0);
			var element = document.getElementById(id);
			if (doa <= startDate) {
				if (element) {
					  element.style.display = 'none';
					}
				this.valid = true;
				if(item.startTimeObject == null) {
					item.startTimeObject = {};
				}
				if(item.startTimeObject.hours != null && item.startTimeObject.minutes != null && item.startTimeObject.meridium != null) {
					item.starttime = item.startTimeObject.hours + ":" + item.startTimeObject.minutes + " " + item.startTimeObject.meridium;
					if(item.startdate != null) {
						item.startdate.setMinutes(item.startTimeObject.minutes * 1);
						if(item.startTimeObject.meridium == 'AM') {
							if(item.startTimeObject.hours * 1 == 12) {
								item.startdate.setHours(0);
							} else {
								item.startdate.setHours(item.startTimeObject.hours * 1);
							}
						} else {
							if(item.startTimeObject.hours * 1 == 12) {
								item.startdate.setHours(12);
							} else {
								item.startdate.setHours((item.startTimeObject.hours * 1) + 12);
							}
						}
					}
				}
			} else {
				if (element) {
					  element.style.display = 'block';
					  if(item.startTimeObject != undefined
								&& item.startTimeObject != null){
							  item.startTimeObject.hours = "";
							  item.startTimeObject.minutes = "";
							  item.startTimeObject.meridium = "";
					  }
					}
			}
		}
	}

  ageOnsetValidationEpisode = function(diseaseName) {
		// show message for validation for current age at onset, less than previous inactive episode
		// diseaseName =  rds , apnea, pphn, pneumothorax
		if(this.respiratorySystemObj.respSystemObject.pastTableList.length>0){
			if(diseaseName!=null && diseaseName!=''){
				switch(diseaseName)
				{
				case 'rds':
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0)
					{
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus=='Inactive'){
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays==this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].ageinhoursdays){
								if(parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset)<=parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays==true && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].ageinhoursdays==false){
									diff = parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset) - parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].ageatonset)*24;
								} else{
									diff = parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset)*24 - parseInt(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].ageatonset);
								}
								if(diff<=0)
									{this.ageOnsetPreviousExceed = true;}
								else
									{this.ageOnsetPreviousExceed = false;}
							}
						}
						else
							{this.ageOnsetPreviousExceed = false;}
					}
					else
						this.ageOnsetPreviousExceed = false;
					break;

				case 'apnea':
					if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0)
					{
						if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus=='Inactive'){
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours==this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].ageinhours){
								if(parseInt(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset)<=parseInt(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours==true && this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].ageinhours==false){
									diff = parseInt(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset) - parseInt(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].ageatonset)*24;
								} else{
									diff = parseInt(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset)*24 - parseInt(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].ageatonset);
								}
								if(diff<=0)
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
						}
						else
							this.ageOnsetPreviousExceed = false;
					}
					else
						this.ageOnsetPreviousExceed = false;
					break;
				case 'pphn':
					if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0)
					{
						if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus=='Inactive'){
							if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays==this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].ageinhoursdays){
								if(parseInt(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset)<=parseInt(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays==true && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].ageinhoursdays==false){
									diff = parseInt(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset) - parseInt(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].ageatonset)*24;

								} else{
									diff = parseInt(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset)*24 - parseInt(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].ageatonset);
								}
								if(diff<=0)
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
						}
						else
							this.ageOnsetPreviousExceed = false;
					}
					else
						this.ageOnsetPreviousExceed = false;
					break;
				case 'pneumothorax':
					if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0)
					{
						if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus=='Inactive'){
							if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays==this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].ageinhoursdays){
								if(parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset)<=parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays==true && this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].ageinhoursdays==false){
									diff = parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset) - parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].ageatonset)*24;

								} else{
									diff = parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset)*24 - parseInt(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].ageatonset);
								}
								if(diff<=0)
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
						}
						else
							this.ageOnsetPreviousExceed = false;
					}
					else
						this.ageOnsetPreviousExceed = false;
					break;
				case 'others':
					if(this.respiratorySystemObj.respSystemObject.others.pastOtherList.length>0)
					{
						if(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].eventstatus=='Inactive'){
							if(this.respiratorySystemObj.respSystemObject.others.currentOther.ageinhoursdays==this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].ageinhoursdays){
								if(parseInt(this.respiratorySystemObj.respSystemObject.others.currentOther.ageatonset)<=parseInt(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].ageatonset))
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
							else {
								var diff = 0;
								if(this.respiratorySystemObj.respSystemObject.others.currentOther.ageinhoursdays==true && this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].ageinhoursdays==false){
									diff = parseInt(this.respiratorySystemObj.respSystemObject.others.currentOther.ageatonset) - parseInt(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].ageatonset)*24;
								} else{
									diff = parseInt(this.respiratorySystemObj.respSystemObject.others.currentOther.ageatonset)*24 - parseInt(this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].ageatonset);
								}
								if(diff<=0)
									this.ageOnsetPreviousExceed = true;
								else
									this.ageOnsetPreviousExceed = false;
							}
						}
						else
							this.ageOnsetPreviousExceed = false;
					}
					else
						this.ageOnsetPreviousExceed = false;
					break;
				}
			}
		}
		console.log("message set"+this.ageOnsetPreviousExceed );
	}
  calculateRespSystemDateTime = function(){
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate = this.RDSTempObj.assessmentDate ;
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate==null || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate==undefined){
			console.log("date selected error");
			return;
		}
		var currentAgeData = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime = currentAgeData;
	}

  setTodayAssessment = function(date){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = date;
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate = todayDate;
		this.ApneaTempObj.assessmentDate = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate;
	}

  calculateAgeAtAssessmentNewApnea = function(){
		console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate);
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate = this.ApneaTempObj.assessmentDate ;
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate==null || this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate==undefined){
			console.log("date selected error");
			return;
		}

		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt")

		var timeBirthArray = this.childObject.timeOfBirth.split(',');
		var birthHour = parseInt(timeBirthArray[0]);
		var birthMin = parseInt(timeBirthArray[1]);
		/*
		 * 12,00,AM or 00,00,AM are same, ie. 0000Hrs in 24Hr
		 * 12,00,PM or 00,00,PM are same, ie. 1200Hrs in 24Hr
		 */
		if(timeBirthArray[2]=='AM'){
			if(birthHour==12)
				this.dateBirth.setHours(0);
			else
				this.dateBirth.setHours(birthHour);
		}else{
			if(birthHour==12)
				this.dateBirth.setHours(12);
			else
				this.dateBirth.setHours(birthHour+12);
		}
		this.dateBirth.setMinutes(birthMin);

		var currentAgeData = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate;
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentTime = currentAgeData;

		this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours == true)
			{this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment = Math.round(parseInt(this.diffTime)/(1000*60*60)+1);}
		else
			{this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment = Math.round(parseInt(this.diffTime)/(1000*60*60*24)+0);}

		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment);
		//for episodes number.........
		this.assessmentTempObj = this.indcreaseEpisodeNumber(this.respiratorySystemObj.respSystemObject.pastTableList);
		this.creatingProgressNotesApneaTemplate();
	}

  setTodayAssessmentRDS = function(date){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = date;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate = todayDate;
		this.RDSTempObj.assessmentDate = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate;
	}
  calAgeOnsetPphn = function(changedParam) {
			if(changedParam=='meridian'){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays){
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset *= 24;
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset += this.hourDayDiff;
				}else if(!(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays)){
					this.hourDayDiff = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset%24;
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset -= this.hourDayDiff;
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset /= 24;
				}
			}else{
				this.hourDayDiff = 0;
			}
			this.creatingProgressNotesPphnTemplate();
		}
  calculateAgeAtAssessmentNewRDS = function(){
		console.log(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate);
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate = this.RDSTempObj.assessmentDate ;
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate==null || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate==undefined){
			console.log("date selected error");
			return;
		}

		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");

		var timeBirthArray = this.childObject.timeOfBirth.split(',');
		var birthHour = parseInt(timeBirthArray[0]);
		var birthMin = parseInt(timeBirthArray[1]);
		/*
		 * 12,00,AM or 00,00,AM are same, ie. 0000Hrs in 24Hr
		 * 12,00,PM or 00,00,PM are same, ie. 1200Hrs in 24Hr
		 */
		if(timeBirthArray[2]=='AM'){
			if(birthHour==12)
				this.dateBirth.setHours(0);
			else
				this.dateBirth.setHours(birthHour);
		}else{
			if(birthHour==12)
				this.dateBirth.setHours(12);
			else
				this.dateBirth.setHours(birthHour+12);
		}
		this.dateBirth.setMinutes(birthMin);

		var currentAgeData = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentDate;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.assessmentTime = currentAgeData;
		this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours)
			{this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment = Math.round(parseInt(this.diffTime)/(1000*60*60)+1);}
		else
			{this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment = Math.round(parseInt((this.diffTime))/(1000*60*60*24)+0);}

		//for episodes number.........
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment);
		this.assessmentTempObj = this.indcreaseEpisodeNumber(this.respiratorySystemObj.respSystemObject.pastTableList);
		this.creatingProgressNotesTemplate();
	}

	//------------------- time of assessment new rds -----------------------------------------------------

  //------------------- time of assessment new pphn -----------------------------------------------------

	setTodayAssessmentPPHN = function(date){
		// setting today date via JS to manual assessment
		// date,hour,min,meridiem
		var todayDate = date;
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate = todayDate;
		this.PphnTempObj.assessmentDate = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate;
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate;
	}
  calculateAgeAtAssessmentNewPPHN = function(){
		console.log(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate);
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate = this.PphnTempObj.assessmentDate;
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate==null || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate==undefined){
			console.log("date selected error");
			return;
		}
		this.dateBirth = this.childObject.dob;
		this.dateBirth = this.dateformatter(this.dateBirth,"utf","gmt");
		var timeBirthArray = this.childObject.timeOfBirth.split(',');
		var birthHour = parseInt(timeBirthArray[0]);
		var birthMin = parseInt(timeBirthArray[1]);
		/*
		 * 12,00,AM or 00,00,AM are same, ie. 0000Hrs in 24Hr
		 * 12,00,PM or 00,00,PM are same, ie. 1200Hrs in 24Hr
		 */
		if(timeBirthArray[2]=='AM'){
			if(birthHour==12)
				this.dateBirth.setHours(0);
			else
				this.dateBirth.setHours(birthHour);
		}else{
			if(birthHour==12)
				this.dateBirth.setHours(12);
			else
				this.dateBirth.setHours(birthHour+12);
		}
		this.dateBirth.setMinutes(birthMin);

		var currentAgeData = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentDate;
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.assessmentTime = currentAgeData;
		this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours == true)
			{this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment = Math.round(parseInt(this.diffTime)/(1000*60*60)+1);}
		else
			{this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment = Math.round((this.diffTime/(1000*60*60*24))+0);}

		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment);
		//for episodes number.........
		this.assessmentTempObj = this.indcreaseEpisodeNumber(this.respiratorySystemObj.respSystemObject.pastTableList);
		this.creatingProgressNotesPphnTemplate();
	}
		//------------------- time of assessment new pphn -----------------------------------------------------

  differenceInDaysFunc = function (dateObj) { return this.differenceInDays(dateObj); }

  populateTime = function() {
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.respSupportTime = new Date().getTime();
	};
  calAgeOnsetRds = function() {
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays && this.rdsHourFlag){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset *= 24;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset += this.hourDayDiffRds;
			this.rdsHourFlag = false;
		}else if(!(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays || this.rdsHourFlag)){
			this.rdsHourFlag = true;
			this.hourDayDiffRds = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset%24;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset -= this.hourDayDiffRds;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset /= 24;
		}
		this.creatingProgressNotesTemplate();
	}
  calAgeOnset = function() {
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours && this.apneaHourFlag){
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset *= 24;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset += this.hourDayDiff;
			this.apneaHourFlag = false;
		} else if(!(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours || this.apneaHourFlag)){
			this.apneaHourFlag = true;
			this.hourDayDiff = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset%24;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset -= this.hourDayDiff;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset /= 24;
		}
		this.creatingProgressNotesApneaTemplate();
	}
  calPrePostDiff =function() {
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal != ""
			&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPostductal != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPostductal != ""){
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference = (this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal - this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPostductal).toString();
		}
		else
			{this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference = "";}
		this.creatingProgressNotesPphnTemplate();
	}
  createMedicinesNotesText = function(medicinesList){
		var medicineObj  = {
				"medicineSelectedStr" : "",
				"notesStr" : "",
				"selectedStr" : "",
		};
		if(medicinesList != null && medicinesList != undefined && medicinesList != ""){
			for(var indexMed =0; indexMed<medicinesList.length;indexMed++){
				if((medicinesList[indexMed].medicinename != null && medicinesList[indexMed].medicinename != undefined && medicinesList[indexMed].medicinename != "")
						&& (medicinesList[indexMed].dose != null && medicinesList[indexMed].dose != undefined && medicinesList[indexMed].dose != "")
						&& (medicinesList[indexMed].route != null && medicinesList[indexMed].route != undefined && medicinesList[indexMed].route != "")){
					var currentFrequency = "";
					if(medicinesList[indexMed].frequency != null && medicinesList[indexMed].frequency != undefined && medicinesList[indexMed].frequency != "") {
						for(var indexFreq=0;indexFreq<this.respiratorySystemObj.dropDowns.medicineFrequency.length;indexFreq++){
							if(medicinesList[indexMed].frequency==
								this.respiratorySystemObj.dropDowns.medicineFrequency[indexFreq].freqid){
								currentFrequency = this.respiratorySystemObj.dropDowns.medicineFrequency[indexFreq].freqvalue;
							}
						}
					}
					if(medicineObj.medicineSelectedStr==""){
						medicineObj.medicineSelectedStr = medicinesList[indexMed].medicinename
						+" "+Math.round(medicinesList[indexMed].dose*this.respiratorySystemObj.workingWeight*1*100)/100+" mg"
						+" "+medicinesList[indexMed].route+" "+currentFrequency;
					}else{
						medicineObj.medicineSelectedStr = medicineObj.medicineSelectedStr +", "+medicinesList[indexMed].medicinename
						+" "+Math.round(medicinesList[indexMed].dose*this.respiratorySystemObj.workingWeight*1*100)/100+" mg"
						+" "+medicinesList[indexMed].route+" "+currentFrequency;
					}
				}
			}
			if(medicineObj.medicineSelectedStr!=""){
				if(medicineObj.medicineSelectedStr.indexOf(",")==-1){
					medicineObj.notesStr = "Antibiotic ("+medicineObj.medicineSelectedStr+") is started. ";
				}else{
					medicineObj.notesStr = "Antibiotics ("+medicineObj.medicineSelectedStr+") are started. ";
				}
				if(medicineObj.selectedStr==''){
					medicineObj.selectedStr = "Antibiotic  ("+medicineObj.medicineSelectedStr+")";
				}else{
					medicineObj.selectedStr = ", Antibiotic  ("+medicineObj.medicineSelectedStr+")";
				}
			}
		}
		return medicineObj;
	}
	creatingProgressNotesPphnTemplate = function() {
		var template = "";
		if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn==null || this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length<=0 ||
				this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus=='Inactive'){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.episodeNumber != null){
				template += "Episode number: "+ this.respiratorySystemObj.respSystemObject.pphn.currentPphn.episodeNumber + ". ";
			}
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageinhoursdays){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset*1 == 0){
					template += "Baby diagnosed as PPHN at the age of "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset + " hr. ";
				}else{
					template += "Baby diagnosed as PPHN at the age of "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset + " hrs. ";
				}
			}else{
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset*1 == 0){
					template += "Baby diagnosed as PPHN at the age of "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset + " day. ";
				}else{
					template += "Baby diagnosed as PPHN at the age of "+ this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatonset +" days. ";
				}
			}
		}else{
			template += "Baby continues to have PPHN. ";
		}
    //riskFactor
    if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList!=null
        && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList.length>0){
      this.riskPphnStr ="";
      for(var index=0; index<this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList.length;index++){
        for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.riskFactorPphn.length;indexDrop++){
          if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList[index] ==
            this.respiratorySystemObj.dropDowns.riskFactorPphn[indexDrop].key){
            if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList[index]=='otherRisk'
              && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther!=null){
              if(this.riskPphnStr==''){
                this.riskPphnStr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther;
              }else{
                this.riskPphnStr +=", " + this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther;
              }
            }else{
              if(this.riskPphnStr==""){
                this.riskPphnStr = this.respiratorySystemObj.dropDowns.riskFactorPphn[indexDrop].value;
              }else{
                this.riskPphnStr = this.riskPphnStr +", "+ this.respiratorySystemObj.dropDowns.riskFactorPphn[indexDrop].value;
              }
            }
          }
        }
      }
      if(this.riskPphnStr!=''){
        if(this.riskPphnStr.indexOf(",")==-1){
          template = template +" with the risk factor "+this.riskPphnStr.trim();
        }else{
          template = template +" with risk factors "+this.riskPphnStr.trim();
        }
      }
    } else {
      this.riskPphnStr ="";
    }
    template+=". ";
    //Downe score
		if(this.totalDownyScore!=null && this.totalDownyScore!=''){
			template = template + "Downes' score is "+this.totalDownyScore +". ";
		}
		var tempLabileOXyegen = "";
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileOxygenation!=null){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileOxygenation){
				tempLabileOXyegen +="Baby has labile oxygenation ";
				template +="Baby has labile oxygenation ";
			}
		}
		var labileDifference = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference;
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference!=null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference!=''){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal!=null){
				if(tempLabileOXyegen!='')
					template+="with Pre Ductal SpO2 "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal+" %, ";
				else
					template+="Pre Ductal SpO2 "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPreductal+" %, ";
			}
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPostductal!=null){
				template+="with Post Ductal SpO2 "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labPostductal+" %, ";
			}
			template+="( Difference "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.labileDifference+" %). ";
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pulmonaryPressure!=null){
			template+=", Pulmonary pressure (TR Gradient) is "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pulmonaryPressure+" mmHg ";
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.systolicBp!=null){
			template+=", Systemic systolic pressure is "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.systolicBp+" mmHg ";
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.oxgenationIndex!=null){
			template+="and oxygenation index (OI) of "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.oxgenationIndex+" mmHg. ";
		}
		if(this.orderSelectedText != ""){
			if(this.orderSelectedText.indexOf(",")==-1){
				template = template +"Investigation ordered is "+this.orderSelectedText+". ";
			}else{
				template = template +"Investigation ordered are "+this.orderSelectedText+". ";
			}
		}
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText = "";
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.indexOf("TRE009")!=-1){
				var tempText = "" ;
				if((this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname != "") &&
						(this.PphnTempObj.surfactantDoseMlKg != null && this.PphnTempObj.surfactantDoseMlKg != undefined && this.PphnTempObj.surfactantDoseMlKg != "")){
					tempText += " Surfactant ("+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname +") ";
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose!=null){
						tempText += this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose+" ml ";
					}
					tempText += "given";
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone!=null
							&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null &&
							this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=''){
						if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone){
							tempText += " and insure to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType+" ";
						}else{
							tempText += " and continue to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType+" ";
						}
					}else{
						tempText += ", ";
					}
				}
				template += tempText+ ".";
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText = tempText+" ";
			}
			var respSupportTextInitial = "";
			if(this.isRespSavedPphn!=null && this.isRespSavedPphn==true &&   this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=""){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.indexOf("TRE009")!=-1
						&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone!=null){
					respSupportTextInitial += "";
				}else{
					if(this.PphnTempObj.pphnTreatmentStatus!=null){
						if(this.PphnTempObj.pphnTreatmentStatus=='continue')
							respSupportTextInitial += "Treatment continued to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType
							+"";
						else
							respSupportTextInitial += "Treatment changed to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType
							+"";
					}else{
						respSupportTextInitial += "Treatment given is "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType
						+"";
					}
				}
				var respStr = "";
				respStr += "Mode: "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType + ", ";
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
					respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
          if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
            respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
          } else {
            respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
          }
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
					respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
					respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
					respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
					respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
					respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
					respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
					respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
					respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
					respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
					respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
				}
				if(respStr !=""){
					respStr = respStr.substring(0, (respStr.length - 2));
					if(template==''){
						template += respSupportTextInitial+" (" +respStr+ "). ";
					}else{
						template += respSupportTextInitial+" (" +respStr+ "). ";
					}
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText==''){
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += respSupportTextInitial+" ("+respStr+")";
					}else{
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += respSupportTextInitial+" ("+respStr+")";
					}
				}
			}
			if(this.respiratorySystemObj.respSystemObject.respSupport.isactive!=null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive==true
					&& this.respiratorySystemObj.respSystemObject.respSupport.eventid!=null
					&& (this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList==null
							||this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.indexOf("TRE010")==-1)){
				template += "Baby was removed from Respiratory Support. ";
			}
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.indexOf("TRE013")!=-1){
				var tempText = "Pulmonary Vasodilators (TR Gradient) given with "
					var tempVarText = "";
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnIno!=null){
					this.inoDoseMessage = "";
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnIno*1 <1 || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnIno*1 >50){
						this.inoDoseMessage = "Range 1-50";
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnIno =null;
					}else{
						tempVarText += "iNO " + this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnIno+" PPM ";
					}
				}
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.methaemoglobinLevel!=null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.methaemoglobinLevel!=""
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.methaemoglobinLevel!="NA" && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.methaemoglobinLevel!="na"){
					tempVarText += ", Methaemoglobin Level " + this.respiratorySystemObj.respSystemObject.pphn.currentPphn.methaemoglobinLevel + " g/dL ";
				}
				/*+ this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.frequency*/
				var currentFrequency = "";
				for(var indexFreq=0;indexFreq<this.respiratorySystemObj.dropDowns.medicineFrequency.length;indexFreq++){
					if(this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.frequency==
						this.respiratorySystemObj.dropDowns.medicineFrequency[indexFreq].freqid){
						currentFrequency = this.respiratorySystemObj.dropDowns.medicineFrequency[indexFreq].freqvalue;
					}
				}
				if((this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose != null && this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose != undefined && this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose != "")
						&& (this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route != null && this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route != undefined && this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route != "")
            && (currentFrequency != null && currentFrequency != undefined && currentFrequency!='')) {
					tempVarText += ", Sildenafil " + " ("+ this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose + " mg/kg "
					+ this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route+" "+currentFrequency+") ";
				}
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.otherPulmonaryvasodialotrs!=null){
					tempVarText += " ,"+ this.respiratorySystemObj.respSystemObject.pphn.currentPphn.otherPulmonaryvasodialotrs + ". ";
				}
				template += tempText + tempVarText;
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText==''){
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += tempText + tempVarText;
				}else{
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += ", "+tempText + tempVarText;
				}
			}
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.indexOf("Other")!=-1){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther != ""){
					this.PphnTempObj.isOtherSavedPphn = true;
					this.PphnTempObj.isOtherSelectPphn = true;
					template += this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther;
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText==''){
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += ""+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther;
					}else{
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += ", "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther;
					}
				}
			}
		}
    if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr != '') {
      template += this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr + ". ";
      if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText==''){
        this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += ""+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr;
      }else{
        this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText += ", "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr;
      }
    }
		//for plan to reassess the baby
		var planAddedd = false;
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime!=null &&
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime!=""
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestimeType!=null){
			var planAddedd = true;
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestimeType=='Mins'){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime* 1 == 1){
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" Min "
				}else{
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" Mins ";
				}
			}else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestimeType=='Hours'){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime* 1 == 1){
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" hour "
				}else{
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" hours ";
				}
			} else {
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime* 1 == 1){
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" day "
				}else{
					template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pphn.currentPphn.reassestime +" days ";
				}
			}
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.othercomments != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.othercomments != ""){
			if(planAddedd==true){
				template += "and " + this.respiratorySystemObj.respSystemObject.pphn.currentPphn.othercomments + ". ";
			}else{
				template += this.respiratorySystemObj.respSystemObject.pphn.currentPphn.othercomments + ". ";
			}
		}else if(planAddedd==true){
			template += ".";
		}
		this.causeOfPphnStr = "";
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList!=null
				&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList.length>0){
			for(var index=0; index<this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList.length;index++){
				for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.causeOfPphn.length;indexDrop++){
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfPphn[indexDrop].key){
						if(this.causeOfPphnStr==""){
							this.causeOfPphnStr = this.respiratorySystemObj.dropDowns.causeOfPphn[indexDrop].value;
						}else{
							this.causeOfPphnStr += ", " + this.respiratorySystemObj.dropDowns.causeOfPphn[indexDrop].value;
						}
					}
				}
			}
			if(this.causeOfPphnStr.includes('Other') && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphnOther!=null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphnOther!="") {
				this.causeOfPphnStr = this.causeOfPphnStr.replace('Other', this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphnOther);
			}

			if(this.causeOfPphnStr!=''){
				if(this.causeOfPphnStr.indexOf(",")==-1){
					template = template +" "+this.causeOfPphnStr+" is the most likely cause of the PPHN.";
				}else{
					template = template +" "+this.causeOfPphnStr+" are the most likely cause of the PPHN.";
				}
			}
		}else{
			this.causeOfPphnStr ="";
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.associatedevent!= null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.associatedevent!="") {
			template += " Associated Event is "+ this.respiratorySystemObj.respSystemObject.pphn.currentPphn.associatedevent+".";
		}
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.progressnotes = template;
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus=='Yes')
			this.ageOnsetValidationEpisode('pphn');
	}
  switchPastMedTablePPHN = function() {
		if(this.otherMedTablePPHNFlag) {
			this.otherMedTablePPHNText = "View Past Medication";
		} else {
			this.otherMedTablePPHNText = "Hide Past Medication";
		}
		this.otherMedTablePPHNFlag = !this.otherMedTablePPHNFlag;
	}
	switchPastMedTableRDS = function() {
		if(this.otherMedTableRDSFlag) {
			this.otherMedTableRDSText = "View Past Medication";
		} else {
			this.otherMedTableRDSText = "Hide Past Medication";
		}
		this.otherMedTableRDSFlag = !this.otherMedTableRDSFlag;
	}
  switchEventApnea = function() {
		this.TableApneaFlag = !this.TableApneaFlag;
	}
	switchPastMedTable = function() {
		if(this.otherMedTableFlag) {
			this.otherMedTableText = "View Past Medication";
		} else {
			this.otherMedTableText = "Hide Past Medication";
		}
		this.otherMedTableFlag = !this.otherMedTableFlag;
	}

  creatingProgressNotesApneaTemplate = function(){
		var template = "";
		if(!this.pastAgeInactiveApnea){
			console.log("in treatment visit is 1");
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = "Start";
		}
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus == "No") {
			console.log("in apnea progress notes event is no");
			template = "Baby has no Episode of apnea.";
		} else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus == "Inactive") {
			console.log("in apnea progress notes event is inactive");
		} else {
			console.log("in progress notes=====================");
			if(!this.pastAgeInactiveApnea) {
				if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea==null || this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length<=0 ||
						this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus=='Inactive'){
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.episodeNumber != null){
						template += "Episode number: " + this.respiratorySystemObj.respSystemObject.apnea.currentApnea.episodeNumber + ". ";
					}
				}
				template += "Baby developed Apnea at the age of ";
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageinhours){
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset*1 == 0){
						template += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset +" hr";
					}else{
						template += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset +" hrs";
					}
				}else{
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset*1 == 0){
						template += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset +" day";
					}else{
						template += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatonset +" days";
					}
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.pastNursingEpisode != null
						&& this.respiratorySystemObj.respSystemObject.apnea.pastNursingEpisode.length>0) {
					var lastEvent = this.respiratorySystemObj.respSystemObject.apnea.pastNursingEpisode[0];
					var str = "";
					if(null != lastEvent.disaturation && lastEvent.disaturation) {
						str = ", associated with Desaturation (SpO2 " + lastEvent.spo2 + " %)";
					}
					if(null != lastEvent.bradycardia && lastEvent.bradycardia) {
						if(str == "") {
							str = ", associated with bradycardia (HR " + lastEvent.hr + "/min)";
						} else {
							str += " & bradycardia (HR " + lastEvent.hr + "/min)";
						}
					}
					template += str + ". Recovery was " + lastEvent.recovery;
				}
			} else {
				//json obj variable is in minutes
				var timeStr = "";
        if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate != null && this.respiratorySystemObj.respSystemObject.apnea.pastApnea != null && this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length > 0){
          var pastassessmentDate = new Date (this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].assessmentTime);
          var assessmentNewDate = new Date(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.assessmentDate);
          var diffTime = (assessmentNewDate.getTime() - pastassessmentDate.getTime()) / (1000 * 60);
          diffTime = this.round(diffTime, 2);
          if(diffTime < 60){
            if(diffTime < 2){
              timeStr = "1 minute";
            }
            else {
  						timeStr = diffTime + " minutes";
  					}
          } else {
  					var mins = diffTime % 60;
  					var hrs = (diffTime - mins) / 60;
  					if(hrs == 1) {
  						timeStr = hrs + " hour";
  					} else {
              hrs = this.round(hrs, 2);
  						timeStr = hrs + " hours";
  					}
  				}
        }else{
  				if(this.respiratorySystemObj.respSystemObject.apnea.numberOfHours < 60) {
  					if(this.respiratorySystemObj.respSystemObject.apnea.numberOfHours < 2) {
  						timeStr = "1 minute";
  					} else {
  						timeStr = this.respiratorySystemObj.respSystemObject.apnea.numberOfHours + " minutes";
  					}
  				} else {
  					var mins = this.respiratorySystemObj.respSystemObject.apnea.numberOfHours % 60;
  					var hrs = (this.respiratorySystemObj.respSystemObject.apnea.numberOfHours - mins) / 60;
  					if(hrs == 1) {
  						timeStr = hrs + " hour";
  					} else {
  						timeStr = hrs + " hours";
  					}
  				}
        }
				console.log("min-hrs time: " + timeStr);
				if(this.respiratorySystemObj.respSystemObject.apnea.numberOfEpisode > 0) {
					template = "Baby had "+this.respiratorySystemObj.respSystemObject.apnea.numberOfEpisode + " episodes of  Apnea in last "
					+ timeStr + ", ";
					var str = "";
					if(this.respiratorySystemObj.respSystemObject.apnea.numberOfSpontaneous > 0) {
						str += this.respiratorySystemObj.respSystemObject.apnea.numberOfSpontaneous + " recovered spontaneously";
					}
					if(this.respiratorySystemObj.respSystemObject.apnea.numberOfStimulation > 0) {
						if(str == "") {
							str += this.respiratorySystemObj.respSystemObject.apnea.numberOfStimulation + " required stimulation";
						} else {
							if(this.respiratorySystemObj.respSystemObject.apnea.numberOfPPV > 0){
								str += ", " + this.respiratorySystemObj.respSystemObject.apnea.numberOfStimulation + " required stimulation";
							} else {
								str += ", and " + this.respiratorySystemObj.respSystemObject.apnea.numberOfStimulation + " required stimulation";
							}
						}
					}
					if(this.respiratorySystemObj.respSystemObject.apnea.numberOfPPV > 0) {
						if(str == "") {
							str += this.respiratorySystemObj.respSystemObject.apnea.numberOfPPV + " required PPV";
						} else {
							str += ", and " + this.respiratorySystemObj.respSystemObject.apnea.numberOfPPV + " required PPV";
						}
					}
					console.log("episode str: "+str);
					template += str;
				} else {
					template = "No episode of Apnea in past " + timeStr;
				}
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.apneaEvent) {
				var episodeStr ="";
				var tempDate = new Date();
				var merdian = "AM";
				var hours = tempDate.getHours();
				if(hours >= 12) {
					hours = hours - 12;
					merdian = "PM";
				}
				this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.nnVitalparameterTime=hours+":"+tempDate.getMinutes()+":"+ merdian;
				if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.bradycardia !=null
						&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.cyanosis !=null) {
					if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.bradycardia
							&&  this.respiratorySystemObj.respSystemObject.apnea.currentApnea.cyanosis) {
						episodeStr += " which was associated with Bradycardia and Clinical Cyanosis";
					} else if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.bradycardia) {
						episodeStr += " which was associated with Bradycardia, but not with Clinical Cyanosis";
					} else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.cyanosis) {
						episodeStr += " which was associated with Clinical Cyanosis, but not with Bradycardia";
					} else {
						episodeStr += " which was not associated with Bradycardia and Clinical Cyanosis";
					}
				} else if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.bradycardia !=null) {
					if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.bradycardia) {
						episodeStr += " which was associated with Bradycardia";
					} else {
						episodeStr += " which was not associated with Bradycardia";
					}
				} else if (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.cyanosis!=null) {
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.cyanosis) {
						episodeStr += " which was associated with Clinical Cyanosis";
					} else {
						episodeStr += " which was not associated with Clinical Cyanosis";
					}
				}
				template += episodeStr;
				if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.hr!=null) {
					template += ", minimum heart rate was "+this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.hr+" bpm";
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.spo2!=null) {
					template += ", minimum SpO2 is "+this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.spo2+"%";
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.disaturation!=null) {
					if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.disaturation) {
						if(episodeStr =="") {
							template += "which was associated with desaturation";
						} else {
							template += ", and associated with desaturation";
						}
					} else {
						if(episodeStr =="") {
							template += "which was not associated with desaturation";
						} else {
							template += ", and not associated with desaturation";
						}
					}
				}
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.recovery!=null){
				if(this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.recovery == "Spontaneous") {
					template += ". Recovery was "+this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.recovery;
				} else {
					template += ". Recovery by "+this.respiratorySystemObj.respSystemObject.apnea.currentEpisode.recovery;
				}
			} else {
				template += ". ";
			}
			if(this.orderSelectedText != ""){
				if(this.orderSelectedText.indexOf(",")==-1){
					template += ". Investigation ordered is " + this.orderSelectedText+".";
				}else{
					template += ". Investigation ordered are " + this.orderSelectedText+".";
				}
			}
			this.treatmentApneaSelectedText = "";
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList != null && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.length > 0){
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes("TRE007")){
					if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType != ""){
						var contChange = "";
						if(this.pastAgeInactiveApnea && this.pastRespSupportObj != undefined && this.pastRespSupportObj != null){
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == this.pastRespSupportObj.rsVentType) {
								contChange = "Treatment continued to ";
							} else {
								contChange = "Treatment changed to ";
							}
						} else {
							contChange = " Treatment given is ";
						}
						this.treatmentApneaSelectedText = contChange + this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
						var respStr = "";
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
							respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
              if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
                respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
              } else {
                respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
              }
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
							respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
							respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
							respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
							respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
							respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
							respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
							respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
							respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
							respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
							respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
						}
						if(respStr == ""){
							this.treatmentApneaSelectedText += ". ";
						} else {
							respStr = respStr.substring(0, (respStr.length - 2));
							this.treatmentApneaSelectedText += " (" + respStr + ")";
						}
					}
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes("TRE008")){
					var caffeineStr = "";
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction != null) {
						if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction == "Stop") {
							caffeineStr +=  this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction + " caffeine";
						} else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction == "Start") {
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute != null) {
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute == "Oral") {
									caffeineStr += "Caffeine is given orally" ;
								} else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute == "IV") {
									caffeineStr += "Caffeine is given intravenously";
								}
							}
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose != null) {
								caffeineStr += ", with bolus dose of "+ this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose +" mg/kg";
							}
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose != null) {
								caffeineStr += " and maintenance dose of "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose +" mg/kg";
							}
						} else if (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction == "Change") {
							if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute != null) {
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute == "Oral") {
									caffeineStr += "Caffeine is given orally" ;
								} else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute == "IV") {
									caffeineStr += "Caffeine is given intravenously";
								}
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose !=null) {
									caffeineStr += ", with bolus dose of "+ this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose +" mg/kg";
								}
								if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose !=null) {
									var incDec = "";
									if(this.apneaPreviousTreatment.caffeineMaintenanceDose*1 < this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose*1) {
										incDec = "increased to ";
									} else if(this.apneaPreviousTreatment.caffeineMaintenanceDose*1 > this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose*1) {
										incDec = "decreased to ";
									}
									caffeineStr += " and maintenance dose " + incDec + this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose +" mg/kg";
								}
							}
						} else {
							caffeineStr += "Caffeine maintenance dose continued";
      				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute = this.apneaPreviousTreatment.caffeineRoute;
      				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose = this.apneaPreviousTreatment.caffeineMaintenanceDose;
						}
					}//Caffeine maintenance dose increased to ___mg/kg
					if (this.treatmentApneaSelectedText != "") { //for text in selected column
						this.treatmentApneaSelectedText += ". " + caffeineStr;
					} else if (caffeineStr != "") {
						this.treatmentApneaSelectedText = caffeineStr;
					}
				}
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null
						&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.indexOf("other")!=-1
						&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentOther!=null){
					if(this.treatmentApneaSelectedText==null || this.treatmentApneaSelectedText==""){
						this.treatmentApneaSelectedText += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentOther;
					} else {
						this.treatmentApneaSelectedText += ", "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentOther;
					}
				}
				if (this.treatmentApneaSelectedText != "") {
					template += " " + this.treatmentApneaSelectedText;
				}
			}
			if(this.respiratorySystemObj.respSystemObject.respSupport.isactive!=null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive==true
					&& this.respiratorySystemObj.respSystemObject.respSupport.eventid!=null
					&& (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList==null
							|| this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.indexOf("TRE007")==-1)){
				template += ". Baby was removed from Respiratory Support";
			}
      if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr != undefined && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr != null && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr != '') {
        if (this.treatmentApneaSelectedText == "") {
          this.treatmentApneaSelectedText += this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr;
        } else if (this.treatmentApneaSelectedText != "") {
          this.treatmentApneaSelectedText = ", " + this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr;
        }
        template += " " + this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr + ".";
      }
			this.causeOfApneaStr ="";
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList!=null
					&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList.length>0){
				for(var index=0; index<this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList.length;index++){
					for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.causeOfApnea.length;indexDrop++){
						if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfApnea[indexDrop].key){
							if(this.causeOfApneaStr==""){
								this.causeOfApneaStr = this.respiratorySystemObj.dropDowns.causeOfApnea[indexDrop].value;
							}else{
								this.causeOfApneaStr = this.causeOfApneaStr +", "+ this.respiratorySystemObj.dropDowns.causeOfApnea[indexDrop].value;
							}
						}
					}
				}
				if(this.causeOfApneaStr.includes('Other') && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.causeofapneaOther!=null && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.causeofapneaOther!="") {
					this.causeOfApneaStr = this.causeOfApneaStr.replace('Other', this.respiratorySystemObj.respSystemObject.apnea.currentApnea.causeofapneaOther);
				}
				if(this.causeOfApneaStr!=''){
					if(this.causeOfApneaStr.indexOf(",")==-1){
						template += ". Most likely cause of Apnea is " + this.causeOfApneaStr;
					}else{
						template += ". Most likely cause of Apnea are " + this.causeOfApneaStr;
					}
				}
			}
			//for plan to reassess the baby
			var planAddedd = false;
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime!=null &&
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime!=""
						&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.action_plan_timetype!=null){
				var planAddedd = true;
				if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.action_plan_timetype=='Min'){
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime == 1) {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" Min";
					} else {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" Mins";
					}
				}else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.action_plan_timetype=='Hours'){
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime == 1) {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" hour";
					} else {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" hours";
					}
				} else {
					if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime == 1) {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" day";
					} else {
						template += ". Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanTime+" days";
					}
				}
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanComments != null
					&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanComments != ""){
				if(planAddedd==true){
					template += " and "+this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanComments+".";
				}else{
					template += ". " + this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actionPlanComments+".";
				}
			}else if(planAddedd==true){
				template += ".";
			}
		}
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment = template;
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment!=null){
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.includes('  ')) {
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.replace('  ',' ');
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.includes('. .')) {
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.replace('. .','. ');
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.includes('..')) {
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaComment.replace('..','. ');
			}
		}
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus=='Yes')
			this.ageOnsetValidationEpisode('apnea');
	}
  //		------------------------------------------code start for on change action after surfactant-----------------------------------------------//
		//for resp distress
	onPlanChangeRespDist = function(){
		var treatmentIndex = 0;
		for(var index=0; index<this.respiratorySystemObj.dropDowns.treatmentAction.length; index++){
			if(this.respiratorySystemObj.dropDowns.treatmentAction[index].key=="TRE005"){
				treatmentIndex = index;
				break;
			}
		}
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant=="INSURE to CPAP"){
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'CPAP';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.onCheckMultiCheckBoxValue("TRE005","treatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('Respiratory Distress', true);
			}
		} else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant=="INSURE to High Flow"){
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'High Flow O2';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.onCheckMultiCheckBoxValue("TRE005","treatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('Respiratory Distress', true);
			}
		} else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant=="Continue IMV"){
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'Mechanical Ventilation';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
				this.onCheckMultiCheckBoxValue("TRE005","treatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('Respiratory Distress', true);
			}
		}
		this.creatingProgressNotesTemplate();
	}
	//for pphn
	onPlanChangePphn = function(){
		var treatmentIndex = 0;
		for(var index=0; index<this.respiratorySystemObj.dropDowns.treatmentAction.length; index++){
			if(this.respiratorySystemObj.dropDowns.treatmentAction[index].key=="TRE010"){
				treatmentIndex = index;
				break;
			}
		}
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant=="INSURE to CPAP"){
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'CPAP';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.onCheckMultiCheckBoxValue("TRE010","pphnTreatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('PPHN', true);
			}
		} else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant=="INSURE to High Flow"){
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'High Flow O2';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.onCheckMultiCheckBoxValue("TRE010","pphnTreatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('PPHN', true);
			}
		} else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant=="Continue IMV"){
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = 'Mechanical Ventilation';
			}
			this.respiratorySystemObj.dropDowns.treatmentAction[treatmentIndex].flag = true;
			if(!this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')){
				this.onCheckMultiCheckBoxValue("TRE010","pphnTreatmentAction",true);
			} else {
				this.callRespiratorySupportPopUP('PPHN', true);
			}
		}
		this.creatingProgressNotesPphnTemplate();
	}
updateTubeStatus = function(tubeStatusType,side){
  if(side=="left"){
    if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes!=null
        && this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length){
      if(tubeStatusType=='observation'){
        this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[this.pneumothoraxTempObj.leftSelectedTubeValue].chestTubeAdjustedValue
        =null;
      }
    }
  }else if(side=='right'){
    if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes!=null
        && this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length>0){
      if(tubeStatusType=='observation'){
        this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[this.pneumothoraxTempObj.rightSelectedTubeValue].chestTubeAdjustedValue
        =null;
      }
    }
  }
  this.saveTreatmentPneumo('chestTube');
  this.creatingProgressNotesPneumoTemplate();
}
  creatingProgressNotesTemplate = function(){
		var template = "";
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress==null || this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length<=0 ||
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus=='Inactive'){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.episodeNumber != null){
				template += "Episode number: " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.episodeNumber + ". ";
			}
		}
		if(this.respiratorySystemObj.ageAtOnset!=null && this.respiratorySystemObj.ageAtOnset!=""){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0){
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus=='Inactive'){
					template += "Baby developed respiratory distress started at ";
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 0){
							template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " hr";
						}else{
							template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " hrs";
						}
					}else{
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 0){
							template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " day";
						}else{
							template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset +" days";
						}
					}
				} else {
					template += "Baby continues to have respiratory distress ";
				}
			} else {
				template += "Baby developed respiratory distress started at ";
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageinhoursdays){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 0){
						template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " hr";
					}else{
						template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " hrs";
					}
				}else{
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset*1 == 0){
						template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset + " day";
					}else{
						template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatonset +" days";
					}
				}
			}
			//riskFactor
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList.length>0){
				this.riskRespDistressStr ="";
				for(var index=0; index<this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList.length;index++){
					for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.riskFactorRds.length;indexDrop++){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList[index] == this.respiratorySystemObj.dropDowns.riskFactorRds[indexDrop].key){
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList[index]=='otherRisk'
								&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther!=null){
								if(this.riskRespDistressStr==''){
									this.riskRespDistressStr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther;
								}else{
									this.riskRespDistressStr +=", " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther;
								}
							}else{
								if(this.riskRespDistressStr==""){
									this.riskRespDistressStr = this.respiratorySystemObj.dropDowns.riskFactorRds[indexDrop].value;
								}else{
									this.riskRespDistressStr = this.riskRespDistressStr +", "+ this.respiratorySystemObj.dropDowns.riskFactorRds[indexDrop].value;
								}
							}
						}
					}
				}
				if(this.riskRespDistressStr!=''){
					if(this.riskRespDistressStr.indexOf(",")==-1){
						template = template +" with the risk factor "+this.riskRespDistressStr.trim();
					}else{
						template = template +" with risk factors "+this.riskRespDistressStr.trim();
					}
				}
			} else {
				this.riskRespDistressStr ="";
			}
			template+=". ";
			//write here code for downe's score..
			if(this.totalDownyScore!=null && this.totalDownyScore!=""){
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0
						&& this.respiratorySystemObj.respSystemObject.pastDownesScore!=null
						&& this.respiratorySystemObj.respSystemObject.pastDownesScore!=""){
					if(this.respiratorySystemObj.respSystemObject.pastDownesScore>this.totalDownyScore){
						template = template + "Downes' score decreased from "+this.respiratorySystemObj.respSystemObject.pastDownesScore+" to "+this.totalDownyScore +". ";
					} else if(this.respiratorySystemObj.respSystemObject.pastDownesScore<this.totalDownyScore){
						template = template + "Downes' score increased from "+this.respiratorySystemObj.respSystemObject.pastDownesScore+" to "+this.totalDownyScore +". ";
					} else if(this.respiratorySystemObj.respSystemObject.pastDownesScore==this.totalDownyScore){
						template = template + "Downes' score measured is "+this.totalDownyScore +". ";
					}
				} else {
					template = template + "Downes' score measured is "+this.totalDownyScore +". ";// With downe's score of 12.
				}
			}
			if(this.orderSelectedText != ""){
				if(this.orderSelectedText.indexOf(",")==-1){
					template = template +"Investigation ordered is "+this.orderSelectedText+". ";
				}else{
					template = template +"Investigation ordered are "+this.orderSelectedText+". ";
				}
			}
			if(this.rdsTreatmentStatus.value!=null && this.rdsTreatmentStatus.value!=''){
				if(this.respiratorySystemObj.respSystemObject.respSupport.isactive!=null
						&& this.respiratorySystemObj.respSystemObject.respSupport.isactive==true
						&& this.respiratorySystemObj.respSystemObject.respSupport.eventid!=null
						&& (this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null
								||this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.indexOf("TRE005")==-1)){
					template += "Baby was removed from Respiratory Support. ";
				}
			}
			this.treatmentSelectedText = "";
			var treatmentText = "";
			var respChanged = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.length>0){
				if(this.rdsTreatmentStatus.value=='Continue'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE005")){
						if(this.respiratorySystemObj.respSystemObject.respSupport!=null
								&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
							if(this.treatmentSelectedText==null || this.treatmentSelectedText==""){
								this.treatmentSelectedText += "Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							} else {
								this.treatmentSelectedText += ", Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							}
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=""){
							var respStr = "";
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null
									&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==false
									&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='HFO'){
								respStr += "Mode: "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType+", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
								respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
                if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
                  respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                } else {
                  respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                }
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
								respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
								respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
								respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
								respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
								respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
								respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
								respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
								respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
								respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
								respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsRate!="") {
								respStr += "Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsRate + " breaths/min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != null && this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != "") {
								respStr += "Backup Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate + " breaths/min, ";
							}
							if(respStr ==""){} else {
								respStr = respStr.substring(0, (respStr.length - 2));
								this.treatmentSelectedText += " (" + respStr + ")";
								template += "Treatment continued to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + "). ";
							}
						}
					}
				} else if(this.rdsTreatmentStatus.value=='Change'){
					var treatmentText = "";
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE004")){
						if(this.treatmentSelectedText!=null && this.treatmentSelectedText!=''){
							this.treatmentSelectedText += ", Surfactant";
						} else {
							this.treatmentSelectedText = "Surfactant";
						}
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname!=null
								&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=null){
							this.treatmentSelectedText += "("+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname +") "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose+" ml/kg";
							treatmentText += "Treatment changed to Surfactant "+"("+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname +") "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose+" ml/kg";
						}
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant!=null){
								if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==true
										&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
									treatmentText+=" and INSURE to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
								} else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==false){
									treatmentText+=" with continue MV ";
								}
							}
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE005")){
						if(this.respiratorySystemObj.respSystemObject.respSupport!=null
								&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
							if(this.treatmentSelectedText==null || this.treatmentSelectedText==""){
								this.treatmentSelectedText += "Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							} else {
								this.treatmentSelectedText += ", Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							}
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=""){
							var respStr = "";
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null
									&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==false
									&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='HFO'){
								respStr += "Mode: "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType+", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
								respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
                if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
                  respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                } else {
                  respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                }
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
								respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
								respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
								respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
								respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
								respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
								respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
								respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
								respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
								respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
								respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsRate!="") {
								respStr += "Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsRate + " breaths/min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != null && this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != "") {
								respStr += "Backup Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate + " breaths/min, ";
							}
							if(respStr ==""){} else {
								respStr = respStr.substring(0, (respStr.length - 2));
								this.treatmentSelectedText += " (" + respStr + ")";
								if(treatmentText==''){
									treatmentText += "Treatment changed to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + ")";
								} else {
									if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
										if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant!=null){
											treatmentText += " (" + respStr + ")";
										}
									} else {
										treatmentText += ", "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + ")";
									}
								}
							}
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE006")){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList!=null){
							var medicineObj = this.createMedicinesNotesText(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList);
							this.treatmentSelectedText += medicineObj.selectedStr;
							treatmentText += medicineObj.notesStr;
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.indexOf("other")!=-1 && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=''){
						if(this.treatmentSelectedText==null || this.treatmentSelectedText==""){
							this.treatmentSelectedText += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
						} else {
							this.treatmentSelectedText += ", "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
						}
						if(treatmentText==''){
							treatmentText += "Treatment changed to ";
						} else {
							treatmentText += ", ";
						}
						treatmentText += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
					}
					template+=treatmentText+". ";
				} else {
					var treatmentText = "";
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE004")){
						if(this.treatmentSelectedText!=null && this.treatmentSelectedText!=''){
							this.treatmentSelectedText += ", Surfactant";
						} else {
							this.treatmentSelectedText = "Surfactant";
						}
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname!=null
								&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=null){
							this.treatmentSelectedText += "("+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname +") "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose+" ml/kg";
							treatmentText += "Treatment given is Surfactant "+"("+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname +") "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose+" ml/kg";
						}
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant!=null){

								if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==true
										&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
									treatmentText+=" and INSURE to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;

								} else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==false){
									treatmentText+=" with continue MV ";
								}
							}
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE005")){
						if(this.respiratorySystemObj.respSystemObject.respSupport!=null
								&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
							if(this.treatmentSelectedText==null || this.treatmentSelectedText==""){
								this.treatmentSelectedText += "Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							} else {
								this.treatmentSelectedText += ", Resiratory Support "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
							}
						}
						if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=""){
							var respStr = "";
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null
									&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone==false
									&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='HFO'){
								respStr += "Mode: "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType+", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
								respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
                if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
                  respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                } else {
                  respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
                }
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
								respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
								respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
								respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
								respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
								respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
								respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
								respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
								respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
								respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
								respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsRate!="") {
								respStr += "Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsRate + " breaths/min, ";
							}
							if(this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != null && this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate != "") {
								respStr += "Backup Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate + " breaths/min, ";
							}
							if(respStr ==""){} else {
								respStr = respStr.substring(0, (respStr.length - 2));
								this.treatmentSelectedText += " (" + respStr + ")";
								if(treatmentText==''){
									treatmentText += "Treatment given is "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + ")";
								} else {
									if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
										if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.actionAfterSurfactant!=null){
											treatmentText += " (" + respStr + ")";
										}
									} else {
										treatmentText += ", "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + ")";
									}
								}
							}
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE006")){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList!=null){
							var medicineObj = this.createMedicinesNotesText(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList);
							this.treatmentSelectedText +=
								medicineObj.selectedStr;
							treatmentText += medicineObj.notesStr;
						}
					}
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.indexOf("other")!=-1
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=null
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=''){
						if(this.treatmentSelectedText==null || this.treatmentSelectedText==""){
							this.treatmentSelectedText += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
						} else {
							this.treatmentSelectedText += ", "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
						}
						if(treatmentText==''){
							treatmentText += "Treatment given is ";
						} else {
							treatmentText += ", ";
						}
						treatmentText += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther;
					}
					template+=treatmentText+". ";
				}
			}

      if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr != '') {
        template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr + ". ";
        if(this.treatmentSelectedText == null || this.treatmentSelectedText == ""){
          this.treatmentSelectedText += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr;
        } else {
          this.treatmentSelectedText += ", " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr;
        }
      }
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList!=null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.length>0){
				this.causeOfRespDistressStr ="";
				for(var index=0; index<this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.length;index++){
					for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.causeOfRds.length;indexDrop++){
						if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfRds[indexDrop].key){
							if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList[index]=='other'
								&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther!=null){
								if(this.causeOfRespDistressStr==''){
									this.causeOfRespDistressStr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther;
								}else{
									this.causeOfRespDistressStr +=", " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther;
								}
							}else{
								if(this.causeOfRespDistressStr==""){
									this.causeOfRespDistressStr = this.respiratorySystemObj.dropDowns.causeOfRds[indexDrop].value;
								}else{
									this.causeOfRespDistressStr = this.causeOfRespDistressStr +", "+ this.respiratorySystemObj.dropDowns.causeOfRds[indexDrop].value;
								}
							}
						}
					}
				}
				if(this.causeOfRespDistressStr!=''){
					if(this.causeOfRespDistressStr.indexOf(",")==-1){
						template = template +this.causeOfRespDistressStr+" is the most likely cause of the RDS. ";
					}else{
						var causeText = '';
						var causes = this.causeOfRespDistressStr.split(",");
						for(var index =0; index<causes.length; index++){
							if(causeText==''){
								causeText += causes[index].trim();
							} else {
								if(index==causes.length-1){
									causeText += " and "+causes[index].trim();
								} else {
									causeText += ", "+causes[index].trim();
								}
							}
						}
						template = template +causeText+" are the most likely cause of the RDS. ";
					}
				}
			}else{
				this.causeOfRespDistressStr ="";
			}
			//for plan to reassess the baby
			var planAddedd = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime!=null &&
					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime!=""
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reasseshoursdays!=null){
				planAddedd = true;
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reasseshoursdays=='Mins'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime*1 == 1){
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" Min ";
					}else{
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" Mins ";
					}
				}else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reasseshoursdays=='Hours'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime*1 == 1){
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" hour ";
					}else{
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" hours ";
					}
				} else {
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime*1 == 1){
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" day ";
					}else{
						template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.reassestime+" days ";
					}
				}
			}
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.othercomments!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.othercomments!=''){
				if(planAddedd==true){
					template += "and "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.othercomments+".";
				}else{
					template += this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.othercomments+".";
				}
			}else if(planAddedd==true){
				template += ".";
			}
			//silverman score is 32.
			if(this.totalSilvermanScore!=null && this.totalSilvermanScore!=""){
				template = template + "Silverman score is "+this.totalSilvermanScore +". ";// With downe's score of 12.
			}
		}
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.progressnotes = template;
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus=='Yes')
			{this.ageOnsetValidationEpisode('rds');}
	}
  getPrescription = function(){
		this.startTimeHH =null;
		this.startTimeMeridian = null;
		this.startTimeMM =null;
    try{
      this.http.request(this.apiData.getPrescription+ this.childObject.uhid + "/test",)
      .subscribe(res => {
        this.response = res.json();
        console.log("get prescription");
        console.log(this.response);
        this.respiratorySystemObj.respSystemObject.pastPrescriptionList = this.response.activePrescription;
      },
      err => {
        console.log("Error occured.")
      }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	}; //end of function

	//edit medication................
	editMedication = function(medicineObj,type){
		if(type=='editModeOn' && this.PneumoTempObj.tempPastPrescriptionList.length==0){
			this.PneumoTempObj.tempPastPrescriptionList = Object.assign({},this.respiratorySystemObj.respSystemObject.pastPrescriptionList);
		}
		var submitIndex = null;
		if(type=='editModeOn' || type=='editSubmit' || type=='editCancel'){
			for(var index=0; index<this.respiratorySystemObj.respSystemObject.pastPrescriptionList.length;index++){
				if(medicineObj.babyPresid==this.respiratorySystemObj.respSystemObject.pastPrescriptionList[index].babyPresid){
					this.respiratorySystemObj.respSystemObject.pastPrescriptionList[index].isInEditMode = !(this.respiratorySystemObj.respSystemObject.pastPrescriptionList[index].isInEditMode);
					submitIndex = index;
					if(type=='editCancel'){
						this.respiratorySystemObj.respSystemObject.pastPrescriptionList[index] = Object.assign({},this.PneumoTempObj.tempPastPrescriptionList[index]);
					}
				}
			}
		}
		if(type=='editSubmit'){
      try
      {
        this.http.post(this.apiData.editPrescription,
          medicineObj).subscribe(res => {
            this.prescription =res.json();
            if(this.prescription.type=="success"){
							this.respiratorySystemObj.respSystemObject.pastPrescriptionList[submitIndex] = Object.assign({},this.prescription.returnedObject);
							this.PneumoTempObj.tempPastPrescriptionList[submitIndex] = Object.assign({},this.prescription.returnedObject);
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
	}
	//below code is for stop medication
	navi= function(deleteBabyPrescription,actionid) {
		if(actionid == "delete"){
      deleteBabyPrescription.stopTime.date = new Date(deleteBabyPrescription.stopTime.date);
			if(deleteBabyPrescription.stopTime.date != null && deleteBabyPrescription.stopTime.date != undefined && deleteBabyPrescription.stopTime.date != ""){
				deleteBabyPrescription.enddate = deleteBabyPrescription.stopTime.date;
        try
        {
          this.http.post(this.apiData.deletePrescription+ deleteBabyPrescription.babyPresid + '/test',
            deleteBabyPrescription).subscribe(res => {
              this.prescription =res.json();
							this.showModal(this.prescription.message, this.prescription.type);
							if(this.prescription.type=="success"){
								this.getPrescription();
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
			}else{
				for(var i=0;i<this.respiratorySystemObj.respSystemObject.pastPrescriptionList.length;i++){
					if(this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].babyPresid == deleteBabyPrescription.babyPresid){
						this.respiratorySystemObj.respSystemObject.pastPrescriptionList[i].stopMessage = "Please select medicine stop date and time";
					}
				}
			}
		}
	}
  clearPneumoTreatment = function(treatmentType){
    if(treatmentType == 'pneumoSurfactant')
    {
      this.isSurfactantSavedPneumo = false;
      if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
        && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList('TRE015')){
          this.onCheckMultiCheckBoxValue('TRE015','pneumoTreatmentAction',false);
        }
      }
      else if(treatmentType == 'pneumoAntibiotics')
      {
        this.isAntibioticsSavedPneumo = false;
        if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
          && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList('TRE017')){
            this.onCheckMultiCheckBoxValue('TRE017','pneumoTreatmentAction',false);
          }
        }
        else if(treatmentType == 'pneumoNeedle')
        {
          this.isNeedleAspirationSavedPneumo = false;
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
            && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList('TRE018')){
              this.onCheckMultiCheckBoxValue('TRE018','pneumoTreatmentAction',false);
            }
          }
          else if(treatmentType == 'pneumoChesttube')
          {
            this.isChestTubeSavedPneumo = false;
            if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
              && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList('TRE019')){
                this.onCheckMultiCheckBoxValue('TRE019','pneumoTreatmentAction',false);
              }
            }
            else if(treatmentType == 'respiratory')
            {
              this.showRespSupportYesNoPopUP('Pneumothorax');
            }
            else if(treatmentType == 'pneumoOther')
            {
              this.isOtherSavedPneumo = false;
              if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
                && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList('other')){
                  this.onCheckMultiCheckBoxValue('other','pneumoTreatmentAction',false);
                }
              }
            }
  callRespiratorySupportPopUP = function(moduleName, flag) {
		console.log("vedaant"+flag);
		if(flag==true)
			this.respiratorySupportPopupView = true;
		else if(flag == false)
			this.respiratorySupportPopupView = false;
		this.respiratoryTempObject = Object.assign({},this.respiratorySystemObj.respSystemObject.respSupport);
		this.whichModuleRespirstorySupport = moduleName;
		console.log("Select Respiratory Support initiated");
		$("#RespiratorySupportPopUPOverlay").css("display", "block");
		$("#RespiratorySupportPopUP").addClass("showing");
	};
  closeRespiratorySupportPopUP = function(diseaseType){
		this.flowRateMessage ="";
		this.tvMessage ="";
		this.breathRateMessage ="";
		this.etMessage ="";
		this.spoMessage ="";
		this.respiratorySystemObj.respSystemObject.respSupport = Object.assign({},this.respiratoryTempObject);
		if(this.respiratorySystemObj.respSystemObject.respSupport.creationtime == null ||
				this.respiratorySystemObj.respSystemObject.respSupport.creationtime =='')
		{
			/* null all values and resp support selected */
			this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsEt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMv = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsTv = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsVentType = null;
			/* new added */
			// rsRate rsIsEndotracheal rsTubeSize rsFixation rsSpo2 rspCO2
			this.respiratorySystemObj.respSystemObject.respSupport.rsRate = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsTubeSize = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsFixation = null;
			this.rsAmplitude = "";
			this.rsEt = "";
			this.rsFio2 = "";
			this.rsFlowRate ="";
			this.rsFrequency = "";
			this.rsIt = "";
			this.rsMap = "";
			this.rsMechVentType = "";
			this.rsMv = "";
			this.rsPeep = "";
			this.rsPip = "";
			this.rsTv = "";
			this.rsVentType ="";
			var treatmentindex ;
		}
		$("#RespiratorySupportPopUPOverlay").css("display", "none");
		$("#RespiratorySupportPopUP").toggleClass("showing");
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPphnTemplate();
    this.creatingProgressNotesPneumoTemplate();
	}
  submitRDSResp = function(){
		this.isRdsValid = this.validateRds();
		if(this.isRdsValid){
			if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null
					&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=''){
				if (this.respiratoryDistressValue) {
					this.respiratorySystemObj.respSystemObject.respSupport.eventname  =  "Respiratory Distress";
				} else if (this.PPHNValue) {
					this.respiratorySystemObj.respSystemObject.respSupport.eventname  =  "PPHN";
				} else if (this.apneaDistressValue) {
					this.creatingProgressNotesApneaTemplate();
					this.respiratorySystemObj.respSystemObject.respSupport.eventname  =  "Apnea";
				} else if (this.OthersDistressValue) {
					this.respiratorySystemObj.respSystemObject.respSupport.eventname  =  "Other";
				} else if (this.PulmonaryDistressValue){
					this.respiratorySystemObj.respSystemObject.respSupport.eventname  =  "Pneumothorax";
				}
				console.log("function submitRDSResp")
				console.log(this.respiratorySystemObj.respSystemObject.respSupport);
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone!=null){
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone==true){
						this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant =
							this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
					}
				}
				if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='Low Flow O2'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = true;
					}
				} else if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='High Flow O2'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = true;
					}
				} else if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='CPAP'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = true;
					}
				} else if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='Mechanical Ventilation'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = false;
					}
				} else if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType=='HFO'){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = false;
					}
				}
				$("#RespiratorySupportPopUPOverlay").css("display", "none");
				$("#RespiratorySupportPopUP").toggleClass("showing");
				this.creatingProgressNotesTemplate();
				this.creatingProgressNotesApneaTemplate();
				this.creatingProgressNotesPphnTemplate();
			  this.creatingProgressNotesPneumoTemplate();
				this.saveTreatmentPphn('respiratory');
				this.saveTreatmentApnea('respiratory');
				this.saveTreatmentRds('rdsRespiratory');
				this.systemValue('TRE014','actionType');
			} else {
				this.closeRespiratorySupportPopUP(this.whichModuleRespirstorySupport);
			}
		}
	}
	refreshRespSupportEndoOptions = function() {
		this.respiratorySystemObj.respSystemObject.respSupport.rsTubeSize = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFixation = null;
	}
	refreshValuesRespiratory = function() {
		console.log("functionvalues ");
		console.log(this.respiratorySystemObj.respSystemObject.respSupport);
		this.flowRateMessage ="";
		this.tvMessage ="";
		this.breathRateMessage ="";
		this.etMessage ="";
		this.spoMessage ="";
		// below 6 fields are text, so blank them
		this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsEt = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsMv = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsTv = null;
		// rsRate rsIsEndotracheal rsTubeSize rsFixation rsSpo2 rspCO2 [NEW ADDED]
		this.respiratorySystemObj.respSystemObject.respSupport.rsRate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsTubeSize = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFixation = null;
		switch(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType)
		{
		case 'Low Flow O2':
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
			this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		case 'High Flow O2':
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
			this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		case 'CPAP':
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
			this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = '3';
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;

    case 'NIMV':
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
      this.respiratorySystemObj.respSystemObject.respSupport.rsRate = null;
      this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			break;
		case 'Mechanical Ventilation':
			if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType=='SIMV')
			{
				this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
				this.respiratorySystemObj.respSystemObject.respSupport.rsPip = '10';
				this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = '3';
				this.respiratorySystemObj.respSystemObject.respSupport.rsIt = '0.10';
				this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			}
			else if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType=='PSV')
			{
				this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
				this.respiratorySystemObj.respSystemObject.respSupport.rsPip = '10';
				this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = '3';
				this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
				this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			}
			else
			{
				this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = null;
				this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
				this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
				this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
				this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
			}
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		case 'HFO':
			this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = '21';
			this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsMap = '3';
			this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
			this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
			this.flowRateMessage ="";
			this.tvMessage ="";
			this.breathRateMessage ="";
			this.etMessage ="";
			this.spoMessage ="";
			break;
		default:
			console.log("issue in submitting respsupport");
		break;
		}
		console.log(this.respiratorySystemObj.respSystemObject.respSupport);
		console.log(this.respiratorySystemObj);
	}
	// call this function if all values in resp support object to be made null
	refreshRespiratoryObject = function(){
		this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsEt = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsMv = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsTv = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsIt = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsMap = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsPeep = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsPip = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsRate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsBackuprate = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsIsEndotracheal = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsTubeSize = null;
		this.respiratorySystemObj.respSystemObject.respSupport.rsFixation = null;
	}
	fetchValueRespSupport = function(itemValue,selectedfield){
		if (selectedfield == 'ItLeft'){
			console.log(this.respiratorySystemObj.respSystemObject.respSupport.rsEt);
			console.log(this.respiratorySystemObj.respSystemObject.respSupport.rsIt);
			if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt != null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt != undefined){
				this.respiratorySystemObj.respSystemObject.respSupport.rsRate = (this.respiratorySystemObj.respSystemObject.respSupport.rsEt*1 + this.respiratorySystemObj.respSystemObject.respSupport.rsIt*1)*60;
				this.respiratorySystemObj.respSystemObject.respSupport.rsRate = this.respiratorySystemObj.respSystemObject.respSupport.rsRate.toPrecision(4);
			}
		}
		switch(selectedfield)
		{
		case 'mapLeft':
			this.mapLeft = itemValue;
			break;
		case 'mapRight':
			this.mapRight = itemValue;
			break;
		case 'Fio2Left':
			this.Fio2Left = itemValue;
			break;
		case 'Fio2Right':
			this.Fio2Right = itemValue;
			break;
		case 'PipLeft':
			this.PipLeft = itemValue;
			break;
		case 'PipRight':
			this.PipRight = itemValue;
			break;
		case 'PeepLeft':
			this.PeepLeft = itemValue;
			break;
		case 'PeepRight':
			this.PeepRight = itemValue;
			break;
		case 'ItLeft':
			this.ItLeft = itemValue;
			break;
		case 'ItRight':
			this.ItRight = itemValue;
			break;
		default:
			console.log("fetchValue: no value fetched")
			break;
		}
		console.log("fetchValues result");
	}
	calculateBreadthRate = function(){
		if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt != null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt != undefined){
			this.respiratorySystemObj.respSystemObject.respSupport.rsRate = (this.respiratorySystemObj.respSystemObject.respSupport.rsEt*1 + this.respiratorySystemObj.respSystemObject.respSupport.rsIt*1)*60;
			this.respiratorySystemObj.respSystemObject.respSupport.rsRate = this.respiratorySystemObj.respSystemObject.respSupport.rsRate.toPrecision(4);
		}
	}
  calculateET = function(){
    if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt != null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt != undefined){
			this.respiratorySystemObj.respSystemObject.respSupport.rsEt = (this.respiratorySystemObj.respSystemObject.respSupport.rsRate/60 - this.respiratorySystemObj.respSystemObject.respSupport.rsIt*1);
			this.respiratorySystemObj.respSystemObject.respSupport.rsEt = this.respiratorySystemObj.respSystemObject.respSupport.rsEt.toPrecision(2);
		}
  }
  refreshRdsAllObj = function(type){
		if(type=="pphn"){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.labileOxygenation = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.labPreductal = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.labPostductal = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.labileDifference = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.pulmonaryPressure = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.systolicBp = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.oxgenationIndex = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.methaemoglobinLevel = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.pphnIno = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sildenafil = null;
		}
		this.creatingProgressNotesTemplate();
	}
  surfactantInsure = function(){
		this.isSurfactantInsure = !this.isSurfactantInsure;
		if(this.isSurfactantInsure){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = true;
		} else {
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = false;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsInsureType = null;
		}
		this.creatingProgressNotesTemplate();
	}
	testfunction = function(){
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsFlowRate;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsFio2;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsMap;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsMechVentType;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsPip;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsPeep;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsIt;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsEt;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsMv;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsAmplitude;
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsFrequency;
	}
//		end of the above code
//		below code is for OK cancel Respiratory Support popup
  respSupportPopupYes = function(module,inactiveMessage) {
		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");
		var symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList;
		if(symptomsArr!=null)
		{
			for(var i=0;i< symptomsArr.length;i++){
				if(symptomsArr[i] =='TRE005'){
					symptomsArr.splice(i,1);
          this.isRespiratorySaved = false;
					this.isRespiratorySelect = false;
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList =  symptomsArr;
		}
    var symptomsArr1 = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList;
		if(symptomsArr1!=null)
		{
			for(var i=0;i< symptomsArr1.length;i++){
				if(symptomsArr1[i] =='TRE010'){
					symptomsArr1.splice(i,1);
					this.PphnTempObj.isRespSelectPphn = false;
					this.isRespSavedPphn = false;
				}
			}
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList =  symptomsArr1;
		}
    var symptomsArr2= this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList;
		if(symptomsArr2!=null)
		{
			for(var i=0;i< symptomsArr2.length;i++){
				if(symptomsArr2[i] =='TRE016'){
					symptomsArr2.splice(i,1);
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList =  symptomsArr2;
		}
		var symptomsArr3 = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList;
		if(symptomsArr3!=null)
		{
			for(var i=0;i< symptomsArr3.length;i++){
				if(symptomsArr3[i] =='TRE007'){
					symptomsArr3.splice(i,1);
					this.isApneaRespiratorySaved = false;
					this.isApneaRespiratorySelect = false;
				}
			}
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList =  symptomsArr3;
		}
		var symptomsArr4 = this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList ;
		if(symptomsArr4 != null)
		{
			for(var i=0;i< symptomsArr4.length;i++){
				if(symptomsArr4[i] =='TRE024'){
					symptomsArr4.splice(i,1);
					this.isOtherRespiratorySaved = false;
					this.isOtherRespiratorySelect = false;
				}
			}
			this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList =  symptomsArr4;
		}
		this.refreshRespiratoryObject();
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPphnTemplate();
	  this.creatingProgressNotesPneumoTemplate();
		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");
	}
	showRespSupportYesNoPopUP = function(diseaseType){
		this.whichModuleRespirstorySupport = diseaseType;
		$("#RespSupportYesNoPopup").addClass("showing");
		$("#respSupportOverlay").addClass("show");
	}
	closeRespSupportYesNoPopUP = function(diseaseType){
		$("#RespSupportYesNoPopup").removeClass("showing");
		$("#respSupportOverlay").removeClass("show");
		var treatmentindex = 0;
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPphnTemplate();
		this.creatingProgressNotesPneumoTemplate();
	}
//		below code is inotrop Popup
	callInotropPopUP = function() {
		console.log("Select Respiratory Support initiated");
		$("#inotropPopUPOverlay").css("display", "block");
		$("#inotropPopUP").addClass("showing");
	}
  showOkPopUp = function(module,inactiveMessage) {
		console.log("its ok");
		this.inactiveMessageText = inactiveMessage;
		this.inactiveModule = module;
		$("#OkCancelPopUp").addClass("showing");
		$("#scoresOverlay").addClass("show");
		if(this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
			this.inactiveWithRespSupport = true;
		} else {
			this.inactiveWithRespSupport = false;
		}
	}
	closeOkPopUp = function(){
		$("#OkCancelPopUp").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
	}
	submitInactive = function() {
		if( this.inactiveModule!=""){
			if(this.inactiveModule=="respSystem"){//top of the screen status
				this.respiratorySystemObj.systemStatus = "Inactive";
				this.respiratoryDistressValue = false;
				this.apneaDistressValue = false;
				this.OthersDistressValue = false;
				this.PPHNValue = false;
				this.submitResp();
			} else if(this.inactiveModule=="respDistress"){
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus = "Inactive";
				this.submitResp();
			} else if(this.inactiveModule=="pphn"){
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus = "Inactive";
				this.submitResp();
			} else if(this.inactiveModule=="apnea"){
				this.creatingProgressNotesApneaTemplate();
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = "Inactive";
				this.submitResp();
			} else if(this.inactiveModule=="other"){
				this.respiratorySystemObj.respSystemObject.others.currentOther.eventstatus = "Inactive";
				this.respiratorySystemObj.respSystemObject.others.currentOther.progressnotes = "Other Assessment is Inactivated";
				this.submitResp();
			} else if(this.inactiveModule=="pneumo"){
				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus = "Inactive";
				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.progressnotes = "Pneumothorax Assessment is Inactivated";
				this.submitResp();
			}
			this.closeOkPopUp();
		}else{
			this.showModal("unknown model","failure");
		}
	}
  orderSelected = function(){
		//iterate ovver list for order list of selected...
		console.log("dsfasfdsf");
		this.orderSelectedText = "";
		this.investOrderSelected = [];
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			console.log(this.respiratorySystemObj.dropDowns.testsList[obj]);
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				if(this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected==true){
					if(this.orderSelectedText==''){
						this.orderSelectedText =  this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					}else{
						this.orderSelectedText = this.orderSelectedText +", "+ this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					}
          this.respiratorySystemObj.respSystemObject.orderSelectedText = this.orderSelectedText;
					this.investOrderSelected.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
				} else {
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderSelected);
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPphnTemplate();
		this.creatingProgressNotesPneumoTemplate();
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");
		$('body').css('overflow', 'auto');
	};
	closeModalOrderInvestigation = function() {
		console.log("closing");
		this.investOrderNotOrdered = [];
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				if(this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected==true){
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderNotOrdered.push(testName);
					if(this.investOrderSelected.indexOf(testName) == -1)
						this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = false;
				} else {
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderNotOrdered);
		console.log("closeModalOrderInvestigation closing");
		$("#ballardOverlay").css("display", "none");
		$('body').css('overflow', 'auto');
		$("#OrderInvestigationPopup").toggleClass("showing");
	};
	showOrderInvestigation = function(){
		if(this.apneaDistressValue == true){
			this.jaundiceDropDownValue = "Apnea";
			this.selectedDecease = "Apnea";
		}
		if(this.respiratoryDistressValue == true || this.OthersDistressValue == true || this.PPHNValue || this.PulmonaryDistressValue){
			this.jaundiceDropDownValue = "Respiratory";
			this.selectedDecease = "Respiratory";
		}
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
				if(this.investOrderNotOrdered.indexOf(testName) != -1)
					this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
			}
		}
		if(this.PPHNValue  == true){
			this.jaundiceDropDownValue = "Respiratory";
			this.selectedDecease = "Respiratory";
		}
		this.listTestsByCategory = this.respiratorySystemObj.dropDowns.testsList[this.jaundiceDropDownValue];
		console.log("showOrderInvestigation initiated");
		$("#ballardOverlay").css("display", "block");
		$("#OrderInvestigationPopup").addClass("showing");
		$('body').css('overflow', 'hidden');
	};
  onCheckMultiCheckBoxValue = function(Value,multiCheckBoxId,flagValue){
  var symptomsArr = [];
		if(multiCheckBoxId=="treatmentAction"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = symptomsArr;
		} else if(multiCheckBoxId=="pphnTreatmentAction") {
			if(Value == "TRE010" && flagValue == false){
				this.showRespSupportYesNoPopUP('PPHN');
				return;
			}
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = symptomsArr;
		} else if(multiCheckBoxId=="medicinePphn") {
			console.log("in med pphn");
			console.log(Value);
			if(this.respiratorySystemObj.respSystemObject.pphn.antibioticsList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.antibioticsList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.antibioticsList;
			}
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.antibioticsEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.antibioticsEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.antibioticsList = symptomsArr;
			if(this.respiratorySystemObj.respSystemObject.pphn.antibioticsList!=null &&
					this.respiratorySystemObj.respSystemObject.pphn.antibioticsList.length>0){
				this.systemValue('TRE011','actionType');
			}
		} else if(multiCheckBoxId=="medicineOther") {
			if(this.respiratorySystemObj.respSystemObject.others.currentBabyPrescriptionList!=null
					&& this.respiratorySystemObj.respSystemObject.others.currentBabyPrescriptionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.others.currentBabyPrescriptionList;
			}
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.others.babyPrescriptionEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.others.babyPrescriptionEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.others.currentBabyPrescriptionList = symptomsArr;
			this.saveTreatmentOthers('othersAntibiotics');
		}else if(multiCheckBoxId=="medicine"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList;
			}
			var date = new Date();
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var meridian = "AM";
			if (hours > 12) {
				hours -= 12;
				meridian = "PM"
			} else if (hours === 0) {
				hours = 12;
			}
			var timeStr = hours+":"+minutes+" "+meridian;
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.respiratoryDistress.babyPrescriptionEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						prescEmptyObj.starttime = timeStr;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.respiratoryDistress.babyPrescriptionEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							prescEmptyObj.starttime = timeStr;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList = symptomsArr;
			this.saveTreatmentRds('rdsAntibiotics');
		} else if(multiCheckBoxId=="ino"){
			if(this.respiratorySystemObj.respSystemObject.pphn.inoList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.inoList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.inoList;
			}
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.inoEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.inoEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.inoList = symptomsArr;
			if(this.respiratorySystemObj.respSystemObject.pphn.inoList!=null &&
					this.respiratorySystemObj.respSystemObject.pphn.inoList.length>0){
				this.systemValue('TRE012','actionType');
			}
		} else if(multiCheckBoxId=="sedation"){
			if(this.respiratorySystemObj.respSystemObject.pphn.sedationList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.sedationList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.sedationList;
			}
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.sedationEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.sedationEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.sedationList = symptomsArr;
			if(this.respiratorySystemObj.respSystemObject.pphn.sedationList!=null &&
					this.respiratorySystemObj.respSystemObject.pphn.sedationList.length>0){
				this.systemValue('TRE014','actionType');
			}
		} else if(multiCheckBoxId=="treatmentPlan"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList.length>0){
				var isNotValidOptions = false;
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList.includes('RDSPLAN0003')
						&& Value=="RDSPLAN0004" && flagValue==true){
					isNotValidOptions = true;
				}else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList.includes('RDSPLAN0004')
						&& Value=="RDSPLAN0003" && flagValue==true){
					isNotValidOptions = true;
				}
				if(isNotValidOptions==true){
					for(var indexPlan=0; indexPlan<this.respiratorySystemObj.dropDowns.respiratoryPlan.length;indexPlan++){
						if(this.respiratorySystemObj.dropDowns.respiratoryPlan[indexPlan].key==Value){
							this.respiratorySystemObj.dropDowns.respiratoryPlan[indexPlan].flag = false;
						}
					}
				}
			}{
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList.length>0){
            symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList;
				}
				var flag = true;
				if(symptomsArr.length == 0){
					symptomsArr.push(Value);
				}
				else{
					for(var i=0;i< symptomsArr.length;i++){
						if(Value == symptomsArr[i]){
							symptomsArr.splice(i,1);
							flag = false;
						}
					}
					if(flag == true){
						symptomsArr.push(Value);
					}
				}
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsPlanList = symptomsArr;
			}
		}
		else if(multiCheckBoxId=="pphnPlan"){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnPlanList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnPlanList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnPlanList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnPlanList = symptomsArr;
			console.log(this.respiratorySystemObj.respSystemObject.pphn.currentPphn);
		}
		else if(multiCheckBoxId=="causeOfPphn") {
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList = symptomsArr;
		}else if(multiCheckBoxId=="riskFactorPphn") {
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList = symptomsArr;
		}else if(multiCheckBoxId=="causeOfRespDistress"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList = symptomsArr;
		}
		else if(multiCheckBoxId=="causeOfPneumo"){
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList = symptomsArr;
		}else if(multiCheckBoxId=="riskFactorPneumo") {
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList = symptomsArr;
		}else if(multiCheckBoxId=="apneaTreatment"){
			if(Value == "TRE007" && flagValue == false){
				this.showRespSupportYesNoPopUP('Apnea');
				return;
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList;
			}
			var flag = true;
			if(symptomsArr== null || symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = symptomsArr;
			console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea);
		}
		else if(multiCheckBoxId=="bpdTreatment"){
			console.log("vedaant");
			console.log(Value)
			console.log(flagValue)
			console.log(this.respiratorySystemObj);
			if(Value == 'TRE020')
			{
				if(flagValue==true)
					this.callRespiratorySupportBPDPopUP();

			}
			else if (Value == 'TRE021')
			{}
			else if (Value == 'TRE022')
			{}
		}
		else if(multiCheckBoxId=="apneaPlan"){
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaPlanList!=null
					&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaPlanList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaPlanList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaPlanList = symptomsArr;
			console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea);
		}
		else if(multiCheckBoxId=="causeOfApnea"){
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList!=null
					&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.apneaCauseList = symptomsArr;
		}
		else if(multiCheckBoxId=="otherTreatmentAction"){
			if(this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList = symptomsArr;
			console.log(this.respiratorySystemObj.respSystemObject.others.currentOther.treatmentActionList);
		} else if(multiCheckBoxId=="pneumoTreatmentAction"){
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList = symptomsArr;
		} else if(multiCheckBoxId=="medicinePneumo"){
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList!=null
					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList.length>0){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList;
			}
			if(symptomsArr.length == 0){
				for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
					if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
						var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
						var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pneumothorax.babyPrescriptionEmptyObj);
						prescEmptyObj.comments = selectedMedObj.description;
						prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
						prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
						prescEmptyObj.medicinename = selectedMedObj.medname;
						prescEmptyObj.medid = selectedMedObj.medid;
						prescEmptyObj.flag = true;
						symptomsArr.push(prescEmptyObj);
					}
				}
			}else{
				if(flagValue==true){
					for(var indexMed = 0; indexMed<this.respiratorySystemObj.dropDowns.medicine.length;indexMed++){
						if(this.respiratorySystemObj.dropDowns.medicine[indexMed].medid==Value){
							var selectedMedObj = this.respiratorySystemObj.dropDowns.medicine[indexMed];
							var prescEmptyObj = Object.assign({},this.respiratorySystemObj.respSystemObject.pneumothorax.babyPrescriptionEmptyObj);
							prescEmptyObj.comments = selectedMedObj.description;
							prescEmptyObj.dose = selectedMedObj.dosemultiplier; //put weight calculation thing here.
							prescEmptyObj.medicationtype = selectedMedObj.medicationtype;
							prescEmptyObj.medicinename = selectedMedObj.medname;
							prescEmptyObj.medid = selectedMedObj.medid;
							symptomsArr.push(prescEmptyObj);
						}
					}
				}else{
					for(var i=0;i<symptomsArr.length;i++){
						if(Value == symptomsArr[i].medid){
							symptomsArr.splice(i,1);
						}
					}
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList = symptomsArr;
			console.log(symptomsArr);
		} else if(multiCheckBoxId=="planRds"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsplanList!=null){
				symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsplanList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsplanList = symptomsArr;
		} else if(multiCheckBoxId=="planPneumo"){
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentplanList!=null){
				symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentplanList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentplanList = symptomsArr;
		} else if(multiCheckBoxId=="riskFactorRds"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList!=null){
				symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList;
			}
			var flag = true;
			if(symptomsArr.length == 0){
				symptomsArr.push(Value);
			}
			else{
				for(var i=0;i< symptomsArr.length;i++){
					if(Value == symptomsArr[i]){
						symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					symptomsArr.push(Value);
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList = symptomsArr;
		}
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPphnTemplate();
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesPneumoTemplate();
	}
  populateMultiCheckBox = function(id) {
		console.log(id);
		var fields = id.split('-');
		var name = fields[2];
		console.log(name);
		var checkboxContId = "#checkboxes-"+ name;
		console.log(checkboxContId);
		var width = $("#multiple-selectbox-2").width();
		if(width == null || width == 0) {
			width = $("#multiple-selectbox-"+name).width() - 2;
		}
		if (!this.expanded) {
			$(checkboxContId).toggleClass("show");
			$(checkboxContId).width(width);
			console.log(width);
			this.expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			this.expanded = false;
		}
	}
//below function is used to set the value of  variables when the system is selected as NO
	systemNo = function(){
		this.respiratoryDistressValue = false;
		this.apneaDistressValue = false;
		this.PPHNValue = false;
		this.OthersDistressValue = false;
	}

	clinicalSectionApneaVisible = function(){
		this.isclinicalSectionApneaVisible = !this.isclinicalSectionApneaVisible;
	};
	actionSectionApneaVisible = function(){
		this.isActionApneaVisible = !(this.isActionApneaVisible);
	}
	planSectionApneaVisible = function(){
		this.isPlanApneaVisible = !this.isPlanApneaVisible;
	}
	causeApneaSectionVisible = function(){
		this.isApneaCauseVisible = !this.isApneaCauseVisible;
	}
	progressNotesActionSectionVisible = function(){
		this.isProgressNotesActionVisible = !this.isProgressNotesActionVisible;
	}
	respiratoryDistressVisible = function(){
		this.respiratorySystemObj.respSystemObject.eventName  =  "respDistress";
		this.respiratoryDistressValue = true;
		this.apneaDistressValue = false;
		this.PulmonaryDistressValue = false;
		this.BPDDistressValue = false;
		this.OthersDistressValue = false;
		this.PPHNValue = false;
		this.ageOnsetPreviousExceed = false;
	}
	apneaDistressVisible = function(){
		this.respiratorySystemObj.respSystemObject.eventName = "apnea";
		this.apneaDistressValue = true;
		this.respiratoryDistressValue = false;
		this.PulmonaryDistressValue = false;
		this.BPDDistressValue = false;
		this.OthersDistressValue = false;
		this.PPHNValue = false;
		this.ageOnsetPreviousExceed = false;
	}
	PulmonaryDistressVisible = function(){
		this.respiratorySystemObj.respSystemObject.eventName  =  "pneumothorax";
		this.PulmonaryDistressValue = true;
		this.respiratoryDistressValue = false;
		this.apneaDistressValue = false;
		this.BPDDistressValue = false;
		this.OthersDistressValue = false;
		this.PPHNValue = false;
		this.ageOnsetPreviousExceed = false;
	}
	PPHNVisible = function(){
		this.respiratorySystemObj.respSystemObject.eventName  =  "pphn";
		this.PPHNValue = true;
		this.PulmonaryDistressValue = false;
		this.respiratoryDistressValue = false;
		this.apneaDistressValue = false;
		this.BPDDistressValue = false;
		this.OthersDistressValue = false;
		this.ageOnsetPreviousExceed = false;
	}
  orderInotrope = function(){
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.inotropes = this.SelectedInotrope+this.totalInotrope;
		$("#inotropPopUPOverlay").css("display", "none");
		$("#inotropPopUP").toggleClass("showing");
		$('body').css('overflow', 'auto');
		this.creatingProgressNotesTemplate();
	}
	calculatingTotalInotrope = function(selectedInotrope){
		if(selectedInotrope == "Dopamine"){
			if(this.DopamineValue != null){
				this.totalInotrope =  parseInt(this.totalInotrope) + parseInt(this.DopamineValue);
			}
		}
		if(selectedInotrope == "Doputamine"){
			if(this.DoputamineValue != null){
				this.totalInotrope =  parseInt(this.totalInotrope) + parseInt(this.DoputamineValue);
			}
		}
		if(selectedInotrope == "Epinephrine"){
			if(this.EpinephrineValue != null){
				this.totalInotrope =  parseInt(this.totalInotrope) + parseInt(this.EpinephrineValue);
			}
		}
		if(selectedInotrope == "Vasopressin"){
			if(this.VasopressinValue != null){
				this.totalInotrope =  parseInt(this.totalInotrope) + parseInt(this.VasopressinValue);
			}
		}
		if(selectedInotrope == "Milrinone"){
			if(this.MilrinoneValue != null){
				this.totalInotrope =  parseInt(this.totalInotrope) + parseInt(this.MilrinoneValue);
			}
		}
	}
	selectedInotrope = function(SelectedCheckbox,checkboxValue){
		if(SelectedCheckbox == "Dopamine"){
			if(checkboxValue == true){
				this.SelectedInotrope =  this.SelectedInotrope + "Dopamine ";
			}
			else{
				this.SelectedInotrope = this.SelectedInotrope.replace("Dopamine ","");
				if(this.DopamineValue!=null){
					this.totalInotrope =  parseInt(this.totalInotrope) - parseInt(this.DopamineValue);
				}
				this.DopamineValue = null;
			}
		}
		if(SelectedCheckbox == "Doputamine"){
			if(checkboxValue == true){
				this.SelectedInotrope =  this.SelectedInotrope + "Doputamine ";
			}
			else{
				this.SelectedInotrope = this.SelectedInotrope.replace("Doputamine ","");
				if(this.DoputamineValue!=null){
					this.totalInotrope =  parseInt(this.totalInotrope) - parseInt(this.DoputamineValue);
				}
				this.DoputamineValue = null;
			}
		}
		if(SelectedCheckbox == "Epinephrine"){
			if(checkboxValue == true){
				this.SelectedInotrope =  this.SelectedInotrope + "Epinephrine ";
			}
			else{
				if(this.EpinephrineValue!=null){
					this.totalInotrope =  parseInt(this.totalInotrope) - parseInt(this.EpinephrineValue);
				}
				this.EpinephrineValue = null;
				this.SelectedInotrope = this.SelectedInotrope.replace("Epinephrine ","");
			}
		}
		if(SelectedCheckbox == "Vasopressin"){
			if(checkboxValue == true){
				this.SelectedInotrope =  this.SelectedInotrope + "Vasopressin ";
			}
			else{
				if(this.VasopressinValue!=null){
					this.totalInotrope =  parseInt(this.totalInotrope) - parseInt(this.VasopressinValue);
				}
				this.VasopressinValue = null;
				this.SelectedInotrope = this.SelectedInotrope.replace("Vasopressin ","");
			}
		}
		if(SelectedCheckbox == "Milrinone"){
			if(checkboxValue == true){
				this.SelectedInotrope =  this.SelectedInotrope + "Milrinone ";
			}
			else{
				if(this.MilrinoneValue!=null){
					this.totalInotrope =  parseInt(this.totalInotrope) - parseInt(this.MilrinoneValue);
				}
				this.MilrinoneValue = null;
				this.SelectedInotrope = this.SelectedInotrope.replace("Milrinone ","");
			}
		}
	}
//		end of the above code
	populateTestsListByCategory = function(assessmentCategory){
		this.selectedDecease = assessmentCategory;
		console.log(assessmentCategory);
		this.listTestsByCategory = this.respiratorySystemObj.dropDowns.testsList[assessmentCategory];
		console.log( this.listTestsByCategory);
	}
  TimeCtrl = function() {
		this.clock = "loading clock..."; // initialise the time variable
		this.tickInterval = 1000 //ms
		var tick = function() {
			this.clock = Date.now(); // get the current time
      setTimeout(() => {
       tick}, this.tickInterval);
		};
		// Start the timer
    setTimeout(() => {
     tick}, this.tickInterval);
	//	$timeout(tick, this.tickInterval);
	}
	upgradeDowngradeVentMode = function(modeType){
		console.log("logsss");
		if(modeType=="upgrade"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType=="6"){
				this.warningMessage = "Please select proper ventilator mode.";
        this.showWarningPopUp();
			}else{
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType =
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType*1 +1;
			}
		}else if(modeType=="downgrade"){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType=="0"){
				this.warningMessage = "Please select proper ventilator mode.";
        this.showWarningPopUp();
			}else{
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType =
					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ventilationType*1 -1;
			}
		}
		this.creatingProgressNotesTemplate();
	}
	populatePastRdsRecord = function(respDistressId){
		for(var index=0; index<this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length;index++){
			if(respDistressId==this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[index].resprdsid){
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress =this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[index];
			}
		}
		//setting age at onset
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList!=null){
			this.orderSelectedText = "";
			for(var obj in this.respiratorySystemObj.dropDowns.testsList){
				for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
					for(var indexPastOrder = 0; indexPastOrder<this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList.length;indexPastOrder++){
						if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
							this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList[indexPastOrder]){
							this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected=true;
							if(this.orderSelectedText==""){
								this.orderSelectedText = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
							}else{
								this.orderSelectedText = this.orderSelectedText + "," + this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
							}

						}
					}
				}
			}
		}
		//setting test list data..
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction!=""){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = [];
			this.treatmentSelectedText = "";
			var treatmentActionList =[];
			treatmentActionList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction
			.replace("[","").replace("]",",").replace(" ","").split(",");
			for(var index = 0;index<treatmentActionList.length;index++ ){
				for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.treatmentAction.length;indexDropDown++){
					if(treatmentActionList[index].replace(" ","")
							== this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key.replace(" ","")){
						this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].flag = true;
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.push(treatmentActionList[index]);
						if(this.treatmentSelectedText==""){
							this.treatmentSelectedText = this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].value;
						}else{
							this.treatmentSelectedText = this.treatmentSelectedText + "," +this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].value;
						}
					}
				}
			}
		}
		//setting plan data...
		//setting cause of rds...
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds!=""){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList = [];
			this.causeOfRespDistressStr = "";
			var rdsCauseList =[];
			rdsCauseList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds
			.replace("[","").replace("]",",").replace(" ","").split(",");
			for(var index =0; index < rdsCauseList.length;index++){
				for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.causeOfRds.length;indexDropDown++){
					if(rdsCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].key){
						this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.push(rdsCauseList[index]);
						this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].flag = true;
						if(this.causeOfRespDistressStr==""){
							this.causeOfRespDistressStr = this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].value;
						}else{
							this.causeOfRespDistressStr = this.causeOfRespDistressStr + "," + this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].value;
						}
					}
				}
			}
		}
	}
  saveSilvermanScore = function(){
		this.silvermanScoreList =
		{
				"silvermanscoreid": null,
				"creationtime": null,
				"modificationtime": null,
				"uhid": this.childObject.uhid,
				"upperchest": this.totalUpperChest,
				"lowerchest": this.totalLowerChest,
				"xiphoidretract": this.totalXiphoid,
				"narasdilat": this.totalNaras,
				"expirgrunt": this.totalExpir,
				"silvermanscore": this.silvermanScoreTotalGrade
		};
		console.log(this.silvermanScoreList);
    try
    {
      this.http.post(this.apiData.getEmptyAdvanceAdmissionForm,
        this.silvermanScoreList).subscribe(res => {
          this.silvermanScoreList =res.json();
          this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.silvermanscoreid = this.silvermanScoreList.returnedObject.silvermanscoreid;
					console.log("Silver data"+this.silvermanScoreList);
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
//		end of the above code for silverman Score
//		below is the code to get silverman score
	getSilverman = function() {
		var silvermanid = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.silvermanscoreid;
    try{
      this.http.request(this.apiData.getSilvermanScore + silvermanid,)
      .subscribe(res => {
        this.responseSiverman = res.json();
      },
      err => {
        console.log("Error occured.")
      }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
//		end to get silverman score
//		below code is for downe Score
//		below method is used to open the downe score
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
			this.creatingProgressNotesTemplate();
			this.creatingProgressNotesPphnTemplate();
			this.creatingProgressNotesPneumoTemplate();
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
		}if(downesValue == "retractions"){
			this.totalRetractions = parseInt(this.retractions);
		}if(downesValue == "grunting"){
			this.totalGrunting = parseInt(this.grunting);
		}if(downesValue == "airEntry"){
			this.totalAirEntry = parseInt(this.airEntry);
		}if(downesValue == "respiratoryRate"){
			this.totalRespiratoryRate = parseInt(this.respiratoryRate);
		}
		this.downesValueCalculated = this.totalCyanosis + this.totalRetractions + this.totalGrunting + this.totalAirEntry + this.totalRespiratoryRate;
		this.downesScoreTotalGrade = this.downesValueCalculated;
	}
//		below method is used to save the downe Score
	saveDownesScore = function(){
    this.downesScoreList.downesscoreid = null;
    this.downesScoreList.creationtime = null;
    this.downesScoreList.modificationtime = null;
    this.downesScoreList.cynosis = this.totalCyanosis;
    this.downesScoreList.uhid = this.childObject.uhid;
    this.downesScoreList.airentry = this.totalAirEntry;
    this.downesScoreList.retractions = this.totalRetractions;
    this.downesScoreList.grunting = this.totalGrunting;
    this.downesScoreList.respiratoryrate = this.totalRespiratoryRate;
    this.downesScoreList.downesscore = this.downesScoreTotalGrade;
    try
    {
      this.http.post(this.apiData.saveDownesScore,
        this.downesScoreList).subscribe(res => {
          this.downesScoreList =res.json();
          this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesscoreid = this.downesScoreList.returnedObject.downesscoreid+"";
					this.respiratorySystemObj.respSystemObject.pphn.currentPphn.downesscoreid = this.downesScoreList.returnedObject.downesscoreid+"";
					console.log("Downes data"+JSON.stringify(this.downesScoreList));
					this.submitResp();
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
//		end of the above code of downe Score
//		below is the code to get downe score
	getDownes = function() {
		var downesid = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesscoreid;
    try{
      this.http.request(this.apiData.getDownesScore + downesid,)
        .subscribe(res => {
          this.responseDownes = res.json();
        },
        err => {
          console.log("Error occured.")
        }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};
//		end to get downe score
//		end of the above Code
//		below code is for the respiratory support Popup for Apnea
	callRespiratorySupportApneaPopUP = function() {
		console.log("Select Respiratory Support initiated for Apnea");
		$("#apneaRespiratorySupportPopUPOverlay").css("display", "block");
		$("#apneaRespiratorySupportPopUP").addClass("showing");
	};
	closeRespiratorySupportApneaPopUP = function(){
		this.refreshApneaRespObj();
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.pop("TRE007");
		this.respiratorySystemObj.dropDowns.treatmentAction[6].isApneaFalg = false;
		console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList);
		console.log("Respiratory Support closing");
		$("#apneaRespiratorySupportPopUPOverlay").css("display", "none");
		$("#apneaRespiratorySupportPopUP").toggleClass("showing");
	}
	submitApneaResp = function(){
		this.creatingProgressNotesApneaTemplate();
		$("#apneaRespiratorySupportPopUPOverlay").css("display", "none");
		$("#apneaRespiratorySupportPopUP").toggleClass("showing");
	}
  refreshApneaRespObj = function(type){
		console.log("in refresh resp obj");
		console.log(this.respiratorySystemObj.respSystemObject.apnea.respSupport);
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsVentType = type;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsFlowRate = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsFio2 = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsMap = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsMechVentType = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsPip = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsPeep = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsIt = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsEt = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsTv = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsMv = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsAmplitude = null;
		this.respiratorySystemObj.respSystemObject.apnea.respSupport.rsFrequency = null;
	}
//		end of the above code
//		above code is used for oder investigation of Apnea
//		below code is used for the modal popup of order investigation
	apneaOrderSelected = function(){
		//iterate ovver list for order list of selected...
		console.log("dsfasfdsf");
		this.orderSelectedText = "";
		this.investOrderSelected = [];
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			console.log(this.respiratorySystemObj.dropDowns.testsList[obj]);
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				if(this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected==true){
					if(this.orderSelectedText==''){
						this.orderSelectedText =  this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					}else{
						this.orderSelectedText = this.orderSelectedText +", "+ this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					}
					this.investOrderSelected.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
				} else {
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderSelected);
		this.creatingProgressNotesTemplate();
		this.creatingProgressNotesApneaTemplate();
		this.creatingProgressNotesPneumoTemplate();
		$("#apneaOrgerInvestigationOverlay").css("display", "none");
		$("#apneaOrderInvestigationPopup").toggleClass("showing");
		$('body').css('overflow', 'auto');
	};
	closeApneaModalOrderInvestigation = function() {
		this.investOrderNotOrdered = [];
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				if(this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected==true){
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderNotOrdered.push(testName);
					if(this.investOrderSelected.indexOf(testName) == -1)
						this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = false;
				} else {
					var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		console.log(this.investOrderNotOrdered);
		console.log("closeModalOrderInvestigation closing");
		$("#apneaOrgerInvestigationOverlay").css("display", "none");
		$("#apneaOrderInvestigationPopup").toggleClass("showing");
		$('body').css('overflow', 'auto');
	};
	showApneaOrderInvestigation = function(){
		if(this.apneaDistressValue == true){
			this.jaundiceDropDownValue = "Apnea";
			this.selectedDecease = "Apnea";
		}
		if(this.respiratoryDistressValue == true){
			this.jaundiceDropDownValue = "Respiratory";
			this.selectedDecease = "Respiratory";
		}
		for(var obj in this.respiratorySystemObj.dropDowns.testsList){
			for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
				var testName = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
				if(this.investOrderNotOrdered.indexOf(testName) != -1)
					this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
			}
		}
		this.listTestsByCategory = this.respiratorySystemObj.dropDowns.testsList[this.jaundiceDropDownValue];
		console.log("showOrderInvestigation initiated");
		$("#apneaOrgerInvestigationOverlay").css("display", "block");
		$("#apneaOrderInvestigationPopup").addClass("showing");
		$('body').css('overflow', 'hidden');
	};
  pphnSurfactantInsure = function(){
		this.isPPHNSurfactantInsure = !this.isPPHNSurfactantInsure;
		if(this.isPPHNSurfactantInsure){
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone = true;
		} else {
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone = false;
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant = null;
		}
		this.creatingProgressNotesPphnTemplate();
	}
  showPassiveConfirmation = function(){
		$("#passiveConfirmationOverlay").css("display", "block");
		$("#passiveConfirmationPopup").addClass("showing");
	}
	passiveConfirmationClose = function() {
		$("#passiveConfirmationOverlay").css("display", "none");
		$("#passiveConfirmationPopup").toggleClass("showing");
		$('body').css('overflow', 'auto');
	};
	submitInactiveSystem = function(flag) {
		console.log(flag);
		this.respiratorySystemObj.respSystemObject.stopTreatmentFlag = flag;
		this.submitInactive();
	};
	submitPassiveSystem = function(flag) {
		console.log(flag);
		this.respiratorySystemObj.respSystemObject.stopTreatmentFlag = flag;
		this.submitResp();
		this.passiveConfirmationClose();
	};
	submitSystem = function() {
		console.log('submitSystem');
		var popupFlag = false;
		if(this.respiratorySystemObj.respSystemObject.eventName == 'respDistress') {
      this.currentEvent = 'RDS';
      if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus == 'Inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'RDS'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.eventstatus == 'No'){
				popupFlag = true;
			}
		} else if(this.respiratorySystemObj.respSystemObject.eventName == 'apnea') {
      this.currentEvent = 'Apnea';
      if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus == 'Inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'Apnea'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus == 'No') {
				popupFlag = true;
			}
		} else if(this.respiratorySystemObj.respSystemObject.eventName == 'pphn') {
      this.currentEvent = 'PPHN';
      if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus == 'Inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'PPHN'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.eventstatus == 'No') {
				popupFlag = true;
			}
		} else if(this.respiratorySystemObj.respSystemObject.eventName == 'pneumothorax') {
      this.currentEvent = 'Pneumothorax';
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus == 'Inactive' && this.pastPrescriptionList != null && this.pastPrescriptionList.length > 0){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.isactive != false && obj.eventname != 'Stable Notes' && obj.eventname == 'Pneumothorax'){
            this.showMedicationPopUp();
            return;
          }
        }
      }
			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus == 'No') {
				popupFlag = true;
			}
		}
		if(popupFlag && this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
			this.showPassiveConfirmation();
		} else {
			this.checkSilvermanAndSubmit();
		}
	}
  //		below code is for PPHN
	clinicalSectionPPHNVisible = function(){
		this.isclinicalSectionPPHNVisible = !this.isclinicalSectionPPHNVisible;
	}
	actionSectionPPHNVisible = function(){
		this.isActionPPHNVisible = !this.isActionPPHNVisible;
	}
	planSectionPPHNVisible = function(){
		this.isPlanPPHNVisible = !this.isPlanPPHNVisible;
	}
	progressNotesPphnSectionVisible = function(){
		this.isPphnprogressNotesVisible = !this.isPphnprogressNotesVisible;
	}
	causePphnSectionVisible = function(){
		this.isPphnCauseVisible = !this.isPphnCauseVisible;
	}
	printJaundiceData = function(fromDateTable : Date, toDateTable : Date){
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
    for(var i=0; i < this.respiratorySystemObj.respSystemObject.pastTableList.length; i++) {
      var item = this.respiratorySystemObj.respSystemObject.pastTableList[i];
          if(item.creationtime >= fromDateTable.getTime() && item.creationtime <= toDateTable.getTime()) {
          var obj = Object.assign({}, item);
          data.push(obj);
      }
    }
    this.printDataObj.printData = data;
  }
    this.printDataObj.uhid = this.childObject.uhid;
		this.printDataObj.whichTab = "Respiratory Systems";
    console.log(this.printDataObj);
		if(this.printDataObj.dateFrom == undefined){
			this.printDataObj.dateFrom = new Date();
		}
		if(this.printDataObj.dateFrom !=null){
			this.printDataObj.dateFrom = this.dateformatter(this.printDataObj.dateFrom, "gmt", "utf");
		}
		if(this.printDataObj.dateTo == undefined){
			this.printDataObj.dateTo= new Date();
		}
		if(this.printDataObj.dateTo !=null){
			this.printDataObj.dateTo = this.dateformatter(this.printDataObj.dateTo, "gmt", "utf");
		}
		localStorage.setItem('printDataObj', JSON.stringify(this.printDataObj));
    this.router.navigateByUrl('/jaundice/jaundice-print');
    if(this.printDataObj.dateFrom !=null){
      this.printDataObj.dateFrom = this.dateformatter(this.printDataObj.dateFrom, "utf", "gmt");
    }else{}
    if(this.printDataObj.dateTo !=null){
      this.printDataObj.dateTo = this.dateformatter(this.printDataObj.dateTo, "utf", "gmt");
    }else{}
	}
	clearDownesScore = function(){
		this.cyanosis = null;
		this.retractions = null;
		this.grunting = null;
		this.airEntry = null;
		this.respiratoryRate = null;
		this.downesScoreTotalGrade = null;
	}
//		end of the above code
	refreshResp = function(){
		this.getDropDowns();
		this.getDataResp();
		this.clearDownesScore();
	}
  formatAMPM = function(date) {
		var hours = date.getHours();
		var mins = date.getMinutes();
		if(mins*1<10) {
			mins = "0"+mins;
		}
		var ampm = hours >= 12 ? 'PM' : 'AM';
		hours = hours % 12;
		hours = hours ? hours : 12;
		this.RDSTempObj.hours = hours+":"+mins;
		this.PphnTempObj.hours = hours+":"+mins;
		this.ApneaTempObj.hours = hours+":"+mins;
		this.PneumoTempObj.hours = hours+":"+mins;
		this.OtherTempObj.hours = hours+":"+mins;
		console.log(this.ApneaTempObj.hours);
		if(ampm=='PM'){
			this.RDSTempObj.meridian = false;
			this.PphnTempObj.meridian = false;
			this.ApneaTempObj.meridian = false;
			this.PneumoTempObj.meridian = false;
			this.OtherTempObj.meridian = false;
		}else if(ampm=='AM'){
			this.RDSTempObj.meridian = true;
			this.PphnTempObj.meridian = true;
			this.ApneaTempObj.meridian = true;
			this.PneumoTempObj.meridian = true;
			this.OtherTempObj.meridian = true;
		}
		this.RDSTempObj.hoursPrev = hours;
		this.RDSTempObj.meridianPrev = this.RDSTempObj.meridian;
		this.PphnTempObj.hoursPrev = hours;
		this.PphnTempObj.meridianPrev = this.PphnTempObj.meridian;
		this.ApneaTempObj.hoursPrev = hours;
		this.ApneaTempObj.meridianPrev = this.ApneaTempObj.meridian;
		this.PneumoTempObj.hoursPrev = hours;
		this.PneumoTempObj.meridianPrev = this.PneumoTempObj.meridian;
		this.OtherTempObj.hoursPrev = hours;
		this.OtherTempObj.meridianPrev = this.ApneaTempObj.meridian;
		this.calAgeAtOnsetOnChange('RDS');
	}
	calAgeAtOnsetOnChange = function(moduleValue){
		if(moduleValue == 'RDS'){
			if((this.RDSTempObj.hours!=null && this.RDSTempObj.hours!='') || this.RDSTempObj.hours==undefined){
				if(this.RDSTempObj.hours*1 < 1 || this.RDSTempObj.hours*1 > 12 || this.RDSTempObj.hours==undefined){
					$("#invalidTime").css("display","block");
				} else if(this.RDSTempObj.hours*1 >=1  || this.RDSTempObj.hours*1 <= 12){
					$("#invalidTime").css("display","none");
					var timeDate = new Date();
					var meridianOnset = 0;
					if(this.RDSTempObj.meridian!=true){
						meridianOnset = 12;
					}
					timeDate.setHours(this.RDSTempObj.hours*1+meridianOnset);
					timeDate.setMinutes(0);
					timeDate.setSeconds(0);
					timeDate.setMilliseconds(0);
					this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.timeofassessment = timeDate.getTime();
				}
			}
			this.creatingProgressNotesTemplate();
		}
		if(moduleValue == 'Pneumo'){
			if((this.PneumoTempObj.hours!=null && this.PneumoTempObj.hours!='') || this.PneumoTempObj.hours==undefined){
				if(this.PneumoTempObj.hours*1 < 1 || this.PneumoTempObj.hours*1 > 12 || this.PneumoTempObj.hours==undefined){
					$("#invalidTimePneumo").css("display","block");
				} else if(this.PneumoTempObj.hours*1 >=1  || this.PneumoTempObj.hours*1 <= 12){
					$("#invalidTimePneumo").css("display","none");
				}
			}
			this.creatingProgressNotesPneumoTemplate();
		}
		if(moduleValue == 'Other'){
			if((this.OtherTempObj.hours!=null && this.OtherTempObj.hours!='') || this.OtherTempObj.hours==undefined){
				if(this.OtherTempObj.hours*1 < 1 || this.OtherTempObj.hours*1 > 12 || this.OtherTempObj.hours==undefined){
					$("#invalidTime").css("display","block");
				} else if(this.OtherTempObj.hours*1 >=1  || this.OtherTempObj.hours*1 <= 12){
					$("#invalidTime").css("display","none");
					var timeDate = new Date();
					var meridianOnset = 0;
					if(this.OtherTempObj.meridian!=true){
						meridianOnset = 12;
					}
					timeDate.setHours(this.OtherTempObj.hours*1+meridianOnset);
					timeDate.setMinutes(0);
					timeDate.setSeconds(0);
					timeDate.setMilliseconds(0);
					this.respiratorySystemObj.respSystemObject.others.currentOther.timeofassessment = timeDate.getTime();
				}
			}
		}
	}
	calTimeOfAssessment = function (eventName) {
		if(eventName == 'Apnea'){
			var timeDate = new Date();
			var meridianOnset = 0;
			var timeStr = this.ApneaTempObj.hours.split(':');
			if(timeStr.length>1) {
				if(this.ApneaTempObj.meridian!=true){
					meridianOnset = 12;
				}
				timeDate.setHours(timeStr[0]*1+meridianOnset);
				timeDate.setMinutes(timeStr[1]*1);
			} else {
				if(this.ApneaTempObj.meridian!=true){
					meridianOnset = 12;
				}
				timeDate.setHours(timeStr[0]*1+meridianOnset);
				timeDate.setMinutes(0);
			}
			timeDate.setSeconds(0);
			timeDate.setMilliseconds(0);
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.timeofassessment = timeDate.getTime();
			console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.timeofassessment);
		}
		else if(eventName == 'Pphn'){
			var timeDate = new Date();
			var meridianOnset = 0;
			var timeStr = this.PphnTempObj.hours.split(':');
			if(timeStr.length>1) {
				if(this.PphnTempObj.meridian!=true){
					meridianOnset = 12;
				}
				timeDate.setHours(timeStr[0]*1+meridianOnset);
				timeDate.setMinutes(timeStr[1]*1);
			} else {
				if(this.PphnTempObj.meridian!=true){
					meridianOnset = 12;
				}
				timeDate.setHours(timeStr[0]*1+meridianOnset);
				timeDate.setMinutes(0);
			}
			timeDate.setSeconds(0);
			timeDate.setMilliseconds(0);
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.timeofassessment = timeDate.getTime();
			console.log(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.timeofassessment);
		}
	}
  SurfactantSelect = function(){
		this.isSurfactantSelect = true;
		this.isRespiratorySelect = false;
		this.isAntibioticSelect = false;
		this.isOtherSelect = false;
    this.isMedicationSelect = false;
	}
	RespiratorySelect = function(){
		this.isSurfactantSelect = false;
		this.isRespiratorySelect = true;
		this.isAntibioticSelect = false;
		this.isOtherSelect = false;
    this.isMedicationSelect = false;
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null
				|| this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.indexOf('TRE005')==-1){
			this.callRespiratorySupportPopUP('Respiratory Distress', false);
		}
	}
	AntibioticSelect = function(){
		this.isSurfactantSelect = false;
		this.isRespiratorySelect = false;
		this.isAntibioticSelect = true;
		this.isOtherSelect = false;
    this.isMedicationSelect = false;
	}
	OtherSelect = function(){
		this.isSurfactantSelect = false;
		this.isRespiratorySelect = false;
		this.isAntibioticSelect = false;
		this.isOtherSelect = true;
    this.isMedicationSelect = false;
  }
  medicationSelect = function(){
    this.isMedicationSelect = true;
    this.isSurfactantSelect = false;
		this.isRespiratorySelect = false;
		this.isAntibioticSelect = false;
		this.isOtherSelect = false;
	}
//		----------------------------------------------------APNEA Changes--------------------------------
	ApneaRespiratorySelect = function(){
		this.isApneaRespiratorySelect = true;
		this.isApneaCaffeineSelect = false;
		this.isApneaOtherSelect = false;
    this.isApneaMedicationsSelect =  false;
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList==null
				|| this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.indexOf('TRE007')==-1){
			this.callRespiratorySupportPopUP('Apnea', false);
		}
	}
	ApneaCaffeineSelect = function(){
		this.isApneaRespiratorySelect = false;
		this.isApneaCaffeineSelect = true;
		this.isApneaOtherSelect = false;
    this.isApneaMedicationsSelect =  false;
	}
	ApneaOtherSelect = function(){
		this.isApneaRespiratorySelect = false;
		this.isApneaCaffeineSelect = false;
		this.isApneaOtherSelect = true;
    this.isApneaMedicationsSelect =  false;
	}
  ApneaMedicationsSelect = function(){
    this.isApneaRespiratorySelect = false;
    this.isApneaCaffeineSelect = false;
    this.isApneaOtherSelect = false;
    this.isApneaMedicationsSelect =  true;
  }
  initTreatmentVariablesApnea = function(){
		this.isApneaRespiratorySelect = false;
		this.isApneaCaffeineSelect = false;
		this.isApneaOtherSelect = false;
		if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea != null
				&& this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length > 0
				&& this.respiratorySystemObj.respSystemObject.apnea.pastApnea["0"].actiontype != null){
			this.apneaPreviousTreatment = this.respiratorySystemObj.respSystemObject.apnea.pastApnea["0"];
			this.treatmentVisiblePastApnea = true;
			this.treatmentApneaTabsVisible = false;
			this.apneaTreatmentStatus = undefined;
		} else {
			this.treatmentApneaTabsVisible = true;
			this.treatmentVisiblePastApnea = false;
			if(this.respiratorySystemObj.respSystemObject.respSupport != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
				console.log('respiratory support object Apnea');
				var symptomArr = [];
				symptomArr.push('TRE007');
				this.isApneaRespiratorySaved = true;
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = symptomArr;
			}
		}
		this.creatingProgressNotesApneaTemplate();
	}
	initTreatmentRedirectApnea = function(){
		this.treatmentVisiblePastApnea = true;
		this.treatmentApneaTabsVisible = true;
		this.apneaTreatmentStatus = 'Change';
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList != null
				&& this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.length > 0) {
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('TRE007')) {
				this.isApneaRespiratorySaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('TRE008')) {
				this.isApneaCaffeineSaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('other')) {
				this.actionSectionApneaVisible();
				this.isApneaOtherSelect = true;
				this.isApneaOtherSaved = true;
			}
		}
		this.creatingProgressNotesApneaTemplate();
	}
	continueTreatmentApnea = function(){
		this.treatmentApneaTabsVisible = false;
		this.isApneaRespiratorySelect = false;
		this.isApneaCaffeineSelect = false;
		this.isApneaOtherSelect = false;
		this.apneaTreatmentStatus = 'Continue';
		this.apneaPreviousTreatment = this.respiratorySystemObj.respSystemObject.apnea.pastApnea["0"];
		var symptomArr = [];
		var treatmentApneaList = this.apneaPreviousTreatment.actiontype.replace("[","").replace("]","").replace(" ","").split(",");
		for(var index = 0; index<treatmentApneaList.length; index++){
			if(treatmentApneaList[index].trim()!='' && treatmentApneaList[index].trim()!='other'){
				symptomArr.push(treatmentApneaList[index]);
			}
		}
		if(!symptomArr.includes('TRE007')) {
			if(this.respiratorySystemObj.respSystemObject.respSupport != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
				console.log('respiratory support object Apnea');
				symptomArr.push('TRE007');
			}
		}
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = symptomArr;
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes("TRE008")){
			if(this.apneaPreviousTreatment.caffeineAction == 'Stop') {
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = 'Start';
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.pop('TRE008');
			} else {
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = 'Continue';
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute = this.apneaPreviousTreatment.caffeineRoute;
				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose = this.apneaPreviousTreatment.caffeineMaintenanceDose;
			}
		} else {
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = 'Start';
		}
		console.log(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList);
		this.creatingProgressNotesApneaTemplate();
	}
//		set new values for treatment
	changeTreatmentApnea = function() {
		this.apneaTreatmentStatus = 'Change';
		this.treatmentApneaTabsVisible = true;
		this.apneaPreviousTreatment = this.respiratorySystemObj.respSystemObject.apnea.pastApnea["0"];
		var symptomArr = [];
		var treatmentApneaList = this.apneaPreviousTreatment.actiontype.replace("[","").replace("]","").replace(" ","").split(",");
		for(var index = 0; index<treatmentApneaList.length; index++){
			if(treatmentApneaList[index].trim()!='' && treatmentApneaList[index].trim()!='other'){
				symptomArr.push(treatmentApneaList[index]);
			}
		}
		if(!symptomArr.includes('TRE007')) {
			if(this.respiratorySystemObj.respSystemObject.respSupport != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
				console.log('respiratory support object Apnea');
				symptomArr.push('TRE007');
			}
		}
		this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList = symptomArr;
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null) {
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('TRE007')) {
				this.isApneaRespiratorySaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes("TRE008")){
				if(this.apneaPreviousTreatment.caffeineAction != 'Stop') {
					this.isApneaCaffeineSaved = true;
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = 'Continue';
  				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineRoute = this.apneaPreviousTreatment.caffeineRoute;
  				this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineMaintenanceDose = this.apneaPreviousTreatment.caffeineMaintenanceDose;
				} else {
					this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.pop('TRE008');
				}
			}
		}
		this.creatingProgressNotesApneaTemplate();
	}
	saveTreatmentApnea = function(treatmentType){
	  switch(treatmentType)
		{
		case 'caffeine':
			if((this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('TRE008'))){
				this.onCheckMultiCheckBoxValue('TRE008','apneaTreatment',true);
				this.isApneaCaffeineSaved = true;
			} else if((this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction=='Start')
					&& (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose == null
							|| this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineBolusDose == "")) {
				this.onCheckMultiCheckBoxValue('TRE008','apneaTreatment',false);
				this.isApneaCaffeineSaved = false;
			}
			break;
		case 'respiratory':
			if((this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('TRE007'))){
				this.onCheckMultiCheckBoxValue('TRE007','apneaTreatment',true);
				this.isApneaRespiratorySaved = true;
			}
			break;
		case 'other':
			if((this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentActionList.includes('other'))){
				this.onCheckMultiCheckBoxValue('other','apneaTreatment',true);
				this.isApneaOtherSaved = true;
			}else if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentOther == null
					|| this.respiratorySystemObj.respSystemObject.apnea.currentApnea.treatmentOther == "") {
				this.onCheckMultiCheckBoxValue('other','apneaTreatment',false);
				this.isApneaOtherSaved = false;
			}
			break;
		}
		this.creatingProgressNotesApneaTemplate();
	}
	stopApneaRespSupport = function() {
		this.onCheckMultiCheckBoxValue('TRE007','apneaTreatment',false);
	}
  calculateAgeAtAssessmentApnea = function() {
		var meridianOnset = 0;
		if(this.ApneaTempObj.meridian != true){
			meridianOnset = 12;
		}
		if(this.ApneaTempObj.hours != undefined) {
			var timeStr = this.ApneaTempObj.hours.split(':');
			var hrs = 0;
			if(timeStr[0] * 1 != 12) {
				hrs = timeStr[0] * 1;
			}
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment =
				(this.respiratorySystemObj.ageAtOnset - this.currentHrs) + (hrs + meridianOnset);
		}
	}
	ageOnAssessmentCalculationApnea = function(){
		if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours && this.ageAtAssessmentApneaHourFlag){
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment *= 24;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment += this.hourDayDiffAgeAtAssessmentApnea;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment);
			this.ageAtAssessmentApneaHourFlag = false;
		}else if(!(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isageofassesmentinhours || this.ageAtAssessmentApneaHourFlag)){
			this.ageAtAssessmentApneaHourFlag = true;
			this.hourDayDiffAgeAtAssessmentApnea = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment%24;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment -= this.hourDayDiffAgeAtAssessmentApnea;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment /= 24;
			this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.ageatassesment);
		}
	}
  calculateAgeAtAssessmentPphn = function() {
		var meridianOnset = 0;
		if(this.PphnTempObj.meridian != true){
			meridianOnset = 12;
		}
		if(this.PphnTempObj.hours != undefined) {
			var timeStr = this.PphnTempObj.hours.split(':');
			var hrs = 0;
			if(timeStr[0] * 1 != 12) {
				hrs = timeStr[0] * 1;
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment =
				(this.respiratorySystemObj.ageAtOnset - this.currentHrs) + (hrs + meridianOnset);
		}
	}
	ageOnSetCalculation = function(){
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours && this.PphnTempObj.ageAtAssessmentPphnHourFlag){
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment *= 24;
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment += this.hourDayDiffAgeAtAssessment;
			this.PphnTempObj.ageAtAssessmentPphnHourFlag = false;
		}else if(!(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isageofassesmentinhours || this.PphnTempObj.ageAtAssessmentPphnHourFlag)){
			this.PphnTempObj.ageAtAssessmentPphnHourFlag = true;
			this.hourDayDiffAgeAtAssessment = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment%24;
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment -= this.hourDayDiffAgeAtAssessment;
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.ageatassesment /= 24;
		}
	}
	calculateAgeAtAssessmentRds = function() {
		var meridianOnset = 0;
		if(this.RDSTempObj.meridian != true){
			meridianOnset = 12;
		}
		if(this.RDSTempObj.hours != undefined) {
			var timeStr = this.RDSTempObj.hours.split(':');
			var hrs = 0;
			if(timeStr[0] * 1 != 12) {
				hrs = timeStr[0] * 1;
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment =
				(this.respiratorySystemObj.ageAtOnset - this.currentHrs) + (hrs + meridianOnset);
		}
	}
  ageOnAssessmentCalculationRds = function(){
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours && this.ageAtAssessmentRdsHourFlag){
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment *= 24;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment += this.hourDayDiffAgeAtAssessmentRds;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment);
			this.ageAtAssessmentRdsHourFlag = false;
		}else if(!(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isageofassesmentinhours || this.ageAtAssessmentRdsHourFlag)){
			this.ageAtAssessmentRdsHourFlag = true;
			this.hourDayDiffAgeAtAssessmentRds = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment%24;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment -= this.hourDayDiffAgeAtAssessmentRds;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment /= 24;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.ageatassesment);
		}
	}
	calSurfactantMl = function(){
		var childObject = JSON.parse(localStorage.getItem('selectedChild'));
		console.log(childObject);
		var weight = 0;
		this.surfactantDoseMessage = "";
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname == 'survanta' && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose*1 >4){
			this.surfactantDoseMessage = "Cannot be greater than 4";
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose = "";
		}else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname == 'curosurf' && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose*1 > 2.5){
			this.surfactantDoseMessage = "Cannot be greater than 2.5";
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose = "";

		}else if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname == 'neosurf' && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose*1 > 5){
			this.surfactantDoseMessage = "Cannot be greater than 5";
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose = "";
		}else{
			if(childObject.todayWeight!=null && childObject.todayWeight!=''
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=''){
				weight = childObject.todayWeight/1000;
			} else {
				weight = childObject.workingWeight/1000;
			}
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=''){
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDoseMl =
					Math.round((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose * weight)*100)/100+"";
			} else {
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDoseMl = null;
			}
			this.saveTreatmentRds('rdsSurfactant');
		}
	}
	rdsOtherCause = function(){
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList!=null){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.includes('other')){
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther!=null){
					this.causeOfRespDistressStr = "";
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList!=null
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.length>0){
						this.symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList;
					}
					for(var indexList = 0; indexList<this.symptomsArr.length;indexList++){
						for(var indexCause=0;indexCause<this.respiratorySystemObj.dropDowns.causeOfRds.length;indexCause++){
							if(this.symptomsArr[indexList]== this.respiratorySystemObj.dropDowns.causeOfRds[indexCause].key){
								if(this.symptomsArr[indexList]=="other"){
									if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther!=''){
										if(this.causeOfRespDistressStr==''){
											this.causeOfRespDistressStr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther;
										}else{
											this.causeOfRespDistressStr +=", " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrdsOther;
										}
									}
								} else {
									if(this.causeOfRespDistressStr==''){
										this.causeOfRespDistressStr = this.respiratorySystemObj.dropDowns.causeOfRds[indexCause].value;
									}else{
										this.causeOfRespDistressStr +=", " + this.respiratorySystemObj.dropDowns.causeOfRds[indexCause].value;
									}
								}
							}
						}
					}
				}
			}
		}
		this.creatingProgressNotesTemplate();
	}
	rdsOtherRisk = function(){
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList!=null){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList.includes('otherRisk')){
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther!=null){
					this.riskRespDistressStr = "";
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList!=null
							&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList.length>0){
						this.symptomsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorList;
					}
					for(var indexList = 0; indexList<this.symptomsArr.length;indexList++){
						for(var indexCause=0;indexCause<this.respiratorySystemObj.dropDowns.riskFactorRds.length;indexCause++){
							if(this.symptomsArr[indexList]== this.respiratorySystemObj.dropDowns.riskFactorRds[indexCause].key){
								if(this.symptomsArr[indexList]=="otherRisk"){
									if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther!=''){
										if(this.riskRespDistressStr==''){
											this.riskRespDistressStr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther;
										}else{
											this.riskRespDistressStr +=", " + this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactorOther;
										}
									}
								} else {
									if(this.riskRespDistressStr==''){
										this.riskRespDistressStr = this.respiratorySystemObj.dropDowns.riskFactorRds[indexCause].value;
									}else{
										this.riskRespDistressStr +=", " + this.respiratorySystemObj.dropDowns.riskFactorRds[indexCause].value;
									}
								}
							}
						}
					}
				}
			}
		}
		this.creatingProgressNotesTemplate();
	}

  pphnOtherRisk = function(){
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList!=null){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList.includes('otherRisk')){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther!=null){
					this.riskPphnStr = "";
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList!=null
							&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList.length>0){
						this.symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorList;
					}
					for(var indexList = 0; indexList<this.symptomsArr.length;indexList++){
						for(var indexCause=0;indexCause<this.respiratorySystemObj.dropDowns.riskFactorPphn.length;indexCause++){
							if(this.symptomsArr[indexList]== this.respiratorySystemObj.dropDowns.riskFactorPphn[indexCause].key){
								if(this.symptomsArr[indexList]=="otherRisk"){
									if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther!=''){
										if(this.riskPphnStr==''){
											this.riskPphnStr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther;
										}else{
											this.riskPphnStr +=", " + this.respiratorySystemObj.respSystemObject.pphn.currentPphn.riskfactorOther;
										}
									}
								} else {
									if(this.riskPphnStr==''){
										this.riskPphnStr = this.respiratorySystemObj.dropDowns.riskFactorPphn[indexCause].value;
									}else{
										this.riskPphnStr +=", " + this.respiratorySystemObj.dropDowns.riskFactorPphn[indexCause].value;
									}
								}
							}
						}
					}
				}
			}
		}
		this.creatingProgressNotesPphnTemplate();
	}

  pneumoOtherRisk = function(){
    if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList!=null){
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList.includes('otherRisk')){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther!=null){
          this.riskPneumoStr = "";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList!=null
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList.length>0){
            this.symptomsArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList;
          }
          for(var indexList = 0; indexList<this.symptomsArr.length;indexList++){
            for(var indexCause=0;indexCause<this.respiratorySystemObj.dropDowns.riskFactorPneumo.length;indexCause++){
              if(this.symptomsArr[indexList]== this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexCause].key){
                if(this.symptomsArr[indexList]=="otherRisk"){
                  if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther!=''){
                    if(this.riskPneumoStr==''){
                      this.riskPneumoStr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther;
                    }else{
                      this.riskPneumoStr +=", " + this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther;
                    }
                  }
                } else {
                  if(this.riskPneumoStr==''){
                    this.riskPneumoStr = this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexCause].value;
                  }else{
                    this.riskPneumoStr +=", " + this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexCause].value;
                  }
                }
              }
            }
          }
        }
      }
    }
    this.creatingProgressNotesPneumoTemplate();
  }

	initTreatmentRedirectRds = function(){
		this.treatmentVisiblePastRds = true;
		this.treatmentTabsVisible = true;
		this.rdsTreatmentStatus.value = 'Change';
		console.log("initTreatmentRedirectRds");
		console.log(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList);
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList != null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.length > 0) {
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE004')) {
				this.isSurfactantSaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')) {
				this.isRespiratorySaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE006')) {
				this.isAntibioticSaved = true;
			}
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('other')) {
				this.isOtherSelect = true;
				this.isOtherSaved = true;
			}
		}
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0){
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress["0"].treatmentaction!=null){
				this.treatmentVisiblePastRds = true;
			}else{
				this.treatmentVisiblePastRds = false;
			}
		} else {
			this.treatmentTabsVisible = true;
			this.treatmentVisiblePastRds = false;
		}
		this.creatingProgressNotesTemplate();
	}
	initTreatmentVariablesRds = function(){
		//init all tabs status
		this.isSurfactantSelect = false;
		this.isSurfactantSaved = false;
		this.isRespiratorySelect = false;
		this.isRespiratorySaved = false;
		this.isAntibioticSelect = false;
		this.isAntibioticSaved = false;
		this.isOtherSelect = false;
		this.isOtherSaved = false;
		this.treatmentTabsVisible = true;
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress["0"].treatmentaction!=null){
			this.treatmentVisiblePastRds = true;
			this.treatmentTabsVisible = false;
		} else {
			this.treatmentTabsVisible = true;
			this.treatmentVisiblePastRds = false;
			if(this.respiratorySystemObj.respSystemObject.respSupport != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
				console.log('respiratory support object RDS');
				var symptomArr = [];
				symptomArr.push('TRE005');
				this.isRespiratorySaved = true;
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = symptomArr;
			}
		}
	}
	continueTreatmentRds = function(){
		this.treatmentTabsVisible = false;
		this.treatmentSelectedText = this.pastTreatmentSelectedText;
		this.rdsTreatmentStatus.value = 'Continue';
		var previousTreatment = this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress["0"];
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction = previousTreatment.treatmentaction;
		var treatmentRdsList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");
		var symptomArr = [];
		for(var index = 0; index<treatmentRdsList.length; index++){
			if(treatmentRdsList[index].trim()!='' && treatmentRdsList[index].trim()=="TRE005"){
				symptomArr.push(treatmentRdsList[index].trim());
			}
		}
		if(!symptomArr.includes('TRE005')) {
			if(this.respiratorySystemObj.respSystemObject.respSupport != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
					&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
				console.log('respiratory support object RDS');
				symptomArr.push('TRE005');
			}
		}
		this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = symptomArr;
		console.log(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList);
		this.creatingProgressNotesTemplate();
		this.isSurfactantSelect = false;
		this.isRespiratorySelect = false;
		this.isAntibioticSelect = false;
		this.isOtherSelect = false;
		this.isSurfactantSaved = false;
		this.isRespiratorySaved = false;
		this.isAntibioticSaved = false;
		this.isOtherSaved = false;
	}

//		set new values for treatment
	changeTreatmentRds = function() {
		this.rdsTreatmentStatus.value = 'Change';
		this.treatmentTabsVisible = true;
		this.treatmentSelectedText = '';
		if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null
				&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0){
			var pastRespDistress = this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0];
			if(pastRespDistress.treatmentaction!=null && pastRespDistress.treatmentaction!=''){
				var prevTreatmentRdsList = pastRespDistress.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");
				var symptomArr = [];
				for(var index = 0; index<prevTreatmentRdsList.length; index++){
					if(prevTreatmentRdsList[index].trim()!='' && prevTreatmentRdsList[index].trim()=="TRE005" && this.respiratorySystemObj.respSystemObject.respSupport.isactive == true){
						symptomArr.push(prevTreatmentRdsList[index].trim());
					}
				}
			}
			if(!symptomArr.includes('TRE005')) {
				if(this.respiratorySystemObj.respSystemObject.respSupport != null
						&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
						&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
					console.log('respiratory support object RDS');
					symptomArr.push('TRE005');
				}
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = symptomArr;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes("TRE005")){
				this.isRespiratorySaved = true;
			}
		}
		this.creatingProgressNotesTemplate();
	}
  onInsureContResp = function(){
		this.fromInsureCont = true;
		this.saveTreatmentRds('rdsSurfactant');
		this.fromInsureCont = false;
	}
	saveTreatmentRds = function(treatmentType){
		switch(treatmentType)
		{
		case 'rdsSurfactant':
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname!=''
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose!=''){
				if((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null)
						|| (this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
								&& !this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE004'))){
					this.onCheckMultiCheckBoxValue('TRE004','treatmentAction',true);
					this.isSurfactantSelect = false;
					this.isSurfactantSaved = true;
				}
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone!=null){
					if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005')){
						if(this.fromInsureCont==true){
							this.callRespiratorySupportPopUP('Respiratory Distress', true);
						}
					} else {
						if(this.fromInsureCont==true){
							this.callRespiratorySupportPopUP('Respiratory Distress', false);
						}
					}
				}
			} else {
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE004')){
					this.onCheckMultiCheckBoxValue('TRE004','treatmentAction',false);
					this.isSurfactantSelect = true;
					this.isSurfactantSaved = false;
				}
			}
			break;
		case 'rdsRespiratory':
			if((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE005'))){
				this.onCheckMultiCheckBoxValue('TRE005','treatmentAction',true);
			}
			this.isRespiratorySaved = true;
			break;
		case 'rdsAntibiotics':
			var allRequired = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList.length>0){
				var antibioticArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList;
				for(var index=0; index<antibioticArr.length;index++){
					if(antibioticArr[index].route!=null && antibioticArr[index].route!='' && antibioticArr[index].dose!=null && antibioticArr[index].dose!=''){
						allRequired = true;
					} else {
						allRequired = false;
						break;
					}
				}
			}
			if(allRequired){
				if((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null)
						|| (this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
								&& !this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE006'))){
					this.onCheckMultiCheckBoxValue('TRE006','treatmentAction',true);
					this.isAntibioticSaved = true;
				}
			} else {
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE006')){
					this.onCheckMultiCheckBoxValue('TRE006','treatmentAction',false);
					this.isAntibioticSaved = false;
				}
			}
			break;
		case 'rdsOther':
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther!=''){
				if((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList==null)
						|| (this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
								&& !this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('other'))){
					this.onCheckMultiCheckBoxValue('other','treatmentAction',true);
				}
				this.isOtherSaved = true;
			} else {
				if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
						&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('other')){
					this.onCheckMultiCheckBoxValue('other','treatmentAction',false);
					this.isOtherSelect = true;
					this.isOtherSaved = false;
				}
			}
			break;
		}
		this.creatingProgressNotesTemplate();
	}
	clearTreatmentRds = function(treatmentType){
		if(treatmentType == 'rdsSurfactant')
		{
			this.isSurfactantSaved = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE004')){
				this.onCheckMultiCheckBoxValue('TRE004','treatmentAction',false);
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.sufactantname = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isinsuredone = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rsInsureType = null;
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDoseMl = null;
			this.isSurfactantInsure = false;
		}
		else if(treatmentType == 'respSupport')
		{
			this.showRespSupportYesNoPopUP('Respiratory Distress');
		}
		else if(treatmentType == 'rdsAntibiotics')
		{
			this.isAntibioticSaved = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('TRE006')){
				this.onCheckMultiCheckBoxValue('TRE006','treatmentAction',false);
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList = null;
			}
			for(var index=0; index<this.respiratorySystemObj.dropDowns.medicine.length;index++){
				this.respiratorySystemObj.dropDowns.medicine[index].flag = false;
			}
			this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList = null;
		}
		else if(treatmentType == 'rdsOther')
		{
			this.isOtherSaved = false;
			if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList.includes('other')){
				this.onCheckMultiCheckBoxValue('other','treatmentAction',false);
				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentOther = null;
			}
		}
		this.creatingProgressNotesTemplate();
	}
  surfactantSelectPphn = function(){
		this.PphnTempObj.isSurfactantSelectPphn = true;
		this.PphnTempObj.isVasodilatorsSelectPphn = false;
		this.PphnTempObj.isRespSelectPphn = false;
		this.PphnTempObj.isOtherSelectPphn = false;
    this.PphnTempObj.isMedicationSelectPphn = false;
	}
	vasodilatorsSelectPphn = function(){
		this.PphnTempObj.isSurfactantSelectPphn = false;
		this.PphnTempObj.isVasodilatorsSelectPphn = true;
		this.PphnTempObj.isRespSelectPphn = false;
		this.PphnTempObj.isOtherSelectPphn = false;
    this.PphnTempObj.isMedicationSelectPphn = false;
	}
  respSelectPphn = function(comingFrom){
		this.PphnTempObj.isVasodilatorsSelectPphn = false;
		this.PphnTempObj.isOtherSelectPphn = false;
    this.PphnTempObj.isMedicationSelectPphn = false;
		if(comingFrom=="respSupport"){
			this.PphnTempObj.isSurfactantSelectPphn = false;
			this.PphnTempObj.isRespSelectPphn = true;
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList==null
					|| this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.length==0 || this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')==-1){
				this.PphnTempObj.isRespSupportisInsure = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone;
				this.callRespiratorySupportPopUP('PPHN', false);
			}
		}else{
			this.PphnTempObj.isSurfactantSelectPphn = true;
			this.PphnTempObj.isRespSelectPphn = false;
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010')!=-1){
				this.callRespiratorySupportPopUP('PPHN', true);
			}else{
				this.callRespiratorySupportPopUP('PPHN', false);
			}
		}
	}
	otherSelectPphn = function(){
		this.PphnTempObj.isSurfactantSelectPphn = false;
		this.PphnTempObj.isVasodilatorsSelectPphn = false;
		this.PphnTempObj.isRespSelectPphn = false;
		this.PphnTempObj.isOtherSelectPphn = true;
    this.PphnTempObj.isMedicationSelectPphn = false;
	}
  medicationsSelectPphn = function(){
    this.PphnTempObj.isSurfactantSelectPphn = false;
    this.PphnTempObj.isVasodilatorsSelectPphn = false;
    this.PphnTempObj.isRespSelectPphn = false;
    this.PphnTempObj.isOtherSelectPphn = false;
    this.PphnTempObj.isMedicationSelectPphn = true;
  }
  systemValue = function(Value,multiCheckBoxName){
		if(multiCheckBoxName=='actionType'){
			this.symptomsArr = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				if(!this.symptomsArr.includes(Value) && Value!='')
					this.symptomsArr.push(Value);
			}
			if(this.symptomsArr==null){
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = [];
			}else{
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = this.symptomsArr;
			}
			this.saveTreatmentPphn('vasodilator');
		}
		this.creatingProgressNotesPphnTemplate();
	}
	updateTreatment = function(updateType) {
		var previousTreatment = this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0];
		var tempTreatment = previousTreatment.treatmentaction;
    var treatmentPphnList = '';
    if(previousTreatment.treatmentaction != null) {
      treatmentPphnList = previousTreatment.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");
    }
	  var symptomArr = [];
		if(tempTreatment.includes("TRE010") && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null
				&& this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=''){
			symptomArr.push("TRE010");
			this.isRespSavedPphn = true;
			this.PphnTempObj.isRespSelectPphn =true;
		} else if(this.respiratorySystemObj.respSystemObject.respSupport != null
				&& this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
				&& this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
			console.log('respiratory support object PPHN');
			symptomArr.push("TRE010");
			this.isRespSavedPphn = true;
			this.PphnTempObj.isRespSelectPphn =true;
		}
		this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = symptomArr;
		if(updateType=='continue'){
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.length==0){
				this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText = "previous treatment continued";
			}
		}else if(updateType=='change'){}
		this.creatingProgressNotesPphnTemplate();
	}
	saveTreatmentPphn = function(treatmentType){
		console.log('saveTreatmentPphn ' + treatmentType);
		switch(treatmentType)
		{
		case 'respiratory':
			if((this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE010'))){
				this.onCheckMultiCheckBoxValue('TRE010','pphnTreatmentAction',true);
				this.isRespSavedPphn = true;
			}
			break;
		case 'surfactant':
			if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname!=''
						&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose!=null
						&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose!=''){
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone!=null) {
					if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isinsuredone==true){
						if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant == null
								|| this.respiratorySystemObj.respSystemObject.pphn.currentPphn.actionAfterSurfactant == ''){
							if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
									&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE009')){
								this.onCheckMultiCheckBoxValue('TRE009','pphnTreatmentAction',false);
								this.PphnTempObj.isSurfactantSavedPphn = false;
							}
						}
					} else {
						console.log("in insure else");
						this.callRespiratorySupportPopUP('PPHN', false);
					}
				}
			} else {
				if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
						&& this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('TRE009')){
					this.onCheckMultiCheckBoxValue('TRE009','pphnTreatmentAction',false);
					this.PphnTempObj.isSurfactantSavedPphn = false;
				}
			}
			this.PphnTempObj.isSurfactantSavedPphn = true;
			break;
		case 'antibiotic':
			if(this.respiratorySystemObj.respSystemObject.pphn.antibioticsList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.antibioticsList.length>0){
				this.PphnTempObj.isAntibioticsSavedPphn = true;
			}
			break;
		case 'inotropes':
			if(this.respiratorySystemObj.respSystemObject.pphn.inoList != null
					&& this.respiratorySystemObj.respSystemObject.pphn.inoList.length > 0){
				this.PphnTempObj.isInotropesSavedPphn = true;
			}
			break;
		case 'sedation':
			if(this.respiratorySystemObj.respSystemObject.pphn.sedationList!=null
					&& this.respiratorySystemObj.respSystemObject.pphn.sedationList.length>0){
				this.PphnTempObj.isSedationSavedPphn = true;
			}
			break;
		case 'vasodilator':
			this.PphnTempObj.isVasodilatorsSavedPphn = true;
			if((this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route == null
					|| this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.route == "")
					&& (this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose == null
							|| this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.dose == "")
							&& (this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.frequency == null
									|| this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj.frequency == "")) {
				this.PphnTempObj.isVasodilatorsSavedPphn = false;
			}
			break;
		case 'Other':
			if((this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList==null)
					|| (this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList!=null
							&& !this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList.includes('Other'))){
				this.onCheckMultiCheckBoxValue('Other','pphnTreatmentAction',true);
				this.PphnTempObj.isOtherSavedPphn = true;
			}else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther == null
					|| this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentOther == "") {
				this.onCheckMultiCheckBoxValue('Other','pphnTreatmentAction',false);
				this.PphnTempObj.isOtherSavedPphn = false;
			}
			break;
		}
		this.creatingProgressNotesPphnTemplate();
	}
	stopPphnRespSupport = function() {
		this.onCheckMultiCheckBoxValue('TRE010','pphnTreatmentAction',false);
	}
	calSurfactantMlPphn = function(){
		console.log(this.childObject);
		var weight = 0;
		this.surfactantDosePphnMessage = "";
		if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname == 'survanta' && this.PphnTempObj.surfactantDoseMlKg*1 >4){
			this.surfactantDosePphnMessage = "Cannot be greater than 4";
			this.PphnTempObj.surfactantDoseMlKg = "";
		}else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname == 'curosurf' && this.PphnTempObj.surfactantDoseMlKg*1 > 2.5){
			this.surfactantDosePphnMessage = "Cannot be greater than 2.5";
			this.PphnTempObj.surfactantDoseMlKg = "";
		}else if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.sufactantname == 'neosurf' && this.PphnTempObj.surfactantDoseMlKg*1 > 5){
			this.surfactantDosePphnMessage = "Cannot be greater than 5";
			this.PphnTempObj.surfactantDoseMlKg = "";
		}else{
			if(this.childObject.todayWeight!=null && this.childObject.todayWeight!=''
				&& this.PphnTempObj.surfactantDoseMlKg!=null && this.PphnTempObj.surfactantDoseMlKg!=''){
				weight = this.childObject.todayWeight/1000;
			} else {
				weight = this.childObject.workingWeight/1000;
			}
			this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose =
				Math.round((this.PphnTempObj.surfactantDoseMlKg * weight)*100)/100+"";
			this.saveTreatmentPphn('surfactant');
		}
	}
//		----------------------------------end of pphn Treatment-------------------------------------------
  redirectToPrescription = function(eventName) {
    localStorage.setItem('medicationAssessment',JSON.stringify(eventName));
    localStorage.setItem('currentRespSystemObj',JSON.stringify(this.respiratorySystemObj));
    if(eventName=="RDS") {
      localStorage.setItem('RDSTempObj',JSON.stringify(this.RDSTempObj));
      localStorage.setItem('prescriptionList',JSON.stringify(this.respiratorySystemObj.respSystemObject.respiratoryDistress.prescriptionList));
    } else if(eventName=="Apnea") {
      localStorage.setItem('ApneaTempObj',JSON.stringify(this.ApneaTempObj));
      localStorage.setItem('orderSelectedTextStr',JSON.stringify(this.orderSelectedText));
      localStorage.setItem('prescriptionList',JSON.stringify(this.respiratorySystemObj.respSystemObject.apnea.prescriptionList));
    } else if(eventName=="PPHN") {
      localStorage.setItem('PphnTempObj',JSON.stringify(this.PphnTempObj));
      localStorage.setItem('prescriptionList',JSON.stringify(this.respiratorySystemObj.respSystemObject.pphn.prescriptionList));
    } else if(eventName=="Pneumothorax") {
      localStorage.setItem('PneumoTempObj',JSON.stringify(this.PneumoTempObj));
      localStorage.setItem('prescriptionList',JSON.stringify(this.respiratorySystemObj.respSystemObject.pneumothorax.prescriptionList));
    }
    this.router.navigateByUrl('/med/medications');
  }
  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }
  setPresRedirectVariable = function(){
    var respiratorySystemObj = JSON.parse(localStorage.getItem('currentRespSystemObj'));
    if(respiratorySystemObj != null){
      this.respiratorySystemObj = respiratorySystemObj;
      var prescriptionList =  JSON.parse(localStorage.getItem('prescriptionList'));
      var medStr = "";
      var medStartStr = "";
      var medStopStr = "";
      var medContStr = "";
      var medRevStr = "";

      for(var index=0; index < prescriptionList.length; index++) {
        var medicine = prescriptionList[index];
        var route = null;
        if(medicine.route == 'IV' || medicine.route == 'IM'){
          route = " Inj. "
        }else if(medicine.route == 'PO'){
          route = " Syrup "
        }else{
          route = "";
        }
        if(medicine.isContinueMedication == true){
          medContStr += ", " + route + medicine.medicinename;
        }else if(medicine.isEdit == true){
          medRevStr += ", " + route + medicine.medicinename;
        }else if(medicine.isactive == false){
          medStopStr += ", " + route + medicine.medicinename;
        }else if(medicine.refBabyPresid == null){
          medStartStr += ", " + route + medicine.medicinename;
        }
      }

      if(medStartStr != '') {
        medStr += "Started: " + medStartStr.substring(2, medStartStr.length);
      }

      if(medStopStr != '') {
        if(medStr == '') {
          medStr += "Stopped: " + medStopStr.substring(2, medStopStr.length);
        } else {
          medStr += ", Stopped: " + medStopStr.substring(2, medStopStr.length);
        }
      }

      if(medRevStr != '') {
        if(medStr == '') {
          medStr += "Revised: " + medRevStr.substring(2, medRevStr.length);
        } else {
          medStr += ", Revised: " + medRevStr.substring(2, medRevStr.length);
        }
      }

      if(medContStr != '') {
        if(medStr == '') {
          medStr += "Continued: " + medContStr.substring(2, medContStr.length);
        } else {
          medStr += ", Continued: " + medContStr.substring(2, medContStr.length);
        }
      }

      var assessmentName = JSON.parse(localStorage.getItem('assessmentName'));
      if(assessmentName == 'RDS'){
        this.respiratoryDistressVisible();
        this.medicationSelect();
        this.RDSTempObj = JSON.parse(localStorage.getItem('RDSTempObj'));
        this.respiratorySystemObj.respSystemObject.respiratoryDistress.prescriptionList = prescriptionList;
        this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.medicationStr = medStr;
      } else if(assessmentName == 'Apnea'){
        this.apneaDistressVisible();
        this.actionSectionApneaVisible();
        this.treatmentApneaTabsVisible = true;
        this.ApneaMedicationsSelect();
        this.ApneaTempObj = JSON.parse(localStorage.getItem('ApneaTempObj'));
        this.orderSelectedText = JSON.parse(localStorage.getItem('orderSelectedTextStr'));
        localStorage.removeItem('orderSelectedTextStr');
        this.respiratorySystemObj.respSystemObject.apnea.prescriptionList = prescriptionList;
        this.respiratorySystemObj.respSystemObject.apnea.currentApnea.medicationStr = medStr;
      } else if(assessmentName == 'PPHN'){
        this.PPHNVisible();
        this.actionSectionPPHNVisible();
        this.isActionPPHNVisible = true;
        this.PphnTempObj = JSON.parse(localStorage.getItem('PphnTempObj'));
        localStorage.removeItem("PphnTempObj");
        this.medicationsSelectPphn();
        this.respiratorySystemObj.respSystemObject.pphn.prescriptionList = prescriptionList;
        this.respiratorySystemObj.respSystemObject.pphn.currentPphn.medicationStr = medStr;
      } else if(assessmentName == 'Pneumothorax'){
        this.PulmonaryDistressVisible();
        this.actionSectionPhenumothoraxVisible();
        this.isMedicationSelectPneumo = true;
        this.medicationSelectPneumo();
        this.PneumoTempObj = JSON.parse(localStorage.getItem('PneumoTempObj'));
        this.respiratorySystemObj.respSystemObject.pneumothorax.prescriptionList = prescriptionList;
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr = medStr;
      }
    }
    localStorage.removeItem("assessmentName");
    localStorage.removeItem("prescriptionList");
    localStorage.removeItem("currentRespSystemObj");
  }
// ------------------------------------ prescription redirect function end -------------------------------------------
  validateRds = function(){
    if(this.respiratorySystemObj.respSystemObject.respSupport.rsSpo2 != null && this.respiratorySystemObj.respSystemObject.respSupport.rsSpo2 !=""){
      this.spoMessage="";
      if(this.respiratorySystemObj.respSystemObject.respSupport.rsSpo2*1 < 0 || this.respiratorySystemObj.respSystemObject.respSupport.rsSpo2*1 > 100){
        this.spoMessage="Range 0 to 100";
        this.respiratorySystemObj.respSystemObject.respSupport.rsSpo2="";
        return false;
      }
    }
    if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate != null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate != ""){
      this.flowRateMessage ="";
      if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate < 0 || this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate > 25 || this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate == 0){
        this.flowRateMessage = "Range 0 to 25";
        this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate = "";
        return false;
      }
    }
    if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!= null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv != ""){
      this.tvMessage = "";
      if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv < 1 || this.respiratorySystemObj.respSystemObject.respSupport.rsTv > 30){
        this.tvMessage = "Range 1 to 20";
        this.respiratorySystemObj.respSystemObject.respSupport.rsTv = "";
        return false;
      }
    }
    return true;
  }
  populateRespSupportText = function(respSupport){
    var respStr = "";
    var respSupportObj = {
        "respStr" : "",
        "respVentType" : ""
    };
    if (respSupport.rsVentType!=null && respSupport.rsVentType!="") {
      this.pastRespSupportObj = Object.assign({},respSupport);
      if(respSupport.rsMechVentType!=null && respSupport.rsMechVentType!="") {
        respStr += "Mechanical Vent Type: " + respSupport.rsMechVentType + ", ";
      }
      if(respSupport.rsMap!=null && respSupport.rsMap!="") {
        if(respSupport.rsVentType == 'CPAP') {
          respStr += "MAP/PEEP: " + respSupport.rsMap + ", ";
        } else {
          respStr += "MAP: " + respSupport.rsMap + ", ";
        }
      }
      if(respSupport.rsFrequency!=null && respSupport.rsFrequency!="") {
        respStr += "Frequency: " + respSupport.rsFrequency + ", ";
      }
      if(respSupport.rsTv!=null && respSupport.rsTv!="") {
        respStr += "TV: " + respSupport.rsTv + ", ";
      }
      if(respSupport.rsAmplitude!=null && respSupport.rsAmplitude!="") {
        respStr += "Amplitude: " + respSupport.rsAmplitude + ", ";
      }
      if(respSupport.rsFio2!=null && respSupport.rsFio2!="") {
        respStr += "FiO2: " + respSupport.rsFio2 + " %, ";
      }
      if(respSupport.rsFlowRate!=null && respSupport.rsFlowRate!="") {
        respStr += "Flow Rate: " + respSupport.rsFlowRate + " Liters/Min, ";
      }
      if(respSupport.rsPip!=null && respSupport.rsPip!="") {
        respStr += "PIP: " + respSupport.rsPip + " cm H2O, ";
      }
      if(respSupport.rsPeep!=null && respSupport.rsPeep!="") {
        respStr += "PEEP: " + respSupport.rsPeep + " cm H2O, ";
      }
      if(respSupport.rsIt!=null && respSupport.rsIt!="") {
        respStr += "IT: " + respSupport.rsIt + " secs, ";
      }
      if(respSupport.rsEt!=null && respSupport.rsEt!="") {
        respStr += "ET: " + respSupport.rsEt + " secs, ";
      }
      if(respSupport.rsMv!=null && respSupport.rsMv!="") {
        respStr += "MV: " + respSupport.rsMv + ", ";
      }
      if(respStr !=""){
        respStr = respStr.substring(0, (respStr.length - 2));
      }
      respSupportObj.respStr = respStr;
      respSupportObj.respVentType = respSupport.rsVentType;
    }
    return respSupportObj;
  }
  populatePastSelectedText = function(assessmentType,assessment,respStr){
    if(assessmentType=="RDS"){
      this.pastTreatmentSelectedText = "";
      if(assessment.treatmentaction!=null &&
          assessment.treatmentaction!=''){
        if(assessment.treatmentaction.includes("TRE004")){
          if(this.pastTreatmentSelectedText==''){
            this.pastTreatmentSelectedText = "Surfactant";
          } else {
            this.pastTreatmentSelectedText += ", "+"Surfactant";
          }
          var surfactantStr = "";
          if(assessment.sufactantname!=null
              && assessment.sufactantname!=''
                && assessment.surfactantDose!=null
                && assessment.surfactantDose!=''){
            surfactantStr = "("+assessment.sufactantname+") "+assessment.surfactantDose+" ml/kg";
            this.pastTreatmentSelectedText += surfactantStr;
          }
        }
        if(assessment.treatmentaction.includes("TRE005")){
          if(this.pastTreatmentSelectedText==''){
            this.pastTreatmentSelectedText = "Respiratory Support";
          } else {
            this.pastTreatmentSelectedText += ", "+"Respiratory Support";
          }
          if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType != null) {
            this.pastTreatmentSelectedText += " "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +"(" + respStr + ")";
          }
        }
        if(assessment.treatmentaction.includes("other")){
          if(this.pastTreatmentSelectedText==''){
            this.pastTreatmentSelectedText = assessment.treatmentOther;
          } else {
            this.pastTreatmentSelectedText += ", "+assessment.treatmentOther;
          }
        }
      }
      if(assessment.medicationStr != null && assessment.medicationStr != ''){
        if(this.pastTreatmentSelectedText==''){
          this.pastTreatmentSelectedText = assessment.medicationStr;
        } else {
          this.pastTreatmentSelectedText += ", "+assessment.medicationStr;
        }
      }
      this.pastOrderSelected = "";
      if(assessment.orderInvestigationStringList!=null &&
          assessment.orderInvestigationStringList.length>0){
        for(var index = 0; index<this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].orderInvestigationStringList.length;index++){
          if(this.pastOrderSelected == ''){
            this.pastOrderSelected = this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].orderInvestigationStringList[index];
          } else {
            this.pastOrderSelected += ", "+this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].orderInvestigationStringList[index];
          }
        }
      }
      this.pastCauseOfRespDistressStr = "";
      if(assessment.causeofrds!=null && assessment.causeofrds!=""){
        var couseListStr = assessment.causeofrds.replace("[","").replace("]","");
        var causeList = couseListStr.split(",");
        console.log(causeList);
        for(index = 0; index<causeList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.causeOfRds.length;i++){
            if(causeList[index].trim()==this.respiratorySystemObj.dropDowns.causeOfRds[i].key){
              if(this.pastCauseOfRespDistressStr==''){
                this.pastCauseOfRespDistressStr = this.respiratorySystemObj.dropDowns.causeOfRds[i].value;
              } else {
                this.pastCauseOfRespDistressStr += ", "+this.respiratorySystemObj.dropDowns.causeOfRds[i].value;
              }
            }
          }
        }
      }
      this.pastRiskRespDistressStr = "";
      if(assessment.riskfactor!=null &&
          assessment.riskfactor!=""){
        var riskFactorListStr = assessment.riskfactor.replace("[","").replace("]","");
        var riskList = riskFactorListStr.split(",");
        for(index = 0; index<riskList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.riskFactorRds.length;i++){
            if(riskList[index].trim()==this.respiratorySystemObj.dropDowns.riskFactorRds[i].key){
              if(this.pastRiskRespDistressStr==''){
                this.pastRiskRespDistressStr = this.respiratorySystemObj.dropDowns.riskFactorRds[i].value;
              } else {
                this.pastRiskRespDistressStr += ", "+this.respiratorySystemObj.dropDowns.riskFactorRds[i].value;
              }
            }
          }
        }
      }
      if(!(this.respiratoryDistressValue || this.apneaDistressValue || this.PPHNValue || this.OthersDistressValue || this.PulmonaryDistressValue)){
        console.log('none log find');
        if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress!=null &&
            this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length>0 &&
            this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[0].eventstatus!=null){
          this.respiratoryDistressValue = true;
        }else if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea!=null &&
            this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length>0 &&
            this.respiratorySystemObj.respSystemObject.apnea.pastApnea[0].eventstatus!=null){
          this.apneaDistressValue =true;
        }else if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn!=null &&
            this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length>0 &&
            this.respiratorySystemObj.respSystemObject.pphn.pastPphn[0].eventstatus!=null){
          this.PPHNValue =true;
        }else if(this.respiratorySystemObj.respSystemObject.others.pastOtherList!=null &&
            this.respiratorySystemObj.respSystemObject.others.pastOtherList.length>0 &&
            this.respiratorySystemObj.respSystemObject.others.pastOtherList[0].eventstatus!=null){
          this.OthersDistressValue =true;
        }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo!=null &&
            this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length>0 &&
            this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus!=null){
          this.PulmonaryDistressValue =true;
        }
      }
    } else if(assessmentType=="PPHN"){
      this.pastRiskPphnStr = "";//Past Risk Factors
      if(assessment.riskfactor!=null &&
          assessment.riskfactor!=""){
        var riskFactorListStr = assessment.riskfactor.replace("[","").replace("]","");
        var riskList = riskFactorListStr.split(",");
        for(index = 0; index<riskList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.riskFactorPphn.length;i++){
            if(riskList[index].trim()==this.respiratorySystemObj.dropDowns.riskFactorPphn[i].key){
              if(this.pastRiskPphnStr==''){
                this.pastRiskPphnStr = this.respiratorySystemObj.dropDowns.riskFactorPphn[i].value;
              } else {
                this.pastRiskPphnStr += ", "+this.respiratorySystemObj.dropDowns.riskFactorPphn[i].value;
              }
            }
          }
        }
      }
      this.pastPphnOrderInvestigationStr = "";
      this.pastCauseOfPphnStr = ""; // assessmentType,assessment,respStr
      if(assessment.causeofpphn!=null
          && assessment.causeofpphn!=""){
        var couseListStr = assessment.causeofpphn.replace("[","").replace("]","");
        var causeList = couseListStr.split(",");
        for(index = 0; index<causeList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.causeOfPphn.length;i++){
            if(causeList[index].trim()==this.respiratorySystemObj.dropDowns.causeOfPphn[i].key){
              if(this.pastCauseOfPphnStr==''){
                this.pastCauseOfPphnStr = this.respiratorySystemObj.dropDowns.causeOfPphn[i].value;
              } else {
                this.pastCauseOfPphnStr += ", "+this.respiratorySystemObj.dropDowns.causeOfPphn[i].value;
              }
            }
          }
        }
      }
      if(assessment.orderInvestigationListStr != null) {
        for(var i=0; i < assessment.orderInvestigationListStr.length;i++) {
          if(this.pastPphnOrderInvestigationStr == "") {
            this.pastPphnOrderInvestigationStr = assessment.orderInvestigationListStr[i];
          } else {
            this.pastPphnOrderInvestigationStr += ", "+assessment.orderInvestigationListStr[i];
          }
        }
      }
    }else if (assessmentType=="pneumothorax"){
      this.pastRiskPneumoStr = "";//Past Risk Factors
      if(assessment.riskfactor!=null &&
          assessment.riskfactor!=""){
        var riskFactorListStr = assessment.riskfactor.replace("[","").replace("]","");
        var riskList = riskFactorListStr.split(",");
        for(index = 0; index<riskList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.riskFactorPneumo.length;i++){
            if(riskList[index].trim()==this.respiratorySystemObj.dropDowns.riskFactorPneumo[i].key){
              if(this.pastRiskPneumoStr==''){
                this.pastRiskPneumoStr = this.respiratorySystemObj.dropDowns.riskFactorPneumo[i].value;
              } else {
                this.pastRiskPneumoStr += ", "+this.respiratorySystemObj.dropDowns.riskFactorPneumo[i].value;
              }
            }
          }
        }
      }

      this.pastpneumothoraxTempObj.pneumoTreatmentSelectedText = "";
      if(assessment.treatmentaction!=null && assessment.treatmentaction!=''){
        this.pastpneumothoraxTempObj.pneumoTreatmentSelectedText = assessment.treatmentSelectedText;
      }
      this.pastPneumoOrderSelectedText = "";
      if(assessment.orderInvestigationStringList!=null &&
          assessment.orderInvestigationStringList.length>0){
        for(var index = 0; index<assessment.orderInvestigationStringList.length;index++){
          if(this.pastPneumoOrderSelectedText == ''){
            this.pastPneumoOrderSelectedText = assessment.orderInvestigationStringList[index];
          } else {
            this.pastPneumoOrderSelectedText += ", "+assessment.orderInvestigationStringList[index];
          }
        }
      }
      this.pastCauseOfPneumoStr = "";
      if(assessment.causeofpneumothorax!=null &&
          assessment.causeofpneumothorax!=""){
        var couseListStr = assessment.causeofpneumothorax.replace("[","").replace("]","");
        var causeList = couseListStr.split(",");
        console.log(causeList);
        for(index = 0; index<causeList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.causeOfPneumo.length;i++){
            if(causeList[index].trim()==this.respiratorySystemObj.dropDowns.causeOfPneumo[i].key){
              if(this.pastCauseOfPneumoStr==''){
                this.pastCauseOfPneumoStr = this.respiratorySystemObj.dropDowns.causeOfPneumo[i].value;
              } else {
                this.pastCauseOfPneumoStr += ", "+this.respiratorySystemObj.dropDowns.causeOfPneumo[i].value;
              }
            }
          }
        }
      }
    }else if(assessmentType=="Apnea"){
      this.pastApneaOrderInvestigationStr = "";
      this.pastTreatmentApneaText = "";
      this.pastCauseOfApneaStr = "";
      if(assessment.apneaCause!=null
          && assessment.apneaCause!=""){
        var couseListStr = assessment.apneaCause.replace("[","").replace("]","");
        var causeList = couseListStr.split(",");
        console.log(causeList);
        for(index = 0; index<causeList.length; index++){
          for(i=0; i<this.respiratorySystemObj.dropDowns.causeOfApnea.length;i++){
            if(causeList[index].trim()==this.respiratorySystemObj.dropDowns.causeOfApnea[i].key){
              if(this.pastCauseOfApneaStr==''){
                this.pastCauseOfApneaStr = this.respiratorySystemObj.dropDowns.causeOfApnea[i].value;
              } else {
                this.pastCauseOfApneaStr += ", "+this.respiratorySystemObj.dropDowns.causeOfApnea[i].value;
              }
            }
          }
        }
      }
      if(assessment.eventstatus!=null){
        this.respiratorySystemObj.respSystemObject.apnea.currentApnea.eventstatus = assessment.eventstatus;
        if(assessment.actiontype != null
            && assessment.actiontype.includes("TRE007")){
          if(this.pastTreatmentApneaText==''){
            this.pastTreatmentApneaText = "Respiratory Support";
          } else {
            this.pastTreatmentApneaText += ", " + "Respiratory Support";
          }
          if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType != null) {
            this.pastTreatmentApneaText += " "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType +" (" + respStr + ")";
          }
          for (var key in this.respiratorySystemObj.respSystemObject.apnea.pastRespSupportMap) {
            console.log("Key: " + key);
            if(key == assessment.respotherid) {
              var pastRespSupport = this.respiratorySystemObj.respSystemObject.apnea.pastRespSupportMap[key];
              if(pastRespSupport.rsVentType!=null){
              }
            }
          }
        }
        var caffeineStr = "";
        if(assessment.caffeineAction != null &&
            assessment.caffeineRoute != null) {
          if(assessment.caffeineAction == "Start") {
            caffeineStr += "Caffeine was given " + assessment.caffeineRoute;
          } else {
            caffeineStr +=  assessment.caffeineAction +
            " caffeine " + assessment.caffeineRoute;
          }
          if(assessment.caffeineBolusDose !=null) {
            caffeineStr += ", with bolus dose of "+ assessment.caffeineBolusDose +" mg";
          }
          if(assessment.caffeineMaintenanceDose !=null) {
            caffeineStr += " and maintenance dose of "+assessment.caffeineMaintenanceDose +" mg";
          }
          if(this.pastTreatmentApneaText!=""){ //for text in selected column
            this.pastTreatmentApneaText += ", " + caffeineStr;
          }else if (caffeineStr != ""){
            this.pastTreatmentApneaText = caffeineStr;
          }
        }
        if(this.pastTreatmentApneaText == ""){
          this.pastTreatmentApneaText += assessment.medicationStr;
        }else if (this.pastTreatmentApneaText != ""){
          this.pastTreatmentApneaText = ", " + assessment.medicationStr;
        }
        if(assessment.orderInvestigationList != null) {
          for(var i=0; i < assessment.orderInvestigationList.length;i++) {
            for(var obj in this.respiratorySystemObj.dropDowns.testsList){
              for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
                if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
                  assessment.orderInvestigationList[i]){
                  if(this.pastApneaOrderInvestigationStr == "") {
                    this.pastApneaOrderInvestigationStr = this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
                  } else {
                    this.pastApneaOrderInvestigationStr += ", "+ this.respiratorySystemObj.dropDowns.testsList[obj][index].testname;
                  }
                }
              }
            }
          }
        }
      }
      this.caffieneStartDisabled = false;
      this.caffieneStopDisabled = true;
      this.caffieneContinueDisabled = true;
      this.caffieneChangeDisabled = true;
      if(assessment.actiontype != null
          && assessment.actiontype.includes("TRE008")) {
        if(assessment.caffeineAction!=null){
          if(assessment.caffeineAction != 'Stop'){
            this.caffieneStartDisabled = true;
            this.caffieneStopDisabled = false;
            this.caffieneContinueDisabled = false;
            this.caffieneChangeDisabled = false;
          }
        }
      } else {
        this.respiratorySystemObj.respSystemObject.apnea.currentApnea.caffeineAction = 'Start';
      }
    }
  }
  populatePastAssessment = function(assessment){
    if(assessment.event=="Respiratory Distress"){
      for(var indexPastResp=0;indexPastResp<this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress.length;indexPastResp++){
        if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp].resprdsid==assessment.id){
          this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress =
            Object.assign({},this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp]);
          this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.isNewEntry = false;
          this.respiratoryDistressVisible();
          this.setTodayAssessmentRDS(new Date(assessment.creationtime));
          //populating downes score
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesScore != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesScore != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesScore != ""){
            this.downesScoreList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.downesScore;
            this.cyanosis    =  this.downesScoreList.cynosis;
            this.retractions  = this.downesScoreList.retractions;
            this.grunting  = this.downesScoreList.grunting;
            this.airEntry  = this.downesScoreList.airentry;
            this.respiratoryRate = this.downesScoreList.respiratoryrate;
            this.downesScoreTotalGrade = this.downesScoreList.downesscore;
          }
          //populating risk factors....
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactor != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactor != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactor != ""){
            var factorsArr = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.riskfactor.replace("[","").replace("]","").split(",");
            for(var indexFactors =0; indexFactors<factorsArr.length;indexFactors++){
              for(var index=0; index<this.respiratorySystemObj.dropDowns.riskFactorRds.length;index++){
                if(factorsArr[indexFactors].trim()==this.respiratorySystemObj.dropDowns.riskFactorRds[index].key.trim()){
                  this.respiratorySystemObj.dropDowns.riskFactorRds[index].flag = true;
                  this.onCheckMultiCheckBoxValue(this.respiratorySystemObj.dropDowns.riskFactorRds[index].key,'riskFactorRds',this.respiratorySystemObj.dropDowns.riskFactorRds[index].value);
                }
              }
            }
          }
          //populating past order investigation list...
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList != ""){
            for(var indexOrder=0; indexOrder<this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList.length;indexOrder++){
              for(var obj in this.respiratorySystemObj.dropDowns.testsList){
                for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
                  if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
                    this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.orderInvestigationList[indexOrder]){
                    this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
                    this.investOrderNotOrdered.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
                  }
                }
              }
            }
            this.orderSelected();
            this.closeModalOrderInvestigation();
          }
          //populating treatment...
          //setting treatments...
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction!=null
              && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction!=""){
            this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentActionList = [];
            this.treatmentVisiblePastRds = false;
            this.treatmentTabsVisible = true;
            this.treatmentSelectedText = "";
            var treatmentActionList =[];
            treatmentActionList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.treatmentaction
            .replace("[","").replace("]",",").replace(" ","").split(",");
            for(var index = 0;index<treatmentActionList.length;index++ ){
              for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.treatmentAction.length;indexDropDown++){
                if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] != "") && treatmentActionList[index].replace(" ","")
                    == this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key.replace(" ","")){
                  this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].flag = true;
                  var name = this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key;
                  if(name != null && name != undefined && name != ""){
                    name = name.trim();
                    if(name=='TRE004'){
                      this.saveTreatmentRds('rdsSurfactant');
                      this.calSurfactantMl();
                    }else if(name=='other'){
                      this.saveTreatmentRds('rdsOther');
                    }else if(name=='TRE006'){
                      if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.babyPrescription != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.babyPrescription != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.babyPrescription != ""){
                        this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList = [];
                        var prescriptions = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.babyPrescription;
                        for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                          for(var indexAllMed =0; indexAllMed<this.respiratorySystemObj.dropDowns.allMedicine.length;indexAllMed++){
                            if(prescriptions[indexPrescription].medid ==
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medid){
                              var prescriptionItem = prescriptions[indexPrescription];
                              if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate != ""){
                                prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
                                prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
                              }
                              this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList.push(
                                  Object.assign({},prescriptions[indexPrescription]));
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].flag =true;
                              this.respiratorySystemObj.dropDowns.medicine.push(Object.assign({},this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed]));
                              this.createMedInstruction(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentBabyPrescriptionList[indexPrescription]);
                            }
                          }
                        }
                        this.isAntibioticSaved = true;
                        this.isAntibioticSelect = true;
                      }
                    }else if(name=='TRE005'){
                      this.respiratorySystemObj.respSystemObject.respSupport =
                        this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.respSupport;
                      this.saveTreatmentRds('rdsRespiratory');
                    }
                  }
                }
              }
            }
          }
          //setting cause of rds...
          if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds!=null
              && this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds!=""){
            this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList = [];
            this.causeOfRespDistressStr = "";
            var rdsCauseList =[];
            rdsCauseList = this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.causeofrds
            .replace("[","").replace("]",",").replace(" ","").split(",");
            for(var index =0; index < rdsCauseList.length;index++){
              for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.causeOfRds.length;indexDropDown++){
                if(rdsCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].key){
                  this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.rdsCauseList.push(rdsCauseList[index]);
                  this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].flag = true;
                  if(this.causeOfRespDistressStr==""){
                    this.causeOfRespDistressStr = this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].value;
                  }else{
                    this.causeOfRespDistressStr = this.causeOfRespDistressStr + "," + this.respiratorySystemObj.dropDowns.causeOfRds[indexDropDown].value;
                  }
                }
              }
            }
          }
          this.creatingProgressNotesTemplate();
          if(indexPastResp*1>0){
            var pastRespSupportObj	= this.populateRespSupportText(this.respiratorySystemObj.respSystemObject.respSupport);
            this.populatePastSelectedText("RDS"
                ,this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1],pastRespSupportObj.respStr);
            if(this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1].downesScore != null && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1].downesScore != undefined && this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1].downesScore != ""){
              this.respiratorySystemObj.respSystemObject.pastDownesScore =
                this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1].downesScore.downesscore;
              this.respiratorySystemObj.respSystemObject.pastDownesTime =
                this.respiratorySystemObj.respSystemObject.respiratoryDistress.pastRespDistress[indexPastResp-1].downesScore.creationtime;
            }
          }else{
            this.respiratorySystemObj.respSystemObject.pastDownesScore = null;
            this.respiratorySystemObj.respSystemObject.pastDownesTime = null
            this.pastTreatmentSelectedText = "";
            this.pastOrderSelected = "";
            this.pastCauseOfRespDistressStr = "";
            this.pastRiskRespDistressStr = "";
          }
          break;
        }
      }
    }else if(assessment.event=="Others"){
      for(var index=0;index<this.respiratorySystemObj.respSystemObject.others.pastOtherList.length;index++){
        if(this.respiratorySystemObj.respSystemObject.others.pastOtherList[index].resprdsid==assessment.id){
          this.respiratorySystemObj.respSystemObject.others.currentOther  =
            Object.assign({},this.respiratorySystemObj.respSystemObject.others.pastOtherList[index]);
          this.OthersDistressVisible();
          this.creatingProgressNotesOtherTemplate();
        }
      }
    }else if(assessment.event=="Pneumothorax"){
      for(var indexPast=0;indexPast<this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length;indexPast++){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[indexPast].resppneumothoraxid==assessment.id){
          this.PulmonaryDistressVisible();
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo =
            Object.assign({},this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[indexPast]);
          this.setTodayAssessmentPneumothorax(new Date(assessment.creationtime));
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isNewEntry = false;
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.downesScore != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.downesScore != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.downesScore != null){
            this.downesScoreList = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.downesScore;
            this.cyanosis    =  this.downesScoreList.cynosis;
            this.retractions  = this.downesScoreList.retractions;
            this.grunting  = this.downesScoreList.grunting;
            this.airEntry  = this.downesScoreList.airentry;
            this.respiratoryRate = this.downesScoreList.respiratoryrate;
            this.downesScoreTotalGrade = this.downesScoreList.downesscore;
            this.totalDownyScore = this.downesScoreList.downesscore;
            this.closeModalDownesOnSave();
            this.closeModalDownes();
          }
          //populating past order investigation list...
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.orderInvestigationList != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.orderInvestigationList != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.orderInvestigationList != ""){
            for(var indexOrder=0; indexOrder<this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.orderInvestigationList.length;indexOrder++){
              for(var obj in this.respiratorySystemObj.dropDowns.testsList){
                for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
                  if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
                    this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.orderInvestigationList[indexOrder]){
                    this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
                    this.investOrderNotOrdered.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
                  }
                }
              }
            }
            this.orderSelected();
            this.closeModalOrderInvestigation();
          }
          //..treatment section past populate..
          this.treatmentVisiblePastPneumo = false;
          this.treatmentTabsVisiblePneumo = true;
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentaction != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentaction != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentaction != ""){
            this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList = [];
            //to make it for change or continue make it this.PphnTempObj.pphnTreatmentStatus = 'change';
            var treatmentActionList =[];
            var isAnySelected = false;
            treatmentActionList = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentaction
            .replace("[","").replace("]",",").replace(" ","").split(",");
            for(var index = 0;index<treatmentActionList.length;index++ ){
              for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.treatmentAction.length;indexDropDown++){
                if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] != "") && treatmentActionList[index].replace(" ","")
                    == this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key.replace(" ","")){
                  this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].flag = true;
                  var name = this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key;
                  if(name=="TRE015"){//surfactant
                    this.surfactantSelectPneumo();
                    this.calSurfactantMlPneumo();
                  }else if(name=='TRE016'){
                    this.rdsSelectPneumo();
                    this.isRdsSavedPneumo = true;
                    this.respiratorySystemObj.respSystemObject.respSupport = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.respSupport;
                    this.submitRDSResp();
                  }else if(name=='TRE017'){
                    this.isAntibioticsSavedPneumo  = true;
                    this.antibioticsSelectPneumo();
                    if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.babyPrescription != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.babyPrescription != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.babyPrescription != ""){
                      this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList = [];
                      var prescriptions = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.babyPrescription;
                      for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                        for(var indexAllMed =0; indexAllMed<this.respiratorySystemObj.dropDowns.allMedicine.length;indexAllMed++){
                          if(prescriptions[indexPrescription].medid ==
                            this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medid &&
                            this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medicationtype == 'TYPE0001'){
                            var prescriptionItem = prescriptions[indexPrescription];
                            if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate != ""){
                              prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
                              prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
                            }
                            this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList.push(
                                Object.assign({},prescriptions[indexPrescription]));
                            this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].flag =true;
                            this.respiratorySystemObj.dropDowns.medicine.push(Object.assign({},this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed]));
                          }
                        }
                      }
                    }
                  }else if(name=='TRE018'){
                    this.isNeedleAspirationSavedPneumo = true;
                    this.needleAspirationSelectPneumo();
                    if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight != ""){
                      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight = null;
                      this.needleRight = true;
                      this.putNeedleAspiration('right');
                    }
                    if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft != ""){
                      $("#needleLeft").removeClass("button-blue-transparent");
                      $("#needleLeft").addClass("button-blue");
                    }
                  }
                  this.creatingProgressNotesPneumoTemplate();
                }
              }
            }
          }
        }
      }
    }else if(assessment.event=="PPHN"){
      for(var indexPastPphn=0;indexPastPphn<this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length;indexPastPphn++){
        if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn].resppphnid == assessment.id){
          this.respiratorySystemObj.respSystemObject.pphn.currentPphn =
            Object.assign({},this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn]);
          this.respiratorySystemObj.respSystemObject.pphn.currentPphn.isNewEntry = false;
          this.PPHNVisible();
          this.setTodayAssessmentPPHN(new Date(assessment.creationtime));
          var weight = 0;
          if(this.childObject.todayWeight!=null && this.childObject.todayWeight!=''
            && this.respiratorySystemObj.respSystemObject.others.currentOther.sufactantdose!=null
            && this.respiratorySystemObj.respSystemObject.others.currentOther.sufactantdose!=''){
            weight = this.childObject.todayWeight/1000;
          } else {
            weight = this.childObject.workingWeight/1000;
          }
          this.PphnTempObj.surfactantDoseMlKg = Math.round((this.respiratorySystemObj.respSystemObject.pphn.currentPphn.surfactantDose*100)/weight)/100;
          //populating downes score
          if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.downesScore != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.downesScore != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.downesScore != ""){
            this.downesScoreList = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.downesScore;
            this.cyanosis    =  this.downesScoreList.cynosis;
            this.retractions  = this.downesScoreList.retractions;
            this.grunting  = this.downesScoreList.grunting;
            this.airEntry  = this.downesScoreList.airentry;
            this.respiratoryRate = this.downesScoreList.respiratoryrate;
            this.downesScoreTotalGrade = this.downesScoreList.downesscore;
          }
          //populating past order investigation list...
          if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.orderInvestigationList != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.orderInvestigationList != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.orderInvestigationList != ""){
            for(var indexOrder=0; indexOrder<this.respiratorySystemObj.respSystemObject.pphn.currentPphn.orderInvestigationList.length;indexOrder++){
              for(var obj in this.respiratorySystemObj.dropDowns.testsList){
                for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
                  if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
                    this.respiratorySystemObj.respSystemObject.pphn.currentPphn.orderInvestigationList[indexOrder]){
                    this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
                    this.investOrderNotOrdered.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
                  }
                }
              }
            }
            this.orderSelected();
            this.closeModalOrderInvestigation();
          }
          //populating treatment...
          //setting treatments...
          if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentaction!=null
              && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentaction!=""){
            this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentActionList = [];
            this.PphnTempObj.pphnTreatmentStatus = null;
            //to make it for change or continue make it this.PphnTempObj.pphnTreatmentStatus = 'change';
            this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentSelectedText = "";
            var treatmentActionList =[];
            var isAnySelected = false;
            treatmentActionList = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.treatmentaction
            .replace("[","").replace("]",",").replace(" ","").split(",");
            for(var index = 0;index<treatmentActionList.length;index++ ){
              for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.treatmentAction.length;indexDropDown++){
                if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] != "") && treatmentActionList[index].replace(" ","")
                    == this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key.replace(" ","")){
                  this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].flag = true;
                  var name = this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key;
                  if(name != null && name != undefined && name != ""){
                    name = name.trim();
                    if(name=='TRE009'){
                      this.systemValue('TRE009','actionType');
                      this.saveTreatmentPphn('surfactant');
                      this.calSurfactantMlPphn();
                      this.PphnTempObj.isSurfactantSelectPphn = true;
                      isAnySelected = true;
                    }else if(name=='others'){
                      this.saveTreatmentPphn('Other');
                      this.PphnTempObj.isOtherSavedPphn = true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isOtherSelectPphn =true;
                        isAnySelected = true;
                      }
                    }else if(name=='TRE011'){
                      this.PphnTempObj.isAntibioticsSavedPphn = true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isAntibioticsSelectPphn = true;
                        isAnySelected = true;
                      }
                      if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != null &&this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != ""){
                        this.respiratorySystemObj.respSystemObject.pphn.antibioticsList = [];
                        var prescriptions = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription;
                        for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                          for(var indexAllMed =0; indexAllMed<this.respiratorySystemObj.dropDowns.allMedicine.length;indexAllMed++){
                            if(prescriptions[indexPrescription].medid ==
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medid &&
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medicationtype == 'TYPE0001'){
                              var prescriptionItem = prescriptions[indexPrescription];
                              if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate != ""){
                                prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
                                prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
                              }
                              this.respiratorySystemObj.respSystemObject.pphn.antibioticsList.push(
                                  Object.assign({},prescriptions[indexPrescription]));
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].flag =true;
                              this.respiratorySystemObj.dropDowns.medicine.push(Object.assign({},this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed]));
                              this.onCheckMultiCheckBoxValue(prescriptionItem.medid,'medicinePphn',true);
                            }
                          }
                        }
                        this.saveTreatmentPphn('antibiotic');
                      }
                    }else if(name=='TRE010'){
                      this.isRespSavedPphn =true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isRespSelectPphn   =true;
                        isAnySelected = true;
                      }
                      this.respiratorySystemObj.respSystemObject.respSupport =this.respiratorySystemObj.respSystemObject.pphn.currentPphn.respSupport;
                      this.saveTreatmentPphn('respiratory');
                    }else if(name=='TRE012'){
                      this.PphnTempObj.isInotropesSavedPphn  = true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isInotropesSelectPphn  = true;
                        isAnySelected = true;
                      }
                      if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != ""){
                        this.respiratorySystemObj.respSystemObject.pphn.inoList = [];
                        for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                          for(var indexAllMed =0; indexAllMed<this.respiratorySystemObj.dropDowns.allMedicine.length;indexAllMed++){
                            if(prescriptions[indexPrescription].medid ==
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medid &&
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medicationtype == 'TYPE0004'){
                              var prescriptionItem = prescriptions[indexPrescription];
                              if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined &&prescriptionItem.startdate != ""){
                                prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
                                prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
                              }
                              this.respiratorySystemObj.respSystemObject.pphn.inoList.push(
                                  Object.assign({},prescriptions[indexPrescription]));
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].flag =true;
                              this.respiratorySystemObj.dropDowns.medicine.push(Object.assign({},this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed]));
                              this.onCheckMultiCheckBoxValue(prescriptionItem.medid,'ino',true);
                            }
                          }
                        }
                        this.saveTreatmentPphn('inotropes');
                      }
                    }else if(name=='TRE013'){//pulomonary
                      this.PphnTempObj.isVasodilatorsSavedPphn = true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isVasodilatorsSelectPphn = true;
                        isAnySelected = true;
                      }
                      if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != null && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != undefined && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription != ""){
                        var prescriptions = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription;
                        for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                          if(prescriptions[indexPrescription].medicinename=='Sildenafil'){
                            this.respiratorySystemObj.respSystemObject.pphn.sildenafilObj =
                              prescriptions[indexPrescription];
                            break;
                          }
                        }
                      }
                      this.systemValue('TRE013','actionType');
                      this.saveTreatmentPphn('vasodilator');
                    }else if(name=='TRE014'){	//
                      this.PphnTempObj.isSedationSavedPphn   = true;
                      if(isAnySelected==false){
                        this.PphnTempObj.isSedationSelectPphn   = true;
                        isAnySelected = true;
                      }
                      this.respiratorySystemObj.respSystemObject.pphn.sedationList = [];
                      var prescriptions = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.babyPrescription;
                      if(prescriptions != null && prescriptions != undefined && prescriptions != ""){
                        for(var indexPrescription = 0;indexPrescription<prescriptions.length;indexPrescription++ ){
                          for(var indexAllMed =0; indexAllMed<this.respiratorySystemObj.dropDowns.allMedicine.length;indexAllMed++){
                            if(prescriptions[indexPrescription].medid ==
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medid &&
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].medicationtype == 'TYPE0007'){
                              var prescriptionItem = prescriptions[indexPrescription];
                              if(prescriptionItem.startdate != null && prescriptionItem.startdate != undefined && prescriptionItem.startdate != ""){
                                prescriptionItem.startTimeObject = this.getNicuTimeStampFromTimeStampFormat(new Date(prescriptionItem.startdate));
                                prescriptionItem.startdate = prescriptionItem.startTimeObject.date;
                              }
                              this.respiratorySystemObj.respSystemObject.pphn.sedationList.push(
                                  Object.assign({},prescriptions[indexPrescription]));
                              this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed].flag =true;
                              this.respiratorySystemObj.dropDowns.medicine.push(Object.assign({},this.respiratorySystemObj.dropDowns.allMedicine[indexAllMed]));
                              this.onCheckMultiCheckBoxValue(prescriptionItem.medid,'sedation',true);
                            }
                          }
                        }
                        this.saveTreatmentPphn('sedation');
                      }
                    }
                  }
                }
              }
            }
          }
          //setting cause of pphn...
          if(this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphn!=null
              && this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphn!=""){
            this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList = [];
            this.causeOfPphnStr = "";
            var causeList =[];
            causeList = this.respiratorySystemObj.respSystemObject.pphn.currentPphn.causeofpphn
            .replace("[","").replace("]",",").replace(" ","").split(",");
            for(var index =0; index < causeList.length;index++){
              for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.causeOfPphn.length;indexDropDown++){
                if(causeList[index] == this.respiratorySystemObj.dropDowns.causeOfPphn[indexDropDown].key){
                  this.respiratorySystemObj.respSystemObject.pphn.currentPphn.pphnCauseList.push(causeList[index]);
                  this.respiratorySystemObj.dropDowns.causeOfPphn[indexDropDown].flag = true;
                  if(this.causeOfPphnStr==""){
                    this.causeOfPphnStr = this.respiratorySystemObj.dropDowns.causeOfPphn[indexDropDown].value;
                  }else{
                    this.causeOfPphnStr = this.causeOfPphnStr + "," + this.respiratorySystemObj.dropDowns.causeOfPphn[indexDropDown].value;
                  }
                }
              }
            }
          }
          var pastRespSupportObj	= this.populateRespSupportText(this.respiratorySystemObj.respSystemObject.respSupport);
          if(indexPastPphn+1<this.respiratorySystemObj.respSystemObject.pphn.pastPphn.length){
            this.populatePastSelectedText("PPHN" ,this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1],pastRespSupportObj.respStr);
            if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].downesScore != null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].downesScore != undefined && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].downesScore != ""){
              this.respiratorySystemObj.respSystemObject.pastDownesScore =
                this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].downesScore.downesscore;
              this.respiratorySystemObj.respSystemObject.pastDownesTime =
                this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].downesScore.creationtime;
              this.closeModalDownesOnSave();
              if((this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != undefined && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != "") &&
                  this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentSelectedText!=null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentSelectedText!=''){
                this.PphnTempObj.treatmentVisiblePast = true;
                this.PphnTempObj.pastTreatmentPphnText = this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentSelectedText;
              }
            }else{
              this.respiratorySystemObj.respSystemObject.pastDownesScore = null;
              this.respiratorySystemObj.respSystemObject.pastDownesTime = null
              this.pastPphnOrderInvestigationStr = "";
              this.pastOrderSelected = "";
              this.pastCauseOfPphnStr = "";
              this.PphnTempObj.pastTreatmentPphnText = "";
            }
            if(this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != null && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != undefined && this.respiratorySystemObj.respSystemObject.pphn.pastPphn[indexPastPphn+1].treatmentaction != ""){
              this.PphnTempObj.treatmentVisiblePast = true;
            }else{
              this.PphnTempObj.treatmentVisiblePast = false;
            }
          }else{
            this.respiratorySystemObj.respSystemObject.pastDownesScore = null;
            this.respiratorySystemObj.respSystemObject.pastDownesTime = null
            this.pastTreatmentSelectedText = "";
            this.pastOrderSelected = "";
            this.pastCauseOfPphnStr = "";
            this.PphnTempObj.treatmentVisiblePast = false;
            this.PphnTempObj.pastTreatmentPphnText ="";
          }
          this.creatingProgressNotesPphnTemplate();
          break;
        }
      }
    }else if(assessment.event=="Apnea"){
      console.log("Apnea*************************");
      for(var indexApnea=0;indexApnea<this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length;indexApnea++){
        if(this.respiratorySystemObj.respSystemObject.apnea.pastApnea[indexApnea].apneaid==assessment.id){
          this.respiratorySystemObj.respSystemObject.apnea.currentApnea =
            Object.assign({},this.respiratorySystemObj.respSystemObject.apnea.pastApnea[indexApnea]);
          if(indexApnea==0){
            this.pastAgeInactiveApnea = false;
          }else if (indexApnea>=1 && this.respiratorySystemObj.respSystemObject.apnea.pastApnea[indexApnea].eventstatus=='Inactive'){
            this.pastAgeInactiveApnea = false;
          }else{
            this.pastAgeInactiveApnea = true;
          }
          this.treatmentVisiblePastApnea = false;
          this.treatmentApneaTabsVisible = true;
          this.apneaDistressVisible();
          this.setTodayAssessment(new Date(assessment.creationtime)); //apnea
          this.respiratorySystemObj.respSystemObject.apnea.currentApnea.isNewEntry = false;
          //populating past order investigation list...
          if(this.respiratorySystemObj.respSystemObject.apnea.currentApnea.orderInvestigationList != null && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.orderInvestigationList != undefined && this.respiratorySystemObj.respSystemObject.apnea.currentApnea.orderInvestigationList != ""){
            for(var indexOrder=0; indexOrder<this.respiratorySystemObj.respSystemObject.apnea.currentApnea.orderInvestigationList.length;indexOrder++){
              for(var obj in this.respiratorySystemObj.dropDowns.testsList){
                for(var index = 0; index<this.respiratorySystemObj.dropDowns.testsList[obj].length;index++){
                  if(this.respiratorySystemObj.dropDowns.testsList[obj][index].testid==
                    this.respiratorySystemObj.respSystemObject.apnea.currentApnea.orderInvestigationList[indexOrder]){
                    this.respiratorySystemObj.dropDowns.testsList[obj][index].isSelected = true;
                    this.investOrderNotOrdered.push(this.respiratorySystemObj.dropDowns.testsList[obj][index].testname);
                  }
                }
              }
            }
            this.apneaOrderSelected();
            this.closeApneaModalOrderInvestigation();
          }
          var treatmentActionList =[];
          var isAnySelected = false;
          treatmentActionList = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.actiontype
          .replace("[","").replace("]",",").replace(" ","").split(",");
          for(var index = 0;index<treatmentActionList.length;index++ ){
            for(var indexDropDown = 0; indexDropDown<this.respiratorySystemObj.dropDowns.treatmentAction.length;indexDropDown++){
              if((treatmentActionList[index] != null && treatmentActionList[index] != undefined && treatmentActionList[index] != "") && treatmentActionList[index].replace(" ","")
                  == this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key.replace(" ","")){
                this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].flag = true;
                var name = this.respiratorySystemObj.dropDowns.treatmentAction[indexDropDown].key;
                if(name != null && name != undefined && name != ""){
                  if(name=='TRE008'){
                    this.ApneaCaffeineSelect();
                    this.isApneaCaffeineSaved = true;
                    this.saveTreatmentApnea('caffeine');
                  }else if (name=='TRE007'){
                    this.isApneaRespiratorySaved = true;
                    this.ApneaRespiratorySelect();
                    this.respiratorySystemObj.respSystemObject.respSupport = this.respiratorySystemObj.respSystemObject.apnea.currentApnea.respSupport;
                    this.submitRDSResp();
                  }
                }
              }
            }
          }
          this.creatingProgressNotesApneaTemplate();
          if(indexApnea+1<this.respiratorySystemObj.respSystemObject.apnea.pastApnea.length){
            var pastRespSupportObj	= this.populateRespSupportText(this.respiratorySystemObj.respSystemObject.respSupport);
            this.populatePastSelectedText("Apnea"
                ,this.respiratorySystemObj.respSystemObject.apnea.pastApnea[indexApnea+1],pastRespSupportObj.respStr);
          }else{
            this.pastApneaOrderInvestigationStr = "";
            this.pastTreatmentApneaText = "";
            this.pastCauseOfApneaStr = "";
          }
        }
      }
    }
  }
  putNeedleAspiration = function(sideOfNeedle){
    if(sideOfNeedle=='right'){
      if(this.needleRight==true){
        this.needleRight = false;
        $("#needleRight").removeClass("button-blue-transparent");
        $("#needleRight").addClass("button-blue");
      }
      else{
        this.needleRight = true;
        $("#needleRight").removeClass("button-blue");
        $("#needleRight").addClass("button-blue-transparent");
      }
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight!=null){
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight = !(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight);
      }else{
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight = true;
      }
    }else if(sideOfNeedle=='left'){
      if(this.needleLeft){
        this.needleLeft = false;
        $("#needleLeft").removeClass("button-blue-transparent");
        $("#needleLeft").addClass("button-blue");
      }
      else{
        this.needleLeft = true;
        $("#needleLeft").removeClass("button-blue");
        $("#needleLeft").addClass("button-blue-transparent");
      }
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft!=null){
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft = !(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft);
      }else{
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft = true;
      }
    }
    if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft ||
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight){
        this.isNeedleAspirationSavedPneumo = true;
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null || this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE018")==-1){
          this.onCheckMultiCheckBoxValue('TRE018','pneumoTreatmentAction',true);
        }
      }else{
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE018")!=-1){
          this.onCheckMultiCheckBoxValue('TRE018','pneumoTreatmentAction',false);
        }
        this.isNeedleAspirationSavedPneumo = false;
      }
      this.creatingProgressNotesPneumoTemplate();
    }
  calSurfactantMlPneumo = function(){
  			var childObject = JSON.parse(localStorage.getItem('selectedChild'));
  			console.log(childObject);
  			var weight = 0;
  			this.surfactantDosePneumoMessage = "";
  			if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname == 'survanta' && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose*1 >4){
  				this.surfactantDosePneumoMessage = "Cannot be greater than 4";
  				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose = "";
  			}else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname == 'curosurf' && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose*1 > 2.5){
  				this.surfactantDosePneumoMessage = "Cannot be greater than 2.5";
  				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose = "";
  			}else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname == 'neosurf' && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose*1 > 5){
  				this.surfactantDosePneumoMessage = "Cannot be greater than 5";
  				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose = "";
  			}else{
  				this.PneumoTempObj.surfactantDoseMlKg = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose;
  				if(childObject.todayWeight!=null && childObject.todayWeight!=''
  					&& this.PneumoTempObj.surfactantDoseMlKg!=null && this.PneumoTempObj.surfactantDoseMlKg!=''){
  					weight = childObject.todayWeight/1000;
  				} else {
  					weight = childObject.workingWeight/1000;
  				}
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose!=null
    					&& this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose!=''){
    				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDoseMl =
    					Math.round((this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDose * weight)*100)/100+"";
    			} else {
    				this.respiratorySystemObj.respSystemObject.respiratoryDistress.currentRespDistress.surfactantDoseMl = null;
    			}
  				this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDoseMl =
  					Math.round((this.PneumoTempObj.surfactantDoseMlKg * weight)*100)/100+"";
  				this.saveTreatmentPneumo('surfactant');
  			}
  		}
  surfactantSelectPneumo = function(){
    this.isSurfactantSelectPneumo = true;
    this.isNeedleAspirationSelectPneumo = false;
    this.isChestTubeSelectPneumo = false;
    this.isRdsSelectPneumo = false;
    this.isOtherSelectPneumo = false;
    this.isMedicationSelectPneumo = false;
  }
  needleAspirationSelectPneumo = function(){
		this.isSurfactantSelectPneumo = false;
		this.isNeedleAspirationSelectPneumo = true;
		this.isChestTubeSelectPneumo = false;
		this.isRdsSelectPneumo = false;
		this.isOtherSelectPneumo = false;
    this.isMedicationSelectPneumo = false;
	}
  chestTubeSelectPneumo = function(){
		this.isSurfactantSelectPneumo = false;
		this.isNeedleAspirationSelectPneumo = false;
		this.isChestTubeSelectPneumo = true;
		this.isRdsSelectPneumo = false;
		this.isOtherSelectPneumo = false;
    this.isMedicationSelectPneumo = false;
		this.saveTreatmentPneumo('chestTube');
	}
	rdsSelectPneumo = function(){
		this.isSurfactantSelectPneumo = false;
		this.isNeedleAspirationSelectPneumo = false;
		this.isChestTubeSelectPneumo = false;
		this.isRdsSelectPneumo = true;
		this.isOtherSelectPneumo = false;
    this.isMedicationSelectPneumo = false;
		if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null
				|| this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf('TRE016')==-1){
			this.callRespiratorySupportPopUP('Pneumothorax', false);
		}
	}
	 otherSelectPneumo = function(){
		this.isSurfactantSelectPneumo = false;
		this.isNeedleAspirationSelectPneumo = false;
		this.isChestTubeSelectPneumo = false;
		this.isRdsSelectPneumo = false;
		this.isOtherSelectPneumo = true;
    this.isMedicationSelectPneumo = false;
	}
  medicationSelectPneumo = function(){
    this.isSurfactantSelectPneumo = false;
    this.isNeedleAspirationSelectPneumo = false;
    this.isChestTubeSelectPneumo = false;
    this.isRdsSelectPneumo = false;
    this.isOtherSelectPneumo = false;
    this.isMedicationSelectPneumo = true;
  }
// service function
  getTimeStampFromNicuTimeStampFormat = function(date,hrs,min,sec,meridian){
		var timeStamp = new Date();
		if(!this.isEmpty(date)){
			timeStamp = date;
		}
		if(!this.isEmpty(hrs)){
			if(meridian=="PM"){
				if(hrs*1<12){
					hrs = hrs*1+12;
				}
			}else {
				if(hrs*1==12){
					hrs = 0;
				}
			}
			timeStamp.setHours(hrs*1);
		}
		if(!this.isEmpty(min)){
			timeStamp.setMinutes(min);
		}
		if(!this.isEmpty(sec)){
			timeStamp.setSeconds(sec);
		}
		return timeStamp;
	}
  getNicuTimeStampFromTimeStampFormat = function(timeStamp){
		var hours = timeStamp.getHours();
		var minutes = timeStamp.getMinutes();
		var seconds = timeStamp.getSeconds();
		var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = (hours*1) % 12;
		hours = (hours*1) ? (hours*1) : 12; // the hour '0' should be '12'
		minutes = (minutes*1) < 10 ? '0'+ minutes : minutes;
		seconds = (seconds*1) <10 ? '0' + seconds : seconds;
		var strTime = hours + ':' + minutes + ':' + ampm;
		if((hours*1)<10){
			hours = '0'+hours;
		}
    let tempObj = {
  		"hours" : hours,
  		"minutes" : minutes,
  		"seconds" : seconds,
  		"meridian" : ampm,
  		"date" : timeStamp,
    };
		return tempObj;
	};
  callingPneumothoraxFunction = function(){
      this.calculateAgeAtAssessmentNewPneumothorax();
  }
  differenceInDays = function(dateStr){
    let oneDay = 24 * 60 * 60 * 1000;
		let firstDate = new Date();
		let secondDate = new Date(dateStr);
		let diffDays = Math.ceil(Math.abs((firstDate.getTime() - secondDate.getTime()) / (oneDay)));
		return diffDays;
  }
  dateformatter = function(date : Date, inFormat : string, outFormat :string){
    if(inFormat == "gmt"){
      if (outFormat == "utf")
      {
        let tzoffset : any = (new Date(date)).getTimezoneOffset() * 60000; //offset in milliseconds
				let localISOTime : any = (new Date(new Date(date).getTime() - tzoffset)).toISOString().slice(0,-1);
				localISOTime = localISOTime+"Z";
				return localISOTime;
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
  showModal = function(message, type) {
    if(type){
    if (type=='success'){
      $("#modal-message-icon").addClass("sprite sprite-success");
    }
    else if (type=='failure'){
      $("#modal-message-icon").addClass("sprite sprite-failure");
    }
    else{
      $("#modal-message-icon").addClass("sprite sprite-info");
    }
    }
      $("#defaultOverlay").css("display", "block");
      $("#defaultModal").addClass("showing");
      document.getElementById('modalMessage').innerHTML = message;
          $('html,body').animate({
              scrollTop: 0
          }, 300);
  }
  closeModal = function() {
    $("#modal-message-icon").className = '';
    $("#defaultOverlay").css("display", "none");
    $("#defaultModal").removeClass("showing");
    document.getElementById('modalMessage').innerHTML = '';
  }
  // pneumothorax code start
  calculateAgeAtAssessmentNewPneumothorax = function(){
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentDate = this.PneumoTempObj.assessmentDate ;
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentDate==null || this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentDate==undefined){
        console.log("date selected error");
        return;
      }
      this.dateBirth = this.childObject.dob;
      this.dateBirth = new Date(this.dateBirth);
      var timeBirthArray = this.childObject.timeOfBirth.split(',');
      var birthHour = parseInt(timeBirthArray[0]);
      var birthMin = parseInt(timeBirthArray[1]);
      /*
       * 12,00,AM or 00,00,AM are same, ie. 0000Hrs in 24Hr
       * 12,00,PM or 00,00,PM are same, ie. 1200Hrs in 24Hr
       */
      if(timeBirthArray[2]=='AM'){
        if(birthHour==12)
          this.dateBirth.setHours(0);
        else
          this.dateBirth.setHours(birthHour);
      }else{
        if(birthHour==12)
          this.dateBirth.setHours(12);
        else
          this.dateBirth.setHours(birthHour+12);
      }
      this.dateBirth.setMinutes(birthMin);

      var currentAgeData = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentDate;
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.assessmentTime = currentAgeData;
      this.diffTime = currentAgeData.getTime() - this.dateBirth.getTime();
      // ageAtAssessmentInhoursDays field is different here
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays == true)
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment = (this.diffTime/(1000*60*60))+1;
      else
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment = (this.diffTime/(1000*60*60*24))+0;
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment = Math.round(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment);
        //for episodes number.........
      this.assessmentTempObj = this.indcreaseEpisodeNumber(this.respiratorySystemObj.respSystemObject.pastTableList);
      this.creatingProgressNotesPneumoTemplate();
    }
    creatingProgressNotesPneumoTemplate = function(){
      var template = "";
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo==null || this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo.length<=0 ||
          this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo[0].eventstatus=='Inactive'){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.episodeNumber != null){
          template += "Episode number: " + this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.episodeNumber + ". ";
        }
        if(this.respiratorySystemObj.ageAtOnset!=null && this.respiratorySystemObj.ageAtOnset!=""){
          template += "Baby developed pneumothorax at the age of ";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays){
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset*1 == 0){
              template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset + " hr. ";
            }else{
              template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset + " hrs. ";
            }
          }else{
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset*1 == 1 || this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset*1 == 0){
              template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset + " day. ";
            }else{
              template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset +" days. ";
            }
          }
        }
      }else{
        template += "Baby being managed for Pneumothorax. ";
      }
      //riskFactor
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList!=null
          && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList.length>0){
        this.riskPneumoStr ="";
        for(var index=0; index<this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList.length;index++){
          for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.riskFactorPneumo.length;indexDrop++){
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList[index] ==
              this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexDrop].key){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorList[index]=='otherRisk'
                && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther!=null){
                if(this.riskPneumoStr==''){
                  this.riskPneumoStr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther;
                }else{
                  this.riskPneumoStr +=", " + this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.riskfactorOther;
                }
              }else{
                if(this.riskPneumoStr==""){
                  this.riskPneumoStr = this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexDrop].value;
                }else{
                  this.riskPneumoStr = this.riskPneumoStr +", "+ this.respiratorySystemObj.dropDowns.riskFactorPneumo[indexDrop].value;
                }
              }
            }
          }
        }
        if(this.riskPneumoStr!=''){
          if(this.riskPneumoStr.indexOf(",")==-1){
            template = template +" with the risk factor "+this.riskPneumoStr.trim();
          }else{
            template = template +" with risk factors "+this.riskPneumoStr.trim();
          }
        }
      } else {
        this.riskPneumoStr ="";
      }
      template+=". ";

      //write here code for downe's score..
      if(this.totalDownyScore!=null && this.totalDownyScore!=""){
        if(this.respiratorySystemObj.respSystemObject.pastDownesScore!=null && this.respiratorySystemObj.respSystemObject.pastDownesScore!=""){
          if(this.respiratorySystemObj.respSystemObject.pastDownesScore>this.totalDownyScore){
            template = template + "Downes' score decreased from "+this.respiratorySystemObj.respSystemObject.pastDownesScore+" to "+this.totalDownyScore +". ";
          } else if(this.respiratorySystemObj.respSystemObject.pastDownesScore<this.totalDownyScore){
            template = template + "Downes' score increased from "+this.respiratorySystemObj.respSystemObject.pastDownesScore+" to "+this.totalDownyScore +". ";
          } else if(this.respiratorySystemObj.respSystemObject.pastDownesScore==this.totalDownyScore){
            template = template + "Downes' score measured is "+this.totalDownyScore +". ";
          }
        } else {
          template = template + "Downes' score measured is "+this.totalDownyScore +". ";// With downe's score of 12.
        }
      }
      var tempText = "";
      if((this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeRight==null || this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeRight==false)
          && (this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeLeft==null || this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeLeft==false)){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='transillumination'){
          tempText+="Diagnosed by transillumination ";
        } else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='x-Ray'){
          tempText+="Diagnosed by chest X-Ray ";
        }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='clinical'){
          tempText+="Diagnosed clinically ";
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==true
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination==true){
          tempText+="on both side";
        }
        else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==true
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination==false){
          tempText+="on left side";
        } else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==false
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination==true){
          tempText+="on right side";
        }
        }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeRight==true &&
          this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeLeft==true){
        var temp = "";
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxRight!=null){
          temp += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxRight+" on right side";
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxLeft!=null){
          if(temp=='')
            temp += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxLeft+" on left side";
          else
            temp += " and "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxLeft+" on left side";
        }
        tempText += "Pneumothorax is "+temp;
        }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeRight==true){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxRight!=null){
          tempText+="Pneumothorax is "+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxRight+" on right side ";
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==true){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy!=null){
            if(tempText!=''){
              tempText += "and diagnosed ";
            }else {
              tempText += "Pneumothorax diagnosed ";
            }
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='transillumination'){
              tempText+="by transillumination on left side";
            } else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='x-Ray'){
              tempText+="by chest X-Ray on left side";
            }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='clinical'){
              tempText+="clinically on left side";
            }
          }
        }
      }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.isPastTubeLeft==true){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxLeft!=null){
          tempText+="Pneumothorax is "+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.statusOfPneumothoraxLeft+" on left side ";
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==true){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination==true){
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy!=null){
              if(tempText!=''){
                tempText += "and diagnosed ";
              }else {
                tempText += "Pneumothorax diagnosed ";
              }
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='transillumination'){
                tempText+="by transillumination on right side";

              } else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='x-Ray'){
                tempText+="by chest X-Ray on right side";

              }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumoDiagnosisBy=='clinical'){
                tempText+="clinically on right side";
              }
            }
          }
        }
      }else{}
      template+= tempText+". ";
      if(this.orderSelectedText!= null && this.orderSelectedText != "") {
        if(this.orderSelectedText.includes(',')) {
          template += "Investigation ordered are "+this.orderSelectedText+". ";
        } else {
          template += "Investigation ordered is "+this.orderSelectedText+". ";
        }
      }
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText = "";
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes("TRE015")){
          var tempText = "";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname!=null){
            tempText += tempText + "Surfactant ("+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname +") ";
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose!=null){
              tempText += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose+" ml/kg ";
            }
            tempText+=" given ";
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone!=null){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone==true
                  && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
                tempText+=" and INSURE to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
              } else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone==false){
                tempText+=" and continue to "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
              }
            }
          }
          template += tempText+ "";
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText = tempText;
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE016")!=-1
            ||
            this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone!=null){
          if(this.respiratorySystemObj.respSystemObject.respSupport!=null
              && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null){
            var respStr = "";
            respStr += "Mode: "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType + ", ";
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType!="") {
              respStr += "Mechanical Vent Type: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMechVentType + ", ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsMap!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMap!="") {
              if(this.respiratorySystemObj.respSystemObject.respSupport.rsVentType == 'CPAP') {
                respStr += "MAP/PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
              } else {
                respStr += "MAP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMap + ", ";
              }
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency!="") {
              respStr += "Frequency: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFrequency + ", ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsTv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsTv!="") {
              respStr += "TV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsTv + ", ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude!="") {
              respStr += "Amplitude: " + this.respiratorySystemObj.respSystemObject.respSupport.rsAmplitude + ", ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFio2!="") {
              respStr += "FiO2: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFio2 + " %, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate!="") {
              respStr += "Flow Rate: " + this.respiratorySystemObj.respSystemObject.respSupport.rsFlowRate + " Liters/Min, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsPip!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPip!="") {
              respStr += "PIP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPip + " cm H2O, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsPeep!="") {
              respStr += "PEEP: " + this.respiratorySystemObj.respSystemObject.respSupport.rsPeep + " cm H2O, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsIt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsIt!="") {
              respStr += "IT: " + this.respiratorySystemObj.respSystemObject.respSupport.rsIt + " secs, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsEt!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsEt!="") {
              respStr += "ET: " + this.respiratorySystemObj.respSystemObject.respSupport.rsEt + " secs, ";
            }
            if(this.respiratorySystemObj.respSystemObject.respSupport.rsMv!=null && this.respiratorySystemObj.respSystemObject.respSupport.rsMv!="") {
              respStr += "MV: " + this.respiratorySystemObj.respSystemObject.respSupport.rsMv + ", ";
            }
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname==null
                && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone==null){
              template += "Treatment given is  "+this.respiratorySystemObj.respSystemObject.respSupport.rsVentType;
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
                if(this.pneumoTreatmentStatus.value!=null){
                  if(this.pneumoTreatmentStatus.value=='Continue'){
                    this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += " Respiratory support continued to ";
                  }else{
                    this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += " Respiratory support changed to ";
                  }
                }else{
                  this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += " Respiratory support ";
                }
              }else{
                this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", Respiratory support ";
              }
            }else{}
            if(respStr ==""){} else {
              respStr = respStr.substring(0, (respStr.length - 2));
              template += " (" +respStr+ "). ";
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
                this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += respStr;
              }else{
                this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += "("+respStr+")";
              }
            }
          }
        }
        if(this.respiratorySystemObj.respSystemObject.respSupport.isactive!=null
            && this.respiratorySystemObj.respSystemObject.respSupport.isactive==true
            && this.respiratorySystemObj.respSystemObject.respSupport.eventid!=null
            && (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null
                ||this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE016")==-1)){
          template += "Baby was removed from Respiratory Support. ";
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList!=null){
          var medicineObj = this.createMedicinesNotesText(this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList);
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText +=
            medicineObj.selectedStr;
          template += medicineObj.notesStr;
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE018")!=-1){
          var tempText = "Needle Aspiration done ";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight==true
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft==true){
            tempText += "on both side";
          }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationRight==true){
            tempText += "on right side";
          }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.needleAspirationLeft==true){
            tempText += "on left side";
          }
          if(tempText!=''){
            template += tempText+". ";
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += tempText;
            }else{
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", "+tempText;
            }
          }
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("TRE019")!=-1){
          var rightTubesText = "";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length==1){
            rightTubesText +="Chest tube inserted on right side ("+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[0].chesttubeValue+"";
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[0].chestTubeAdjustedValue!=null){
              rightTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[0].chestTubeAdjustedValue;
            }
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[0].clampStatus!=null){
              rightTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[0].clampStatus;
            }
            rightTubesText +=").";
          }else{
            for(var index=0; index<this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length; index++){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].chesttubeValue!=null
                  && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination==true){
                console.log(this.PneumoTempObj.mapForNumber);
                rightTubesText +=this.PneumoTempObj.mapForNumber[(index*1+1)]+" chest tube inserted on right side ("+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].chesttubeValue+"";
                if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].chestTubeAdjustedValue!=null){
                  rightTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].chestTubeAdjustedValue;
                }
                if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].clampStatus!=null){
                  rightTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[index].clampStatus;
                }
                rightTubesText +=").";
              }
            }
          }
          if(rightTubesText!=''){
            template += rightTubesText;
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += rightTubesText;
            }else{
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", "+rightTubesText;
            }
          }
          var leftTubesText = "";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length==1){
            leftTubesText +="Chest tube inserted on left side ("+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[0].chesttubeValue+"";
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[0].chestTubeAdjustedValue!=null){
              leftTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[0].chestTubeAdjustedValue;
            }
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[0].clampStatus!=null){
              leftTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[0].clampStatus;
            }
            leftTubesText +=").";
          }else{
            for(var index=0; index<this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length; index++){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].chesttubeValue!=null
                  && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination==true){
                leftTubesText +=this.PneumoTempObj.mapForNumber[(index*1+1)]+" chest tube inserted on left side ("+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].chesttubeValue+"";
                if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].chestTubeAdjustedValue!=null){
                  leftTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].chestTubeAdjustedValue;
                }
                if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].clampStatus!=null){
                  leftTubesText += ", "+this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[index].clampStatus;
                }
                leftTubesText +=").";
              }
            }
          }
          if(leftTubesText!=''){
            template += leftTubesText;
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += leftTubesText;
            }else{
              this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", "+leftTubesText;
            }
          }
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.indexOf("other")!=-1
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther!=null
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther!=''){
          template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther + ". ";
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
            this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ""+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther;
          }else{
            this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", "+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther;
          }
        }
      }
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr != undefined && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr != null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr != '') {
        template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr + ". ";
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText==''){
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ""+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr;
        }else{
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText += ", "+ this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.medicationStr;
        }
      }
      //cause of pneumo
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList!=null && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList.length>0){
        this.causeOfPneumoStr ="";
        for(var index=0; index<this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList.length;index++){
          for(var indexDrop=0; indexDrop<this.respiratorySystemObj.dropDowns.causeOfPneumo.length;indexDrop++){
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList[index] == this.respiratorySystemObj.dropDowns.causeOfPneumo[indexDrop].key){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.pneumothoraxCauseList[index]=='other'
                && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherCause!=null){
                if(this.causeOfPneumoStr==''){
                  this.causeOfPneumoStr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherCause;
                }else{
                  this.causeOfPneumoStr +=", " + this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherCause;
                }
              }else{
                if(this.causeOfPneumoStr==""){
                  this.causeOfPneumoStr = this.respiratorySystemObj.dropDowns.causeOfPneumo[indexDrop].value;
                }else{
                  this.causeOfPneumoStr = this.causeOfPneumoStr +", "+ this.respiratorySystemObj.dropDowns.causeOfPneumo[indexDrop].value;
                }
              }
            }
          }
        }
        if(this.causeOfPneumoStr!=''){
          if(this.causeOfPneumoStr.indexOf(",")==-1){
            template = template +"Most likely cause is "+this.causeOfPneumoStr+". ";
          }else{
            template = template +"Most likely cause are "+this.causeOfPneumoStr+". ";
          }
        }
      }else{
        this.causeOfPneumoStr ="";
      }
      //for plan to reassess the baby
      var planAddedd = false;
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime!=null &&
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime!=""
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestimeType!=null){
        var planAddedd = true;
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestimeType=='Mins'){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime*1 == 1){
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" Min ";
          }else{
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" Mins ";
          }
        }else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestimeType=='Hours'){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime*1 == 1){
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" hour ";
          }else{
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" hours ";
          }
        } else {
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime*1 == 1){
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" day ";
          }else{
            template += "Plan is to reassess the baby after "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.reassestime+" days ";
          }
        }
      }
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherplanComments!=null
          && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherplanComments!=''){
        if(planAddedd==true){
          template += "and "+this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherplanComments+". ";
        }else{
          template += this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.otherplanComments+". ";
        }
      }else if(planAddedd==true){
        template += ".";
      }
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.progressnotes = template;
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.eventstatus=='Yes')
        this.ageOnsetValidationEpisode('pneumothorax');
    }
    selectSideOfTransillumination = function(){
      if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination){
        $("#needleRight").css("display","inline-block");
        $("#needleLeft").css("display","none");
      }
      else if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination){
        $("#needleRight").css("display","none");
        $("#needleLeft").css("display","inline-block");
      }
      else if(!this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination){
        $("#needleRight").css("display","none");
        $("#needleLeft").css("display","none");
      }
      else{
        $("#needleLeft").css("display","inline-block");
        $("#needleRight").css("display","inline-block");
      }
      this.saveTreatmentPneumo('chestTube');
      this.creatingProgressNotesPneumoTemplate();
    }
    removeChestTube = function(type){
      if(type=='right'){
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length>0
          && this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length-1]
          .resppneumothoraxid==null){
            this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.pop();
            if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length>0){
              this.pneumothoraxTempObj.rightSelectedTubeValue = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length-1;
            }
          }
        }
        if(type=='left'){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length>0
            && this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length-1]
            .resppneumothoraxid==null){
              this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.pop();
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length>0){
                this.pneumothoraxTempObj.rightSelectedTubeValue = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length-1;
              }
            }
          }
          this.saveTreatmentPneumo('chestTube');
          this.creatingProgressNotesPneumoTemplate();
        }
      openCloseChestTube = function(selectedTube,side,indexValue){
          console.log(selectedTube);
          if(side=='right'){
            this.pneumothoraxTempObj.rightSelectedTubeValue = indexValue;
            this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightObjectToShow = selectedTube;
          }else if(side=='left')
          this.pneumothoraxTempObj.leftSelectedTubeValue = indexValue;
          this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftObjectToShow = selectedTube;
        }
  insertChestTube = function(type){
			if(type=='right'){
        var chestTubeEmpObj = Object.assign({}, this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.emptyTube);
				chestTubeEmpObj.isLeftChestTube = false;
				chestTubeEmpObj.uhid = this.childObject.uhid;
				this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.push(chestTubeEmpObj);
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length>0){
					this.pneumothoraxTempObj.rightSelectedTubeValue = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length-1;
				}
			}
			if(type=='left'){
        var chestTubeEmpObj = Object.assign({}, this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.emptyTube);
				chestTubeEmpObj.isLeftChestTube = true;
				chestTubeEmpObj.uhid = this.childObject.uhid;
				this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.push(chestTubeEmpObj);
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length>0){
					this.pneumothoraxTempObj.leftSelectedTubeValue = this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length-1;
				}
			}
			this.saveTreatmentPneumo('chestTube');
			this.creatingProgressNotesPneumoTemplate();
		}

    saveTreatmentPneumo = function(treatmentType){
      console.log('saveTreatmentPphn ' + treatmentType);
      switch(treatmentType)
      {
      case 'respiratory':
        if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
            || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE016'))){
          this.onCheckMultiCheckBoxValue('TRE016','pneumoTreatmentAction',true);
          this.isRdsSavedPneumo = true;
        }
        break;
      case 'surfactant':
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname!=null
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.sufactantname!=''
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose!=null
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.surfactantDose!=''){
          this.onCheckMultiCheckBoxValue('TRE015','pneumoTreatmentAction',true);
          this.isSurfactantSavedPneumo = true;
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.isinsuredone!=null){
            if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
                || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                    && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE015'))){
            }
            if(this.PneumoTempObj.fromInsureContinue==true){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE016')){
                this.callRespiratorySupportPopUP('Pneumothorax', true);
              }else{
                this.callRespiratorySupportPopUP('Pneumothorax', false);
              }
            }
          }
        } else {
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE015')){
            this.onCheckMultiCheckBoxValue('TRE015','pneumoTreatmentAction',false);
            this.isSurfactantSavedPneumo = false;
          }
        }
        break;
      case 'antibiotic':
        var allRequired = false;
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList!=null
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList.length>0){
          var antibioticArr = this.respiratorySystemObj.respSystemObject.pneumothorax.currentBabyPrescriptionList;
          for(var index=0; index<antibioticArr.length;index++){
            if(antibioticArr[index].route!=null && antibioticArr[index].route!='' && antibioticArr[index].dose!=null && antibioticArr[index].dose!=''){
              allRequired = true;
            } else {
              allRequired = false;
              break;
            }
          }
        }
        if(allRequired){
          if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
              || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                  && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE017'))){
            this.onCheckMultiCheckBoxValue('TRE017','pneumoTreatmentAction',true);
            this.isAntibioticsSavedPneumo = true;
          }
        } else {
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE017')){
            this.onCheckMultiCheckBoxValue('TRE017','pneumoTreatmentAction',false);
            this.isAntibioticsSavedPneumo = false;
          }
        }
        break;
      case 'pneumoNeedle':
        if(this.pneumothoraxTempObj.needleAspDate != null && this.pneumothoraxTempObj.needleAspDate != ""
          && this.pneumothoraxTempObj.needleAspHour != null && this.pneumothoraxTempObj.needleAspHour != ""
            && this.pneumothoraxTempObj.needleAspMin != null && this.pneumothoraxTempObj.needleAspMin != ""
              && this.pneumothoraxTempObj.needleAspMeridian != null && this.pneumothoraxTempObj.needleAspMeridian != ""){
          this.onCheckMultiCheckBoxValue('TRE018','pneumoTreatmentAction',true);
          this.isNeedleAspirationSavedPneumo = true;
        }
        else{
          this.isNeedleAspirationSavedPneumo = false ;
        }
        break;
      case 'chestTube':
        console.log("chest tube");
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination != null){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.lefttransillumination
              && this.isChestTubeSelectPneumo!=null && this.isChestTubeSelectPneumo==true){
            for(var i=0 ; i <this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes.length ; i++){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.leftTubes[i].chesttubeValue != null){
                if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
                    || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                        && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE019'))){
                  this.onCheckMultiCheckBoxValue('TRE019','pneumoTreatmentAction',true);
                  this.isChestTubeSavedPneumo = true;
                }
              }
            }
          }
        }
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination != null){
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.righttransillumination
              && this.isChestTubeSelectPneumo!=null && this.isChestTubeSelectPneumo==true){
            for(var i=0 ; i <this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes.length ; i++){
              if(this.respiratorySystemObj.respSystemObject.pneumothorax.chestTubeObj.rightTubes[i].chesttubeValue != null){
                if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
                    || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                        && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('TRE019'))){
                  this.onCheckMultiCheckBoxValue('TRE019','pneumoTreatmentAction',true);
                  this.isChestTubeSavedPneumo = true;
                }
              }
            }
          }
        }
        break;
      case 'other':
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther!=null
            && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentOther!=''){
          if((this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList==null)
              || (this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
                  && !this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('other'))){
            this.onCheckMultiCheckBoxValue('other','pneumoTreatmentAction',true);
          }
          this.isOtherSavedPneumo = true;
        } else {
          if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList!=null
              && this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.includes('other')){
            this.onCheckMultiCheckBoxValue('other','pneumoTreatmentAction',false);
            this.isOtherSavedPneumo = false;
          }
        }
        break;
      }
      this.creatingProgressNotesPneumoTemplate();
    }
    clinicalSectionPhenumothoraxVisible = function(){
      this.isclinicalSectionPhenumothoraxVisible = !this.isclinicalSectionPhenumothoraxVisible;
    }
    actionSectionPhenumothoraxVisible = function(){
      this.isActionPhenumothoraxVisible = !this.isActionPhenumothoraxVisible;
    }
    PhenumothoraxsurfactantInsure = function(){
      this.isPhenumothoraxsurfactantInsure = !this.isPhenumothoraxsurfactantInsure;
    }
    causePneumothoraxSectionVisible = function(){
      this.isCausePneumothoraxVisible = !this.isCausePneumothoraxVisible;
    }
    progressNotesPneumothoraxSectionVisible = function(){
      this.isProgressNotesPneumothoraxVisible = !this.isProgressNotesPneumothoraxVisible;
    }
    planPneumothoraxSectionVisible = function(){
      this.isPlanPneumothoraxVisible = !this.isPlanPneumothoraxVisible;
    }
	calculateAgeAtAssessmentPneumo = function(changedParam) {
			if(changedParam=='meridian'){
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays && this.ageAtAssessmentPneumoHourFlag){
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment *= 24;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment += this.hourDayDiffAgeAtAssessmentPneumo;
					this.ageAtAssessmentPneumoHourFlag = false;
				}else if(!(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageAtAssessmentInhoursDays || this.ageAtAssessmentPneumoHourFlag)){
					this.ageAtAssessmentPneumoHourFlag = true;
					this.hourDayDiffAgeAtAssessmentPneumo = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment%24;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment -= this.hourDayDiffAgeAtAssessmentPneumo;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatassesment /= 24;
				}
			}else{
				this.hourDayDiffAgeAtAssessmentPneumo = 0;
			}
		}
    calAgeOnsetPneumo = function(changedParam) {
			//this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset = this.respiratorySystemObj.ageAtOnset;
			if(changedParam=='meridian'){
				if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays){
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset *= 24;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset += this.hourDayDiff;
				}else if(!(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageinhoursdays)){
					this.hourDayDiff = this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset%24;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset -= this.hourDayDiff;
					this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.ageatonset /= 24;
				}
			}else{
				this.hourDayDiff = 0;
			}
			this.creatingProgressNotesPneumoTemplate();
		}
    continueTreatmentPneumo = function(){
      if(this.pneumoTreatmentStatus.value=='Continue'){
        this.treatmentTabsVisiblePneumo = false;
      }else if(this.pneumoTreatmentStatus.value=='Change'){
        this.treatmentTabsVisiblePneumo = true;
      }
      this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText = "";
      var previousTreatment = this.respiratorySystemObj.respSystemObject.pneumothorax.pastRespPneumo["0"];
      var treatmentPneumoList = previousTreatment.treatmentaction.replace("[","").replace("]","").replace(" ","").split(",");
      var symptomArr = [];
      for(var index = 0; index<treatmentPneumoList.length; index++){
        if( treatmentPneumoList[index].trim() == "TRE019"){
          this.isChestTubeSavedPneumo = true;
          this.isChestTubeSelectPneumo  = true;
          symptomArr.push(treatmentPneumoList[index].trim());
        }
        if((treatmentPneumoList[index].trim() == "TRE016" && this.respiratorySystemObj.respSystemObject.respSupport!=null
        && this.respiratorySystemObj.respSystemObject.respSupport.rsVentType!=null)){
          if(this.pneumoTreatmentStatus.value=='Continue'){
            this.isRdsSelectPneumo  = false;
            this.isRdsSavedPneumo  = false;
          }else if(this.pneumoTreatmentStatus.value=='Change'){
            this.isRdsSelectPneumo  = true;
            this.isRdsSavedPneumo  = true;
          }
          symptomArr.push(treatmentPneumoList[index].trim());
        }
      }
      if(!symptomArr.includes('TRE016')) {
        if(this.respiratorySystemObj.respSystemObject.respSupport != null
          && this.respiratorySystemObj.respSystemObject.respSupport.creationtime != null
          && this.respiratorySystemObj.respSystemObject.respSupport.isactive) {
            console.log('respiratory support object Pneumothorax');
            symptomArr.push('TRE016');
            this.isRdsSavedPneumo  = true;
          }
        }
        this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList = symptomArr;
        if(this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentActionList.length==0){
          this.respiratorySystemObj.respSystemObject.pneumothorax.currentRespPneumo.treatmentSelectedText = "Previous treatment continued";
        }else{}
        this.creatingProgressNotesPneumoTemplate();
      }
      showWarningPopUp = function() {
        $("#OkWarningPopUp").addClass("showing");
        $("#warningOverlay").addClass("show");
      }
      closeWarningPopUp = function(){
        this.warningMessage = "";
        $("#OkWarningPopUp").removeClass("showing");
        $("#warningOverlay").removeClass("show");
      }

      showMedicationPopUp = function() {
        $("#medicationPopup").addClass("showing");
        $("#MedicationOverlay").addClass("show");
      };
      closeMedicationPopUp = function(){
        $("#medicationPopup").removeClass("showing");
        $("#MedicationOverlay").removeClass("show");
      };

      toggleAll = function(event) {
        var bool = true;
        if (event.target.checked) {
          bool = false;
        }
        this.checked = !bool;
        this.selectAll = !bool;

      }

      stopAllMed(selectAll : any) {
        if(this.selectAll == null || this.selectAll == false){
          this.selectAll = true;
        }
        else{
          this.selectAll = false;
        }

        for(var index=0;index<this.pastPrescriptionList.length;index++){
          this.toggleIscontinue(index);
          if(this.selectAll == true){
            this.pastPrescriptionList[index].isStopped = true;
            this.pastPrescriptionList[index].enddate = new Date();
          }
          else{
            this.pastPrescriptionList[index].isStopped = false;
            this.pastPrescriptionList[index].enddate = null;
          }
        }
      }

      setEndDate(index : any){
        if(this.pastPrescriptionList[index].isStopped == null || this.pastPrescriptionList[index].isStopped == false){
          this.pastPrescriptionList[index].isStopped = true;
        }
        else{
          this.pastPrescriptionList[index].isStopped = false;
        }
        this.updateEndDate(index);
      }

      updateEndDate(index : any){
        this.toggleIscontinue(index);
        if(this.pastPrescriptionList[index].isStopped == true){
          this.pastPrescriptionList[index].enddate = new Date();
        }
        else{
          this.pastPrescriptionList[index].enddate = null;
        }
      }

      commentBoxEnable(index : any){
        this.pastPrescriptionList[index].isContinue = true;
        this.pastPrescriptionList[index].isStopped = false;
        this.pastPrescriptionList[index].enddate = null;
        this.selectAll = false;
      }

      toggleIscontinue(index : any){
        this.pastPrescriptionList[index].isContinue = false;
        this.pastPrescriptionList[index].continueReason = "";
      }

      saveMedicine (){
        for(var i = 0; i < this.pastPrescriptionList.length; i++) {
          var obj = this.pastPrescriptionList[i];
          if(obj.eventname == this.currentEvent) {
            if(obj.isactive == true && obj.enddate != null){
              obj.enddate = new Date(obj.enddate);
              obj.isactive = false;
              this.createRespectiveList(obj);
            }
            else if(obj.isactive == true && obj.isContinue != null && obj.isContinue == true && obj.continueReason != null && obj.continueReason != ''){
              obj.eventname = "Stable Notes";
              this.createRespectiveList(obj);
            }
          }
        }
        this.closeMedicationPopUp();
      }
      createRespectiveList = function(data : any){
        if(this.currentEvent == 'RDS'){
          this.respiratorySystemObj.respSystemObject.respiratoryDistress.prescriptionList.push(data);
        }
        if(this.currentEvent == 'Apnea'){
          this.respiratorySystemObj.respSystemObject.apnea.prescriptionList.push(data);
        }
        if(this.currentEvent == 'PPHN'){
          this.respiratorySystemObj.respSystemObject.pphn.prescriptionList.push(data);
        }
        if(this.currentEvent == 'Pneumothorax'){
          this.respiratorySystemObj.respSystemObject.pneumothorax.prescriptionList.push(data);
        }
      }

  ngOnInit() {}
}
