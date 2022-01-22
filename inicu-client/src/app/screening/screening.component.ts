import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import { ExportCsv } from '../export-csv';
import { ScreeningObj } from './screeningObj';
import { HearingRisk } from './hearingRisk';
import { RopRisk } from './ropRisk';
import { Neurological } from './neurological';

@Component({
  selector: 'screening',
  templateUrl: './screening.component.html',
  styleUrls: ['./screening.component.css']
})

export class ScreeningComponent implements OnInit {
  isLoaderVisible : boolean;
  loaderContent : string;
  http: Http;
  router : Router;
  apiData : APIurl;
  oneDay : number;
  workingWeight : any;
  loggedUser : string;
  selectedChild : any; 
  timezoneOffset : number;
  currentTimeLong : number;
  screeningObj : ScreeningObj;

  neurological : Neurological;
  neurologicalData : any;
  printNeurologicalFromDate : Date;
  printNeurologicalToDate : Date;

  ropData : any;
  ropRisk : RopRisk;
  printRopFromDate : Date;
  printRopToDate : Date;
  printRopList : Array<any>;
  enableRopPrint : boolean;

  hearingData : any;
  hearingRisk : HearingRisk;
  printHearingFromDate : Date;
  printHearingToDate : Date;

  usgData : any;
  printUSGFromDate : Date;
  printUSGToDate : Date;

  metabolicData : any;
  printMetabolicFromDate : Date;
  printMetabolicToDate : Date;
  investOrderSelected : Array<any>;
  investOrderNotOrdered : Array<any>;
  
  minDate : string;
  maxDate : Date;
  constructor(http: Http, router : Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.timezoneOffset = new Date().getTimezoneOffset();
    this.selectedChild = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.screeningObj = new ScreeningObj();
    this.oneDay = 24 * 60 * 60 * 1000;
    this.currentTimeLong = new Date().getTime();
    this.printNeurologicalFromDate = new Date();
    this.printNeurologicalToDate = new Date();
    this.printRopFromDate = new Date();
    this.printRopToDate = new Date();
    this.enableRopPrint = false;
    this.printHearingFromDate = new Date();
    this.printHearingToDate = new Date();
    this.printUSGFromDate = new Date();
    this.printUSGToDate = new Date();
    this.printMetabolicFromDate = new Date();
    this.printMetabolicToDate = new Date();
    this.investOrderSelected = [];
    this.investOrderNotOrdered = [];
	}

	screeningTabVisible(whichTabValue) {
		this.screeningObj.whichTab = whichTabValue;
	}

	clinicalSectionHearingVisible(){
		this.screeningObj.isClinicalSectionHearingVisible = !this.screeningObj.isClinicalSectionHearingVisible;
	}

	actionSectionHearingVisible(){
		this.screeningObj.isActionHearingVisible = !this.screeningObj.isActionHearingVisible;
	}

	planSectionHearingVisible(){
		this.screeningObj.isPlanHearingVisible = !this.screeningObj.isPlanHearingVisible;
	}

	progressNotesSectionHearingVisible(){
		this.screeningObj.isProgressNotesHearingVisible = !this.screeningObj.isProgressNotesHearingVisible;
	}

	clinicalSectionROPVisible(){
		this.screeningObj.isClinicalSectionROPVisible = !this.screeningObj.isClinicalSectionROPVisible;
	}

	actionSectionROPVisible(){
		this.screeningObj.isActionROPVisible = !this.screeningObj.isActionROPVisible;
	}

	planSectionROPVisible(){
		this.screeningObj.isPlanROPVisible = !this.screeningObj.isPlanROPVisible;
	}

	progressNotesSectionROPVisible(){
		this.screeningObj.isProgressNotesROPVisible = !this.screeningObj.isProgressNotesROPVisible;
	}

	screeningTreamentTabVisible(screeningTreamentVariable){
		this.screeningObj.whichTreatmentTab = screeningTreamentVariable;
	}
  clinicalSectionMetabolicVisible(){
    this.screeningObj.isClinicalSectionMetabolicVisible = !this.screeningObj.isClinicalSectionMetabolicVisible;
  }

  actionSectionMetabolicVisible(){
    this.screeningObj.isActionMetabolicVisible = !this.screeningObj.isActionMetabolicVisible;
  }

  planSectionMetabolicVisible(){
    this.screeningObj.isPlanMetabolicVisible = !this.screeningObj.isPlanMetabolicVisible;
  }
  screeningUSGVisible(){
    this.screeningObj.isScreeningUSGVisible = !this.screeningObj.isScreeningUSGVisible;
  }
  findingUSGVisible(){
    this.screeningObj.isFindingUSGVisible = !this.screeningObj.isFindingUSGVisible;
  }
  planUSGVisible(){
    this.screeningObj.isPlanUSGVisible = !this.screeningObj.isPlanUSGVisible;
  }
  reportUSGVisible(){
    this.screeningObj.isReportUSGVisible = !this.screeningObj.isReportUSGVisible;
  }
// ---------------------------------------------- Neurological Code Start Here ----------------------------------------------

