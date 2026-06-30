import { ResourceModel } from '../common/resource.model';
import { ProfessionalUser } from './professional_user.model';

export class ProfessionalExperience extends ResourceModel<ProfessionalExperience> {
  experience: string;
  startYear: string;
  endYear: string;
  specialization: string;
  place: string;
  professional_user: ProfessionalUser;
  constructor(model?: Partial<ProfessionalExperience>) {
    super(model);
  }
}
