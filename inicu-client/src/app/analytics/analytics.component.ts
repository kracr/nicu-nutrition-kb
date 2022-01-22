import { Component, OnInit } from '@angular/core';
import { Chart } from 'angular-highcharts';
import { Pipe } from '@angular/core';
import { Router } from '@angular/router';
import { Sin } from './sin';
import { SinData } from './sinData';
import { QIData } from './qiData';
import { QI } from './qi';
import { Usage } from './usage';
import { Adoption } from './adoption';
import { APIurl } from '../../../model/APIurl';
import { UsageData } from './usageData';
import { Http, Response } from '@angular/http';
import { ExportCsv } from '../export-csv';
import * as $ from 'jquery/dist/jquery.min.js';
import { AnalyticsSearch } from './analyticsSearch';
import { Keyvalue } from '../userpanel/Keyvalue';

//import { Inject } from '@angular/core';
//import { Chart } from 'angular-highcharts';

@Component({
  selector: 'analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.css']
///  providers: [Chart]
})
export class AnalyticsComponent implements OnInit {
  chart : Chart;
  docChart : Chart;
  nurChart : Chart;
  analyticsChart : Chart;
  apiData : APIurl;
  maxFromDate : Date;
  minFromDate : Date;
  minToDate : Date;
  maxToDate : Date;
  maxDate : Date;
	http: Http;
	oneDay : number;
	sin: Sin;
  qi : QI;
  qiList : QIData;
  usage : Usage;
  usageData : UsageData;
  adoption : Adoption;
  keyValue : Keyvalue;
	isUsage : string;
	isQuality : string;
	isSinSheet : string;
	isAdoption : string;
	totalPatients : number;
	admitted : number;
	discharged : number;
	otherTableFlag : boolean;
  noOfBabies : any[];
  IV : Array<any>;
  nonInvasiveVenti : Array<any>;
  allFormVenti : Array<any>;
  catheters : Array<any>;
  cannula : Array<any>;
  IVfluids : Array<any>;
  PN : Array<any>;
  showToWeightError : boolean;
  rangeGestFrom : Array<number>;
  rangeGestTo : Array<number>;
  usageToggle : string;
  vitalToggle : string;
  router : Router;
  report : any;
  tempResp : any;
  usageGraphList : any;
  doctorAdoptionList : any;
  nurseAdoptionList : any;
  expanded : boolean;
  adoptionToggle : string;
  sinDataList : any;
  vitalFromDate : any;
  vitalToDate : any;
  vitalList : any;
  isLoaderVisible : boolean;
  loaderContent : string;
  branchName : string;
  isVital : string;
  isOccupancy : string;
  monthMapper : any;
  usageOption : string;
  analyticsSearch : AnalyticsSearch;
  dischargeTypes : any;
  assessmentTypes : any;
  dischargeTypeArrayList : any;
  assessmentTypeArrayList : any;
  totalPatientsOccupancy : number;
//  chart : Chart;

	constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Analyzing the data...';
    this.analyticsSearch = new AnalyticsSearch();
	// @Inject(Chart) chart: Chart
    this.apiData = new APIurl();
    this.http = http;
    this.router = router;
  //  this.chart = chart;
		this.sin = new Sin();
		this.oneDay = 24*60*60*1000;
		this.maxDate = new Date();
		this.isUsage = "false";
		this.isQuality = "false";
		this.isSinSheet = "true";
		this.isAdoption = "false";
		this.totalPatients = 20;
		this.admitted = 12;
		this.discharged = 8;
		this.otherTableFlag = false;
    this.noOfBabies = [];
    this.IV = [];
    this.nonInvasiveVenti = [];
    this.allFormVenti = [];
    this.catheters = [];
    this.cannula = [];
    this.PN = [];
    this.keyValue = new Keyvalue();
    this.IVfluids = [];
    this.qi = new QI();
    this.qiList = new QIData();
    this.showToWeightError = false;
    this.rangeGestFrom = [];
    this.rangeGestTo = [];
    this.usage = new Usage();
    this.adoption = new Adoption();
    this.usageData = new UsageData();
    this.usageToggle = "doctor-panel";
    this.vitalToggle = "vital-parameters";
    this.adoptionToggle = "doctor-panel";
    this.sinDataList = {};
    this.vitalToDate = new Date();
    this.vitalToDate.setHours(8);
    this.expanded = false;
    this.vitalToDate.setMinutes(0);
    this.vitalToDate.setSeconds(0);
    this.vitalToDate.setMilliseconds(0);
    this.vitalFromDate = new Date(this.vitalToDate.getTime() - this.oneDay);
    this.vitalFromDate.setHours(8);
    this.vitalFromDate.setMinutes(0);
    this.vitalFromDate.setSeconds(0);
    this.vitalFromDate.setMilliseconds(0);
    this.maxFromDate = this.vitalToDate;
    this.maxToDate = new Date();
    this.minFromDate = new Date(this.maxFromDate.getTime() - 365*24*60*60*1000);
    this.minToDate = this.vitalFromDate;
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.usageOption = "list";
    this.dischargeTypes = [];
    this.assessmentTypes = [];
    this.dischargeTypeArrayList = [];
    this.assessmentTypeArrayList = [];
    this.totalPatientsOccupancy = 0;
    this.init();
	}
	init() {
	console.log("Analytics controller ts init");
    let isCommingFromPrintSinSheetValue = localStorage.getItem('isCommingFromPrintSinSheetValue');
    if(isCommingFromPrintSinSheetValue != null){
      if(isCommingFromPrintSinSheetValue){
        this.isQuality = "false";
        this.isSinSheet = "true";
        this.isUsage = "false";
       localStorage.removeItem("isCommingFromPrintSinSheet");
      }
    }
	let isCommingFromPrintUsageValue = JSON.parse(localStorage.getItem('isCommingFromPrintUsage'));
    console.log(isCommingFromPrintUsageValue);
    if(isCommingFromPrintUsageValue != null){
      console.log(typeof(isCommingFromPrintUsageValue));
      if(isCommingFromPrintUsageValue){
        this.isQuality = "false";
        this.isUsage = "true";
        this.isSinSheet = "false";
      localStorage.removeItem("isCommingFromPrintUsage");
      }
    }
    this.getSinSheetData(this.sin.fromDate, this.sin.toDate, true, false);
    this.getVitalData(this.vitalFromDate, this.vitalToDate);
    this.getQISheet(this.qi.fromDate, this.qi.toDate, null, null, null,null);
    this.getrangeGestTo();
    this.getrangeGestFrom();
    this.getAnalyticsUsage(this.usage.fromDate, this.usage.toDate, false);
    this.getOccupancyData(this.adoption.fromDate, this.adoption.toDate);
    this.getAnalyticsData();

    var dischargeTypeList = ["LAMA", "Death", "Discharge", "Transfer", "Discharge On Request"];
    for(var i=0 ; i< dischargeTypeList.length; i++){
      this.keyValue = new Keyvalue();
      this.keyValue.key = dischargeTypeList[i];
      this.keyValue.value = dischargeTypeList[i];
      this.dischargeTypes.push(this.keyValue);
    }

    var assessmentTypeList = ["Respiratory Distress", "Apnea", "PPHN", "Pneumothorax", "Jaundice", "Sepsis", "Asphyxia", "Seizures"];
    for(var i=0 ; i< assessmentTypeList.length; i++){
      this.keyValue = new Keyvalue();
      this.keyValue.key = assessmentTypeList[i];
      this.keyValue.value = assessmentTypeList[i];
      this.assessmentTypes.push(this.keyValue);
    }


	}
  fromDateValidation(){
    if(this.vitalFromDate != null){
      this.minToDate = new Date(this.vitalFromDate);
    }
  }
  toDateValidation(){
    if(this.vitalToDate != null){
      this.maxFromDate = new Date(this.vitalToDate);
    }
  }

  getVitalData = function(fromDate : Date, toDate : Date){
    if(this.vitalToDate == null || this.vitalFromDate == null){
      return false;
    }
    else{
      if(this.isVital == "true"){
        this.isLoaderVisible = true;
        this.loaderContent = 'Analyzing the data...';
      }
      var fromParameter = fromDate.getTime();
      var toParameter = toDate.getTime();
      try{
          this.http.request(this.apiData.getVitalData + fromParameter + "/" + toParameter + "/" + this.branchName + "/",)
          .subscribe(res => {
            this.vitalList = res.json();
            this.isLoaderVisible = false;
            this.loaderContent = '';
          }, err => {
              console.log("Error occured.")
          });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
  }

  systemicReport() {
		console.log("comming into the systemicReport");
		var fromParameter = null;
		var toParameter = null;
    try{
      this.http.request(this.apiData.getSystemicReport + fromParameter + "/" + toParameter + "/" + this.branchName + "/").subscribe((res: Response) => {
			this.report = res.json();
			for(var index = 0; index < this.report.systemicList.length; index++) {
				var obj = this.report.systemicList[index];
				for(var jaundice = 1; jaundice <= this.report.maxJaundice; jaundice++) {
					if(jaundice <= obj.no_jaundice) {
						var jaundiceObj = Object.assign({}, obj.jaundiceList[jaundice - 1]);
						obj["Ep_" + jaundice + "_jaundice_onset"] = jaundiceObj.jaundice_onset;
						obj["Ep_" + jaundice + "_jaundice_duration"] = jaundiceObj.jaundice_duration;
						obj["Ep_" + jaundice + "_jaundice_cause"] = jaundiceObj.jaundice_cause;
						obj["Ep_" + jaundice + "_jaundice_phototherapy"] = jaundiceObj.jaundice_phototherapy;
						obj["Ep_" + jaundice + "_phototherapy_duration_hrs"] = jaundiceObj.phototherapy_duration_hrs;
						obj["Ep_" + jaundice + "_jaundice_exchange"] = jaundiceObj.jaundice_exchange;
					} else {
						obj["Ep_" + jaundice + "_jaundice_onset"] = null;
						obj["Ep_" + jaundice + "_jaundice_duration"] = null;
						obj["Ep_" + jaundice + "_jaundice_cause"] = null;
						obj["Ep_" + jaundice + "_jaundice_phototherapy"] = null;
						obj["Ep_" + jaundice + "_phototherapy_duration_hrs"] = null;
						obj["Ep_" + jaundice + "_jaundice_exchange"] = null;
					}
				}
				delete obj['jaundiceList'];

				for(var rds = 1; rds <= this.report.maxRds; rds++) {
					if(rds <= obj.no_rds) {
						var rdsObj = Object.assign({}, obj.rdsList[rds - 1]);
						obj["Ep_" + rds + "_rds_onset"] = rdsObj.rds_onset;
						obj["Ep_" + rds + "_rds_duration"] = rdsObj.rds_duration;
						obj["Ep_" + rds + "_rds_cause"] = rdsObj.rds_cause;
						obj["Ep_" + rds + "_max_downes"] = rdsObj.max_downes;
						obj["Ep_" + rds + "_resp_support"] = rdsObj.resp_support;
						obj["Ep_" + rds + "_duration_lowflow"] = rdsObj.duration_lowflow;
						obj["Ep_" + rds + "_duration_highflow"] = rdsObj.duration_highflow;
						obj["Ep_" + rds + "_duration_cpap"] = rdsObj.duration_cpap;
						obj["Ep_" + rds + "_duration_mv"] = rdsObj.duration_mv;
						obj["Ep_" + rds + "_duration_hfo"] = rdsObj.duration_hfo;
						obj["Ep_" + rds + "_rds_surfactant"] = rdsObj.rds_surfactant;
						obj["Ep_" + rds + "_age_at_surfactant"] = rdsObj.age_at_surfactant;
						obj["Ep_" + rds + "_surfactant_type"] = rdsObj.surfactant_type;
						obj["Ep_" + rds + "_surfactant_dose"] = rdsObj.surfactant_dose;
						obj["Ep_" + rds + "_rds_no_antibiotics"] = rdsObj.no_antibiotics;
						obj["Ep_" + rds + "_rds_antibiotics_duration"] = rdsObj.antibiotics_duration;
					} else {
						obj["Ep_" + rds + "_rds_onset"] = null;
						obj["Ep_" + rds + "_rds_duration"] = null;
						obj["Ep_" + rds + "_rds_cause"] = null;
						obj["Ep_" + rds + "_max_downes"] = null;
						obj["Ep_" + rds + "_resp_support"] = null;
						obj["Ep_" + rds + "_duration_lowflow"] = null;
						obj["Ep_" + rds + "_duration_highflow"] = null;
						obj["Ep_" + rds + "_duration_cpap"] = null;
						obj["Ep_" + rds + "_duration_mv"] = null;
						obj["Ep_" + rds + "_duration_hfo"] = null;
						obj["Ep_" + rds + "_rds_surfactant"] = null;
						obj["Ep_" + rds + "_age_at_surfactant"] = null;
						obj["Ep_" + rds + "_surfactant_type"] = null;
						obj["Ep_" + rds + "_surfactant_dose"] = null;
						obj["Ep_" + rds + "_rds_no_antibiotics"] = null;
						obj["Ep_" + rds + "_rds_antibiotics_duration"] = null;
					}
				}
				delete obj['rdsList'];

				for(var sepsis = 1; sepsis <= this.report.maxSepsis; sepsis++) {
					if(sepsis <= obj.no_sepsis) {
						var sepsisObj = Object.assign({}, obj.sepsisList[sepsis - 1]);
						obj["Ep_" + sepsis + "_sepsis_onset"] = sepsisObj.sepsis_onset;
						obj["Ep_" + sepsis + "_sepsis_duration"] = sepsisObj.sepsis_duration;
						obj["Ep_" + sepsis + "_sepsis_cause"] = sepsisObj.sepsis_cause;
						obj["Ep_" + sepsis + "_sepsis_onset_type"] = sepsisObj.sepsis_onset_type;
						obj["Ep_" + sepsis + "_sepsis_no_antibiotics"] = sepsisObj.no_antibiotics;
						obj["Ep_" + sepsis + "_sepsis_antibiotics_duration"] = sepsisObj.antibiotics_duration;
						obj["Ep_" + sepsis + "_sepsis_nec"] = sepsisObj.sepsis_nec;
					} else {
						obj["Ep_" + sepsis + "_sepsis_onset"] = null;
						obj["Ep_" + sepsis + "_sepsis_duration"] = null;
						obj["Ep_" + sepsis + "_sepsis_cause"] = null;
						obj["Ep_" + sepsis + "_sepsis_onset_type"] = null;
						obj["Ep_" + sepsis + "_sepsis_no_antibiotics"] = null;
						obj["Ep_" + sepsis + "_sepsis_antibiotics_duration"] = null;
						obj["Ep_" + sepsis + "_sepsis_nec"] = null;
					}
				}
				delete obj['sepsisList'];
			}
			console.log(this.report.systemicList);
			this.exportReportCsv(this.report.systemicList);
     });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
  }

	exportReportCsv(data : any) {
    if(data != null && data.length > 0){
  		var fileName = "INICU-Report.csv";
  		var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(data);
  		var finalCSVData = encodeURI(csvData);

  		var downloadLink = document.createElement("a");
  		downloadLink.setAttribute("href", finalCSVData);
  		downloadLink.setAttribute("download", fileName);
  		downloadLink.click();
    }
	}

  getAnalyticsData = function() {

    try{
        this.http.request(this.apiData.getUsageAnalytics + "/" + this.branchName,)
        .subscribe(res => {
    this.getData = res.json();
    console.log(this.getData);
    this.totalPatients = parseInt(this.getData.totalPatient);
    this.admitted = parseInt(this.getData.patientAdmitted);
    this.discharged = parseInt(this.getData.patientDischarged);
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



	getSinSheetData = function(fromDate : Date, toDate : Date, initFlag : boolean, csvFlag : boolean) {
		console.log("comming into the getSinSheetData");
    fromDate = new Date(fromDate);
    toDate = new Date(toDate);
    if(fromDate.getTime() > this.sin.maxDate.getTime()) {
			//document.getElementById("sinFromDate").focus();
			//$("#sinFromDateError").css("display","block");
			//$("#sinToDateError").css("display","none");
			//$("#sinFromToDateError").css("display","none");
		} else if (toDate.getTime() > this.sin.maxDate.getTime()) {
			//document.getElementById("sinToDate").focus();
			//$("#sinFromDateError").css("display","none");
			//$("#sinToDateError").css("display","block");
			//$("#sinFromToDateError").css("display","none");
		}  else if (fromDate.getTime() > toDate.getTime()) {
			//document.getElementById("sinFromDate").focus();
			//$("#sinFromToDateError").css("display","block");
			//$("#sinFromDateError").css("display","none");
			//$("#sinToDateError").css("display","none");
		} else {
			fromDate.setHours(0);
			fromDate.setMinutes(0);
			fromDate.setSeconds(0);
			fromDate.setMilliseconds(0);
			toDate = new Date(toDate.getTime() + this.oneDay);
			toDate.setHours(0);
			toDate.setMinutes(0);
			toDate.setSeconds(0);
			toDate.setMilliseconds(0);

			var fromParameter = fromDate.getTime() + (fromDate.getTimezoneOffset() * 60000);
			var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);

       		try{
              this.http.request(this.apiData.getSinSheetData + fromParameter + "/" + toParameter + "/" + this.branchName + "/",)
            	.subscribe(res => {
		    		this.sin.sinSheetList = res.json();
					console.log(this.sin.sinSheetList);

					var offset = new Date().getTimezoneOffset() * 60000;
					for(var i = 0; i<this.sin.sinSheetList.length;i++) {
						var obj = this.sin.sinSheetList[i];
						obj.dataDate -= offset;
					}

					this.printGraph();
					if(initFlag) {
						this.sin.current = this.sin.sinSheetList[this.sin.sinSheetList.length - 1];
					} else if(csvFlag) {
						this.exportCsv();
				  } else if(this.isPrintSinSheet == true){
						this.isPrintSinSheet = false;
						localStorage.setItem('sinLocalData', JSON.stringify(this.sin));
            this.router.navigateByUrl('/ana/sinsheetPdf');
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
		};
	}

  printSinSheetData = function(fromDate, toDate){
    fromDate = new Date(fromDate);
    toDate = new Date(toDate);
    this.isPrintSinSheet = true;
		this.getSinSheetData(fromDate,toDate, false, false);
  }

	exportCsv = function() {
		var fileName = "SIN-Sheet-details.csv";
		var dataForCSV = Object.assign({}, this.sin.sinSheetList);

		for(var i = 0; i < dataForCSV.length; i++) {
			var obj = dataForCSV[i];
			//dateString = moment(date).format('YYYY-MM-DD');
			//obj.dataDate = dateService.formatter(new Date(obj.dataDate), "gmt", "standard");
		}

		var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
		var finalCSVData = encodeURI(csvData);

		var downloadLink = document.createElement("a");
		downloadLink.setAttribute("href", finalCSVData);
		downloadLink.setAttribute("download", fileName);
		downloadLink.click();
	}

	switchTable = function() {
		this.otherTableFlag = !this.otherTableFlag;
	}

	displayPatientAnalytics = function(value){
		console.log(value);
		if(value == "USAGE"){
			this.isUsage = "true";
			this.isQuality = "false";
			this.isSinSheet = "false";
			this.isAdoption = "false";
      this.isVital = "false";
      this.isOccupancy = "false";
		}else if(value == "QUALITY"){
			this.isUsage = "false";
			this.isQuality = "true";
			this.isSinSheet = "false";
			this.isAdoption = "false";
      this.isOccupancy = "false";
      this.isVital = "false";
		}else if(value == "SIN SHEET"){
			this.isUsage = "false";
			this.isQuality = "false";
			this.isSinSheet = "true";
			this.isAdoption = "false";
      this.isOccupancy = "false";
      this.isVital = "false";
		}else if(value == "ADOPTION"){
			this.isUsage = "false";
			this.isQuality = "false";
			this.isSinSheet = "false";
      this.isVital = "false";
      this.isOccupancy = "false";
			this.isAdoption = "true";
		}else if(value == "VITAL"){
			this.isUsage = "false";
			this.isQuality = "false";
			this.isSinSheet = "false";
			this.isAdoption = "false";
      this.isVital = "true";
      this.isOccupancy = "false";
		}else if(value == "OCCUPANCY"){
			this.isUsage = "false";
			this.isQuality = "false";
			this.isSinSheet = "false";
			this.isAdoption = "false";
      this.isVital = "false";
      this.isOccupancy = "true";
		}
	}

  printGraph = function(){
    // this.getData = {};
    for (let i = 0; i < this.sin.sinSheetList.length; i++) {
				let obj = this.sin.sinSheetList[i];
        let tempDateValue = new Date(obj.dataDate);
        console.log(tempDateValue);
    		this.noOfBabies
				.push([
					("2017-10-20"),
					(obj.noOfBabies) ]);
				this.IV
				.push([
					("2017-10-20"),
					(obj.ivAntibiotics) ]);
				this.nonInvasiveVenti
				.push([
					("2017-10-20"),
					(obj.nonInvasiveVentilator) ]);
				this.allFormVenti
				.push([
					("2017-10-20"),
					(obj.totalVentilator) ]);
				this.catheters
				.push([
					("2017-10-20"),
					(obj.centralLine) ]);
				this.cannula
				.push([
					("2017-10-20"),
					(obj.peripheralCannula) ]);
				this.PN
				.push([
					("2017-10-20"),
					(obj.pnFeed) ]);
				this.IVfluids
				.push([
					("2017-10-20"),
					(obj.ivFeed) ]);
					console.log("**********");
					console.log(this.noOfBabies);
					console.log(this.IV);
					console.log(this.nonInvasiveVenti);
					console.log(this.allFormVenti);
					console.log(this.catheters);
					console.log(this.cannula);
					console.log(this.PN);
					console.log(this.IVfluids);
			}

      var tempSeries = [
							{
								name : 'Number of Babies',
								marker : {
									enabled : true
								},
								color : '#39bd6b',
								enableMouseTracking : true,
								data : this.noOfBabies
							},
							{
								name : 'Babies on IV Antibiotics',
								marker : {
									enabled : true
								},
								color : '#fb7143',
								enableMouseTracking : true,
								data : this.IV
							},
							{
								name : 'Babies in Non-Invasive Ventilator',
								marker : {
									enabled : true
								},
								color : '#e279fc',
								enableMouseTracking : true,
								data : this.nonInvasiveVenti
							},
							{
								name : 'Babies on all Forms Ventilator',
								marker : {
									enabled : true
								},
								color : '#feb23a',
								enableMouseTracking : true,
								data : this.allFormVenti
							},
							{
								name : 'Babies on Central Catheters',
								marker : {
									enabled : true
								},
								color : "#74afff",
								enableMouseTracking : true,
								data : this.catheters
							},
							{
								name : 'Babies with Peripheral Cannula',
								color : '#ac91f9',
								marker : {
									enabled : true
								},
								enableMouseTracking : true,
								data : this.cannula
							},
							{
								name : 'Babies on PN',
								marker : {
									enabled : true
								},
								color : "#99004c",
								enableMouseTracking : true,
								data : this.PN
							},
							{
								name : 'Babies on IV Fluids',
								marker : {
									enabled : true
								},
								color : "#FF69B4",
								enableMouseTracking : true,
								data : this.IVfluids
							}];

      this.chart = new Chart({
          chart: {
            type: 'spline'
          },
					title : {
						text : 'Graph For Sin Sheet'
					},
					xAxis : {
						title : {
							text : 'Date'
						},
						allowDecimals : false,
						tickInterval : 1,
						/*min : 0,
								max : 34*/
					},
					yAxis : {
						title : {
							text : 'Graph of Sin Sheet',
						},
						allowDecimals : false,
					},
          credits: {
            enabled: false
          },
          series: tempSeries
        });
      console.log(this.IVfluids);

  }


  // QI

  getQISheet = function(fromDate : Date, toDate : Date, fromGest : string, toGest : string, fromWeight : number, toWeight : number) {
    fromDate = new Date(fromDate);
    toDate = new Date(toDate);
      if(fromDate.getTime() > this.qi.maxDate.getTime()) {
				//document.getElementById("qiFromDate").focus();
				// $("#qiFromDateError").css("display","block");
				// $("#qiToDateError").css("display","none");
				// $("#qiFromToDateError").css("display","none");
			} else if (toDate.getTime() > this.qi.maxDate.getTime()) {
				//document.getElementById("qiToDate").focus();
				// $("#qiFromDateError").css("display","none");
				// $("#qiToDateError").css("display","block");
				// $("#qiFromToDateError").css("display","none");
			}  else if (fromDate.getTime() > toDate.getTime()) {
				//document.getElementById("qiFromDate").focus();
				// $("#qiFromToDateError").css("display","block");
				// $("#qiFromDateError").css("display","none");
				// $("#qiToDateError").css("display","none");
			} else {
				// $("#qiFromToDateError").css("display","none");
				// $("#qiFromDateError").css("display","none");
				// $("#qiToDateError").css("display","none");
				fromDate.setHours(0);
				fromDate.setMinutes(0);
				fromDate.setSeconds(0);
				fromDate.setMilliseconds(0);
				toDate = new Date(toDate.getTime() +this.oneDay);
				toDate.setHours(0);
				toDate.setMinutes(0);
				toDate.setSeconds(0);
				toDate.setMilliseconds(0);

				if(toWeight == null || toWeight == undefined){
					toWeight = null;
				}
				if(fromWeight == null || fromWeight == undefined){
					fromWeight = null;
				}
				if(toGest == "" || toGest == undefined){
					toGest = null;
				}
				if(fromGest == "" || fromGest == undefined){
					fromGest = null;
				}

				if(this.qi.fromWeight != null && this.qi.fromWeight != "" && this.qi.fromWeight != undefined
						&& this.qi.toWeight != null && this.qi.toWeight != "" && this.qi.toWeight != undefined){
					if(this.qi.fromWeight*1 > this.qi.toWeight*1){
						this.qi.toWeight = "";
						this.showToWeightError = true;
					}
					else {
						this.showToWeightError = false;
						var fromParameter = fromDate.getTime() + (fromDate.getTimezoneOffset() * 60000);
						var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);
            try{
                this.http.request(this.apiData.getQIData + fromParameter + "/" + toParameter + "/"+ fromGest + "/" + toGest + "/" + fromWeight + "/" + toWeight + "/" + this.branchName + "/",)
              	.subscribe(res => {
  		    		this.qiList = res.json();
  					console.log(this.qiList);

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
				} else {
					this.showToWeightError = false;
					var fromParameter = fromDate.getTime() + (fromDate.getTimezoneOffset() * 60000);
					var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);
          try{
              this.http.request(this.apiData.getQIData + fromParameter + "/" + toParameter + "/"+ fromGest + "/" + toGest + "/" + fromWeight + "/" + toWeight + "/" + this.branchName + "/",)
              .subscribe(res => {
                this.qiList = res.json();
                console.log(this.qiList);
                console.log(JSON.stringify(this.qiList.sepsisUhidList));
              }, err => {
                  console.log("Error occured.")
              });
          }
          catch(e){
            console.log("Exception in http service:"+e);
          };
				}
			}
		};

		showMortality = function(){
			if(this.isMortality){
				this.isMortality = false;
				this.isPatientCount = false;
			}
			else{
				this.isMortality = true;
				this.isPatientCount = false;
			}
		}
		showPatientCount = function(){
			if(this.isPatientCount){
				this.isMortality = false;
				this.isPatientCount = false;
			}
			else{
				this.isMortality = false;
				this.isPatientCount = true;
			}
		}

    getrangeGestFrom = function(){
    		for(let i=20;i<=45;++i) {
    			this.rangeGestFrom.push(i);
    		}
        console.log(this.rangeGestFrom);
      }
      getrangeGestTo = function(){

    		for(var i= 20 ;i<=45;++i) {
    			this.rangeGestTo.push(i);
    		}
      }
    		creatToRange = function(value){
    			console.log(value);
    			if(value == ""){
    				this.qi.toGestation = "";
    			}
    			this.rangeGestTo = [];
    			let startRange = value*1;
    			for(var i= startRange ;i<=45;++i) {
    				this.rangeGestTo.push(i);
    			}
    		}
		// end of code for quality indicator


    //usage analytics
		getAnalyticsUsage = function(fromDate : Date, toDate : Date, csvFlag : boolean) {
      this.getAnalyticsUsageGraph(fromDate, toDate);
      fromDate = new Date(fromDate);
      toDate = new Date(toDate);
      if(fromDate.getTime() > this.usage.maxDate.getTime()) {
				//document.getElementById("usageFromDate").focus();
			} else if (toDate.getTime() > this.usage.maxDate.getTime()) {
				//document.getElementById("usageToDate").focus();
			}  else if (fromDate.getTime() > toDate.getTime()) {
				//document.getElementById("usageFromDate").focus();
			} else {
				console.log("in getAnalyticsUsage ");
				fromDate.setHours(0);
				fromDate.setMinutes(0);
				fromDate.setSeconds(0);
				fromDate.setMilliseconds(0);
				toDate = new Date(toDate.getTime() + this.oneDay);
				toDate.setHours(0);
				toDate.setMinutes(0);
				toDate.setSeconds(0);
				toDate.setMilliseconds(0);

				var fromParameter = fromDate.getTime() + (fromDate.getTimezoneOffset() * 60000);
				var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);
        this.isLoaderVisible = true;
        this.loaderContent = 'Analyzing the data...';
        try{
            this.http.request(this.apiData.getAnalyticsUsage + fromParameter + "/" + toParameter + "/" + this.branchName + "/",)
            .subscribe(res => {
                if(res.json() != null && res.json() != undefined){
                  this.usage.usageList = res.json();
                }
                this.isLoaderVisible = false;
                this.loaderContent = '';

                if(csvFlag) {
                  this.exportUsageCsv(this.usage.usageList, "Usage-details.csv");
                } else if(this.isPrintUsage == true){
                  this.isPrintUsage = false;
                  localStorage.setItem('usageLocalData', JSON.stringify(this.usage));
                this.router.navigateByUrl('/ana/usagePdf');
                }
             },
            err => {
              console.log("Error occured.")
            });
        } catch(e){
          console.log("Exception in http service:"+e);
        };
			}
		};

    getOccupancyData = function(fromDate : Date, toDate : Date) {
      fromDate = new Date(fromDate);
      toDate = new Date(toDate);
      if(fromDate.getTime() > this.adoption.maxDate.getTime()) {
				//document.getElementById("adoptionFromDate").focus();
			} else if (toDate.getTime() > this.adoption.maxDate.getTime()) {
				//document.getElementById("adoptionToDate").focus();
			}  else if (fromDate.getTime() > toDate.getTime()) {
				//document.getElementById("adoptionFromDate").focus();
			} else {
				fromDate.setHours(0);
				fromDate.setMinutes(0);
				fromDate.setSeconds(0);
				fromDate.setMilliseconds(0);
				toDate = new Date(toDate.getTime() + this.oneDay);
				toDate.setHours(0);
				toDate.setMinutes(0);
				toDate.setSeconds(0);
				toDate.setMilliseconds(0);

				var fromParameter = fromDate.getTime();
				var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);
        this.analyticsSearch.dischargeTypeList = this.dischargeTypeArrayList + "";
        this.analyticsSearch.assessmentTypeList = this.assessmentTypeArrayList + "";

        try{
            this.http.post(this.apiData.getOccupancyData + fromParameter + "/" + toParameter + "/" + this.branchName + "/", this.analyticsSearch)
            .subscribe(res => {
                this.monthMapper = res.json();
                var monthsList = [];
                var inbornList = [];
                var outbornList = [];
                this.totalPatientsOccupancy = 0;
                var months = ["Jan", "Feb","March","April","May","June","July","August","Sept","Oct","Nov","Dec"];
                for(var j = 0; j< months.length; j++){
                  if(this.monthMapper[months[j]] != undefined){
                    if(this.monthMapper[months[j]].numberOfBabies != null){
                      this.totalPatientsOccupancy += this.monthMapper[months[j]].numberOfBabies;
                    }
                    monthsList.push(months[j]);
                    if(this.monthMapper[months[j]].inbornCount != null)
                      inbornList.push([months[j],(this.monthMapper[months[j]].inbornCount)]);
                    else{
                      inbornList.push([months[j],null]);
                    }
                    if(this.monthMapper[months[j]].outbornCount != null)
                      outbornList.push([months[j],(this.monthMapper[months[j]].outbornCount)]);
                    else{
                      outbornList.push([months[j],null]);
                    }
                  }
                }
                var analyticsSeries = [
                  {
                    name : 'Inborn',
                    marker : {
                      enabled : true
                    },
                    color : '#83c9ef',
                    enableMouseTracking : true,
                    data : inbornList
                  },
                  {
                    name : 'Outborn',
                    marker : {
                      enabled : true
                    },
                    color : '#b0e8c5',
                    enableMouseTracking : true,
                    data : outbornList
                  }];

                this.analyticsChart = new Chart({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: 'Stacked column chart'
                    },
                    xAxis: {
                        categories: monthsList
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: 'Number of Admissions'
                        },
                        stackLabels: {
                            enabled: true,
                            style: {
                                fontWeight: 'bold'
                            }
                        }
                    },
                    legend: {
                        align: 'right',
                        x: -30,
                        verticalAlign: 'top',
                        y: 25,
                        floating: true,
                        borderColor: '#CCC',
                        borderWidth: 1,
                        shadow: false
                    },
                    // tooltip: {
                    //     headerFormat: '<b>{point.x}</b><br/>',
                    //     pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
                    // },
                    plotOptions: {
                        column: {
                            stacking: 'normal',
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    series: analyticsSeries
                });
             },
            err => {
              console.log("Error occured.")
            });
        } catch(e){
          console.log("Exception in http service:"+e);
        };
			}
		};

    exportOccupancyCSV = function(fromDate : Date, toDate : Date) {
      fromDate = new Date(fromDate);
      toDate = new Date(toDate);
      if(fromDate.getTime() > this.adoption.maxDate.getTime()) {
        //document.getElementById("adoptionFromDate").focus();
      } else if (toDate.getTime() > this.adoption.maxDate.getTime()) {
        //document.getElementById("adoptionToDate").focus();
      }  else if (fromDate.getTime() > toDate.getTime()) {
        //document.getElementById("adoptionFromDate").focus();
      } else {
        fromDate.setHours(0);
        fromDate.setMinutes(0);
        fromDate.setSeconds(0);
        fromDate.setMilliseconds(0);
        toDate = new Date(toDate.getTime() + this.oneDay);
        toDate.setHours(0);
        toDate.setMinutes(0);
        toDate.setSeconds(0);
        toDate.setMilliseconds(0);

        var fromParameter = fromDate.getTime();
        var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);
        this.analyticsSearch.dischargeTypeList = this.dischargeTypeArrayList + "";
        this.analyticsSearch.assessmentTypeList = this.assessmentTypeArrayList + "";
        this.isLoaderVisible = true;
        this.loaderContent = 'Exporting the data.....';
        try{
            this.http.post(this.apiData.getExportOccupancy + fromParameter + "/" + toParameter + "/" + this.branchName + "/", this.analyticsSearch)
            .subscribe(res => {
              this.isLoaderVisible = false;
              this.loaderContent = '';
                var occupancyDataList = res.json();
                this.exportUsageCsv(occupancyDataList, "Occupancy-Details.csv");
             },
            err => {
              console.log("Error occured.")
            });
        } catch(e){
          console.log("Exception in http service:"+e);
        };
      }
    };

    getAnalyticsUsageGraph = function(fromDate : Date, toDate : Date) {
      fromDate = new Date(fromDate);
      toDate = new Date(toDate);
      if(fromDate.getTime() > this.usage.maxDate.getTime()) {
        //document.getElementById("usageFromDate").focus();
      } else if (toDate.getTime() > this.usage.maxDate.getTime()) {
        //document.getElementById("usageToDate").focus();
      }  else if (fromDate.getTime() > toDate.getTime()) {
        //document.getElementById("usageFromDate").focus();
      } else {
				console.log("in getAnalyticsUsageGraph");
				fromDate.setHours(0);
				fromDate.setMinutes(0);
				fromDate.setSeconds(0);
				fromDate.setMilliseconds(0);
				toDate = new Date(toDate.getTime() + this.oneDay);
				toDate.setHours(0);
				toDate.setMinutes(0);
				toDate.setSeconds(0);
				toDate.setMilliseconds(0);

				var fromParameter = fromDate.getTime() + (fromDate.getTimezoneOffset() * 60000);
				var toParameter = toDate.getTime() + (toDate.getTimezoneOffset() * 60000);

        try{
            this.http.request(this.apiData.getAnalyticsUsageGraph + fromParameter + "/" + toParameter + "/" + this.branchName + "/",)
            .subscribe(res => {
                this.usageGraphList = res.json();
                this.printAdoptionGraph();
                console.log("Adoption data: ");
                console.log(this.usageGraphList);
             },
            err => {
              console.log("Error occured.")
            });
        } catch(e){
          console.log("Exception in http service:"+e);
        };
			}
		};
	printAdoptionGraph() {
		this.doctorAdoptionList = [];
  		this.nurseAdoptionList = [];
  		console.log(this.usageGraphList.doctor);
  		console.log(this.usageGraphList.nurse);
		for(var docData in this.usageGraphList.doctor){
			var tempDocEle = new Date(new Date(docData).getTime() + 86400000);
			this.doctorAdoptionList.push([ tempDocEle,(Math.round(this.usageGraphList.doctor[docData]))]);
		}
		for(var nurseData in this.usageGraphList.nurse){
			var tempNurEle = new Date(new Date(nurseData).getTime() + 86400000);
			this.nurseAdoptionList.push([ tempNurEle,(Math.round(this.usageGraphList.nurse[nurseData]))]);
		}
		console.log(this.doctorAdoptionList);
		console.log(this.nurseAdoptionList);
		var tempDocSeries = [
			{
				name : 'Adoption',
				marker : {
					enabled : true
				},
				color : '#39bd6b',
				enableMouseTracking : true,
				data : this.doctorAdoptionList
			}];
			this.docChart = new Chart({
        		chart: {
            		type: 'spline'
          		},
				title : {
					text : 'Graph For Adoption(Doctor)'
					},
				xAxis : {
					title : {
						text : 'Date'
					},
					allowDecimals : false,
					tickInterval:  1 * 24 * 3600 * 1000,
        			type: 'datetime',
        			startOnTick: true,
        			labels: {
            			format: '{value:%d/%m/%Y}',
            			rotation: -45,
            			y: 30,
            			align: 'center'
        			}
				},
				yAxis : {
					title : {
						text : 'Graph of Adoption(Doctor)',
					},
					allowDecimals : false,
					min : 0,
					max : 100,
					tickInterval : 5
				},
          		credits: {
            		enabled: false
          		},
          		plotOptions: {
			        series: {
			            pointStart: this.usage.fromDate.getTime(),
			            pointInterval: 1 * 24 * 3600 * 1000
			        }
			    },
          		series: tempDocSeries
       		});

       		var tempNurSeries = [
			{
				name : 'Adoption',
				marker : {
					enabled : true
				},
				color : '#39bd6b',
				enableMouseTracking : true,
				data : this.nurseAdoptionList
			}];
			this.nurChart = new Chart({
        		chart: {
            		type: 'spline'
          		},
				title : {
					text : 'Graph For Adoption(Nursing)'
					},
				xAxis : {
					title : {
						text : 'Date'
					},
					allowDecimals : false,
					tickInterval:  1 * 24 * 3600 * 1000,
        			type: 'datetime',
        			startOnTick: true,
        			labels: {
            			format: '{value:%d/%m/%Y}',
            			rotation: -45,
            			y: 30,
            			align: 'center'
        			}
				},
				yAxis : {
					title : {
						text : 'Graph of Adoption(Nurse)',
					},
					allowDecimals : false,
					min : 0,
					max : 100,
					tickInterval : 5
				},
          		credits: {
            		enabled: false
          		},
          		plotOptions: {
			        series: {
			            pointStart: this.usage.fromDate.getTime(),
			            pointInterval: 1 * 24 * 3600 * 1000
			        }
			    },
          		series: tempNurSeries
       		});
		}
    exportUsageCsv = function(dataForCSV : any, fileName : any){

  		var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
  		var finalCSVData = encodeURI(csvData);

  		var downloadLink = document.createElement("a");
  		downloadLink.setAttribute("href", finalCSVData);
  		downloadLink.setAttribute("download", fileName);
  		downloadLink.click();

    }

  	printUsageData = function(fromDate : Date, toDate: Date){
    	fromDate = new Date(fromDate);
    	toDate = new Date(toDate);
    	this.isPrintUsage = true;
    	this.getAnalyticsUsage(fromDate, toDate, false);
  	}
 	redirectDoctorPanelUsage = function(uhid) {
	 try
     {
       this.http.request(this.apiData.getChildData + "/" + uhid + "/").
       subscribe(res => {
       	this.tempResp = res.json();
       	if(this.tempResp != null && this.tempResp.length > 0) {
				localStorage.setItem('selectedChild', JSON.stringify(this.tempResp[0]));
				this.router.navigateByUrl('/respiratory/assessment-sheet-respiratory');
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



	try{
        this.http.request().subscribe((res: Response) => {

           });
        }catch(e){
            console.log("Exception in http service:"+e);
        };
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
		let dischargeTypeArr = [];
		if(type=="dischargeType"){
			if( this.dischargeTypeArrayList != null &&  this.dischargeTypeArrayList.length > 0)
				dischargeTypeArr = this.dischargeTypeArrayList;

			let flag = true;
			if(dischargeTypeArr.length == 0){
				dischargeTypeArr.push(value);
			}
			else{
				for(let i=0;i< dischargeTypeArr.length;i++){
					if(value == dischargeTypeArr[i]){
						dischargeTypeArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					dischargeTypeArr.push(value);
				}
			}
			this.dischargeTypeArrayList = dischargeTypeArr ;
		}else if(type=="assessmentType"){
			if( this.assessmentTypeArrayList != null &&  this.assessmentTypeArrayList.length > 0)
				dischargeTypeArr = this.assessmentTypeArrayList;

			let flag = true;
			if(dischargeTypeArr.length == 0){
				dischargeTypeArr.push(value);
			}
			else{
				for(let i=0;i< dischargeTypeArr.length;i++){
					if(value == dischargeTypeArr[i]){
						dischargeTypeArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					dischargeTypeArr.push(value);
				}
			}
			this.assessmentTypeArrayList = dischargeTypeArr ;
		}
  }

  showSinBabyDetails = function(data : any){
    this.sinDataList =  Object.assign({}, data);
    console.log("sin data initiated");
    $("#sinOverlay").css("display", "block");
    $("#sinScorePopup").addClass("showing");
  }
  closeModalSin = function(){
		console.log("sin data closing");
    this.sinDataList.dataList = null;
		$("#sinOverlay").css("display", "none");
		$("#sinScorePopup").toggleClass("showing");
	};

  ngOnInit() {
  }

}
