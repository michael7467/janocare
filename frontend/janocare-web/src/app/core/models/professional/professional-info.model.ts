import { ResourceModel } from '../common/resource.model';

export class ProfessionalInfo extends ResourceModel<ProfessionalInfo> {
  officeNumber: string;
  daysOfWeek: string;
  startTime: string;
  endTime: string;
  institutionUserId: number;
  professionalUserId: number;
  isAvailable: boolean;
  public override id?: number = undefined;

  constructor(model?: Partial<ProfessionalInfo>) {
    super(model);
  }
}
