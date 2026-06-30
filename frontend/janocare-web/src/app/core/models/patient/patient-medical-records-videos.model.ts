import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { ProfessionalUser } from "../professional/professional_user.model";
import { Dependant } from "./dependant.model";

export class PatientMedicalRecordVideos extends ResourceModel<PatientMedicalRecordVideos>  {
    
    recordTitle: string;
    recordDescription:string;
    recordDate:number;
    recordPath:string;
    patientMedicalRecordId:number;
    institution:Institution;
    patient:Patient;
    professional:ProfessionalUser;
    dependant:Dependant;
    cardAppointmentId:number;
    constructor(model?: Partial<PatientMedicalRecordVideos>) {
        super(model);
    }
    
}