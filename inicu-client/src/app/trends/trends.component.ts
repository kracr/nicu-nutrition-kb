import { Component, OnInit, ViewChild } from '@angular/core';
import * as Highcharts from 'highcharts';
import xrange from "highcharts/modules/xrange";
import { Chart } from 'angular-highcharts';
import { chart } from 'highcharts';
import { APIurl } from '../../../model/APIurl';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import { CmData } from './cmdata';
import { ResponseCmData } from './responseCmData';
import { XYAxis } from './xyaxis';
xrange(Highcharts);
@Component({
  selector: 'trends',
  templateUrl: './trends.component.html',
  styleUrls: ['./trends.component.css']
})
export class TrendsComponent implements OnInit {
  chartOne : Chart;
  // chartTwo : Chart;
  apiData : APIurl;
	http: Http;
  router : Router;
  currentDate : Date;
  cmDate : Date;
  fromDateAssessment : Date;
  toDateAssessment : Date;
  hourFromSelect : string;
  hourToSelect : string;
  hourToDisabled : boolean;
  cmData : CmData;
  responseCmData : ResponseCmData;
  child : any;
  uhid : string;
  continousTabVisible : boolean;
  assessmentTabVisible : XYAxis;
  assessmentsChartVisible : Boolean;
  isLoaderVisible : boolean;
  loaderContent : string;
  medicationTabVisible : boolean;
  derivedDataTabVisible : boolean;
  outputDerivedDataTabVisible : boolean;
  derivedAllData : boolean;
  allBloodGasData : boolean;
  allHmLmData : boolean;
  allProcedureData : boolean;
  allMedicationData : boolean;
  allAssessments : boolean;
  medicationTypes : Array<any>;
  medicationTypeState = [];
  procedureTabVisible : boolean;
  bloodgasTabVisible : boolean;
  procedureTypeState = [];
  staticMonitorData = [
    { key: "HR", value: true, data_key: 'hrobject'},
    { key: "Spo2", value: true, data_key: 'spo2Object'},
    { key: "PR", value: true, data_key: 'probject'},
    // { key: "PEEP", value: true},
    { key: "RR", value: true, data_key: 'rrobject'},
    { key: "Renal SPO2", value: true, data_key: 'o3rSO2_1Object'},
    { key: "Cerebral SPO2", value: true, data_key: 'o3rSO2_2Object'},
    { key: "PI", value: true, data_key: 'piobject'},
  ];
  staticVentilatorData = [
    { key: "FIO2", value: true, data_key: 'fio2Object'},
    { key: "PIP", value: true, data_key: 'pipobject'},
    { key: "PEEP", value: true, data_key: 'peepobject'}
  ];
  staticDerivedData = [
    { key: "HR - Stvar", value: true, data_key: 'st_HRVObject'},
    { key: "HR - Ltvar", value: true, data_key: 'lt_HRVObject'},
    { key: "PR - Stvar", value: true, data_key: 'st_PRVObject'},
    { key: "PR - Ltvar", value: true, data_key: 'lt_PRVObject'},
    { key: "PhysiScore", value: true, data_key: 'physiscoreobject'}
  ];
  outputDerivedData = [
    { key: "HM/LM", value: true, data_key: 'hmStatusObject'}
  ]


  staticBloodGasData = [
    { key: "be", value: false},
    { key: "hco2", value: false},
    { key: "pco2", value: false},
    { key: "ph", value: false},
    { key: "po2", value: false},
    { key: "lactate", value: false},
    { key: "na", value: false},
    { key: "glucose", value: false},
    { key: "be_ecf", value: false},
    { key: "thbc", value: false},
    { key: "hct", value: false}

  ];


  staticAllChart = {
    monitor : true,
    ventilator : true
  };
  hrdata : Array<any>;
  prdata : Array<any>;
  rrdata : Array<any>;
  spo2data : Array<any>;
  physidata : Array<any>;
  ltvar_HR : Array<any>;
  stvar_HR : Array<any>;
  ltvar_PR : Array<any>;
  stvar_PR : Array<any>;
  pidata : Array<any>;
  renaldata : Array<any>;
  cerebraldata : Array<any>;
  offset : number;
  isDataAvailable : boolean;
  selector : string;
  hours : Array<string>;
  hourFrom : any;
  hourTo : any;
  minDate : String;
  maxDate : Date;
  trendFromDate : Date;
  trendToDate : Date;
  continousDataCount : number;
  continousData : Array<any>;
  continousColors : Array<any>;
  bloodGasDataCount : number;
  bloodGasData : Array<any>;
  bloodGasColors : Array<any>;
  initialDataPoint : any;
  finalDataPoint : any;
  @ViewChild('chartTwo') chartElement;
  @ViewChild('chartThree') chartGroup;
  @ViewChild('chartFour') chartScatter;
  @ViewChild('chartFive') chartContinous;
  @ViewChild('chartSix') chartDerived;
  @ViewChild('chartSeven') chartBloodGas;
  @ViewChild('chartEight') chartOutputDerived;


