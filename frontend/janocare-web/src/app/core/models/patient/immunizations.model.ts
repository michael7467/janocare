import { ResourceModel } from '../common/resource.model';
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientImmunization extends ResourceModel<PatientImmunization>  {
    
    immunizationName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientImmunization>) {
        super(model);
    }
    
}