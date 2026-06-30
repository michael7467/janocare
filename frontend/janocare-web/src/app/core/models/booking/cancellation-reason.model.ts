import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class CancellationReason extends ResourceModel<CancellationReason>  {

    reason: string;
    constructor(model?: Partial<CancellationReason>) {
        super(model);
    }
    
}