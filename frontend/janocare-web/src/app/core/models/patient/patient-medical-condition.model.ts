import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientMedicalCondition extends ResourceModel<PatientMedicalCondition>  {
    
    conditionName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientMedicalCondition>) {
        super(model);
    }
    
}