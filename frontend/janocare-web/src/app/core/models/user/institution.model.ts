import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';
import { City, Country, State } from '../common';

export class Institution extends ResourceModel<Institution> {
 

  name: string;
  logoi:string;
  address:string;
  constructor(model?: Partial<Institution>) {
    super(model);
  }
}
