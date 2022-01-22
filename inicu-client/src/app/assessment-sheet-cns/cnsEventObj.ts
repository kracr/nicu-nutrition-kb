import {AsphyxiaEvent} from './AsphyxiaEvent';
import {SeizuresEvent} from './SeizuresEvent';
import {CommonEventsInfo} from './commonEventsInfo';
export interface CnseventObject {
    eventName?: any;
    commonEventsInfo: CommonEventsInfo;
    asphyxiaEvent: AsphyxiaEvent;
    seizuresEvent: SeizuresEvent;
    ivhEvent: any;
    stopTreatmentFlag: boolean;
}
