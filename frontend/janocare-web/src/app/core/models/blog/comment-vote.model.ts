import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";
import { DoctorBlog } from "./doctor-blog.model";
import { User } from "../user";
import { BlogComment } from "./blog-comment.model";

export class CommentVote extends ResourceModel<CommentVote>  {


    vote: string;
    voteType:string;
    postId:number;
    userId:number;
    postCommentId:number;
    post:DoctorBlog;
    user:User;
    postComment:BlogComment;
    constructor(model?: Partial<CommentVote>) {
        super(model);
    }
    
}