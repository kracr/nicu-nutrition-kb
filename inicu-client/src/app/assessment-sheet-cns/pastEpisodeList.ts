export class PastEpisodeList {
   episodeid: number;
   creationtime: number;
   modificationtime: number;
   uhid: string;
   loggeduser: string;
   nnVitalparameterTime: string;
   apnea?: any;
   bradycardia?: any;
   tachycardia?: any;
   hr?: any;
   disaturation?: any;
   spo2?: any;
   recovery?: any;
   seizures: boolean;
   symptomaticStatus: boolean;
   symptomaticValue: string;
   seizureDuration: string;
   seizureType: string;
}
