import { ResourceModel } from '../common/resource.model';
import { SubSpecialization } from '../common';

export class ProfessionalSubSpecialization extends ResourceModel<ProfessionalSubSpecialization> {
  subSpecializationsId: string;
  subSpecializations: SubSpecialization;
  constructor(model?: Partial<ProfessionalSubSpecialization>) {
    super(model);
  }
}
