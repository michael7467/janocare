import { ResourceModel } from '../common/resource.model';

export class BookingSlotAnalyzed extends ResourceModel<BookingSlotAnalyzed>  {

    slotDate: Date;
    slotInterval: number;
    Total: string;
    ACCEPTED:Number;
    CANCELLED:Number;
    COMPLETED:Number;
    PENDING:Number;
    REJECTED:Number;

    
    constructor(model?: Partial<BookingSlotAnalyzed>) {
        super(model);
    }
    
}