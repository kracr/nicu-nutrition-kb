import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProceduresModel } from './proceduresModel';
import { ExchangeTransfusion } from './exchangeTransfusion';
import { RefTestslist } from '../common-model/refTestslist';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { PeripheralCannula } from './peripheralCannula';
import { CentralLine } from './centralLine';
import { LumbarPuncture } from './lumbarPuncture';
import { Vtap } from './vtap';
import { EtIntubation } from './etIntubation';
import { etIntubationReason } from './etIntubationReasonList';
import { ProcedureChesttube } from './procedureChesttube';
import { ProcedureOther } from './procedureOther';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'procedures',
  templateUrl: './procedures.component.html',
  styleUrls: ['./procedures.component.css']
})
export class ProceduresComponent implements OnInit {
	isLoaderVisible : boolean;
	loaderContent : string;
	showTransfusion : boolean;
	apiData : APIurl;
	uhid : string;
	DateOfTransfusion : any;
	dateListOfTransfusion : Array<any>;
	loggedUser : string;
	http: Http;
	router : Router;
	hours : Array<number>;
	minutes : Array<number>;
	hoursList : Array<string>;
	minutesList : Array<string>;
	procedureWhichTab : string;
	investOrderNotOrdered : Array<any>;
	investOrderSelected : Array<any>;
	proceduresModel : ProceduresModel;
	exchangeTransfusionSortedList : Array<ExchangeTransfusion>;
	printExchangeTransfusionList : Array<ExchangeTransfusion>;
	bagImageObj:any = {};
	currentExchangeTotalVolumeIn : any;
	pastExchangeTotalVolumeIn : any;
	currentExchangeTotalVolumeOut : any;
	pastExchangeTotalVolumeOut : any;
	imageResponse : Object;
	selectedFile:File = null;
	listTestsByCategory : Array<RefTestslist>;
	orderSelectedText : string;
	leftSelectedTubeValue : number;
	rightSelectedTubeValue : number;
	isDoctorProcedure : boolean;
	selectedDecease : string;
	minDate : String;
	maxDate : Date;
	minTimeIn : any;
	child :any;
	totalVolume : number;
	isCurrentExchange:boolean;
	branchName : string;
  showPco2TextBox : boolean;
  showFio2TextBox : boolean;
  reasonIntubationStr : string;
  reasonReintubationStr : string;
  reasonExtubationStr : string;
	constructor(http: Http, router : Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.apiData = new APIurl();
		this.http = http;
		this.showTransfusion = false;
		this.router = router;
		this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
		this.loggedUser = 'test';
		this.hours = new Array<number>();
		this.minutes = new Array<number>();
		this.hoursList = new Array<string>();
		this.minutesList = new Array<string>();
		this.procedureWhichTab = 'peripheral cannula';
		this.investOrderNotOrdered = new Array<any>();
		this.investOrderSelected = new Array<any>();
		this.proceduresModel = new ProceduresModel();
		this.isCurrentExchange = true;
		this.dateListOfTransfusion = [];
		this.exchangeTransfusionSortedList = [];
		this.listTestsByCategory = new Array<RefTestslist>();
		this.orderSelectedText = "";
		this.leftSelectedTubeValue = 0;
		this.rightSelectedTubeValue = 0;
		this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
		this.isDoctorProcedure = false;
		if(localStorage.getItem('selectedChild') != undefined){
			this.child = JSON.parse(localStorage.getItem('selectedChild'));
		}
    this.etIntubationGet();
	}

