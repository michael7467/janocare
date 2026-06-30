import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';
import { City, Country, State } from '../common';

export class UserProfile extends ResourceModel<UserProfile> {
  firstName: string;
  lastName: string;
  middleName: string;
  profilePic: string;
  
  gender: 'Male' | 'Female';
  created_at: string;
  updated_at: string;
  nationalId: string;
  city: City;
  state: State;
  country: Country;
  constructor(model?: Partial<UserProfile>) {
    super(model);
  }
}
