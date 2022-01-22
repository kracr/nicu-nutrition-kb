import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Http, Response} from '@angular/http';
import { Usage } from '../analytics/usage';

@Component({
  selector: 'app-usage-print',
  templateUrl: './usage-print.component.html',
  styleUrls: ['./usage-print.component.css']
})
export class UsagePrintComponent implements OnInit {
      http : Http;
      usage : Usage;
      router : Router;
  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.usage = new Usage();
    this.init();
  }
      init(){
      this.usage =  JSON.parse(localStorage.getItem('usageLocalData'));

      setTimeout(()=>{
      window.print();
    },800)
    setTimeout(()=>{
      console.log("in this function");
        localStorage.setItem('isCommingFromPrintUsage', "true");
        this.router.navigateByUrl('/ana/analytics');
  },1000)
  localStorage.removeItem("usageLocalData");
      }

  ngOnInit() {
  }

}