	// below code is used for the modal popup of order investigation
	closeModalOrderInvestigation() {
		this.investOrderNotOrdered = new Array<any>();
		for(var obj in this.proceduresModel.testsList){
			for(var index = 0; index<this.proceduresModel.testsList[obj].length;index++){
				if(this.proceduresModel.testsList[obj][index].isSelected==true){
					var testName = this.proceduresModel.testsList[obj][index].testname;

					this.investOrderNotOrdered.push(testName);
					if(this.investOrderSelected.indexOf(testName) == -1)
						this.proceduresModel.testsList[obj][index].isSelected = false;

				} else {
					var testName = this.proceduresModel.testsList[obj][index].testname;
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");
	};

	showOrderInvestigation(){
    var dropDownValue = "Infection";
    this.selectedDecease = "Infection";
		for(var obj in this.proceduresModel.testsList){
			for(var index = 0; index<this.proceduresModel.testsList[obj].length;index++){
				var testName = this.proceduresModel.testsList[obj][index].testname;
				if(this.investOrderNotOrdered.indexOf(testName) != -1)
					this.proceduresModel.testsList[obj][index].isSelected = true;
			}
		}
    this.listTestsByCategory = this.proceduresModel.testsList[dropDownValue];
		$("#ballardOverlay").css("display", "block");
		$("#OrderInvestigationPopup").addClass("showing");
	}

	orderSelected(){
		this.orderSelectedText = "";
		this.investOrderSelected = new Array<any>();
		for(var obj in this.proceduresModel.testsList){
			console.log(this.proceduresModel.testsList[obj]);
			for(var index = 0; index<this.proceduresModel.testsList[obj].length;index++){
				if(this.proceduresModel.testsList[obj][index].isSelected==true){
					if(this.orderSelectedText==''){
						this.orderSelectedText =  this.proceduresModel.testsList[obj][index].testname;
					}else{
						this.orderSelectedText = this.orderSelectedText +", "+ this.proceduresModel.testsList[obj][index].testname;
					}

					this.investOrderSelected.push(this.proceduresModel.testsList[obj][index].testname);
				} else {
					var testName = this.proceduresModel.testsList[obj][index].testname;
					this.investOrderSelected.splice(this.investOrderSelected.indexOf(testName),this.investOrderSelected.indexOf(testName)+1);
					this.investOrderNotOrdered.splice(this.investOrderNotOrdered.indexOf(testName),this.investOrderNotOrdered.indexOf(testName)+1);
				}
			}
		}
    if(this.procedureWhichTab == 'Suction'){
      this.proceduresModel.emptyEtSuctionObj.labOrders = this.orderSelectedText;
    }

		$("#ballardOverlay").css("display", "none");
		$("#OrderInvestigationPopup").toggleClass("showing");
	}

  populateTestsListByCategory = function(assessmentCategory){
    this.selectedDecease = assessmentCategory;
    console.log(assessmentCategory);
    this.listTestsByCategory = this.proceduresModel.testsList[assessmentCategory];
    console.log( this.listTestsByCategory);
  }


	getProcedures(uhid : string) {
		console.log("comming into the getProcedures");
		 try{
				this.http.request(this.apiData.getProcedures + uhid + "/" + this.loggedUser + "/" +
					this.branchName + "/").subscribe((res: Response) => {
					this.proceduresModel = res.json();
					this.getBloodBagImage();
	            	this.proceduresModel.emptyOtherObj = new ProcedureOther();
					this.proceduresModel.workingWeight = this.proceduresModel.workingWeight/1000;
					if(this.proceduresModel.isPlanned != null && this.proceduresModel.isPlanned.exchangetrans != null && 	this.proceduresModel.isPlanned.exchangetrans != true) {
						this.isCurrentExchange = false;
					}
					if(this.proceduresModel.exchangeDateList != null) {
						this.proceduresModel.exchangeDateList.forEach(date=>{
							this.dateListOfTransfusion.push(date[0]);
							console.log(this.dateListOfTransfusion);
							if(this.DateOfTransfusion == null || this.DateOfTransfusion == "" || this.DateOfTransfusion == undefined) {
								this.DateOfTransfusion = this.dateListOfTransfusion[0];
								console.log(this.DateOfTransfusion);
								this.fetchingExchangeTransfusionId(this.DateOfTransfusion);
								return;
							}
						})
					}

        	this.chestTubeGet();
        	this.processPeripheralDateTime();
          this.etIntubationGet();
					this.processCentralLineDateTime();
					this.clearExchangeTransfusionList();
	           });
	     }catch(e){
	            console.log("Exception in http service:"+e);
	     };
	}

    fetchingExchangeTransfusionId(selectedDate){
		this.proceduresModel.exchangeDateList.forEach(date=>{
			var selectedExchangeDate = date[0];
			if(selectedExchangeDate == selectedDate){
				var exchangeSajaundiceId = date[1];
				console.log(exchangeSajaundiceId);
				this.filterExchangeTransfusionList(exchangeSajaundiceId);
				return;
			}
		})
	}

  etIntubationGet(){
    this.showFio2TextBox = true;
    this.showPco2TextBox = true;
    this.reasonIntubationStr = "";
    this.reasonReintubationStr = "";
    this.reasonExtubationStr = "";
  }

	emptyExchangeTransfusionObj(){
		this.proceduresModel.exchangeTransfusionObj.aliquot = 10;
		this.proceduresModel.exchangeTransfusionObj.babyBloodGroup = null;
		this.proceduresModel.exchangeTransfusionObj.exchangeType = "DVET";
		this.proceduresModel.exchangeTransfusionObj.volume = 160;
		this.proceduresModel.exchangeTransfusionObj.route = null;
		this.proceduresModel.exchangeTransfusionObj.routeCentralType = null;
		this.proceduresModel.exchangeTransfusionObj.bloodProduct = null;
		this.proceduresModel.exchangeTransfusionObj.collectionDate = null;
		this.proceduresModel.exchangeTransfusionObj.expiryDate = null;
		this.proceduresModel.exchangeTransfusionObj.bagNo = null;
		this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup = null;
		this.proceduresModel.exchangeTransfusionObj.motherBloodGroup = null;
		this.proceduresModel.exchangeTransfusionObj.checkedBy = null;

	}

	filterExchangeTransfusionList(sajaundiceId){
        this.exchangeTransfusionSortedList = [];
    	for(var i = 0; i<this.proceduresModel.allExchangeTransfusionList.length;i++){
    		if(this.proceduresModel.allExchangeTransfusionList[i].sajaundiceId == sajaundiceId){
				this.exchangeTransfusionSortedList.push(this.proceduresModel.allExchangeTransfusionList[i]);
				console.log(this.exchangeTransfusionSortedList);
    		}
		}
		if(this.isCurrentExchange == false) {
			if(this.exchangeTransfusionSortedList.length>0){
				this.updateExchangeTransfusionObj(this.exchangeTransfusionSortedList[0]);
			}else{
				this.updateExchangeTransfusionObj(this.emptyExchangeTransfusionObj());
			}
		}else{
			if(this.proceduresModel.currentExchangeTransfusionList != null){
				this.updateExchangeTransfusionObj(this.proceduresModel.currentExchangeTransfusionList[0]);
			}
			else{
				this.updateExchangeTransfusionObj(this.emptyExchangeTransfusionObj());
			}
		}
	}

	updateExchangeTransfusionObj(obj) {
		if(obj != null) {
			this.proceduresModel.exchangeTransfusionObj.aliquot = obj.aliquot;
			this.proceduresModel.exchangeTransfusionObj.babyBloodGroup = obj.babyBloodGroup;
			this.proceduresModel.exchangeTransfusionObj.exchangeType = obj.exchangeType;
			this.proceduresModel.exchangeTransfusionObj.volume = obj.volume;
			this.proceduresModel.exchangeTransfusionObj.workingWeight = obj.workingWeight;
			this.proceduresModel.exchangeTransfusionObj.route = obj.route;
			this.proceduresModel.exchangeTransfusionObj.routeCentralType = obj.routeCentralType;
			this.proceduresModel.exchangeTransfusionObj.bloodProduct = obj.bloodProduct;
			this.proceduresModel.exchangeTransfusionObj.collectionDate = new Date(obj.collectionDate);
			this.proceduresModel.exchangeTransfusionObj.expiryDate = new Date(obj.expiryDate);
			this.proceduresModel.exchangeTransfusionObj.bagNo = obj.bagNo;
			this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup = obj.exchangeBloodGroup;
			this.proceduresModel.exchangeTransfusionObj.motherBloodGroup = obj.motherBloodGroup;
			this.proceduresModel.exchangeTransfusionObj.checkedBy = obj.checkedBy;

			console.log(this.proceduresModel.exchangeTransfusionObj);

		}
	}

    updateTransfusionList(){
    	for(var i=0; i<this.proceduresModel.exchangeTransfusionObjList.length; i++){
    		this.proceduresModel.exchangeTransfusionObjList[i].exchangeType = this.proceduresModel.exchangeTransfusionObj.exchangeType;
    		this.proceduresModel.exchangeTransfusionObjList[i].route = this.proceduresModel.exchangeTransfusionObj.route;
    		this.proceduresModel.exchangeTransfusionObjList[i].aliquot = this.proceduresModel.exchangeTransfusionObj.aliquot;
    		this.proceduresModel.exchangeTransfusionObjList[i].collectionDate = this.proceduresModel.exchangeTransfusionObj.collectionDate;
			this.proceduresModel.exchangeTransfusionObjList[i].exchangeBloodGroup  = this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup;
			this.proceduresModel.exchangeTransfusionObjList[i].motherBloodGroup = this.proceduresModel.exchangeTransfusionObj.motherBloodGroup;
			this.proceduresModel.exchangeTransfusionObjList[i].bagNo = this.proceduresModel.exchangeTransfusionObj.bagNo;
    		this.proceduresModel.exchangeTransfusionObjList[i].expiryDate = this.proceduresModel.exchangeTransfusionObj.expiryDate;
    		this.proceduresModel.exchangeTransfusionObjList[i].checkedBy  = this.proceduresModel.exchangeTransfusionObj.checkedBy;
  			this.proceduresModel.exchangeTransfusionObjList[i].babyBloodGroup = this.proceduresModel.exchangeTransfusionObj.babyBloodGroup;
			this.proceduresModel.exchangeTransfusionObjList[i].bloodProduct = this.proceduresModel.exchangeTransfusionObj.bloodProduct;
			this.proceduresModel.exchangeTransfusionObjList[i].routeCentralType = this.proceduresModel.exchangeTransfusionObj.routeCentralType;
			this.proceduresModel.exchangeTransfusionObjList[i].volume = this.proceduresModel.exchangeTransfusionObj.volume;

    	}
    }

	showPastExchangeTransfusion() {
		this.isCurrentExchange = false;
		if(this.exchangeTransfusionSortedList.length>0){
			this.updateExchangeTransfusionObj(this.exchangeTransfusionSortedList[0]);
		}else{
			this.updateExchangeTransfusionObj(this.emptyExchangeTransfusionObj());
		}
	}

	showCurrentExchangeTransfusion() {
		this.isCurrentExchange = true;
		if(this.proceduresModel.currentExchangeTransfusionList != null){
			this.updateExchangeTransfusionObj(this.proceduresModel.currentExchangeTransfusionList[0]);
		}else{
			this.updateExchangeTransfusionObj(this.emptyExchangeTransfusionObj());
		}
	}

	addTransfusion(transfusion : ExchangeTransfusion){
	  	transfusion.sajaundiceId = this.proceduresModel.isPlanned.sajaundiceid;
	  	transfusion.uhid = this.proceduresModel.isPlanned.uhid;
	  	transfusion.episodeNumber = this.proceduresModel.isPlanned.episodeNumber;
	  	transfusion.loggeduser = this.proceduresModel.isPlanned.loggeduser;
	  	transfusion.creationtime = new Date();
	  	transfusion.modificationtime = new Date();
	  	if((this.proceduresModel.exchangeTransfusionObj.volumeIn != null) &&
			  (this.proceduresModel.exchangeTransfusionObj.volumeOut !=null) &&
			  (this.proceduresModel.exchangeTransfusionObj.timeIn != null) &&
			  (this.proceduresModel.exchangeTransfusionObj.timeOut != null)){
	  		this.proceduresModel.exchangeTransfusionObjList.push(transfusion);
		  	var dateOfCollection = this.proceduresModel.exchangeTransfusionObj.collectionDate;
		  	var bloodGroup = this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup;
		  	var rhBlood = this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroupRh;
		  	var bagNo = this.proceduresModel.exchangeTransfusionObj.bagNo;
		  	var aliquot = this.proceduresModel.exchangeTransfusionObj.aliquot;
		  	var route = this.proceduresModel.exchangeTransfusionObj.route;
		  	var routeCentralType = this.proceduresModel.exchangeTransfusionObj.routeCentralType;
  			var babyBloodGroup = this.proceduresModel.exchangeTransfusionObj.babyBloodGroup;
  			var motherBloodGroup = this.proceduresModel.exchangeTransfusionObj.motherBloodGroup;
  			var volume = this.proceduresModel.exchangeTransfusionObj.volume;
  			var exchangeType = this.proceduresModel.exchangeTransfusionObj.exchangeType;
  			var dateOfExpiry = this.proceduresModel.exchangeTransfusionObj.expiryDate;
  			var bloodProduct = this.proceduresModel.exchangeTransfusionObj.bloodProduct;
  			var checkedBy = this.proceduresModel.exchangeTransfusionObj.checkedBy;
  	  	console.log(this.proceduresModel.exchangeTransfusionObjList);
  	  	this.proceduresModel.exchangeTransfusionObj = new ExchangeTransfusion();
  	  	this.proceduresModel.exchangeTransfusionObj.collectionDate = dateOfCollection;
  			this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup = bloodGroup;
  			this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroupRh = rhBlood ;
  			this.proceduresModel.exchangeTransfusionObj.motherBloodGroup = motherBloodGroup;
  			this.proceduresModel.exchangeTransfusionObj.bagNo = bagNo;
  			this.proceduresModel.exchangeTransfusionObj.aliquot = aliquot;
  			this.proceduresModel.exchangeTransfusionObj.exchangeType = exchangeType;
  			this.proceduresModel.exchangeTransfusionObj.route = route;
  			this.proceduresModel.exchangeTransfusionObj.routeCentralType = routeCentralType;
  			this.proceduresModel.exchangeTransfusionObj.expiryDate = dateOfExpiry;
  			this.proceduresModel.exchangeTransfusionObj.checkedBy = checkedBy;
  			this.proceduresModel.exchangeTransfusionObj.babyBloodGroup = babyBloodGroup;
  			this.proceduresModel.exchangeTransfusionObj.bloodProduct = bloodProduct;
  			this.proceduresModel.exchangeTransfusionObj.volume = volume;
  			this.showTransfusion = true;
	  	}
	}

	exchangeTransfusionPrint() {
		this.printExchangeTransfusionList = [];
		if(this.isCurrentExchange == true){
			this.printExchangeTransfusionList = this.proceduresModel.currentExchangeTransfusionList ;
			if(this.currentExchangeTotalVolumeIn != undefined && this.currentExchangeTotalVolumeOut != undefined){
				localStorage.setItem('currentExchangeTotalVolumeIn',this.currentExchangeTotalVolumeIn.toString());
				localStorage.setItem('currentExchangeTotalVolumeOut',this.currentExchangeTotalVolumeOut.toString());
			}
		}

		if(this.isCurrentExchange == false){
			this.printExchangeTransfusionList = this.exchangeTransfusionSortedList ;
			if(this.pastExchangeTotalVolumeIn != undefined && this.pastExchangeTotalVolumeOut != undefined) {
				localStorage.setItem('pastExchangeTotalVolumeOut', this.pastExchangeTotalVolumeOut.toString());
				localStorage.setItem('pastExchangeTotalVolumeIn',this.pastExchangeTotalVolumeIn.toString());
			}

		}

		if(this.printExchangeTransfusionList != null && this.printExchangeTransfusionList.length>0){
			localStorage.setItem("isCurrentExchange",JSON.stringify(this.isCurrentExchange));
			localStorage.setItem("workingWeight", JSON.stringify(this.proceduresModel.workingWeight));
			localStorage.setItem("printExchangeTransfusionList",JSON.stringify(this.printExchangeTransfusionList));
			localStorage.setItem("totalVolume",this.totalVolume.toString());
			if(this.proceduresModel.babyDetailObj.bloodgroup != null) {
				localStorage.setItem('babyBloodGroup' , this.proceduresModel.babyDetailObj.bloodgroup);
			}else {
				localStorage.setItem('babyBloodGroup' , this.proceduresModel.exchangeTransfusionObj.babyBloodGroup);
			}
			if(this.proceduresModel.motherBloodGroup == null ){
				localStorage.setItem("motherBloodGroup",JSON.stringify(this.proceduresModel.exchangeTransfusionObj.motherBloodGroup));
			}else{
				localStorage.setItem("motherBloodGroup",JSON.stringify(this.proceduresModel.motherBloodGroup));
			}
			this.router.navigateByUrl('/proceed/exchange-transfusion-print');
		}
	}

	getSum(list,type) : number {
		let sum = 0;
		if(this.proceduresModel.currentExchangeTransfusionList != null) {
			if(list == 'currentExchangeTransfusionList' && type == 'volumeIn'){
				this.proceduresModel.currentExchangeTransfusionList.forEach(item=>{
					sum += item.volumeIn;
				})
				this.currentExchangeTotalVolumeIn = sum;
			}
			if(list == 'currentExchangeTransfusionList' && type == 'volumeOut'){
				this.proceduresModel.currentExchangeTransfusionList.forEach(item=>{
					sum += item.volumeOut;
				})
				this.currentExchangeTotalVolumeOut = sum;

			}
		}
		if(this.exchangeTransfusionSortedList != null) {
			if(list == 'pastExchangeTransfusionList' && type == 'volumeOut'){
				for(let i = 0; i < this.exchangeTransfusionSortedList.length; i++) {
					sum += this.exchangeTransfusionSortedList[i].volumeOut;
				}
				this.pastExchangeTotalVolumeOut = sum;
			}
			if(list == 'pastExchangeTransfusionList' && type == 'volumeIn'){
				for(let i = 0; i < this.exchangeTransfusionSortedList.length; i++) {
					sum += this.exchangeTransfusionSortedList[i].volumeIn;
				}
				this.pastExchangeTotalVolumeIn = sum;
			}
		}

		return sum;
	  }

	validateExchangeTransfusion(procedure) {
    	if(this.proceduresModel.exchangeTransfusionObj.aliquot !=null &&
    		this.proceduresModel.exchangeTransfusionObj.exchangeBloodGroup != "" &&
			this.proceduresModel.exchangeTransfusionObj.expiryDate != null && this.proceduresModel.exchangeTransfusionObj.collectionDate != null &&
			(this.proceduresModel.exchangeTransfusionObj.babyBloodGroup !=null || this.proceduresModel.babyDetailObj.bloodgroup != null)
			&& (this.proceduresModel.exchangeTransfusionObj.volume != null) ) {
			this.saveBloodBagImage();
    		this.saveProcedures(procedure);
    	}
	}

	processExchangeTransfusionExpiryDate(){
		if(this.proceduresModel.exchangeTransfusionObj.expiryDate != null){
			this.proceduresModel.exchangeTransfusionObj.expiryDate = new Date(this.proceduresModel.
				exchangeTransfusionObj.expiryDate);
		}else{
			this.proceduresModel.exchangeTransfusionObj.expiryDate = null;
		}
	}

	processExchangeTransfusionCollectionDate(){
		if(this.proceduresModel.exchangeTransfusionObj.collectionDate != null){
			this.proceduresModel.exchangeTransfusionObj.collectionDate = new Date(this.proceduresModel.
				exchangeTransfusionObj.collectionDate);
		}else {
			this.proceduresModel.exchangeTransfusionObj.collectionDate = null;
		}
	}

	saveProcedures(procedure : string) {
		this.updateTransfusionList();
		console.log("comming into the saveProcedures");
		console.log(this.proceduresModel);
		this.chestTubeSave(); //method to set some values..
    this.etIntubationSave();
		this.proceduresModel.exchangeTransfusionObj.totalVolume = this.totalVolume;
		this.proceduresModel.exchangeTransfusionObj.workingWeight = this.proceduresModel.workingWeight;
		this.proceduresModel.exchangeTransfusionObj.bloodBagImage = this.bagImageObj.toString();
		this.isLoaderVisible = true;
  	this.loaderContent = 'Saving...';
		try{
        this.http.post(this.apiData.saveProcedures + this.uhid	+ "/" + this.loggedUser + "/"
        + this.branchName + "/" + procedure + "/",this.proceduresModel).subscribe(res => {
        	this.proceduresModel = res.json().returnedObject;
        	this.isLoaderVisible = false;
        	this.loaderContent = 'Saving...';
         	console.log("Data after Procedure Saved ");
  				console.log(this.proceduresModel);
  				this.proceduresModel.emptyOtherObj = new ProcedureOther();
  				this.processPeripheralDateTime();
  				this.processCentralLineDateTime();
  				this.proceduresModel.exchangeTransfusionObj.timeIn = null;
  				this.proceduresModel.exchangeTransfusionObj.timeOut = null;
  				this.proceduresModel.exchangeTransfusionObj.volumeIn = null;
  				this.proceduresModel.exchangeTransfusionObj.volumeOut = null;
  				this.processExchangeTransfusionExpiryDate();
  				this.processExchangeTransfusionCollectionDate();
  				this.proceduresModel.workingWeight = this.proceduresModel.workingWeight/1000;
  				this.orderSelectedText = "";
          this.etIntubationGet();
          },err => {
              console.log("Error occured.")
            });
		}
		catch(e){
			console.log("Exception in http service:"+e);
		};
	}

  etIntubationSave(){
    this.proceduresModel.currentEtIntubationObj.reasonReintubation = this.reasonReintubationStr;
    this.proceduresModel.currentEtIntubationObj.reasonIntubation = this.reasonIntubationStr;
    this.proceduresModel.currentEtIntubationObj.removalReason = this.reasonExtubationStr;
  }

	round(value : any, precision : any) {
		var multiplier = Math.pow(10, precision);
		return Math.round(value * multiplier) / multiplier;
	}

	updateVolume(){
		if(this.proceduresModel.exchangeTransfusionObj.exchangeType == 'DVET') {
			this.proceduresModel.exchangeTransfusionObj.volume = 160;
		}else {
			this.proceduresModel.exchangeTransfusionObj.volume = 80;
		}
		this.updateTotalVolume();
	}

    updateTotalVolume() {
    	let totalVolume = this.proceduresModel.workingWeight * this.proceduresModel.exchangeTransfusionObj.volume;
		this.totalVolume = this.round(totalVolume,2);
    }

    updateVolumeOut() {
    	this.proceduresModel.exchangeTransfusionObj.timeOut = new Date();
    }

    updateVolumeIn() {
    	this.proceduresModel.exchangeTransfusionObj.timeIn = new Date();
    }

	clearExchangeTransfusionList(){
		this.proceduresModel.exchangeTransfusionObjList = [];
		this.proceduresModel.exchangeTransfusionObj.volumeIn = null;
		this.proceduresModel.exchangeTransfusionObj.volumeOut = null;
		this.proceduresModel.exchangeTransfusionObj.timeIn = null;
		this.proceduresModel.exchangeTransfusionObj.timeOut = null;
		this.processExchangeTransfusionCollectionDate();
		this.processExchangeTransfusionExpiryDate();

		if(this.proceduresModel.exchangeTransfusionObj.exchangeType == null){
			this.proceduresModel.exchangeTransfusionObj.exchangeType = 'DVET';
		}
		if(this.proceduresModel.exchangeTransfusionObj.aliquot == null){
			this.proceduresModel.exchangeTransfusionObj.aliquot = 10;
		}
		if(this.proceduresModel.exchangeTransfusionObj.volume == null){
			if(this.proceduresModel.exchangeTransfusionObj.exchangeType == 'DVET'){
				this.proceduresModel.exchangeTransfusionObj.volume  = 160;
			}
			else{
				this.proceduresModel.exchangeTransfusionObj.volume  = 80;
			}
		}
		let totalVolume = this.proceduresModel.workingWeight * this.proceduresModel.exchangeTransfusionObj.volume;
		this.totalVolume = this.round(totalVolume,2);

	}
	//---- upload blood bag code start ---

	getBloodBagImage(){
		console.log("In the get baby Image function");
		var imageTempName = "bloodbag_"+this.proceduresModel.babyDetailObj.uhid+"_pic.png"
		console.log(imageTempName);
		var url=this.apiData.getBloodBagImage + '/' + imageTempName + '/';
		console.log(url);
		try{
			this.http.request(url)
			.subscribe(res => {
			console.log(res);
			this.bagImageObj = res.json();
			console.log("bagImageObj" +this.bagImageObj);
			},
			err => {
				console.log("Error occured.")
			});
			}
			catch(e){
			console.log("Exception in http service:"+e);
			};

	  }

	  readBagURL(file:FileList){
		this.selectedFile=file.item(0);
		var reader=new FileReader();
		reader.onload=(event:any)=>{
			this.bagImageObj.name = "bloodbag_" + this.proceduresModel.babyDetailObj.uhid+"_pic.png" ;
			this.bagImageObj.data = event.target.result;
		}
		if(this.selectedFile){
			reader.readAsDataURL(this.selectedFile);
		}
		console.log(this.selectedFile);
	  };

	  saveBloodBagImage(){
		if(this.proceduresModel.babyDetailObj.uhid != null && this.proceduresModel.babyDetailObj.uhid != undefined
		  && this.proceduresModel.babyDetailObj.uhid != "" ){
			if(this.selectedFile!=null && this.selectedFile!=undefined){
			  var url=this.apiData.saveBloodBagImage + '/';
			  console.log(url);
			   try{
				   this.http.post(url,this.bagImageObj)
				   .subscribe(res => {
					   this.imageResponse=res.json();
					   console.log(this.imageResponse);
					},
				 err => {
					 console.log("Error occured.")
				   });
				}
				catch(e){
			  console.log("Exception in http service:"+e);
				};
			}else{
				return;
			 }
		}else{
			alert("Please Enter UHID First");
		}
	  }

//		-------------------------------------- pheripheral cannula code start --------------------------------

	processPeripheralDateTime() {
		for(var i = 0; i < this.proceduresModel.currentPeripheralList.length; i++) {
			var obj = this.proceduresModel.currentPeripheralList[i];
			if(obj.peripheral_cannula_id != null) {
				obj.insertion_timestamp = new Date(obj.insertion_timestamp);
			}
		}
	}

	addPeripheralCannula() {
		this.proceduresModel.currentPeripheralList.push(Object.assign({}, this.proceduresModel.emptyPeripheralObj));
	}

	removePeripheralCannula(index : number) {
		this.proceduresModel.currentPeripheralList.splice(index,1);
	}

	insertionDate(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentPeripheralList[index].insertion_timestamp = tempDate.getTime();
		this.insertionTime(index);
	}

	insertionTime(index : number) {
		var tempHrs = this.proceduresModel.currentPeripheralList[index].insertion_hrs;
		var tempMins = this.proceduresModel.currentPeripheralList[index].insertion_mins;
		var tempMeridian = this.proceduresModel.currentPeripheralList[index].insertion_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentPeripheralList[index].insertion_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);

			this.proceduresModel.currentPeripheralList[index].insertion_timestamp = tempDate.getTime();
		}
	}

	toggleRemove (index : number) {
		if(this.proceduresModel.currentPeripheralList[index].remove) {
			this.proceduresModel.currentPeripheralList[index].remove = false;
			this.proceduresModel.currentPeripheralList[index].removal_date = null;
			this.proceduresModel.currentPeripheralList[index].removal_hrs = null;
			this.proceduresModel.currentPeripheralList[index].removal_mins = null;
			this.proceduresModel.currentPeripheralList[index].removal_meridian = null;
			this.proceduresModel.currentPeripheralList[index].removal_timestamp = null;
			this.proceduresModel.currentPeripheralList[index].removal_reason = null;
		} else {
			this.proceduresModel.currentPeripheralList[index].remove = true;
		}
	}

	removalDate(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentPeripheralList[index].removal_timestamp = tempDate.getTime();
		this.removalTime(index);
	}

	removalTime(index : number) {
		var tempHrs = this.proceduresModel.currentPeripheralList[index].removal_hrs;
		var tempMins = this.proceduresModel.currentPeripheralList[index].removal_mins;
		var tempMeridian = this.proceduresModel.currentPeripheralList[index].removal_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentPeripheralList[index].removal_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);

			this.proceduresModel.currentPeripheralList[index].removal_timestamp = tempDate.getTime();
		}
	}

