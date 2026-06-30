import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { ProfessionalUser } from "../professional/professional_user.model";
import { CardAppointments } from "../transaction";
import { Dependant } from "./dependant.model";

export class PatientPrescriptionsDetail extends ResourceModel<PatientPrescriptionsDetail>  {
    
    medicationName: string;
    medicationDescription:string;
    medicationDosage: string;
    medicationFrequency:string;
    medicationDuration:string;
    constructor(model?: Partial<PatientPrescriptionsDetail>) {
        super(model);
    }
    
}