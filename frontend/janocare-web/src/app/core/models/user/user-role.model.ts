import { ResourceModel } from '../common/resource.model';
import { User } from './user.model';

export class UserRole extends ResourceModel<UserRole> {
 
  role_name: 'ADMIN' | 'ACCOUNT_MANAGER' | 'USER' | 'INSTITUTION_USER' | 'PROFESSIONAL_USER' | 'FINANCE' | 'CUSTOMER_CARE';
  role_description: string;
  created_at: string;
  updated_at: string;
  users: User[];
  constructor(model?: Partial<UserRole>) {
    super(model);
  }
}