//	------------------------------- pheripheral cannula code end ----------------------------------------

//	-------------------------------- central line code start --------------------------------------------

	centralLineType(item : CentralLine) {
		if(item.centralLineType == "uv" || item.centralLineType == "ua") {
			item.site = "umblical";
		}
	}

	processCentralLineDateTime() {
		for(var i = 0; i < this.proceduresModel.currentCentralLineList.length; i++) {
			var obj = this.proceduresModel.currentCentralLineList[i];
			if(obj.centralLineId != null) {
				obj.insertion_timestamp = new Date(obj.insertion_timestamp);
			}
		}
	}

	addCentralLine() {
		this.proceduresModel.currentCentralLineList.push(Object.assign({}, this.proceduresModel.emptyCentralLineObj));
	}

	removeCentralLine(index : number) {
		this.proceduresModel.currentCentralLineList.splice(index,1);
	}

	insertionDateCentralLine(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentCentralLineList[index].insertion_timestamp = tempDate.getTime();
		this.insertionTimeCentralLine(index);
	}

	insertionTimeCentralLine(index : number) {
		var tempHrs = this.proceduresModel.currentCentralLineList[index].insertion_hrs;
		var tempMins = this.proceduresModel.currentCentralLineList[index].insertion_mins;
		var tempMeridian = this.proceduresModel.currentCentralLineList[index].insertion_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentCentralLineList[index].insertion_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);
			this.proceduresModel.currentCentralLineList[index].insertion_timestamp = tempDate.getTime();
		}
	}

	toggleRemoveCentralLine(index : number) {
		if(this.proceduresModel.currentCentralLineList[index].remove) {
			this.proceduresModel.currentCentralLineList[index].remove = false;

			this.proceduresModel.currentCentralLineList[index].removal_date = null;
			this.proceduresModel.currentCentralLineList[index].removal_hrs = null;
			this.proceduresModel.currentCentralLineList[index].removal_mins = null;
			this.proceduresModel.currentCentralLineList[index].removal_meridian = null;
			this.proceduresModel.currentCentralLineList[index].removal_timestamp = null;
			this.proceduresModel.currentCentralLineList[index].removalReason = null;
		} else {
			this.proceduresModel.currentCentralLineList[index].remove = true;
		}
	}

	removalDateCentralLine(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentCentralLineList[index].removal_timestamp = tempDate.getTime();
		this.removalTimeCentralLine(index);
	}

	removalTimeCentralLine(index : number) {
		var tempHrs = this.proceduresModel.currentCentralLineList[index].removal_hrs;
		var tempMins = this.proceduresModel.currentCentralLineList[index].removal_mins;
		var tempMeridian = this.proceduresModel.currentCentralLineList[index].removal_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentCentralLineList[index].removal_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);

			this.proceduresModel.currentCentralLineList[index].removal_timestamp = tempDate.getTime();
		}
	}

