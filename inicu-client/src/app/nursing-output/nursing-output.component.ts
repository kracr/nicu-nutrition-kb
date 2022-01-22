import { Component, OnInit } from '@angular/core';
import { NursingOutputObj } from './nursingOutputObj';
import { Http, Response } from '@angular/http';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
@Component({
  selector: 'nursing-output',
  templateUrl: './nursing-output.component.html',
  styleUrls: ['./nursing-output.component.css']
})
export class NursingOutputComponent implements OnInit {
	nursingOutputObj : NursingOutputObj;
	http: Http;
	apiData : APIurl;
  maxDob : any;
	router : Router;
	uhid : string;
	nursingOutputData : any;
  nursingOutputDataFullShift : any;
	isOutputSummary : boolean;
	isEnteralSummary : boolean;
	isParenteralSummary : boolean;
	isOtherAdditivesSummary : boolean;
  isDisplayRemark : boolean;
	totalEn : number;
  totalPn :  number;
  totalUrine : number;
  totalFeed : number;
  totalMedicineVol : number;
  urineMlKgHr : number;
  totalDrain : number;
  totalGastricAspirate : number;
  childObject : any;
  weight : number;
  hours : number;
  isPrintFromMedFlag : boolean;
  fluidReq : number;
  totalHeplockDelVolume : number;
  totalBloodDelVolume : number;
  sodiumVolume : number;
  calciumVolume : number;
  totalBolus : number;
  totalAdditives : number;
  protein : number;
  energy : number;
  hoursList : Array<any>;
  minutesList : Array<any>;
  strPupil : string;
  isShowDisable  = false;
  isComment : boolean;

	constructor(http: Http, router: Router) {
    this.hoursList = new Array<any>();
    this.minutesList = new Array<any>();
		this.nursingOutputObj = new NursingOutputObj();
    this.isDisplayRemark = false;
		this.http = http;
  	this.router = router;
  	this.apiData = new APIurl();
    this.hours = 1;
  	this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
  	this.childObject = JSON.parse(localStorage.getItem('selectedChild'));

    this.totalEn = 0;
    this.totalPn = 0;
    this.totalUrine = 0;
    this.totalFeed = 0;
    this.totalMedicineVol = 0;
    this.urineMlKgHr = 0;
    this.totalDrain = 0;
    this.totalGastricAspirate = 0;
    this.totalHeplockDelVolume = 0;
    this.totalBloodDelVolume = 0;
    this.sodiumVolume = 0;
    this.calciumVolume = 0;
    this.totalBolus = 0;
    this.totalAdditives = 0;
    this.protein = 0;
    this.energy = 0;
    this.strPupil = '';
    this.weight = 1;
    if(this.childObject.workingWeight != null) {
      this.weight = this.childObject.workingWeight / 1000;
    } else if(this.childObject.todayWeight != null) {
      this.weight = this.childObject.todayWeight / 1000;
    }
  	this.isOutputSummary = false;
		this.isEnteralSummary = false;
		this.isParenteralSummary = false;
		this.isOtherAdditivesSummary = false;
    this.isPrintFromMedFlag = false;
    this.isComment = false;
	}

  //The time selected should be in range of 24 hrs
  fromDateToDateValidation() {
    this.nursingOutputObj.toDate = null;
    this.nursingOutputObj.toDateMaxValue = new Date(this.nursingOutputObj.fromDate.getTime());
    this.nursingOutputObj.toDateMaxValue.setHours(8);
    this.nursingOutputObj.toDateMaxValue.setMinutes(0);

    if(this.nursingOutputObj.fromDate.getHours() >= 8) {
      this.nursingOutputObj.toDateMaxValue = new Date(this.nursingOutputObj.toDateMaxValue.getTime() + (24*60*60*1000));
    }
  }

