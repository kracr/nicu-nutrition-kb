import { Component, OnInit } from '@angular/core';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import { NicuRoomObj } from './nicuRoomObj';
import { RoomList } from './roomList';
import { Room } from './Room';
import { APIurl } from '../../../model/APIurl';
import { BedJSON } from './bedJSON';
import { ReturnedObject } from './returnedObject';
import { RootObject } from './rootObject';
import { FinalBedJson } from './finalBedJson';
@Component({
  selector: 'roommanagement',
  templateUrl: './roommanagement.component.html',
  styleUrls: ['./roommanagement.component.css']
})
export class RoommanagementComponent implements OnInit {

	http: Http;
  apiData : APIurl;
	bedValue : number;
	newRoom : string;
	newBed : number;
	moveBed : string;
	moveBedStatus : boolean;
	bedId : string;
	vacant : boolean;
	isBed : boolean;
	bedDisplay : boolean;
	changeRoom : Array<string>;
  bedData : FinalBedJson;
  roomBed : NicuRoomObj;
  roomId : string;
	value : number;
  moveInRoom : string;
  vacantBedList : Array<any>;
  deleteRoom : number;
  branchName : string;

  constructor(http: Http) {
  	this.moveBed = "";
    this.apiData = new APIurl();
  	this.moveBedStatus = false;
  	this.vacant = true;
    this.value = 0;
    this.vacantBedList = [];
    this.changeRoom = [];
    this.moveInRoom = "";
    this.bedData = new FinalBedJson();
    this.roomBed = new NicuRoomObj();
    this.http = http;
    this.branchName = JSON.parse(localStorage.getItem('logedInUser')).branchName;
  	this.init();
  }

  init() {
    this.getBedData();
  };

  getBedData(){
    this.moveBed="";
		this.moveBedStatus=false;
		this.vacant=true;
		let dataParams;
		try{
        this.http.post(this.apiData.getBedData + this.branchName + "/",
        dataParams).subscribe(res => {
           this.handleGetData(res.json());
        },
        err => {
          console.log("Error occured.")
        });
     }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	}

	handleGetData(responseData: any){
    console.log(responseData);
    this.bedData = responseData;
    if(this.value==0 && this.bedData.returnedObject.roomList != null && this.bedData.returnedObject.nicuRoomObj != null){
  		this.roomId=this.bedData.returnedObject.roomList[0].key;
    	this.newBed = parseInt(this.bedData.returnedObject.nicuRoomObj[0].newBed);
  		this.roomBed=this.bedData.returnedObject.nicuRoomObj[0];
		}
		this.value=1;
	}

  roomNoCheck(){
	   if(this.newRoom==null||this.newRoom==""){
		    alert("Room No must be filled ");
	   }
	   else{
		    this.addNewRoom();
		 }
	}

