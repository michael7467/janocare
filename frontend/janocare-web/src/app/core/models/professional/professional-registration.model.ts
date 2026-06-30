import { ResourceModel } from "../common/resource.model";

export class ProfessionalRegistration extends ResourceModel<ProfessionalRegistration>  {

    registrationName: string;
    registrationDate: string;
    certificatePhoto: string;

    professionalId: number;
    constructor(model?: Partial<ProfessionalRegistration>) {
        super(model);
    }
  
  
  }
