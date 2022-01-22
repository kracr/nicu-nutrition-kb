import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { AppComponent } from '../app.component';
import { SentOrders } from './sentOrders';
import { ExportCsv } from '../export-csv';
import * as $ from 'jquery/dist/jquery.min.js';
import { Chart } from 'angular-highcharts';

@Component({
  selector: 'nursing-reports',
  templateUrl: './nursing-reports.component.html',
  styleUrls: ['./nursing-reports.component.css']
})
export class NursingReportsComponent implements OnInit {
  isLoaderVisible : boolean;
  loaderContent : string;
  isOrderVisible : boolean;
	isExecutionVisible : boolean;
	labOrderTab : string;
  labOrdersData : any;
  apiData : APIurl;
  uhid : string;
  child : any;
  router : Router;
  http : Http;
  sentdate : any;
  currentOrderTime : any;
  currentTests : Array<any>;
  sentOrders : SentOrders;
  toDate : Date;
	fromDate : Date;
	fromDateNull : boolean;
	toDateNull : boolean;
	fromToError : boolean;
	fromDateTableNull : boolean;
	toDateTableNull : boolean;
	fromToTableError : boolean;
  copiedSentData : Array<any>;
  commentVitals : any;
  resultData :any;
  baby_uhid : any;
  searchObj : any;
  testResultsData : any;
  testsListMapped : any;
  selectedTestID : string;
  testView : boolean;
  graphView : boolean;
  graphAllow : boolean;
  testMode : boolean;
  searchedStartDate : any;
  searchedEndDate : any;
  admdate : any;



