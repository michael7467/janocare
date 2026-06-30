import { ResourceModel } from "../common/resource.model";
import { ProfessionalUser } from "./professional_user.model";

export class ProfessionalQualification extends ResourceModel<ProfessionalQualification>  {

    qualificationName: string;
    institutionName: string;
    procrumentYear: string;


    professional_user: ProfessionalUser;
    constructor(model?: Partial<ProfessionalQualification>) {
        super(model);
    }
  
  
  }
