import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { ProfessionalUser } from "../professional/professional_user.model";
import { CardAppointments } from "../transaction";
import { Dependant } from "./dependant.model";
import { PatientPrescriptionsDetail } from "./patient-prescription-detail.model";

export class PatientPrescriptions extends ResourceModel<PatientPrescriptions>  {
    
    patientPrescriptionDetails:PatientPrescriptionsDetail[];
    prescriptionDate:Date;
    institution:Institution;
    patient:Patient;
    professional:ProfessionalUser;
    dependant:Dependant;
    cardAppointment:CardAppointments;
    constructor(model?: Partial<PatientPrescriptions>) {
        super(model);
    }
    
}