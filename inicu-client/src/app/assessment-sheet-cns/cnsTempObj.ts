import {History} from './history';
import {Sensorium} from './sensorium';
export class CnsTempObj{
  history : History;
  manageIvFluid : boolean;
  treatmentProgressText : string;
  isRespSelectAshphyxia : boolean;
	isHypoglycemiaSelectAshphyxia : boolean;
	isIvFluidSelectAshphyxia : boolean;
	isAntibioticsSelectAshphyxia : boolean;
	isMedicationSelectAshphyxia : boolean;
	isOtherSelectAshphyxia : boolean;
  asphyxiaTreatmentStatus : string;
  meridian: boolean;
  hours : any;
  isAcidosisSavedAshphyxia : any;
  isRdsSavedAshphyxia : any;
  isPphnSavedAsphyxia : any;
  isIvhSavedAshphyxia : any;
  sensorium : Sensorium;
  pastSelectedOrderInvestigationStr : string;
  pastSelectedCauseStr : string;
  currentSelectedTreatment : any;
}
