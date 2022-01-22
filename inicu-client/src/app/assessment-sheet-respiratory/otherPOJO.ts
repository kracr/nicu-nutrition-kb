import { CurrentOther } from './currentOther';
import { RespSupport } from './respSupport';
import { BabyPrescription } from '../common-model/babyPrescription';
export class OtherPOJO{
  currentOther: CurrentOther;
  respSupport: RespSupport;
  babyPrescriptionEmptyObj: BabyPrescription;
  currentBabyPrescriptionList: BabyPrescription[];
  pastOtherList: CurrentOther[];
  pastRespSupportMap: any;
}