  getNeurological() {
    try{
      this.http.request(this.apiData.getNeurological + this.selectedChild.uhid + '/').subscribe((res: Response) => {
        this.neurologicalData = res.json();
        this.neurological = new Neurological();
        this.neurologicalData.currentObj.screening_time = new Date();
        this.calNeurologicalCgaNpa();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveNeurological() {
    this.neurologicalData.currentObj.loggeduser = this.loggedUser;
    this.neurologicalData.currentObj.uhid = this.selectedChild.uhid;
    this.neurologicalData.currentObj.episodeid = this.selectedChild.episodeid;
    this.isLoaderVisible = true;
    try{
      this.http.post(this.apiData.saveNeurological + this.selectedChild.uhid + '/' + this.loggedUser + '/', this.neurologicalData.currentObj).subscribe(res => {
        if(res.json().type=="success"){
          this.neurologicalData = res.json().returnedObject;
          this.isLoaderVisible = false;
          this.neurological = new Neurological();
          this.neurologicalData.currentObj.screening_time = new Date();
          this.calNeurologicalCgaNpa();
        }
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  printPdfNeurological(execute){
    console.log('printPdfNeurological');
    var fromTime = this.printNeurologicalFromDate.getTime() + this.timezoneOffset;
    var toTime = this.printNeurologicalToDate.getTime() + this.timezoneOffset;

    try{
      this.http.request(this.apiData.getScreeningNeurologicalPrint + this.selectedChild.uhid + "/" + fromTime + "/" + toTime + "/",)
      .subscribe(res => {
        this.handleNeurologicalPrintData(res.json(), execute);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleNeurologicalPrintData(response : any, execute : any){
    if(response != null){
      console.log(response);
      if(execute == 'csv') {
        if(response.neurologicalList != null && response.neurologicalList.length > 0) {
          this.exportCsv(response.neurologicalList, "INICU-Screen-Neurological.csv");
        }
      } else if (execute == 'table') {
        this.neurologicalData.pastNeurologicalList = response.neurologicalList;
      } else if (execute == 'print') {
          response.whichTab = 'ROP';
          response.from_time = this.printRopFromDate.getTime();
          response.to_time = this.printRopToDate.getTime();;
          localStorage.setItem('printScreenNeurologicalDataObj', JSON.stringify(response));
          // this.router.navigateByUrl('/nursing/nursing-print');
      }
    }
  }

  calNeurologicalCgaNpa() {
    console.log('inside calNeurologicalCgaNpa');
    this.calCgaNpa(this.neurologicalData.currentObj);
    this.progressNotesNeurological();
  }

  postureNeurological(sign : string) {
    if(sign == 'abnormal / deviant') {
      var value = "";
      this.neurologicalData.currentObj.posture_type = sign;

      if(this.neurological.postureOpisthotonous) {
        value += ", Opisthotonous";
      }

      if(this.neurological.postureArmFlexed) {
        value += ", Arm very flexed";
      }

      if(this.neurological.postureLegExtended) {
        value += ", Legs very extended";
      }

      this.neurologicalData.currentObj.posture_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.posture_value = null;
      this.neurological.postureOpisthotonous = false;
      this.neurological.postureArmFlexed = false;
      this.neurological.postureLegExtended = false;
    }

    this.progressNotesNeurological();
  }

  headExtensorNeurological(sign : string) {
    if(sign == 'Optimal') {
      var value = "";
      this.neurologicalData.currentObj.head_extensor_type = sign;

      if(this.neurological.headControlRemainVertical) {
        value += ", Remain vertical";
      }

      if(this.neurological.headControlWobbles) {
        value += ", Wobbles";
      }

      this.neurologicalData.currentObj.head_extensor_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.head_extensor_value = null;
      this.neurological.headControlRemainVertical = false;
      this.neurological.headControlWobbles = false;
    }

    this.progressNotesNeurological();
  }

  headFlexorNeurological(sign : string) {
    if(sign == 'Optimal') {
      var value = "";
      this.neurologicalData.currentObj.head_flexor_type = sign;

      if(this.neurological.headFlexorRemainVertical) {
        value += ", Remain vertical";
      }

      if(this.neurological.headFlexorWobbles) {
        value += ", Wobbles";
      }

      this.neurologicalData.currentObj.head_flexor_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.head_flexor_value = null;
      this.neurological.headFlexorRemainVertical = false;
      this.neurological.headFlexorWobbles = false;
    }

    this.progressNotesNeurological();
  }

  ventralNeurological(sign : string) {
    var value = "";
    this.neurologicalData.currentObj.ventral_type = sign;

    if(sign == 'Abnormal') {
      this.neurological.backStraight = false;

      if(this.neurological.backCurved) {
        value += ", Back curved";
      }

      if(this.neurological.handStraight) {
        value += ", Head and limbs hang stright";
      }
    } else if(sign == 'Optimal') {
      this.neurological.backCurved = false;
      this.neurological.handStraight = false;

      if(this.neurological.backStraight) {
        value += ", Back straight";
      }
    }

    this.neurologicalData.currentObj.ventral_value = value.substring(2, value.length);
    this.progressNotesNeurological();
  }

  bodyNeurological(sign : string) {
    if(sign == 'abnormal / deviant') {
      var value = "";
      this.neurologicalData.currentObj.body_type = sign;

      if(this.neurological.cramped) {
        value += ", Cramped";
      }

      if(this.neurological.athetoid) {
        value += ", Athetoid";
      }

      if(this.neurological.otherAbnormalMovement) {
        value += ", Other abnormal movement";
      }

      this.neurologicalData.currentObj.body_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.body_value = null;
      this.neurological.cramped = false;
      this.neurological.athetoid = false;
      this.neurological.otherAbnormalMovement = false;
    }

    this.progressNotesNeurological();
  }

  tremorsNeurological(sign : string) {
    if(sign == 'abnormal / deviant') {
      var value = "";
      this.neurologicalData.currentObj.tremor_startles_type = sign;

      if(this.neurological.tremulousAlways) {
        value += ", Tremulous always";
      }

      if(this.neurological.manyStartles) {
        value += ", Many startles";
      }

      this.neurologicalData.currentObj.tremor_startles_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.tremor_startles_value = null;
      this.neurological.tremulousAlways = false;
      this.neurological.manyStartles = false;
    }

    this.progressNotesNeurological();
  }

  reflexNeurological(sign : string) {
    var value = "";
    this.neurologicalData.currentObj.moro_reflex_type = sign;

    if(sign == 'Abnormal') {
      this.neurological.minimalAdduction = false;
      this.neurological.difficultElicit = false;

      if(this.neurological.noResponse) {
        value += ", No Response";
      }

      if(this.neurological.fullAbduction) {
        value += ", Full abduction of the  arms";
      }

      if(this.neurological.extensionElbows) {
        value += ", Extension at the elbows";
      }

      if(this.neurological.noAdduction) {
        value += ", No Adduction";
      }

      this.neurologicalData.currentObj.moro_reflex_value = value.substring(2, value.length);
    } else if(sign == 'abnormal / deviant') {
      this.neurological.noResponse = false;
      this.neurological.fullAbduction = false;
      this.neurological.extensionElbows = false;
      this.neurological.noAdduction = false;

      if(this.neurological.minimalAdduction) {
        value += ", Minimal Adduction or abduction";
      }

      if(this.neurological.difficultElicit) {
        value += ", Difficult to elicit";
      }

      this.neurologicalData.currentObj.moro_reflex_value = value.substring(2, value.length);
    } else {
      this.neurological.noResponse = false;
      this.neurological.fullAbduction = false;
      this.neurological.extensionElbows = false;
      this.neurological.noAdduction = false;
      this.neurological.minimalAdduction = false;
      this.neurological.difficultElicit = false;
      this.neurologicalData.currentObj.moro_reflex_value = null;
    }

    this.progressNotesNeurological();
  }

  auditoryNeurological(sign : string) {
    if(sign == 'Optimal') {
      var value = "";
      this.neurologicalData.currentObj.auditory_type = sign;

      if(this.neurological.brightens) {
        value += ", Brightens";
      }

      if(this.neurological.stimulus) {
        value += ", Turns to stimulus either side";
      }

      this.neurologicalData.currentObj.auditory_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.auditory_value = null;
      this.neurological.brightens = false;
      this.neurological.stimulus = false;
    }

    this.progressNotesNeurological();
  }

  alertnessNeurological(sign : string) {
    if(sign == 'Optimal') {
      var value = "";
      this.neurologicalData.currentObj.alertness_type = sign;

      if(this.neurological.alertnessSustained) {
        value += ", Alertness Sustained";
      }

      if(this.neurological.alertState) {
        value += ", May use stimulus to come to alert state";
      }

      this.neurologicalData.currentObj.alertness_value = value.substring(2, value.length);
    } else {
      this.neurologicalData.currentObj.alertness_value = null;
      this.neurological.alertnessSustained = false;
      this.neurological.alertState = false;
    }

    this.progressNotesNeurological();
  }

  progressNotesNeurological() {
    console.log('inside progressNotesNeurological');
    var greyArea = 0;

    if(this.neurologicalData.currentObj.posture_type == 'Abnormal' && this.neurologicalData.currentObj.posture_type == 'abnormal / deviant') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.arm_traction_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.leg_traction_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.head_extensor_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.head_flexor_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.head_lag_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.ventral_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.body_type == 'Abnormal' && this.neurologicalData.currentObj.body_type == 'abnormal / deviant') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.tremor_startles_type == 'Abnormal' && this.neurologicalData.currentObj.tremor_startles_type == 'abnormal / deviant') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.moro_reflex_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.auditory_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.visual_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.alertness_type == 'Abnormal') {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.suck == false) {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.facial) {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.eye) {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.sunset) {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.hand) {
      greyArea++;
    }

    if(this.neurologicalData.currentObj.clonus) {
      greyArea++;
    }

    if(greyArea < 2) {
      this.neurologicalData.currentObj.reports = "Grey area is equal to " + greyArea + ". Neurological Screening is normal";
    } else {
      this.neurologicalData.currentObj.reports = "Grey area is equal to " + greyArea + " and require detailed neurological assessment";
    }
  }

// ---------------------------------------------- Neurological Code End Here ----------------------------------------------

// ---------------------------------------------- ROP Code Start Here ----------------------------------------------

  getRop() {
    try{
      this.http.request(this.apiData.getRop + this.selectedChild.uhid + '/').subscribe((res: Response) => {
        this.ropData = res.json();
        this.ropData.currentObj.screening_time = new Date();
        this.ropRisk = new RopRisk();
        this.calRopCgaNpa();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveRop() {
    this.ropData.currentObj.loggeduser = this.loggedUser;
    this.ropData.currentObj.uhid = this.selectedChild.uhid;
    this.ropData.currentObj.episodeid = this.selectedChild.episodeid;
    this.isLoaderVisible = true;
    try{
      this.http.post(this.apiData.saveRop + this.selectedChild.uhid + '/' + this.loggedUser + '/', this.ropData.currentObj).subscribe(res => {
        if(res.json().type=="success"){
          this.ropData = res.json().returnedObject;
          this.isLoaderVisible = false;
          this.ropData.currentObj.screening_time = new Date();
          this.ropRisk = new RopRisk();
          this.calRopCgaNpa();
        }
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }
  //print rop data
  ropPrint(){
    if((this.ropData.currentObj != null || this.ropData.currentObj != undefined || this.ropData.currentObj != "")
      && (this.enableRopPrint)){
      localStorage.setItem("printRopList",JSON.stringify(this.printRopList));
      localStorage.setItem("dateAndTimeOfScreening", JSON.stringify(new Date(this.ropData.currentObj.screening_time)));
      this.router.navigateByUrl("screen/screening-print");
    }
  }
  //populate past rop data onclick functionality
  populatePastRop(index){
    if(this.ropData.pastRopList != null || this.ropData.pastRopList != undefined || this.ropData.pastRopList != ""){
      this.ropData.currentObj = this.ropData.pastRopList[index];
      this.ropData.currentObj.screening_time = new Date(this.ropData.pastRopList[index].screening_time);
      console.log(this.ropData.currentObj.screening_time);
      this.ropData.currentObj.left_laser_date = null;
      this.ropData.currentObj.right_laser_date = null;
      this.ropData.currentObj.left_avastin_date = null;
      this.ropData.currentObj.right_avastin_date = null;
      this.ropData.currentObj.left_surgery_date = null;
      this.ropData.currentObj.right_surgery_date = null;
      if(this.ropData.pastRopList[index].reassess_date != null){
        this.ropData.currentObj.reassess_date = new Date(this.ropData.pastRopList[index].reassess_date);
      }
      this.printRopList = this.ropData.currentObj;
      this.enableRopPrint = true;
      console.log(this.printRopList);

    }
  }
  printPdfRop(execute){
    console.log('printPdfRop');
    var fromTime = this.printRopFromDate.getTime() + this.timezoneOffset;
    var toTime = this.printRopToDate.getTime() + this.timezoneOffset;

    try{
      this.http.request(this.apiData.getScreeningRopPrint + this.selectedChild.uhid + "/" + fromTime + "/" + toTime + "/",)
      .subscribe(res => {
        this.handleRopPrintData(res.json(), execute);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleRopPrintData(response : any, execute : any){
    if(response != null){
      console.log(response);
      if(execute == 'csv') {
        if(response.ropList != null && response.ropList.length > 0) {
          this.exportCsv(response.ropList, "INICU-Screen-ROP.csv");
        }
      } else if (execute == 'table') {
        this.ropData.pastRopList = response.ropList;
      } else if (execute == 'print') {
          response.whichTab = 'ROP';
          response.from_time = this.printRopFromDate.getTime();
          response.to_time = this.printRopToDate.getTime();;
          localStorage.setItem('printScreenRopDataObj', JSON.stringify(response));
          // this.router.navigateByUrl('/nursing/nursing-print');
      }
    }
  }

  createRopRiskText() {
    this.ropData.currentObj.risk_factor = "";
    var tempRopRiskStr = "";

    if(this.ropRisk!=null){
      if(this.ropRisk.gestation==true){
        tempRopRiskStr = "Gestation < 32 week";
      }

      if(this.ropRisk.oxygen==true){
        if(tempRopRiskStr=='')
        tempRopRiskStr = "Oxygen supplementation";
        else
        tempRopRiskStr += ", " +  "Oxygen supplementation";
      }

      if(this.ropRisk.mv==true){
        if(tempRopRiskStr=='')
        tempRopRiskStr = "Mechanical Ventilation";
        else
        tempRopRiskStr += ", " +  "Mechanical Ventilation";
      }

      if(this.ropRisk.sepsis==true){
        if(tempRopRiskStr=='')
        tempRopRiskStr = "Sepsis";
        else
        tempRopRiskStr += ", " +  "Sepsis";
      }

      if(this.ropRisk.blood==true){
        if(tempRopRiskStr=='')
        tempRopRiskStr = "Blood transfusions";
        else
        tempRopRiskStr += ", " +  "Blood transfusions";
      }

      if(this.ropRisk.poor_weight==true){
        if(tempRopRiskStr=='')
        tempRopRiskStr = "Poor Weight Gain";
        else
        tempRopRiskStr += ", " +  "Poor Weight Gain";
      }

      if(this.ropRisk.othersType == true && this.ropRisk.othersValue != null && this.ropRisk.othersValue != ''){
          if(tempRopRiskStr=='')
          tempRopRiskStr = this.ropRisk.othersValue;
          else
          tempRopRiskStr += ", " + this.ropRisk.othersValue;
      } else {
          this.ropRisk.othersValue = "";
      }
      this.ropData.currentObj.risk_factor = tempRopRiskStr;
      console.log(this.ropData.currentObj.risk_factor);
    }
    this.progressNotesRop();
  }

  calRopCgaNpa() {
    console.log('inside calRopCgaNpa');
    this.calCgaNpa(this.ropData.currentObj);
    this.calNextReassessDateRop();
  }

  calNextReassessDateRop() {
    console.log('inside calNextReassessDateRop');
    if(this.ropData.currentObj.reassess_time != null) {
      var reassessTimeLong = this.ropData.currentObj.reassess_time * this.oneDay;
      if(this.ropData.currentObj.reassess_timetype == 'Weeks') {
        reassessTimeLong *= 7;
      }

      this.ropData.currentObj.reassess_date = new Date(this.ropData.currentObj.screening_time.getTime() + reassessTimeLong);
    }
    this.progressNotesRop();
  }

  processRopTreatment() {
    console.log('inside processRopTreatment');
    var treatmentList = [];

    if(this.ropData.currentObj.no_treatment_left || this.ropData.currentObj.no_treatment_right) {
      treatmentList.push('TRE089');

      if(this.ropData.currentObj.no_treatment_left) {
        this.ropData.currentObj.left_laser = false;
        this.ropData.currentObj.left_laser_date = null;
        this.ropData.currentObj.left_laser_comment = null;

        this.ropData.currentObj.left_avastin = false;
        this.ropData.currentObj.left_avastin_date = null;
        this.ropData.currentObj.left_avastin_dose = null;

        this.ropData.currentObj.left_surgery = false;
        this.ropData.currentObj.left_surgery_date = null;
        this.ropData.currentObj.left_surgery_comment = null;
      }

      if(this.ropData.currentObj.no_treatment_right) {
        this.ropData.currentObj.right_laser = false;
        this.ropData.currentObj.right_laser_date = null;
        this.ropData.currentObj.right_laser_comment = null;

        this.ropData.currentObj.right_avastin = false;
        this.ropData.currentObj.right_avastin_date = null;
        this.ropData.currentObj.right_avastin_dose = null;

        this.ropData.currentObj.right_surgery = false;
        this.ropData.currentObj.right_surgery_date = null;
        this.ropData.currentObj.right_surgery_comment = null;
      }
    }

    if(this.ropData.currentObj.left_laser || this.ropData.currentObj.right_laser) {
      treatmentList.push('TRE090');
    }

    if(this.ropData.currentObj.left_avastin || this.ropData.currentObj.right_avastin) {
      treatmentList.push('TRE091');
    }

    if(this.ropData.currentObj.left_surgery || this.ropData.currentObj.right_surgery) {
      treatmentList.push('TRE092');
    }

    if(this.ropData.currentObj.other_treatment != null && this.ropData.currentObj.other_treatment != '') {
      treatmentList.push('Other');
    }

    this.ropData.currentObj.treatmentList = treatmentList;
    this.progressNotesRop();
  }

  progressNotesRop() {
    console.log('inside progressNotesRop');
    var progressNote = "Eye examination was done under local anesthesia at CGA " + this.ropData.currentObj.cga_weeks + " weeks " + this.ropData.currentObj.cga_days + " days and PNA " + this.ropData.currentObj.pna_weeks + " weeks " + this.ropData.currentObj.pna_days + " days. ";

    if(this.ropData.currentObj.risk_factor != null && this.ropData.currentObj.risk_factor !='') {
      progressNote += "Associated risk factors are " + this.ropData.currentObj.risk_factor + ". ";
    }

    if(this.ropData.currentObj.is_rop) {
      var ropText = "";
      if(this.ropData.currentObj.is_rop_left) {
        ropText += "ROP at left eye";
        if(this.ropData.currentObj.rop_left_zone != null) {
          ropText += ", zone " + this.ropData.currentObj.rop_left_zone;
        }

        if(this.ropData.currentObj.rop_left_stage != null) {
          ropText += ", stage " + this.ropData.currentObj.rop_left_stage;
        }
      }

      if(this.ropData.currentObj.is_rop_right) {
        if(ropText == '') {
          ropText += "ROP at right eye";
        } else {
          ropText += " and right eye";
        }

        if(this.ropData.currentObj.rop_right_zone != null) {
          ropText += ", zone " + this.ropData.currentObj.rop_right_zone;
        }

        if(this.ropData.currentObj.rop_right_stage != null) {
          ropText += ", stage " + this.ropData.currentObj.rop_right_stage;
        }
      }
      progressNote += ropText + ". ";
    }

    if(this.ropData.currentObj.no_treatment_left && this.ropData.currentObj.no_treatment_right) {
      progressNote += "No treatment required for both eyes. ";
    } else if(this.ropData.currentObj.no_treatment_left){
      progressNote += "No treatment required for the left eye. ";
    } else if(this.ropData.currentObj.no_treatment_right){
      progressNote += "No treatment required for the right eye. ";
    }

    if(this.ropData.currentObj.treatmentList.includes('TRE090')) {
      var laserStr = "";
      if(this.ropData.currentObj.left_laser){
        laserStr += "Left eye Laser is done";
        if(this.ropData.currentObj.left_laser_date != null) {
          laserStr += " - " + this.formatDate(this.ropData.currentObj.left_laser_date);
        }

        if(this.ropData.currentObj.left_laser_comment != null && this.ropData.currentObj.left_laser_comment != '') {
          laserStr += " (" + this.ropData.currentObj.left_laser_comment + ")";
        }
      }

      if(this.ropData.currentObj.right_laser){
        if(laserStr != '') {
          laserStr += " and ";
        }

        laserStr += "Right eye Laser is done";
        if(this.ropData.currentObj.right_laser_date != null) {
          laserStr += " - " + this.formatDate(this.ropData.currentObj.right_laser_date);
        }

        if(this.ropData.currentObj.right_laser_comment != null && this.ropData.currentObj.right_laser_comment != '') {
          laserStr += " (" + this.ropData.currentObj.right_laser_comment + ")";
        }
      }
      if(laserStr != '') {
        progressNote += laserStr + ". ";
      }
    }

    if(this.ropData.currentObj.treatmentList.includes('TRE091')) {
      var avastinStr = "";
      if(this.ropData.currentObj.left_avastin && this.ropData.currentObj.left_avastin_dose != null){
        avastinStr += "Left eye " + this.ropData.currentObj.left_avastin_dose + " mg of Avastin";

        if(this.ropData.currentObj.left_avastin_date != null) {
          avastinStr += " (" + this.formatDate(this.ropData.currentObj.left_avastin_date) + ")";
        }

        avastinStr += " given";
      }

      if(this.ropData.currentObj.right_avastin && this.ropData.currentObj.right_avastin_dose){
        if(avastinStr != '') {
          avastinStr += " and ";
        }

        avastinStr += "Right eye " + this.ropData.currentObj.right_avastin_dose + " mg of Avastin";
        if(this.ropData.currentObj.right_avastin_date != null) {
          avastinStr += " (" + this.formatDate(this.ropData.currentObj.right_avastin_date) + ")";
        }

        avastinStr += " given";
      }
      if(avastinStr != '') {
        progressNote += avastinStr + ". ";
      }
    }

    if(this.ropData.currentObj.treatmentList.includes('TRE092')) {
      var surgeryStr = "";
      if(this.ropData.currentObj.left_surgery){
        surgeryStr += "Left eye Surgery is done";
        if(this.ropData.currentObj.left_surgery_date != null) {
          surgeryStr += " - " + this.formatDate(this.ropData.currentObj.left_surgery_date);
        }

        if(this.ropData.currentObj.left_surgery_comment != null && this.ropData.currentObj.left_surgery_comment != '') {
          surgeryStr += " (" + this.ropData.currentObj.left_surgery_comment + ")";
        }
      }

      if(this.ropData.currentObj.right_surgery){
        if(surgeryStr != '') {
          surgeryStr += " and ";
        }

        surgeryStr += "Right eye Surgery is done";
        if(this.ropData.currentObj.right_surgery_date != null) {
          surgeryStr += " - " + this.formatDate(this.ropData.currentObj.right_surgery_date);
        }

        if(this.ropData.currentObj.right_surgery_comment != null && this.ropData.currentObj.right_surgery_comment != '') {
          surgeryStr += " (" + this.ropData.currentObj.right_surgery_comment + ")";
        }
      }
      if(surgeryStr != '') {
        progressNote += surgeryStr + ". ";
      }
    }

    if(this.ropData.currentObj.treatmentList.includes('Other')) {
      progressNote += this.ropData.currentObj.other_treatment + ". ";
    }

    if(this.ropData.currentObj.reassess_time != null && this.ropData.currentObj.reassess_time !='') {
      progressNote += "Plan is to reassess after " + this.ropData.currentObj.reassess_time;

      if(this.ropData.currentObj.reassess_time > 1) {
        progressNote += " " + this.ropData.currentObj.reassess_timetype;
      } else {
        progressNote += " " + this.ropData.currentObj.reassess_timetype.substring(0, this.ropData.currentObj.reassess_timetype.length - 1);
      }

      progressNote += " (" + this.formatDate(this.ropData.currentObj.reassess_date) + "). ";
    }

    if(this.ropData.currentObj.reassess_comments != null && this.ropData.currentObj.reassess_comments !='') {
      progressNote += this.ropData.currentObj.reassess_comments;
    }

    this.ropData.currentObj.progress_note = progressNote;
  }

// ----------------------------------------------- ROP Code End Here --------------------------------------------

// -------------------------------------------- Hearing Code Start Here -----------------------------------------
  getHearing() {
    try{
      this.http.request(this.apiData.getHearing + this.selectedChild.uhid + '/').subscribe((res: Response) => {
        this.hearingData = res.json();
        this.hearingRisk = new HearingRisk();
        this.hearingData.currentObj.screening_time = new Date();
        this.calHearingCgaNpa();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveHearing() {
    this.hearingData.currentObj.loggeduser = this.loggedUser;
    this.hearingData.currentObj.uhid = this.selectedChild.uhid;
    this.hearingData.currentObj.episodeid = this.selectedChild.episodeid;
    this.isLoaderVisible = true;
    try{
      this.http.post(this.apiData.saveHearing + this.selectedChild.uhid + '/' + this.loggedUser + '/', this.hearingData.currentObj).subscribe(res => {
        if(res.json().type=="success"){
          this.hearingData = res.json().returnedObject;
          this.isLoaderVisible = false;
          this.hearingRisk = new HearingRisk();
          this.hearingData.currentObj.screening_time = new Date();
          this.calHearingCgaNpa();
        }
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  printPdfHearing(execute){
    console.log('printPdfHearing');
    var fromTime = this.printHearingFromDate.getTime() + this.timezoneOffset;
    var toTime = this.printHearingToDate.getTime() + this.timezoneOffset;

    try{
      this.http.request(this.apiData.getScreeningHearingPrint + this.selectedChild.uhid + "/" + fromTime + "/" + toTime + "/",)
      .subscribe(res => {
        this.handleHearingPrintData(res.json(), execute);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleHearingPrintData(response : any, execute : any){
    if(response != null){
      console.log(response);
      if(execute == 'csv') {
        if(response.hearingList != null && response.hearingList.length > 0) {
          this.exportCsv(response.hearingList, "INICU-Screen-Hearing.csv");
        }
      } else if (execute == 'table') {
        this.hearingData.pastHearingList = response.hearingList;
      } else if (execute == 'print') {
          response.whichTab = 'Hearing';
          response.from_time = this.printHearingFromDate.getTime();
          response.to_time = this.printHearingToDate.getTime();;
          localStorage.setItem('printScreenHearingDataObj', JSON.stringify(response));
          // this.router.navigateByUrl('/nursing/nursing-print');
      }
    }
  }

  createHearingRiskText() {
    this.hearingData.currentObj.risk_factor = "";
    var temphearingRiskStr = "";

    if(this.hearingRisk!=null){

      if(this.hearingRisk.family_history==true){
        temphearingRiskStr = "Family History";
      }

      if(this.hearingRisk.nicu_stay==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "NICU Stay > 5days";
        else
        temphearingRiskStr += ", " +  "NICU Stay > 5days";
      }

      if(this.hearingRisk.ototoxic==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Ototoxic Medications";
        else
        temphearingRiskStr += ", " +  "Ototoxic Medications";
      }

      if(this.hearingRisk.hyperbilirubinemia==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Hyperbilirubinemia with Exchange Transfusion";
        else
        temphearingRiskStr += ", " +  "Hyperbilirubinemia with Exchange Transfusion";
      }

      if(this.hearingRisk.infection==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Intra Utero Infections";
        else
        temphearingRiskStr += ", " +  "Intra Utero Infections";
      }

      if(this.hearingRisk.craniofacial==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Craniofacial Anomalies";
        else
        temphearingRiskStr += ", " +  "Craniofacial Anomalies";
      }

      if(this.hearingRisk.forelock==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "White Forelock";
        else
        temphearingRiskStr += ", " +  "White Forelock";
      }

      if(this.hearingRisk.sepsis==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Culture-positive Sepsis";
        else
        temphearingRiskStr += ", " +  "Culture-positive Sepsis";
      }

      if(this.hearingRisk.meningitis==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Meningitis";
        else
        temphearingRiskStr += ", " +  "Meningitis";
      }

      if(this.hearingRisk.herpes==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Herpes";
        else
        temphearingRiskStr += ", " +  "Herpes";
      }

      if(this.hearingRisk.varicella==true){
        if(temphearingRiskStr=='')
        temphearingRiskStr = "Varicella";
        else
        temphearingRiskStr += ", " +  "Varicella";
      }

      if(this.hearingRisk.othersType == true && this.hearingRisk.othersValue != null && this.hearingRisk.othersValue != ''){
          if(temphearingRiskStr=='')
          temphearingRiskStr = this.hearingRisk.othersValue;
          else
          temphearingRiskStr += ", " + this.hearingRisk.othersValue;
      } else {
          this.hearingRisk.othersValue = "";
      }
      this.hearingData.currentObj.risk_factor = temphearingRiskStr;
      console.log(this.hearingData.currentObj.risk_factor);
    }
    this.progressNotesHearing();
  }

  calHearingCgaNpa() {
    console.log('inside calCgaNpaHearing');
    this.calCgaNpa(this.hearingData.currentObj);
    this.calNextReassessDateHearing();
  }

  calNextReassessDateHearing() {
    console.log('inside calNextReassessDateHearing');
    if(this.hearingData.currentObj.reassess_time != null) {
      var reassessTimeLong = this.hearingData.currentObj.reassess_time * this.oneDay;
      if(this.hearingData.currentObj.reassess_timetype == 'Weeks') {
        reassessTimeLong *= 7;
      }

      this.hearingData.currentObj.reassess_date = new Date(this.hearingData.currentObj.screening_time.getTime() + reassessTimeLong);
    }

    this.progressNotesHearing();
  }

  progressNotesHearing() {
    console.log('inside progressNotesHearing');
    var progressNote = "Hearing Screening was done on CGA " + this.hearingData.currentObj.cga_weeks + " weeks " + this.hearingData.currentObj.cga_days + " days and PNA " + this.hearingData.currentObj.pna_weeks + " weeks " + this.hearingData.currentObj.pna_days + " days";

    if(this.hearingData.currentObj.indication == 'Routine') {
      progressNote += " as routine check-up";
    } else {
      progressNote += " with high risk";
      if(this.hearingData.currentObj.risk_factor != null && this.hearingData.currentObj.risk_factor !='') {
        progressNote += " of " + this.hearingData.currentObj.risk_factor;
      }
    }

    if(this.hearingData.currentObj.screening_test == null || this.hearingData.currentObj.screening_test == '') {
      progressNote += ". ";
    } else {
      if(this.hearingData.currentObj.screening_test == 'Both') {
        progressNote += " by OAE and ABR";
      } else if(this.hearingData.currentObj.screening_test == 'OAE') {
        progressNote += " by OAE";
      } else if(this.hearingData.currentObj.screening_test == 'ABR') {
        progressNote += " by ABR";
      }

      if(this.hearingData.currentObj.screening_test == 'Both' || this.hearingData.currentObj.screening_test == 'OAE') {
        if(this.hearingData.currentObj.oae_left != null && this.hearingData.currentObj.oae_left != '') {
          progressNote += ", OAE left ear " + this.hearingData.currentObj.oae_left;
        }

        if(this.hearingData.currentObj.oae_right != null && this.hearingData.currentObj.oae_right != '') {
          progressNote += ", OAE right ear " + this.hearingData.currentObj.oae_right;
        }
      }

      if(this.hearingData.currentObj.screening_test == 'Both' || this.hearingData.currentObj.screening_test == 'ABR') {
        if(this.hearingData.currentObj.abr_left != null && this.hearingData.currentObj.abr_left != '') {
          progressNote += ", ABR left ear " + this.hearingData.currentObj.abr_left;
        }

        if(this.hearingData.currentObj.abr_right != null && this.hearingData.currentObj.abr_right != '') {
          progressNote += ", ABR right ear " + this.hearingData.currentObj.abr_right;
        }
      }
      progressNote += ". ";
    }

    if(this.hearingData.currentObj.treatment_given && this.hearingData.currentObj.treatment != null && this.hearingData.currentObj.treatment != '') {
      progressNote += "Treatment given is " + this.hearingData.currentObj.treatment + ". ";
    }

    if(this.hearingData.currentObj.reassess_time != null && this.hearingData.currentObj.reassess_time !='') {
      progressNote += "Plan is to reassess after " + this.hearingData.currentObj.reassess_time;

      if(this.hearingData.currentObj.reassess_time > 1) {
        progressNote += " " + this.hearingData.currentObj.reassess_timetype;
      } else {
        progressNote += " " + this.hearingData.currentObj.reassess_timetype.substring(0, this.hearingData.currentObj.reassess_timetype.length - 1);
      }

      progressNote += " (" + this.formatDate(this.hearingData.currentObj.reassess_date) + "). ";
    }

    if(this.hearingData.currentObj.reassess_comments != null && this.hearingData.currentObj.reassess_comments !='') {
      progressNote += this.hearingData.currentObj.reassess_comments;
    }

    this.hearingData.currentObj.progress_note = progressNote;
  }

// ---------------------------------------------- Hearing Code End Here ------------------------------------------

// ---------------------------------------------- USG Code Start Here ------------------------------------------
  getUSG() {
    try{
      this.http.request(this.apiData.getUSG + this.selectedChild.uhid + '/').subscribe((res: Response) => {
        this.usgData = res.json();
        this.usgData.currentObj.screening_time = new Date();
        this.calUSGCgaNpa();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveUSG() {
    this.usgData.currentObj.loggeduser = this.loggedUser;
    this.usgData.currentObj.uhid = this.selectedChild.uhid;
    this.usgData.currentObj.episodeid = this.selectedChild.episodeid;
    this.isLoaderVisible = true;
    try{
      this.http.post(this.apiData.saveUSG + this.selectedChild.uhid + '/' + this.loggedUser + '/', this.usgData.currentObj).subscribe(res => {
        if(res.json().type=="success"){
          this.usgData = res.json().returnedObject;
          this.isLoaderVisible = false;
          this.usgData.currentObj.screening_time = new Date();
          this.calUSGCgaNpa();
        }
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  printPdfUSG(execute){
    console.log('printPdfUSG');
    var fromTime = this.printUSGFromDate.getTime() + this.timezoneOffset;
    var toTime = this.printUSGToDate.getTime() + this.timezoneOffset;

    try{
      this.http.request(this.apiData.getScreeningUSGPrint + this.selectedChild.uhid + "/" + fromTime + "/" + toTime + "/",)
      .subscribe(res => {
        this.handleUSGPrintData(res.json(), execute);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleUSGPrintData(response : any, execute : any){
    if(response != null){
      console.log(response);
      if(execute == 'csv') {
        if(response.usgList != null && response.usgList.length > 0) {
          this.exportCsv(response.usgList, "INICU-Screen-USG.csv");
        }
      } else if (execute == 'table') {
        this.usgData.pastUSGList = response.usgList;
      } else if (execute == 'print') {
          response.whichTab = 'USG';
          response.from_time = this.printUSGFromDate.getTime();
          response.to_time = this.printUSGToDate.getTime();;
          localStorage.setItem('printScreenUSGDataObj', JSON.stringify(response));
          // this.router.navigateByUrl('/nursing/nursing-print');
      }
    }
  }

  calUSGCgaNpa() {
    console.log('inside calCgaNpaUSG');
    this.calCgaNpa(this.usgData.currentObj);
    this.calNextReassessDateUSG();
  }

  calNextReassessDateUSG() {
    console.log('inside calNextReassessDateUSG');
    if(this.usgData.currentObj.reassess_time != null) {
      var reassessTimeLong = this.usgData.currentObj.reassess_time * this.oneDay;
      if(this.usgData.currentObj.reassess_timetype == 'Weeks') {
        reassessTimeLong *= 7;
      }

      this.usgData.currentObj.reassess_date = new Date(this.usgData.currentObj.screening_time.getTime() + reassessTimeLong);
    }
    this.progressNotesUSG();
  }

  progressNotesUSG() {
    console.log('inside progressNotesUSG');

    var progressNote = "Cranial USG screening was done at CGA " + this.usgData.currentObj.cga_weeks + " weeks " + this.usgData.currentObj.cga_days + " days and PNA " + this.usgData.currentObj.pna_weeks + " weeks " + this.usgData.currentObj.pna_days + " days. ";

    if(this.usgData.currentObj.brain_parenchyma != null && this.usgData.currentObj.brain_parenchyma != '') {
      progressNote += "Brain parencnyma is " + this.usgData.currentObj.brain_parenchyma;

      if(this.usgData.currentObj.lateral_ventricle != null && this.usgData.currentObj.lateral_ventricle != '') {
        progressNote += " with " + this.usgData.currentObj.lateral_ventricle + " lateral ventricle";
      }

      if(this.usgData.currentObj.parenchyma_description != null && this.usgData.currentObj.parenchyma_description != '') {
        progressNote += " (" + this.usgData.currentObj.parenchyma_description + ")";
      }
      progressNote += ". ";
    }

    if(this.usgData.currentObj.right_ventricle != null && this.usgData.currentObj.right_ventricle != '') {
      progressNote += "Right ventricle measured as " + this.usgData.currentObj.right_ventricle + " mm. ";
    }

    if(this.usgData.currentObj.left_ventricle != null && this.usgData.currentObj.left_ventricle != '') {
      progressNote += "Left ventricle measured as " + this.usgData.currentObj.left_ventricle + " mm. ";
    }

    if(this.usgData.currentObj.third_ventricle != null && this.usgData.currentObj.third_ventricle != '') {
      progressNote += "Third ventricle measured as " + this.usgData.currentObj.third_ventricle + " mm. ";
    }

    if(this.usgData.currentObj.ivh_type != null && this.usgData.currentObj.ivh_type != '') {
      progressNote += this.usgData.currentObj.ivh_type + " was observed";

      if(this.usgData.currentObj.ivh_side != null && this.usgData.currentObj.ivh_side != '') {
        progressNote += " on " + this.usgData.currentObj.ivh_side + " side";
      }

      if(this.usgData.currentObj.ivh_description != null && this.usgData.currentObj.ivh_description != '') {
        progressNote += " (" + this.usgData.currentObj.ivh_description + ")";
      }

      progressNote += ". ";
    }

    var observeStr = "";
    if(this.usgData.currentObj.ventriculomegaly != null && this.usgData.currentObj.ventriculomegaly != '') {
      observeStr += this.usgData.currentObj.ventriculomegaly;
    }

    if(this.usgData.currentObj.pvl_type != null && this.usgData.currentObj.pvl_type != '') {
      if(observeStr == '') {
        observeStr += this.usgData.currentObj.pvl_type;
      } else {
        observeStr += " and " + this.usgData.currentObj.pvl_type;
      }

      if(this.usgData.currentObj.pvl_description != null && this.usgData.currentObj.pvl_description != '') {
        observeStr += " (" + this.usgData.currentObj.pvl_description + ")";
      }
    }

    if(observeStr != '') {
      progressNote += observeStr + " observed. ";
    }

    if(this.usgData.currentObj.finding_comments != null && this.usgData.currentObj.finding_comments != '') {
      progressNote += this.usgData.currentObj.finding_comments + ". ";
    }

    if(this.usgData.currentObj.reassess_time != null && this.usgData.currentObj.reassess_time !='') {
      progressNote += "Plan is to reassess after " + this.usgData.currentObj.reassess_time;

      if(this.usgData.currentObj.reassess_time > 1) {
        progressNote += " " + this.usgData.currentObj.reassess_timetype;
      } else {
        progressNote += " " + this.usgData.currentObj.reassess_timetype.substring(0, this.usgData.currentObj.reassess_timetype.length - 1);
      }

      progressNote += " (" + this.formatDate(this.usgData.currentObj.reassess_date) + "). ";
    }

    if(this.usgData.currentObj.reassess_comments != null && this.usgData.currentObj.reassess_comments !='') {
      progressNote += this.usgData.currentObj.reassess_comments;
    }

    this.usgData.currentObj.progress_note = progressNote;
  }

// ---------------------------------------------- USG Code End Here ------------------------------------------

// ---------------------------------------------- Metabolic Code Start Here ------------------------------------------
  getMetabolic() {
    try{
      this.http.request(this.apiData.getMetabolic + this.selectedChild.uhid + '/').subscribe((res: Response) => {
        this.metabolicData = res.json();
        this.metabolicData.currentObj.screening_time = new Date();
        this.calMetabolicCgaNpa();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  saveMetabolic() {
    this.metabolicData.currentObj.loggeduser = this.loggedUser;
    this.metabolicData.currentObj.uhid = this.selectedChild.uhid;
    this.metabolicData.currentObj.episodeid = this.selectedChild.episodeid;
    this.isLoaderVisible = true;
    try{
      this.http.post(this.apiData.saveMetabolic + this.selectedChild.uhid + '/' + this.loggedUser + '/', this.metabolicData).subscribe(res => {
        if(res.json().type=="success"){
          this.metabolicData = res.json().returnedObject;
          this.isLoaderVisible = false;
          this.metabolicData.currentObj.screening_time = new Date();
          this.calMetabolicCgaNpa();
        }
      }, err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  printPdfMetabolic(execute){
    console.log('printPdfMetabolic');
    var fromTime = this.printMetabolicFromDate.getTime() + this.timezoneOffset;
    var toTime = this.printMetabolicToDate.getTime() + this.timezoneOffset;

    try{
      this.http.request(this.apiData.getScreeningMetabolicPrint + this.selectedChild.uhid + "/" + fromTime + "/" + toTime + "/",)
      .subscribe(res => {
        this.handleMetabolicPrintData(res.json(), execute);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleMetabolicPrintData(response : any, execute : any){
    if(response != null){
      console.log(response);
      if(execute == 'csv') {
        if(response.metabolicList != null && response.metabolicList.length > 0) {
          this.exportCsv(response.metabolicList, "INICU-Screen-Metabolic.csv");
        }
      } else if (execute == 'table') {
        this.metabolicData.pastMetabolicList = response.metabolicList;
      } else if (execute == 'print') {
          response.whichTab = 'Metabolic';
          response.from_time = this.printMetabolicFromDate.getTime();
          response.to_time = this.printMetabolicToDate.getTime();;
          localStorage.setItem('printScreenMetabolicDataObj', JSON.stringify(response));
          // this.router.navigateByUrl('/nursing/nursing-print');
      }
    }
  }

  calMetabolicCgaNpa() {
    console.log('inside calMetabolicCgaNpa');
    this.calCgaNpa(this.metabolicData.currentObj);
    this.calNextReassessDateMetabolic();
  }

  calNextReassessDateMetabolic() {
    console.log('inside calNextReassessDateMetabolic');
    if(this.metabolicData.currentObj.reassess_time != null) {
      var reassessTimeLong = this.metabolicData.currentObj.reassess_time * this.oneDay;
      if(this.metabolicData.currentObj.reassess_timetype == 'Weeks') {
        reassessTimeLong *= 7;
      }

      this.metabolicData.currentObj.reassess_date = new Date(this.metabolicData.currentObj.screening_time.getTime() + reassessTimeLong);
    }
    this.progressNotesMetabolic();
  }

  progressNotesMetabolic() {
    console.log('inside progressNotesMetabolic');
  }

  showMetabolicInvestigations(){
    if(this.metabolicData.currentObj.metabolic_screening == null || this.metabolicData.currentObj.metabolic_screening == ''){
        for(var index = 0; index < this.metabolicData.testsList.length; index++){
          this.metabolicData.testsList[index].isSelected = true;
        }
    }

    $("#metabolicOrderOverlay").css("display", "block");
    $("#OrderInvestigationPopup").addClass("showing");
  }

  closeMetabolicInvestigation(){
    this.investOrderNotOrdered = [];
      for(var index = 0; index < this.metabolicData.testsList.length; index++){
        var test = this.metabolicData.testsList[index];

        if(this.investOrderSelected.indexOf(test.testname) == -1) {
          this.metabolicData.testsList[index].isSelected = false;
          this.investOrderNotOrdered.push(test.testname);
        } else {
          this.metabolicData.testsList[index].isSelected = true;
          this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(test.testname), this.investOrderNotOrdered.indexOf(test.testname)+1);
        }
      }
    console.log(this.investOrderNotOrdered);

    $("#metabolicOrderOverlay").css("display", "none");
    $("#OrderInvestigationPopup").toggleClass("showing");
  }

  orderSelected = function(){
    this.metabolicData.currentObj.metabolic_screening = "";
    this.investOrderSelected = [];

    for(var index = 0; index<this.metabolicData.testsList.length;index++){
      if(this.metabolicData.testsList[index].isSelected==true){
        if(this.metabolicData.currentObj.metabolic_screening == ''){
          this.metabolicData.currentObj.metabolic_screening = this.metabolicData.testsList[index].testname;
        } else {
          this.metabolicData.currentObj.metabolic_screening += ", " + this.metabolicData.testsList[index].testname;
        }
        this.investOrderSelected.push(this.metabolicData.testsList[index].testname);
      } else {
        var testName = this.metabolicData.testsList[index].testname;
        this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
        this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
      }
    }

    console.log(this.investOrderSelected);
    this.progressNotesMetabolic();

    $("#metabolicOrderOverlay").css("display", "none");
    $("#OrderInvestigationPopup").toggleClass("showing");
  }

// ---------------------------------------------- Metabolic Code End Here ------------------------------------------

  calCgaNpa(screenObj : any) {
    var dateDiff = Math.floor((screenObj.screening_time.getTime() - this.currentTimeLong) / this.oneDay);
    var daysOnScreening = (this.selectedChild.dayOfLife.substring(0, this.selectedChild.dayOfLife.indexOf(' ') + 1) * 1) + dateDiff + 1;

    if(dateDiff == 0) {
      screenObj.cga_weeks = this.selectedChild.gestationWeeks;
      screenObj.cga_days = this.selectedChild.gestationDays;
    } else {
      var gestDays = dateDiff + (this.selectedChild.gestationWeeks * 7) + this.selectedChild.gestationDays;
      screenObj.cga_weeks = Math.floor(gestDays / 7);
      screenObj.cga_days = gestDays - (screenObj.cga_weeks * 7);
    }

    if(daysOnScreening > 6) {
      screenObj.pna_weeks = Math.floor(daysOnScreening / 7);
      screenObj.pna_days = daysOnScreening - (screenObj.pna_weeks * 7);
    } else {
      screenObj.pna_weeks = 0;
      screenObj.pna_days = daysOnScreening;
    }
  }

  formatDate(source : Date) {
    var dateStr = "";

    if (source.getDate() < 10 ){
      dateStr += '0' + source.getDate() + "-";
    } else {
      dateStr += source.getDate() + "-";
    }

    if (source.getMonth() < 9){
      dateStr += '0' + (source.getMonth() + 1) + "-";
    } else {
      dateStr += (source.getMonth() + 1) + "-";
    }
    dateStr += source.getFullYear() + " ";

    if (source.getHours() < 10 ){
      dateStr += '0' + source.getHours() + ":";
    } else {
      dateStr += source.getHours() + ":";
    }

    if (source.getMinutes() < 10 ){
      dateStr += '0' + source.getMinutes();
    } else {
      dateStr += source.getMinutes();
    }

    return dateStr;
  }

  exportCsv(dataForCSV : any, fileName : any) {
    var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
    var finalCSVData = encodeURI(csvData);

    var downloadLink = document.createElement("a");
    downloadLink.setAttribute("href", finalCSVData);
    downloadLink.setAttribute("download", fileName);
    downloadLink.click();
  }

	ngOnInit() {
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
		this.screeningTabVisible('Neurological Screening');
		this.screeningObj.isClinicalSectionHearingVisible = true;
		this.screeningObj.isActionHearingVisible = false;
		this.screeningObj.isPlanHearingVisible = false;
		this.screeningObj.isProgressNotesHearingVisible = true;
		this.screeningObj.isClinicalSectionROPVisible = true;
		this.screeningObj.isActionROPVisible = false;
		this.screeningObj.isPlanROPVisible =  false;
		this.screeningObj.isProgressNotesROPVisible = true;
    this.screeningObj.isClinicalSectionMetabolicVisible = true;
    this.screeningObj.isActionMetabolicVisible = false;
    this.screeningObj.isPlanMetabolicVisible = false;
    this.screeningObj.isScreeningUSGVisible = true;
    this.screeningObj.isFindingUSGVisible = false;
    this.screeningObj.isPlanUSGVisible = false;
    this.screeningObj.isReportUSGVisible = true;
    this.getNeurological();
    this.getRop();
    this.getHearing();
    this.getUSG();
    this.getMetabolic();

	}

}
