import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";

export class BlogComment extends ResourceModel<BlogComment>  {

 
    status: string;
    publishedAt: Date;
    content: string;
    postId: number;
    userId: number;
    constructor(model?: Partial<BlogComment>) {
        super(model);
    }
    
}