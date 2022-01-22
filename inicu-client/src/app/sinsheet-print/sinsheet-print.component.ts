import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';
import { Sin } from '../analytics/sin';
import { SinData } from '../analytics/sinData';

@Component({
  selector: 'app-sinsheet-print',
  templateUrl: './sinsheet-print.component.html',
  styleUrls: ['./sinsheet-print.component.css']
})
export class SinsheetPrintComponent implements OnInit {
  http : Http;
  router : Router;
  sin : Sin;
  sinSheetList : Array<SinData>;
  constructor(http : Http, router : Router) {
    this.http = http;
    this.router = router;
    this.sin = new Sin();
    this.sinSheetList = new Array<SinData>();
    this.init();
  }

    init(){
      this.sin = JSON.parse(localStorage.getItem('sinLocalData'));
			this.sinSheetList = this.sin.sinSheetList;
			console.log(this.sinSheetList);
      setTimeout(()=>{
        window.print();
      },800)
      setTimeout(()=>{
        console.log("in this function");
        localStorage.setItem('isCommingFromPrintSinSheet', "true");
        this.router.navigateByUrl('/ana/analytics');
      },1000)
    	localStorage.removeItem("sinLocalData");
    }
  ngOnInit() {
  }

}
