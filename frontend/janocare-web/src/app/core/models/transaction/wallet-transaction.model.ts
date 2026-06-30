import { ResourceModel } from "../common/resource.model";

export class WalletTransactions extends ResourceModel<WalletTransactions>  {
 
    previousBalance:number;
    totalAmount:number;
    currentBalance:number;
    transactionType:number;
    narration:string;
    transactionDate:Date;
    actionBy:string;

    
    
    constructor(model?: Partial<WalletTransactions>) {
        super(model);
    }
    
}