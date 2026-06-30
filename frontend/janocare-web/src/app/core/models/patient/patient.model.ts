import { ResourceModel } from '../common/resource.model';
import { User } from "../user";

export class Patient extends ResourceModel<Patient>  {
    dob: Date;
    ecpFirstName: string;
    ecpLastName:string;
    ecpAddress:string;
    ecpRelationship:string;
    ecpPhoneNumber:string;
    address:string;
    user:User;
    constructor(model?: Partial<Patient>) {
        super(model);
    }
    
}