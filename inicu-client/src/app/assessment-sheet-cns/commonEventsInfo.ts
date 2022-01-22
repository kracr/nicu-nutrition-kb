import {PastPrescriptionList} from './pastPrescription';
import {RespSupport} from './respSupport';
import {RespUsage} from './respUsage';
import {ScoreIvh} from './scoreIvh';
import {ScorePapile} from './scorePapile';
import {ThompsonScoreObj} from './scoreThompson';
import {LeveneObj} from './leveneObj';
import {SarnatScoreObj} from './scoreSarnat';
import {DowneScoreObj} from './scoreDownes';
import {Prescription} from './prescription';

export class CommonEventsInfo {
        pastPrescriptionList: PastPrescriptionList[];
        babyPrescriptionEmptyObj: PastPrescriptionList;
        medicationBabyPrescriptionList: Prescription[];
        antibioticsBabyPrescriptionList: Prescription[];
        respSupport: RespSupport;
        respUsage: RespUsage[];
        scoreIvh: ScoreIvh;
        isScoreIvh: boolean;
        scorePapile: ScorePapile;
        isScorePapile: boolean;
        thompsonScoreObj: ThompsonScoreObj;
        thompsonFlag: boolean;
        leveneObj: LeveneObj;
        leveneFlag: boolean;
        sarnatScoreObj: SarnatScoreObj;
        isSarnatScore: boolean;
        pastCnsHistory: any[];
        pastPapileScore?: any;
        pastIvhScore?: any;
        downeFlag: boolean;
        downeScoreObj: DowneScoreObj;
    }
