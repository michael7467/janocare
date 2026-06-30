import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";
import { DoctorBlog } from "./doctor-blog.model";
import { User } from "../user";

export class PostVote extends ResourceModel<PostVote>  {


    vote: string;
    voteType:string;
    postId:number;
    userId:number;
    post:DoctorBlog;
    user:User;
    constructor(model?: Partial<PostVote>) {
        super(model);
    }
    
}