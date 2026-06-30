import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";
import { User } from "../user";

export class ChatMessages extends ResourceModel<ChatMessages>  {

    message: string;
    status: string;
    messageType: number;
    senderId: number;
    receiverId: number;
    replyToId: number;
    sender: User;
    receiver: User;
    // cancellationReason: CancellationReason;
    constructor(model?: Partial<ChatMessages>) {
        super(model);
    }
    
}