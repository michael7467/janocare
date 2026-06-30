import { ResourceModel } from '../common/resource.model';
import { Institution } from "../user";
import { Patient } from "./patient.model";
import { Dependant } from "./dependant.model";
import { ProfessionalUser } from "../professional";

export class PatientLabTest extends ResourceModel<PatientLabTest>  {
    
    testName: string;
    testDescription:string;
    testDate: Date;
    patient:Patient;
    institution:Institution;
    professional:ProfessionalUser;
    cardAppointmentId:number;
    dependant:Dependant;
    constructor(model?: Partial<PatientLabTest>) {
        super(model);
    }
    
}