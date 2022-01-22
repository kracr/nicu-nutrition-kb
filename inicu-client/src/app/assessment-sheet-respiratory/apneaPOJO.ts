import { CurrentApnea } from './currentApnea';
import { RespSupport } from './respSupport';
import { NursingEpisodeEmptyObj } from './nursingEpisodeEmptyObj'
export class ApneaPOJO{
  currentApnea: CurrentApnea;
  respSupport: RespSupport;
  apneaEvent: boolean;
  currentEpisode: NursingEpisodeEmptyObj;
  pastApnea: CurrentApnea[];
  pastNursingEpisode: NursingEpisodeEmptyObj[];
  pastRespSupportMap: any;
  numberOfEpisode: number;
  numberOfHours: number;
  numberOfSpontaneous: number;
  numberOfStimulation: number;
  numberOfPPV: number;
  cummulativeDaysOfApnea: number;
}
