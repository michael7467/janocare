import { ResourceModel } from '../common/resource.model';
import { Role } from '../_base';
import { LoginHistory } from './login-history.model';
import { UserDevice } from './user-device.model';
import { UserProfile } from './user-profile.model';
import { ProfessionalUser } from '../professional/professional_user.model';

export class User extends ResourceModel<User> {
  username: string;
  email: string;
  phone: string;
  national_id: string;
  status: 'PENDING' | 'ACTIVE' | 'BLOCKED' | 'SUSPENDED' | 'INACTIVE' | 'DELETED' | 'ARCHIVED';
  role: Role;
  professionalId:number;
  institutionUserId:number;
  devices: UserDevice[];
  login_histories: LoginHistory[];
  userProfile: UserProfile;
  professional:ProfessionalUser;
  createdAt: string;
  updatedAt: string;

  constructor(model?: Partial<User>) {
    super(model);
  }
}
