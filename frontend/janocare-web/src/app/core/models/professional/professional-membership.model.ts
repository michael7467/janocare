import { ResourceModel } from "../common/resource.model";
import { ProfessionalUser } from "./professional_user.model";

export class ProfessionalMembership extends ResourceModel<ProfessionalMembership>  {

    membership_name: string;
    professional_user: ProfessionalUser;
    constructor(model?: Partial<ProfessionalMembership>) {
        super(model);
    }
  
  
  }
