import { ResourceModel } from "../common/resource.model";

export class SettlementAccount extends ResourceModel<SettlementAccount>  {

    accountNumber: string;
    priority: number;
    professionalId: number;
    bankId:Number;
    
    constructor(model?: Partial<SettlementAccount>) {
        super(model);
    }
    
}