	getNursingOutputData(fromDate : Date, toDate : Date, type : string,comingForm:string) {
  	if(fromDate != null && toDate != null) {
  		fromDate.setSeconds(0);
  		fromDate.setMilliseconds(0);
  		toDate.setSeconds(0);
  		toDate.setMilliseconds(0);

  		var fromParameter = fromDate.getTime();
      var toParameter = toDate.getTime();
      if(comingForm == 'onclickShow'){
        this.isShowDisable = true;
      }
    	try {
        this.http.request(this.apiData.getNursingOutputData + this.uhid + "/" + fromParameter + "/" + toParameter + "/").subscribe((res: Response) => {
          this.nursingOutputData = res.json();
          this.isShowDisable = false;
          console.log(this.nursingOutputData);
          this.strPupil = '';
          var leftPupilSizeStr = '';
          var rightpupilSizeStr = '';
          var leftPupilReactStr = '';
          var rightPupilReactStr = '';
          var isPupilEqualStr = '';
          if(this.nursingOutputData.vitalList.length>0 && this.nursingOutputData.vitalList != null &&
            this.nursingOutputData.vitalList != undefined && this.nursingOutputData.vitalList != ''){
              this.nursingOutputData.vitalList.forEach(temp=>{
                if(temp.isPupilEqual==false){
                  if(isPupilEqualStr == ""){
                    if(this.strPupil != ''){
                      isPupilEqualStr = ','
                    }
                    isPupilEqualStr = 'Left and Right Pupil Are Unequal ';
                    this.strPupil=this.strPupil + isPupilEqualStr;
                  }
                }
                if(temp.leftPupilSize=='Dilated'){
                  if(leftPupilSizeStr == ""){
                    if(this.strPupil != ''){
                      leftPupilSizeStr=','
                     }
                      leftPupilSizeStr =leftPupilSizeStr + 'Left Pupil Is Dilated ';
                      this.strPupil=this.strPupil + leftPupilSizeStr ;

                  }
                }
                if(temp.rightPupilSize=='Dilated'){
                  if(rightpupilSizeStr == ""){
                     if(this.strPupil != ''){
                      rightpupilSizeStr = ','
                     }
                    rightpupilSizeStr = rightpupilSizeStr + 'Right Pupil Is Dilated ';
                    this.strPupil=this.strPupil + rightpupilSizeStr;
                  }
                }
                if(temp.leftPupilReaction=='No Reaction'){
                  if(leftPupilReactStr == ""){
                    if(this.strPupil != ''){
                      leftPupilReactStr = ','
                     }
                    leftPupilReactStr = leftPupilReactStr + 'No Reaction In Left Pupil';
                    this.strPupil = this.strPupil + leftPupilReactStr;
                  }
                }
                if(temp.rightPupilReaction=='No Reaction'){
                  if(rightPupilReactStr == ""){
                    if(this.strPupil != ''){
                      rightPupilReactStr = ','
                    }
                    rightPupilReactStr = rightPupilReactStr + 'No Reaction In Right Pupil';
                    this.strPupil=this.strPupil + rightPupilReactStr ;
                  }
                }
              })
              if(this.strPupil != ''){
                this.strPupil = this.strPupil +'. ';
              }

          }
          this.populateOrderString();
          localStorage.setItem('pupilStr',this.strPupil);
          //not required as of now becuse of changes
          this.hours = Math.round((this.nursingOutputObj.toDate.getTime() - this.nursingOutputObj.fromDate.getTime()) / (60 * 60 * 1000));

          //fetching fluid required from doctor orders
          if(this.nursingOutputData.doctorNutritionOrder != null && this.nursingOutputData.doctorNutritionOrder.length != 0 && this.nursingOutputData.doctorNutritionOrder[0].totalIntake != null && this.hours != null){
           this.fluidReq = this.nursingOutputData.doctorNutritionOrder[0].totalIntake;
          }

          //calculations for calcium and sodium intake(24 hrs)
          if(this.nursingOutputData.doctorNutritionOrder != null && this.nursingOutputData.doctorNutritionOrder.length != 0){
            this.calculateCalcium(this.nursingOutputData.doctorNutritionOrder);
            this.calculateSodium(this.nursingOutputData.doctorNutritionOrder);
          }

          //Mapping of Doctor Blood Product with Nursing Blood Product with the help of foreign keys
          if(this.nursingOutputData.bloodProductList != null && this.nursingOutputData.bloodProductList.length > 0){
            this.generatebloodProductMap(this.nursingOutputData);
          }

          //Mapping of Doctor Medication with Nursing Medication with the help of foreign keys
          //Calculate the medvolume(24 hrs)
          if(this.nursingOutputData.pastMedicationList != null && this.nursingOutputData.pastMedicationList.length > 0){
            this.isPrintFromMedFlag = true;
            this.generateMedicationMap(this.nursingOutputData, type);
            this.calculateMedicineIntake(this.nursingOutputData.pastMedicationList);
          }else{
            this.totalMedicineVol = 0;
            this.isPrintFromMedFlag = false;
          }

          //Calculate Blood Product delivered Volume(24 hrs)
          if(this.nursingOutputData.allPastNursingBloodProductList != null && this.nursingOutputData.allPastNursingBloodProductList.length > 0){
            this.calculateBloodProductVolume(this.nursingOutputData.allPastNursingBloodProductList);
          }

          //Calculate Heplock delivered volume(24 hrs)
          if(this.nursingOutputData.pastHeplockList != null && this.nursingOutputData.pastHeplockList.length > 0){
            this.calculateHeplockVolume(this.nursingOutputData.pastHeplockList);
          }

          //Calculate Pn, En from intakeOutputList(24 hrs)
          if(this.nursingOutputData.pastIntakeOutputList.length != 0){
      		  this.calculateTotalIntakeOutput(this.nursingOutputData.pastIntakeOutputList);
      		}

          //Calculate cumm En and Cumm Urine -> To be displayed in table
          if(this.nursingOutputData.intakeOutputList.length != 0){
      		  this.calculateCummulativeData(this.nursingOutputData.intakeOutputList);
      		}

          //Calculate Protein and Energy data from feedCalulator
          if(this.nursingOutputData.feedCalulator != null && this.nursingOutputData.feedCalulator.lastDeficitCal != null &&
            this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake != null && this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake != null){
              this.protein = 0;
              this.energy = 0;
              if(this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Protein != null){
                this.protein = this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Protein;
              }
              if(this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Protein != null){
                this.protein += this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Protein;
              }
              if(this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Energy != null){
                this.energy = this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Energy;
              }
              if(this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Energy != null){
                this.energy += this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Energy;
              }
          }

          //This block is not useful right now
          if(this.nursingOutputData.intakeOutputList != null && this.nursingOutputData.intakeOutputList.length != 0){
    		    this.isOutputSummary = false;
    		    this.isEnteralSummary = false;
    		    this.isParenteralSummary = false;
    		    this.isOtherAdditivesSummary = false;
            for(var i=0;i<this.nursingOutputData.intakeOutputList.length;i++){
              if(this.nursingOutputData.intakeOutputList[i].enteralComment != null || this.nursingOutputData.intakeOutputList[i].parenteralComment != null
                 || this.nursingOutputData.intakeOutputList[i].outputComment != null || this.nursingOutputData.intakeOutputList[i].enteralComment != '' ||
                  this.nursingOutputData.intakeOutputList[i].parenteralComment != '' || this.nursingOutputData.intakeOutputList[i].outputComment != ''){
                this.isDisplayRemark = true;
              }
            }
    		    for(var i=0;i<this.nursingOutputData.intakeOutputList.length;i++){
    			    if((this.isOtherAdditivesSummary != true) && (this.nursingOutputData.intakeOutputList[i].calciumVolume != null
               || this.nursingOutputData.intakeOutputList[i].ironVolume != null || this.nursingOutputData.intakeOutputList[i].mvVolume != null
               || this.nursingOutputData.intakeOutputList[i].otherAdditiveVolume != null || this.nursingOutputData.intakeOutputList[i].calciumComment != null
               || this.nursingOutputData.intakeOutputList[i].ironComment != null || this.nursingOutputData.intakeOutputList[i].mvComment != null
               || this.nursingOutputData.intakeOutputList[i].otherAdditiveComment != null)){
  			        this.isOtherAdditivesSummary = true;
    			    }
      			}
      		}

          if(this.hours == 24) {
            //this.nursingOutputDataFullShift = this.nursingOutputData;
            if(this.isPrintFromMedFlag == false && type == 'print') {
              this.goToPrintNursingOutput();
            }
          } else if(this.isPrintFromMedFlag == false && type == 'print') {
            this.goToPrintNursingOutput();
          }

          this.isCommentAvailable();
        }, err => console.log(err), () => this.isShowDisable = false
        );
      }catch(e){
        console.log("Exception in http service:"+e);
      };
    }
  }

