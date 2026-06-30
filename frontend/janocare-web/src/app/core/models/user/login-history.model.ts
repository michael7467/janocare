import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';

export class LoginHistory extends ResourceModel<LoginHistory> {
 
  login_time: string;
  logout_time: string;
  login_status: 'FAILED' | 'SUCCESS';
  ip_address: string;
  user_agent: string;
  user: User;
  constructor(model?: Partial<LoginHistory>) {
    super(model);
  }
}
