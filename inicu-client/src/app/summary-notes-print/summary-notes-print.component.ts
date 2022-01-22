import { Component, DoCheck, OnChanges, OnInit } from '@angular/core';
import { Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';

@Component({
  selector: 'summary-notes-print',
  templateUrl: './summary-notes-print.component.html',
  styleUrls: ['./summary-notes-print.component.css']
})
export class SummaryNotesPrintComponent implements OnInit {
   printDataObj : any;
   child : any;
   childData : any;
   tobHours : any;
   tobMins : any;
   tobMer : any;
   gestWeek : any;
   gestation : any;
   gestDays : any;
   userObj : any;
   feedType : string;
   doctorNotesPrintData : any;
   router : Router;
   http : Http;

  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
		this.childData = JSON.parse(localStorage.getItem('selectedChild'));
		if(this.child.timeOfBirth != null){
			this.tobHours = this.child.timeOfBirth.slice(0,2);
			this.tobMins = this.child.timeOfBirth.slice(3,5);
			this.tobMer = this.child.timeOfBirth.slice(6,8);
		}
		if(this.child.gestation != null){
			this.gestWeek = this.child.gestation.substr(0, 2);
    		this.gestDays = this.child.gestation.substr(9, 1);
		}
		console.log(this.childData);
		this.userObj = JSON.parse(localStorage.getItem('logedInUser')).userName;
		this.printDataObj = {};
     this.printDataObj = JSON.parse(localStorage.getItem('printDataObj'));
      console.log("doctornotes-pdf-controller is running");
   }

     

    printPdf = function(){
			this.ButtonVisible = false;
			setTimeout(window.print, 1000);
			setTimeout( () => {this.router.navigateByUrl('/summary/progress-notes');},3000);
		};


  ngOnInit() {
    this.printPdf();
  }

}
