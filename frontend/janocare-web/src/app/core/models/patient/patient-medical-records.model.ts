import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { ProfessionalUser } from "../professional/professional_user.model";
import { Dependant } from "./dependant.model";

export class PatientMedicalRecords extends ResourceModel<PatientMedicalRecords>  {
    
    recordName: string;
    recordDescription:string;
    recordDate:number;
    institution:Institution;
    patient:Patient;
    professional:ProfessionalUser;
    dependant:Dependant;
    cardAppointmentId:number;
    constructor(model?: Partial<PatientMedicalRecords>) {
        super(model);
    }
    
}