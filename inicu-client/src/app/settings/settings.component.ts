import { Component, OnInit } from '@angular/core';
import { APIurl } from '../../../model/APIurl';
import {Http, Response} from '@angular/http';
import { Router } from '@angular/router';
import * as $ from 'jquery/dist/jquery.min.js';
import { SettingJson } from './settingJson';
import { RegisterDevice } from './registerDevice';
import { ExceptionDataObj } from './exceptionDataObj';
import { BoxDetail } from './boxDetail';
import { Chart } from 'angular-highcharts';
import { HeartBeatObj } from './heartBeatObj';
import { NursingOutputParameters } from './nursingOutputParameters';
import { RefHospitalBranchName } from './refHospitalBranchName';

@Component({
  selector: 'settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  apiData : APIurl;
  http: Http;
  settingJson : SettingJson;
  registerDevice : RegisterDevice;
  boxDetail : BoxDetail;
  devicetypelist1 : Array<any>;
  devicetypelist2 : Array<any>;
  bedsList : Array<any>;
  macDeviceValue : any;
  uhidValue : string;
  boxSerialNo : string;
  macExceptionsData : Array<ExceptionDataObj>;
  editUhidMessage : any;
  editObj : RegisterDevice;
  deleteValue : string;
  devicemacId2 : string;
  devicenamelist1 : Array<any>;
  devicenamelist2 : Array<any>;
  counter : number;
  array : Array<any>;
  forBoxDetails : Array<any>;
  boxName : string;
  showBoxDeatil : boolean;
  visibleBox : boolean;
  mapUhid : string;
  boxdetails : any;
  TempBoxSerialNo : any;
  heartBeatObj : HeartBeatObj;
  heartBeatData : any;
  chartCont : Chart;
  heartbeatPoints : any;
  branchName : string;
  oldUhid : any;
  newUhid : any;
  refHospitalBranchName: RefHospitalBranchName;
  nursingOutputParameters : NursingOutputParameters;

  constructor(http: Http, private router: Router) {
    this.apiData = new APIurl();
    this.http = http;
    this.settingJson = new SettingJson();
    this.registerDevice = new RegisterDevice();
    this.devicetypelist1 = [];
    this.devicetypelist2 = [];
    this.bedsList = [];
    this.array = [];
    this.boxDetail = new BoxDetail();
    this.forBoxDetails = [];
    this.editObj = new RegisterDevice();
    this.heartBeatObj = new HeartBeatObj();
    this.heartbeatPoints = [];
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
    this.refHospitalBranchName = new RefHospitalBranchName();
    this.nursingOutputParameters = new NursingOutputParameters();
    this.refHospitalBranchName.branchname = this.branchName;
  }

  logoSubmit = function(){
  //   try
  //   {
  //     this.visibleBox = false;
  //     var logoData = {
	// 				"name": this.name,
	// 				"image": logo
	// 		};
	// 		console.log(logoData);
  //     this.http.post(this.apiData.logo , JSON.stringify({
  //       someParameter: 1,
  //       someOtherParameter: 2
  //     })).subscribe(res => {
  //       console.log(res.json());
  //       this.settingJson = res.json();
  //     },
  //     err => {
  //       console.log("Error occured.")
  //     }
  //   );
  // }
  // catch(e){
  //   console.log("Exception in http service:"+e);
  // };
};


  getSettingDetails = function(){
    try
    {
      this.http.post(this.apiData.getSetting , JSON.stringify({
        someParameter: 1,
        someOtherParameter: 2
      })).subscribe(res => {
        console.log(res.json());
        this.settingJson = res.json();
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

getNursingOuputParameters = function(){
    try{
      this.http.request(this.apiData.getNursingOuputParameters + "/" + this.branchName + "/")
      .subscribe((res: Response) => {
        this.nursingOutputParameters = res.json();
      });
    }catch(e){
      console.log("Exception in http service:"+e);
    };
}
getRegisteredDevice = function(){
  try
  {
    this.http.post(this.apiData.registerDevice + "/" + this.branchName , JSON.stringify({
      someParameter: 1,
      someOtherParameter: 2
    })).subscribe(res => {
      console.log(res.json());
      this.registerDevice = res.json();
      this.boxDetail.dropdown = res.json().dropDowns;
      for (var key in this.registerDevice.registeredDevices) {
        if (this.registerDevice.registeredDevices.hasOwnProperty(key)) {
          var utcDate = this.registerDevice.registeredDevices[key].addedon;
          var localDate = new Date(utcDate);
          this.registerDevice.registeredDevices[key].addedon = localDate;
        }
      }

      this.devicetypelist1 = Object.keys(this.registerDevice.dropDowns.deviceTypeBrand);
      this.devicetypelist2 = Object.keys(this.registerDevice.dropDowns.deviceTypeBrand);
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
showMapModal = function(){
  $("#mapOverlay").css("display", "block");
  $("#mapPopup").addClass("showing");
}
closeMap = function() {
  $("#mapOverlay").css("display", "none");
  $("#mapPopup").toggleClass("showing");
  $('body').css('overflow', 'auto');
};
mapDevice = function(macValue){
  this.macDeviceValue = macValue;
  this.showMapModal();
  console.log(macValue);
}
submitMapDevice = function(uhidValue){
  console.log(uhidValue);
  console.log(this.macDeviceValue);
  this.uhidValue =  uhidValue + '';
  this.macDeviceValue = this.macDeviceValue + '';
  console.log(typeof(this.macDeviceValue));
  console.log(typeof(uhidValue));
  try
  {
    this.http.post(this.apiData.connectRegisteredDevice + this.macDeviceValue + '/' + this.uhidValue + '/' + this.branchName + "/"  , JSON.stringify({
      someParameter: 1,
      someOtherParameter: 2
    })).subscribe(res => {
      console.log("submitMapDevice");
      console.log(res.json());
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
showExceptionModal = function(){
  $("#exceptionOverlay").css("display", "block");
  $("#exceptionPopup").addClass("showing");
}
closeException = function() {
  $("#exceptionOverlay").css("display", "none");
  $("#exceptionPopup").toggleClass("showing");
  $('body').css('overflow', 'auto');
};
showException = function(boxSerialNo){
  console.log(boxSerialNo);
  this.boxSerialNo = boxSerialNo + '';
  console.log(typeof(boxSerialNo));
  try{
    this.http.request(this.apiData.getDeviceExceptions + this.boxSerialNo)
    .subscribe((res: Response) => {
      console.log(res.json());
      this.macExceptionsData = res.json();
      this.showExceptionModal();
    });
  }catch(e){
    console.log("Exception in http service:"+e);
  };
}

showEdit = function(showValue){
  this.editObj = Object.assign({}, showValue);
  console.log(this.editObj);
  if(this.editObj.board1 != null){
    this.editObj.devicenamelist1 = null;
    this.editObj.devicetypelist1 = this.devicetypelist1;
    this.editObj.devicenamelist1 = this.registerDevice.dropDowns.deviceBrandName[this.editObj.board1.brandName];
    this.editObj.devicebrandlist1 = this.registerDevice.dropDowns.deviceTypeBrand[this.editObj.board1.deviceType];
    this.devicemacId1 = this.registerDevice.dropDowns.deviceTypeBrand[this.editObj.board1.macId];

  }
  if(this.editObj.board2 != null){
    this.editObj.devicenamelist2 = null;
    this.editObj.devicenamelist2 = this.registerDevice.dropDowns.deviceBrandName[this.editObj.board2.brandName];
    this.editObj.devicebrandlist2 = this.registerDevice.dropDowns.deviceTypeBrand[this.editObj.board2.deviceType];
    this.devicemacId2 = this.registerDevice.dropDowns.deviceTypeBrand[this.editObj.board2.macId];
  }
  $("#editOverlay").css("display", "block");
  $("#editPopup").addClass("showing");

}
closeEditModal = function(value) {
  this.editObj = null;
  $("#editOverlay").css("display", "none");
  $("#editPopup").toggleClass("showing");
  $('body').css('overflow', 'auto');
  if(value == 'OK'){
    this.getSettingDetails();
    this.getRegisteredDevice();
  }
};
editBox = function(){
  console.log("sending box to save");
  console.log(JSON.stringify(this.editObj));
  if(this.editObj.board2==null){
    this.editObj.board2 = {};
    this.editObj.board2.boardName = "Beagle Board 2";
    this.editObj.board2.macId = null;
    this.editObj.board2.deviceType = null;
    this.editObj.board2.brandName = null;
    this.editObj.board2.deviceName = null;
  }

  try
  {
    this.http.post(this.apiData.editBoxDetails, JSON.stringify(this.editObj)).subscribe(res => {
      console.log("response form server");
      console.log(res.json());
      this.closeEditModal('OK');
    },
    err => {
      console.log("Error occured.")
    });
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
}
deleteBoxFromList = function(deleteValue){
  console.log(deleteValue);
  this.deleteValue = deleteValue + '';
  console.log(typeof(deleteValue));
  try
  {
    this.http.post(this.apiData.deleteBox + deleteValue + '/' , JSON.stringify(this.editObj)).subscribe(res => {
      console.log("response form server");
      console.log(res.json());
      if(res.json().type == "success"){
        this.getSettingDetails();
        this.getRegisteredDevice();
      }
    },
    err => {
      console.log("Error occured.")
    });
  }
  catch(e){
    console.log("Exception in http service:"+e);
  };
}
filterBrandName = function(devType,whichBoard){
  console.log(devType);
  console.log(whichBoard);
  if(whichBoard == 'board1'){
    console.log(this.boxDetail);
    this.devicebrandlist1 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.boxDetail.box.board1.brandName = null;
    this.boxDetail.box.board1.deviceName = null;
    this.devicenamelist1 = null;
    console.log(this.devicebrandlist1);
  }
  if(whichBoard == 'board2'){
    this.devicebrandlist2 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.boxDetail.box.board2.brandName = null;
    this.boxDetail.box.board2.deviceName = null;
    this.devicenamelist2 = null;
    console.log(this.devicebrandlist2);
  }

};

filterDeviceName = function(devbrand,whichBoard){
  console.log(devbrand);
  console.log(whichBoard);
  if(whichBoard == 'board1'){
    this.devicenamelist1 = null;
    this.devicenamelist1 = this.boxDetail.dropdown.deviceBrandName[devbrand];
    this.boxDetail.box.board1.deviceName = null;
  }
  if(whichBoard == 'board2'){
    this.devicenamelist2 = null;
    this.devicenamelist2 = this.boxDetail.dropdown.deviceBrandName[devbrand];
    this.boxDetail.box.board2.deviceName = null;
  }

};
emptyBrandDevice = function(devType,whichBoard){
  console.log(devType);
  console.log(whichBoard);
  if(whichBoard == 'board1'){
    this.editObj.board1.brandName = '';
    this.editObj.board1.deviceName = '';
    this.editObj.devicebrandlist1 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.editObj.devicenamelist1 = '';
  }
  if(whichBoard == 'board2'){
    this.editObj.board2.brandName = '';
    this.editObj.board2.deviceName = '';
    this.editObj.devicebrandlist2 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.editObj.devicenamelist2 = '';
  }

};
filterDeviceNameEdit = function(devbrand,whichBoard){
  console.log(devbrand);
  console.log(whichBoard);
  if(whichBoard == 'board1'){
    this.editObj.devicenamelist1 = '';
    this.editObj.devicenamelist1 = this.boxDetail.dropdown.deviceBrandName[devbrand];
  }
  if(whichBoard == 'board2'){
    this.editObj.devicenamelist2 = '';
    this.editObj.devicenamelist2 = this.boxDetail.dropdown.deviceBrandName[devbrand];
  }

};

filterBrandNameEdit = function(devType,whichBoard){
  console.log(devType);
  console.log(whichBoard);
  if(whichBoard == 'board1'){
    console.log(this.boxDetail);
    this.editObj.devicebrandlist1 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.editObj.devicenamelist1 = null;
  }
  if(whichBoard == 'board2'){
    this.devicebrandlist2 = this.boxDetail.dropdown.deviceTypeBrand[devType];
    this.devicenamelist2 = null;
    console.log(this.devicebrandlist2);
  }

};
submitRegisteredDevice = function(){
  var isValid = false;
  isValid = this.validateRegisteredDevice();
  if(isValid){
    try
    {
      this.http.post(this.apiData.adddRegisteredDevice, JSON.stringify(this.editObj)).subscribe(res => {
        if(res.json().response.type == "success"){
          this.registerDevice = res.json();
        }
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }
};
validateRegisteredDevice = function(){
  var isValid = true;
  if(this.registerDevice.deviceDetails.deviceType==null||this.registerDevice.deviceDetails.deviceType==""){
    // showModal("Device Type cannot be left empty.", "failure");
    isValid = false;
  }else if(this.registerDevice.deviceDetails.brandName==null||this.registerDevice.deviceDetails.brandName==""){
    // showModal("Device Brand cannot be left empty.", "failure");
    isValid = false;
  }else if(this.registerDevice.deviceDetails.deviceName==null||this.registerDevice.deviceDetails.deviceName==""){
    // showModal("Device Name cannot be left empty.", "failure");
    isValid = false;
  }
  return isValid;
};
submitSetting = function(param){
  console.log(param);
  if(param=="basic"){
    this.settingJson.dischargesetting.basic = true;
    this.settingJson.dischargesetting.advanced = false;
  }else if(param=="advanced"){
    this.settingJson.dischargesetting.basic = false;
    this.settingJson.dischargesetting.advanced = true;
  }else if(param=="inicu"){
    this.settingJson.devicesetting.inicu = true;
    this.settingJson.devicesetting.infinity = false;
    this.settingJson.devicesetting.carescape = false;
  }else if(param=="infinity"){
    this.settingJson.devicesetting.inicu = false;
    this.settingJson.devicesetting.infinity = true;
    this.settingJson.devicesetting.carescape = false;
  }else if(param=="carescape"){
    this.settingJson.devicesetting.inicu = false;
    this.settingJson.devicesetting.infinity = false;
    this.settingJson.devicesetting.carescape = true;
  }
  try
  {
    this.http.post(this.apiData.addSetting, this.settingJson).subscribe(res => {
        console.log(res);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };
  addBox = function(boardName){
    if(boardName!=null && boardName!=""){
      try
      {
        this.http.post(this.apiData.addBoard + '/'+boardName,
        JSON.stringify(this.settingJson)).subscribe(res => {
          this.boxdetails = res.json();
          boardName = null;
          this.boxName = "";
        },
        err => {
          console.log("Error occured.")
        });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }else{
      //showModal("Please enter the name.","failure");
    }
  };
  showBoxDetails = function(indexValue){
    if(this.forBoxDetails[indexValue]!=null&&this.forBoxDetails[indexValue]==false){
      this.forBoxDetails[indexValue]=true;
    }else{
      this.forBoxDetails[indexValue]=false;
    }
  }
  plusButton = function(indexValue){
    return !(this.forBoxDetails[indexValue]);
  }
  minusButton = function(indexValue){
    return this.forBoxDetails[indexValue];
  }
  hideBoxMinus = function(indexValue){
    if(this.forBoxDetails[indexValue]!=null&&this.forBoxDetails[indexValue]==false){
      this.forBoxDetails[indexValue]=true;
    }else{
      this.forBoxDetails[indexValue]=false;
    }
  }
  hideBoxDetails = function(indexValue){
    return this.forBoxDetails[indexValue];
  }

  clearBoradData = function(boardValue){
    if(boardValue == 'board1'){
      this.boxDetail.box.board1 = this.tempBoardDetails;
    }
    if(boardValue == 'board2'){
      this.boxDetail.box.board2 = this.tempBoardDetails;
    }
  }
  SaveBox = function(){
    try
    {
      this.http.post(this.apiData.saveBoxDetails + "/" + this.branchName,
        JSON.stringify(this.boxDetail.box)).subscribe(res => {
          this.deleteBox();
        },
        err => {
          console.log("Error occured.")
        });
      }
      catch(e){
        console.log("Exception in http service:"+e);
      };
    }
    deleteBox = function(){
      this.boxDetail = null;
      this.visibleBox = false;
    }
    addDeviceBox = function(){
      this.visibleBox = true;
      try
      {
        this.http.post(this.apiData.generateBoxDetails,).subscribe(res => {
            this.boxDetail = res.json();
            this.boxDetail.box.board1.boardName = 'Beagle Board 1';
            this.boxDetail.box.board2.boardName = 'Beagle Board 2';
            this.tempBoardDetails = this.boxDetail.box.board1;
            this.devicetypelist1 = Object.keys(this.boxDetail.dropdown.deviceTypeBrand);
            this.devicetypelist2 = Object.keys(this.boxDetail.dropdown.deviceTypeBrand);
          },
          err => {
            console.log("Error occured.")
          });
        }
        catch(e){
          console.log("Exception in http service:"+e);
        };
      };

      editUhid = function(oldUhid,newUhid){
        if(this.oldUhid != "" &&  this.oldUhid != undefined && this.newUhid != "" && this.newUhid != undefined ) {
          try{
            this.http.request(this.apiData.editUhid + oldUhid + "/" + newUhid + "/")
            .subscribe((res: Response) => {
              var editUhidResponseData = res.json();
              this.editUhidMessage = editUhidResponseData.message;
              this.showEditUhidModal();
            });
          }catch(e){
            console.log("Exception in http service:"+e);
          };
        }
      }

      addHelplineNumbers = function(){
        try{
          this.http.post(this.apiData.addHelplineNumbers, this.refHospitalBranchName)
          .subscribe((res: Response) => {
            var helplineResponseData = res.json();
          });
        }catch(e){
          console.log("Exception in http service:"+e);
        };
      }

      setNursingPrintFormat = function(){
        try{
          this.http.post(this.apiData.setNursingPrintFormat, this.refHospitalBranchName)
          .subscribe((res: Response) => {
            var nursingPrintFormatResponseData = res.json();
          });
        }catch(e){
          console.log("Exception in http service:"+e);
        };
      }

      setNursingOutputParameters = function(){
        try{
          this.http.post(this.apiData.setNursingOutputParameters, this.nursingOutputParameters)
          .subscribe((res: Response) => {
            var nursingOutputParametersResponseData = res.json();
          });
        }catch(e){
          console.log("Exception in http service:"+e);
        };
      }

      showEditUhidModal = function(){
        $("#editUhidOverlay").css("display", "block");
        $("#editUhidPopup").addClass("showing");
      }

      closeEditUhidModal = function() {
        $("#editUhidOverlay").css("display", "none");
        $("#editUhidPopup").toggleClass("showing");
        $('body').css('overflow', 'auto');
      };
      checkMacID = function(){
        if(this.boxDetail.box.board1.macId == this.boxDetail.box.board2.macId){
          alert("Mac ID Can't be Same");
          this.boxDetail.box.board1.macId = null;
          this.boxDetail.box.board2.macId = null;
        }
      }

      showHeartBeatModal = function(boxSerialNo) {
        $("#HeartBeatOverlay").css("display","block");
        $("#HeartBeatPopup").addClass("showing");
        this.heartBeatObj.heartBeatTo = new Date();
        this.heartBeatObj.heartBeatFrom = new Date(this.heartBeatObj.heartBeatTo.getTime() - 3600000);
        this.TempBoxSerialNo = boxSerialNo;
        this.chartCont= new Chart({
          chart : {
            type : 'line',
            zoomType : 'xy',
          },
          xAxis : {
            title : {
              text : 'time'
            }
          },
          yAxis : {
            title : {
              text : 'data received'
            }
          }
        })
      }
      closeHeartBeat = function() {
					$("#HeartBeatOverlay").css("display","none");
					$("#HeartBeatPopup").toggleClass("showing");
					$('body').css('overflow', 'auto');
      };

      showHeartBeatData = function(){
				if(this.TempBoxSerialNo != null){
					this.heartBeatObj.boxSerialNo = this.TempBoxSerialNo;
					this.heartBeatObj.heartBeatFrom = new Date(this.heartBeatObj.heartBeatFrom);
					this.heartBeatObj.heartBeatTo = new Date(this.heartBeatObj.heartBeatTo);

					var originalMatrix = [];
					var interval = ((this.heartBeatObj.heartBeatTo - this.heartBeatObj.heartBeatFrom)/60000);
					for (var i = 0; i<interval;i++){
						originalMatrix.push(0);
					}
					console.log(this.heartBeatObj);

          try{
              this.http.post(this.apiData.getDeviceHeartBeatUsingFromTo, this.heartBeatObj)
              .subscribe(res => {
                this.heartBeatData = (res.json());

                var datapoints = this.heartBeatData;
                if(datapoints.length>0){
                  var points = [];
                  var dateTime = [];
                  var i;
                  var startTime = this.heartBeatData[0].creationtime;
                  console.log(new Date(parseInt(startTime)));
                  dateTime.push(new Date(parseInt(startTime)));

                  var startTimeLag = ((this.heartBeatData[0].creationtime - this.heartBeatObj.heartBeatFrom.getTime())/60000);

                  if(startTimeLag > 1){
                    for (i = 1; i < startTimeLag; i++) {
                      points.push(0);
                    }
                  }

                  var len = datapoints.length;

                  for (i = 0; i < len; i++) {
                    console.log(parseInt(datapoints[i].creationtime) - parseInt(startTime));
                    if (parseInt(datapoints[i].creationtime) - parseInt(startTime) < 70000){
                      points.push(1);
                      dateTime.push(new Date(parseInt(startTime)));
                    }
                    else {
                      //	points.push(0);
                      while (parseInt(datapoints[i].creationtime) - parseInt(startTime) > 70000) {
                        points.push(0);
                        startTime += 60000;
                        dateTime.push(new Date(parseInt(startTime)));
                      }
                      //points.push(1);
                      dateTime.push(new Date(parseInt(datapoints[i].creationtime)));
                    }
                    startTime = datapoints[i].creationtime;
                  }

                  var endTimeLag = ((this.heartBeatObj.heartBeatTo.getTime() - startTime)/60000);

                  if(endTimeLag > 1){
                    for (i = 1; i < endTimeLag; i++) {
                      points.push(0);
                    }
                  }

                  for(i = 0; i < interval;i++){
                    if(points[i] != null && points[i] != undefined){
                      originalMatrix[i] = points[i];
                    }
                  }
                  for(i = 0 ; i < originalMatrix.length ; i++){
                    this.heartbeatPoints.push([i,  originalMatrix[i]]);
                  }

                  this.chartCont = new Chart({
                    chart : {
                      type : 'line',
                      zoomType : 'xy',

                    },
                    xAxis : {
                      title : {
                        text : 'time'
                      }
                    },

                    yAxis : {
                      title : {
                        text : 'data received'
                      },
                      allowDecimals : false,
                      tickInterval : 1,
                      min : 0,
                      max : 1
                    },
                    series: [{
                        data: originalMatrix
                    }]
                  })
                }
                else{
                  var points = [];
                  var dateTime = [];
                  this.chartCont= new Chart({
                    chart : {
                      type : 'line',
                      zoomType : 'xy',

                    },
                    xAxis : {
                      title : {
                        text : 'time'
                      }
                    },

                    yAxis : {
                      title : {
                        text : 'data received'
                      }
                    }
                  })
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
        else{
          alert('No Box Exits');
        }
      }

      handleHeartBeatData = function(response : any){



      }

  ngOnInit() {
    this.getSettingDetails();
    this.getRegisteredDevice();
    this.getNursingOuputParameters();
  }

    }
