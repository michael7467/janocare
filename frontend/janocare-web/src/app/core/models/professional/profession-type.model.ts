import { ResourceModel } from "../common/resource.model";

export class ProfessionType extends ResourceModel<ProfessionType> {
 
    type: string;
    minConsultationFee: string;
    minBookingFee: string;
    minInstantConsultationFee: string;
    maxConsultationFee: string;
    maxBookingFee: string;
    maxInstantConsultationFee: string;
    slotInterval:string;
  constructor(model?: Partial<ProfessionType>) {
    super(model);
  }
}