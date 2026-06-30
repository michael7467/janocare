import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientVital extends ResourceModel<PatientVital>  {
    
    weight:number;
    height:number;
    bloodPressure:number;
    pulse:number;
    temprature:number;
    respiratoryRate:number;
    bloodOxygen:number;
    bloodGlucose:number;
    remark:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientVital>) {
        super(model);
    }
    
}