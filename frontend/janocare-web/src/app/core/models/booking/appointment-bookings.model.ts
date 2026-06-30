import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class AppointmentBookings extends ResourceModel<AppointmentBookings>  {

    bookingDate: Date;
    bookingSlotId: string;
    bookingStatus: string;
    bookingType: string;
    professionalId:Number;
    dependantId:Number;
    bookingReason: string;
    timezone: string;
    type:string;
    patient:Patient;
    constructor(model?: Partial<AppointmentBookings>) {
        super(model);
    }
    
}