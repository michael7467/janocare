import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class BlogCommentReply extends ResourceModel<BlogCommentReply>  {

    postCommentId:number;
    status: string;
    publishedAt: Date;
    content: string;
    userId: number;
    constructor(model?: Partial<BlogCommentReply>) {
        super(model);
    }
    
}