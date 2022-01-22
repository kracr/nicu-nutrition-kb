import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery/dist/jquery.min.js';
import { Chart } from 'angular-highcharts';

@Component({
  selector: 'lab-reports',
  templateUrl: './lab-reports.component.html',
  styleUrls: ['./lab-reports.component.css']
})
export class LabReportsComponent implements OnInit {

  router : Router;
  http : Http;
  apiData : APIurl;
  labOrderTab : string;
  uhid : string;
  child : any;
  nursingOrder : any;
  selectedTestID : string;
  testResultsData : any;
  testsListMappedComplete : any;
  baby_uhid : any;
  testsListMapped : any;
  disableIncrement : boolean;
  testView : boolean;
  graphView : boolean;
  graphAllow : boolean;
  testItemsListComplete : any;
  investigationsOrdered : any;
  investigationsOrderedShown : any;
  inputObjects : any;
  investigationsSent : any;
  investigationsReceived : any;
  testMode : boolean;
  birthdate : any;
  admdate : any;
  selectedItemID : any;
  searchedStartDate : any;
  searchedEndDate : any;
  maxDate : any;
  selectedInvDate : any;
  selectedInvestigationTestID : any;
  selectedSubTestID : any;
  testsItemsListMapped : any;
  testItemResults : any;
  searchObj : any;
  avg : any;
  isDoctorLabTrue : boolean;

  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.labOrderTab = "Lab";
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
    this.uhid = this.child.uhid;
    this.testResultsData = null;
    this.testsListMappedComplete = null;
    this.baby_uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.testsListMapped = null;
    this.disableIncrement = true;
    this.testView = true;
    this.graphView = false;
    this.graphAllow = false;
    this.testItemsListComplete = null;
    this.investigationsOrdered = null;
    this.investigationsOrderedShown = null;
    this.inputObjects = [];
    this.investigationsSent = {};
    this.investigationsReceived = null;
    this.testMode = true;
    this.birthdate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob);
    this.admdate= new Date(JSON.parse(localStorage.getItem('selectedChild')).admitDate);
    this.selectedTestID = "0";
    this.selectedItemID = "0";
    this.searchedStartDate = this.admdate;
    this.searchedEndDate = new Date();
    this.maxDate = new Date();
    this.selectedInvDate = this.maxDate;
    this.selectedInvestigationTestID = "0";
    this.selectedSubTestID = "";
    this.testsItemsListMapped = [];
    this.testItemResults = [];
    this.searchObj = {};
    this.isDoctorLabTrue = true;
   }

     getNursingOrder = function(){
       try
       {
         this.http.request(this.apiData.saveNursingOrder + this.uhid + "/").
         subscribe(res => {
           this.nursingOrder = res.json();
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

    getInvestigationsOrdered = function( testid, date) {
			var testid = this.selectedInvestigationTestID;
			var date = this.convertJSDate(this.selectedInvDate);

      try
      {
        this.http.request(this.apiData.getInvestigationsOrdered + '/' + this.baby_uhid + '/' + testid + '/' + date).
        subscribe(res => {
          this.investigationsOrdered = res.json();
          this.investigationsOrderedShown = res.json();

          if(res.json()!=null)
            this.populateInvestigationForm(this.investigationsOrdered);

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

    populateInvestigationForm = function(invOrdered) {

      console.log("inside populateInvestigationForm ");
      var loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
      var invDate = this.convertJSDate(this.selectedInvDate);
      console.log(this.testItemsListComplete);

      this.inputObjects = [];
        //investigationid important
      for(var i=0; i<invOrdered.length; ++i){
        var testid = invOrdered[i].testslistid;;
        var uhid = invOrdered[i].uhid;
        var username = loggedInUserObj.userName;
        if(this.testItemsListComplete==null){
          break;
        }
        var testdata = [];
        if(testid in this.testItemsListComplete){
          var testItemsList = this.testItemsListComplete[testid];
          for(var j=0; j<testItemsList.length;++j){
            var obj = {
              "testid" : "",
              "prn" : "",
              "itemid" : "",
              "itemname" : "",
              "itemvalue" : "",
              "itemunit" : "",
              "resultdate" : "",
              "normalrange" : ""
            }
            obj.testid = testid;
            obj.prn = uhid;
            obj.itemid = this.testItemsListComplete[testid][j].itemid;
            obj.itemname = this.testItemsListComplete[testid][j].itemname;
            obj.itemvalue = this.testItemsListComplete[testid][j].itemvalue;
            obj.itemunit = this.testItemsListComplete[testid][j].itemunit;
            obj.normalrange = this.testItemsListComplete[testid][j].normalrange;
            obj.resultdate = invDate;
            testdata.push(obj);
//							console.log(obj);
          }
        }
        this.inputObjects.push(testdata);
      }
      console.log("inputobjects raw");
      console.log(this.inputObjects);

    }

    getTestItemsList = function() {
			// console.log("Inside function getTestsListDropdown");
      try
      {
        this.http.request(this.apiData.getTestItemsList + '/').
        subscribe(res => {
          this.testItemsListComplete = res.json();

          console.log("getting investigations orered ");
          var todayDate = this.convertJSDate(new Date());
          this.getInvestigationsOrdered("0",todayDate);

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

    decrementDate = function(){
  			this.selectedInvDate = new Date(this.selectedInvDate.getTime() - (24*60*60*1000));
  			this.getInvestigationsOrdered();
        var todayDate = this.dateformatter(new Date(), "gmt", "standard");
        var selectedDate = this.dateformatter(this.selectedInvDate, "gmt", "standard");

  			if(todayDate!=selectedDate)
  				this.disableIncrement = false;

  	}

		incrementDate = function(){
			console.log("increment date"+this.selectedInvDate);
			this.selectedInvDate = new Date(this.selectedInvDate.getTime() + (24*60*60*1000));
			console.log(this.selectedInvDate)
			console.log(this.maxDate)
			this.getInvestigationsOrdered();

			var todayDate = this.dateformatter(new Date(), "gmt", "standard");
      var selectedDate = this.dateformatter(this.selectedInvDate, "gmt", "standard");

			console.log(todayDate)
			console.log(selectedDate)
			if(todayDate===selectedDate){
				this.disableIncrement = true;
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


  ngOnInit() {
    this.getNursingOrder();
    console.log("in the tests controller");
		this.getTestsListMapped();
		this.getTestItemsList();
    if(this.router.url==='/nursing/nursing-panel'){
      this.isDoctorLabTrue = false;
      this.labOrderTab = 'Report';
    }
		var todayDate = this.convertJSDate(new Date());
		console.log("todayDate "+todayDate);

		this.searchObj = { "searchedUHID" : this.baby_uhid,
				"searchedStartDate" : todayDate,
				"searchedEndDate" : todayDate,
				"selectedTestID": "0",
		};

  }

}
