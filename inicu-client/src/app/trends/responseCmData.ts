import { XYAxis } from './xyaxis';
export class ResponseCmData {
  uhid?: string;
 fromdate?: any;
 todate?: any;
 startTime?: any;
 endTime?: any;
 graphType: string;
 entryTime?: any;
 daily: boolean;
 spo2: XYAxis;
 rr: XYAxis;
 fio2: XYAxis;
 hr: XYAxis;
 all: XYAxis;
 peep: XYAxis;
 pip: XYAxis;
}
