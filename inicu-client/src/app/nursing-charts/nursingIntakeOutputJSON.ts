import { RefFeedTypeList } from './refFeedTypeList';
import { OralFeedList } from './oralFeedList';
import { NursingIntakeEmptyObj } from './nursingIntakeEmptyObj';

export class NursingIntakeOutputJSON{
  hours : any;
  minutes : any;
  entryDate : string;
  feedMethodStr : string;
  babyFeedObj : any;
  babyFeedList : any;
  refFeedTypeList : RefFeedTypeList[];
  oralFeedList : OralFeedList[];
  currentNursingObj : NursingIntakeEmptyObj;
  pastNursingIntakeList : NursingIntakeEmptyObj[];
  enFeedList : any[];
  addtivesbrandList : any;

  constructor() {
  	this.currentNursingObj = new NursingIntakeEmptyObj();
  }
}
