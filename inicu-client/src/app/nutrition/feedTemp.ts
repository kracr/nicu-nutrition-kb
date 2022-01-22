import { PastFeedTemp } from './pastFeedTemp';

export class FeedTemp {

	printFrom : any;
	printTo : any;
	pastBolusText : any;
	feedMethodSelectedText : any;
	feedTypeSecondarySelectedText : any;
	ebm : any;
	ebmandlactodexhmf : any;
	lactodex : any;
	dexolac : any;
	enfamil : any;
	visyneralz : any;
	tonoform : any;
	bolusSelectedText : any;
	sodiumMeg : any;
	pastEnteralTotalFeed : any;
	totalIntake : any;
	dextroseNetConcentration : any;
	osmolarity : any;
	textMessage : any;
	pastCal : PastFeedTemp;
	sodiumMlkgDay : any;
	isSodiumEdit : boolean;
	totalfeedMlDay : any;
	enCurrentCalculator : any;
	protienEnteral : any;
	lipidOfEnteral : any;
	carbohydratesOfEnteral : any;
	pnCurrentCalculator : any;
	ebmandsimilarhmf : any;
	totalenteraAdditivelvolume : any;
	aminoacidTotal : any;
	bolusTotalFeed : any;
	lipidTotal : any;
	totalparenteralAdditivevolume : any;
	dextroseVolumemlperday : any;
	totalMedvolume : any;
	pastParenteralTotalFeed : any;
	maxDate : any;
	breastFeedingSelectedText : any;
	feedTypeSelectedText : any;
	popupEventName : any;
	popupParamName : any;
	isFeedVolumeEntered : any;
	feedmethodList : any;

	constructor() {
		this.pastCal = new PastFeedTemp();
	}

}
