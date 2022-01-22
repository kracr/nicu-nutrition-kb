import { BabyfeedDetail } from './babyfeedDetail';
import { BabytpnfeedDetail } from './babytpnfeedDetail';
import { BabyfeedivmedDetail } from './babyfeedivmedDetail';
import { BloodProduct } from './bloodProduct';

export class TpnFeedCurrentObj {

	babyFeed : BabyfeedDetail;
	babyTpnFeed : BabytpnfeedDetail;
	ivMed : BabyfeedivmedDetail;
	babyBloodProduct : BloodProduct;
	currentWeight : string;
	feedGivenDateTime : any;
	feedGivenHrs : any;
	feedGivenMinutes : any;
	feedGivenMeridian : any;
	isNewEntry : boolean;

}