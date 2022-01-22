import { Component, OnInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { DischargeTempObj } from './dischargetempObj';
import { DischargedSummaryData } from './dischargedSummaryData';
import { Router } from '@angular/router';
import * as $ from 'jquery';
@Component({
  selector: 'app-discharge-summary',
  templateUrl: './discharge-summary.component.html',
  styleUrls: ['./discharge-summary.component.css']
})
export class DischargeSummaryComponent implements OnInit {
	  apiData : APIurl;
  	http: Http;
  	dischargeTempObj : DischargeTempObj;
  	dischargedSummaryData : DischargedSummaryData;
  	whichDischargeSummaryPage : string;
  	dischargeSummaryPage2WhichTab : string;
  	courseWhichTab : string;
    courseWhichAssesTab : string;
  	isComeFromDischargeList : boolean;
  	isEditFirstDischargeAdvice : boolean;
  	isEditSecondDischargeAdvice : boolean;
  	isEditThirdDischargeAdvice : boolean;
  	isEditFourDischargeAdvice : boolean;
  	isEditFiveDischargeAdvice : boolean;
  	isEditSixDischargeAdvice : boolean;
  	isEditSevenDischargeAdvice : boolean;
  	isEditEightDischargeAdvice : boolean;
    isEditNinthDischargeAdvice : boolean;
    isEditTwelthDischargeAdvice : boolean;
    isEditThirteenthDischargeAdvice : boolean;
    isEditFourteenthDischargeAdvice : boolean;
    isEditFifteenthDischargeAdvice : boolean;
    isEditSixteenthDischargeAdvice : boolean;
    isEditTenthDischargeAdvice : boolean;
    isEditEleventhDischargeAdvice : boolean;
    isSelectedMed : boolean;
    timeOfBirthStr : string;
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
    babyAge : number;
    provisionalNotesRds : string;
    provisionalNotesApnea : string;
    provisionalNotesPphn : string;
    provisionalNotesPneumothorax : string;
    provisionalNotesJaundice : string;
    provisionalNotesAsphyxia : string;
    provisionalNotesSeizure : string;
    provisionalNotesSepsis : string;
    provisionalNotesMiscellaneous : string;
    currProcedure : string;
    currBloodProduct : string;
    currGrowth : string;
    currNutrition : string;
    currVaccination : string;
    currScreening : string;
    updateResult : boolean;
    assessment : string;
    currDate : Date;
    prevDischargeDate : Date;
    isPhysicalExamination : boolean;
  	constructor(http: Http,private router: Router) {
	  	this.http = http;
	  	this.dischargeTempObj = new DischargeTempObj();
	   	this.apiData = new APIurl();
	   	this.whichDischargeSummaryPage = 'page1';
	   	this.isComeFromDischargeList = false;
	   	this.isEditFirstDischargeAdvice = false;
	   	this.isEditSecondDischargeAdvice = false;
  		this.isEditThirdDischargeAdvice = false;
  		this.isEditFourDischargeAdvice = false;
  		this.isEditFiveDischargeAdvice = false;
  		this.isEditSixDischargeAdvice = false;
  		this.isEditSevenDischargeAdvice = false;
  		this.isEditEightDischargeAdvice = false;
  		this.isEditNinthDischargeAdvice = false;
      this.isEditTenthDischargeAdvice = false;
      this.isEditNinthDischargeAdvice = false;
      this.isEditFifteenthDischargeAdvice = false;
      this.isEditSixteenthDischargeAdvice = false;
      this.isEditEleventhDischargeAdvice = false;
      this.isEditTwelthDischargeAdvice = false;
      this.isEditThirteenthDischargeAdvice = false;
      this.isEditFourteenthDischargeAdvice = false;
      this.isSelectedMed = false;
      this.timeOfBirthStr = "";
      this.ageHours = null;
      this.babyAge = null;
      this.provisionalNotesRds = null;
      this.provisionalNotesApnea = null;
      this.provisionalNotesPphn = null;
      this.provisionalNotesPneumothorax = null;
      this.provisionalNotesJaundice = null;
      this.provisionalNotesAsphyxia = null;
      this.provisionalNotesSeizure = null;
      this.provisionalNotesMiscellaneous = null;
      this.currProcedure = null;
      this.currBloodProduct = null;
      this.currNutrition = null;
      this.currVaccination = null;
      this.currGrowth = null;
      this.currScreening = null;
      this.updateResult = null;
      this.currDate = new Date();
      this.prevDischargeDate = null;
      this.isPhysicalExamination = false;
	}

  getDischargedSummary(){
  	let child = JSON.parse(localStorage.getItem('selectedChild'));
	this.dischargeTempObj.dateofdischarge = new Date();
	try{
        this.http.request(this.apiData.getDischargeSummary+'/'+child.episodeid+'/'+child.uhid+'/').subscribe((res: Response) => {
            this.handleResponseData(res.json());
	    });
	    }catch(e){
	        console.log("Exception in http service:"+e);
	 	};
	}
	handleResponseData(responseData){
		console.log(responseData);
		this.dischargedSummaryData = responseData;
    if(this.dischargedSummaryData != null && this.dischargedSummaryData.babyDetails != null && this.dischargedSummaryData.babyDetails.timeofbirth != null){
      var timeOfBirthHr = this.dischargedSummaryData.babyDetails.timeofbirth.slice(0, 2);
      var timeOfBirthMin = this.dischargedSummaryData.babyDetails.timeofbirth.slice(3, 5);
      var timeOfBirthMer = this.dischargedSummaryData.babyDetails.timeofbirth.slice(6, 8);
      this.timeOfBirthStr = timeOfBirthHr +':'+timeOfBirthMin+ ' '+ timeOfBirthMer;
      if(this.dischargedSummaryData.babyDetails.dateofbirth != null && this.dischargedSummaryData.outcome != null
        && this.dischargedSummaryData.outcome.entrytime != null){
        var birthDate = new Date(this.dischargedSummaryData.babyDetails.dateofbirth);
        var dischargeDate = new Date(this.dischargedSummaryData.outcome.entrytime);
        var ageOfBaby = Math.abs(Math.ceil((dischargeDate.getTime() - birthDate.getTime())/(1000*60*60*24)));
        if(ageOfBaby == null || ageOfBaby == 0){
          ageOfBaby = 1;
        }
        this.babyAge = ageOfBaby;
        localStorage.setItem('babyAge',this.babyAge.toString());
        if(this.dischargedSummaryData.dischargeNotes != null){
          this.provisionalNotesRds = this.dischargedSummaryData.dischargeNotes.provisionalNotesRds;
          this.provisionalNotesApnea = this.dischargedSummaryData.dischargeNotes.provisionalNotesApnea;
          this.provisionalNotesPphn = this.dischargedSummaryData.dischargeNotes.provisionalNotesPphn;
          this.provisionalNotesPneumothorax = this.dischargedSummaryData.dischargeNotes.provisionalNotesPneumothorax;
          this.provisionalNotesJaundice = this.dischargedSummaryData.dischargeNotes.provisionalNotesJaundice;
          this.provisionalNotesAsphyxia = this.dischargedSummaryData.dischargeNotes.provisionalNotesAsphyxia;
          this.provisionalNotesSeizure = this.dischargedSummaryData.dischargeNotes.provisionalNotesSeizure;
          this.provisionalNotesSepsis = this.dischargedSummaryData.dischargeNotes.provisionalNotesSepsis;
          this.provisionalNotesMiscellaneous = this.dischargedSummaryData.dischargeNotes.provisionalNotesMiscellaneous;
          this.currBloodProduct = this.dischargedSummaryData.dischargeNotes.currBloodProduct;
          this.currProcedure = this.dischargedSummaryData.dischargeNotes.currProcedure;
          this.currGrowth = this.dischargedSummaryData.dischargeNotes.currGrowth;
          this.currNutrition = this.dischargedSummaryData.dischargeNotes.currNutrition;
          this.currVaccination = this.dischargedSummaryData.dischargeNotes.currVaccination;
          this.currScreening = this.dischargedSummaryData.dischargeNotes.currScreening;

          if((this.dischargedSummaryData.dischargeNotes.rdsNotes != null && this.dischargedSummaryData.dischargeNotes.rdsNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.apneaNotes != null && this.dischargedSummaryData.dischargeNotes.apneaNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.pphnNotes != null && this.dischargedSummaryData.dischargeNotes.pphnNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.pneumoNotes != null && this.dischargedSummaryData.dischargeNotes.pneumoNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.jaundice != null && this.dischargedSummaryData.dischargeNotes.jaundice != '') ||
             (this.dischargedSummaryData.dischargeNotes.asphyxiaNotes != null && this.dischargedSummaryData.dischargeNotes.asphyxiaNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.seizureNotes != null && this.dischargedSummaryData.dischargeNotes.seizureNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.sepsisNotes != null && this.dischargedSummaryData.dischargeNotes.sepsisNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.miscellaneousNotes != null && this.dischargedSummaryData.dischargeNotes.miscellaneousNotes != '') ||
             (this.dischargedSummaryData.dischargeNotes.growth != null && this.dischargedSummaryData.dischargeNotes.growth != '') ||
             (this.dischargedSummaryData.dischargeNotes.nutrition != null && this.dischargedSummaryData.dischargeNotes.nutrition != '') ||
             (this.dischargedSummaryData.dischargeNotes.bloodProduct != null && this.dischargedSummaryData.dischargeNotes.bloodProduct != '') ||
             (this.dischargedSummaryData.dischargeNotes.procedure != null && this.dischargedSummaryData.dischargeNotes.procedure != '') ||
             (this.dischargedSummaryData.dischargeNotes.screening != null && this.dischargedSummaryData.dischargeNotes.screening != ''))
             this.prevDischargeDate = this.dischargedSummaryData.dischargeNotes.creationtime;
        }
        else{
            this.prevDischargeDate = null;
        }

        if((this.dischargedSummaryData.babyPhysicalExamData.apearance != null && this.dischargedSummaryData.babyPhysicalExamData.apearance != 'Active')
         || (this.dischargedSummaryData.babyPhysicalExamData.head_neck != null && this.dischargedSummaryData.babyPhysicalExamData.head_neck == 'Abnormal')
         || (this.dischargedSummaryData.babyPhysicalExamData.palate != null && this.dischargedSummaryData.babyPhysicalExamData.palate != 'Normal')
         || (this.dischargedSummaryData.babyPhysicalExamData.lip != null && this.dischargedSummaryData.babyPhysicalExamData.lip != 'Normal')
         || (this.dischargedSummaryData.babyPhysicalExamData.eyes != null && this.dischargedSummaryData.babyPhysicalExamData.eyes == 'Abnormal')
         || (this.dischargedSummaryData.babyPhysicalExamData.skin != null && this.dischargedSummaryData.babyPhysicalExamData.skin != 'Normal')
    		 || (this.dischargedSummaryData.babyPhysicalExamData.chest != null && this.dischargedSummaryData.babyPhysicalExamData.chest == 'Abnormal')
    		 || (this.dischargedSummaryData.babyPhysicalExamData.abdomen != null && this.dischargedSummaryData.babyPhysicalExamData.abdomen == 'Abnormal')
         || (this.dischargedSummaryData.babyPhysicalExamData.anal != null && this.dischargedSummaryData.babyPhysicalExamData.anal != 'Patent')
         || (this.dischargedSummaryData.babyPhysicalExamData.genitals != null && this.dischargedSummaryData.babyPhysicalExamData.genitals == 'Abnormal')
         || (this.dischargedSummaryData.babyPhysicalExamData.reflexes != null && this.dischargedSummaryData.babyPhysicalExamData.reflexes != 'Abnormal')
         || (this.dischargedSummaryData.babyPhysicalExamData.cong_malform != null && this.dischargedSummaryData.babyPhysicalExamData.cong_malform == 'Yes')){
          this.isPhysicalExamination = true;
        }
      }
      //this.calculateOtherDetails();
    }
		console.log(this.dischargedSummaryData);
	}
	saveDischargedSummary(whichPage){
		let child = JSON.parse(localStorage.getItem('selectedChild'));
		//let loggedInUserObj = JSON.parse(localStorage.getItem('selectedChild'));
		this.dischargedSummaryData.whichPage = whichPage;
		console.log("saving discharge summary");
		let dischargedDateTemp = new Date(this.dischargedSummaryData.outcome.entrytime);
		this.dischargedSummaryData.babyDetails.dischargeddate = this.dateformatter(dischargedDateTemp,'gmt','standard');
		console.log(this.dischargedSummaryData.babyDetails.dischargeddate);
		//this.dischargedSummaryData.loggedUser  =loggedInUserObj.userName;
		this.dischargedSummaryData.uhid = child.uhid;
		console.log(this.dischargedSummaryData);
		try
          {
            this.http.post(this.apiData.saveDischargedSummary,
            	this.dischargedSummaryData).subscribe(res => {
                    this.handleDischargeSaveData(res.json(),whichPage);
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
	handleDischargeSaveData(responseData, whichPage){
		console.log(responseData);
		console.log(whichPage);
		if(whichPage == '1stPage'){
			this.whichDischargeSummaryPage = 'page2';
		}
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

		savingAntenatalHistory(){
			this.dischargedSummaryData.dischargeNotes.isAntenatalHistory = true;
			this.saveDischargedSummary('2ndPage');
		}

    savingBirthDetails(){
      this.dischargedSummaryData.dischargeNotes.isBirthDetails = true;
			this.saveDischargedSummary('2ndPage');
		}

    savingStatusAtBirth(){
			this.dischargedSummaryData.dischargeNotes.isStatusAtAdmission = true;
			this.saveDischargedSummary('2ndPage');
		}

    savingGrowth(){
      this.dischargedSummaryData.dischargeNotes.isGrowth = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateGrowth(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.growth = this.currGrowth;
      }
    }

		savingStableNotes(){
			this.dischargedSummaryData.dischargeNotes.isStableNotes = true;
			this.saveDischargedSummary('2ndPage');
		}

    savingRdsNotes(){
			this.dischargedSummaryData.dischargeNotes.isRdsNotes = true;
			this.saveDischargedSummary('2ndPage');
		}

    updateRdsNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.rdsNotes = this.provisionalNotesRds;
      }
    }

    savingApneaNotes(){
      this.dischargedSummaryData.dischargeNotes.isApneaNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateApneaNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.apneaNotes = this.provisionalNotesApnea;
      }
    }

    savingPphnNotes(){
      this.dischargedSummaryData.dischargeNotes.isPphnNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updatePphnNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.pphnNotes = this.provisionalNotesPphn;
      }
    }

    savingPneumothoraxNotes(){
      this.dischargedSummaryData.dischargeNotes.isPneumoNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updatePneumothoraxNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.pneumoNotes = this.provisionalNotesPneumothorax;
      }
    }

    savingJaundice(){
      this.dischargedSummaryData.dischargeNotes.isJaundice = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateJaundice(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.jaundice = this.provisionalNotesJaundice;
      }
    }

    savingAsphyxiaNotes(){
      this.dischargedSummaryData.dischargeNotes.isAsphyxiaNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateAsphyxiaNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.asphyxiaNotes = this.provisionalNotesAsphyxia;
      }
    }

    savingSeizureNotes(){
      this.dischargedSummaryData.dischargeNotes.isSeizureNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateSeizureNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.seizureNotes = this.provisionalNotesSeizure;
      }
    }

    savingSepsisNotes(){
      this.dischargedSummaryData.dischargeNotes.isSepsisNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateSepsisNotes(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.sepsisNotes = this.provisionalNotesSepsis;
      }
    }

    savingMiscellaneous(){
      this.dischargedSummaryData.dischargeNotes.isMiscellaneousNotes = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateMiscellaneous(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.miscellaneousNotes = this.provisionalNotesMiscellaneous;
      }
    }

    savingTreatment(){
      this.dischargedSummaryData.dischargeNotes.isTreatment = true;
      this.saveDischargedSummary('2ndPage');
    }

    savingNutrition(){
      this.dischargedSummaryData.dischargeNotes.isNutrition = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateNutrition(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.nutrition = this.currNutrition;
      }
    }

    savingVaccination(){
      this.dischargedSummaryData.dischargeNotes.isVaccination = true;
      this.saveDischargedSummary('2ndPage');
    }


    updateVaccination(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.vaccination = this.currVaccination;
      }
    }

    savingBloodProduct(){
      this.dischargedSummaryData.dischargeNotes.isBloodProduct = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateBloodProduct(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.bloodProduct = this.currBloodProduct;
      }
    }

    savingProcedure(){
      this.dischargedSummaryData.dischargeNotes.isProcedure = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateProcedure(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.procedure = this.currProcedure;
      }
    }

    savingScreening(){
      this.dischargedSummaryData.dischargeNotes.isScreening = true;
      this.saveDischargedSummary('2ndPage');
    }

    updateScreening(){
      if(this.updateResult == true){
        this.dischargedSummaryData.dischargeNotes.screening = this.currScreening;
      }
    }

    savingAdviceOnDischarge(){
      var adviceString = "";
      var isSelectedAdviceOnDischarge = false;
      if(this.dischargedSummaryData.dischargeAdviceTemplates != null && this.dischargedSummaryData.dischargeAdviceTemplates.length > 0){
        for(var i=0;i<this.dischargedSummaryData.dischargeAdviceTemplates.length;i++){
          if(this.dischargedSummaryData.dischargeAdviceTemplates[i].isselected){
            isSelectedAdviceOnDischarge = true;
            adviceString += String(this.dischargedSummaryData.dischargeAdviceTemplates[i].fixedvalue);
            if(i!=1 && i!=5 && this.dischargedSummaryData.dischargeAdviceTemplates[i].editedvalue != null
              && this.dischargedSummaryData.dischargeAdviceTemplates[i].editedvalue != ''){
              adviceString += String(" " + this.dischargedSummaryData.dischargeAdviceTemplates[i].editedvalue) + " ";
            }
            if(this.dischargedSummaryData.dischargeAdviceTemplates[i].secondTempDate != null){
              adviceString += String(" " + this.dischargedSummaryData.dischargeAdviceTemplates[i].secondTempDate) + " ";
            }
            if(this.dischargedSummaryData.dischargeAdviceTemplates[i].sixthTempDate != null){
              adviceString += String(" " + this.dischargedSummaryData.dischargeAdviceTemplates[i].sixthTempDate);
            }
            adviceString += ".\n";
          }
        }
      }

      if(isSelectedAdviceOnDischarge == true && this.dischargedSummaryData.prescriptionList != null && this.dischargedSummaryData.prescriptionList.length > 0){
        adviceString += " Medication : \n";
        for(var i=0;i<this.dischargedSummaryData.prescriptionList.length;i++){
          if(this.dischargedSummaryData.prescriptionList[i].medicinename != null
            && this.dischargedSummaryData.prescriptionList[i].medicinename != ''){
            adviceString += String(this.dischargedSummaryData.prescriptionList[i].medicinename) + " ";
          }
          if(this.dischargedSummaryData.prescriptionList[i].dose != null
            && this.dischargedSummaryData.prescriptionList[i].dose != ''){
            adviceString += String(this.dischargedSummaryData.prescriptionList[i].dose) + " ";
          }
          if(this.dischargedSummaryData.prescriptionList[i].dose_unit != null
            && this.dischargedSummaryData.prescriptionList[i].dose_unit != ''){
            adviceString += String(this.dischargedSummaryData.prescriptionList[i].dose_unit) + " ";
          }
          if(this.dischargedSummaryData.prescriptionList[i].dose_unit_time != null
            && this.dischargedSummaryData.prescriptionList[i].dose_unit_time != ''){
            adviceString += String('/' +  this.dischargedSummaryData.prescriptionList[i].dose_unit_time);
          }
          adviceString += "\n";
        }
      }

      if(adviceString != null){
        this.dischargedSummaryData.dischargeNotes.advice = adviceString;
        localStorage.setItem('adviceOnDischarge',this.dischargedSummaryData.dischargeNotes.advice);
      }
      console.log(this.dischargedSummaryData.dischargeNotes.advice);
    }

		backToNextPage(whichPage){
			this.whichDischargeSummaryPage = whichPage;
      if(whichPage == 'Page3'){
        this.savingAdviceOnDischarge();
      }
      this.saveDischargedSummary('2ndPage');
		}

		getDischargePopup = function() {
      		if(this.dischargedSummaryData.assessmentMessage == null) {
  				$("#OkCancelPopUp").addClass("showing");
  				$("#dischargeConfirmOverlay").addClass("show");
      		}
        };
    	cancelOkPopUp(){
			$("#CancelPopUp").removeClass("showing");
			$("#dischargeRejectOverlay").removeClass("show");
		};
		closeOkPopUp(){
			$("#OkCancelPopUp").removeClass("showing");
			$("#dischargeConfirmOverlay").removeClass("show");
		};
		dischargeBaby(){
			this.dischargedSummaryData.isDischargeBaby = true;
			this.saveDischargedSummary('2ndPage');
			this.router.navigateByUrl('/discharged/dischargePatients');
		}

		generateDischargePrint = function(){
			localStorage.setItem('isDischargePreivew', JSON.stringify(true));
			if(this.isComeFromDischargeList == false)
			{  this.router.navigateByUrl('/discharge/discharge-summary-print');
			}
		};

		dischargeAdviseTemplate = function() {
			$("#dischargeAdviceOverlay").css("display", "block");
			$("#dischargeAdvicePopup").addClass("showing");
		};
		closeDischargeAdviseTemplate = function() {
			$("#dischargeAdviceOverlay").css("display", "none");
			$("#dischargeAdvicePopup").toggleClass("showing");
		};
