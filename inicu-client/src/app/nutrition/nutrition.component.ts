import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RefTestslist } from '../common-model/refTestslist';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { FeedPojo } from './feedPojo';
import { FeedDropDown } from './feedDropDown';
import { OralfeedDetail } from './oralfeedDetail';
import { PastFeedTemp } from './pastFeedTemp';
import { FeedTemp } from './feedTemp';
import { BabyfeedDetail } from './babyfeedDetail';
import { BloodProduct } from './bloodProduct';
import { DecimalPipe } from '@angular/common';
import * as $ from 'jquery';
import { AppComponent } from '../app.component';
import { CaclulatorDeficitPOJO } from './caclulatorDeficitPOJO';

@Component({
  selector: 'nutrition',
  templateUrl: './nutrition.component.html',
  styleUrls: ['./nutrition.component.css']
})
export class NutritionComponent implements OnInit {
	isDisabled:boolean;
  	isLoaderVisible : boolean;
  	loaderContent : string;
	apiData : APIurl;
	http: Http;
	router : Router;
	uhid : string;
	loggedUser : string;
	discharge_summary_type : string;
	tempVarTohideShowIvFluid : boolean;
	dropdownData : FeedDropDown;
	feedTempObject : FeedTemp;
	tpnFeed : FeedPojo;
	vanishSubmitResponseVariable : boolean;
	isExpandBolusTable : boolean;
	isNormal : boolean;
	isFeedComplete : boolean;
	isIVFluidComplete : boolean;
	isFeedDirty : boolean;
	expanded : boolean;
	isDextroseCalcuated : boolean;
	totalAdditiveDataEnteral : number;
	totalAdditiveDataParenteral : number;
	isrightSectionHeader : boolean;
	isrightParenternalSectionHeader : boolean;
	isEnternalVisible : boolean;
	isBloodVisible : boolean;
	isParenteralVisible : boolean;
  isStopVisible :  boolean;
	textInfo : string;
	responseType : any;
	responseMessage : any;
	minDate : string;
  additionalBolusNo : string;
  additionalBolusYes : string;
	maxDate : Date;
  noOfEnFeed: number;
  enFeedDisableSubmit : boolean;
  totalMedInfVolume : number;
  totalBloodProductVolume : number;
  totalHeplockVolume : number;
  medicineStr : string;
  durationOffset : any;
  selectedFluidType :  Array<any>;
  fluidTypeAvailableList : Array<any>;

	constructor(http: Http, router : Router) {
		this.apiData = new APIurl();
		this.http = http;
		this.router = router;
		this.feedTempObject = new FeedTemp();
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.loggedUser = 'test';
		// this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.discharge_summary_type = JSON.parse(localStorage.getItem('settingReference'));
		this.isExpandBolusTable = true;
		this.isNormal = true;
		this.isFeedComplete = false;
		this.isIVFluidComplete = false;
		this.isFeedDirty = false;
		this.expanded = false;
		this.isDextroseCalcuated = false;
		this.totalAdditiveDataEnteral = 0;
		this.totalAdditiveDataParenteral = 0;
		this.isrightSectionHeader = false;
		this.isrightParenternalSectionHeader = false;
		this.isEnternalVisible = true;
		this.isBloodVisible = false;
		this.isParenteralVisible = false;
    this.isStopVisible = false;
		this.textInfo = "";
    this.noOfEnFeed = 0;
    this.totalMedInfVolume = 0;
    this.totalBloodProductVolume = 0;
    this.totalHeplockVolume = 0;
    this.additionalBolusNo = "";
    this.additionalBolusYes = "";
    this.isDisabled=true;
    this.durationOffset = 1;
    this.selectedFluidType = [];
    this.fluidTypeAvailableList = [];
	}

	printNutrition() {
		let printData = [];
		var index = 0;

		for(var i = 0; i < this.tpnFeed.babyFeedList.length; i++) {
			var obj = this.tpnFeed.babyFeedList[i];
			if(obj.creationtime >= this.feedTempObject.printFrom.getTime() && obj.creationtime <= this.feedTempObject.printTo.getTime()) {
				printData[index++] = obj;
			}
		}
		localStorage.setItem('nutritionPrintData', JSON.stringify(printData));
		// code here to redirect for print router
		console.log(printData);
	}

