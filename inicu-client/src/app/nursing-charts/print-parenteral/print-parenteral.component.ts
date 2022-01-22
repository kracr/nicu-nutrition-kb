import { Component, OnInit, OnDestroy } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import {Observable} from 'rxjs/Rx';


@Component({
  selector: 'print-parenteral',
  templateUrl: './print-parenteral.component.html'
})
export class PrintParenteral implements OnInit, OnDestroy {

    printDataObj = {};
    isExpandPN: boolean = true;
    additives: boolean;

    constructor(private router: Router) {}

    ngOnInit() {
        this.printDataObj = JSON.parse(localStorage.getItem('ordersParenteralPrint'));
        this.checkAdditives();
        console.log("this.printDataObj", this.printDataObj);
        this.printPdf();
    }


    checkAdditives() {
        if(this.printDataObj["sodiumTotal"] ||
        this.printDataObj["potassiumTotal"] ||
        this.printDataObj["calciumTotal"]||
        this.printDataObj["phosphorousTotal"]||
        this.printDataObj["mviTotal"]||
        this.printDataObj["hco3Total"]||
        this.printDataObj["magnesiumTotal"] ) {
            this.additives = true;
        } else {
            this.additives = false;
        }
    }

    printPdf() {
        setTimeout(()=>{
          window.print();
        },2000);
      setTimeout(()=>{
        localStorage.removeItem('ordersParenteralPrint');
        window.close();
      },3000);
    }


    ngOnDestroy() {

    }
    
}