import { ResourceModel } from '../common/resource.model';
import { UserProfile } from '../user';
import { City } from './city.model';
import { Country } from './country.model';

export class State extends ResourceModel<State> {

  stateName: string;
  isActive: boolean;
  country: Country;
  cities: City[];
  userProfile: UserProfile[];
  //   institutions: Institution[];
  constructor(model?: Partial<State>) {
    super(model);
  }
}
