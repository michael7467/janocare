import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';
import { City, Country, State } from '../common';

export class InstitutionRole extends ResourceModel<InstitutionRole> {
 

  name: string;
  description:string;
  constructor(model?: Partial<InstitutionRole>) {
    super(model);
  }
}