//	-------------------------- central line code end -------------------------------------------------------

//	-------------------------- Lumbar Puncture code start --------------------------------------------------

	lumbarPunctureTime() {
		this.proceduresModel.emptyLumbarPunctureObj.lumbar_timestamp = new Date(this.proceduresModel.emptyLumbarPunctureObj.lumbar_date);
	}

//	-------------------------- Lumbar Puncture code end ---------------------------------------------------

//	-------------------------- VTap code start -------------------------------------------------------

	vtapTime() {
		this.proceduresModel.emptyVtapObj.vtap_timestamp = new Date(this.proceduresModel.emptyVtapObj.vtap_date);
		console.log(this.proceduresModel.emptyVtapObj.vtap_timestamp);
	}

//	-------------------------- VTap code end -------------------------------------------------------

//	-------------------------------- ET Intubation code start --------------------------------------------

  //To Populate Reason for Et Intubation Pop up
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


  arrayRemove(arr, value) {
   return arr.filter(function(ele){
       return ele != value;
   });
  }

  //below code is used for creating reason string
   showReasonTextBox(id,type,reason){
    if(type == 'intubation'){
      if(this.reasonIntubationStr.includes(reason)){
        if(!this.reasonIntubationStr.includes(",")){
          this.reasonIntubationStr = this.reasonIntubationStr.replace(reason,'');
        }
        else{
          this.reasonIntubationStr = this.reasonIntubationStr.replace(', '+reason,'');
        }
      }
      else{
        if(this.reasonIntubationStr == ""){
          this.reasonIntubationStr += reason;
        }
        else{
          this.reasonIntubationStr += ", " + reason;
        }
      }
    }

    if(type == 'reintubation'){
      if(this.reasonReintubationStr.includes(reason)){
        if(!this.reasonReintubationStr.includes(",")){
          this.reasonReintubationStr = this.reasonReintubationStr.replace(reason,'');
        }
        else{
          this.reasonReintubationStr = this.reasonReintubationStr.replace(', '+reason,'');
        }
      }
      else{
        if(this.reasonReintubationStr == ""){
          this.reasonReintubationStr += reason;
        }
        else{
          this.reasonReintubationStr += ", " + reason;
        }
      }
    }

    if(type == 'extubation'){
      if(this.reasonExtubationStr.includes(reason)){
        if(!this.reasonExtubationStr.includes(",")){
          this.reasonExtubationStr = this.reasonExtubationStr.replace(reason,'');
        }
        else{
          this.reasonExtubationStr = this.reasonExtubationStr.replace(', '+reason,'');
        }
      }
      else{
        if(this.reasonExtubationStr == ""){
          this.reasonExtubationStr += reason;
        }
        else{
          this.reasonExtubationStr +=  ", " + reason;
        }
      }
    }

    if(id == 'ETR0010' || id == 'ETR003'){
     this.showPco2TextBox = !this.showPco2TextBox;
    }
    if(id == 'ETR002' || id == 'ETR009'){
     this.showFio2TextBox = !this.showFio2TextBox ;
     }
  }

  //below code is used for reset selected etIntubation Reason
  resetEtIntubationReason(){
    this.proceduresModel.etIntubationReasonList.forEach((item)=>{
      	item.flag = false;
    })

     this.showPco2TextBox = true;
     this.proceduresModel.currentEtIntubationObj.pco2Value = null;

     this.showFio2TextBox = true;
     this.proceduresModel.currentEtIntubationObj.fiO2Value = null;

     this.reasonIntubationStr = "";
     this.reasonReintubationStr = "";
  }

	addEtIntubation() {
		this.proceduresModel.currentEtIntubationList.push(Object.assign({}, this.proceduresModel.emptyEtIntubationObj));
	}

	removeEtIntubation(index : number) {
		this.proceduresModel.currentEtIntubationList.splice(index,1);
	}

	insertionDateEtIntubation(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentEtIntubationList[index].insertion_timestamp = tempDate.getTime();
		this.insertionTimeEtIntubation(index);
	}

	insertionTimeEtIntubation(index : number) {
		var tempHrs = this.proceduresModel.currentEtIntubationList[index].insertion_hrs;
		var tempMins = this.proceduresModel.currentEtIntubationList[index].insertion_mins;
		var tempMeridian = this.proceduresModel.currentEtIntubationList[index].insertion_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentEtIntubationList[index].insertion_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);

			this.proceduresModel.currentEtIntubationList[index].insertion_timestamp = tempDate.getTime();
		}
	}

	toggleRemoveEtIntubation() {
		if(this.proceduresModel.currentEtIntubationObj.isextubation != null &&
       this.proceduresModel.currentEtIntubationObj.isextubation == true) {
        this.proceduresModel.currentEtIntubationObj.isextubation = false;
  			this.proceduresModel.currentEtIntubationObj.removal_timestamp = null;
  			this.proceduresModel.currentEtIntubationObj.removalReason = null;
		} else {
  			this.proceduresModel.currentEtIntubationObj.isextubation = true;
		}
    this.reasonExtubationStr = "";
	}

	removalDateEtIntubation(index : number, dateValue : Date) {
		var tempDate = dateValue;
		this.proceduresModel.currentEtIntubationList[index].removal_timestamp = tempDate.getTime();
		this.removalTimeEtIntubation(index);
	}

	removalTimeEtIntubation(index : number) {
		var tempHrs = this.proceduresModel.currentEtIntubationList[index].removal_hrs;
		var tempMins = this.proceduresModel.currentEtIntubationList[index].removal_mins;
		var tempMeridian = this.proceduresModel.currentEtIntubationList[index].removal_meridian;

		if(tempHrs != null && tempMins != null && tempMeridian != null) {
			if(tempMeridian == 'AM' && tempHrs == 12) {
				tempHrs = 0;
			} else if(tempMeridian == 'PM' && tempHrs != 12) {
				tempHrs += 12;
			}

			var tempDate = new Date(this.proceduresModel.currentEtIntubationList[index].removal_timestamp);
			tempDate.setHours(tempHrs);
			tempDate.setMinutes(tempMins);

			this.proceduresModel.currentEtIntubationList[index].removal_timestamp = tempDate.getTime();
		}
	}

