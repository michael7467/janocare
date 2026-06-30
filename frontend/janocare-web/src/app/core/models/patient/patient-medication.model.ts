import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientMedication extends ResourceModel<PatientMedication>  {
    
    medicationName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientMedication>) {
        super(model);
    }
    
}