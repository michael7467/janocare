import { ResourceModel } from '../common/resource.model';

export class Bank extends ResourceModel<Bank> {

  bank_name: string;
  is_active: boolean;

  constructor(model?: Partial<Bank>) {
    super(model);
  }
}