  chart: Highcharts.ChartObject;
  constructor(http: Http, router: Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
    this.fromDateAssessment = new Date();
    this.toDateAssessment = new Date(this.fromDateAssessment.getTime() + 24* 60*  60 * 1000);
    this.currentDate = new Date();
  	this.cmDate = new Date(this.currentDate.getTime() + 24* 60*  60 * 1000);
  	this.currentDate = this.cmDate;
  	this.cmDate = new Date(this.currentDate.getTime() - 24* 60*  60 * 1000);
  	this.currentDate = this.cmDate;
    this.hourToDisabled = false;
    this.cmData = new CmData;
    this.responseCmData = new ResponseCmData();
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
		this.uhid = this.child.uhid;
    // this.continousTabVisible = new XYAxis;
    this.assessmentTabVisible = new XYAxis;
    this.isLoaderVisible = false;
    this.loaderContent = 'Loading...';
    this.hrdata = [];
		this.rrdata = [];
		this.spo2data = [];
    this.physidata = [];
    this.prdata = [];
    this.physidata = [];
    this.ltvar_HR = [];
    this.stvar_HR = [];
    this.ltvar_PR = [];
    this.stvar_PR = [];
    this.derivedAllData = true;
    this.allBloodGasData = false;
    this.allHmLmData = false;
    this.allProcedureData = false;
    this.allMedicationData = false;
    this.allAssessments = false;
    this.pidata = [];
    this.renaldata = [];
    this.cerebraldata = [];
    this.isDataAvailable = false;
    this.continousDataCount = 0;
    this.continousData = [];
    this.continousColors = [];
    this.bloodGasDataCount = 0;
    this.bloodGasData = [];
    this.bloodGasColors = [];
    this.initialDataPoint = null;
    this.finalDataPoint = null;
    this.selector = "daily";
    this.hourFrom = "00:00:00";
    this.hourTo = "24:00:00";
    this.offset = new Date().getTimezoneOffset();
    this.hours = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];
    var selectHour = new Date().getHours();
    if(selectHour<10)
      {this.hourFromSelect = "0"+selectHour;}
    else
      {this.hourFromSelect = ""+selectHour;}

    if(selectHour+1<10)
      {this.hourToSelect = "0"+(selectHour+1);}
    else
      {this.hourToSelect = ""+(selectHour+1);}
      this.maxDate = new Date();
     var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
     var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
     var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
     this.trendToDate = new Date();
     this.trendFromDate = new Date(this.trendToDate.getTime() - (1000 * 60 * 60));

