import { CurrentRespDistress } from './currentRespDistress';
import { BabyPrescription } from '../common-model/babyPrescription';
import { RespDistressGraph } from './respDistressGraph';
export class RespiratoryDistress{
  currentRespDistress: CurrentRespDistress;
  pastRespDistress: CurrentRespDistress[];
  babyPrescriptionEmptyObj: BabyPrescription;
  currentBabyPrescriptionList: BabyPrescription[];
  respDistressGraph: RespDistressGraph;
  pastRespSupport: any;
  associatedEvents: any[];

}
