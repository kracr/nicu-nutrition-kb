import { BabyVisit } from './babyVisit';
import { VitalParametersInfo } from './vitalParametersInfo';
import { DailyAssessmentInfo } from './dailyAssessmentInfo';
import { NNEntryTime } from './nnEntryTime';
import { VentilatorInfo } from './ventilatorInfo';
import { BloodGasInfo } from './bloodGasInfo';
export class NursingObservationData{
  babyVisit : BabyVisit;
  bloodGasInfo : BloodGasInfo;
  vitalParametersInfo : VitalParametersInfo;
  neuroVitalsInfo : any;
  intakeInfo : any;
  outputInfo : any;
  cathetersInfo : any;
  ventilatorInfo : VentilatorInfo;
  dailyAssessmentInfo : DailyAssessmentInfo;
  miscInfo : any;
  uhid : string;
  loggedUser : string;
  time : any;
  nnEntryTime : NNEntryTime;
  photoTherapy : any;
  userDate : any;
  entryDate : any;
  creationtime : any;
}
