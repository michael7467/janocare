import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientSurgery extends ResourceModel<PatientSurgery>  {
    
    surgeryName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientSurgery>) {
        super(model);
    }
    
}