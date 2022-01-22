import { CommonEventsInfo } from './commonEventsInfo';
import { SepsisEvent } from './sepsisEvent';
import { VapEvent } from './vapEvent';
import { ClabsiEvent } from './clabsiEvent'
export class InfectionEventObject
    {
        eventName : string;
		commonEventsInfo : CommonEventsInfo;
		sepsisEvent : SepsisEvent;
		vapEvent : VapEvent;
		clabsiEvent : ClabsiEvent;
    }