	changeOralFeed(type : any){
    if((!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.duration)) && (!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.feedduration)) && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous"){
      this.noOfEnFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.duration / (this.tpnFeed.currentFeedInfo.babyFeed.feedduration * 1));
    }
		if(type=='feedVolume'){
			this.feedTempObject.isFeedVolumeEntered = true;
		}else if(type=="totalFeed"){
			this.feedTempObject.isFeedVolumeEntered = false;
      this.calFeedsAuto();
		}else{
			this.feedTempObject.isFeedVolumeEntered = null;
		}

		this.calculateCommonData('nonfeed');
    this.validateEnFeed();
	}

  calFeedsAuto(){
    if((!this.isEmpty(this.noOfEnFeed)) && (!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay))){
      if(Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay / this.noOfEnFeed) != (this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay / this.noOfEnFeed)){
        this.tpnFeed.enFeedDetailList[0].feed_volume = Math.floor(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay / this.noOfEnFeed);
        if(this.tpnFeed.enFeedDetailList.length > 1){
          this.tpnFeed.enFeedDetailList[1].feed_volume = this.tpnFeed.enFeedDetailList[0].feed_volume + 1;
          this.tpnFeed.enFeedDetailList[1].no_of_feed = (this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay % this.noOfEnFeed);
          this.tpnFeed.enFeedDetailList[0].no_of_feed = this.noOfEnFeed - this.tpnFeed.enFeedDetailList[1].no_of_feed;
        }
        else{
          var feedTwo = JSON.parse(JSON.stringify(this.tpnFeed.emptyEnFeedDetailObj));
          feedTwo.feed_volume = this.tpnFeed.enFeedDetailList[0].feed_volume + 1;
          feedTwo.no_of_feed = (this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay % this.noOfEnFeed);
          this.tpnFeed.enFeedDetailList[0].no_of_feed = this.noOfEnFeed - feedTwo.no_of_feed;
          this.tpnFeed.enFeedDetailList.push(feedTwo);
        }
      }
      else{
        this.tpnFeed.enFeedDetailList[0].feed_volume = (this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay / this.noOfEnFeed);
        this.tpnFeed.enFeedDetailList[0].no_of_feed = this.noOfEnFeed;
        if(this.tpnFeed.enFeedDetailList.length > 1){
          for(var i = 1; i<this.tpnFeed.enFeedDetailList.length;i++){
            this.tpnFeed.enFeedDetailList.splice(i,1);
          }
        }
      }
    }
  }

  calEnFeedNo() {
    if(this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous"){
      this.noOfEnFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.duration / (this.tpnFeed.currentFeedInfo.babyFeed.feedduration * 1));

      this.calFeedsAuto();
      this.calEnFeedValues();
      this.calculateCommonData(null);
      this.validateEnFeed();
    }else{

    }
  }

  calEnFeedValues() {
    if((!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.duration)) && (!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.feedduration)) && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous"){
      this.noOfEnFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.duration / (this.tpnFeed.currentFeedInfo.babyFeed.feedduration * 1));
    }

    if(this.tpnFeed.enFeedDetailList[0].feed_volume != null && this.tpnFeed.enFeedDetailList[0].feed_volume != 0
      && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != null && this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay) && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous") {
      this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay = this.tpnFeed.enFeedDetailList[0].feed_volume * this.noOfEnFeed;
      this.changeOralFeed('feedVolume');
    }

    this.validateEnFeed();
  }

	expandBolusTable(){
		this.isExpandBolusTable = !this.isExpandBolusTable;
	}

	showCheckboxes(id : any) {
		console.log(id);
		let fields = id.split('-');
		let name = fields[2];
		console.log(name);
		let checkboxContId = "#checkboxes-"+ name;
		console.log(checkboxContId);
		let width = 165;
		if (!this.expanded) {
			$(checkboxContId).toggleClass("show");
			// checkboxes.style.display = "block";
			$(checkboxContId).width(width);
			this.expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			this.expanded = false;
		}
	}

	systemValue = function(type : any, value : any){
		let feedMethodArr = [];
		if(type=="feedTypeSecondary"){
			if( this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList!=null &&  this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList.length>0)
				feedMethodArr = this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList;

			let flag = true;
			if(feedMethodArr.length == 0){
				feedMethodArr.push(value);
			}
			else{
				for(let i=0;i< feedMethodArr.length;i++){
					if(value == feedMethodArr[i]){
						feedMethodArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					feedMethodArr.push(value);
				}
			}

			this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList = feedMethodArr ;
			//set up selected feed method...

			this.feedTempObject.feedTypeSecondarySelectedText = "";
			for(let index=0;index<this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList.length;index++){

				for(let indexDrop=0;indexDrop<this.dropdownData.feedType.length;indexDrop++){
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList[index]==this.dropdownData.feedType[indexDrop].key){
						if(this.feedTempObject.feedTypeSecondarySelectedText==''){
							this.feedTempObject.feedTypeSecondarySelectedText = this.dropdownData.feedType[indexDrop].value;
						}else{
							this.feedTempObject.feedTypeSecondarySelectedText = this.feedTempObject.feedTypeSecondarySelectedText + ", " + this.dropdownData.feedType[indexDrop].value;
						}
					}
				}
			}
		}else if(type=="feedType"){
			if( this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList!=null &&  this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList.length>0)
				feedMethodArr = this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList;

			let flag = true;
			if(feedMethodArr.length == 0){
				feedMethodArr.push(value);
			}
			else{
				for(let i=0;i< feedMethodArr.length;i++){
					if(value == feedMethodArr[i]){
						feedMethodArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					feedMethodArr.push(value);
				}
			}
			this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList = feedMethodArr ;
			//set up selected feed method...

			this.feedTempObject.feedTypeSelectedText = "";
			for(let index=0;index<this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList.length;index++){

				for(let indexDrop=0;indexDrop<this.dropdownData.feedType.length;indexDrop++){
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList[index]==this.dropdownData.feedType[indexDrop].key){
						if(this.feedTempObject.feedTypeSelectedText==''){
							this.feedTempObject.feedTypeSelectedText = this.dropdownData.feedType[indexDrop].value;
						}else{
							this.feedTempObject.feedTypeSelectedText = this.feedTempObject.feedTypeSelectedText + ", " + this.dropdownData.feedType[indexDrop].value;
						}
					}
				}
			}
		}else if(type=='normalFeed'){
			if( this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList!=null &&  this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.length>0)
				feedMethodArr = this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList;

			let flag = true;
			if(feedMethodArr.length == 0){
				feedMethodArr.push(value);
			} else {
				for(let i=0;i< feedMethodArr.length;i++){
					if(value == feedMethodArr[i]){
						feedMethodArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					feedMethodArr.push(value);
				}
			}

			this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList = feedMethodArr ;
			//set up selected feed method...

			this.feedTempObject.feedMethodSelectedText = "";
			if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.length>0){
				for(let index=0;index<this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.length;index++){
					for(let indexDrop=0;indexDrop<this.dropdownData.feedMethod.length;indexDrop++){
						if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList[index]==this.dropdownData.feedMethod[indexDrop].key){
							if(this.feedTempObject.feedMethodSelectedText==''){
								this.feedTempObject.feedMethodSelectedText = this.dropdownData.feedMethod[indexDrop].value;
							}else{
								this.feedTempObject.feedMethodSelectedText = this.feedTempObject.feedMethodSelectedText + ", " + this.dropdownData.feedMethod[indexDrop].value;
							}
						}
					}
				}

				for(let indexDrop=0;indexDrop<this.dropdownData.feedMethod.length;indexDrop++){
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.includes('METHOD03') || this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.includes('METHOD02') ||
							this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.includes('METHOD04')){
						if(this.dropdownData.feedMethod[indexDrop].key!='METHOD03' && this.dropdownData.feedMethod[indexDrop].key!='METHOD02'
							&& this.dropdownData.feedMethod[indexDrop].key!='METHOD04'){
							this.dropdownData.feedMethod[indexDrop].isDisabled = true;
						}else{
							this.dropdownData.feedMethod[indexDrop].isDisabled = false;
						}
					}else{
						if(this.dropdownData.feedMethod[indexDrop].key=='METHOD03' || this.dropdownData.feedMethod[indexDrop].key=='METHOD02'
							|| this.dropdownData.feedMethod[indexDrop].key=='METHOD04'){
							this.dropdownData.feedMethod[indexDrop].isDisabled = true;
						}else{
							this.dropdownData.feedMethod[indexDrop].isDisabled = false;
						}
					}

				}
			}else{
				for(let indexDrop=0;indexDrop<this.dropdownData.feedMethod.length;indexDrop++){
					this.dropdownData.feedMethod[indexDrop].isDisabled = false;
				}
			}
		}else if(type=="fluidType"){
			if( this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList!=null &&  this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList.length>0)
				feedMethodArr = this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList;

			let flag = true;
			if(feedMethodArr.length == 0){
				feedMethodArr.push(value);
			}
			else{
				for(let i=0;i< feedMethodArr.length;i++){
					if(value == feedMethodArr[i]){
						feedMethodArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
          feedMethodArr.push(value);
				}
			}
			this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList = feedMethodArr ;
			//set up selected feed method...
      this.selectedFluidType = [];
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType = "";
			for(let index=0;index<this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList.length;index++){

				for(let indexDrop=0;indexDrop<this.dropdownData.fluidType.length;indexDrop++){
					if(this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList[index]==this.dropdownData.fluidType[indexDrop].key){
            this.selectedFluidType.push(this.dropdownData.fluidType[indexDrop].value);
            if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType==''){
							this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType = this.dropdownData.fluidType[indexDrop].value;
						}else{
							this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType = this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType + "," + this.dropdownData.fluidType[indexDrop].value;
						}
					}
				}
			}

      this.calculateCommonData(null);
		}else if(type=='bolus'){

			if( this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList!=null &&  this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList.length>0)
				feedMethodArr = this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList;

			let flag = true;
			if(feedMethodArr.length == 0){
				feedMethodArr.push(value);
			}
			else{
				for(let i=0;i< feedMethodArr.length;i++){
					if(value == feedMethodArr[i]){
						feedMethodArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					feedMethodArr.push(value);
				}
			}

			this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList = feedMethodArr ;
			this.feedTempObject.bolusSelectedText = "";
			for(let index=0;index<this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList.length;index++){

				for(let indexDrop=0;indexDrop<this.dropdownData.bolusMethod.length;indexDrop++){
					if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList[index]==this.dropdownData.bolusMethod[indexDrop].key){
						if(this.feedTempObject.bolusSelectedText==''){
							this.feedTempObject.bolusSelectedText = this.dropdownData.bolusMethod[indexDrop].value;
						}else{
							this.feedTempObject.bolusSelectedText = this.feedTempObject.bolusSelectedText + ", " + this.dropdownData.bolusMethod[indexDrop].value;
						}
					}
				}
			}
		}
	}

	calculateCommonData(unitType) {

    // if(this.tpnFeed.currentFeedInfo.babyFeed.feedduration != null && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != undefined){
    //   if(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration == null || this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration == undefined)
    //     this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration = this.tpnFeed.currentFeedInfo.babyFeed.feedduration;
    //   if(this.tpnFeed.currentFeedInfo.babyFeed.ironDuration == null && this.tpnFeed.currentFeedInfo.babyFeed.ironDuration == undefined)
    //     this.tpnFeed.currentFeedInfo.babyFeed.ironDuration = this.tpnFeed.currentFeedInfo.babyFeed.feedduration;
    //   if(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration == null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration == undefined)
    //     this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration = this.tpnFeed.currentFeedInfo.babyFeed.feedduration;
    //   if(this.tpnFeed.currentFeedInfo.babyFeed.otherDuration == null && this.tpnFeed.currentFeedInfo.babyFeed.otherDuration == undefined)
    //     this.tpnFeed.currentFeedInfo.babyFeed.otherDuration = this.tpnFeed.currentFeedInfo.babyFeed.feedduration;
    // }
		if((this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay!=null) && (this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay*1)!=0){
			this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = (this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1) * this.durationOffset;
      this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalIntake, 2);
      if(this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed*1>0){
				// this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = this.tpnFeed.currentFeedInfo.babyFeed.totalIntake*1-
				// this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed*1;
				// this.feedTempObject.pastBolusText = "Past bolus feed "+this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed+" ml/day is" +
				// " deducted from the total intake";
			}else{
				this.feedTempObject.pastBolusText = "";
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.isenternalgiven){
				this.calculateEnteral(unitType);
			}else {
				this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume =null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven){
				this.calculateParenteral(unitType);
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal = null;
				this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =null;
				this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = null;
				this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed =null;
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
				this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
				this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume =null;
        this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume =null;
        this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume =null;
			}
			this.currentCalculatorData();

      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherFeed))
					this.tpnFeed.currentFeedInfo.babyFeed.otherFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.otherFeed*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed))
				this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironFeed))
				this.tpnFeed.currentFeedInfo.babyFeed.ironFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.ironFeed*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed))
				this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed*100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed))
				this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed*100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.feedvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.feedvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.feedvolume*1)/1;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalIntake))
				this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalIntake*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal* 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal* 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.ironTotal * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal* 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherTotal))
  			this.tpnFeed.currentFeedInfo.babyFeed.otherTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.otherTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.hco3Total))
				this.tpnFeed.currentFeedInfo.babyFeed.hco3Total = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.hco3Total * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal))
				this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume))
				this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume * 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume))
				this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume * 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume))
        this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume * 100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday))
				this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate))
				this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration))
				this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = Math.round( this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration* 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume * 100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.girvalue))
				this.tpnFeed.currentFeedInfo.babyFeed.girvalue = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.girvalue*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed))
				this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed*100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate))
  			this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate*100)/100;
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume))
				this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume);
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume))
				this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume*100)/100;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume);
			if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume))
				this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*100)/100;

		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = null;
      this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = null;
			this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
			this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
			this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids = null;
			this.tpnFeed.currentFeedInfo.babyFeed.remainingfluid = null;
			this.tpnFeed.currentFeedInfo.babyFeed.remainingfluidMlkg = null;
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseConc = null;
			this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = null;
			this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
			this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday =null;
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = null;
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume =null;
			this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal = null;
			this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =null;
			this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
      this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;

		}

		//just for temporary basic........
		if(this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.bolusvolume!=''){
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = (this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1) * this.durationOffset;
      this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed, 2);
      if(this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != null && this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != 0 && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion != null && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion == true) {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = (this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed * 60) / this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime;
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate, 2);
      } else {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
      }

			/*if(this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed==null || this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed==''){
				this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed = this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed = this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed*1  + 1*this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
			}*/
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = null;
      this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
		}//just for temporary basic........

		//this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay * 100)/100;
	}

	currentCalculatorData() {
		if(this.tpnFeed.currentFeedInfo.babyFeed.isAdditiveInENCalculation){
			this.tpnFeed.currentFeedInfo.babyFeed.totalenteralvolume =
				(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay*1+this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume*1);
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.totalenteralvolume =
				(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay*1);
		}

		//calculate P/E for EN and PN and All..........................

		let ENvolumeTotal  = this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay;

		this.feedTempObject.enCurrentCalculator = {
				"Energy" : 0,
				"Protein" :0,
				"Fat" : 0,
				"Vitamina" : 0,
				"Vitamind" : 0,
				"Calcium" : 0,
				"Phosphorus" : 0,
				"Iron" : 0
		};

		if(ENvolumeTotal!=null && ENvolumeTotal!='' && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous"){
			for(let indexOralFeedList=0; indexOralFeedList<this.tpnFeed.oralFeedList.length;indexOralFeedList++){
				for(let index=0;index<this.tpnFeed.feedCalulator.refNutritionInfo.length;index++){
					if(this.tpnFeed.oralFeedList[indexOralFeedList].feedtypeId==
						this.tpnFeed.feedCalulator.refNutritionInfo[index].feedtypeId){
						this.feedTempObject.enCurrentCalculator.Energy = this.feedTempObject.enCurrentCalculator.Energy*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].energy)/100;
						this.feedTempObject.enCurrentCalculator.Protein = this.feedTempObject.enCurrentCalculator.Protein*1 +
						((	this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].protein)/100;
						this.feedTempObject.enCurrentCalculator.Fat = this.feedTempObject.enCurrentCalculator.Fat*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].fat)/100;
						this.feedTempObject.enCurrentCalculator.Vitamina = this.feedTempObject.enCurrentCalculator.Vitamina*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].vitamina)/100;
						this.feedTempObject.enCurrentCalculator.Vitamind = this.feedTempObject.enCurrentCalculator.Vitamind*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].vitamind)/100;
						this.feedTempObject.enCurrentCalculator.Calcium = this.feedTempObject.enCurrentCalculator.Calcium*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].calcium)/100;
						this.feedTempObject.enCurrentCalculator.Phosphorus = this.feedTempObject.enCurrentCalculator.Phosphorus*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].phosphorus)/100;
						this.feedTempObject.enCurrentCalculator.Iron = this.feedTempObject.enCurrentCalculator.Iron*1 +
						((this.tpnFeed.oralFeedList[indexOralFeedList].feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration/
								this.tpnFeed.currentFeedInfo.babyFeed.feedduration)*
								this.tpnFeed.feedCalulator.refNutritionInfo[index].iron)/100;

					}
				}
			}
			this.feedTempObject.protienEnteral  = this.feedTempObject.enCurrentCalculator.Protein*4;

			this.tpnFeed.currentFeedInfo.babyFeed.enProteinEnergyRatio  = this.feedTempObject.protienEnteral/this.feedTempObject.enCurrentCalculator.Energy;

			this.tpnFeed.currentFeedInfo.babyFeed.enProteinIngmsEnergyRatio = (this.feedTempObject.enCurrentCalculator.Protein*100)/this.feedTempObject.enCurrentCalculator.Energy;
			//now c/l of enteral......
			this.feedTempObject.lipidOfEnteral = 10*this.feedTempObject.enCurrentCalculator.Fat;
			this.feedTempObject.carbohydratesOfEnteral = this.feedTempObject.enCurrentCalculator.Energy*1 - (this.feedTempObject.protienEnteral*1+this.feedTempObject.lipidOfEnteral*1);
			this.tpnFeed.currentFeedInfo.babyFeed.enCarbohydratesLipidsRatio = this.feedTempObject.carbohydratesOfEnteral/this.feedTempObject.lipidOfEnteral;



		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.enProteinEnergyRatio = null;
			this.tpnFeed.currentFeedInfo.babyFeed.enCarbohydratesLipidsRatio = null;
		}

		this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume =
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*1+this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume
			*1+this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal*1+this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal*1+this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed
			*1+this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume*1 + this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume*1 + this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume*1 + this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume*1;

		//parenteral calculator record....
		//calculate PN calculator.............

		this.feedTempObject.pnCurrentCalculator = {
				"aminoAcid" : 0,
				"lipid" :0,
				"gir" : 0
		};
		if(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc!=null && this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc!=''){
			this.feedTempObject.pnCurrentCalculator.aminoAcid = 4*this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
		}
		if(this.tpnFeed.currentFeedInfo.babyFeed.lipidConc!=null && this.tpnFeed.currentFeedInfo.babyFeed.lipidConc!=''){
			this.feedTempObject.pnCurrentCalculator.lipid =
				10*this.tpnFeed.currentFeedInfo.babyFeed.lipidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.girvalue!=null && this.tpnFeed.currentFeedInfo.babyFeed.girvalue!=''){
			this.feedTempObject.pnCurrentCalculator.gir =
				4.9*this.tpnFeed.currentFeedInfo.babyFeed.girvalue*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
		}

		if(this.feedTempObject.pnCurrentCalculator.aminoAcid*1>0 || this.feedTempObject.pnCurrentCalculator.lipid*1>0
				|| this.feedTempObject.pnCurrentCalculator.gir*1>0){
			this.tpnFeed.currentFeedInfo.babyFeed.pnProteinEnergyRatio =
				(this.feedTempObject.pnCurrentCalculator.aminoAcid*1)/(this.feedTempObject.pnCurrentCalculator.aminoAcid*1
						+this.feedTempObject.pnCurrentCalculator.lipid*1+this.feedTempObject.pnCurrentCalculator.gir*1);
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.pnProteinEnergyRatio = null;
		}

		if(this.feedTempObject.pnCurrentCalculator.aminoAcid*1>0 || this.feedTempObject.pnCurrentCalculator.aminoAcid*1>0
				|| this.feedTempObject.pnCurrentCalculator.aminoAcid*1>0){
			this.tpnFeed.currentFeedInfo.babyFeed.pnProteinIngmsEnergyRatio =
				(this.feedTempObject.pnCurrentCalculator.aminoAcid*100)/(this.feedTempObject.pnCurrentCalculator.aminoAcid*1
						+this.feedTempObject.pnCurrentCalculator.lipid*1+this.feedTempObject.pnCurrentCalculator.gir*1);
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.pnProteinIngmsEnergyRatio = null;
		}

		if(this.feedTempObject.pnCurrentCalculator.lipid*1>0){
			this.tpnFeed.currentFeedInfo.babyFeed.pnCarbohydratesLipidsRatio =
				(this.feedTempObject.pnCurrentCalculator.gir*1)/(this.feedTempObject.pnCurrentCalculator.lipid*1);
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.pnCarbohydratesLipidsRatio = null;
		}

		//calculate total
		let totalProtein  = this.feedTempObject.protienEnteral*1 +(this.feedTempObject.pnCurrentCalculator.aminoAcid*1);
		let totalEnergy = this.feedTempObject.enCurrentCalculator.Energy*1+
		(this.feedTempObject.pnCurrentCalculator.aminoAcid*1+this.feedTempObject.pnCurrentCalculator.lipid*1+this.feedTempObject.pnCurrentCalculator.gir*1);
		this.tpnFeed.currentFeedInfo.babyFeed.totalProteinEnergyRatio = totalProtein/totalEnergy;

		let totalProteinIngms = (this.tpnFeed.currentFeedInfo.babyFeed.enProteinIngmsEnergyRatio*1)+
		(this.tpnFeed.currentFeedInfo.babyFeed.pnProteinIngmsEnergyRatio*1);

		this.tpnFeed.currentFeedInfo.babyFeed.totalProteinEnergyRatio = totalProtein/totalEnergy;

		this.tpnFeed.currentFeedInfo.babyFeed.totalProteinIngmsEnergyRatio = totalProteinIngms*100/totalEnergy;

		let totalCarbohydrates = this.feedTempObject.carbohydratesOfEnteral*1+(this.feedTempObject.pnCurrentCalculator.gir*1);
		let totalLipid = this.feedTempObject.lipidOfEnteral*1 + (this.feedTempObject.pnCurrentCalculator.lipid*1);
		this.tpnFeed.currentFeedInfo.babyFeed.totalCarbohydratesLipidsRatio = totalCarbohydrates/totalLipid;
	}

	calculateEnteral(unitType) {
		if(this.tpnFeed.currentFeedInfo.babyFeed.feedduration!=null && this.tpnFeed.currentFeedInfo.babyFeed.feedduration!='' && this.tpnFeed.currentFeedInfo.babyFeed.feedduration != "Continuous"){
			if(this.feedTempObject.isFeedVolumeEntered!=null){
				if(this.feedTempObject.isFeedVolumeEntered==false && this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay!=''){
					this.tpnFeed.currentFeedInfo.babyFeed.feedvolume = (this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay * (this.tpnFeed.currentFeedInfo.babyFeed.feedduration*1)/this.tpnFeed.currentFeedInfo.babyFeed.duration*1);

					if(this.tpnFeed.enFeedDetailList.length == 1) {
						this.tpnFeed.enFeedDetailList[0].no_of_feed = this.noOfEnFeed;
						this.tpnFeed.enFeedDetailList[0].feed_volume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.feedvolume, 1);
					}
				} else if(this.feedTempObject.isFeedVolumeEntered==true && this.tpnFeed.currentFeedInfo.babyFeed.feedvolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.feedvolume!='' && this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay)) {
					this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay = (this.tpnFeed.currentFeedInfo.babyFeed.feedvolume*this.tpnFeed.currentFeedInfo.babyFeed.duration) / (this.tpnFeed.currentFeedInfo.babyFeed.feedduration*1);
				}
			}
		}
		this.caclulateAdditiveData(unitType);
	}

	calculateParenteral(unitType) {
		//bolus feed calculation here...so its easy to calculate the  iv fluids...
		if(this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.bolusvolume!=''){
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = (this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1) * this.durationOffset;
      this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed, 2);
      if(this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != null && this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != 0 && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion != null && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion == true) {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = (this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed * 60) / this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime;
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate, 2);
      } else {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
      }
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = null;
      this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
		}

		this.caclulateAdditiveData('nonfeed'); //to calculate amino acid, lipid and additive...
		//this.calculateTotalMedicine();
		this.calculateIvFluid(unitType);
		this.calculateOsmolarity();
	}

  dextroseSelect() {
    // if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType != null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType != undefined){
    //   var dextrosefive = ["D5", "0.45 NS in D5(N/2)", "0.3 NS in D5(N/3)", "0.2 NS in D5(N/4)", "0.18 NS in D5(N/5)"];
    //   var dextroseten = ["D10", "0.45 NS in D10(N/2)", "0.3 NS in D10(N/3)", "0.2 NS in D10(N/4", "0.18 NS in D10(N/5)"];
    //   if(dextrosefive.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
    //     this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven = "fiveDextrose";
    //     this.calculateCommonData('nonfeed');
    //   }
    //   if(dextroseten.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
    //     this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven = "tenDextrose";
    //     this.calculateCommonData('nonfeed');
    //   }
    // }

  }

	calculateOsmolarity() {
		let volumeTotal  = 0;
		let osmolarityTotal = 0;
		if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow!=''){
			volumeTotal = this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume*1;
			if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow==5){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume*0.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow==10){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume*0.5;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow==25){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume*1.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow==50){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume*2.5;
			}
		}

    if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow!=''){
			volumeTotal = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume*1;
			if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow==5){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume*0.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow==10){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume*0.5;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow==25){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume*1.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow==50){
				osmolarityTotal = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume*2.5;
			}
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*1;
			if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh==5){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*0.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh==10){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*0.5;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh==25){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*1.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh==50){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume*2.5;
			}
		}

    if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume*1;
			if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh==5){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume*0.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh==10){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume*0.5;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh==25){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume*1.25;
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh==50){
				osmolarityTotal = osmolarityTotal + this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume*2.5;
			}
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal*1;
			if(this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume==0.15){
				osmolarityTotal = osmolarityTotal + (0.3*this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal);
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume==0.5){
				osmolarityTotal = osmolarityTotal + (1*this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal);
			}
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal*1;
			osmolarityTotal = osmolarityTotal + 1*this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal*1;
			osmolarityTotal = osmolarityTotal + 2.8*this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal*1;
			osmolarityTotal = osmolarityTotal + 0.68*this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.mviTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.mviTotal!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.mviTotal*1;
			osmolarityTotal = osmolarityTotal + 4*this.tpnFeed.currentFeedInfo.babyFeed.mviTotal;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal!=''){
			volumeTotal = volumeTotal + this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal*1;
			osmolarityTotal = osmolarityTotal + 4*this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal;
		}

    if(osmolarityTotal == null || volumeTotal == null || volumeTotal == 0) {
    this.tpnFeed.currentFeedInfo.babyFeed.osmolarity = 0;
    } else {
      this.tpnFeed.currentFeedInfo.babyFeed.osmolarity = (osmolarityTotal*1000)/(volumeTotal);
    }
	}

	calculateIvFluid(unitType) {
		//calculation of total iv fluids... its the total parenteral....
		let totalIvFluids = this.tpnFeed.currentFeedInfo.babyFeed.totalIntake;
		if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven!=null && this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven){

			if(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay!=null){
				totalIvFluids = totalIvFluids - this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay;
			}
			if(this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral!=null && this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral==true
					&&	this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume!=''){
				totalIvFluids = totalIvFluids - this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume;
			}
			this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids = totalIvFluids; //or total parenteral...in ml/day
			this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday = (this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids*1)/(this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1);
      this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday, 2);
      if(this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven == false){
        var totalVolume = 0;
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday)){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*1;
        }
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal) && this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate != true){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal*1;
        }
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume)){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume*1;
        }
        this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = (totalVolume*1)/(this.tpnFeed.currentFeedInfo.babyFeed.duration);
        this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate, 2);
      }
      else{
        this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = 0;
        //this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = (this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*1)/(24);
      }


      this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate, 2);

			//calculating total Dextrose Volume (ml/day) ...

			if((this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven!=null && this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven == false)){
				if(this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids!=''
					&& this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids*1>0){
					this.isDextroseCalcuated = true;
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids;

					if(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
						this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
					}

					if(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
						this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal;
					}

					if(this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
						this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal;
					}

					if(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
						this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume;
					}
					if(this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
						this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume;
					}
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume!=0){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
            this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume;
          }
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume!=0){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
            this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume;
          }

					// if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume!=''){
					// 	this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday-
					// 	this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume*1;
					// }
        }
        else{
    			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
    			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
      	}
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType!=''){
        if(this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids!='' && this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids*1>0){
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids;

          if(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed!=''){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
          }
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume!=''){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume;
          }
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume!=''){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume;
          }
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume!=0){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume;
          }
          if(this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume!=0){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume;
          }

          if(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal!=null){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal;
          }

          if(this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal!=''){
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume-
            this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal;
          }

          this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = (this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume*1) / (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1);

          //Code to calculate rate according to the volumes
          var totalVolume = 0;
          if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume)){
            totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume*1;
          }
          //We will not add amino volume to rate if the amino would go in a different dip
          if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal) && this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate != true){
            totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal*1;
          }
          if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume)){
            totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume*1;
          }
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = (totalVolume*1)/(this.tpnFeed.currentFeedInfo.babyFeed.duration);

          this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume, 2);
    			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate, 2);
          this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate;

    		}
        else{
    			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
    			this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
          this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
      	}
			}
      else {
        this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
        this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = null;
        this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = null;
        this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
      }

			if(totalIvFluids*1>=0){
				this.textInfo = "";
			}else{
				this.textInfo = "Total feed must be greater than sum of prescribed feeds";
			}
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.totalIvfluids =null;
			this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday = null;
			this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = null;
		}
		this.calculateGirInfo(unitType);
	}

  calculateAminoRate(){
    if(this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate == null || this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate == false){
      this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate = true;
    }
    else if(this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate != null && this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate == true){
      this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate = false;
    }
    this.calculateCommonData(null);
  }

	calculateGirInfo(unitType) {
		console.log("gir calculation....")
    if((this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven!=null && this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven == false)){

      //Code to calculate rate according to the volumes
      if(this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven == false){
        var totalVolume = 0;
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday)){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*1;
        }
        //We will not add amino volume to rate if the amino would go in a different dip
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal) && this.tpnFeed.currentFeedInfo.babyFeed.isAminoRate != true){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal*1;
        }
        if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume)){
          totalVolume = totalVolume + this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume*1;
        }
        this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = (totalVolume*1)/(this.tpnFeed.currentFeedInfo.babyFeed.duration);
      }
      else{
        //this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = (this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*1)/(24);
      }

      this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate, 2);
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate)){
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate;
      }

      //Dead Code
      if(this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven != null && this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven != undefined && this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven=='tenDextrose'){
				this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = 10;
				this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration*this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*0.007)
				/(1*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight);
			}else if(this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven != null && this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven != undefined && this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven=='fiveDextrose'){
				this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = 5;
				this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration*this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*0.007)
				/(1*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight);
			}

      //calculate dextrose concentration on the basis of GIR
			if(this.tpnFeed.currentFeedInfo.babyFeed.girvalue!=null && this.tpnFeed.currentFeedInfo.babyFeed.girvalue!=''
				&& this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = (this.tpnFeed.currentFeedInfo.babyFeed.girvalue*1*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight)/
				(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday*0.007);

				this.tpnFeed.currentFeedInfo.babyFeed.dextroseNetConcentration  = (this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday *
					this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration)/(this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday
					+this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal+this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume);

				let temp = Math.floor(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration);
				let multiplerMin = Math.floor(temp/5);
				let multiplerMax = multiplerMin+1;

        //Populating the lower and higher dextrose concentration types according to the dextrose concentration
        if(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration != null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday != null && unitType == 'dexPN'){
          if(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration >= 5 && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration <= 10){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow = 5;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh = 10;
          }
          else if(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration >= 10 && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration <= 25){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow = 10;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh = 25;
          }
          else if(this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration >= 25 && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration <= 50){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow = 25;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh = 50;
          }
        }

        //Lower and Upper Dextrose volume to be calculated according to their respective concentrations
				if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow!=''
				&& this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!=''
				&& this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration!=null && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration!=''){
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume =
          ((this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh - this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration)/
					(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh - this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow))*
  				this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday;
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume = this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday -
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume;

          //If any of the dextrose volume is negative then we will not consider the case
          if(this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume < 0 || this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume < 0){
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = null;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume = null;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow = null;
            this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh = null;
          }
				}else{
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = null;
					this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume =null;
				}
			}
    }
    else if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType!=''){
      if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume!=undefined && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume*1>0){
          var dextrosefive = ["D5", "0.45 NS in D5(N/2)", "0.3 NS in D5(N/3)", "0.2 NS in D5(N/4)", "0.18 NS in D5(N/5)","ISO P(N/6)"];
          var dextroseten = ["D10", "0.45 NS in D10(N/2)", "0.3 NS in D10(N/3)", "0.2 NS in D10(N/4)", "0.18 NS in D10(N/5)"];
          var dextrosetwentfive = ["D25"];
          var dextrosefifty = ["D50"];

          //If the user selects more than one fluid type
          if(this.selectedFluidType != null && this.selectedFluidType.length > 1){

            //calculate GIR value on the basis of dextrose concentration and vice versa
            if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration != null && this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate != null && this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate == false){
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration * this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = this.round(this.tpnFeed.currentFeedInfo.babyFeed.girvalue, 2);
            }else if(this.tpnFeed.currentFeedInfo.babyFeed.girvalue != null && this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate != null && this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate == true){
              this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = this.tpnFeed.currentFeedInfo.babyFeed.girvalue / (this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
              this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = this.round(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration, 2);
            }

            //getting both the fluid types
            this.fluidTypeAvailableList = this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType.split(",");
            var fluidTypeOne = this.fluidTypeAvailableList[0];
            var fluidTypeTwo = this.fluidTypeAvailableList[1];

            //Populating the lower and higher dextrose concentration types according to the dextrose concentration
            if(fluidTypeOne!=null && fluidTypeOne!='' && fluidTypeTwo!=null && fluidTypeTwo!=''
    				&& this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration!=''){
              var lowerDextroseConc = null;
              var higherDextroseConc = null;
              if(dextrosefive.includes(fluidTypeOne)){
                lowerDextroseConc = 5;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = fluidTypeOne;
              }
              else if(dextroseten.includes(fluidTypeOne)){
                lowerDextroseConc = 10;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = fluidTypeOne;
              }
              else if(dextrosetwentfive.includes(fluidTypeOne)){
                lowerDextroseConc = 25;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = fluidTypeOne;
              }
              else if(dextrosefifty.includes(fluidTypeOne)){
                lowerDextroseConc = 50;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = fluidTypeOne;
              }
              if(dextrosefifty.includes(fluidTypeTwo)){
                higherDextroseConc = 50;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = fluidTypeTwo;
              }
              else if(dextroseten.includes(fluidTypeTwo)){
                higherDextroseConc = 10;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = fluidTypeTwo;
              }
              else if(dextrosetwentfive.includes(fluidTypeTwo)){
                higherDextroseConc = 25;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = fluidTypeTwo;
              }
              else if(dextrosefive.includes(fluidTypeTwo)){
                higherDextroseConc = 5;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = fluidTypeTwo;
              }

              //Use case
              if(lowerDextroseConc != null && higherDextroseConc != null){

                if(higherDextroseConc < lowerDextroseConc){
                  var temp = lowerDextroseConc;
                  lowerDextroseConc = higherDextroseConc;
                  higherDextroseConc = temp;

                  var temp = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType;
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType;
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = temp;
                }

                //Lower and Upper Dextrose volume to be calculated according to their respective concentrations
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = lowerDextroseConc;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = higherDextroseConc;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume =
                ((this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh - this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration)/
                (this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh - this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow))*
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume;
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume -
                this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume;

                //If any of the dextrose volume is negative then we will not consider the case
                if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume < 0 || this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume < 0){
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
                  this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
                }
              }
            }else{
              if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume == null || this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration==null || this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration==''){
                this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
              }
            }
          }
          else{
            if(dextrosefive.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (5 * this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
            }
            if(dextroseten.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (10 * this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
            }
            if(dextrosetwentfive.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (25 * this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
            }
            if(dextrosefifty.includes(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType)){
              this.tpnFeed.currentFeedInfo.babyFeed.girvalue = (50 * this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume * 0.007);
            }
            this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate = false;
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
          	this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
          	this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
          	this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
            this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
          	this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
          }
      }
      else{
        this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
        this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
      }
    }

    else {
      this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
      this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = null;
      this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = null;
			this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = null;
			this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume = null;
		}
	}

	calculateTotalMedicine() {
		let totalMed = 0;
		if(!this.tpnFeed.currentFeedInfo.babyFeed.isIVMedcineGiven && this.tpnFeed.babyPrescriptionList != null && this.tpnFeed.babyPrescriptionList != undefined){
			for(let i=0; i< this.tpnFeed.babyPrescriptionList.length;i++){
				this.tpnFeed.babyPrescriptionList[i].dilusion=null;
			}
		}

		for(let i=0; i< this.tpnFeed.babyPrescriptionList.length;i++){
			if((this.tpnFeed.babyPrescriptionList[i].dilusion*1)!=0  && this.tpnFeed.babyPrescriptionList != null && this.tpnFeed.babyPrescriptionList != undefined){
				totalMed = (this.tpnFeed.babyPrescriptionList[i].dilusion*1)*(this.tpnFeed.babyPrescriptionList[i].frequencyInt*1)+ (totalMed*1);
			}
			this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume = totalMed;
		}
	}

  refreshDailyPN(){
    this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = null;
    this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven = null;
    this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isBolusGiven = null;
    this.tpnFeed.currentFeedInfo.babyFeed.bolusType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isInfusion = null;
    this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime = null;
    this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
    this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isParenteralDextrose = null;
    this.tpnFeed.currentFeedInfo.babyFeed.totalivfluidMlkgday =null;
    this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLowvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHighvolume =null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidConc =null;
    this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg = null;
    this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.potassiumVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calciumConcentrationType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calciumVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.phosphorousVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mviTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.hco3Volume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.hco3Total = null;
    this.tpnFeed.currentFeedInfo.babyFeed.magnesiumVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList = null;
    this.selectedFluidType = [];
    for(let indexDropDown=0; indexDropDown<this.dropdownData.fluidType.length;indexDropDown++){
      if(this.dropdownData.fluidType[indexDropDown]["flag"] != undefined){
        this.dropdownData.fluidType[indexDropDown].flag = false;
      }
    }

    this.calculateCommonData(null);
  }

  calculateDuration(){
    if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.duration) && this.tpnFeed.currentFeedInfo.babyFeed.duration < 72 && this.tpnFeed.currentFeedInfo.babyFeed.duration > 0){
      this.durationOffset = this.tpnFeed.currentFeedInfo.babyFeed.duration / 24;
      this.durationOffset = Math.floor(this.durationOffset);
      var offset = this.tpnFeed.currentFeedInfo.babyFeed.duration % 24;
      offset = offset / 24;
      this.durationOffset = this.durationOffset + offset;
      this.calculateCommonData(null);
    }
    else{
      this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = null;
    }
  }

  refreshReadymadeConc(){
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeTotalFluidVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidRate = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isIVFluidGiven = null;
    this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
    this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLow = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcLowvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHigh = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighType = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcHighvolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidConc =null;
    this.selectedFluidType = [];
    for(let indexDropDown=0; indexDropDown<this.dropdownData.fluidType.length;indexDropDown++){
      if(this.dropdownData.fluidType[indexDropDown]["flag"] != undefined){
        this.dropdownData.fluidType[indexDropDown].flag = false;
      }
    }

    this.calculateCommonData(null);
  }
  refreshParenteralConc(){
    this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday = null;
    this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isParenteralDextrose = null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc =null;
    this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal =null;
    this.tpnFeed.currentFeedInfo.babyFeed.lipidConc =null;
    this.calculateCommonData(null);
  }

  // openBolusModal = function(){
  //   $("#commentsOverlay").css("display", "none");
  //   $("#NursingVitalsPopup").toggleClass("showing");
  //   $('body').css('overflow', 'auto');
  // }
  // closeBolusModal = function(){
  //   console.log("closeBolusPopup closing");
  //   $("#commentsOverlay").css("display", "none");
  //   $('body').css('overflow', 'auto');
  //   $("#NursingVitalsPopup").toggleClass("showing");
  // }

  calculateAdditionalBolusData(){
    if(this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.bolusvolume!=''){
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = (this.tpnFeed.currentFeedInfo.babyFeed.bolusVolume*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1);
      this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed, 2);
      this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume = this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed*1;
      if(this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != null && this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime != 0 && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion != null && this.tpnFeed.currentFeedInfo.babyFeed.isInfusion == true) {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = (this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed * 60) / this.tpnFeed.currentFeedInfo.babyFeed.bolus_infusiontime;
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate, 2);
      } else {
        this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
      }
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed = null;
      this.tpnFeed.currentFeedInfo.babyFeed.bolus_rate = null;
		}
  }

  submitBolusModal = function(status){
    if(!this.isEmpty(this.tpnFeed.lastFeedInfo) && !this.isEmpty(this.tpnFeed.lastFeedInfo.totalfluidMlDay)){
      this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay = this.tpnFeed.lastFeedInfo.totalfluidMlDay;
      this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = (this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1);
      this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalIntake, 2);
    }
    this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume = null;
    if(status == "Yes"){
      var today = new Date();
      var diffinHour = (today.getTime() - this.tpnFeed.lastFeedInfo.creationtime)*1/ (1000 * 60 * 60);
      diffinHour = Math.round(diffinHour)
      var parenteralVolumePerHour = this.tpnFeed.lastFeedInfo.totalparenteralvolume / 24;
      this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume = (parenteralVolumePerHour * (24 - diffinHour)) + (this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed);
      this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = (this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume*1) / (24 - diffinHour);
      this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate, 2);
      this.additionalBolusYes = "The new fluid rate is " + this.tpnFeed.currentFeedInfo.babyFeed.ivfluidrate + " ml/hr";
    }
    if(status == "No"){
      this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume = this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume + this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
      this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay = this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay + this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
      this.additionalBolusNo = "The total volume is " + this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay + " ml";
    }
  }

  refreshEnteral(){
    this.tpnFeed.currentFeedInfo.babyFeed.isAdLibGiven = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedmethod_type = false;
    this.tpnFeed.currentFeedInfo.babyFeed.feedduration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedMethodOther = null;
    this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedduration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral = null;
    this.tpnFeed.currentFeedInfo.babyFeed.isAdditiveInENCalculation = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calBrand = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed = null;
    this.tpnFeed.currentFeedInfo.babyFeed.ironVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.ironBrand = null;
    this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.ironDuration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.ironFeed = null;
    this.tpnFeed.currentFeedInfo.babyFeed.multiVitaminBrand = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminDBrand = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mctVolume = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mctBrand = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mctDuration = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mctTotal = null;
    this.tpnFeed.currentFeedInfo.babyFeed.mctFeed = null;
    this.feedTempObject.feedMethodSelectedText = "";
    this.feedTempObject.feedTypeSelectedText = "";
    this.feedTempObject.feedTypeSecondarySelectedText = "";

    this.getDropdownData();

  }

  includeIVMed(){
    this.tpnFeed.currentFeedInfo.babyFeed.stopPN = null;
    this.tpnFeed.currentFeedInfo.babyFeed.stopAdditionalPN = null;
    if(this.tpnFeed.currentFeedInfo.isNewEntry == true){
      this.totalMedInfVolume = 0;
      this.medicineStr = null;
      if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven == true && this.tpnFeed.babyPrescriptionList != null && this.tpnFeed.babyPrescriptionList != undefined){
        for(let i=0; i< this.tpnFeed.babyPrescriptionList.length;i++){
    			if((this.tpnFeed.babyPrescriptionList[i].inf_volume)!=null){
    				this.totalMedInfVolume = (this.tpnFeed.babyPrescriptionList[i].inf_volume*1) + (this.totalMedInfVolume*1);
            if(this.medicineStr == null){
              this.medicineStr = this.tpnFeed.babyPrescriptionList[i].medicinename;
            }
            else{
              this.medicineStr = this.medicineStr + ", " + this.tpnFeed.babyPrescriptionList[i].medicinename;
            }
          }
    		}
        this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume = this.totalMedInfVolume;
      }
      else{
        this.totalMedInfVolume = 0;
        this.medicineStr = null;
        this.tpnFeed.currentFeedInfo.babyFeed.totalMedvolume = this.totalMedInfVolume;
      }
    }
  }

  includeBloodProduct(){
    this.totalBloodProductVolume = 0;
    if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven == true && this.tpnFeed.babyBloodProductList != null && this.tpnFeed.babyBloodProductList != undefined){
      for(let i=0; i< this.tpnFeed.babyBloodProductList.length;i++){
  			if((this.tpnFeed.babyBloodProductList[i].total_volume)!=null){
  				this.totalBloodProductVolume = (this.tpnFeed.babyBloodProductList[i].total_volume*1) + (this.totalBloodProductVolume*1);
  			}
  		}
      this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume = this.totalBloodProductVolume;
    }
    else{
      this.totalBloodProductVolume = 0;
      this.tpnFeed.currentFeedInfo.babyFeed.totalBloodProductVolume = this.totalBloodProductVolume;
    }
  }

  includeHeplock(){
    this.totalHeplockVolume = 0;
    if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven == true && this.tpnFeed.babyCentralLineList != null && this.tpnFeed.babyCentralLineList != undefined){
      for(let i=0; i< this.tpnFeed.babyCentralLineList.length;i++){
  			if((this.tpnFeed.babyCentralLineList[i].heparinTotalVolume)!=null){
  				this.totalHeplockVolume = (this.tpnFeed.babyCentralLineList[i].heparinTotalVolume*1) + (this.totalHeplockVolume*1);
  			}
  		}
      this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume = this.totalHeplockVolume;
    }
    else{
      this.totalHeplockVolume = 0;
      this.tpnFeed.currentFeedInfo.babyFeed.totalHeplockVolume = this.totalHeplockVolume;
    }
  }

	caclulateAdditiveData(unitType) {
		this.totalAdditiveDataEnteral = 0;
		this.totalAdditiveDataParenteral = 0;
    var calStrength = 0;
    var ironStrength = 0;


    for(var i=0; i < this.tpnFeed.addtivesbrandList.length; i++){
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calBrand) && this.tpnFeed.currentFeedInfo.babyFeed.calBrand == this.tpnFeed.addtivesbrandList[i].enaddtivesbrandid){
        calStrength = this.tpnFeed.addtivesbrandList[i].strength;
      }
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironBrand) && this.tpnFeed.currentFeedInfo.babyFeed.ironBrand == this.tpnFeed.addtivesbrandList[i].enaddtivesbrandid){
        ironStrength = this.tpnFeed.addtivesbrandList[i].strength;
      }

    }



    if(unitType == 'feed'){
       if(calStrength != 0 && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calBrand) && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed!='' && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration != undefined){

         this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal =
           (this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration;
         this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal, 2);
         this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume = (calStrength * this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal) / this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
         this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume, 2);

         this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal;
       }
       else{
         this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = null;
       }
       if(this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed!='' && this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration != undefined){
         this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal =
           (this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration;
         this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal, 2);
         this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal;
       }
       else{
         this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal = null;
       }

       // if(this.tpnFeed.currentFeedInfo.babyFeed.isStrengthOther != null && this.tpnFeed.currentFeedInfo.babyFeed.isStrengthOther){
       //
       //   if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.unitOther) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherStrength) && this.tpnFeed.currentFeedInfo.babyFeed.otherFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.otherFeed!='' && this.tpnFeed.currentFeedInfo.babyFeed.otherDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.otherDuration != undefined){
       //
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherTotal =
       //       (this.tpnFeed.currentFeedInfo.babyFeed.otherFeed * 24) / this.tpnFeed.currentFeedInfo.babyFeed.otherDuration;
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherTotal = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.otherTotal*100)/100;
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherVolume = (this.tpnFeed.currentFeedInfo.babyFeed.otherStrength * this.tpnFeed.currentFeedInfo.babyFeed.otherTotal) / this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.otherVolume*100)/100;
       //
       //     this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.otherTotal;
       //   }
       //   else{
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherTotal = null;
       //   }
       //   this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.totalAdditiveDataEnteral*1;
       // }
       // else if(this.tpnFeed.currentFeedInfo.babyFeed.isStrengthOther != null && !this.tpnFeed.currentFeedInfo.babyFeed.isStrengthOther){
       //
       //   if(this.tpnFeed.currentFeedInfo.babyFeed.otherDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.otherDuration != undefined && this.tpnFeed.currentFeedInfo.babyFeed.otherFeed !=null && this.tpnFeed.currentFeedInfo.babyFeed.otherFeed !=''){
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherTotal =
       //       (this.tpnFeed.currentFeedInfo.babyFeed.otherFeed * 24) / this.tpnFeed.currentFeedInfo.babyFeed.otherDuration;
       //     this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.otherTotal;
       //   }
       //   else{
       //     this.tpnFeed.currentFeedInfo.babyFeed.otherTotal = null;
       //   }
       //   this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.totalAdditiveDataEnteral*1;
       // }
       // else{
       //
       // // }
       // if(this.tpnFeed.currentFeedInfo.babyFeed.mctFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.mctFeed!='' && this.tpnFeed.currentFeedInfo.babyFeed.mctDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.mctDuration!= undefined){
       //
       //   this.tpnFeed.currentFeedInfo.babyFeed.mctTotal =
       //     (this.tpnFeed.currentFeedInfo.babyFeed.mctFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.mctDuration;
       //   this.tpnFeed.currentFeedInfo.babyFeed.mctTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.mctTotal, 2);
       //   this.tpnFeed.currentFeedInfo.babyFeed.mctVolume = (16 * this.tpnFeed.currentFeedInfo.babyFeed.mctTotal);
       //   this.tpnFeed.currentFeedInfo.babyFeed.mctVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.mctVolume, 2);
       //
       //   this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.mctTotal;
       // }
       // else{
       //   this.tpnFeed.currentFeedInfo.babyFeed.mctTotal = null;
       // }
       if(ironStrength != 0 && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironBrand) && this.tpnFeed.currentFeedInfo.babyFeed.ironFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.ironFeed!='' && this.tpnFeed.currentFeedInfo.babyFeed.ironDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.ironDuration!= undefined){

         this.tpnFeed.currentFeedInfo.babyFeed.ironTotal =
           (this.tpnFeed.currentFeedInfo.babyFeed.ironFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.ironDuration;
         this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironTotal, 2);
         this.tpnFeed.currentFeedInfo.babyFeed.ironVolume = (ironStrength * this.tpnFeed.currentFeedInfo.babyFeed.ironTotal) / this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
         this.tpnFeed.currentFeedInfo.babyFeed.ironVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironVolume, 2);

         this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.ironTotal;
       }
       else{
         this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = null;
       }
       if(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration != null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration != undefined && this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed!=null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed!=''){

         this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal =
           (this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration;
        this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal, 2);
         this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal;
       }
       else{
         this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal = null;
       }
       this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.totalAdditiveDataEnteral*1;
       if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume)){
         this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume, 2);
       }
     }
     else if(unitType == 'nonfeed'){

       if(this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral!=null && this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral){

         if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calBrand) && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume!='' && calStrength != 0){
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal =
             (this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight) / calStrength;
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal, 2);
           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal;

           if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration)){
             this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed = (this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal * this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration) / this.tpnFeed.currentFeedInfo.babyFeed.duration;
             this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed, 2);
           }
         }
         else if(calStrength != 0 && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calBrand) && this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed)){
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal =
             (this.tpnFeed.currentFeedInfo.babyFeed.calsyrupFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.calsyrupDuration;
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal, 2);
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume = (calStrength * this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal) / this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
           this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.calsyrupVolume, 2);

           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal;
         }
         else{
             this.tpnFeed.currentFeedInfo.babyFeed.calsyrupTotal = null;
         }

         if(this.tpnFeed.currentFeedInfo.babyFeed.mctVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.mctVolume!=''){
           this.tpnFeed.currentFeedInfo.babyFeed.mctTotal =
             (this.tpnFeed.currentFeedInfo.babyFeed.mctVolume / 16);
           this.tpnFeed.currentFeedInfo.babyFeed.mctTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.mctTotal, 2);
           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.mctTotal;

           if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.mctDuration)){
             this.tpnFeed.currentFeedInfo.babyFeed.mctFeed = (this.tpnFeed.currentFeedInfo.babyFeed.mctTotal * this.tpnFeed.currentFeedInfo.babyFeed.mctDuration) / this.tpnFeed.currentFeedInfo.babyFeed.duration;
             this.tpnFeed.currentFeedInfo.babyFeed.mctFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.mctFeed, 2);
           }
         }
         else{
             this.tpnFeed.currentFeedInfo.babyFeed.mctTotal = null;
         }

         if(this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal!=''){
           if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration)){
             this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed = (this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal * this.tpnFeed.currentFeedInfo.babyFeed.vitaminDDuration) / this.tpnFeed.currentFeedInfo.babyFeed.duration;
             this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.vitaminDFeed, 2);
           }
           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal;
         }
         else{
             this.tpnFeed.currentFeedInfo.babyFeed.vitamindTotal = null;
         }

         // if(this.tpnFeed.currentFeedInfo.babyFeed.otherVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.otherVolume!=''){
         //   this.tpnFeed.currentFeedInfo.babyFeed.otherTotal =
         //     this.tpnFeed.currentFeedInfo.babyFeed.otherVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
         //   this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.otherTotal;
         //
         //   if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherDuration)){
         //     this.tpnFeed.currentFeedInfo.babyFeed.otherFeed = (this.tpnFeed.currentFeedInfo.babyFeed.otherTotal * this.tpnFeed.currentFeedInfo.babyFeed.otherDuration) / 24;
         //   }
         // }else if(this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherVolume) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherDuration) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.otherFeed)){
         //   this.tpnFeed.currentFeedInfo.babyFeed.otherVolume = (this.tpnFeed.currentFeedInfo.babyFeed.otherFeed * 24) / (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * this.tpnFeed.currentFeedInfo.babyFeed.otherDuration);
         //   this.tpnFeed.currentFeedInfo.babyFeed.otherVolume = Math.round(this.tpnFeed.currentFeedInfo.babyFeed.otherVolume*100)/100;
         //   this.tpnFeed.currentFeedInfo.babyFeed.otherTotal =
         //     this.tpnFeed.currentFeedInfo.babyFeed.otherVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
         //   this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.otherTotal;
         //  }
         // else{
         //   this.tpnFeed.currentFeedInfo.babyFeed.otherTotal = null;
         // }

         if(ironStrength != 0 && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironBrand) && this.tpnFeed.currentFeedInfo.babyFeed.ironVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.ironVolume!=''){

           this.tpnFeed.currentFeedInfo.babyFeed.ironTotal =
             (this.tpnFeed.currentFeedInfo.babyFeed.ironVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight) / ironStrength;
           this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironTotal, 2);
           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.ironTotal;

           if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironDuration)){
             this.tpnFeed.currentFeedInfo.babyFeed.ironFeed = (this.tpnFeed.currentFeedInfo.babyFeed.ironTotal * this.tpnFeed.currentFeedInfo.babyFeed.ironDuration) / this.tpnFeed.currentFeedInfo.babyFeed.duration;
             this.tpnFeed.currentFeedInfo.babyFeed.ironFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironFeed, 2);
           }
         }else if(ironStrength != 0 && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironBrand) && this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironVolume) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironDuration) && !this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.ironFeed)){

           this.tpnFeed.currentFeedInfo.babyFeed.ironTotal =
             (this.tpnFeed.currentFeedInfo.babyFeed.ironFeed * this.tpnFeed.currentFeedInfo.babyFeed.duration) / this.tpnFeed.currentFeedInfo.babyFeed.ironDuration;
           this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironTotal, 2);
           this.tpnFeed.currentFeedInfo.babyFeed.ironVolume = (ironStrength * this.tpnFeed.currentFeedInfo.babyFeed.ironTotal) / this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
           this.tpnFeed.currentFeedInfo.babyFeed.ironVolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.ironVolume, 2);

           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.ironTotal;
          }
          else{
           this.tpnFeed.currentFeedInfo.babyFeed.ironTotal = null;
         }

         if(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal!=''){
           if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration)){
             this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed = (this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal * this.tpnFeed.currentFeedInfo.babyFeed.vitaminaDuration) / this.tpnFeed.currentFeedInfo.babyFeed.duration;
             this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed = this.round(this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed, 2);
           }
           this.totalAdditiveDataEnteral = this.totalAdditiveDataEnteral + this.tpnFeed.currentFeedInfo.babyFeed.vitaminaTotal;
         }else{
             this.tpnFeed.currentFeedInfo.babyFeed.vitaminaFeed = null;
         }
         this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.totalAdditiveDataEnteral*1;
         if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume)){
           this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume, 2);
         }
       }else{
         this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = null;
       }
     }
		//enteral end here.............


		if(this.tpnFeed.currentFeedInfo.babyFeed.additiveparenteral!=null && this.tpnFeed.currentFeedInfo.babyFeed.additiveparenteral){
			//parentreral start..............
			if(this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume!=''){
				if(this.feedTempObject.isSodiumEdit==undefined || this.feedTempObject.isSodiumEdit==null){
					this.feedTempObject.isSodiumEdit = true;
          if(this.tpnFeed.currentFeedInfo.isNewEntry == true){
					     this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg = 3;
          }
				}
				if(this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg!=null && this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg!=''){
					this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal = (this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg*1*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight) * this.durationOffset/
					this.tpnFeed.currentFeedInfo.babyFeed.sodiumVolume;

					this.feedTempObject.sodiumMlkgDay = this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal/this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
					this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal;
				}else{
					this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal = null;
					this.feedTempObject.sodiumMlkgDay = null;
				}

			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.sodiumTotal = null;
				this.feedTempObject.sodiumMlkgDay = null;
				this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg = null;
				this.feedTempObject.isSodiumEdit = null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.calciumVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.calciumVolume!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal =
					this.tpnFeed.currentFeedInfo.babyFeed.calciumVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * this.durationOffset;
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal;
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.calciumTotal = null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.phosphorousVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.phosphorousVolume!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal =
					this.tpnFeed.currentFeedInfo.babyFeed.phosphorousVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * this.durationOffset;
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal;
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.phosphorousTotal = null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.potassiumVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.potassiumVolume!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal =
					((this.tpnFeed.currentFeedInfo.babyFeed.potassiumVolume*1)* this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1*this.durationOffset)/2;
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal;

			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.potassiumTotal = null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.mviTotal!=null && this.tpnFeed.currentFeedInfo.babyFeed.mviTotal!=''){
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.mviTotal*1;
			}



			if(this.tpnFeed.currentFeedInfo.babyFeed.hco3Volume!=null && this.tpnFeed.currentFeedInfo.babyFeed.hco3Volume!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.hco3Total =
					this.tpnFeed.currentFeedInfo.babyFeed.hco3Volume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * this.durationOffset;
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.hco3Total*1;
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.hco3Total = null;
			}

			if(this.tpnFeed.currentFeedInfo.babyFeed.magnesiumVolume!=null && this.tpnFeed.currentFeedInfo.babyFeed.magnesiumVolume!=''){
				this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal =
					this.tpnFeed.currentFeedInfo.babyFeed.magnesiumVolume * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * this.durationOffset;
				this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal;
			}else{
				this.tpnFeed.currentFeedInfo.babyFeed.magnesiumTotal = null;
			}
			this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = this.totalAdditiveDataParenteral*1;
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralAdditivevolume = null;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc!=null && this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc!=''){
			this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal =
				this.tpnFeed.currentFeedInfo.babyFeed.aminoacidConc * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * 10 * this.durationOffset;
			//this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral + this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal;
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.aminoacidTotal = null;
		}

		if(this.tpnFeed.currentFeedInfo.babyFeed.lipidConc!=null && this.tpnFeed.currentFeedInfo.babyFeed.lipidConc!=''){
			this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal =
				this.tpnFeed.currentFeedInfo.babyFeed.lipidConc * this.tpnFeed.currentFeedInfo.babyFeed.workingWeight * 5 * this.durationOffset;
			//this.totalAdditiveDataParenteral = this.totalAdditiveDataParenteral +this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal*1 ;
		}else{
			this.tpnFeed.currentFeedInfo.babyFeed.lipidTotal = null;
		}
	}

	pastCalculatorData(lastFeed : BabyfeedDetail) {
		this.feedTempObject.pastCal = new PastFeedTemp();
		//calculate here data other than deficit...

    this.feedTempObject.feedmethodList = lastFeed.feedmethodList;
		this.feedTempObject.totalIntake = lastFeed.totalIntake;
		this.feedTempObject.totalfeedMlDay = lastFeed.totalfeedMlDay;
		this.feedTempObject.totalenteraAdditivelvolume = lastFeed.totalenteraAdditivelvolume;
		this.feedTempObject.aminoacidTotal = lastFeed.aminoacidTotal;
		this.feedTempObject.bolusTotalFeed = lastFeed.bolusTotalFeed;
		this.feedTempObject.lipidTotal = lastFeed.lipidTotal;
		this.feedTempObject.totalparenteralAdditivevolume = lastFeed.totalparenteralAdditivevolume;
		this.feedTempObject.dextroseVolumemlperday = lastFeed.dextroseVolumemlperday;
		this.feedTempObject.totalMedvolume = lastFeed.totalMedvolume;
		this.feedTempObject.dextroseNetConcentration = lastFeed.dextroseNetConcentration;
		this.feedTempObject.osmolarity = lastFeed.osmolarity;


		this.feedTempObject.pastEnteralTotalFeed =
			(this.feedTempObject.totalfeedMlDay*1+this.feedTempObject.totalenteraAdditivelvolume*1);

		this.feedTempObject.pastParenteralTotalFeed =
			(lastFeed.totalparenteralvolume);

		//this part is for t/e ,c/l......

		if(this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake!=null){
			this.feedTempObject.pastCal.protienEnteral  = this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Protein*4;

			this.feedTempObject.pastCal.enProteinEnergyRatio = this.feedTempObject.pastCal.protienEnteral/this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Energy;

			this.feedTempObject.pastCal.enProteinIngmsEnergyRatio = (this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Protein*100)/this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Energy;
		}else{
			this.feedTempObject.pastCal.enProteinIngmsEnergyRatio = null;
		}
		let tempDenominator = 0;
		if(this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake!=null){
			this.feedTempObject.pastCal.lipidOfEnteral = 10*this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Fat;
			this.feedTempObject.pastCal.carbohydratesOfEnteral = this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Energy*1 - (this.feedTempObject.pastCal.protienEnteral*1+this.feedTempObject.pastCal.lipidOfEnteral*1);
			tempDenominator = Math.round(this.feedTempObject.pastCal.carbohydratesOfEnteral*100/
					(this.feedTempObject.pastCal.lipidOfEnteral*1+this.feedTempObject.pastCal.carbohydratesOfEnteral*1));
			this.feedTempObject.pastCal.enCarbohydratesLipidsRatio = tempDenominator+"/"+(100-tempDenominator*1);
		}else{
			this.feedTempObject.pastCal.enCarbohydratesLipidsRatio = null;
		}


		//paretenteral part t/e c/l etc...
		if(this.tpnFeed.feedCalulator.deficitCalToShow.parentalIntake!=null){
			this.feedTempObject.pastCal.pnProteinEnergyRatio = (lastFeed.aminoacidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*4)/
			(this.tpnFeed.feedCalulator.deficitCalToShow.parentalIntake.Energy*1);

			this.feedTempObject.pastCal.pnProteinIngmsEnergyRatio = (lastFeed.aminoacidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*100)/
			(this.tpnFeed.feedCalulator.deficitCalToShow.parentalIntake.Energy*1);
		}
		tempDenominator  = Math.round((100*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*lastFeed.girvalue*4.9)/(this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*lastFeed.lipidConc*10
				+this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*lastFeed.girvalue*4.9));
		this.feedTempObject.pastCal.pnCarbohydratesLipidsRatio = tempDenominator+"/"+(100-tempDenominator*1);

		this.feedTempObject.pastCal.totalProteinEnergyRatio = (this.feedTempObject.pastCal.protienEnteral*1+ lastFeed.aminoacidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*4)/
		(this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Energy*1+this.tpnFeed.feedCalulator.deficitCalToShow.parentalIntake.Energy*1);

		this.feedTempObject.pastCal.totalProteinIngmsEnergyRatio = (this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Protein*100+ lastFeed.aminoacidConc*this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*100)/
		(this.tpnFeed.feedCalulator.deficitCalToShow.enteralIntake.Energy*1+this.tpnFeed.feedCalulator.deficitCalToShow.parentalIntake.Energy*1);


		tempDenominator = Math.round((this.feedTempObject.pastCal.carbohydratesOfEnteral*1+
				this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*lastFeed.girvalue*4.9)/(this.feedTempObject.pastCal.lipidOfEnteral*1+
						this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*lastFeed.lipidConc*10));

    if(tempDenominator == Infinity){
      this.feedTempObject.pastCal.totalCarbohydratesLipidsRatio = "-/-";
    }
    else{
      this.feedTempObject.pastCal.totalCarbohydratesLipidsRatio = tempDenominator +" : "+ 1;
    }
	}

	setTodayFeedDateTime() {
		// setting today date via JS to manual assessment date,hour,min,meridiem
		this.tpnFeed.currentFeedInfo.feedGivenDateTime = new Date();
	}

	populatePastFeed(babyFeedId : any) {
		this.isDisabled=false;
		this.tpnFeed.currentFeedInfo.isNewEntry = false;
		this.isEnternalVisible = true;
		this.isBloodVisible = false;
		this.isParenteralVisible = false;
    this.isStopVisible = false;
    var workingWeight = this.tpnFeed.currentFeedInfo.babyFeed.workingWeight;
		this.tpnFeed.currentFeedInfo.babyFeed = new BabyfeedDetail();
		if(!this.isEmpty(this.tpnFeed.babyFeedList)){
			for(let i=0; i<this.tpnFeed.babyFeedList.length;i++){
				if(this.tpnFeed.babyFeedList[i].babyfeedid==babyFeedId){
          this.tpnFeed.currentFeedInfo.babyFeed = Object.assign({}, this.tpnFeed.babyFeedList[i]);

				//	this.tpnFeed.currentFeedInfo.babyFeed = this.tpnFeed.babyFeedList[i];

          this.durationOffset = this.tpnFeed.currentFeedInfo.babyFeed.duration / 24;
          this.durationOffset = Math.floor(this.durationOffset);
          var offset = this.tpnFeed.currentFeedInfo.babyFeed.duration % 24;
          offset = offset / 24;
          this.durationOffset = this.durationOffset + offset;

          //Populate the feed calculator according to the babyfeed selected
          if(this.tpnFeed.calculatorList != null && this.tpnFeed.calculatorList.length > i){
            this.tpnFeed.feedCalulator.deficitCalToShow = this.tpnFeed.calculatorList[i].lastDeficitCal;
            this.pastCalculatorData(this.tpnFeed.babyFeedList[i]);
          }

					let tempObject = this.getNicuTimeStampFromTimeStampFormat(new Date(this.tpnFeed.currentFeedInfo.babyFeed.creationtime));
					this.tpnFeed.currentFeedInfo.feedGivenDateTime = tempObject.date;
					this.tpnFeed.currentFeedInfo.feedGivenHrs = tempObject.hours;
					this.tpnFeed.currentFeedInfo.feedGivenMinutes = tempObject.minutes;
					this.tpnFeed.currentFeedInfo.feedGivenMeridian = tempObject.meridian;

					//populate bolus.....
					if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethod!=null &&
							this.tpnFeed.currentFeedInfo.babyFeed.bolusMethod!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList =
							this.tpnFeed.currentFeedInfo.babyFeed.bolusMethod.replace("[","").replace("]","").split(",");
						this.feedTempObject.bolusSelectedText = "";
						if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList!=null){
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList.length;index++){
								for(let indexDropDown=0; indexDropDown<this.dropdownData.bolusMethod.length;indexDropDown++){
									if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList[index].replace(" ","")==
										this.dropdownData.bolusMethod[indexDropDown].key){
										this.dropdownData.bolusMethod[indexDropDown].flag = true;
										if(this.feedTempObject.bolusSelectedText==''){
											this.feedTempObject.bolusSelectedText = this.dropdownData.bolusMethod[indexDropDown].value;
										}else{
											this.feedTempObject.bolusSelectedText = this.feedTempObject.bolusSelectedText + "," + this.dropdownData.bolusMethod[indexDropDown].value;
										}
									}
								}
							}
						}
					}

					//populate feed type.....
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedtype!=null &&
							this.tpnFeed.currentFeedInfo.babyFeed.feedtype!=''){
						this.feedTempObject.feedTypeSelectedText = "";
						this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList =
							this.tpnFeed.currentFeedInfo.babyFeed.feedtype.replace("[","").replace("]","")
							.replace(" ","").split(",");
						if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList!=null){
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList.length;index++){
								for(let indexDropDown=0; indexDropDown<this.dropdownData.feedType.length;indexDropDown++){
									if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList[index].replace(" ","")==
										this.dropdownData.feedType[indexDropDown].key){
										this.dropdownData.feedType[indexDropDown].flag = true;
										if(this.feedTempObject.feedTypeSelectedText==''){
											this.feedTempObject.feedTypeSelectedText = this.dropdownData.feedType[indexDropDown].value;
										}else{
											this.feedTempObject.feedTypeSelectedText = this.feedTempObject.feedTypeSelectedText + "," + this.dropdownData.feedType[indexDropDown].value;
										}

									}
								}
							}

							//populate oral feed.....
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList.length;index++){
								for(let indexPast =0; indexPast<this.tpnFeed.pastOralFeedList.length;indexPast++){
									let feedTypeId = this.tpnFeed.currentFeedInfo.babyFeed.feedTypeList[index].replace(" ","");
									if(feedTypeId==	this.tpnFeed.pastOralFeedList[indexPast].feedtypeId){

										if(feedTypeId=="TYPE01"){
											this.feedTempObject.ebm = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE02"){
											this.feedTempObject.ebmandlactodexhmf = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE03"){
											this.feedTempObject.ebmandsimilarhmf = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE05"){
											this.feedTempObject.lactodex = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE06"){
											this.feedTempObject.dexolac = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}

										if(feedTypeId=="TYPE07"){
											this.feedTempObject.enfamil = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE08"){
											this.feedTempObject.visyneralz = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
										if(feedTypeId=="TYPE09"){
											this.feedTempObject.tonoform = this.tpnFeed.pastOralFeedList[indexPast].feedvolume;
										}
									}
								}
							}
						}
					}

					//populate feed type.....
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondary!=null && this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondary!=''){
						this.feedTempObject.feedTypeSecondarySelectedText = "";
						this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList =
							this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondary.replace("[","").replace("]","")
							.replace(" ","").split(",");
						if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList!=null){
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList.length;index++){
								for(let indexDropDown=0; indexDropDown<this.dropdownData.feedType.length;indexDropDown++){
									if(this.tpnFeed.currentFeedInfo.babyFeed.feedTypeSecondaryList[index].replace(" ","")==
										this.dropdownData.feedType[indexDropDown].key){
										this.dropdownData.feedType[indexDropDown].flag = true;
										if(this.feedTempObject.feedTypeSecondarySelectedText==''){
											this.feedTempObject.feedTypeSecondarySelectedText = this.dropdownData.feedType[indexDropDown].value;
										}else{
											this.feedTempObject.feedTypeSecondarySelectedText += "," + this.dropdownData.feedType[indexDropDown].value;
										}
									}
								}
							}
						}
					}

          //populate fluid type.....
					if(this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList!=null && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList!='' && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList.includes("[") && this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList.includes("]")){
						this.feedTempObject.feedTypeSecondarySelectedText = "";
						this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList =
							this.tpnFeed.currentFeedInfo.babyFeed.readymadeFluidTypeList.replace("[","").replace("]","")
							.replace(" ","").split(",");
						if(this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList!=null){
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList.length;index++){
								for(let indexDropDown=0; indexDropDown<this.dropdownData.fluidType.length;indexDropDown++){
									if(this.tpnFeed.currentFeedInfo.babyFeed.fluidTypeList[index].replace(" ","")==
										this.dropdownData.fluidType[indexDropDown].key){
                    this.selectedFluidType.push(this.dropdownData.fluidType[indexDropDown].value);
										this.dropdownData.fluidType[indexDropDown].flag = true;
									}
								}
							}
						}
					}

					this.feedTempObject.feedMethodSelectedText = "";
					if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList!=null){
						for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.length;index++){
							for(let indexDropDown=0; indexDropDown<this.dropdownData.feedMethod.length;indexDropDown++){
								if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList[index]==
									this.dropdownData.feedMethod[indexDropDown].key){
									this.dropdownData.feedMethod[indexDropDown].flag = true;
									if(this.feedTempObject.feedMethodSelectedText==''){
										this.feedTempObject.feedMethodSelectedText = this.dropdownData.feedMethod[indexDropDown].value;
									}else{
										this.feedTempObject.feedMethodSelectedText = this.feedTempObject.feedMethodSelectedText + "," + this.dropdownData.feedMethod[indexDropDown].value;
									}
								}
							}
						}
					}

					this.feedTempObject.breastFeedingSelectedText = "";
					if(this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeed!=null &&
							this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeed!=''){
						this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeedList =
							this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeed.replace("[","").replace("]","").split(",");
						if(this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeedList!=null){
							for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeedList.length;index++){
								for(let indexDropDown=0; indexDropDown<this.dropdownData.libBreastFeeding.length;indexDropDown++){
									if(this.tpnFeed.currentFeedInfo.babyFeed.libBreastFeedList[index].replace(" ","")==
										this.dropdownData.libBreastFeeding[indexDropDown].key){
										this.dropdownData.libBreastFeeding[indexDropDown].flag = true;
										if(this.feedTempObject.breastFeedingSelectedText==''){
											this.feedTempObject.breastFeedingSelectedText = this.dropdownData.libBreastFeeding[indexDropDown].value;
										}else{
											this.feedTempObject.breastFeedingSelectedText = this.feedTempObject.breastFeedingSelectedText + "," + this.dropdownData.libBreastFeeding[indexDropDown].value;
										}

									}
								}
							}
						}
					}

					if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList!=null){
						for(let index=0; index<this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList.length;index++){
							for(let indexDropDown=0; indexDropDown<this.dropdownData.feedMethod.length;indexDropDown++){
								if(this.tpnFeed.currentFeedInfo.babyFeed.bolusMethodList[index]==
									this.dropdownData.feedMethod[indexDropDown].key){
									this.dropdownData.feedMethod[indexDropDown].flag = true;
								}
							}
						}
					}
				}
			}

			if(!this.isEmpty(this.tpnFeed.pastEnFeedDetailList)){
				this.tpnFeed.enFeedDetailList = [];
				for(let i=0; i<this.tpnFeed.pastEnFeedDetailList.length;i++){
					if(this.tpnFeed.pastEnFeedDetailList[i].babyfeedid==babyFeedId){
						let copy = JSON.parse(JSON.stringify(this.tpnFeed.pastEnFeedDetailList[i]));
						this.tpnFeed.enFeedDetailList.push(copy);
					}
				}

        if(this.tpnFeed.enFeedDetailList.length == 0) {
          let copy = JSON.parse(JSON.stringify(this.tpnFeed.emptyEnFeedDetailObj));
          this.tpnFeed.enFeedDetailList.push(copy);
        }
			} else {
				this.tpnFeed.enFeedDetailList = [];
        let copy = JSON.parse(JSON.stringify(this.tpnFeed.emptyEnFeedDetailObj));
        this.tpnFeed.enFeedDetailList.push(copy);
			}

			// if(!this.isEmpty(this.tpnFeed.ivMedList)){
			// 	this.tpnFeed.babyPrescriptionList = [];
			// 	for(let i=0; i<this.tpnFeed.ivMedList.length;i++){
      //
			// 		if(this.tpnFeed.ivMedList[i].babyfeedid==babyFeedId){
			// 			let copy = JSON.parse(JSON.stringify(this.tpnFeed.babyPrescEmptyObj));
      //
			// 			copy.medicinename = this.tpnFeed.ivMedList[i].medicineName;
			// 			copy.dose = this.tpnFeed.ivMedList[i].medicineDose;
			// 			copy.frequency = this.tpnFeed.ivMedList[i].medicineFrequency;
			// 			//getting integer part of the frequency.....
			// 			for(let indexDrop=0;indexDrop<this.dropdownData.frequencyMed.length;indexDrop++){
			// 				if(copy.frequency==this.dropdownData.frequencyMed[indexDrop].freqvalue){
			// 					copy.frequencyInt = this.dropdownData.frequencyMed[indexDrop].id;
			// 				}
			// 			}
      //
			// 			copy.dilusion = this.tpnFeed.ivMedList[i].dillution;
			// 			this.tpnFeed.babyPrescriptionList.push(copy);
			// 		}
			// 	}
			// }else{
			// 	this.tpnFeed.babyPrescriptionList = [];
			// }

			if(!this.isEmpty(this.tpnFeed.babyBloodProductList)){
				let isFoundInList = false;
				for(let i=0; i<this.tpnFeed.babyBloodProductList.length;i++){
					if(this.tpnFeed.babyBloodProductList[i].babyfeedid==babyFeedId){
						this.tpnFeed.currentFeedInfo.babyBloodProduct = this.tpnFeed.babyBloodProductList[i];
						isFoundInList = true;
					}
				}

				if(!isFoundInList){
					this.tpnFeed.currentFeedInfo.babyBloodProduct = new BloodProduct();
				}
			}
		}
    else{
      this.tpnFeed.currentFeedInfo.babyFeed.workingWeight = workingWeight;
      this.tpnFeed.currentFeedInfo.babyFeed.duration = 24;
      this.tpnFeed.currentFeedInfo.babyFeed.isBolusGiven = false;
      this.tpnFeed.currentFeedInfo.babyFeed.isReadymadeSolutionGiven = true;
      this.tpnFeed.currentFeedInfo.babyFeed.totalenteraAdditivelvolume = 0
      this.tpnFeed.currentFeedInfo.babyFeed.pastBolusTotalFeed = 0;
      this.tpnFeed.currentFeedInfo.babyFeed.feedmethod_type = false;
      this.tpnFeed.currentFeedInfo.babyFeed.isFeedGiven = true;
    }
	}

  refreshIsGirCalculate(){
    this.tpnFeed.currentFeedInfo.babyFeed.girvalue = null;
    this.tpnFeed.currentFeedInfo.babyFeed.readymadeDextroseConcentration = null;
    this.calculateCommonData('nonfeed');
  }

  addEnfeedRow(index : number) {
    var item = this.tpnFeed.enFeedDetailList[index];
    let copy = JSON.parse(JSON.stringify(this.tpnFeed.emptyEnFeedDetailObj));
    this.tpnFeed.enFeedDetailList.push(copy);
    this.validateEnFeed();
  }

  removeEnfeedRow(index : number) {
    this.tpnFeed.enFeedDetailList.splice(index,1);
    this.validateEnFeed();
  }

  validateEnFeed() {
    var totalNoFeed = 0;
    var totalFeedVol = 0;

    if(this.tpnFeed.currentFeedInfo.babyFeed.feedduration != null && this.tpnFeed.currentFeedInfo.babyFeed.feedduration == "Continuous"){
      this.enFeedDisableSubmit = false;
    }else{
      for(var i = 0; i < this.tpnFeed.enFeedDetailList.length; i++) {
        var item = this.tpnFeed.enFeedDetailList[i];
        if(item.no_of_feed == null || item.no_of_feed == 0 || item.feed_volume == null || item.feed_volume == 0) {
          this.enFeedDisableSubmit = true;
          return;
        }
        totalNoFeed += item.no_of_feed;
        totalFeedVol += (item.feed_volume * item.no_of_feed);
      }

      if(totalNoFeed != this.noOfEnFeed || this.round(totalFeedVol, 0) != this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalfeedMlDay, 0)) {
        this.enFeedDisableSubmit = true;
      } else {
        this.enFeedDisableSubmit = false;
      }
    }
  }

	ParenteralSectionVisible() {
		console.log("in function desired");
		this.isParenteralVisible = true;
		if(this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven==null){
			this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven = true;
		}
		this.isEnternalVisible = false;
		this.isBloodVisible = false;
    this.includeIVMed();
    this.includeBloodProduct();
    this.includeHeplock();
		this.calculateCommonData('nonfeed');
	}

  stopSectionVisible() {
    console.log("in function desired");
    this.isStopVisible = true;
  }

	EnternalSectionVisible(){
		console.log("in function desired");
		this.isEnternalVisible = true;
		if(this.tpnFeed.currentFeedInfo.babyFeed.isenternalgiven==null){
			this.tpnFeed.currentFeedInfo.babyFeed.isenternalgiven =true;
		}
		this.isParenteralVisible = false;
    this.isStopVisible = false;
		this.isBloodVisible = false;
		this.calculateCommonData('nonfeed');
	}
	BloodSectionVisible(){
		this.isBloodVisible = true;
		if(this.tpnFeed.currentFeedInfo.babyBloodProduct.isBloodGiven == null){
			this.tpnFeed.currentFeedInfo.babyBloodProduct.isBloodGiven = true;
		}
		this.isEnternalVisible = false;
    this.calculateCommonData('nonfeed');
		this.isParenteralVisible = false;

	}
	rightSectionFunction(value : string){
		if(value=="current"){
			this.isrightSectionHeader = false;
			this.tpnFeed.feedCalulator.deficitCalToShow = this.tpnFeed.feedCalulator.currentDeficitCal;
		}
		if(value=="last"){
			this.isrightSectionHeader = true;
			this.tpnFeed.feedCalulator.deficitCalToShow = this.tpnFeed.feedCalulator.lastDeficitCal;
		}
	}

	rightSectionParenternalFunction(value : string){
		if(value=="current"){
			this.isrightParenternalSectionHeader = false;
		}
		if(value=="last"){
			this.isrightParenternalSectionHeader = true;
		}
	}

	viewCalculationInDatils() {
		console.log("View Calculations initiated");
		$("#viewCalculationOverlay").css("display", "block");
		$("#viewCalculationPopup").addClass("showing");
	};

	closeModalviewCalculation() {
		console.log("View Calculations closing");
		$("#viewCalculationOverlay").css("display", "none");
		$("#viewCalculationPopup").toggleClass("showing");
	};

