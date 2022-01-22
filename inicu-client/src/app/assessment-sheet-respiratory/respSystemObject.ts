import { PastPrescriptionList } from './pastPrescriptionList';
import { RespiratoryDistress } from './respiratoryDistress';
import { ApneaPOJO } from './apneaPOJO';
import { BpdPOJO } from './bpdPOJO';
import { RespSupport } from './respSupport';
import { OtherPOJO } from './otherPOJO';
import { PphnPOJO } from './PphnPOJO';
import { RespUsage } from './respUsage';
import { PastTableList } from './pastTableList';
import { Pneumothorax } from './pneumothorax';
export class RespSystemObject{
  eventName: string;
  pastPrescriptionList: PastPrescriptionList[];
  respiratoryDistress: RespiratoryDistress;
  apnea: ApneaPOJO;
  bpd: BpdPOJO;
  others: OtherPOJO;
  pphn: PphnPOJO;
  pneumothorax: Pneumothorax;
  respSupport: RespSupport;
  respUsage: RespUsage[];
  pastDownesScore: number;
  pastDownesTime: any;
  pastTableList: PastTableList[];
  stopTreatmentFlag: boolean;
  surfactantDose: string;
  orderSelectedText : string;
}
