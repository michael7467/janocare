import { ResourceModel } from "../common/resource.model";
import { ProfessionalUser } from "./professional_user.model";


export class ProfessionalAchievement  extends ResourceModel<ProfessionalAchievement>{

  awardOrRecognitionName: string;
  professionalId: number;

  constructor(model?: Partial<ProfessionalAchievement>) {
    super(model);
   }

}