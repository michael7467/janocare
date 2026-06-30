import { ResourceModel } from '../common/resource.model';
import { UserProfile } from '../user';
import { City } from './city.model';
import { State } from './state.model';

export class Country extends ResourceModel<Country> {

  countryName: string;
  phonePrefix: string;
  isActive: Boolean;

  cities: City[];
  states: State[];
  userProfile: UserProfile[];
  //   institutions: Institution[];
  constructor(model?: Partial<Country>) {
    super(model);
  }
}
