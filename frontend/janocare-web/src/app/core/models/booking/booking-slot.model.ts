import { ResourceModel } from '../common/resource.model';

export class BookingSlot extends ResourceModel<BookingSlot> {
  slotDate: Date;
  slotInterval: number;
  slotTime: string;
  Total: number;
  PENDING: number;
  REJECTED: number;
  CANCELLED: number;
  COMPLETED: number;
  ACCEPTED: number;
  startTime: string;      // ← add
  endTime: string;        // ← add
    status: string;         // ← add
  professionalId: Number;
  bookingSlotStatus: string;
  constructor(model?: Partial<BookingSlot>) {
    super(model);
  }
}