//	-------------------------- ET Intubation code end ----------------------------------------------

//	-------------------------- CHEST TUBE CODE START ----------------------------------------------

	GoToPneumoThorax(){
		localStorage.setItem('isComingFromProcedureTube', JSON.stringify("Yes"));
		localStorage.setItem('procedureModel', JSON.stringify(this.proceduresModel));
		this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
	}

	chestTubeGet(){//initals for chest tube..
		if (JSON.parse(localStorage.getItem('isComingFromProcedureTube'))!=null){
			localStorage.removeItem("isComingFromProcedureTube");
			this.proceduresModel = JSON.parse(localStorage.getItem('procedureModel'));
			this.procedureWhichTab ="Chest";
		}
	}

	chestTubeSave(){
		this.proceduresModel.chestTube.entrytime = new Date();
		if(this.proceduresModel.chestTube.inserteddate != null && this.proceduresModel.chestTube.inserteddate != ''){
			this.proceduresModel.chestTube.inserteddate = new Date(this.proceduresModel.chestTube.inserteddate);
		}

		if(this.proceduresModel.chestTube.removaldate != null && this.proceduresModel.chestTube.removaldate != ''){
			this.proceduresModel.chestTube.removaldate = new Date(this.proceduresModel.chestTube.removaldate);
		}
	}

	openCloseChestTube(selectedTube : any, side : string, indexValue : number){
		if(side=='right'){
			this.rightSelectedTubeValue = indexValue;
			this.proceduresModel.pneumothorax.chestTubeObj.rightObjectToShow = selectedTube;
		}else if(side=='left') {
			this.leftSelectedTubeValue = indexValue;
			this.proceduresModel.pneumothorax.chestTubeObj.leftObjectToShow = selectedTube;
		}
	}

