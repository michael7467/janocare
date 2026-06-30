import { ResourceModel } from '../common/resource.model';
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientFamilyHistory extends ResourceModel<PatientFamilyHistory>  {
    
    familyHistoryTitle: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientFamilyHistory>) {
        super(model);
    }
    
}