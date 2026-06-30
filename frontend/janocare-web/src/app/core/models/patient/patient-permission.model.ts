import { ResourceModel } from '../common/resource.model';
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";
import { ProfessionalUser } from "../professional/professional_user.model";

export class PatientPermission extends ResourceModel<PatientPermission>  {
    
    type: string;
    status:string;
    patient:Patient;
    patietId:string;
    dependant:Dependant;
    professionalUser:ProfessionalUser
    constructor(model?: Partial<PatientPermission>) {
        super(model);
    }
    
}