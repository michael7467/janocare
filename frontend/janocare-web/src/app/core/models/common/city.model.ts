import { ResourceModel } from '../common/resource.model';
import { UserProfile } from '../user';
import { Country } from './country.model';
import { State } from './state.model';

export class City extends ResourceModel<City> {
 
  cityName: string;
  isActive: boolean;
  country: Country;
  state: State;
  userProfile: UserProfile[];
  //   institutions: Institution[];

  constructor(model?: Partial<City>) {
    super(model);
  }
}
