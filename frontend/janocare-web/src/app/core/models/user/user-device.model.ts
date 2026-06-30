import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';

export class UserDevice extends ResourceModel<UserDevice> {
  
  device_type: 'WEB' | 'APP' | 'API' | 'WINDOWS' | 'Ubuntu';
  // device_token: string;
  is_active: boolean;
  user: User;
  constructor(model?: Partial<UserDevice>) {
    super(model);
  }
}
