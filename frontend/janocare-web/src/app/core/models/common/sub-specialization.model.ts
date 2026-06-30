import { ResourceModel } from '../common/resource.model';

export class SubSpecialization extends ResourceModel<SubSpecialization> {
  name: string;
  icon?: string;
  specializationId: number;

  constructor(model?: Partial<SubSpecialization>) {
    super(model);
  }
}
