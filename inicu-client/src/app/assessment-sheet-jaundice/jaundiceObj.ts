
import { KeyValueObj } from '../common-model/keyValueObj';

export class Jaundice {
  sajaundiceid?: any;
  tcbortsb: boolean;
  ageatassesment: number;
  isageofassesmentinhours: boolean;
  ageofonset: number;
  causeofjaundice?: any;
  causeofjaundiceList?: any;
  creationtime?: any;
  creationTimeStr?: any;
  durationofjaundice?: any;
  durphoto?: any;
  exchangetrans?: any;
  ivig?: any;
  ivigvalue?: any;
  modificationtime?: any;
  noofexchange?: any;
  phototherapy?: any;
  uhid?: any;
  loggeduser?: any;
  comment?: any;
  isNewEntry: boolean;
  isageofonsetinhours: boolean;
  tbcvalue?: any;
  isphotobelowthreshold?: any;
  observationtype?: any;
  actiontype?: any;
  maxTcb: number;
  maxTsb?: any;
  riskfactor?: any;
  riskFactorList?: any;
  actiontypeList: any[];
  otheractiontype?: any;
  actionduration?: any;
  isactiondurationinhours?: any;
  orderinvestigation?: any;
  orderinvestigationList?: any;
  isinvestigationodered?: any;
  jaundicestatus: string;
  dateofbirth: string;
  nursingcomment?: any;
  bindscoreid?: any;
  otherCause?: any;
  associatedevent?: any;
  gestation: number;
  phototherapyvalue?: any;
  isEdit?: any;
  pastOrderInvestigationStr?: any;
  pastTreatmentActionStr?: any;
  treatmentOther?: any;
  treatmentactionplanlist?: any;
  durationOfEpisode?: any;
  durationOfPhototherapy?: any;
  numberOfExchangeTransfusion?: any;
  totalDoseOfIVIG?: any;
  prescriptionBackMessage?: any;
  durationOfOtherTreatment?: any;
  planOther?: any;
  jaundiceDiagnosisBy?: any;
  assessmentDate?: any;
  assessmentHour: string;
  assessmentMin: string;
  assessmentMeridiem: boolean;
  assessmentTime: Date;
  episodeNumber?: any;
  edit?: any;

  constructor(){
    this.assessmentTime = new Date();
  }
}
