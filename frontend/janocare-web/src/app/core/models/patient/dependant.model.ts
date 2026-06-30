import { ResourceModel } from '../common/resource.model';
import { Patient } from "./patient.model";

export class Dependant extends ResourceModel<Dependant>  {
    dob: Date;
    firstName: string;
    lastName:string;
    relationship:string;
    gender:string;
    dependantPhoto:string;
    patientId:string;
    patient:Patient;
    constructor(model?: Partial<Dependant>) {
        super(model);
    }
    
}