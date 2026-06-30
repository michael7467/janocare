import { ResourceModel } from '../common/resource.model';
import { Specialization } from '../common';

export class ProfessionalSpecialization extends ResourceModel<ProfessionalSpecialization> {
  specializationId: number;
  specializations: Specialization;
  constructor(model?: Partial<ProfessionalSpecialization>) {
    super(model);
  }
}