  populateOrderString = function() {

    for(var k =0 ; k < this.nursingOutputData.doctorNutritionOrder.length; k++){
      this.primaryFeed = "";
      this.feedTypeArr = [];
      if (!(this.nursingOutputData.doctorNutritionOrder[k] == null || this.nursingOutputData.doctorNutritionOrder[k].feedtype == null || this.nursingOutputData.doctorNutritionOrder[k].feedtype == "")) {
        this.feedTypeArr = this.nursingOutputData.doctorNutritionOrder[k].feedtype.replace("[", "").replace("]", "").split(",");
        for (var i = 0; i < this.nursingOutputData.refFeedTypeList.length; i++) {
          if (this.feedTypeArr[0] == this.nursingOutputData.refFeedTypeList[i].feedtypeid) {
            this.primaryFeed = this.nursingOutputData.refFeedTypeList[i].feedtypename;
          }
        }
      }

      this.formulaType = "";
      if (!(this.nursingOutputData.doctorNutritionOrder[k] == null || this.nursingOutputData.doctorNutritionOrder[k].feedtype == null || this.nursingOutputData.doctorNutritionOrder[k].feedtype == "")) {
        if(this.nursingOutputData.doctorNutritionOrder[k].feedTypeSecondary != null) {
          var secondaryFeedArr = this.nursingOutputData.doctorNutritionOrder[k].feedTypeSecondary.replace("[", "").replace("]", "").split(",");
          for(var index =0; index<secondaryFeedArr.length;index++){
            for (var i = 0; i < this.nursingOutputData.refFeedTypeList.length; i++) {

              if (secondaryFeedArr[index] == this.nursingOutputData.refFeedTypeList[i].feedtypeid) {
                if(this.formulaType=='')
                  this.formulaType = this.nursingOutputData.refFeedTypeList[i].feedtypename;
                else{
                  this.formulaType += ", "+this.nursingOutputData.refFeedTypeList[i].feedtypename;
                }
              }
            }
          }
        }
      }

      this.enFeedStr = "";
      if(this.nursingOutputData.doctorNutritionOrder[k] != null && this.nursingOutputData.doctorNutritionOrder[k].isenternalgiven) {
        if(this.nursingOutputData.doctorNutritionOrder[k].feedduration != 'Continuous'){
          if(this.nursingOutputData.enFeedList != null && this.nursingOutputData.enFeedList.length > 0) {
            for(var i = 0; i < this.nursingOutputData.enFeedList.length; i++) {
              if(i == 0) {
                this.enFeedStr = this.nursingOutputData.enFeedList[i].feed_volume + ' (ml/feed) for ' + this.nursingOutputData.enFeedList[i].no_of_feed + ' feed';
              } else {
                this.enFeedStr += ', ' + this.nursingOutputData.enFeedList[i].feed_volume + ' (ml/feed) for ' + this.nursingOutputData.enFeedList[i].no_of_feed + ' feed';
              }
            }
          } else {
            if(this.nursingOutputData.feedMethodStr == 'Breast Feed') {
              this.enFeedStr = 'Give EN';
              this.isDisable = true;
              if(this.nursingOutputData.doctorNutritionOrder[k].isAdLibGiven) {
                this.nursingOutputData.doctorNutritionOrder[k].feedduration = null;
              }
            } else {
              this.enFeedStr = this.nursingOutputData.doctorNutritionOrder[k].feedvolume + ' (ml/feed)';
            }
          }
        }else if(this.nursingOutputData.doctorNutritionOrder[k].feedduration != null && this.nursingOutputData.doctorNutritionOrder[k].feedduration == 'Continuous'){
          var remVolume = this.round((this.nursingOutputData.doctorNutritionOrder[k].totalfeedMlDay / this.nursingOutputData.doctorNutritionOrder[k].duration), 2);
          this.enFeedStr = remVolume + ' (ml/hr) continuously ';
        }
      }
      this.nursingOutputData.doctorNutritionOrder[k].enFeedStr = this.enFeedStr;
      this.nursingOutputData.doctorNutritionOrder[k].formulaType = this.formulaType;
      this.nursingOutputData.doctorNutritionOrder[k].primaryFeed = this.primaryFeed;
    }
  }