//		below code is for OK cancel popup
	showOkPopUp(eventName : string ,parameterName : string) {
		this.feedTempObject.textMessage = parameterName
		this.feedTempObject.popupEventName = eventName;
		this.feedTempObject.popupParamName = parameterName;
		$("#OkCancelPopUp").addClass("showing");
		$("#scoresOverlay").addClass("show");
	};

	closeOkPopUp(operation : any){
		$("#OkCancelPopUp").removeClass("showing");
		$("#scoresOverlay").removeClass("show");
		this.calculateCommonData('nonfeed');
	};

	showWarningPopUp() {
		$("#OkWarningPopUp").addClass("showing");
		$("#warningOverlay").addClass("show");
	};

	closeWarningPopUp(){
		$("#OkWarningPopUp").removeClass("showing");
		$("#warningOverlay").removeClass("show");
	};

	submitPopup(operation : any) {
		this.closeOkPopUp(operation);
		//deduction for sodium.....
		if(this.feedTempObject.popupEventName=='parenteral'){
			if(this.feedTempObject.popupParamName=='phosphorus'){
				this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg = (this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg*1 - (this.tpnFeed.currentFeedInfo.babyFeed.phosphorousVolume*2)).toFixed(2);
			}else if(this.feedTempObject.popupParamName=='NaHCO3'){
				this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg = (this.tpnFeed.currentFeedInfo.babyFeed.sodiumMeg - (this.tpnFeed.currentFeedInfo.babyFeed.hco3Volume*0.9)).toFixed(2);
			}
			this.calculateCommonData('nonfeed');
		}
	};

	getDropdownData() {
		 try{
	            this.http.request(this.apiData.getFeedDropdownData).subscribe((res: Response) => {
	            	this.dropdownData = res.json();
	       });
	     }catch(e){
	            console.log("Exception in http service:"+e);
	     };
	};

	getTpnFeedDetails() {
		this.feedTempObject = new FeedTemp();
		// if(this.discharge_summary_type == 'basic'){
		// 	this.tempVarTohideShowIvFluid = false;
		// }else{
		// 	this.tempVarTohideShowIvFluid = true;
		// }

		try{
        this.http.request(this.apiData.getTpnFeed + this.uhid + "/" + this.loggedUser + "/").subscribe((res: Response) => {
      	this.tpnFeed = res.json();
      	this.tpnFeed.feedCalulator.currentDeficitCal = new CaclulatorDeficitPOJO();
      	console.log(this.tpnFeed);
        this.populatePastFeed(this.tpnFeed.currentFeedInfo.babyFeed.babyfeedid);
				this.setTodayFeedDateTime();
				//fix pnLipid to 20%
				this.tpnFeed.currentFeedInfo.babyFeed.lipidPnvolume = "20%";
        this.tpnFeed.currentFeedInfo.babyFeed.isRevise = false;
				//fix pnAminoAcid to 10%
				this.tpnFeed.currentFeedInfo.babyFeed.aminoacidPnvolume = "10%";
        this.tpnFeed.currentFeedInfo.babyFeed.isGirCalculate = false;

        this.tpnFeed.currentFeedInfo.babyFeed.additionalDuration = 24;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalTotalVolume = null;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalPNType = null;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalVolume = null;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalKCL = null;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalRate = null;

				//removing flag that tells its coming from the hypoglycemia event...
				var isComingFromMetabolicTreatment = JSON.parse(localStorage.getItem('isComingFromMetabolicTreatment'));
				if(isComingFromMetabolicTreatment != null){
					localStorage.removeItem("isComingFromMetabolicTreatment");
					localStorage.setItem('redirectBackToAssessment',JSON.stringify("hypoglycemia"));
				}else{
					localStorage.removeItem("redirectBackToAssessment");
				}

				var isComingFromCNSTreatment = JSON.parse(localStorage.getItem('isComingFromCNSTreatment'));
				if(isComingFromCNSTreatment != null){
					localStorage.removeItem("isComingFromCNSTreatment");
					localStorage.setItem('redirectBackToCNSAssessment',JSON.stringify("yes"));
				}else{
					localStorage.removeItem("redirectBackToCNSAssessment");
				}

				//deficit to show default will be current....
				this.tpnFeed.feedCalulator.deficitCalToShow = this.tpnFeed.feedCalulator.lastDeficitCal;
				if(this.tpnFeed.babyFeedList!=null && this.tpnFeed.babyFeedList.length>0){
					this.pastCalculatorData(this.tpnFeed.babyFeedList[0]);
				}

				//this.tpnFeed.currentFeedInfo.babyFeed.potassiumVolume = 2;
        this.tpnFeed.currentFeedInfo.babyFeed.ismakeDailyPN = "dailyPN";
				this.caclulateAdditiveData('nonfeed');
        if(!this.isEmpty(this.tpnFeed.lastFeedInfo) && !this.isEmpty(this.tpnFeed.lastFeedInfo.totalfluidMlDay)){
          this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay = this.tpnFeed.lastFeedInfo.totalfluidMlDay;
          var offset = this.tpnFeed.currentFeedInfo.babyFeed.duration / 24;
          offset = Math.floor(offset);
          var offsetMod = this.tpnFeed.currentFeedInfo.babyFeed.duration % 24;
          offsetMod = offsetMod / 24;
          offset = offset + offsetMod;
          this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = (this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay*1) * (this.tpnFeed.currentFeedInfo.babyFeed.workingWeight*1) * offset*1;
          this.tpnFeed.currentFeedInfo.babyFeed.totalIntake = this.round(this.tpnFeed.currentFeedInfo.babyFeed.totalIntake, 2);
        }
        this.tpnFeed.currentFeedInfo.isNewEntry = true;
			});
		}catch(e){
	        console.log("Exception in http service:"+e);
	    };
	}

  calculateAdditionalRate(){
    if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.additionalDuration)){
      var rate = 0;
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.additionalVolume)){
        rate = this.tpnFeed.currentFeedInfo.babyFeed.additionalVolume;
      }
      if(!this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.additionalKCL)){
        rate += this.tpnFeed.currentFeedInfo.babyFeed.additionalKCL;
      }
      if(rate != 0){
        this.tpnFeed.currentFeedInfo.babyFeed.additionalRate = rate / this.tpnFeed.currentFeedInfo.babyFeed.additionalDuration;
        this.tpnFeed.currentFeedInfo.babyFeed.additionalRate = this.round(this.tpnFeed.currentFeedInfo.babyFeed.additionalRate, 2);
        if(rate > this.tpnFeed.currentFeedInfo.babyFeed.additionalTotalVolume){
          this.tpnFeed.currentFeedInfo.babyFeed.additionalKCL = null;
          this.tpnFeed.currentFeedInfo.babyFeed.additionalVolume = null;
          this.tpnFeed.currentFeedInfo.babyFeed.additionalRate = null;
        }
      }

    }
    else{
      this.tpnFeed.currentFeedInfo.babyFeed.additionalRate = null;
    }

  }

	saveTpnFeedDetails(procedure : string) {

    // if(this.tpnFeed.currentFeedInfo.babyFeed.ismakeDailyPN!=null && this.tpnFeed.currentFeedInfo.babyFeed.ismakeDailyPN ==false){
    //   this.additionalBolusNo = "The total volume is " + this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay + this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
    //   this.additionalBolusYes = "The new fluid rate is " + this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay + this.tpnFeed.currentFeedInfo.babyFeed.bolusTotalFeed;
    //   this.openBolusModal();
    // }
    if(this.isEmpty(this.tpnFeed.currentFeedInfo.babyFeed.duration) || this.tpnFeed.currentFeedInfo.babyFeed.duration == 0 || this.tpnFeed.currentFeedInfo.babyFeed.duration > 72){
      return;
    }
		if(this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay == null || this.tpnFeed.currentFeedInfo.babyFeed.totalfluidMlDay == '') {
      if(!(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList != null && this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.length == 1
        && this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.includes('METHOD03') && this.tpnFeed.currentFeedInfo.babyFeed.isAdLibGiven != null
        && (this.tpnFeed.currentFeedInfo.babyFeed.isAdLibGiven || this.tpnFeed.currentFeedInfo.babyFeed.feedduration != null) &&
        !(this.tpnFeed.currentFeedInfo.babyFeed.additiveenteral == true && this.tpnFeed.currentFeedInfo.babyFeed.isAdditiveInENCalculation == true))) {
          return;
        }
		} else if(this.round((this.tpnFeed.currentFeedInfo.babyFeed.totalIntake - (this.tpnFeed.currentFeedInfo.babyFeed.totalenteralvolume * 1 + this.tpnFeed.currentFeedInfo.babyFeed.totalparenteralvolume * 1)), 0) < 0) {
			this.showWarningPopUp();
			return;
		}

    if(this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList != null && this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList != undefined && this.tpnFeed.currentFeedInfo.babyFeed.feedmethodList.includes('METHOD01')) {
      this.tpnFeed.currentFeedInfo.babyFeed.isAdLibGiven = false;
    }

    if((this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday!='' && this.tpnFeed.currentFeedInfo.babyFeed.dextroseVolumemlperday<0) || (this.enFeedDisableSubmit == true) ||
    (this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!=null && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh!='' && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcHigh < this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration) ||
    (this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow!=null && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration!=null && this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration!=''  && this.tpnFeed.currentFeedInfo.babyFeed.dextroseConcLow > this.tpnFeed.currentFeedInfo.babyFeed.currentDextroseConcentration)){
      return;
    }

    if(this.tpnFeed.currentFeedInfo.babyFeed.stopPN != null && this.tpnFeed.currentFeedInfo.babyFeed.stopPN == true){
      this.refreshDailyPN();
      this.tpnFeed.currentFeedInfo.babyFeed.isparentalgiven = false;
    }

    this.isLoaderVisible = true;
    this.loaderContent = 'Saving...';
		try{
	        this.http.post(this.apiData.addTpnFeed + this.uhid + "/" + this.loggedUser + "/",
	        this.tpnFeed).subscribe(res => {
					this.vanishSubmitResponseVariable = true;
          this.isLoaderVisible = false;
          this.loaderContent = 'Saving...';
					this.responseType = res.json().type;
					this.responseMessage = res.json().message;
					setTimeout(() => {this.vanishSubmitResponseVariable = false;}, 3000);

					if(res.json().type=="success"){
						let isComingFromMetabolicTreatment = JSON.parse(localStorage.getItem('redirectBackToAssessment'));
						let isComingFromCNSTreatment = JSON.parse(localStorage.getItem('redirectBackToCNSAssessment'));

						if(isComingFromMetabolicTreatment!=null){
							localStorage.setItem('infoFeedNutrition',JSON.stringify(this.tpnFeed.currentFeedInfo.babyFeed));
							localStorage.setItem('comingBackFromFeed',JSON.stringify(true));
							localStorage.removeItem("redirectBackToAssessment");
							this.router.navigateByUrl('/metabolic/assessment-sheet-metabolic');
						} else if(isComingFromCNSTreatment!=null){
							localStorage.setItem('infoFeedNutrition',JSON.stringify(this.tpnFeed.currentFeedInfo.babyFeed));
							localStorage.setItem('comingBackFromFeed',JSON.stringify(true));
							localStorage.removeItem("redirectBackToCNSAssessment");
							this.router.navigateByUrl('/cns/assessment-sheet-cns');
						} else {
							this.getDropdownData();
							this.getTpnFeedDetails();
						}
					}
	        	},
	            err => {
	              console.log("Error occured.")
	            });
		}
		catch(e){
			console.log("Exception in http service:"+e);
		};
	}


	isEmpty(object : any) : boolean {
		if (object == null || object == '' || object == 'null' || object.length == 0) {
			return true;
		}
		else {
			return false;
		}
	};

	getTimeStampFromNicuTimeStampFormat(date : Date, hrs : any, min : any, sec : any, meridian : any) {
		let timeStamp = new Date();
		if (!this.isEmpty(date)) {
			timeStamp = date;
		}
		if (!this.isEmpty(hrs)) {
			if (meridian == "PM") {
				if (hrs * 1 < 12) {
					hrs = hrs * 1 + 12;
				}
			}
			else {
				if (hrs * 1 == 12) {
					hrs = 0;
				}
			}
			timeStamp.setHours(hrs * 1);
		}
		if (!this.isEmpty(min)) {
			timeStamp.setMinutes(min);
		}
		if (!this.isEmpty(sec)) {
			timeStamp.setSeconds(sec);
		}
		return timeStamp;
	};

	getNicuTimeStampFromTimeStampFormat(timeStamp : Date) {
		let hours : any = timeStamp.getHours();
		let minutes : any = timeStamp.getMinutes();
		let seconds : any = timeStamp.getSeconds();
		let ampm = hours >= 12 ? 'PM' : 'AM';
		//console.log("hours "+hours+ "minutes "+minutes+"ampm "+ampm);
		hours = (hours * 1) % 12;
		hours = (hours * 1) ? (hours * 1) : 12; // the hour '0' should be '12'
		minutes = (minutes * 1) < 10 ? '0' + minutes : minutes;
		seconds = (seconds * 1) < 10 ? '0' + seconds : seconds;
		let strTime = hours + ':' + minutes + ':' + ampm;
		//console.log("hours **********"+hours);
		if ((hours * 1) < 10) {
			hours = '0' + hours;
		}
		let tempObj = {
			hours : hours + '',
			minutes : minutes + '',
			seconds : seconds + '',
			meridian : ampm,
			date : timeStamp
		};
		return tempObj;
	}

  round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

	redirectToPrint(){
    if(this.tpnFeed.currentFeedInfo.babyFeed != null){
      localStorage.setItem('printPNOrder', JSON.stringify(this.tpnFeed.currentFeedInfo.babyFeed));
      localStorage.setItem('feedCalculator', JSON.stringify(this.tpnFeed.feedCalulator));
      localStorage.setItem('proteinEnergyRatio', JSON.stringify(this.feedTempObject.pastCal.totalProteinEnergyRatio));
      localStorage.setItem('totalCarbohydratesLipidsRatio', JSON.stringify(this.feedTempObject.pastCal.totalCarbohydratesLipidsRatio));
      localStorage.setItem('durationOffset', JSON.stringify(this.durationOffset));

	    this.router.navigateByUrl('/feed/nutrition-print');
    }
}

	ngOnInit() {
  	console.log("init");
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

    var isComingFromPrint = JSON.parse(localStorage.getItem('printPNOrder'));
    if(isComingFromPrint!=null){
      localStorage.removeItem('printPNOrder');
      localStorage.removeItem('feedCalculator');
      localStorage.removeItem('proteinEnergyRatio');
      localStorage.removeItem('totalCarbohydratesLipidsRatio');
      localStorage.removeItem('durationOffset');

    }
  	this.getDropdownData();
  	this.getTpnFeedDetails();
  }

}