//	-------------------------- CHEST TUBE CODE END ----------------------------------------------

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
		if(this.router.url === '/proceed/procedures'){
			this.isDoctorProcedure = true;
		}
	    if(this.router.url === '/procedure/nursing-procedures'){
	      this.isDoctorProcedure = false;
	    }
		console.log("In Procedure controller");
		this.getProcedures(this.uhid);
		for(var i=1; i<=12; ++i) {
			this.hours.push(i)
		}

		for(var i=0; i<=59; ++i) {
			this.minutes.push(i)
		}

		for(var i=1;i<=12;++i){
			if(i<10)
				this.hoursList.push("0"+i.toString());
			else
				this.hoursList.push(i.toString());
		}

		for(var i=0;i<60;++i){
			if(i<10)
				this.minutesList.push("0"+i.toString());
			else
				this.minutesList.push(i.toString());
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


  removeTransfusionFromList(index : any) {
  	this.proceduresModel.exchangeTransfusionObjList.splice(index, 1);
  }

  clearTransfusion(){
  	this.proceduresModel.exchangeTransfusionObj.timeIn = null;
  	this.proceduresModel.exchangeTransfusionObj.timeOut = null;
  	this.proceduresModel.exchangeTransfusionObj.volumeIn = null;
  	this.proceduresModel.exchangeTransfusionObj.volumeOut = null;
  	this.proceduresModel.exchangeTransfusionObj.spo2 = null;
  	this.proceduresModel.exchangeTransfusionObj.hr = null;
  	this.proceduresModel.exchangeTransfusionObj.comment = " ";
  }

  editTransfusionFromList(index : any) {
		this.proceduresModel.exchangeTransfusionObj.timeIn = this.proceduresModel.exchangeTransfusionObjList[index].timeIn;
		this.proceduresModel.exchangeTransfusionObj.timeOut = this.proceduresModel.exchangeTransfusionObjList[index].timeOut;
		this.proceduresModel.exchangeTransfusionObj.spo2 = this.proceduresModel.exchangeTransfusionObjList[index].spo2;
		this.proceduresModel.exchangeTransfusionObj.volumeIn = this.proceduresModel.exchangeTransfusionObjList[index].volumeIn;
		this.proceduresModel.exchangeTransfusionObj.volumeOut = this.proceduresModel.exchangeTransfusionObjList[index].volumeOut;
		this.proceduresModel.exchangeTransfusionObj.hr = this.proceduresModel.exchangeTransfusionObjList[index].hr;
		this.proceduresModel.exchangeTransfusionObj.comment = this.proceduresModel.exchangeTransfusionObjList[index].comment;
		this.proceduresModel.exchangeTransfusionObjList.splice(index, 1);
		console.log(this.proceduresModel.exchangeTransfusionObjList);
	}

  initializeValuesHeplock(obj){
    if(!this.isEmpty(obj.antiCoagulentBrand) && !this.isEmpty(obj.antiCoagulentType) && !this.isEmpty(obj.solutionType)){
      obj.heparinStrength = 10;
      obj.heparinDose = 1;
      obj.heparinTotalVolume = 24;
      obj.heparinVolume = (24 * obj.heparinDose) / obj.heparinStrength;
      obj.heparinRate = obj.heparinTotalVolume / 24;
      obj.heparinOverfillVolume = 0;
    }
  }

  calculateHeparinVolume(obj){
    if(!this.isEmpty(obj.heparinStrength) && !this.isEmpty(obj.heparinDose) && !this.isEmpty(obj.heparinTotalVolume)){
      obj.heparinVolume = (24 * obj.heparinDose) / obj.heparinStrength;
      obj.heparinRate = obj.heparinTotalVolume / 24;
    }
  }

}
