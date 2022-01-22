import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
@Component({
  selector: 'nursing-print',
  templateUrl: './nursing-print.component.html',
  styleUrls: ['./nursing-print.component.css']
})
export class NursingPrintComponent implements OnInit, AfterViewInit {
	childData : any;
  printDataObj : any;
	gestationWeeks : any;
  gestationDays : any;
  refFeedTypeList : any;
  weight : number;
  urineMlKgHr : number;
  totalGastricAspirate : number;
  totalFeed : number;
  totalUrine : number;
  hours : number;
  totalMedicineVol : number;
  totalPn : number;
  totalEn : number;
  fluidReq : number;
  totalHeplockDelVolume : number;
  totalBloodDelVolume : number;
  totalDrain : number;
  isOutputSummary : boolean;
  isEnteralSummary : boolean;
  isParenteralSummary : boolean;
  isOtherAdditivesSummary : boolean;
  isDisplayRemark : boolean;
  nursingOutputData : any;
  isFlag : boolean;
  sodiumVolume : number;
  calciumVolume : number;
  totalBolus : number;
  protein : number;
  energy : number;
  pupilStr : string;
  childObject : any;
	constructor(
    private router: Router,
    private _location: Location
    ) {
    this.isDisplayRemark = false;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.pupilStr = localStorage.getItem('pupilStr');
    this.childData = JSON.parse(localStorage.getItem('selectedChild'));
    console.log(this.childData);


  }
	printPdf(){
    setTimeout(window.print, 1000);
    if(this.printDataObj == null){
      this.printDataObj.whichTab = this.nursingOutputData.whichTab;
    }
    console.log(this.printDataObj);
    const self = this;
    if (window.matchMedia) {
      var mediaQueryList = window.matchMedia('print');
      const redirect = (mql)=>{
        if(!mql.matches){
          if (mediaQueryList) {
            mediaQueryList.removeListener(redirect);
            
          }
          self._location.back();
        }
  
      }

      mediaQueryList.addListener(redirect);
    }
  }
  
  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }

  
  ngOnInit() {
    this.printDataObj = JSON.parse(localStorage.getItem('printNursingDataObjNotes'));
    if(this.printDataObj.whichTab == 'Nursing-output'){
      this.nursingOutputData = this.printDataObj;
      // this.nursingOutputData.procedureList.forEach(item=>{
      //   var d = new Date(item[1]);
      //   item[1] = d.getDate() + '/' + (d.getMonth()+1) + '/' + d.getFullYear();
      //   console.log(item[1]);
      // })
      console.log(this.nursingOutputData);
      
      if(new Date(this.printDataObj.to_time) != null && new Date(this.printDataObj.to_time).getHours() == 8 && new Date(this.printDataObj.to_time).getMinutes() == 0){
        this.isFlag = true;
      }
      else if(new Date(this.printDataObj.to_time) != null &&
        !(new Date(this.printDataObj.to_time).getHours() == 8 && new Date(this.printDataObj.to_time).getMinutes() == 0)){
        this.isFlag = false;
      }
      if(this.nursingOutputData.intakeOutputList.length != 0 ){
        this.isOutputSummary = false;
        this.isEnteralSummary = false;
        this.isParenteralSummary = false;
        this.isOtherAdditivesSummary = false;
        for(var i=0;i<this.nursingOutputData.intakeOutputList.length;i++){
          if(this.nursingOutputData.intakeOutputList[i].enteralComment != null || this.nursingOutputData.intakeOutputList[i].parenteralComment != null || this.nursingOutputData.intakeOutputList[i].outputComment != null || this.nursingOutputData.intakeOutputList[i].enteralComment != '' || this.nursingOutputData.intakeOutputList[i].parenteralComment != '' || this.nursingOutputData.intakeOutputList[i].outputComment != ''){
              this.isDisplayRemark = true;
            }
        }
      }
    }
    if(this.printDataObj.whichTab == 'Intake/Output'){
      if(this.printDataObj.intakeOutputList.length != 0){
      this.isOutputSummary = false;
      this.isEnteralSummary = false;
      this.isParenteralSummary = false;
      this.isOtherAdditivesSummary = false;
      for(var i=0;i<this.printDataObj.intakeOutputList.length;i++){
        if((this.isOutputSummary != true) && (this.printDataObj.intakeOutputList[i].abdomenGirth != null || this.printDataObj.intakeOutputList[i].gastricAspirate != null || this.printDataObj.intakeOutputList[i].urine != null || this.printDataObj.intakeOutputList[i].stool != null || this.printDataObj.intakeOutputList[i].vomit || null && this.printDataObj.intakeOutputList[i].drain != null || this.printDataObj.intakeOutputList[i].outputComment != null)){
            this.isOutputSummary = true;
        }
        if((this.isEnteralSummary != true) && (this.printDataObj.intakeOutputList[i].primaryFeedValue != null || this.printDataObj.intakeOutputList[i].formulaValue != null || this.printDataObj.intakeOutputList[i].actualFeed != null || this.printDataObj.intakeOutputList[i].enteralComment || null)){
          this.isEnteralSummary = true;
        }
        if((this.isParenteralSummary != true) && (this.printDataObj.intakeOutputList[i].lipid_delivered != null || this.printDataObj.intakeOutputList[i].tpn_delivered != null || this.printDataObj.intakeOutputList[i].parenteralComment != null)){
          this.isParenteralSummary = true;
        }
        if((this.isOtherAdditivesSummary != true) && (this.printDataObj.intakeOutputList[i].calciumVolume != null || this.printDataObj.intakeOutputList[i].ironVolume != null || this.printDataObj.intakeOutputList[i].mvVolume != null || this.printDataObj.intakeOutputList[i].otherAdditiveVolume != null || this.printDataObj.intakeOutputList[i].calciumComment != null || this.printDataObj.intakeOutputList[i].ironComment != null || this.printDataObj.intakeOutputList[i].mvComment != null || this.printDataObj.intakeOutputList[i].otherAdditiveComment != null)){
          this.isOtherAdditivesSummary = true;
        }
      }
        if(localStorage.getItem('totalEn') != undefined){
          this.totalEn = JSON.parse(localStorage.getItem('totalEn'));
        }
        if(localStorage.getItem('totalPn') != undefined){
          this.totalPn = JSON.parse(localStorage.getItem('totalPn'));
        }
        if(localStorage.getItem('totalUrine') != undefined){
          this.totalUrine = JSON.parse(localStorage.getItem('totalUrine'));
        }
        if(localStorage.getItem('totalFeed') != undefined){
          this.totalFeed = JSON.parse(localStorage.getItem('totalFeed'));
        }
        if(localStorage.getItem('urineMlKgHr') != undefined){
          this.urineMlKgHr = JSON.parse(localStorage.getItem('urineMlKgHr'));
        }
      }
    }
    if(localStorage.getItem('refFeedTypeList') != undefined){
      this.refFeedTypeList = JSON.parse(localStorage.getItem('refFeedTypeList'));
    }

    if(localStorage.getItem('fluidReq') != undefined){
      this.fluidReq = JSON.parse(localStorage.getItem('fluidReq'));
    }
    if(localStorage.getItem('totalEn') != undefined){
      this.totalEn = JSON.parse(localStorage.getItem('totalEn'));
    }
    if(localStorage.getItem('totalPn') != undefined){
      this.totalPn = JSON.parse(localStorage.getItem('totalPn'));
    }
    if(localStorage.getItem('totalMedicineVol') != undefined){
      this.totalMedicineVol = JSON.parse(localStorage.getItem('totalMedicineVol'));
    }
    if(localStorage.getItem('hours') != undefined){
      this.hours = JSON.parse(localStorage.getItem('hours'));
    }
    if(localStorage.getItem('totalUrine') != undefined){
      this.totalUrine = JSON.parse(localStorage.getItem('totalUrine'));
    }
    if(localStorage.getItem('totalFeed') != undefined){
      this.totalFeed = JSON.parse(localStorage.getItem('totalFeed'));
    }
    if(localStorage.getItem('totalGastricAspirate') != undefined){
      this.totalGastricAspirate = JSON.parse(localStorage.getItem('totalGastricAspirate'));
    }
    if(localStorage.getItem('urineMlKgHr') != undefined){
      this.urineMlKgHr = JSON.parse(localStorage.getItem('urineMlKgHr'));
    }
    if(localStorage.getItem('weight') != undefined){
      this.weight = JSON.parse(localStorage.getItem('weight'));
    }
    if(localStorage.getItem('totalHeplockDelVolume') != undefined){
      this.totalHeplockDelVolume = JSON.parse(localStorage.getItem('totalHeplockDelVolume'));
    }
    if(localStorage.getItem('totalBloodDelVolume') != undefined){
      this.totalBloodDelVolume = JSON.parse(localStorage.getItem('totalBloodDelVolume'));
    }
    if(localStorage.getItem('totalDrain') != undefined){
      this.totalDrain = JSON.parse(localStorage.getItem('totalDrain'));
    }
    if(localStorage.getItem('totalBolus') != undefined){
      this.totalBolus = JSON.parse(localStorage.getItem('totalBolus'));
    }
    if(localStorage.getItem('protein') != undefined){
      this.protein = JSON.parse(localStorage.getItem('protein'));
    }
    if(localStorage.getItem('energy') != undefined){
      this.energy = JSON.parse(localStorage.getItem('energy'));
    }
    if(localStorage.getItem('sodiumVolume') != undefined){
      this.sodiumVolume = JSON.parse(localStorage.getItem('sodiumVolume'));
    }
    if(localStorage.getItem('calciumVolume') != undefined){
      this.calciumVolume = JSON.parse(localStorage.getItem('calciumVolume'));
    }
    if(this.childData.gestation) {
        console.log("found ",this.childData.gestation);
        this.gestationWeeks = this.childData.gestation.split("Weeks");
        var days = this.childData.gestation.match("Weeks(.*)Days");
        console.log(days);
        console.log(this.gestationWeeks);
        this.gestationWeeks = parseInt(this.gestationWeeks[0]);
        this.gestationDays = parseInt(days[1]);
      }
      else {
        console.log("no found");
        this.gestationWeeks = null;
        this.gestationDays = null;
      }

    console.log(this.printDataObj);
    localStorage.removeItem('printNursingDataObjNotes');
    if(localStorage.getItem('pupilStr') != undefined){
        localStorage.removeItem('pupilStr');
    }
  }
  
  ngAfterViewInit() {
    this.printPdf();
  }
}
