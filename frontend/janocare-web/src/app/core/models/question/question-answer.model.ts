import { ResourceModel } from "../common/resource.model";
import { Patient } from "../patient";

export class QuestionAnswer extends ResourceModel<QuestionAnswer>  {

 
    status: string;
    publishedAt: Date;
    content: string;
    postId: number;
    userId: number;
    constructor(model?: Partial<QuestionAnswer>) {
        super(model);
    }
    
}