  addNewRoom(){
  	this.moveBed="";
  	this.moveBedStatus=false;
  	this.vacant=true;
  	this.newRoom=this.newRoom;
  	let dataParams;
    try{
      this.http.post(this.apiData.addRoom + this.newRoom + '/test/' + this.branchName,
      dataParams).subscribe(res => {
        this.handleGetRoomData(res.json());
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};

  handleGetRoomData(responseData : any){
   console.log(responseData);
   alert(responseData.message);
   this.getBedData();
  }

  getBedListOfRoom(roomKey){
		this.moveBed="";
		this.moveBedStatus=false;
		this.vacant=true;
		if(this.deleteRoom==0){
		    this.roomId=roomKey;
		}
		else{
		    this.deleteRoom=0;
        if(this.bedData.returnedObject.nicuRoomObj != null){
		        this.roomId = this.bedData.returnedObject.nicuRoomObj[0].room.key;
        }
		}
    if(this.bedData.returnedObject.nicuRoomObj != null){
  		for(var i=0;i<this.bedData.returnedObject.nicuRoomObj.length;i++){
  			if(roomKey==this.bedData.returnedObject.nicuRoomObj[i].room.key){
  				this.isBed=true;
  				this.roomBed=this.bedData.returnedObject.nicuRoomObj[i];
  				this.newBed = parseInt(this.bedData.returnedObject.nicuRoomObj[i].newBed);
  				break;
  			}
  		}
    }
	};

  getStatusBed(value){
    this.moveBed="";
		this.moveBedStatus=false;
    this.vacantBedList = [];
    this.vacant=false;
		if(value==true){
      this.vacantBedList =[];
			this.bedDisplay=true;
      if(this.roomBed.bedJSON != null){
        for(let i=0;i<this.roomBed.bedJSON.length; i++){
          if (this.roomBed.bedJSON[i].status == this.bedDisplay.toString()) {
            this.vacantBedList.push(this.roomBed.bedJSON[i]);
          }
        }
    }
    }
		if(value==false){
      this.vacantBedList =[];
			this.bedDisplay=false;
      if(this.roomBed.bedJSON != null){
        for(let i=0;i<this.roomBed.bedJSON.length; i++){
          if (this.roomBed.bedJSON[i].status == this.bedDisplay.toString()) {
            this.vacantBedList.push(this.roomBed.bedJSON[i]);
          }
        }
      }
		}
	}
	bedDropdown(bed){
		this.changeRoom=[];
    this.moveInRoom = "";
	  this.moveBed=bed;
		if(this.moveBedStatus==true){
		    this.moveBedStatus=false;
		}
	  else{
		    this.moveBedStatus=true;
        if(this.bedData.returnedObject.roomList != null){
  		    for(let i=0;i<this.bedData.returnedObject.roomList.length;i++){
  			       if(this.roomId!=this.bedData.returnedObject.roomList[i].key){
  				           this.changeRoom.push(this.bedData.returnedObject.roomList[i].value);
               }
          }
        }
		}
	};

  clickedBed(){
	   console.log(this.newBed);
	};

	clickedRoom(keyEvent){
		console.log(this.newRoom);
	};

  setRoomChange(value){
		console.log(value);
    let dataParams;
    try{
      this.http.post(this.apiData.changeBedRoomNo + this.moveBed  + '/' + value +'/test/' + this.branchName + "/",
      dataParams).subscribe(res => {
        this.handleGetChangeBedData(res.json());
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};

  handleGetChangeBedData(responseData){
    console.log(responseData);
    alert(responseData.message);
    this.bedData = responseData;
    this.getBedListOfRoom(this.roomId);
    this.getBedData();
  }

	newBedVerify(){
		if(this.newBed==undefined||this.newBed==0){
		    alert("Enter Valid 'BedNo.'  between 1 and 9999");
		}
	  else{
		    this.addBedInRoom();
		}
	}

  deleteBed(bedKey){
		this.moveBed="";
		this.moveBedStatus=false;
		this.vacant=true;
		console.log(this.roomId);
		let dataParams;
    try{
      this.http.post(this.apiData.deleteBed + bedKey + '/test/' + this.branchName,
      dataParams).subscribe(res => {
        this.handleGetDeleteBedData(res.json());
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  }
  handleGetDeleteBedData(responseData){
    console.log(responseData);
    alert(responseData.message);
    this.bedData = responseData;
    this.getBedListOfRoom(this.roomId);
    this.getBedData();
    this.getBedListOfRoom(this.roomId);
  }

  addBedInRoom(){
    console.log("bhghkbhjcn");
  	console.log(this.bedValue);
  	this.moveBed="";
  	this.moveBedStatus=false;
  	let dataParams;
    this.vacant=true;
    let newBedNumber : string;
    newBedNumber =  this.newBed.toLocaleString();
    try{
      this.http.post(this.apiData.addBed + this.roomId + '/' + newBedNumber + '/test/' + this.branchName + "/",
      dataParams).subscribe(res => {
        this.handleGetAddNewBedData(res.json());
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
  };

  handleGetAddNewBedData(responseData){
    console.log(responseData);
    alert(responseData.message);
    this.bedData = responseData;
    this.newBed= +this.bedData.returnedObject.newBed;
    console.log(this.newBed);
    this.getBedListOfRoom(this.roomId);
  }

  setdeleteRoom(roomKey){
		this.moveBed="";
		if(this.roomId==roomKey){
			this.value=0;
		}
		else{
			this.value=1;
		}

		this.moveBedStatus=false;
		this.vacant=true;
		let dataParams;
    try{
      this.http.post(this.apiData.deleteRoom + roomKey + '/test/' + this.branchName,
      dataParams).subscribe(res => {
        this.handleGetDeleteRoomData(res.json());
        this.getBedListOfRoom(roomKey);
      },
      err => {
        console.log("Error occured.")
      });
    }
    catch(e){
      console.log("Exception in http service:"+e);
    };
	};

  handleGetDeleteRoomData(responseData){
    console.log(responseData);
    alert(responseData.message);
    this.bedData = responseData;
    this.value=0;
    this.getBedData();
    this.getBedListOfRoom(this.roomId);
  }

	ngOnInit() {
  }
}
