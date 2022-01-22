import { PastPrescriptionList } from './pastPrescriptionList';
import { CurrentRespPneumo } from './currentRespPneumo';
import { RespSupport } from './respSupport';
import { BabyPrescription } from '../common-model/babyPrescription';
import { ChestTubeObj } from './chestTubeObj';
export class Pneumothorax {
  currentRespPneumo : CurrentRespPneumo;
  pastRespPneumo : Array<CurrentRespPneumo>;
  babyPrescriptionEmptyObj: BabyPrescription;
  currentBabyPrescriptionList : any;
  respSupport : RespSupport
  pastRespSupport : any;
  chestTubeObj : ChestTubeObj;
  associatedEvents : null
}