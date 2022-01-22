import { Component, OnInit, Pipe, PipeTransform, Injectable } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { NotesTempObj } from './notesTempObj';
import { PrintDataObj } from './printDataObj';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery';
import { AppComponent } from '../app.component';
@Component({
  selector: 'summary-notes',
  templateUrl: './summary-notes.component.html',
  styleUrls: ['./summary-notes.component.css']
})
export class SummaryNotesComponent implements OnInit {

  notesTempObj : NotesTempObj;
  printDataObj : PrintDataObj;
  apiData : APIurl;
  todayDate : any;
  router : Router;
  doctorNotesPrintData : any;
  http : Http;
  child : any;
  symptomsArr : string[];
  expanded: boolean;
  date : Date;
  datetimedialog : Date;
  datetime: Date;
  minDate : string;
  maxDate : Date;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.notesTempObj = new NotesTempObj();
    this.printDataObj = new PrintDataObj();
    this.symptomsArr = [];
    this.expanded = false;
    this.date = new Date();
    this.datetimedialog = new Date();
    this.datetime = new Date();
    this.router = router;
    this.apiData = new APIurl();
    this.init();
  }

  init(){
    this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).admittime.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM'){
      tempHr = '00';
    }else if(tempHr != 12 && tempMer == 'PM'){
      tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).admitDate) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    this.notesTempObj.Type = "Invegtigation";
    this.notesTempObj.moduleNamesDropDown = [
       {"key":"All", "value": "All", "flag" : true, "isDisabled":false},
       {"key" : "RespSystems" , "value" : "Respiratory System","flag" : true, "isDisabled":true},
       {"key":"Jaundice" , "value" : "Jaundice", "flag" : true, "isDisabled":true},
       {"key":"Infection" , "value" : "Infection", "flag" : true, "isDisabled":true},
       {"key":"CNS" , "value" : "CNS", "flag" : true, "isDisabled":true},
       {"key":"Stable Notes" , "value" : "Stable Notes", "flag" : true, "isDisabled":true},
       {"key":"Misc" , "value" : "Misc", "flag" : true, "isDisabled":true}
    ];

    this.printDataObj.timeFrom = "08";
    this.printDataObj.timeTo = "08";
    this.printDataObj.dateFrom = new Date(((new Date()).getTime() - (24*60*60*1000)));
    this.printDataObj.dateTo = new Date();
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
    console.log(this.child.uhid);
    this.printDataObj.uhid = this.child.uhid;
    this.printDataObj.isNotesBySystem = true;
    this.printDataObj.moduleName = ["All","Jaundice","RespSystems","Infection","CNS","Stable Notes", "Misc"];
    this.todayDate = new Date();

    this.maxDate = new Date(this.todayDate.getTime() + 24* 60 * 60 * 1000);
    var child = JSON.parse(localStorage.getItem('selectedChild'));
    this.getDoctorNotesPrintData();
  }

  getDoctorNotesPrintData = function(){
    console.log(this.printDataObj);
    try{
        this.http.post(this.apiData.getDoctorNotesPrintData,this.printDataObj).
        subscribe(res => {
            this.doctorNotesPrintData = res.json();
          },
          err => {
            console.log("Error occured.")
          });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }

  showCheckboxes = function(id) {
		console.log(id);
		var fields = id.split('-');
		var name = fields[2];
		console.log(name);
		var checkboxContId = "#checkboxes-"+ name;
		console.log(checkboxContId);

		var width = $("#multiple-selectbox-1").width();
		if (!this.expanded) {
      $(checkboxContId).toggleClass("show");
			// checkboxes.style.display = "block";
			$(checkboxContId).width(width);
			this.expanded = true;
		} else {
			$(checkboxContId).removeClass("show");
			this.expanded = false;
		}
	};

	systemValue = function(Value,multiCheckBoxName){
		if(multiCheckBoxName=='system'){
			this.symptomsArr = this.printDataObj.moduleName;
			if(this.symptomsArr==null){
				this.symptomsArr =[];
			}
			var flag = true;
			if(this.symptomsArr.length == 0){
				this.symptomsArr.push(Value);
			}else{
				for(var i=0;i< this.symptomsArr.length;i++){
					if(Value == this.symptomsArr[i]){
						this.symptomsArr.splice(i,1);
						flag = false;
					}
				}
				if(flag == true){
					this.symptomsArr.push(Value);
				}
			}

			if(this.symptomsArr==null){
				this.printDataObj.moduleName = [];
			}else{
				this.printDataObj.moduleName = this.symptomsArr;
			}

			if(Value=='All' ){
				if(flag==true){
					for(var index=0; index<this.notesTempObj.moduleNamesDropDown.length;index++){
						this.notesTempObj.moduleNamesDropDown[index].flag = true;
						this.printDataObj.moduleName[index] = this.notesTempObj.moduleNamesDropDown[index].key;
					}

				}else{
					this.printDataObj.moduleName = [];
          for(var index=0; index<this.notesTempObj.moduleNamesDropDown.length;index++){
						this.notesTempObj.moduleNamesDropDown[index].flag = false;
					}
				}
			}else{
				if(this.printDataObj.moduleName.length>=0){
					var tempArr = [];
					for(var index=0; index<this.printDataObj.moduleName.length;index++){
						if(this.printDataObj.moduleName[index]!='All'){
							tempArr[index] = this.printDataObj.moduleName[index];
						}
					}
					this.notesTempObj.moduleNamesDropDown[0].flag=false;
				}
				this.printDataObj.moduleName = tempArr;
			}
			console.log(this.symptomsArr);
			this.getDoctorNotesPrintData();
		}
	};

  printPdf = function(){
    localStorage.setItem('printDataObj', JSON.stringify(this.printDataObj));
    this.router.navigateByUrl('/summary/summary-notes-print');
  };

  ngOnInit() {

  }

}
