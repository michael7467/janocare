import { ResourceModel } from '../common/resource.model';
import { Patient } from "../patient";
import { ProfessionalUser } from "../professional";
import { PostVote } from "./post-vote.model";
import { DoctorBlogMeta } from "./doctor-blog-meta.model";

export class DoctorBlog extends ResourceModel<DoctorBlog>  {

    publishedAt: Date;
    title: string;
    metaTitle: string;
    slug: string;
    summary:string;
    status:string;
    views:number;
    upVotes:number;
    downVotes:number;
    content:string;
    postMetas:DoctorBlogMeta[];
    professionalId:number;
    postCategoryId:number;
    professional:ProfessionalUser;
    postVotes:PostVote[];
    constructor(model?: Partial<DoctorBlog>) {
        super(model);
    }
    
}