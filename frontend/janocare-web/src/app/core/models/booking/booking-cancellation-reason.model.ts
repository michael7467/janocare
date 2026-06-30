import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";
import { CancellationReason } from "./cancellation-reason.model";
import { AppointmentBookings } from "./appointment-bookings.model";

export class BookingCancellationReason extends ResourceModel<BookingCancellationReason>  {

    comment: string;
    timezone: string;
    userId: number;
    appointmentBooking: AppointmentBookings;
    cancellationReason: CancellationReason;
    constructor(model?: Partial<BookingCancellationReason>) {
        super(model);
    }
    
}