//		end of the above code
		editFirstDischargeAdvice = function(){
			this.isEditFirstDischargeAdvice = !this.isEditFirstDischargeAdvice;
		}
		editSecondDischargeAdvice = function(){
			this.isEditSecondDischargeAdvice = !this.isEditSecondDischargeAdvice;
		}
		editThirdDischargeAdvice = function(){
			this.isEditThirdDischargeAdvice = !this.isEditThirdDischargeAdvice;
		}
		editFourDischargeAdvice = function(){
			this.isEditFourDischargeAdvice = !this.isEditFourDischargeAdvice;
		}
		editFiveDischargeAdvice = function(){
			this.isEditFiveDischargeAdvice = !this.isEditFiveDischargeAdvice;
		}
		editSixDischargeAdvice = function(){
			this.isEditSixDischargeAdvice = !this.isEditSixDischargeAdvice;
		}
		editSevenDischargeAdvice = function(){
			this.isEditSevenDischargeAdvice = !this.isEditSevenDischargeAdvice;
		}
		editEightDischargeAdvice = function(){
			this.isEditEightDischargeAdvice = !this.isEditEightDischargeAdvice;
		}
    editNinthDischargeAdvice = function(){
			this.isEditNinthDischargeAdvice = !this.isEditNinthDischargeAdvice;
		}
    editTenthDischargeAdvice = function(){
			this.isEditTenthDischargeAdvice = !this.isEditTenthDischargeAdvice;
		}
    editFifteenthDischargeAdvice = function(){
			this.isEditFifteenthDischargeAdvice = !this.isEditFifteenthDischargeAdvice;
		}
    editSixteenthDischargeAdvice = function(){
			this.isEditSixteenthDischargeAdvice = !this.isEditSixteenthDischargeAdvice;
		}
    editEleventhDischargeAdvice = function(){
			this.isEditEleventhDischargeAdvice = !this.isEditEleventhDischargeAdvice;
		}
    editTwelthDischargeAdvice = function(){
			this.isEditTwelthDischargeAdvice = !this.isEditTwelthDischargeAdvice;
		}
    editThirteenthDischargeAdvice = function(){
			this.isEditThirteenthDischargeAdvice = !this.isEditThirteenthDischargeAdvice;
		}
    editFourteenthDischargeAdvice = function(){
			this.isEditFourteenthDischargeAdvice = !this.isEditFourteenthDischargeAdvice;
		}

    calculateOtherDetails = function() {
      try{
        if(this.dischargedSummaryData.outcome.entrytime != null && this.dischargedSummaryData.outcome.entrytime != undefined){
          var child = JSON.parse(localStorage.getItem('selectedChild'));
          var gestationString = child.gestation;
          console.log("calculate gestation and age");
          console.log(child.dob);
          console.log(child.timeOfBirth);

          this.diff = this.dischargedSummaryData.outcome.entrytime - Date.parse(child.dob);
          this.diff = (this.diff / (24 * 60 * 60 * 1000)) + 1;
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

          var diffHrs = (this.dischargedSummaryData.outcome.entrytime - dateTimeBirth.getTime()) / (60 * 60 * 1000);

          this.ageDaysdiff = (diffHrs / 24);
          this.ageDaysdiff = parseInt(this.ageDaysdiff);
          if((this.ageDaysdiff + 1) != this.ageDays){
            this.ageDays = this.ageDays - 1;
          }

          if(this.ageDays < 6) {
            this.ageHours = Math.round(diffHrs);
          }

          if(gestationString) {
            console.log("found ",gestationString);
            var weeks = gestationString.split("Weeks");
            var days = gestationString.match("Weeks(.*)Days");
            this.gestationWeeks = parseInt(weeks[0]);
            this.gestationDays = parseInt(days[1]);
          }
          else {
            console.log("no found");
            this.gestationWeeks = 0;
            this.gestationDays = 0;
          }

          if (this.ageDays > 7) {
            this.lifeWeeks = (this.ageDays / 7);
            this.lifeWeeks = parseInt(this.lifeWeeks);
            console.log(this.lifeWeeks + "this.lifeWeeks");
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
              console.log(this.totalDaysOfGestation + "gestationending");
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

          console.log(this.gestationWeeks);
          console.log(this.gestationDays);
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

		backToPreviousPage(tabValue){
			this.whichDischargeSummaryPage = tabValue;
      console.log(this.whichDischargeSummaryPage);
		}
		ngOnInit() {
	  		this.getDischargedSummary();
	  }


    showUpdatePopUp = function(assessment) {
      $("#updateDischargeSummaryPopup").addClass("showing");
      $("#updateDischargeSummaryOverlay").css("display","block");
      this.assessment = assessment;
    };

    closeUpdatePopUp = function(updateResult){
      $("#updateDischargeSummaryPopup").removeClass("showing");
      $("#updateDischargeSummaryOverlay").css("display", "none");
      this.updateResult = updateResult;

      if(this.assessment == 'Rds'){
        this.updateRdsNotes();
      }

      if(this.assessment == 'Apnea'){
        this.updateApneaNotes();
      }

      if(this.assessment == 'Pphn'){
        this.updatePphnNotes();
      }

      if(this.assessment == 'Pneumothorax'){
        this.updatePneumothoraxNotes();
      }

      if(this.assessment == 'Jaundice'){
        this.updateJaundice();
      }

      if(this.assessment == 'Asphyxia'){
        this.updateAsphyxiaNotes();
      }

      if(this.assessment == 'Seizure'){
        this.updateSeizureNotes();
      }

      if(this.assessment == 'Sepsis'){
        this.updateSepsisNotes();
      }

      if(this.assessment == 'Miscellaneous'){
        this.updateMiscellaneous();
      }

      if(this.assessment == 'Blood Product'){
        this.updateBloodProduct();
      }

      if(this.assessment == 'Procedure'){
        this.updateProcedure();
      }

      if(this.assessment == 'Growth'){
        this.updateGrowth();
      }

      if(this.assessment == 'Nutrition'){
        this.updateNutrition();
      }

      if(this.assessment == 'Vaccination'){
        this.updateVaccination();
      }

      if(this.assessment == 'Screening'){
        this.updateScreening();
      }
    };
}