     if(tempHr == 12 && tempMer == 'AM'){
        tempHr = '00';
     }else if(tempHr != 12 && tempMer == 'PM'){
        tempHr = parseInt(tempHr) + 12;
     }
     var tempFullTime = tempHr +':' +tempMin+':00';
     this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
     var tempPrevTime = this.minDate.slice(16,24);
     this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
     this.cmData.medication = false;
     this.cmData.procedure = false;
     this.cmData.assessment = false;
     this.cmData.hmOutput = false;
     this.cmData.bloodGas = false;
     this.bloodgasTabVisible = false;
      this.progressiveDate(false);
      this.enableForm();
  }

  activeAssessments = function(){
    if(this.allAssessments){
      this.cmData.assessment = true;
      this.cmData.medication = false;
      this.cmData.procedure = false;
      this.cmData.hmOutput = false;
      this.cmData.bloodGas = false;
      this.assessmentsChartVisible = true;
      this.progressiveDate(true);
    }else{
      this.assessmentsChartVisible = false;
    }
  }

  progressiveDate = function(status : boolean) {
      if (this.hourFromSelect != null && this.hourFromSelect != undefined && this.hourFromSelect != "") {
				this.hourToDisabled = false;
				var arr = [];
				var minHour = parseInt(this.hourFromSelect);

				if (this.hourToSelect != null && this.hourToSelect != undefined && this.hourToSelect != ""){
					if(parseInt(this.hourToSelect)<=parseInt(this.hourFromSelect)){
						if((parseInt(this.hourFromSelect)+1)<10){
							this.hourToSelect = "0"+(parseInt(this.hourFromSelect)+1)+"";
						} else {
							this.hourToSelect = (parseInt(this.hourFromSelect)+1)+"";
						}
					}
				}

				minHour = minHour + 1;
				console.log(minHour);
				for (minHour; minHour <= 24; minHour++) {
					var temp = minHour.toString();
					if (temp.length == 1) {
						temp = "0" + temp;
					}
					arr.push(temp);
				}
				console.log(arr);
				this.hourToArr = arr;
				this.hourFrom = this.hourFromSelect;
				var serverFrom = (this.hourFrom * 60) + this.offset;
				var serverFromMin = (serverFrom % 60);
				var serverFromHr = (serverFrom - serverFromMin)/60;

				this.hourFrom = serverFromHr + ":" + serverFromMin + ":00";
				this.cmData.startTime = this.hourFrom;
				this.cmData.endTime = this.hourTo;
			}

			if (this.hourToSelect != null && this.hourToSelect != undefined && this.hourToSelect != "") {
				this.hourTo = this.hourToSelect;
        let tempHourTo =this.hourTo*1;
				var serverTo = (tempHourTo * 60) + this.offset;
				var serverToMin = (serverTo % 60);
				var serverToHr = (serverTo - serverToMin)/60;

				this.hourTo = serverToHr + ":" + serverToMin + ":00";
				this.cmData.startTime = this.hourFrom;
				this.cmData.endTime = this.hourTo;
			}

			this.getMonitoringData(this.cmDate, status);
		};

    getMonitoringData = function(entryTime, isGetAllData) {
    	this.currentDate = entryTime;
			this.cmData.todate = this.dateformatter(this.currentDate, "gmt", "standard");
			this.cmData.fromdate = this.dateformatter(this.currentDate, "gmt", "standard");

			//console.log("get hours"+this.cmDate.getHours());

			this.cmData.uhid = this.uhid;
      console.log(this.cmData.uhid);
			if (this.selector == 'hourly') {
				this.cmData.daily = false;
    		 this.cmData.startTime = this.hourFrom;
				 this.cmData.endTime = this.hourTo;
				if(this.cmData.endTime === "24:00:00"){
					this.cmData.endTime = "23:59:59";
				}
			} else {
				this.cmData.daily = true;
				this.cmData.startTime = "00:00:00";
				this.cmData.endTime = "23:59:59";
			}
			console.log(this.cmData);
		this.enableForm();

    if(!isGetAllData){
      this.cmData.medication = false;
      this.cmData.procedure = false;
      this.cmData.assessment = false;
      this.cmData.bloodGas = false;
      this.cmData.hmOutput = false;

    }
		//	var tempScrollTop = $(window).scrollTop();
    this.isLoaderVisible = true;
    this.loaderContent = 'Loading...';

    try
    {
      this.http.post(this.apiData.getcmData + this.trendFromDate + "/" + this.trendToDate + "/dummy",
        this.cmData).subscribe(res => {
          this.responseCmData =res.json();
          console.log(this.responseCmData);
          this.isLoaderVisible = false;
          this.loaderContent = '';
          this.cmData = this.responseCmData;
          if(this.responseCmData.assessment != true && this.responseCmData.medication != true && this.responseCmData.procedure != true && this.responseCmData.bloodGas != true && this.responseCmData.hmOutput != true){
            if(this.responseCmData.all.xAxis!=null && this.responseCmData.all.xAxis.length>0)
              this.isDataAvailable = true;
            else
              this.isDataAvailable = false;
            console.log("areas");
            if(this.cmData.all != null && this.cmData.all != undefined){
              console.log(this.cmData.all.xAxis);
            }

            this.rrdata = [];
            this.spo2data = [];
            this.physidata= [];
            this.fio2data = [];
            this.peepdata = [];
            this.pipdata = [];
            this.prdata = [];
            this.ltvar_HR = [];
            this.stvar_HR = [];
            this.ltvar_PR = [];
            this.stvar_PR = [];
            this.pidata = [];
            this.renaldata = [];
            this.cerebraldata = [];

            // this.continousTabVisible.all = true;
            this.assessmentTabVisible.all = true;
            //this.medicationTabVisible.all = true;
            // this.procedureTabVisible.all = true;
            // this.procedureChartVisible('all',this.procedureTabVisible.all);
          if(this.cmData.st_HRVObject == null && this.cmData.lt_HRVObject == null && this.cmData.st_PRVObject == null &&
             this.cmData.lt_PRVObject == null && this.physiscoreobject == null && this.cmData.hmStatusObject == null){
             this.derivedDataTabVisible = false;
          }else{
            this.derivedDataTabVisible = true;
          }

          this.continousChartVisible();
          this.derivedChartVisible();
        }
        if(this.responseCmData.assessment == true){
          this.assessmentChartVisible('all',this.assessmentTabVisible.all);
        }
        if(this.responseCmData.bloodGas == true){
          this.bloodGasChartVisible();
        }
        if(this.responseCmData.procedure == true){
          this.filterUniqueProcedure();
          this.procedureChartVisible();
        }
        if(this.responseCmData.medication == true){
          this.filterUniqueMedicine();
          this.medicationChartVisible();
        }
        if(this.responseCmData.hmOutput == true){
          this.outputChartVisible();
        }

        //	$(window).scrollTop(tempScrollTop);
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

    // below method used for enable and all checkboxes for ventilator and monitor
    enableAllPhsyologicalCheckboxes(mode){
      console.log(mode);
//check if mode is monitor
      if(mode == 'monitor'){
        if(!this.staticAllChart.monitor){
          this.staticMonitorData.forEach(res=>{
            res.value = false;
          })
        }
        else{
          this.staticMonitorData.forEach(res=>{
            res.value = true;
          })
        }
      }
//check if mode is ventilator
      if(mode == 'ventilator'){
        if(!this.staticAllChart.ventilator){
          this.staticVentilatorData.forEach(res=>{
            res.value = false;
          })
        }
        else{
          this.staticVentilatorData.forEach(res=>{
            res.value = true;
          })
        }
      }
      this.continousChartVisible();


    }

    pushContinuousData = function(physilogicalData,parameter){

      if(physilogicalData != null && physilogicalData.length > 0){
        console.log(physilogicalData, parameter);
        console.log(this.continousDataCount)
        this.continousDataCount = this.continousDataCount + 1;
        var tempData = {
          data: physilogicalData,
          lineWidth: 0.5,
          name: parameter
        }
        this.continousData.push(tempData);
        console.log(this.continousData);
        // const activeContinousData = this.staticMonitorData.filter(res=>{
        //   if(res.)
        // })
        // this.
        // console.log(this.continousTypeState);
      }
    }

    // below code is used for rendering phsyological data
     continousChartVisible = function(){

       if(this.cmData.spo2Object != null && this.cmData.spo2Object.length > 0){
         this.initialDataPoint = this.cmData.spo2Object[0][0];
         this.initialDataPoint = new Date(this.initialDataPoint - 19800000);
         var size = this.cmData.spo2Object.length;
         this.finalDataPoint = this.cmData.spo2Object[size - 1][0];
         this.finalDataPoint = new Date(this.finalDataPoint - 19800000);
       }



       this.continousDataCount = 0;
       this.continousData = [];
       this.continousColors = [];
       var colorsList = ["#90ed7d","#d08e44","#7cb5ec","#b0de93","#b96262","#39bd6b","#f7a35c","#f15c80","#6d747f","#a0709d","#6e9940","#9973d6","#cafe69","#000000"];
       const activatedStaticMonitorCharts = this.staticMonitorData.filter(res => res.value);
       const activatedStaticVentilatorCharts = this.staticVentilatorData.filter(res => res.value);
       const compiledActivatedCharts = [...activatedStaticMonitorCharts, ...activatedStaticVentilatorCharts];

       compiledActivatedCharts.forEach(res => this.pushContinuousData(this.cmData[res.data_key], res.key));


      //  this.pushContinuousData(this.cmData.hrobject, "HR");
      //  this.pushContinuousData(this.cmData.probject, "PR");
      //  this.pushContinuousData(this.cmData.spo2Object, "Spo2");
      //  this.pushContinuousData(this.cmData.piobject, "PI");
      //  this.pushContinuousData(this.cmData.rrobject, "RR");
      //  this.pushContinuousData(this.cmData.peepobject, "PEEP");
      //  this.pushContinuousData(this.cmData.pipobject, "PIP");
      //  this.pushContinuousData(this.cmData.fio2Object, "Fi02");
      //  this.pushContinuousData(this.cmData.o3rSO2_1Object, "Renal");
      //  this.pushContinuousData(this.cmData.o3rSO2_2Object, "Cerebral");
      //  this.pushContinuousData(this.cmData.st_HRVObject, "HR - Stvar");
      //  this.pushContinuousData(this.cmData.lt_HRVObject, "HR - Ltvar");
      //  this.pushContinuousData(this.cmData.st_PRVObject, "PR - Stvar");
      //  this.pushContinuousData(this.cmData.lt_PRVObject, "PR - Ltvar");
      for(var i = 0;i < this.continousDataCount; i++){
         this.continousColors.push(colorsList[i]);
      }
       if(compiledActivatedCharts.length>0){
        this.continousTabVisible = true;
       }else{
        this.continousTabVisible = false;
       }

       var contChart = {

           chart: {
               zoomType: 'x'
           },

           title: {
               text: 'Highcharts drawing points',
               align: 'left'
           },

           subtitle: {
               text: 'Using the Boost module',
                align: 'right',
                y: 14
           },

           tooltip: {
               valueDecimals: 2
           },

           xAxis: {
               type: 'datetime'
           },
           colors: this.continousColors,

           series: this.continousData

       };


          const chart6 = chart(this.chartContinous.nativeElement,contChart);
  }

  pushBloodGasData = function(parameter){

    if(this.cmData.bloodGasList != null && this.cmData.bloodGasList.length > 0){
      this.bloodGasDataCount = this.bloodGasDataCount + 1;
      var masterData = [];
      this.bloodgasTabVisible = true;
      for(var i=0;i<this.cmData.bloodGasList.length;i++){
        var data = [];
        var yvalue = null;
        var xvalue = null;
        xvalue = this.cmData.bloodGasList[i].entryDate;
        if(parameter == "be")
          yvalue = parseFloat(this.cmData.bloodGasList[i].be);
        if(parameter == "hco2")
          yvalue = parseFloat(this.cmData.bloodGasList[i].hco2);
        if(parameter == "hct")
          yvalue = parseFloat(this.cmData.bloodGasList[i].hct);
        if(parameter == "thbc")
          yvalue = parseFloat(this.cmData.bloodGasList[i].thbc);
        if(parameter == "ph")
          yvalue = parseFloat(this.cmData.bloodGasList[i].ph);
        if(parameter == "po2")
          yvalue = parseFloat(this.cmData.bloodGasList[i].po2);
        if(parameter == "na")
          yvalue = parseFloat(this.cmData.bloodGasList[i].na);
        if(parameter == "lactate")
          yvalue = parseFloat(this.cmData.bloodGasList[i].lactate);
        if(parameter == "glucose")
          yvalue = parseFloat(this.cmData.bloodGasList[i].glucose);
        if(parameter == "be_ecf")
          yvalue = parseFloat(this.cmData.bloodGasList[i].be_ecf);
        if(parameter == "pco2")
          yvalue = parseFloat(this.cmData.bloodGasList[i].pco2);
        data.push(xvalue);
        data.push(yvalue);
        masterData.push(data);
      }
      var tempData = {
        data: masterData,
        lineWidth: 0.5,
        name: parameter
      }
      this.bloodGasData.push(tempData);
      console.log(this.bloodGasData);
    }else{
      this.bloodgasTabVisible = false;
    }
  }

  // below code is used for rendering blood gas data
   bloodGasChartVisible = function(){

     this.bloodGasDataCount = 0;
     this.bloodGasData = [];
     this.bloodGasColors = [];
     var colorsList = ["#90ed7d","#d08e44","#7cb5ec","#b0de93","#b96262","#39bd6b","#f7a35c","#f15c80","#6d747f","#a0709d","#6e9940","#9973d6","#cafe69","#000000"];
     const activeBloodGasCheckboxes = this.staticBloodGasData.filter(res=>res.value);
     activeBloodGasCheckboxes.forEach(element => this.pushBloodGasData(element.key));

    for(var i = 0;i < this.bloodGasDataCount; i++){
       this.bloodGasColors.push(colorsList[i]);
    }

     var bloodChart = {

         chart: {
             zoomType: 'x'
         },

         title: {
             text: 'Highcharts drawing points'
         },

         subtitle: {
             text: 'Using the Boost module'
         },

         tooltip: {
             valueDecimals: 2
         },

         xAxis: {
             type: 'datetime'
         },
         colors: this.bloodGasColors,

         series: this.bloodGasData

     };


        const chart8 = chart(this.chartBloodGas.nativeElement,bloodChart);
      }
  //below code is used for activate all blood gas checkboxes
    enableAllBloodGasCheckboxes(){
      if(this.allBloodGasData) {
        this.cmData.bloodGas = true;
        this.cmData.medication = false;
        this.cmData.procedure = false;
        this.cmData.assessment = false;
        this.cmData.hmOutput = false;
        this.staticBloodGasData.forEach(res=>res.value=true);
        this.progressiveDate(true);

      }else{
        this.staticBloodGasData.forEach(res=>res.value=false);
        this.bloodGasChartVisible();
      }
    }

    enableOutputParameterCheckboxes(){
      if(this.allHmLmData) {
        this.cmData.hmOutput = true;
        this.cmData.medication = false;
        this.cmData.procedure = false;
        this.cmData.assessment = false;
        this.cmData.bloodGas = false;
        this.outputDerivedData.forEach(res=>res.value=true);
        this.progressiveDate(true);

      }else{
        this.outputDerivedData.forEach(res=>res.value=false);
        this.outputChartVisible();
      }
    }

    outputChartVisible = function(){
      let colorsList = ["#a0709d"];
      const activatedStaticDerivedCharts = this.outputDerivedData.filter(res => res.value);
      const localDerivedDataArray =  activatedStaticDerivedCharts.map(res=> {
        return  {
          data: this.cmData[res.data_key],
          lineWidth: 0.5,
          name: res.key
        };
      })
      if(localDerivedDataArray.length > 0){
        this.outputDerivedDataTabVisible = true;
      }

      var outputDerivedChart = {

        chart: {
            zoomType: 'x'
        },

        title: {
            text: 'Highcharts drawing points'
        },

        subtitle: {
            text: 'Using the Boost module'
        },

        tooltip: {
            valueDecimals: 2
        },

        xAxis: {
            type: 'datetime'
        },
        colors: colorsList,

        series: localDerivedDataArray

    };
       const chart9 = chart(this.chartOutputDerived.nativeElement,outputDerivedChart);

    }
  // below code is used for rendering derived data

    derivedChartVisible = function(){
      let colorsList = ["#a0709d","#6e9940","#9973d6","#cafe69","#000000","#39bd6b"];
      const activatedStaticDerivedCharts = this.staticDerivedData.filter(res => res.value);
      const localDerivedDataArray =  activatedStaticDerivedCharts.map(res=> {
        return  {
          data: this.cmData[res.data_key],
          lineWidth: 0.5,
          name: res.key
        };
      })
      if(localDerivedDataArray.length > 0){
        this.derivedDataTabVisible = true;
      }

      var derivedChart = {

        chart: {
            zoomType: 'x'
        },

        title: {
            text: 'Highcharts drawing points'
        },

        subtitle: {
            text: 'Using the Boost module'
        },

        tooltip: {
            valueDecimals: 2
        },

        xAxis: {
            type: 'datetime'
        },
        colors: colorsList,

        series: localDerivedDataArray

    };
       const chart7 = chart(this.chartDerived.nativeElement,derivedChart);

    }

  //below code is used for activate all derived data checkboxes
    enableAllDerivedDataCheckboxes(){
      if(this.derivedAllData){
        this.staticDerivedData.forEach(res=>res.value = true)
        this.derivedChartVisible();
      }else{
        this.staticDerivedData.forEach(res=>res.value = false)
        this.derivedChartVisible();
      }
    }




     assessmentChartVisible = function(whichChart,ChartSectionValue){

     console.log(whichChart);
     console.log(ChartSectionValue);
      var cmSeries = [];
      var xaxis = [];
      //var tempScrollTop = $(window).scrollTop();
    if(whichChart!='all'){


      if(this.assessmentTabVisible.sepsis == true || this.assessmentTabVisible.asphyxia == true || this.assessmentTabVisible.seizures == true
               || this.assessmentTabVisible.rds || this.assessmentTabVisible.apnea == true || this.assessmentTabVisible.pphn == true
               || this.assessmentTabVisible.pneumothorax == true || this.assessmentTabVisible.jaundice == true)
           {
            this.assessmentTabVisible.all = false;
          }

      }
      else{
        if(ChartSectionValue==false)
        {
          this.assessmentTabVisible.sepsis = false;
          this.assessmentTabVisible.asphyxia = false;
          this.assessmentTabVisible.seizures = false;
          this.assessmentTabVisible.apnea = false;
          this.assessmentTabVisible.pphn = false;
          this.assessmentTabVisible.pneumothorax = false;
          this.assessmentTabVisible.rds = false;
          this.assessmentTabVisible.jaundice = false;}
        }

        if(this.assessmentTabVisible.all){
          this.assessmentTabVisible.sepsis = true;
          this.assessmentTabVisible.rds = true;
          this.assessmentTabVisible.asphyxia = true;
          this.assessmentTabVisible.seizures = true;
          this.assessmentTabVisible.apnea = true;
          this.assessmentTabVisible.pphn = true;
          this.assessmentTabVisible.pneumothorax = true;
          this.assessmentTabVisible.jaundice = true;
        }

        var assessmentData = [];
        var assessmentTypes  = [];
        var assessmentYLevel = -1;
        if(this.assessmentTabVisible.jaundice && this.cmData.jaundice != null && this.cmData.jaundice.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Jaundice");
          for(var i = 0;i<this.cmData.jaundice.length;i++){
            var startDate = new Date(this.cmData.jaundice[i].startDate);
            var endDate = new Date(this.cmData.jaundice[i].endDate);
            var jaundiceTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(jaundiceTempData);
          }
        }
        if(this.assessmentTabVisible.sepsis && this.cmData.sepsis != null && this.cmData.sepsis.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Sepsis");
          for(var i = 0;i<this.cmData.sepsis.length;i++){
            var startDate = new Date(this.cmData.sepsis[i].startDate);
            var endDate = new Date(this.cmData.sepsis[i].endDate);
            var sepsisTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(sepsisTempData);
          }
        }
        if(this.assessmentTabVisible.rds && this.cmData.rds != null && this.cmData.rds.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("RDS");
          for(var i = 0;i<this.cmData.rds.length;i++){
            var startDate = new Date(this.cmData.rds[i].startDate);
            var endDate = new Date(this.cmData.rds[i].endDate);
            var rdsTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(rdsTempData);
          }
        }
        if(this.assessmentTabVisible.pphn && this.cmData.pphn != null && this.cmData.pphn.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("PPHN");
          for(var i = 0;i<this.cmData.pphn.length;i++){
            var startDate = new Date(this.cmData.pphn[i].startDate);
            var endDate = new Date(this.cmData.pphn[i].endDate);
            var pphnTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(pphnTempData);
          }
        }
        if(this.assessmentTabVisible.apnea && this.cmData.apnea != null && this.cmData.apnea.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Apnea");
          for(var i = 0;i<this.cmData.apnea.length;i++){
            var startDate = new Date(this.cmData.apnea[i].startDate);
            var endDate = new Date(this.cmData.apnea[i].endDate);
            var apneaTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(apneaTempData);
          }
        }
        if(this.assessmentTabVisible.pneumothorax && this.cmData.pneumothorax != null && this.cmData.pneumothorax.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Pneumothorax");
          for(var i = 0;i<this.cmData.pneumothorax.length;i++){
            var startDate = new Date(this.cmData.pneumothorax[i].startDate);
            var endDate = new Date(this.cmData.pneumothorax[i].endDate);
            var pneumothoraxTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(pneumothoraxTempData);
          }
        }
        if(this.assessmentTabVisible.asphyxia && this.cmData.asphyxia != null && this.cmData.asphyxia.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Asphyxia");
          for(var i = 0;i<this.cmData.asphyxia.length;i++){
            var startDate = new Date(this.cmData.asphyxia[i].startDate);
            var endDate = new Date(this.cmData.asphyxia[i].endDate);
            var asphyxiaTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(asphyxiaTempData);
          }
        }
        if(this.assessmentTabVisible.seizures && this.cmData.seizures != null && this.cmData.seizures.length > 0){
          assessmentYLevel = assessmentYLevel + 1;
          assessmentTypes.push("Seizures");
          for(var i = 0;i<this.cmData.seizures.length;i++){
            var startDate = new Date(this.cmData.seizures[i].startDate);
            var endDate = new Date(this.cmData.seizures[i].endDate);
            var seizuresTempData = {
              x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
              x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
              y : assessmentYLevel
            }
            assessmentData.push(seizuresTempData);
          }
        }
        if(assessmentTypes != undefined){
          if(assessmentTypes.length<=0){
            this.assessmentsChartVisible = false;
          }else{
            this.assessmentsChartVisible = true;
          }
        }

      var testChart = {
        chart: {
            type: 'xrange'
        },
        title: {
            text: 'Highcharts X-range'
        },
        xAxis: {
          type: 'datetime'
        },
        yAxis: {
            title: {
                text: ''
            },
            categories: assessmentTypes,
            reversed: true
        },
        series: [{
            name: 'Time',
            // pointPadding: 0,
            // groupPadding: 0,
            borderColor: 'gray',
            pointWidth: 10,
            data: assessmentData
        }]
    };
    const chart3 = chart(this.chartElement.nativeElement,testChart)
  }
//filter unique medicine from medicine list
  filterUniqueMedicine(){
    if (!this.cmData.medicineList) {
      this.cmData.medicineList = [];
    }
    const uniqueMedicineList = new Set(this.cmData.medicineList.map(res => res.medicinename));
    console.log(uniqueMedicineList);
    this.medicationTypeState = Array.from(uniqueMedicineList).map(res => {
      return {key: res, value: true};
    });
    console.log(this.medicationTypeState);

  }

  medicationChartVisible = function(){
     var medicationData = [];
     var medicationYLevel = -1;
     if(this.cmData.medicineList != null && this.cmData.medicineList.length > 0){
      this.medicationTabVisible = true;
      medicationYLevel = medicationYLevel + 1;
      this.medicationTypes = this.medicationTypeState.filter(res => res.value).map(res => res.key);


       for(var i = 0;i<this.cmData.medicineList.length;i++){
         if(this.medicationTypes.includes(this.cmData.medicineList[i].medicinename)){
           medicationYLevel = this.medicationTypes.indexOf(this.cmData.medicineList[i].medicinename);
         }
         var startDate = new Date(this.cmData.medicineList[i].startDate);
         var endDate = new Date(this.cmData.medicineList[i].endDate);
         var jaundiceTempData = {
           x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
           x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
           y : medicationYLevel
         }
         medicationData.push(jaundiceTempData);
       }
     }
     if(this.medicationTypes != undefined){
      if(this.medicationTypes.length<=0){
        this.medicationTabVisible = false;
      }
    }

   var medicationChart = {
     chart: {
         type: 'xrange'
     },
     title: {
         text: 'Highcharts X-range'
     },
     xAxis: {
       type: 'datetime'
     },
     yAxis: {
         title: {
             text: ''
         },
         categories: this.medicationTypes,
         reversed: true
     },
     series: [{
         name: 'Time',
         // pointPadding: 0,
         // groupPadding: 0,
         borderColor: 'gray',
         pointWidth: 10,
         data: medicationData
     }]
  };
  const chart4 = chart(this.chartGroup.nativeElement,medicationChart)

  }

  //below code is used for activate all medication checkboxes
  enableAllMedicationCheckboxes(){
    if(this.allMedicationData){
      this.cmData.medication = true;
      this.cmData.procedure = false;
      this.cmData.assessment = false;
      this.cmData.hmOutput = false;
      this.cmData.bloodGas = false;
      this.medicationTypeState.forEach(res=>res.value=true);
      this.progressiveDate(true);
    }else{
      this.medicationTypeState.forEach(res=>res.value=false);
      this.medicationChartVisible();
    }
  }

  // below code is used for filter unique procedure from procedure list
  filterUniqueProcedure(){
    if (!this.cmData.procedureList) {
      this.cmData.procedureList = [];
    }
    const uniqueProcedureList = new Set(this.cmData.procedureList.map(res => res.medicinename));
    console.log(uniqueProcedureList);
    this.procedureTypeState = Array.from(uniqueProcedureList).map(res=>{
      return {key:res, value: true};
    })
    console.log(this.procedureTypeState);

  }

  // below code is used for rendering procedure data
  procedureChartVisible(){
    var procedureData = [];
    var procedureTypes  = [];
    var procedureYLevel = -1;
     if(this.cmData.procedureList != null && this.cmData.procedureList.length > 0){
       this.procedureTabVisible = true;
      procedureYLevel = procedureYLevel + 1;
      procedureTypes = this.procedureTypeState.filter(res=>res.value).map(res => res.key);
       for(var i = 0;i<this.cmData.procedureList.length;i++){
         if(procedureTypes.includes(this.cmData.procedureList[i].medicinename)){
           procedureYLevel = procedureTypes.indexOf(this.cmData.procedureList[i].medicinename);
         }
         var startDate = new Date(this.cmData.procedureList[i].startDate);
         var endDate = new Date(this.cmData.procedureList[i].endDate);
         var jaundiceTempData = {
           x: Date.UTC(startDate.getUTCFullYear(),startDate.getUTCMonth(),startDate.getUTCDate(),startDate.getHours(),startDate.getMinutes()),
           x2: Date.UTC(endDate.getUTCFullYear(),endDate.getUTCMonth(),endDate.getUTCDate(), endDate.getHours(),endDate.getMinutes()),
           y : procedureYLevel
         }
         procedureData.push(jaundiceTempData);
       }
     }
     if(procedureTypes != undefined){
      if(procedureTypes.length<=0){
        this.procedureTabVisible = false;
      }
    }


     var procedureChart = {
       chart: {
           type: 'xrange'
       },
       title: {
           text: 'Highcharts X-range'
       },
       xAxis: {
         type: 'datetime'
       },
       yAxis: {
           title: {
               text: ''
           },
           categories: procedureTypes,
           reversed: true
       },
       series: [{
           name: 'Time',
           // pointPadding: 0,
           // groupPadding: 0,
           borderColor: 'gray',
           pointWidth: 10,
           data: procedureData
       }]
    };

    const chart5 = chart(this.chartScatter.nativeElement,procedureChart);

  }
  //below code is used for activate all procedure checkboxes
  enableAllProcedureCheckboxes(){
    if(this.allProcedureData){
      this.cmData.procedure = true;
      this.cmData.medication = false;
      this.cmData.assessment = false;
      this.cmData.hmOutput = false;
      this.cmData.bloodGas = false;
      this.procedureTypeState.forEach(res=>res.value=true);
      this.progressiveDate(true);
    }else{
      this.procedureTypeState.forEach(res=>res.value=false);
      this.procedureChartVisible();
    }
  }

    enableForm = function() {
    			if(!this.cmDate)
    				{this.cmDate = new Date();}

    			var selectDateStandard = this.dateformatter(new Date(this.cmDate), "gmt", "standard");
    			if (selectDateStandard == this.minCmDate) {
    				this.previousDisable = true;
    				this.nextDisable = false;
    			} else if (selectDateStandard == this.maxCmDate) {
    				this.previousDisable = false;
    				this.nextDisable = true;
    			} else {
    				this.nextDisable = false;
    				this.previousDisable = false;

    			}
    			if (selectDateStandard == this.minCmDate && selectDateStandard == this.maxCmDate) {
    				this.nextDisable = true;
    				this.previousDisable = true;
          }
    		};
dateformatter = function(date :Date, inFormat : string, outFormat :string){
  if(inFormat == "gmt"){
    if (outFormat == "utf")
    {
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
  ngOnInit() {

  }

}
