import { ResourceModel } from "../common/resource.model";

export class SettlementTransactions extends ResourceModel<SettlementTransactions>  {

  
    transactionFrom: string;
    transactionTo: string;
    transactionCount:number;
    totalAmount:number;
    taxDeducted:number;
    serviceCharge:number;
    settlementDate:Date;
    status:string;
    professionalSettlementAccountId:number;
    constructor(model?: Partial<SettlementTransactions>) {
        super(model);
    }
    
}