import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { APIurl } from '../../../model/APIurl';
import { Http, Response } from '@angular/http';
import * as $ from 'jquery/dist/jquery.min.js';
import { ExchangeTransfusion } from '../procedures/exchangeTransfusion';

@Component({
  selector: 'exchange-transfusion-print',
  templateUrl: './exchange-transfusion-print.component.html',
  styleUrls: ['./exchange-transfusion-print.component.css']
})

export class ExchangeTransfusionPrint implements OnInit {
	totalVolume : number;
	workingWeight : number;
	router : Router;
	babyBloodGroup : any;
	printExchangeTransfusionList : ExchangeTransfusion;
	recentTransfusionList : ExchangeTransfusion;
	isCurrentExchange : boolean;
	currentExchangeTotalVolumeIn : any;
	currentExchangeTotalVolumeOut : any;
	pastExchangeTotalVolumeIn : any;
	pastExchangeTotalVolumeOut : any;
	motherBloodGroup : any;

	constructor(http: Http, router : Router){
		this.router = router;
		this.printExchangeTransfusionList = JSON.parse(localStorage.getItem('printExchangeTransfusionList'));
		this.isCurrentExchange = JSON.parse(localStorage.getItem('isCurrentExchange'));
		this.recentTransfusionList = this.printExchangeTransfusionList[0];
		this.babyBloodGroup = localStorage.getItem('babyBloodGroup');
		console.log(this.printExchangeTransfusionList);
		this.totalVolume = JSON.parse(localStorage.getItem('totalVolume'));
		this.motherBloodGroup = JSON.parse(localStorage.getItem('motherBloodGroup'));
		console.log("mother" + this.motherBloodGroup);
		this.workingWeight = JSON.parse(localStorage.getItem("workingWeight"));
		if(this.isCurrentExchange){
			this.currentExchangeTotalVolumeIn = JSON.parse(localStorage.getItem('currentExchangeTotalVolumeIn'));
			this.currentExchangeTotalVolumeOut = JSON.parse(localStorage.getItem('currentExchangeTotalVolumeOut'));
		}

		if(!this.isCurrentExchange) {
			this.pastExchangeTotalVolumeIn = JSON.parse(localStorage.getItem('pastExchangeTotalVolumeIn'));
			this.pastExchangeTotalVolumeOut = JSON.parse(localStorage.getItem('pasttExchangeTotalVolumeOut'));
		}
	}

	 printPdf = function(){
		setTimeout(function () {
			window.print();
		}, 1000);
  		setTimeout( () => {
        	this.router.navigateByUrl('/proceed/procedures');
    	},3000);
	};

	ngOnInit() {
    	this.printPdf();
     	localStorage.removeItem('isCurrentExchange');
		localStorage.removeItem('printExchangeTransfusionList');
		localStorage.removeItem('babyBloodGroup');
		localStorage.removeItem('workingWeight');
		localStorage.removeItem('sortedList');
		localStorage.removeItem('bloodGroup');
		localStorage.removeItem('motherBloodGroup');
		localStorage.removeItem('totalVolume');
		localStorage.removeItem('pastExchangeTotalVolumeIn');
		localStorage.removeItem('pastExchangeTotalVolumeOut');
		localStorage.removeItem('currentExchangeTotalVolumeIn');
		localStorage.removeItem('currentExchangeTotalVolumeOut')
	}
}