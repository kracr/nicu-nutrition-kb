import { PeripheralCannula } from './peripheralCannula';
import { CentralLine } from './centralLine';
import { LumbarPuncture } from './lumbarPuncture';
import { Vtap } from './vtap';
import { EtIntubation } from './etIntubation';
import { ExchangeTransfusion } from './exchangeTransfusion';
import { etIntubationReason } from './etIntubationReasonList';
import { ProcedureChesttube } from './procedureChesttube';
import { BabyDetailObj } from '../admission-form/babyDetailObj';
//import { PneumothoraxPOJO } from '../pneumothorax/PneumothoraxPOJO';
import { ProcedureOther } from './procedureOther';
import { KeyValueObj } from '../common-model/keyValueObj';
import { RefTestslist } from '../common-model/refTestslist';
import { AppComponent } from '../app.component';
import { Jaundice } from '../assessment-sheet-jaundice/jaundiceObj';
export class ProceduresModel {

	emptyPeripheralObj : PeripheralCannula;
	currentPeripheralList : Array<PeripheralCannula>;
	pastPeripheralList : Array<PeripheralCannula>;

	emptyCentralLineObj : CentralLine;
	currentCentralLineList : Array<CentralLine>;
	pastCentralLineList : Array<CentralLine>;
	motherBloodGroup : any;
	exchangeDateList : any;
	isPlanned : Jaundice;

	allExchangeTransfusionList : ExchangeTransfusion[];
  exchangeTransfusionObj : ExchangeTransfusion;
  currentExchangeTransfusionList : ExchangeTransfusion[];
  exchangeTransfusionObjList : ExchangeTransfusion[];
	babyDetailObj : BabyDetailObj;
	emptyLumbarPunctureObj : LumbarPuncture;
	pastLumbarPunctureList : Array<LumbarPuncture>;

	emptyVtapObj : Vtap;
	pastVtapList : Array<Vtap>;

	emptyEtIntubationObj : EtIntubation;
	currentEtIntubationObj : EtIntubation;
	currentEtIntubationList : Array<EtIntubation>;
	pastEtIntubationList : Array<EtIntubation>;
	etIntubationReasonList : etIntubationReason[];
	pastEtIntubationTableList : Array<Object>;

	chestTube : ProcedureChesttube;
	pastTubesList : Array<ProcedureChesttube>;
	pneumothorax : any;

	workingWeight : number;
	emptyOtherObj : ProcedureOther;
	pastOtherList : Array<ProcedureOther>;

	orderInvestigation : Array<KeyValueObj>;
	testsList : Map<any, Array<RefTestslist>>;

	emptyEtSuctionObj : any;
	pasEtSuctionList : Array<any>;

}
