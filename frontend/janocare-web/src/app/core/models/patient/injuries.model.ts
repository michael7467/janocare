import { ResourceModel } from "../common/resource.model";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";

export class PatientInjury extends ResourceModel<PatientInjury>  {
    
    injuryName: string;
    description:string;
    patient:Patient;
    dependant:Dependant;
    constructor(model?: Partial<PatientInjury>) {
        super(model);
    }
    
}