import { CurrentPphn } from './currentPphn';
import { BabyPrescription } from '../common-model/babyPrescription';
import { RespSupport } from './respSupport';
export class PphnPOJO{
  currentPphn: CurrentPphn;
  pastPphn: CurrentPphn[];
  antibioticsEmptyObj: BabyPrescription;
  antibioticsList: BabyPrescription[];
  inoEmptyObj: BabyPrescription;
  inoList: BabyPrescription[];
  sedationEmptyObj: BabyPrescription;
  sedationList: BabyPrescription[];
  respSupport: RespSupport;
  pastRespSupport: any;
  associatedEvents: any;
  sildenafilObj: BabyPrescription;

}
