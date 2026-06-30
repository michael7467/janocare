import { ResourceModel } from '../common/resource.model';

export class Specialization extends ResourceModel<Specialization> {
  name: string;

  constructor(model?: Partial<Specialization>) {
    super(model);
  }
}