  	constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = "Saving...";
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
		this.isOrderVisible = true;
		this.isExecutionVisible = true;
		this.labOrderTab = 'Lab';
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
    this.baby_uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.admdate= new Date(JSON.parse(localStorage.getItem('selectedChild')).admitDate);
    this.uhid = this.child.uhid;
    this.currentTests = [];
    this.copiedSentData = [];
    this.sentOrders = new SentOrders();
    this.searchObj = {};
    this.testResultsData = null;
    this.testsListMapped = null;
    this.testView = true;
    this.graphView = false;
    this.graphAllow = false;
    this.testMode = true;
    this.searchedStartDate = this.admdate;
    this.searchedEndDate = new Date();
    this.init();

  }

  init() {
    var date  = new Date();
    this.sentdate = date;
    this.getLabOrdersData();
    this.getTestsListMapped();
    var todayDate = this.convertJSDate(new Date());
    console.log("todayDate "+todayDate);
    this.searchObj = { "searchedUHID" : this.baby_uhid,
				"searchedStartDate" : todayDate,
				"searchedEndDate" : todayDate,
				"selectedTestID": "0",
		}
  };

  getLabOrdersData(){
    try{

      this.http.request(this.apiData.getLabOrders + this.uhid + "/",)
      .subscribe(res => {
        this.labOrdersData = res.json();
        this.copiedSentData = Object.assign([],this.labOrdersData.labOrdersSent);
        console.log(this.labOrdersData);
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

  orderSectionVisible(){
		this.isOrderVisible = !this.isOrderVisible;
	}
	ExecutionSectionVisible(){
		this.isExecutionVisible = !this.isExecutionVisible;
	}
  executeTest = function(test: any){

    var date  = new Date();
    this.sentdate = date;

    if(!this.isEmpty(test) && test.issamplesent != true){
      if(test.issampleSelected == false || test.issampleSelected == null){
        if (this.currentOrderTime != null && this.currentOrderTime != undefined){
          if(test.investigationorder_time == new Date(this.currentOrderTime).getTime()){
            this.currentOrderTime = new Date(test.investigationorder_time);
            test.issampleSelected = true;
            this.currentTests.push(test);
          }
          else{
            test.issampleSelected = true;
            if(!this.isEmpty(this.currentTests)){
              for(var i=0; i<this.currentTests.length ;i++){
                this.currentTests[i].issampleSelected = false;
              }
            }
            this.currentTests = [];
            this.currentOrderTime = new Date(test.investigationorder_time);
            this.currentTests.push(test);
          }
        }
        else{
          test.issampleSelected = true;
          this.currentOrderTime = new Date(test.investigationorder_time);
          this.currentTests.push(test);
        }
      }
      else{
        test.issampleSelected = false;
        if(!this.isEmpty(this.currentTests)){
          for(var i=0; i<this.currentTests.length ;i++){
            if(test.investigationorderid ==this.currentTests[i].investigationorderid){
              this.currentTests.splice(i,1);
              break;
            }
          }
        }
        if(this.currentTests != null && this.currentTests != undefined){
          if(this.currentTests.length < 1){
            this.currentOrderTime = null;
          }
        }
      }
      this.sentOrders.ordersList = this.currentTests;
      this.sentOrders.loggeduser = "test";
      this.sentOrders.uhid = this.uhid;
      console.log(test);
      console.log(this.labOrdersData.labOrders);
    }
  }

  sendSample(){

    if(this.sentdate == null || this.sentdate == undefined){
      return;
    }
    if(this.sentOrders.ordersList != null && this.sentOrders.ordersList != undefined && this.sentOrders.ordersList.length > 0){
      this.sentOrders.sentdate = new Date(this.sentdate).getTime();
      this.isLoaderVisible = true;

      try
      {
        this.http.post(this.apiData.sendSample, this.sentOrders).
        subscribe(res => {
        this.labOrdersData = res.json();
        this.isLoaderVisible = false;
        this.copiedSentData = Object.assign([],this.labOrdersData.labOrdersSent);
        this.sentOrders = new SentOrders();
        this.sentdate = null;
        this.currentTests = [];
        this.currentOrderTime = null;
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

  convertJSDate = function(inputDate){

    var outputDate = "";
    if(inputDate!=null && inputDate!="")
    {
      //check if date object and not invalid date
      //inputDate instanceof Date
      inputDate = new Date(inputDate);
      var month = (inputDate.getMonth()+1).toString();
      var day = inputDate.getDate().toString();
      month = ("0" + month).slice(-2);
      day = ("0"+day).slice(-2);
      outputDate = inputDate.getFullYear().toString() + '-' + month + '-' + day;
      console.log("convertjsdate: "+inputDate+" "+outputDate);
    }
    return outputDate;
  }

  filterNursingOrders(fromDateTable : Date, toDateTable : Date, flag : String) {

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
      this.copiedSentData = [];
      for(var i=0; i < this.labOrdersData.labOrdersSent.length; i++) {
        var item = this.labOrdersData.labOrdersSent[i];

        if(item.orderdate >= fromDateTable.getTime() && item.orderdate <= toDateTable.getTime()) {
            var obj = Object.assign({}, item);
            obj.orderdate = new Date(obj.orderdate);
            obj.sentdate= new Date(obj.sentdate);
            data.push(obj);
            this.copiedSentData.push(obj);
        }
      }

      if(flag == 'print') {
        // print code here
        this.printOrder(data,fromDateTable,toDateTable);
      } else if(flag == 'csv') {
        this.exportOrdersCsv(data);
      }
    }
  }
  printOrder(dataForPrint : any, fromDateTable : Date, toDateTable : Date){
    var tempArr = [];
    tempArr = dataForPrint;
    dataForPrint = {};
    console.log(dataForPrint);
    dataForPrint.data = tempArr;
    dataForPrint.whichTab = 'Orders';
    dataForPrint.from_time = fromDateTable.getTime();
    dataForPrint.to_time = toDateTable.getTime();;
    localStorage.setItem('printNursingDataObjNotes', JSON.stringify(dataForPrint));
    this.router.navigateByUrl('/nursing/nursing-print');
  }
  exportOrdersCsv(dataForCSV : any) {
    var fileName = "INICU-Nursing-Orders.csv";
    var csvData = "data:text/csv;charset=utf-8," + ExportCsv.convertToCSV(dataForCSV);
    var finalCSVData = encodeURI(csvData);

    var downloadLink = document.createElement("a");
    downloadLink.setAttribute("href", finalCSVData);
    downloadLink.setAttribute("download", fileName);
    downloadLink.click();
  }

  isEmpty(object : any) : boolean {
    if (object == null || object == '' || object == 'null' || object.length == 0) {
      return true;
    }
    else {
      return false;
    }
  };

  openCommentsModal = function(comment : any){
  $("#nursingReportsCommentsOverlay").css("display", "none");
  $("#nursingReportsComments").toggleClass("showing");
  $('body').css('overflow', 'auto');
    this.commentVitals = comment;

  }
  closeModalNursingVitals = function(){
    console.log("closeNursingVitalsPopup closing");
    $("#nursingReportsCommentsOverlay").css("display", "none");
    $('body').css('overflow', 'auto');
    $("#nursingReportsComments").toggleClass("showing");
    this.commentVitals = null;
  }

  showTestsResult = function(data : any){
    this.resultData = data;
    if(this.resultData != null){
      $("#resultsOverlay").css("display", "none");
      $("#NursingResultsPopup").toggleClass("showing");
      $('body').css('overflow', 'auto');
    }
  }
  closeModalNursingResults = function(){
    console.log("closeresultsNursingPopup closing");
    $("#resultsOverlay").css("display", "none");
    $('body').css('overflow', 'auto');
    $("#NursingResultsPopup").toggleClass("showing");
    this.resultData = null;
  }

  getTestsListMapped = function() {
    try
    {
      this.http.request(this.apiData.getTestsListMapped + '/' + this.baby_uhid + '/').
      subscribe(res => {
        this.testsListMapped = res.json();
        console.log("tests list received " );
        console.log(res.json());
        console.log("runing showTestResults()");
        this.showTestResults();

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

  showTestResults = function(){
     console.log("in function showTestResults "+this.selectedTestID+this.searchedStartDate+" "+this.searchedEndDate);
     this.selectedSubTestID = "";

     this.graphView = false;
     if(this.searchedStartDate>this.searchedEndDate){
       alert("Please check the From and To dates");
       return;
     }
     else{
         this.searchObj.searchedStartDate = this.convertJSDate(this.searchedStartDate);
         this.searchObj.searchedEndDate = this.convertJSDate(this.searchedEndDate);
         console.log(this.selectedTestID);
         if(this.selectedTestID!=null)
         this.searchObj.selectedTestID = this.selectedTestID;
         console.log(this.selectedTestID);
     }

     console.log("Get request  ");
     console.log(this.searchObj);

     try
     {
       this.http.post(this.apiData.getTestsData + "/", this.searchObj).
       subscribe(res => {
         this.testResultsData = res.json();
         console.log("tests data received : ");
         console.log(this.testResultsData);

         this.testsItemsListMapped = [];
         if(this.selectedTestID!='0' && this.testResultsData!=null && this.testResultsData.length>0){
           for(var i=0;i<this.testResultsData[0].testItemObjectsList.length;i++){
             this.testsItemsListMapped.push(this.testResultsData[0].testItemObjectsList[i].itemname);
           }
         }else{
           this.testsItemsListMapped = [];
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

  checkStartDate = function() {
    // allow same from and to for all tests(all tests info for one day only)
    console.log("inside checkstartdate");
    console.log(this.searchedStartDate);
    console.log(this.searchedEndDate);
    var maxDate = new Date();
    if(this.searchedStartDate>maxDate){
        alert("Date cannot be greater than today");
        this.searchedStartDate = this.maxDate;  //set today's date
    }
    if(this.searchedStartDate < this.admdate){
        alert("Date cannot be before DOA ");
        this.searchedStartDate = this.admdate;  //set today's date
    }
  }

  fetchGraphData = function(){
    this.baby_uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    var paramterName = this.selectedSubTestID;
    if(paramterName==''){
      this.testItemResults = [];
      this.plotGraph();
      return;
    }

    try
    {
      this.http.request(this.apiData.getGraphData + '/' + this.baby_uhid + '/' + paramterName + '/').
      subscribe(res => {
        this.testItemResults = res.json();
        this.plotGraph();

      },
      err => {
        console.log("Error occured.")
      }
      );
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };

    console.log(this.testResultsData);
    var len = this.testResultsData.length;
    var valuesXY = [];
    var values = [];
    var xAxis = [];
    for(var i = len-1; i>=0;--i){
      var testresult = this.testResultsData[i];

      var timestampOfResult = new Date(testresult.creationtime); // change this
      var timeOfResult = timestampOfResult.getHours()+":"+timestampOfResult.getMinutes()+":"+timestampOfResult.getSeconds();

      console.log(this.testResultsData[i].resultdate + " "+timeOfResult);
      valuesXY.push([this.testResultsData[i].resultdate + " "+timeOfResult, parseFloat(this.testResultsData[i].testItemObjectsList[0].itemvalue)])
      xAxis.push(this.testResultsData[i].resultdate + " "+timeOfResult);
      values.push(parseFloat(this.testResultsData[i].testItemObjectsList[0].itemvalue))
    }
  }

  reportView = function(labreport) {
    console.log(labreport)
    var obj = { "labreport" : "" }
    obj.labreport = labreport;

//			var out = message.replace(/(?:\r\n|\r|\n)/g, '<br />').replace('<br /><br />', '<br />');
    var out = labreport.replace(/(?:\r\n|\r|\n)/g, '\n');
    var arr = labreport.split('\n');
    this.reportContent = arr;
          $("#ballardOverlay").css("display", "block");
          $("#apgarScorePopup").addClass("showing");
  }

  checkEndDate = function() {
     // allow same from and to for all tests(all tests info for one day only)
     var maxDate = new Date();

     if(this.searchedEndDate>maxDate)
     {
       alert("Date cannot be greater than today");
       this.searchedEndDate = this.maxDate;  //set today's date

     }
     if(this.searchedEndDate < this.admdate)
     {
       alert("Date cannot be before DOA ");
       this.searchedEndDate = this.maxDate;  //set today's date
     }
  }

  plotGraph = function(){
    var xaxis = [];
    var yaxis = [];
    var sum = 0.0;

    var len = this.testItemResults.length;

    var max = 0
    var min = 500;
    var sum = 0.0;
    if(this.testItemResults!=null && len>0){
      for(var i=0; i<this.testItemResults.length; ++i){
        var datetime = new Date(this.testItemResults[i].labReportDate);
        var resulttime = this.convertJSDate(datetime)+" "
        if(datetime.getHours()<10){
          resulttime += "0"
        }
        resulttime +=datetime.getHours().toString();
        resulttime += ":";
        if(datetime.getMinutes()<10){
          resulttime += "0";
        }
        resulttime += datetime.getMinutes().toString();
        xaxis.push(resulttime);
//					xaxis.push(this.testItemResults[i].labReportDate);

        var itemvalue = parseFloat(this.testItemResults[i].itemvalue);
        yaxis.push(itemvalue);
        sum += parseFloat(this.testItemResults[i].itemvalue);

        if(itemvalue<min)
          min = itemvalue;
        if(itemvalue>max)
          max = itemvalue;
      }
    }

    this.avg = (sum/len).toFixed(2);
    this.avg = parseFloat(this.avg);
    var chart;
    console.log("xaxis");
    console.log(xaxis);
    this.heightChart = new Chart({
        chart: {
            type: 'line',
            marginRight:25,
            marginLeft:60,
            backgroundColor:'#eee',
            borderWidth:1,
            borderColor:'#ccc',
            plotBackgroundColor:'#fff',
            plotBorderWidth:1,
            plotBorderColor:'#ccc',
        },
        credits:{
            enabled:false
        },
        title: {
            text: '',
        },

        legend: {
            enabled:false
        },
        plotOptions:{
            series: {
                shadow: false,
                lineWidth:1,
                states: {
                    hover: {
                        enabled:true,
                        lineWidth:1
                    }
                },
                marker: {
                    enabled:true,
                    symbol:'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                },
            }
        },
        xAxis: {
            lineWidth:0,
            tickColor:'#999',
            categories: xaxis,
            type: 'datetime',
              dateTimeLabelFormats: {
                  month: '%b %e, %Y'
              }
        },
        yAxis: {

            plotLines:[

            {
                value:this.avg,
                color:'rgba(254,90,169,.75)',
                width:3,
            },

            ],
            title: {text: ''},
            lineWidth:0,
            gridLineWidth:1,
            gridLineColor:'rgba(0,0,0,.25)',
            startOnTick:false,
            endOnTick:false,
            minPadding:0,
            maxPadding:0,

        },
        series: [{
            data: yaxis,
        }]
    });
  }


  ngOnInit() {
  }

}
