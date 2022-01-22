import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import * as $ from 'jquery/dist/jquery.min.js';

@Component({
  selector: 'app-add-device',
  templateUrl: './add-device.component.html',
  styleUrls: ['./add-device.component.css']
})
export class AddDeviceComponent implements OnInit {

  router : Router;
  http : Http;
  apiData : APIurl;
  iscorrent : boolean;
  deviceMasterData : any;
  beddetails : any;
  childObject : any;
  loggedInUserObj : any;
  filteredBeds : any;
  buttonLabel : string;
  isAlreadyConnected : any;
  deviceLabel : string;
  filtereddevicelist : any;
  deviceButtonLabel : string;
  deviceButtonLabel2 : string;
  deviceType : any;
  response : any;
  isDeviceConnect : boolean;
  firstDevice : number;
  secondDevice : number;
  branchName : string;

  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.apiData = new APIurl();
    this.iscorrent = false;
    this.childObject = JSON.parse(localStorage.getItem('selectedChild'));
    this.loggedInUserObj = JSON.parse(localStorage.getItem('logedInUser'));
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.buttonLabel = "Connect";
    this.isAlreadyConnected = false;
    this.isDeviceConnect = false;
    this.deviceLabel = "Active Device"
    this.deviceButtonLabel = "Connect";
    this.deviceButtonLabel2 = "Connect";
    this.firstDevice = 0;
    this.secondDevice = 0;
    this.response = {};

  }
  ngOnInit() {
    this.getAddDevicePreference();
  }

  addDevice = function(value){
      var label = "";
      if(value == 'first'){
        label = this.deviceButtonLabel;
        this.deviceMasterData.device.deviceName = this.firstDevice;
      }
      if(value == 'second'){
        label = this.deviceButtonLabel2;
        this.deviceMasterData.device.deviceName = this.secondDevice;
      }

			console.log("button status");
			console.log(this.deviceButtonLabel);

			if(label=="Disconnect"){
        try
        {
          this.http.post(this.apiData.disconnectInicuDevice, this.deviceMasterData.device).
          subscribe(res => {
            this.response = (res.json());
            alert(this.response.message);
            if(this.response.type == "success"){
              if(value == 'first'){
                this.deviceButtonLabel="Connect";
                this.firstDevice = "";
              }
              if(value == 'second'){
                this.deviceButtonLabel2="Connect";
                this.secondDevice = "";
              }
              this.loadTemplate();
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
				// disconnect the current active devi
			}
      else{
				console.log("submit is pressed");
				var devData = this.deviceMasterData.device;
				var isSuccessful = this.validate(devData);
				if(isSuccessful){
          try
          {
            this.http.post(this.apiData.addDevice + this.branchName + "/" , this.deviceMasterData.device).
            subscribe(res => {
              this.response = (res.json());
              alert(this.response.response.message);
              if(this.response.response.type == "success"){
                this.loadTemplate();
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
			}
    }

    refreshDevice = function(){
      this.router.navigate(['/dash/dashboard']);
    }

    validate = function(devData){
			var isSuccessful = true;
			if(devData.deviceName==null){
				alert("Please select a valid inicu device.");
				isSuccessful = false;
			}
			return isSuccessful;
		}

    getAddDevicePreference = function(){
			console.log("inside setting preference");
      try
      {
        this.http.post(this.apiData.addSettingPreference).
        subscribe(res => {
          this.response = (res.json());
          this.deviceType = this.response.integration_type;
          if(this.deviceType=="infinity"){
            //this.loadbedDetails();
          }else{
            this.loadTemplate();
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

loadTemplate = function(){
    if(this.childObject.uhid==null){
      alert("could not find uhid! Please login again.");
    }
    try
    {
      this.http.post(this.apiData.getDeviceInfo + "/" + this.childObject.uhid + "/" + this.childObject.bed.key + "/" + this.branchName).
      subscribe(res => {
        this.deviceMasterData = (res.json());
        // iterate deviceMasterData to set date.
        for(var index=0;index<this.deviceMasterData.pastDevice.length;index++){
          var localDate = new Date(this.deviceMasterData.pastDevice[index].date);
          this.deviceMasterData.pastDevice[index].date = localDate;

          var starttime = new Date(this.deviceMasterData.pastDevice[index].timeFrom);
          this.deviceMasterData.pastDevice[index].timeFrom = starttime;

          var endtime = new Date(this.deviceMasterData.pastDevice[index].timeTo);
          this.deviceMasterData.pastDevice[index].timeTo = endtime;
        }
        if(this.deviceMasterData.device.deviceList!=null && this.deviceMasterData.device.deviceList.length > 0){
          this.isDeviceConnect = true;
          if(this.deviceMasterData.device.deviceList.length == 1){
            this.firstDevice = this.deviceMasterData.device.deviceList[0];
            this.secondDevice = "";
            this.deviceButtonLabel="Disconnect";
            this.deviceButtonLabel2="Connect";
          }
          if(this.deviceMasterData.device.deviceList.length == 2){
            this.firstDevice = this.deviceMasterData.device.deviceList[0];
            this.secondDevice = this.deviceMasterData.device.deviceList[1]
            this.deviceButtonLabel="Disconnect";
            this.deviceButtonLabel2="Disconnect";
          }
        }else{
          this.isDeviceConnect = false;
          this.deviceButtonLabel="Connect";
          this.deviceButtonLabel2="Connect";
        }
        console.log(this.deviceMasterData);
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

  getButtonStatus = function(value){
      if(value == 'first'){
  			var isDisabled = true;
  			if(this.deviceButtonLabel=="Connect"){
  				isDisabled = false;
  			}else
  				isDisabled = true;

  			return isDisabled;
      }
      if(value == 'second'){
        var isDisabled = true;
        if(this.deviceButtonLabel2=="Connect"){
          isDisabled = false;
        }else
          isDisabled = true;

        return isDisabled;
      }
		};

    changeDevStatus = function(item){
			console.log(item);
			var deviceid = item.deviceId;
      try
      {
        this.http.post(this.apiData.disconnectDevice +"/"+ this.childObject.uhid +"/"+ this.childObject.bed.key +"/"+ deviceid + "/" + this.branchName).
        subscribe(res => {
          this.response = (res.json());
          console.log("status changed");
          alert(this.response.response.message);
          this.deviceMasterData = this.response;
        },
        err => {
          console.log("Error occured.")
        }
      );
      }
      catch(e){
        console.log("Exceptionthis. in http service:"+e);
      };
		}

}
