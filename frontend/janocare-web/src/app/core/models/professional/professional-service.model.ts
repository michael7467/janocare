import { ResourceModel } from "../common/resource.model";
import { ProfessionalUser } from "./professional_user.model";


  export class ProfessionalService extends ResourceModel<ProfessionalService>   {
  
    service_name: string;
    professional_user: ProfessionalUser;
    
    constructor(model?: Partial<ProfessionalService>) {
        super(model);
    }
  
  }
