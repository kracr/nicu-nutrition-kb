import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NursingNotes } from './nursingNotes';
import { NursingNotesModel } from './nursingNotesModel';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import { AppComponent } from '../app.component';
@Component({
  selector: 'nursing-notes',
  templateUrl: './nursing-notes.component.html',
  styleUrls: ['./nursing-notes.component.css']
})
export class NursingNotesComponent implements OnInit {
  isLoaderVisible : boolean;
  loaderContent : string;
  apiData : APIurl;
  hours : Array<number>;
  nursingNotes : NursingNotesModel;
  isEditShow : boolean;
  uhid : string;
  loggedUser : string;
  fromDate : Date;
  toDate : Date;
  http: Http;
  router : Router;
  response : any;
  maxDate : Date;
  minDate : string;
  fromDateTableNull : boolean;
  toDateTableNull : boolean;
  fromDateTable : Date;
  toDateTable : Date;
  fromToTableError : boolean;
  copiedSentData : Array<any>;

  constructor(http: Http, router: Router) {
    this.isLoaderVisible = false;
    this.loaderContent = 'Saving...';
    this.router = router;
    this.apiData = new APIurl();
  	this.hours = new Array<number>();
    this.nursingNotes = new NursingNotesModel();
    this.isEditShow = false;
    this.http = http;
    this.uhid = JSON.parse(localStorage.getItem('selectedChild')).uhid;
    this.loggedUser = JSON.parse(localStorage.getItem('logedInUser')).userName;
    this.fromDate = new Date();
    this.toDate = new Date();
    this.response = {};
    this.fromDateTable = new Date();
    this.fromDateTable.setHours(0);
    this.fromDateTable.setMinutes(0);
    this.toDateTable = new Date();
  }

  ngOnInit() {
  	this.maxDate = new Date();
    var tempHr = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(0, 2);
    var tempMin = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(3, 5);
    var tempMer = JSON.parse(localStorage.getItem('selectedChild')).timeOfBirth.slice(6, 8);
    if(tempHr == 12 && tempMer == 'AM') {
      tempHr = '00';
    } else if(tempHr != 12 && tempMer == 'PM') {
      tempHr = parseInt(tempHr) + 12;
    }
    var tempFullTime = tempHr +':' +tempMin+':00';
    this.minDate = new Date(JSON.parse(localStorage.getItem('selectedChild')).dob) + '';
    var tempPrevTime = this.minDate.slice(16,24);
    this.minDate = this.minDate.replace(tempPrevTime,tempFullTime);
    for(var i = 0; i < 24; i++){
      this.hours.push(i);
    }
  	this.getNursingNotes(null, null, null);
  }

processPastNotes(){
	//var offset = new Date().getTimezoneOffset() * (60 * 1000);
	for(var i = 0; i < this.nursingNotes.pastNotesList.length; i++) {
		var obj = this.nursingNotes.pastNotesList[i];
		//obj.from_time += offset;
		//obj.to_time += offset;
		obj.nursing_notes = obj.nursing_notes.replace('<span>','');
		obj.nursing_notes = obj.nursing_notes.replace('</span>','');
		obj.nursing_notes = obj.nursing_notes.replace(/<br>/g,'\n');
	}
}

openCKEditorData(){
//	if(this.isEditShow == true){
//		var editor = CKEDITOR.instances["nursingNotes"];
//		editor.destroy(true);
//	}
//	setTimeout(function() {
//		CKEDITOR.replace('nursingNotes');
//	},100);
	this.isEditShow = true;
}

  getNursingNotes(fromDate : Date, toDate : Date, type : string) {
	console.log("comming into the getNursingNotes");
	var fromParameter = null;
	var toParameter = null;

	if(fromDate != null && toDate != null) {
		fromDate.setSeconds(0);
		fromDate.setMilliseconds(0);
		toDate.setSeconds(0);
		toDate.setMilliseconds(0);
		fromParameter = fromDate.getTime();
		toParameter = toDate.getTime();
	}

	 try{
        this.http.request(this.apiData.getNursingNotes + this.uhid + "/" + fromParameter + "/" + toParameter + "/").subscribe((res: Response) => {
          this.nursingNotes = res.json();
          this.processPastNotes();
          this.filterNursingNote(this.fromDateTable, this.toDateTable, 'table');
  				if(this.nursingNotes.currentNotes.nursing_notes!=null) {
  					this.openCKEditorData();
  				}
        });
     }catch(e){
        console.log("Exception in http service:"+e);
     };
  }

	saveNursingNotes() {
		console.log("comming into the saveNursingNotes");
		if(this.nursingNotes.currentNotes.nursing_notes != null && this.nursingNotes.currentNotes.nursing_notes != '') {
			this.nursingNotes.currentNotes.loggeduser = this.loggedUser;
      this.isLoaderVisible = true;
      this.loaderContent = 'Saving...';
			try{
        this.http.post(this.apiData.setNursingNotes, this.nursingNotes).subscribe(res => {
        	this.nursingNotes = res.json();
          this.isLoaderVisible = false;
        	this.processPastNotes();
        },
        err => {
          console.log("Error occured.")
        });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
	}
  filterNursingNote(fromDateTable : Date, toDateTable : Date, flag : String) {
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
      for(var i=0; i < this.nursingNotes.pastNotesList.length; i++) {
        var item = this.nursingNotes.pastNotesList[i];
        if(item.to_time >= fromDateTable.getTime() && item.from_time <= toDateTable.getTime()) {
            var obj = Object.assign({}, item);
            data.push(obj);
            this.copiedSentData.push(obj);
        }
      }
      if(flag == 'print'){
        this.printNursingNotes(fromDateTable, toDateTable, flag);
      }
    }
  }
  printNursingNotes(fromDateTable : Date, toDateTable : Date, flag : String){
    this.response.whichTab = 'Nursing Notes';
    this.response.from_time = fromDateTable;
    this.response.to_time = toDateTable;
    this.response.nursingNotes = this.copiedSentData;
    localStorage.setItem('printNursingDataObjNotes', JSON.stringify(this.response));
    this.router.navigateByUrl('/nursing/nursing-print');
  }
}
