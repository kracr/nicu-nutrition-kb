import { TpnFeedCurrentObj } from './tpnFeedCurrentObj';
import { BabyfeedDetail } from './babyfeedDetail';
import { BabytpnfeedDetail } from './babytpnfeedDetail';
import { BabyfeedivmedDetail } from './babyfeedivmedDetail';
import { BloodProduct } from './bloodProduct';
import { BabyPrescription } from './babyPrescription';
import { OralfeedDetail } from './oralfeedDetail';
import { FeedCalculatorPOJO } from './feedCalculatorPOJO';

export class FeedPojo {

	currentFeedInfo : TpnFeedCurrentObj;
	babyFeedList : Array<BabyfeedDetail>;
	babyTpnFeedList : Array<BabytpnfeedDetail>;
	babyPrescriptionList : Array<BabyPrescription>;
	babyBloodProductList : Array<BloodProduct>;
	ivMedList : Array<BabyfeedivmedDetail>;
	babyPrescEmptyObj : BabyPrescription;
	feedCalulator : FeedCalculatorPOJO;
	oralFeedEmptyObj : OralfeedDetail;
	oralFeedList : Array<OralfeedDetail>;
	pastOralFeedList : Array<OralfeedDetail>;
	emptyEnFeedDetailObj : any;
	enFeedDetailList : Array<any>;
	pastEnFeedDetailList : Array<any>;
  lastFeedInfo : BabyfeedDetail;
	babyCentralLineList : any;
  addtivesbrandList : Array<any>;
	calculatorList : Array<FeedCalculatorPOJO>;

}
