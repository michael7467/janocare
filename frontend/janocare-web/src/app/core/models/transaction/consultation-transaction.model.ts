import { ResourceModel } from "../common/resource.model";

export class ConsultationTransactions extends ResourceModel<ConsultationTransactions>  {
    amount: number;
    referenceNumber: string;
    paymentType:string;
    settlementStatus:number;
    transactionDate:Date;
    transactionNote:string;
    constructor(model?: Partial<ConsultationTransactions>) {
        super(model);
    }
    
}