  isCommentAvailable(){
    if(this.nursingOutputData.anthropometryList != null && this.nursingOutputData.anthropometryList.length != 0){
      for(var i=0;i<this.nursingOutputData.anthropometryList.length;i++){
        if(this.nursingOutputData.anthropometryList[i].comments != null || this.nursingOutputData.anthropometryList[i].comments != ''){
          this.isComment = true;
        }
      }
    }

    if(this.nursingOutputData.vitalList != null && this.nursingOutputData.vitalList.length != 0){
      for(var i=0;i<this.nursingOutputData.vitalList.length;i++){
        if(this.nursingOutputData.vitalList[i].comments != null && this.nursingOutputData.vitalList[i].comments != ''){
          this.isComment = true;
        }
      }
    }

    if(this.nursingOutputData.intakeOutputList != null && this.nursingOutputData.intakeOutputList.length != 0){
      for(var i=0;i<this.nursingOutputData.intakeOutputList.length;i++){
        if((this.nursingOutputData.intakeOutputList[i].outputComment != null && this.nursingOutputData.intakeOutputList[i].outputComment != '') ||
           (this.nursingOutputData.intakeOutputList[i].enteralComment != null && this.nursingOutputData.intakeOutputList[i].enteralComment != '') ||
           (this.nursingOutputData.intakeOutputList[i].parenteralComment != null && this.nursingOutputData.intakeOutputList[i].parenteralComment != '') ||
           (this.nursingOutputData.intakeOutputList[i].calciumComment != null && this.nursingOutputData.intakeOutputList[i].calciumComment != '') ||
           (this.nursingOutputData.intakeOutputList[i].ironComment != null && this.nursingOutputData.intakeOutputList[i].ironComment != '') ||
           (this.nursingOutputData.intakeOutputList[i].mvComment != null && this.nursingOutputData.intakeOutputList[i].mvComment != '')){
             this.isComment = true;
        }
      }
    }
  }

  calculateHeplockVolume(dataList : any){
    this.totalHeplockDelVolume = 0;
    if(dataList != null && dataList.length != 0){
      for(var i = 0; i < dataList.length; i++){
        if(dataList[i].deliveredVolume != null){
          this.totalHeplockDelVolume = this.totalHeplockDelVolume + dataList[i].deliveredVolume;
        }
      }
    }
  }

  calculateBloodProductVolume(dataList : any){
    this.totalBloodDelVolume = 0;
    if(dataList != null && dataList.length != 0){
      for(var i = 0; i < dataList.length; i++){
        if(dataList[i].delivered_volume != null){
          this.totalBloodDelVolume = this.totalBloodDelVolume + dataList[i].delivered_volume;
        }
      }
    }
  }

  //This function currently not called/used
  getNursingOutputDataFullShift(fromDate : Date, toDate : Date, type : string) {
  	if(fromDate != null && toDate != null) {
  		fromDate.setSeconds(0);
  		fromDate.setMilliseconds(0);
  		toDate.setSeconds(0);
  		toDate.setMilliseconds(0);

  		var fromParameter = fromDate.getTime();
  		var toParameter = toDate.getTime();

    	try {
        this.http.request(this.apiData.getNursingOutputData + this.uhid + "/" + fromParameter + "/" + toParameter + "/").subscribe((res: Response) => {
          console.log(res.json());
          this.nursingOutputDataFullShift = res.json();
          console.log(this.nursingOutputDataFullShift);
          if(this.nursingOutputDataFullShift.pastMedicationList.length > 0){
            this.generateMedicationMap(this.nursingOutputDataFullShift, type);
            this.calculateMedicineIntake(this.nursingOutputDataFullShift.pastMedicationList);
          } else {
            this.totalMedicineVol = 0;
          }

          if(this.nursingOutputData.doctorNutritionOrder != null && this.nursingOutputData.doctorNutritionOrder.length != 0){
            this.calculateCalcium(this.nursingOutputData.doctorNutritionOrder);
            this.calculateSodium(this.nursingOutputData.doctorNutritionOrder);
          }

          if(this.nursingOutputDataFullShift.allPastNursingBloodProductList != null && this.nursingOutputDataFullShift.allPastNursingBloodProductList.length > 0){
            this.calculateBloodProductVolume(this.nursingOutputDataFullShift.allPastNursingBloodProductList);
          }

          if(this.nursingOutputDataFullShift.pastHeplockList != null && this.nursingOutputDataFullShift.pastHeplockList.length > 0){
            this.calculateHeplockVolume(this.nursingOutputDataFullShift.pastHeplockList);
          }

          if(this.nursingOutputDataFullShift.pastIntakeOutputList.length != 0){
      		  this.calculateTotalIntakeOutput(this.nursingOutputDataFullShift.pastIntakeOutputList);
      		}

          if(this.isPrintFromMedFlag == false && type == 'print'){
            this.goToPrintNursingOutput();
          }
        });
      }catch(e){
        console.log("Exception in http service:"+e);
      };
    }
  }

