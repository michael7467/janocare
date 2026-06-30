import { ResourceModel } from "../common/resource.model";
import { Dependant, Patient } from "../patient";
import { Institution } from "../user";
import { ProfessionalUser } from "../professional/professional_user.model";

export class CardAppointments extends ResourceModel<CardAppointments>  {
    appointmentDate: Date;
    appointmentStatus: string;
    appointmentReason:string;
    dependant:Dependant;
    institution:Institution;
    institutionServiceId:number;
    professional:ProfessionalUser;
    patient:Patient
    constructor(model?: Partial<CardAppointments>) {
        super(model);
    }
    
}