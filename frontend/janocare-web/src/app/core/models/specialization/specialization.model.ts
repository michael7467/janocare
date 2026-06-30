import { ResourceModel } from '../../../shared';

export class SpecializationModel extends ResourceModel<SpecializationModel> {
  name: string;
  icons: string;

  constructor(model?: Partial<SpecializationModel>) {
    super(model);
  }
}
