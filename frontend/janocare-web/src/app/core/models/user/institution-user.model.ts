import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';
import { City, Country, State } from '../common';
import { Institution } from './institution.model';
import { InstitutionRole } from './institution-role.model';

export class InstitutionUser extends ResourceModel<InstitutionUser> {
 

  institution: Institution;
  institutionRole:InstitutionRole;
  constructor(model?: Partial<InstitutionUser>) {
    super(model);
  }
}
