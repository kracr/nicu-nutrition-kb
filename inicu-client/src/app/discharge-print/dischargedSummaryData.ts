import { Outcome } from './outcome';
import { BabyDetail } from './babyDetail';
import { ParenteDetail } from './parenteDetail';
import { BabyVisit } from './babyVisit';
import { DischargeAdviceTemplates } from './dischargeAdviceTemplates';
import { DischargeNotes } from './dischargeNotes';
export class DischargedSummaryData {
  admissionVisit: string;
  assessmentMessage: string;
  babyDetails : BabyDetail;
  babyStatus : string;
  birthVisit : BabyVisit;
  comments: string;
  dischargeAdviceTemplates: Array<DischargeAdviceTemplates>;
  dischargeNotes : DischargeNotes;
  dischargeVisit : string;
  edd : Date;
  finalDiagnosis : string;
  isDischargeBaby : boolean;
  loggedUser: string;
  outcome : Outcome;
  parenteDetail : ParenteDetail;
  uhid : string;
  whichPage : string;
  babyPhysicalExamData : any;
}