  calculateMedicineIntake(dataList : any){
    this.totalMedicineVol = 0;
    for(var i = 0; i < dataList.length; i++){
      var obj = dataList[i];
      console.log(obj);
      if(obj.stop_flag == false && obj.doctorMedicine != null && obj.doctorMedicine.route == 'IV') {
        if(obj.delivered_volume != null && obj.delivered_volume != '') {
          this.totalMedicineVol += obj.delivered_volume;
        } else if(obj.doctorMedicine.inf_volume != null && obj.doctorMedicine.inf_volume != '') {
          this.totalMedicineVol += obj.doctorMedicine.inf_volume;
        }
      }
    }
  }

  calculateCummulativeData (dataList : any){
    var urineCumm = 0;
    var enCumm = 0;
    this.totalAdditives = 0;

    if(dataList != null && dataList.length != 0){
      for(var i = 0; i < dataList.length; i++){
        var obj = dataList[i];
        if(obj.urine != null){
          urineCumm += parseFloat(obj.urine);
          urineCumm = this.round(urineCumm, 2);
        }
        obj.cummulativeUrine = urineCumm;

        if(obj.actualFeed != null){
          enCumm += (obj.actualFeed);
          enCumm = this.round(enCumm, 2);
        }

        if(obj.calciumVolume != null){
          enCumm += (obj.calciumVolume);
          enCumm = this.round(enCumm, 2);

          this.totalAdditives += (obj.calciumVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.ironVolume != null){
          enCumm += (obj.ironVolume);
          enCumm = this.round(enCumm, 2);

          this.totalAdditives += (obj.ironVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.mvVolume != null){
          enCumm += (obj.mvVolume);
          enCumm = this.round(enCumm, 2);

          this.totalAdditives += (obj.mvVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.vitamindVolume != null){
          enCumm += (obj.vitamindVolume);
          enCumm = this.round(enCumm, 2);

          this.totalAdditives += (obj.vitamindVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.otherAdditiveVolume != null){
          enCumm += (obj.otherAdditiveVolume);
          enCumm = this.round(enCumm, 2);

          this.totalAdditives += (obj.otherAdditiveVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }
        obj.cummulativeEn = enCumm;
        obj.totalAdditives = this.totalAdditives;
        this.totalAdditives = 0;
      }
    }
    return dataList;
  }

  calculateTotalIntakeOutput(dataList : any){
    this.totalEn = 0;
    this.totalPn = 0;
    this.totalAdditives = 0;
    this.totalUrine = 0;
    this.totalFeed = 0;
    this.urineMlKgHr = 0;
    this.totalDrain = 0;
    this.totalGastricAspirate = 0;
    this.totalBolus = 0;

    if(dataList != null && dataList.length != 0){
      for(var i = 0; i < dataList.length; i++){
        var obj = dataList[i];
        if(obj.urine != null){
          this.totalUrine += parseFloat(obj.urine);
          this.totalUrine = this.round(this.totalUrine, 2);
        }
        obj.cummulativeUrine = this.totalUrine;

        if(obj.bolusDeliveredFeed != null) {
          this.totalBolus += (obj.bolusDeliveredFeed);
          this.totalBolus = this.round(this.totalBolus, 2);
        }

        if(obj.drain != null) {
          this.totalDrain += parseFloat(obj.drain);
          this.totalDrain = this.round(this.totalDrain, 2);
        }

        if(obj.gastricAspirate != null) {
          this.totalGastricAspirate += parseFloat(obj.gastricAspirate);
          this.totalGastricAspirate = this.round(this.totalGastricAspirate, 2);
        }

        if(obj.actualFeed != null){
          this.totalEn += (obj.actualFeed);
          this.totalEn = this.round(this.totalEn, 2);
        }

        if(obj.calciumVolume != null){
          this.totalEn += (obj.calciumVolume);
          this.totalEn = this.round(this.totalEn, 2);

          this.totalAdditives += (obj.calciumVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.ironVolume != null){
          this.totalEn += (obj.ironVolume);
          this.totalEn = this.round(this.totalEn, 2);

          this.totalAdditives += (obj.ironVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.mvVolume != null){
          this.totalEn += (obj.mvVolume);
          this.totalEn = this.round(this.totalEn, 2);

          this.totalAdditives += (obj.mvVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.vitamindVolume != null){
          this.totalEn += (obj.vitamindVolume);
          this.totalEn = this.round(this.totalEn, 2);

          this.totalAdditives += (obj.vitamindVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }

        if(obj.otherAdditiveVolume != null){
          this.totalEn += (obj.otherAdditiveVolume);
          this.totalEn = this.round(this.totalEn, 2);

          this.totalAdditives += (obj.otherAdditiveVolume);
          this.totalAdditives = this.round(this.totalAdditives, 2);
        }
        obj.totalAdditives = this.totalAdditives;
        this.totalAdditives = 0;
        obj.cummulativeEn = this.totalEn;

        if(obj.lipid_delivered != null){
          this.totalPn += (obj.lipid_delivered);
          this.totalPn = this.round(this.totalPn, 2);
        }

        if(obj.tpn_delivered != null){
          this.totalPn += (obj.tpn_delivered);
          this.totalPn = this.round(this.totalPn, 2);
        }

        if(obj.readymadeDeliveredFeed != null){
          this.totalPn += (obj.readymadeDeliveredFeed);
          this.totalPn = this.round(this.totalPn, 2);
        }
      }

      if(this.totalUrine != null && this.weight != null) {
        this.urineMlKgHr = this.totalUrine / (this.weight);
      }
      this.totalFeed = this.totalEn + this.totalPn;
    }
    return dataList;
  }

  calculateCalcium(babyFeedList : any){
    try{
      this.calciumVolume = 0;

      if(this.nursingOutputData.intakeOutputList != null && this.nursingOutputData.intakeOutputList.length > 0){
        for(var i= 0; i < this.nursingOutputData.intakeOutputList.length; i++){
          if(this.nursingOutputData.intakeOutputList[i].calciumDeliveredFeed != null){
            this.calciumVolume += (this.nursingOutputData.intakeOutputList[i].calciumDeliveredFeed * 9.3);
          }
        }
      }
      if(this.calciumVolume != undefined){
        this.calciumVolume = this.calciumVolume / this.weight;
      }
      if(this.nursingOutputData.feedCalulator != null && this.nursingOutputData.feedCalulator.lastDeficitCal != null && this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake != null
        && this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake != null){
          if(this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Calcium != null){
            this.calciumVolume = this.calciumVolume + this.nursingOutputData.feedCalulator.lastDeficitCal.enteralIntake.Calcium;
          }
          if(this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Calcium != null){
            this.calciumVolume = this.calciumVolume + this.nursingOutputData.feedCalulator.lastDeficitCal.parentalIntake.Calcium;
          }
      }
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  calculateSodium(babyFeedList : any){
    try{
      this.sodiumVolume = 0;
      if(this.nursingOutputData.intakeOutputList != null && this.nursingOutputData.intakeOutputList.length > 0){
        for(var i= 0; i < this.nursingOutputData.intakeOutputList.length; i++){
          if(babyFeedList[0].bolusType != null && babyFeedList[0].bolusType == "saline" && this.nursingOutputData.intakeOutputList[i].bolusDeliveredFeed != null){
            this.sodiumVolume += ((this.nursingOutputData.intakeOutputList[i].bolusDeliveredFeed * 154) / 100);
          }
          if(babyFeedList[0].sodiumVolume != null && babyFeedList[0].sodiumVolume == "0.15" && babyFeedList[0].sodiumTotal != null && this.nursingOutputData.intakeOutputList[i].readymadeDeliveredFeed != null && babyFeedList[0].totalfluidMlDay != null){
            this.sodiumVolume += ((((babyFeedList[0].sodiumTotal / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].readymadeDeliveredFeed) * 154) / 1000);
          }
          if(babyFeedList[0].sodiumVolume != null && babyFeedList[0].sodiumVolume == "0.5" && babyFeedList[0].sodiumTotal != null && this.nursingOutputData.intakeOutputList[i].readymadeDeliveredFeed != null && babyFeedList[0].totalfluidMlDay != null){
            this.sodiumVolume += ((((babyFeedList[0].sodiumTotal / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].readymadeDeliveredFeed) * 513) / 1000);
          }

          if(babyFeedList[0].sodiumVolume != null && babyFeedList[0].sodiumVolume == "0.15" && babyFeedList[0].sodiumTotal != null && this.nursingOutputData.intakeOutputList[i].tpn_delivered != null && babyFeedList[0].totalfluidMlDay != null){
            this.sodiumVolume += ((((babyFeedList[0].sodiumTotal / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 154) / 1000);
          }
          if(babyFeedList[0].sodiumVolume != null && babyFeedList[0].sodiumVolume == "0.5" && babyFeedList[0].sodiumTotal != null && this.nursingOutputData.intakeOutputList[i].tpn_delivered != null && babyFeedList[0].totalfluidMlDay != null){
            this.sodiumVolume += ((((babyFeedList[0].sodiumTotal / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 513) / 1000);
          }

          if(babyFeedList[0].isReadymadeSolutionGiven == true && babyFeedList[0].readymadeFluidType != null && babyFeedList[0].readymadeTotalFluidVolume != null && this.nursingOutputData.intakeOutputList[i].tpn_delivered != null && babyFeedList[0].totalfluidMlDay != null){
            if(babyFeedList[0].readymadeFluidType == "0.45 NS in D5(N/2)" || babyFeedList[0].readymadeFluidType == "0.45 NS in D10(N/2)"){
              this.sodiumVolume += ((((babyFeedList[0].readymadeTotalFluidVolume / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 77) / 1000);
            }
            if(babyFeedList[0].readymadeFluidType == "0.3 NS in D5(N/3)" || babyFeedList[0].readymadeFluidType == "0.3 NS in D10(N/3)"){
              this.sodiumVolume += ((((babyFeedList[0].readymadeTotalFluidVolume / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 51.3) / 1000);
            }
            if(babyFeedList[0].readymadeFluidType == "0.2 NS in D5(N/4)" || babyFeedList[0].readymadeFluidType == "0.2 NS in D5(N/4)"){
              this.sodiumVolume += ((((babyFeedList[0].readymadeTotalFluidVolume / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 38.5) / 1000);
            }
            if(babyFeedList[0].readymadeFluidType == "0.18 NS in D5(N/5)" || babyFeedList[0].readymadeFluidType == "0.18 NS in D10(N/5)"){
              this.sodiumVolume += ((((babyFeedList[0].readymadeTotalFluidVolume / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 30.8) / 1000);
            }
            if(babyFeedList[0].readymadeFluidType == "ISO P(N/6)"){
              this.sodiumVolume += ((((babyFeedList[0].readymadeTotalFluidVolume / babyFeedList[0].totalfluidMlDay) * this.nursingOutputData.intakeOutputList[i].tpn_delivered) * 23) / 1000);
            }
          }
        }
      }

      if(this.sodiumVolume != undefined){
        this.sodiumVolume = this.sodiumVolume / this.weight;
      }
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  generatebloodProductMap(bloodProductDataList : any) {
    var bloodProductMap = {};
    if(bloodProductDataList.pastNursingBloodProductList != null && bloodProductDataList.pastNursingBloodProductList.length > 0) {
      for (var i=0; i < bloodProductDataList.pastNursingBloodProductList.length; i++) {
        var item = bloodProductDataList.pastNursingBloodProductList[i];
        bloodProductMap[item.doctor_blood_products_id] = item;
      }
    }
    bloodProductDataList.isBloodProductTableVisible = false;
    for (var i=0; i < bloodProductDataList.bloodProductList.length; i++) {
      var item = bloodProductDataList.bloodProductList[i];
      item.assessment_time = new Date(item.assessment_time);
      if(bloodProductMap[item.doctor_blood_products_id] != undefined &&
        bloodProductMap[item.doctor_blood_products_id] != null) {
          bloodProductDataList.isBloodProductTableVisible = true;
          item.nursingObj = bloodProductMap[item.doctor_blood_products_id];
      } else {
        item.nursingObj = Object.assign({}, bloodProductDataList.emptyBloodProductObj);
      }
    }
    console.log('obj after blood product');
    console.log(bloodProductDataList);
  }

  generateMedicationMap(dataList : any, type : any) {
    var medicationMap = {};
    var assessmentMedicineMap = {};
    for(let i = 0; i < dataList.allMedicationList.length; i++) {
     let item = dataList.allMedicationList[i];
     medicationMap[item.babyPresid] = item;

     if(item.frequency == null) {
      item.freqFactor = 1;
      item.medicationFreqStr = "";
     } else {
      for(var j = 0; j < dataList.freqList.length; j++) {
       var freq = dataList.freqList[j];
       if(freq.freqid == item.frequency) {
        item.freqFactor = freq.frequency_int;
        item.medicationFreqStr = freq.freqvalue;
       }
      }
     }
    }

    for(let i = 0; i < dataList.pastAssessmentMedicineList.length; i++) {
     let item = dataList.pastAssessmentMedicineList[i];
     assessmentMedicineMap[item.assessmentid] = item;
    }

    for(let i = 0; i < dataList.medicationList.length; i++) {
     let item = dataList.medicationList[i];
     if(item.assessment_medicine == null || item.assessment_medicine == false) {
        item.doctorMedicine = medicationMap[item.baby_presid];
      } else {
        item.doctorMedicine = {};
        var obj = assessmentMedicineMap[item.baby_presid];
        item.doctorMedicine.medicinename = obj.med_name;
        item.doctorMedicine.dose = obj.dose;
        item.doctorMedicine.route = obj.route;
      }
    }

    if(type == 'print'){
        this.isPrintFromMedFlag = false;
        this.goToPrintNursingOutput();
    }
  }

  //Setting the values to local Storage which are to be used in print
  goToPrintNursingOutput(){
  	console.log("this is print Nursing Output functin");
  	this.nursingOutputData.whichTab = 'Nursing-output';
  	this.nursingOutputData.from_time = this.nursingOutputObj.fromDate;
    this.nursingOutputData.to_time = this.nursingOutputObj.toDate;
    console.log(JSON.stringify(this.nursingOutputData));
    localStorage.setItem('printNursingDataObjNotes', JSON.stringify(this.nursingOutputData));
    if(this.fluidReq != undefined && this.fluidReq != null){
      localStorage.setItem('fluidReq', JSON.stringify(this.fluidReq));
    }
    if(this.totalEn != undefined && this.totalEn != null){
      localStorage.setItem('totalEn', JSON.stringify(this.totalEn));
    }
    if(this.totalPn != undefined && this.totalPn != null){
      localStorage.setItem('totalPn', JSON.stringify(this.totalPn));
    }
    if(this.totalMedicineVol != undefined && this.totalMedicineVol != null){
      localStorage.setItem('totalMedicineVol', JSON.stringify(this.totalMedicineVol));
    }
    if(this.totalFeed != undefined && this.totalFeed != null){
      localStorage.setItem('totalFeed', JSON.stringify(this.totalFeed));
    }
    if(this.hours != undefined && this.hours != null){
      localStorage.setItem('hours', JSON.stringify(this.hours));
    }
    if(this.totalUrine != undefined && this.totalUrine != null){
      localStorage.setItem('totalUrine', JSON.stringify(this.totalUrine));
    }
    if(this.totalFeed != undefined && this.totalFeed != null){
      localStorage.setItem('totalFeed', JSON.stringify(this.totalFeed));
    }
    if(this.urineMlKgHr != undefined && this.urineMlKgHr != null){
      localStorage.setItem('urineMlKgHr', JSON.stringify(this.urineMlKgHr));
    }
    if(this.totalGastricAspirate != undefined && this.totalGastricAspirate != null){
      localStorage.setItem('totalGastricAspirate', JSON.stringify(this.totalGastricAspirate));
    }
    if(this.weight != undefined && this.weight != null){
      localStorage.setItem('weight', JSON.stringify(this.weight));
    }
    if(this.totalHeplockDelVolume != undefined && this.totalHeplockDelVolume != null){
      localStorage.setItem('totalHeplockDelVolume', JSON.stringify(this.totalHeplockDelVolume));
    }
    if(this.totalBloodDelVolume != undefined && this.totalBloodDelVolume != null){
      localStorage.setItem('totalBloodDelVolume', JSON.stringify(this.totalBloodDelVolume));
    }
    if(this.totalDrain != undefined && this.totalDrain != null){
      localStorage.setItem('totalDrain', JSON.stringify(this.totalDrain));
    }
    if(this.totalBolus != undefined && this.totalBolus != null){
      localStorage.setItem('totalBolus', JSON.stringify(this.totalBolus));
    }
    if(this.totalAdditives != undefined && this.totalAdditives != null){
      localStorage.setItem('totalAdditives', JSON.stringify(this.totalAdditives));
    }
    if(this.sodiumVolume != undefined && this.sodiumVolume != null){
      localStorage.setItem('sodiumVolume', JSON.stringify(this.sodiumVolume));
    }
    if(this.calciumVolume != undefined && this.calciumVolume != null){
      localStorage.setItem('calciumVolume', JSON.stringify(this.calciumVolume));
    }
    if(this.protein != undefined && this.protein != null){
      localStorage.setItem('protein', JSON.stringify(this.protein));
    }
    if(this.energy != undefined && this.energy != null){
      localStorage.setItem('energy', JSON.stringify(this.energy));
    }
    this.router.navigateByUrl('/nursing/nursing-print');
	}
  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }
	ngOnInit() {
     for(let i=1;i<=12;++i){
      if(i<10)
        this.hoursList.push("0"+i.toString());
      else
        this.hoursList.push(i.toString());
    }

    for(let i=0;i<60;++i){
      if(i<10)
        this.minutesList.push("0"+i.toString());
      else
        this.minutesList.push(i.toString());
    }
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM'){
       tempHr = '00';
    }else if(tempHr != 12 && tempMer == 'PM'){
       tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.maxDob = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
    var tempPrevTime = this.maxDob.slice(16,24);
    this.maxDob = this.maxDob.replace(tempPrevTime,tempFullTime);


    if(localStorage.getItem('fluidReq') != undefined){
      localStorage.removeItem('fluidReq');
    }
    if(localStorage.getItem('totalPn') != undefined){
      localStorage.removeItem('totalPn');
    }
    if(localStorage.getItem('totalEn') != undefined){
      localStorage.removeItem('totalEn');
    }
    if(localStorage.getItem('totalMedicineVol') != undefined){
      localStorage.removeItem('totalMedicineVol');
    }
    if(localStorage.getItem('totalFeed') != undefined){
      localStorage.removeItem('totalFeed');
    }
    if(localStorage.getItem('hours') != undefined){
      localStorage.removeItem('hours');
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
    if(localStorage.getItem('totalGastricAspirate') != undefined){
      localStorage.removeItem('totalGastricAspirate');
    }
    if(localStorage.getItem('weight') != undefined){
      localStorage.removeItem('weight');
    }
    if(localStorage.getItem('totalHeplockDelVolume') != undefined){
      localStorage.removeItem('totalHeplockDelVolume');
    }
    if(localStorage.getItem('totalBloodDelVolume') != undefined){
      localStorage.removeItem('totalBloodDelVolume');
    }
    if(localStorage.getItem('totalDrain') != undefined){
      localStorage.removeItem('totalDrain');
    }
    if(localStorage.getItem('totalBolus') != undefined){
      localStorage.removeItem('totalBolus');
    }
    if(localStorage.getItem('totalAdditives') != undefined){
      localStorage.removeItem('totalAdditives');
    }
    if(localStorage.getItem('protein') != undefined){
      localStorage.removeItem('protein');
    }
    if(localStorage.getItem('energy') != undefined){
      localStorage.removeItem('energy');
    }
    if(localStorage.getItem('sodiumVolume') != undefined){
      localStorage.removeItem('sodiumVolume');
    }
    if(localStorage.getItem('calciumVolume') != undefined){
      localStorage.removeItem('calciumVolume');
    }
    console.log("In nursing-output component");
		this.getNursingOutputData(this.nursingOutputObj.fromDate, this.nursingOutputObj.toDate,'show','onclickShow');
	}

}
