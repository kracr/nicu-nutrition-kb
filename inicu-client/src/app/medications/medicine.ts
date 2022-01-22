export class Medicine {

    medid: string;
    creationtime: any;
    modificationtime: any;
    userid: string;
    medname: string;
    brand: string;
    suspensiondose?: number;
    suspensiondoseunit: string;
    frequency: string;
    isactive: boolean;
    description: string;
    dosemultiplier?: any;
    dosemultiplierunit?: any;
    medicationtype: string;
    typeKeyVal?: any;
    freqKeyVal?: any;
    mentionOthers?: any;
    formulaperdose: boolean;
    othertype?: any;
    eachDose?: any;
    medicineTypeStr: string;
    unitMG: boolean;
    edit: boolean;
    route ?: any;
    storage : string;
    monitoring : string;
    interactions : string;
    effects: string;
    maximumConcIV : number;
    maximumConcIM : number;
    maximumRate : number;
}
