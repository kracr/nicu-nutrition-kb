import { Component, NgModule, OnInit, Pipe, PipeTransform, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {Http, Response} from '@angular/http';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery/dist/jquery.min.js';


@Component({
  selector: 'camera',
  templateUrl: './camera.component.html',
  styleUrls: ['./camera.component.css']
})
export class CameraComponent implements OnInit {
  child : any;
  apiData : APIurl;
  http: Http;
  userDetails : any;
  settingRefObj : any;
  gestWeek : any;
  gestDays : any;
  iterateVariable : any;
  babyVideoData : any;
  babyVideoObj : any;
  babyDisplayDataObj : any;
  myInterval : any;
  tempArrayLen : number;
  tempInterval : number;
  changeValue : boolean;
  counter : number;
  framesList : Array<string>;
  constructor(http: Http, private router: Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.child = JSON.parse(localStorage.getItem('selectedChild'));
    this.iterateVariable = 0;
    this.babyVideoData = {};
    this.babyDisplayDataObj = {};
    this.counter = 0;
    this.framesList = new Array<string>();
    this.babyVideoObj = {
      "isVideoAvl" : false
    };


   }

  setBabyDisplayString = function(){
     if(this.myInterval == undefined){
        this.myInterval = setInterval(() => {
        if(this.framesList[0] != undefined && this.framesList[0] != null){
          if(this.babyDisplayDataObj.babyDisplayString != ''){
            this.babyDisplayDataObj.babyDisplayString = null;
          }
          this.babyDisplayDataObj.babyDisplayString = "data:image/jpg;base64," + this.framesList.splice(0, 1);
          this.counter = this.counter + 1;
          console.log(this.counter);
        }
      },this.tempIntervals);
       $("#loadingOverlay").removeClass("loading-overlay");
     }
    }

    closeBabyVideo = function(){
      this.changeValue = false;
      clearInterval(this.myInterval);
      localStorage.setItem('runVideo', JSON.stringify(false));
    }

   getVideo = function(){
    if(JSON.parse(localStorage.getItem('runVideo')) == true){
      this.myGetApiInterval = setInterval(() => {
        this.changeValue = true;
        this.babyVideoData.framesTempList = [];
        if(this.isVideoStart == true){
          this.framesList = [];
        }
        try
        {
          this.http.request(this.apiData.getBabyVideo + this.child.uhid +'/',).
          subscribe(res => {
            this.babyVideoData.framesTempList = res.json();
            if(this.framesList != undefined && this.framesList != null){
            if(this.babyVideoData.framesTempList.length > 0 ){
            console.log(res.json());
            this.framesList = this.framesList.concat(this.babyVideoData.framesTempList);
            }
            // this.tempInterval = 60000 / this.babyVideoData.framesTempList.length;
            this.tempInterval = 65;
            this.tempArrayLen = this.framesList.length;
            this.setBabyDisplayString();
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
    },500);
  }else{
    clearInterval(this.myInterval);
    clearInterval(this.myGetApiInterval);
    this.changeValue = false;
  }

   }

  ngOnInit() {
    console.log("in camera feed controller");
    window.setInterval(function(){
      if(localStorage.getItem('selectedChild') != null){
        this.child = JSON.parse(localStorage.getItem('selectedChild'));
          if(this.child.gestation != null){
            this.gestWeek = this.child.gestation.substr(0, 2);
              this.gestDays = this.child.gestation.substr(9, 1);
          }
        }
      }, 1000);

      this.userDetails= JSON.parse(localStorage.getItem('logedInUser'));
      console.log("The User Details are");
      console.log(this.userDetails);
      this.settingRefObj = JSON.parse(localStorage.getItem('settingReference'));
      this.getVideo();
  }

}










