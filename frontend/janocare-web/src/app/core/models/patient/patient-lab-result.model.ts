import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";
import { ProfessionalUser } from "../professional";


export class PatientLabResult extends ResourceModel<PatientLabResult>  {
    
    resultDescription: string;
    mresultDate:Date;
    patientLabTest:number;
    patient:Patient;
    institution:Institution;
    professional:ProfessionalUser;
    cardAppointmentId:number;
    dependant:Dependant;
    constructor(model?: Partial<PatientLabResult>) {
        super(model);
    }
    
}