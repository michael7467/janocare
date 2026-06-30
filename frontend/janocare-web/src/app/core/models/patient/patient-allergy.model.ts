import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientAllergy extends ResourceModel<PatientAllergy>  {
    
    allergyName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientAllergy>) {
        super(model);
    }
    
}