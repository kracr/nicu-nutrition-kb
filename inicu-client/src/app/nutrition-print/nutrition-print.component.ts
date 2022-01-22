import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'nutrition-print',
  templateUrl: './nutrition-print.component.html',
  styleUrls: ['./nutrition-print.component.css']
})
export class NutritionPrintComponent implements OnInit {
  tempFeed : any;
  feedCalculator : any;
  childData : any;
  pnVolume : number;
  pnRate : number;
  lipidRate : number;
  router : Router;
  energy : any;
  protein : any;
  proteinEnergyRatio : any;
  totalCarbohydratesLipidsRatio : any;
  energyEN : number;
  energyPN : number;
  proteinEN : number;
  proteinPN : number;
  durationOffset : number;

	constructor(router : Router){
     this.childData = JSON.parse(localStorage.getItem('selectedChild'));
     this.pnVolume = 0;
     this.pnRate = 0;
     this.lipidRate = 0;
     this.energy = 0;
     this.protein = 0;
     this.proteinEnergyRatio = 0;
     this.totalCarbohydratesLipidsRatio = 0;
     this.energyEN = 0;
     this.energyPN = 0;
     this.proteinEN = 0;
     this.proteinPN = 0;
     this.durationOffset = 1;
     this.router = router;
     this.init();
	}
  init(){
    this.tempFeed = JSON.parse(localStorage.getItem('printPNOrder'));
    this.feedCalculator = JSON.parse(localStorage.getItem('feedCalculator'));
    this.proteinEnergyRatio = JSON.parse(localStorage.getItem('proteinEnergyRatio'));
    this.totalCarbohydratesLipidsRatio = JSON.parse(localStorage.getItem('totalCarbohydratesLipidsRatio'));
    this.durationOffset = JSON.parse(localStorage.getItem('durationOffset'));

    //calculating Total Volume , PN Rate, Lipid Rate
    if(this.tempFeed.lipidTotal != null){
      this.lipidRate = this.tempFeed.lipidTotal / this.tempFeed.duration;
      this.lipidRate = this.round(this.lipidRate, 2);
    }
    if(this.tempFeed.aminoacidTotal != null){
      this.pnVolume += this.tempFeed.aminoacidTotal;
    }
    if(this.tempFeed.sodiumTotal != null){
      this.pnVolume += this.tempFeed.sodiumTotal;
    }
    if(this.tempFeed.potassiumTotal != null){
      this.pnVolume += this.tempFeed.potassiumTotal;
    }
    if(this.tempFeed.mviTotal != null){
      this.pnVolume += this.tempFeed.mviTotal;
    }
    if(this.tempFeed.calciumTotal != null){
      this.pnVolume += this.tempFeed.calciumTotal;
    }
    if(this.tempFeed.phosphorousTotal != null){
      this.pnVolume += this.tempFeed.phosphorousTotal;
    }
    if(this.tempFeed.dextroseConcHighvolume != null){
      this.pnVolume += this.tempFeed.dextroseConcHighvolume;
    }
    if(this.tempFeed.dextroseConcLowvolume != null){
      this.pnVolume += this.tempFeed.dextroseConcLowvolume;
    }
    if(this.pnVolume != 0 && this.pnVolume != null){
      this.pnRate = this.pnVolume / this.tempFeed.duration;
      this.pnRate = this.round(this.pnRate, 2);
    }

    //calculate Energy
    if(!this.isEmpty(this.feedCalculator.deficitCalToShow.enteralIntake.Energy)){
      this.energy = this.feedCalculator.deficitCalToShow.enteralIntake.Energy;
      this.energyEN = this.feedCalculator.deficitCalToShow.enteralIntake.Energy;
    }
    if(!this.isEmpty(this.feedCalculator.deficitCalToShow.parentalIntake.Energy)){
      this.energy += this.feedCalculator.deficitCalToShow.parentalIntake.Energy;
      this.energyPN = this.feedCalculator.deficitCalToShow.parentalIntake.Energy;
    }
    //calculate Protein
    if(!this.isEmpty(this.feedCalculator.deficitCalToShow.enteralIntake.Protein)){
      this.protein = this.feedCalculator.deficitCalToShow.enteralIntake.Protein;
      this.proteinEN = this.feedCalculator.deficitCalToShow.enteralIntake.Protein;
    }

    if(!this.isEmpty(this.feedCalculator.deficitCalToShow.parentalIntake.Protein)){
      this.protein += this.feedCalculator.deficitCalToShow.parentalIntake.Protein;
      this.proteinPN = this.feedCalculator.deficitCalToShow.parentalIntake.Protein;
    }
  }
	printPdf = function(){
		setTimeout(window.print, 500);
  	setTimeout( () => {
        	this.router.navigateByUrl('/feed/nutrition');
        },3000);
	};


	ngOnInit() {
    this.printPdf();

    //Calculate Protein Energy Percent
    

	}

  isEmpty(object : any) : boolean {
		if (object == null || object == '' || object == 'null' || object.length == 0 || object == NaN) {
			return true;
		}
		else {
			return false;
		}
	};

  round(value : any, precision : any) {
    var multiplier = Math.pow(10, precision);
    return Math.round(value * multiplier) / multiplier;
  }

}
